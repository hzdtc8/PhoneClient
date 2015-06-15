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
	
	private static String setupd="setup;{0};{1};{2}/>"; //setup;tagID;caption;content/>
	private static String crossDevice="crossDevice;{0};{1};{2}/>";//crossDevice;tagID;caption;content/>
	private static String objectClick="objectClick;{0};{1};{2}/>";//objectClick;tagID;caption;content/>
	private static String returnValue="returnValue;{0};{1};{2}/>";//returnValue;tagID;caption;content/>
	private static String returnGesture="returnGesture;{0};{1};{2}/>";//returnGesture;tagID;caption;content/>
	
	private String messageText;
	private MsgType messageType;
	
	public String getMessageText()
	{
		return messageText;
	}
	
	public void setMessageText(String text)
	{
		messageText=text;
	}
	
	public MsgType getMessageType()
	{
		return messageType;
	}
	
	public void setMessageType(MsgType msgType)
	{
		messageType=msgType;
	}
	
	
	


}
