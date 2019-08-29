package com.autonavi.sync.beans;

public class City {
    public static final String JSON_FIELD_POI_CITY_CODE = "city_code";
    public static final String JSON_FIELD_POI_CITY_NAME = "city_name";
    public String cityCode;
    public String cityName;

    public City(String str, String str2) {
        this.cityCode = str;
        this.cityName = str2;
    }
}
