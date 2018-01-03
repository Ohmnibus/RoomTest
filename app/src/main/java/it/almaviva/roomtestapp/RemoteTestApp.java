package it.almaviva.roomtestapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import it.almaviva.roomtestapp.dao.MyDatabase;
import it.almaviva.roomtestapp.utils.MessageUtil;

/**
 * Created by m.spalletti on 03/01/2018.
 */

public class RemoteTestApp extends Application {
	public static final String KEY_IS_DB_POPULATED = "DB_IS_POPULATED";
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		initContext();
		intiDB();
	}

	private void initContext() {
		context = this;
	}

	private void intiDB() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if (!preferences.getBoolean(KEY_IS_DB_POPULATED, false)) {
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... voids) {
					for (int i = 0; i < 50; i++) {
						MyDatabase.getInstance(context).messageDao().insert(MessageUtil.getRandom());
					}
					return null;
				}
			}.execute();
			preferences.edit().putBoolean(KEY_IS_DB_POPULATED, true).apply();
		} else {
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... voids) {
					//for (int i = 0; i < 50; i++) {
						int asd = MyDatabase.getInstance(context).messageDao().countAll();
						asd = asd + 1;
					//}
					return null;
				}
			}.execute();
		}
	}

	public static Context getContext() {
		return context;
	}
}
