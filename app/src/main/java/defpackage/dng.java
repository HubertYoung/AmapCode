package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.bundle.setting.api.ISettingService;
import org.json.JSONObject;

/* renamed from: dng reason: default package */
/* compiled from: ShortcutNaviAction */
public class dng extends wb {
    public final boolean b() {
        return true;
    }

    public final void b(JSONObject jSONObject, wa waVar) {
        if (a() != null) {
            POI a = kv.a(jSONObject.optJSONObject("poiInfo").toString());
            if (!(a != null || a() == null || a().getBundle() == null)) {
                a = (POI) a().getBundle().getObject("POI");
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("toPoi", a);
            if (a() != null) {
                ISettingService iSettingService = (ISettingService) a.a.a(ISettingService.class);
                if (iSettingService != null) {
                    iSettingService.a(a().mPageContext, pageBundle);
                }
            }
        }
    }
}
