package com.comptel.flowone.CharsStatistics;

import java.util.ArrayList;
import java.util.HashMap;

public class CharStatisticCalculator implements Runnable {
	
	private HashMap<Character, Long> sharedCharsCount;
	private ArrayList<ArrayList<String>> sharedStringList;
	
	private int startIndex,endIndex;

	public CharStatisticCalculator(HashMap<Character, Long> sharedCharsCount,
			ArrayList<ArrayList<String>> sharedStringList,int startIndex,int endIndex) {
		this.sharedCharsCount = sharedCharsCount;
		this.sharedStringList = sharedStringList;
		this.startIndex=startIndex;
		this.endIndex=endIndex;
		
	}

	/// process the incoming string to fetch statistics thread entry point will
	/// go through the shared String list
	@Override
	public void run() {
		
		synchronized (sharedStringList) {
			for (int i = startIndex; i < endIndex; i++) {
				
				processStringStatistic(sharedStringList.get(i));

			}
		}
	}

	/// process the incoming string to fetch statistics
	synchronized void processStringStatistic(ArrayList<String> inputStrs) {
		
		for (String inputStr : inputStrs) {

			for (char c : inputStr.toCharArray()) {
				sharedCharsCount.merge(c, // key = char
						(long) 1, // value to merge
						Long::sum); // counting
			}

		}
	}
}
