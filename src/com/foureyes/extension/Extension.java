package com.foureyes.extension;

import com.foureyes.encrypt.HashCode;

/**
 * An interface that will be implemented by all
 * FES's extensions.
 * Extensions are Minecraft plugins hooking into
 * FESClientBridge, and have some possibiities to
 * support the collaborated servers at many fields
 * like reduce lag, etc. And, those extensions are
 * exclusive and there's not any plugin do the same
 * things.<br>
 * And, all extensions will be sold through FESClient.
 * Maybe some are free, but most of them are premium.
 * The cost depend on the hard of making extension,
 * the benefits it bring and sometime is VAT fee, etc.<br>
 * To use extensions, they must be validated first.
 */
public interface Extension {

	/**
	 * Get the extension's hash-code. The hash-code
	 * will be used in validating the extension.
	 * 
	 * @return The hash-code.
	 * @see com.foureyes.extension.validate.Validate
	 */
	public HashCode getHashCode();
	
	/**
	 * Get the current version of the extension.
	 * 
	 * @return The extension's version.
	 */
	public String getVersion();
	
	/**
	 * Check if the extension is just a snapshot.
	 * 
	 * @return isSnapshot.
	 */
	public boolean isSnapshot();
	
}
