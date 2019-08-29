package com.android.dingtalk.share.ddsharemodule.plugin;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;

public class DDVersionCheck {
    public static int getSdkVersionFromMetaData(Context context, int i) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(ShareConstant.DD_APP_PACKAGE, 128);
            if (applicationInfo.metaData != null) {
                return applicationInfo.metaData.getInt(ShareConstant.DD_SDK_VERSION_META_KEY);
            }
            return i;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return i;
        }
    }
}
