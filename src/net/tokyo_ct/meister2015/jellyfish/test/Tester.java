package net.tokyo_ct.meister2015.jellyfish.test;

import java.io.File;
import org.junit.Test;

public class Tester {
	@Test
	public void test(){
		File top = new File("serverfiles/html/a.html");
		System.out.print(top.length());

	}
}
