package com.alipay.mobile.common.lbs;

import android.location.Location;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.map.model.geocode.ReGeocodeResult;

public class LBSLocation extends Location implements Cloneable {
    private static final String TAG = "LBSLocation";
    private float accuracy;
    private String adCode;
    private String address;
    private String aoiname;
    private String cellInfo;
    private String cellInfokey;
    private String city;
    private String cityAdcode;
    @Deprecated
    private String cityCode;
    private String corseLocation;
    private String corseLocationkey;
    private String country;
    private String district;
    private String districtAdcode;
    private String fineLocation;
    private String fineLocationkey;
    private boolean isCache;
    private boolean isGetAMapAPP = false;
    private boolean isReGeocoded;
    private double latitude;
    private long localTime;
    private Long locationtime;
    private double longitude;
    private String province;
    private int reGeocodeLevel = -1;
    private ReGeocodeResult reGeocodeResult;
    private String street;
    private String wifiLocation;
    private String wifiLocationkey;

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

    public boolean isCache() {
        return this.isCache;
    }

    public void setCache(boolean z) {
        this.isCache = z;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "return null, clone exception:".concat(String.valueOf(th)));
            return null;
        }
    }

    public float getAccuracy() {
        return this.accuracy;
    }

    public void setAccuracy(float f) {
        this.accuracy = f;
    }

    public LBSLocation(Location location) {
        super(location);
    }

    public LBSLocation() {
        super("");
    }

    public long getLocalTime() {
        return this.localTime;
    }

    public void setLocalTime(long j) {
        this.localTime = j;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String str) {
        this.province = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String str) {
        this.district = str;
    }

    @Deprecated
    public String getCityCode() {
        return this.cityCode;
    }

    @Deprecated
    public void setCityCode(String str) {
        this.cityCode = str;
    }

    public String getAdCode() {
        return this.adCode;
    }

    public void setAdCode(String str) {
        this.adCode = str;
    }

    public boolean getIsGetAMapAPP() {
        return this.isGetAMapAPP;
    }

    public void setIsGetAMapAPP(boolean z) {
        this.isGetAMapAPP = z;
    }

    public String getFineLocation() {
        return this.fineLocation;
    }

    public void setFineLocation(String str) {
        this.fineLocation = str;
    }

    public String getFineLocationkey() {
        return this.fineLocationkey;
    }

    public void setFineLocationkey(String str) {
        this.fineLocationkey = str;
    }

    public String getCorseLocation() {
        return this.corseLocation;
    }

    public void setCorseLocation(String str) {
        this.corseLocation = str;
    }

    public String getCorseLocationkey() {
        return this.corseLocationkey;
    }

    public void setCorseLocationkey(String str) {
        this.corseLocationkey = str;
    }

    public String getWifiLocation() {
        return this.wifiLocation;
    }

    public void setWifiLocation(String str) {
        this.wifiLocation = str;
    }

    public String getWifiLocationkey() {
        return this.wifiLocationkey;
    }

    public void setWifiLocationkey(String str) {
        this.wifiLocationkey = str;
    }

    public String getCellInfo() {
        return this.cellInfo;
    }

    public void setCellInfo(String str) {
        this.cellInfo = str;
    }

    public String getCellInfokey() {
        return this.cellInfokey;
    }

    public void setCellInfokey(String str) {
        this.cellInfokey = str;
    }

    public void setCountry(String str) {
        this.country = str;
    }

    public String getCountry() {
        return this.country;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String str) {
        this.street = str;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String str) {
        this.address = str;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double d) {
        this.latitude = d;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double d) {
        this.longitude = d;
    }

    public Long getLocationtime() {
        return this.locationtime;
    }

    public void setLocationtime(Long l) {
        this.locationtime = l;
    }

    public String getAoiname() {
        return this.aoiname;
    }

    public void setAoiname(String str) {
        this.aoiname = str;
    }

    public boolean isReGeocoded() {
        return this.isReGeocoded;
    }

    public void setReGeocoded(boolean z) {
        this.isReGeocoded = z;
    }

    public int getReGeocodeLevel() {
        return this.reGeocodeLevel;
    }

    public void setReGeocodeLevel(int i) {
        this.reGeocodeLevel = i;
    }

    public ReGeocodeResult getReGeocodeResult() {
        return this.reGeocodeResult;
    }

    public void setReGeocodeResult(ReGeocodeResult reGeocodeResult2) {
        this.reGeocodeResult = reGeocodeResult2;
    }
}
