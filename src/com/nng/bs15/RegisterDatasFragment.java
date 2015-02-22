package com.nng.bs15;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_register_datas)
public class RegisterDatasFragment extends Fragment implements OnClickListener, OnTouchListener {

	@ViewById(R.id.reg_text)
	EditText reg_text;
	@ViewById(R.id.reg_save)
	Button reg_save;
	@ViewById(R.id.reg_keyboard)
	GridLayout reg_keyboard;
	@App
	BS15Application app;

	@AfterInject
	protected void afterInject() {
		for (KEYS key : KEYS.values()) {
			TextView v = new TextView(getActivity());
			v.setText(key.getText());
			v.setTag(key);
			v.setOnClickListener(this);
			v.setOnTouchListener(this);

			int numOfCol = reg_keyboard.getColumnCount();
			int numOfRow = (int) Math.ceil(Float.valueOf(KEYS.values().length) / 10f);
			reg_keyboard.setRowCount(numOfRow);
			reg_keyboard.addView(v);
		}
	}

	boolean isShift = false;
	String password = "";

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
		default:
			if (v.getTag() instanceof KEYS) {
				KEYS key = (KEYS) v.getTag();
				switch (key) {
				case KEY_SHIFT:
					isShift = true;
					break;
				case KEY_BACKSPACE:
					password = password.substring(0, password.length() - 2);
					break;
				default:
					if (isShift) {
						password += String.valueOf(key.getValue()).toUpperCase();
						isShift = false;
					} else {
						password += String.valueOf(key.getValue());
					}
					break;
				}
				reg_text.setText(password);
			}
			break;
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}