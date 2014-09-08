import themidibus.*;
import javax.sound.midi.MidiMessage;


public class MidiIO {
	UnitControlSystem p;
	MidiBus midibus;
	
	int channel = 0;
	int velocity = 127;
	
	MidiIO(UnitControlSystem parent, MidiBus midi) {
		p = parent;
		MidiBus.list();
		// "Abelton" bus gets inputs from Abelton Live, used to playback recorded midi-tracks.
		// "Eclipse" bus is used for output, so that activity can be recorded into midi-tracks in Abelton.
		// Set up midibuses with Audio Midi Setup > Window > Midi Window .. Virtual > Port > + (Look in Research 2014/Abelton for instruction).
		midibus = midi;
	}
	
	public void sendSolenoid(Unit u, int solenoid) {
		if(u.solenoidState[solenoid]) {
			// Send Up.
			midibus.sendNoteOn(channel, u.midiID + solenoid + 1, velocity );
			p.println("Midi ON sent: ", channel, u.midiID + solenoid + 1, velocity);
		} else {
			// Send Down.
			midibus.sendNoteOff(channel, u.midiID + solenoid + 1, velocity );
			p.println("Midi OFF sent: ", channel, u.midiID + solenoid + 1, velocity);
		}
	}
	
	public void sendDimmer(Unit u) {
		// TODO: get dimmer to right type (boolean?).
		if(u.blowerState == false)  { 
			midibus.sendNoteOn(channel, u.midiID, velocity );
		} else {
			midibus.sendNoteOff(channel, u.midiID, velocity );
		}
	}
	
	void midiMessage(MidiMessage message) { // You can also use midiMessage(MidiMessage message, long timestamp, String bus_name)
		  // Receive a MidiMessage
		  // MidiMessage is an abstract class, the actual passed object will be either javax.sound.midi.MetaMessage, javax.sound.midi.ShortMessage, javax.sound.midi.SysexMessage.
		  // Check it out here http://java.sun.com/j2se/1.5.0/docs/api/javax/sound/midi/package-summary.html
		  p.println();
		  p.println("MidiMessage Data @ " + p.millis());
		  p.println("--------");
		  p.println("Status Byte/MIDI Command:"+message.getStatus());
		  for (int i = 1;i < message.getMessage().length;i++) {
		    p.println("Param "+(i+1)+": "+(int)(message.getMessage()[i] & 0xFF));
		  }
		}
}
