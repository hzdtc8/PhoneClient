package com.michaelmarner.gestures;
import java.io.IOException;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.R.string;
import android.util.Log;



public class HandleXml {
	
	LinkedList<Point> points;
	private XmlPullParserFactory xmlPullParserFactory;
	private Gesture mGesture;
	
	
	/**
	 * @return the gesture
	 */
	public Gesture getGesture() {
		return mGesture;
	}


	/**
	 * @param gesture the gesture to set
	 */
	public void setGesture(Gesture gesture) {
		mGesture = gesture;
	}


	public void parseXMLAndStoreIt(XmlPullParser myParser) throws XmlPullParserException, IOException
	{
		float n=Float.parseFloat("10000");
		points = new LinkedList<Point>();
		String name= null;
		Point pointsPoint = null;
		int eventType = myParser.getEventType();
		Log.i("Assets.demo", "parseXMLAndStoreIt: eventType "+eventType);
		while(eventType!=XmlPullParser.END_DOCUMENT)
		{
			Log.i("Assets.demo", "parseXMLAndStoreIt: eventType "+eventType);
			String nameString = myParser.getName();
			Log.i("Assets.demo", "parseXMLAndStoreIt: getname()"+nameString);
			switch (eventType) {
			
			case XmlPullParser.START_TAG:
				Log.i("Assets.demo", "parseXMLAndStoreIt: start_tag");
				 if (nameString.equals("Gesture")) {
						
						name = myParser.getAttributeValue(null, "Name");
						Log.i("Assets.demo", "parseXMLAndStoreIt: gesture name set "+ name);
					}
				 else if(nameString.equals("Point"))
					//convert value into float
					{
					Log.i("Assets.demo", "parseXMLAndStoreIt: Point");
					//pointsPoint.x=Float.parseFloat(myParser.getAttributeValue(null, "X"));
					float x = Float.parseFloat(myParser.getAttributeValue(null, "X"));
					
					Log.i("Assets.demo", "parseXMLAndStoreIt: Point.x="+x );
					//pointsPoint.y=Float.parseFloat(myParser.getAttributeValue(null, "y"));
					float y=Float.parseFloat(myParser.getAttributeValue(null, "Y"));
					Log.i("Assets.demo", "parseXMLAndStoreIt: Point.y="+y);
					pointsPoint= new Point(x, y);
					points.add(pointsPoint);
					}
				 
				

			case XmlPullParser.END_TAG:
				/*
				Log.i("Assets.demo", "parseXMLAndStoreIt: end_tag");
				if (nameString.equals("Point")) {
					float y=Float.parseFloat(myParser.getAttributeValue(null, "y"));
					Log.i("Assets.demo", "parseXMLAndStoreIt: Point.y="+y);
				}
				*/mGesture= new Gesture(name,points);
				Log.i("Assets.demo", "gesture name: "+mGesture.name );
				break;
			
			}
			eventType=myParser.next();
		}
		
		
		
	}

	
}
