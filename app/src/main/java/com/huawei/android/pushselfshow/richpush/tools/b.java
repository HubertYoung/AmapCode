package com.huawei.android.pushselfshow.richpush.tools;

import com.autonavi.minimap.offline.model.FilePathHelper;

public class b {
    public static String a(String str) {
        return ("application/zip".equals(str) || "application/zip_local".equals(str)) ? FilePathHelper.SUFFIX_DOT_ZIP : "text/html".equals(str) ? ".html" : "image/jpeg".equals(str) ? ".jpg" : ".unknow";
    }
}
