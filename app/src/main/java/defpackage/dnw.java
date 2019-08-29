package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.tencent.open.SocialConstants;
import java.io.File;
import org.json.JSONObject;

/* renamed from: dnw reason: default package */
/* compiled from: OpenResourceByPathAction */
public class dnw extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String optString = jSONObject.optString("type");
            String optString2 = jSONObject.optString("path");
            if (!TextUtils.isEmpty(optString2) && new File(optString2).exists() && optString.equals(SocialConstants.PARAM_AVATAR_URI)) {
                drj.a();
                drj.a(a.mPageContext, optString2);
            }
        }
    }
}
