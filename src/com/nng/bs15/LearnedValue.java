/**
 * 
 */
package com.nng.bs15;

/**
 * @author kisstajmi
 *
 */
public class LearnedValue extends PatternValue {

	int	diffTestTime	= 0;
	int	diffKeyNum		= 0;
	int	diffSpecNum		= 0;
	int	diffAvgKeyPress	= 0;
	int	diffAvgkeyTime	= 0;

	public LearnedValue(PatternValue patternValue) {
		super(patternValue);
	}

	public LearnedValue() {
		super();
	}

	public boolean matches(PatternValue pv) {
		int req = 0;
		if (this.testTime - this.diffTestTime < pv.testTime && this.testTime + this.diffTestTime > pv.testTime) {
			req++;
		}
		if (this.keyNum - this.diffKeyNum < pv.keyNum && this.keyNum + this.diffKeyNum > pv.keyNum) {
			req++;
		}
		if (this.specNum - this.diffSpecNum < pv.specNum && this.specNum + this.diffSpecNum > pv.specNum) {
			req++;
		}
		if (this.KeyPress - this.diffAvgKeyPress < pv.KeyPress && this.KeyPress + this.diffAvgKeyPress > pv.KeyPress) {
			req++;
		}
		if (this.keyTime - this.diffAvgkeyTime < pv.keyTime && this.keyTime + this.diffAvgkeyTime > pv.keyTime) {
			req++;
		}
		return req > 2;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" diffTestTime: " + diffTestTime);
		sb.append(" diffKeyNum: " + diffKeyNum);
		sb.append(" diffSpecNum: " + diffSpecNum);
		sb.append(" diffAvgKeyPress: " + diffAvgKeyPress);
		sb.append(" diffAvgkeyTime: " + diffAvgkeyTime);
		return super.toString() + sb.toString();
	}
}
