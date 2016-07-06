package com.dounine.fasthttp;

import com.dounine.fasthttp.medias.IMedia;
import com.dounine.fasthttp.rep.Response;
import com.dounine.fasthttp.types.HeartType;

import java.util.List;

/**
 * Created by huanghuanlai on 16/7/6.
 */
public interface IRransmission {

    void setFlashLight(IFlashLight flashLight);

    void heartbeat(HeartType heartType);

    void push(HeartType heartType,IMedia media);

    void push(HeartType heartType,List<IMedia> medias);

    Response response();
}
