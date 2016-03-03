package net.tokyo_ct.meister2015.jellyfish.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import net.tokyo_ct.meister2015.jellyfish.train.Train;

public class TrainTest {

	@Test
	public void test() {
		Train t = null;
		try {
			t = new Train(FileUtils.readFileToString(new File("testzama.txt")));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		t.getTrainsFromDay("MON");
		String[] str = t.getTrainsFromHour("6");
		str = Arrays.copyOfRange(str, 1, str.length);
		System.out.println(StringUtils.join(str, " "));
	}

}
