package com.nng.bs15;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_select_action)
public class SelectActionFragment extends Fragment implements OnClickListener {

	@ViewById(R.id.sel_add_new_learning_data)
	Button sel_add_new_learning_data;
	@ViewById(R.id.sel_add_new_test_data)
	Button sel_add_new_test_data;
	@ViewById(R.id.sel_start)
	Button sel_start;
	@ViewById(R.id.sel_server_ip)
	EditText sel_server_ip;
	@ViewById(R.id.sel_server_port)
	EditText sel_server_port;
	@App
	BS15Application app;

	@Click({ R.id.sel_add_new_learning_data, R.id.sel_add_new_test_data, R.id.sel_start })
	@Override
	public void onClick(View v) {
		app.setLearning(false);
		switch (v.getId()) {
		case R.id.sel_add_new_learning_data:
			app.setLearning(true);
		case R.id.sel_add_new_test_data:
			FragmentTransaction transaction = getFragmentManager().beginTransaction();

			// Replace whatever is in the fragment_container view with this
			// fragment,
			// and add the transaction to the back stack
			transaction.replace(R.id.container, new RegisterDatasFragment_());
			transaction.addToBackStack(getActivity().getString(R.string.bs15backstack));

			// Commit the transaction
			transaction.commit();
			break;
		case R.id.sel_start:
			Intent i = new Intent(getActivity(), ServerCommunicationService_.class);
			if (sel_server_ip.getText().toString().trim().length() > 0) {
				i.putExtra("server_ip", sel_server_ip.getText().toString());
			}
			if (sel_server_port.getText().toString().trim().length() > 0) {
				i.putExtra("server_port", sel_server_port.getText().toString());
			}
			getActivity().startService(i);
			break;
		}
	}
}