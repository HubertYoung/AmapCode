package defpackage;

import android.graphics.Rect;
import com.alipay.mobile.beehive.capture.utils.AudioUtils;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.life.api.entity.ScenicGuideEntity;
import com.autonavi.bundle.life.api.entity.ScenicGuideListItemEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayRouteItemEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayRoutePoiItemEntity;
import com.autonavi.bundle.life.api.entity.ScenicSpeakEntity;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.life.sketchscenic.ScenicEntranceEntity;
import com.autonavi.minimap.life.sketchscenic.ScenicEntranceRequest$1;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.minimap.scenic.ScenicMainPageRecRequestHolder;
import com.autonavi.minimap.scenic.param.ScenicMainPageRecRequest;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dqs reason: default package */
/* compiled from: ScenicEntranceRequest */
public final class dqs {
    ScenicMainPageRecRequest a;
    volatile boolean b = false;

    /* renamed from: dqs$a */
    /* compiled from: ScenicEntranceRequest */
    public interface a {
        void a(ScenicEntranceEntity scenicEntranceEntity, ScenicGuideEntity scenicGuideEntity, ScenicSpeakEntity scenicSpeakEntity, ScenicPlayEntity scenicPlayEntity);
    }

    public final void a(int i, String str, a aVar) {
        this.a = new ScenicMainPageRecRequest();
        this.a.b = String.valueOf(i);
        this.a.c = str;
        this.a.e = "scenic_guide,scenic";
        this.a.f = a();
        ScenicMainPageRecRequest scenicMainPageRecRequest = this.a;
        StringBuilder sb = new StringBuilder();
        sb.append(LocationInstrument.getInstance().getLatestPosition().getLongitude());
        sb.append(",");
        sb.append(LocationInstrument.getInstance().getLatestPosition().getLatitude());
        scenicMainPageRecRequest.g = sb.toString();
        StringBuilder sb2 = new StringBuilder("native param level: ");
        sb2.append(i);
        sb2.append("    poiid: ");
        sb2.append(str);
        sb2.append("   geoobj: ");
        sb2.append(a());
        sb2.append("    user_loc: ");
        sb2.append(this.a.g);
        AMapLog.debug("infoservice.scenic", "hanyang", sb2.toString());
        ScenicMainPageRecRequestHolder.getInstance().sendScenicMainPageRec(this.a, new ScenicEntranceRequest$1(this, aVar));
    }

    private static String a() {
        StringBuilder sb = new StringBuilder();
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (mapView == null) {
            return sb.toString();
        }
        Rect H = mapView.H();
        DPoint a2 = cfg.a((long) H.left, (long) H.top);
        DPoint a3 = cfg.a((long) H.right, (long) H.bottom);
        sb.append(a2.x);
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(a2.y);
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(a3.x);
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(a3.y);
        return sb.toString();
    }

    public static /* synthetic */ ScenicGuideEntity a(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        ScenicGuideEntity scenicGuideEntity = new ScenicGuideEntity();
        scenicGuideEntity.a = jSONObject.optInt("effective_level");
        JSONArray optJSONArray = jSONObject.optJSONArray("list");
        if (optJSONArray == null || optJSONArray.length() == 0) {
            return scenicGuideEntity;
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject jSONObject2 = (JSONObject) optJSONArray.get(i);
            ScenicGuideListItemEntity scenicGuideListItemEntity = new ScenicGuideListItemEntity();
            scenicGuideListItemEntity.a = jSONObject2.optString("name");
            scenicGuideListItemEntity.b = jSONObject2.optString("icon_type");
            scenicGuideListItemEntity.c = jSONObject2.optString("widget_type");
            scenicGuideListItemEntity.d = jSONObject2.optString("back_args");
            scenicGuideListItemEntity.e = jSONObject2.optString(ActionConstant.SCHEMA);
            scenicGuideEntity.b.add(scenicGuideListItemEntity);
        }
        return scenicGuideEntity;
    }

    public static /* synthetic */ ScenicPlayEntity b(JSONObject jSONObject) throws JSONException {
        if (jSONObject == null) {
            return null;
        }
        JSONObject optJSONObject = jSONObject.optJSONObject(AudioUtils.CMDPLAY);
        ScenicPlayEntity scenicPlayEntity = new ScenicPlayEntity();
        if (optJSONObject != null) {
            scenicPlayEntity.a = optJSONObject.optString("name");
            scenicPlayEntity.b = optJSONObject.optString("icon_type");
            scenicPlayEntity.c = optJSONObject.optString("widget_type");
            scenicPlayEntity.d = optJSONObject.optInt("bubble_duration");
            scenicPlayEntity.e = optJSONObject.optString("c_title");
            scenicPlayEntity.f = optJSONObject.optString(ActionConstant.SCHEMA);
            scenicPlayEntity.g = optJSONObject.optBoolean("in_aoi");
            JSONArray optJSONArray = optJSONObject.optJSONArray(AutoConstants.AUTO_FILE_ROUTE);
            if (!(optJSONArray == null || optJSONArray.length() == 0)) {
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject2 = (JSONObject) optJSONArray.get(i);
                    ScenicPlayRouteItemEntity scenicPlayRouteItemEntity = new ScenicPlayRouteItemEntity();
                    scenicPlayRouteItemEntity.a = jSONObject2.optInt("play_id");
                    scenicPlayRouteItemEntity.b = jSONObject2.optString(ActionConstant.SCHEMA);
                    scenicPlayRouteItemEntity.c = jSONObject2.optString("play_theme");
                    scenicPlayRouteItemEntity.d = jSONObject2.optInt("play_dis");
                    scenicPlayRouteItemEntity.e = jSONObject2.optString("play_spc");
                    scenicPlayRouteItemEntity.f = jSONObject2.optString("route_coords");
                    JSONArray optJSONArray2 = jSONObject2.optJSONArray("spots");
                    if (!(optJSONArray2 == null || optJSONArray2.length() == 0)) {
                        for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                            JSONObject jSONObject3 = (JSONObject) optJSONArray2.get(i2);
                            ScenicPlayRoutePoiItemEntity scenicPlayRoutePoiItemEntity = new ScenicPlayRoutePoiItemEntity();
                            scenicPlayRoutePoiItemEntity.a = jSONObject3.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                            scenicPlayRoutePoiItemEntity.b = jSONObject3.optString("lat");
                            scenicPlayRoutePoiItemEntity.c = jSONObject3.optString(LocationParams.PARA_FLP_AUTONAVI_LON);
                            scenicPlayRoutePoiItemEntity.d = jSONObject3.optString("name");
                            scenicPlayRoutePoiItemEntity.e = jSONObject3.optInt("s_mode");
                            scenicPlayRoutePoiItemEntity.f = jSONObject3.optInt("spot_dis");
                            scenicPlayRoutePoiItemEntity.g = jSONObject3.optString("render_style_main");
                            scenicPlayRoutePoiItemEntity.h = jSONObject3.optString("render_style_sub");
                            scenicPlayRoutePoiItemEntity.i = jSONObject3.optString("render_rank");
                            scenicPlayRoutePoiItemEntity.j = jSONObject3.optString("minizoom");
                            scenicPlayRoutePoiItemEntity.k = jSONObject3.optString("anchor");
                            scenicPlayRoutePoiItemEntity.l = jSONObject3.optString("foucesMainKey");
                            scenicPlayRoutePoiItemEntity.m = jSONObject3.optString("foucesSubKey");
                            scenicPlayRouteItemEntity.g.add(scenicPlayRoutePoiItemEntity);
                        }
                    }
                    scenicPlayEntity.h.add(scenicPlayRouteItemEntity);
                }
            }
        }
        return scenicPlayEntity;
    }
}
