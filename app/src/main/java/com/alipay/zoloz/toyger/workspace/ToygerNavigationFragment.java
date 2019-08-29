package com.alipay.zoloz.toyger.workspace;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.alipay.mobile.security.bio.config.bean.NavigatePage;
import com.alipay.mobile.security.bio.utils.BioLog;
import com.alipay.mobile.security.bio.utils.NetworkUtil;
import com.alipay.mobile.security.bio.utils.StringUtil;
import com.alipay.zoloz.toyger.R;
import com.alipay.zoloz.toyger.extservice.record.ToygerRecordService;

public class ToygerNavigationFragment extends ToygerFragment {
    private String mABTest = "a";
    private FaceRemoteConfig mFaceRemoteConfig;
    private Handler mMainHandler = new c(this);
    /* access modifiers changed from: private */
    public ToygerRecordService mToygerRecordService;
    private String mUrl;
    /* access modifiers changed from: private */
    public WebView mWebView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mToygerRecordService = (ToygerRecordService) this.mBioServiceManager.getBioService(ToygerRecordService.class);
        this.mToygerRecordService.write(ToygerRecordService.ENTER_GUIDE_PAGE);
        try {
            View inflate = layoutInflater.inflate(R.layout.toyger_circle_navigate, viewGroup, false);
            this.mWebView = (WebView) inflate.findViewById(R.id.face_circle_nav_webView);
            this.mFaceRemoteConfig = this.mToygerCallback.getRemoteConfig();
            this.mUrl = this.mFaceRemoteConfig.getNavi().getUrl();
            if (TextUtils.isEmpty(this.mUrl)) {
                this.mUrl = NavigatePage.DEFAULT_URL;
            }
            initWebSetting(this.mWebView);
            return inflate;
        } catch (Exception e) {
            forward(new ToygerCaptureFragment());
            return new View(getContext());
        }
    }

    private void initWebSetting(WebView webView) {
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(-1);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setSavePassword(false);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        webView.setWebChromeClient(new NavWebChromeClient(this.mMainHandler));
    }

    public void onResume() {
        String format;
        super.onResume();
        if (StringUtil.isNullorEmpty(this.mABTest) || !NetworkUtil.isNetworkAvailable(getActivity().getApplicationContext())) {
            this.mWebView.loadUrl("file:///android_asset/html/nav/facewelcome.html");
        } else if (!StringUtil.isNullorEmpty(this.mUrl)) {
            if (this.mUrl.indexOf("?") > 0) {
                format = String.format("%1$s&os=android&abtest=%2$s", new Object[]{this.mUrl, this.mABTest});
            } else {
                format = String.format("%1$s?os=android&abtest=%2$s", new Object[]{this.mUrl, this.mABTest});
            }
            BioLog.i("url:" + format);
            this.mWebView.loadUrl(format);
        }
    }

    public boolean ontActivityEvent(int i, KeyEvent keyEvent) {
        if (i == 4) {
            this.mToygerRecordService.write(ToygerRecordService.EXIT_GUIDE_PAGE);
            this.mToygerCallback.sendResponse(202);
        }
        return super.ontActivityEvent(i, keyEvent);
    }
}
