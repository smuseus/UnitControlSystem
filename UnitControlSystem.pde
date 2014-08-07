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

UnfoldingMap map;
UnitHandler  handler;
UserInput    user;
MidiHandler  midi;

void setup() {
  size(800,600);
  map = new UnfoldingMap(this, new Microsoft.AerialProvider());
  map.zoomAndPanTo(new Location(52.5f, 13.4f), 100);
  handler = new UnitHandler();
  user = new UserInput(this);
  midi = new MidiHandler(this);

}

void draw() {
 map.draw();
 handler.draw(); 
 user.draw();
}

// Input event Handeling

void mousePressed() {
  user.mousePressed();
}

void mouseDragged() {
  user.mouseDragged();
}

void mouseReleased() {
  user.mouseReleased();
}

void keyPressed() {
  user.keyPressed();
}

void rawMidi(byte[] data) {
  midi.rawMidi(data);
}
  
