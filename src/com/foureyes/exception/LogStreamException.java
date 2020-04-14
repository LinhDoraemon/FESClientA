package com.foureyes.exception;

/**
 * An exception that will throw  
 */
public class LogStreamException extends Exception {

	private static final long serialVersionUID = 2064535535638883032L;
	
	public LogStreamException(String trace) {
		super(trace);
	}
		
}
