package com.amap.bundle.statistics.util;

import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.autonavi.common.Callback;
import java.io.File;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

public class LogUploadUtil$1 implements AosResponseCallback<AosByteResponse> {
    final /* synthetic */ Callback a;
    final /* synthetic */ File b;
    final /* synthetic */ afn c;

    public LogUploadUtil$1(afn afn, Callback callback, File file) {
        this.c = afn;
        this.a = callback;
        this.b = file;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        try {
            JSONObject jSONObject = new JSONObject(new String((byte[]) ((AosByteResponse) aosResponse).getResult(), "UTF-8").trim());
            if (jSONObject.getBoolean("result")) {
                if (jSONObject.getInt("code") == 1) {
                    this.a.callback(Integer.valueOf(200));
                }
            }
        } catch (JSONException e) {
            if (this.a != null) {
                this.a.error(e, true);
            }
        } catch (UnsupportedEncodingException e2) {
            if (this.a != null) {
                this.a.error(e2, true);
            }
        }
        ahm.a(new Runnable() {
            public final void run() {
                LogUploadUtil$1.this.b.delete();
            }
        });
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        if (this.a != null) {
            this.a.error(aosResponseException, false);
        }
    }
}
