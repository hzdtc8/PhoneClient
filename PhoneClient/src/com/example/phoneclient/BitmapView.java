package com.example.phoneclient;

import java.util.LinkedList;
import java.util.List;

import android.R.integer;
import android.R.string;
import android.animation.FloatEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.provider.Telephony.Mms.Addr;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class BitmapView extends ImageView {
	float x;
	float y;
	String X;
	String Y;
	int Xx,Yy;
	LinkedList<PointerListener> listeners;
	//private float mMobileHeigth,mMobileWidth;
	mobileDe mObiledemienDe;
	Calculate calculate;


	
	public BitmapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mObiledemienDe = new mobileDe();
		// TODO Auto-generated constructor stub
	}

	public void addListener(PointerListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(4);
		paint.setStyle(Style.STROKE);
		
		canvas.drawPoint(x, y, paint);
		
		
		calculate = new Calculate();
		// what's use for?
		mObiledemienDe.setMobileHeigth(calculate.calculateMobileHeight(y));
		mObiledemienDe.setMobileWidth(calculate.calculateMobileWidth(x));
		System.out.println("get mobile width="+mObiledemienDe.getMobileWidth()+" and heigth="+mObiledemienDe.getMobileHeigth());
		
		
		
			}

	
//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		// TODO Auto-generated method stub
//		invalidate();
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			x=event.getX();
//			Xx=Math.round(x);
//			System.out.println("ontouchEvent x= "+x);
//			System.out.println("ontouchEvent Xx= "+Xx);
//			y=event.getY();
//			Yy=Math.round(y);
//			System.out.println("ontouchEvent x= "+y);
//			System.out.println("ontouchEvent Xx= "+Yy);
//		break;
//		}
//		
//		return false;
//	}
//	
//	public boolean onLongClickEvent(MotionEvent event)
//	{
//		invalidate();
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			x=event.getX();
//			Xx=Math.round(x);
//			System.out.println("ontouchEvent x= "+x);
//			System.out.println("ontouchEvent Xx= "+Xx);
//			y=event.getY();
//			Yy=Math.round(y);
//			System.out.println("ontouchEvent x= "+y);
//			System.out.println("ontouchEvent Xx= "+Yy);
////		break;
////		}
//		return true;
//		
//	}

}
