package defpackage;

import android.text.TextUtils;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3DialogPage;
import com.autonavi.minimap.ajx3.Ajx3Page;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ehc reason: default package */
/* compiled from: ShareBikeAjxPage */
public final class ehc {
    public static void a(int i, String str) {
        String str2;
        Class cls = Ajx3Page.class;
        switch (i) {
            case 1:
                str2 = "path://amap_bundle_tripgroup/src/share_bike/ShareBikeHistory.page.js";
                break;
            case 2:
                str2 = "path://amap_bundle_tripgroup/src/share_bike/WalletListPage.page.js";
                break;
            case 3:
                str2 = "path://amap_bundle_tripgroup/src/share_bike/ShareBikeWalletDetail.page.js";
                break;
            case 4:
                str2 = "path://amap_bundle_tripgroup/src/share_bike/ShareBikeHelp.page.js";
                break;
            case 6:
                try {
                    String optString = new JSONObject(str).optString("firepage");
                    awt awt = (awt) a.a.a(awt.class);
                    if (awt != null) {
                        awt.a(optString);
                    }
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
            case 7:
                str2 = "path://amap_bundle_tripgroup/src/share_bike/ShareBikeLoginPage.page.js";
                cls = Ajx3DialogPage.class;
                break;
            default:
                str2 = null;
                break;
        }
        if (!TextUtils.isEmpty(str2)) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", str2);
            if (!TextUtils.isEmpty(str)) {
                pageBundle.putString("jsData", str);
            }
            AMapPageUtil.getPageContext().startPage(cls, pageBundle);
        }
    }
}
