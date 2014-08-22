import java.util.ArrayList;

/*
 * Unit 
 * 
 * Coordinator 
 * 	64-bit Address: 13 A2 00 40 B0 E8 FF
 * 
 * Unit A
 * 	64-bit Address: 
 * 
 * Unit B
 * Unit C
 * Unit D
 * Unit E
 *
 * 
 */

public class UnitHandler {
	  UnitControlSystem p;
	  
	  ArrayList<Unit> units;	    

	  UnitHandler(UnitControlSystem parent) {
		p = parent;
	    units = new ArrayList<Unit>();

	  }
	  
	  void draw() {
	    for(Unit u : units) {
	      u.draw();
	    }
	  }
	  
	  void addUnit() {
	      units.add(new Unit(p.map.getLocation(p.mouseX,p.mouseY), p)); 
	  }
	  
	  void addUnit(Unit u) {
		  units.add(u);
	  }
	  
	  void removeUnit(Unit u) {
	    if( u != null) {
	      units.remove(u);
	    }
	  }
	  
	  void clear() {
		  units.clear();
	  }
	  
	  // Compares the instruction set of the solenoids with the state set. If 
	  // there is an difference, it try sending this update to the solenoid, if this returns success,
	  // it will also update the new state to that of the instruction set. 
	  // Does the same for the dimmer.
	  void sync() {
		  for(Unit u : units) {
			  for(int i=0; i<u.solenoidState.length; i++) {
				  if(u.solenoidState[i] != u.solenoidInstr[i]) {
					  if(p.comm.send(u.address, p.comm.updown(i, u.solenoidInstr[i]))) {
						  u.solenoidState[i] = u.solenoidInstr[i];
						  p.println("Solenoid state update successfull");
					  } else {
						  p.println("Solenoid state not updated.");
					  }
				  }
				  if(u.dimmerState != u.dimmerInstr) {
					  if(p.comm.send(u.address, p.comm.dimm(u.dimmerInstr))) {
						  u.dimmerState = u.dimmerInstr;
						  p.println("Dimmer state update successfull");
					  } else {
						  p.println("Dimmer state not updated.");
					  }
				  }
			  }
		  }
	  }
}
