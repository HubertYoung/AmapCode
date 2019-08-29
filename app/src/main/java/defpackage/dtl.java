package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.scansdk.constant.Constants;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.photograph.LaunchCameraAndGalleryPage;
import com.autonavi.minimap.photograph.LaunchOnlyCameraPage;
import com.autonavi.minimap.photograph.LaunchOnlyGalleryPage;
import com.autonavi.minimap.photograph.jsaction.AddPhotoAction$1;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dtl reason: default package */
/* compiled from: AddPhotoAction */
public class dtl extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String str = "";
            String str2 = "";
            String str3 = "";
            String str4 = "";
            String str5 = "imgbase64";
            String str6 = "";
            String str7 = "";
            Object obj = null;
            if (jSONObject != null) {
                try {
                    obj = jSONObject.getJSONObject("example");
                } catch (JSONException unused) {
                }
                str = jSONObject.optString("_action", "");
                str2 = jSONObject.optString("businessName", "");
                str3 = jSONObject.optString(Constants.SERVICE_TITLE_TEXT, "");
                str4 = jSONObject.optString("maxLength", "");
                str6 = jSONObject.optString("onlyCamera", "");
                str7 = jSONObject.optString("onlyPicture", "");
                str5 = jSONObject.optString("returnType", "imgbase64");
            }
            AddPhotoAction$1 addPhotoAction$1 = new AddPhotoAction$1(this, a, waVar);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("_action", str);
            pageBundle.putObject("callback", addPhotoAction$1);
            pageBundle.putString("businessName", str2);
            pageBundle.putString(Constants.SERVICE_TITLE_TEXT, str3);
            pageBundle.putString("maxLength", str4);
            pageBundle.putObject("example", obj);
            pageBundle.putString("returnType", str5);
            boolean z = false;
            boolean z2 = !TextUtils.isEmpty(str6) && Boolean.parseBoolean(str6);
            if (!TextUtils.isEmpty(str7) && Boolean.parseBoolean(str7)) {
                z = true;
            }
            if (z2) {
                a.mPageContext.startPage(LaunchOnlyCameraPage.class, pageBundle);
            } else if (z) {
                a.mPageContext.startPage(LaunchOnlyGalleryPage.class, pageBundle);
            } else {
                a.mPageContext.startPage(LaunchCameraAndGalleryPage.class, pageBundle);
            }
        }
    }
}
