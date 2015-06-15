package com.example.phoneclient;

import android.app.Application;

public class mobileDe extends Application {
	private float mMobileHeigth,mMobileWidth;
	/**
	 * @return the mobileHeigth
	 */
	public float getMobileHeigth() {
		return mMobileHeigth;
	}
	/**
	 * @param mobileHeigth the mobileHeigth to set
	 */
	public void setMobileHeigth(float mobileHeigth) {
		mMobileHeigth = mobileHeigth;
	}
	/**
	 * @return the mobileWidth
	 */
	public float getMobileWidth() {
		return mMobileWidth;
	}
	/**
	 * @param f the mobileWidth to set
	 */
	public void setMobileWidth(float f) {
		mMobileWidth = f;
	}

}
