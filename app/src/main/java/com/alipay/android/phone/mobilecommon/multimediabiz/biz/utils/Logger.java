package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import android.text.TextUtils;
import com.alipay.alipaylogger.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;

public class Logger {
    public static final String COST_TIME_TAG = "CostTime";
    private String a = null;

    public static class TimeCost {
        private long a;
        private String b;
        private String c;

        private TimeCost() {
        }

        public static TimeCost begin(Class clazz, String msg) {
            TimeCost cost = new TimeCost();
            cost.a = System.currentTimeMillis();
            cost.c = clazz.getSimpleName();
            cost.b = cost.c + ", " + msg;
            Logger.TIME(msg + ", start: " + cost.a, cost.a, new Object[0]);
            return cost;
        }

        public void end(String msg) {
            if (TextUtils.isEmpty(msg)) {
                msg = this.b;
            }
            Logger.TIME(msg + ", cost: " + (System.currentTimeMillis() - this.a), System.currentTimeMillis() - this.a, new Object[0]);
            this.a = System.currentTimeMillis();
        }

        public void end() {
            end(null);
        }
    }

    private static String a(String format, Object... args) {
        String str = format;
        if (args != null && args.length > 0) {
            try {
                str = String.format(format, args);
            } catch (Exception e) {
                str = format;
            }
        }
        return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId() + ")] " + str;
    }

    public static void V(String tag, String format, Object... args) {
        try {
            Log.d(tag, a(format, args));
        } catch (Throwable th) {
        }
    }

    public static void D(String tag, String format, Object... args) {
        try {
            Log.i(tag, a(format, args));
        } catch (Throwable th) {
        }
    }

    public static void I(String tag, String format, Object... args) {
        try {
            Log.i(tag, a(format, args));
        } catch (Throwable th) {
        }
    }

    public static void W(String tag, String format, Object... args) {
        try {
            Log.w(tag, a(format, args));
        } catch (Throwable th) {
        }
    }

    public static void P(String tag, String format, Object... args) {
        LoggerFactory.getTraceLogger().print(tag, a(format, args));
    }

    public static void E(String tag, String format, Throwable e, Object... args) {
        E(tag, e, format, args);
    }

    public static void E(String tag, Throwable e, String format, Object... args) {
        String msg = a(format, args);
        if (e == null) {
            try {
                Log.e(tag, msg);
            } catch (Throwable th) {
            }
        } else {
            Log.e(tag, msg, e);
        }
    }

    public static void E(String tag, String format, Object... args) {
        E(tag, (Throwable) null, format, args);
    }

    public static void TIME(String format, long time, Object... args) {
        if (time > 100) {
            D("CostTime", format, args);
        } else {
            P("CostTime", format, args);
        }
    }

    public static final Logger getLogger(Class clazz) {
        return getLogger(clazz.getSimpleName());
    }

    public static final Logger getLogger(String tag) {
        Logger logger = new Logger();
        logger.a = tag;
        return logger;
    }

    public void v(String format, Object... args) {
        V(this.a, format, args);
    }

    public void d(String format, Object... args) {
        D(this.a, format, args);
    }

    public void i(String format, Object... args) {
        I(this.a, format, args);
    }

    public void w(String format, Object... args) {
        W(this.a, format, args);
    }

    public void p(String format, Object... args) {
        P(this.a, format, args);
    }

    public void e(Throwable e, String format, Object... args) {
        E(this.a, e, format, args);
    }

    public void e(String format, Object... args) {
        E(this.a, (Throwable) null, format, args);
    }
}
