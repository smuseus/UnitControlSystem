import processing.core.*;
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

	public void setup() {
		size(600, 600);
		map = setupMap();		
		handler = new UnitHandler(this);
		user = new UserInput(this);
		//comm = new Comm(this); // Wait with xbee, still needs to be debugged.
		file = new FileHandler(this);
	}

	public void draw() {
		//handler.sync(); // Wait with xbee, still needs to be debugged. 
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

	
	UnfoldingMap setupMap() {
		UnfoldingMap m = new UnfoldingMap(this, new Microsoft.AerialProvider());
		m.zoomAndPanTo(new Location(52.5f, 13.4f), 100);
		return m;
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
		PApplet.main(new String[] { UnitControlSystem.class.getName() });
	}
}
