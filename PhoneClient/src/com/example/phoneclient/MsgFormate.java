package com.example.phoneclient;

public class MsgFormate {
	
	public enum MsgType
	{
		setup,
		crossDevice,
		objectClick,
		returnValue,
		returnGesture
	
	}
	
	private static String setup ="setup;{0};{1};{2}/>"; //setup;tagID;caption;content/>
	private static String crossDevice="crossDevice;{0};{1};{2}/>";//crossDevice;tagID;caption;content/>

}
