/**
 * 
 */
package com.nng.bs15;

/**
 * @author kisstajmi
 *
 */
public class PatternValue {

	int	testTime	= 0;
	int	keyNum		= 0;
	int	specNum		= 0;
	int	KeyPress	= 0;
	int	keyTime		= 0;

	// copy constructor
	public PatternValue(PatternValue patternValue) {
		this.testTime = patternValue.testTime;
		this.keyNum = patternValue.keyNum;
		this.specNum = patternValue.specNum;
		this.KeyPress = patternValue.KeyPress;
		this.keyTime = patternValue.keyTime;
	}

	public PatternValue() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TestTime: " + testTime);
		sb.append(" KeyNum: " + keyNum);
		sb.append(" SpecNum: " + specNum);
		sb.append(" KeyPress: " + KeyPress);
		sb.append(" keyTime: " + keyTime);
		return sb.toString();
	}

}
