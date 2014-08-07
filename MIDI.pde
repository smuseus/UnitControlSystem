class MidiHandler {
  
  MidiBus bus;

  MidiHandler(PApplet p) {
    bus = new MidiBus(p, 0, 0);
    bus.sendTimestamps(false);
  }

  void rawMidi(byte[] data) {
    if(user.mode.equals("MIDI")) {
      setMidi(data);
    } else {
      for(Unit u : handler.units) {
        if(u.midiChannel == (int)(data[1] & 0xFF)) {
          u.dimmer = (float)(data[2] & 0xFF)/127.0;
        }
      }
    }
  }
  
  void setMidi(byte[] data) {
    if(user.selectedUnit != null) {
      user.selectedUnit.midiChannel = (int)(data[1] & 0xFF);
    }
    user.mode = "USER";
  }
}
