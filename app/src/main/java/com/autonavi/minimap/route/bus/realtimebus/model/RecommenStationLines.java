package com.autonavi.minimap.route.bus.realtimebus.model;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.List;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
public class RecommenStationLines implements Serializable {
    private List<RecommenStationBuses> buses;
    private String end_time;
    private String index;
    public boolean isFollow;
    private String is_realtime;
    private String key_name;
    private String lineid;
    private String name;
    private String start_time;
    private String stationid;
    private String status;
    private String tactics;
    private String type;

    public String getStationid() {
        return this.stationid;
    }

    public void setStationid(String str) {
        this.stationid = str;
    }

    public String getLineid() {
        return this.lineid;
    }

    public void setLineid(String str) {
        this.lineid = str;
    }

    public String getIndex() {
        return this.index;
    }

    public int getIndexWithInt() {
        try {
            return Integer.parseInt(this.index);
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public void setIndex(String str) {
        this.index = str;
    }

    public String getTactics() {
        return this.tactics;
    }

    public void setTactics(String str) {
        this.tactics = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getKey_name() {
        return this.key_name;
    }

    public void setKey_name(String str) {
        this.key_name = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getStart_time() {
        return this.start_time;
    }

    public String getFormateStartTime() {
        return TextUtils.isEmpty(this.start_time) ? "" : new StringBuffer(this.start_time).insert(2, ":").toString();
    }

    public String getFormateEndTime() {
        return TextUtils.isEmpty(this.end_time) ? "" : new StringBuffer(this.end_time).insert(2, ":").toString();
    }

    public void setStart_time(String str) {
        this.start_time = str;
    }

    public String getEnd_time() {
        return this.end_time;
    }

    public void setEnd_time(String str) {
        this.end_time = str;
    }

    public String getIs_realtime() {
        return this.is_realtime;
    }

    public boolean isRealTime() {
        return "1".equals(this.is_realtime);
    }

    public void setIs_realtime(String str) {
        this.is_realtime = str;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public List<RecommenStationBuses> getBuses() {
        return this.buses;
    }

    public void setBuses(List<RecommenStationBuses> list) {
        this.buses = list;
    }
}
