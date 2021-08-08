package com.idax.openvision.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.idax.openvision.Entity.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static AppDatabase instance = null;

    public static AppDatabase getAppDataBase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "open-vision.db").allowMainThreadQueries().addCallback(new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                }
            }).build();
        }
        return instance;
    }

    public abstract UserDao userDao();
}
/*
@Database(entities = {Obj.class, Etc.class}, version = 1, exportSchema = false)
	public abstract class AppDataBase extends RoomDatabase {
		public static AppDataBase instance = null;
		public static AppDataBase getAppDataBase(Context context) {
			if (instance == null) {
				instance = Room.databaseBuilder(context, AppDataBase.class, "inventory.db").allowMainThreadQueries().addCallback(new RoomDatabase.Callback() {
					@Override
					public void onCreate(@NonNull SupportSQLiteDatabase db) {
						super.onCreate(db);
					}
				}).build();
			}
			return instance;
		}
 */