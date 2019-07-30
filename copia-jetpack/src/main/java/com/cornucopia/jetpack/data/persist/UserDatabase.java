package com.cornucopia.jetpack.data.persist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cornucopia.jetpack.data.model.User;

/**
 * Created by thom on 25/5/2017.
 */
@Database(entities = {User.class}, version=1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
