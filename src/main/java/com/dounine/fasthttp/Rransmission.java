package com.dounine.fasthttp;

import com.dounine.fasthttp.medias.IMedia;
import com.dounine.fasthttp.medias.MediaFile;
import com.dounine.fasthttp.rep.Response;
import com.dounine.fasthttp.types.HeartType;
import com.dounine.fasthttp.utils.FileUtils;
import com.dounine.fasthttp.utils.InputStreamUtils;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.*;
import java.net.URLConnection;
import java.util.List;

import static com.dounine.fasthttp.types.HeartType.PUT;

/**
 * Created by huanghuaai on 16/7/6.
 * 传送数据
 */
public class Rransmission implements IRransmission {

    protected IFlashLight flashLight;

    protected ICoordinate coordinate;

    protected IMedia media;

    protected List<IMedia> medias;

    protected Response response;

    public Rransmission(IFlashLight flashLight){
        setFlashLight(flashLight);
    }

    @Override
    public void setFlashLight(IFlashLight flashLight) {
        this.flashLight = flashLight;
    }


    @Override
    public void heartbeat(final HeartType heartType) {
        coordinate = flashLight.coordinate();
        switch (heartType){
            case GET:
                getHeartbeat();break;
            case POST:
                postHeartbeat();break;
            case PUT:
                putHeartbeat();break;
            case PATCH:
                patchHeartbeat();break;
            case DELETE:
                deleteHeartbeat();break;
            case FILE:
                fileHeartbeat();break;
            case OPTIONS:
                optionsHeartbeat();break;
        }
    }

    @Override
    public void push(HeartType heartType,IMedia media) {
        this.media = media;
        heartbeat(heartType);
    }

    @Override
    public void push(HeartType heartType,List<IMedia> medias) {
        this.medias = medias;
        heartbeat(heartType);
    }

    @Override
    public Response response() {
        if(null==response){
            HttpURLConnection httpURLConnection = coordinate.httpPoint();
            response = new Response();
            try {
                InputStreamUtils.istreamToString(httpURLConnection.getInputStream());
                response.setCode(httpURLConnection.getResponseCode());
            }catch (FileNotFoundException enfe){
                response.setCode(404);
                response.setText("request url can't find it.");
                enfe.printStackTrace();
            } catch (IOException e) {
                response.setCode(-1);
                response.setText(e.getMessage());
                e.printStackTrace();
            }
        }
        return response;
    }

    private StringBuilder dataJoin(StringBuilder sb){
        IMedia med = (IMedia) media;
        String frontStr = "";
        if(null!=media){
            sb.append(med.getName());
            sb.append("=");
            sb.append(med.getValue());
            frontStr = "&";
        }
        if(null!=medias){
            for(IMedia im : medias){
                sb.append(frontStr);
                sb.append(im.getName());
                sb.append("=");
                sb.append(im.getValue());
                frontStr = "&";
            }
        }
        return sb;
    }

    public void getHeartbeat(){
        StringBuilder sb = new StringBuilder("?");
        dataJoin(sb);
        coordinate.data(sb.toString());
        URLConnection connection = coordinate.point();
        try {
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.connect();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postHeartbeat(){
        StringBuilder sb = new StringBuilder();
        dataJoin(sb);
        URLConnection connection = coordinate.point();
        try {
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(sb.toString().getBytes());
            os.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void putHeartbeat(){
        StringBuilder sb = new StringBuilder();
        dataJoin(sb);
        HttpURLConnection connection = coordinate.httpPoint();
        try {
            connection.setRequestMethod(PUT.toString());
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(sb.toString().getBytes());
            os.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void patchHeartbeat(){
        StringBuilder sb = new StringBuilder();
        dataJoin(sb);
        HttpURLConnection connection = coordinate.httpPoint();
        try {
            connection.setRequestMethod(HeartType.PATCH.toString());
            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(sb.toString().getBytes());
            os.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteHeartbeat(){
        StringBuilder sb = new StringBuilder();
        dataJoin(sb);
        coordinate.data(sb.toString());
        HttpURLConnection connection = coordinate.httpPoint();
        try {
            connection.setRequestMethod(HeartType.DELETE.toString());
            connection.connect();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileHeartbeat(){
        final URLConnection connection = coordinate.point();
        final MediaFile mediaFile = (MediaFile) media;
        final String boundary = Long.toHexString(System.currentTimeMillis());
        final String CRLF = "\r\n";
        final String split = "------";
        try {
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=----" + boundary);
            connection.setDoOutput(true);
            OutputStream output = connection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(output));
            writer.append(split);
            writer.append(boundary).append(CRLF);
            writer.append("Content-Disposition:form-data;name=\"data\";filename=\"" + mediaFile.getFile().getName() + "\"").append(CRLF);
            writer.append("Content-Type:application/octet-stream").append(CRLF);
            writer.append(CRLF).append(CRLF);
            writer.flush();

            FileUtils.writeFileByOutput(mediaFile.getFile(),output);

            writer.append(CRLF);
            writer.append(split);
            writer.append(boundary);
            writer.append("--");
            writer.flush();
            java.net.HttpURLConnection httpURLConnection = ((java.net.HttpURLConnection) connection);
            int responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void optionsHeartbeat(){
        StringBuilder sb = new StringBuilder();
        dataJoin(sb);
        coordinate.data(sb.toString());
        HttpURLConnection connection = coordinate.httpPoint();
        try {
            connection.setRequestMethod(HeartType.OPTIONS.toString());
            connection.connect();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
