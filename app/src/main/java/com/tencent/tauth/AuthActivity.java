package com.tencent.tauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import com.tencent.connect.common.AssistActivity;
import com.tencent.open.a.f;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;

/* compiled from: ProGuard */
public class AuthActivity extends Activity {
    private static final String ACTION_ADD_TO_QQFAVORITES = "addToQQFavorites";
    public static final String ACTION_KEY = "action";
    private static final String ACTION_SEND_TO_MY_COMPUTER = "sendToMyComputer";
    public static final String ACTION_SHARE_PRIZE = "sharePrize";
    private static final String ACTION_SHARE_TO_QQ = "shareToQQ";
    private static final String ACTION_SHARE_TO_QZONE = "shareToQzone";
    private static final String ACTION_SHARE_TO_TROOP_BAR = "shareToTroopBar";
    private static final String SHARE_PRIZE_ACTIVITY_ID = "activityid";
    private static final String TAG = "openSDK_LOG.AuthActivity";
    private static int mShareQzoneBackTime;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent() == null) {
            f.d(TAG, "-->onCreate, getIntent() return null");
            return;
        }
        Uri uri = null;
        try {
            uri = getIntent().getData();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("-->onCreate, getIntent().getData() has exception! ");
            sb.append(e.getMessage());
            f.e(TAG, sb.toString());
        }
        f.a(TAG, "-->onCreate, uri: ".concat(String.valueOf(uri)));
        handleActionUri(uri);
    }

    private void handleActionUri(Uri uri) {
        String str;
        f.c(TAG, "-->handleActionUri--start");
        if (uri == null || uri.toString() == null || uri.toString().equals("")) {
            f.d(TAG, "-->handleActionUri, uri invalid");
            finish();
            return;
        }
        String uri2 = uri.toString();
        Bundle decodeUrl = Util.decodeUrl(uri2.substring(uri2.indexOf(MetaRecord.LOG_SEPARATOR) + 1));
        if (decodeUrl == null) {
            f.d(TAG, "-->handleActionUri, bundle is null");
            finish();
            return;
        }
        String string = decodeUrl.getString("action");
        f.c(TAG, "-->handleActionUri, action: ".concat(String.valueOf(string)));
        if (string == null) {
            execAuthCallback(decodeUrl, uri2);
        } else if (string.equals("shareToQQ") || string.equals("shareToQzone") || string.equals("addToQQFavorites") || string.equals("sendToMyComputer") || string.equals("shareToTroopBar")) {
            if (string.equals("shareToQzone") && SystemUtils.getAppVersionName(this, "com.tencent.mobileqq") != null && SystemUtils.compareQQVersion(this, "5.2.0") < 0) {
                int i = mShareQzoneBackTime + 1;
                mShareQzoneBackTime = i;
                if (i == 2) {
                    mShareQzoneBackTime = 0;
                    finish();
                    return;
                }
            }
            f.c(TAG, "-->handleActionUri, most share action, start assistactivity");
            Intent intent = new Intent(this, AssistActivity.class);
            intent.putExtras(decodeUrl);
            intent.setFlags(603979776);
            startActivity(intent);
            finish();
        } else if (string.equals(ACTION_SHARE_PRIZE)) {
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(getPackageName());
            try {
                str = Util.parseJson(decodeUrl.getString(ModuleLongLinkService.CALLBACK_KEY_RESPONSE)).getString(SHARE_PRIZE_ACTIVITY_ID);
            } catch (Exception e) {
                f.b(TAG, "sharePrize parseJson has exception.", e);
                str = "";
            }
            if (!TextUtils.isEmpty(str)) {
                launchIntentForPackage.putExtra(ACTION_SHARE_PRIZE, true);
                Bundle bundle = new Bundle();
                bundle.putString(SHARE_PRIZE_ACTIVITY_ID, str);
                launchIntentForPackage.putExtras(bundle);
            }
            startActivity(launchIntentForPackage);
            finish();
        } else {
            execAuthCallback(decodeUrl, uri2);
        }
    }

    private void execAuthCallback(Bundle bundle, String str) {
        f.a(TAG, "execAuthCallback url = ".concat(String.valueOf(str)));
    }
}
