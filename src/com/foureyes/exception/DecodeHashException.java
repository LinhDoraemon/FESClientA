package com.foureyes.exception;

/**
 * An exception that will throw when getting errors while
 * decoding hash-code or RSA code.
 */
public class DecodeHashException extends Exception {

	private static final long serialVersionUID = 2973131839841913181L;

	public DecodeHashException(String trace) {
		super(trace);
	}
}
