package com.alipay.mobile.core.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.mobile.aspect.FrameworkPointcutCall;
import com.alipay.mobile.aspect.FrameworkPointcutExecution;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.core.ApplicationManager;
import com.alipay.mobile.core.ServiceManager;
import com.alipay.mobile.core.app.impl.ApplicationManagerImpl;
import com.alipay.mobile.core.app.impl.LocalBroadcastManagerWrapper;
import com.alipay.mobile.core.exception.FrameworkExceptionHandler;
import com.alipay.mobile.core.init.BootLoader;
import com.alipay.mobile.core.init.impl.BootLoaderImpl;
import com.alipay.mobile.core.loading.impl.LoadingPageManagerImpl;
import com.alipay.mobile.core.pipeline.impl.PipelineManager;
import com.alipay.mobile.core.service.impl.ServiceManagerImpl;
import com.alipay.mobile.framework.DescriptionManager;
import com.alipay.mobile.framework.FrameworkMonitor;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.LauncherApplicationAgent.ExceptionHandlerAgent;
import com.alipay.mobile.framework.LauncherApplicationAgent.StandardExceptionHandlerAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.MicroContent;
import com.alipay.mobile.framework.R;
import com.alipay.mobile.framework.app.ActivityApplication;
import com.alipay.mobile.framework.app.AppLoadException;
import com.alipay.mobile.framework.app.ApplicationDescription;
import com.alipay.mobile.framework.app.IApplicationEngine;
import com.alipay.mobile.framework.app.IApplicationInstaller;
import com.alipay.mobile.framework.app.MicroApplication;
import com.alipay.mobile.framework.app.fragment.FragmentApplication;
import com.alipay.mobile.framework.app.ui.ActivityCollections;
import com.alipay.mobile.framework.app.ui.ActivityHelper;
import com.alipay.mobile.framework.app.ui.ActivityResponsable;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.alipay.mobile.framework.exception.StartActivityRecord;
import com.alipay.mobile.framework.loading.LoadingPageManager;
import com.alipay.mobile.framework.performance.StartAppExceptionManager;
import com.alipay.mobile.framework.permission.RequestPermissionsResultCallback;
import com.alipay.mobile.framework.pipeline.Pipeline;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.ActivityLifecycleCallback;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.AppCheckUtil;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.ExceptionHandler;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.PerformanceHelper;
import com.alipay.mobile.framework.quinoxless.QuinoxUtilsDecorator.UcNativeCrashApi;
import com.alipay.mobile.framework.quinoxless.QuinoxlessFramework;
import com.alipay.mobile.framework.service.MicroService;
import com.alipay.mobile.framework.service.ServiceDescription;
import com.alipay.mobile.framework.service.ext.ExternalService;
import com.alipay.mobile.framework.service.ext.ExternalServiceManager;
import com.alipay.mobile.quinox.startup.UpgradeHelper;
import com.alipay.mobile.quinox.startup.UpgradeHelper.UpgradeEnum;
import com.alipay.mobile.quinox.utils.MonitorLogger;
import com.alipay.mobile.quinox.utils.SharedPreferenceUtil;
import com.alipay.mobile.quinox.utils.StringUtil;
import com.alipay.mobile.quinox.utils.TimingLogger;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class MicroApplicationContextImpl implements MicroApplicationContext {
    public static final String KEY_STATE_FLAG = "@@";
    public static final String MICROAPPLICATIONCONTEXTIMPL_WORKTHREAD = "MicroApplicationContextImpl_WorkThread";
    public static final String SHARE_PREF_STATES = "_share_tmp_";
    static final String TAG = "MicroAppContextImpl";
    private final AtomicBoolean a = new AtomicBoolean(false);
    private final Handler b = new Handler(Looper.getMainLooper());
    private Handler c;
    /* access modifiers changed from: private */
    public MACWorkerMonitor d;
    /* access modifiers changed from: private */
    public Application e;
    /* access modifiers changed from: private */
    public Activity f;
    /* access modifiers changed from: private */
    public WeakReference<AlertDialog> g = null;
    private ServiceManager h;
    /* access modifiers changed from: private */
    public ApplicationManager i;
    private PipelineManager j;
    private LocalBroadcastManagerWrapper k;
    private LoadingPageManager l;
    private BootLoader m;
    private final AppExitHelper n = new AppExitHelper();
    /* access modifiers changed from: private */
    public final StartAppExceptionManager o = new StartAppExceptionManager();
    private WeakReference<Context> p = new WeakReference<>(null);

    public class APGenericProgressDialog extends AlertDialog {
        private ProgressBar a;
        private TextView b;
        private CharSequence c;
        private boolean d;
        private boolean e;

        public APGenericProgressDialog(Context context) {
            super(context);
        }

        public APGenericProgressDialog(Context context, int theme) {
            super(context, theme);
        }

        /* access modifiers changed from: protected */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.generic_progress_dialog);
            this.a = (ProgressBar) findViewById(16908301);
            this.b = (TextView) findViewById(R.id.message);
            a();
            setIndeterminate(this.d);
        }

        private void a() {
            int i = 8;
            this.b.setText(this.c);
            if (this.c == null || "".equals(this.c)) {
                this.b.setVisibility(8);
            }
            ProgressBar progressBar = this.a;
            if (this.e) {
                i = 0;
            }
            progressBar.setVisibility(i);
        }

        public void setMessage(CharSequence message) {
            this.c = message;
        }

        public void setProgressVisiable(boolean progressVisiable) {
            this.e = progressVisiable;
        }

        public void setIndeterminate(boolean indeterminate) {
            if (this.a != null) {
                this.a.setIndeterminate(indeterminate);
            } else {
                this.d = indeterminate;
            }
        }
    }

    static class SafetyRunnable implements Runnable {
        private final Runnable a;

        public SafetyRunnable(Runnable runnable) {
            this.a = runnable;
        }

        public void run() {
            try {
                this.a.run();
            } catch (Throwable e) {
                TraceLogger.w((String) MicroApplicationContextImpl.TAG, e);
            }
        }
    }

    public Activity updateActivity(Activity activity) {
        Activity temp = this.f;
        this.f = activity;
        return temp;
    }

    public void registerExceptionHandlerAgent(StandardExceptionHandlerAgent agent) {
        FrameworkExceptionHandler.getInstance().registerExceptionHandlerAgent(agent);
    }

    public void preload(Application application) {
        this.e = application;
        ActivityCollections.createInstance();
        DescriptionManager.createInstance(application);
        a(true);
    }

    public void attachContext(Application application, ExceptionHandlerAgent agent) {
        this.e = application;
        TraceLogger.w((String) TAG, "attachContext(" + application + ")");
        this.n.init(this.e, this);
        if (!QuinoxlessFramework.isQuinoxlessMode()) {
            ExceptionHandler.stop();
            FrameworkExceptionHandler.getInstance().init(this.e, this.n, agent);
        }
        ActivityCollections.createInstance();
        TimingLogger.getBootLogger().addSplit("t_maac1");
        DescriptionManager.createInstance(application);
        TimingLogger.getBootLogger().addSplit("t_maac2");
        ActivityLifecycleCallback.setBackgroundCallback(new ActivityAllStoppedCallback(this.e));
        a(false);
    }

    /* access modifiers changed from: 0000 */
    public Handler getWorkThreadHandle() {
        if (this.c != null) {
            return this.c;
        }
        synchronized (this) {
            if (this.c != null) {
                Handler handler = this.c;
                return handler;
            }
            HandlerThread workerThread = new HandlerThread(MICROAPPLICATIONCONTEXTIMPL_WORKTHREAD);
            workerThread.setPriority(10);
            workerThread.start();
            Handler handler2 = new Handler(workerThread.getLooper());
            this.d = new MACWorkerMonitor();
            this.d.setWorkerThread(workerThread);
            handler2.getLooper().setMessageLogging(this.d);
            this.c = handler2;
            Handler handler3 = this.c;
            return handler3;
        }
    }

    private void a(boolean preload) {
        if (this.h == null) {
            ServiceManagerImpl serviceManagerImpl = new ServiceManagerImpl();
            serviceManagerImpl.attachContext(this);
            this.h = serviceManagerImpl;
        }
        if (this.i == null) {
            ApplicationManagerImpl applicationManager = new ApplicationManagerImpl();
            applicationManager.attachContext(this);
            this.i = applicationManager;
            this.h.registerService(ApplicationManager.class.getName(), this.i);
        }
        if (this.j == null) {
            this.j = new PipelineManager();
        }
        if (this.k == null) {
            this.k = LocalBroadcastManagerWrapper.getInstance(this.e);
            this.h.registerService(LocalBroadcastManagerWrapper.class.getName(), this.k);
        }
        if (this.l == null) {
            this.l = new LoadingPageManagerImpl(this);
        }
        if (this.m == null) {
            this.m = new BootLoaderImpl(this);
        }
        if (preload) {
            this.m.preload();
            return;
        }
        TimingLogger.getBootLogger().addSplit("t_mainit1");
        this.m.load();
        TraceLogger.w((String) TAG, (String) "startup : Boot finish (in framework, ga ga ga test).");
        this.a.set(true);
    }

    public LoadingPageManager getLoadingPageManager() {
        return this.l;
    }

    public Application getApplicationContext() {
        return this.e;
    }

    public void loadBundle(String bundleName) {
        this.m.loadBundle(bundleName);
    }

    private MicroApplication a(MicroApplication microApplication, Activity activeActivity) {
        MicroApplication appRet = microApplication;
        if (!(microApplication instanceof FragmentApplication)) {
            return appRet;
        }
        if (activeActivity instanceof BaseActivity) {
            return ((BaseActivity) activeActivity).getActivityApplication();
        }
        if (activeActivity instanceof BaseFragmentActivity) {
            return ((BaseFragmentActivity) activeActivity).getActivityApplication();
        }
        MicroApplication app = microApplication;
        while (true) {
            if (!(app instanceof FragmentApplication)) {
                break;
            }
            String sourceAppId = app.getSourceId();
            if (TextUtils.isEmpty(sourceAppId)) {
                app = microApplication;
                break;
            }
            app = this.i.findAppById(sourceAppId);
        }
        if (app != null) {
            return app;
        }
        return appRet;
    }

    public void startActivity(MicroApplication microApplication, String className) {
        Object[] args = {microApplication, className};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITY1, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITY1, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            Context validStartActivityContext = a();
            if (!((this.f == null && validStartActivityContext == null) || microApplication == null || TextUtils.isEmpty(className))) {
                MicroApplication microApplication2 = a(microApplication, this.f);
                ComponentName componentName = new ComponentName(this.e.getPackageName(), className);
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.addFlags(262144);
                a(intent);
                intent.putExtra("app_id", microApplication2.getAppId());
                microApplication2.setIsPrevent(true);
                try {
                    if (!(this.f instanceof BaseActivity) && !(this.f instanceof BaseFragmentActivity)) {
                        TraceLogger.w((String) TAG, (Throwable) new StartActivityRecord("startActivity(app=" + microApplication2 + ", className=" + className + ")"));
                    }
                    if (validStartActivityContext != null) {
                        try {
                            Intent pushIntent = new Intent(intent);
                            pushIntent.addFlags(268435456);
                            validStartActivityContext.startActivity(pushIntent);
                        } catch (Exception e2) {
                            this.f.startActivity(intent);
                        }
                        setStartActivityContext(null);
                    } else {
                        this.f.startActivity(intent);
                    }
                } catch (ActivityNotFoundException e3) {
                    TraceLogger.w((String) TAG, (Throwable) new RuntimeException("Start ActivityShell.", e3));
                    microApplication2.setIsPrevent(false);
                    throw new RuntimeException("Failed to startActivity(app=" + microApplication2 + ", className=" + className + ")", e3);
                }
            }
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITY1, this, args);
    }

    private static void a(Intent intent) {
        if (intent != null) {
            Bundle mExtras = intent.getExtras();
            if (mExtras != null) {
                intent.replaceExtras(new Bundle());
                intent.putExtra("mExtras", mExtras);
                return;
            }
            Log.i(TAG, "moveExtras(mExtras == null)");
            return;
        }
        Log.w(TAG, "moveExtras(intent == null)");
    }

    public void startActivityFromFragment(MicroApplication microApplication, Fragment fragment, String className, int requestCode) {
        Object[] args = {microApplication, fragment, className, Integer.valueOf(requestCode)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFROMFRAGMENT1, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFROMFRAGMENT1, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(microApplication, fragment, className, requestCode);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFROMFRAGMENT1, this, args);
    }

    public void startActivityForResult(MicroApplication microApplication, String className, int requestCode) {
        Object[] args = {microApplication, className, Integer.valueOf(requestCode)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFORRESULT1, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFORRESULT1, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(microApplication, (Fragment) null, className, requestCode);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFORRESULT1, this, args);
    }

    private void a(MicroApplication microApplication, Fragment fragment, String className, int requestCode) {
        if (this.f != null && microApplication != null && !TextUtils.isEmpty(className)) {
            MicroApplication microApplication2 = a(microApplication, this.f);
            ComponentName componentName = new ComponentName(this.e.getPackageName(), className);
            Intent intent = new Intent();
            intent.setComponent(componentName);
            intent.addFlags(262144);
            a(intent);
            intent.putExtra("app_id", microApplication2.getAppId());
            microApplication2.setIsPrevent(true);
            try {
                if (!(this.f instanceof BaseActivity) && !(this.f instanceof BaseFragmentActivity)) {
                    TraceLogger.w((String) TAG, (Throwable) new StartActivityRecord("startActivityForResult(app=" + microApplication2 + ", className=" + className + ", code=" + requestCode + ")"));
                }
                if (fragment == null || !(this.f instanceof FragmentActivity)) {
                    this.f.startActivityForResult(intent, requestCode);
                } else {
                    ((FragmentActivity) this.f).startActivityFromFragment(fragment, intent, requestCode);
                }
            } catch (ActivityNotFoundException e2) {
                TraceLogger.w((String) TAG, (Throwable) new RuntimeException("Start ActivityShell.", e2));
                microApplication2.setIsPrevent(false);
                throw new RuntimeException("Failed to startActivityForResult(app=" + microApplication2 + ", className=" + className + ", code=" + requestCode + ")", e2);
            }
        }
    }

    public void startActivity(MicroApplication microApplication, Intent intent) {
        Object[] args = {microApplication, intent};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITY2, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITY2, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            Context validStartActivityContext = a();
            if (!((this.f == null && validStartActivityContext == null) || microApplication == null || intent == null)) {
                MicroApplication microApplication2 = a(microApplication, this.f);
                intent.addFlags(262144);
                a(intent);
                intent.putExtra("app_id", microApplication2.getAppId());
                microApplication2.setIsPrevent(true);
                try {
                    if (!(this.f instanceof BaseActivity) && !(this.f instanceof BaseFragmentActivity)) {
                        TraceLogger.w((String) TAG, (Throwable) new StartActivityRecord("startActivity(app=" + microApplication2 + ", intent=" + intent + ")"));
                    }
                    if (validStartActivityContext != null) {
                        try {
                            Intent pushIntent = new Intent(intent);
                            pushIntent.addFlags(268435456);
                            validStartActivityContext.startActivity(pushIntent);
                        } catch (Exception e2) {
                            this.f.startActivity(intent);
                        }
                        setStartActivityContext(null);
                    } else {
                        this.f.startActivity(intent);
                    }
                } catch (ActivityNotFoundException e3) {
                    throw e3;
                } catch (ActivityNotFoundException e4) {
                    TraceLogger.w((String) TAG, (Throwable) new RuntimeException("Start ActivityShell.", e4));
                    microApplication2.setIsPrevent(false);
                    throw new RuntimeException("Failed to startActivity(app=" + microApplication2 + ", intent=" + intent + ")", e4);
                } catch (Throwable e5) {
                    TraceLogger.w((String) TAG, e5);
                    microApplication2.setIsPrevent(false);
                }
            }
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITY2, this, args);
    }

    public void startExtActivity(MicroApplication microApplication, Intent intent) {
        Object[] args = {microApplication, intent};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITY, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITY, this, args);
        if ((aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) && intent != null) {
            if (this.f != null) {
                intent.addFlags(262144);
                if (!(this.f instanceof BaseActivity) && !(this.f instanceof BaseFragmentActivity)) {
                    TraceLogger.w((String) TAG, (Throwable) new StartActivityRecord("startExtActivity(app=" + microApplication + ", intent=" + intent + ")"));
                }
                b();
                this.f.startActivity(intent);
            } else {
                try {
                    LauncherApplicationAgent.getInstance().getApplicationContext().startActivity(intent);
                } catch (Throwable t) {
                    TraceLogger.e((String) TAG, t);
                }
            }
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITY, this, args);
    }

    public void startActivityFromFragment(MicroApplication microApplication, Fragment fragment, Intent intent, int requestCode) {
        Object[] args = {microApplication, fragment, intent, Integer.valueOf(requestCode)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFROMFRAGMENT2, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFROMFRAGMENT2, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(microApplication, fragment, intent, requestCode);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFROMFRAGMENT2, this, args);
    }

    public void startActivityForResult(MicroApplication microApplication, Intent intent, int requestCode) {
        Object[] args = {microApplication, intent, Integer.valueOf(requestCode)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFORRESULT2, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFORRESULT2, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            a(microApplication, (Fragment) null, intent, requestCode);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTACTIVITYFORRESULT2, this, args);
    }

    private void a(MicroApplication microApplication, Fragment fragment, Intent intent, int requestCode) {
        if (this.f != null && microApplication != null && intent != null) {
            MicroApplication microApplication2 = a(microApplication, this.f);
            intent.addFlags(262144);
            a(intent);
            intent.putExtra("app_id", microApplication2.getAppId());
            microApplication2.setIsPrevent(true);
            try {
                if (!(this.f instanceof BaseActivity) && !(this.f instanceof BaseFragmentActivity)) {
                    TraceLogger.w((String) TAG, (Throwable) new StartActivityRecord("startActivityForResult(app=" + microApplication2 + ", intent=" + intent + ", code=" + requestCode + ")"));
                }
                if (fragment == null || !(this.f instanceof FragmentActivity)) {
                    this.f.startActivityForResult(intent, requestCode);
                } else {
                    ((FragmentActivity) this.f).startActivityFromFragment(fragment, intent, requestCode);
                }
            } catch (ActivityNotFoundException e2) {
                TraceLogger.w((String) TAG, (Throwable) new RuntimeException("Start ActivityShell.", e2));
                microApplication2.setIsPrevent(false);
                throw new RuntimeException("Failed to startActivityForResult(app=" + microApplication2 + ", intent=" + intent + ", code=" + requestCode + ")", e2);
            }
        }
    }

    public void startExtActivityFromFragment(MicroApplication microApplication, Fragment fragment, Intent intent, int requestCode) {
        Object[] args = {microApplication, fragment, intent, Integer.valueOf(requestCode)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITYFROMFRAGMENT, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITYFROMFRAGMENT, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            b(microApplication, fragment, intent, requestCode);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITYFROMFRAGMENT, this, args);
    }

    public void startExtActivityForResult(MicroApplication microApplication, Intent intent, int requestCode) {
        Object[] args = {microApplication, intent, Integer.valueOf(requestCode)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITYFORRESULT, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITYFORRESULT, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            b(microApplication, null, intent, requestCode);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTEXTACTIVITYFORRESULT, this, args);
    }

    private void b(MicroApplication microApplication, Fragment fragment, Intent intent, int requestCode) {
        if (this.f != null && intent != null) {
            intent.addFlags(262144);
            if (!(this.f instanceof BaseActivity) && !(this.f instanceof BaseFragmentActivity)) {
                TraceLogger.w((String) TAG, (Throwable) new StartActivityRecord("startExtActivityForResult(app=" + microApplication + ", intent=" + intent + ", code=" + requestCode + ")"));
            }
            if (fragment == null || !(this.f instanceof FragmentActivity)) {
                b();
                this.f.startActivityForResult(intent, requestCode);
                return;
            }
            ((FragmentActivity) this.f).startActivityFromFragment(fragment, intent, requestCode);
        }
    }

    public WeakReference<Activity> getTopActivity() {
        return new WeakReference<>(this.f);
    }

    public ActivityApplication getTopApplication() {
        ActivityApplication retApp = null;
        if (this.f != null) {
            if (this.f instanceof BaseActivity) {
                retApp = ((BaseActivity) this.f).getActivityApplication();
            }
            if (retApp == null && (this.f instanceof BaseFragmentActivity)) {
                retApp = ((BaseFragmentActivity) this.f).getActivityApplication();
            }
        }
        if (retApp != null) {
            return retApp;
        }
        MicroApplication topRunningApp = findTopRunningApp();
        if (topRunningApp instanceof ActivityApplication) {
            return (ActivityApplication) topRunningApp;
        }
        return retApp;
    }

    public void onDestroyContent(MicroContent microContent) {
        if (microContent instanceof MicroApplication) {
            ApplicationManager applicationManager = (ApplicationManager) findServiceByInterface(ApplicationManager.class.getName());
            if (applicationManager != null) {
                applicationManager.onDestroyApp((MicroApplication) microContent);
            }
        } else if (microContent instanceof MicroService) {
            this.h.onDestroyService((MicroService) microContent);
        } else {
            throw new RuntimeException("microContent must be MicroApplication or MicroService.");
        }
    }

    public <T> T findServiceByInterface(String className) {
        if (this.h != null) {
            Object t = this.h.findServiceByInterface(className);
            if (t == null) {
                return getExtServiceByInterface(className);
            }
            return t;
        }
        TraceLogger.e((String) TAG, "MicroApplicationContextImpl.findServiceByInterface(" + className + "), but mServiceManager==null, return null");
        return null;
    }

    public <T> boolean registerService(String className, T service) {
        return this.h.registerService(className, service);
    }

    public <T> T unregisterService(String interfaceName) {
        return this.h.unregisterService(interfaceName);
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        this.k.unregisterReceiver(receiver);
    }

    public List<MicroApplication> findAppsById(String appId) {
        if (this.i != null) {
            return this.i.findAppsById(appId);
        }
        return null;
    }

    public MicroApplication findAppById(String appId) {
        if (this.i != null) {
            return this.i.findAppById(appId);
        }
        return null;
    }

    public void addDescription(ApplicationDescription... descriptions) {
        this.i.addDescription(descriptions);
    }

    public boolean deleteDescriptionByAppId(String... appIds) {
        return this.i.deleteDescriptionByAppId(appIds);
    }

    public boolean updateDescription(ApplicationDescription description) {
        return this.i.updateDescription(description);
    }

    public ApplicationDescription findDescriptionByAppId(String appId) {
        return this.i.findDescriptionByAppId(appId);
    }

    public MicroApplication findTopRunningApp() {
        if (this.i != null) {
            return this.i.getTopRunningApp();
        }
        return null;
    }

    public void startEntryApp(Bundle params) {
        this.i.startEntryApp(params);
    }

    private static String a(Pair pair) {
        StringBuilder builder = new StringBuilder();
        if (pair != null) {
            builder.append("Pair(first=").append(pair.first).append(",second=").append(pair.second).append(")");
        }
        return builder.toString();
    }

    public void startApp(String sourceAppId, String targetAppId, Bundle startParams, Bundle sceneParams, FragmentActivity fragmentActivity) {
        TraceLogger.i((String) TAG, "startApp(): [sourceAppId=" + sourceAppId + "], [targetAppId=" + targetAppId + "].");
        Bundle startParamsCopy = copyBundle(startParams);
        Object[] args = fragmentActivity == null ? new Object[]{sourceAppId, targetAppId, startParamsCopy} : new Object[]{sourceAppId, targetAppId, startParamsCopy, fragmentActivity};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTAPP, this, args);
        Set rejectAdvices = new HashSet();
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTAPP, this, args, rejectAdvices);
        TraceLogger.w((String) TAG, "startApp(): " + StringUtil.array2String(args) + ", onExecutionAround.aroundResult=[" + a(aroundResult) + "]");
        Bundle newStartParams = copyBundle(startParamsCopy);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            try {
                newStartParams.putBoolean(PointCutConstants.REALLY_STARTAPP, true);
            } catch (Throwable t) {
                TraceLogger.e((String) TAG, Log.getStackTraceString(t));
            }
            Runnable startAppRunnable = a(sourceAppId, targetAppId, newStartParams, sceneParams, fragmentActivity);
            SharedPreferences sp = SharedPreferenceUtil.getInstance().getDefaultSharedPreference(this.e);
            String schemeInnerSource = startParamsCopy.getString("schemeInnerSource", null);
            if (schemeInnerSource != null) {
                String sourceAppIds = sp.getString(SharedPreferenceUtil.CONFIG_KEY_INSTANT_START_APP_SOURCE_APPID, null);
                if (sourceAppIds == null || !sourceAppIds.contains(schemeInnerSource)) {
                    getWorkThreadHandle().post(startAppRunnable);
                } else {
                    startAppRunnable.run();
                }
            } else {
                String instantStartAppList = sp.getString(SharedPreferenceUtil.CONFIG_KEY_INSTANT_START_APP, null);
                if (!startParamsCopy.getBoolean(PointCutConstants.INSTANT_STARTAPP, false) || instantStartAppList == null || (!"all".equals(instantStartAppList) && !instantStartAppList.contains(targetAppId))) {
                    getWorkThreadHandle().post(startAppRunnable);
                } else {
                    TraceLogger.i((String) TAG, (String) "instantStartApp, run directly");
                    startAppRunnable.run();
                }
            }
        } else {
            try {
                newStartParams.putBoolean(PointCutConstants.REALLY_STARTAPP, false);
            } catch (Throwable t2) {
                TraceLogger.e((String) TAG, Log.getStackTraceString(t2));
            }
            FrameworkMonitor.getInstance(null).handleAppStartupReject(targetAppId, "2001", rejectAdvices);
            this.o.onStartAppReject(null, targetAppId, "2001");
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTAPP, this, fragmentActivity == null ? new Object[]{sourceAppId, targetAppId, copyBundle(newStartParams)} : new Object[]{sourceAppId, targetAppId, copyBundle(newStartParams), fragmentActivity});
    }

    private Runnable a(String sourceAppId, String targetAppId, Bundle startParams, Bundle sceneParams, FragmentActivity fragmentActivity) {
        final String str = sourceAppId;
        final String str2 = targetAppId;
        final Bundle bundle = startParams;
        final Bundle bundle2 = sceneParams;
        final FragmentActivity fragmentActivity2 = fragmentActivity;
        return new SafetyRunnable(new Runnable() {
            public void run() {
                TraceLogger.i((String) MicroApplicationContextImpl.TAG, "doStartApp(): onCall [sourceAppId=" + str + "], [targetAppId=" + str2 + "].");
                if (MicroApplicationContextImpl.this.d != null) {
                    MicroApplicationContextImpl.this.d.processingAppId = str2;
                }
                Bundle startParamsCopy = MicroApplicationContextImpl.this.copyBundle(bundle);
                Bundle sceneParamsNotNull = bundle2 != null ? bundle2 : new Bundle();
                Object[] args = fragmentActivity2 == null ? new Object[]{str, str2, startParamsCopy, sceneParamsNotNull} : new Object[]{str, str2, startParamsCopy, sceneParamsNotNull, fragmentActivity2};
                FrameworkPointcutCall.onCallBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP, this, args);
                Set rejectAdvices = new HashSet();
                Pair aroundResult = FrameworkPointcutCall.onCallAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP, this, args, rejectAdvices);
                TraceLogger.w((String) MicroApplicationContextImpl.TAG, "doStartApp(): onCall " + StringUtil.array2String(args) + ", onCallAround.aroundResult=[" + aroundResult + "]");
                if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
                    PerformanceHelper.beforeStartApp(MicroApplicationContextImpl.this.e);
                    MicroApplicationContextImpl.this.i.findDescriptionByAppId(str2);
                    MicroApplicationContextImpl.this.doStartApp(str, str2, startParamsCopy, sceneParamsNotNull, fragmentActivity2);
                } else {
                    FrameworkMonitor.getInstance(null).handleAppStartupReject(str2, "2002", rejectAdvices);
                    MicroApplicationContextImpl.this.o.onStartAppReject(null, str2, "2002");
                }
                FrameworkPointcutCall.onCallAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP, this, args);
            }
        });
    }

    public void startApp(String sourceAppId, String targetAppId, Bundle params, FragmentActivity fragmentActivity) {
        startApp(sourceAppId, targetAppId, params, null, fragmentActivity);
    }

    public void startApp(String sourceAppId, String targetAppId, Bundle params) {
        startApp(sourceAppId, targetAppId, params, null, null);
    }

    @Deprecated
    public void doStartApp(String sourceAppId, String targetAppId, Bundle params) {
        doStartApp(sourceAppId, targetAppId, params, null, null);
    }

    public void doStartApp(String sourceAppId, String targetAppId, Bundle startParams, Bundle sceneParams, FragmentActivity fragmentActivity) {
        TraceLogger.i((String) TAG, "doStartApp(): onExecution [sourceAppId=" + sourceAppId + "], [targetAppId=" + targetAppId + "].");
        Bundle startParamsCopy = copyBundle(startParams);
        Object[] args = fragmentActivity == null ? new Object[]{sourceAppId, targetAppId, copyBundle(startParamsCopy), sceneParams} : new Object[]{sourceAppId, targetAppId, copyBundle(startParamsCopy), sceneParams, fragmentActivity};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP, this, args);
        Set rejectAdvices = new HashSet();
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP, this, args, rejectAdvices);
        if (aroundResult != null && ((Boolean) aroundResult.first).booleanValue()) {
            try {
                startParamsCopy.putBoolean(PointCutConstants.REALLY_DOSTARTAPP, false);
            } catch (Throwable t) {
                TraceLogger.e((String) TAG, Log.getStackTraceString(t));
            }
            FrameworkMonitor.getInstance(null).handleAppStartupReject(targetAppId, "2003", rejectAdvices);
            this.o.onStartAppReject(null, targetAppId, "2003");
        } else if (this.i.findDescriptionByAppId(targetAppId) != null) {
            try {
                startParamsCopy.putBoolean(PointCutConstants.REALLY_DOSTARTAPP, true);
            } catch (Throwable t2) {
                TraceLogger.e((String) TAG, Log.getStackTraceString(t2));
            }
            final String str = sourceAppId;
            final String str2 = targetAppId;
            final Bundle bundle = startParams;
            final FragmentActivity fragmentActivity2 = fragmentActivity;
            Runnable r = new Runnable() {
                public void run() {
                    try {
                        MicroApplicationContextImpl.this.i.startApp(str, str2, MicroApplicationContextImpl.this.copyBundle(bundle), fragmentActivity2);
                        PerformanceHelper.afterStartApp(MicroApplicationContextImpl.this.e);
                    } catch (AppLoadException e) {
                        TraceLogger.e((String) MicroApplicationContextImpl.TAG, (Throwable) e);
                    }
                }
            };
            if (Looper.getMainLooper() != Looper.myLooper() || !SharedPreferenceUtil.getInstance().getDefaultSharedPreference(this.e).getBoolean(SharedPreferenceUtil.CONFIG_KEY_IS_POST_IF_MAINLOOP, true)) {
                this.b.post(r);
            } else {
                r.run();
            }
        } else {
            try {
                startParamsCopy.putBoolean(PointCutConstants.REALLY_DOSTARTAPP, false);
            } catch (Throwable t3) {
                TraceLogger.e((String) TAG, Log.getStackTraceString(t3));
            }
            PerformanceHelper.afterStartApp(this.e);
            TraceLogger.d((String) TAG, "Failed to find ApplicationDescription by [targetAppId=" + targetAppId + "]");
            installApp(sourceAppId, targetAppId, copyBundle(startParamsCopy), sceneParams, fragmentActivity);
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_DOSTARTAPP, this, fragmentActivity == null ? new Object[]{sourceAppId, targetAppId, copyBundle(startParamsCopy), sceneParams} : new Object[]{sourceAppId, targetAppId, copyBundle(startParamsCopy), sceneParams, fragmentActivity});
    }

    public void installApp(String sourceAppId, String targetAppId, Bundle startParams, Bundle sceneParams, FragmentActivity fragmentActivity) {
        Object[] args = {sourceAppId, targetAppId, startParams, sceneParams, fragmentActivity};
        FrameworkPointcutCall.onCallBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_INSTALLAPP, this, args);
        Pair aroundResult = FrameworkPointcutCall.onCallAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_INSTALLAPP, this, args);
        if (this.i != null && (aroundResult == null || !((Boolean) aroundResult.first).booleanValue())) {
            this.i.installApp(sourceAppId, targetAppId, startParams, sceneParams, fragmentActivity);
        }
        FrameworkPointcutCall.onCallAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_INSTALLAPP, this, args);
    }

    public void finishApp(final String sourceId, final String targetId, Bundle params) {
        final Bundle params1 = copyBundle(params);
        Object[] args = {sourceId, targetId, copyBundle(params1)};
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_FINISHAPP, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_FINISHAPP, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            try {
                params1.putBoolean(PointCutConstants.REALLY_FINISHAPP, true);
            } catch (Throwable t) {
                TraceLogger.e((String) TAG, Log.getStackTraceString(t));
            }
            this.b.post(new Runnable() {
                public void run() {
                    MicroApplicationContextImpl.this.i.finishApp(sourceId, targetId, MicroApplicationContextImpl.this.copyBundle(params1));
                }
            });
        } else {
            try {
                params1.putBoolean(PointCutConstants.REALLY_FINISHAPP, false);
            } catch (Throwable t2) {
                TraceLogger.e((String) TAG, Log.getStackTraceString(t2));
            }
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_FINISHAPP, this, new Object[]{sourceId, targetId, copyBundle(params1)});
    }

    public void finishAllApps() {
        this.i.exit();
        clearState();
        MonitorLogger.flush(true);
    }

    public MicroApplication createAppById(String appId) {
        return this.i.createAppById(appId);
    }

    public <T extends ExternalService> T getExtServiceByInterface(String className) {
        if (this.h != null) {
            ExternalServiceManager exm = (ExternalServiceManager) this.h.findServiceByInterface(ExternalServiceManager.class.getName());
            if (exm != null) {
                return exm.getExternalService(className);
            }
            TraceLogger.e((String) TAG, "MicroApplicationContextImpl.getExtServiceByInterface(" + className + "), but exm==null, return null");
        } else {
            TraceLogger.e((String) TAG, "MicroApplicationContextImpl.getExtServiceByInterface(" + className + "), but mServiceManager==null, return null");
        }
        return null;
    }

    public void registerExternalService(ServiceDescription serviceDescription) {
        if (this.h != null) {
            ExternalServiceManager exm = (ExternalServiceManager) this.h.findServiceByInterface(ExternalServiceManager.class.getName());
            if (exm != null) {
                exm.registerExternalService(serviceDescription);
            } else {
                TraceLogger.e((String) TAG, (String) "MicroApplicationContextImpl.registerExternalService, but exm==null");
            }
        } else {
            TraceLogger.e((String) TAG, (String) "MicroApplicationContextImpl.registerExternalService, but mServiceManager==null");
        }
    }

    public boolean finishAllActivities(Activity exclude) {
        if (this.n == null) {
            return false;
        }
        try {
            this.n.finishAllActivities(this.f, exclude);
            return true;
        } catch (Exception e2) {
            TraceLogger.w(TAG, "finishAllActivities", e2);
            return false;
        }
    }

    public void exit() {
        Object[] args = new Object[0];
        FrameworkPointcutExecution.onExecutionBefore(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_EXIT, this, args);
        Pair aroundResult = FrameworkPointcutExecution.onExecutionAround(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_EXIT, this, args);
        if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
            this.k.close();
            this.i.exit();
            finishAllActivities(null);
            clearState();
        }
        FrameworkPointcutExecution.onExecutionAfter(PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_EXIT, this, args);
        MonitorLogger.flush(true);
        UcNativeCrashApi.onExit();
        Process.killProcess(Process.myPid());
        System.exit(10);
    }

    public void background() {
        background(this.f);
    }

    public void background(Activity activity) {
        AppCheckUtil.frameBackgroundCalled();
        if (activity == null) {
            activity = this.f;
        }
        if (activity != null) {
            activity.moveTaskToBack(true);
            ActivityHelper.sendUserLeaveHintBroadcast(activity);
        }
    }

    public synchronized boolean hasInited() {
        return this.a.get();
    }

    public void Toast(final String msg, final int period) {
        boolean needRetry = false;
        if (this.f == null) {
            TraceLogger.w((String) TAG, (Throwable) new IllegalAccessError("mActiveActivity == null"));
        } else if (this.f instanceof ActivityResponsable) {
            String pointCut = null;
            if (this.f instanceof BaseActivity) {
                pointCut = PointCutConstants.BASEACTIVITY_TOAST;
            } else if (this.f instanceof BaseFragmentActivity) {
                pointCut = PointCutConstants.BASEFRAGMENTACTIVITY_TOAST;
            }
            Object[] args = {msg, Integer.valueOf(period)};
            FrameworkPointcutCall.onCallBefore(pointCut, this.f, args);
            Pair aroundResult = FrameworkPointcutCall.onCallAround(pointCut, this.f, args);
            if (aroundResult == null || !((Boolean) aroundResult.first).booleanValue()) {
                if (this.f instanceof ActivityResponsable) {
                    ((ActivityResponsable) this.f).toast(msg, period);
                } else {
                    needRetry = true;
                }
            }
            FrameworkPointcutCall.onCallAfter(pointCut, this.f, args);
        } else {
            needRetry = true;
        }
        if (needRetry) {
            try {
                this.f.runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            if (MicroApplicationContextImpl.this.f != null) {
                                Context context = MicroApplicationContextImpl.this.f;
                                Toast toast = new Toast(context);
                                View view = LayoutInflater.from(context).inflate(R.layout.transient_notification, null);
                                ((TextView) view.findViewById(16908299)).setText(msg);
                                toast.setView(view);
                                toast.setDuration(period);
                                toast.setGravity(17, 0, 0);
                                toast.show();
                            }
                        } catch (Throwable e) {
                            TraceLogger.e(MicroApplicationContextImpl.TAG, "single toast", e);
                        }
                    }
                });
            } catch (Throwable e2) {
                TraceLogger.e(TAG, "single toast", e2);
            }
        }
    }

    public void Alert(String title, String msg, String positive, OnClickListener positiveListener, String negative, OnClickListener negativeListener) {
        if (this.f == null) {
            TraceLogger.w((String) TAG, (Throwable) new IllegalAccessError("mActiveActivity == null"));
        } else if (this.f instanceof ActivityResponsable) {
            ((ActivityResponsable) this.f).alert(title, msg, positive, positiveListener, negative, negativeListener);
        } else {
            TraceLogger.w((String) TAG, (String) "Alert mActiveActivity is not ActivityResponsable, do it self");
            final String str = title;
            final String str2 = msg;
            final String str3 = positive;
            final OnClickListener onClickListener = positiveListener;
            final String str4 = negative;
            final OnClickListener onClickListener2 = negativeListener;
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    if (MicroApplicationContextImpl.this.f != null && !MicroApplicationContextImpl.this.f.isFinishing()) {
                        Builder builder = new Builder(MicroApplicationContextImpl.this.f);
                        if (str != null) {
                            builder.setTitle(str);
                        }
                        if (str2 != null) {
                            builder.setMessage(str2);
                        }
                        if (str3 != null) {
                            builder.setPositiveButton(str3, onClickListener);
                        }
                        if (str4 != null) {
                            builder.setNegativeButton(str4, onClickListener2);
                        }
                        AlertDialog alertDialog = builder.create();
                        try {
                            alertDialog.show();
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.setCancelable(false);
                        } catch (Exception e) {
                            TraceLogger.w((String) MicroApplicationContextImpl.TAG, "DialogHelper.alert(): exception=" + e);
                        }
                    }
                }
            });
        }
    }

    public void showProgressDialog(final String msg) {
        if (this.f == null) {
            TraceLogger.w((String) TAG, (Throwable) new IllegalAccessError("mActiveActivity == null"));
        } else if (this.f instanceof ActivityResponsable) {
            a(this.f);
            ((ActivityResponsable) this.f).showProgressDialog(msg);
        } else {
            a(this.f);
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    if (MicroApplicationContextImpl.this.f != null && !MicroApplicationContextImpl.this.f.isFinishing()) {
                        APGenericProgressDialog aPGenericProgressDialog = new APGenericProgressDialog(MicroApplicationContextImpl.this.f);
                        MicroApplicationContextImpl.this.g = new WeakReference(aPGenericProgressDialog);
                        aPGenericProgressDialog.setMessage(msg);
                        aPGenericProgressDialog.setProgressVisiable(true);
                        aPGenericProgressDialog.setCancelable(true);
                        aPGenericProgressDialog.setOnCancelListener(null);
                        aPGenericProgressDialog.setCanceledOnTouchOutside(false);
                        try {
                            aPGenericProgressDialog.show();
                        } catch (Throwable e) {
                            TraceLogger.e((String) MicroApplicationContextImpl.TAG, "DialogHelper.showProgressDialog()" + e);
                            MicroApplicationContextImpl.this.g.clear();
                            MicroApplicationContextImpl.this.g = null;
                        }
                    }
                }
            });
        }
    }

    public void showProgressDialog(final String msg, final boolean cancelable, final OnCancelListener cancelListener) {
        if (this.f == null) {
            TraceLogger.w((String) TAG, (Throwable) new IllegalAccessError("mActiveActivity == null"));
        } else if (this.f instanceof ActivityResponsable) {
            a(this.f);
            ((ActivityResponsable) this.f).showProgressDialog(msg, cancelable, cancelListener);
        } else {
            a(this.f);
            this.f.runOnUiThread(new Runnable() {
                public void run() {
                    if (MicroApplicationContextImpl.this.f != null && !MicroApplicationContextImpl.this.f.isFinishing()) {
                        APGenericProgressDialog aPGenericProgressDialog = new APGenericProgressDialog(MicroApplicationContextImpl.this.f);
                        MicroApplicationContextImpl.this.g = new WeakReference(aPGenericProgressDialog);
                        aPGenericProgressDialog.setMessage(msg);
                        aPGenericProgressDialog.setProgressVisiable(true);
                        aPGenericProgressDialog.setCancelable(cancelable);
                        aPGenericProgressDialog.setOnCancelListener(cancelListener);
                        aPGenericProgressDialog.setCanceledOnTouchOutside(false);
                        try {
                            aPGenericProgressDialog.show();
                        } catch (Throwable e) {
                            TraceLogger.e((String) MicroApplicationContextImpl.TAG, "DialogHelper.showProgressDialog()" + e);
                            MicroApplicationContextImpl.this.g.clear();
                            MicroApplicationContextImpl.this.g = null;
                        }
                    }
                }
            });
        }
    }

    public void dismissProgressDialog() {
        if (this.f == null) {
            TraceLogger.w((String) TAG, (Throwable) new IllegalAccessError("mActiveActivity == null"));
        } else if (this.f instanceof ActivityResponsable) {
            ((ActivityResponsable) this.f).dismissProgressDialog();
            a(this.f);
        } else {
            TraceLogger.w((String) TAG, (String) "Alert mActiveActivity is not ActivityResponsable, dismissProgressDialog self");
            a(this.f);
        }
    }

    private void a(Activity activity) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    if (MicroApplicationContextImpl.this.g != null && MicroApplicationContextImpl.this.g.get() != null) {
                        try {
                            ((AlertDialog) MicroApplicationContextImpl.this.g.get()).dismiss();
                        } catch (Throwable e) {
                            TraceLogger.w((String) MicroApplicationContextImpl.TAG, "DialogHelper.dismissProgressDialog(): exception=" + e);
                        } finally {
                            MicroApplicationContextImpl.this.g.clear();
                            MicroApplicationContextImpl.this.g = null;
                        }
                    }
                }
            });
        }
    }

    public void onWindowFocus(MicroApplication application) {
    }

    public void clearTopApps() {
        this.i.exit();
    }

    public void saveState() {
        Editor editor = this.e.getSharedPreferences(SHARE_PREF_STATES, 0).edit();
        editor.putBoolean("@@", true);
        this.i.saveState(editor);
        editor.apply();
    }

    @Deprecated
    public void restoreState() {
        restoreState(true);
    }

    public void restoreState(boolean foreground) {
        SharedPreferences preferences = this.e.getSharedPreferences(SHARE_PREF_STATES, 0);
        if (preferences.contains("@@")) {
            if (UpgradeEnum.NONE == UpgradeHelper.getInstance(getApplicationContext()).getUpgrade()) {
                long start = System.currentTimeMillis();
                TraceLogger.d((String) TAG, (String) "ApplicationManager.restoreState() begin");
                this.i.restoreState(preferences, foreground);
                TraceLogger.d((String) TAG, "ApplicationManager.restoreState() end. cost " + (System.currentTimeMillis() - start) + " ms");
            }
            preferences.edit().clear().apply();
        }
    }

    public void clearState() {
        this.e.getSharedPreferences(SHARE_PREF_STATES, 0).edit().clear().apply();
    }

    public boolean registerApplicationEngine(String engineType, IApplicationEngine engine) {
        return this.i.registerApplicationEngine(engineType, engine);
    }

    public boolean unregisterApplicationEngine(String engineType) {
        return this.i.unregisterApplicationEngine(engineType);
    }

    public int getActiveActivityCount() {
        return this.i.getActiveActivityCount();
    }

    public boolean registerApplicationInstaller(IApplicationInstaller installer) {
        return this.i.registerApplicationInstaller(installer);
    }

    public boolean unregisterApplicationInstaller(IApplicationInstaller installer) {
        return this.i.unregisterApplicationInstaller(installer);
    }

    public Pipeline getPipelineByName(String name) {
        if (this.j == null) {
            return null;
        }
        return this.j.getPipelineByName(name);
    }

    public Pipeline getPipelineByName(String name, long timeout) {
        if (this.j == null) {
            return null;
        }
        return this.j.getPipelineByName(name, timeout);
    }

    public PipelineManager getPipelineManager() {
        return this.j;
    }

    public void setStartActivityContext(Context context) {
        this.p = new WeakReference<>(context);
    }

    private Context a() {
        if (this.p != null) {
            Context context = (Context) this.p.get();
            if (context != null) {
                return context;
            }
        }
        return null;
    }

    @TargetApi(23)
    public void requestPermissions(String[] permissions, int requestCode, RequestPermissionsResultCallback callback) {
        requestPermissions(null, permissions, requestCode, callback);
    }

    @TargetApi(23)
    public void requestPermissions(Activity activity, String[] permissions, int requestCode, RequestPermissionsResultCallback callback) {
        if (VERSION.SDK_INT >= 23) {
            boolean bFlag = false;
            if (activity instanceof ActivityResponsable) {
                ((ActivityResponsable) activity).requestPermissions(permissions, requestCode, callback);
                bFlag = true;
            } else if (getTopActivity() != null) {
                Activity topActivity = (Activity) getTopActivity().get();
                if (topActivity instanceof ActivityResponsable) {
                    ((ActivityResponsable) topActivity).requestPermissions(permissions, requestCode, callback);
                    bFlag = true;
                }
            }
            if (!bFlag) {
                int[] grantResults = new int[permissions.length];
                int N = grantResults.length;
                for (int i2 = 0; i2 < N; i2++) {
                    grantResults[i2] = -1;
                }
                callback.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public void postInit() {
        this.m.postLoad();
    }

    public void initSerivces() {
        this.m.loadServices();
    }

    public Bundle copyBundle(Bundle params) {
        return params != null ? new Bundle(params) : new Bundle();
    }

    private static void b() {
        try {
            LauncherApplicationAgent.getInstance().getBundleContext().setupInstrumentation();
        } catch (Throwable t) {
            TraceLogger.w((String) TAG, t);
        }
    }

    public Map<String, Set<String>> getLazyBundles() {
        try {
            return DescriptionManager.getInstance().getLazyBundles();
        } catch (Throwable th) {
            return null;
        }
    }

    public StartAppExceptionManager getStartAppExceptionManager() {
        return this.o;
    }
}
