package com.example.phoneclient;

import android.R.integer;

public class Calculate {
	
	private int mMobileHeight, mMobileWidth,mScreenHeight,mScreenwidth;

	/**
	 * @return the mobileHeight
	 */
	public int getMobileHeight() {
		return mMobileHeight;
	}

	/**
	 * @param mobileHeight the mobileHeight to set
	 */
	public void setMobileHeight(int mobileHeight) {
		mMobileHeight = mobileHeight;
	}

	/**
	 * @return the mobileWidth
	 */
	public int getMobileWidth() {
		return mMobileWidth;
	}

	/**
	 * @param mobileWidth the mobileWidth to set
	 */
	public void setMobileWidth(int mobileWidth) {
		mMobileWidth = mobileWidth;
	}

	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight() {
		return mScreenHeight;
	}

	/**
	 * @param screenHeight the screenHeight to set
	 */
	public void setScreenHeight(int screenHeight) {
		mScreenHeight = screenHeight;
	}

	/**
	 * @return the screenwidth
	 */
	public int getScreenwidth() {
		return mScreenwidth;
	}

	/**
	 * @param screenwidth the screenwidth to set
	 */
	public void setScreenwidth(int screenwidth) {
		mScreenwidth = screenwidth;
	}
	
	public float calculateMobileHeight(float height)
	{
		 
		return (float) (height/125.78);
		
	}
	public float calculateMobileWidth(float width)
	{
		
		return (float) (width/125.78);
	}
	public float calculateScreenHeight(float height)
	{
		
		return (float) (height/35.85);
	}
	public float calculateScreenWidth(float width)
	{
		
		return (float) (width/35.85);
	}
	
	public float totalWidth(float mobilewidth,float screenwidth)
	{
		return mobilewidth+screenwidth;
	}
	public float totalLength(float mobileHeight, float screenLength)
	{
		return mobileHeight+screenLength;
	}
	

}
