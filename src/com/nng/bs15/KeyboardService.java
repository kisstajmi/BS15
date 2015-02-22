/**
 * 
 */
package com.nng.bs15;

import android.annotation.SuppressLint;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

/**
 * @author kisstajmi
 *
 */
public class KeyboardService extends InputMethodService implements OnKeyboardActionListener {

	private KeyboardView kv;
	private Keyboard keyboard;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateInputView() {
		kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
		keyboard = new Keyboard(this, R.xml.qwerty);
		kv.setKeyboard(keyboard);
		kv.setOnKeyboardActionListener(this);
		return kv;
	}

	private void playClick(int keyCode) {
		AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
		switch (keyCode) {
		case 32:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
			break;
		case Keyboard.KEYCODE_DONE:
		case 10:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
			break;
		case Keyboard.KEYCODE_DELETE:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
			break;
		default:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.inputmethodservice.KeyboardView.OnKeyboardActionListener#onKey
	 * (int, int[])
	 */
	@Override
	public void onKey(int primaryCode, int[] arg1) {
		InputConnection ic = getCurrentInputConnection();
		playClick(primaryCode);
		switch (primaryCode) {
		case Keyboard.KEYCODE_DELETE:
			ic.deleteSurroundingText(1, 0);
			keyboard.setShifted(false);
			break;
		case Keyboard.KEYCODE_SHIFT:
			keyboard.setShifted(true);
			kv.invalidateAllKeys();
			break;
		case Keyboard.KEYCODE_DONE:
			ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
			keyboard.setShifted(false);
			break;
		default:
			char code = (char) primaryCode;
			if (Character.isLetter(code) && keyboard.isShifted()) {
				code = Character.toUpperCase(code);
			}
			ic.commitText(String.valueOf(code), 1);
			keyboard.setShifted(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.inputmethodservice.KeyboardView.OnKeyboardActionListener#onPress
	 * (int)
	 */
	@Override
	public void onPress(int arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.inputmethodservice.KeyboardView.OnKeyboardActionListener#onRelease
	 * (int)
	 */
	@Override
	public void onRelease(int arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.inputmethodservice.KeyboardView.OnKeyboardActionListener#onText
	 * (java.lang.CharSequence)
	 */
	@Override
	public void onText(CharSequence arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.inputmethodservice.KeyboardView.OnKeyboardActionListener#swipeDown
	 * ()
	 */
	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.inputmethodservice.KeyboardView.OnKeyboardActionListener#swipeLeft
	 * ()
	 */
	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.inputmethodservice.KeyboardView.OnKeyboardActionListener#swipeRight
	 * ()
	 */
	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub

	}

}
