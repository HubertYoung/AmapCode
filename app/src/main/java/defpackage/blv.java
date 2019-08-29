package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: blv reason: default package */
/* compiled from: TRCCompensateAction */
public class blv extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        if (a() != null && jSONObject != null) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("amap.extra.road.camera.pageparam", jSONObject);
                pageContext.startPage((String) "amap.drive.action.road.camera", pageBundle);
            }
        }
    }
}
