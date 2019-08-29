package com.alipay.android.phone.inside.commonbiz.ids;

import android.text.TextUtils;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;

public enum InsideChannel {
    Alipay(BehavorReporter.PROVIDE_BY_ALIPAY),
    Tao("tao"),
    Merchant("merchant");
    
    private String value;

    private InsideChannel(String str) {
        this.value = str;
    }

    public final String getValue() {
        return this.value;
    }

    public static InsideChannel getChannel(String str) {
        if (TextUtils.equals(str, Alipay.getValue())) {
            return Alipay;
        }
        if (TextUtils.equals(str, Tao.getValue())) {
            return Tao;
        }
        if (TextUtils.equals(str, Merchant.getValue())) {
            return Merchant;
        }
        LoggerFactory.e().a("commonbiz", "InsideChannelUnmatch");
        return Merchant;
    }
}
