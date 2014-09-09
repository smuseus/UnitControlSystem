
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PVector;
import processing.data.*;

public class FileHandler {
	UnitControlSystem p;
	String filepath = "../data/scene.json";
	
	FileHandler(UnitControlSystem parent) {
		p = parent;
	}
	
	public void load() {
		p.handler.clear();
		JSONObject json = p.loadJSONObject(filepath);
		
		// Load Units
		JSONArray units = json.getJSONArray("units");
		for(int i=0; i<units.size(); i++) {
			p.handler.addUnit( JSONtoUnit(units.getJSONObject(i)) );
		}
		
		// Load map image
		p.map.loadMap(json.getString("mapImage"));
	}
	
	public void save() {
		JSONObject json = new JSONObject();
		
		// Save Units
		JSONArray units = new JSONArray();
		for(Unit u : p.handler.units) {
			units.append(unitToJSON(u));	
		}
		json.setJSONArray("units", units);
		
		// Save file path of map image
		json.setString("mapImage", p.map.filepath);
		
		
		p.saveJSONObject(json, filepath);
		p.println("Scene saved to: " + filepath);
	}

	private Unit JSONtoUnit(JSONObject json) {
		
		// Load location
		PVector loc = new PVector(json.getFloat("x"), json.getFloat("y"));
		
		// Initialize Unit
		Unit u = new Unit(loc, p);
		
		// Load id
		u.id = json.getString("id");
		
		// Load address
		String[] addrsString = p.split(json.getString("address"), ", ");
		int[] addrsInt = new int[addrsString.length];
		for(int i=0; i<addrsString.length; i++) {
			addrsInt[i] = hexToInt(addrsString[i]);
		}
		u.address.setAddress(addrsInt);
		
		// Load orientation
		u.orientation = json.getFloat("orientation");
		
		// Load key sey
		String[] keySetString = json.getJSONArray("keySet").getStringArray();
		for(int i=0; i<keySetString.length; i++) u.keySet[i] =  keySetString[i].charAt(0);
		
		// Load midi
		u.midiID = json.getInt("midiID"); 
		
		return u;
	}
	
	private JSONObject unitToJSON(Unit u) {
		JSONObject json = new JSONObject();
		
		// Store id
		json.setString("id", u.id);
		
		// Store address
		int[] addrsInt = u.address.getAddress();
		String[] addrsString = new String[addrsInt.length];
		for(int i=0; i< addrsInt.length; i++) {
			addrsString[i] = intToHex(addrsInt[i]);
		}
		json.setString("address", p.join(addrsString, ", "));
		
		// Store location
		json.setFloat("x", u.location.x);
		json.setFloat("y", u.location.y);
		
		// Store orientation
		json.setFloat("orientation", u.orientation);
		
		// Store key set
		JSONArray keySet = new JSONArray();
		for(int i=0; i<6; i++) {
			keySet.setString(i, "" + u.keySet[i]);
		}
		json.setJSONArray("keySet", keySet);
		
		// Store midi
		json.setInt("midiID", u.midiID);
		
		return json;
	}
	
	private static String intToHex(int n) {
		return Integer.toHexString(n);
	}
	
	private static int hexToInt(String hex) {
		return Integer.parseInt(hex, 16);
	}
}
