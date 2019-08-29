package com.alipay.mobile.common.logging.api.monitor;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Performance implements Parcelable {
    public static final Creator<Performance> CREATOR = new Creator<Performance>() {
        public final Performance createFromParcel(Parcel source) {
            return new Performance(source);
        }

        public final Performance[] newArray(int size) {
            return new Performance[size];
        }
    };
    public static final String KEY_LOG_HEADER = "header";
    private String a;
    private String b;
    private String c;
    private String d;
    private Map<String, String> e = new HashMap();
    private String f;
    private int g = 2;

    public class Builder {
        private final Performance a = new Performance();

        public Builder setSubType(String subType) {
            this.a.setSubType(subType);
            return this;
        }

        public Builder setParam1(String param1) {
            this.a.setParam1(param1);
            return this;
        }

        public Builder setParam2(String param1) {
            this.a.setParam2(param1);
            return this;
        }

        public Builder setParam3(String param1) {
            this.a.setParam3(param1);
            return this;
        }

        public Builder setPageId(String pageId) {
            this.a.setPageId(pageId);
            return this;
        }

        public Builder addExtParam(String key, String value) {
            this.a.addExtParam(key, value);
            return this;
        }

        public Builder setLoggerLevel(int loggerLevel) {
            this.a.setLoggerLevel(loggerLevel);
            return this;
        }

        public Performance build() {
            return this.a;
        }

        public void performance(PerformanceID performanceID) {
            LoggerFactory.getMonitorLogger().performance(performanceID, this.a);
        }
    }

    public String getSubType() {
        return this.a;
    }

    public void setSubType(String subType) {
        this.a = subType;
    }

    public String getParam1() {
        return this.b;
    }

    public void setParam1(String param1) {
        this.b = param1;
    }

    public String getParam2() {
        return this.c;
    }

    public void setParam2(String param2) {
        this.c = param2;
    }

    public String getParam3() {
        return this.d;
    }

    public void setParam3(String param3) {
        this.d = param3;
    }

    public Map<String, String> getExtPramas() {
        return this.e;
    }

    public void addExtParam(String key, String value) {
        this.e.put(key, value);
    }

    public void removeExtParam(String key) {
        this.e.remove(key);
    }

    public String getPageId() {
        return this.f;
    }

    public void setPageId(String pageId) {
        this.f = pageId;
    }

    public int getLoggerLevel() {
        return this.g;
    }

    public void setLoggerLevel(int loggerLevel) {
        this.g = loggerLevel;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeString(this.b);
        dest.writeString(this.c);
        dest.writeString(this.d);
        dest.writeString(this.f);
        this.e = this.e != null ? this.e : new HashMap<>();
        dest.writeInt(this.e.size());
        for (Entry entry : this.e.entrySet()) {
            dest.writeString((String) entry.getKey());
            dest.writeString((String) entry.getValue());
        }
    }

    public Performance() {
    }

    protected Performance(Parcel in) {
        this.a = in.readString();
        this.b = in.readString();
        this.c = in.readString();
        this.d = in.readString();
        this.f = in.readString();
        int extParamsSize = in.readInt();
        this.e = new HashMap(extParamsSize);
        for (int i = 0; i < extParamsSize; i++) {
            this.e.put(in.readString(), in.readString());
        }
    }
}
