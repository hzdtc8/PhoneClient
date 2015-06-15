package com.michaelmarner.gestures;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;



public class SerializeGestureHandler {
	public String writerXml(Gesture g)
	{
		StringWriter xmlWriter = new StringWriter();
		try {
			XmlPullParserFactory xFactory = XmlPullParserFactory.newInstance();
			XmlSerializer xSerializer = xFactory.newSerializer();
			
			xSerializer.setOutput(xmlWriter);
			xSerializer.startDocument("UTF-8", true); 
			
			xSerializer.startTag("","Gesture");
			xSerializer.attribute("", "Name", g.name.toString());
			Log.i("Outside of For", "X="+g.getPoints().toString());
			
			for(Point point:g.getPoints())
			{
			xSerializer.startTag("", "Point");
			xSerializer.attribute("", "X",String.valueOf(point.x));
			Log.i("Create XML", "orginal X="+point.x);
			Log.i("Create XML", "X="+String.valueOf(point.x));
			xSerializer.attribute("", "Y",String.valueOf(point.y));
			Log.i("Create XML", "Y="+String.valueOf(point.y));
			xSerializer.endTag("", "Point");
			}
		   xSerializer.endTag("", "Gesture");
		   xSerializer.endDocument();
		
		
			
			
			/*
			for (Gesture gesture: g) {
				xSerializer.startTag("","Gesture");
				xSerializer.attribute("", "Name", gesture.name.toString());
				Log.i("Outside of For", "X="+gesture.getPoints().toString());
				
			   for(Point point:gesture.getPoints())
				{
				xSerializer.startTag("", "Point");
				xSerializer.attribute("", "X",String.valueOf(point.x));
				Log.i("Create XML", "orginal X="+point.x);
				Log.i("Create XML", "X="+String.valueOf(point.x));
				xSerializer.attribute("", "Y",String.valueOf(point.y));
				Log.i("Create XML", "Y="+String.valueOf(point.y));
				xSerializer.endTag("", "Point");
				}
			   xSerializer.endTag("", "Gesture");
			   xSerializer.endDocument();
			}
			*/
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		return xmlWriter.toString();
		
	}

}
