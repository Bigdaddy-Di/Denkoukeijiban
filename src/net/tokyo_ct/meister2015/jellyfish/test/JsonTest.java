package net.tokyo_ct.meister2015.jellyfish.test;

import static org.junit.Assert.*;

import java.io.IOException;

import net.tokyo_ct.meister2015.jellyfish.datamanager.JsonManager;

import org.junit.Test;

public class JsonTest {

	@Test
	public void test() {
		JsonManager jm = new JsonManager("C:/Users/TeamET/setting.json");
		jm.addUser("admin", "XXX", "VVV");
		jm.setLocale(0, 0);
		try {
			jm.write();
			jm.read();

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		System.out.println(jm.verification("admin", "XXX"));
		System.out.println(jm.verification("admin", "YYY"));
		System.out.println(jm.verification("ad", "YYY"));
	}

}
