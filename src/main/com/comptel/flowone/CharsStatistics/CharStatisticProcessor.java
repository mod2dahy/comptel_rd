package com.comptel.flowone.CharsStatistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

public class CharStatisticProcessor {

	public static final Logger logger = Logger.getLogger(CharStatisticProcessor.class.getName());

	static {
		org.apache.log4j.PropertyConfigurator.configure("log4j.properties");

	}
	/// shared Characters repositories
	private HashMap<Character, Long> sharedCharsCount;

	ArrayList<ArrayList<String>> mainStringList = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> mainStringList1 = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> mainStringList2 = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> mainStringListOthers = new ArrayList<ArrayList<String>>();
	ExecutorService executor;
	ArrayList<StringGenerator> generatorList;// String Generators
	ArrayList<CharStatisticCalculator> charCalcList;/// Characters Calculators
	/// holding the threads status to control the program flow
	ArrayList<Future<?>> genratorsThreadsStatus;
	ArrayList<Future<?>> calcThreadsStatus;

	public CharStatisticProcessor() {

		generatorList = new ArrayList<>();
		genratorsThreadsStatus = new ArrayList<>();
		calcThreadsStatus = new ArrayList<>();
		charCalcList = new ArrayList<>();
		sharedCharsCount = new HashMap<>();
	}

	/// Processor Entry point to start build corresponding String Generators and
	/// Statistics Calculators
	public void process(long amountOfString) {
		buildGeneratorslist(amountOfString);
		executor = Executors.newFixedThreadPool(generatorList.size() + 8);
		for (int i = 0; i < generatorList.size(); i++) {
			genratorsThreadsStatus.add(executor.submit(generatorList.get(i)));
		}
		while (isAnythreadStillWorking(genratorsThreadsStatus))
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		printSize();/// for logging
		buildCalculaters();
		logger.debug("Number of threads Calc " + charCalcList.size());
		for (int i = 0; i < charCalcList.size(); i++) {

			calcThreadsStatus.add(executor.submit(charCalcList.get(i)));
		}
		while (isAnythreadStillWorking(calcThreadsStatus))
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		printCharStaticsitc();
		executor.shutdown();
	}

	/// based on the incoming amount of required String will create list of list
	/// as MAX size limitation in Array List
	/// will keep consume (MAX_VALUE*MAX_VALUE) from the incoming Long
	private void buildGeneratorslist(long amountOfString) {

		long currnt = amountOfString;

		long bigInt = Integer.MAX_VALUE;
		bigInt *= bigInt;
		logger.debug(" bigInt " + bigInt);
		if (currnt > bigInt) {
			currnt -= bigInt;
			generatorList.add(new StringGenerator(Integer.MAX_VALUE, mainStringList, Integer.MAX_VALUE));
		}
		logger.debug(" current " + currnt);
		if (currnt > bigInt) {
			currnt -= bigInt;
			generatorList.add(new StringGenerator(Integer.MAX_VALUE, mainStringList1, Integer.MAX_VALUE));
		}

		logger.debug(" current " + currnt);

		int others = (int) Math.round(Math.sqrt(currnt));
		generatorList.add(new StringGenerator(others, mainStringList2, others));

		long last = currnt - (long) others * others;

		if (last > 0)
			generatorList.add(new StringGenerator((int) last, mainStringListOthers, 1));
	}

	private void buildCalculaters() {
		if (mainStringList.size() > 0)
			buildCalcPair(mainStringList);

		if (mainStringList1.size() > 0)
			buildCalcPair(mainStringList1);

		if (mainStringList2.size() > 0)
			buildCalcPair(mainStringList2);

		if (mainStringListOthers.size() > 0)
			charCalcList.add(new CharStatisticCalculator(sharedCharsCount, mainStringListOthers, 0,
					mainStringListOthers.size()));

	}

	/// divide the incoming list to two segments and create calculator for each
	/// one
	private void buildCalcPair(ArrayList<ArrayList<String>> stringList) {

		int middle = stringList.size() / 2;
		logger.debug("buildCalcPair " + middle);
		charCalcList.add(new CharStatisticCalculator(sharedCharsCount, stringList, 0, middle));
		charCalcList.add(new CharStatisticCalculator(sharedCharsCount, stringList, middle, stringList.size()));
	}

	/// check status of all input threads in the list if any one
	private boolean isAnythreadStillWorking(ArrayList<Future<?>> threadsStatus) {
		for (int i = 0; i < threadsStatus.size(); i++) {
			if (!threadsStatus.get(i).isDone())
				return true;
		}
		return false;
	}

	// print the final statistics
	private void printCharStaticsitc() {
		sharedCharsCount.forEach((key, value) -> System.err.println(key + "         " + value));

	}

	/// print size for logging propose
	private void printSize() {

		logger.debug("mainStringList " + mainStringList.size());
		logger.debug("mainStringList1 " + mainStringList1.size());
		logger.debug("mainStringList2 " + mainStringList2.size());
		logger.debug("mainStringListOthers  " + mainStringListOthers.size());
	}

}
