package com.alipay.zoloz.hardware;

import android.text.TextUtils;
import com.alipay.biometrics.common.annotation.NotProguard;
import com.alipay.mobile.security.bio.service.BioMetaInfo;
import com.alipay.mobile.security.bio.service.BioServiceDescription;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.zoloz.hardware.a.a;

@NotProguard
public class HardwareMetaInfo extends BioMetaInfo {
    public static final String ASTRA_PRO_ISP_SERVICE = "com.alipay.zoloz.hardware.isp.AstraProIspService";

    public HardwareMetaInfo() {
        if (TextUtils.equals("", "AstraPro")) {
            try {
                BioServiceDescription bioServiceDescription = new BioServiceDescription();
                bioServiceDescription.setClazz(Class.forName(ASTRA_PRO_ISP_SERVICE));
                bioServiceDescription.setInterfaceName(a.class.getName());
                addExtService(bioServiceDescription);
            } catch (Throwable th) {
                BioLog.e(th);
            }
        }
    }
}
