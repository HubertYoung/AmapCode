package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.PageBundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bln reason: default package */
/* compiled from: RegisterDataAction */
public class bln extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        AMapLog.d("TestWebView", "RegisterDataAction triggered.");
        JsAdapter a = a();
        if (a != null) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String optString = jSONObject2.optString("id");
                String optString2 = jSONObject2.optString("title");
                String optString3 = jSONObject2.optString("author");
                String optString4 = jSONObject2.optString("head_image");
                String optString5 = jSONObject2.optString("push_time", "");
                if (!TextUtils.isEmpty(optString5)) {
                    optString5 = optString5.split(Token.SEPARATOR)[0];
                }
                JSONArray optJSONArray = jSONObject2.optJSONArray("cat_id");
                String[] strArr = null;
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    strArr = new String[optJSONArray.length()];
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        strArr[i] = optJSONArray.getString(i);
                    }
                }
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("id", optString);
                pageBundle.putString("title", optString2);
                pageBundle.putString("author", optString3);
                pageBundle.putString("head_image", optString4);
                pageBundle.putString("push_time", optString5);
                if (strArr != null) {
                    pageBundle.putStringArray("cat_id", strArr);
                }
                a.mPageContext.setArguments(pageBundle);
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }
}
