package com.amap.bundle.statistics;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.statistics.log.LogRecorder;
import com.autonavi.amap.app.AMapAppGlobal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;

public class LogManager {
    public static final int INDENT_SPACES = 2;
    private static final String KEY_DIV = "div";
    public static final String TAG = "LogManager";
    private static long beginTime;
    private static long lastEventStartTime;
    private static volatile LogManager mInstance;
    private static volatile JSONObject sDefaultDivObject;
    private static String sdiv;

    private LogManager() {
    }

    public static LogManager getInstance() {
        if (mInstance == null) {
            synchronized (LogManager.class) {
                if (mInstance == null) {
                    mInstance = new LogManager();
                }
            }
        }
        return mInstance;
    }

    public static void startInitLogTask() {
        LogRecorder.getInstance().startInitLogTask(AMapAppGlobal.getApplication());
    }

    public static long getLastEventStartTime() {
        return lastEventStartTime;
    }

    private static long getTime() {
        if (beginTime == 0) {
            try {
                beginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault()).parse("2011-01-01 00:00:00:000").getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new Date().getTime() - beginTime;
    }

    @NonNull
    private static JSONObject addCommonParams(JSONObject jSONObject) {
        if (TextUtils.isEmpty(sdiv)) {
            sdiv = a.a().a;
            sDefaultDivObject = new JSONObject();
            try {
                sDefaultDivObject.put("div", sdiv);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jSONObject == null) {
            if (sDefaultDivObject == null) {
                sDefaultDivObject = new JSONObject();
            }
            return sDefaultDivObject;
        }
        try {
            jSONObject.put("div", sdiv);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    @NonNull
    private static JSONObject obtainDefaultParams() {
        return addCommonParams(null);
    }

    public static void actionLog(int i, int i2) {
        obtainDefaultParams();
        actionLog(i, i2, 0, 0);
    }

    public static void actionLog(int i, int i2, int i3, int i4) {
        JSONObject obtainDefaultParams = obtainDefaultParams();
        try {
            afg afg = new afg();
            afg.e = Long.valueOf(getTime());
            afg.b = String.valueOf(i);
            afg.c = String.valueOf(i2);
            afg.g = Integer.valueOf(i3);
            afg.h = Integer.valueOf(i4);
            afg.i = obtainDefaultParams.toString();
            LogRecorder.getInstance().addActionLog(afg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionLog(int i, int i2, JSONObject jSONObject) {
        try {
            JSONObject addCommonParams = addCommonParams(jSONObject);
            afg afg = new afg();
            afg.b = String.valueOf(i);
            afg.c = String.valueOf(i2);
            if (addCommonParams != null) {
                afg.i = addCommonParams.toString();
            }
            LogRecorder.getInstance().addActionLog(afg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionLog(int i, String str, JSONObject jSONObject) {
        JSONObject addCommonParams = addCommonParams(jSONObject);
        try {
            afg afg = new afg();
            afg.b = String.valueOf(i);
            afg.c = str;
            if (addCommonParams != null) {
                afg.i = addCommonParams.toString();
            }
            LogRecorder.getInstance().addActionLog(afg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionLog(int i, int i2, long j, JSONObject jSONObject) {
        try {
            JSONObject addCommonParams = addCommonParams(jSONObject);
            afg afg = new afg();
            afg.b = String.valueOf(i);
            afg.c = String.valueOf(i2);
            afg.d = Long.valueOf(j);
            if (addCommonParams != null) {
                afg.i = addCommonParams.toString();
            }
            LogRecorder.getInstance().addActionLog(afg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionLog(int i, String str, long j, JSONObject jSONObject) {
        JSONObject addCommonParams = addCommonParams(jSONObject);
        try {
            afg afg = new afg();
            afg.b = String.valueOf(i);
            afg.c = str;
            afg.d = Long.valueOf(j);
            if (addCommonParams != null) {
                afg.i = addCommonParams.toString();
            }
            LogRecorder.getInstance().addActionLog(afg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionLog(int i, int i2, int i3, int i4, JSONObject jSONObject) {
        JSONObject addCommonParams = addCommonParams(jSONObject);
        try {
            afg afg = new afg();
            afg.b = String.valueOf(i);
            afg.c = String.valueOf(i2);
            afg.g = Integer.valueOf(i3);
            afg.h = Integer.valueOf(i4);
            if (addCommonParams != null) {
                afg.i = addCommonParams.toString();
            }
            LogRecorder.getInstance().addActionLog(afg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionLogV2(String str, String str2) {
        obtainDefaultParams();
        actionLogV2(str, str2, 0, 0);
    }

    public static void actionLogV2(String str, String str2, int i, int i2) {
        JSONObject obtainDefaultParams = obtainDefaultParams();
        try {
            afg afg = new afg();
            afg.e = Long.valueOf(getTime());
            afg.b = str;
            afg.c = str2;
            afg.g = Integer.valueOf(i);
            afg.h = Integer.valueOf(i2);
            if (obtainDefaultParams != null) {
                afg.i = obtainDefaultParams.toString();
            }
            LogRecorder.getInstance().addActionLog(afg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actionLogV2(String str, String str2, JSONObject jSONObject) {
        JSONObject addCommonParams = addCommonParams(jSONObject);
        if (bno.a) {
            if (addCommonParams == null) {
                addCommonParams = new JSONObject();
            }
            try {
                StringBuilder sb = new StringBuilder("[actionLogV2(String pageId, String buttonId, JSONObject jo)]\npageId:");
                sb.append(str);
                sb.append(", buttonId:");
                sb.append(str2);
                sb.append("\nPARAMS");
                sb.append(addCommonParams.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            afg afg = new afg();
            afg.b = str;
            afg.c = str2;
            if (addCommonParams != null) {
                afg.i = addCommonParams.toString();
            }
            LogRecorder.getInstance().addActionLog(afg);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void actionLogV25(String str, String str2, Entry... entryArr) {
        if (bno.a) {
            StringBuilder sb = new StringBuilder("pageId:");
            sb.append(str);
            sb.append(", buttonId:");
            sb.append(str2);
        }
        JSONObject jSONObject = null;
        if (entryArr != null) {
            try {
                if (entryArr.length > 0) {
                    jSONObject = new JSONObject();
                    for (Entry entry : entryArr) {
                        jSONObject.put(entry.getKey().toString(), entry.getValue());
                    }
                }
            } catch (JSONException unused) {
                actionLogV2(str, str2);
                return;
            }
        }
        actionLogV2(str, str2, jSONObject);
    }

    public static void actionLogV2(String str, String str2, int i, int i2, JSONObject jSONObject) {
        JSONObject addCommonParams = addCommonParams(jSONObject);
        if (bno.a) {
            if (addCommonParams == null) {
                addCommonParams = new JSONObject();
            }
            try {
                StringBuilder sb = new StringBuilder("[actionLogV2(String pageId, String buttonId, JSONObject jo)]\npageId:");
                sb.append(str);
                sb.append(", buttonId:");
                sb.append(str2);
                sb.append("\nPARAMS");
                sb.append(addCommonParams.toString(2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            afg afg = new afg();
            afg.b = str;
            afg.c = str2;
            afg.g = Integer.valueOf(i);
            afg.h = Integer.valueOf(i2);
            if (addCommonParams != null) {
                afg.i = addCommonParams.toString();
            }
            LogRecorder.getInstance().addActionLog(afg);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
