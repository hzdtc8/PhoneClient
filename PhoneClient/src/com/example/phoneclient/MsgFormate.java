package com.example.phoneclient;

import java.text.MessageFormat;

public class MsgFormate {
	
	public enum MsgType
	{
		setup,
		crossDevice,
		objectClick,
		returnValue,
		returnGesture
	
	}
	
	private static String setup="setup;{0};{1};{2}"; //setup;tagID;caption;content/>
	private static String crossDevice="crossDevice;{0};{1};{2}";//crossDevice;tagID;caption;content/>
	private static String objectClick="objectClick;{0};{1};{2}";//objectClick;tagID;caption;content/>
	private static String returnValue="returnValue;{0};{1};{2}";//returnValue;tagID;caption;content/>
	private static String returnGesture="returnGesture;{0};{1};{2}";//returnGesture;tagID;caption;content/>
	
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
	
	public static MsgFormate newSetup(String tagID,String caption,String content)
	{
		MsgFormate msgFormate = new MsgFormate();
		msgFormate.messageText = MessageFormat.format(setup, tagID,caption,content);
		msgFormate.messageType= MsgType.setup;
		
		return msgFormate;
	}
	
	public static MsgFormate newCrossDevice(String tagID,String caption,String content)
	{
		MsgFormate msgFormate = new MsgFormate();
		msgFormate.messageText = MessageFormat.format(crossDevice,tagID,caption,content );
		msgFormate.messageType = MsgType.crossDevice;
		
		return msgFormate;
	}
	
	public static MsgFormate newReturnValue(String tagID,String caption,String content)
	{
		MsgFormate msgFormate = new MsgFormate();
		msgFormate.messageText = MessageFormat.format(returnValue,tagID,caption,content );
		msgFormate.messageType = MsgType.returnValue;
		
		return msgFormate;
	}
	
	public static MsgFormate newReturnGesture(String tagID,String caption,String content)
	{
		MsgFormate msgFormate = new MsgFormate();
		msgFormate.messageText = MessageFormat.format(returnGesture,tagID,caption,content );
		msgFormate.messageType = MsgType.returnGesture;
		
		return msgFormate;
	}
	public static MsgFormate newObjectClick(String tagID,String caption,String content)
	{
		MsgFormate msgFormate = new MsgFormate();
		msgFormate.messageText = MessageFormat.format(objectClick,tagID,caption,content );
		msgFormate.messageType = MsgType.objectClick;
		
		return msgFormate;
	}
	
	
	
	
	
	


}
