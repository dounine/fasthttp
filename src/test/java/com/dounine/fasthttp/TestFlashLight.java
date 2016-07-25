package com.dounine.fasthttp;

import com.dounine.fasthttp.medias.*;
import com.dounine.fasthttp.types.HeartType;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huanghuanlai on 16/7/6.
 */
public class TestFlashLight {

    @Test
    public void testGetMedia() {
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        IMedia media = new Media();
        media.setName("name");
        media.setValue("lake");
        flashLight.ready(media);
        flashLight.emit();
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testGetMedias() {
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name", "我是中文"));
        getMediaList.add(new Media("name1", "lake"));
        getMediaList.add(new Media("name2", "lake"));
        flashLight.ready(getMediaList);
        flashLight.emit();
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testPostMedias() {
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name", "我是中文"));
        getMediaList.add(new Media("name1", "lake"));
        getMediaList.add(new Media("name2", "lake"));
        flashLight.ready(getMediaList);
        flashLight.emit(HeartType.POST);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testPutMedias() {
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name", "我是中文"));
        getMediaList.add(new Media("name1", "lake"));
        getMediaList.add(new Media("name2", "lake"));
        flashLight.ready(getMediaList);
        flashLight.emit(HeartType.PUT);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testPatchMedias() {
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name", "我是中文"));
        getMediaList.add(new Media("name1", "lake"));
        getMediaList.add(new Media("name2", "lake"));
        flashLight.ready(getMediaList);
        flashLight.emit(HeartType.PATCH);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testDeleteMedias() {
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/test"));
        flashLight.on();//打开
        List<IMedia> getMediaList = new ArrayList<>();
        getMediaList.add(new Media("name", "我是中文"));
        getMediaList.add(new Media("name1", "lake"));
        getMediaList.add(new Media("name2", "lake"));
        flashLight.ready(getMediaList);
        flashLight.emit(HeartType.DELETE);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
    }

    @Test
    public void testFileMedias() {
        long begin = System.currentTimeMillis();
        IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file/u"));
        flashLight.on();//打开
        flashLight.ready(new MediaFile(new File("/Users/huanghuanlai/Desktop/b.java")));
        flashLight.emit(HeartType.FILE);
        System.out.println(flashLight.response().toString());
        flashLight.off();//关闭
        System.out.println(System.currentTimeMillis() - begin);
    }

    @Test
    public void testFindToken() {
        String tokenS = "{code:200,text:\"{\"data\":{\"avgShar\":1048576,\"createTime\":\"2016-07-08T14:01:38.119\",\"maxShar\":38,\"name\":\"CodeRunner 2.1.1.zip\",\"shars\":[],\"size\":39788544,\"token\":\"1a685f99bc394ab8bb09b92a94af98a0\",\"updateTime\":\"2016-07-08T14:01:38.119\"},\"errno\":0}\"}";
        Pattern pattern = Pattern.compile("\"token\":\"\\d{32}\"");
        Matcher matcher = pattern.matcher(tokenS);
        if (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    @Test
    public void testSharFileMedias() {
        File uploadFile = new File("/Users/huanghuanlai/Desktop/b2b2c.zip");

        IFlashLight flashLightToken = new FlashLight(new Coordinate("http://localhost:8081/file/token"));
        flashLightToken.on();
        List<IMedia> tokenMedias = new ArrayList<>();
        tokenMedias.add(new Media("name", uploadFile.getName()));
        tokenMedias.add(new Media("size", uploadFile.length()));
        flashLightToken.ready(tokenMedias);
        flashLightToken.emit(HeartType.POST);
        String responseText = flashLightToken.response().toString();
        flashLightToken.off();
        Pattern tokenPattern = Pattern.compile("\"token\":\"[a-z0-9]{32}\"");
        Pattern avgSharPattern = Pattern.compile("\"avgShar\":\\d+");
        Matcher matcherToken = tokenPattern.matcher(responseText);
        Matcher matcherAvgShar = avgSharPattern.matcher(responseText);
        if (matcherToken.find()&&matcherAvgShar.find()) {
            String token = matcherToken.group().split("\"")[3];
            String avgShar = matcherAvgShar.group().split(":")[1];
            IMediaSharFile mediaSharFile = new MediaSharFile(uploadFile);
            mediaSharFile.setSharSize(Long.parseLong(avgShar));
            mediaSharFile.setToken(token);
            IFlashLight flashLight = new FlashLight(new Coordinate("http://localhost:8081/file"));
            flashLight.on();//打开
            flashLight.ready(mediaSharFile);
            flashLight.emit(HeartType.SHARE_FILE);
            flashLight.off();//关闭
        }

    }
}
