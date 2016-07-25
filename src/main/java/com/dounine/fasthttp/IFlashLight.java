package com.dounine.fasthttp;

import com.dounine.fasthttp.medias.IMedia;
import com.dounine.fasthttp.rep.Response;
import com.dounine.fasthttp.types.HeartType;

import java.util.List;

/**
 * Created by huanghuanlai on 16/7/6.
 */
public interface IFlashLight {

    void on();

    void off();

    void emit();

    void emit(HeartType heartType);

    void ready(IMedia media);

    void ready(List<IMedia> medias);

    void irising();

    ICoordinate coordinate();

    Response response();
}
