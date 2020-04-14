package com.foureyes.multicore.entityTracking.v1_14_R1;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.scheduler.BukkitRunnable;

import com.foureyes.utilities.ReflectionUtils;

import net.minecraft.server.v1_14_R1.ChunkProviderServer;
import net.minecraft.server.v1_14_R1.EntityArmorStand;
import net.minecraft.server.v1_14_R1.EntityEnderDragon;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.MinecraftServer;
import net.minecraft.server.v1_14_R1.PlayerChunkMap.EntityTracker;
import net.minecraft.server.v1_14_R1.WorldServer;

/**
 * A class that will untrack the entities.
 */
public class UntrackerTask extends BukkitRunnable {

	private static boolean running = false;

	private static Field trackerField;

	static {
		try {
			trackerField = ReflectionUtils.getClassPrivateField(EntityTracker.class, "tracker");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		if (MinecraftServer.getServer().recentTps[0] > 19.5) {
			return;
		}
		running = true;
		for (World world : Bukkit.getWorlds()) {
			untrackProcess(world.getName());
		}
		running = false;
	}

	private void untrackProcess(String worldName) {
		if (Bukkit.getWorld(worldName) == null) {
			return;
		}
		Set<Integer> toRemove = new HashSet<>();
		int removed = 0;
		WorldServer ws = ((CraftWorld) Bukkit.getWorld(worldName)).getHandle();
		ChunkProviderServer cps = ws.getChunkProvider();

		try {
			for (EntityTracker et : cps.playerChunkMap.trackedEntities.values()) {
				net.minecraft.server.v1_14_R1.Entity nmsEnt = (net.minecraft.server.v1_14_R1.Entity) trackerField
						.get(et);
				if (nmsEnt instanceof EntityPlayer || nmsEnt instanceof EntityEnderDragon) {
					continue;
				}
				if (nmsEnt instanceof EntityArmorStand && nmsEnt.getBukkitEntity().getCustomName() != null) {
					continue;
				}
				boolean remove = false;
				if (et.trackedPlayers.size() == 0) {
					remove = true;
				} else if (et.trackedPlayers.size() == 1) {
					for (EntityPlayer ep : et.trackedPlayers) {
						if (!ep.getBukkitEntity().isOnline()) {
							remove = true;
						}
					}
					if (!remove) {
						continue;
					}
				}
				if (remove) {
					toRemove.add(nmsEnt.getId());
					removed++;
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		for (int id : toRemove) {
			cps.playerChunkMap.trackedEntities.remove(id);
//			EntityTickManager.getInstance().disableTicking(id, worldName);
		}

		Bukkit.getConsoleSender().sendMessage("§e[EntityTrackingFixLag] §bĐã hủy load §c" + removed + " §bentities tại " + worldName);

	}

	/**
	 * Check if this task is running or not.
	 */
	public static boolean isRunning() {
		return running;
	}

}