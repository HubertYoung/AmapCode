package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.mine.qrcode.page.QRLoginConfirmPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"amap.basemap.action.qr_login_page"}, module = "qrcode", pages = {"com.autonavi.mine.qrcode.page.QRLoginConfirmPage"})
@KeepName
public final class QRCODE_PageAction_DATA extends HashMap<String, Class<?>> {
    public QRCODE_PageAction_DATA() {
        put("amap.basemap.action.qr_login_page", QRLoginConfirmPage.class);
    }
}
