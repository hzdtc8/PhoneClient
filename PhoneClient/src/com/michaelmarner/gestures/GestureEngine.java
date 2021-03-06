/*
 * Copyright (c) 2010, Michael Marner (michael@20papercups.net) 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.michaelmarner.gestures;

import java.util.*;

import android.R.string;
import android.app.Application;
import android.util.Log;

public class GestureEngine{

	/**
	 * The number of points paths will be resized to.
	 */
	private int pathFidelity;
	private int size;
	private LinkedList<Gesture> gestures;
	private static String Gesture_Engine="gestureEngine";
	
	
	public void setLinkedList(LinkedList<Gesture> gesture)
	{
		this.gestures=gesture;
	}
	
	//add the getter
	public LinkedList<Gesture> getLinkedList()
	{
		return gestures;
	}
	
	public GestureEngine() {
		gestures = new LinkedList<Gesture>();
		pathFidelity = 64;
		size = 2;
	}
	public GestureEngine(LinkedList<Gesture> gesture) {
		gestures = new LinkedList<Gesture>();
		gestures=gesture;
		pathFidelity = 64;
		size = 2;
	}
	
	public void addGesture(Gesture p) {
		Log.i(" In Engine class handleGesture", "Before Point: "+p.getPoints().toString());	
		prepare(p);
		Log.i(" In Engine class handleGesture", "after prepare Point: "+p.getPoints().toString());	
		gestures.add(p);
		Log.i(" In Engine class handleGesture", "after add Point: "+p.getPoints().toString());	
	}
	public List<Gesture> get()
	{
		return gestures;
	}

	public GestureMatch recognise(Gesture g) {
		if (gestures.size() == 0)
			throw new RuntimeException("Gesture list is empty!");
		prepare(g);
		
		float b = Integer.MAX_VALUE;
		
		GestureMatch bestMatch = new GestureMatch();
		
		for (Gesture template : gestures) {
		
			float d = distanceAtBestAngle(g, template, -45, 45, 2);
			if (d < b) {
				b = d;
				bestMatch.gesture = template;
			}
		}
		Log.i(Gesture_Engine, "match name: "+bestMatch.gesture.name);
		bestMatch.confidence = (float) (1 - b / 0.5 * Math.sqrt(size*size + size*size));
		Log.i(Gesture_Engine,"Recognise starts");
		Log.i(Gesture_Engine, "confidence: " +bestMatch.confidence);
		Log.i(Gesture_Engine, "Name: " +bestMatch.gesture.name);
		return bestMatch;
	}
	
	private float distanceAtBestAngle(Gesture path, Gesture template, float thetaA, float thetaB, float deviation) {
		float magicNumber = (float) (0.5*(-1 + Math.sqrt(5)));

		float x1 = magicNumber*thetaA + (1-magicNumber)*thetaB;
		float f1 = distanceAtAngle(path, template, x1);
		float x2 = (1-magicNumber)*thetaA + magicNumber*thetaB;
		float f2 = distanceAtAngle(path, template, x2);

		while (Math.abs(thetaB - thetaA) > deviation)
		{
			if (f1 < f2)
			{
				thetaB = x2;
				x2 = x1;
				f2 = f1;
				x1 = magicNumber*thetaA + (1-magicNumber)*thetaB;
				f1 = distanceAtAngle(path, template, x1);
			}
			else
			{
				thetaA = x1;
				x1 = x2;
				f1 = f2;
				x2 = (1-magicNumber)*thetaA + magicNumber*thetaB;
				f2 = distanceAtAngle(path, template, x2);
			}
		}
		if (f1 < f2)
			return f1;
		
		return f2;
	}
	
	
	private float distanceAtAngle(Gesture path, Gesture template, float theta) {
		Gesture newPoints = new Gesture(path);
		newPoints.rotateBy(theta);
		return newPoints.distance(template);
	}
	
	
	private void prepare(Gesture p) {
		p.resample(pathFidelity);
		p.rotateToZero();
		p.scaleToSquare(size);
		p.translateToOrigin();
	}
	

}
