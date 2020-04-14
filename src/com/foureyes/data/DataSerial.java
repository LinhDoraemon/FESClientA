package com.foureyes.data;

public enum DataSerial {

	LOGIN_SESSION;
	
	public int getSerial(boolean trace) {
		if(this == LOGIN_SESSION) {
			if(trace == true) return 100011;
			return 100012;
		}
		return 000000;
	}
	
}
