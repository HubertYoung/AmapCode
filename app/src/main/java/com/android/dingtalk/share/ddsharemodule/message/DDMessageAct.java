package com.android.dingtalk.share.ddsharemodule.message;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.android.dingtalk.share.ddsharemodule.ShareConstant;
import com.android.dingtalk.share.ddsharemodule.plugin.SignatureCheck;

public class DDMessageAct {
    private static final String TAG = "DDMessageAct";

    public static boolean sendDDFriend(Context context, String str, Bundle bundle) {
        if (context != null) {
            return send(context, str, bundle, initSendFriendIntent());
        }
        return false;
    }

    public static boolean sendDing(Context context, String str, Bundle bundle) {
        if (context != null) {
            return send(context, str, bundle, initSendDingIntent());
        }
        return false;
    }

    private static boolean send(Context context, String str, Bundle bundle, Intent intent) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        String packageName = context.getPackageName();
        String mD5Signature = SignatureCheck.getMD5Signature(context, packageName);
        intent.putExtra(ShareConstant.EXTRA_MESSAGE_SDK_VERSION, ShareConstant.SDK_VERSION);
        intent.putExtra(ShareConstant.EXTRA_MESSAGE_APP_PACKAGE_NAME, packageName);
        intent.putExtra(ShareConstant.EXTRA_ACTION_TYPE, ShareConstant.OUT_SHARE_ACTION_TYPE);
        intent.putExtra(ShareConstant.EXTRA_MESSAGE_APP_SIGNATURE, mD5Signature);
        intent.putExtra(ShareConstant.EXTRA_MESSAGE_APP_ID, str);
        if (VERSION.SDK_INT < 21) {
            intent.addFlags(268435456);
        }
        try {
            context.startActivity(intent);
            new StringBuilder("send dd message, intent=").append(intent);
            return true;
        } catch (ActivityNotFoundException unused) {
            return false;
        } catch (Exception e) {
            new StringBuilder("send fail ").append(e.getMessage());
            return false;
        }
    }

    private static Intent initSendFriendIntent() {
        return initIntentByScheme(ShareConstant.DD_ENTER_ACTIVITY_SCHEME);
    }

    private static Intent initSendDingIntent() {
        return initIntentByScheme(ShareConstant.DD_DING_ACTIVITY_SCHEME);
    }

    private static Intent initIntentByScheme(String str) {
        return new Intent("android.intent.action.VIEW", Uri.parse(str));
    }
}
