package com.amap.bundle.network.component.mergerequest;

import android.text.TextUtils;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.aosservice.response.AosStringResponse;
import com.autonavi.common.Callback;
import java.util.Iterator;
import java.util.Map.Entry;
import org.json.JSONObject;

public class MergeRequester$1 implements AosResponseCallbackOnUi<AosStringResponse> {
    final /* synthetic */ zz a;

    public MergeRequester$1(zz zzVar) {
        this.a = zzVar;
    }

    public /* synthetic */ void onSuccess(AosResponse aosResponse) {
        AosStringResponse aosStringResponse = (AosStringResponse) aosResponse;
        Iterator<Entry<String, Callback>> it = this.a.a.entrySet().iterator();
        try {
            JSONObject jSONObject = new JSONObject((String) aosStringResponse.getResult()).getJSONObject("data");
            while (it.hasNext()) {
                Entry next = it.next();
                String str = (String) next.getKey();
                Callback callback = (Callback) next.getValue();
                if (callback != null) {
                    try {
                        if (!TextUtils.isEmpty(str)) {
                            String optString = jSONObject.optString(str);
                            if (!TextUtils.isEmpty(optString)) {
                                callback.callback(optString);
                            }
                        }
                    } catch (Exception e) {
                        callback.error(e, true);
                    }
                }
            }
        } catch (Exception e2) {
            while (it.hasNext()) {
                Callback callback2 = (Callback) it.next().getValue();
                if (callback2 != null) {
                    callback2.error(e2, false);
                }
                it.remove();
            }
        }
    }

    public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
        Iterator<Entry<String, Callback>> it = this.a.a.entrySet().iterator();
        while (it.hasNext()) {
            Callback callback = (Callback) it.next().getValue();
            if (callback != null) {
                callback.error(aosResponseException, false);
            }
            it.remove();
        }
    }
}
