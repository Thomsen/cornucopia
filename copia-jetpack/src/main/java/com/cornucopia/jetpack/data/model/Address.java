package com.cornucopia.jetpack.data.model;


import androidx.room.ColumnInfo;

/**
 * Created by thom on 26/5/2017.
 */

public class Address {

    public String street;

    public String state;

    public String city;

    @ColumnInfo(name = "post_code")
    public int postCode;
}
