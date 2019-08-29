package com.taobao.applink.h;

import com.taobao.applink.util.TBAppLinkUtil;
import java.util.Map;

public class c {
    private static String a(int i) {
        switch (i) {
            case 1:
                return TBAppLinkUtil.APLUS_H5_OPEN;
            case 2:
                return TBAppLinkUtil.APLUS_NATIVE_OPEN;
            default:
                return null;
        }
    }

    public static void a(int i, Map map) {
        String a = a(i);
        if (map != null && a != null) {
            a.a().a(a).a(map).b();
        }
    }
}
