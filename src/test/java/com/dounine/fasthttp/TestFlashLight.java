package com.dounine.fasthttp;

import com.dounine.fasthttp.medias.IMedia;
import com.dounine.fasthttp.medias.Media;
import com.dounine.fasthttp.medias.MediaFile;
import com.dounine.fasthttp.types.HeartType;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huanghuanlai on 16/7/6.
 */
public class TestFlashLight {

    @Test
    public void testGetMedia(){
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        IMedia media = new Media();
        media.setName("name");
        media.setValue("lake");
        flashLight.emit(media);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testGetMedias(){
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name","我是中文"));
        getMediaList.add(new Media("name1","lake"));
        getMediaList.add(new Media("name2","lake"));
        flashLight.emits(getMediaList);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testPostMedias(){
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name","我是中文"));
        getMediaList.add(new Media("name1","lake"));
        getMediaList.add(new Media("name2","lake"));
        flashLight.emits(HeartType.POST,getMediaList);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testPutMedias(){
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name","我是中文"));
        getMediaList.add(new Media("name1","lake"));
        getMediaList.add(new Media("name2","lake"));
        flashLight.emits(HeartType.PUT,getMediaList);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testPatchMedias(){
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name","我是中文"));
        getMediaList.add(new Media("name1","lake"));
        getMediaList.add(new Media("name2","lake"));
        flashLight.emits(HeartType.PATCH,getMediaList);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testDeleteMedias(){
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name","我是中文"));
        getMediaList.add(new Media("name1","lake"));
        getMediaList.add(new Media("name2","lake"));
        flashLight.emits(HeartType.DELETE,getMediaList);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testFileMedias(){
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/u"));
        flashLight.on();//打开
        flashLight.emit(HeartType.FILE,new MediaFile(new File("/Users/huanghuanlai/Desktop/b.java")));
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }
}
