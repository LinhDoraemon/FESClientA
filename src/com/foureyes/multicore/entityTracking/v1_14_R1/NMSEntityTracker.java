package com.foureyes.multicore.entityTracking.v1_14_R1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import com.foureyes.utilities.ReflectionUtils;

import net.minecraft.server.v1_14_R1.ChunkProviderServer;
import net.minecraft.server.v1_14_R1.PlayerChunkMap;

/**
 * A class that will track all the entities in the collaborated
 * server.
 * Minecraft tracks a lot of entities, even if they are outside
 * the tracking range of the player, that's a normal behavior but 
 * is a tps killer for 1.14.4 and 1.15 servers with more than 30 players. 
 * So we untrack those entities in specific ticks and track them again
 * if the player is near. Thus, the server's TPS can be more stable.
 *
 */
public final class NMSEntityTracker {
	
	private static Method addEntityMethod;
	private static Method removeEntityMethod;
	
	static {
		try {
		addEntityMethod = ReflectionUtils.getPrivateMethod(PlayerChunkMap.class, "addEntity", 
				new Class[] {net.minecraft.server.v1_14_R1.Entity.class});
		removeEntityMethod = ReflectionUtils.getPrivateMethod(PlayerChunkMap.class, "removeEntity", 
				new Class[] {net.minecraft.server.v1_14_R1.Entity.class});
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private NMSEntityTracker() {}
	
	public static void trackEntities(ChunkProviderServer cps, Set<net.minecraft.server.v1_14_R1.Entity> trackList) {
		try {
			for(net.minecraft.server.v1_14_R1.Entity entity : trackList) {
				if(cps.playerChunkMap.trackedEntities.containsKey(entity.getId())) {
					continue;
				}
				addEntityMethod.invoke(cps.playerChunkMap, entity);
			}
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void untrackEntities(ChunkProviderServer cps, Set<net.minecraft.server.v1_14_R1.Entity> untrackList) {
		try {
			for(net.minecraft.server.v1_14_R1.Entity entity : untrackList) {
				removeEntityMethod.invoke(cps.playerChunkMap, entity);
			}
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}