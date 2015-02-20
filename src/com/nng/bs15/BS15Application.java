/**
 * 
 */
package com.nng.bs15;

import org.androidannotations.annotations.EApplication;

import android.app.Application;

/**
 * @author kisstajmi
 *
 */
@EApplication
public class BS15Application extends Application {

	private boolean isLearning = false;

	/**
	 * 
	 */
	public BS15Application() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the isLearning
	 */
	public boolean isLearning() {
		return isLearning;
	}

	/**
	 * @param isLearning
	 *            the isLearning to set
	 */
	public void setLearning(boolean isLearning) {
		this.isLearning = isLearning;
	}

}
