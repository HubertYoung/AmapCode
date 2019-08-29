package com.alipay.mobile.core;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.app.IApplicationEngine;
import com.alipay.mobile.framework.app.IApplicationInstaller;
import com.alipay.mobile.framework.app.MicroApplication;
import java.util.List;

public interface ApplicationManager {
    void addDescription(ApplicationDescription... applicationDescriptionArr);

    void clear();

    void clearTop(MicroApplication microApplication);

    MicroApplication createAppById(String str);

    boolean deleteDescriptionByAppId(String... strArr);

    void exit();

    MicroApplication findAppById(String str);

    List<MicroApplication> findAppsById(String str);

    ApplicationDescription findDescriptionByAppId(String str);

    void finishApp(String str, String str2, Bundle bundle);

    int getActiveActivityCount();

    @Deprecated
    String getEntryAppName();

    MicroApplication getTopRunningApp();

    void installApp(String str, String str2, Bundle bundle, Bundle bundle2, FragmentActivity fragmentActivity);

    void onDestroyApp(MicroApplication microApplication);

    boolean registerApplicationEngine(String str, IApplicationEngine iApplicationEngine);

    boolean registerApplicationInstaller(IApplicationInstaller iApplicationInstaller);

    @Deprecated
    void restoreState(SharedPreferences sharedPreferences);

    void restoreState(SharedPreferences sharedPreferences, boolean z);

    void saveState(Editor editor);

    void setEntryAppId(String str);

    @Deprecated
    void setEntryAppName(String str);

    void startApp(String str, String str2, Bundle bundle);

    void startApp(String str, String str2, Bundle bundle, Bundle bundle2, FragmentActivity fragmentActivity);

    void startApp(String str, String str2, Bundle bundle, FragmentActivity fragmentActivity);

    void startEntryApp(Bundle bundle);

    boolean unregisterApplicationEngine(String str);

    boolean unregisterApplicationInstaller(IApplicationInstaller iApplicationInstaller);

    boolean updateDescription(ApplicationDescription applicationDescription);
}
