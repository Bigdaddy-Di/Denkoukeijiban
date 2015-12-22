package net.tokyo_ct.meister2015.jellyfish.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.tokyo_ct.meister2015.jellyfish.datamanager.SQLiteManager;

import org.junit.Test;

public class HashTest {

	@Test
	public void test() throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in),
				1);
		String s = r.readLine();
		System.out.println("hashed:" + SQLiteManager.hash(s));
	}

}
