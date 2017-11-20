package config;

import java.util.HashMap;
import java.util.Map;

public class Config {

	private static Map<String,String> config;
	
	public static Map<String,String> get(){
		if (config == null) {
			config = new HashMap<String,String>();
		}
		return config;
	}
}
