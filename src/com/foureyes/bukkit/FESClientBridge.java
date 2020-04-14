package com.foureyes.bukkit;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.foureyes.command.Tesst;
import com.foureyes.data.JSONEmitter;
import com.foureyes.data.PlayerStatistic;
import com.foureyes.encrypt.HashCode;
import com.foureyes.event.PlayerPackageListener;
import com.foureyes.extension.Extension;
import com.foureyes.io.Connection;
import com.foureyes.security.X509EncodedKeySpec;
import com.foureyes.thread.JSONRepackageDataSending;
import com.foureyes.utilities.BroadcastPane;
import com.foureyes.utilities.Files;

import io.socket.client.IO;

public class FESClientBridge extends JavaPlugin {

	/**
	 * A hash-map stores all collaborated server's extension. The key is the
	 * Extension interface, and the value is the plugin.
	 * 
	 * @see Extension
	 */
	private static HashMap<Extension, Plugin> EXTENSIONS = new HashMap<>();

	public void onEnable() {
		try {
			loadAllLibraries();
		} catch (Exception e) {
			String msg = "Chúng tôi không thể tiến hành load tài nguyên.\n"
					+ "Vui lòng xem lại đường dẫn plugins/FESClientBridge/lib.\n"
					+ "Nếu không tồn tại, hãy thử khởi động lại plugin này; hoặc thông\n"
					+ " báo tới nhân viên hỗ trợ của chúng tôi bằng cách nhấn vào LIÊN HỆ.";
			BroadcastPane.showErrorAndContact(this, msg);
		}

		getCommand("testsocket").setExecutor(new Tesst());
		getCommand("gpu").setExecutor(new Tesst());
		Bukkit.getPluginManager().registerEvents(new PlayerPackageListener(), this);

		if (Bukkit.getOnlinePlayers().size() != 0) {
			Bukkit.getOnlinePlayers().forEach(player -> {
				HashMap<PlayerStatistic, String> map = new HashMap<>();
				for (PlayerStatistic pl : PlayerStatistic.values()) {
					map.put(pl, "0");
				}
				JSONEmitter.PLAYERS.put(player.getName(), map);
			});
		}

		X509EncodedKeySpec.generateKey();
		try {
			Tesst.socket = IO.socket("http://139.180.213.77:4000");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onDisable() {
		JSONRepackageDataSending.isRunning = false;
		Tesst.socket.disconnect();
	}

	/**
	 * The control connection which will be setup when the bridge is activated.
	 */
	public static Connection control;

	/**
	 * Return the connection of the collaborated server to the control.
	 */
	public static Connection getControlConnection() {
		return control;
	}

	/**
	 * This method will load all the libraries that this bridge plugin need.
	 * 
	 * @throws Exception : throw if there's an error while trying to load all
	 *                   libraries.
	 */
	public void loadAllLibraries() throws Exception {
		Files.loadLibrary(new File("plugins" + File.separator + "FESClientBridge" + File.separator + "lib"
				+ File.separator + "aparapi.jar"));
		Files.loadLibrary(new File("plugins" + File.separator + "FESClientBridge" + File.separator + "lib"
				+ File.separator + "bcel.jar"));
		Files.loadLibrary(new File("plugins" + File.separator + "FESClientBridge" + File.separator + "lib"
				+ File.separator + "aparapi-jni.jar"));
		Files.loadLibrary(new File(
				"plugins" + File.separator + "FESClientBridge" + File.separator + "lib" + File.separator + "json.jar"));
		Files.loadLibrary(new File("plugins" + File.separator + "FESClientBridge" + File.separator + "lib"
				+ File.separator + "okhttp.jar"));
		Files.loadLibrary(new File(
				"plugins" + File.separator + "FESClientBridge" + File.separator + "lib" + File.separator + "okio.jar"));
		Files.loadLibrary(new File("plugins" + File.separator + "FESClientBridge" + File.separator + "lib"
				+ File.separator + "engineio.jar"));
		Files.loadLibrary(new File("plugins" + File.separator + "FESClientBridge" + File.separator + "lib"
				+ File.separator + "socketio.jar"));
	}

	/**
	 * Get all validated extensions of the collaborated server.
	 */
	public Set<Extension> getExtensions() {
		return EXTENSIONS.keySet();
	}

	/**
	 * Get the plugin through extension.
	 * 
	 * @param extension The extension.
	 * @return If there's not any validated extension like that, it'll return null.
	 */
	public Plugin getExtension(Extension extension) {
		return EXTENSIONS.getOrDefault(extension, null);
	}

	/**
	 * Get the extension by its hash-code.
	 * 
	 * @param hashCode The extension's hash-code
	 * @return The extension has that hash-code
	 */
	public Extension getExtensionByHashCode(HashCode hashCode) {
		for (Extension e : getExtensions()) {
			if (e.getHashCode().getCode().equalsIgnoreCase(hashCode.getCode())) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Register an extension.
	 */
	public void registerExtension(Extension extension, Plugin plugin) {
		EXTENSIONS.put(extension, plugin);
	}
}
