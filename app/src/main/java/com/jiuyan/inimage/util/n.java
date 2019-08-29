package com.jiuyan.inimage.util;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileQueryResult;
import com.alipay.mobile.framework.LauncherApplicationAgent;

/* compiled from: ServiceUtil */
public class n {
    public static <T> T a(Class cls) {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(cls.getName());
    }

    public static String a(String str) {
        APFileQueryResult queryCacheFile = ((MultimediaFileService) a(MultimediaFileService.class)).queryCacheFile(str);
        if (queryCacheFile == null || TextUtils.isEmpty(queryCacheFile.path)) {
            return null;
        }
        return queryCacheFile.path;
    }
}
