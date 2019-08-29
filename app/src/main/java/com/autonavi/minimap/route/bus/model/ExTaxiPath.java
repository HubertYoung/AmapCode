package com.autonavi.minimap.route.bus.model;

import android.content.res.Resources;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import java.io.Serializable;

public class ExTaxiPath implements axb, Serializable {
    private static final long serialVersionUID = 2364738128878740382L;
    public int cost;
    public int endX;
    public int endY;
    public String endpoint;
    public int length;
    public String mEndName;
    public String mStartName;
    public int startX;
    public int startY;
    public String startpoint;
    public int time;

    public String getDestDesc() {
        if (!TextUtils.isEmpty(this.mEndName)) {
            return this.mEndName;
        }
        return AMapAppGlobal.getApplication().getString(R.string.ending_point);
    }

    public String getCostDesc() {
        Resources resources = AMapAppGlobal.getApplication().getResources();
        StringBuilder sb = new StringBuilder();
        sb.append(cfe.a(this.length));
        StringBuilder sb2 = new StringBuilder("/");
        sb2.append(resources.getString(R.string.estimate));
        sb.append(sb2.toString());
        sb.append(lf.a(this.time));
        if (this.cost > 0) {
            sb.append("/");
            sb.append(this.cost);
            sb.append(resources.getString(R.string.rmb));
        }
        StringBuilder sb3 = new StringBuilder("(");
        sb3.append(sb.toString());
        sb3.append(")");
        return sb3.toString();
    }

    public String getPathDesc() {
        return AMapAppGlobal.getApplication().getString(R.string.taxi);
    }

    public int getPathIcon() {
        return R.drawable.direction_bus_list_taxi;
    }

    public String getStartDesc() {
        if (!TextUtils.isEmpty(this.mStartName)) {
            return this.mStartName;
        }
        return AMapAppGlobal.getApplication().getString(R.string.starting_point);
    }

    public int getCostTime() {
        return this.time;
    }

    public int getCostDistance() {
        return this.length;
    }

    public double getCostFee() {
        return (double) this.cost;
    }

    public String getStartEndName() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStartDesc());
        sb.append("→");
        sb.append(getDestDesc());
        return sb.toString();
    }
}
