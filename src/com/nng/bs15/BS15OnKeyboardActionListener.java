/*
 * Copyright (C) 2011 - Riccardo Ciovati
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nng.bs15;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.KeyEvent;
import android.widget.EditText;

/***
 * Listener da associare ad un oggetto KeyboardView in modo tale che quando
 * viene premuto un tasto il corrispondente evento viene girato all'activity
 * passata al costruttore
 */
public class BS15OnKeyboardActionListener implements OnKeyboardActionListener {

	private Activity		mTargetActivity;
	private Keyboard		mKeyboard;
	private KeyboardView	mKeyboardView;
	private EditText		editText;

	/***
	 * 
	 * @param targetActivity
	 *            Activity a cui deve essere girato l'evento
	 *            "pressione di un tasto sulla tastiera"
	 * @param keyboard
	 */
	public BS15OnKeyboardActionListener(Activity targetActivity, Keyboard keyboard, KeyboardView keyboardView, EditText _edittext) {
		mTargetActivity = targetActivity;
		mKeyboard = keyboard;
		mKeyboardView = keyboardView;
		editText = _edittext;
	}

	@Override
	public void swipeUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swipeDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onText(CharSequence text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRelease(int primaryCode) {
		// TODO Auto-generated method stub

	}

	private void shiftOn(boolean on) {
		mKeyboard.setShifted(on);
		// shiftKey.icon = mKeyboard.isShifted() ? shiftLockDrawable :
		// shiftDrawable;
		mKeyboardView.invalidateAllKeys();
	}

	@Override
	public void onKey(int primaryCode, int[] keyCodes) {

		Editable editable = editText.getEditableText();
		int selectionStart = editText.getSelectionStart();

		if (primaryCode == Keyboard.KEYCODE_SHIFT) {
			shiftOn(!mKeyboard.isShifted());
		} else {
			// if (primaryCode == Keyboard.KEYCODE_DELETE) {
			// if (editable != null && selectionStart > 0) {
			// editable.delete(selectionStart - 1, selectionStart);
			// }
			// } else {
			// String editChar = KeyEvent.keyCodeToString(primaryCode);
			// if (mKeyboard.isShifted()) {
			// editChar = editChar.toUpperCase();
			// }
			// editable.insert(selectionStart, editChar);
			// }

			long eventTime = System.currentTimeMillis();
			if (mKeyboard.isShifted()) {
				KeyEvent event = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, primaryCode, 0, KeyEvent.META_SHIFT_ON, 0, 0,
						KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);
				mTargetActivity.dispatchKeyEvent(event);
			} else {
				KeyEvent event = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, primaryCode, 0, 0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD
						| KeyEvent.FLAG_KEEP_TOUCH_MODE);
				mTargetActivity.dispatchKeyEvent(event);
			}

			shiftOn(false);
		}

	}

	@Override
	public void onPress(int primaryCode) {
		// TODO Auto-generated method stub

	}

}