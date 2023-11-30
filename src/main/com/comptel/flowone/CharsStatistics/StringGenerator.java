package com.comptel.flowone.CharsStatistics;

import java.util.ArrayList;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


/// String Generator Class responsible to generate the Random Strings using the incoming values in constructor 
public class StringGenerator implements Runnable{
    private ArrayList<ArrayList<String>> sharedStringList;
    private int amountOfString;
    private int amountOfList;

	public StringGenerator(int noOfStrings , ArrayList<ArrayList<String>> mainStringList,int amountOfList)
	{
		this.amountOfString=noOfStrings;
		this.sharedStringList=mainStringList;
		this.amountOfList=amountOfList;
	}
	/// the main Thread processing keep working over the incoming ArrayList to build the requried Strings
	@Override
	public void run() {
		for (int i = 0; i < this.amountOfList; i++) {
			synchronized (sharedStringList) {
			
				sharedStringList.add(generateList(amountOfString));
			}
				
		}
		
	}
	/// generate  List of Random Strings 
	private ArrayList<String> generateList(int number)
	{
		ArrayList<String> randomStringList=new ArrayList<String>();
		for (int i = 0; i < number; i++) {
			randomStringList.add(generateRandomString());
		}
		return randomStringList;
	}
	/// Generate Random String between 1000 to 10000 length and with Latin char a to z (small)
	private  String generateRandomString()
	{
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'   
	    int targetStringLength   = ThreadLocalRandom.current().nextInt(1000,10000);
	   
	    Random random = new Random();
	    char[] buffer = new char[targetStringLength];
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	       buffer[i]=(char)randomLimitedInt;
	     
	    }
	   
	    return new String(buffer);
	}

}
