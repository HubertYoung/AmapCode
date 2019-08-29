package com.taobao.accs.antibrush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.taobao.accs.base.TaoBaseService.ExtHeaderType;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.MsgDistribute;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AntiBrush {
    private static final String ANTI_ATTACK_ACTION = "mtopsdk.mtop.antiattack.checkcode.validate.activity_action";
    private static final String ANTI_ATTACK_RESULT_ACTION = "mtopsdk.extra.antiattack.result.notify.action";
    public static final int STATUS_BRUSH = 419;
    private static final String TAG = "AntiBrush";
    private static String mHost = null;
    private static volatile boolean mIsInCheckCodeActivity = false;
    private static ScheduledFuture<?> mTimeoutTask;
    private BroadcastReceiver mAntiAttackReceiver = null;
    /* access modifiers changed from: private */
    public Context mContext;

    public static class AntiReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            boolean z = false;
            try {
                String stringExtra = intent.getStringExtra("Result");
                ALog.e(AntiBrush.TAG, "anti onReceive result: ".concat(String.valueOf(stringExtra)), new Object[z]);
                z = stringExtra.equalsIgnoreCase("success");
            } catch (Exception e) {
                ALog.e(AntiBrush.TAG, "anti onReceive", e, new Object[(z ? 1 : 0)]);
            } finally {
                AntiBrush.onResult(GlobalClientInfo.getContext(), z);
            }
        }
    }

    public boolean checkAntiBrush(URL url, Map<Integer, String> map) {
        boolean z = true;
        if (map != null) {
            try {
                if (UtilityImpl.isForeground(this.mContext)) {
                    String str = map.get(Integer.valueOf(ExtHeaderType.TYPE_STATUS.ordinal()));
                    if ((TextUtils.isEmpty(str) ? 0 : Integer.valueOf(str).intValue()) == 419) {
                        String str2 = map.get(Integer.valueOf(ExtHeaderType.TYPE_LOCATION.ordinal()));
                        if (!TextUtils.isEmpty(str2)) {
                            ALog.e(TAG, "start anti bursh location:".concat(String.valueOf(str2)), new Object[0]);
                            handleantiBrush(str2);
                            String str3 = null;
                            if (mTimeoutTask != null) {
                                mTimeoutTask.cancel(true);
                                mTimeoutTask = null;
                            }
                            mTimeoutTask = ThreadPoolExecutorFactory.schedule(new Runnable() {
                                public void run() {
                                    ALog.e(AntiBrush.TAG, "anti bursh timeout", new Object[0]);
                                    AntiBrush.onResult(AntiBrush.this.mContext, false);
                                }
                            }, 60000, TimeUnit.MILLISECONDS);
                            if (url != null) {
                                str3 = url.getHost();
                            }
                            mHost = str3;
                            if (!TextUtils.isEmpty(GlobalClientInfo.mCookieSec) && TextUtils.isEmpty(CookieMgr.getCookieSec(mHost))) {
                                ALog.e(TAG, "cookie invalid, clear", new Object[0]);
                                UtilityImpl.clearCookie(this.mContext);
                            }
                            return z;
                        }
                    }
                }
            } catch (Throwable th) {
                th = th;
                z = false;
                ALog.e(TAG, "checkAntiBrush error", th, new Object[0]);
                return z;
            }
        }
        z = false;
        try {
            ALog.e(TAG, "cookie invalid, clear", new Object[0]);
            UtilityImpl.clearCookie(this.mContext);
        } catch (Throwable th2) {
            th = th2;
            ALog.e(TAG, "checkAntiBrush error", th, new Object[0]);
            return z;
        }
        return z;
    }

    public AntiBrush(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static void onResult(Context context, boolean z) {
        mIsInCheckCodeActivity = false;
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(context.getPackageName());
        intent.putExtra("command", 104);
        intent.putExtra(Constants.KEY_ANTI_BRUSH_RET, z);
        MsgDistribute.distribMessage(context, intent);
        if (mTimeoutTask != null) {
            mTimeoutTask.cancel(true);
            mTimeoutTask = null;
        }
        if (mHost != null) {
            UtilityImpl.storeCookie(context, CookieMgr.getCookieSec(mHost));
        }
    }

    private void handleantiBrush(String str) {
        if (mIsInCheckCodeActivity) {
            ALog.e(TAG, "handleantiBrush return", "mIsInCheckCodeActivity", Boolean.valueOf(mIsInCheckCodeActivity));
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction(ANTI_ATTACK_ACTION);
            intent.setPackage(this.mContext.getPackageName());
            intent.setFlags(268435456);
            intent.putExtra("Location", str);
            ALog.e(TAG, "handleAntiBrush:", new Object[0]);
            this.mContext.startActivity(intent);
            mIsInCheckCodeActivity = true;
            if (this.mAntiAttackReceiver == null) {
                this.mAntiAttackReceiver = new AntiReceiver();
            }
            this.mContext.registerReceiver(this.mAntiAttackReceiver, new IntentFilter(ANTI_ATTACK_RESULT_ACTION));
        } catch (Throwable th) {
            ALog.e(TAG, "handleantiBrush", th, new Object[0]);
        }
    }
}
