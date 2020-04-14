package com.foureyes.io;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.bukkit.Bukkit;

import com.foureyes.data.DataSerial;
import com.foureyes.encrypt.HashCode;
import com.foureyes.exception.ControlCloseException;
import com.foureyes.exception.SendingDataException;
import com.foureyes.thread.LocalTransmitting;

/**
 * The core connection class which can be set an example
 * for data communication between the collaborated server
 * and the FES's control cloud.
 * The connection consists of all necessary server's information,
 * the sending and receiving data method, etc. So, if this class
 * get an unexpected error, the system can not work or stop instantly. 
 */
public class Connection {

	public static final long serialID = 4232547889181337429L;

	/**
	 * The socket of the control
	 */
	private Socket control;

	/**
	 * The IP of the server connecting to control
	 */
	private String IP;

	/**
	 * The specific hash-code of each server given by FES Studio. Without this
	 * hash-code, the client server cannot use FES's Client and also FES's
	 * extensions.
	 * Do not share this hash-code to other serves;otherwise, they can control
	 * all your server data and even using your extensions for free.
	 */
	private HashCode hashCode;

	/**
	 * The checking of server's authentication to control. If it isn't authed, the
	 * server can not connect to our FES's control cloud. As a result, There's
	 * nothing working, including extensions.
	 */
	private boolean authed = false;

	/**
	 * The get off point of a connection to FES's control and cloud. When the
	 * connection is set, the reading data thread, which use for transmitting data
	 * between the collaborated server with FES's control cloud, will be
	 * automatically run. Then authenticate the connection.
	 * 
	 * @param control : The control's socket
	 * @param IP : The server IP
	 * @param hashCode : The specific given hash-code
	 */
	public Connection(Socket control, String IP, HashCode hashCode) {
		this.control = control;
		this.IP = IP;
		this.hashCode = hashCode;
		
		Thread dataThread = new Thread(new LocalTransmitting(this));
		dataThread.start();

		authenticate();
	}

	/**
	 * Return the FES's control's socket
	 */
	public Socket getControl() {
		return control;
	}

	/**
	 * Return the server's IP
	 */
	public String getIP() {
		return IP;
	}

	/**
	 * Return the server hash-code
	 */
	public HashCode getHashCode() {
		return hashCode;
	}

	/**
	 * Return true if the server is authed, and false in the other hand.
	 */
	public boolean isAuthed() {
		return authed;
	}

	/**
	 * Set the authed value of the server.
	 * 
	 * @param authed : The value whether the collaborated
	 * server is authed or not.
	 */
	public void setAuthed(boolean authed) {
		this.authed = authed;
	}

	/**
	 * The method used to authenticate the server with the FES's
	 * control cloud
	 */
	public void authenticate() {
		if(!(isAuthed())) {
			try {
				send("#auth;" + getIP() + ";" + getHashCode());
			} catch (ControlCloseException | SendingDataException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * The method using for transmitting information. The data will be hashed and
	 * send to the FES's control through socket. The sending speed depends on the
	 * server's VPN, so if server is using a low Internet connection, the
	 * information may take a while before reaching FES's control cloud.
	 * 
	 * @param log : The data that need transmitting
	 * @throws ControlCloseException : throw if the control connection is closed
	 * @throws SendingDataException : throw if getting an error while
	 * trying to send data to FES's control.
	 */
	public void send(String log) throws ControlCloseException, SendingDataException {
		String hash = new HashCode(log).getCode();
		if (!getControl().isClosed()) {
			try {
				OutputStream out = getControl().getOutputStream();
				PrintWriter writer = new PrintWriter(out);

				writer.write(hash + "\n");
				writer.flush();
			} catch (Exception e) {
				throw new SendingDataException("Getting an error while trying to send data to FES's control cloud !");
			}
		} else {
			throw new ControlCloseException("The control connection is closed !");
		}
	}
	
	/**
	 * The most important method (can also be considered as the core of
	 * this bridge plugin) uses for translating data received from the
	 * FES's control cloud.
	 * Each data received has its own serial code. So depending on which
	 * code received, we will do the matching things.
	 * In the future, may be there're tons of receiving data, so we will
	 * improve the receive method later, if it's needed;
	 * All the received serial codes are listed in enum called DataSerial
	 * in com.foureyes.data package.
	 * 
	 * @param log : The serial of retrieved data
	 */
	public String receive(int log) {
		if(log == DataSerial.LOGIN_SESSION.getSerial(true)) {
			Bukkit.getOnlinePlayers().forEach(p -> {
				p.sendTitle("§a§lAUTHENTICATION", "§c§lSuccessfully !");
			});
			return "Authenticated successfully !";
		}
		return "";
	}
}
