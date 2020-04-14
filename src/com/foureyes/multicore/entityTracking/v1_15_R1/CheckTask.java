package com.foureyes.multicore.entityTracking.v1_15_R1;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_15_R1.ChunkProviderServer;
import net.minecraft.server.v1_15_R1.WorldServer;

/**
 * An BukkitRunnable class that use to check all entities in each world before
 * tracking.
 */
public class CheckTask extends BukkitRunnable {

	@Override
	public void run() {
		if (UntrackerTask.isRunning()) {
			return;
		}
		for (World world : Bukkit.getWorlds()) {
			if (world == null) {
				continue;
			}
			checkWorld(world.getName());
		}
	}

	/**
	 * A method that will check all its entities and preparing for tracking them.
	 * 
	 * @param worldName : The name of world that will be checked
	 */
	public void checkWorld(String worldName) {
		WorldServer ws = ((CraftWorld) Bukkit.getWorld(worldName)).getHandle();
		ChunkProviderServer cps = ws.getChunkProvider();

		Set<net.minecraft.server.v1_15_R1.Entity> trackAgain = new HashSet<>();

		int d = 25;
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player player : Bukkit.getWorld(worldName).getPlayers()) {
					for (Entity ent : player.getNearbyEntities(d, d, d)) {
						trackAgain.add(((CraftEntity) ent).getHandle());
//						EntityTickManager.getInstance().enableTicking(((CraftEntity) ent).getHandle(), worldName);
					}
				}
				NMSEntityTracker.trackEntities(cps, trackAgain);
			}

		};
	}

}