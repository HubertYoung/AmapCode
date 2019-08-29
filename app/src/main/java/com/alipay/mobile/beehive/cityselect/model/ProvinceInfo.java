package com.alipay.mobile.beehive.cityselect.model;

import java.util.ArrayList;
import java.util.List;

public class ProvinceInfo extends ProvinceCityInfo {
    private String ProvinceName;
    private List<CityInfo> citys = new ArrayList();

    public String getProvinceName() {
        return this.ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        this.ProvinceName = provinceName;
    }

    public void addCity(CityInfo info) {
        this.citys.add(info);
    }

    public List<CityInfo> getCitys() {
        return this.citys;
    }

    public void setCitys(List<CityInfo> citys2) {
        this.citys = citys2;
    }
}
