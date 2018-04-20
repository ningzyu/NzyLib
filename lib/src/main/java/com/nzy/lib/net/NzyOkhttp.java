package com.nzy.lib.net;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 作者：宁震宇on 2018/4/19.
 * 邮箱：348723352@qq.com
 * 本类作用：
 */

public class NzyOkhttp {
    //获取post携带的参数
    public static RequestBody getBody(Map<String, String> params) {
        FormBody.Builder fb = new FormBody.Builder();
        for (Param param : getParams(params)) {
            fb.add(param.key, param.value);
        }
        return fb.build();
    }

    /**
     * 将请求结果集绑定消息发送到当前Handler
     *
     * @param result
     * @param what
     * @param handler
     */
    private static void SendMsg(String result, int what, Handler handler) {
        Message msg = new Message();//消息类
        msg.what = what;//标记要传的线程编号
        msg.obj = result;
        handler.sendMessage(msg);
//        Bundle b = new Bundle();
//        b.putString("MSG", strInfo);
//        msg.setData(b);
    }

    //同步post请求
    private static String post1(String url, RequestBody body) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.
                Builder().
                post(body).
                url(url)
                .build();
        //.enqueue(new Callback(){});
        okhttp3.Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("哎，出错了" + response);
        }
    }

    //异步post请求
    private static okhttp3.Call getHttpCall(String url, RequestBody body) {
        //1. 构造OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //2 构建一个Request
        //2.2 构建一个builder
        //2.3 完成Request的构建
        Request request = new Request.Builder().post(body).url(url).build();
        //3 获得Call对象
        okhttp3.Call call = client.newCall(request);
        return call;
    }

    /**
     * 参数类
     */
    private static class Param {
        public Param() {
        }
        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
        String key;
        String value;
    }

    /**
     * 将map集合转换成参数实体
     *
     * @param params
     * @return
     */
    private static Param[] getParams(Map<String, String> params) {
        if (params == null) return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }



}
