package org.android.agoo.intent;

import android.content.Context;
import android.content.Intent;
import com.taobao.accs.utl.ALog;

public final class IntentUtil {
    public static final String AGOO_COMMAND = "command";
    private static final String INTENT_FROM_AGOO_COMMAND = ".intent.action.COMMAND";
    private static final String INTENT_FROM_STARTACTIVITY_COMMAND = ".intent.action.startActivity.COMMAND";
    private static final String INTENT_FROM_THIRDPUSH_COMMAND = ".intent.thirdPush.action.COMMAND";
    private static final String TAG = "IntentUtil";

    public static final Intent createActivityIntent(Context context, String str, Intent intent) {
        Intent intent2;
        try {
            intent2 = new Intent();
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(context.getPackageName());
                sb.append(INTENT_FROM_STARTACTIVITY_COMMAND);
                intent2.setAction(sb.toString());
                intent2.setPackage(context.getPackageName());
                intent2.setClassName(context, "com.taobao.agoo.AgooStartActivityService");
                intent2.putExtra("command", str);
                intent2.putExtras(intent.getExtras());
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            intent2 = null;
            ALog.w(TAG, "createComandIntent", th, new Object[0]);
            return intent2;
        }
        return intent2;
    }

    public static final Intent createComandIntent(Context context, String str) {
        Intent intent;
        try {
            intent = new Intent();
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(context.getPackageName());
                sb.append(INTENT_FROM_AGOO_COMMAND);
                intent.setAction(sb.toString());
                intent.setPackage(context.getPackageName());
                intent.putExtra("command", str);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            intent = null;
            ALog.e(TAG, "createComandIntent", th, new Object[0]);
            return intent;
        }
        return intent;
    }

    public static final Intent createThirdpushComandIntent(Context context, String str) {
        Intent intent;
        try {
            intent = new Intent();
            try {
                intent.setAction(getThirdPushCommand(context));
                intent.setPackage(context.getPackageName());
                intent.putExtra("command", str);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            intent = null;
            ALog.w(TAG, "createComandIntent", th, new Object[0]);
            return intent;
        }
        return intent;
    }

    public static final String getAgooCommand(Context context) {
        if (context != null) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(context.getPackageName());
                sb.append(INTENT_FROM_AGOO_COMMAND);
                return sb.toString();
            } catch (Throwable th) {
                ALog.w(TAG, "getAgooCommand", th, new Object[0]);
            }
        }
        return null;
    }

    public static final String getThirdPushCommand(Context context) {
        if (context != null) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(context.getPackageName());
                sb.append(INTENT_FROM_THIRDPUSH_COMMAND);
                return sb.toString();
            } catch (Throwable th) {
                ALog.w(TAG, "getAgooCommand", th, new Object[0]);
            }
        }
        return null;
    }
}
