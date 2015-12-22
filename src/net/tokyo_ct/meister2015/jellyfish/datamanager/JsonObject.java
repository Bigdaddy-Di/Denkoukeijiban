package net.tokyo_ct.meister2015.jellyfish.datamanager;

import java.util.HashMap;
import java.util.Map;

import net.tokyo_ct.meister2015.jellyfish.datamanager.User;

public class JsonObject {

	public JsonObject() {
		weatherLocale = new HashMap<String, Integer>();
		users = new HashMap<String, User>();
	}

	public Map<String, Integer> weatherLocale;
	public Map<String, User> users;

}
