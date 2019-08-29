package com.autonavi.minimap.route.sharebike.model;

public class TokenInfo extends BaseNetResult {
    public long mTimeStamp;
    private String mToken;
    public String mVersion;

    public TokenInfo(String str) {
        this.mToken = str;
    }

    public String getPreToken() {
        return this.mToken;
    }
}
