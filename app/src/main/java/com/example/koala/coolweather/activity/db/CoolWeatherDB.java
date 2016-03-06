package com.example.koala.coolweather.activity.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.koala.coolweather.activity.model.City;
import com.example.koala.coolweather.activity.model.County;
import com.example.koala.coolweather.activity.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koala on 2016/3/6.
 */
public class CoolWeatherDB {

    public static final String DB_NAME = "cool_weather";
    public static final int VERSION = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    //构造方法私有化
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    //获取CoolWeatherDB的实例
    public synchronized static CoolWeatherDB getInstance(Context context){
        if (coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    //将province实例存储到数据库
    public void saveProvince(Province province){
        if (province != null){
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            db.insert("Province", null, values);
        }
    }

    //遍历数据库中所有province数据
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToFirst());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    //将city实例存储到数据库
    public void saveCity(City city){
        if (city != null){
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("province_id", city.getProvinceId());
            db.insert("City", null, values);
        }
    }

    //遍历数据库中某province下所有City数据
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "province_id = ?",
                new String[]{String.valueOf(provinceId)}, null, null, null);
        if (cursor.moveToFirst()){
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("provinceId")));
                list.add(city);
            }while (cursor.moveToNext());
        }

        if (cursor != null){
            cursor.close();
        }
        return list;
    }

    //存储County实例
    public void saveCounty(County county){
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_code", county.getCountyCode());
            values.put("county_name", county.getCountyName());
            values.put("cityId", county.getCityId());
            db.insert("County", null, values);
        }
    }

    //遍历数据库中某city下所有County
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)},
                null, null, null);
        if (cursor.moveToFirst()){
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("city_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("city_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("cityId")));
                list.add(county);
            }while (cursor.moveToNext());
        }

        if (cursor != null){
            cursor.close();
        }
        return list;
    }

}