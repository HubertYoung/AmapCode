package com.alipay.mobile.h5container.api;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.nebula.embedviewcommon.H5NewEmbedBaseViewListener;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewProvider;
import com.alipay.mobile.nebula.provider.H5EmbededViewProvider;
import com.alipay.mobile.nebula.view.H5LoadingView;
import com.alipay.mobile.nebula.view.H5TitleView;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;

public interface H5Page extends H5CoreNode {

    public interface H5ErrorHandler {
        boolean shouldInterceptError(String str, int i);
    }

    public interface H5PageHandler {
        boolean shouldExit();
    }

    public interface H5TitleBarReadyCallback {
        void onCreate();
    }

    void applyParamsIfNeed();

    @TargetApi(19)
    void execJavaScript4EmbedView(String str, IH5EmbedViewJSCallback iH5EmbedViewJSCallback);

    boolean exitPage();

    boolean exitTabPage();

    APWebViewClient getAPWebViewClient();

    String getAdvertisementViewTag();

    H5AvailablePageData getAvailablePageData();

    H5Bridge getBridge();

    View getContentView();

    H5Context getContext();

    H5EmbededViewProvider getEmbededViewProvider();

    Object getExtra(String str);

    H5LoadingView getH5LoadingView();

    H5TitleView getH5TitleBar();

    long getLastTouch();

    H5NewEmbedViewProvider getNewEmbedViewProvider();

    View getNewEmbedViewRoot(H5NewEmbedBaseViewListener h5NewEmbedBaseViewListener);

    H5PageData getPageData();

    int getPageId();

    Bundle getParams();

    String getPerformance();

    String getRedirectUrl();

    View getRootView();

    H5Session getSession();

    String getShareUrl();

    String getTitle();

    H5TitleBarReadyCallback getTitleBarReadyCallBack();

    String getUrl();

    String getVersion();

    ViewGroup getViewGroup();

    APWebView getWebView();

    int getWebViewId();

    boolean hasContentBeforeRedirect();

    boolean ifContainsEmbedSurfaceView();

    boolean ifContainsEmbedView();

    boolean isTinyApp();

    boolean isTransparentTitleState();

    void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5);

    void loadUrl(String str);

    boolean onInterceptError(String str, int i);

    boolean pageIsClose();

    void replace(String str);

    boolean scriptbizLoadedAndBridgeLoaded();

    void sendExitEvent();

    void setContainsEmbedSurfaceView(boolean z);

    void setContainsEmbedView(boolean z);

    void setContentBeforeRedirect(boolean z);

    void setExtra(String str, Object obj);

    void setH5ErrorHandler(H5ErrorHandler h5ErrorHandler);

    void setH5TitleBar(H5TitleView h5TitleView);

    void setHandler(H5PageHandler h5PageHandler);

    void setNewEmbedViewRoot(View view);

    void setPageId(int i);

    void setPerformance(String str);

    void setRootView(View view);

    void setTextSize(int i);

    void setTitle(String str);

    void setTitleBarReadyCallBack(H5TitleBarReadyCallback h5TitleBarReadyCallback);

    void setWebViewId(int i);
}
