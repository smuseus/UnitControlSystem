import java.util.ArrayList;

import processing.core.PVector;

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
	      units.add(new Unit(new PVector(p.mouseX, p.mouseY), p)); 
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
				  if(u.solenoidNeedsToBeUpdated(i)) {
					  if(true) { // TODO: switch back
//					  if(p.comm.send(u.address, p.comm.updown(i, u.solenoidInstr[i]))) {
						  u.updateSolenoid(i);
					  } else {
						  u.clearSolenoidInstructions(i);
					  }
				  }
			  }
			  if(u.blowerNeedsToBeUpdated()) {
				  if(u.solenoidsAreClosed()) {
					  if(u.blowerDidItsOfftime()) {
						  if(true) { // TODO: switch back
	//					  if(p.comm.send(u.address, p.comm.dimm(u.dimmerInstr))) {
							  u.updateBlower();
						  } else {
							  u.clearBlowerInstructions();
						  }
					  } else {
						  u.clearBlowerInstructions();
						  p.println("Blower is still cooling");
					  }
				  } else {
					  u.closeAllSolenoids();
				  }
			  }
			  if(u.blowerOnTooLong()){
				  u.turnOffBlower();
			  }
		  }
	  }

}
