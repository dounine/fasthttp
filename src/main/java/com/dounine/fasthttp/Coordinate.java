package com.dounine.fasthttp;

import sun.net.www.protocol.http.HttpURLConnection;

import java.io.IOException;
import java.net.*;

/**
 * Created by huanghuanlai on 16/7/6.
 * 坐标
 */
public class Coordinate implements ICoordinate{

    /**
     * address
     */
    protected String uri;
    protected URL url;
    protected URLConnection connection;

    public Coordinate(String uri){
        this.uri = uri;
    }
    public Coordinate(StringBuffer uri){
        this.uri = uri.toString();
    }

    @Override
    public void init() {
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void data(String data) {
        try {
            url = new URL(uri+data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public URLConnection point() {
        if(null!=connection){
            return connection;
        }
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP,new InetSocketAddress("localhost",8080));
            connection = url.openConnection(proxy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public HttpURLConnection httpPoint() {
        return (HttpURLConnection)point();
    }
}
