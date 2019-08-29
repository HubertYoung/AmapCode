package com.alipay.mobile.accountopenauth.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.android.phone.inside.accountopenauth.R;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.android.phone.inside.api.accountopenauth.IFastOAuthService;
import com.alipay.android.phone.inside.common.image.ImageLoader;
import com.alipay.android.phone.inside.common.image.ImageLoader.ClipsInfo;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.accountopenauth.api.rpc.model.res.AuthAgreementModel;
import com.alipay.mobile.accountopenauth.api.rpc.model.res.BuAuthCotentModel;
import com.alipay.mobile.accountopenauth.api.rpc.model.res.OauthPrepareResult;
import com.alipay.mobile.accountopenauth.api.rpc.model.res.TinyAppAuthExecuteResult;
import com.alipay.mobile.accountopenauth.biz.FastOAuthDataManager;
import com.alipay.mobile.accountopenauth.common.CommonUtil;
import com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import com.alipay.mobile.accountopenauth.ui.Base.BaseActivity;
import com.alipay.mobile.accountopenauth.ui.widget.LottieAnimationView;
import java.util.ArrayList;
import java.util.List;

public class FastOpenAuthActivity extends BaseActivity implements OnClickListener {
    private static final String TAG = "FastOpenAuthActivity_";
    private ImageView mAccountImg;
    private TextView mAgreement;
    private TextView mAgreementLink;
    private TextView mAgreementList;
    private ImageView mAppLogo;
    private View mAuthLogin;
    private String mAvatar;
    private boolean mEnableBack = true;
    private View mGrantAuth;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler(Looper.getMainLooper());
    private String mLoginId;
    /* access modifiers changed from: private */
    public LottieAnimationView mLoginLottie;
    /* access modifiers changed from: private */
    public OauthPrepareResult mOauthPrepareResult;
    private LottieAnimationView mPrepareLoadingLottie;
    private View mPrepareLoadingView;
    private boolean mResumeShowHasCheck = false;
    /* access modifiers changed from: private */
    public String mSSOToken;
    private TextView mSecurityPhone;
    private long mThreadId;
    private boolean mWinFocusShowHasCheck = false;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.a);
        OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Activity_Open", "fastoauth", "", "", BehaviorType.EVENT);
        initView();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mThreadId = extras.getLong("sourceUUID");
        }
        getPrepareResult();
    }

    private void initView() {
        this.mGrantAuth = findViewById(R.id.i);
        this.mAuthLogin = findViewById(R.id.g);
        this.mAppLogo = (ImageView) findViewById(R.id.b);
        this.mAgreement = (TextView) findViewById(R.id.c);
        this.mAgreementList = (TextView) findViewById(R.id.e);
        this.mAgreementLink = (TextView) findViewById(R.id.d);
        this.mLoginLottie = (LottieAnimationView) findViewById(R.id.l);
        this.mAccountImg = (ImageView) findViewById(R.id.j);
        this.mSecurityPhone = (TextView) findViewById(R.id.k);
        this.mPrepareLoadingView = findViewById(R.id.q);
        this.mPrepareLoadingLottie = (LottieAnimationView) findViewById(R.id.p);
        findViewById(R.id.h).setOnClickListener(this);
        findViewById(R.id.f).setOnClickListener(this);
    }

    public void onClick(View view) {
        if (view != null) {
            int id = view.getId();
            if (R.id.h == id) {
                onAuth();
                return;
            }
            if (R.id.f == id) {
                OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Activity_Close", "fastoauth", "", "", BehaviorType.EVENT);
                FastOAuthDataManager.a().e();
                finishCurrentActivity();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (!this.mResumeShowHasCheck) {
            this.mResumeShowHasCheck = true;
            OAuthTraceLogger.a((String) TAG, (String) "onresume hascheck");
            IFastOAuthService fastOAuthService = AccountOAuthServiceManager.getInstance().getFastOAuthService();
            if (fastOAuthService != null && !fastOAuthService.canShowFastPage(this.mThreadId)) {
                OAuthTraceLogger.a((String) TAG, (String) "canShowFastPage false step 4");
                OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Activity_Close", "fastoauth", "mccancel", "resume", BehaviorType.EVENT);
                FastOAuthDataManager.a().f();
                finishCurrentActivity();
            }
        }
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (!this.mWinFocusShowHasCheck) {
            this.mWinFocusShowHasCheck = true;
            OAuthTraceLogger.a((String) TAG, (String) "onWindowFocusChanged hascheck");
            if (z) {
                IFastOAuthService fastOAuthService = AccountOAuthServiceManager.getInstance().getFastOAuthService();
                if (fastOAuthService != null && !fastOAuthService.canShowFastPage(this.mThreadId)) {
                    OAuthTraceLogger.a((String) TAG, (String) "canShowFastPage false step 5");
                    OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Activity_Close", "fastoauth", "mccancel", "wfocus", BehaviorType.EVENT);
                    FastOAuthDataManager.a().f();
                    finishCurrentActivity();
                }
            }
        }
    }

    public void onBackPressed() {
        if (this.mEnableBack) {
            OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Activity_Back", "fastoauth", "", "", BehaviorType.EVENT);
            FastOAuthDataManager.a().e();
            super.onBackPressed();
        }
    }

    private void getPrepareResult() {
        this.mOauthPrepareResult = FastOAuthDataManager.a().b();
        final Bundle extras = getIntent().getExtras();
        if (this.mOauthPrepareResult == null && extras != null) {
            showPrepareLoading();
            new Thread(new Runnable() {
                public void run() {
                    FastOpenAuthActivity.this.mOauthPrepareResult = FastOAuthDataManager.a().a(extras.getString("authUrlParams"), extras);
                    if (FastOpenAuthActivity.this.isFinishing()) {
                        OAuthTraceLogger.a((String) FastOpenAuthActivity.TAG, (String) "activity is finishing 1");
                    } else {
                        FastOpenAuthActivity.this.mHandler.post(new Runnable() {
                            public void run() {
                                FastOpenAuthActivity.this.cancelPrepareLoading();
                                FastOpenAuthActivity.this.onPrepareEnd();
                            }
                        });
                    }
                }
            }).start();
        } else if (isFinishing()) {
            OAuthTraceLogger.a((String) TAG, (String) "activity is finishing 2");
        } else {
            onPrepareEnd();
        }
    }

    /* access modifiers changed from: private */
    public void onPrepareEnd() {
        if (tryInitAuthPageData()) {
            this.mGrantAuth.setVisibility(0);
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
            translateAnimation.setDuration(500);
            this.mGrantAuth.startAnimation(translateAnimation);
            return;
        }
        OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Activity_Prepare_Failed", "fastoauth", "", "", BehaviorType.EVENT);
        FastOAuthDataManager.a().d();
        finishCurrentActivity();
        Toast.makeText(this, getString(R.string.g), 0).show();
    }

    private void onAuth() {
        parseParams();
        if (TextUtils.isEmpty(this.mLoginId) || TextUtils.isEmpty(this.mSSOToken)) {
            OAuthTraceLogger.b((String) TAG, (String) "外部会有判断，任意一个参数为空，不会走到这里");
            OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Activity_Auth_Failed", "fastoauth", "keyParamIsEmpty", "", BehaviorType.EVENT);
            FastOAuthDataManager.a().d();
            return;
        }
        fillLoginPage();
        new Thread(new Runnable() {
            public void run() {
                String str = "";
                if (FastOpenAuthActivity.this.mOauthPrepareResult != null) {
                    str = FastOpenAuthActivity.this.mOauthPrepareResult.contextToken;
                    OAuthTraceLogger.a((String) FastOpenAuthActivity.TAG, "contextToken:".concat(String.valueOf(str)));
                }
                FastOAuthDataManager.a();
                TinyAppAuthExecuteResult a2 = FastOAuthDataManager.a(FastOpenAuthActivity.this.mSSOToken, str);
                if (a2 == null || TextUtils.isEmpty(a2.authCode)) {
                    OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Activity_Auth_Failed", "fastoauth", "authResponseFailed", "", BehaviorType.EVENT);
                    FastOAuthDataManager.a().d();
                } else {
                    StringBuilder sb = new StringBuilder("auth success ,authcode = ");
                    sb.append(a2.authCode);
                    sb.append(",appId = ");
                    sb.append(a2.appId);
                    OAuthTraceLogger.a((String) FastOpenAuthActivity.TAG, sb.toString());
                    Bundle bundle = new Bundle();
                    bundle.putString("auth_code", a2.authCode);
                    bundle.putString("app_id", a2.appId);
                    FastOAuthDataManager.a().a(bundle);
                }
                FastOpenAuthActivity.this.mHandler.post(new Runnable() {
                    public void run() {
                        FastOpenAuthActivity.this.mLoginLottie.cancelAnimation();
                    }
                });
                FastOpenAuthActivity.this.finishCurrentActivity();
            }
        }).start();
        this.mEnableBack = false;
    }

    private boolean tryInitAuthPageData() {
        if (this.mOauthPrepareResult == null) {
            LoggerFactory.f().b((String) TAG, (String) "mOauthPrepareResult null");
            return false;
        } else if (!tryFillAgreements()) {
            LoggerFactory.f().b((String) TAG, (String) "fill agreements failed");
            return false;
        } else {
            if (!TextUtils.isEmpty(this.mOauthPrepareResult.appLogoLink)) {
                loadImage(this.mOauthPrepareResult.appLogoLink, this.mAppLogo);
            }
            return true;
        }
    }

    private boolean tryFillAgreements() {
        CharSequence charSequence;
        String str = this.mOauthPrepareResult.appName;
        List<String> buildAuthTextList = buildAuthTextList();
        SpannableString spannableString = null;
        if (buildAuthTextList == null || buildAuthTextList.isEmpty()) {
            charSequence = null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (String next : buildAuthTextList) {
                if (!TextUtils.isEmpty(next)) {
                    sb.append("• ");
                    sb.append(next);
                    sb.append("\n");
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            charSequence = sb.toString();
        }
        List<AuthAgreementModel> buildAuthLinkList = buildAuthLinkList();
        if (buildAuthLinkList != null && !buildAuthLinkList.isEmpty()) {
            StringBuilder sb2 = new StringBuilder();
            if (buildAuthLinkList.size() == 1) {
                AuthAgreementModel authAgreementModel = buildAuthLinkList.get(0);
                if (authAgreementModel != null && !TextUtils.isEmpty(authAgreementModel.name) && !TextUtils.isEmpty(authAgreementModel.link)) {
                    sb2.append(authAgreementModel.name);
                }
            } else {
                for (int i = 0; i < buildAuthLinkList.size(); i++) {
                    AuthAgreementModel authAgreementModel2 = buildAuthLinkList.get(i);
                    if (authAgreementModel2 != null && !TextUtils.isEmpty(authAgreementModel2.name) && !TextUtils.isEmpty(authAgreementModel2.link)) {
                        if (i < buildAuthLinkList.size() - 2) {
                            sb2.append(authAgreementModel2.name);
                            sb2.append("、");
                        } else if (i < buildAuthLinkList.size() - 1) {
                            sb2.append(authAgreementModel2.name);
                            sb2.append("和");
                        } else {
                            sb2.append(authAgreementModel2.name);
                        }
                    }
                }
            }
            if (sb2.length() > 0) {
                String string = getString(R.string.b, new Object[]{sb2.toString()});
                SpannableString spannableString2 = new SpannableString(string);
                for (AuthAgreementModel next2 : buildAuthLinkList) {
                    if (next2 != null && !TextUtils.isEmpty(next2.name) && !TextUtils.isEmpty(next2.link)) {
                        int indexOf = string.indexOf(next2.name);
                        int length = next2.name.length() + indexOf;
                        spannableString2.setSpan(new TaoUrlSpan(next2.link).setContext(this).setTitle(next2.name), indexOf, length, 33);
                        spannableString2.setSpan(new ForegroundColorSpan(Color.parseColor("#108EE9")), indexOf, length, 33);
                    }
                }
                spannableString = spannableString2;
            }
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        this.mAgreement.setText(getString(R.string.a, new Object[]{str}));
        if (!TextUtils.isEmpty(charSequence)) {
            this.mAgreementList.setText(charSequence);
        } else {
            this.mAgreementList.setVisibility(8);
        }
        if (spannableString != null) {
            this.mAgreementLink.setText(spannableString);
            this.mAgreementLink.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            this.mAgreementLink.setVisibility(8);
        }
        return true;
    }

    private List<String> buildAuthTextList() {
        ArrayList arrayList = new ArrayList();
        List<BuAuthCotentModel> list = this.mOauthPrepareResult.buAuthCotentModels;
        if (list != null && !list.isEmpty()) {
            for (BuAuthCotentModel next : list) {
                if (!(next == null || next.authText == null || next.authText.isEmpty())) {
                    arrayList.addAll(next.authText);
                }
            }
        }
        return arrayList;
    }

    private List<AuthAgreementModel> buildAuthLinkList() {
        ArrayList arrayList = new ArrayList();
        List<BuAuthCotentModel> list = this.mOauthPrepareResult.buAuthCotentModels;
        if (list != null && !list.isEmpty()) {
            for (BuAuthCotentModel next : list) {
                if (!(next == null || next.agreements == null || next.agreements.isEmpty())) {
                    arrayList.addAll(next.agreements);
                }
            }
        }
        return arrayList;
    }

    private void fillLoginPage() {
        this.mGrantAuth.setVisibility(8);
        ((LayoutParams) this.mAuthLogin.getLayoutParams()).height = this.mGrantAuth.getHeight();
        this.mAuthLogin.setVisibility(0);
        this.mLoginLottie.setVisibility(0);
        this.mLoginLottie.playAnimation();
        this.mSecurityPhone.setText(CommonUtil.a(this.mLoginId));
        if (!TextUtils.isEmpty(this.mAvatar)) {
            loadImage(this.mAvatar, this.mAccountImg);
        }
    }

    private void showPrepareLoading() {
        this.mEnableBack = false;
        this.mPrepareLoadingView.setVisibility(0);
        this.mPrepareLoadingLottie.playAnimation();
    }

    /* access modifiers changed from: private */
    public void cancelPrepareLoading() {
        if (this.mPrepareLoadingView.getVisibility() == 0) {
            this.mPrepareLoadingLottie.cancelAnimation();
            this.mPrepareLoadingView.setVisibility(8);
        }
        this.mEnableBack = true;
    }

    private void parseParams() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mLoginId = extras.getString("loginId");
            this.mAvatar = extras.getString("avatar");
            this.mSSOToken = extras.getString("token");
        }
        StringBuilder sb = new StringBuilder("parseParams mLoginId：");
        sb.append(this.mLoginId);
        sb.append(" mAvatar：");
        sb.append(this.mAvatar);
        sb.append(" mSSOToken：");
        sb.append(this.mSSOToken);
        OAuthTraceLogger.a((String) TAG, sb.toString());
    }

    private void loadImage(String str, ImageView imageView) {
        try {
            ImageLoader.a(imageView, str, false, new ClipsInfo());
        } catch (Throwable th) {
            OAuthTraceLogger.a(TAG, "loadImage error", th);
        }
    }

    /* access modifiers changed from: private */
    public void finishCurrentActivity() {
        try {
            finish();
            overridePendingTransition(17432576, 17432577);
        } catch (Throwable th) {
            OAuthTraceLogger.a(TAG, "finishCurrentActivity error", th);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mResumeShowHasCheck = false;
        this.mWinFocusShowHasCheck = false;
    }
}
