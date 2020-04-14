package com.foureyes.thread;

import org.bukkit.Bukkit;

import com.foureyes.command.Tesst;
import com.foureyes.data.JSONEmitter;
import com.foureyes.system.SystemPart;
import com.foureyes.system.SystemProcessor;
import com.foureyes.utilities.BroadcastPane;

public class JSONRepackageDataSending extends Thread {

	public static boolean isRunning = false;

	public JSONRepackageDataSending() {
		isRunning = true;
	}

	public int seconds = 0;

	@Override
	public void run() {
		while (isRunning) {
			try {
				if (seconds % 5 == 0 && seconds != 0) {
					JSONEmitter.setSystem(SystemPart.MAX_PLAYERS, "" + Bukkit.getMaxPlayers());
					JSONEmitter.setSystem(SystemPart.MAX_RAM, "" + SystemProcessor.getMaxRAM());
					JSONEmitter.setSystem(SystemPart.ONLINE_PLAYERS, "" + Bukkit.getOnlinePlayers().size());
					JSONEmitter.setSystem(SystemPart.RAM, "" + SystemProcessor.getCurrentRAMUsed());
					JSONEmitter.setSystem(SystemPart.TPS, SystemProcessor.getCurrentTPS());
				}

				if (seconds == 60) {
					Tesst.socket.emit("Data", JSONEmitter.repackage().toJSONString());
					JSONEmitter.reset();
					seconds = 0;
				}
				
				seconds++;
				Thread.sleep(1 * 1000);
			} catch (Exception e) {
				e.printStackTrace();
				BroadcastPane.showErrorAndContact(Bukkit.getPluginManager().getPlugin("FESClientBridge"),
						"Đã có một vấn đề nghiêm trọng xảy ra trong quá trình truyền\n"
								+ "dữ liệu lên hệ thống của chúng tôi. Máy chủ của bạn sẽ không\n"
								+ "thể cập nhật hệ thống dữ liệu trên FES Client. Hãy\n"
								+ "liên hệ với chúng tôi để sửa chữa ngay.");
			}
		}
	}
}
