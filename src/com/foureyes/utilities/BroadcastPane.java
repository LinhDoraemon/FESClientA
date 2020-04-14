package com.foureyes.utilities;

import java.awt.Desktop;
import java.net.URL;

import javax.swing.JOptionPane;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * This class is a util-class that create and show some specific JOptionPanes
 * that have been configured.
 */
public class BroadcastPane {

	public static void showErrorAndContact(Plugin plugin, String message) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			int result = JOptionPane.showOptionDialog(null, "ĐÃ CÓ LỖI XẢY RA ! \n" + message, "FES CLIENT PLUGIN",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Liên hệ", "Đồng ý"}, null);
			if (result == JOptionPane.YES_OPTION) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
				if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
					try {
						desktop.browse(new URL("https://www.facebook.com/FourEyesTeam/").toURI());
					} catch (Exception e) {
					}
				}
			}
			Bukkit.getPluginManager().disablePlugin(plugin);
		});
	}

}
