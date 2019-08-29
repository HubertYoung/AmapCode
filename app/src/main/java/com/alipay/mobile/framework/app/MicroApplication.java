package com.alipay.mobile.framework.app;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.MicroContent;

public abstract class MicroApplication implements MicroContent {
    public static final String KEY_APP_CLEAR_TOP = "appClearTop";
    public static final String KEY_APP_SCENE_ID = "ap_framework_sceneId";
    public static final String KEY_APP_START_FROM_EXTERNAL = "startFromExternal";
    private MicroApplicationContext a;
    private String b;
    private String c;
    private String d;
    private String e;
    protected ApplicationDescription mDes;
    protected boolean mIsPrevent = false;
    protected boolean mIsStartFromExternal = false;
    protected String mSceneId;
    protected Bundle mSceneParams;

    public abstract void create(Bundle bundle);

    public abstract String getEntryClassName();

    /* access modifiers changed from: protected */
    public abstract void onCreate(Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void onDestroy(Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void onRestart(Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void onStart();

    /* access modifiers changed from: protected */
    public abstract void onStop();

    public abstract void restart(Bundle bundle);

    public abstract void start();

    public abstract void stop();

    public boolean canRestart(Bundle params) {
        return true;
    }

    public final void setIsPrevent(boolean isPrevent) {
        this.mIsPrevent = isPrevent;
    }

    public boolean isStartFromExternal() {
        return this.mIsStartFromExternal;
    }

    public void setStartFromExternal(boolean isStartFromExternal) {
        this.mIsStartFromExternal = isStartFromExternal;
    }

    public String getParentAppClassName() {
        return this.c;
    }

    public void setParentAppClassName(String parentAppClassName) {
        this.c = parentAppClassName;
    }

    public void setAppId(String appId) {
        this.d = appId;
    }

    public String getAppId() {
        return this.d;
    }

    public Bundle getSceneParams() {
        return this.mSceneParams;
    }

    public void setSceneParams(Bundle sceneParams) {
        this.mSceneParams = sceneParams;
    }

    public void destroy(Bundle params) {
        onDestroy(params);
    }

    public void attachContext(MicroApplicationContext applicationContext) {
        attachContext(applicationContext, null);
    }

    public void attachContext(MicroApplicationContext applicationContext, Bundle sceneParams) {
        this.a = applicationContext;
        this.mSceneParams = sceneParams;
    }

    public MicroApplicationContext getMicroApplicationContext() {
        return this.a;
    }

    public <T> T getServiceByInterface(String className) {
        return this.a.findServiceByInterface(className);
    }

    public String getSourceId() {
        return this.b;
    }

    public void setSourceId(String sourceId) {
        this.b = sourceId;
    }

    public String getReferrer() {
        return this.e;
    }

    public void setReferrer(String referrer) {
        this.e = referrer;
    }

    public void saveState(Editor editor) {
    }

    public void restoreState(SharedPreferences preferences) {
    }

    public String getSceneId() {
        return this.mSceneId;
    }

    public void setSceneId(String sceneId) {
        this.mSceneId = sceneId;
    }

    public ApplicationDescription getmDes() {
        return this.mDes;
    }

    public void setmDes(ApplicationDescription des) {
        this.mDes = des;
    }
}
