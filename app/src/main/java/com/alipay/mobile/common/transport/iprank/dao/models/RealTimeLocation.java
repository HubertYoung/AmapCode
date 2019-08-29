package com.alipay.mobile.common.transport.iprank.dao.models;

import android.content.Context;
import com.alipay.mobile.common.transport.iprank.mng.IpLbsManager;
import com.alipay.mobile.common.transport.iprank.utils.IpRankUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.SharedPreUtils;

public class RealTimeLocation {
    public static long LBS_ERROR = -100;
    private static RealTimeLocation g = null;
    private long a = LBS_ERROR;
    private String b = "";
    private long c = -1;
    private long d = -1;
    private IpLbsManager e = null;
    private Context f = null;

    public static RealTimeLocation getInstance(Context context) {
        if (g != null) {
            return g;
        }
        synchronized (RealTimeLocation.class) {
            try {
                if (g == null) {
                    g = new RealTimeLocation(context);
                }
            }
        }
        return g;
    }

    private RealTimeLocation(Context context) {
        this.f = context;
        this.a = b();
        this.d = c();
        this.e = new IpLbsManager(this.f);
    }

    private boolean a() {
        return System.currentTimeMillis() > c();
    }

    public long getLbsIdGently() {
        try {
            this.a = b();
            if (this.a == LBS_ERROR || this.a < 0 || a()) {
                LogCatUtil.info("IPR_RealTimeLoc", "realTimeLocation not init or has timeout,get new lbs_id");
                String mLocation = IpRankUtil.getLatLng(this.f);
                this.a = this.e.getLbsIdAnyway(mLocation);
                this.b = mLocation;
                this.c = System.currentTimeMillis();
                this.d = this.c + 1200000;
                a(this.a);
                b(this.d);
            }
            return this.a;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "IPR_RealTimeLoc", ex);
            return -1;
        }
    }

    public String toString() {
        return "RealTimeLocation{lbs_id=" + this.a + ", latlng='" + this.b + '\'' + ", recordTime=" + this.c + '}';
    }

    private void a(long lbs_id) {
        SharedPreUtils.putData(this.f, (String) "ip_rank_lbsId", lbs_id);
    }

    private long b() {
        return SharedPreUtils.getLonggData(this.f, "ip_rank_lbsId");
    }

    private void b(long outTime) {
        SharedPreUtils.putData(this.f, (String) "ip_rank_outTime", outTime);
    }

    private long c() {
        return SharedPreUtils.getLonggData(this.f, "ip_rank_outTime");
    }
}
