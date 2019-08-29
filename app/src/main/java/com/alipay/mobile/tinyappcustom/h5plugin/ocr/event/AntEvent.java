package com.alipay.mobile.tinyappcustom.h5plugin.ocr.event;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AntEvent implements Parcelable {
    public static final Creator<AntEvent> CREATOR = new Creator<AntEvent>() {
        public final AntEvent createFromParcel(Parcel in) {
            return new AntEvent(in, 0);
        }

        public final AntEvent[] newArray(int size) {
            return new AntEvent[size];
        }
    };
    private String a;
    private String b;
    private int c;
    private String d;
    private String e;
    private String f;
    private Map<String, String> g;
    private String h;

    public static class Builder {
        private final AntEvent a = new AntEvent(0);

        public Builder setEventID(String eventID) {
            this.a.b(eventID);
            return this;
        }

        public Builder setBizType(String bizType) {
            this.a.a(bizType);
            return this;
        }

        public Builder addExtParam(String key, String value) {
            this.a.a(key, value);
            return this;
        }

        public Builder setLoggerLevel(int loggerLevel) {
            this.a.a(loggerLevel);
            return this;
        }

        public AntEvent build() {
            return this.a;
        }
    }

    /* synthetic */ AntEvent(byte b2) {
        this();
    }

    /* synthetic */ AntEvent(Parcel x0, byte b2) {
        this(x0);
    }

    private AntEvent() {
        this.c = 2;
        this.g = new HashMap();
    }

    private AntEvent(Parcel in) {
        this.c = 2;
        this.g = new HashMap();
        this.a = in.readString();
        this.b = in.readString();
        this.c = in.readInt();
        this.d = in.readString();
        this.e = in.readString();
        this.f = in.readString();
        this.h = in.readString();
        int extParamsSize = in.readInt();
        this.g = new HashMap(extParamsSize);
        for (int i = 0; i < extParamsSize; i++) {
            this.g.put(in.readString(), in.readString());
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeString(this.b);
        dest.writeInt(this.c);
        dest.writeString(this.d);
        dest.writeString(this.e);
        dest.writeString(this.f);
        dest.writeString(this.h);
        this.g = this.g != null ? this.g : new HashMap<>();
        dest.writeInt(this.g.size());
        for (Entry entry : this.g.entrySet()) {
            dest.writeString((String) entry.getKey());
            dest.writeString((String) entry.getValue());
        }
    }

    public int describeContents() {
        return 0;
    }

    public String getEventID() {
        return this.a;
    }

    /* access modifiers changed from: private */
    public void b(String eventID) {
        this.a = eventID;
    }

    public String getBizType() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public final void a(String bizType) {
        this.b = bizType;
    }

    public int getLoggerLevel() {
        return this.c;
    }

    /* access modifiers changed from: 0000 */
    public final void a(int loggerLevel) {
        this.c = loggerLevel;
    }

    public String getParam1() {
        return this.d;
    }

    public String getParam2() {
        return this.e;
    }

    public String getParam3() {
        return this.f;
    }

    public Map<String, String> getExtParams() {
        return this.g;
    }

    public String getPageId() {
        return this.h;
    }

    /* access modifiers changed from: private */
    public void a(String key, String value) {
        this.g.put(key, value);
    }

    public void send() {
        Log.e(LoggerFactory.TAG, "reportNoInitialization", new IllegalMonitorStateException("need invoke bind before use"));
    }
}
