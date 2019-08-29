package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import android.text.TextUtils;

public abstract class APImageQuery extends BaseReq {
    static final int TYPE_BIG = 2;
    static final int TYPE_CLEAR = 4;
    static final int TYPE_CUT = 1;
    static final int TYPE_DEFAULT = 0;
    static final int TYPE_ORIGINAL = 3;
    static final int TYPE_THUMBNAIL = 5;
    public String path;
    protected String queryKey;
    protected int queryType;

    public APImageQuery(String path2) {
        this(path2, CutScaleType.KEEP_RATIO);
    }

    public APImageQuery(String path2, CutScaleType cutScaleType) {
        this.queryType = 0;
        this.path = path2;
        this.cutScaleType = cutScaleType;
    }

    public String getQueryKey() {
        if (TextUtils.isEmpty(this.queryKey)) {
            this.queryKey = this.path + this.queryType + this.width + this.height + this.cutScaleType + getQuality() + (this.plugin != null ? this.plugin.getPluginKey() : "") + (this.imageMarkRequest != null ? this.imageMarkRequest.getMarkId() : "");
        }
        return this.queryKey;
    }

    public String toString() {
        return "APImageQuery{path='" + this.path + '\'' + "queryType='" + this.queryType + '\'' + '}';
    }
}
