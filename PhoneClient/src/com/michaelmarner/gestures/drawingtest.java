package com.michaelmarner.gestures;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.provider.SyncStateContract.Constants;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.michaelmarner.gestures.*;

public class drawingtest extends View {

	private Gesture currentGesture;// Current gesture we have 
	private GestureMode opMode; //current gesture mode(recognize or record)
	private LinkedList<GestureListener> listeners; // the linkedlist is used for store the each gesture's handler
	private static String TAG="TEST";

	
	public drawingtest(Context context)
	{
		super(context);
		listeners = new LinkedList<GestureListener>(); //create a new listeners object
		currentGesture= new Gesture();// create a new current gesture
		
	}
	public drawingtest(Context context, AttributeSet attributeSet)
	{
		
		
		super(context,attributeSet);
		listeners = new LinkedList<GestureListener>();//create a new listeners object
		currentGesture= new Gesture();// create a new current gesture

	
	}
	public void addListener(GestureListener listener)
	{
		this.listeners.add(listener);
	}
	protected void onDraw(Canvas canvas)
	{	
		Log.i(TAG, "OnDraw ");
		canvas.drawRGB(60, 60, 60);
		Paint paint = new Paint();
		
	
		paint.setColor(Color.RED);
		paint.setStrokeWidth(2);
		paint.setStyle(Style.STROKE);
		
		
		List<Point> points = currentGesture.getPoints();
		for(int i=1;i<points.size();++i)
		{
			Point p0 = points.get(i-1);//preview point
			Point p1= points.get(i); // current point
			canvas.drawLine(p0.x, p0.y, p1.x, p1.y, paint);
		}
	}
	
	public boolean onTouchEvent(MotionEvent mEvent)
	{
		
		
		invalidate();
		switch (mEvent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			currentGesture.add(new Point(mEvent.getX(), mEvent.getY()));
			Log.i(TAG, "X= "+ mEvent.getX());
			Log.i(TAG, "y= "+ mEvent.getY());
			Log.i("Point.toString()", "Point: "+new Point(mEvent.getX(), mEvent.getY()));
			break;
			
		case MotionEvent.ACTION_MOVE:
			currentGesture.add(new Point(mEvent.getX(), mEvent.getY()));
			Log.i(TAG, "X= "+ mEvent.getX());
			Log.i(TAG, "y= "+ mEvent.getY());
			Log.i("Point.toString()", "Point: "+new Point(mEvent.getX(), mEvent.getY()));
			break;
		case MotionEvent.ACTION_UP:
			currentGesture.add(new Point(mEvent.getX(), mEvent.getY()));
			Log.i("Point.toString()", "Point: "+new Point(mEvent.getX(), mEvent.getY()));
			Log.i("Point.toString()", "currentGesture point: "+currentGesture.getPoints().toString());
			for(GestureListener listener:listeners)
			{
				if (listener != null)
				{
					listener.handleGesture(currentGesture);
				}
				currentGesture = new Gesture();

				break;
			}
			Log.i(TAG, "X= "+ mEvent.getX());
			Log.i(TAG, "y= "+ mEvent.getY());
			break;
		
		}
		return true;
	}

}
