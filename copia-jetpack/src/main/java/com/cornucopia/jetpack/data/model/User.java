package com.cornucopia.jetpack.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

/**
 * Created by thom on 25/5/2017.
 */
@Entity(tableName =  "users", indices = {@Index("first_name"), @Index("last_name")})
public class User {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "first_name")
    private String name;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @Embedded
    public Address address;

    @Ignore
    private Bitmap picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
