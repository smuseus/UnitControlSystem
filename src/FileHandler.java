
import de.fhpotsdam.unfolding.geo.Location;
import processing.data.*;

public class FileHandler {
	UnitControlSystem p;
	String filepath = "../data/scene.json";
	
	FileHandler(UnitControlSystem parent) {
		p = parent;
	}
	
	public void load() {
		p.handler.clear();
		JSONArray json = p.loadJSONArray(filepath);
		for(int i=0; i<json.size(); i++) {
			p.handler.addUnit( JSONtoUnit(json.getJSONObject(i)) );
		}
	}
	
	public void save() {
		JSONArray json = new JSONArray();
		for(Unit u : p.handler.units) {
			json.append(unitToJSON(u));	
		}
		
		p.saveJSONArray(json, filepath);
		p.println("Scene saved to: " + filepath);
	}

	private Unit JSONtoUnit(JSONObject json) {
		
		// Load gps
		Location gps = new Location(json.getFloat("lat"), json.getFloat("lon"));
		
		// Initialize Unit
		Unit u = new Unit(gps, p);
		
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
		u.midiChannel = json.getInt("midiChannel");
		
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
		
		// Store gps location
		json.setFloat("lat", u.gps.getLat());
		json.setFloat("lon", u.gps.getLon());
		
		// Store orientation
		json.setFloat("orientation", u.orientation);
		
		// Store key set
		JSONArray keySet = new JSONArray();
		for(int i=0; i<5; i++) {
			keySet.setString(i, "" + u.keySet[i]);
		}
		json.setJSONArray("keySet", keySet);
		
		// Store midi
		json.setInt("midiChannel", u.midiChannel);
		
		return json;
	}
	
	private static String intToHex(int n) {
		return Integer.toHexString(n);
	}
	
	private static int hexToInt(String hex) {
		return Integer.parseInt(hex, 16);
	}
}
