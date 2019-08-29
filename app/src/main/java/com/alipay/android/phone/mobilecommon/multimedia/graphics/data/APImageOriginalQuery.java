package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

public class APImageOriginalQuery extends APImageQuery {
    public boolean requireImageInfo;

    public APImageOriginalQuery(String path) {
        super(path);
        this.requireImageInfo = true;
        this.queryType = 3;
    }
}
