package com.autonavi.minimap.bundle.splashscreen.api;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import com.autonavi.minimap.bundle.featureguide.api.GuideStartType;
import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface ISplashManager {
    public static final String AFP_EXTERNAL_URL = "afp_external_url";
    public static final String AFP_EXTERNAL_URL_FLAG = "afp_external_url_flag";
    public static final String AFP_INVALID_URL = "afp_invalid_url";
    public static final String AFP_INVALID_URL_FLAG = "afp_invalid_url_flag";
    public static final String AFP_WEBVIEW_URL = "afp_webview_url";
    public static final String AFP_WEBVIEW_URL_FLAG = "afp_webview_url_flag";
    public static final String Disclaimer = "Disclaimer";
    public static final String FROM_GUIDE_VIEW = "from_guide_view";

    boolean getIsGuideViewCreated();

    void initAfpSplash();

    void initUserGuideSplash(GuideStartType guideStartType);

    void setMapStartListener(ddp ddp);

    void setSplashActivity(Activity activity);

    void setSplashActivityFragmentManager(FragmentManager fragmentManager);

    void setSplashOnDestroy(boolean z);

    void setSplashOnPause(boolean z);

    void setSplashOnRestart(boolean z);

    void updateSplashData(boolean z);
}
