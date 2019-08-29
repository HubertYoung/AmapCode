package com.alipay.mobile.tinyappcommon.embedview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Bundle;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Builder;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Page.H5PageHandler;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.refresh.H5PullContainer;
import com.alipay.mobile.nebula.refresh.H5PullableView;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5BaseEmbedView;
import com.alipay.mobile.nebula.view.H5Progress;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.ui.H5Fragment;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class H5EmbedWebView extends H5BaseEmbedView {
    public static final String ACTION_TYPE = "postMessage";
    public static final String ON_TO_WEBVIEW_MESSAGE = "onToWebViewMessage";
    /* access modifiers changed from: private */
    public static final String TAG = H5EmbedWebView.class.getSimpleName();
    public static final String WEB_VIEW_PAGE_TAG = "MINI-PROGRAM-WEB-VIEW-PAGE-TAG";
    public static final String WEB_VIEW_TAG = "MINI-PROGRAM-WEB-VIEW-TAG";
    public static final String WEB_VIEW_WORK_ID = "MINI-PROGRAM-WEB-VIEW-WORKID";
    /* access modifiers changed from: private */
    public boolean isEmbedWebViewFill;
    private String mAppId;
    /* access modifiers changed from: private */
    public View mRealView;
    private boolean mSupportOpenAppId;
    /* access modifiers changed from: private */
    public H5Page mWebViewH5Page;
    private OnLayoutChangeListener webViewLayoutFillChecker = new OnLayoutChangeListener() {
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    if (H5EmbedWebView.this.isEmbedWebViewFill) {
                        try {
                            H5Page h5Page = (H5Page) H5EmbedWebView.this.mH5Page.get();
                            if (h5Page != null) {
                                int w = h5Page.getWebView().getView().getWidth();
                                int h = h5Page.getWebView().getView().getHeight();
                                if (H5EmbedWebView.this.mRealView.getWidth() != w || H5EmbedWebView.this.mRealView.getHeight() != h) {
                                    LayoutParams layoutParams = H5EmbedWebView.this.mRealView.getLayoutParams();
                                    if (layoutParams == null) {
                                        return;
                                    }
                                    if (layoutParams.width != w || layoutParams.height != h) {
                                        layoutParams.width = w;
                                        layoutParams.height = h;
                                        H5EmbedWebView.this.mRealView.setLayoutParams(layoutParams);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            H5Log.e(H5EmbedWebView.TAG, (Throwable) e);
                        }
                    }
                }
            }, 16);
        }
    };

    public View getView(int width, int height, String viewId, String mType, Map<String, String> params) {
        H5Log.d(TAG, "getView...width=" + width + ",param=" + params.toString());
        if (this.mRealView == null) {
            H5Page h5Page = (H5Page) this.mH5Page.get();
            if (h5Page == null) {
                return null;
            }
            if (!(this.mContext.get() instanceof Activity)) {
                return null;
            }
            this.mAppId = params.get("appId");
            Bundle param = new Bundle();
            String currentAppId = H5Utils.getString(h5Page.getParams(), (String) "appId");
            param.putString("MINI-PROGRAM-WEB-VIEW-TAG", currentAppId);
            if (!TextUtils.isEmpty(this.mAppId)) {
                TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
                if (mixActionService != null) {
                    this.mSupportOpenAppId = !mixActionService.shouldInterceptWebviewOpenAppId(currentAppId, this.mAppId);
                }
                if (this.mSupportOpenAppId) {
                    param.putString("appId", this.mAppId);
                }
            }
            param.putString(H5Param.CREATEPAGESENCE, "H5Activity");
            param.putString(H5Fragment.fragmentType, H5Fragment.subtab);
            H5Session h5Session = h5Page.getSession();
            String workId = "";
            if (h5Session != null) {
                workId = h5Session.getServiceWorkerID();
                H5Log.d(TAG, "workId " + workId);
            }
            param.putString("element", params.get("id"));
            param.putString("viewId", viewId);
            param.putString("parentAppId", currentAppId);
            String packageNick = H5Utils.getString(h5Page.getParams(), (String) H5AppUtil.package_nick);
            String version = H5Utils.getString(h5Page.getParams(), (String) "appVersion");
            param.putString("parentPackageNick", packageNick);
            param.putString("parentVersion", version);
            H5Bundle bundle = new H5Bundle();
            bundle.setParams(param);
            this.mWebViewH5Page = ((H5Service) H5Utils.findServiceByInterface(H5Service.class.getName())).createPage((Activity) this.mContext.get(), bundle);
            this.mWebViewH5Page.setHandler(new H5PageHandler() {
                public boolean shouldExit() {
                    return false;
                }
            });
            this.mWebViewH5Page.setExtra(WEB_VIEW_WORK_ID, workId);
            this.mWebViewH5Page.setExtra(WEB_VIEW_PAGE_TAG, h5Page);
            h5Page.setExtra(WEB_VIEW_PAGE_TAG, this.mWebViewH5Page);
            prepareH5Progress();
            this.mRealView = this.mWebViewH5Page.getContentView();
        }
        return this.mRealView;
    }

    /* access modifiers changed from: private */
    public void titleChanged() {
        try {
            String title = this.mWebViewH5Page.getWebView().getTitle();
            H5Log.d(TAG, "titleChanged...title=" + title);
            ((H5Page) this.mH5Page.get()).getH5TitleBar().setTitle(title);
        } catch (Throwable e) {
            H5Log.e(TAG, "titleChanged...e=" + e);
        }
    }

    public void onEmbedViewAttachedToWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
        try {
            prepareCheckWebViewFill(params);
        } catch (Exception e) {
            H5Log.e(TAG, (Throwable) e);
        }
    }

    private boolean enableCheckWebViewFill() {
        H5ConfigProvider h5ConfigProvider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (h5ConfigProvider != null) {
            return "1".equals(h5ConfigProvider.getConfig("ta_webview_fill_check"));
        }
        return false;
    }

    private void prepareCheckWebViewFill(Map<String, String> params) {
        boolean z = true;
        if (params != null && enableCheckWebViewFill()) {
            String style = params.get(ResUtils.STYLE);
            if (!TextUtils.isEmpty(style)) {
                boolean isWidthFill = false;
                boolean isHeightFill = false;
                String[] styles = style.split(";");
                if (styles != null && styles.length > 0) {
                    for (String styleItem : styles) {
                        if (isWidthFill && isHeightFill) {
                            break;
                        }
                        String styleItem2 = styleItem.trim();
                        if (styleItem2.startsWith("width:")) {
                            String[] styleItemValues = styleItem2.split(":");
                            if (styleItemValues != null && styleItemValues.length == 2) {
                                isWidthFill = TextUtils.equals(styleItemValues[1].trim(), "100%");
                            }
                        } else if (styleItem2.startsWith("height:")) {
                            String[] styleItemValues2 = styleItem2.split(":");
                            if (styleItemValues2 != null && styleItemValues2.length == 2) {
                                isHeightFill = TextUtils.equals(styleItemValues2[1].trim(), "100%");
                            }
                        }
                    }
                }
                if (!isWidthFill || !isHeightFill) {
                    z = false;
                }
                this.isEmbedWebViewFill = z;
            }
            if (this.isEmbedWebViewFill && this.mRealView != null) {
                this.mRealView.removeOnLayoutChangeListener(this.webViewLayoutFillChecker);
                this.mRealView.addOnLayoutChangeListener(this.webViewLayoutFillChecker);
            }
        }
    }

    public void onEmbedViewDetachedFromWebView(int width, int height, String viewId, String mType, Map<String, String> params) {
    }

    public void onEmbedViewDestory(int width, int height, String viewId, String mType, Map<String, String> params) {
    }

    public void onEmbedViewParamChanged(int width, int height, String viewId, String mType, Map<String, String> params, String[] name, String[] value) {
    }

    public void onEmbedViewVisibilityChanged(int width, int height, String viewId, String mType, Map<String, String> params, int reason) {
    }

    public void onWebViewResume() {
        if (this.mWebViewH5Page != null) {
            H5Page h5Page = (H5Page) this.mH5Page.get();
            if (h5Page == null) {
                H5Log.w(TAG, "webview resume fatal error");
                return;
            }
            String popParam = h5Page.getSession().getData().get(H5Param.H5_SESSION_POP_PARAM);
            String resumeParam = h5Page.getSession().getData().get(H5Param.H5_SESSION_RESUME_PARAM);
            JSONObject data = new JSONObject();
            if (!TextUtils.isEmpty(popParam)) {
                JSONObject objData = H5Utils.parseObject(popParam);
                if (objData != null) {
                    data.put((String) "data", (Object) objData);
                } else {
                    JSONArray arrayData = H5Utils.parseArray(popParam);
                    if (arrayData != null) {
                        data.put((String) "data", (Object) arrayData);
                    } else {
                        data.put((String) "data", (Object) popParam);
                    }
                }
            }
            if (!TextUtils.isEmpty(resumeParam)) {
                data.put((String) "resumeParams", (Object) H5Utils.parseObject(resumeParam));
            }
            this.mWebViewH5Page.getBridge().sendToWeb(new Builder().action("resume").param(data).type("call").build());
        }
    }

    public void onWebViewPause() {
    }

    public void onWebViewDestory() {
        if (this.mWebViewH5Page != null) {
            this.mWebViewH5Page.setHandler(null);
            this.mWebViewH5Page.exitPage();
        }
        this.mRealView = null;
        this.mWebViewH5Page = null;
    }

    public void onReceivedMessage(String actionType, JSONObject data, H5BridgeContext bridgeContext) {
        if (!ACTION_TYPE.equals(actionType)) {
            return;
        }
        if (this.mWebViewH5Page == null) {
            H5Log.d(TAG, "onReceivedMessage...mWebViewH5Page is null");
        } else if (data == null) {
            H5Log.d(TAG, "onReceivedMessage...actionType=" + actionType);
            JSONObject result = new JSONObject();
            result.put((String) "error", (Object) Integer.valueOf(2));
            result.put((String) "errorMessage", (Object) "data is null");
            this.mWebViewH5Page.getBridge().sendDataWarpToWeb(ON_TO_WEBVIEW_MESSAGE, result, null);
        } else {
            int callback = H5Utils.getInt(data, (String) "callback");
            JSONObject res = H5Utils.getJSONObject(data, "res", null);
            JSONObject result2 = new JSONObject();
            result2.put((String) "callback", (Object) Integer.valueOf(callback));
            result2.put((String) "res", (Object) res);
            this.mWebViewH5Page.getBridge().sendDataWarpToWeb(ON_TO_WEBVIEW_MESSAGE, result2, null);
        }
    }

    /* access modifiers changed from: 0000 */
    public H5Page getMainDOMPage() {
        H5Page mainDOMPage = (H5Page) this.mH5Page.get();
        Object obj = mainDOMPage.getExtra(WEB_VIEW_PAGE_TAG);
        if (obj instanceof H5Page) {
            return (H5Page) obj;
        }
        return mainDOMPage;
    }

    public void onReceivedRender(JSONObject data, H5BridgeContext bridgeContext) {
        if (this.mWebViewH5Page == null) {
            H5Log.d(TAG, "onReceivedRender..webview h5Page is null");
            return;
        }
        String url = data.getString("src");
        H5Log.d(TAG, "onReceivedRender...url=" + url);
        if (TextUtils.isEmpty(this.mAppId)) {
            this.mWebViewH5Page.loadUrl(url);
        }
        ((H5Page) this.mH5Page.get()).getH5TitleBar().setTitle(url);
        setBackButtonVisibility();
        this.mWebViewH5Page.getPluginManager().register((H5Plugin) new H5SimplePlugin() {
            public void onPrepare(H5EventFilter filter) {
                super.onPrepare(filter);
                filter.addAction(CommonEvents.H5_PAGE_RECEIVED_TITLE);
            }

            public boolean interceptEvent(H5Event event, H5BridgeContext context) {
                if (CommonEvents.H5_PAGE_RECEIVED_TITLE.equals(event.getAction())) {
                    H5EmbedWebView.this.titleChanged();
                }
                return super.interceptEvent(event, context);
            }
        });
        ((H5Page) this.mH5Page.get()).getPluginManager().register((H5Plugin) new H5SimplePlugin() {
            public void onPrepare(H5EventFilter filter) {
                super.onPrepare(filter);
                filter.addAction(CommonEvents.H5_PAGE_BACK);
            }

            public boolean interceptEvent(H5Event event, H5BridgeContext context) {
                if (CommonEvents.H5_PAGE_BACK.equals(event.getAction())) {
                    return H5EmbedWebView.this.interceptBackEvent();
                }
                return super.interceptEvent(event, context);
            }
        });
        ((H5Page) this.mH5Page.get()).getPluginManager().register((H5Plugin) new H5SimplePlugin() {
            public void onPrepare(H5EventFilter filter) {
                super.onPrepare(filter);
                filter.addAction(CommonEvents.H5_PAGE_FINISHED);
            }

            public boolean interceptEvent(H5Event event, H5BridgeContext context) {
                CommonEvents.H5_PAGE_FINISHED.equals(event.getAction());
                return super.interceptEvent(event, context);
            }
        });
    }

    private void setBackButtonVisibility() {
        H5Page parentH5Page = (H5Page) this.mH5Page.get();
        if (parentH5Page != null) {
            int pageSize = 0;
            H5Session h5Session = parentH5Page.getSession();
            if (!(h5Session == null || h5Session.getPages() == null)) {
                pageSize = Nebula.getSessionPagesWithOutPrerender(h5Session.getPages()).size();
            }
            if (pageSize == 1 || TextUtils.equals(H5Fragment.subtab, H5Utils.getString(parentH5Page.getParams(), (String) H5Fragment.fragmentType))) {
                parentH5Page.sendEvent("showBackButton", null);
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean interceptBackEvent() {
        try {
            if (this.mWebViewH5Page == null) {
                return false;
            }
            if (!canInterceptBackEvent()) {
                H5Log.d(TAG, "interceptBackEvent...can not intercept back event");
                return false;
            }
            APWebView apWebView = this.mWebViewH5Page.getWebView();
            if (apWebView == null || !apWebView.canGoBack()) {
                H5Log.d(TAG, "interceptBackEvent...can not go back");
                return false;
            } else if (apWebView.copyBackForwardList().getCurrentIndex() <= 0) {
                H5Log.d(TAG, "interceptBackEvent...webview with no history");
                return false;
            } else {
                H5Log.e(TAG, (String) "interceptBackEvent...go back");
                apWebView.goBack();
                return true;
            }
        } catch (Throwable e) {
            H5Log.e(TAG, "interceptBackEvent...e=" + e);
            return false;
        }
    }

    public View getSpecialRestoreView(int width, int height, String viewId, String mType, Map<String, String> params) {
        return null;
    }

    public Bitmap getSnapshot(int width, int height, String viewId, String mType, Map<String, String> params) {
        return getBitmapFromView(this.mRealView);
    }

    public void triggerPreSnapshot() {
        H5Log.d(TAG, "triggerPreSnapshot...");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance((Context) this.mContext.get());
        Intent intent = new Intent();
        intent.setAction("embedview.snapshot.complete");
        localBroadcastManager.sendBroadcast(intent);
    }

    private Bitmap getBitmapFromView(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(c);
        } else {
            c.drawColor(-1);
        }
        view.draw(c);
        return bitmap;
    }

    /* access modifiers changed from: protected */
    public boolean canInterceptBackEvent() {
        return true;
    }

    private void prepareH5Progress() {
        try {
            View parentRootView = ((H5Page) this.mH5Page.get()).getRootView();
            H5PullContainer h5PullContainer = (H5PullContainer) parentRootView.findViewById(R.id.h5_pc_container);
            if (h5PullContainer != null) {
                h5PullContainer.setPullableView((H5PullableView) this.mWebViewH5Page.getWebView());
            }
            View ucProviderLayout = parentRootView.findViewById(R.id.h5_ly_providerLayout);
            if (ucProviderLayout != null && ucProviderLayout.getVisibility() == 0) {
                ucProviderLayout.setVisibility(8);
            }
            final View h5ProviderLayout = parentRootView.findViewById(R.id.h5_ly_provider_layout);
            final TextView h5ProviderDomain = (TextView) parentRootView.findViewById(R.id.h5_tv_provider_domain);
            this.mWebViewH5Page.getPluginManager().register((H5Plugin) new H5SimplePlugin() {
                public void onPrepare(H5EventFilter filter) {
                    filter.addAction(CommonEvents.H5_PAGE_FINISHED);
                }

                public boolean interceptEvent(H5Event event, H5BridgeContext context) {
                    if (CommonEvents.H5_PAGE_FINISHED.equals(event.getAction())) {
                        if (h5ProviderDomain != null) {
                            String host = H5UrlHelper.getHost(H5EmbedWebView.this.mWebViewH5Page.getUrl());
                            if (TextUtils.isEmpty(host)) {
                                h5ProviderDomain.setText("");
                            } else {
                                h5ProviderDomain.setText("网页由 " + host + " 提供");
                            }
                        }
                        if (!(h5ProviderLayout == null || h5ProviderLayout.getVisibility() == 0)) {
                            h5ProviderLayout.setVisibility(0);
                        }
                    }
                    return false;
                }
            });
            TinyAppMixActionService service = TinyAppService.get().getMixActionService();
            if (service != null && service.isEmbedWebViewShowProgress()) {
                try {
                    final WeakReference h5ProgressRef = new WeakReference((H5Progress) ((H5Page) this.mH5Page.get()).getRootView().findViewById(R.id.h5_pb_progress));
                    if (h5ProgressRef.get() != null) {
                        final Handler h5ProgressHandler = new Handler(Looper.getMainLooper());
                        final AtomicLong h5ProgressUpdateTime = new AtomicLong(0);
                        this.mWebViewH5Page.getParams().putBoolean(H5Param.LONG_SHOW_PROGRESS, true);
                        this.mWebViewH5Page.getPluginManager().register((H5Plugin) new H5SimplePlugin() {
                            public void onPrepare(H5EventFilter filter) {
                                filter.addAction(CommonEvents.H5_PAGE_PROGRESS);
                                filter.addAction(CommonEvents.H5_PAGE_FINISHED);
                                filter.addAction(CommonEvents.H5_PAGE_STARTED);
                                filter.addAction(CommonEvents.H5_TOOLBAR_RELOAD);
                            }

                            public boolean interceptEvent(H5Event event, H5BridgeContext context) {
                                String action = event.getAction();
                                if (CommonEvents.H5_PAGE_PROGRESS.equals(action)) {
                                    final int progress = H5Utils.getInt(event.getParam(), (String) "progress");
                                    if (h5ProgressRef.get() != null) {
                                        long now = System.currentTimeMillis();
                                        if (now - h5ProgressUpdateTime.get() >= 50) {
                                            h5ProgressUpdateTime.set(now);
                                            h5ProgressHandler.post(new Runnable() {
                                                public void run() {
                                                    H5Progress h5Progress = (H5Progress) h5ProgressRef.get();
                                                    if (h5Progress != null) {
                                                        h5Progress.updateProgress(progress);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                } else if (CommonEvents.H5_PAGE_STARTED.equals(action) || CommonEvents.H5_TOOLBAR_RELOAD.equals(action)) {
                                    if (h5ProgressRef.get() != null) {
                                        h5ProgressUpdateTime.set(System.currentTimeMillis());
                                        h5ProgressHandler.post(new Runnable() {
                                            public void run() {
                                                H5Progress h5Progress = (H5Progress) h5ProgressRef.get();
                                                if (h5Progress != null) {
                                                    h5Progress.setVisibility(0);
                                                    h5Progress.updateProgress(0);
                                                }
                                            }
                                        });
                                    }
                                } else if (CommonEvents.H5_PAGE_FINISHED.equals(action) && h5ProgressRef.get() != null) {
                                    h5ProgressUpdateTime.set(System.currentTimeMillis());
                                    h5ProgressHandler.postDelayed(new Runnable() {
                                        public void run() {
                                            H5Progress h5Progress = (H5Progress) h5ProgressRef.get();
                                            if (h5Progress != null) {
                                                h5Progress.setVisibility(8);
                                            }
                                        }
                                    }, 300);
                                }
                                return false;
                            }
                        });
                    }
                } catch (Exception e) {
                    H5Log.e(TAG, (Throwable) e);
                }
            }
        } catch (Exception e2) {
            H5Log.e(TAG, (Throwable) e2);
        }
    }
}
