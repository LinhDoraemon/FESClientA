package com.foureyes.extension.validate;

import org.bukkit.plugin.Plugin;

import com.foureyes.extension.Extension;

/**
 * This class is used to validated all the FESClient's
 * extensions. Without validating, those extensions can
 * not run.<br>
 * We will validate by checking the extensions' hash-code.
 * If the collaborated server has these hash-code, then 
 * they can use the extensions.<br>
 * Moreover, all the extensions will use this FESClientBridge
 * as a core to extend. So, hackers are impossible to run
 * extensions as independent plugins.
 *
 */
public class Validate {

	public static ValidateStatus validate(Extension extension, Plugin plugin) {
		
		return ValidateStatus.SUCCESS;
	}
	
}
