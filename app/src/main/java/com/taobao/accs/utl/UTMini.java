package com.taobao.accs.utl;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.ta.utdid2.device.UTDevice;
import com.ut.mini.IUTApplication;
import com.ut.mini.UTAnalytics;
import com.ut.mini.UTHitBuilders.UTHitBuilder;
import com.ut.mini.core.sign.IUTRequestAuthentication;
import com.ut.mini.core.sign.UTSecuritySDKRequestAuthentication;
import com.ut.mini.crashhandler.IUTCrashCaughtListner;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import java.util.Map;
import java.util.Map.Entry;

public final class UTMini implements UT {
    public static final int EVENTID_AGOO = 19999;
    public static final String PAGE_AGOO = "Page_Push";
    private static final String TAG = "UTMini";
    private static UTMini instance = new UTMini();

    public final void onCaughException(Throwable th) {
    }

    public final void stop(Context context) {
    }

    public static UTMini getInstance() {
        return instance;
    }

    public final void start(Application application, final String str, String str2, final String str3) {
        try {
            UTAnalytics.getInstance().setAppApplicationInstance(application, new IUTApplication() {
                public String getUTAppVersion() {
                    return null;
                }

                public IUTCrashCaughtListner getUTCrashCraughtListener() {
                    return null;
                }

                public boolean isAliyunOsSystem() {
                    return false;
                }

                public boolean isUTCrashHandlerDisable() {
                    return false;
                }

                public boolean isUTLogEnable() {
                    return false;
                }

                public String getUTChannel() {
                    return str3;
                }

                public IUTRequestAuthentication getUTRequestAuthInstance() {
                    return new UTSecuritySDKRequestAuthentication(str);
                }
            });
            ALog.d(TAG, "start success", new Object[0]);
        } catch (Throwable th) {
            ALog.e(TAG, "start fail ", th, new Object[0]);
        }
    }

    public final void commitEvent(int i, String str, Object obj) {
        try {
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(str, i, convertObjectToString(obj), null, null, null);
            UTAnalytics.getInstance().getTracker("accs").send(uTOriginalCustomHitBuilder.build());
        } catch (Throwable unused) {
        }
    }

    public final void commitEvent(int i, String str, Object obj, Object obj2) {
        try {
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(str, i, convertObjectToString(obj), convertObjectToString(obj2), null, null);
            UTAnalytics.getInstance().getTracker("accs").send(uTOriginalCustomHitBuilder.build());
        } catch (Throwable unused) {
        }
    }

    public final void commitEvent(int i, String str, Object obj, Object obj2, Object obj3) {
        try {
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(str, i, convertObjectToString(obj), convertObjectToString(obj2), convertObjectToString(obj3), null);
            UTAnalytics.getInstance().getTracker("accs").send(uTOriginalCustomHitBuilder.build());
        } catch (Throwable unused) {
        }
    }

    public final void commitEvent(int i, String str, Object obj, Object obj2, Object obj3, String... strArr) {
        try {
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(str, i, convertObjectToString(obj), convertObjectToString(obj2), convertObjectToString(obj3), null);
            uTOriginalCustomHitBuilder.setProperty(UTHitBuilder.FIELD_ARGS, _convertStringAToKVSString(strArr));
            UTAnalytics.getInstance().getTracker("accs").send(uTOriginalCustomHitBuilder.build());
        } catch (Throwable unused) {
        }
    }

    public final void commitEvent(int i, String str, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        try {
            commitEvent(i, str, obj, obj2, obj3, mapToArray(map));
        } catch (Throwable unused) {
        }
    }

    public final String getUtdId(Context context) {
        try {
            return UTDevice.getUtdid(context);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String convertObjectToString(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return ((String) obj).toString();
        }
        if (obj instanceof Integer) {
            StringBuilder sb = new StringBuilder();
            sb.append(((Integer) obj).intValue());
            return sb.toString();
        } else if (obj instanceof Long) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(((Long) obj).longValue());
            return sb2.toString();
        } else if (obj instanceof Double) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(((Double) obj).doubleValue());
            return sb3.toString();
        } else if (obj instanceof Float) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(((Float) obj).floatValue());
            return sb4.toString();
        } else if (obj instanceof Short) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(((Short) obj).shortValue());
            return sb5.toString();
        } else if (obj instanceof Byte) {
            StringBuilder sb6 = new StringBuilder();
            sb6.append(((Byte) obj).byteValue());
            return sb6.toString();
        } else if (obj instanceof Boolean) {
            return ((Boolean) obj).toString();
        } else {
            if (obj instanceof Character) {
                return ((Character) obj).toString();
            }
            return obj.toString();
        }
    }

    private String _convertStringAToKVSString(String... strArr) {
        if (strArr != null && strArr.length == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (strArr != null && strArr.length > 0) {
            boolean z = false;
            for (int i = 0; i < strArr.length; i++) {
                if (!TextUtils.isEmpty(strArr[i])) {
                    if (z) {
                        stringBuffer.append(",");
                    }
                    stringBuffer.append(strArr[i]);
                    z = true;
                }
            }
        }
        return stringBuffer.toString();
    }

    private static String[] mapToArray(Map<String, String> map) {
        int i = 0;
        if (map == null || map.isEmpty()) {
            return new String[0];
        }
        String[] strArr = new String[map.size()];
        for (Entry next : map.entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            if (str == null) {
                strArr[i] = "";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("=");
                sb.append(str2);
                strArr[i] = sb.toString();
            }
            i++;
        }
        return strArr;
    }

    public static String getCommitInfo(int i, String str, String str2, String str3, Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("eventId=");
        sb.append(i);
        sb.append(";arg1=");
        sb.append(str);
        sb.append(";arg2=");
        sb.append(str2);
        sb.append(";arg3=");
        sb.append(str3);
        if (map != null) {
            sb.append(";args=");
            sb.append(map.toString());
        }
        return sb.toString();
    }

    public static String getCommitInfo(int i, String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        sb.append("eventId=");
        sb.append(i);
        sb.append(";arg1=");
        sb.append(str);
        sb.append(";arg2=");
        sb.append(str2);
        sb.append(";arg3=");
        sb.append(str3);
        if (str4 != null) {
            sb.append(";args=");
            sb.append(str4);
        }
        return sb.toString();
    }
}
