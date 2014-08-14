//Java is really horrible

class Comm {
  
  String COMPORT = "/dev/ttyUSB0";
  
  XBee xbee = new XBee();
  
  int[] payload = new int[] { 'X', 'B', 'E', 'E' };

  IntList dig = new IntList();


  Comm(){
    

    
    
   // for (XBeeAddress64 address : addrs) {
      ZNetTxRequest request = new ZNetTxRequest(addrs[0], payload);

      try{
        xbee.open(COMPORT, 9600);
        println("zb request is " + request.getXBeePacket().getPacket());
        println("sending tx " + request);
      ZNetTxStatusResponse response = (ZNetTxStatusResponse) xbee.sendSynchronous(request, 10000);
  println("received response " + response);
      }  
      catch (Exception e) {
        System.out.println("XBee failed to initialize");
        e.printStackTrace();
        System.exit(1);
      }
      
   
    
  }

  void send (int to_whom, int[] payload) {



    try{
      ZNetTxRequest request = new ZNetTxRequest(addrs[to_whom], payload);
      ZNetTxStatusResponse response = (ZNetTxStatusResponse) xbee.sendSynchronous(request, 10000);
      request.setFrameId(xbee.getNextFrameId()); // update frame id for next request
      println("received response " + response.getDeliveryStatus());
     
              //log.debug("status response bytes:" + ByteUtils.toBase16(response.getPacketBytes()));

          if (response.getDeliveryStatus() == ZNetTxStatusResponse.DeliveryStatus.SUCCESS) {
            // the packet was successfully delivered
            if (response.getRemoteAddress16().equals(XBeeAddress16.ZNET_BROADCAST)) {
              // specify 16-bit address for faster routing?.. really only need to do this when it changes
              request.setDestAddr16(response.getRemoteAddress16());
            }              
          } else {
            // packet failed.  log error
            // it's easy to create this error by unplugging/powering off your remote xbee.  when doing so I get: packet failed due to error: ADDRESS_NOT_FOUND  
            println("packet failed due to error: " + response.getDeliveryStatus());
          }
          
          // I get the following message: Response in 75, Delivery status is SUCCESS, 16-bit address is 0x08 0xe5, retry count is 0, discovery status is SUCCESS 
      //    println("Response in " + (System.currentTimeMillis() - start) + ", Delivery status is " + response.getDeliveryStatus() + ", 16-bit address is " + ByteUtils.toBase16(response.getRemoteAddress16().getAddress()) + ", retry count is " +  response.getRetryCount() + ", discovery status is " + response.getDeliveryStatus());          
    }  catch (XBeeTimeoutException e) {
          println("request timed out");
        }
        catch (XBeeException e) {
          println("Fuckwhat?");
        }


          
  }  


  int[] up (int sol){ //int unit,  0-4

    payload [0] = 48+sol;
    payload [1] = 'u';
    payload [2] = 48;
    payload [3] = 48;
    
//   send (addrs[unit], payload); //// does not work in java, would have written differently if knew
    return payload;
    
    
  }
  
  int[]  down (int sol){ //int unit, 

    payload [0] = 48+sol;
    payload [1] = 'd';
    payload [2] = 48;
    payload [3] = 48;

 //   send (addrs[unit], payload);
   
     return payload;
  
  }
  
  int[] dimm(int value){ //still a bit misterious hardware/zerocross code
    
    dig = digits(value);
    
    for(int u = dig.size(); u > 0; u--){
      payload[dig.size() - u] = 48+dig.get(u-1);
      println(u);
    }
    
     payload [dig.size()] = 'b';
     
//     send (addrs[unit], payload);
     dig.clear();
     
         return payload;

  }



  
}


