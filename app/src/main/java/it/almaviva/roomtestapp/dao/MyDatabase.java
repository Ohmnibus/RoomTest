package it.almaviva.roomtestapp.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import it.almaviva.roomtestapp.entity.Message;

/**
 * Created by m.spalletti on 03/01/2018.
 */
@Database(entities = {Message.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {

	public abstract MessageDao messageDao();

	private static MyDatabase mInstance = null;

	public static synchronized MyDatabase getInstance(Context ctx) {
		if (mInstance == null) {
			mInstance = Room.databaseBuilder(ctx.getApplicationContext(), MyDatabase.class, "myDatabase").build();
		}
		return mInstance;
	}
}
