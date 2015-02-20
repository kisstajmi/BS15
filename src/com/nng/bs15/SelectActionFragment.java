package com.nng.bs15;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
@EFragment(R.layout.fragment_select_action)
public class SelectActionFragment extends Fragment implements OnClickListener {

	@ViewById(R.id.sel_add_new_learning_data)
	Button sel_add_new_learning_data;
	@ViewById(R.id.sel_add_new_test_data)
	Button sel_add_new_test_data;
	@App
	BS15Application app;

	@Click({ R.id.sel_add_new_learning_data, R.id.sel_add_new_test_data })
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
		}
	}
}