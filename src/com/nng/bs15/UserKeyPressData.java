package com.nng.bs15;

public class UserKeyPressData {

	int		relativeposx	= 0;
	int		relativeposy	= 0;
	boolean	isup;
	String	code;
	int		time			= 0;

	public UserKeyPressData(int _relativeposx, int _relativeposy, boolean _isup, String _code, int _time) {
		relativeposx = _relativeposx;
		relativeposy = _relativeposy;
		isup = _isup;
		code = _code;
		time = _time;
	}

}
