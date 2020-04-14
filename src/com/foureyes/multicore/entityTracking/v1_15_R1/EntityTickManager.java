package com.foureyes.multicore.entityTracking.v1_15_R1;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.foureyes.utilities.ReflectionUtils;

import net.minecraft.server.v1_15_R1.WorldServer;

/**
 * A BukkitRunnable class that will control all tasks and methods of EntityTick
 * - which is done every second by Minecraft, and it's one of the most serious
 * reasons causing lag. Controlling the EntityTick is the key of EntityTracking
 * because we only track the entity has high tick and far from player in a
 * specific distance.
 */
public class EntityTickManager extends BukkitRunnable {

	private static Field tickingEntitiesField;
	private static Field entityCount;

	static {
		try {
			tickingEntitiesField = ReflectionUtils.getClassPrivateField(WorldServer.class, "tickingEntities");
			entityCount = ReflectionUtils.getClassPrivateField(net.minecraft.server.v1_15_R1.Entity.class,
					"entityCount");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static EntityTickManager instance;

	private Map<String, EntityTickWorldCache> cache = new HashMap<>();

	private EntityTickManager(Plugin plugin) {
		this.runTaskTimer(plugin, 61, 61);
	}

	/**
	 * Disable ticking entity with specific id in a world.
	 * 
	 * @param id        : The ID of the entity
	 * @param worldName : The entity's world
	 */
	public void disableTicking(int id, String worldName) {
		cache.get(worldName).getToTick().remove(id);
		cache.get(worldName).getToUntick().add(id);
	}

	/**
	 * Enable ticking entity with specific id in a world.
	 * 
	 * @param entity    : The NMS entity that need ticking
	 * @param worldName : The entity's world
	 */
	public void enableTicking(net.minecraft.server.v1_15_R1.Entity entity, String worldName) {
		cache.get(worldName).getToUntick().remove(entity.getId());
		cache.get(worldName).getToTick().put(entity.getId(), entity);
	}

	/**
	 * A task that will start ticking entities.
	 */
	@Override
	public void run() {
		if (UntrackerTask.isRunning()) {
			return;
		}
		for (String worldName : cache.keySet()) {
			EntityTickWorldCache ewc = cache.get(worldName);
			WorldServer ws = ewc.getWorldServer();
			if (ws.b()) {
				continue;
			}
			try {
				if (tickingEntitiesField.getBoolean(ws)) {
					continue;
				}
				for (int i : ewc.getToUntick()) {
					ws.entitiesById.remove(i);
				}
				ewc.getToUntick().clear();
				for (int i : ewc.getToTick().keySet()) {
					net.minecraft.server.v1_15_R1.Entity entity = ewc.getToTick().get(i);
					if (entity == null) {
						continue;
					}
					if (!entity.valid || entity.dead) {
						continue;
					}
					if (ws.entitiesById.containsValue(entity)) {
						continue;
					}
					if (ws.entitiesById.containsKey(i)) {
						int id = ((AtomicInteger) entityCount.get(null)).incrementAndGet();
						ws.entitiesById.put(id, entity);
					} else {
						ws.entitiesById.put(i, entity);
					}
				}
				ewc.getToTick().clear();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the EntityTickManager instance.
	 */
	public static EntityTickManager getInstance() {
		if (instance == null) {
			instance = new EntityTickManager(Bukkit.getPluginManager().getPlugin("FESClientBridge"));
		}
		return instance;
	}

	/**
	 * Get the world's EntityTick cache.
	 * 
	 * @return The cache
	 */
	public Map<String, EntityTickWorldCache> getCache() {
		return cache;
	}

}