import processing.core.PImage;
import processing.core.PVector;


public class Map {
	UnitControlSystem p;
	boolean active = false;
	PVector position;
	PImage mapImage;
	String filepath = "../data/helsinki_docks.png";
	
	Map(UnitControlSystem parent) {
		p = parent;
		position = new PVector();
		mapImage = p.loadImage(filepath);
	}
	
	PVector getScreenPosition(PVector location) {
		PVector v = new PVector();
		v.set(location);
		v.sub(position);
		return v;
	}
	
	PVector getLocation(float x, float y) {
		PVector v = new PVector(x, y);
		v.add(position);
		return v;
	}
	
	void activateMap() {
		active = true;
	}
	
	void deactivateMap() {
		active = false;
	}
	
	void loadMap(String f) {
		filepath = f;
		mapImage = p.loadImage(filepath);
		p.println("Map loaded");
	}
	
	void draw() {
		p.background(50);
		p.image(mapImage, -position.x, -position.y, mapImage.width/2, mapImage.height/2);		
	}
}
