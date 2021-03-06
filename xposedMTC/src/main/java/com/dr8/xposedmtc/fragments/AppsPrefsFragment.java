package com.dr8.xposedmtc.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceFragment;

import com.dr8.xposedmtc.R;

public class AppsPrefsFragment extends PreferenceFragment {

	private ProgressDialog pd;
    static Runnable myRunnable;
    private static Handler myHandler;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
        Context ctx = getActivity();

        myRunnable = new Runnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
                addPreferencesFromResource(R.xml.prefs_apps);
            }
        };

        myHandler = new Handler();

        AsyncTask<Void, Void, Void> doAppsList = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                myHandler.post(myRunnable);
                myRunnable.run();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                pd.dismiss();
            }
        };

        pd = ProgressDialog.show(ctx, getResources().getString(R.string.loading), getResources().getString(R.string.pleasewait), true, false);
        doAppsList.execute((Void[])null);

    }



}
