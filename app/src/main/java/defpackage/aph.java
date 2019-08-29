package defpackage;

import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"airticket"})
/* renamed from: aph reason: default package */
/* compiled from: AirTicketRouter */
public class aph extends esk {
    public static final String a = "aph";

    private boolean a() {
        String str = "https://h5.m.taobao.com/trip/flight/myorder/list.html";
        String a2 = lo.a().a((String) "airticket_orders");
        if (!TextUtils.isEmpty(a2)) {
            try {
                String optString = new JSONObject(a2).optString("url", "");
                if (!TextUtils.isEmpty(optString)) {
                    str = optString;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        eao.a(a, "fliggy order url: ".concat(String.valueOf(str)));
        aja aja = new aja(str);
        aja.b = new ajf() {
            public final boolean g() {
                return true;
            }

            public final boolean h() {
                return true;
            }
        };
        aix aix = (aix) a.a.a(aix.class);
        if (aix == null) {
            return false;
        }
        aix.a(AMapPageUtil.getPageContext(), aja);
        return true;
    }

    public boolean start(ese ese) {
        if (ese == null) {
            return false;
        }
        List<String> pathSegments = ese.a.getPathSegments();
        if (pathSegments == null || pathSegments.size() <= 0 || !TextUtils.equals(pathSegments.get(0), "orders")) {
            return false;
        }
        return a();
    }
}
