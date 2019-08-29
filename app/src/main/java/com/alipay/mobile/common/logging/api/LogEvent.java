package com.alipay.mobile.common.logging.api;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import com.alipay.mobile.common.transportext.biz.diagnose.network.Configuration;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import java.io.Serializable;

public class LogEvent implements Serializable {
    private static final long serialVersionUID = 1;
    protected Bundle bundle;
    protected String category;
    boolean hasSetMessage = false;
    protected IRender iRender;
    protected Level level;
    protected String message;
    protected String tag;
    protected long timeStamp;
    protected String uploadUrl;

    public class Level implements Serializable {
        public static final Level ALL = new Level(Integer.MIN_VALUE, "ALL");
        public static final int ALL_INT = Integer.MIN_VALUE;
        public static final Level DEBUG = new Level(10000, "D");
        public static final int DEBUG_INT = 10000;
        public static final Level ERROR = new Level(40000, "E");
        public static final int ERROR_INT = 40000;
        public static final Level INFO = new Level(20000, LogHelper.DEFAULT_LEVEL);
        public static final int INFO_INT = 20000;
        public static final Level LOGGER_HIGH = new Level(1);
        public static final Level LOGGER_LOW = new Level(3);
        public static final Level LOGGER_MEDIUM = new Level(2);
        public static final Level OFF = new Level(5000, "OFF");
        public static final int OFF_INT = 5000;
        public static final Level VERBOSE = new Level(5000, SecureSignatureDefine.SG_KEY_SIGN_VERSION);
        public static final int VERBOSE_INT = 5000;
        public static final Level WARN = new Level(30000, "W");
        public static final int WARN_INT = 30000;
        private static final long serialVersionUID = -814092767334282137L;
        public int levelInt;
        public String levelStr;
        public int loggerLevel = -1;

        public Level(int loggerLevel2) {
            this.loggerLevel = loggerLevel2;
        }

        private Level(int levelInt2, String levelStr2) {
            this.levelInt = levelInt2;
            this.levelStr = levelStr2;
        }

        public String toString() {
            return this.levelStr;
        }

        public int toInt() {
            return this.levelInt;
        }

        public boolean isGreaterOrEqual(Level r) {
            return this.levelInt >= r.levelInt;
        }

        public static Level toLevel(String sArg) {
            return toLevel(sArg, DEBUG);
        }

        public static Level valueOf(String sArg) {
            return toLevel(sArg, DEBUG);
        }

        public static Level toLevel(int val) {
            return toLevel(val, DEBUG);
        }

        public static Level toLevel(int val, Level defaultLevel) {
            switch (val) {
                case 5000:
                    return VERBOSE;
                case 10000:
                    return DEBUG;
                case 20000:
                    return INFO;
                case 30000:
                    return WARN;
                case 40000:
                    return ERROR;
                default:
                    return defaultLevel;
            }
        }

        public static Level toLevel(String sArg, Level defaultLevel) {
            if (sArg == null) {
                return defaultLevel;
            }
            if (sArg.equalsIgnoreCase("ALL")) {
                return ALL;
            }
            if (sArg.equalsIgnoreCase(RequestMethodConstants.TRACE_METHOD)) {
                return VERBOSE;
            }
            if (sArg.equalsIgnoreCase("DEBUG")) {
                return DEBUG;
            }
            if (sArg.equalsIgnoreCase("INFO")) {
                return INFO;
            }
            if (sArg.equalsIgnoreCase(Configuration.LVL_WARN)) {
                return WARN;
            }
            if (sArg.equalsIgnoreCase("ERROR")) {
                return ERROR;
            }
            if (sArg.equalsIgnoreCase("OFF")) {
                return OFF;
            }
            return defaultLevel;
        }
    }

    public LogEvent(String logCategory, String tag2, Level level2, String msg) {
        this.category = logCategory;
        this.tag = tag2;
        this.level = level2;
        this.message = msg;
        this.timeStamp = System.currentTimeMillis();
    }

    public LogEvent(String logCategory, String tag2, Level level2, String msg, IRender iRender2) {
        this.category = logCategory;
        this.tag = tag2;
        this.level = level2;
        this.message = msg;
        this.iRender = iRender2;
        this.timeStamp = System.currentTimeMillis();
    }

    public LogEvent() {
    }

    private void a() {
        if (this.message == null && this.iRender != null && !this.hasSetMessage) {
            try {
                this.message = this.iRender.doRender();
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().warn((String) "LogEvent", tr);
            }
        }
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String logCategory) {
        this.category = logCategory;
    }

    public Level getLevel() {
        return this.level;
    }

    public void setLevel(Level level2) {
        this.level = level2;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag2) {
        this.tag = tag2;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp2) {
        this.timeStamp = timeStamp2;
    }

    public String getMessage() {
        a();
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
        this.hasSetMessage = true;
    }

    public String getUploadUrl() {
        return this.uploadUrl;
    }

    public void setUploadUrl(String uploadUrl2) {
        this.uploadUrl = uploadUrl2;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle2) {
        this.bundle = bundle2;
    }

    public String toString() {
        a();
        return this.message;
    }

    public boolean isIllegal() {
        return TextUtils.isEmpty(getCategory()) || getLevel() == null;
    }
}
