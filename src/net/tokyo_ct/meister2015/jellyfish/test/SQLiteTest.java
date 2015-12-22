package net.tokyo_ct.meister2015.jellyfish.test;

import static org.junit.Assert.*;
import net.tokyo_ct.meister2015.jellyfish.datamanager.SQLiteManager;

import org.junit.Test;

public class SQLiteTest {

	@Test
	public void veriTest() {
		SQLiteManager man = new SQLiteManager(
				"jdbc:sqlite:C:/Users/TeamET/setting.sqlite3");

		System.out.println(man.passwordVerification("admin", "pass"));
		System.out.println(man.passwordVerification("admin", "passpass"));
		System.out.println(man.passwordVerification("amin", "pass"));

	}

	@Test
	public void permTest() {
		SQLiteManager man = new SQLiteManager(
				"jdbc:sqlite:C:/Users/TeamET/setting.sqlite3");

		System.out.println(man.permission("admin"));
		System.out.println(man.permission("sakura"));
		System.out.println(man.permission("shaoran"));
		System.out.println(man.permission("meilling"));
		System.out.println(man.permission("tomoyo"));
		System.out.println(man.permission("yukito"));

	}

}
