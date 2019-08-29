package com.alipay.mobile.framework;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.alipay.mobile.framework.LauncherApplicationAgent.ExceptionHandlerAgent;
import com.alipay.mobile.framework.LauncherApplicationAgent.StandardExceptionHandlerAgent;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.app.IApplicationEngine;
import com.alipay.mobile.framework.app.IApplicationInstaller;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.loading.LoadingPageManager;
import com.alipay.mobile.framework.performance.StartAppExceptionManager;
import com.alipay.mobile.framework.permission.RequestPermissionsResultCallback;
import com.alipay.mobile.framework.pipeline.Pipeline;
import com.alipay.mobile.framework.service.ServiceDescription;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MicroApplicationContext {
    void Alert(String str, String str2, String str3, OnClickListener onClickListener, String str4, OnClickListener onClickListener2);

    void Toast(String str, int i);

    void addDescription(ApplicationDescription... applicationDescriptionArr);

    void attachContext(Application application, ExceptionHandlerAgent exceptionHandlerAgent);

    @Deprecated
    void background();

    void background(Activity activity);

    void clearState();

    void clearTopApps();

    MicroApplication createAppById(String str);

    boolean deleteDescriptionByAppId(String... strArr);

    void dismissProgressDialog();

    void exit();

    MicroApplication findAppById(String str);

    List<MicroApplication> findAppsById(String str);

    ApplicationDescription findDescriptionByAppId(String str);

    <T> T findServiceByInterface(String str);

    MicroApplication findTopRunningApp();

    boolean finishAllActivities(Activity activity);

    void finishAllApps();

    void finishApp(String str, String str2, Bundle bundle);

    int getActiveActivityCount();

    Application getApplicationContext();

    @Deprecated
    <T extends ExternalService> T getExtServiceByInterface(String str);

    Map<String, Set<String>> getLazyBundles();

    LoadingPageManager getLoadingPageManager();

    Pipeline getPipelineByName(String str);

    Pipeline getPipelineByName(String str, long j);

    StartAppExceptionManager getStartAppExceptionManager();

    WeakReference<Activity> getTopActivity();

    ActivityApplication getTopApplication();

    boolean hasInited();

    void initSerivces();

    void installApp(String str, String str2, Bundle bundle, Bundle bundle2, FragmentActivity fragmentActivity);

    void loadBundle(String str);

    void onDestroyContent(MicroContent microContent);

    void onWindowFocus(MicroApplication microApplication);

    void postInit();

    void preload(Application application);

    boolean registerApplicationEngine(String str, IApplicationEngine iApplicationEngine);

    boolean registerApplicationInstaller(IApplicationInstaller iApplicationInstaller);

    void registerExceptionHandlerAgent(StandardExceptionHandlerAgent standardExceptionHandlerAgent);

    void registerExternalService(ServiceDescription serviceDescription);

    <T> boolean registerService(String str, T t);

    @TargetApi(23)
    void requestPermissions(Activity activity, String[] strArr, int i, RequestPermissionsResultCallback requestPermissionsResultCallback);

    @TargetApi(23)
    void requestPermissions(String[] strArr, int i, RequestPermissionsResultCallback requestPermissionsResultCallback);

    @Deprecated
    void restoreState();

    void restoreState(boolean z);

    void saveState();

    void setStartActivityContext(Context context);

    void showProgressDialog(String str);

    void showProgressDialog(String str, boolean z, OnCancelListener onCancelListener);

    void startActivity(MicroApplication microApplication, Intent intent);

    void startActivity(MicroApplication microApplication, String str);

    void startActivityForResult(MicroApplication microApplication, Intent intent, int i);

    void startActivityForResult(MicroApplication microApplication, String str, int i);

    void startActivityFromFragment(MicroApplication microApplication, Fragment fragment, Intent intent, int i);

    void startActivityFromFragment(MicroApplication microApplication, Fragment fragment, String str, int i);

    void startApp(String str, String str2, Bundle bundle);

    void startApp(String str, String str2, Bundle bundle, Bundle bundle2, FragmentActivity fragmentActivity);

    void startApp(String str, String str2, Bundle bundle, FragmentActivity fragmentActivity);

    void startEntryApp(Bundle bundle);

    void startExtActivity(MicroApplication microApplication, Intent intent);

    void startExtActivityForResult(MicroApplication microApplication, Intent intent, int i);

    void startExtActivityFromFragment(MicroApplication microApplication, Fragment fragment, Intent intent, int i);

    boolean unregisterApplicationEngine(String str);

    boolean unregisterApplicationInstaller(IApplicationInstaller iApplicationInstaller);

    void unregisterReceiver(BroadcastReceiver broadcastReceiver);

    <T> T unregisterService(String str);

    Activity updateActivity(Activity activity);

    boolean updateDescription(ApplicationDescription applicationDescription);
}
