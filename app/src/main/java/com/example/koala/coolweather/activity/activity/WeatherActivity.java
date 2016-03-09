package com.example.koala.coolweather.activity.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.koala.coolweather.R;
import com.example.koala.coolweather.activity.util.HttpCallbackListener;
import com.example.koala.coolweather.activity.util.HttpUtil;
import com.example.koala.coolweather.activity.util.Utility;

import java.io.InputStream;


/**
 * Created by LX on 2016/3/9.
 */
public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout weatherInfoLayout;

    private TextView cityNameText;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;

    private Button switchCity;
    private Button refreshWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);

        weatherInfoLayout = (LinearLayout)findViewById(R.id.weather_info_layout);
        cityNameText = (TextView)findViewById(R.id.city_name);
        publishText = (TextView)findViewById(R.id.publish_text);
        weatherDespText = (TextView)findViewById(R.id.weather_desp);
        temp1Text = (TextView)findViewById(R.id.temp1);
        temp2Text = (TextView)findViewById(R.id.temp2);
        currentDateText = (TextView)findViewById(R.id.current_date);

        switchCity = (Button)findViewById(R.id.switch_city);
        refreshWeather = (Button)findViewById(R.id.refresh_weather);
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);

        String countyCode = getIntent().getStringExtra("county_code");

        //如果有县级代号则查询天气
        if (!TextUtils.isEmpty(countyCode)){
            publishText.setText("同步中... ...");
            weatherInfoLayout.setVisibility(View.VISIBLE);
            cityNameText.setVisibility(View.VISIBLE);
            queryWeatherCode(countyCode);
        }else {
            //如果没有代号则显示本地天气
            showWeather();
        }
    }

    //根据县级代号查询天气代号
    private void queryWeatherCode(String countyCode){
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address, "countyCode");     //此方法与ChooseAreaActivity中的方法同名

    }

    //根据天气代号查询天气信息
    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address, "weatherCode");     //此方法与ChooseAreaActivity中的方法同名
    }

    private void queryFromServer(final String address, final String type){

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

                //从服务器返回结果response中解析天气代号
                if ("countyCode".equals(type)) {
                    String[] array = response.split("\\|");
                    if (array != null && array.length == 2) {
                        String weatherCode = array[1];
                        queryWeatherInfo(weatherCode);
                    }
                } else if ("weatherCode".equals(type)) {        //从服务器返回结果response中解析天气信息
                    Utility.handleWeatherResponse(WeatherActivity.this, response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                    }
                });

            }
        });
    }


    private void showWeather(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(preferences.getString("city_name", ""));
        temp1Text.setText(preferences.getString("temp1", ""));
        temp2Text.setText(preferences.getString("temp2", ""));
        weatherDespText.setText(preferences.getString("weather_desp", ""));
        publishText.setText("今天" + preferences.getString("publish_time", "") + "发布");
        currentDateText.setText(preferences.getString("current_date", ""));
        weatherInfoLayout.setVisibility(View.VISIBLE);
        cityNameText.setVisibility(View.VISIBLE);
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishText.setText("同步中...");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = preferences.getString("weather_code", "");
                if (!TextUtils.isEmpty(weatherCode)){
                    queryWeatherInfo(weatherCode);
                }
                break;
            default:
                break;
        }
    }
}
