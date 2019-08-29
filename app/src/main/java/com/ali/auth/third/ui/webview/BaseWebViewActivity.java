package com.ali.auth.third.ui.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.cookies.CookieManagerWrapper;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.core.util.CommonUtils;
import com.ali.auth.third.core.util.ResourceUtils;
import com.ali.auth.third.login.util.LoginStatus;
import com.ali.auth.third.ui.support.ActivityResultHandler;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class BaseWebViewActivity extends Activity {
    public static final String TAG = "BaseWebViewActivity";
    private Handler a = new Handler();
    public AuthWebView authWebView;
    protected View backButton;
    public boolean canReceiveTitle;
    protected View closeButton;
    protected View titleBar;
    public TextView titleText;

    class a extends AsyncTask<String, Void, Void> {
        private String b;

        a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Void doInBackground(String... strArr) {
            this.b = strArr[0];
            CookieManagerWrapper.INSTANCE.refreshCookie();
            return null;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public void onPostExecute(Void voidR) {
            super.onPostExecute(voidR);
            if (!CommonUtils.isNetworkAvailable()) {
                CommonUtils.toast("com_taobao_tae_sdk_network_not_available_message");
                return;
            }
            try {
                BaseWebViewActivity.this.authWebView.resumeTimers();
                if (VERSION.SDK_INT >= 11) {
                    BaseWebViewActivity.this.authWebView.onResume();
                }
            } catch (Exception unused) {
            }
            BaseWebViewActivity.this.authWebView.loadUrl(this.b);
        }
    }

    /* access modifiers changed from: protected */
    public AuthWebView createTaeWebView() {
        try {
            return new AuthWebView(this);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public WebChromeClient createWebChromeClient() {
        return new c(this);
    }

    /* access modifiers changed from: protected */
    public WebViewClient createWebViewClient() {
        return new BaseWebViewClient();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ActivityResultHandler activityResultHandler = (ActivityResultHandler) KernelContext.getService(ActivityResultHandler.class, Collections.singletonMap(ActivityResultHandler.REQUEST_CODE_KEY, String.valueOf(i)));
        if (activityResultHandler != null) {
            activityResultHandler.onActivityResult(2, i, i2, intent, this, null, this.authWebView);
        }
    }

    /* access modifiers changed from: protected */
    public void onBackHistory() {
        setResult(ResultCode.USER_CANCEL.code, new Intent());
        LoginStatus.resetLoginFlag();
        finish();
    }

    public void onBackPressed() {
        onBackHistory();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        String str2 = null;
        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(ResourceUtils.getRLayout(this, "com_taobao_tae_sdk_web_view_activity"), null);
        this.authWebView = createTaeWebView();
        if (this.authWebView == null) {
            LoginStatus.resetLoginFlag();
            finish();
            return;
        }
        this.authWebView.setWebViewClient(createWebViewClient());
        this.authWebView.setWebChromeClient(createWebChromeClient());
        linearLayout.addView(this.authWebView, new LayoutParams(-1, -1));
        setContentView(linearLayout);
        this.titleText = (TextView) findViewById(ResourceUtils.getIdentifier(this, "id", "com_taobao_tae_sdk_web_view_title_bar_title"));
        this.backButton = findViewById(ResourceUtils.getIdentifier(this, "id", "com_taobao_tae_sdk_web_view_title_bar_back_button"));
        if (this.backButton != null) {
            this.backButton.setOnClickListener(new a(this));
        }
        this.closeButton = findViewById(ResourceUtils.getIdentifier(this, "id", "com_taobao_tae_sdk_web_view_title_bar_close_button"));
        if (this.closeButton != null) {
            this.closeButton.setOnClickListener(new b(this));
            this.closeButton.setVisibility(8);
        }
        this.titleBar = findViewById(ResourceUtils.getIdentifier(this, "id", "com_taobao_tae_sdk_web_view_title_bar"));
        Serializable serializable = getIntent() != null ? getIntent().getSerializableExtra("ui_contextParams") : bundle != null ? bundle.getSerializable("ui_contextParams") : null;
        if (serializable instanceof Map) {
            this.authWebView.getContextParameters().putAll((Map) serializable);
        }
        if (bundle != null) {
            str2 = bundle.getString("title");
            str = bundle.getString("url");
        } else {
            str = null;
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = getIntent().getStringExtra("title");
        }
        if (TextUtils.isEmpty(str2)) {
            this.canReceiveTitle = true;
        } else {
            this.canReceiveTitle = false;
            this.titleText.setText(str2);
        }
        if (TextUtils.isEmpty(str)) {
            str = getIntent().getStringExtra("url");
        }
        SDKLogger.d(TAG, "onCreate url=".concat(String.valueOf(str)));
        if (KernelContext.checkServiceValid()) {
            new a().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[]{str});
            return;
        }
        LoginStatus.resetLoginFlag();
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        if (this.authWebView != null) {
            ViewGroup viewGroup = (ViewGroup) this.authWebView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(this.authWebView);
            }
            this.authWebView.removeAllViews();
            this.authWebView.destroy();
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onFailure(ResultCode resultCode) {
        finish();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.authWebView != null) {
            try {
                this.authWebView.resumeTimers();
                if (VERSION.SDK_INT >= 11) {
                    this.authWebView.onResume();
                }
            } catch (Exception unused) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("url", this.authWebView.getUrl());
        bundle.putString("title", this.titleText.getText().toString());
        bundle.putSerializable("ui_contextParams", this.authWebView.getContextParameters());
    }

    public void setResult(ResultCode resultCode) {
        onFailure(resultCode);
    }
}
