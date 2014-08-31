import processing.core.PConstants;
import de.fhpotsdam.unfolding.events.*;
import de.fhpotsdam.unfolding.interactions.MouseHandler;
import de.fhpotsdam.unfolding.utils.*;

public class UserInput {
	UnitControlSystem p;
	Unit selectedUnit;
	EventDispatcher mapEvent;

	String mode = "MAP";

	// ALT : option mode.
	// USER : user, use keyboard to control flutes.
	// MAP : zoom, pan with map.
	// UMOVE : move unit.
	// UORIENT : orient unit.
	//

	UserInput(UnitControlSystem parent) {
		p = parent;
		mapInit(p);

	}

	void mousePressed() {
		hitTree();
	}

	void mouseDragged() {
		if (mode.equals("UORIENT"))
			selectedUnit.orientation += (p.mouseX - p.pmouseX) * 0.01;
		if (mode.equals("UMOVE"))
			selectedUnit.gps = p.map.getLocation(p.mouseX, p.mouseY);
	}

	void mouseReleased() {
		if (mode.equals("MAP") || mode.equals("UMOVE") || mode.equals("UORIENT")) mode = "USER";
	}

	void keyPressed() {
		if (p.key == p.CODED) {
			if (p.keyCode == p.ALT)	mode = "ALT";
			if (p.keyCode == p.ENTER) mode = "USER";
		} else {
			if (mode.equals("ALT")) {
				if (p.key == 'a') {
					p.handler.addUnit();
				} else if (p.key == 'd') {
					p.handler.removeUnit(selectedUnit);
					selectedUnit = null;
				} else if (p.key == 'i') {
					if (selectedUnit != null) {
						selectedUnit.id = "";
						mode = "UID";
					}
				} else if (p.key == 'm') {
					mode = "MIDI";
				} else if (p.key == 's') {
					p.file.save();
				} else if (p.key == 'l') {
					p.file.load();
				} else if (p.key == 'c') {
					p.handler.clear();
				} else {
					mode = "USER";
				}
			} else {
				if (mode.equals("USER"))
					keyAction();
				if (mode.equals("UID")) {
					if (p.key == p.CODED) { // TODO: Something quircky with the
											// event handling, to confirm, press
											// ALT then ENTER
						if (p.keyCode == p.ENTER)
							mode = "USER";
					} else {
						selectedUnit.id += p.key;
					}
				}
				if (mode.equals("USETKEY1")) {
					selectedUnit.keySet[0] = p.key;
					mode = "USER";
				}
				if (mode.equals("USETKEY2")) {
					selectedUnit.keySet[1] = p.key;
					mode = "USER";
				}
				if (mode.equals("USETKEY3")) {
					selectedUnit.keySet[2] = p.key;
					mode = "USER";
				}
				if (mode.equals("USETKEY4")) {
					selectedUnit.keySet[3] = p.key;
					mode = "USER";
				}
				if (mode.equals("USETKEY5")) {
					selectedUnit.keySet[4] = p.key;
					mode = "USER";
				}
			}
		}
	}

	void hitTree() {
		// Does mouse hit-testing for all the graphical objects in the scene.
		if (false) {
			// Do something with component.
		} else {
			selectedUnit = checkUnits();
			if (selectedUnit != null) {
				checkUnitComponent(selectedUnit);
			} else {
				mode = "MAP";
			}
		}

		// Post Processes
		if (mode.equals("MAP")) {
			mapActive(true);
		} else {
			mapActive(false);
		}
	}

	Unit checkUnits() {
		ScreenPosition screen;
		float hitRadius = 50;
		for (Unit u : p.handler.units) {
			screen = p.map.getScreenPosition(u.gps);
			if (p.dist(p.mouseX, p.mouseY, screen.x, screen.y) < hitRadius)
				return u;
		}
		return null;
	}

	void checkUnitComponent(Unit u) {
		ScreenPosition screen = p.map.getScreenPosition(u.gps);
		if (p.dist(p.mouseX, p.mouseY,
				screen.x + p.pentatip(0, 30, u.orientation).x,
				screen.y + p.pentatip(0, 30, u.orientation).y) < 20) {
			mode = "UORIENT";
		} else {
			if (p.dist(p.mouseX, p.mouseY,
					screen.x + p.pentaside(0, 40, u.orientation).x, screen.y
							+ p.pentaside(0, 40, u.orientation).y) < 15) {
				mode = "USETKEY1";
			} else if (p.dist(p.mouseX, p.mouseY,
					screen.x + p.pentaside(1, 40, u.orientation).x, screen.y
							+ p.pentaside(1, 40, u.orientation).y) < 15) {
				mode = "USETKEY2";
			} else if (p.dist(p.mouseX, p.mouseY,
					screen.x + p.pentaside(2, 40, u.orientation).x, screen.y
							+ p.pentaside(2, 40, u.orientation).y) < 15) {
				mode = "USETKEY3";
			} else if (p.dist(p.mouseX, p.mouseY,
					screen.x + p.pentaside(3, 40, u.orientation).x, screen.y
							+ p.pentaside(3, 40, u.orientation).y) < 15) {
				mode = "USETKEY4";
			} else if (p.dist(p.mouseX, p.mouseY,
					screen.x + p.pentaside(4, 40, u.orientation).x, screen.y
							+ p.pentaside(4, 40, u.orientation).y) < 15) {
				mode = "USETKEY5";
			} else {
				mode = "UMOVE";
			}
		}
	}

	void keyAction() {
		for (Unit u : p.handler.units) {
			for (int i = 0; i < u.keySet.length-1; i++) { // -1 one because the last char in the array defines the dimmer on/off key.
				if (p.key == u.keySet[i]) {
					u.solenoidState[i] = !u.solenoidState[i]; // TODO: Change back to solenoidIntr, 
				} else 
				if (p.key == u.keySet[5]) {
					u.dimmerState = !u.dimmerState;	// TODO: Change back to dimmerState, 
				}
			}
		}
	}

	void mapInit(UnitControlSystem p) {
		mapEvent = new EventDispatcher();
		MouseHandler mouseHandler = new MouseHandler(p, p.map);
		mapEvent.addBroadcaster(mouseHandler);
		mapActive(true);
	}

	void mapActive(boolean b) {
		if (b) {
			mapEvent.register(p.map, PanMapEvent.TYPE_PAN, p.map.getId());
		} else {
			mapEvent.unregister(p.map, PanMapEvent.TYPE_PAN, p.map.getId());
			mapEvent.unregister(p.map, ZoomMapEvent.TYPE_ZOOM, p.map.getId()); // TODO: Make this happen eariler.
		}
	}

	void draw() {
		p.textAlign(PConstants.LEFT, PConstants.TOP);
		if (mode.equals("ALT")) {
			p.text("a (add), d (delete), i (set id), m (set midi), s (save), l (load), c (clear)", 10, 10);
		}
		p.noStroke(); p.fill(0);
		p.rect(0,p.height-30,p.width,p.height);
		p.fill(255);
		p.text("MODE: " + mode, 10, p.height-25);
		if (selectedUnit != null) {
			p.ellipseMode(p.CENTER);
			p.noFill();
			p.strokeWeight(1);
			p.stroke(255);
			p.ellipse(selectedUnit.apperance.screen.x,
					  selectedUnit.apperance.screen.y, 100, 100);
			p.textAlign(p.CENTER, p.CENTER);
	    	p.text(selectedUnit.address.toString(), selectedUnit.apperance.screen.x, selectedUnit.apperance.screen.y+70);
	    	p.text("Midi: ", selectedUnit.apperance.screen.x, selectedUnit.apperance.screen.y+90);
		}
	}
}
