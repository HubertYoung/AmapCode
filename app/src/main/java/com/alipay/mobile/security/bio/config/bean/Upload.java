package com.alipay.mobile.security.bio.config.bean;

import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.StringUtil;

public class Upload {
    private int a = 10;
    private String b = "2.0";
    private float c = 0.8f;
    private String d = MetaRecord.DEFAULT_LOG_CLASSIFIERS;
    private String[] e;

    public String getLog_classifier() {
        return this.d;
    }

    public void setLog_classifier(String str) {
        this.d = str;
    }

    public String[] getCollection() {
        return this.e;
    }

    public void setCollection(String[] strArr) {
        this.e = strArr;
    }

    public int getMinquality() {
        return this.a;
    }

    public void setMinquality(int i) {
        this.a = i;
    }

    public String getMode() {
        return this.b;
    }

    public void setMode(String str) {
        this.b = str;
    }

    public float getUpload_compress_rate() {
        return this.c;
    }

    public void setUpload_compress_rate(float f) {
        this.c = f;
    }

    public String getLogClassifier() {
        return this.d;
    }

    public void setLogClassifier(String str) {
        this.d = str;
    }

    public String toString() {
        return "Upload{minquality=" + this.a + ", mode='" + this.b + '\'' + ", upload_compress_rate=" + this.c + ", log_classifier='" + this.d + '\'' + ", collection='" + StringUtil.array2String(this.e) + '\'' + '}';
    }
}
