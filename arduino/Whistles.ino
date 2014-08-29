
/* 
* ioioioio+aeaeaeae
*/

#include <S.h>
#include <XBee.h>
#include <SoftwareSerial.h>

//Solenoid S (int pin, int pin2)
// Pin 7 is interupt 4 - used for zero cross



S s[5] = {
  S(12,10,11),
  S(9,8,11),
  S(6,5,11), 
  S(3,4,11),
  S(A5,A4,11)
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
};



//UNIT 44
//S s[5] = { S(A1,A0,5), S(8,9,4),  S(A3,A2,10), S(11,12,6), S(A5,A4,3)  };

//UNIT 11
//S s[5] = { S(A1,A0,5), S(8,9,4), S(A3,A2,10), S(11,12,6), S(A5,A4,3) };

//UNIT 00
//S s[5] = { S(A3,A2,10), S(8,9,4), S(A1,A0,5), S(A5,A4,3), S(11,12,6) };

// UNIT 22
//S s[5] = { S(A3,A2,10), S(11,12,6), S(A5,A4,3), S(A1,A0,5), S(8,9,4) };


//dimming stuff
int AC_LOAD = 13;    // Output to Opto Triac pin
int dimming = 0;  // Dimming level 0-100

volatile int hightime;
volatile int offtime;
volatile int pzero;

//workaround
int _state;

//bzzzz
XBee xbee = XBee();
XBeeResponse response = XBeeResponse();
// create reusable response objects for responses we expect to handle 
ZBRxResponse rx = ZBRxResponse();
ModemStatusResponse msr = ModemStatusResponse();


void setup()
{
  //pinMode(11, OUTPUT);
  pinMode(AC_LOAD, OUTPUT);// Set AC Load pin as output
  digitalWrite(AC_LOAD, LOW);
//  attachInterrupt(4, zero_crosss_int, RISING);  timer4 is pin 7
  
  
  Serial.begin(9600); //Debug serial

/*
  Serial1.begin(9600);
  xbee.begin(Serial1);
*/
}

/*
void zero_crosss_int()  
{
  //this gets called every zerocross from the AC wave
   // 1 full 50Hz wave = 1/50 = 20ms  
  // Every zerocrossing thus: (50Hz)-> 10ms (1/2 Cycle) 
  // 10ms=10000us
   
 //need to get this fucker right Funcking clock does not count inside a interrupt!!! 
//basically turning ACLOAD on and OFF with right delay = dimm
  
}
*/


void loop()
{

 if (Serial.available()) { handleInput(Serial.read()); }                
/*  xbee.readPacket();
    if (xbee.getResponse().isAvailable()) {

/*      Serial.println(" got something");
         Serial.println(rx.getDataLength());
         Serial.println(char(rx.getData(0)));
         Serial.println(char(rx.getData(1)));
         Serial.println(char(rx.getData(2)));
         Serial.println(char(rx.getData(3)));
      

       //stuff gets handled by serial_com
      for ( int s =0 ; s <= rx.getDataLength(); s++ ) { 
        handleInput(char(rx.getData(s)));
      }

      
      //loads of crap below.. mostly debuggin code from example.. might come handy though, when we try on the field
      
   
      if (xbee.getResponse().getApiId() == ZB_RX_RESPONSE) {
        // got a zb rx packet  
        // now fill our zb rx class
        xbee.getResponse().getZBRxResponse(rx);
            
        if (rx.getOption() == ZB_PACKET_ACKNOWLEDGED) {
            // the sender got an ACK
   //         flashLed(statusLed, 10, 10);
        } else {
            // we got it (obviously) but sender didn't get an ACK
    //        flashLed(errorLed, 2, 20);
        }
       
      } else if (xbee.getResponse().getApiId() == MODEM_STATUS_RESPONSE) {
        xbee.getResponse().getModemStatusResponse(msr);
        // the local XBee sends this response on certain events, like association/dissociation
        
        if (msr.getStatus() == ASSOCIATED) {
          // yay this is great.  flash led
      //    flashLed(statusLed, 10, 10);
        } else if (msr.getStatus() == DISASSOCIATED) {
          // this is awful.. flash led to show our discontent
        //  flashLed(errorLed, 10, 10);
        } else {
          // another status
       //   flashLed(statusLed, 5, 10);
        }
      } else {
      	// not something we were expecting
    //    flashLed(errorLed, 1, 25);    
      }
    } else if (xbee.getResponse().isError()) {
      //nss.print("Error reading packet.  Error code: ");  
      //nss.println(xbee.getResponse().getErrorCode());
    }


 delay(10);
*/
 
 }
 


