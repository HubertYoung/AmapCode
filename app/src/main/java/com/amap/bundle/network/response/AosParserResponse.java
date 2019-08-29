package com.amap.bundle.network.response;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class AosParserResponse extends AosByteResponse {
    public static final String b = AMapAppGlobal.getApplication().getString(R.string.network_error_message);
    public static final String c = AMapAppGlobal.getApplication().getString(R.string.network_error_message);
    public static final String d = AMapAppGlobal.getApplication().getString(R.string.error_fail_to_parse_data);
    protected static final String e = AMapAppGlobal.getApplication().getString(R.string.error_unknown);
    /* access modifiers changed from: private */
    public String a = null;
    public int f = -1;
    public String g = b;
    public String h = "";
    public long i = 0;
    public boolean j = false;
    public JSONObject k = null;
    /* access modifiers changed from: private */
    public String l = null;

    public abstract String b();

    /* renamed from: a */
    public byte[] parseResult() {
        byte[] responseBodyData = getResponseBodyData();
        if (responseBodyData == null) {
            this.f = -1;
            return null;
        }
        try {
            this.k = new JSONObject(new String(responseBodyData, "UTF-8"));
            this.h = this.k.getString("version");
            this.j = this.k.getBoolean("result");
            this.f = this.k.getInt("code");
            String str = this.g;
            JSONObject jSONObject = this.k;
            if (jSONObject != null) {
                String optString = jSONObject.optString("message");
                if (!TextUtils.isEmpty(optString)) {
                    str = optString;
                }
            }
            this.g = str;
            this.i = this.k.getLong("timestamp");
            JSONArray optJSONArray = this.k.optJSONArray("_notice_");
            if (optJSONArray != null && optJSONArray.length() > 0) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(0);
                String optString2 = optJSONObject.optString(ResUtils.STYLE, "");
                this.a = optJSONObject.optString("content");
                this.l = optJSONObject.optString("action");
                if (optString2.equalsIgnoreCase("0")) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        public final void run() {
                            aaf.a(AosParserResponse.this.a);
                            AosParserResponse.this.a = null;
                            AosParserResponse.this.l = null;
                        }
                    }, 1000);
                } else if (!optString2.equalsIgnoreCase("-1")) {
                    this.a = null;
                    this.l = null;
                }
            }
        } catch (Exception e2) {
            this.j = false;
            this.f = -2;
            this.g = d;
            AMapLog.logFatalNative(AMapLog.GROUP_COMMON, "P0002", ALCTtsConstant.EVENT_ID_TTS_INIT_ERROR, e2.toString());
        }
        this.g = b();
        return getResponseBodyData();
    }
}
