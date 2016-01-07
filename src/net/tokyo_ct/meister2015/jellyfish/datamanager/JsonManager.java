package net.tokyo_ct.meister2015.jellyfish.datamanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonManager {

	JsonObject jo;
	File file;

	public JsonManager(String url) {
		file = new File(url);
		jo = new JsonObject();

	}

	public void setLocale(int pref, int city) {
		jo.weatherLocale.put("pref", pref);
		jo.weatherLocale.put("city", city);
		try {
			this.write();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void addUser(String id, String pass, String permission) {
		if (!jo.users.containsKey(id)) {
			User user = new User();
			user.hashedPass = pass;
			user.permission = permission;
			jo.users.put(id, user);
		}
	}

	public boolean verification(String id, String pass) {
		if (jo.users.get(id) != null) {
			return jo.users.get(id).hashedPass.equals(pass);
		} else {
			return false;
		}
	}

	public String writeToJson() {
		String json = "";
		try {
			json = (new ObjectMapper()).writeValueAsString(jo);
		} catch (JsonProcessingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return json;
	}

	public void write() throws IOException {
		FileUtils.writeStringToFile(file, this.writeToJson());
	}

	public void read() throws IOException {
		System.out.println(FileUtils.readFileToString(file));
		if (FileUtils.readFileToString(file).equals("")) {
			System.out.println("aabb");
			this.write();
		}
		jo = (new ObjectMapper()).readValue(FileUtils.readFileToString(file),
				JsonObject.class);
	}

}
