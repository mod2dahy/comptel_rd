package com.comptel.flowone.CharsStatistics;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		long start = System.currentTimeMillis();

		



			
			CharStatisticProcessor proc1=new CharStatisticProcessor();
	//	proc1.process(Long.MAX_VALUE);
			proc1.process(9876543210l);
			long end = System.currentTimeMillis();

			NumberFormat formatter = new DecimalFormat("#0.00000");
			System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");


	}

}
