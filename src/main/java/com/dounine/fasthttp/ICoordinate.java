package com.dounine.fasthttp;

import sun.net.www.protocol.http.HttpURLConnection;

import java.net.URLConnection;

/**
 * Created by huanghuanlai on 16/7/6.
 */
public interface ICoordinate {

    void init();

    void data(String data);

    URLConnection point();

    HttpURLConnection httpPoint();

}
