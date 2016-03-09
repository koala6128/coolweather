package com.example.koala.coolweather.activity.model;

/**
 * Created by koala on 2016/3/6.
 */
public class Province {

    private int id;     //province在表中的编号
    private String provinceName;        //省名
    private String provinceCode;        //省代号

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getProvinceName(){
        return provinceName;
    }

    public void setProvinceName(String provinceName){
        this.provinceName = provinceName;
    }

    public String getProvinceCode(){
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode){
        this.provinceCode = provinceCode;
    }

}
