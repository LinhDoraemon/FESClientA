package com.foureyes.exception;

/**
 * An exception that will throw when getting errors
 * while convert bytes to keys including public key 
 * and private key.
 */
public class KeyConvertException extends Exception {

	private static final long serialVersionUID = -6705570184898208L;

	public KeyConvertException(String trace) {
		super(trace);
	}
}
