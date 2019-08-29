package com.alipay.mobile.map.model.geocode;

import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.map.model.LatLonPoint;
import java.io.Serializable;
import java.util.List;

public class ReGeocodeResult extends CodeResult implements Serializable, Cloneable {
    private static final String TAG = "ReGeocodeResult";
    private static final long serialVersionUID = 1;
    private String adcode;
    private String cityAdcode;
    private String citySimpleName;
    private String country;
    private String countryCode;
    private List<Crossroad> crossroads;
    private String district;
    private String districtAdcode;
    private String formatAddress;
    private boolean fromCache;
    private boolean isChina;
    private boolean isChineseMainLand;
    private boolean isMunicipality;
    private LatLonPoint latlonPoint;
    private String neighborhood;
    private List<PoiItem> pois;
    private String province;
    private List<RegeocodeRoad> roads;
    private StreetNumber streetNumber;
    private String township;

    public Object clone() {
        try {
            return super.clone();
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "return null, clone exception:".concat(String.valueOf(th)));
            return null;
        }
    }

    public String getCityAdcode() {
        return this.cityAdcode;
    }

    public void setCityAdcode(String str) {
        this.cityAdcode = str;
    }

    public String getDistrictAdcode() {
        return this.districtAdcode;
    }

    public void setDistrictAdcode(String str) {
        this.districtAdcode = str;
    }

    public List<Crossroad> getCrossroads() {
        return this.crossroads;
    }

    public void setCrossroads(List<Crossroad> list) {
        this.crossroads = list;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String str) {
        this.district = str;
    }

    public String getFormatAddress() {
        return this.formatAddress;
    }

    public void setFormatAddress(String str) {
        this.formatAddress = str;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public void setNeighborhood(String str) {
        this.neighborhood = str;
    }

    public List<PoiItem> getPois() {
        return this.pois;
    }

    public void setPois(List<PoiItem> list) {
        this.pois = list;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String str) {
        this.province = str;
    }

    public List<RegeocodeRoad> getRoads() {
        return this.roads;
    }

    public void setRoads(List<RegeocodeRoad> list) {
        this.roads = list;
    }

    public StreetNumber getStreetNumber() {
        return this.streetNumber;
    }

    public void setStreetNumber(StreetNumber streetNumber2) {
        this.streetNumber = streetNumber2;
    }

    public String getTownship() {
        return this.township;
    }

    public void setTownship(String str) {
        this.township = str;
    }

    public void setLatlonPoint(LatLonPoint latLonPoint) {
        this.latlonPoint = latLonPoint;
    }

    public LatLonPoint getLatlonPoint() {
        return this.latlonPoint;
    }

    public boolean isChineseMainLand() {
        return this.isChineseMainLand;
    }

    public void setChineseMainLand(boolean z) {
        this.isChineseMainLand = z;
    }

    public String getAdcode() {
        return this.adcode;
    }

    public void setAdcode(String str) {
        this.adcode = str;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String str) {
        this.countryCode = str;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public String getCitySimpleName() {
        return this.citySimpleName;
    }

    public void setCitySimpleName(String str) {
        this.citySimpleName = str;
    }

    public boolean isChina() {
        return this.isChina;
    }

    public void setChina(boolean z) {
        this.isChina = z;
    }

    public boolean isMunicipality() {
        return this.isMunicipality;
    }

    public void setMunicipality(boolean z) {
        this.isMunicipality = z;
    }

    public boolean isFromCache() {
        return this.fromCache;
    }

    public void setFromCache(boolean z) {
        this.fromCache = z;
    }
}
