/**
 * 
 */
package com.nng.bs15;

import java.util.Vector;

import org.androidannotations.annotations.EApplication;

import android.app.Application;

/**
 * @author kisstajmi
 *
 */
@EApplication
public class BS15Application extends Application {

	private boolean						isLearning			= false;
	private String						userPass			= "";
	private Vector<UserKeyPressData>	userKeyPressDatas	= new Vector<UserKeyPressData>();
	private LearnedValue				learnedValue		= new LearnedValue();

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

	/**
	 * @return the userPass
	 */
	public String getUserPass() {
		return userPass;
	}

	/**
	 * @param userPass
	 *            the userPass to set
	 */
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	/**
	 * @return the userKeyPressDatas
	 */
	public Vector<UserKeyPressData> getUserKeyPressDatas() {
		return userKeyPressDatas;
	}

	/**
	 * @param userKeyPressDatas
	 *            the userKeyPressDatas to set
	 */
	public void addUserKeyPressDatas(Vector<UserKeyPressData> userKeyPressData) {
		this.userKeyPressDatas.addAll(userKeyPressData);
	}

	public void addLearnedData(LearnedValue makeAVG) {
		learnedValue = makeAVG;
	}

	public LearnedValue getLearnedValue() {
		return learnedValue;
	}

}
