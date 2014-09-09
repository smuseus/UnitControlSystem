import processing.core.PVector;

import com.rapplogic.xbee.api.XBeeAddress64;

import de.fhpotsdam.unfolding.geo.*;

public class Unit {
	  UnitControlSystem p;
	  Apperance apperance;
	  
	  String id;
	  XBeeAddress64 address;
	  PVector location;
	  float orientation;
	  
	  char[] keySet = {'2','3','e','w','q','0'};
	  int midiID; // 40 + unit # * 10, ex. unit 22 = 40 + 2 * 10 = 60;

	  boolean[] solenoidState = { true, true, true, true, true }; // (State is reversed, false is on, true is off, due to reversed array on Arduino).
	  boolean[] solenoidInstr = { true, true, true, true, true };
	  boolean blowerState = false;
	  boolean blowerInstr = false;
	  
	  int maxontime = 600000; // in millis, ten minutes
	  int neededofftime = 180000; // in millis, three minutes
	  
	  int ontime = 0;
	  int offtime = -neededofftime;

	  Unit(PVector location, UnitControlSystem parent) {
		p = parent;
		apperance = new Apperance(this, p);
		
	    id = "Default"; 
	    address = new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0x9e, 0xec, 0xcd);
	    this.location = location;
	    orientation = 0; // Offset form north in radiants. minus to left, plus to right.      
	  }
	  
	  void draw() {
	    apperance.draw();
	  }	  
	  
	  boolean solenoidNeedsToBeUpdated(int i) {
		  if(solenoidState[i] != solenoidInstr[i]) {
			  return true;
		  } else {
			  return false;
		  }
	  }
	  
	  boolean blowerNeedsToBeUpdated() {
		  if(blowerState != blowerInstr) {
			  return true;
		  } else {
			  return false;
		  }
	  }
	  
	  boolean solenoidsAreClosed() {
		  for(int i=0; i < solenoidState.length; i++) {
			  if(!solenoidState[i]) return false;
		  }
		  return true;
	  }
	  
	  void closeAllSolenoids() {
		  for(int i=0; i < solenoidInstr.length; i++) { 
			  solenoidInstr[i] = true;
		  }
	  }
	  
	  void updateSolenoid(int i) {
		  solenoidState[i] = solenoidInstr[i];
		  p.println("Solenoid #" + i+ " state update successfull");
		  //p.midi.sendSolenoid(this, i);
	  }
	  
	  void clearSolenoidInstructions(int i) {
		  solenoidInstr[i] = solenoidState[i];  
		  p.println("Solenoid state not updated.");
	  }
	  
	  void turnOnBlower() {
		  blowerInstr = true;
	  }
	  
	  void turnOffBlower() {
		  blowerInstr = false;
	  }
	  
	  void turnOnWhistle(int i) {
		  solenoidInstr[i] = false;
	  }
	  
	  void turnOffWhistle(int i) {
		  solenoidInstr[i] = true;
	  }
	  
	  void toggleWhistle(int i) {
		  solenoidInstr[i] = !solenoidInstr[i];
	  }
	  
	  void updateBlower() {
		  blowerState = blowerInstr;
		  if(blowerState) {
			  ontime = p.millis();
		  } else {
			 offtime = p.millis();
		  }
		  p.println("Blower state update successfull");
		  //p.midi.sendDimmer(this); // TODO: Fix such that it matches dimmer.
	  }
	  
	  void clearBlowerInstructions() {
		  blowerInstr = blowerState;
		  p.println("Blower state not updated.");
	  }
	  
	  boolean blowerDidItsOfftime() {
		  if(p.millis() - offtime > neededofftime ) {
			  return true;
		  } else {
			  return false;
		  }
	  }
	  
	  boolean blowerOnTooLong() {
		  if(p.millis() - ontime < maxontime ) {
			  return false;
		  } else {
			  return true;
		  }
	  }
}
