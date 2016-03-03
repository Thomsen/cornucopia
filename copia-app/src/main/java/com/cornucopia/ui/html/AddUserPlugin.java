package com.cornucopia.ui.html;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddUserPlugin {

    public static final int GET_USERS = 0x232;

    private Handler mHandler;

    public AddUserPlugin(Handler handler) {
        this.mHandler = handler;
    }

    public void getUsers() {
        List<UserInfo> uList = getUserList();

        // 转化为json格式数据 [{name, sex, date, contact}, {name, sex, date,
        // contact}]

        JSONArray jsonArray = new JSONArray();

        for (UserInfo uInfo : uList) {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("name", uInfo.getName());
                jsonObject.put("sex", uInfo.getSex());
                jsonObject.put("date", uInfo.getDate());
                jsonObject.put("contact", uInfo.getContact());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonArray.put(jsonObject);

        }

        String urlParam = "javascript:addUserShow('" + jsonArray.toString() + "')";
        Message msg = mHandler.obtainMessage();
        msg.obj = urlParam;
        msg.what = GET_USERS;
//        msg.setTarget(mHandler);
        mHandler.sendMessage(msg);

    }

    private List<UserInfo> getUserList() {
        List<UserInfo> userList = new ArrayList<UserInfo>();

        userList.add(new UserInfo("lulu", "female", "00/08/2000",
                "lulu@gmail.com"));
        userList.add(new UserInfo("thom", "male", "01/12/1900",
                "oss@gmail.com"));

        return userList;

    }
}