package com.comptel.flowone.CharsStatistics;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class StringGenratorTest {
	private StringGenerator stringGenerator;
	private ArrayList<ArrayList<String>> data;


	@Before
	public void setup() {
		data = new ArrayList<>();

		stringGenerator=new StringGenerator(5,data, 5);
		Thread t = new Thread(stringGenerator);

		t.start();
		while (t.isAlive()) {

		}
	}

	@Test
	public void dataSizetest() {
		

		/// sharedCharsCount.forEach((key, value) -> System.out.println(key + "
		/// " + value));
		for (int i = 0; i < 5; i++) {
		//	System.err.println(data.get(i).size()); test the size of Strings
			Assert.assertEquals(data.get(i).size(), 5);
		}

	}
	
	@Test
	public void dataLengthTest() {
		

		/// sharedCharsCount.forEach((key, value) -> System.out.println(key + "
		/// " + value));
		for (int i = 0; i < 5; i++) {
		//	System.err.println(data.get(i).size()); test the size of Strings
			for (int j = 0; j < 5; j++) {
				Assert.assertTrue(data.get(i).get(i).length()<10000);
				Assert.assertTrue(data.get(i).get(i).length()>1000);
			}
		}
	}
		
		@Test
		public void dataContentTest() {
			

			/// sharedCharsCount.forEach((key, value) -> System.out.println(key + "
			/// " + value));
			for (int i = 0; i < 5; i++) {
			//	System.err.println(data.get(i).size()); test the size of Strings
				for (int j = 0; j < 5; j++) {
					for(char c: data.get(i).get(j).toCharArray())
					{
					
						Assert.assertTrue((c >= 'a' && c <= 'z'));
					}
				}
			}

	}
}
