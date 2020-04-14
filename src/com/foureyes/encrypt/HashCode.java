package com.foureyes.encrypt;

import java.util.Base64;

import com.foureyes.exception.DecodeHashException;

/**
 * An very important class that using in most of
 * FES's client stuffs. We use hash-code as a 
 * way of transmitting data and check for important
 * data. Each our collaborate servers has their own
 * hash-code given by FES Team. If the given hash-code
 * changed, we won't make sure the bridge and the control
 * will work matchly. 
 */
public class HashCode {

	public static final long serialID = 425874611214358762L;
	
	/**
	 * The official hash-code after being encoded
	 */
	private String code;
	
	/**
	 * This is the step using to encode from raw String
	 * using Base64 encoder. Because this crypter will
	 * still be supported and developed in the near future,
	 * we decided to use it.
	 * 
	 * @param raw : The raw String code
	 */
	public HashCode(String raw) {
		setCode(Base64.getEncoder().encodeToString(raw.getBytes()));
	}
	
	/**
	 * Get the encoded hash-code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Set the encoded hash-code
	 * 
	 * @param code : The encoded hash-code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Decode the hashed code above by using
	 * Base64 decoder.
	 * 
	 * @param hash : The hash-code
	 * @throws DecodeHashException : Error while decoding
	 */
	public byte[] decode(String hash) throws DecodeHashException {
		try {
			byte[] decodedValue = Base64.getDecoder().decode(hash);
			return decodedValue;
		} catch (Exception e) {
			throw new DecodeHashException("Cannot decode the hash-code : " + hash);
		}
	}
}
