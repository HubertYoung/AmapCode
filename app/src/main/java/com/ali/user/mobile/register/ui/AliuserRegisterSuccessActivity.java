package com.ali.user.mobile.register.ui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ali.user.mobile.base.BaseActivity;
import com.ali.user.mobile.common.api.UIConfigManager;
import com.ali.user.mobile.context.AliuserLoginContext;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LogAgent;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.login.LoginParam;
import com.ali.user.mobile.register.model.StateUtils;
import com.ali.user.mobile.security.ui.R;
import com.ali.user.mobile.service.UserRegisterService;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.widget.ui.BalloonLayout;

public class AliuserRegisterSuccessActivity extends BaseActivity {
    private static final String TAG = "AliuserRegisterSuccessActivity";
    private static final int sCountDown = 3;
    private ValueAnimator mAnimator;
    protected String mAppName;
    /* access modifiers changed from: private */
    public Button mConfirmButton;
    private AnimatorListener mEndListener = new AnimatorListener() {
        public void onAnimationRepeat(Animator animator) {
        }

        public void onAnimationStart(Animator animator) {
            if (AliuserRegisterSuccessActivity.this.mConfirmButton != null && AliuserRegisterSuccessActivity.this.mAppName != null) {
                AliuserRegisterSuccessActivity.this.mConfirmButton.setText(AliuserRegisterSuccessActivity.this.getResources().getString(R.string.bk, new Object[]{AliuserRegisterSuccessActivity.this.mAppName, Integer.valueOf(3)}));
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (AliuserRegisterSuccessActivity.this.mConfirmButton != null) {
                AliuserRegisterSuccessActivity.this.mConfirmButton.performClick();
            }
        }

        public void onAnimationCancel(Animator animator) {
            onAnimationEnd(animator);
        }
    };
    protected Handler mHandler;
    protected String mMobileNo;
    protected String mRegionNo;
    protected String mToken;
    private TextView mTvTip;
    private AnimatorUpdateListener mUpdate = new AnimatorUpdateListener() {
        private Integer b;

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            if (AliuserRegisterSuccessActivity.this.mConfirmButton != null && AliuserRegisterSuccessActivity.this.mAppName != null) {
                Integer num = (Integer) valueAnimator.getAnimatedValue();
                if (num != null && !num.equals(this.b)) {
                    this.b = num;
                    AliuserRegisterSuccessActivity.this.mConfirmButton.setText(AliuserRegisterSuccessActivity.this.getResources().getString(R.string.bk, new Object[]{AliuserRegisterSuccessActivity.this.mAppName, this.b}));
                }
            }
        }
    };
    protected UserRegisterService mUserRegisterService;
    private WebView mWebView2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mToken = getIntent().getStringExtra("token");
        this.mMobileNo = getIntent().getStringExtra("mobile_for_sms");
        this.mRegionNo = getIntent().getStringExtra("regionNo");
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mUserRegisterService = AliuserLoginContext.e();
        setContentView(R.layout.m);
        initViews();
        initDatas();
        initWebview();
        LoggerUtils.a("", TAG, "login", "");
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.mAnimator != null) {
            this.mAnimator.end();
        }
        this.mAnimator = ValueAnimator.ofInt(new int[]{3, 0});
        this.mAnimator.addUpdateListener(this.mUpdate);
        this.mAnimator.addListener(this.mEndListener);
        this.mAnimator.setDuration(BalloonLayout.DEFAULT_DISPLAY_DURATION);
        this.mAnimator.setInterpolator(new LinearInterpolator());
        this.mAnimator.start();
    }

    private void initViews() {
        ImageView imageView = (ImageView) findViewById(R.id.ac);
        TextView textView = (TextView) findViewById(R.id.by);
        this.mTvTip = (TextView) findViewById(R.id.bW);
        this.mWebView2 = (WebView) findViewById(R.id.cL);
        this.mConfirmButton = (Button) findViewById(R.id.K);
        this.mAppName = AppInfo.getInstance().getAppName();
        if (TextUtils.isEmpty(this.mAppName)) {
            this.mAppName = "";
        }
        UIConfigManager.a(this.mConfirmButton);
        Drawable g = UIConfigManager.g();
        if (g != null) {
            imageView.setImageDrawable(g);
        }
        int h = UIConfigManager.h();
        if (h != Integer.MAX_VALUE) {
            textView.setTextColor(h);
        }
    }

    private void initDatas() {
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getString(R.string.b));
        sb.append(Token.SEPARATOR);
        sb.append(this.mMobileNo);
        sb.append(Token.SEPARATOR);
        sb.append(getResources().getString(R.string.cI));
        this.mTvTip.setText(sb.toString());
        this.mConfirmButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                LogAgent.b("UC-ZC-150512-29", "zcsuccess", "RegisterSuccess", AliuserRegisterSuccessActivity.this.mMobileNo, AliuserRegisterSuccessActivity.this.mToken);
                AliuserRegisterSuccessActivity.this.goLogin(AliuserRegisterSuccessActivity.this.mMobileNo, AliuserRegisterSuccessActivity.this.mToken, "afterreg", true);
            }
        });
    }

    private void initWebview() {
        AliUserLog.c(TAG, "loadUrl:https://ds.alipay.com/help/icon.htm");
        this.mWebView2.loadUrl("https://ds.alipay.com/help/icon.htm");
        this.mWebView2.setBackgroundColor(0);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    public void startActivity(Intent intent) {
        intent.putExtra("token", this.mToken);
        intent.putExtra("mobile_for_sms", this.mMobileNo);
        intent.putExtra("regionNo", this.mRegionNo);
        super.startActivity(intent);
    }

    /* access modifiers changed from: protected */
    public void goLogin(String str, String str2, String str3, boolean z) {
        goLogin(str, str2, str3, z, null);
    }

    /* access modifiers changed from: protected */
    public void goLogin(String str, String str2, String str3, boolean z, String str4) {
        StateUtils.c();
        LoginParam loginParam = new LoginParam();
        loginParam.loginAccount = str;
        loginParam.token = str2;
        loginParam.validateTpye = str3;
        Intent a = AliuserLoginContext.a(getApplicationContext());
        a.putExtra("login_param", loginParam);
        a.putExtra("from_register", true);
        a.putExtra("loginTargetBiz", str4);
        if (z) {
            a.addFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
            a.addFlags(536870912);
        }
        startActivity(a);
    }

    public void setAppId() {
        this.mAppId = "20000009";
    }
}
