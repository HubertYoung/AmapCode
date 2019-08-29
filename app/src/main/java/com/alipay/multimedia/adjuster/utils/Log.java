package com.alipay.multimedia.adjuster.utils;

import android.text.TextUtils;

public class Log {
    public static final String COST_TIME_TAG = "CostTime";
    private static Logger sLogger;
    private String mModuleName;
    private String mTag;
    private boolean openModuleTag = false;

    public static void setLogger(Logger logger) {
        sLogger = logger;
    }

    public static final Log getLogger(Class clazz) {
        return getLogger(clazz.getSimpleName());
    }

    public Log setModuleName(String moduleName) {
        this.mModuleName = moduleName;
        return this;
    }

    public Log openModuleTag(boolean isOn) {
        this.openModuleTag = isOn;
        return this;
    }

    public static final Log getLogger(String tag) {
        Log logger = new Log();
        logger.mTag = tag;
        return logger;
    }

    private static String format(String format, Object... args) {
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
        String msg = format(format, args);
        if (sLogger != null) {
            sLogger.v(tag, msg);
        } else {
            android.util.Log.v(tag, msg);
        }
    }

    public static void D(String tag, String format, Object... args) {
        String msg = format(format, args);
        if (sLogger != null) {
            sLogger.d(tag, msg);
        } else {
            android.util.Log.i(tag, msg);
        }
    }

    public static void I(String tag, String format, Object... args) {
        String msg = format(format, args);
        if (sLogger != null) {
            sLogger.i(tag, msg);
        } else {
            android.util.Log.i(tag, msg);
        }
    }

    public static void W(String tag, String format, Object... args) {
        String msg = format(format, args);
        if (sLogger != null) {
            sLogger.w(tag, msg);
        } else {
            android.util.Log.w(tag, msg);
        }
    }

    public static void P(String tag, String format, Object... args) {
        String msg = format(format, args);
        if (sLogger != null) {
            sLogger.d(tag, msg);
        } else {
            android.util.Log.v(tag, msg);
        }
    }

    public static void E(String tag, Throwable e, String format, Object... args) {
        String msg = format(format, args);
        if (sLogger != null) {
            sLogger.e(tag, msg, e);
        } else {
            android.util.Log.e(tag, msg, e);
        }
    }

    public static void E(String tag, String format, Throwable e, Object... args) {
        E(tag, e, format, args);
    }

    public static void E(String tag, String format, Object... args) {
        E(tag, (Throwable) null, format, args);
    }

    public void v(String format, Object... args) {
        V(getTag(), getLogMsg(format), args);
    }

    public void d(String format, Object... args) {
        D(getTag(), getLogMsg(format), args);
    }

    public void i(String format, Object... args) {
        I(getTag(), getLogMsg(format), args);
    }

    public void w(String format, Object... args) {
        W(getTag(), getLogMsg(format), args);
    }

    public void p(String format, Object... args) {
        P(getTag(), getLogMsg(format), args);
    }

    public void e(Throwable e, String format, Object... args) {
        E(getTag(), e, getLogMsg(format), args);
    }

    public void e(String format, Object... args) {
        E(getTag(), (Throwable) null, getLogMsg(format), args);
    }

    private String getTag() {
        return (!this.openModuleTag || TextUtils.isEmpty(this.mModuleName)) ? this.mTag : this.mModuleName;
    }

    private String getLogMsg(String format) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(this.mModuleName)) {
            builder.append("[").append(this.mModuleName).append("]");
        }
        if (!TextUtils.isEmpty(this.mTag)) {
            builder.append("[").append(this.mTag).append("]");
        }
        builder.append(format);
        return builder.toString();
    }

    public static void TIME(String format, long time, Object... args) {
        if (time > 100) {
            D("CostTime", format, args);
        } else {
            P("CostTime", format, args);
        }
    }
}
