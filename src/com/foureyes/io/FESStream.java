package com.foureyes.io;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.foureyes.encrypt.HashCode;
import com.foureyes.exception.LogStreamException;
import com.gtranslate.Translator;

/**
 * A stream class that will support us to stored some data
 * which are printed into console. The data will be stored
 * as specific String, which will be hashed using <b>HashCode</b>
 * class <i>(To reduce the weight and increase the security)</i>. 
 * Moreover, it's also can translate the text into
 * every language as long as that language is supported by
 * Google Translate. 
 */
public class FESStream {

	/**
	 * A list stores all the hashed texts
	 */
	private List<String> TEXTS = new ArrayList<>();
	
	/**
	 * Add a text to stored-list encoded in Base64.
	 * 
	 * @param text The raw text retrieve from console
	 */
	public void add(String text) {
		HashCode code = new HashCode(text);
		TEXTS.add(code.getCode());
	}
	
	/**
	 * Get the text at specific index.
	 * 
	 * @param index The index of retrieving text
	 * @return The encoded text
	 */
	public String get(int index) {
		return TEXTS.get(index);
	}
	
	/**
	 * Get the raw text from specific index.
	 * For sure that the String cannot be interrupted, we
	 * set the return type to byte[]. To convert to String
	 * , use : <br><br>
	 * <code>new String(rawValue)</code>
	 * 
	 * @param index The index of retrieving text
	 * @return The raw text in byte[]
	 */
	public byte[] raw(int index) {
		return Base64.getDecoder().decode(get(index));
	}
	
	/**
	 * Translate a normal text from base language to another
	 * by using Google Translate API.
	 * We create this method in order to advance some bridge's
	 * features like Bugger, etc - things that need to translate
	 * content to help users understand.
	 * 
	 * @param text The text that need translating
	 * @param fromLan The base languague
	 * @param toLan The language that you want to translate to
	 * @return
	 */
	public String translate(String text, String fromLan, String toLan) {
		Translator translate = Translator.getInstance();
		return translate.translate(text, fromLan, toLan);
	}
	
	/**
	 * Log the text in to a log.txt file.
	 * 
	 * @return true if creating file successfully, false in the other hand.
	 * @throws LogStreamException
	 */
	public boolean log() throws LogStreamException {
		try {
			
		} catch (Exception e) {
			throw new LogStreamException("Cannot log the FES Console Stream text data");
		}
		return true;
	}
}
