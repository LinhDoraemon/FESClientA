package com.foureyes.configuration;

import java.io.File;
import java.util.Base64;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * A class that will help us control all things stored in
 * this bridge's configuration file (config.yml);
 */
public class ConfigurationSession {

	/**
	 * A specific hash-code that will be encoded in BASE64 to store and will be
	 * decoded then encoded again when being sent to FES's control cloud to verify.
	 */
	public static String HASHED_VERIFY = getSession().getString("HASH_CODE");
	/**
	 * A decoded form of the hash-code.
	 */
	public static String RAW_HASHED_VERIFY = new String(Base64.getDecoder().decode(HASHED_VERIFY));

	/**
	 * Load the configuration (config.yml) of this bridge plugin.
	 * 
	 * @return The bridge's configuration file.
	 */
	public static FileConfiguration getSession() {
		return YamlConfiguration.loadConfiguration(
				new File("plugins" + File.separator + "PDXClientBridge" + File.separator + "config.yml"));
	}

}
