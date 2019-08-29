package com.autonavi.minimap.ajx3.log;

import com.amap.location.sdk.fusion.LocationParams;
import org.json.JSONException;
import org.json.JSONObject;

public class LogInfo {
    /* access modifiers changed from: private */
    public String diu;
    /* access modifiers changed from: private */
    public String msg;
    /* access modifiers changed from: private */
    public long time;

    public static class Builder {
        private LogInfo logInfo = new LogInfo();

        public Builder setDiu(String str) {
            this.logInfo.diu = str;
            return this;
        }

        public Builder setMsg(String str) {
            this.logInfo.msg = str;
            return this;
        }

        public Builder setTime(long j) {
            this.logInfo.time = j;
            return this;
        }

        public LogInfo build() {
            return this.logInfo;
        }
    }

    private LogInfo() {
    }

    public String getDiu() {
        return this.diu;
    }

    public String getMsg() {
        return this.msg;
    }

    public long getTime() {
        return this.time;
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(LocationParams.PARA_COMMON_DIU, getDiu());
            jSONObject.put("msg", getMsg());
            jSONObject.put("date", getTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
