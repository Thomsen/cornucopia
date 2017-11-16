package com.cornucoppia.component.data.persist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.cornucoppia.component.data.model.User;

/**
 * Created by thom on 25/5/2017.
 */
@Database(entities = {User.class}, version=1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
