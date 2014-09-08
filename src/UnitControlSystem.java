/*
 * TODO:
 * 
 * 
 * 	Add start up function : send all command five times, end with all off, all down.
 * 
 *	Finish MIDI bus passing.
 * 
 */


import javax.sound.midi.MidiMessage;

import processing.core.*;
import themidibus.*;
import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class UnitControlSystem extends PApplet {

	public UnfoldingMap map;
	public UnitHandler handler;
	public UserInput user;
	public Comm comm;
	public FileHandler file;
	public MidiIO midi;
	public MidiBus midibus;
	
	public boolean ONLINE_MAP = false;

	public void setup() {
		size(600, 1024);
		map = new UnfoldingMap(this, new Microsoft.AerialProvider());
		handler = new UnitHandler(this);
		user = new UserInput(this);
		//comm = new Comm(this); // Wait with xbee, still needs to be debugged.
		file = new FileHandler(this);
		midibus = new MidiBus(this, "Abelton", "Eclipse");
		//midi = new MidiIO(this, midibus);
	}

	public void draw() {
		handler.sync(); // Wait with xbee, still needs to be debugged. 
		map.draw();
		handler.draw();
		user.draw();
	}
	
	public void mousePressed() {
		user.mousePressed();
	}
	
	public void mouseDragged() {
		user.mouseDragged();
	}
	
	public void mouseReleased() {
		user.mouseReleased();
	}
	
	public void keyPressed() {
		user.keyPressed();
	}
	
	public void midiMessage(MidiMessage message) { // You can also use midiMessage(MidiMessage message, long timestamp, String bus_name)
		midi.midiMessage(message);
	}

	PVector pentatip(int i, float r, float o) {
		return new PVector(cos(TWO_PI / 5 * (i) + o - HALF_PI) * r, sin(TWO_PI
				/ 5 * (i) + o - HALF_PI)
				* r);
	}

	PVector pentaside(int i, float r, float o) {
		return new PVector(cos((float) (TWO_PI / 5 * (i + 0.5) + o - HALF_PI))
				* r, sin((float) (TWO_PI / 5 * (i + 0.5) + o - HALF_PI)) * r);
	}
	
	public static void main(String args[]) {
		PApplet.main(new String[] { "UnitControlSystem" });
	}
}
