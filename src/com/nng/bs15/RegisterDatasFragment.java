package com.nng.bs15;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.Touch;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.inputmethodservice.Keyboard;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_register_datas)
public class RegisterDatasFragment extends Fragment implements OnClickListener, OnTouchListener, OnKeyListener {

	@ViewById(R.id.reg_text)
	EditText			reg_text;
	@ViewById(R.id.reg_save)
	Button				reg_save;
	// @ViewById(R.id.reg_keyboard)
	// GridLayout reg_keyboard;
	@ViewById(R.id.keyboard)
	BS15KeyBoardView	keyboardView;
	@App
	BS15Application		app;
	Keyboard			keyboard;
	@SystemService
	InputMethodManager	inputMethodManager;

	@AfterViews
	protected void afterInject() {

		// Create the Keyboard
		keyboard = new Keyboard(getActivity(), R.xml.qwerty);

		// Attach the keyboard to the view
		keyboardView.setKeyboard(keyboard);

		keyboardView.setOnKeyboardActionListener(new BS15OnKeyboardActionListener(getActivity(), keyboard, keyboardView, reg_text));
	}

	boolean	isShift		= false;
	String	password	= "";

	@Click(R.id.reg_save)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reg_save:
			FragmentTransaction f = getFragmentManager().beginTransaction();
			f.remove(this);
			f.commit();
			getFragmentManager().popBackStack();
			SaveMethod();
			break;
		// default:
		// if (v.getTag() instanceof KEYS) {
		// KEYS key = (KEYS) v.getTag();
		// switch (key) {
		// case KEY_SHIFT:
		// isShift = true;
		// break;
		// case KEY_BACKSPACE:
		// password = password.substring(0, password.length() - 2);
		// break;
		// default:
		// if (isShift) {
		// password += String.valueOf(key.getValue()).toUpperCase();
		// isShift = false;
		// } else {
		// password += String.valueOf(key.getValue());
		// }
		// break;
		// }
		// reg_text.setText(password);
		// }
		// break;
		}
	}

	private void SaveMethod() {
		if (isValiduUser()) {
			// TODO send to server
		}
	}

	private boolean isValiduUser() {
		if (password.equals(app.getUserPass())) {
			// TODO vizsgálat
		}
		return false;
	}

	@Touch(R.id.reg_text)
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		showKeyboardWithAnimation();
		return true;
	}

	private void showKeyboardWithAnimation() {
		if (keyboardView.getVisibility() == View.GONE || keyboardView.getVisibility() == View.INVISIBLE) {
			Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_bottom);
			keyboardView.showWithAnimation(animation);
			keyboardView.setVisibility(View.VISIBLE);
			reg_text.requestFocus();
		}
	}

	private void hideKeyboardWithAnimation() {
		if (keyboardView.getVisibility() == View.VISIBLE) {
			Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_bottom);
			keyboardView.hideWithAnimation(animation);
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		boolean ret = false;
		// playClick(keyCode);
		// String text = reg_text.getText().toString();
		// switch (keyCode) {
		// case Keyboard.KEYCODE_SHIFT:
		// keyboard.setShifted(true);
		// keyboardView.invalidateAllKeys();
		// ret = true;
		// break;
		// case Keyboard.KEYCODE_DONE:
		// hideKeyboardWithAnimation();
		// keyboard.setShifted(false);
		// ret = true;
		// break;
		// case Keyboard.KEYCODE_DELETE:
		// text = text.substring(0, text.length() - 1);
		// keyboard.setShifted(false);
		// ret = true;
		// break;
		// case KeyEvent.KEYCODE_ENTER:
		// keyboard.setShifted(false);
		// ret = true;
		// break;
		// default:
		// char code = (char) keyCode;
		// if (Character.isLetter(code) && keyboard.isShifted()) {
		// code = Character.toUpperCase(code);
		// }
		// text += code;
		// keyboard.setShifted(false);
		// ret = true;
		// }
		// reg_text.setText(text);
		return ret;
	}

	private void playClick(int keyCode) {
		AudioManager am = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);
		switch (keyCode) {
		case 32:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
			break;
		case Keyboard.KEYCODE_DONE:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
			break;
		case Keyboard.KEYCODE_DELETE:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
			break;
		default:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
		}
	}

}