package org.tuc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * It reads input from the standard input hiding from the user the usage 
 * of java.io package classes
 */
public class StandardInputRead {
	
	/**  The error that is return when reading positive integers from stdin. */
	public static final int POS_ERROR = 1;
	
	/**  The error that is return when reading negative integers from stdin. */
	public static final int NEG_ERROR = -1;
	
	/**  The basic reader. */
	BufferedReader in;
	
	/**
	 * Class constructor.
	 */
	public StandardInputRead() {
		super();
		in = new BufferedReader(new InputStreamReader(System.in));		
	}

	/**
	 * It reads a string from standard inputand returns it as value. 
	 * In case of an error it returns null
	 *
	 * @param message The message that is apperad to the user asking for input
	 * @return the string
	 */
	public String readString(String message) {
		
		System.out.print(message);
		try {
			return in.readLine();
		}
		catch (IOException e) {
			return null;
		}			
	}
	
	/**
	 * It reads an positive integer, zero included, from standard input
	 * and returns it as value. In case of an error it returns -1
	 *
	 * @param message The message that is apperad to the user asking for input
	 * @return the int
	 */
	public int readPositiveInt(String message) {
		
		String str;
		int num;
		
		System.out.print(message);			
		try {
			str = in.readLine();
			num = Integer.parseInt(str);
			if (num < 0 ){
				return POS_ERROR;
			}
			else {
				return num;
			}			
		}
		catch (IOException | NumberFormatException e) {			
			return POS_ERROR;
		}
		
	}
	
	/**
	 * It reads an negative integer from standard input and returns it as value. 
	 * In case of an error it returns 1
	 *
	 * @param message The message that is apperad to the user asking for input
	 * @return the int
	 */
	public int readNegativeInt(String message) {
		
		String str;
		int num;
		
		System.out.print(message);			
		try {
			str = in.readLine();
			num = Integer.parseInt(str);
			if (num >= 0 ){
				return NEG_ERROR;
			}
			else {
				return num;
			}			
		}
		catch (IOException | NumberFormatException e) {			
			return NEG_ERROR;
		}
		
	}		
}