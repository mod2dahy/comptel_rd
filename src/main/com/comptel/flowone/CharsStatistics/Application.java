package com.comptel.flowone.CharsStatistics;

public class Application {

	static final String SIZE = "size";

	public static void main(String[] args) {
		/// starting point of the Application read the Amount of String from user options

		String size = System.getProperty(SIZE);
		if (size == null || size.isEmpty()) {
			System.err.println("Usage: Java -Dsize=<AmountOfStrings> example java -Dsize=345 Char_Statistic.jar");
			System.exit(1);
		}

		long lSize;
		try {
			lSize = Long.parseLong(size);
			CharStatisticProcessor charStatisticProcessor = new CharStatisticProcessor();
			charStatisticProcessor.process(lSize);
		} catch (NumberFormatException ex) {
			System.err.println("Usage: the AmountOfStrings should be number ");
			System.exit(1);
		}

	}

}
