package com.baiduvoice.httpfun;

/**
 * Created by lihui on 2017-02-24.
 */
import java.io.*;
import java.net.URLDecoder;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


public class HttpRequestUtils {
    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam){
        return httpPost(url, jsonParam, false);
    }

    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url,JSONObject jsonParam, boolean noNeedResponse)  {
        //post请求返回结果
        HttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);

        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(),"utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
                System.out.println(jsonParam.toString());
            }

            HttpResponse result = httpClient.execute(method);

            url = URLDecoder.decode(url, "UTF-8");

            System.out.println("post请求[" + result.getStatusLine().getStatusCode() + "]");
            String str1 = EntityUtils.toString(result.getEntity());
            System.out.println(str1);
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    //str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = new JSONObject(str);
                    System.out.println(str);
                } catch (Exception e) {
                    //Log.e(TAG,"post请求提交失败:" + url, e);
                    e.printStackTrace();
                    //System.out.println("post请求提交失败:" + url);
                }
            }
        } catch (IOException e) {
            //Log("post请求提交失败:" + url, e);
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * post请求
     * @param url         url地址
     * @param strParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPostVoiceGen(String url,String strParam, boolean noNeedResponse) {
        //post请求返回结果
        HttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonResult = null;
        Header[] headers = null;

        try {
            url = URLDecoder.decode(url, "UTF-8");
            HttpPost method = new HttpPost(url);
            if (null != strParam) {
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                method.setEntity(entity);
            }

            HttpResponse result = httpClient.execute(method);


            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {

                String str;
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    if (noNeedResponse) {
                        return null;
                    }
                    headers = result.getAllHeaders();
                    for(Header h:headers){

                        //System.out.println(h.getName()+":"+h.getValue());
                        if(h.getName().equals("Content-type")) {

                            if(h.getValue().equals("audio/mp3")) {
                                jsonResult = new JSONObject();
                                jsonResult.put("succ", "path");
                                //System.out.println(jsonResult);
                            }
                            else {
                                str = EntityUtils.toString(result.getEntity());
                                /**把json字符串转换成json对象**/
                                jsonResult = new JSONObject(str);
                                //System.out.println(str);
                            }

                        }
                    }
                } catch (Exception e) {
                    //Log.e(TAG,"post请求提交失败:" + url, e);
                    e.printStackTrace();
                    //System.out.println("post请求提交失败:" + url);
                }
            }
        } catch (IOException e) {
            //Log("post请求提交失败:" + url, e);
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * post请求
     * @param url         url地址
     * @param strParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static JSONObject httpPost(String url,String strParam, boolean noNeedResponse){
        //post请求返回结果
        HttpClient httpClient = HttpClientBuilder.create().build();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != strParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                method.setEntity(entity);
                //System.out.println(strParam);
            }

            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            //System.out.println("post请求[" + result.getStatusLine().getStatusCode() + "]");
            //tring str1 = EntityUtils.toString(result.getEntity());
            //System.out.println(str1);
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    jsonResult = new JSONObject(str);
                    System.out.println(str);
                } catch (Exception e) {
                    //Log.e(TAG,"post请求提交失败:" + url, e);
                    e.printStackTrace();
                    //System.out.println("post请求提交失败:" + url);
                }
            }
        } catch (IOException e) {
            //Log("post请求提交失败:" + url, e);
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static JSONObject httpGet(String url){
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = new JSONObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                //logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            //logger.error("get请求提交失败:" + url, e);
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonResult;
    }
}