package com.autonavi.minimap.basemap.traffic.bean;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import java.io.Serializable;

public class TrafficSubscribeResult implements Serializable {
    private static final long serialVersionUID = -8505724574117058801L;
    public int code;
    public boolean hassuggestion;
    public int length;
    public String message;
    public boolean result;
    public int savingminutes;
    public int trafficlightnum;
    public int traveltime;
    public String viaroads;
    public int waitminutes;

    public String getSaveTimeSuggest() {
        if (this.waitminutes <= 0 || this.savingminutes <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(AMapPageUtil.getAppContext().getString(R.string.save_time_suggest_prefix));
        sb.append(this.waitminutes);
        sb.append(AMapPageUtil.getAppContext().getString(R.string.save_time_suggest_suffix));
        sb.append(this.savingminutes);
        sb.append(AMapPageUtil.getAppContext().getString(R.string.oper_minute));
        return sb.toString();
    }

    public String getRoadInfo() {
        if (TextUtils.isEmpty(this.viaroads)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(AMapPageUtil.getAppContext().getString(R.string.oper_by_way_of));
        sb.append(this.viaroads);
        return sb.toString();
    }

    public String getCostInfo() {
        String restTime = getRestTime(this.traveltime * 60);
        String switchStrFromMeter_ = switchStrFromMeter_(this.length);
        if (TextUtils.isEmpty(restTime) || TextUtils.isEmpty(switchStrFromMeter_)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(restTime);
        sb.append(Token.SEPARATOR);
        sb.append(switchStrFromMeter_);
        return sb.toString();
    }

    public String switchStrFromMeter_(int i) {
        if (i >= 1000) {
            StringBuilder sb = new StringBuilder();
            sb.append(((float) Math.round((((float) i) / 1000.0f) * 10.0f)) / 10.0f);
            sb.append("公里");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(i);
        sb2.append("米");
        return sb2.toString();
    }

    public String getRestTime(int i) {
        int i2 = i / 60;
        if (i2 >= 60) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2 / 60);
            sb.append("时");
            String sb2 = sb.toString();
            int i3 = i2 % 60;
            if (i3 <= 0) {
                return sb2;
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(i3);
            sb3.append("分");
            return sb3.toString();
        } else if (i2 == 0) {
            return "<1分钟";
        } else {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(i2);
            sb4.append(AMapPageUtil.getAppContext().getString(R.string.oper_minute));
            return sb4.toString();
        }
    }
}
