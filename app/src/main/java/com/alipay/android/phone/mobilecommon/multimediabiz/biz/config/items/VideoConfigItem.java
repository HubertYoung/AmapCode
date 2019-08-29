package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class VideoConfigItem {
    @JSONField(name = "sds")
    public int enableSetTexSize = 0;
    @JSONField(name = "omx")
    public int omx = 1;
    @JSONField(name = "orientation")
    public int orientation = 0;
    @JSONField(name = "prerate")
    public float prerate = 0.08f;
    @JSONField(name = "retainsurface")
    public int retainsurface = 0;
    @JSONField(name = "rotation")
    public int rotation = 0;
    @JSONField(name = "sizerate")
    public float sizerate = 1.08f;
    @JSONField(name = "abr")
    public int useAbr = 1;
    @JSONField(name = "vpc")
    public int videoPlayPrepareCheck = 1;

    public boolean checkVideoPlayHandlePrepare() {
        return this.videoPlayPrepareCheck == 1;
    }

    public String toString() {
        return "VideoConfigItem{sizerate=" + this.sizerate + "prerate=" + this.prerate + "useAbr=" + this.useAbr + "omx=" + this.omx + "sds=" + this.enableSetTexSize + "vpc=" + this.videoPlayPrepareCheck + '}';
    }
}
