package com.comptel.flowone.CharsStatistics;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;




public class CharCalcTest {

	private CharStatisticCalculator calc;
	private ArrayList<ArrayList<String>> data;
	private HashMap<Character, Long> sharedCharsCount;

	@Before
	public void setup() {
		data = new ArrayList<>();

		ArrayList<String> tmp = new ArrayList<>();
		tmp.add("aaaaaaaa");
		tmp.add("bbbb");
		tmp.add("ccc");
		tmp.add("ddddd");
		data.add(tmp);
		sharedCharsCount = new HashMap<>();
		calc = new CharStatisticCalculator(sharedCharsCount, data,0,data.size());
	}

	@Test
	public void testSample() {
		Thread t = new Thread(calc);

		t.start();
		while (t.isAlive()) {

		}

		 sharedCharsCount.forEach((key, value) -> System.out.println(key + " " + value));
		Assert.assertEquals(sharedCharsCount.get('a').intValue(), 8);
		Assert.assertEquals(sharedCharsCount.get('b').intValue(), 4);
		Assert.assertEquals(sharedCharsCount.get('c').intValue(), 3);
		Assert.assertEquals(sharedCharsCount.get('d').intValue(), 5);

	}
}
