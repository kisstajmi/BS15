package com.nng.bs15;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_register_datas)
public class RegisterDatasFragment extends Fragment implements OnClickListener {

	@ViewById(R.id.reg_text)
	EditText reg_text;
	@ViewById(R.id.reg_save)
	Button reg_save;
	@ViewById(R.id.reg_keyboard)
	GridView reg_keyboard;

	@Click(R.id.reg_save)
	@Override
	public void onClick(View v) {
		FragmentTransaction f = getFragmentManager().beginTransaction();
		f.remove(this);
		f.commit();
		getFragmentManager().popBackStack();
	}
}