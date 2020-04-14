package com.foureyes.thread;

import com.foureyes.io.FESStream;

/**
 * A thread class that will help us to retrieve all texts that
 * have been printed from console.
 * We use <b>ConsoleOutputStream</b> for some different purposes
 * like detect every time the console printing an error <i>(Can see
 * more clearly in Bugger feature which point out the errors and
 * the ways how to fix them)</i>, or creating log files. 
 */
public class ConsoleOutputStream extends Thread {

	public static FESStream ERROR = new FESStream();
	public static FESStream WARNING = new FESStream();
	public static FESStream LOG = new FESStream();
	
}
