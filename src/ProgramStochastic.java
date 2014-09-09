import java.util.ArrayList;
import java.util.Stack;


public class ProgramStochastic {
	UnitControlSystem p;
	
	boolean run = false;
	boolean initializationCycle = true;

	int solenoidChangeHz = 1; // Probability of a solenoid changing state, given in events per second.
	int framerate = 25; // Needs to be set to get accurate probabilistics.
	
	int interval = 4000; 
	int timeTillNextBlowerON = interval;
	ArrayList<Unit> unitSequence;
	Stack<Unit> unitsON, unitsOFF;
	
	ProgramStochastic(UnitControlSystem parent) {
		p = parent;
		p.frameRate(framerate);
		unitsON = new Stack<Unit>();
		unitsOFF = new Stack<Unit>();
	}

	void setup() {
		generateRandomUnitSequence();
		run = true;
	}
	
	void run() {
		if(run) {
			if(initializationCycle) {
				if(timeTillNextBlowerON < p.millis()) {
					activateNext();
					timeTillNextBlowerON = p.millis() + interval; 
					p.println("Next activated");
				}
			}
			
			for(Unit u : unitsON) {
				for(int i=0; i < p.handler.units.size(); i++ ) {
					if(probabilityBasedEvent()) u.toggleWhistle(i);
				}
			}
		}
	}
	
	boolean probabilityBasedEvent() {
		if(p.random(0,1) < solenoidChangeHz / (float) ( framerate * unitsON.size() * 5 )) {
			return true;
		} else {
			return false;
		}
	}
	
	void generateRandomUnitSequence() {
		unitSequence = new ArrayList<Unit>();
		while(unitSequence.size() < p.handler.units.size()) {
			int r = (int) Math.floor( Math.random() * p.handler.units.size() );
			Unit u = p.handler.units.get(r);
			if(!unitSequence.contains(u)) unitSequence.add(u);
		}
	}
	
	void activateNext() {
		if(!unitSequence.isEmpty()) {
			Unit u = (Unit) unitSequence.toArray()[0];
			u.turnOnBlower();
			unitSequence.remove(u);
			unitsON.add(u);
		} else {
			initializationCycle = false;
			p.println("Program initialized, now running continously");
		}
	} 
}
