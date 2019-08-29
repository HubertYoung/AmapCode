package com.alipay.mobile.common.logging.api.behavor;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.common.SuperId;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Behavor implements Parcelable {
    public static final Creator<Behavor> CREATOR = new Creator<Behavor>() {
        public final Behavor createFromParcel(Parcel source) {
            return new Behavor(source);
        }

        public final Behavor[] newArray(int size) {
            return new Behavor[size];
        }
    };
    private String A = null;
    private int B = 2;
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q = H5Param.URL;
    private String r = SuperId.BIT_1_NEARBY_SEARCH;
    private Map<String, String> s;
    private String t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;
    private String z;

    public class Builder {
        private final Behavor a = new Behavor();

        public Builder(String userCaseID) {
            this.a.setUserCaseID(userCaseID);
        }

        @Deprecated
        public Builder setAppID(String appID) {
            this.a.setAppID(appID);
            return this;
        }

        @Deprecated
        public Builder setViewID(String viewID) {
            this.a.setViewID(viewID);
            return this;
        }

        @Deprecated
        public Builder setRefViewID(String sourceViewID) {
            this.a.setRefViewID(sourceViewID);
            return this;
        }

        @Deprecated
        public Builder setBehaviourPro(String seedID) {
            this.a.setBehaviourPro(seedID);
            return this;
        }

        public Builder setRenderBizType(String seedID) {
            this.a.setRenderBizType(seedID);
            return this;
        }

        public Builder setSeedID(String seedID) {
            this.a.setSeedID(seedID);
            return this;
        }

        public Builder setTrackId(String seedID) {
            this.a.setTrackId(seedID);
            return this;
        }

        public Builder setTrackToken(String seedID) {
            this.a.setTrackToken(seedID);
            return this;
        }

        public Builder setTrackDesc(String seedID) {
            this.a.setTrackDesc(seedID);
            return this;
        }

        public Builder setParam1(String param1) {
            this.a.setParam1(param1);
            return this;
        }

        public Builder setParam2(String param2) {
            this.a.setParam2(param2);
            return this;
        }

        public Builder setParam3(String param3) {
            this.a.setParam3(param3);
            return this;
        }

        public Builder addExtParam(String key, String value) {
            this.a.addExtParam(key, value);
            return this;
        }

        public Builder setExtParam(Map<String, String> extParams) {
            this.a.setExtParam(extParams);
            return this;
        }

        public Builder setPageId(String pageId) {
            this.a.setPageId(pageId);
            return this;
        }

        public Builder setSpmStatus(String spmStatus) {
            this.a.setSpmStatus(spmStatus);
            return this;
        }

        public Builder setLoggerLevel(int level) {
            this.a.setLoggerLevel(level);
            return this;
        }

        public Builder setPageStayTime(String pageStayTime) {
            this.a.setPageStayTime(pageStayTime);
            return this;
        }

        public Builder setEntityContentId(String entityContentId) {
            this.a.setEntityContentId(entityContentId);
            return this;
        }

        public Builder setRefer(String refer) {
            this.a.setRefer(refer);
            return this;
        }

        public Builder setAbTestInfo(String abTestInfo) {
            this.a.setAbTestInfo(abTestInfo);
            return this;
        }

        public Builder setXpath(String xpath) {
            this.a.setxPath(xpath);
            return this;
        }

        public Behavor build() {
            return this.a;
        }

        public void click() {
            LoggerFactory.getBehavorLogger().click(this.a);
        }

        public void openPage() {
            LoggerFactory.getBehavorLogger().openPage(this.a);
        }

        public void longClick() {
            LoggerFactory.getBehavorLogger().longClick(this.a);
        }

        public void submit() {
            LoggerFactory.getBehavorLogger().submit(this.a);
        }

        public void slide() {
            LoggerFactory.getBehavorLogger().slide(this.a);
        }

        public void autoOpenPage() {
            LoggerFactory.getBehavorLogger().autoOpenPage(this.a);
        }

        public void autoEvent() {
            LoggerFactory.getBehavorLogger().autoEvent(this.a);
        }
    }

    public String getUserCaseID() {
        return this.a;
    }

    public void setUserCaseID(String userCaseID) {
        this.a = userCaseID;
    }

    public String getAppID() {
        return this.b;
    }

    @Deprecated
    public void setAppID(String appID) {
        this.b = appID;
    }

    public String getViewID() {
        return this.d;
    }

    @Deprecated
    public void setViewID(String viewID) {
        this.d = viewID;
    }

    public String getRefViewID() {
        return this.e;
    }

    @Deprecated
    public void setRefViewID(String refViewID) {
        this.e = refViewID;
    }

    public String getSeedID() {
        return this.f;
    }

    public void setSeedID(String seedID) {
        this.f = seedID;
    }

    public String getParam1() {
        return this.g;
    }

    public void setParam1(String param1) {
        this.g = param1;
    }

    public String getParam2() {
        return this.h;
    }

    public void setParam2(String param2) {
        this.h = param2;
    }

    public String getParam3() {
        return this.i;
    }

    public void setParam3(String param3) {
        this.i = param3;
    }

    @Deprecated
    public String getLegacyParam() {
        return this.j;
    }

    @Deprecated
    public void setLegacyParam(String legacyParam) {
        this.j = legacyParam;
    }

    public String getTrackId() {
        return this.k;
    }

    public void setTrackId(String trackId) {
        this.k = trackId;
    }

    public String getTrackToken() {
        return this.l;
    }

    public void setTrackToken(String trackToken) {
        this.l = trackToken;
    }

    public String getTrackDesc() {
        return this.m;
    }

    public void setTrackDesc(String trackDesc) {
        this.m = trackDesc;
    }

    public Map<String, String> getExtParams() {
        if (this.s == null) {
            this.s = new HashMap();
        }
        return this.s;
    }

    public void addExtParam(String key, String value) {
        if (this.s == null) {
            this.s = new HashMap();
        }
        this.s.put(key, value);
    }

    public void setExtParam(Map<String, String> extParams) {
        this.s = extParams;
    }

    public void removeExtParam(String key) {
        if (this.s != null) {
            this.s.remove(key);
        }
    }

    @Deprecated
    public void addExtParam5(String key, String value) {
    }

    public String getStatus() {
        return this.n;
    }

    @Deprecated
    public void setStatus(String status) {
        this.n = status;
    }

    public String getStatusMsg() {
        return this.o;
    }

    @Deprecated
    public void setStatusMsg(String statusMsg) {
        this.o = statusMsg;
    }

    public String getUrl() {
        return this.p;
    }

    @Deprecated
    public void setUrl(String url) {
        this.p = url;
    }

    public String getBehaviourPro() {
        return this.q;
    }

    public String getRenderBizType() {
        return this.A;
    }

    public void setRenderBizType(String renderBizType) {
        this.A = renderBizType;
    }

    public void setBehaviourPro(String behaviourPro) {
        this.q = behaviourPro;
    }

    public String getLogPro() {
        return this.r;
    }

    @Deprecated
    public void setLogPro(String logPro) {
        this.r = logPro;
    }

    public String getAppVersion() {
        return this.c;
    }

    @Deprecated
    public void setAppVersion(String appVersion) {
        this.c = appVersion;
    }

    public String getPageId() {
        return this.t;
    }

    public void setPageId(String pageId) {
        this.t = pageId;
    }

    public String getSpmStatus() {
        return this.u;
    }

    public void setSpmStatus(String spmStatus) {
        this.u = spmStatus;
    }

    public int getLoggerLevel() {
        return this.B;
    }

    public void setLoggerLevel(int logLevel) {
        this.B = logLevel;
    }

    public String getEntityContentId() {
        return this.v;
    }

    public void setEntityContentId(String entityContentId) {
        this.v = entityContentId;
    }

    public String getPageStayTime() {
        return this.w;
    }

    public void setPageStayTime(String pageStayTime) {
        this.w = pageStayTime;
    }

    public String getRefer() {
        return this.x;
    }

    public void setRefer(String refer) {
        this.x = refer;
    }

    public String getAbTestInfo() {
        return this.y;
    }

    public void setAbTestInfo(String abTestInfo) {
        this.y = abTestInfo;
    }

    public String getxPath() {
        return this.z;
    }

    public void setxPath(String xPath) {
        this.z = xPath;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeString(this.b);
        dest.writeString(this.c);
        dest.writeString(this.d);
        dest.writeString(this.e);
        dest.writeString(this.f);
        dest.writeString(this.g);
        dest.writeString(this.h);
        dest.writeString(this.i);
        dest.writeString(this.j);
        dest.writeString(this.k);
        dest.writeString(this.l);
        dest.writeString(this.m);
        dest.writeString(this.n);
        dest.writeString(this.o);
        dest.writeString(this.p);
        dest.writeString(this.q);
        dest.writeString(this.r);
        this.s = this.s != null ? this.s : new HashMap<>();
        dest.writeInt(this.s.size());
        for (Entry entry : this.s.entrySet()) {
            dest.writeString((String) entry.getKey());
            dest.writeString((String) entry.getValue());
        }
        dest.writeString(this.t);
        dest.writeString(this.u);
        dest.writeString(this.v);
        dest.writeString(this.w);
        dest.writeString(this.x);
        dest.writeString(this.y);
        dest.writeString(this.z);
        dest.writeInt(this.B);
        dest.writeString(this.A);
    }

    public Behavor() {
    }

    protected Behavor(Parcel in) {
        this.a = in.readString();
        this.b = in.readString();
        this.c = in.readString();
        this.d = in.readString();
        this.e = in.readString();
        this.f = in.readString();
        this.g = in.readString();
        this.h = in.readString();
        this.i = in.readString();
        this.j = in.readString();
        this.k = in.readString();
        this.l = in.readString();
        this.m = in.readString();
        this.n = in.readString();
        this.o = in.readString();
        this.p = in.readString();
        this.q = in.readString();
        this.A = in.readString();
        this.r = in.readString();
        int extParamsSize = in.readInt();
        this.s = new HashMap(extParamsSize);
        for (int i2 = 0; i2 < extParamsSize; i2++) {
            this.s.put(in.readString(), in.readString());
        }
        this.t = in.readString();
        this.u = in.readString();
        this.v = in.readString();
        this.w = in.readString();
        this.x = in.readString();
        this.y = in.readString();
        this.z = in.readString();
        this.B = in.readInt();
    }
}
