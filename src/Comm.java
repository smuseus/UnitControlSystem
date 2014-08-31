import processing.data.IntList;

// Instruction for setting up the RXTX serial with Mac OSX.
// https://sites.google.com/site/enginerdextraordinaire/the-team/java-project/xbee-api-in-netbeans
// librxtxSerial.jnilib compiled for 64-bit Mac OSX.

import com.rapplogic.xbee.api.*;
import com.rapplogic.xbee.api.zigbee.*;

public class Comm {

	UnitControlSystem p;

	String COMPORT = "/dev/tty.usbserial-AD02AHVC";

	XBee xbee = new XBee();

	// Keep for backup.
	XBeeAddress64[] addrs = {
			new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0x9e, 0xec, 0xcd),
			new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0x9e, 0xec, 0xc8), };

	int[] payload = new int[] { 'X', 'B', 'E', 'E' };

	IntList dig = new IntList();

	Comm(UnitControlSystem parent) {
		p = parent;
		
		// for (XBeeAddress64 address : addrs) {
		ZNetTxRequest request = new ZNetTxRequest(addrs[0], payload);

		try {
			xbee.open(COMPORT, 9600);
			p.println("zb request is " + request.getXBeePacket().getPacket());
			p.println("sending tx " + request);
			ZNetTxStatusResponse response = (ZNetTxStatusResponse) xbee
					.sendSynchronous(request, 10000);
			p.println("received response " + response);
		} catch (Exception e) {
			System.out.println("XBee failed to initialize");
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	void sync() {
	// Compare
	}

	boolean send(XBeeAddress64 to_whom, int[] payload) {

		try {
			ZNetTxRequest request = new ZNetTxRequest(to_whom, payload);
			ZNetTxStatusResponse response = (ZNetTxStatusResponse) xbee.sendSynchronous(request, 10000);
			request.setFrameId(xbee.getNextFrameId()); // update frame id for next request
			p.println("received response " + response.getDeliveryStatus());

			// log.debug("status response bytes:" +
			// ByteUtils.toBase16(response.getPacketBytes()));

			if (response.getDeliveryStatus() == ZNetTxStatusResponse.DeliveryStatus.SUCCESS) {
				// the packet was successfully delivered
				if (response.getRemoteAddress16().equals(XBeeAddress16.ZNET_BROADCAST)) {
					// specify 16-bit address for faster routing?.. really only
					// need to do this when it changes
					request.setDestAddr16(response.getRemoteAddress16());
				}
			return true;
			} else {
				// packet failed. log error
				// it's easy to create this error by unplugging/powering off
				// your remote xbee. when doing so I get: packet failed due to
				// error: ADDRESS_NOT_FOUND
				p.println("packet failed due to error: " + response.getDeliveryStatus());
				return false;
			}

			// I get the following message: Response in 75, Delivery status is
			// SUCCESS, 16-bit address is 0x08 0xe5, retry count is 0, discovery
			// status is SUCCESS
			// println("Response in " + (System.currentTimeMillis() - start) +
			// ", Delivery status is " + response.getDeliveryStatus() +
			// ", 16-bit address is " +
			// ByteUtils.toBase16(response.getRemoteAddress16().getAddress()) +
			// ", retry count is " + response.getRetryCount() +
			// ", discovery status is " + response.getDeliveryStatus());
		} catch (XBeeTimeoutException e) {
			p.println("request timed out");
		} catch (XBeeException e) {
			p.println("Fuckwhat?");
		}
		return false;
	}

	
	// Combines the up and down method into a single method, where the "up" payload is returned if 
	// state is equals true, or the "down" payload if state equals false. This just makes using the send function
	// more convient, since the Unit class stores the solnoid states in an array of booleans.
	int[] updown(int sol, boolean state) {
		if(state) {
			payload[0] = 48 + sol;
			payload[1] = 'u';
			payload[2] = 48;
			payload[3] = 48;
			return payload;
		} else {
			payload[0] = 48 + sol;
			payload[1] = 'd';
			payload[2] = 48;
			payload[3] = 48;
			return payload;	
		}
	}
	
	int[] up(int sol) {
		payload[0] = 48 + sol;
		payload[1] = 'u';
		payload[2] = 48;
		payload[3] = 48;
		return payload;
	}

	int[] down(int sol) {
		payload[0] = 48 + sol;
		payload[1] = 'd';
		payload[2] = 48;
		payload[3] = 48;
		return payload;
	}

	int[] dimm(boolean value) { // Zero cross is out, now just sending boolean, 0 for off, n < 0 for on.

		dig = digits((value) ? 1 : 0);

		for (int u = dig.size(); u > 0; u--) {
			payload[dig.size() - u] = 48 + dig.get(u - 1);
			p.println(u);
		}

		payload[dig.size()] = 'b';

		// send (addrs[unit], payload);
		dig.clear();

		return payload;
	}

	IntList digits(int i) {
		IntList digits = new IntList();
		while (i > 0) {

			digits.append(i % 10);
			i /= 10;
		}
		return digits;
	}
}
