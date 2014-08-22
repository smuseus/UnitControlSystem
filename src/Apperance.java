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
	    soundfield = p.loadImage("../data/soundfield-01.png");
	  }  
	  
	  void draw() {
	    screen = p.map.getScreenPosition(u.gps);
	        
	    // Active Whistle
	    if(u.solenoidState[0]) whistleField(0);
	    if(u.solenoidState[1]) whistleField(1);
	    if(u.solenoidState[2]) whistleField(2);
	    if(u.solenoidState[3]) whistleField(3);
	    if(u.solenoidState[4]) whistleField(4);

	    p.stroke(255); p.strokeWeight(1); p.noFill();
	    p.ellipse(screen.x, screen.y, 33, 33);
	    
	    // Line Pentagon.
	    p.stroke(255); p.strokeWeight(1);
	    for(int i=0; i < 5; i++) {
	      p.line(screen.x + p.pentatip(i, 40,u.orientation).x, 
	    		 screen.y + p.pentatip(i, 40,u.orientation).y, 
	             screen.x + p.pentatip(i+1, 40,u.orientation).x, 
	             screen.y + p.pentatip(i+1, 40,u.orientation).y);
	    }
	    
	    // Facing Angle
	    p.stroke(255); p.strokeWeight(5);
	    p.point(screen.x + p.pentatip(0, 30,u.orientation).x, 
	    		screen.y + p.pentatip(0, 30,u.orientation).y);
	    
	    p.textAlign(p.CENTER, p.CENTER);
	    p.fill(255);
	    
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
	      p.scale(-1.0f);
	      p.rotate((p.TWO_PI/5.0f * (i+0.5f) + u.orientation));
	      p.translate(0.0f, 70.0f);
	      
	      float yoff = 0;
	      float ywid = 70.0f;
	      float xwid = ywid/1.2f;
	      
	      p.fill(255, 20);
	      p.stroke(255, 120); p.strokeWeight(1);
	      for(int n=1; n < 5; n++) {
	        p.ellipse( 0, 
	        		   ((p.pow(1.45f, n) * ywid) / 2.25f) - 50,
	        		   xwid*n,
	        		   p.pow(1.45f, n) * ywid);
	      }
	      
	    p.popMatrix();
	  }
}
