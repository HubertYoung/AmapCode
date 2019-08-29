package com.tencent.connect.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.autonavi.indoor.constant.MessageCode;
import com.sina.weibo.sdk.constant.WBConstants;
import com.tencent.connect.a.a;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.ThreadManager;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthAgent extends BaseApi {
    public static final String SECURE_LIB_ARM64_FILE_NAME = "libwbsafeedit_64";
    public static final String SECURE_LIB_ARM_FILE_NAME = "libwbsafeedit";
    public static String SECURE_LIB_FILE_NAME = "libwbsafeedit";
    public static String SECURE_LIB_NAME = null;
    public static final String SECURE_LIB_X86_64_FILE_NAME = "libwbsafeedit_x86_64";
    public static final String SECURE_LIB_X86_FILE_NAME = "libwbsafeedit_x86";
    private IUiListener a;
    private String b;
    /* access modifiers changed from: private */
    public WeakReference<Activity> c;

    /* compiled from: ProGuard */
    class CheckLoginListener implements IUiListener {
        IUiListener a;

        public CheckLoginListener(IUiListener iUiListener) {
            this.a = iUiListener;
        }

        public void onComplete(Object obj) {
            String str;
            if (obj == null) {
                f.e("openSDK_LOG.AuthAgent", "CheckLoginListener response data is null");
                return;
            }
            JSONObject jSONObject = (JSONObject) obj;
            try {
                int i = jSONObject.getInt("ret");
                if (i == 0) {
                    str = "success";
                } else {
                    str = jSONObject.getString("msg");
                }
                if (this.a != null) {
                    this.a.onComplete(new JSONObject().put("ret", i).put("msg", str));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                f.e("openSDK_LOG.AuthAgent", "CheckLoginListener response data format error");
            }
        }

        public void onError(UiError uiError) {
            if (this.a != null) {
                this.a.onError(uiError);
            }
        }

        public void onCancel() {
            if (this.a != null) {
                this.a.onCancel();
            }
        }
    }

    /* compiled from: ProGuard */
    class FeedConfirmListener implements IUiListener {
        IUiListener a;
        private final String c = "sendinstall";
        private final String d = "installwording";
        private final String e = "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi";

        /* compiled from: ProGuard */
        abstract class ButtonListener implements OnClickListener {
            Dialog d;

            ButtonListener(Dialog dialog) {
                this.d = dialog;
            }
        }

        public FeedConfirmListener(IUiListener iUiListener) {
            this.a = iUiListener;
        }

        public void onComplete(Object obj) {
            String str;
            if (obj != null) {
                JSONObject jSONObject = (JSONObject) obj;
                if (jSONObject != null) {
                    boolean z = false;
                    try {
                        if (jSONObject.getInt("sendinstall") == 1) {
                            z = true;
                        }
                        str = jSONObject.getString("installwording");
                    } catch (JSONException unused) {
                        f.d("openSDK_LOG.AuthAgent", "FeedConfirmListener onComplete There is no value for sendinstall.");
                        str = "";
                    }
                    String decode = URLDecoder.decode(str);
                    StringBuilder sb = new StringBuilder(" WORDING = ");
                    sb.append(decode);
                    sb.append("xx");
                    f.a("openSDK_LOG.AuthAgent", sb.toString());
                    if (z && !TextUtils.isEmpty(decode)) {
                        a(decode, this.a, obj);
                    } else if (this.a != null) {
                        this.a.onComplete(obj);
                    }
                }
            }
        }

        private void a(String str, final IUiListener iUiListener, final Object obj) {
            PackageInfo packageInfo;
            Activity activity = (Activity) AuthAgent.this.c.get();
            if (activity != null) {
                Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(1);
                PackageManager packageManager = activity.getPackageManager();
                Drawable drawable = null;
                try {
                    packageInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
                } catch (NameNotFoundException e2) {
                    e2.printStackTrace();
                    packageInfo = null;
                }
                if (packageInfo != null) {
                    drawable = packageInfo.applicationInfo.loadIcon(packageManager);
                }
                AnonymousClass1 r5 = new ButtonListener(dialog) {
                    public void onClick(View view) {
                        FeedConfirmListener.this.a();
                        if (this.d != null && this.d.isShowing()) {
                            this.d.dismiss();
                        }
                        if (iUiListener != null) {
                            iUiListener.onComplete(obj);
                        }
                    }
                };
                AnonymousClass2 r6 = new ButtonListener(dialog) {
                    public void onClick(View view) {
                        if (this.d != null && this.d.isShowing()) {
                            this.d.dismiss();
                        }
                        if (iUiListener != null) {
                            iUiListener.onComplete(obj);
                        }
                    }
                };
                ColorDrawable colorDrawable = new ColorDrawable();
                colorDrawable.setAlpha(0);
                dialog.getWindow().setBackgroundDrawable(colorDrawable);
                dialog.setContentView(a(activity, drawable, str, r5, r6));
                dialog.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        if (iUiListener != null) {
                            iUiListener.onComplete(obj);
                        }
                    }
                });
                if (activity != null && !activity.isFinishing()) {
                    dialog.show();
                }
            }
        }

        private Drawable a(String str, Context context) {
            Drawable drawable;
            Bitmap bitmap;
            try {
                InputStream open = context.getApplicationContext().getAssets().open(str);
                if (open == null) {
                    return null;
                }
                if (str.endsWith(".9.png")) {
                    try {
                        bitmap = BitmapFactory.decodeStream(open);
                    } catch (OutOfMemoryError e2) {
                        e2.printStackTrace();
                        bitmap = null;
                    }
                    if (bitmap == null) {
                        return null;
                    }
                    byte[] ninePatchChunk = bitmap.getNinePatchChunk();
                    NinePatch.isNinePatchChunk(ninePatchChunk);
                    drawable = new NinePatchDrawable(bitmap, ninePatchChunk, new Rect(), null);
                } else {
                    Drawable createFromStream = Drawable.createFromStream(open, str);
                    try {
                        open.close();
                        drawable = createFromStream;
                    } catch (IOException e3) {
                        drawable = createFromStream;
                        e = e3;
                        e.printStackTrace();
                        return drawable;
                    }
                }
                return drawable;
            } catch (IOException e4) {
                e = e4;
                drawable = null;
                e.printStackTrace();
                return drawable;
            }
        }

        private View a(Context context, Drawable drawable, String str, OnClickListener onClickListener, OnClickListener onClickListener2) {
            Context context2 = context;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context2.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            RelativeLayout relativeLayout = new RelativeLayout(context2);
            ImageView imageView = new ImageView(context2);
            imageView.setImageDrawable(drawable);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setId(1);
            int i = (int) (60.0f * f);
            int i2 = (int) (f * 14.0f);
            int i3 = (int) (18.0f * f);
            LayoutParams layoutParams = new LayoutParams(i, i);
            layoutParams.addRule(9);
            layoutParams.setMargins(0, i3, (int) (6.0f * f), i3);
            relativeLayout.addView(imageView, layoutParams);
            TextView textView = new TextView(context2);
            textView.setText(str);
            textView.setTextSize(14.0f);
            textView.setGravity(3);
            textView.setIncludeFontPadding(false);
            textView.setPadding(0, 0, 0, 0);
            textView.setLines(2);
            textView.setId(5);
            textView.setMinWidth((int) (185.0f * f));
            LayoutParams layoutParams2 = new LayoutParams(-2, -2);
            layoutParams2.addRule(1, 1);
            layoutParams2.addRule(6, 1);
            float f2 = 5.0f * f;
            layoutParams2.setMargins(0, 0, (int) f2, 0);
            relativeLayout.addView(textView, layoutParams2);
            View view = new View(context2);
            view.setBackgroundColor(Color.rgb(MessageCode.MSG_NLP_RESPONSED, MessageCode.MSG_NLP_RESPONSED, MessageCode.MSG_NLP_RESPONSED));
            view.setId(3);
            LayoutParams layoutParams3 = new LayoutParams(-2, 2);
            layoutParams3.addRule(3, 1);
            layoutParams3.addRule(5, 1);
            layoutParams3.addRule(7, 5);
            int i4 = (int) (12.0f * f);
            layoutParams3.setMargins(0, 0, 0, i4);
            relativeLayout.addView(view, layoutParams3);
            LinearLayout linearLayout = new LinearLayout(context2);
            LayoutParams layoutParams4 = new LayoutParams(-2, -2);
            layoutParams4.addRule(5, 1);
            layoutParams4.addRule(7, 5);
            layoutParams4.addRule(3, 3);
            Button button = new Button(context2);
            button.setText("跳过");
            button.setBackgroundDrawable(a("buttonNegt.png", context2));
            button.setTextColor(Color.rgb(36, 97, 131));
            button.setTextSize(20.0f);
            button.setOnClickListener(onClickListener2);
            button.setId(4);
            int i5 = (int) (45.0f * f);
            LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(0, i5);
            layoutParams5.rightMargin = i2;
            int i6 = (int) (4.0f * f);
            layoutParams5.leftMargin = i6;
            layoutParams5.weight = 1.0f;
            linearLayout.addView(button, layoutParams5);
            Button button2 = new Button(context2);
            button2.setText("确定");
            button2.setTextSize(20.0f);
            button2.setTextColor(Color.rgb(255, 255, 255));
            button2.setBackgroundDrawable(a("buttonPost.png", context2));
            button2.setOnClickListener(onClickListener);
            LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(0, i5);
            layoutParams6.weight = 1.0f;
            layoutParams6.rightMargin = i6;
            linearLayout.addView(button2, layoutParams6);
            relativeLayout.addView(linearLayout, layoutParams4);
            FrameLayout.LayoutParams layoutParams7 = new FrameLayout.LayoutParams((int) (279.0f * f), (int) (f * 163.0f));
            relativeLayout.setPadding(i2, 0, i4, i4);
            relativeLayout.setLayoutParams(layoutParams7);
            relativeLayout.setBackgroundColor(Color.rgb(247, 251, 247));
            PaintDrawable paintDrawable = new PaintDrawable(Color.rgb(247, 251, 247));
            paintDrawable.setCornerRadius(f2);
            relativeLayout.setBackgroundDrawable(paintDrawable);
            return relativeLayout;
        }

        /* access modifiers changed from: protected */
        public void a() {
            Bundle g = AuthAgent.this.composeActivityParams();
            Activity activity = (Activity) AuthAgent.this.c.get();
            if (activity != null) {
                HttpUtils.requestAsync(AuthAgent.this.mToken, activity, "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi", g, "POST", null);
            }
        }

        public void onError(UiError uiError) {
            if (this.a != null) {
                this.a.onError(uiError);
            }
        }

        public void onCancel() {
            if (this.a != null) {
                this.a.onCancel();
            }
        }
    }

    /* compiled from: ProGuard */
    class TokenListener implements IUiListener {
        private final IUiListener b;
        private final boolean c;
        private final Context d;

        public TokenListener(Context context, IUiListener iUiListener, boolean z, boolean z2) {
            this.d = context;
            this.b = iUiListener;
            this.c = z;
            f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener()");
        }

        public void onComplete(Object obj) {
            f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onComplete");
            JSONObject jSONObject = (JSONObject) obj;
            try {
                String string = jSONObject.getString("access_token");
                String string2 = jSONObject.getString("expires_in");
                String string3 = jSONObject.getString("openid");
                if (!(string == null || AuthAgent.this.mToken == null || string3 == null)) {
                    AuthAgent.this.mToken.setAccessToken(string, string2);
                    AuthAgent.this.mToken.setOpenId(string3);
                    a.d(this.d, AuthAgent.this.mToken);
                }
                String string4 = jSONObject.getString(Constants.PARAM_PLATFORM_ID);
                if (string4 != null) {
                    try {
                        this.d.getSharedPreferences(Constants.PREFERENCE_PF, 0).edit().putString(Constants.PARAM_PLATFORM_ID, string4).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                        f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onComplete error", e);
                    }
                }
                if (this.c) {
                    CookieSyncManager.getInstance().sync();
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
                f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onComplete error", e2);
            }
            this.b.onComplete(jSONObject);
            AuthAgent.this.releaseResource();
            f.b();
        }

        public void onError(UiError uiError) {
            f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onError");
            this.b.onError(uiError);
            f.b();
        }

        public void onCancel() {
            f.b("openSDK_LOG.AuthAgent", "OpenUi, TokenListener() onCancel");
            this.b.onCancel();
            f.b();
        }
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(SECURE_LIB_FILE_NAME);
        sb.append(".so");
        SECURE_LIB_NAME = sb.toString();
        String str = Build.CPU_ABI;
        if (str == null || str.equals("")) {
            SECURE_LIB_FILE_NAME = SECURE_LIB_ARM_FILE_NAME;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(SECURE_LIB_FILE_NAME);
            sb2.append(".so");
            SECURE_LIB_NAME = sb2.toString();
            f.c("openSDK_LOG.AuthAgent", "is arm(default) architecture");
        } else if (str.equalsIgnoreCase("arm64-v8a")) {
            SECURE_LIB_FILE_NAME = SECURE_LIB_ARM64_FILE_NAME;
            StringBuilder sb3 = new StringBuilder();
            sb3.append(SECURE_LIB_FILE_NAME);
            sb3.append(".so");
            SECURE_LIB_NAME = sb3.toString();
            f.c("openSDK_LOG.AuthAgent", "is arm64-v8a architecture");
        } else if (str.equalsIgnoreCase("x86")) {
            SECURE_LIB_FILE_NAME = SECURE_LIB_X86_FILE_NAME;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(SECURE_LIB_FILE_NAME);
            sb4.append(".so");
            SECURE_LIB_NAME = sb4.toString();
            f.c("openSDK_LOG.AuthAgent", "is x86 architecture");
        } else if (str.equalsIgnoreCase("x86_64")) {
            SECURE_LIB_FILE_NAME = SECURE_LIB_X86_64_FILE_NAME;
            StringBuilder sb5 = new StringBuilder();
            sb5.append(SECURE_LIB_FILE_NAME);
            sb5.append(".so");
            SECURE_LIB_NAME = sb5.toString();
            f.c("openSDK_LOG.AuthAgent", "is x86_64 architecture");
        } else {
            SECURE_LIB_FILE_NAME = SECURE_LIB_ARM_FILE_NAME;
            StringBuilder sb6 = new StringBuilder();
            sb6.append(SECURE_LIB_FILE_NAME);
            sb6.append(".so");
            SECURE_LIB_NAME = sb6.toString();
            f.c("openSDK_LOG.AuthAgent", "is arm(default) architecture");
        }
    }

    public AuthAgent(QQToken qQToken) {
        super(qQToken);
    }

    public int doLogin(Activity activity, String str, IUiListener iUiListener) {
        return doLogin(activity, str, iUiListener, false, null);
    }

    public int doLogin(Activity activity, String str, IUiListener iUiListener, boolean z, Fragment fragment) {
        this.b = str;
        this.c = new WeakReference<>(activity);
        this.a = iUiListener;
        if (a(activity, fragment, z)) {
            f.c("openSDK_LOG.AuthAgent", "OpenUi, showUi, return Constants.UI_ACTIVITY");
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), "2", Constants.VIA_REPORT_TYPE_SSO_LOGIN, "5", "0", "0", "0");
            return 1;
        }
        d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), "2", Constants.VIA_REPORT_TYPE_SSO_LOGIN, "5", "1", "0", "0");
        f.d("openSDK_LOG.AuthAgent", "doLogin startActivity fail show dialog.");
        this.a = new FeedConfirmListener(this.a);
        return a(z, this.a);
    }

    public void releaseResource() {
        this.c = null;
        this.a = null;
    }

    private int a(boolean z, IUiListener iUiListener) {
        f.c("openSDK_LOG.AuthAgent", "OpenUi, showDialog -- start");
        CookieSyncManager.createInstance(Global.getContext());
        Bundle composeCGIParams = composeCGIParams();
        if (z) {
            composeCGIParams.putString("isadd", "1");
        }
        composeCGIParams.putString("scope", this.b);
        composeCGIParams.putString("client_id", this.mToken.getAppId());
        if (isOEM) {
            StringBuilder sb = new StringBuilder("desktop_m_qq-");
            sb.append(installChannel);
            sb.append("-android-");
            sb.append(registerChannel);
            sb.append("-");
            sb.append(businessId);
            composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, sb.toString());
        } else {
            composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(System.currentTimeMillis() / 1000);
        String sb3 = sb2.toString();
        composeCGIParams.putString("sign", SystemUtils.getAppSignatureMD5(Global.getContext(), sb3));
        composeCGIParams.putString("time", sb3);
        composeCGIParams.putString("display", "mobile");
        composeCGIParams.putString(WBConstants.AUTH_PARAMS_RESPONSE_TYPE, "token");
        composeCGIParams.putString(WBConstants.AUTH_PARAMS_REDIRECT_URL, ServerSetting.DEFAULT_REDIRECT_URI);
        composeCGIParams.putString("cancel_display", "1");
        composeCGIParams.putString(FunctionSupportConfiger.SWITCH_TAG, "1");
        composeCGIParams.putString("status_userip", Util.getUserIp());
        StringBuilder sb4 = new StringBuilder();
        sb4.append(ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_CGI_AUTHORIZE));
        sb4.append(Util.encodeUrl(composeCGIParams));
        final String sb5 = sb4.toString();
        final TokenListener tokenListener = new TokenListener(Global.getContext(), iUiListener, true, false);
        f.b("openSDK_LOG.AuthAgent", "OpenUi, showDialog TDialog");
        ThreadManager.executeOnSubThread(new Runnable() {
            public void run() {
                SystemUtils.extractSecureLib(AuthAgent.SECURE_LIB_FILE_NAME, AuthAgent.SECURE_LIB_NAME, 3);
                final Activity activity = (Activity) AuthAgent.this.c.get();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            AuthDialog authDialog = new AuthDialog(activity, SystemUtils.ACTION_LOGIN, sb5, tokenListener, AuthAgent.this.mToken);
                            if (activity != null && !activity.isFinishing()) {
                                authDialog.show();
                            }
                        }
                    });
                }
            }
        });
        f.c("openSDK_LOG.AuthAgent", "OpenUi, showDialog -- end");
        return 2;
    }

    private boolean a(Activity activity, Fragment fragment, boolean z) {
        f.c("openSDK_LOG.AuthAgent", "startActionActivity() -- start");
        Intent targetActivityIntent = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
        if (targetActivityIntent != null) {
            Bundle composeCGIParams = composeCGIParams();
            if (z) {
                composeCGIParams.putString("isadd", "1");
            }
            composeCGIParams.putString("scope", this.b);
            composeCGIParams.putString("client_id", this.mToken.getAppId());
            if (isOEM) {
                StringBuilder sb = new StringBuilder("desktop_m_qq-");
                sb.append(installChannel);
                sb.append("-android-");
                sb.append(registerChannel);
                sb.append("-");
                sb.append(businessId);
                composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, sb.toString());
            } else {
                composeCGIParams.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
            }
            composeCGIParams.putString("need_pay", "1");
            composeCGIParams.putString(Constants.KEY_APP_NAME, SystemUtils.getAppName(Global.getContext()));
            targetActivityIntent.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_LOGIN);
            targetActivityIntent.putExtra(Constants.KEY_PARAMS, composeCGIParams);
            if (hasActivityForIntent(targetActivityIntent)) {
                this.a = new FeedConfirmListener(this.a);
                UIListenerManager.getInstance().setListenerWithRequestcode(11101, this.a);
                if (fragment != null) {
                    f.b("openSDK_LOG.AuthAgent", "startAssitActivity fragment");
                    startAssitActivity(fragment, targetActivityIntent, 11101);
                } else {
                    f.b("openSDK_LOG.AuthAgent", "startAssitActivity activity");
                    startAssitActivity(activity, targetActivityIntent, 11101);
                }
                f.c("openSDK_LOG.AuthAgent", "startActionActivity() -- end, found activity for loginIntent");
                d.a().a(0, "LOGIN_CHECK_SDK", "1000", this.mToken.getAppId(), "", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
                return true;
            }
        }
        d.a().a(1, "LOGIN_CHECK_SDK", "1000", this.mToken.getAppId(), "", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "startActionActivity fail");
        f.c("openSDK_LOG.AuthAgent", "startActionActivity() -- end, no target activity for loginIntent");
        return false;
    }

    /* access modifiers changed from: protected */
    public void a(IUiListener iUiListener) {
        f.c("openSDK_LOG.AuthAgent", "reportDAU() -- start");
        String accessToken = this.mToken.getAccessToken();
        String openId = this.mToken.getOpenId();
        String appId = this.mToken.getAppId();
        String str = "";
        if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(openId) && !TextUtils.isEmpty(appId)) {
            StringBuilder sb = new StringBuilder();
            sb.append("tencent&sdk&qazxc***14969%%");
            sb.append(accessToken);
            sb.append(appId);
            sb.append(openId);
            sb.append("qzone3.4");
            str = Util.encrypt(sb.toString());
        }
        if (TextUtils.isEmpty(str)) {
            f.e("openSDK_LOG.AuthAgent", "reportDAU -- encrytoken is null");
            return;
        }
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("encrytoken", str);
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://openmobile.qq.com/user/user_login_statis", composeCGIParams, "POST", null);
        f.c("openSDK_LOG.AuthAgent", "reportDAU() -- end");
    }

    /* access modifiers changed from: protected */
    public void b(IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("reqType", "checkLogin");
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://openmobile.qq.com/v3/user/get_info", composeCGIParams, "GET", new TempRequestListener(new CheckLoginListener(iUiListener)));
    }
}
