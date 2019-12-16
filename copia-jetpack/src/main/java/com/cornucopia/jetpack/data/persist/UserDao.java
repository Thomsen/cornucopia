package com.cornucopia.jetpack.data.persist;

import androidx.lifecycle.LiveData;


import com.cornucopia.jetpack.data.model.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

/**
 * Created by thom on 25/5/2017.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(User user);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("select * from users where id= :userId")
    LiveData<User> load(String userId);

    @Query("select * from users")
    User[] loadAllUsers();

    @Query("select * from users where first_name = :name ")
    List<User> findUsersByName(String name);

    // rxjava2
    @Query("select * from users where id= :id limit 1")
    Flowable<User> loadUserById(int id);


//
//    boolean hasUser(int freshTimeout);
}
