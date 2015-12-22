package net.tokyo_ct.meister2015.jellyfish.datamanager;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

public class AccessTokenManager {
	private BidiMap accessTokens = new DualHashBidiMap();

	public void putAccessToken(String id, String accessToken) {
		accessTokens.put(id, accessToken);
	}

	public String getAccessToken(String id) {
		return (String) accessTokens.get(id);
	}

	public String getId(String accessToken) {
		return (String) accessTokens.getKey(accessToken);
	}
	
	public void print(){
	}

}
