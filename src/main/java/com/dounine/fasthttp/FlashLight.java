package com.dounine.fasthttp;

import com.dounine.fasthttp.medias.*;
import com.dounine.fasthttp.rep.Response;
import com.dounine.fasthttp.types.HeartType;

import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by huanghuanlai on 16/7/6.
 * 手电筒
 */
public class FlashLight implements IFlashLight{

    protected ICoordinate coordinate;

    protected IRransmission rransmission;

    public FlashLight(ICoordinate coordinate){
        setCoordinate(coordinate);
    }

    public FlashLight(){
    }

    public void setCoordinate(ICoordinate coordinate){
        this.coordinate =coordinate;
    }

    /**
     * on of flashlight
     */
    @Override
    public void on(){
        coordinate.init();
    }

    /**
     * off of flashlight
     */
    @Override
    public void off(){

        URLConnection connection = coordinate.point();
        if(connection instanceof HttpURLConnection){
            ((HttpURLConnection)connection).disconnect();
        }
    }

    @Override
    public void emit() {

    }

    /**
     * emit the Media
     */
    @Override
    public void emit(IMedia media){
        URLConnection connection = coordinate.point();
        rransmission = new Rransmission(this);
        rransmission.push(HeartType.GET,media);
    }

    public void emit(HeartType heartType,IMedia media){
        URLConnection connection = coordinate.point();
        rransmission = new Rransmission(this);
        rransmission.push(heartType,media);
    }

    @Override
    public void emits(List<IMedia> medias) {
        rransmission = new Rransmission(this);
        rransmission.push(HeartType.GET,medias);
    }

    public void emits(HeartType heartType,List<IMedia> medias) {
        rransmission = new Rransmission(this);
        rransmission.push(heartType,medias);
    }

    @Override
    public void irising() {

    }

    @Override
    public ICoordinate coordinate() {
        return coordinate;
    }

    @Override
    public Response response() {
        return rransmission.response();
    }

}
