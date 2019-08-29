package com.alipay.android.phone.mobilecommon.apsecurity;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.service.local.apsecurity.ApSecurityService;
import com.alipay.mobile.security.bio.utils.BioLog;

public class AlipayApSecurityService extends ApSecurityService {
    private String a = null;

    public void init(Context context) {
        BioLog.i("AlipayApSecurityService init");
        if (TextUtils.isEmpty(this.a)) {
            TextUtils.isEmpty(sApdidToken);
        }
    }

    public String getApDidToken() {
        String str = this.a == null ? "" : this.a;
        BioLog.d("AlipayApSecurityService.getApDidToken() == " + str);
        return str;
    }
}
