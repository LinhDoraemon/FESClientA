package com.foureyes.exception;

/**
 * An exception that will throw when getting errors while
 * sending/receiving data from FES's control because of the
 * close of the control.
 */
public class ControlCloseException extends Exception {

	private static final long serialVersionUID = 4258922457420849934L;

	public ControlCloseException(String trace) {
		super(trace);
	}
	
}
