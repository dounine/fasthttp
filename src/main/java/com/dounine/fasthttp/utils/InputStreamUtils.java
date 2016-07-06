package com.dounine.fasthttp.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huanghuanlai on 16/7/6.
 */
public final class InputStreamUtils {

    private InputStreamUtils(){}

    public static String istreamToString(InputStream is,int len){
        byte[] bytes = new byte[len];
        try {
            is.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(bytes,0,len);
    }

    public static String istreamToString(InputStream is){
        StringBuilder strs = new StringBuilder();
        byte[] bytes = new byte[1024];
        int len = 0;
        try {
            len = is.read(bytes);
            while(-1!=len){
                strs.append(new String(bytes,0,len));
                len = is.read(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strs.toString();
    }
}
