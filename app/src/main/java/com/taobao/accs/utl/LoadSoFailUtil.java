package com.taobao.accs.utl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;

public class LoadSoFailUtil {
    public static final int MAX_FAIL_TIMES = 3;
    private static final String TAG = "LoadSoFailUtil";

    public static boolean enableSoFailCheck() {
        return true;
    }

    public static void loadSoSuccess() {
        try {
            int soFailTimes = getSoFailTimes();
            if (soFailTimes > 0) {
                Editor edit = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_LOAD_SO_FILE_NAME, 0).edit();
                edit.clear();
                edit.apply();
                ALog.i(TAG, "loadSoSuccess", "fail times", Integer.valueOf(soFailTimes));
            }
        } catch (Throwable th) {
            ALog.e(TAG, "loadSoSuccess", th, new Object[0]);
        }
    }

    public static void loadSoFail() {
        try {
            Context context = GlobalClientInfo.getContext();
            SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_LOAD_SO_FILE_NAME, 0);
            int i = sharedPreferences.getInt(Constants.SP_KEY_LOAD_SO_TIMES, 0) + 1;
            if (i > 0) {
                Editor edit = sharedPreferences.edit();
                edit.putInt(Constants.SP_KEY_LOAD_SO_TIMES, i);
                edit.commit();
            }
            ALog.e(TAG, "loadSoFail", "times", Integer.valueOf(i));
            if (VERSION.SDK_INT == 15) {
                UtilityImpl.killService(context);
            }
        } catch (Throwable th) {
            ALog.e(TAG, "loadSoFail", th, new Object[0]);
        }
    }

    public static int getSoFailTimes() {
        int i;
        try {
            if (!enableSoFailCheck()) {
                return 0;
            }
            i = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_LOAD_SO_FILE_NAME, 0).getInt(Constants.SP_KEY_LOAD_SO_TIMES, 0);
            try {
                ALog.i(TAG, "getSoFailTimes", "times", Integer.valueOf(i));
            } catch (Throwable th) {
                th = th;
            }
            return i;
        } catch (Throwable th2) {
            th = th2;
            i = 0;
            ALog.e(TAG, "getSoFailTimes", th, new Object[0]);
            return i;
        }
    }
}
