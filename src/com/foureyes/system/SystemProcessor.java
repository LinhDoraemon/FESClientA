package com.foureyes.system;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import org.bukkit.Bukkit;

/**
 * A class that can get some system parts' status of the collaborated server
 * such as : CPU, TPS, Ping ...<br>
 * All those things can be found at class <b>SystemPart</b>
 * 
 * @see SystemPart
 */
public class SystemProcessor {

	/**
	 * Get the current TPS of the collaborated server in 1ms, 5ms, and 15ms. Those
	 * TPS will be separated by the ','. Each TPS will be an integer.
	 * 
	 * @return The current server's TPS in 1/5/15ms
	 */
	public static String getCurrentTPS() {
		double[] tps = Bukkit.getTPS();
		return "" + (int)tps[0] + "," + (int)tps[1] + "," + (int)tps[2];
	}

	/**
	 * Get the TPS at specific ping.
	 * 
	 * @see TPSPing
	 * @param ping The ping that you want to get TPS.
	 * @return The TPS at specific ping.
	 */
	public static double getTPSAt(TPSPing ping) {
		double[] tps = Bukkit.getTPS();
		switch (ping) {
		case A1MS:
			return tps[0];
		case A5MS:
			return tps[1];
		case A15MS:
			return tps[2];
		default:
			return -1;
		}
	}

	/**
	 * Get the current RAM collaborated server is using.
	 * 
	 * @return The current RAM used
	 */
	public static long getCurrentRAMUsed() {
		Runtime r = Runtime.getRuntime();
		long memUsed = (r.totalMemory() - r.freeMemory()) / 1048576;
		return memUsed;
	}
	
	/**
	 * Get the maximum RAM that collaborated server can run.
	 * 
	 * @return The maximum RAM of server
	 */
	public static long getMaxRAM() {
		return Runtime.getRuntime().totalMemory();
	}

	/**
	 * Get the server's CPU usage percent.
	 * 
	 * @return Percent of CPU usage
	 */
	public static double getPercentCPUUsage() {
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		return osBean.getSystemLoadAverage()*100;
	}
}
