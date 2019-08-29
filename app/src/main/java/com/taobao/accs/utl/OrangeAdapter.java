package com.taobao.accs.utl;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.ChannelService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.OrangeConfigListenerV1;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class OrangeAdapter {
    private static final String ACCS_ENABLE_KEY = "main_function_enable";
    private static final String BIND_SERVICE_KEY = "bind_service_enable";
    private static final String HEARTBEAT_KEY = "heartbeat_smart_enable";
    public static final String NAMESPACE = "accs";
    private static final String PULLUP = "pullup";
    private static final String TAG = "OrangeAdapter";
    private static final String TNET_ELECTION_KEY = "election_enable";
    private static final String TNET_LOG_KEY = "tnet_log_off";
    public static boolean mOrangeValid = false;

    public static class OrangeListener implements OrangeConfigListenerV1 {
        public void onConfigUpdate(String str, boolean z) {
            if (GlobalClientInfo.getContext() == null) {
                ALog.e(OrangeAdapter.TAG, "onConfigUpdate context null", new Object[0]);
                return;
            }
            try {
                ALog.i(OrangeAdapter.TAG, "onConfigUpdate", "namespace", str);
                if (str != null && "accs".equals(str)) {
                    OrangeAdapter.checkAccsEnabled();
                    OrangeAdapter.getConfigForAccs();
                }
            } catch (Throwable th) {
                ALog.e(OrangeAdapter.TAG, "onConfigUpdate", th, new Object[0]);
            }
        }
    }

    static {
        try {
            Class.forName("com.taobao.orange.OrangeConfig");
            mOrangeValid = true;
        } catch (Exception unused) {
            mOrangeValid = false;
        }
    }

    public static void registerListener(String[] strArr, OrangeConfigListenerV1 orangeConfigListenerV1) {
        if (mOrangeValid) {
            OrangeConfig.getInstance().registerListener(strArr, orangeConfigListenerV1);
        } else {
            ALog.w(TAG, "no orange sdk", new Object[0]);
        }
    }

    public static String getConfig(String str, String str2, String str3) {
        if (mOrangeValid) {
            return OrangeConfig.getInstance().getConfig(str, str2, str3);
        }
        ALog.w(TAG, "no orange sdk", new Object[0]);
        return str3;
    }

    public static boolean isAccsEnabled() {
        boolean z;
        try {
            z = Boolean.valueOf(getConfig("accs", ACCS_ENABLE_KEY, "true")).booleanValue();
        } catch (Throwable th) {
            ALog.e(TAG, "isAccsEnabled", th, new Object[0]);
            z = true;
        }
        ALog.i(TAG, "isAccsEnabled", "enable", Boolean.valueOf(z));
        return z;
    }

    public static boolean isSmartHb() {
        boolean z;
        try {
            z = getConfigFromSP(GlobalClientInfo.getContext(), Constants.SP_KEY_HB_SMART_ENABLE, true);
        } catch (Throwable th) {
            ALog.e(TAG, "isSmartHb", th, new Object[0]);
            z = true;
        }
        ALog.d(TAG, "isSmartHb", "result", Boolean.valueOf(z));
        return z;
    }

    public static boolean isBindService() {
        boolean z;
        try {
            z = getConfigFromSP(GlobalClientInfo.getContext(), "bind_service_enable", true);
        } catch (Throwable th) {
            ALog.e(TAG, "isBindService", th, new Object[0]);
            z = true;
        }
        ALog.d(TAG, "isBindService", "result", Boolean.valueOf(z));
        return z;
    }

    public static boolean isTnetLogOff(boolean z) {
        boolean z2;
        Throwable th;
        boolean z3;
        String str = "default";
        if (z) {
            try {
                str = getConfig("accs", "tnet_log_off", "default");
            } catch (Throwable th2) {
                th = th2;
                z3 = true;
                ALog.e(TAG, "isTnetLogOff", th, new Object[0]);
                z2 = z3;
                ALog.i(TAG, "isTnetLogOff", "result", Boolean.valueOf(z2));
                return z2;
            }
        }
        if (str.equals("default")) {
            z2 = getConfigFromSP(GlobalClientInfo.getContext(), "tnet_log_off", true);
        } else {
            z2 = Boolean.valueOf(str).booleanValue();
            try {
                saveConfigToSP(GlobalClientInfo.getContext(), (String) "tnet_log_off", z2);
            } catch (Throwable th3) {
                Throwable th4 = th3;
                z3 = z2;
                th = th4;
            }
        }
        ALog.i(TAG, "isTnetLogOff", "result", Boolean.valueOf(z2));
        return z2;
    }

    private static boolean getConfigFromSP(Context context, String str, boolean z) {
        try {
            return context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getBoolean(str, z);
        } catch (Exception e) {
            ALog.e(TAG, "getConfigFromSP fail:", e, "key", str);
            return z;
        }
    }

    private static void saveConfigToSP(Context context, String str, boolean z) {
        if (context == null) {
            try {
                ALog.e(TAG, "saveTLogOffToSP context null", new Object[0]);
            } catch (Exception e) {
                ALog.e(TAG, "saveConfigToSP fail:", e, "key", str, "value", Boolean.valueOf(z));
            }
        } else {
            Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            edit.putBoolean(str, z);
            edit.apply();
            ALog.i(TAG, "saveConfigToSP", "key", str, "value", Boolean.valueOf(z));
        }
    }

    public static void saveConfigToSP(Context context, String str, int i) {
        if (context == null) {
            try {
                ALog.e(TAG, "saveTLogOffToSP context null", new Object[0]);
            } catch (Exception e) {
                ALog.e(TAG, "saveConfigToSP fail:", e, "key", str, "value", Integer.valueOf(i));
            }
        } else {
            Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            edit.putInt(str, i);
            edit.apply();
            ALog.i(TAG, "saveConfigToSP", "key", str, "value", Integer.valueOf(i));
        }
    }

    private static void saveConfigsToSP(Context context, Map<String, Boolean> map) {
        if (map != null) {
            try {
                if (map.size() != 0) {
                    Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                    for (Entry next : map.entrySet()) {
                        edit.putBoolean((String) next.getKey(), ((Boolean) next.getValue()).booleanValue());
                    }
                    edit.apply();
                    ALog.i(TAG, "saveConfigsToSP", "configs", map.toString());
                }
            } catch (Exception e) {
                ALog.e(TAG, "saveConfigsToSP fail:", e, "configs", map.toString());
            }
        }
    }

    private static void savePullupInfo(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                Editor edit = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                edit.putString(PULLUP, str);
                edit.apply();
            } catch (Throwable th) {
                ALog.e(TAG, "savePullupInfo fail:", th, PULLUP, str);
            }
            ALog.i(TAG, "savePullupInfo", PULLUP, str);
        }
    }

    public static String getPullupInfo() {
        try {
            return GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_FILE_NAME, 0).getString(PULLUP, null);
        } catch (Throwable th) {
            ALog.e(TAG, "getPullupInfo fail:", th, new Object[0]);
            return null;
        }
    }

    public static void getConfigForAccs() {
        HashMap hashMap = new HashMap();
        hashMap.put("tnet_log_off", Boolean.valueOf(getConfig("accs", "tnet_log_off", "false")));
        hashMap.put("election_enable", Boolean.valueOf(getConfig("accs", "election_enable", String.valueOf(GlobalClientInfo.mSupprotElection))));
        hashMap.put(Constants.SP_KEY_HB_SMART_ENABLE, Boolean.valueOf(getConfig("accs", HEARTBEAT_KEY, "true")));
        hashMap.put("bind_service_enable", Boolean.valueOf(getConfig("accs", "bind_service_enable", "true")));
        saveConfigsToSP(GlobalClientInfo.getContext(), hashMap);
        saveConfigToSP(GlobalClientInfo.getContext(), (String) ChannelService.SUPPORT_FOREGROUND_VERSION_KEY, UtilityImpl.String2Int(getConfig("accs", ChannelService.SUPPORT_FOREGROUND_VERSION_KEY, "24")));
        savePullupInfo(getConfig("accs", PULLUP, null));
    }

    public static void checkAccsEnabled() {
        if (!isAccsEnabled()) {
            ALog.e(TAG, "force disable service", new Object[0]);
            ACCSManager.forceDisableService(GlobalClientInfo.getContext());
        } else if (UtilityImpl.getFocusDisableStatus(GlobalClientInfo.getContext())) {
            ALog.i(TAG, "force enable service", new Object[0]);
            ACCSManager.forceEnableService(GlobalClientInfo.getContext());
        }
    }
}
