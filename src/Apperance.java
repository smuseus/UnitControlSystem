import processing.core.PApplet;
import processing.core.PImage;
import de.fhpotsdam.unfolding.utils.ScreenPosition;

public class Apperance {
	  UnitControlSystem p;
	
	  Unit u;
	  ScreenPosition screen;
	  PImage soundfield;
	  
	  Apperance(Unit u, UnitControlSystem parent) {
		p = parent;
	    this.u = u;
	    soundfield = p.loadImage("../data/soundfield.png");
	  }  
	  
	  void draw() {
	    screen = p.map.getScreenPosition(u.gps);
	        
	    // Active Whistle
	    if(u.solenoidState[0]) whistleField(0);
	    if(u.solenoidState[1]) whistleField(1);
	    if(u.solenoidState[2]) whistleField(2);
	    if(u.solenoidState[3]) whistleField(3);
	    if(u.solenoidState[4]) whistleField(4);

	    // Line Pentagon.
	    p.stroke(255); p.strokeWeight(2);
	    for(int i=0; i < 5; i++) {
	      p.line(screen.x + p.pentatip(i, 30,u.orientation).x, 
	           screen.y + p.pentatip(i, 30,u.orientation).y, 
	           screen.x + p.pentatip(i+1, 30,u.orientation).x, 
	           screen.y + p.pentatip(i+1, 30,u.orientation).y);
	    }
	    
	    // Facing Angle
	    p.stroke(255,0,0); p.strokeWeight(5);
	    p.point(screen.x + p.pentatip(0, 30,u.orientation).x, 
	          screen.y + p.pentatip(0, 30,u.orientation).y);
	    
	    p.textAlign(p.CENTER, p.CENTER);
	    
	    // ID
	    p.text(u.id, screen.x, screen.y);
	    
	    // XbeeAdress64 and midi channel under user.draw();

	    // Key Set
	    for(int i=0; i < 5; i++) {
	      p.text(u.keySet[i], 
	            screen.x + p.pentaside(i, 40,u.orientation).x, 
	            screen.y + p.pentaside(i, 40,u.orientation).y);
	    } 
	  }

	  void whistleField(int i) {    
	    p.pushMatrix();
	      p.translate(screen.x, screen.y);
	      p.scale((float) 0.5);
	      p.rotate((float) (p.TWO_PI/5 * (i+0.5) + u.orientation));
	      p.translate(0, -160);
	      p.imageMode(p.CENTER);
	      p.tint(255,255*u.dimmerState); 
	        p.image(soundfield,0,0);
	      p.tint(255,255); 
	      p.imageMode(p.CORNER);
	    p.popMatrix();
	  }
}
