package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.bundle.buscard.page.BusCardFullPage;
import com.autonavi.bundle.buscard.page.BusCardPayPage;
import com.autonavi.bundle.buscard.page.BusCardSimplePage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"buscard"})
/* renamed from: ast reason: default package */
/* compiled from: BusCardRouter */
public class ast extends esk {
    public static void a(int i, String str) {
        final Class cls;
        switch (i) {
            case 1:
                cls = BusCardFullPage.class;
                break;
            case 2:
                cls = BusCardSimplePage.class;
                break;
            case 3:
                cls = BusCardPayPage.class;
                break;
            default:
                cls = null;
                break;
        }
        if (cls != null) {
            final PageBundle pageBundle = new PageBundle();
            if (!TextUtils.isEmpty(str)) {
                pageBundle.putString("jsData", str);
            }
            ebr.a(true).post(new Runnable() {
                public final void run() {
                    AMapPageUtil.getPageContext().startPageForResult(cls, pageBundle, 1014);
                }
            });
        }
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        String str = uri.getPathSegments().get(0);
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (TextUtils.equals(str, "detailPage")) {
            String queryParameter = uri.getQueryParameter("from");
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("from", queryParameter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            a(1, jSONObject.toString());
            return true;
        } else if (TextUtils.equals(str, "simplePage")) {
            a(2, null);
            return true;
        } else if (!TextUtils.equals(str, "qr_horizontalPage")) {
            return false;
        } else {
            a(3, null);
            return true;
        }
    }
}
