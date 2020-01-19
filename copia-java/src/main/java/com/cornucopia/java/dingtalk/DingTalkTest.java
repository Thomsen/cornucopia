package com.cornucopia.java.dingtalk;

import com.cornucopia.java.http.OkHttpOpt;

public class DingTalkTest {

    public static void main(String[] args) {
        getAccessToken();
        getUserId();
    }

    private static void getAccessToken() {
        String corpId = "dingmzqn88qzvlzzgutd";
        String cropSecret = "VtVF5ENbcuQvA5ZXDVGiB3xD2mnpJqOHjfF_LBCg0j5U0qmmqETEAx0HlykAwNn4";
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://oapi.dingtalk.com/gettoken?corpid=");
        buffer.append(corpId);
        buffer.append("&corpsecret=");
        buffer.append(cropSecret);
        String url = buffer.toString();

        OkHttpOpt httpOpt = new OkHttpOpt();
        String result = httpOpt.okHttpGet(url);
        System.out.println(result);  //  14aeaa62f19f3799a1a3aba4e47f8899
    }

    private static void getUser() {
//        https://oapi.dingtalk.com/user/get_by_mobile?access_token=ACCESS_TOKEN&mobile=1589586456

        String accessToken = "14aeaa62f19f3799a1a3aba4e47f8899";
        String mobile = "15895864562";
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://oapi.dingtalk.com/user/get_by_mobile?access_token=");
        buffer.append(accessToken);
        buffer.append("&mobile=");
        buffer.append(mobile);
        String url = buffer.toString();

        OkHttpOpt httpOpt = new OkHttpOpt();
        String result = httpOpt.okHttpGet(url);
        System.out.println(result);
    }

    private static void getUserId() {
        String accessToken = "14aeaa62f19f3799a1a3aba4e47f8899";
        String code = "15895864562";
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://oapi.dingtalk.com/user/getuserinfo?access_token=");
        buffer.append(accessToken);
        buffer.append("&code=");
        buffer.append(code);
        String url = buffer.toString();

        OkHttpOpt httpOpt = new OkHttpOpt();
        String result = httpOpt.okHttpGet(url);
        System.out.println(result);
    }

}
