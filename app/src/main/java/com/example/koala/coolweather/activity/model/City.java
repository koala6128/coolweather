package com.example.koala.coolweather.activity.model;

/**
 * Created by koala on 2016/3/6.
 */
public class City {

    private int id;     //city在表中的编号
    private String cityName;    //city名称
    private String cityCode;    //city代号
    private int provinceId;     //city对应省的编号

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCityName(){
        return cityName;
    }

    public void setCityName(String cityName){
        this.cityName = cityName;
    }

    public String getCityCode(){
        return cityCode;
    }

    public void setCityCode(String cityCode){
        this.cityCode = cityCode;
    }

    public int  getProvinceId(){
        return provinceId;
    }

    public void setProvinceId(int provinceId){
        this.provinceId = provinceId;
    }
}
