package com.nng.bs15;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_register_datas)
public class RegisterDatasFragment extends Fragment {

	@ViewById(R.id.reg_text)
	EditText reg_text;
	@ViewById(R.id.reg_save)
	Button save;
	@ViewById(R.id.reg_keyboard)
	GridView reg_keyboard;

	public RegisterDatasFragment() {
	}
}