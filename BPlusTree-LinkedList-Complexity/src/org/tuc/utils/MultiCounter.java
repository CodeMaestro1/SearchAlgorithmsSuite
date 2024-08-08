package org.tuc.utils;

/**
 * A class with static member variables and methods that can be used to count multiple stuff. 
 * safe.
 * 
 * 
 */
public class MultiCounter {
	
	  //This class should not be instantiated
	  private MultiCounter() {
		    throw new IllegalStateException("Utility class");
		  }

	/**
	 * variable holding our counters. We support up to 10 counters
	 */
	private static long[] counters = new long[10];


	/**
	 * Resets the internal counter of counterIndex to zero.
	 *
	 * @param counterIndex the counter index
	 */
	public static void resetCounter(int counterIndex) {
		if (counterIndex-1 < counters.length)
			counters[counterIndex-1] = 0;
	}

	/**
	 * Returns the current count for given counterIndex.
	 *
	 * @param counterIndex the counter index
	 * @return the current count for given counterIndex
	 */
	public static long getCount(int counterIndex) {
		if (counterIndex-1 < counters.length)
			return counters[counterIndex-1];
		return -1;
	}

	/**
	 * Increases the current count of counterIndex by 1. Returns always true so that it can be used
	 * in boolean statements
	 *
	 * @param counterIndex the counter index
	 * @return always true
	 */
	public static boolean increaseCounter(int counterIndex) {
		if (counterIndex-1 < counters.length)
			counters[counterIndex-1]++;
		return true;
	}
}


