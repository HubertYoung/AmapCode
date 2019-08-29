package com.autonavi.bl.search;

public class OfflineParam implements Cloneable {
    public int adcode;
    public int aroundRadius;
    public String keyword;
    public int languageType;
    public double latitude;
    public double longitude;
    public int resultMaxCount;
    public int searchForm;
    public int searchType;

    public OfflineParam clone() {
        try {
            return (OfflineParam) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
