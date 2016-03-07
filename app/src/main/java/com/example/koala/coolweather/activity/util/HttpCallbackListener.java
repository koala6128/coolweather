package com.example.koala.coolweather.activity.util;

/**
 * Created by koala on 2016/3/7.
 */
public interface HttpCallbackListener {

    void onFinish(String response);
    void onError(Exception e);
}
