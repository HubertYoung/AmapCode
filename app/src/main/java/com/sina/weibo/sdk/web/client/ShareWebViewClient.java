package com.sina.weibo.sdk.web.client;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.constant.WBConstants.Base;
import com.sina.weibo.sdk.constant.WBConstants.Response;
import com.sina.weibo.sdk.utils.WbUtils;
import com.sina.weibo.sdk.web.WebViewRequestCallback;
import com.sina.weibo.sdk.web.WeiboCallbackManager;
import com.sina.weibo.sdk.web.WeiboSdkWebActivity;
import com.sina.weibo.sdk.web.param.BaseWebViewRequestParam;

public class ShareWebViewClient extends BaseWebViewClient {
    private static final String RESP_PARAM_CODE = "code";
    private static final String RESP_PARAM_MSG = "msg";
    private static final String RESP_SUCC_CODE = "0";
    private Activity activity;
    private boolean hasCallbacked = false;

    public ShareWebViewClient(Activity activity2, WebViewRequestCallback webViewRequestCallback, BaseWebViewRequestParam baseWebViewRequestParam) {
        super(webViewRequestCallback, baseWebViewRequestParam);
        this.activity = activity2;
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        if (this.requestCallback != null) {
            this.requestCallback.onPageStartedCallBack(webView, str, bitmap);
        }
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        if (this.requestCallback != null) {
            this.requestCallback.onPageFinishedCallBack(webView, str);
        }
    }

    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        super.onReceivedError(webView, webResourceRequest, webResourceError);
        if (this.requestCallback != null) {
            this.requestCallback.onReceivedErrorCallBack(webView, webResourceError.getErrorCode(), webResourceError.getDescription().toString(), webResourceRequest.getUrl().toString());
        }
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        super.onReceivedError(webView, i, str, str2);
        if (this.requestCallback != null) {
            this.requestCallback.onReceivedErrorCallBack(webView, i, str, str2);
        }
    }

    @TargetApi(24)
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        return needOverLoad(webResourceRequest.getUrl().toString());
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (this.requestCallback != null) {
            this.requestCallback.shouldOverrideUrlLoadingCallBack(webView, str);
        }
        return needOverLoad(str);
    }

    private boolean needOverLoad(String str) {
        if (!str.startsWith(WeiboSdkWebActivity.BROWSER_CLOSE_SCHEME)) {
            return false;
        }
        Bundle parseUri = WbUtils.parseUri(str);
        if (this.param.getBaseData() != null && !TextUtils.isEmpty(this.param.getBaseData().getCallback())) {
            String callback = this.param.getBaseData().getCallback();
            WeiboCallbackManager instance = WeiboCallbackManager.getInstance();
            if (instance.getWeiboAuthListener(callback) != null && !parseUri.isEmpty()) {
                instance.removeWeiboAuthListener(callback);
            }
        }
        String string = parseUri.getString("code");
        String string2 = parseUri.getString("msg");
        if (TextUtils.isEmpty(string)) {
            sendSdkCancleResponse(this.activity);
        } else if (!"0".equals(string)) {
            sendSdkErrorResponse(this.activity, string2);
        } else {
            sendSdkOkResponse(this.activity);
        }
        if (this.requestCallback != null) {
            this.requestCallback.closePage();
        }
        return true;
    }

    private void sendSdkResponse(Activity activity2, int i, String str) {
        Bundle extras = activity2.getIntent().getExtras();
        if (extras != null && !this.hasCallbacked) {
            Intent intent = new Intent(WBConstants.ACTIVITY_REQ_SDK);
            String string = extras.getString("packageName");
            intent.setFlags(131072);
            intent.setPackage(string);
            intent.putExtras(extras);
            intent.putExtra(Base.APP_PKG, activity2.getPackageName());
            intent.putExtra(Response.ERRCODE, i);
            intent.putExtra(Response.ERRMSG, str);
            try {
                activity2.startActivityForResult(intent, WBConstants.SDK_ACTIVITY_FOR_RESULT_CODE);
            } catch (ActivityNotFoundException unused) {
            }
            this.hasCallbacked = true;
        }
    }

    public void sendSdkCancleResponse(Activity activity2) {
        sendSdkResponse(activity2, 1, "send cancel!!!");
    }

    public void sendSdkOkResponse(Activity activity2) {
        sendSdkResponse(activity2, 0, "send ok!!!");
    }

    public void sendSdkErrorResponse(Activity activity2, String str) {
        sendSdkResponse(activity2, 2, str);
    }

    public void errorBack(Activity activity2, String str) {
        super.errorBack(activity2, str);
        sendSdkErrorResponse(activity2, str);
    }

    public void closeWeb() {
        super.closeWeb();
        sendSdkCancleResponse(this.activity);
    }

    public boolean onBackKeyDown() {
        closeWeb();
        if (this.requestCallback != null) {
            this.requestCallback.closePage();
        }
        return true;
    }
}
