package com.example.koala.coolweather.activity.model;

/**
 * Created by koala on 2016/3/6.
 */
public class County {

    private int id;     //county在表中的编号
    private String countyName;      //县名
    private String countyCode;      //县代号
    private int cityId;     //所属市的代号

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCountyName(){
        return countyName;
    }

    public void setCountyName(String countyName){
        this.countyName = countyName;
    }

    public String getCountyCode(){
        return countyCode;
    }

    public void setCountyCode(String countyCode){
        this.countyCode = countyCode;
    }

    public int  getCityId(){
        return cityId;
    }

    public void setCityId(int cityId){
        this.cityId = cityId;
    }
}
