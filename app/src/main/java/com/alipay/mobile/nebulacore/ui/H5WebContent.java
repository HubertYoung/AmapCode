package com.alipay.mobile.nebulacore.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.widget.RelativeLayout.LayoutParams;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5PullHeader;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.h5container.api.H5WebContentImpl;
import com.alipay.mobile.nebula.R;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.provider.H5ConfigProvider;
import com.alipay.mobile.nebula.provider.H5DisClaimerProvider;
import com.alipay.mobile.nebula.provider.H5PullHeaderViewProvider;
import com.alipay.mobile.nebula.provider.H5ViewProvider;
import com.alipay.mobile.nebula.provider.H5WarningTipProvider;
import com.alipay.mobile.nebula.refresh.H5PullAdapter;
import com.alipay.mobile.nebula.refresh.H5PullContainer;
import com.alipay.mobile.nebula.util.H5ImageUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5PatternHelper;
import com.alipay.mobile.nebula.util.H5ThirdDisclaimerUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.H5LoadingView;
import com.alipay.mobile.nebula.view.H5Progress;
import com.alipay.mobile.nebula.view.H5PullHeaderView;
import com.alipay.mobile.nebula.view.H5WebContentView;
import com.alipay.mobile.nebula.webview.WebViewType;
import com.alipay.mobile.nebulacore.Nebula;
import com.alipay.mobile.nebulacore.core.H5PageImpl;
import com.alipay.mobile.nebulacore.env.H5Environment;
import com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl;
import com.taobao.accs.common.Constants;
import java.util.regex.Pattern;

public class H5WebContent extends H5SimplePlugin {
    public static final String TAG = "H5WebContent";
    private static Pattern[] a = null;
    /* access modifiers changed from: private */
    public boolean b = false;
    /* access modifiers changed from: private */
    public H5PullContainer c;
    private H5DisClaimerProvider d;
    /* access modifiers changed from: private */
    public H5Progress e;
    private View f;
    private boolean g;
    /* access modifiers changed from: private */
    public boolean h;
    private boolean i;
    /* access modifiers changed from: private */
    public boolean j;
    /* access modifiers changed from: private */
    public H5PageImpl k;
    /* access modifiers changed from: private */
    public boolean l;
    /* access modifiers changed from: private */
    public boolean m;
    /* access modifiers changed from: private */
    public H5WebContentView n;
    private boolean o;
    private String p;
    private H5CloseHandler q;
    private H5Bridge r;
    private int s;
    /* access modifiers changed from: private */
    public boolean t = false;
    private H5PullAdapter u = new H5PullAdapter() {
        private H5PullHeaderView b;

        public void onLoading() {
            if (this.b != null && H5WebContent.this.k != null) {
                this.b.showLoading();
                H5Log.d(H5WebContent.TAG, "onLoading,TimeoutRunnable begin,send event FIRE_PULL_TO_REFRESH ");
                H5WebContent.this.k.sendEvent(CommonEvents.FIRE_PULL_TO_REFRESH, null);
                H5Utils.runOnMain(new TimeoutRunnable(), 20000);
            }
        }

        public boolean canRefresh() {
            return H5WebContent.this.j;
        }

        public boolean canPull() {
            return !H5WebContent.this.b && H5WebContent.this.h;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0032, code lost:
            if (r4.b == null) goto L_0x0034;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.view.View getHeaderView() {
            /*
                r4 = this;
                com.alipay.mobile.nebulacore.ui.H5WebContent r2 = com.alipay.mobile.nebulacore.ui.H5WebContent.this
                com.alipay.mobile.nebulacore.core.H5PageImpl r2 = r2.k
                com.alipay.mobile.h5container.api.H5Context r2 = r2.getContext()
                android.content.Context r0 = r2.getContext()
                com.alipay.mobile.nebula.view.H5PullHeaderView r2 = r4.b
                if (r2 != 0) goto L_0x0040
                com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl r2 = com.alipay.mobile.nebulacore.manager.H5ProviderManagerImpl.getInstance()
                java.lang.Class<com.alipay.mobile.nebula.provider.H5ViewProvider> r3 = com.alipay.mobile.nebula.provider.H5ViewProvider.class
                java.lang.String r3 = r3.getName()
                java.lang.Object r1 = r2.getProvider(r3)
                com.alipay.mobile.nebula.provider.H5ViewProvider r1 = (com.alipay.mobile.nebula.provider.H5ViewProvider) r1
                if (r1 == 0) goto L_0x0034
                com.alipay.mobile.nebulacore.ui.H5WebContent r2 = com.alipay.mobile.nebulacore.ui.H5WebContent.this
                com.alipay.mobile.nebula.refresh.H5PullContainer r2 = r2.c
                com.alipay.mobile.nebula.view.H5PullHeaderView r2 = r1.createPullHeaderView(r0, r2)
                r4.b = r2
                com.alipay.mobile.nebula.view.H5PullHeaderView r2 = r4.b
                if (r2 != 0) goto L_0x0040
            L_0x0034:
                com.alipay.mobile.nebulacore.ui.H5WebContent r2 = com.alipay.mobile.nebulacore.ui.H5WebContent.this
                com.alipay.mobile.nebula.refresh.H5PullContainer r2 = r2.c
                com.alipay.mobile.nebula.view.H5PullHeaderView r2 = com.alipay.mobile.nebulacore.ui.H5WebContent.b(r0, r2)
                r4.b = r2
            L_0x0040:
                com.alipay.mobile.nebula.view.H5PullHeaderView r2 = r4.b
                android.view.View r2 = r2.getContentView()
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.ui.H5WebContent.AnonymousClass1.getHeaderView():android.view.View");
        }

        public void onProgressUpdate(int progress) {
            this.b.onProgressUpdate(progress);
        }

        public void onOpen() {
            if (this.b != null) {
                this.b.showOpen(H5WebContent.this.b());
                H5Log.d(H5WebContent.TAG, "open ");
            }
        }

        public void onOver() {
            if (this.b != null) {
                this.b.showOver();
            }
        }

        public void onFinish() {
            if (this.b != null) {
                this.b.showFinish();
                H5WebContent.this.m = true;
            }
        }

        public void onRefreshFinish() {
            if (this.b != null) {
                this.b.onRefreshFinish();
            }
            if (H5WebContent.this.t) {
                H5WebContent.this.c.notifyViewChanged();
            }
            H5WebContent.this.t = false;
        }
    };

    private class H5CloseHandler implements H5CallBack {
        public long lastClose = 0;
        public boolean waiting = false;

        public H5CloseHandler() {
        }

        public void onCallBack(JSONObject param) {
            this.waiting = false;
            boolean prevent = H5Utils.getBoolean(param, (String) "prevent", false);
            H5Log.d(H5WebContent.TAG, "close event prevent " + prevent);
            if (!prevent) {
                H5WebContent.this.k();
            }
        }
    }

    class PullFreshCallback implements H5CallBack {
        public PullFreshCallback() {
        }

        public void onCallBack(JSONObject param) {
            boolean prevent = H5Utils.getBoolean(param, (String) "prevent", false);
            H5Log.d(H5WebContent.TAG, "FIRE_PULL_TO_REFRESH event prevent：" + prevent);
            if (prevent) {
                H5Log.d(H5WebContent.TAG, "set e.preventDefault(),not send H5_PAGE_RELOAD");
                return;
            }
            H5Log.d(H5WebContent.TAG, "not set e.preventDefault(),sendEvent H5_PAGE_RELOAD");
            H5WebContent.this.k.sendEvent(CommonEvents.H5_PAGE_RELOAD, null);
            H5WebContent.this.m = true;
        }
    }

    private class TimeoutRunnable implements Runnable {
        public TimeoutRunnable() {
        }

        public void run() {
            H5Log.d(H5WebContent.TAG, "TimeoutRunnable timeout,pullFreshTimeOut :" + H5WebContent.this.l);
            if (H5WebContent.this.l) {
                H5Log.d(H5WebContent.TAG, "TimeoutRunnable timeout,invoke restorePullToRefresh");
                H5WebContent.this.e.setVisibility(8);
                H5WebContent.this.c.fitContent();
                H5WebContent.this.l = true;
                H5WebContent.this.m = true;
                return;
            }
            H5WebContent.this.l = true;
        }
    }

    static {
        a();
    }

    private static void a() {
        H5ConfigProvider provider = (H5ConfigProvider) H5Utils.getProvider(H5ConfigProvider.class.getName());
        if (provider != null && H5Utils.isUCM57()) {
            String configArrayStr = provider.getConfigWithProcessCache("h5_disablePullDownPatterns");
            if (configArrayStr != null) {
                JSONArray configArray = H5Utils.parseArray(configArrayStr);
                if (configArray != null) {
                    int size = configArray.size();
                    a = new Pattern[size];
                    for (int i2 = 0; i2 < size; i2++) {
                        a[i2] = H5PatternHelper.compile(configArray.getString(i2));
                    }
                }
            }
        }
    }

    public H5WebContent(H5PageImpl h5Page) {
        int i2 = 0;
        this.k = h5Page;
        this.h = true;
        this.g = false;
        this.l = true;
        this.m = true;
        this.o = false;
        this.q = new H5CloseHandler();
        this.r = h5Page.getBridge();
        if (a != null) {
            String url = H5Utils.getString(h5Page.getParams(), (String) "url");
            Pattern[] patternArr = a;
            int length = patternArr.length;
            while (true) {
                if (i2 >= length) {
                    break;
                } else if (patternArr[i2].matcher(url).find()) {
                    this.b = true;
                    H5Log.d(TAG, "disable pullDown by config");
                    break;
                } else {
                    i2++;
                }
            }
        }
        c();
    }

    /* access modifiers changed from: private */
    public static H5PullHeaderView b(Context context, ViewGroup viewGroup) {
        H5PullHeaderViewProvider headerViewProvider = (H5PullHeaderViewProvider) H5ProviderManagerImpl.getInstance().getProvider(H5PullHeaderViewProvider.class.getName());
        if (headerViewProvider == null || !headerViewProvider.enableUsePullHeader()) {
            return new H5PullHeader(context, viewGroup);
        }
        return headerViewProvider.createPullHeaderView(context, viewGroup);
    }

    /* access modifiers changed from: private */
    public int b() {
        if (this.k == null || this.k.getParams() == null) {
            return 0;
        }
        String paramStyle = H5Utils.getString(this.k.getParams(), (String) H5Param.PULL_REFRESH_STYLE);
        if (TextUtils.equals(paramStyle, "default")) {
            return 0;
        }
        if (TextUtils.equals(paramStyle, H5Param.LONG_TRANSPARENT)) {
            return 1;
        }
        if (this.n == null || this.n.getContentView() == null) {
            return 0;
        }
        Drawable drawable = this.n.getContentView().getBackground();
        if (!(drawable instanceof ColorDrawable)) {
            return 0;
        }
        int color = ((ColorDrawable) drawable).getColor();
        if ((((double) ((16711680 & color) >> 16)) * 0.299d) + (((double) ((65280 & color) >> 8)) * 0.587d) + (((double) (color & 255)) * 0.114d) <= 200.0d) {
            return 1;
        }
        return 0;
    }

    public void setUrlProviderMargin(int height) {
        LayoutParams mLayoutParams = new LayoutParams(-1, -1);
        mLayoutParams.topMargin = height;
        this.n.getProviderLayout().setLayoutParams(mLayoutParams);
    }

    public void onRelease() {
        this.k = null;
        this.q = null;
        this.r = null;
    }

    public H5Page getPage() {
        return this.k;
    }

    public View getContent() {
        return this.n.getContentView();
    }

    @SuppressLint({"InflateParams"})
    private void c() {
        Context context = this.k.getContext().getContext();
        H5ViewProvider h5ViewProvider = (H5ViewProvider) Nebula.getProviderManager().getProvider(H5ViewProvider.class.getName());
        if (h5ViewProvider != null) {
            this.n = h5ViewProvider.createWebContentView(context);
        }
        if (h5ViewProvider == null || this.n == null) {
            this.n = new H5WebContentImpl(context);
        }
        this.f = this.n.getHdivider();
        this.o = this.n.isCustomBackground();
        H5Log.d(TAG, "isCustomBackground " + this.o);
        this.e = this.n.getProgress();
        this.c = this.n.getPullContainer();
        this.c.setContentView(this.k.getWebView().getView());
        this.c.setPullableView(this.k.getWebView());
        this.c.setPullAdapter(this.u);
        this.c.setH5Page(this.k);
        refreshView();
        d();
        this.d = (H5DisClaimerProvider) H5Utils.getProvider(H5DisClaimerProvider.class.getName());
        if (this.k != null) {
            this.s = H5ThirdDisclaimerUtils.needShowDisclaimer(this.k.getParams(), "");
            if (this.d != null && this.s != 0) {
                this.d.showDisclaimer(this.k, this.s);
            }
        }
    }

    public void refreshView() {
        boolean visible = H5Utils.getBoolean(this.k.getParams(), (String) H5Param.LONG_SHOW_PROGRESS, false);
        this.e.setVisibility(visible ? 0 : 8);
        this.g = visible;
        this.i = H5Utils.getBoolean(this.k.getParams(), (String) "showDomain", true);
        this.j = H5Utils.getBoolean(this.k.getParams(), (String) "pullRefresh", false);
        this.h = H5Utils.getBoolean(this.k.getParams(), (String) "canPullDown", true);
        e();
    }

    private void d() {
        Bundle params = this.k.getParams();
        if (params != null) {
            if (params.containsKey(H5Param.BACKGROUND_IMAGE_URL)) {
                a(H5Utils.getString(params, (String) H5Param.BACKGROUND_IMAGE_URL));
            }
            if (params.containsKey(H5Param.BACKGROUND_IMAGE_COLOR)) {
                int imageColor = H5Utils.getInt(params, (String) H5Param.BACKGROUND_IMAGE_COLOR);
                if (imageColor != -1) {
                    this.n.setBackgroundImageColor(imageColor);
                }
            }
        }
    }

    private void a(String imageUrl) {
        Bundle params = this.k.getParams();
        if (params != null && !TextUtils.isEmpty(imageUrl)) {
            if (!imageUrl.startsWith("http")) {
                Bitmap bitmap = H5ImageUtil.base64ToBitmap(imageUrl);
                if (bitmap != null) {
                    this.n.setBackgroundImage(bitmap);
                    return;
                }
                imageUrl = H5Utils.getAbsoluteUrlWithURLLib(H5Utils.getString(params, (String) "url"), imageUrl);
            }
            H5Log.d(TAG, "imageUrl : " + imageUrl);
            a(imageUrl, params);
        }
    }

    private void a(final String imageUrl, final Bundle params) {
        if (this.k.getSession() == null || this.k.getSession().getWebProvider() == null) {
            a(params, imageUrl);
            return;
        }
        String onlineHost = H5Utils.getString(params, (String) H5Param.ONLINE_HOST);
        if (TextUtils.isEmpty(onlineHost) || !imageUrl.startsWith(onlineHost)) {
            a(params, imageUrl);
        } else {
            this.k.getSession().getWebProvider().getContent(imageUrl, (ResponseListen) new ResponseListen() {
                public void onGetResponse(WebResourceResponse webResourceResponse) {
                    H5Log.d(H5WebContent.TAG, "showImage form offline package");
                    if (webResourceResponse == null || webResourceResponse.getData() == null) {
                        H5WebContent.this.a(params, imageUrl);
                        return;
                    }
                    final Bitmap bitmap = BitmapFactory.decodeStream(webResourceResponse.getData());
                    if (bitmap != null) {
                        H5Utils.runOnMain(new Runnable() {
                            public void run() {
                                H5WebContent.this.n.setBackgroundImage(bitmap);
                            }
                        });
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(Bundle params, String imageUrl) {
        String onlineHost = H5Utils.getString(params, (String) H5Param.ONLINE_HOST);
        String cdnBaseUrl = H5Utils.getString(params, (String) H5Param.CDN_HOST);
        if (!TextUtils.isEmpty(onlineHost) && !TextUtils.isEmpty(cdnBaseUrl) && imageUrl.startsWith(onlineHost)) {
            imageUrl = imageUrl.replace(onlineHost, cdnBaseUrl);
            H5Log.d(TAG, " after replace " + imageUrl);
        }
        Nebula.loadImageKeepSize(imageUrl, new H5ImageListener() {
            public void onImage(Bitmap bitmap) {
                if (bitmap != null) {
                    H5WebContent.this.n.setBackgroundImage(bitmap);
                }
            }
        });
    }

    private void e() {
        if (H5Utils.getBoolean(this.k.getParams(), (String) H5Param.isH5app, false) || this.j) {
            this.n.showProviderVisibility(false);
        } else {
            this.n.showProviderVisibility(true);
        }
        this.c.notifyViewChanged();
    }

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CommonEvents.H5_PAGE_STARTED);
        filter.addAction(CommonEvents.H5_PAGE_PROGRESS);
        filter.addAction(CommonEvents.H5_PAGE_FINISHED);
        filter.addAction(CommonEvents.H5_PAGE_BACKGROUND);
        filter.addAction(CommonEvents.H5_PAGE_PHYSICAL_BACK);
        filter.addAction(CommonEvents.H5_TOOLBAR_BACK);
        filter.addAction(CommonEvents.H5_TOOLBAR_CLOSE);
        filter.addAction(CommonEvents.H5_TOOLBAR_RELOAD);
        filter.addAction(CommonEvents.H5_TITLEBAR_TITLE);
        filter.addAction(CommonEvents.H5_TITLEBAR_SUBTITLE);
        filter.addAction(CommonEvents.CLOSE_WEBVIEW);
        filter.addAction("pullRefresh");
        filter.addAction("canPullDown");
        filter.addAction("showDomain");
        filter.addAction(CommonEvents.SHOW_PROGRESS_BAR);
        filter.addAction(CommonEvents.FIRE_PULL_TO_REFRESH);
        filter.addAction(CommonEvents.RESTORE_PULL_TO_REFRESH);
        filter.addAction("setPullDownText");
        filter.addAction(CommonEvents.H5_RPC_FAILED);
        filter.addAction(CommonEvents.H5_PAGE_ERROR);
        filter.addAction("showProgressBar_fallback");
        filter.addAction(H5Param.LONG_BOUNCE_TOP_COLOR);
        filter.addAction("disClaimerClick");
        filter.addAction("showDisClaimer");
        filter.addAction(CommonEvents.H5_SET_WEBVIEW_TOP);
        filter.addAction(CommonEvents.SET_BACKGROUND_IMAGE);
        filter.addAction(CommonEvents.REMOVE_BACKGROUND_IMAGE);
        filter.addAction(CommonEvents.START_PULL_DOWN_REFRESH);
        filter.addAction(CommonEvents.H5_SET_CAN_PULL_DOWN);
    }

    public boolean interceptEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        JSONObject param = event.getParam();
        if (CommonEvents.H5_PAGE_STARTED.equals(action) || CommonEvents.H5_TOOLBAR_RELOAD.equals(action)) {
            if (this.g) {
                this.e.setProgress(0);
                this.e.setVisibility(0);
            }
        } else if (CommonEvents.H5_PAGE_FINISHED.equals(action)) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    H5WebContent.this.g();
                }
            }, 300);
        } else if (CommonEvents.H5_PAGE_PROGRESS.equals(action)) {
            this.e.updateProgress(H5Utils.getInt(param, (String) "progress"));
        } else if (!CommonEvents.H5_PAGE_BACKGROUND.equals(action) && CommonEvents.H5_PAGE_ERROR.equals(action)) {
            this.k.hideLoadingView();
            if (this.d != null) {
                this.d.hideDisclaimer(this.k);
            }
            H5WarningTipProvider tipProvider = (H5WarningTipProvider) H5Utils.getProvider(H5WarningTipProvider.class.getName());
            if (tipProvider != null) {
                tipProvider.hideWarningTip(this.k);
            }
        }
        return false;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext bridgeContext) {
        String action = event.getAction();
        JSONObject param = event.getParam();
        if (CommonEvents.H5_TITLEBAR_TITLE.equals(action) || CommonEvents.H5_TITLEBAR_SUBTITLE.equals(action)) {
            JSONObject data = new JSONObject();
            data.put((String) "data", (Object) param);
            if (!(this.k == null || this.k.getBridge() == null)) {
                this.k.getBridge().sendToWeb(action, data, null);
            }
        } else if ("pullRefresh".equals(action)) {
            this.j = H5Utils.getBoolean(param, (String) "pullRefresh", false);
            e();
        } else if ("canPullDown".equals(action)) {
            this.h = H5Utils.getBoolean(param, (String) "canPullDown", true);
        } else if ("showDomain".equals(action)) {
            this.i = H5Utils.getBoolean(param, (String) "showDomain", true);
        } else if (CommonEvents.CLOSE_WEBVIEW.equals(action)) {
            this.k.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
        } else if (CommonEvents.H5_PAGE_PHYSICAL_BACK.equals(action) || CommonEvents.H5_TOOLBAR_BACK.equals(action)) {
            this.k.sendEvent(CommonEvents.H5_PAGE_BACK, null);
        } else if (CommonEvents.H5_TOOLBAR_CLOSE.equals(action)) {
            h();
        } else if (CommonEvents.H5_TOOLBAR_RELOAD.equals(action)) {
            this.k.sendEvent(CommonEvents.H5_PAGE_RELOAD, null);
        } else if (CommonEvents.SHOW_PROGRESS_BAR.equals(action)) {
            this.g = H5Utils.getBoolean(param, (String) H5Param.LONG_SHOW_PROGRESS, false);
            if (!this.g) {
                this.e.setVisibility(8);
            }
        } else if ("showProgressBar_fallback".equals(action)) {
            H5Log.d(TAG, "showProgressBar_fallback");
            this.e.setVisibility(0);
        } else if (CommonEvents.FIRE_PULL_TO_REFRESH.equals(action)) {
            b(action);
        } else if (CommonEvents.RESTORE_PULL_TO_REFRESH.equals(action)) {
            H5Log.d(TAG, "handleEvent restorePullToRefresh");
            this.m = true;
            f();
        } else if ("setPullDownText".equals(action)) {
            this.p = H5Utils.getString(param, (String) "pullDownText");
            if (!TextUtils.isEmpty(this.p)) {
                this.n.setProviderText(this.p);
                this.n.getH5ProviderDomain().setMaxLines(1);
                this.n.getH5ProviderDomain().setEllipsize(TruncateAt.valueOf("END"));
                this.n.setProviderLogo(null);
                this.n.setProviderUc(null);
            }
            bridgeContext.sendSuccess();
        } else if (H5Param.LONG_BOUNCE_TOP_COLOR.equals(action)) {
            boolean isException = false;
            int bounceTopColor = 0;
            try {
                bounceTopColor = Integer.parseInt(H5Utils.getString(param, (String) "value"));
            } catch (Exception e2) {
                H5Log.e((String) TAG, (Throwable) e2);
                isException = true;
            }
            if (!isException) {
                this.n.switchCustomContentBg(bounceTopColor, H5Environment.getResources().getDrawable(R.drawable.h5_uclogo_white), this.k.getWebView() != null && this.k.getWebView().getType() == WebViewType.THIRD_PARTY);
            }
        } else if ("disClaimerClick".equals(action)) {
            if (this.d != null && j()) {
                this.d.showDisclaimer(this.k, this.s);
            }
        } else if ("showDisClaimer".equals(action)) {
            if (this.d != null) {
                int mode = H5Utils.getInt(param, (String) Constants.KEY_MODE);
                if (mode == 1 || mode == 2) {
                    this.d.showDisclaimer(this.k, mode);
                } else {
                    this.d.hideDisclaimer(this.k);
                }
            }
            return false;
        } else if (CommonEvents.H5_SET_WEBVIEW_TOP.equals(action)) {
            this.c.setWebViewTop(H5Utils.getString(param, (String) "position"), H5Utils.getBoolean(param, (String) "animated", false));
            bridgeContext.sendSuccess();
        } else if (CommonEvents.SET_BACKGROUND_IMAGE.equals(action)) {
            String imageUrl = H5Utils.getString(param, (String) ActionConstant.IMG_URL);
            if (!TextUtils.isEmpty(imageUrl)) {
                a(imageUrl);
            }
            if (param.containsKey("color")) {
                this.n.setBackgroundImageColor(H5Utils.getInt(param, (String) "color"));
            }
            bridgeContext.sendSuccess();
        } else if (CommonEvents.REMOVE_BACKGROUND_IMAGE.equals(action)) {
            this.n.hideBackgroundImage();
            bridgeContext.sendSuccess();
        } else if (CommonEvents.START_PULL_DOWN_REFRESH.equals(action)) {
            a(bridgeContext);
        } else if (!CommonEvents.H5_SET_CAN_PULL_DOWN.equals(action)) {
            return false;
        } else {
            this.h = H5Utils.getBoolean(param, (String) "canPullDown", true);
        }
        return true;
    }

    private void a(H5BridgeContext bridgeContext) {
        if (!this.c.isBackToTop()) {
            bridgeContext.sendError(10, (String) "当前正在下拉中");
            return;
        }
        this.t = true;
        this.c.startPullToRefresh();
        bridgeContext.sendSuccess();
    }

    private void f() {
        if (!this.c.isBackToTop()) {
            this.l = false;
            H5Log.d(TAG, "invoke restorePullToRefresh,not backToTop");
            this.e.setVisibility(8);
            this.c.fitContent();
            return;
        }
        H5Log.d(TAG, "invoke restorePullToRefresh,already backToTop");
    }

    private void b(String action) {
        PullFreshCallback pullFreshCallback = new PullFreshCallback();
        if (this.m) {
            JSONObject params = new JSONObject();
            params.put((String) "from", (Object) this.t ? "code" : "manual");
            JSONObject data = new JSONObject();
            data.put((String) "data", (Object) params);
            this.k.getBridge().sendToWeb(action, data, pullFreshCallback);
            H5Log.d(TAG, "sendToWeb FIRE_PULL_TO_REFRESH");
            this.m = false;
            return;
        }
        H5Log.d(TAG, "date not return,not sendToWeb FIRE_PULL_TO_REFRESH");
    }

    /* access modifiers changed from: private */
    public void g() {
        boolean z = true;
        if (this.k != null) {
            this.e.setVisibility(8);
            this.c.fitContent();
            String host = H5UrlHelper.getHost(this.k.getUrl());
            if (TextUtils.isEmpty(host) || !this.i) {
                this.n.setProviderText("");
                return;
            }
            if (!TextUtils.isEmpty(this.p)) {
                this.n.setProviderText(this.p);
                this.n.getH5ProviderDomain().setMaxLines(1);
                this.n.getH5ProviderDomain().setEllipsize(TruncateAt.valueOf("END"));
                this.n.setProviderLogo(null);
                this.n.setProviderUc(null);
            } else {
                String provider = H5Environment.getResources().getString(R.string.h5_provider, new Object[]{host});
                if (!(this.k == null || this.k.getWebView() == null || this.k.getWebView().getType() != WebViewType.THIRD_PARTY)) {
                    this.n.setProviderLogo(H5Environment.getResources().getDrawable(R.drawable.h5_uclogo));
                    this.n.setProviderUc("  " + H5Environment.getResources().getString(R.string.h5_browser_provider));
                }
                this.n.setProviderText(provider);
            }
            if (this.h) {
                int bounceTopColor = H5Utils.getInt(this.k.getParams(), (String) H5Param.LONG_BOUNCE_TOP_COLOR, (int) H5Param.DEFAULT_LONG_BOUNCE_TOP_COLOR);
                if (bounceTopColor != -657927) {
                    H5WebContentView h5WebContentView = this.n;
                    Drawable drawable = H5Environment.getResources().getDrawable(R.drawable.h5_uclogo_white);
                    if (this.k.getWebView() == null || this.k.getWebView().getType() != WebViewType.THIRD_PARTY) {
                        z = false;
                    }
                    h5WebContentView.switchCustomContentBg(bounceTopColor, drawable, z);
                }
            }
        }
    }

    public View getHdivider() {
        return this.f;
    }

    public H5LoadingView getH5LoadingView() {
        if (this.n != null) {
            return this.n.getH5LoadingView();
        }
        return null;
    }

    private void h() {
        if (H5Utils.getBoolean(this.k.getParams(), (String) "isTinyApp", false)) {
            this.k.sendEvent(CommonEvents.EXIT_SESSION, null);
        } else {
            i();
        }
    }

    private void i() {
        long time = System.currentTimeMillis();
        if (time - this.q.lastClose < 500) {
            H5Log.d(TAG, "ignore bridge, perform close!");
            k();
            return;
        }
        H5Log.d(TAG, "send close event to bridge!");
        this.q.waiting = true;
        this.q.lastClose = time;
        if (this.r != null) {
            this.r.sendToWeb("closeWindow", null, this.q);
        }
    }

    private boolean j() {
        if (this.k == null || this.k.getPageData() == null || this.k.getPageData().getWarningTipSet() == null) {
            return true;
        }
        if (this.k.getPageData().getWarningTipSet().contains("inputWarning") || this.k.getPageData().getWarningTipSet().contains("dataFlow")) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void k() {
        this.k.sendEvent(CommonEvents.H5_PAGE_CLOSE, null);
    }
}
