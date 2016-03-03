package net.tokyo_ct.meister2015.jellyfish.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import net.tokyo_ct.meister2015.jellyfish.train.Holiday;
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
		ZonedDateTime zdt = ZonedDateTime.now();
		Holiday hol = new Holiday(zdt.getYear());
		String day = "";
		if (hol.isHoliday(Date.valueOf(zdt.toLocalDate()))) {
			day = "HOL";
		} else {
			switch (zdt.getDayOfWeek().getValue()) {
			case 1:
				day = "MON";
				break;
			case 2:
				day = "TUE";
				break;
			case 3:
				day = "WED";
				break;
			case 4:
				day = "THU";
				break;
			case 5:
				day = "FRI";
				break;
			case 6:
				day = "SAT";
				break;
			case 7:
				day = "SUN";
				break;
			}
		}
		System.out.println(day);
		t.getTrainsFromDay(day);
		String[] str = t.getTrainsFromHour(String.valueOf(zdt.getHour()));
		str = Arrays.copyOfRange(str, 1, str.length);
		System.out.println(StringUtils.join(str, " "));
	}

}
