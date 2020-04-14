package com.foureyes.multicore.entityTracking.v1_15_R1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.minecraft.server.v1_15_R1.WorldServer;

/**
 * The cache that will store some data of EntityTick.
 */
public class EntityTickWorldCache {
	
	private String worldName;
	private WorldServer worldServer;
	
	private Set<Integer> toUntick = new HashSet<>();
	private Map<Integer, net.minecraft.server.v1_15_R1.Entity> toTick = new HashMap<>();
	
	public EntityTickWorldCache(WorldServer worldServer) {
		this.worldName = worldServer.getWorld().getName();
		this.worldServer = worldServer;
	}

	/**
	 * Get a set of entities' ID that need unticking.
	 * 
	 * @return An integer set of entities' ID
	 */
	public Set<Integer> getToUntick() {
		return toUntick;
	}

	/**
	 * Get a map with values are entities that need ticking and
	 * keys are their's ID.
	 * 
	 * @return A map of ticking entities.
	 */
	public Map<Integer, net.minecraft.server.v1_15_R1.Entity> getToTick() {
		return toTick;
	}

	/**
	 * Get EntityTick world name.
	 * @return The name of the world.
	 */
	public String getWorldName() {
		return worldName;
	}

	/**
	 * Get the WorldServer.
	 * @return WorldServer
	 */
	public WorldServer getWorldServer() {
		return worldServer;
	}
}