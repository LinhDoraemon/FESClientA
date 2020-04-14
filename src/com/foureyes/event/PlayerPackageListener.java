package com.foureyes.event;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.foureyes.data.JSONEmitter;
import com.foureyes.data.PlayerStatistic;
import com.foureyes.utilities.BroadcastPane;

/**
 * A listener class that will listen some minecraft's events to collect players'
 * statistic data and store in <b>JSONEmitter</b> so as to send to FES's control
 * cloud every minute.
 * 
 * @see JSONEmitter
 */
public class PlayerPackageListener implements Listener {

	@EventHandler
	public void FES_PPL_PLAYER_JOIN(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if(JSONEmitter.PLAYERS.containsKey(player.getName()) == false) {
			HashMap<PlayerStatistic, String> map = new HashMap<>();
			for(PlayerStatistic pl : PlayerStatistic.values()) {
				map.put(pl, "0");
			}
			JSONEmitter.PLAYERS.put(player.getName(), map);
		}
	}
	
	@EventHandler
	public void FES_PPL_BLOCK_BREAK(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();

		if (block == null)
			return;

		int value = 0;
		try {
			value = Integer.parseInt(JSONEmitter.getPlayer(player, PlayerStatistic.BLOCKS_BREAK));
		} catch (Exception ex) {
			ex.printStackTrace();
			BroadcastPane.showErrorAndContact(Bukkit.getPluginManager().getPlugin("FESClientBridge"),
					"Chúng tôi không thể thu thập dữ liệu người dùng !\n"
							+ "Đây là vấn đề của hệ thống, vui lòng báo cáo cho nhân viên\n"
							+ "của chúng tôi để được hỗ trợ.");
		}
		JSONEmitter.setPlayer(player, PlayerStatistic.BLOCKS_BREAK, "" + ((value + 1)));
	}

	@EventHandler
	public void FES_PPL_BLOCK_PLACE(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		Block block = e.getBlock();

		if (block == null)
			return;

		int value = 0;
		try {
			value = Integer.parseInt(JSONEmitter.getPlayer(player, PlayerStatistic.BLOCKS_PLACED));
		} catch (Exception ex) {
			ex.printStackTrace();
			BroadcastPane.showErrorAndContact(Bukkit.getPluginManager().getPlugin("FESClientBridge"),
					"Chúng tôi không thể thu thập dữ liệu người dùng !\n"
							+ "Đây là vấn đề của hệ thống, vui lòng báo cáo cho nhân viên\n"
							+ "của chúng tôi để được hỗ trợ.");
		}
		JSONEmitter.setPlayer(player, PlayerStatistic.BLOCKS_PLACED, "" + (value + 1));
	}

	@EventHandler
	public void FES_PPL_ENTITY_KILL(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity))
			return;

		Player player = (Player) e.getDamager();
		LivingEntity livingEn = (LivingEntity) e.getEntity();

		if (livingEn.getHealth() - e.getDamage() <= 0) {
			if (livingEn instanceof Player) {
				int value = 0;
				try {
					value = Integer.parseInt(JSONEmitter.getPlayer(player, PlayerStatistic.PLAYERS_KILLED));
				} catch (Exception ex) {
					ex.printStackTrace();
					BroadcastPane.showErrorAndContact(Bukkit.getPluginManager().getPlugin("FESClientBridge"),
							"Chúng tôi không thể thu thập dữ liệu người dùng !\n"
									+ "Đây là vấn đề của hệ thống, vui lòng báo cáo cho nhân viên\n"
									+ "của chúng tôi để được hỗ trợ.");
				}
				JSONEmitter.setPlayer(player, PlayerStatistic.PLAYERS_KILLED, "" + (value + 1));
			} else {
				int value = 0;
				try {
					value = Integer.parseInt(JSONEmitter.getPlayer(player, PlayerStatistic.MOBS_KILLED));
				} catch (Exception ex) {
					ex.printStackTrace();
					BroadcastPane.showErrorAndContact(Bukkit.getPluginManager().getPlugin("FESClientBridge"),
							"Chúng tôi không thể thu thập dữ liệu người dùng !\n"
									+ "Đây là vấn đề của hệ thống, vui lòng báo cáo cho nhân viên\n"
									+ "của chúng tôi để được hỗ trợ.");
				}
				JSONEmitter.setPlayer(player, PlayerStatistic.MOBS_KILLED, "" + (value + 1));
			}
		}
	}
	
	@EventHandler
	public void FES_PPL_EXP(PlayerExpChangeEvent e) {
		Player player = e.getPlayer();
		
		try {
		JSONEmitter.setPlayer(player, PlayerStatistic.EXP, "" + player.getExp());
		JSONEmitter.setPlayer(player, PlayerStatistic.EXP_LEVEL_UP, "" + player.getExpToLevel());
		JSONEmitter.setPlayer(player, PlayerStatistic.LEVEL, "" + player.getLevel());
		}catch (Exception ex) {
			ex.printStackTrace();
			BroadcastPane.showErrorAndContact(Bukkit.getPluginManager().getPlugin("FESClientBridge"),
					"Chúng tôi không thể thu thập dữ liệu người dùng !\n"
							+ "Đây là vấn đề của hệ thống, vui lòng báo cáo cho nhân viên\n"
							+ "của chúng tôi để được hỗ trợ.");
		}
	}
}
