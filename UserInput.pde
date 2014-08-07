class UserInput {
  
  Unit selectedUnit;
  EventDispatcher mapEvent;
  
  String mode = "MAP";
  
  UserInput(PApplet p) {
    mapInit(p);

  }
  
  void mousePressed() {
    hitTree();
  }
  
  void mouseDragged() {
    if(mode.equals("UORIENT")) selectedUnit.orientation += (mouseX - pmouseX) * 0.01;
    if(mode.equals("UMOVE"))   selectedUnit.gps = map.getLocation(mouseX, mouseY);
  }
  
  void mouseReleased() {
    if(mode.equals("MAP") || mode.equals("UMOVE") || mode.equals("UORIENT")) mode = "USER";
  }
  
  void keyPressed() {
    if(key == CODED) {
        if(keyCode == ALT) mode = "ALT";
        if(keyCode == ENTER) mode = "USER";
    } else {
      if(mode.equals("ALT")) {
        if(key == 'a') {
          handler.addUnit();
        } else if(key == 'd') {
          handler.removeUnit(selectedUnit);
          selectedUnit = null;
        } else if(key == 'i') {
          if(selectedUnit != null) { 
            selectedUnit.id = "";
            mode = "UID";
          }
        } else if(key == 'm') {
          mode = "MIDI";
        }else {
          mode = "USER";
        }
      } else {
        if(mode.equals("USER")) keyAction();
        if(mode.equals("UID")) {
          if(key == CODED) {  // TODO: Something quircky with the event handling, to confirm, press ALT then ENTER
            if(keyCode == ENTER) mode = "USER";
          } else {
            selectedUnit.id += key;
          }
        }
        if(mode.equals("USETKEY1")) {
          selectedUnit.keySet[0] = key;
          mode = "USER";
        }
        if(mode.equals("USETKEY2")) {
          selectedUnit.keySet[1] = key;
          mode = "USER";
        }
        if(mode.equals("USETKEY3")) {
          selectedUnit.keySet[2] = key;
          mode = "USER";
        }
        if(mode.equals("USETKEY4")) {
          selectedUnit.keySet[3] = key;
          mode = "USER";
        }
        if(mode.equals("USETKEY5")) {
          selectedUnit.keySet[4] = key;
          mode = "USER";
        }
      }
    }
  }

  void hitTree() {
    
    // Hit check
    if(false) {
      // Do something with component.
    } else {
      selectedUnit = checkUnits();
      if(selectedUnit != null) {
        checkUnitComponent(selectedUnit);
      } else {
        mode = "MAP";
      }
    }      
    
    // Post Processes
    if(mode.equals("MAP")) {
      mapActive(true);
    } else {
      mapActive(false);
    } 
  } 
  
  Unit checkUnits() {
    ScreenPosition screen; float hitRadius = 50;
    for(Unit u : handler.units) {
      screen = map.getScreenPosition(u.gps);
      if(dist(mouseX, mouseY, screen.x, screen.y) < hitRadius) return u;
    }
    return null;
  }
  
  void checkUnitComponent(Unit u) {
    ScreenPosition screen = map.getScreenPosition(u.gps);
    if(dist(mouseX, mouseY, screen.x + pentatip(0, 30, u.orientation).x, 
                                   screen.y + pentatip(0, 30, u.orientation).y) < 20) {
      mode = "UORIENT";
    } else {
             if(dist(mouseX, mouseY, screen.x + pentaside(0, 40, u.orientation).x, 
                                     screen.y + pentaside(0, 40, u.orientation).y) < 15) {
        mode = "USETKEY1";
      } else if(dist(mouseX, mouseY, screen.x + pentaside(1, 40, u.orientation).x, 
                                     screen.y + pentaside(1, 40, u.orientation).y) < 15) {
        mode = "USETKEY2";
      } else if(dist(mouseX, mouseY, screen.x + pentaside(2, 40, u.orientation).x, 
                                     screen.y + pentaside(2, 40, u.orientation).y) < 15) {
        mode = "USETKEY3";
      } else if(dist(mouseX, mouseY, screen.x + pentaside(3, 40, u.orientation).x, 
                                     screen.y + pentaside(3, 40, u.orientation).y) < 15) {
        mode = "USETKEY4";
      } else if(dist(mouseX, mouseY, screen.x + pentaside(4, 40, u.orientation).x, 
                                     screen.y + pentaside(4, 40, u.orientation).y) < 15) {
        mode = "USETKEY5";
      } else {
        mode = "UMOVE";
      }
    } 
  }
  
  void keyAction() {
    for(Unit u : handler.units) {
      for(int i=0; i < u.keySet.length; i++) {
        if(key==u.keySet[i]) u.solenoid[i] = !u.solenoid[i];
      }
    }
  }
  
  void mapInit(PApplet p) {
    mapEvent = new EventDispatcher();
    MouseHandler mouseHandler = new MouseHandler(p, map);
    mapEvent.addBroadcaster(mouseHandler);
    mapActive(true);
  }
  
  void mapActive(boolean b) {
    if(b) {
      mapEvent.register(map, PanMapEvent.TYPE_PAN, map.getId());
      mapEvent.register(map, ZoomMapEvent.TYPE_ZOOM, map.getId());
    } else {
      mapEvent.unregister(map, PanMapEvent.TYPE_PAN, map.getId());
      mapEvent.unregister(map, ZoomMapEvent.TYPE_ZOOM, map.getId());
    }
  }
  
  void draw() {
    textAlign(LEFT,TOP);
    if(mode.equals("ALT")) text("a (add), d (delete), i (set id), m (set midi)", 10, 10);
    text(mode, 10, 50);
    if(selectedUnit != null) {
      ellipseMode(CENTER);
      noFill(); strokeWeight(1); stroke(255);
      ellipse(selectedUnit.apperance.screen.x, 
              selectedUnit.apperance.screen.y,
              100, 100);
    }
  }
}
