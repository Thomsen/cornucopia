package com.cornucopia.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class StringUtils {

    private StringUtils() {};
    
    /**
     * 对象转成字符串
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
    	return (null == obj ? "" : String.valueOf(obj));
    }
    
    public static String convertStreamToString(InputStream inStream) {
        String result = "result";

        try {
            InputStreamReader inReader = new InputStreamReader(inStream,
                    "UTF-8");
            BufferedReader bufferReader = new BufferedReader(inReader);
            StringBuffer strBuffer = new StringBuffer();
            String s;
            while ((s = bufferReader.readLine()) != null) {
                strBuffer.append(s);
            }

            result = strBuffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
