package com.foureyes.multicore.entityTracking.v1_14_R1;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.plugin.Plugin;

import com.foureyes.multicore.entityTracking.NMS;

/**
 * A NMS handler class that will start entities' tracking
 * task and checking entities task. Moreover, it can help
 * us load the worlds' cache.
 */
public class NMSHandler implements NMS {

	/**
	 * Start EntityTracking and EntityChecking tasks.
	 */
	@Override
	public void startTasks(Plugin plugin) {
		new UntrackerTask().runTaskTimer(plugin, 500, 500);
		new CheckTask().runTaskTimer(plugin, 500 + 1, 40);
	}

	/**
	 * Load all worlds' cache.
	 */
	@Override
	public void loadWorldCache() {
		for(World world : Bukkit.getWorlds()) {
			if(world == null) {
				continue;
			}
			EntityTickManager.getInstance().getCache().put(world.getName(), 
					new EntityTickWorldCache(((CraftWorld)world).getHandle()));
		}
	}

}