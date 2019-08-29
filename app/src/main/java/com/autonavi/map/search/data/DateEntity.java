package com.autonavi.map.search.data;

import com.autonavi.common.js.action.LifeEntity;

public class DateEntity extends LifeEntity {
    public static final String DATETYPE_HOTEL = "hotel";
    public static final String DATETYPE_VIEWPOINT = "scenic";
    private static final long serialVersionUID = 1;
    private String date;
    private String dateType;
    private String hotelCheckIn;
    private String hotelCheckOut;
    private String liveOrLeave;
    private String source;

    public DateEntity() {
    }

    public DateEntity(String str) {
        this.date = str;
        this.dateType = DATETYPE_VIEWPOINT;
    }

    public DateEntity(String str, String str2) {
        this.hotelCheckIn = str;
        this.hotelCheckOut = str2;
        this.dateType = DATETYPE_HOTEL;
    }

    public String getDateType() {
        return this.dateType;
    }

    public void setDateType(String str) {
        this.dateType = str;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public String getLiveOrLeave() {
        return this.liveOrLeave;
    }

    public void setLiveOrLeave(String str) {
        this.liveOrLeave = str;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public String getHotelCheckIn() {
        return this.hotelCheckIn;
    }

    public void setHotelCheckIn(String str) {
        this.hotelCheckIn = str;
    }

    public String getHotelCheckOut() {
        return this.hotelCheckOut;
    }

    public void setHotelCheckOut(String str) {
        this.hotelCheckOut = str;
    }
}
