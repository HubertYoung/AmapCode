package com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.ConfigManager;

public class BeautyConfigItem {
    public static final int BEAUTY_LEVEL_DEFAULT = 0;
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_QUPAI = 1;
    public int bvLevel = 0;
    public int type = ConfigManager.getInstance().getCommonConfigItem().beautyType;

    public boolean isFalconBeauty() {
        return this.type == 0;
    }

    public String toString() {
        return "BeautyConfigItem{type=" + this.type + ", bvLevel=" + this.bvLevel + '}';
    }
}
