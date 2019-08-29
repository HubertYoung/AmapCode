package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class ImageProcessorConf {
    @JSONField(name = "dt")
    public double correctCutScaleTypeDelta = 0.04d;
    @JSONField(name = "obs")
    public int oilPicBrushSize = 4;
    @JSONField(name = "oc")
    public int oilPicCoarseness = 5;
    @JSONField(name = "scn")
    public int systemCropNew = 1;

    public String toString() {
        return "ImageProcessorConf{oilPicBrushSize=" + this.oilPicBrushSize + ", oilPicCoarseness=" + this.oilPicCoarseness + ", correctCutScaleTypeDelta=" + this.correctCutScaleTypeDelta + ", systemCropNew=" + this.systemCropNew + '}';
    }
}
