package com.alipay.mobile.nebula.appcenter.apphandler.loadingview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.loading.LoadingPageHandler;
import com.alipay.mobile.framework.loading.LoadingPageManager;
import com.alipay.mobile.framework.loading.LoadingView;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppHandler;
import com.alipay.mobile.nebula.appcenter.apphandler.H5AppPrepareData;
import com.alipay.mobile.nebula.appcenter.apphandler.H5StartAppInfo;
import com.alipay.mobile.nebula.appcenter.appsync.H5PageStatues;
import com.alipay.mobile.nebula.appcenter.model.AppInfo;
import com.alipay.mobile.nebula.tinypermission.H5ApiManager;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ParamParser;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class H5LoadingFrameworkImpl implements H5LoadingManager, H5LoadingTypeListen {
    public static final String ANIMATION_TYPE_STOP_LOADING = "ANIMATION_STOP_LOADING_PREPARE";
    public static final String DATA_UPDATE_APPEARANCE_LOADING_ICON = "UPDATE_APPEARANCE_LOADING_ICON";
    public static final String DATA_UPDATE_APPEARANCE_LOADING_TEXT = "UPDATE_APPEARANCE_LOADING_TEXT";
    public static final String DATA_UPDATE_APPERRANCE_LOADING_BOTTOM_TIP = "UPDATE_APPEARANCE_LOADING_BOTTOM_TIP";
    public static final String MSG_UPDATE_APPEARANCE = "UPDATE_APPEARANCE";
    private static final String TAG = "H5LoadingFrameworkImpl";
    /* access modifiers changed from: private */
    public H5StartAppInfo mH5StartAppInfo;
    private LoadingPageHandler mLoadingPageHandler;
    /* access modifiers changed from: private */
    public String mNbUrl = null;
    /* access modifiers changed from: private */
    public H5PageStatues mPageStatues;
    /* access modifiers changed from: private */
    public boolean mPlayingExitAnim = false;
    private long mStartLoadingTime = 0;
    /* access modifiers changed from: private */
    public Timer mTimer;
    /* access modifiers changed from: private */
    public boolean needShown = true;

    private class MyTimeTask extends TimerTask {
        /* access modifiers changed from: private */
        public int timeout;
        /* access modifiers changed from: private */
        public String type;

        MyTimeTask(String type2, int timeout2) {
            this.type = type2;
            this.timeout = timeout2;
        }

        public void run() {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5Log.d(H5LoadingFrameworkImpl.TAG, "timeout " + MyTimeTask.this.timeout + " type " + MyTimeTask.this.type + " nbUrl " + H5LoadingFrameworkImpl.this.mNbUrl);
                    if (H5LoadingFrameworkImpl.this.isPageDestroy()) {
                        H5Log.d(H5LoadingFrameworkImpl.TAG, "pageDestroy, not run timeTask");
                    } else if (H5LoadingFrameworkImpl.this.mPageStatues != null && (H5LoadingFrameworkImpl.this.mPageStatues.currentPageStatues == 3 || H5LoadingFrameworkImpl.this.mPageStatues.currentPageStatues == 2)) {
                        H5Log.d(H5LoadingFrameworkImpl.TAG, "pageStatus: " + H5LoadingFrameworkImpl.this.mPageStatues.currentPageStatues + " not run timeout");
                    } else if (H5AppHandler.isSyncType(MyTimeTask.this.type)) {
                        if (!(H5LoadingFrameworkImpl.this.mH5StartAppInfo == null || H5LoadingFrameworkImpl.this.mH5StartAppInfo.appPrepareData == null)) {
                            H5LoadingFrameworkImpl.this.mH5StartAppInfo.appPrepareData.uploadPrepareLog("finish", H5AppPrepareData.PREPARE_TIMEOUT);
                        }
                        if (TextUtils.isEmpty(H5LoadingFrameworkImpl.this.mNbUrl)) {
                            H5LoadingFrameworkImpl.this.sendToWebFail();
                            return;
                        }
                        if (H5LoadingFrameworkImpl.this.mPageStatues != null) {
                            H5LoadingFrameworkImpl.this.mPageStatues.currentPageStatues = 3;
                        }
                        H5AppHandler.exitPageAndOpenUrl(H5LoadingFrameworkImpl.this.mNbUrl, H5LoadingFrameworkImpl.this);
                    } else {
                        if (H5LoadingFrameworkImpl.this.mH5StartAppInfo != null && H5LoadingFrameworkImpl.this.mH5StartAppInfo.useAppX) {
                            H5LoadingFrameworkImpl.this.mH5StartAppInfo.params.putBoolean("is_local", true);
                            H5LoadingFrameworkImpl.this.mH5StartAppInfo.params.putLong("perf_open_app_time", SystemClock.elapsedRealtime());
                        }
                        H5AppHandler.exitAndStartApp(H5LoadingFrameworkImpl.this.mH5StartAppInfo, H5LoadingUtil.getH5LoadingManager());
                    }
                }
            });
        }
    }

    public void open(Bundle bundle, String appId, String type, final H5StartAppInfo h5StartAppInfo, int timeout) {
        this.mStartLoadingTime = System.currentTimeMillis();
        H5ParamParser.parse(h5StartAppInfo.params, H5Param.LONG_NB_URL, false);
        this.mNbUrl = H5Utils.getString(h5StartAppInfo.params, (String) H5Param.LONG_NB_URL);
        this.mLoadingPageHandler = new LoadingPageHandler() {
            public int getPriority() {
                return 0;
            }

            public boolean needShowLoadingPage(String sourceAppId, String targetAppId, Bundle bundle) {
                H5Log.d(H5LoadingFrameworkImpl.TAG, "needShowLoadingPage for targetAppId: " + targetAppId + " h5StartAppInfo.targetAppId: " + h5StartAppInfo.targetAppId);
                if (!H5LoadingFrameworkImpl.this.needShown || !TextUtils.equals(h5StartAppInfo.targetAppId, targetAppId)) {
                    return false;
                }
                H5LoadingFrameworkImpl.this.needShown = false;
                return true;
            }

            public void onCreateLoadingPage(Activity loadingPage, String sourceId, String targetAppId, Bundle params) {
                boolean enableOuter;
                boolean enableInner;
                boolean enableNoAppinfo;
                if (!h5StartAppInfo.useAppX || h5StartAppInfo.tinyType != 0 || TextUtils.equals("66666672", h5StartAppInfo.targetAppId)) {
                    enableOuter = false;
                } else {
                    enableOuter = true;
                }
                if (!h5StartAppInfo.useAppX || h5StartAppInfo.tinyType != 1 || !h5StartAppInfo.isUsePresetPopmenu) {
                    enableInner = false;
                } else {
                    enableInner = true;
                }
                if (h5StartAppInfo.tinyType != 2 || !h5StartAppInfo.enableUpDownAnimWithoutAppinfo) {
                    enableNoAppinfo = false;
                } else {
                    enableNoAppinfo = true;
                }
                boolean isMiniService = H5Utils.isTinyMiniService(params);
                H5Log.d(H5LoadingFrameworkImpl.TAG, "onCreateLoadingPage enableOuter " + enableOuter + " enableInner " + enableInner + " enableNoAppinfo " + enableNoAppinfo + " isMiniService " + isMiniService);
                if (loadingPage != null && !isMiniService) {
                    if (enableOuter || enableInner || enableNoAppinfo || H5Utils.canTransferH5ToTinyWithAnimation(targetAppId, params)) {
                        loadingPage.overridePendingTransition(loadingPage.getResources().getIdentifier("tiny_push_up_in", ResUtils.ANIM, H5Utils.getContext().getPackageName()), loadingPage.getResources().getIdentifier("tiny_fading_out", ResUtils.ANIM, H5Utils.getContext().getPackageName()));
                    }
                }
            }

            public void onCancelLoadingPage(Activity loadingPage, String sourceId, String targetAppId, Bundle params) {
                H5Log.d(H5LoadingFrameworkImpl.TAG, "onCancelLoadingPage");
                H5LoadingFrameworkImpl.this.getLoadingManager().unregisterLoadingPageHandler(this);
                if (H5LoadingFrameworkImpl.this.mTimer != null) {
                    H5LoadingFrameworkImpl.this.mTimer.cancel();
                    H5LoadingFrameworkImpl.this.mTimer = null;
                }
                if (H5LoadingFrameworkImpl.this.mH5StartAppInfo != null && H5LoadingFrameworkImpl.this.mH5StartAppInfo.appPrepareData != null) {
                    H5LoadingFrameworkImpl.this.mH5StartAppInfo.appPrepareData.uploadPrepareLog("cancel", "0");
                }
            }

            public void onStopLoadingPage(Activity loadingPage, String sourceId, String targetAppId, Bundle params) {
                H5Log.d(H5LoadingFrameworkImpl.TAG, "onStopLoadingPage");
                H5LoadingFrameworkImpl.this.getLoadingManager().unregisterLoadingPageHandler(this);
                if (H5LoadingFrameworkImpl.this.mTimer != null) {
                    H5LoadingFrameworkImpl.this.mTimer.cancel();
                    H5LoadingFrameworkImpl.this.mTimer = null;
                }
            }

            public void onFinishLoadingPage(Activity loadingPage, String sourceId, String targetAppId, Bundle params) {
                H5Log.d(H5LoadingFrameworkImpl.TAG, "onFinishLoadingPage");
                if (loadingPage != null) {
                    loadingPage.overridePendingTransition(0, 0);
                }
                if (H5LoadingFrameworkImpl.this.mTimer != null) {
                    H5LoadingFrameworkImpl.this.mTimer.cancel();
                    H5LoadingFrameworkImpl.this.mTimer = null;
                }
            }

            public LoadingView createLoadingView(String s, String s1, Bundle bundle) {
                H5Log.d(H5LoadingFrameworkImpl.TAG, "createLoadingView");
                H5ApiManager h5ApiManager = (H5ApiManager) H5Utils.getProvider(H5ApiManager.class.getName());
                if (h5ApiManager != null) {
                    return h5ApiManager.getLoadingViewFromTiny(h5StartAppInfo);
                }
                return null;
            }
        };
        this.mH5StartAppInfo = h5StartAppInfo;
        this.mPageStatues = new H5PageStatues();
        this.mPageStatues.currentPageStatues = 1;
        H5LoadingUtil.setH5LoadingTypeListen(this);
        H5LoadingUtil.setH5LoadingManager(this);
        final String appName = H5Utils.getString(bundle, (String) "appName");
        final String slogan = H5Utils.getString(bundle, (String) H5AppHandler.sAppSlogan);
        String logo = H5Utils.getString(bundle, (String) H5AppHandler.sAppIcon);
        H5Log.d(TAG, "appName " + appName + " logo:" + logo + " timeout:" + timeout + " type:" + type);
        getLoadingManager().registerLoadingPageHandler(this.mLoadingPageHandler);
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                H5LoadingFrameworkImpl.this.getLoadingManager().startLoading(H5LoadingFrameworkImpl.this.mH5StartAppInfo.sourceAppId, H5LoadingFrameworkImpl.this.mH5StartAppInfo.targetAppId, H5LoadingFrameworkImpl.this.mH5StartAppInfo.params);
                H5LoadingFrameworkImpl.this.setLoadingInfo("UPDATE_APPEARANCE_LOADING_TEXT", appName);
                if (!TextUtils.isEmpty(slogan)) {
                    H5LoadingFrameworkImpl.this.setLoadingInfo("UPDATE_APPEARANCE_LOADING_BOTTOM_TIP", "本小程序由\"" + slogan + "\"提供");
                }
            }
        });
        if (!TextUtils.isEmpty(logo)) {
            H5ImageUtil.loadImage(logo, new H5ImageListener() {
                public void onImage(final Bitmap bitmap) {
                    if (bitmap != null) {
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                H5LoadingFrameworkImpl.this.setLoadingImage(bitmap);
                            }
                        });
                    }
                }
            });
        }
        if (timeout > 0 && this.mTimer == null) {
            try {
                this.mTimer = new Timer();
                this.mTimer.schedule(new MyTimeTask(type, timeout), (long) (timeout * 1000));
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        }
    }

    public void update(final AppInfo appInfo) {
        if (appInfo != null) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5LoadingFrameworkImpl.this.setLoadingInfo("UPDATE_APPEARANCE_LOADING_TEXT", appInfo.name);
                    if (H5Utils.isTinyApp(appInfo) && !TextUtils.isEmpty(appInfo.slogan)) {
                        H5LoadingFrameworkImpl.this.setLoadingInfo("UPDATE_APPEARANCE_LOADING_BOTTOM_TIP", "本小程序由\"" + appInfo.slogan + "\"提供");
                    }
                }
            });
            if (!TextUtils.isEmpty(appInfo.icon_url)) {
                H5ImageUtil.loadImage(appInfo.icon_url, new H5ImageListener() {
                    public void onImage(final Bitmap bitmap) {
                        if (bitmap != null) {
                            H5Utils.runOnMain(new Runnable() {
                                public void run() {
                                    H5LoadingFrameworkImpl.this.setLoadingImage(bitmap);
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    public long getStartLoadingTime() {
        return this.mStartLoadingTime;
    }

    /* access modifiers changed from: private */
    public LoadingPageManager getLoadingManager() {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext().getLoadingPageManager();
    }

    public void exit() {
        H5Log.d(TAG, "loading exit");
        if (!TextUtils.isEmpty(this.mH5StartAppInfo.targetAppId)) {
            getLoadingManager().stopLoading(this.mH5StartAppInfo.targetAppId);
            getLoadingManager().unregisterLoadingPageHandler(this.mLoadingPageHandler);
            return;
        }
        H5Log.d(TAG, "exit targetAppId is null");
    }

    public void sendToWebFail() {
        H5Log.d(TAG, "sendToWebFail");
        if (this.mPageStatues != null) {
            this.mPageStatues.currentPageStatues = 2;
        }
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                if (H5LoadingFrameworkImpl.this.isReady() && !H5LoadingFrameworkImpl.this.mPlayingExitAnim && H5LoadingFrameworkImpl.this.getLoadingManager().findLoadingView(H5LoadingFrameworkImpl.this.mH5StartAppInfo.targetAppId) != null) {
                    H5LoadingFrameworkImpl.this.getLoadingManager().findLoadingView(H5LoadingFrameworkImpl.this.mH5StartAppInfo.targetAppId).stop();
                    H5LoadingFrameworkImpl.this.setLoadingInfo("UPDATE_APPEARANCE_LOADING_TEXT", H5Utils.getNebulaResources().getString(R.string.h5_network_poor));
                    H5LoadingFrameworkImpl.this.setLoadingInfo("UPDATE_APPEARANCE_LOADING_BOTTOM_TIP", "");
                }
            }
        });
    }

    public boolean isReady() {
        if (getLoadingManager().findLoadingView(this.mH5StartAppInfo.targetAppId) != null) {
            return true;
        }
        return false;
    }

    public boolean isPageDestroy() {
        if (getLoadingManager().findLoadingView(this.mH5StartAppInfo.targetAppId) == null) {
            return true;
        }
        return false;
    }

    public int getPageStatues() {
        return this.mPageStatues.currentPageStatues;
    }

    public void setPageStatue(int statue) {
        this.mPageStatues.currentPageStatues = statue;
    }

    public void playExitAnimation() {
        if (this.mPageStatues.currentPageStatues != 2) {
            if (TextUtils.isEmpty(this.mH5StartAppInfo.targetAppId) || getLoadingManager().findLoadingView(this.mH5StartAppInfo.targetAppId) == null) {
                H5Log.d(TAG, "exit targetAppId is null");
                return;
            }
            this.mPlayingExitAnim = true;
            getLoadingManager().findLoadingView(this.mH5StartAppInfo.targetAppId).performAnimation("ANIMATION_STOP_LOADING_PREPARE", null);
        }
    }

    public void onGetType(String type, int timeout, String appId) {
        H5Log.d(TAG, "onGetType " + type + " timeout:" + timeout + Token.SEPARATOR + this.mTimer);
        if (this.mTimer != null && timeout > 0) {
            try {
                this.mTimer.cancel();
                this.mTimer = new Timer();
                this.mTimer.schedule(new MyTimeTask(type, timeout), (long) (timeout * 1000));
            } catch (Throwable throwable) {
                H5Log.e((String) TAG, throwable);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setLoadingInfo(String key, String value) {
        Map data = new HashMap();
        data.put(key, value);
        if (getLoadingManager().findLoadingView(this.mH5StartAppInfo.targetAppId) != null) {
            getLoadingManager().findLoadingView(this.mH5StartAppInfo.targetAppId).sendMessage("UPDATE_APPEARANCE", data);
        }
    }

    /* access modifiers changed from: private */
    public void setLoadingImage(Bitmap bitmap) {
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        Map data = new HashMap();
        data.put("UPDATE_APPEARANCE_LOADING_ICON", drawable);
        if (getLoadingManager().findLoadingView(this.mH5StartAppInfo.targetAppId) != null) {
            getLoadingManager().findLoadingView(this.mH5StartAppInfo.targetAppId).sendMessage("UPDATE_APPEARANCE", data);
        }
    }
}
