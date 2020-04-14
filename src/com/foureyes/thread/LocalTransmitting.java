package com.foureyes.thread;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.foureyes.io.Connection;

public class LocalTransmitting extends Thread {

	private Connection connection;

	public LocalTransmitting(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public void run() {
		while (!getConnection().getControl().isClosed()) {
			try {
				InputStream in = getConnection().getControl().getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));

				String data = null;

				while (!getConnection().getControl().isClosed() && (data = reader.readLine()) != null) {
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				
				try {
					getConnection().getControl().close();
					this.stop();
				} catch (Exception e2) {
					e.printStackTrace();
				}
				
			}
		}
	}

}
