import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.utils.*;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.events.EventDispatcher;
import de.fhpotsdam.unfolding.events.MapEvent;
import de.fhpotsdam.unfolding.events.PanMapEvent;
import de.fhpotsdam.unfolding.events.ZoomMapEvent;
import de.fhpotsdam.unfolding.interactions.MouseHandler;

import themidibus.*;
import javax.sound.midi.MidiMessage; //Import the MidiMessage classes http://java.sun.com/j2se/1.5.0/docs/api/javax/sound/midi/MidiMessage.html
import javax.sound.midi.SysexMessage;
import javax.sound.midi.ShortMessage;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeTimeoutException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetTxStatusResponse;
import com.rapplogic.xbee.util.ByteUtils;


UnfoldingMap map;
UnitHandler  handler;
UserInput    user;
MidiHandler  midi;

Comm comm;


 XBeeAddress64[] addrs = {
   new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0x9e, 0xec, 0xcd), 
   new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0x9e, 0xec, 0xc8),
   
 };
  



/* * COORDINATOR config:
 * 
 * - Reset to factory settings: 
 * ATRE
 * - Put in API mode 2
 * ATAP 2
 * - Set PAN id to arbitrary value
 * ATID 1AAA
 * - Set the Node Identifier (give it a meaningful name)
 * ATNI COORDINATOR
 * - Save config
 * ATWR
 * - reboot
 * ATFR
 *
 * The XBee network will assign the network 16-bit MY address.  The coordinator MY address is always 0
 * 
 * X-CTU tells SH and SL Addresses
 * 
 * END DEVICE config:
 * 
 * - Reset to factory settings: 
 * ATRE
 * - Put in API mode 2
 * ATAP 2
 * - Set PAN id to arbitrary value
 * ATID 1AAA
 * - Set the Node Identifier (give it a meaningful name)
 * ATNI END_DEVICE_1
 * - Save config
 * ATWR
 * - reboot
 * ATFR
*/


void setup() {

  size(800,600);
  map = new UnfoldingMap(this, new Microsoft.AerialProvider());
  map.zoomAndPanTo(new Location(52.5f, 13.4f), 100);
  handler = new UnitHandler();
  user = new UserInput(this);
  midi = new MidiHandler(this);
  comm = new Comm();



}




void draw() {
 map.draw();
 handler.draw(); 
 user.draw();
}

// Input event Handeling

void mousePressed() {
  user.mousePressed();
  comm.send(0, comm.up(0));
  comm.send(0, comm.up(1));
  comm.send(0, comm.up(2));
  comm.send(0, comm.up(3));
  comm.send(0, comm.up(4));
}

void mouseDragged() {
  user.mouseDragged();
}

void mouseReleased() {
  user.mouseReleased();
  comm.send(0, comm.down(0));
  comm.send(0, comm.down(1));
  comm.send(0, comm.down(2));
  comm.send(0, comm.down(3));
  comm.send(0, comm.down(4));comm.send(0, comm.dimm(76));
}

void keyPressed() {
  user.keyPressed();
}

void rawMidi(byte[] data) {
  midi.rawMidi(data);
}

