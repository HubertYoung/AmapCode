package com.alipay.mobile.core.app.impl;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.alipay.mobile.aspect.FrameworkPointcutExecution;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.core.ApplicationManager;
import com.alipay.mobile.framework.DescriptionManager;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.AppLoadException;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.app.IApplicationEngine;
import com.alipay.mobile.framework.app.IApplicationInstaller;
import com.alipay.mobile.framework.app.IApplicationInstaller.IApplicationInstallCallback;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.fragment.FragmentApplication;
import com.alipay.mobile.framework.util.JSONUtil;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ApplicationManagerImpl implements ApplicationManager {
    public static final String KEY_APPLICATION_MANAGER = "ApplicationManager";
    public static final String KEY_APPLICATION_MANAGER_ENTRY_APP = "ApplicationManager.EntryApp";
    static final String TAG = ApplicationManager.class.getSimpleName();
    private Stack<MicroApplication> a = new Stack<>();
    private List<ApplicationDescription> b = new CopyOnWriteArrayList();
    private Map<String, IApplicationEngine> c = new ConcurrentHashMap();
    private Set<IApplicationInstaller> d = Collections.synchronizedSet(new HashSet());
    private String e;
    private String f;
    /* access modifiers changed from: private */
    public MicroApplicationContext g;

    public void startApp(String sourceAppId, String targetAppId, Bundle params) {
        startApp(sourceAppId, targetAppId, params, null, null);
    }

    public void startApp(String sourceAppId, String targetAppId, Bundle params, FragmentActivity fragmentActivity) {
        startApp(sourceAppId, targetAppId, params, null, null);
    }

    public synchronized void startApp(String sourceAppId, String targetAppId, Bundle startParams, Bundle sceneParams, FragmentActivity fragmentActivity) {
        if (targetAppId == null) {
            throw new RuntimeException("targetAppId should not be null");
        }
        TraceLogger.i(TAG, "startApp(): [sourceAppId=" + sourceAppId + "], [targetAppId=" + targetAppId + "].");
        MicroApplication lastApp = findAppById(targetAppId);
        if (lastApp == null || !lastApp.canRestart(startParams)) {
            a(sourceAppId, targetAppId, startParams, sceneParams, fragmentActivity);
        } else {
            lastApp.setSceneParams(sceneParams);
            a(lastApp, startParams);
        }
    }

    private boolean a(String sourceAppId, String targetAppId, Bundle startParams, Bundle sceneParams, FragmentActivity fragmentActivity) {
        ApplicationDescription targetAppDescription = findDescriptionByAppId(targetAppId);
        TraceLogger.v(TAG, "startApp() targetAppDescription=" + targetAppDescription);
        if (targetAppDescription != null) {
            try {
                a(sourceAppId, targetAppDescription, startParams, sceneParams, fragmentActivity);
                return true;
            } catch (AppLoadException e2) {
                FrameworkMonitor.getInstance(null).handleMicroAppStartupFail(targetAppId, "2004");
                this.g.getStartAppExceptionManager().onStartAppFail(null, targetAppId, "2004");
                TraceLogger.e(TAG, (Throwable) e2);
                return false;
            }
        } else {
            FrameworkMonitor.getInstance(null).handleMicroAppStartupFail(targetAppId, "1000");
            this.g.getStartAppExceptionManager().onStartAppFail(null, targetAppId, "1000");
            return false;
        }
    }

    public MicroApplication createAppById(String appId) {
        ApplicationDescription targetAppDescription = findDescriptionByAppId(appId);
        if (targetAppDescription == null) {
            return null;
        }
        MicroApplication app = a(targetAppDescription, (FragmentActivity) null);
        app.setAppId(targetAppDescription.getAppId());
        app.attachContext(this.g);
        app.setSourceId(appId);
        app.create(null);
        if (!this.a.isEmpty()) {
            this.a.peek().stop();
        }
        this.a.push(app);
        TraceLogger.d(TAG, "=== >createAppById:" + appId + ", stack=" + a());
        return app;
    }

    public void installApp(String sourceAppId, String targetAppId, Bundle startParams, Bundle sceneParams, FragmentActivity fragmentActivity) {
        if (!this.d.isEmpty()) {
            IApplicationInstaller installer = this.d.iterator().next();
            final String str = targetAppId;
            final String str2 = sourceAppId;
            final Bundle bundle = startParams;
            final Bundle bundle2 = sceneParams;
            final FragmentActivity fragmentActivity2 = fragmentActivity;
            IApplicationInstallCallback callback = new IApplicationInstallCallback() {
                public void installed(boolean success) {
                    if (success) {
                        TraceLogger.i(ApplicationManagerImpl.TAG, "Success to install ExternalBundle's Applicaiton for [targetAppId=" + str + "], call startApp() again.");
                        if (ApplicationManagerImpl.this.findDescriptionByAppId(str) == null) {
                            TraceLogger.e(ApplicationManagerImpl.TAG, "What?!! Still failed to find ApplicationDescription by [targetAppId=" + str + "]");
                        } else {
                            ApplicationManagerImpl.this.g.startApp(str2, str, bundle, bundle2, fragmentActivity2);
                        }
                    } else {
                        TraceLogger.w(ApplicationManagerImpl.TAG, "Failed to install ExternalBundle's Applicaiton for [targetAppId=" + str + "]");
                    }
                }

                public void reportEvent(String event, Bundle params) {
                }
            };
            TraceLogger.d(TAG, "Try to install ExternalBundle's Applicaiton for [targetAppId=" + targetAppId + "] using " + installer);
            if (installer != null) {
                installer.installApplication(targetAppId, callback, startParams);
                return;
            }
            return;
        }
        TraceLogger.w(TAG, "Extremely failed to find ApplicationDescription by [targetAppId=" + targetAppId + "], there is no IApplicationInstaller.");
    }

    private void a(String sourceAppId, ApplicationDescription targetAppDes, Bundle startParams, Bundle sceneParams, FragmentActivity fragmentActivity) {
        String sceneId;
        MicroApplication app = a(targetAppDes, sceneParams, fragmentActivity);
        app.setSourceId(sourceAppId);
        MicroApplication referrerApp = getTopRunningApp();
        if (referrerApp != null) {
            app.setReferrer(referrerApp.getAppId());
        }
        app.setmDes(targetAppDes);
        if (startParams != null) {
            try {
                if (startParams.containsKey(MicroApplication.KEY_APP_START_FROM_EXTERNAL)) {
                    Object obj = startParams.get(MicroApplication.KEY_APP_START_FROM_EXTERNAL);
                    if (obj instanceof Boolean) {
                        app.setStartFromExternal(((Boolean) obj).booleanValue());
                    } else if (obj instanceof String) {
                        app.setStartFromExternal(Boolean.parseBoolean((String) obj));
                    }
                }
                if (startParams.containsKey(MicroApplication.KEY_APP_SCENE_ID)) {
                    sceneId = startParams.getString(MicroApplication.KEY_APP_SCENE_ID);
                } else {
                    sceneId = referrerApp != null ? referrerApp.getAppId() : "";
                    startParams.putString(MicroApplication.KEY_APP_SCENE_ID, sceneId);
                }
                app.setSceneId(sceneId);
                TraceLogger.d(TAG, "doStart ap_sceneId = " + sceneId);
            } catch (Throwable throwable) {
                TraceLogger.w(TAG, throwable);
            }
        }
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.APM_FRAMEWORK_FINISHED, null, new Object[]{Long.valueOf(SystemClock.elapsedRealtime()), app.getAppId()});
        app.create(startParams);
        if (!this.a.isEmpty()) {
            this.a.peek().stop();
        }
        this.a.push(app);
        TraceLogger.d(TAG, "=== >doStart:" + targetAppDes + ", stack=" + a());
        app.start();
        FrameworkMonitor.getInstance(null).handleMicroAppStartupSuccess(app.getAppId());
    }

    private void a(MicroApplication app, Bundle params) {
        boolean clearTop = true;
        if (params != null && params.containsKey(MicroApplication.KEY_APP_CLEAR_TOP)) {
            Object c2 = params.get(MicroApplication.KEY_APP_CLEAR_TOP);
            TraceLogger.v(TAG, "doRestart(): appClearTop : " + c2);
            if (c2 instanceof Boolean) {
                clearTop = ((Boolean) c2).booleanValue();
            } else if (c2 instanceof String) {
                clearTop = Boolean.parseBoolean((String) c2);
            } else if (c2 != null) {
                clearTop = Boolean.parseBoolean(c2.toString());
            }
        }
        if (params != null) {
            try {
                if (params.containsKey(MicroApplication.KEY_APP_START_FROM_EXTERNAL)) {
                    Object obj = params.get(MicroApplication.KEY_APP_START_FROM_EXTERNAL);
                    if (obj instanceof Boolean) {
                        app.setStartFromExternal(((Boolean) obj).booleanValue());
                    } else if (obj instanceof String) {
                        app.setStartFromExternal(Boolean.parseBoolean((String) obj));
                    } else {
                        app.setStartFromExternal(false);
                    }
                } else {
                    app.setStartFromExternal(false);
                }
            } catch (Throwable throwable) {
                TraceLogger.w(TAG, throwable);
            }
        }
        if (clearTop) {
            while (!this.a.isEmpty()) {
                MicroApplication tmp = this.a.peek();
                if (app == tmp) {
                    break;
                }
                this.a.pop();
                TraceLogger.v(TAG, "doRestart() pop appId: " + tmp.getAppId());
                tmp.destroy(params);
            }
        } else {
            Stack tmpStack = new Stack();
            while (!this.a.isEmpty() && app != this.a.peek()) {
                tmpStack.push(this.a.pop());
            }
            if (!this.a.isEmpty() && !tmpStack.isEmpty()) {
                this.a.pop();
                while (!tmpStack.isEmpty()) {
                    this.a.push(tmpStack.pop());
                }
                this.a.peek().stop();
                this.a.push(app);
                TraceLogger.v(TAG, "doRestart() bring appId: " + app.getAppId() + " to top");
            }
        }
        TraceLogger.d(TAG, "doRestart:" + app.getAppId() + ", clearTop=" + clearTop + ", stack=" + a());
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.APM_FRAMEWORK_FINISHED, null, new Object[]{Long.valueOf(SystemClock.elapsedRealtime()), app.getAppId()});
        app.restart(params);
        FrameworkMonitor.getInstance(null).handleMicroAppStartupSuccess(app.getAppId());
    }

    private MicroApplication a(ApplicationDescription targetAppDes, Bundle sceneParams, FragmentActivity fragmentActivity) {
        MicroApplication app = a(targetAppDes, fragmentActivity);
        app.setAppId(targetAppDes.getAppId());
        app.attachContext(this.g, sceneParams);
        return app;
    }

    private MicroApplication a(ApplicationDescription targetAppDes, FragmentActivity fragmentActivity) {
        MicroApplication app = null;
        String engineType = targetAppDes.getEngineType();
        if (!TextUtils.isEmpty(engineType)) {
            IApplicationEngine engine = this.c.get(engineType);
            if (engine != null) {
                app = engine.createApplication();
            } else {
                TraceLogger.v(TAG, "createApplicationByDescription(): [IApplicationEngine engine == null, engineType= " + engineType + "]");
            }
        } else {
            TraceLogger.v(TAG, (String) "createApplicationByDescription(): [targetAppDes.engineType is empty]");
        }
        if (app != null) {
            return app;
        }
        TraceLogger.d(TAG, (String) "createApplicationByDescription(): [failed to create application by IApplicationEngine]");
        Object object = null;
        String targetAppClassName = targetAppDes.getClassName();
        try {
            ClassLoader classLoader = targetAppDes.getClassLoader();
            if (classLoader != null) {
                Class clazz = classLoader.loadClass(targetAppClassName);
                if (clazz != null) {
                    if (fragmentActivity == null) {
                        object = clazz.newInstance();
                    } else {
                        Constructor constructor = clazz.getConstructor(new Class[]{FragmentActivity.class});
                        constructor.setAccessible(true);
                        object = constructor.newInstance(new Object[]{fragmentActivity});
                    }
                }
            }
            if (object instanceof MicroApplication) {
                return (MicroApplication) object;
            }
            throw new AppLoadException("App " + targetAppClassName + " is not a App");
        } catch (Throwable e2) {
            FrameworkMonitor.getInstance(null).handleDescriptionInitFail(targetAppDes, e2);
            throw new AppLoadException("App Exception: " + e2);
        }
    }

    public void finishApp(String sourceAppId, String targetId, Bundle params) {
        for (MicroApplication app : findAppsById(targetId)) {
            TraceLogger.d(TAG, "finishApp(): App: [targetId=" + targetId + "] => destroy.");
            app.destroy(params);
        }
    }

    public List<MicroApplication> findAppsById(String appId) {
        List foundApps = new ArrayList();
        synchronized (this.a) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                MicroApplication app = (MicroApplication) it.next();
                if (app != null && appId.equals(app.getAppId())) {
                    foundApps.add(app);
                }
            }
        }
        return foundApps;
    }

    public MicroApplication findAppById(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return null;
        }
        Stack lookupAppStack = new Stack();
        synchronized (this.a) {
            lookupAppStack.addAll(this.a);
        }
        while (!lookupAppStack.isEmpty()) {
            MicroApplication app = (MicroApplication) lookupAppStack.pop();
            if (app != null && appId.equals(app.getAppId())) {
                return app;
            }
        }
        return null;
    }

    private ApplicationDescription a(String appName) {
        if (this.b != null && !TextUtils.isEmpty(appName)) {
            for (ApplicationDescription description : this.b) {
                if (description != null && appName.equalsIgnoreCase(description.getName())) {
                    return description;
                }
            }
        }
        return null;
    }

    public ApplicationDescription findDescriptionByAppId(String appId) {
        ApplicationDescription ret = null;
        Iterator<ApplicationDescription> it = this.b.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ApplicationDescription description = it.next();
            if (appId.equalsIgnoreCase(description.getAppId())) {
                ret = description;
                break;
            }
        }
        if (ret != null) {
            return ret;
        }
        String bundleName = DescriptionManager.getInstance().getBundleNameByAppId(appId);
        if (DescriptionManager.getInstance().isLazyBundle(bundleName)) {
            LauncherApplicationAgent.getInstance().getBundleContext().loadBundle(bundleName);
        }
        Iterator<ApplicationDescription> it2 = this.b.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            ApplicationDescription description2 = it2.next();
            if (appId.equalsIgnoreCase(description2.getAppId())) {
                ret = description2;
                break;
            }
        }
        if (ret != null) {
            return ret;
        }
        List appDesList = DescriptionManager.getInstance().findApplicationDescription(appId);
        if (appDesList.size() <= 0) {
            return ret;
        }
        ApplicationDescription description3 = appDesList.get(0);
        this.b.add(description3);
        return description3;
    }

    public void addDescription(ApplicationDescription... descriptions) {
        if (descriptions != null && descriptions.length > 0) {
            this.b.addAll(Arrays.asList(descriptions));
        }
    }

    public void startEntryApp(Bundle params) {
        ApplicationDescription description = null;
        if (this.f != null) {
            description = findDescriptionByAppId(this.f);
        }
        if (description == null && this.e != null) {
            description = a(this.e);
        }
        if (description == null) {
            AppLoadException e2 = new AppLoadException("startEntryApp(): description==null, mEntryApp=" + this.e);
            TraceLogger.e(TAG, (Throwable) e2);
            throw e2;
        }
        String appId = description.getAppId();
        if (TextUtils.isEmpty(appId)) {
            AppLoadException e3 = new AppLoadException("startEntryApp(): description=" + description + ", mEntryApp=" + this.e);
            TraceLogger.e(TAG, (Throwable) e3);
            throw e3;
        }
        startApp(null, appId, params);
    }

    public void setEntryAppName(String appName) {
        if (TextUtils.isEmpty(appName)) {
            TraceLogger.i(TAG, "setEntryAppName:" + appName + " : ignore.");
            return;
        }
        this.e = appName;
        TraceLogger.i(TAG, "setEntryAppName:" + appName);
    }

    public String getEntryAppName() {
        return this.e;
    }

    public void setEntryAppId(String appId) {
        this.f = appId;
    }

    public void attachContext(MicroApplicationContext applicationContext) {
        this.g = applicationContext;
    }

    public void exit() {
        while (!this.a.isEmpty()) {
            MicroApplication microApplication = this.a.pop();
            TraceLogger.v(TAG, "exit() pop appId: " + microApplication.getAppId());
            microApplication.destroy(null);
        }
    }

    public void clear() {
        this.a.clear();
        TraceLogger.i(TAG, (String) "clear");
    }

    public void onDestroyApp(MicroApplication microApplication) {
        this.a.remove(microApplication);
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.AFTER_DESTROY_APP, null, new Object[]{Long.valueOf(SystemClock.elapsedRealtime()), microApplication.getAppId()});
        TraceLogger.d(TAG, "=== >onDestroyApp:" + microApplication.getAppId() + ", stack=" + a());
    }

    public void clearTop(MicroApplication microApplication) {
        MicroApplication tmp = this.a.peek();
        if (microApplication != tmp) {
            this.a.pop();
            TraceLogger.v(TAG, "clearTop() pop appId: " + tmp.getAppId());
        }
        TraceLogger.d(TAG, "=== >clearTop, stack=" + a());
    }

    public MicroApplication getTopRunningApp() {
        if (!this.a.isEmpty()) {
            return this.a.peek();
        }
        return null;
    }

    public void saveState(Editor editor) {
        List appIds = new ArrayList();
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            try {
                MicroApplication application = (MicroApplication) this.a.get(i);
                appIds.add(application.getAppId());
                application.saveState(editor);
            } catch (Throwable e2) {
                TraceLogger.e(TAG, e2);
            }
        }
        String appMgr = JSONUtil.list2Json(appIds);
        editor.putString(KEY_APPLICATION_MANAGER, appMgr);
        TraceLogger.d(TAG, "saveState.appMgr=" + appMgr);
        editor.putString(KEY_APPLICATION_MANAGER_ENTRY_APP, this.e);
        TraceLogger.d(TAG, "saveState.entryApp=" + this.e);
    }

    @Deprecated
    public void restoreState(SharedPreferences preferences) {
        restoreState(preferences, true);
    }

    public void restoreState(SharedPreferences preferences, boolean foreground) {
        String entryApp = preferences.getString(KEY_APPLICATION_MANAGER_ENTRY_APP, null);
        setEntryAppName(entryApp);
        String appMgr = preferences.getString(KEY_APPLICATION_MANAGER, null);
        TraceLogger.d(TAG, "restoreState appMgr=" + appMgr + ", entryApp=" + entryApp);
        if (appMgr != null) {
            for (String appId : JSONUtil.json2List(appMgr)) {
                try {
                    ApplicationDescription targetAppDes = findDescriptionByAppId(appId);
                    MicroApplication application = null;
                    if (targetAppDes != null) {
                        application = a(targetAppDes, null, null);
                    }
                    if (application == null || (application instanceof FragmentApplication)) {
                        TraceLogger.d(TAG, "skip restoreState=" + application);
                    } else {
                        application.setSourceId(appId);
                        if (foreground) {
                            application.create(null);
                        }
                        application.restoreState(preferences);
                        this.a.push(application);
                    }
                } catch (Throwable exception) {
                    TraceLogger.e(TAG, exception);
                }
            }
        }
        TraceLogger.d(TAG, "=== >restoreState, stack=" + a());
    }

    public boolean deleteDescriptionByAppId(String... appIds) {
        boolean bRet = true;
        for (String appId : appIds) {
            TraceLogger.i(TAG, "deleteDescriptionByAppId(appId=" + appId + ")");
            boolean remove = false;
            if (!TextUtils.isEmpty(appId)) {
                Iterator<ApplicationDescription> it = this.b.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ApplicationDescription description = it.next();
                    if (description != null && appId.equalsIgnoreCase(description.getAppId())) {
                        this.b.remove(description);
                        remove = true;
                        break;
                    }
                }
            }
            bRet &= remove;
        }
        return bRet;
    }

    public boolean updateDescription(ApplicationDescription des) {
        if (des == null) {
            return false;
        }
        String appId = des.getAppId();
        if (TextUtils.isEmpty(appId)) {
            return false;
        }
        for (ApplicationDescription description : this.b) {
            if (description != null && appId.equalsIgnoreCase(description.getAppId())) {
                String name = des.getName();
                if (!TextUtils.isEmpty(name)) {
                    description.setName(name);
                }
                String clsName = des.getClassName();
                if (!TextUtils.isEmpty(clsName)) {
                    description.setClassName(clsName);
                }
                ClassLoader classLoader = des.getClassLoader();
                if (classLoader != null) {
                    description.setClassLoader(classLoader);
                }
                return true;
            }
        }
        return false;
    }

    public boolean registerApplicationEngine(String engineType, IApplicationEngine engine) {
        if (TextUtils.isEmpty(engineType)) {
            throw new IllegalArgumentException("engineType can't be empty");
        } else if (engine == null) {
            throw new IllegalArgumentException("engine can't be null");
        } else {
            TraceLogger.i(TAG, "registerApplicationEngine(engineType" + engineType + ")");
            this.c.put(engineType, engine);
            return true;
        }
    }

    public boolean unregisterApplicationEngine(String engineType) {
        if (!TextUtils.isEmpty(engineType)) {
            return this.c.remove(engineType) != null;
        }
        throw new IllegalArgumentException("engineType can't be empty");
    }

    public int getActiveActivityCount() {
        int count = 0;
        MicroApplication[] apps = new MicroApplication[this.a.size()];
        this.a.toArray(apps);
        for (MicroApplication app : apps) {
            if (app instanceof ActivityApplication) {
                count += ((ActivityApplication) app).getActiveActivityCount();
            }
        }
        return count;
    }

    public boolean registerApplicationInstaller(IApplicationInstaller installer) {
        return this.d.add(installer);
    }

    public boolean unregisterApplicationInstaller(IApplicationInstaller installer) {
        return this.d.remove(installer);
    }

    private String a() {
        StringBuilder strBuilder = new StringBuilder(10);
        synchronized (this.a) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                MicroApplication app = (MicroApplication) it.next();
                if (app != null) {
                    strBuilder.append(app.getAppId()).append(" @ ").append(app.getClass().getName()).append(10);
                }
            }
        }
        return strBuilder.toString();
    }
}
