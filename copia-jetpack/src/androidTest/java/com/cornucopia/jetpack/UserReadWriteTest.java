package com.cornucopia.jetpack;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cornucopia.jetpack.data.model.User;
import com.cornucopia.jetpack.data.persist.UserDao;
import com.cornucopia.jetpack.data.persist.UserDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by thom on 26/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class UserReadWriteTest {

    private UserDao userDao;

    private UserDatabase db;

    @Before
    public void createDB() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        userDao = db.userDao();
    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        User user = new User();
        user.setName("thom");
        user.setLastName("wd");

        userDao.insertAll(user);

        List<User> byName = userDao.findUsersByName(user.getName());

        assertThat(byName.get(0).getName(), equalTo(user.getName()));
    }
}
