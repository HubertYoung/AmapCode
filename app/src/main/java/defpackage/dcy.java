package defpackage;

import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import org.json.JSONObject;

/* renamed from: dcy reason: default package */
/* compiled from: ShareToFriendAction */
public class dcy extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            PageBundle bundle = a().getBundle();
            POI poi = bundle != null ? (POI) bundle.getObject("POI") : null;
            try {
                JSONObject jSONObject2 = new JSONObject();
                if (poi != null) {
                    jSONObject2.put("PoiId", poi.getId());
                    jSONObject2.put("page_type", poi.getType());
                    jSONObject2.put("type", poi.getType());
                    LogManager.actionLog(11100, 6, jSONObject2);
                    bid bid = a.mPageContext;
                    if (bid != null) {
                        if (bid.getActivity() != null) {
                            dct dct = new dct();
                            dct.b = false;
                            dcb dcb = (dcb) a.a.a(dcb.class);
                            if (dcb != null) {
                                dcb.a(DoNotUseTool.getContext(), dct, poi.clone(), ConfigerHelper.getInstance().getShareMsgUrl());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
