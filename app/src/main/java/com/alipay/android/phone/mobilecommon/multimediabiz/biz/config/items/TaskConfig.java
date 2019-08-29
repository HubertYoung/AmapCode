package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alibaba.fastjson.annotation.JSONField;

public class TaskConfig {
    @JSONField(name = "cts")
    public int commonTaskPoolSwitch = 0;
    @JSONField(name = "imageOccurs")
    public int defaultImageOccurs = 8;
    @JSONField(name = "maxOccurs")
    public int defaultMaxOccurs = 8;
    @JSONField(name = "djgoc")
    public int imgDjgImageOccurs = 6;
    @JSONField(name = "urlboc")
    public int imgUrlBlackOccurs = 4;
    @JSONField(name = "urloc")
    public int imgUrlImageOccurs = 4;
    @JSONField(name = "urlwoc")
    public int imgUrlWhiteOccurs = 4;
    @JSONField(name = "lmo")
    public int loadMaxOccurs = 5;
    @JSONField(name = "locmo")
    public int localMaxOccurs = 3;
    @JSONField(name = "sepimg")
    public int separateImage = 1;
    @JSONField(name = "uwimg")
    public int urlWhiteImage = 1;

    public String toString() {
        return "TaskConfig{defaultMaxOccurs=" + this.defaultMaxOccurs + ", defaultImageOccurs=" + this.defaultImageOccurs + ", localMaxOccurs=" + this.localMaxOccurs + ", loadMaxOccurs=" + this.loadMaxOccurs + ", separateImage=" + this.separateImage + ", imgUrlImageOccurs=" + this.imgUrlImageOccurs + ", imgDjgImageOccurs=" + this.imgDjgImageOccurs + ", urlWhiteImage=" + this.urlWhiteImage + ", imgUrlWhiteOccurs=" + this.imgUrlWhiteOccurs + ", imgUrlBlackOccurs=" + this.imgUrlBlackOccurs + ", commonTaskPoolSwitch=" + this.commonTaskPoolSwitch + '}';
    }
}
