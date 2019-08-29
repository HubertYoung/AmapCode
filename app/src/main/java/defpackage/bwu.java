package defpackage;

import android.graphics.Rect;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.gdtaojin.basemap.UiExecutor;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.fragment.PoiDetailPageNew;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;
import com.autonavi.vcs.NativeVcsManager;
import com.uc.webview.export.internal.SDKFactory;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bwu reason: default package */
/* compiled from: MitVoiceSearchModel */
public class bwu extends bgd {
    InfoliteParam a;
    private bfd b = new bfd() {
        public final JSONObject a() {
            bwu bwu = bwu.this;
            bwu.b();
            return bbv.a(bwu.a);
        }
    };

    public String a() {
        return "searchPoi";
    }

    public bwu() {
        bfe bfe = d.a;
        NativeVcsManager.getInstance().setUniversalData(this.b);
    }

    public final boolean a(final bgb bgb, bfb bfb) {
        Object a2 = bfi.a();
        boolean z = false;
        if (a2 == null) {
            return false;
        }
        long j = 0;
        if (a2 instanceof bgm) {
            j = ((bgm) a2).getScenesID();
        } else if (a2 instanceof bfk) {
            j = ((bfk) a2).h();
        }
        if (!a().equals(bgb.d)) {
            return false;
        }
        JSONObject a3 = a(j, bgb);
        bck bck = (bck) a.a.a(bck.class);
        if (a3 == null || bck == null) {
            bfq bfq = i.a;
            StringBuilder sb = new StringBuilder("cmd解析错误 taskId=");
            sb.append(bgb.f);
            bfp.a(bfq, 1, sb.toString());
        } else {
            bck.a();
            bcl bcl = new bcl();
            bcl.a = SearchFor.DEFAULT;
            bcl.c = bgb.c;
            bcl.b = 0;
            bcl.i = false;
            bcl.j = false;
            bcf a4 = bck.a();
            bvt bvt = new bvt(bcl);
            String str = bgb.c;
            if (this.a == null) {
                b();
            }
            this.a.keywords = str;
            this.a.need_utd = true;
            z = a4.a(bvt, a3, this.a);
        }
        if (z) {
            AMapPageUtil.getPageContext();
            UiExecutor.post(new Runnable() {
                public final void run() {
                    d.a.a(bgb.a, 10000, (String) null);
                }
            });
        } else {
            d.a.a(bgb.a, (int) SDKFactory.getCoreType, (String) null);
            bfq bfq2 = i.a;
            StringBuilder sb2 = new StringBuilder("核心参数有误 taskId=");
            sb2.append(bgb.f);
            bfp.a(bfq2, 1, sb2.toString());
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        bty mapView = DoNotUseTool.getMapView();
        Rect rect = new Rect();
        if (mapView != null) {
            rect = mapView.H();
            GLGeoPoint n = mapView.n();
            bid pageContext = AMapPageUtil.getPageContext();
            if (n != null && (pageContext instanceof PoiDetailPageNew)) {
                rect.bottom = n.y + (n.y - rect.top);
            }
        }
        this.a = bbv.a(AppManager.getInstance().getUserLocInfo(), (String) null, rect);
        this.a.citysuggestion = false;
        this.a.need_utd = false;
    }

    public static JSONObject a(long j, bgb bgb) {
        JSONObject jSONObject;
        if (bgb == null || j != bgb.e) {
            bfp.a(i.a, 1, "cmd空,场景不匹配 taskId=".concat(String.valueOf(bgb == null ? "" : bgb.f)));
            return null;
        }
        try {
            jSONObject = new JSONObject(new JSONObject(bgb.b).getJSONObject("voiceCommandResponse").getString("voiceResult"));
        } catch (JSONException e) {
            bfq bfq = i.a;
            StringBuilder sb = new StringBuilder("语音串解析异常 taskId=");
            sb.append(bgb.f);
            sb.append(" msg=");
            sb.append(e.getMessage());
            bfp.a(bfq, 1, sb.toString());
            jSONObject = null;
        }
        return jSONObject;
    }
}
