package com.alipay.mobile.beehive.cityselect.model;

import java.util.ArrayList;
import java.util.List;

public class CountryInfo extends ProvinceCityInfo {
    private String countryName;
    private List<ProvinceInfo> provinces = new ArrayList();

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName2) {
        this.countryName = countryName2;
    }

    public void addProvinces(ProvinceInfo info) {
        this.provinces.add(info);
    }

    public List<ProvinceInfo> getProvinces() {
        return this.provinces;
    }

    public void setProvinces(List<ProvinceInfo> provinces2) {
        this.provinces = provinces2;
    }
}
