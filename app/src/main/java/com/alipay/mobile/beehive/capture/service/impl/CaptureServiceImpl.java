package com.alipay.mobile.beehive.capture.service.impl;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.alipay.mobile.beehive.capture.activity.CaptureActivity;
import com.alipay.mobile.beehive.capture.activity.CaptureV2OrientationActivity;
import com.alipay.mobile.beehive.capture.modle.MediaInfo;
import com.alipay.mobile.beehive.capture.service.CaptureListener;
import com.alipay.mobile.beehive.capture.service.CaptureListenerV2;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.beehive.capture.service.CaptureService;
import com.alipay.mobile.beehive.capture.service.IndustryCaptureListener;
import com.alipay.mobile.beehive.capture.utils.Logger;
import com.alipay.mobile.beehive.capture.utils.OtherUtils;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CaptureServiceImpl extends CaptureService {
    public static final String EXTRA_KEY_BUSINESS_ID = "businessId";
    private static final String TAG = "CaptureServiceImpl";
    private static a currentSession;

    static class a {
        public List<MediaInfo> a = new LinkedList();
        private WeakReference<CaptureListener> b;

        public a(CaptureListener listener) {
            this.b = new WeakReference<>(listener);
        }

        public final CaptureListener a() {
            return (CaptureListener) this.b.get();
        }

        public final void a(boolean isCanceled, MediaInfo mediaInfo) {
            if (mediaInfo == null) {
                a(isCanceled, null, null);
                return;
            }
            List arr = new LinkedList();
            arr.add(mediaInfo);
            a(isCanceled, arr, null);
        }

        public final void a(boolean isCanceled, List<MediaInfo> mediaInfos, Map<String, Object> extras) {
            CaptureListener listener = (CaptureListener) this.b.get();
            if (listener != null) {
                Logger.info(CaptureServiceImpl.TAG, "Is canceled : " + isCanceled);
                if (listener instanceof IndustryCaptureListener) {
                    ((IndustryCaptureListener) listener).onAction(isCanceled, mediaInfos, extras);
                } else {
                    listener.onAction(isCanceled, (mediaInfos == null || mediaInfos.isEmpty()) ? null : mediaInfos.get(0));
                }
            }
        }

        public final void a(Activity activity, View v) {
            CaptureListener listener = (CaptureListener) this.b.get();
            if (listener instanceof CaptureListenerV2) {
                ((CaptureListenerV2) listener).onLatestRecordEntryClicked(activity, v);
            } else {
                Logger.debug(CaptureServiceImpl.TAG, "notifyLatestRecordEntryClicked when CaptureListener is " + (listener == null ? "Null" : "not instance of CaptureListenerV2"));
            }
        }

        public final void a(String event, Bundle args) {
            CaptureListener listener = (CaptureListener) this.b.get();
            if (listener instanceof IndustryCaptureListener) {
                ((IndustryCaptureListener) listener).onRecorderEvent(event, args);
            } else {
                Logger.debug(CaptureServiceImpl.TAG, "notifyRecorderPrepared when CaptureListener is " + (listener == null ? "Null" : "not instance of CaptureListenerV2"));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Logger.debug(TAG, "CaptureServiceImpl onCreate.");
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        Logger.debug(TAG, "CaptureServiceImpl onDestroy.");
    }

    public static synchronized a getCurrentSession() {
        a aVar;
        synchronized (CaptureServiceImpl.class) {
            aVar = currentSession;
        }
        return aVar;
    }

    public static synchronized void setUpCurrentSession(CaptureListener listener) {
        synchronized (CaptureServiceImpl.class) {
            if (currentSession != null) {
                if (currentSession.a() == listener) {
                    Logger.warn(TAG, "CaptureListener is same,do nothing.");
                } else {
                    Logger.warn(TAG, "Capture job concurrent,cancel the old one!");
                }
            }
            Logger.warn(TAG, "Update CaptureSession");
            currentSession = new a(listener);
        }
    }

    public static synchronized void notifyAction(boolean isCancel, MediaInfo mediaInfo, boolean releaseSession) {
        synchronized (CaptureServiceImpl.class) {
            Logger.warn(TAG, "notifyAction called");
            if (currentSession != null) {
                currentSession.a(isCancel, mediaInfo);
                if (releaseSession) {
                    Logger.warn(TAG, "Clear session");
                    currentSession = null;
                }
            } else {
                Logger.warn(TAG, "notifyAction called when captureSession is Null!");
            }
        }
    }

    public static synchronized void notifyIndustryCaptureAction(boolean isCancel, Map<String, Object> extras, boolean releaseSession) {
        synchronized (CaptureServiceImpl.class) {
            Logger.warn(TAG, "notifyAction called");
            if (currentSession != null) {
                for (MediaInfo mediaInfo : currentSession.a) {
                    OtherUtils.scanMediaFile(OtherUtils.getAbsPath(mediaInfo.path));
                }
                currentSession.a(isCancel, currentSession.a, extras);
                if (releaseSession) {
                    Logger.warn(TAG, "Clear session");
                    currentSession = null;
                }
            } else {
                Logger.warn(TAG, "notifyAction called when captureSession is Null!");
            }
        }
    }

    public static synchronized void notifyLatestRecordEntryClicked(Activity activity, View v) {
        synchronized (CaptureServiceImpl.class) {
            Logger.warn(TAG, "notifyLatestRecordEntryClicked called");
            if (currentSession != null) {
                currentSession.a(activity, v);
                Logger.warn(TAG, "Clear session");
                currentSession = null;
            } else {
                Logger.warn(TAG, "notifyLatestRecordEntryClicked called when captureSession is Null!");
            }
        }
    }

    public static synchronized void notifyRecorderEvent(String event, Bundle args, boolean releaseSession) {
        synchronized (CaptureServiceImpl.class) {
            Logger.warn(TAG, "notifyLatestRecordEntryClicked called");
            if (currentSession != null) {
                currentSession.a(event, args);
                Logger.warn(TAG, "Clear session");
                if (releaseSession) {
                    currentSession = null;
                }
            } else {
                Logger.warn(TAG, "notifyLatestRecordEntryClicked called when captureSession is Null!");
            }
        }
    }

    public static synchronized void addOneMorePicToSession(MediaInfo mediaInfo) {
        synchronized (CaptureServiceImpl.class) {
            Logger.d(TAG, "addOneMorePicToSession:###");
            if (currentSession != null) {
                currentSession.a.add(mediaInfo);
            } else {
                Logger.d(TAG, "Session is null.");
            }
        }
    }

    public void capture(MicroApplication app, CaptureListener listener, String businessId, Bundle params) {
        if (checkArgs(businessId, listener)) {
            setUpCurrentSession(listener);
            if (params == null) {
                params = new Bundle();
            }
            params.putString("businessId", businessId);
            int captureStyle = params.getInt(CaptureParam.CAPTURE_STYLE, 1);
            Application context = LauncherApplicationAgent.getInstance().getApplicationContext();
            MicroApplicationContext appCtx = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
            SpmUtils.addSourceAndBizTypeByTop(appCtx.findTopRunningApp(), (Activity) appCtx.getTopActivity().get(), params);
            if (captureStyle == 2) {
                CaptureV2OrientationActivity.startCaptureV2Activity(app, appCtx, context, params);
                return;
            }
            Intent intent = new Intent(context, CaptureActivity.class);
            intent.putExtras(params);
            appCtx.startActivity(app, intent);
            return;
        }
        Logger.debug(TAG, "CaptureService action called,but args is not valid!");
    }

    private boolean checkArgs(String businessId, CaptureListener listener) {
        return !TextUtils.isEmpty(businessId) && listener != null;
    }
}
