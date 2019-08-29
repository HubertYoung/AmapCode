package com.alipay.mobile.security.bio.common.record;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import java.util.Map;
import java.util.Map.Entry;

public class MetaRecord {
    public static final String BIZ_TYPE = "Biometrics";
    public static final String DEFAULT_LOG_CLASSIFIERS = "1#2";
    public static final String LOG_SEPARATOR = "#";
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = 3;
    public static final int PRIORITY_MIDDLE = 2;
    private String a = BIZ_TYPE;
    private String b;
    private String c;
    private String d;
    private String e;
    private int f;
    private boolean g = true;
    private String h = "";
    private String i = "";
    private String j = "";
    private Map<String, String> k;
    private int l = 2;
    private String m = "1";

    public MetaRecord() {
    }

    public MetaRecord(String str, String str2, String str3, String str4) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
    }

    public MetaRecord(String str, String str2, String str3, String str4, String str5) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.m = str5;
    }

    public MetaRecord(String str, String str2, String str3, String str4, int i2) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.l = i2;
    }

    public MetaRecord(String str, String str2, String str3, String str4, int i2, String str5) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.l = i2;
        this.m = str5;
    }

    public MetaRecord(String str, String str2, String str3, String str4, boolean z) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.g = z;
    }

    public MetaRecord(String str, String str2, String str3, String str4, boolean z, int i2) {
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.g = z;
        this.l = i2;
    }

    public String getCaseID() {
        return this.b;
    }

    public void setCaseID(String str) {
        this.b = str;
    }

    public String getActionID() {
        return this.c;
    }

    public void setActionID(String str) {
        this.c = str;
    }

    public String getAppID() {
        return this.d;
    }

    public void setAppID(String str) {
        this.d = str;
    }

    public String getSeedID() {
        return this.e;
    }

    public void setSeedID(String str) {
        this.e = str;
    }

    public int getSequenceId() {
        return this.f;
    }

    public void setSequenceId(int i2) {
        this.f = i2;
    }

    public String getParam1() {
        return this.h;
    }

    public void setParam1(String str) {
        this.h = str;
    }

    public String getParam2() {
        return this.i;
    }

    public void setParam2(String str) {
        this.i = str;
    }

    public String getParam3() {
        return this.j;
    }

    public void setParam3(String str) {
        this.j = str;
    }

    public Map<String, String> getParam4() {
        return this.k;
    }

    public void setParam4(Map<String, String> map) {
        this.k = map;
    }

    public boolean isEnableSequence() {
        return this.g;
    }

    public void setEnableSequence(boolean z) {
        this.g = z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("caseID:" + this.b + LOG_SEPARATOR);
        sb.append("actionID:" + this.c + LOG_SEPARATOR);
        sb.append("appID:" + this.d + LOG_SEPARATOR);
        sb.append("seedID:" + this.e + LOG_SEPARATOR);
        sb.append("bizType:" + this.a + LOG_SEPARATOR);
        sb.append("priority:" + this.l + LOG_SEPARATOR);
        sb.append("classifier:" + this.m + LOG_SEPARATOR);
        sb.append("param1:" + this.h + LOG_SEPARATOR);
        sb.append("param2:" + this.i + LOG_SEPARATOR);
        sb.append("param3:" + this.j + LOG_SEPARATOR);
        sb.append("param4:");
        if (this.k != null) {
            for (Entry next : this.k.entrySet()) {
                Object key = next.getKey();
                sb.append(new StringBuilder(AUScreenAdaptTool.PREFIX_ID).append(key).append("=").append(next.getValue()).toString());
            }
        }
        return sb.toString();
    }

    public String getBizType() {
        return this.a;
    }

    public void setBizType(String str) {
        this.a = str;
    }

    public int getPriority() {
        return this.l;
    }

    public void setPriority(int i2) {
        this.l = i2;
    }

    public String getClassifier() {
        return this.m;
    }

    public void setClassifier(String str) {
        this.m = str;
    }
}
