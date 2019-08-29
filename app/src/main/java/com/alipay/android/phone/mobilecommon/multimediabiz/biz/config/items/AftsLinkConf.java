package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import android.text.TextUtils;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.Arrays;

public class AftsLinkConf extends BaseConfig {
    @JSONField(name = "adf")
    public int aftsDynamicFormat = 0;
    @JSONField(name = "alfs")
    public int aftsLinkFileSwitch = 0;
    @JSONField(name = "alis")
    public int aftsLinkImgSwitch = 0;
    @JSONField(name = "bbs")
    public String[] blackBizs = {""};
    @JSONField(name = "rts")
    public int errorCodeeRetrySwitch = 1;
    @JSONField(name = "fdhs")
    public int fileDlHttpSwitch = 0;
    @JSONField(name = "idhs")
    public int imageDlHttpSwitch = 0;
    @JSONField(name = "od")
    public String onlineDomain = "mdn.alipayobjects.com";
    @JSONField(name = "wf")
    public int webpFormat = 0;

    public void updateTime() {
        super.updateTime();
    }

    public boolean isAftsImageSwitchOn(String biz) {
        if (this.aftsLinkImgSwitch == 0 || TextUtils.isEmpty(biz)) {
            return false;
        }
        return checkAftsBiz(biz);
    }

    public boolean isAftsFileSwitchOn(String biz) {
        if (this.aftsLinkFileSwitch == 0 || TextUtils.isEmpty(biz)) {
            return false;
        }
        return checkAftsBiz(biz);
    }

    private boolean checkAftsBiz(String biz) {
        String[] strArr;
        for (String item : this.blackBizs) {
            if (!TextUtils.isEmpty(item) && biz.startsWith(item)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkNetRetrySwitch() {
        return 1 == this.errorCodeeRetrySwitch;
    }

    public boolean isUseAftsDynamicFormat() {
        return 1 == this.aftsDynamicFormat;
    }

    public boolean checkWebpFormat() {
        return 1 == this.webpFormat;
    }

    public String toString() {
        return "AftsLinkConf{aftsLinkImgSwitch=" + this.aftsLinkImgSwitch + ", aftsLinkFileSwitch=" + this.aftsLinkFileSwitch + ", onlineDomain='" + this.onlineDomain + '\'' + ", aftsDynamicFormat=" + this.aftsDynamicFormat + ", imageDlHttpSwitch=" + this.imageDlHttpSwitch + ", fileDlHttpSwitch=" + this.fileDlHttpSwitch + ", errorCodeeRetrySwitch=" + this.errorCodeeRetrySwitch + ", blackBizs=" + Arrays.toString(this.blackBizs) + '}';
    }
}
