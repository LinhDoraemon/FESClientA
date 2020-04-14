package com.foureyes.multicore.entityTracking.v1_15_R1;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.plugin.Plugin;

import com.aparapi.Kernel;
import com.aparapi.device.Device;
import com.aparapi.internal.kernel.KernelPreferences;
import com.foureyes.multicore.entityTracking.NMS;
import com.foureyes.utilities.BroadcastPane;

/**
 * A NMS handler class that will start entities' tracking task and checking
 * entities task. Moreover, it can help us load the worlds' cache.
 */
public class NMSHandler implements NMS {

	private boolean isRunning = true;

	/**
	 * Start EntityTracking and EntityChecking tasks.
	 */
	@Override
	public void startTasks(Plugin plugin) {

		if (hasGPU()) {
			Bukkit.getConsoleSender()
					.sendMessage("§e[EntityTrackingFixLag] §aChức năng EntityTracking đang chạy đa nhiệm !");
			new Thread(() -> {
				while (isRunning) {
					try {
						Kernel kernel = new Kernel() {
							@Override
							public void run() {
								new UntrackerTask().run();
								new CheckTask().run();
							}
						};
						kernel.run();
						Thread.sleep(30 * 1000);
					} catch (Exception e) {
						isRunning = false;
						BroadcastPane.showErrorAndContact(Bukkit.getPluginManager().getPlugin("FESClientBridge"),
								"Chúng tôi không thể thực hiện EntityTracking trên GPU.\n"
										+ "Hãy liên hệ với chúng tôi để được hỗ trợ.");
					}
				}
			}).start();
		} else {
			Bukkit.getConsoleSender()
					.sendMessage("§e[EntityTrackingFixLag] §aChức năng EntityTracking đang chạy đa luồng !");
			new UntrackerTask().runTaskTimerAsynchronously(plugin, 500, 500);
			new CheckTask().runTaskTimerAsynchronously(plugin, 500 + 1, 40);
		}

	}

	/**
	 * Load all worlds' cache.
	 */
	@Override
	public void loadWorldCache() {
		for (World world : Bukkit.getWorlds()) {
			if (world == null) {
				continue;
			}
			EntityTickManager.getInstance().getCache().put(world.getName(),
					new EntityTickWorldCache(((CraftWorld) world).getHandle()));
		}
	}

	private boolean hasGPU() {
		KernelPreferences preferences = com.aparapi.internal.kernel.KernelManager.instance().getDefaultPreferences();
		for (Device device : preferences.getPreferredDevices(null)) {
			if (device.getType() == Device.TYPE.GPU) {
				return true;
			}
		}
		return false;
	}

}