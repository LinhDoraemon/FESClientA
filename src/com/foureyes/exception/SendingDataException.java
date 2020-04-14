package com.foureyes.exception;

/**
 * A class that will throw when getting errors while trying to send
 * data from collaborated server to FES's control cloud.
 */
public class SendingDataException extends Exception {

	private static final long serialVersionUID = 6123442611793733867L;

	public SendingDataException(String trace) {
		super(trace);
	}
	
}
