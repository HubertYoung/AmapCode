package com.alipay.mobile.beehive.api;

import android.content.Context;
import java.util.HashMap;

public interface LocationPermissionSettingExecutor {
    boolean isHasLocation(Context context);

    boolean isSupportGuide(Context context);

    void jumpPermissionPage(Context context);

    boolean locPerSetting(Context context, LocationPerSettingListenerProxy locationPerSettingListenerProxy, HashMap<String, String> hashMap);
}
