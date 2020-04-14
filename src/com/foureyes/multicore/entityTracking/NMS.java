package com.foureyes.multicore.entityTracking;

import org.bukkit.plugin.Plugin;

/**
 * An interface that will be implemented to some NMS-needing class
 */
public interface NMS {

	/**
	 * Start tracking entity task.
	 * @param plugin The client bridge plugin
	 */
	public void startTasks(Plugin plugin);

	/**
	 * Load the world cache.
	 */
	public void loadWorldCache();

}
