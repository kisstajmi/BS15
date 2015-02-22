package com.nng.bs15;

import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new SelectActionFragment_()).commit();
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// FragmentManager fragManager = getFragmentManager();
		// BackStackEntry backEntry = fragManager.getBackStackEntryAt(0);
		// String str = backEntry.getName();
		// Fragment fragment = fragManager.findFragmentByTag(str);
		// if (fragment instanceof OnKeyListener) {
		// return ((OnKeyListener) fragment).onKey(null, event.getKeyCode(),
		// event);
		// }
		return super.dispatchKeyEvent(event);
	}
}
