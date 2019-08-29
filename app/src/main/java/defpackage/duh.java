package defpackage;

import android.content.Context;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.busline.api.IBusLinePage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.bus.busline.page.BusLineSearchPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineToMapPage;
import com.autonavi.minimap.route.bus.model.Bus;
import org.json.JSONException;
import org.json.JSONObject;

@BundleInterface(asy.class)
/* renamed from: duh reason: default package */
/* compiled from: BusLineService */
public class duh extends esi implements asy, bie {
    public final void a(PageBundle pageBundle) {
        AMapPageUtil.getPageContext().startPage(BusLineSearchPage.class, pageBundle);
    }

    public final void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("lineInfo", "");
            int optInt = jSONObject.optInt("mapWatchMode", 1);
            Bus parse = Bus.parse(new JSONObject(optString));
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("BusLineToMapFragment.CURBUS", parse);
            pageBundle.putInt("BusLineToMapFragment.WATCH_MODE", optInt);
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage(BusLineToMapPage.class, pageBundle);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public final void a(Context context, String str, String str2) {
        dwl.a(context, str, str2);
    }

    public final void b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("lineInfo", "");
            dva.a(Bus.parse(new JSONObject(optString)), jSONObject.optInt("selIndex"), jSONObject.optBoolean("isRtbBoard"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public final ata a() {
        return a.a;
    }

    public final IBusLinePage b() {
        return a.a;
    }

    public final asz c() {
        return a.a;
    }

    public final String a(Context context, String str) {
        if (context == null || str == null) {
            return null;
        }
        return context.getSharedPreferences("route_favorite_busline_data", 0).getString(str, null);
    }
}
