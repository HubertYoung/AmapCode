package com.alipay.mobile.core.loading.impl;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.mobile.aspect.Advice;
import com.alipay.mobile.aspect.ExecutionAdvice;
import com.alipay.mobile.aspect.FrameworkPointCutManager;
import com.alipay.mobile.aspect.PointCutConstants;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.exception.IllegalParameterException;
import com.alipay.mobile.framework.loading.LoadingPageHandler;
import com.alipay.mobile.framework.loading.LoadingPageManager;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.framework.loading.LoadingView.Factory;
import com.alipay.mobile.quinox.utils.TraceLogger;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoadingPageManagerImpl extends ExecutionAdvice implements LoadingPageManager {
    private int a = 0;
    private MicroApplicationContext b;
    private Factory c;
    private Handler d = new Handler(Looper.getMainLooper());
    private List<LoadingPageHandler> e = new ArrayList();
    private Map<Integer, LoadingRecord> f = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public WeakReference<Activity> g;

    public LoadingPageManagerImpl(MicroApplicationContext microApplicationContext) {
        this.b = microApplicationContext;
        FrameworkPointCutManager.getInstance().registerPointCutAdvice((String) PointCutConstants.MICROAPPLICATIONCONTEXTIMPL_STARTAPP, (Advice) this);
        microApplicationContext.getApplicationContext().registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            public void onActivityStarted(Activity activity) {
            }

            public void onActivityResumed(Activity activity) {
                if (LoadingPageManagerImpl.this.g != null && (LoadingPageManagerImpl.this.g.get() instanceof LoadingPage)) {
                    Activity lastResumed = (Activity) LoadingPageManagerImpl.this.g.get();
                    if (lastResumed != activity) {
                        TraceLogger.v(LoadingPageManager.TAG, activity + " is coming, " + lastResumed + " will stop");
                        ((LoadingPage) lastResumed).setHasLostFocus(true);
                    }
                }
                LoadingPageManagerImpl.this.g = new WeakReference(activity);
            }

            public void onActivityPaused(Activity activity) {
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    public void setDefaultLoadingViewFactory(Factory loadingViewFactory) {
        this.c = loadingViewFactory;
    }

    public Factory getDefaultLoadingViewFactory() {
        return this.c;
    }

    public synchronized void registerLoadingPageHandler(LoadingPageHandler loadingPageHandler) {
        if (loadingPageHandler == null) {
            throw new IllegalParameterException("loadingPageHandler is null");
        }
        this.e.add(loadingPageHandler);
        Collections.sort(this.e, new Comparator<LoadingPageHandler>() {
            public int compare(LoadingPageHandler lhs, LoadingPageHandler rhs) {
                return lhs.getPriority() - rhs.getPriority();
            }
        });
        TraceLogger.i(TAG, "registerLoadingPageHandler:" + loadingPageHandler + ", size=" + this.e.size());
    }

    public synchronized void unregisterLoadingPageHandler(LoadingPageHandler loadingPageHandler) {
        if (loadingPageHandler == null) {
            throw new IllegalParameterException("loadingPageHandler is null");
        }
        this.e.remove(loadingPageHandler);
        TraceLogger.i(TAG, "unregisterLoadingPageHandler:" + loadingPageHandler + ", size=" + this.f.size());
    }

    public synchronized boolean startLoading(String sourceId, String targetAppId, Bundle params) {
        boolean z;
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            if (!TextUtils.isEmpty(targetAppId)) {
                if (findLoadingRecordByAppId(targetAppId) == null) {
                    Iterator<LoadingPageHandler> it = this.e.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            break;
                        }
                        LoadingPageHandler handler = it.next();
                        long from = System.currentTimeMillis();
                        boolean needShowLoadingPage = false;
                        try {
                            needShowLoadingPage = handler.needShowLoadingPage(sourceId, targetAppId, params);
                        } catch (Throwable t) {
                            TraceLogger.e(TAG, t);
                        }
                        TraceLogger.i(TAG, "needShowLoadingPage:" + needShowLoadingPage + ",handler=" + handler + ",cost=" + (System.currentTimeMillis() - from));
                        if (needShowLoadingPage) {
                            int curToken = this.a;
                            this.a = curToken + 1;
                            LoadingView loadingView = handler.createLoadingView(sourceId, targetAppId, params);
                            if (loadingView == null && this.c != null) {
                                loadingView = this.c.createLoadingView(sourceId, targetAppId, params);
                            }
                            if (loadingView == null) {
                                TraceLogger.e(TAG, "can not createLoadingView:" + handler);
                                z = false;
                                break;
                            }
                            Map<Integer, LoadingRecord> map = this.f;
                            Integer num = new Integer(curToken);
                            map.put(num, new LoadingRecord(curToken, sourceId, targetAppId, params, handler, loadingView));
                            try {
                                Intent intent = new Intent(this.b.getApplicationContext(), LoadingPage.class);
                                intent.putExtra("token", curToken);
                                intent.setFlags(268435456);
                                MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
                                microApplicationContext.startExtActivity(microApplicationContext.getTopApplication(), intent);
                                z = true;
                                break;
                            } catch (Throwable t2) {
                                TraceLogger.e(TAG, t2);
                            }
                        }
                    }
                } else {
                    TraceLogger.w(TAG, "loadingPage showing:" + targetAppId);
                    z = false;
                }
            } else {
                TraceLogger.e(TAG, "empty appId: " + targetAppId);
                z = false;
            }
        } else {
            TraceLogger.e(TAG, "must startLoading on UI thread: " + targetAppId);
            z = false;
        }
        return z;
    }

    public synchronized boolean stopLoading(String appId) {
        boolean z = true;
        synchronized (this) {
            LoadingRecord loadingRecord = findLoadingRecordByAppId(appId);
            if (loadingRecord == null) {
                z = false;
            } else if (loadingRecord.loadingPage != null) {
                LoadingPage loadingPage = (LoadingPage) loadingRecord.loadingPage.get();
                if (loadingPage != null) {
                    loadingPage.stopLoadingPage();
                }
            } else {
                loadingRecord.isStopped = true;
            }
        }
        return z;
    }

    public LoadingView findLoadingView(String appId) {
        LoadingRecord loadingRecord = findLoadingRecordByAppId(appId);
        if (loadingRecord != null) {
            return loadingRecord.loadingView;
        }
        return null;
    }

    public LoadingRecord findLoadingRecordByAppId(String appId) {
        LoadingRecord foundRecord = null;
        for (LoadingRecord record : this.f.values()) {
            if ((foundRecord == null && record.targetAppId.equals(appId)) || (foundRecord != null && record.token > foundRecord.token)) {
                foundRecord = record;
            }
        }
        return foundRecord;
    }

    public LoadingRecord getLoadingPageRecord(int token) {
        return this.f.get(new Integer(token));
    }

    public void removeLoadingPageRecord(int token) {
        this.f.remove(new Integer(token));
    }

    public void onExecutionBefore(String pointCut, Object thisPoint, final Object[] args) {
        this.d.post(new Runnable() {
            public void run() {
                try {
                    LoadingPageManagerImpl.this.startLoading((String) args[0], (String) args[1], (Bundle) args[2]);
                } catch (Throwable t) {
                    TraceLogger.e(LoadingPageManager.TAG, t);
                }
            }
        });
    }

    public Pair<Boolean, Object> onExecutionAround(String pointCut, Object thisPoint, Object[] args) {
        return null;
    }

    public void onExecutionAfter(String pointCut, Object thisPoint, Object[] args) {
    }
}
