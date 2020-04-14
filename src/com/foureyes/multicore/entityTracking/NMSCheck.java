package com.foureyes.multicore.entityTracking;

import org.bukkit.plugin.Plugin;

import com.foureyes.utilities.BroadcastPane;

/**
 * Check the version of collaborated server.
 * Currently, the Entity Tracking Fix Lag feature just
 * supports 1.14 and 1.15. Other versions may be updated
 * soon.
 */
public final class NMSCheck {

	/**
	 * Check the NMS version of collaborated server.
	 * 
	 * @param plugin The bridge plugin
	 * @return The NMS version that server is using.
	 */
	public static NMS getNMS(Plugin plugin) {
		String packageName = plugin.getServer().getClass().getPackage().getName();
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);

		try {
			final Class<?> clazz = Class.forName("com.foureyes.multicore.entityTracking." + version + ".NMSHandler");
			if (NMS.class.isAssignableFrom(clazz)) {
				return (NMS) clazz.getConstructor().newInstance();
			}
		} catch (final Exception e) {
			BroadcastPane.showErrorAndContact(plugin,
					"Phiên bản không được hỗ trợ !\n" + "Có vẻ phiên bản " + version
							+ " của máy chủ bạn không được hỗ trợ \n"
							+ "chức năng EntityTrackingFixLag. Hãy chờ đợi bản cập nhật\n"
							+ "của plugin hoặc nhấn liên hệ để réo developers làm nhanh lên.");
			return null;
		}
		return null;
	}
}