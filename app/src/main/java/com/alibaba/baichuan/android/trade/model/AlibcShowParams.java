package com.alibaba.baichuan.android.trade.model;

import com.taobao.applink.util.TBAppLinkUtil;

public class AlibcShowParams {
    private boolean a;
    private OpenType b;
    private String c;
    private String d;
    private String e;
    private boolean f;
    private boolean g;

    public AlibcShowParams() {
        this.f = true;
        this.g = false;
        this.b = OpenType.Auto;
        this.d = TBAppLinkUtil.TAOBAO_SCHEME;
    }

    public AlibcShowParams(OpenType openType, boolean z) {
        this.f = true;
        this.g = false;
        this.b = openType;
        this.a = z;
    }

    public String getBackUrl() {
        return this.c;
    }

    public String getClientType() {
        return this.d;
    }

    public OpenType getOpenType() {
        return this.b;
    }

    public String getTitle() {
        return this.e;
    }

    public boolean isNeedPush() {
        return this.a;
    }

    public boolean isProxyWebview() {
        return this.g;
    }

    public boolean isShowTitleBar() {
        return this.f;
    }

    public void setBackUrl(String str) {
        this.c = str;
    }

    public void setClientType(String str) {
        this.d = str;
    }

    public void setNeedPush(boolean z) {
        this.a = z;
    }

    public void setOpenType(OpenType openType) {
        this.b = openType;
    }

    public void setProxyWebview(boolean z) {
        this.g = z;
    }

    public void setShowTitleBar(boolean z) {
        this.f = z;
    }

    public void setTitle(String str) {
        this.e = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AlibcShowParams{isNeedPush=");
        sb.append(this.a);
        sb.append(", openType=");
        sb.append(this.b);
        sb.append(", backUrl='");
        sb.append(this.c);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
