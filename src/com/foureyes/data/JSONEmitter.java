package com.foureyes.data;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import com.foureyes.system.SystemPart;

/**
 * This class will store all information that will be packed and sent to FES's
 * control cloud every 1 minute as JSONObject. <br>
 * The data will include : <br>
 * <h5>Player</h5> • Block broke/placed <br>
 * • Mobs/Players killed <br>
 * • Total experience, experience to level up, level <br>
 * <h5>Server</h5> • TPS, Ping <br>
 * • CPU <br>
 * • RAM <br>
 * • Online players, max players <br>
 * We need collect these data because the collaborated server's staffs can
 * control their server and players by watching these information update every
 * minute through our client. From that, they don't need to be available at
 * their server, but still make sure it's under control.
 */
public class JSONEmitter {

	/**
	 * The hash-map that store the status of the collaborated server. The key is
	 * <b>SystemPart</b> enum and the value is, in fact, an integer, but we stored
	 * as string for an easier progress.
	 * 
	 * @see SystemPart
	 */
	public static HashMap<String, HashMap<SystemPart, String>> SYSTEMS = new HashMap<>();
	/**
	 * The hash-map that store some information about players' activities. The key
	 * is a String, which is player's name and the value is another hashmap with
	 * <b>PlayerStatistic</b> key and value, just like SYSTEMS, is an integer.
	 * 
	 * @see PlayerStatistic
	 */
	public static HashMap<String, HashMap<PlayerStatistic, String>> PLAYERS = new HashMap<>();

	/**
	 * Set the system value data at current timestamp.
	 * 
	 * @param part  : The part of the system that need setting
	 * @param value : The value to set
	 */
	public static void setSystem(SystemPart part, String value) {
		String timeStamp = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy")
				.format(new Timestamp(System.currentTimeMillis()));
		HashMap<SystemPart, String> map;
		if(SYSTEMS.containsKey(timeStamp)) {
			map = SYSTEMS.get(timeStamp);
		}else {
			map = new HashMap<>();
		}
		map.put(part, value);
		SYSTEMS.put(timeStamp, map);
	}

	/**
	 * Set player's statistics.
	 * 
	 * @param player    : The player who will be set/changed statistic value
	 * @param statistic : The statistic that need setting
	 * @param value     : The value to set
	 */
	public static void setPlayer(Player player, PlayerStatistic statistic, String value) {
		HashMap<PlayerStatistic, String> map;
		if (PLAYERS.containsKey(player.getName())) {
			map = PLAYERS.get(player.getName());
		} else {
			map = new HashMap<>();
		}
		map.put(statistic, value);
		PLAYERS.put(player.getName(), map);
	}

	/**
	 * Get the value of a system part at a timestamp.
	 * Please attend that the timestamp must be at
	 * <b><code>HH:mm:ss dd.MM.yyyy</code></b> format.
	 * 
	 * @param part The part that need getting value
	 * @return The value of that system part
	 */
	public static String getSystem(SystemPart part, String timeStamp) {
		return SYSTEMS.get(timeStamp).get(part);
	}

	/**
	 * Get the value of a player's statistic.
	 * 
	 * @param player    The player
	 * @param statistic The statistic that need getting value
	 * @return The value of that player's statistic
	 */
	public static String getPlayer(Player player, PlayerStatistic statistic) {
		return PLAYERS.get(player.getName()).getOrDefault(statistic, "0");
	}

	/**
	 * An important method that will repackage all data from system to players'
	 * statistics into a JSONObject. Repackaging these data will make the sending
	 * progress to FES's control cloud more easier and the weight of the data may be
	 * lighter. Thus, it do not take much time to send and have any effects on
	 * collaborated server's network access.
	 * 
	 * @return A JSONObject that contains all system and players' statistic data.
	 */
	public static JSONObject repackage() {
		JSONObject total = new JSONObject();

		if (PLAYERS != null) {
			JSONObject playerMap = new JSONObject();
			PLAYERS.keySet().forEach(s -> {
				JSONObject playerObj = new JSONObject();
				HashMap<PlayerStatistic, String> map = PLAYERS.get(s);
				map.keySet().forEach(key -> {
					playerObj.put(key.toString(), map.get(key));
				});
				playerMap.put(s, playerObj);
			});
			total.put("PLAYERS", playerMap);
		}

		JSONObject systemMap = new JSONObject();
		SYSTEMS.keySet().forEach(s -> {
			JSONObject systemObj = new JSONObject();
			HashMap<SystemPart, String> map = SYSTEMS.get(s);
			map.keySet().forEach(key -> {
				systemObj.put(key.toString(), map.get(key));
			});
			systemMap.put(s, systemObj);
		});

		total.put("SYSTEM", systemMap);
		return total;
	}

	/**
	 * Reset all stored data, which including : system and players'
	 * statistic.
	 */
	public static void reset() {
		SYSTEMS.clear();
		PLAYERS.clear();
	}
}
