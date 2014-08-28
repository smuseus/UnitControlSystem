import com.rapplogic.xbee.api.XBeeAddress64;

import de.fhpotsdam.unfolding.geo.*;

public class Unit {
	  UnitControlSystem p;
	  Apperance apperance;
	  
	  String id;
	  XBeeAddress64 address;
	  Location gps;
	  float orientation;
	  
	  char[] keySet = {'2','3','e','w','q'};
	  int midiID; // 40 + unit # * 10, ex. unit 22 = 40 + 2 * 10 = 60;

	  boolean[] solenoidState = { false, false, false, false, false };
	  boolean[] solenoidInstr = { false, false, false, false, false };
	  int dimmerState = 0; // TODO: Needs to match the datatype of the comm.dimm method.
	  int dimmerInstr = 0;

	  Unit(Location location, UnitControlSystem parent) {
		p = parent;
		apperance = new Apperance(this, p);
		
	    id = "Default"; 
	    address = new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0x9e, 0xec, 0xcd);
	    gps = location;
	    orientation = 0; // Offset form north in radiants. minus to left, plus to right.      
	  }
	  
	  void draw() {
	    apperance.draw();
	  }
}
