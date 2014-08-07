class UnitHandler {
  
  ArrayList<Unit> units;
  Unit selected;
  
  UnitHandler() {
    units = new ArrayList<Unit>();
  }
  
  void draw() {
    for(Unit u : units) {
      u.draw();
    }
  }
  
  void addUnit() {
    units.add(new Unit(map.getLocation(mouseX, mouseY)));
  }
  
  void removeUnit(Unit u) {
    if( u != null) {
      units.remove(u);
    }
  }
}

class Unit {
  
  Apperance apperance;
  
  String id;
  boolean[] solenoid = { false, false, false, false, false };
  float dimmer;
  int midiChannel;
  
  Location gps;
  float orientation;
  
  char[] keySet = {'2','3','e','w','q'};
  
  Unit(Location gps) {
    id = "666";
    dimmer = 1.0;
    apperance = new Apperance(this);
    this.gps = gps;
    orientation = 0; // Offset form north in radiants. minus to left, plus to right.   
  }
  
  void draw() {
    apperance.draw();
  }
}

class Apperance {
  
  Unit u;
  ScreenPosition screen;
  PImage soundfield = loadImage("data/soundfield.png");;
  
  Apperance(Unit u) {
    this.u = u;
  }  
  
  void draw() {
    screen = map.getScreenPosition(u.gps);
        
    // Active Whistle
    if(u.solenoid[0]) whistleField(0);
    if(u.solenoid[1]) whistleField(1);
    if(u.solenoid[2]) whistleField(2);
    if(u.solenoid[3]) whistleField(3);
    if(u.solenoid[4]) whistleField(4);

    // Line Pentagon.
    stroke(255); strokeWeight(2);
    for(int i=0; i < 5; i++) {
      line(screen.x + pentatip(i, 30,u.orientation).x, 
           screen.y + pentatip(i, 30,u.orientation).y, 
           screen.x + pentatip(i+1, 30,u.orientation).x, 
           screen.y + pentatip(i+1, 30,u.orientation).y);
    }
    
    // Facing Angle
    stroke(255,0,0); strokeWeight(5);
    point(screen.x + pentatip(0, 30,u.orientation).x, 
          screen.y + pentatip(0, 30,u.orientation).y);
    
    textAlign(CENTER, CENTER);
    
    // ID
    text(u.id + "\n" + u.midiChannel, screen.x, screen.y-2);
    
    // Key Set
    for(int i=0; i < 5; i++) {
      text(u.keySet[i], 
           screen.x + pentaside(i, 40,u.orientation).x, 
           screen.y + pentaside(i, 40,u.orientation).y);
    } 
  }

  void whistleField(int i) {    
    pushMatrix();
      translate(screen.x, screen.y);
      scale(0.5);
      rotate(TWO_PI/5 * (i+0.5) + u.orientation);
      translate(0, -160);
      imageMode(CENTER);
      tint(255,255*u.dimmer); 
        image(soundfield,0,0);
      tint(255,255); 
      imageMode(CORNER);
    popMatrix();
  }
}

class Communication {
}

