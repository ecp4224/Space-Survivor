/**
 * Programmer: Eddie Penta
 * Date: May 20, 2013
 * Purpose: <INSERT PURPOSE>
 * File name: AnimationCallback.java
 */
package com.eddie.rpeg.engine.render.animation;

public interface AnimationCallback {
	
	public interface OnAnimationCompleted {
		
		public void onAnimationCompleted(AnimationStyle style);
	}
}
