package com.tencent.connect.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AssistActivity extends Activity {
    public static final String EXTRA_INTENT = "openSDK_LOG.AssistActivity.ExtraIntent";
    protected static final int FINISH_BY_TIMEOUT = 0;
    private static final String RESTART_FLAG = "RESTART_FLAG";
    private static final String TAG = "openSDK_LOG.AssistActivity";
    private boolean canFinishByTimeout = false;
    private boolean canStartTimeout = false;
    protected Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 0 && !AssistActivity.this.isFinishing()) {
                AssistActivity.this.finish();
            }
        }
    };
    private boolean isRestart = false;

    public static Intent getAssistActivityIntent(Context context) {
        return new Intent(context, AssistActivity.class);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        int i;
        requestWindowFeature(1);
        super.onCreate(bundle);
        setRequestedOrientation(3);
        f.b(TAG, "--onCreate--");
        if (getIntent() == null) {
            f.e(TAG, "-->onCreate--getIntent() returns null");
            finish();
        }
        Intent intent = (Intent) getIntent().getParcelableExtra(EXTRA_INTENT);
        if (intent == null) {
            i = 0;
        } else {
            i = intent.getIntExtra(Constants.KEY_REQUEST_CODE, 0);
        }
        Bundle bundleExtra = getIntent().getBundleExtra(SystemUtils.H5_SHARE_DATA);
        if (bundle != null) {
            this.isRestart = bundle.getBoolean(RESTART_FLAG);
        }
        if (this.isRestart) {
            f.b(TAG, "is restart");
        } else if (bundleExtra != null) {
            f.d(TAG, "--onCreate--h5 bundle not null, will open browser");
            openBrowser(bundleExtra);
        } else if (intent != null) {
            f.c(TAG, "--onCreate--activityIntent not null, will start activity, reqcode = ".concat(String.valueOf(i)));
            if (intent.getComponent() != null) {
                this.canFinishByTimeout = false;
                startActivityForResult(intent, i);
                return;
            }
            this.canFinishByTimeout = true;
            this.canStartTimeout = false;
            startActivity(intent);
        } else {
            f.e(TAG, "--onCreate--activityIntent is null");
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        f.b(TAG, "-->onStart");
        super.onStart();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        f.b(TAG, "-->onResume");
        super.onResume();
        Intent intent = getIntent();
        if (!intent.getBooleanExtra(SystemUtils.IS_LOGIN, false)) {
            if (!intent.getBooleanExtra(SystemUtils.IS_QQ_MOBILE_SHARE, false) && this.isRestart && !isFinishing()) {
                finish();
            }
            if (this.canFinishByTimeout && this.canStartTimeout) {
                this.handler.sendMessageDelayed(this.handler.obtainMessage(0), 200);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        f.b(TAG, "-->onPause");
        if (this.canFinishByTimeout) {
            if (this.canStartTimeout) {
                this.handler.removeMessages(0);
            } else {
                this.canStartTimeout = true;
            }
        }
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        f.b(TAG, "-->onStop");
        super.onStop();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        f.b(TAG, "-->onDestroy");
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        f.c(TAG, "--onNewIntent");
        super.onNewIntent(intent);
        intent.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_SHARE);
        setResult(-1, intent);
        if (!isFinishing()) {
            f.c(TAG, "--onNewIntent--activity not finished, finish now");
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        f.b(TAG, "--onSaveInstanceState--");
        bundle.putBoolean(RESTART_FLAG, true);
        super.onSaveInstanceState(bundle);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        StringBuilder sb = new StringBuilder("--onActivityResult--requestCode: ");
        sb.append(i);
        sb.append(" | resultCode: ");
        sb.append(i2);
        sb.append("data = null ? ");
        sb.append(intent == null);
        f.c(TAG, sb.toString());
        super.onActivityResult(i, i2, intent);
        if (i != 0) {
            if (intent != null) {
                intent.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_LOGIN);
            }
            setResultData(i2, intent);
            finish();
        }
    }

    public void setResultData(int i, Intent intent) {
        if (intent == null) {
            f.d(TAG, "--setResultData--intent is null, setResult ACTIVITY_CANCEL");
            setResult(0, intent);
            return;
        }
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_RESPONSE);
            f.b(TAG, "--setResultDataForLogin-- ".concat(String.valueOf(stringExtra)));
            if (!TextUtils.isEmpty(stringExtra)) {
                JSONObject jSONObject = new JSONObject(stringExtra);
                String optString = jSONObject.optString("openid");
                String optString2 = jSONObject.optString("access_token");
                if (TextUtils.isEmpty(optString) || TextUtils.isEmpty(optString2)) {
                    f.d(TAG, "--setResultData--openid or token is empty, setResult ACTIVITY_CANCEL");
                    setResult(0, intent);
                    return;
                }
                f.c(TAG, "--setResultData--openid and token not empty, setResult ACTIVITY_OK");
                setResult(-1, intent);
                return;
            }
            f.d(TAG, "--setResultData--response is empty, setResult ACTIVITY_OK");
            setResult(-1, intent);
        } catch (Exception e) {
            f.e(TAG, "--setResultData--parse response failed");
            e.printStackTrace();
        }
    }

    private void openBrowser(Bundle bundle) {
        String string = bundle.getString("viaShareType");
        String string2 = bundle.getString("callbackAction");
        String string3 = bundle.getString("url");
        String string4 = bundle.getString("openId");
        String string5 = bundle.getString("appId");
        String str = "";
        String str2 = "";
        if (SystemUtils.QQ_SHARE_CALLBACK_ACTION.equals(string2)) {
            str = Constants.VIA_SHARE_TO_QQ;
            str2 = "10";
        } else if (SystemUtils.QZONE_SHARE_CALLBACK_ACTION.equals(string2)) {
            str = Constants.VIA_SHARE_TO_QZONE;
            str2 = "11";
        }
        String str3 = str2;
        String str4 = str;
        if (!Util.openBrowser(this, string3)) {
            IUiListener listnerWithAction = UIListenerManager.getInstance().getListnerWithAction(string2);
            if (listnerWithAction != null) {
                listnerWithAction.onError(new UiError(-6, Constants.MSG_OPEN_BROWSER_ERROR, null));
            }
            d.a().a(string4, string5, str4, str3, "3", "1", string, "0", "2", "0");
            finish();
        } else {
            d.a().a(string4, string5, str4, str3, "3", "0", string, "0", "2", "0");
        }
        getIntent().removeExtra("shareH5");
    }
}
