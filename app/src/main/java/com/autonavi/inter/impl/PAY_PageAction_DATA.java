package com.autonavi.inter.impl;

import com.amap.bundle.pay.page.TestWechatPayPage;
import com.autonavi.annotation.helper.PageActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"com.amap.bundle.pay.TestWechatPayPage"}, module = "pay", pages = {"com.amap.bundle.pay.page.TestWechatPayPage"})
@KeepName
public final class PAY_PageAction_DATA extends HashMap<String, Class<?>> {
    public PAY_PageAction_DATA() {
        put("com.amap.bundle.pay.TestWechatPayPage", TestWechatPayPage.class);
    }
}
