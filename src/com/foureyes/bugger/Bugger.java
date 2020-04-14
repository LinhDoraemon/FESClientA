package com.foureyes.bugger;

import javax.swing.JFrame;

/**
 * This class is actually a JFrame which will appear when this
 * plugin crashes.
 * The bugger will show you the detail about the crash and 
 * the way how to fix it sometimes. But, in general, this bugger
 * is a perfect tool for collaborated server's staffs to debug
 * this bridge plugin as well as the extensions.
 */
public class Bugger extends JFrame {

	private static final long serialVersionUID = -1820596891112700345L;

	/**
	 * The content (detail about the crash).
	 */
	private String content;

	/**
	 * The way to solve the problem.
	 * If there's no configuration, it will be shown as
	 * default.
	 */
	private String solution = "Hãy liên hệ với FOUREYES STUDIO để được hướng dẫn thêm về vấn đề này.";
	
	/**
	 * The crash's detail.
	 * @return The content/the detail about the plugin's crash
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Change the plugin crash's detail.
	 * @param content : The new content.
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * The way how to fix the crash, often in brief.
	 * @return The solution.
	 */
	public String getSolution() {
		return solution;
	}
	
	/**
	 * Change the solution.
	 * If you don't change the solution, it'll be displayed
	 * in default.
	 * @param solution : The new solution.
	 */
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	/**
	 * Show the bugger UI.
	 */
	public void appears() {
		setTitle("FES CRASH REPORT");
		
	}
}
