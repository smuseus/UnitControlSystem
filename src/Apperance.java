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
	        
	    // Active Whistle (State is reversed, false is on, true is off, due to reversed array on Arduino). 
	    if(!u.solenoidState[0]) whistleField(0);
	    if(!u.solenoidState[1]) whistleField(1);
	    if(!u.solenoidState[2]) whistleField(2);
	    if(!u.solenoidState[3]) whistleField(3);
	    if(!u.solenoidState[4]) whistleField(4);

	    // Active Blower
	    if(u.blowerState) {
	    	p.fill(255, 120); p.noStroke();
	    	p.ellipse(screen.x, screen.y, 45, 45);
	    }
	   
	    
	    // Line Pentagon.
	    p.stroke(255); p.strokeWeight(2);
	    for(int i=0; i < 5; i++) {
	    	p.line(screen.x + p.pentatip(i, 35,u.orientation).x, 
	    		   screen.y + p.pentatip(i, 35,u.orientation).y, 
	               screen.x + p.pentatip(i+1, 35,u.orientation).x, 
	               screen.y + p.pentatip(i+1, 35,u.orientation).y);
	    }
	    
	    // Facing Angle
	    p.stroke(255); p.strokeWeight(5);
	    p.point(screen.x + p.pentatip(0, 30,u.orientation).x, 
	    		screen.y + p.pentatip(0, 30,u.orientation).y);
	    
	    p.textAlign(p.CENTER, p.CENTER);
	    p.fill(255);
	    
	    // ID
	    p.text(u.id, screen.x, screen.y);
	    
	    // ON/OFF TIME
	    if(u.blowerState) {
	    	p.text("on time: " + p.nf(((p.millis() - u.ontime) / 60000)%60, 2) +  "." + p.nf((p.millis() - u.ontime) / 1000.0f, 2, 2 ) , 
	    		    screen.x + 70, screen.y + 70);
	    } else {
	    	p.text("off time: " + p.nf(((p.millis() - u.offtime) / 60000)%60, 2) +  "." + p.nf((p.millis() - u.ontime) / 1000.0f, 2, 2 ) , 
	    		    screen.x + 70, screen.y + 70);
	    }
	    
	    // XbeeAdress64 and midi channel under user.draw();

	    // Key Set
	    for(int i=0; i < 5; i++) {
	      p.text(u.keySet[i], 
	            screen.x + p.pentaside(i, 50, u.orientation).x, 
	            screen.y + p.pentaside(i, 50, u.orientation).y -2);
	    } 
	  }
	  
	  void whistleField(int i) {
		  
		  // Letter Highlight
		  p.fill(255, 80); p.noStroke();
		  p.ellipse(screen.x + p.pentaside(i, 50,u.orientation).x, 
		          	screen.y + p.pentaside(i, 50,u.orientation).y, 
		          	25, 25);  

		  // Directional line
		  p.stroke(255); p.strokeWeight(2); p.noFill();
		  p.line(screen.x + p.pentaside(i, 120,u.orientation).x, 
		         screen.y + p.pentaside(i, 120,u.orientation).y,
		         screen.x + p.pentaside(i, 70,u.orientation).x, 
		         screen.y + p.pentaside(i, 70,u.orientation).y);  
		  
		  p.pushMatrix();
		      p.translate(screen.x, screen.y);
		      p.scale(-1.5f);
		      p.rotate((p.TWO_PI/5.0f * (i+0.5f) + u.orientation));
		      p.translate(0.0f, 70.0f);
		      
		      float yoff = 0;
		      float ywid = 70.0f;
		      float xwid = ywid/1.2f;
		      
		      p.fill(255, 20);
		      p.stroke(255, 120); p.strokeWeight(0.3f);
		      
		      int n = 4;
		      p.ellipse( 0, 
		    		   ((p.pow(1.45f, n) * ywid) / 2.25f) - 50,
	        		   	 xwid*n,
	        		   	 p.pow(1.45f, n) * ywid); 
	      p.popMatrix();
	  }
}
