package com.cornucopia.jetpack.data.persist;

import com.cornucopia.jetpack.data.model.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by thom on 25/5/2017.
 */
@Database(entities = {User.class}, version=1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
