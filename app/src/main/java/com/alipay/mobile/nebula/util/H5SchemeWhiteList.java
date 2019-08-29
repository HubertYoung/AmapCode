package com.alipay.mobile.nebula.util;

import com.alipay.mobile.framework.service.common.SchemeService;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.util.HashSet;
import java.util.Set;

public class H5SchemeWhiteList {
    public static Set<String> sSchemeMap = new HashSet<String>() {
        {
            add(BehavorReporter.PROVIDE_BY_ALIPAY);
            add(SchemeService.SCHEME_REVEAL);
            add("alipayqr");
            add("alipass");
            add("alipaywifi");
            add("alipaywifimcd");
            add("alipayauth");
            add("alipayre");
            add("alipaysrc");
            add("alipayqrrc");
            add("alipassrc");
            add("alipaywifirc");
            add("alipaywifimcdrc");
            add("alipayauthrc");
            add("alipayrerc");
            add("tel");
            add("sms");
            add("smsto");
            add("mms");
            add("mmsto");
            add("alipayhk");
        }
    };
}
