package com.alipay.mobile.common.transport.monitor.networkqos;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;

public class QosInterfereHelper {
    public static final double QOE_NET_INTERFERENCE = 100.0d;
    private static double b = 0.68d;
    private static double c = 0.27d;
    private static double d = ((1.0d - b) - c);
    private static QosInterfereHelper i = null;
    private long a;
    private double e;
    private double f;
    private double g;
    private int h;

    public static QosInterfereHelper getInstance() {
        if (i != null) {
            return i;
        }
        synchronized (QosInterfereHelper.class) {
            try {
                if (i == null) {
                    i = new QosInterfereHelper();
                }
            }
        }
        return i;
    }

    private QosInterfereHelper() {
        this.a = 0;
        this.e = -1.0d;
        this.f = -1.0d;
        this.g = -1.0d;
        this.h = 0;
        this.a = 0;
    }

    public double interferInputRtt(double rtt) {
        try {
            if (!a()) {
                return rtt;
            }
            if (this.a == 0) {
                this.g = rtt;
                this.f = rtt;
                this.e = rtt;
            }
            this.g = this.f;
            this.f = this.e;
            this.e = rtt;
            this.a++;
            double rttFinal = a((b * this.e) + (c * this.f) + (d * this.g));
            LogCatUtil.debug("QosInterfereHelper", "input:" + rtt + ",result=" + rttFinal);
            return rttFinal;
        } catch (Throwable ex) {
            LogCatUtil.error((String) "QosInterfereHelper", "interferInputRtt ex:" + ex.toString());
            return rtt;
        }
    }

    public void interferOutputRtt(double input, double output) {
        try {
            if (a()) {
                if (AlipayQosService.getInstance().getRtoLevel(output) != 4) {
                    this.h = 0;
                } else if (input >= 200.0d) {
                    this.h = 0;
                } else {
                    this.h++;
                    if (this.h >= 5) {
                        LogCatUtil.debug("QosInterfereHelper", "it's time to interfereQos");
                        AlipayQosService.getInstance().estimate(100.0d, 6);
                        LogCatUtil.debug("QosInterfereHelper", "after interferOutputRtt:" + AlipayQosService.getInstance().getRto());
                        this.h = 0;
                    }
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "QosInterfereHelper", "interferOutputRtt ex:" + ex.toString());
        }
    }

    private static boolean a() {
        if (TextUtils.equals("T", TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.NET_QOS_INTERFER))) {
            return true;
        }
        return false;
    }

    private static double a(double d2) {
        return ((double) Math.round(d2 * 100.0d)) / 100.0d;
    }
}
