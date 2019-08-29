package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.resp;

import android.os.Bundle;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.module.BaseDownResp;

public class ThumbnailsDownResp extends BaseDownResp {
    private byte[] data;
    private Bundle extra;
    private String savePath;

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data2) {
        this.data = data2;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public void setSavePath(String savePath2) {
        this.savePath = savePath2;
    }

    public Bundle getExtra() {
        return this.extra;
    }

    public void setExtra(Bundle extra2) {
        this.extra = extra2;
    }
}
