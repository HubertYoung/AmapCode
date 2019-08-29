package defpackage;

import android.graphics.Point;
import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.action.ActionConstant;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.CpData;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.external.CitySuggestion;
import com.autonavi.bundle.entity.infolite.internal.Condition;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.feedback.ajx.ModuleFeedBack;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xidea.el.json.JSONEncoder;

@SuppressFBWarnings({"IS2_INCONSISTENT_SYNC"})
/* renamed from: els reason: default package */
/* compiled from: InfoliteOnlineParser */
public final class els {
    public InfoliteResult a;
    public InfoliteParam b;

    /* renamed from: els$a */
    /* compiled from: InfoliteOnlineParser */
    public static class a {
        /* access modifiers changed from: private */
        public static final els a = new els(0);
    }

    /* synthetic */ els(byte b2) {
        this();
    }

    private els() {
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:104:0x0310 */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x036c  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x039e  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x03d0  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0418  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean a(org.json.JSONObject r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            r0 = 0
            if (r11 != 0) goto L_0x0006
            monitor-exit(r10)
            return r0
        L_0x0006:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = new com.autonavi.bundle.entity.infolite.internal.InfoliteResult     // Catch:{ all -> 0x04b7 }
            r1.<init>()     // Catch:{ all -> 0x04b7 }
            r10.a = r1     // Catch:{ all -> 0x04b7 }
            com.autonavi.bl.search.InfoliteParam r1 = r10.b     // Catch:{ all -> 0x04b7 }
            if (r1 == 0) goto L_0x001b
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            com.autonavi.bl.search.InfoliteParam r2 = r10.b     // Catch:{ all -> 0x04b7 }
            com.autonavi.bl.search.InfoliteParam r2 = r2.clone()     // Catch:{ all -> 0x04b7 }
            r1.mWrapper = r2     // Catch:{ all -> 0x04b7 }
        L_0x001b:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            auq r1 = r1.responseHeader     // Catch:{ all -> 0x04b7 }
            if (r1 != 0) goto L_0x002a
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            auq r2 = new auq     // Catch:{ all -> 0x04b7 }
            r2.<init>()     // Catch:{ all -> 0x04b7 }
            r1.responseHeader = r2     // Catch:{ all -> 0x04b7 }
        L_0x002a:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            auq r1 = r1.responseHeader     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "version"
            java.lang.String r2 = r11.optString(r2)     // Catch:{ all -> 0x04b7 }
            r1.a = r2     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            auq r1 = r1.responseHeader     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "result"
            boolean r2 = r11.optBoolean(r2)     // Catch:{ all -> 0x04b7 }
            r1.b = r2     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            auq r1 = r1.responseHeader     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "code"
            int r2 = r11.optInt(r2)     // Catch:{ all -> 0x04b7 }
            r1.c = r2     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            auq r1 = r1.responseHeader     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "message"
            java.lang.String r2 = r11.optString(r2)     // Catch:{ all -> 0x04b7 }
            r1.d = r2     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            auq r1 = r1.responseHeader     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "timestamp"
            long r2 = r11.optLong(r2)     // Catch:{ all -> 0x04b7 }
            r1.e = r2     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r1 = r1.searchInfo     // Catch:{ all -> 0x04b7 }
            if (r1 != 0) goto L_0x0075
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = new aus     // Catch:{ all -> 0x04b7 }
            r2.<init>()     // Catch:{ all -> 0x04b7 }
            r1.searchInfo = r2     // Catch:{ all -> 0x04b7 }
        L_0x0075:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r1 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r1 = r1.searchInfo     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "codepoint"
            int r2 = r11.optInt(r2)     // Catch:{ all -> 0x04b7 }
            r1.u = r2     // Catch:{ all -> 0x04b7 }
            java.lang.String r1 = "lqii"
            org.json.JSONObject r1 = r11.optJSONObject(r1)     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            if (r2 != 0) goto L_0x009a
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r3 = new auk     // Catch:{ all -> 0x04b7 }
            r3.<init>()     // Catch:{ all -> 0x04b7 }
            r2.a = r3     // Catch:{ all -> 0x04b7 }
        L_0x009a:
            if (r1 == 0) goto L_0x00b2
            java.lang.String r2 = "gsid"
            boolean r2 = r1.has(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x00b2
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "gsid"
            java.lang.String r3 = r1.optString(r3)     // Catch:{ all -> 0x04b7 }
            r2.R = r3     // Catch:{ all -> 0x04b7 }
        L_0x00b2:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "call_taxi"
            int r3 = a(r1, r3)     // Catch:{ all -> 0x04b7 }
            r2.k = r3     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            int r2 = r2.k     // Catch:{ all -> 0x04b7 }
            r3 = 1
            if (r3 != r2) goto L_0x00cd
            monitor-exit(r10)
            return r3
        L_0x00cd:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "is_general_search"
            int r4 = r11.optInt(r4)     // Catch:{ all -> 0x04b7 }
            r2.v = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "general_flag"
            int r4 = r11.optInt(r4)     // Catch:{ all -> 0x04b7 }
            r2.w = r4     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "general_flag"
            boolean r2 = r11.has(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x00f3
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            r2.x = r3     // Catch:{ all -> 0x04b7 }
        L_0x00f3:
            java.lang.String r2 = "total"
            boolean r2 = r11.has(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x010f
            java.lang.String r2 = "total"
            java.lang.Object r2 = r11.opt(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x010f
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ NumberFormatException -> 0x010f }
            aus r2 = r2.searchInfo     // Catch:{ NumberFormatException -> 0x010f }
            java.lang.String r4 = "total"
            int r4 = r11.optInt(r4)     // Catch:{ NumberFormatException -> 0x010f }
            r2.p = r4     // Catch:{ NumberFormatException -> 0x010f }
        L_0x010f:
            if (r1 == 0) goto L_0x0450
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "self_navigation"
            int r4 = r1.optInt(r4)     // Catch:{ all -> 0x04b7 }
            r2.z = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "preload_next_page"
            int r4 = r1.optInt(r4)     // Catch:{ all -> 0x04b7 }
            r2.A = r4     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "recommend_info"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x018b
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "display_msg"
            int r5 = r2.optInt(r5)     // Catch:{ all -> 0x04b7 }
            r4.C = r5     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "city"
            org.json.JSONObject r4 = r2.optJSONObject(r4)     // Catch:{ all -> 0x04b7 }
            if (r4 == 0) goto L_0x0167
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r5 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r5 = r5.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r5 = r5.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r6 = "current_adcode"
            java.lang.String r6 = r4.optString(r6)     // Catch:{ all -> 0x04b7 }
            r5.D = r6     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r5 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r5 = r5.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r5 = r5.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r6 = "current_city"
            java.lang.String r4 = r4.optString(r6)     // Catch:{ all -> 0x04b7 }
            r5.E = r4     // Catch:{ all -> 0x04b7 }
        L_0x0167:
            java.lang.String r4 = "msg"
            org.json.JSONObject r2 = r2.optJSONObject(r4)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x018b
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "msg_id"
            java.lang.String r5 = r2.optString(r5)     // Catch:{ all -> 0x04b7 }
            r4.F = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "msg_content"
            java.lang.String r2 = r2.optString(r5)     // Catch:{ all -> 0x04b7 }
            r4.G = r2     // Catch:{ all -> 0x04b7 }
        L_0x018b:
            java.lang.String r2 = "no_result_suggest"
            boolean r2 = r1.has(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x01c6
            java.lang.String r2 = "no_result_suggest"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ JSONException -> 0x01c6 }
            if (r2 == 0) goto L_0x01c6
            java.lang.String r4 = ""
            boolean r4 = r2.equals(r4)     // Catch:{ JSONException -> 0x01c6 }
            if (r4 != 0) goto L_0x01c6
            java.lang.String r4 = "+++"
            int r4 = r2.indexOf(r4)     // Catch:{ JSONException -> 0x01c6 }
            if (r4 <= 0) goto L_0x01ba
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ JSONException -> 0x01c6 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x01c6 }
            auk r4 = r4.a     // Catch:{ JSONException -> 0x01c6 }
            java.lang.String r5 = "\\+{3}"
            java.lang.String[] r2 = r2.split(r5)     // Catch:{ JSONException -> 0x01c6 }
            r4.B = r2     // Catch:{ JSONException -> 0x01c6 }
            goto L_0x01c6
        L_0x01ba:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ JSONException -> 0x01c6 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x01c6 }
            auk r4 = r4.a     // Catch:{ JSONException -> 0x01c6 }
            java.lang.String[] r5 = new java.lang.String[r3]     // Catch:{ JSONException -> 0x01c6 }
            r5[r0] = r2     // Catch:{ JSONException -> 0x01c6 }
            r4.B = r5     // Catch:{ JSONException -> 0x01c6 }
        L_0x01c6:
            java.lang.String r2 = "directjump"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x01e4
            java.lang.String r4 = "url"
            java.lang.String r2 = r2.optString(r4)     // Catch:{ all -> 0x04b7 }
            boolean r4 = android.text.TextUtils.isEmpty(r2)     // Catch:{ all -> 0x04b7 }
            if (r4 != 0) goto L_0x01e4
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r11 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r11 = r11.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r11 = r11.a     // Catch:{ all -> 0x04b7 }
            r11.q = r2     // Catch:{ all -> 0x04b7 }
            monitor-exit(r10)
            return r3
        L_0x01e4:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            com.autonavi.bl.search.InfoliteParam r2 = r2.mWrapper     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x0204
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            com.autonavi.bl.search.InfoliteParam r2 = r2.mWrapper     // Catch:{ all -> 0x04b7 }
            int r2 = r2.pagenum     // Catch:{ all -> 0x04b7 }
            if (r2 != r3) goto L_0x0204
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "view_region"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ all -> 0x04b7 }
            java.lang.Double[] r4 = defpackage.bcx.a(r4)     // Catch:{ all -> 0x04b7 }
            r2.u = r4     // Catch:{ all -> 0x04b7 }
        L_0x0204:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            com.autonavi.bl.search.InfoliteParam r2 = r2.mWrapper     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x023c
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            com.autonavi.bl.search.InfoliteParam r2 = r2.mWrapper     // Catch:{ all -> 0x04b7 }
            int r2 = r2.pagenum     // Catch:{ all -> 0x04b7 }
            if (r2 != r3) goto L_0x023c
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "expand_range_tip"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ all -> 0x04b7 }
            r2.d = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "change_query_tip"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ all -> 0x04b7 }
            r2.f = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "change_query_type"
            int r4 = r1.optInt(r4)     // Catch:{ all -> 0x04b7 }
            r2.e = r4     // Catch:{ all -> 0x04b7 }
        L_0x023c:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "suggestionview"
            int r4 = a(r1, r4)     // Catch:{ all -> 0x04b7 }
            r2.b = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "pdheatmap"
            int r4 = r1.optInt(r4, r0)     // Catch:{ all -> 0x04b7 }
            r2.c = r4     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "cache_directive"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x029b
            java.lang.String r4 = "cache_all"
            org.json.JSONObject r2 = r2.optJSONObject(r4)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x028a
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "flag"
            int r5 = r2.optInt(r5)     // Catch:{ all -> 0x04b7 }
            if (r5 != r3) goto L_0x0278
            r5 = 1
            goto L_0x0279
        L_0x0278:
            r5 = 0
        L_0x0279:
            r4.g = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "expires"
            int r2 = r2.optInt(r5)     // Catch:{ all -> 0x04b7 }
            r4.h = r2     // Catch:{ all -> 0x04b7 }
            goto L_0x02ab
        L_0x028a:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            r2.g = r0     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            r2.h = r0     // Catch:{ all -> 0x04b7 }
            goto L_0x02ab
        L_0x029b:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            r2.g = r0     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            r2.h = r0     // Catch:{ all -> 0x04b7 }
        L_0x02ab:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "has_recommend"
            int r4 = a(r1, r4)     // Catch:{ all -> 0x04b7 }
            r2.n = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "brand"
            java.lang.String r5 = "slayer_type"
            java.lang.String r5 = r1.optString(r5)     // Catch:{ all -> 0x04b7 }
            boolean r4 = r4.equalsIgnoreCase(r5)     // Catch:{ all -> 0x04b7 }
            r2.l = r4     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "resource"
            boolean r2 = r1.has(r2)     // Catch:{ JSONException -> 0x0310 }
            if (r2 == 0) goto L_0x0310
            java.lang.String r2 = "resource"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ JSONException -> 0x0310 }
            if (r2 == 0) goto L_0x0310
            java.lang.String r2 = "resource"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ JSONException -> 0x0310 }
            java.lang.String r4 = "brand_url"
            org.json.JSONArray r2 = r2.optJSONArray(r4)     // Catch:{ JSONException -> 0x0310 }
            r4 = 0
        L_0x02ea:
            int r5 = r2.length()     // Catch:{ JSONException -> 0x0310 }
            if (r4 >= r5) goto L_0x0310
            java.lang.Object r5 = r2.get(r4)     // Catch:{ JSONException -> 0x0310 }
            org.json.JSONObject r5 = (org.json.JSONObject) r5     // Catch:{ JSONException -> 0x0310 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r10.a     // Catch:{ JSONException -> 0x0310 }
            aus r6 = r6.searchInfo     // Catch:{ JSONException -> 0x0310 }
            auk r6 = r6.a     // Catch:{ JSONException -> 0x0310 }
            java.util.HashMap<java.lang.String, java.lang.String> r6 = r6.m     // Catch:{ JSONException -> 0x0310 }
            java.lang.String r7 = "brand"
            java.lang.String r7 = r5.optString(r7)     // Catch:{ JSONException -> 0x0310 }
            java.lang.String r8 = "url"
            java.lang.String r5 = r5.optString(r8)     // Catch:{ JSONException -> 0x0310 }
            r6.put(r7, r5)     // Catch:{ JSONException -> 0x0310 }
            int r4 = r4 + 1
            goto L_0x02ea
        L_0x0310:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "render_name_flag"
            int r4 = a(r1, r4)     // Catch:{ all -> 0x04b7 }
            r2.o = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "querytype"
            int r4 = a(r1, r4)     // Catch:{ all -> 0x04b7 }
            r2.a = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "is_current_city"
            int r4 = a(r1, r4)     // Catch:{ all -> 0x04b7 }
            r2.p = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "is_view_city"
            int r4 = a(r1, r4)     // Catch:{ all -> 0x04b7 }
            r2.i = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "target_view_city"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ all -> 0x04b7 }
            r2.j = r4     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "rich_info_flag"
            boolean r4 = r1.optBoolean(r4, r0)     // Catch:{ all -> 0x04b7 }
            r2.V = r4     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "interior"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x0396
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "interior_noresult"
            int r5 = r2.optInt(r5)     // Catch:{ all -> 0x04b7 }
            r4.H = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "need_expand"
            int r5 = r2.optInt(r5)     // Catch:{ all -> 0x04b7 }
            r4.I = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "result_type"
            java.lang.String r2 = r2.optString(r5)     // Catch:{ all -> 0x04b7 }
            r4.J = r2     // Catch:{ all -> 0x04b7 }
        L_0x0396:
            java.lang.String r2 = "tesecai"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x03c8
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "type"
            java.lang.String r5 = r2.optString(r5)     // Catch:{ all -> 0x04b7 }
            r4.r = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "valid"
            int r5 = r2.optInt(r5)     // Catch:{ all -> 0x04b7 }
            r4.s = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "query"
            java.lang.String r2 = r2.optString(r5)     // Catch:{ all -> 0x04b7 }
            r4.t = r2     // Catch:{ all -> 0x04b7 }
        L_0x03c8:
            java.lang.String r2 = "suggesttips"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x0400
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "city"
            java.lang.String r6 = ""
            java.lang.String r5 = r2.optString(r5, r6)     // Catch:{ all -> 0x04b7 }
            r4.v = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "adcode"
            r6 = 0
            long r5 = r2.optLong(r5, r6)     // Catch:{ all -> 0x04b7 }
            r4.w = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "query"
            java.lang.String r6 = ""
            java.lang.String r2 = r2.optString(r5, r6)     // Catch:{ all -> 0x04b7 }
            r4.x = r2     // Catch:{ all -> 0x04b7 }
        L_0x0400:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "suggestcontent"
            java.lang.String r5 = ""
            java.lang.String r4 = r1.optString(r4, r5)     // Catch:{ all -> 0x04b7 }
            r2.y = r4     // Catch:{ all -> 0x04b7 }
            java.lang.String r2 = "query_intent"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x0442
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "type"
            int r5 = r2.optInt(r5)     // Catch:{ all -> 0x04b7 }
            r4.N = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "cate"
            java.lang.String r5 = r2.optString(r5)     // Catch:{ all -> 0x04b7 }
            r4.O = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r5 = "distribution"
            java.lang.String r2 = r2.optString(r5)     // Catch:{ all -> 0x04b7 }
            r4.P = r2     // Catch:{ all -> 0x04b7 }
        L_0x0442:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r2 = r2.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r2 = r2.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "filterbox_fillback"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ all -> 0x04b7 }
            r2.Q = r4     // Catch:{ all -> 0x04b7 }
        L_0x0450:
            if (r1 == 0) goto L_0x04ad
            java.lang.String r2 = "zoom"
            org.json.JSONObject r2 = r1.optJSONObject(r2)     // Catch:{ all -> 0x04b7 }
            if (r2 == 0) goto L_0x04ad
            java.lang.String r4 = "center"
            java.lang.String r4 = r2.optString(r4)     // Catch:{ all -> 0x04b7 }
            if (r4 == 0) goto L_0x04ad
            java.lang.String r5 = ","
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ all -> 0x04b7 }
            int r5 = r4.length     // Catch:{ all -> 0x04b7 }
            r6 = 2
            if (r5 != r6) goto L_0x04ad
            r5 = r4[r0]     // Catch:{ all -> 0x04b7 }
            if (r5 == 0) goto L_0x04ad
            r5 = r4[r3]     // Catch:{ all -> 0x04b7 }
            if (r5 != 0) goto L_0x0475
            goto L_0x04ad
        L_0x0475:
            java.lang.String r5 = "level"
            int r2 = r2.optInt(r5)     // Catch:{ all -> 0x04b7 }
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint     // Catch:{ all -> 0x04b7 }
            r6 = r4[r0]     // Catch:{ all -> 0x04b7 }
            double r6 = java.lang.Double.parseDouble(r6)     // Catch:{ all -> 0x04b7 }
            r4 = r4[r3]     // Catch:{ all -> 0x04b7 }
            double r8 = java.lang.Double.parseDouble(r4)     // Catch:{ all -> 0x04b7 }
            r5.<init>(r6, r8)     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            auv r6 = new auv     // Catch:{ all -> 0x04b7 }
            r6.<init>()     // Catch:{ all -> 0x04b7 }
            r4.M = r6     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            auv r4 = r4.M     // Catch:{ all -> 0x04b7 }
            r4.a = r5     // Catch:{ all -> 0x04b7 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ all -> 0x04b7 }
            aus r4 = r4.searchInfo     // Catch:{ all -> 0x04b7 }
            auk r4 = r4.a     // Catch:{ all -> 0x04b7 }
            auv r4 = r4.M     // Catch:{ all -> 0x04b7 }
            r4.b = r2     // Catch:{ all -> 0x04b7 }
        L_0x04ad:
            org.json.JSONArray r11 = r10.a(r11, r1)     // Catch:{ all -> 0x04b7 }
            if (r11 == 0) goto L_0x04b5
            monitor-exit(r10)
            return r3
        L_0x04b5:
            monitor-exit(r10)
            return r0
        L_0x04b7:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.els.a(org.json.JSONObject):boolean");
    }

    private void b(JSONObject jSONObject) {
        JSONArray optJSONArray = jSONObject.optJSONArray("category");
        if (optJSONArray != null && optJSONArray.length() > 0) {
            defpackage.aui.a[] aVarArr = new defpackage.aui.a[optJSONArray.length()];
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                aVarArr[i] = new defpackage.aui.a(optJSONObject.optString("name"), optJSONObject.optString("value"));
            }
            this.a.searchInfo.d = new aui(jSONObject.optString("checkedvalue"), jSONObject.optString("name"), jSONObject.optString("ctype"), aVarArr);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:106:0x02d9  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x02ed  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.json.JSONArray a(org.json.JSONObject r11, org.json.JSONObject r12) {
        /*
            r10 = this;
            r0 = 0
            if (r11 == 0) goto L_0x0363
            if (r12 != 0) goto L_0x0007
            goto L_0x0363
        L_0x0007:
            java.lang.String r1 = "poi_list"
            org.json.JSONArray r1 = r11.optJSONArray(r1)
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x00fc
            int r4 = r1.length()
            if (r4 == 0) goto L_0x00fc
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a
            com.autonavi.bl.search.InfoliteParam r4 = r4.mWrapper
            if (r4 == 0) goto L_0x00fc
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a
            com.autonavi.bl.search.InfoliteParam r4 = r4.mWrapper
            int r4 = r4.pagenum
            if (r4 != r3) goto L_0x00fc
            java.lang.String r4 = "classify"
            org.json.JSONArray r4 = r11.optJSONArray(r4)
            if (r4 == 0) goto L_0x00f9
            int r5 = r4.length()
            if (r5 != 0) goto L_0x0035
            goto L_0x00f9
        L_0x0035:
            org.json.JSONArray r5 = new org.json.JSONArray
            r5.<init>()
            r6 = r0
            r0 = 0
        L_0x003c:
            int r7 = r4.length()
            if (r0 >= r7) goto L_0x005e
            org.json.JSONObject r7 = r4.optJSONObject(r0)
            if (r7 == 0) goto L_0x005b
            java.lang.String r8 = "label"
            java.lang.String r9 = "ctype"
            java.lang.String r9 = r7.optString(r9)
            boolean r8 = r8.equals(r9)
            if (r8 == 0) goto L_0x0058
            r6 = r7
            goto L_0x005b
        L_0x0058:
            r5.put(r7)
        L_0x005b:
            int r0 = r0 + 1
            goto L_0x003c
        L_0x005e:
            if (r6 == 0) goto L_0x0063
            r10.b(r6)
        L_0x0063:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            java.lang.String r4 = "classify"
            r0.put(r4, r5)     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ JSONException -> 0x00f5 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.Condition r4 = r4.b     // Catch:{ JSONException -> 0x00f5 }
            if (r4 != 0) goto L_0x0080
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ JSONException -> 0x00f5 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.Condition r5 = new com.autonavi.bundle.entity.infolite.internal.Condition     // Catch:{ JSONException -> 0x00f5 }
            r5.<init>()     // Catch:{ JSONException -> 0x00f5 }
            r4.b = r5     // Catch:{ JSONException -> 0x00f5 }
        L_0x0080:
            java.lang.String r4 = "classify_range"
            org.json.JSONObject r12 = r12.optJSONObject(r4)     // Catch:{ JSONException -> 0x00f5 }
            if (r12 == 0) goto L_0x00d7
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ JSONException -> 0x00f5 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.Condition r4 = r4.b     // Catch:{ JSONException -> 0x00f5 }
            java.lang.String r5 = "district_adcode"
            java.lang.String r6 = ""
            java.lang.String r5 = r12.optString(r5, r6)     // Catch:{ JSONException -> 0x00f5 }
            r4.setDistrictAdcode(r5)     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ JSONException -> 0x00f5 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.Condition r4 = r4.b     // Catch:{ JSONException -> 0x00f5 }
            java.lang.String r5 = "district_enable"
            int r5 = r12.optInt(r5, r2)     // Catch:{ JSONException -> 0x00f5 }
            r4.setDistrictEnable(r5)     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ JSONException -> 0x00f5 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.Condition r4 = r4.b     // Catch:{ JSONException -> 0x00f5 }
            java.lang.String r5 = "nearby_enable"
            int r5 = r12.optInt(r5, r2)     // Catch:{ JSONException -> 0x00f5 }
            r4.setNearbyEnable(r5)     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ JSONException -> 0x00f5 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.Condition r4 = r4.b     // Catch:{ JSONException -> 0x00f5 }
            java.lang.String r5 = "subway_adcode"
            java.lang.String r6 = ""
            java.lang.String r5 = r12.optString(r5, r6)     // Catch:{ JSONException -> 0x00f5 }
            r4.setSubwayAdcode(r5)     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r10.a     // Catch:{ JSONException -> 0x00f5 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.Condition r4 = r4.b     // Catch:{ JSONException -> 0x00f5 }
            java.lang.String r5 = "subway_enable"
            int r12 = r12.optInt(r5, r2)     // Catch:{ JSONException -> 0x00f5 }
            r4.setSubwayEnable(r12)     // Catch:{ JSONException -> 0x00f5 }
        L_0x00d7:
            java.lang.String r12 = "classify"
            boolean r12 = r0.has(r12)     // Catch:{ JSONException -> 0x00f5 }
            if (r12 == 0) goto L_0x00eb
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ JSONException -> 0x00f5 }
            aus r12 = r12.searchInfo     // Catch:{ JSONException -> 0x00f5 }
            java.lang.String r4 = "classify"
            java.lang.String r4 = r0.optString(r4)     // Catch:{ JSONException -> 0x00f5 }
            r12.c = r4     // Catch:{ JSONException -> 0x00f5 }
        L_0x00eb:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ JSONException -> 0x00f5 }
            aus r12 = r12.searchInfo     // Catch:{ JSONException -> 0x00f5 }
            com.autonavi.bundle.entity.infolite.internal.Condition r12 = r12.b     // Catch:{ JSONException -> 0x00f5 }
            r12.parseConditions(r0)     // Catch:{ JSONException -> 0x00f5 }
            goto L_0x00f9
        L_0x00f5:
            r12 = move-exception
            r12.printStackTrace()
        L_0x00f9:
            r10.c(r11)
        L_0x00fc:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            auk r12 = r12.a
            int r12 = r12.a
            switch(r12) {
                case 1: goto L_0x02a5;
                case 2: goto L_0x0251;
                case 3: goto L_0x0251;
                case 4: goto L_0x0132;
                case 5: goto L_0x0251;
                default: goto L_0x0107;
            }
        L_0x0107:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r12 = r12.l
            if (r12 != 0) goto L_0x0340
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r12.l = r0
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r12.g = r0
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r12.f = r0
            goto L_0x0340
        L_0x0132:
            java.lang.String r12 = "routing"
            org.json.JSONObject r11 = r11.optJSONObject(r12)
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aur r12 = r12.routingInfo
            if (r12 != 0) goto L_0x0147
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aur r0 = new aur
            r0.<init>()
            r12.routingInfo = r0
        L_0x0147:
            if (r11 == 0) goto L_0x0362
            java.lang.String r12 = "start"
            org.json.JSONObject r12 = r11.optJSONObject(r12)     // Catch:{ Exception -> 0x024b }
            if (r12 != 0) goto L_0x0153
            goto L_0x0362
        L_0x0153:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            if (r0 != 0) goto L_0x0162
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r2 = new aur     // Catch:{ Exception -> 0x024b }
            r2.<init>()     // Catch:{ Exception -> 0x024b }
            r0.routingInfo = r2     // Catch:{ Exception -> 0x024b }
        L_0x0162:
            r10.a(r12, r3, r3)     // Catch:{ Exception -> 0x024b }
            java.lang.String r0 = "keywordres"
            org.json.JSONObject r12 = r12.optJSONObject(r0)     // Catch:{ Exception -> 0x024b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<com.autonavi.common.model.POI> r0 = r0.c     // Catch:{ Exception -> 0x024b }
            if (r0 != 0) goto L_0x017e
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Exception -> 0x024b }
            r2.<init>()     // Catch:{ Exception -> 0x024b }
            r0.c = r2     // Catch:{ Exception -> 0x024b }
        L_0x017e:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r0 = r0.e     // Catch:{ Exception -> 0x024b }
            if (r0 != 0) goto L_0x0191
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Exception -> 0x024b }
            r2.<init>()     // Catch:{ Exception -> 0x024b }
            r0.e = r2     // Catch:{ Exception -> 0x024b }
        L_0x0191:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<java.lang.String> r0 = r0.d     // Catch:{ Exception -> 0x024b }
            if (r0 != 0) goto L_0x01a4
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Exception -> 0x024b }
            r2.<init>()     // Catch:{ Exception -> 0x024b }
            r0.d = r2     // Catch:{ Exception -> 0x024b }
        L_0x01a4:
            if (r12 == 0) goto L_0x01d0
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.lang.String r2 = "keywords"
            java.lang.String r2 = r12.optString(r2)     // Catch:{ Exception -> 0x024b }
            r0.a = r2     // Catch:{ Exception -> 0x024b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<java.lang.String> r0 = r0.d     // Catch:{ Exception -> 0x024b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r2 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r2 = r2.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r2 = r2.e     // Catch:{ Exception -> 0x024b }
            a(r12, r0, r2)     // Catch:{ Exception -> 0x024b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<com.autonavi.common.model.POI> r0 = r0.c     // Catch:{ Exception -> 0x024b }
            java.lang.String r2 = "poi_list"
            org.json.JSONArray r12 = r12.optJSONArray(r2)     // Catch:{ Exception -> 0x024b }
            r10.a(r0, r12)     // Catch:{ Exception -> 0x024b }
        L_0x01d0:
            java.lang.String r12 = "end"
            org.json.JSONObject r11 = r11.optJSONObject(r12)     // Catch:{ Exception -> 0x024b }
            if (r11 != 0) goto L_0x01da
            goto L_0x0362
        L_0x01da:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r12 = r12.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<com.autonavi.common.model.POI> r12 = r12.h     // Catch:{ Exception -> 0x024b }
            if (r12 != 0) goto L_0x01ed
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r12 = r12.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x024b }
            r0.<init>()     // Catch:{ Exception -> 0x024b }
            r12.h = r0     // Catch:{ Exception -> 0x024b }
        L_0x01ed:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r12 = r12.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r12 = r12.j     // Catch:{ Exception -> 0x024b }
            if (r12 != 0) goto L_0x0200
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r12 = r12.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x024b }
            r0.<init>()     // Catch:{ Exception -> 0x024b }
            r12.j = r0     // Catch:{ Exception -> 0x024b }
        L_0x0200:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r12 = r12.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<java.lang.String> r12 = r12.i     // Catch:{ Exception -> 0x024b }
            if (r12 != 0) goto L_0x0213
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r12 = r12.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x024b }
            r0.<init>()     // Catch:{ Exception -> 0x024b }
            r12.i = r0     // Catch:{ Exception -> 0x024b }
        L_0x0213:
            r12 = 2
            r10.a(r11, r3, r12)     // Catch:{ Exception -> 0x024b }
            java.lang.String r12 = "keywordres"
            org.json.JSONObject r11 = r11.optJSONObject(r12)     // Catch:{ Exception -> 0x024b }
            if (r11 == 0) goto L_0x0362
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r12 = r12.routingInfo     // Catch:{ Exception -> 0x024b }
            java.lang.String r0 = "keywords"
            java.lang.String r0 = r11.optString(r0)     // Catch:{ Exception -> 0x024b }
            r12.g = r0     // Catch:{ Exception -> 0x024b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r12 = r12.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<java.lang.String> r12 = r12.i     // Catch:{ Exception -> 0x024b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r0 = r0.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r0 = r0.j     // Catch:{ Exception -> 0x024b }
            a(r11, r12, r0)     // Catch:{ Exception -> 0x024b }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a     // Catch:{ Exception -> 0x024b }
            aur r12 = r12.routingInfo     // Catch:{ Exception -> 0x024b }
            java.util.ArrayList<com.autonavi.common.model.POI> r12 = r12.h     // Catch:{ Exception -> 0x024b }
            java.lang.String r0 = "poi_list"
            org.json.JSONArray r11 = r11.optJSONArray(r0)     // Catch:{ Exception -> 0x024b }
            r10.a(r12, r11)     // Catch:{ Exception -> 0x024b }
            goto L_0x0362
        L_0x024b:
            r11 = move-exception
            defpackage.kf.a(r11)
            goto L_0x0362
        L_0x0251:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r12 = r12.l
            if (r12 != 0) goto L_0x027a
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r12.l = r0
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r12.g = r0
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r12.f = r0
        L_0x027a:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList<java.lang.String> r12 = r12.f
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a
            aus r0 = r0.searchInfo
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r0 = r0.g
            a(r11, r12, r0)
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r12 = r12.l
            r10.a(r12, r1)
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            a(r12, r1)
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            b(r12, r1)
            r10.d(r11)
            goto L_0x0362
        L_0x02a5:
            if (r1 == 0) goto L_0x02d6
            int r12 = r1.length()
            if (r12 <= 0) goto L_0x02d6
            int r12 = r1.length()
            r0 = 0
        L_0x02b2:
            if (r0 > r12) goto L_0x02d6
            org.json.JSONObject r4 = r1.getJSONObject(r0)     // Catch:{ JSONException -> 0x02cf }
            java.lang.String r5 = "item_type"
            boolean r5 = r4.has(r5)     // Catch:{ JSONException -> 0x02cf }
            if (r5 == 0) goto L_0x02d3
            java.lang.String r5 = "citycard"
            java.lang.String r6 = "item_type"
            java.lang.String r4 = r4.optString(r6)     // Catch:{ JSONException -> 0x02cf }
            boolean r4 = r5.equals(r4)     // Catch:{ JSONException -> 0x02cf }
            if (r4 == 0) goto L_0x02d3
            goto L_0x02d7
        L_0x02cf:
            r4 = move-exception
            r4.printStackTrace()
        L_0x02d3:
            int r0 = r0 + 1
            goto L_0x02b2
        L_0x02d6:
            r3 = 0
        L_0x02d7:
            if (r3 != 0) goto L_0x02ed
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            auj r12 = r12.locationInfo
            if (r12 != 0) goto L_0x02e8
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            auj r0 = new auj
            r0.<init>()
            r12.locationInfo = r0
        L_0x02e8:
            r12 = -1
            r10.a(r11, r2, r12)
            goto L_0x0362
        L_0x02ed:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r12 = r12.l
            if (r12 != 0) goto L_0x0316
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r12.l = r0
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r12.g = r0
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r12.f = r0
        L_0x0316:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList<java.lang.String> r12 = r12.f
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a
            aus r0 = r0.searchInfo
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r0 = r0.g
            a(r11, r12, r0)
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r12 = r12.l
            r10.a(r12, r1)
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            a(r12, r1)
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            b(r12, r1)
            r10.d(r11)
            goto L_0x0362
        L_0x0340:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList<java.lang.String> r12 = r12.f
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r0 = r10.a
            aus r0 = r0.searchInfo
            java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r0 = r0.g
            a(r11, r12, r0)
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r12 = r12.l
            r10.a(r12, r1)
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r12 = r10.a
            aus r12 = r12.searchInfo
            a(r12, r1)
            r10.d(r11)
        L_0x0362:
            return r1
        L_0x0363:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.els.a(org.json.JSONObject, org.json.JSONObject):org.json.JSONArray");
    }

    private void c(JSONObject jSONObject) {
        try {
            if (jSONObject.has("scenefilter")) {
                JSONArray jSONArray = jSONObject.getJSONArray("scenefilter");
                if (jSONArray.length() > 0) {
                    this.a.searchInfo.e = new ArrayList<>();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        if (jSONObject2.has("name") && jSONObject2.has("value")) {
                            Condition condition = new Condition();
                            condition.name = jSONObject2.getString("name");
                            condition.value = jSONObject2.getString("value");
                            this.a.searchInfo.e.add(condition);
                        }
                    }
                }
            }
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0073 A[Catch:{ Exception -> 0x018d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(org.json.JSONObject r18, boolean r19, int r20) {
        /*
            r17 = this;
            r1 = r17
            r3 = r20
            r4 = 0
            java.lang.String r5 = "locres"
            r6 = r18
            org.json.JSONObject r5 = r6.optJSONObject(r5)     // Catch:{ Exception -> 0x0190 }
            if (r5 != 0) goto L_0x0010
            return r4
        L_0x0010:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r1.a     // Catch:{ Exception -> 0x0190 }
            auj r6 = r6.locationInfo     // Catch:{ Exception -> 0x0190 }
            if (r6 != 0) goto L_0x001f
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r1.a     // Catch:{ Exception -> 0x0190 }
            auj r7 = new auj     // Catch:{ Exception -> 0x0190 }
            r7.<init>()     // Catch:{ Exception -> 0x0190 }
            r6.locationInfo = r7     // Catch:{ Exception -> 0x0190 }
        L_0x001f:
            r6 = 2
            r7 = 1
            if (r19 == 0) goto L_0x0041
            if (r3 != r7) goto L_0x0032
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r8 = r1.a     // Catch:{ Exception -> 0x0190 }
            aur r8 = r8.routingInfo     // Catch:{ Exception -> 0x0190 }
            java.lang.String r9 = "loctype"
            int r9 = r5.optInt(r9)     // Catch:{ Exception -> 0x0190 }
            r8.b = r9     // Catch:{ Exception -> 0x0190 }
            goto L_0x004d
        L_0x0032:
            if (r3 != r6) goto L_0x004d
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r8 = r1.a     // Catch:{ Exception -> 0x0190 }
            aur r8 = r8.routingInfo     // Catch:{ Exception -> 0x0190 }
            java.lang.String r9 = "loctype"
            int r9 = r5.optInt(r9)     // Catch:{ Exception -> 0x0190 }
            r8.f = r9     // Catch:{ Exception -> 0x0190 }
            goto L_0x004d
        L_0x0041:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r8 = r1.a     // Catch:{ Exception -> 0x0190 }
            auj r8 = r8.locationInfo     // Catch:{ Exception -> 0x0190 }
            java.lang.String r9 = "loctype"
            int r9 = r5.optInt(r9)     // Catch:{ Exception -> 0x0190 }
            r8.a = r9     // Catch:{ Exception -> 0x0190 }
        L_0x004d:
            java.lang.String r8 = "total"
            boolean r8 = r5.has(r8)     // Catch:{ Exception -> 0x0190 }
            if (r8 == 0) goto L_0x006a
            java.lang.String r8 = "total"
            java.lang.String r8 = r5.getString(r8)     // Catch:{ Exception -> 0x0190 }
            if (r8 == 0) goto L_0x006a
            java.lang.String r9 = ""
            boolean r9 = r8.equals(r9)     // Catch:{ Exception -> 0x0190 }
            if (r9 != 0) goto L_0x006a
            int r8 = java.lang.Integer.parseInt(r8)     // Catch:{ Exception -> 0x0190 }
            goto L_0x006b
        L_0x006a:
            r8 = 0
        L_0x006b:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            auj r9 = r9.locationInfo     // Catch:{ Exception -> 0x018d }
            int r9 = r9.a     // Catch:{ Exception -> 0x018d }
            if (r9 == r7) goto L_0x018c
            if (r8 > 0) goto L_0x0077
            goto L_0x018c
        L_0x0077:
            java.lang.String r9 = "poi_list"
            org.json.JSONArray r5 = r5.getJSONArray(r9)     // Catch:{ Exception -> 0x018d }
            if (r5 == 0) goto L_0x018b
            if (r19 == 0) goto L_0x00a1
            if (r3 != r7) goto L_0x00a1
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            aur r9 = r9.routingInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList<com.autonavi.common.model.POI> r9 = r9.c     // Catch:{ Exception -> 0x018d }
            if (r9 != 0) goto L_0x0097
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            aur r9 = r9.routingInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ Exception -> 0x018d }
            r10.<init>()     // Catch:{ Exception -> 0x018d }
            r9.c = r10     // Catch:{ Exception -> 0x018d }
            goto L_0x00e0
        L_0x0097:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            aur r9 = r9.routingInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList<com.autonavi.common.model.POI> r9 = r9.c     // Catch:{ Exception -> 0x018d }
            r9.clear()     // Catch:{ Exception -> 0x018d }
            goto L_0x00e0
        L_0x00a1:
            if (r19 == 0) goto L_0x00c3
            if (r3 != r6) goto L_0x00c3
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            aur r9 = r9.routingInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList<com.autonavi.common.model.POI> r9 = r9.h     // Catch:{ Exception -> 0x018d }
            if (r9 != 0) goto L_0x00b9
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            aur r9 = r9.routingInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ Exception -> 0x018d }
            r10.<init>()     // Catch:{ Exception -> 0x018d }
            r9.h = r10     // Catch:{ Exception -> 0x018d }
            goto L_0x00e0
        L_0x00b9:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            aur r9 = r9.routingInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList<com.autonavi.common.model.POI> r9 = r9.h     // Catch:{ Exception -> 0x018d }
            r9.clear()     // Catch:{ Exception -> 0x018d }
            goto L_0x00e0
        L_0x00c3:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            auj r9 = r9.locationInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList<com.autonavi.common.model.POI> r9 = r9.c     // Catch:{ Exception -> 0x018d }
            if (r9 != 0) goto L_0x00d7
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            auj r9 = r9.locationInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ Exception -> 0x018d }
            r10.<init>()     // Catch:{ Exception -> 0x018d }
            r9.c = r10     // Catch:{ Exception -> 0x018d }
            goto L_0x00e0
        L_0x00d7:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r9 = r1.a     // Catch:{ Exception -> 0x018d }
            auj r9 = r9.locationInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList<com.autonavi.common.model.POI> r9 = r9.c     // Catch:{ Exception -> 0x018d }
            r9.clear()     // Catch:{ Exception -> 0x018d }
        L_0x00e0:
            int r9 = r5.length()     // Catch:{ Exception -> 0x018d }
            if (r4 >= r9) goto L_0x018b
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r9 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r9 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r9)     // Catch:{ Exception -> 0x018d }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r9 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r9     // Catch:{ Exception -> 0x018d }
            java.lang.Object r10 = r5.get(r4)     // Catch:{ Exception -> 0x018d }
            org.json.JSONObject r10 = (org.json.JSONObject) r10     // Catch:{ Exception -> 0x018d }
            java.lang.String r11 = "longitude"
            r12 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            double r14 = r10.optDouble(r11, r12)     // Catch:{ Exception -> 0x018d }
            java.lang.String r11 = "latitude"
            double r6 = r10.optDouble(r11, r12)     // Catch:{ Exception -> 0x018d }
            int r11 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r11 == 0) goto L_0x0183
            int r11 = (r6 > r12 ? 1 : (r6 == r12 ? 0 : -1))
            if (r11 == 0) goto L_0x0183
            com.autonavi.common.model.GeoPoint r11 = r9.getPoint()     // Catch:{ Exception -> 0x018d }
            r11.setLonLat(r14, r6)     // Catch:{ Exception -> 0x018d }
            java.lang.String r6 = "address"
            boolean r6 = r10.has(r6)     // Catch:{ Exception -> 0x018d }
            if (r6 == 0) goto L_0x0122
            java.lang.String r6 = "address"
            java.lang.String r6 = r10.getString(r6)     // Catch:{ Exception -> 0x018d }
            r9.setAddr(r6)     // Catch:{ Exception -> 0x018d }
        L_0x0122:
            java.lang.String r6 = "name"
            boolean r6 = r10.has(r6)     // Catch:{ Exception -> 0x018d }
            if (r6 == 0) goto L_0x0133
            java.lang.String r6 = "name"
            java.lang.String r6 = r10.getString(r6)     // Catch:{ Exception -> 0x018d }
            r9.setName(r6)     // Catch:{ Exception -> 0x018d }
        L_0x0133:
            java.lang.String r6 = "adcode"
            boolean r6 = r10.has(r6)     // Catch:{ Exception -> 0x018d }
            if (r6 == 0) goto L_0x0144
            java.lang.String r6 = "adcode"
            java.lang.String r6 = r10.getString(r6)     // Catch:{ Exception -> 0x018d }
            r9.setAdCode(r6)     // Catch:{ Exception -> 0x018d }
        L_0x0144:
            java.lang.String r6 = "coords"
            boolean r6 = r10.has(r6)     // Catch:{ Exception -> 0x018d }
            if (r6 == 0) goto L_0x0159
            java.lang.String r6 = "coords"
            java.lang.String r6 = r10.getString(r6)     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList r6 = defpackage.bcx.b(r6)     // Catch:{ Exception -> 0x018d }
            r9.setRegions(r6)     // Catch:{ Exception -> 0x018d }
        L_0x0159:
            if (r19 == 0) goto L_0x0168
            r6 = 1
            if (r3 != r6) goto L_0x0169
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r7 = r1.a     // Catch:{ Exception -> 0x018d }
            aur r7 = r7.routingInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList<com.autonavi.common.model.POI> r7 = r7.c     // Catch:{ Exception -> 0x018d }
            r7.add(r9)     // Catch:{ Exception -> 0x018d }
            goto L_0x0184
        L_0x0168:
            r6 = 1
        L_0x0169:
            if (r19 == 0) goto L_0x0178
            r7 = 2
            if (r3 != r7) goto L_0x0179
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r10 = r1.a     // Catch:{ Exception -> 0x018d }
            aur r10 = r10.routingInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList<com.autonavi.common.model.POI> r10 = r10.h     // Catch:{ Exception -> 0x018d }
            r10.add(r9)     // Catch:{ Exception -> 0x018d }
            goto L_0x0185
        L_0x0178:
            r7 = 2
        L_0x0179:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r10 = r1.a     // Catch:{ Exception -> 0x018d }
            auj r10 = r10.locationInfo     // Catch:{ Exception -> 0x018d }
            java.util.ArrayList<com.autonavi.common.model.POI> r10 = r10.c     // Catch:{ Exception -> 0x018d }
            r10.add(r9)     // Catch:{ Exception -> 0x018d }
            goto L_0x0185
        L_0x0183:
            r6 = 1
        L_0x0184:
            r7 = 2
        L_0x0185:
            int r4 = r4 + 1
            r6 = 2
            r7 = 1
            goto L_0x00e0
        L_0x018b:
            return r8
        L_0x018c:
            return r8
        L_0x018d:
            r0 = move-exception
            r2 = r0
            goto L_0x0193
        L_0x0190:
            r0 = move-exception
            r2 = r0
            r8 = 0
        L_0x0193:
            defpackage.kf.a(r2)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.els.a(org.json.JSONObject, boolean, int):int");
    }

    private static void a(aus aus, JSONArray jSONArray) {
        if (jSONArray != null && aus != null) {
            try {
                int length = jSONArray.length();
                JSONArray jSONArray2 = new JSONArray();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String optString = jSONObject.optString("item_type");
                    int optInt = jSONObject.optInt("template_id");
                    if (aus.h == null) {
                        aus.h = new ArrayList<>();
                    }
                    if (aus.j == null) {
                        aus.j = new ArrayList<>();
                    }
                    if (aus.i == null) {
                        aus.i = new ArrayList<>();
                    }
                    if (ModuleFeedBack.RECOMMEND.equalsIgnoreCase(optString)) {
                        a(jSONArray2, jSONObject, i);
                        ArrayList<aup> arrayList = aus.h;
                        aup aup = new aup();
                        a(aup, jSONObject);
                        aup.b = i;
                        arrayList.add(aup);
                    } else if (bcy.a(optString, optInt)) {
                        a(jSONArray2, jSONObject, i);
                        ArrayList<aun> arrayList2 = aus.j;
                        aun aun = new aun();
                        aun.b = jSONObject.optString("title");
                        aun.c = jSONObject.optString("subtitle");
                        aun.d = jSONObject.optString("pic");
                        aun.e = jSONObject.optString(ActionConstant.SCHEMA);
                        aun.g = jSONObject.optString("gsid");
                        aun.a = jSONObject.optInt("template_id");
                        aun.f = i;
                        arrayList2.add(aun);
                    } else if (bcy.b(optString, optInt)) {
                        a(jSONArray2, jSONObject, i);
                        ArrayList<aul> arrayList3 = aus.i;
                        aul aul = new aul();
                        aul.b = jSONObject.optString("title");
                        aul.c = jSONObject.optString("subtitle");
                        aul.d = jSONObject.optString("pic");
                        aul.e = jSONObject.optString(ActionConstant.SCHEMA);
                        aul.g = jSONObject.optString("gsid");
                        aul.a = jSONObject.optInt("template_id");
                        aul.f = i;
                        arrayList3.add(aul);
                    }
                }
                aus.k = jSONArray2.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static void a(JSONArray jSONArray, JSONObject jSONObject, int i) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(RouteItem.ITEM_TAG, jSONObject);
        jSONObject2.put("position", i);
        jSONArray.put(jSONObject2);
    }

    private static void b(aus aus, JSONArray jSONArray) {
        if (jSONArray != null) {
            JSONObject optJSONObject = jSONArray.optJSONObject(0);
            if (optJSONObject != null) {
                aus.y = optJSONObject.optJSONArray("module_names");
                aus.z = optJSONObject.optJSONObject("interface_results");
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:154:0x032e A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x036d A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:164:0x03ca A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:167:0x03f7 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:170:0x040b A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:173:0x0431 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0461 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:179:0x0476 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:197:0x04dc A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x04fe A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:203:0x0520 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x0542 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x0564 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:217:0x0624 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:231:0x0670 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:240:0x06a9 A[Catch:{ JSONException -> 0x07a6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:262:0x0721 A[Catch:{ JSONException -> 0x07a6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.util.List<com.autonavi.common.model.POI> r21, org.json.JSONArray r22) {
        /*
            r20 = this;
            r1 = r20
            r2 = r21
            r3 = r22
            if (r3 != 0) goto L_0x0009
            return
        L_0x0009:
            if (r2 != 0) goto L_0x000c
            return
        L_0x000c:
            int r4 = r22.length()     // Catch:{ JSONException -> 0x07a6 }
            r5 = 0
            r6 = 0
        L_0x0012:
            r7 = 1
            if (r6 >= r4) goto L_0x0782
            org.json.JSONObject r8 = r3.getJSONObject(r6)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r9 = "item_type"
            java.lang.String r9 = r8.optString(r9)     // Catch:{ JSONException -> 0x07a6 }
            boolean r9 = defpackage.bcy.a(r9)     // Catch:{ JSONException -> 0x07a6 }
            if (r9 == 0) goto L_0x0774
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r9 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r9 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r9)     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r9 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r9     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r10 = r9.getPoiChildrenInfo()     // Catch:{ JSONException -> 0x07a6 }
            if (r10 != 0) goto L_0x003b
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r10 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData     // Catch:{ JSONException -> 0x07a6 }
            r10.<init>()     // Catch:{ JSONException -> 0x07a6 }
            r9.setPoiChildrenInfo(r10)     // Catch:{ JSONException -> 0x07a6 }
        L_0x003b:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r10 = r9.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x07a6 }
            if (r10 != 0) goto L_0x0049
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r10 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData     // Catch:{ JSONException -> 0x07a6 }
            r10.<init>()     // Catch:{ JSONException -> 0x07a6 }
            r9.setIndoorPoiInfo(r10)     // Catch:{ JSONException -> 0x07a6 }
        L_0x0049:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r10 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            if (r10 != 0) goto L_0x0057
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r10 = new com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData     // Catch:{ JSONException -> 0x07a6 }
            r10.<init>()     // Catch:{ JSONException -> 0x07a6 }
            r9.setIDynamicRenderInfo(r10)     // Catch:{ JSONException -> 0x07a6 }
        L_0x0057:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.AutoNaviPoiData r10 = r9.getPoiAutoNaviInfo()     // Catch:{ JSONException -> 0x07a6 }
            if (r10 != 0) goto L_0x0065
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.AutoNaviPoiData r10 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.AutoNaviPoiData     // Catch:{ JSONException -> 0x07a6 }
            r10.<init>()     // Catch:{ JSONException -> 0x07a6 }
            r9.setPoiAutoNaviInfo(r10)     // Catch:{ JSONException -> 0x07a6 }
        L_0x0065:
            defpackage.aep.a(r9, r8)     // Catch:{ JSONException -> 0x07a6 }
            r10 = 2
            if (r8 == 0) goto L_0x01a9
            if (r9 != 0) goto L_0x006f
            goto L_0x01a9
        L_0x006f:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r11 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            if (r11 != 0) goto L_0x007d
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r11 = new com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData     // Catch:{ JSONException -> 0x07a6 }
            r11.<init>()     // Catch:{ JSONException -> 0x07a6 }
            r9.setIDynamicRenderInfo(r11)     // Catch:{ JSONException -> 0x07a6 }
        L_0x007d:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r11 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            r11.bFlag = r7     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r11 = "render_style_main"
            boolean r11 = r8.has(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r11 == 0) goto L_0x00b3
            java.lang.String r11 = "render_style_main"
            java.lang.String r11 = r8.optString(r11)     // Catch:{ JSONException -> 0x07a6 }
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 == 0) goto L_0x009d
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            r12.bFlag = r5     // Catch:{ JSONException -> 0x07a6 }
        L_0x009d:
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 != 0) goto L_0x00b3
            boolean r12 = android.text.TextUtils.isDigitsOnly(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 == 0) goto L_0x00b3
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            int r11 = java.lang.Integer.parseInt(r11)     // Catch:{ JSONException -> 0x07a6 }
            r12.mainStyle = r11     // Catch:{ JSONException -> 0x07a6 }
        L_0x00b3:
            java.lang.String r11 = "render_style_sub"
            boolean r11 = r8.has(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r11 == 0) goto L_0x00e3
            java.lang.String r11 = "render_style_sub"
            java.lang.String r11 = r8.optString(r11)     // Catch:{ JSONException -> 0x07a6 }
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 == 0) goto L_0x00cd
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            r12.bFlag = r5     // Catch:{ JSONException -> 0x07a6 }
        L_0x00cd:
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 != 0) goto L_0x00e3
            boolean r12 = android.text.TextUtils.isDigitsOnly(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 == 0) goto L_0x00e3
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            int r11 = java.lang.Integer.parseInt(r11)     // Catch:{ JSONException -> 0x07a6 }
            r12.subStyle = r11     // Catch:{ JSONException -> 0x07a6 }
        L_0x00e3:
            java.lang.String r11 = "render_rank"
            boolean r11 = r8.has(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r11 == 0) goto L_0x0114
            java.lang.String r11 = "render_rank"
            java.lang.String r11 = r8.optString(r11)     // Catch:{ JSONException -> 0x07a6 }
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 == 0) goto L_0x00fd
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            r12.bFlag = r5     // Catch:{ JSONException -> 0x07a6 }
        L_0x00fd:
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 != 0) goto L_0x011a
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ NumberFormatException -> 0x010e }
            float r11 = java.lang.Float.parseFloat(r11)     // Catch:{ NumberFormatException -> 0x010e }
            r12.fRank = r11     // Catch:{ NumberFormatException -> 0x010e }
            goto L_0x011a
        L_0x010e:
            r0 = move-exception
            r11 = r0
            r11.printStackTrace()     // Catch:{ JSONException -> 0x07a6 }
            goto L_0x011a
        L_0x0114:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r11 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            r11.bFlag = r5     // Catch:{ JSONException -> 0x07a6 }
        L_0x011a:
            java.lang.String r11 = "minizoom"
            boolean r11 = r8.has(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r11 == 0) goto L_0x014b
            java.lang.String r11 = "minizoom"
            java.lang.String r11 = r8.optString(r11)     // Catch:{ JSONException -> 0x07a6 }
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 == 0) goto L_0x0134
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            r12.bFlag = r5     // Catch:{ JSONException -> 0x07a6 }
        L_0x0134:
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 != 0) goto L_0x0151
            boolean r12 = android.text.TextUtils.isDigitsOnly(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 == 0) goto L_0x0151
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            int r11 = java.lang.Integer.parseInt(r11)     // Catch:{ JSONException -> 0x07a6 }
            r12.minizoom = r11     // Catch:{ JSONException -> 0x07a6 }
            goto L_0x0151
        L_0x014b:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r11 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            r11.bFlag = r5     // Catch:{ JSONException -> 0x07a6 }
        L_0x0151:
            java.lang.String r11 = "anchor"
            boolean r11 = r8.has(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r11 == 0) goto L_0x01a3
            java.lang.String r11 = "anchor"
            java.lang.String r11 = r8.optString(r11)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r11 = r11.trim()     // Catch:{ JSONException -> 0x07a6 }
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 == 0) goto L_0x016f
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            r12.bFlag = r5     // Catch:{ JSONException -> 0x07a6 }
        L_0x016f:
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r12 != 0) goto L_0x01a9
            java.lang.String r12 = "0x"
            boolean r12 = r11.startsWith(r12)     // Catch:{ NumberFormatException -> 0x019d }
            if (r12 != 0) goto L_0x0185
            java.lang.String r12 = "0X"
            boolean r12 = r11.startsWith(r12)     // Catch:{ NumberFormatException -> 0x019d }
            if (r12 == 0) goto L_0x01a9
        L_0x0185:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r12 = r9.getIDynamicRenderInfo()     // Catch:{ NumberFormatException -> 0x0197 }
            java.lang.String r11 = r11.substring(r10)     // Catch:{ NumberFormatException -> 0x0197 }
            r13 = 16
            long r13 = java.lang.Long.parseLong(r11, r13)     // Catch:{ NumberFormatException -> 0x0197 }
            int r11 = (int) r13     // Catch:{ NumberFormatException -> 0x0197 }
            r12.anchor = r11     // Catch:{ NumberFormatException -> 0x0197 }
            goto L_0x01a9
        L_0x0197:
            r0 = move-exception
            r11 = r0
            r11.printStackTrace()     // Catch:{ NumberFormatException -> 0x019d }
            goto L_0x01a9
        L_0x019d:
            r0 = move-exception
            r11 = r0
            r11.printStackTrace()     // Catch:{ JSONException -> 0x07a6 }
            goto L_0x01a9
        L_0x01a3:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r11 = r9.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x07a6 }
            r11.bFlag = r5     // Catch:{ JSONException -> 0x07a6 }
        L_0x01a9:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r11 = r1.a     // Catch:{ JSONException -> 0x07a6 }
            aus r11 = r11.searchInfo     // Catch:{ JSONException -> 0x07a6 }
            auk r11 = r11.a     // Catch:{ JSONException -> 0x07a6 }
            defpackage.elu.a(r8, r11, r9)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r11 = "poi_sug_info"
            boolean r11 = r8.has(r11)     // Catch:{ JSONException -> 0x07a6 }
            if (r11 == 0) goto L_0x02dc
            java.lang.String r11 = "poi_sug_info"
            org.json.JSONArray r11 = r8.getJSONArray(r11)     // Catch:{ JSONException -> 0x02dc }
            if (r11 == 0) goto L_0x02dc
            int r13 = r11.length()     // Catch:{ JSONException -> 0x02dc }
            if (r13 != 0) goto L_0x01ca
            goto L_0x02dc
        L_0x01ca:
            java.util.ArrayList r13 = new java.util.ArrayList     // Catch:{ JSONException -> 0x02dc }
            r13.<init>()     // Catch:{ JSONException -> 0x02dc }
            int r14 = r11.length()     // Catch:{ JSONException -> 0x02dc }
            r15 = 0
        L_0x01d4:
            if (r15 >= r14) goto L_0x02ce
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchRecommendPoi> r12 = com.autonavi.bundle.entity.common.searchpoi.SearchRecommendPoi.class
            com.autonavi.common.model.POI r12 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r12)     // Catch:{ JSONException -> 0x02dc }
            com.autonavi.bundle.entity.common.searchpoi.SearchRecommendPoi r12 = (com.autonavi.bundle.entity.common.searchpoi.SearchRecommendPoi) r12     // Catch:{ JSONException -> 0x02dc }
            r12.setShowType(r5)     // Catch:{ JSONException -> 0x02dc }
            org.json.JSONObject r10 = r11.getJSONObject(r15)     // Catch:{ JSONException -> 0x02dc }
            java.lang.String r5 = "tupu_icon_forcsr"
            java.lang.String r7 = ""
            java.lang.String r5 = r10.optString(r5, r7)     // Catch:{ JSONException -> 0x02dc }
            java.lang.String r7 = ""
            boolean r7 = r7.equals(r5)     // Catch:{ JSONException -> 0x02dc }
            if (r7 != 0) goto L_0x0220
            r7 = 44
            int r17 = r5.indexOf(r7)     // Catch:{ JSONException -> 0x02dc }
            if (r17 <= 0) goto L_0x0220
            int r7 = r5.indexOf(r7)     // Catch:{ JSONException -> 0x02dc }
            int r17 = r5.length()     // Catch:{ JSONException -> 0x02dc }
            r16 = 1
            int r3 = r17 + -1
            if (r7 >= r3) goto L_0x0220
            java.lang.String r3 = ","
            java.lang.String[] r3 = r5.split(r3)     // Catch:{ JSONException -> 0x02dc }
            r5 = 0
            r7 = r3[r5]     // Catch:{ JSONException -> 0x02dc }
            r12.setTpIcon(r7)     // Catch:{ JSONException -> 0x02dc }
            r3 = r3[r16]     // Catch:{ NumberFormatException -> 0x0220 }
            int r3 = java.lang.Integer.parseInt(r3)     // Catch:{ NumberFormatException -> 0x0220 }
            r12.setShowType(r3)     // Catch:{ NumberFormatException -> 0x0220 }
        L_0x0220:
            java.lang.String r3 = "typecode"
            java.lang.String r5 = ""
            java.lang.String r3 = r10.optString(r3, r5)     // Catch:{ JSONException -> 0x02dc }
            r12.setType(r3)     // Catch:{ JSONException -> 0x02dc }
            java.lang.String r3 = "name"
            java.lang.String r5 = ""
            java.lang.String r3 = r10.optString(r3, r5)     // Catch:{ JSONException -> 0x02dc }
            r12.setName(r3)     // Catch:{ JSONException -> 0x02dc }
            java.lang.String r5 = "pguid"
            java.lang.String r7 = ""
            java.lang.String r5 = r10.optString(r5, r7)     // Catch:{ JSONException -> 0x02dc }
            r12.setId(r5)     // Catch:{ JSONException -> 0x02dc }
            java.lang.String r5 = "shortname"
            java.lang.String r7 = ""
            java.lang.String r5 = r10.optString(r5, r7)     // Catch:{ JSONException -> 0x02dc }
            java.lang.String r7 = ""
            boolean r7 = r7.equals(r5)     // Catch:{ JSONException -> 0x02dc }
            if (r7 == 0) goto L_0x0259
            java.lang.String r3 = defpackage.bcx.c(r3)     // Catch:{ JSONException -> 0x02dc }
            r12.setShortName(r3)     // Catch:{ JSONException -> 0x02dc }
            goto L_0x025c
        L_0x0259:
            r12.setShortName(r5)     // Catch:{ JSONException -> 0x02dc }
        L_0x025c:
            java.lang.String r3 = r12.getShortName()     // Catch:{ JSONException -> 0x02dc }
            int r3 = r3.length()     // Catch:{ JSONException -> 0x02dc }
            r5 = 21
            if (r3 <= r5) goto L_0x0281
            int r3 = r12.getShowType()     // Catch:{ JSONException -> 0x02dc }
            r5 = 1
            if (r3 != r5) goto L_0x0274
            r3 = 2
            r12.setShowType(r3)     // Catch:{ JSONException -> 0x02dc }
            goto L_0x0282
        L_0x0274:
            r3 = 2
            int r5 = r12.getShowType()     // Catch:{ JSONException -> 0x02dc }
            r7 = 4
            if (r5 != r7) goto L_0x0282
            r5 = 3
            r12.setShowType(r5)     // Catch:{ JSONException -> 0x02dc }
            goto L_0x0282
        L_0x0281:
            r3 = 2
        L_0x0282:
            java.lang.String r5 = "x"
            r18 = r4
            r3 = 0
            r19 = r6
            double r5 = r10.optDouble(r5, r3)     // Catch:{ JSONException -> 0x02e0 }
            java.lang.Double r5 = java.lang.Double.valueOf(r5)     // Catch:{ JSONException -> 0x02e0 }
            java.lang.String r6 = "y"
            double r3 = r10.optDouble(r6, r3)     // Catch:{ JSONException -> 0x02e0 }
            double r5 = r5.doubleValue()     // Catch:{ JSONException -> 0x02e0 }
            android.graphics.Point r3 = defpackage.bbw.a(r3, r5)     // Catch:{ JSONException -> 0x02e0 }
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x02e0 }
            int r5 = r3.x     // Catch:{ JSONException -> 0x02e0 }
            int r3 = r3.y     // Catch:{ JSONException -> 0x02e0 }
            r4.<init>(r5, r3)     // Catch:{ JSONException -> 0x02e0 }
            r12.setPoint(r4)     // Catch:{ JSONException -> 0x02e0 }
            java.lang.String r3 = "end_poi_extension"
            java.lang.String r3 = r10.optString(r3)     // Catch:{ JSONException -> 0x02e0 }
            r12.setEndPoiExtension(r3)     // Catch:{ JSONException -> 0x02e0 }
            java.lang.String r3 = "transparent"
            java.lang.String r3 = r10.optString(r3)     // Catch:{ JSONException -> 0x02e0 }
            r12.setTransparent(r3)     // Catch:{ JSONException -> 0x02e0 }
            r13.add(r12)     // Catch:{ JSONException -> 0x02e0 }
            int r15 = r15 + 1
            r4 = r18
            r6 = r19
            r3 = r22
            r5 = 0
            r7 = 1
            r10 = 2
            goto L_0x01d4
        L_0x02ce:
            r18 = r4
            r19 = r6
            int r3 = r13.size()     // Catch:{ JSONException -> 0x02e0 }
            if (r3 <= 0) goto L_0x02e0
            r9.setRecommonPoiInfos(r13)     // Catch:{ JSONException -> 0x02e0 }
            goto L_0x02e0
        L_0x02dc:
            r18 = r4
            r19 = r6
        L_0x02e0:
            java.lang.String r3 = "heat_map_flag"
            r4 = 0
            int r3 = r8.optInt(r3, r4)     // Catch:{ JSONException -> 0x07a6 }
            r9.setHeatMapFlag(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "id"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setId(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "industry"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setIndustry(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "aoi"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0326
            java.lang.String r3 = "aoi"
            java.lang.String r3 = r8.getString(r3)     // Catch:{ JSONException -> 0x07a6 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 != 0) goto L_0x0326
            java.lang.String r3 = "aoi"
            java.lang.String r3 = r8.getString(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.util.ArrayList r3 = defpackage.bcx.b(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r4 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r5 = "poi_polygon_bounds"
            r4.put(r5, r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setRegions(r3)     // Catch:{ JSONException -> 0x07a6 }
        L_0x0326:
            java.lang.String r3 = "view_region"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0352
            java.lang.String r3 = "view_region"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.Double[] r3 = defpackage.bcx.a(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setViewRegions(r3)     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r1.a     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.bl.search.InfoliteParam r4 = r4.mWrapper     // Catch:{ JSONException -> 0x07a6 }
            if (r4 == 0) goto L_0x0352
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r1.a     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.bl.search.InfoliteParam r4 = r4.mWrapper     // Catch:{ JSONException -> 0x07a6 }
            int r4 = r4.pagenum     // Catch:{ JSONException -> 0x07a6 }
            r5 = 1
            if (r4 != r5) goto L_0x0352
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r4 = r1.a     // Catch:{ JSONException -> 0x07a6 }
            aus r4 = r4.searchInfo     // Catch:{ JSONException -> 0x07a6 }
            auk r4 = r4.a     // Catch:{ JSONException -> 0x07a6 }
            r4.u = r3     // Catch:{ JSONException -> 0x07a6 }
        L_0x0352:
            java.lang.String r3 = "rich_info_flag"
            r4 = 0
            boolean r3 = r8.optBoolean(r3, r4)     // Catch:{ JSONException -> 0x07a6 }
            r9.setRichInfoFlag(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "distance_display"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setVoiceDriveDistance(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "name"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0376
            java.lang.String r3 = "name"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setName(r3)     // Catch:{ JSONException -> 0x07a6 }
        L_0x0376:
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "rating"
            java.lang.String r5 = "rating"
            r6 = 0
            java.lang.String r5 = r8.optString(r5, r6)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r4, r5)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "averagecost"
            java.lang.String r5 = "averagecost"
            java.lang.String r5 = r8.optString(r5, r6)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r4, r5)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "averagecostname"
            java.lang.String r5 = "averagecostname"
            java.lang.String r5 = r8.optString(r5, r6)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r4, r5)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "businfo_station_status"
            java.lang.String r5 = "businfo_station_status"
            java.lang.String r5 = r8.optString(r5, r6)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r4, r5)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "update_flag"
            java.lang.String r5 = "update_flag"
            java.lang.String r5 = r8.optString(r5, r6)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r4, r5)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "typecode"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x03d3
            java.lang.String r3 = "typecode"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setType(r3)     // Catch:{ JSONException -> 0x07a6 }
        L_0x03d3:
            java.lang.String r3 = "need_arrive_timecost"
            r4 = 0
            int r3 = r8.optInt(r3, r4)     // Catch:{ JSONException -> 0x07a6 }
            r9.setNeedArriveTimeCost(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "recommend_flag"
            int r3 = r8.optInt(r3, r4)     // Catch:{ JSONException -> 0x07a6 }
            r9.setRecommendFlag(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "reference_rlt_flag"
            int r3 = r8.optInt(r3, r4)     // Catch:{ JSONException -> 0x07a6 }
            r9.setReferenceRltFlag(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "sndt_fl_nona"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0403
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r3 = r9.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "sndt_fl_nona"
            java.lang.String r4 = r8.optString(r4)     // Catch:{ JSONException -> 0x07a6 }
            r3.sndtFloorName = r4     // Catch:{ JSONException -> 0x07a6 }
        L_0x0403:
            java.lang.String r3 = "sndt_parentid"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0417
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r3 = r9.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "sndt_parentid"
            java.lang.String r4 = r8.optString(r4)     // Catch:{ JSONException -> 0x07a6 }
            r3.buildingPoiId = r4     // Catch:{ JSONException -> 0x07a6 }
        L_0x0417:
            java.lang.String r3 = "pic_status"
            int r3 = r8.optInt(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setPicStatus(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "route_planning"
            int r3 = r8.optInt(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setRoutePlanning(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "f_nona"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0459
            java.lang.String r3 = "f_nona"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setFnona(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r3 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r3 = r9.as(r3)     // Catch:{ JSONException -> 0x07a6 }
            com.amap.bundle.datamodel.FavoritePOI r3 = (com.amap.bundle.datamodel.FavoritePOI) r3     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "f_nona"
            java.lang.String r4 = r8.optString(r4)     // Catch:{ JSONException -> 0x07a6 }
            r3.setFnona(r4)     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r3 = r9.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "f_nona"
            java.lang.String r5 = ""
            java.lang.String r4 = r8.optString(r4, r5)     // Catch:{ JSONException -> 0x07a6 }
            r3.floorName = r4     // Catch:{ JSONException -> 0x07a6 }
        L_0x0459:
            java.lang.String r3 = "sndt_fl_no"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x046e
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r3 = r9.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "sndt_fl_no"
            r5 = 0
            int r4 = r8.optInt(r4, r5)     // Catch:{ JSONException -> 0x07a6 }
            r3.floorNo = r4     // Catch:{ JSONException -> 0x07a6 }
        L_0x046e:
            java.lang.String r3 = "sndt_parentid"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0482
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r3 = r9.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "sndt_parentid"
            java.lang.String r4 = r8.optString(r4)     // Catch:{ JSONException -> 0x07a6 }
            r3.parentId = r4     // Catch:{ JSONException -> 0x07a6 }
        L_0x0482:
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r3 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r3 = r9.as(r3)     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r3 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r3     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r4 = "display_icon_name_state"
            int r4 = r8.optInt(r4)     // Catch:{ JSONException -> 0x07a6 }
            r3.setDisplayIconNameState(r4)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "longitude"
            r4 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            double r10 = r8.optDouble(r3, r4)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "latitude"
            double r12 = r8.optDouble(r3, r4)     // Catch:{ JSONException -> 0x07a6 }
            int r3 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x04b0
            int r3 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r3 == 0) goto L_0x04b0
            com.autonavi.common.model.GeoPoint r3 = r9.getPoint()     // Catch:{ JSONException -> 0x07a6 }
            r3.setLonLat(r10, r12)     // Catch:{ JSONException -> 0x07a6 }
        L_0x04b0:
            java.lang.String r3 = "display_x"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x04d4
            java.lang.String r3 = "display_x"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x04d4
            java.lang.String r3 = "display_x"
            double r10 = r8.optDouble(r3)     // Catch:{ Exception -> 0x04d4 }
            java.lang.String r3 = "display_y"
            double r12 = r8.optDouble(r3)     // Catch:{ Exception -> 0x04d4 }
            com.autonavi.common.model.GeoPoint r3 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x04d4 }
            r3.<init>(r10, r12)     // Catch:{ Exception -> 0x04d4 }
            r9.setDisplayPoint(r3)     // Catch:{ Exception -> 0x04d4 }
        L_0x04d4:
            java.lang.String r3 = "parent"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x04f6
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r3 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r3 = r9.as(r3)     // Catch:{ JSONException -> 0x07a6 }
            com.amap.bundle.datamodel.FavoritePOI r3 = (com.amap.bundle.datamodel.FavoritePOI) r3     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "parent"
            java.lang.String r7 = r8.optString(r7)     // Catch:{ JSONException -> 0x07a6 }
            r3.setParent(r7)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "parent"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setParent(r3)     // Catch:{ JSONException -> 0x07a6 }
        L_0x04f6:
            java.lang.String r3 = "childType"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0518
            java.lang.String r3 = "childType"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setChildType(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r3 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r3 = r9.as(r3)     // Catch:{ JSONException -> 0x07a6 }
            com.amap.bundle.datamodel.FavoritePOI r3 = (com.amap.bundle.datamodel.FavoritePOI) r3     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "childType"
            java.lang.String r7 = r8.optString(r7)     // Catch:{ JSONException -> 0x07a6 }
            r3.setChildType(r7)     // Catch:{ JSONException -> 0x07a6 }
        L_0x0518:
            java.lang.String r3 = "towards_angle"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x053a
            java.lang.String r3 = "towards_angle"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setTowardsAngle(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r3 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r3 = r9.as(r3)     // Catch:{ JSONException -> 0x07a6 }
            com.amap.bundle.datamodel.FavoritePOI r3 = (com.amap.bundle.datamodel.FavoritePOI) r3     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "towards_angle"
            java.lang.String r7 = r8.optString(r7)     // Catch:{ JSONException -> 0x07a6 }
            r3.setTowardsAngle(r7)     // Catch:{ JSONException -> 0x07a6 }
        L_0x053a:
            java.lang.String r3 = "end_poi_extension"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x055c
            java.lang.String r3 = "end_poi_extension"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setEndPoiExtension(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r3 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r3 = r9.as(r3)     // Catch:{ JSONException -> 0x07a6 }
            com.amap.bundle.datamodel.FavoritePOI r3 = (com.amap.bundle.datamodel.FavoritePOI) r3     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "end_poi_extension"
            java.lang.String r7 = r8.optString(r7)     // Catch:{ JSONException -> 0x07a6 }
            r3.setEndPoiExtension(r7)     // Catch:{ JSONException -> 0x07a6 }
        L_0x055c:
            java.lang.String r3 = "transparent"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x057e
            java.lang.String r3 = "transparent"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setTransparent(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r3 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r3 = r9.as(r3)     // Catch:{ JSONException -> 0x07a6 }
            com.amap.bundle.datamodel.FavoritePOI r3 = (com.amap.bundle.datamodel.FavoritePOI) r3     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "transparent"
            java.lang.String r7 = r8.optString(r7)     // Catch:{ JSONException -> 0x07a6 }
            r3.setTransparent(r7)     // Catch:{ JSONException -> 0x07a6 }
        L_0x057e:
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "tra_tag"
            java.lang.String r10 = "tra_tag"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r7, r10)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "tra_title"
            java.lang.String r10 = "tra_title"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r7, r10)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "tra_action"
            java.lang.String r10 = "tra_action"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r7, r10)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "tra_action_param"
            java.lang.String r10 = "tra_action_param"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r7, r10)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "ugc_title"
            java.lang.String r10 = "ugc_title"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r7, r10)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "ugc_thread_url"
            java.lang.String r10 = "ugc_thread_url"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r7, r10)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "ugc_plot_url"
            java.lang.String r10 = "ugc_plot_url"
            java.lang.String r10 = r8.optString(r10)     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r7, r10)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r3 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "original"
            java.lang.String r10 = r8.toString()     // Catch:{ JSONException -> 0x07a6 }
            r3.put(r7, r10)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "citycode"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setCityCode(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "adcode"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            r9.setAdCode(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "entrances"
            org.json.JSONArray r3 = r8.optJSONArray(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0652
            int r7 = r3.length()     // Catch:{ JSONException -> 0x07a6 }
            if (r7 <= 0) goto L_0x0652
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ JSONException -> 0x07a6 }
            int r10 = r3.length()     // Catch:{ JSONException -> 0x07a6 }
            r7.<init>(r10)     // Catch:{ JSONException -> 0x07a6 }
            r10 = 0
        L_0x061e:
            int r11 = r3.length()     // Catch:{ JSONException -> 0x07a6 }
            if (r10 >= r11) goto L_0x064f
            org.json.JSONObject r11 = r3.getJSONObject(r10)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r12 = "longitude"
            double r12 = r11.optDouble(r12, r4)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r14 = "latitude"
            double r14 = r11.optDouble(r14, r4)     // Catch:{ JSONException -> 0x07a6 }
            int r11 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r11 == 0) goto L_0x064c
            int r11 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r11 == 0) goto L_0x064c
            android.graphics.Point r11 = defpackage.bbw.a(r14, r12)     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.common.model.GeoPoint r12 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x07a6 }
            int r13 = r11.x     // Catch:{ JSONException -> 0x07a6 }
            int r11 = r11.y     // Catch:{ JSONException -> 0x07a6 }
            r12.<init>(r13, r11)     // Catch:{ JSONException -> 0x07a6 }
            r7.add(r12)     // Catch:{ JSONException -> 0x07a6 }
        L_0x064c:
            int r10 = r10 + 1
            goto L_0x061e
        L_0x064f:
            r9.setEntranceList(r7)     // Catch:{ JSONException -> 0x07a6 }
        L_0x0652:
            java.lang.String r3 = "exits"
            org.json.JSONArray r3 = r8.optJSONArray(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x069e
            int r7 = r3.length()     // Catch:{ JSONException -> 0x07a6 }
            if (r7 <= 0) goto L_0x069e
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ JSONException -> 0x07a6 }
            int r10 = r3.length()     // Catch:{ JSONException -> 0x07a6 }
            r7.<init>(r10)     // Catch:{ JSONException -> 0x07a6 }
            r10 = 0
        L_0x066a:
            int r11 = r3.length()     // Catch:{ JSONException -> 0x07a6 }
            if (r10 >= r11) goto L_0x069b
            org.json.JSONObject r11 = r3.getJSONObject(r10)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r12 = "longitude"
            double r12 = r11.optDouble(r12, r4)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r14 = "latitude"
            double r14 = r11.optDouble(r14, r4)     // Catch:{ JSONException -> 0x07a6 }
            int r11 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
            if (r11 == 0) goto L_0x0698
            int r11 = (r14 > r4 ? 1 : (r14 == r4 ? 0 : -1))
            if (r11 == 0) goto L_0x0698
            android.graphics.Point r11 = defpackage.bbw.a(r14, r12)     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.common.model.GeoPoint r12 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x07a6 }
            int r13 = r11.x     // Catch:{ JSONException -> 0x07a6 }
            int r11 = r11.y     // Catch:{ JSONException -> 0x07a6 }
            r12.<init>(r13, r11)     // Catch:{ JSONException -> 0x07a6 }
            r7.add(r12)     // Catch:{ JSONException -> 0x07a6 }
        L_0x0698:
            int r10 = r10 + 1
            goto L_0x066a
        L_0x069b:
            r9.setExitList(r7)     // Catch:{ JSONException -> 0x07a6 }
        L_0x069e:
            b(r9, r8)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "address"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x0721
            java.lang.String r3 = r9.getType()     // Catch:{ JSONException -> 0x07a6 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r4 != 0) goto L_0x06c1
            int r4 = r3.length()     // Catch:{ JSONException -> 0x07a6 }
            r5 = 5
            if (r4 <= r5) goto L_0x06c1
            r4 = 0
            r5 = 4
            java.lang.String r3 = r3.substring(r4, r5)     // Catch:{ JSONException -> 0x07a6 }
            goto L_0x06c2
        L_0x06c1:
            r4 = 0
        L_0x06c2:
            java.lang.String r5 = "address"
            java.lang.String r5 = r8.optString(r5)     // Catch:{ JSONException -> 0x07a6 }
            r9.setAddr(r5)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "1507"
            boolean r7 = r3.equals(r7)     // Catch:{ JSONException -> 0x07a6 }
            if (r7 != 0) goto L_0x0722
            java.lang.String r7 = "1505"
            boolean r3 = r3.equals(r7)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 != 0) goto L_0x0722
            java.lang.String r3 = ""
            java.lang.String r7 = ""
            java.lang.String r10 = "districtname"
            boolean r10 = r8.has(r10)     // Catch:{ JSONException -> 0x07a6 }
            if (r10 == 0) goto L_0x06ed
            java.lang.String r7 = "districtname"
            java.lang.String r7 = r8.optString(r7)     // Catch:{ JSONException -> 0x07a6 }
        L_0x06ed:
            java.lang.String r10 = "provincename"
            boolean r10 = r8.has(r10)     // Catch:{ JSONException -> 0x07a6 }
            if (r10 == 0) goto L_0x06fb
            java.lang.String r3 = "provincename"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
        L_0x06fb:
            boolean r10 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r10 != 0) goto L_0x071d
            boolean r10 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x07a6 }
            if (r10 != 0) goto L_0x071d
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x07a6 }
            r10.<init>()     // Catch:{ JSONException -> 0x07a6 }
            r10.append(r3)     // Catch:{ JSONException -> 0x07a6 }
            r10.append(r7)     // Catch:{ JSONException -> 0x07a6 }
            r10.append(r5)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = r10.toString()     // Catch:{ JSONException -> 0x07a6 }
            r9.setAddr(r3)     // Catch:{ JSONException -> 0x07a6 }
            goto L_0x0722
        L_0x071d:
            r9.setAddr(r5)     // Catch:{ JSONException -> 0x07a6 }
            goto L_0x0722
        L_0x0721:
            r4 = 0
        L_0x0722:
            c(r9, r8)     // Catch:{ JSONException -> 0x07a6 }
            d(r9, r8)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r3 = "stations"
            boolean r3 = r8.has(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 == 0) goto L_0x075d
            java.lang.String r3 = "stations"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 != 0) goto L_0x075d
            java.lang.String r3 = "stations"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r5 = "null"
            boolean r3 = r3.equals(r5)     // Catch:{ JSONException -> 0x07a6 }
            if (r3 != 0) goto L_0x075d
            java.lang.String r3 = "stations"
            org.json.JSONObject r3 = r8.getJSONObject(r3)     // Catch:{ JSONException -> 0x07a6 }
            java.util.HashMap r5 = r9.getPoiExtra()     // Catch:{ JSONException -> 0x07a6 }
            java.lang.String r7 = "stations"
            java.lang.String r3 = r3.toString()     // Catch:{ JSONException -> 0x07a6 }
            r5.put(r7, r3)     // Catch:{ JSONException -> 0x07a6 }
        L_0x075d:
            java.lang.String r3 = "distance"
            java.lang.String r3 = r8.optString(r3)     // Catch:{ JSONException -> 0x07a6 }
            int r3 = defpackage.bcx.a(r9, r3, r6)     // Catch:{ JSONException -> 0x07a6 }
            r9.setDistance(r3)     // Catch:{ JSONException -> 0x07a6 }
            e(r9, r8)     // Catch:{ JSONException -> 0x07a6 }
            a(r9, r8)     // Catch:{ JSONException -> 0x07a6 }
            r2.add(r9)     // Catch:{ JSONException -> 0x07a6 }
            goto L_0x0779
        L_0x0774:
            r18 = r4
            r19 = r6
            r4 = 0
        L_0x0779:
            int r6 = r19 + 1
            r4 = r18
            r3 = r22
            r5 = 0
            goto L_0x0012
        L_0x0782:
            boolean r3 = a(r21)     // Catch:{ JSONException -> 0x07a6 }
            java.util.Iterator r2 = r21.iterator()     // Catch:{ JSONException -> 0x07a6 }
        L_0x078a:
            boolean r4 = r2.hasNext()     // Catch:{ JSONException -> 0x07a6 }
            if (r4 == 0) goto L_0x07a5
            java.lang.Object r4 = r2.next()     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.common.model.POI r4 = (com.autonavi.common.model.POI) r4     // Catch:{ JSONException -> 0x07a6 }
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r5 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r4 = r4.as(r5)     // Catch:{ JSONException -> 0x07a6 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r4 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r4     // Catch:{ JSONException -> 0x07a6 }
            r5 = 1
            r6 = r3 ^ 1
            r4.setIsShowName(r6)     // Catch:{ JSONException -> 0x07a6 }
            goto L_0x078a
        L_0x07a5:
            return
        L_0x07a6:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.els.a(java.util.List, org.json.JSONArray):void");
    }

    private static boolean a(List<POI> list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        for (POI as : list) {
            SearchPoi searchPoi = (SearchPoi) as.as(SearchPoi.class);
            if (2 == searchPoi.getPoiChildrenInfo().childType) {
                return true;
            }
            if (1 == searchPoi.getPoiChildrenInfo().childType) {
                return true;
            }
        }
        return false;
    }

    private static void a(aup aup, JSONObject jSONObject) throws JSONException {
        aup.a = jSONObject.optString("reason");
        aup.d = jSONObject.optInt("row");
        aup.c = jSONObject.optInt("col");
        aup.e = jSONObject.optString("gsid");
        aup.f = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("domain_list");
        if (optJSONArray != null) {
            int length = optJSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                auo auo = new auo();
                auo.b = jSONObject2.optString("value");
                auo.a = "query".equalsIgnoreCase(jSONObject2.optString("type")) ? 1 : -1;
                auo.c = jSONObject2.optString(ActionConstant.SCHEMA);
                aup.f.add(auo);
            }
        }
    }

    private static void a(SearchPoi searchPoi, JSONObject jSONObject) {
        JSONArray optJSONArray = jSONObject.optJSONArray("correlation_list");
        if (optJSONArray != null && optJSONArray.length() > 0) {
            int length = optJSONArray.length();
            aum aum = new aum();
            aum.a = searchPoi.getId();
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    aut aut = new aut();
                    aut.d = optJSONObject.optString("mPoiId");
                    if (optJSONObject.has("mAnchor")) {
                        String trim = optJSONObject.optString("mAnchor").trim();
                        if (!TextUtils.isEmpty(trim)) {
                            try {
                                if (trim.startsWith("0x") || trim.startsWith("0X")) {
                                    try {
                                        aut.e = (int) Long.parseLong(trim.substring(2), 16);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (NumberFormatException e2) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    aut.a = optJSONObject.optString("mLabelName");
                    aut.f = optJSONObject.optInt("mMainKey");
                    aut.g = optJSONObject.optInt("mSubkey");
                    aut.i = optJSONObject.optInt("mMaxzoom", 20);
                    aut.h = optJSONObject.optInt("mMinzoom");
                    aut.j = Float.parseFloat(optJSONObject.optString("mRank"));
                    GeoPoint geoPoint = new GeoPoint(optJSONObject.optDouble("mLongitude"), optJSONObject.optDouble("mLatitude"));
                    aut.b = geoPoint.x;
                    aut.c = geoPoint.y;
                    aum.b.add(aut);
                }
            }
            ((SearchPoi) searchPoi.as(SearchPoi.class)).setRecommendMode(aum);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:46|47) */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r8.k = -1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r8.l = -1;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:38:0x00d1 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:46:0x00e7 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d(org.json.JSONObject r20) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            if (r2 != 0) goto L_0x0007
            return
        L_0x0007:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r3 = r3.searchInfo
            if (r3 != 0) goto L_0x0016
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r4 = new aus
            r4.<init>()
            r3.searchInfo = r4
        L_0x0016:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r3 = r3.searchInfo
            java.util.ArrayList<aue> r3 = r3.r
            if (r3 != 0) goto L_0x0029
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r3 = r3.searchInfo
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            r3.r = r4
        L_0x0029:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r3 = r3.searchInfo
            java.util.ArrayList<aue> r3 = r3.r
            r3.clear()
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r3 = r3.searchInfo
            java.lang.String r4 = "total"
            int r4 = r2.optInt(r4)
            r3.p = r4
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r3 = r3.searchInfo
            java.lang.String r4 = "busline_count"
            int r4 = r2.optInt(r4)
            r3.q = r4
            java.lang.String r3 = "busline_list"
            boolean r3 = r2.has(r3)
            r4 = 0
            if (r3 == 0) goto L_0x03e4
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r3 = r3.searchInfo
            int r3 = r3.q
            if (r3 <= 0) goto L_0x03e4
            java.lang.String r3 = "busline_list"
            org.json.JSONArray r3 = r2.optJSONArray(r3)     // Catch:{ Exception -> 0x03de }
            if (r3 != 0) goto L_0x0064
            return
        L_0x0064:
            int r5 = r3.length()     // Catch:{ Exception -> 0x03de }
            r6 = 0
        L_0x0069:
            if (r6 >= r5) goto L_0x03e4
            org.json.JSONObject r7 = r3.getJSONObject(r6)     // Catch:{ Exception -> 0x03de }
            aue r8 = new aue     // Catch:{ Exception -> 0x03de }
            r8.<init>()     // Catch:{ Exception -> 0x03de }
            java.lang.String r9 = "id"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x0084
            java.lang.String r9 = "id"
            java.lang.String r9 = r7.getString(r9)     // Catch:{ Exception -> 0x03de }
            r8.a = r9     // Catch:{ Exception -> 0x03de }
        L_0x0084:
            java.lang.String r9 = "type"
            int r9 = r7.optInt(r9)     // Catch:{ Exception -> 0x03de }
            r8.n = r9     // Catch:{ Exception -> 0x03de }
            java.lang.String r9 = "name"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x009c
            java.lang.String r9 = "name"
            java.lang.String r9 = r7.getString(r9)     // Catch:{ Exception -> 0x03de }
            r8.b = r9     // Catch:{ Exception -> 0x03de }
        L_0x009c:
            java.lang.String r9 = "front_name"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x00ac
            java.lang.String r9 = "front_name"
            java.lang.String r9 = r7.getString(r9)     // Catch:{ Exception -> 0x03de }
            r8.i = r9     // Catch:{ Exception -> 0x03de }
        L_0x00ac:
            java.lang.String r9 = "terminal_name"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x00bc
            java.lang.String r9 = "terminal_name"
            java.lang.String r9 = r7.getString(r9)     // Catch:{ Exception -> 0x03de }
            r8.j = r9     // Catch:{ Exception -> 0x03de }
        L_0x00bc:
            r9 = -1
            java.lang.String r10 = "start_time"
            boolean r10 = r7.has(r10)     // Catch:{ JSONException -> 0x00d1 }
            if (r10 == 0) goto L_0x00ce
            java.lang.String r10 = "start_time"
            int r10 = r7.getInt(r10)     // Catch:{ JSONException -> 0x00d1 }
            r8.k = r10     // Catch:{ JSONException -> 0x00d1 }
            goto L_0x00d3
        L_0x00ce:
            r8.k = r9     // Catch:{ JSONException -> 0x00d1 }
            goto L_0x00d3
        L_0x00d1:
            r8.k = r9     // Catch:{ Exception -> 0x03de }
        L_0x00d3:
            java.lang.String r10 = "end_time"
            boolean r10 = r7.has(r10)     // Catch:{ JSONException -> 0x00e7 }
            if (r10 == 0) goto L_0x00e4
            java.lang.String r10 = "end_time"
            int r10 = r7.getInt(r10)     // Catch:{ JSONException -> 0x00e7 }
            r8.l = r10     // Catch:{ JSONException -> 0x00e7 }
            goto L_0x00e9
        L_0x00e4:
            r8.l = r9     // Catch:{ JSONException -> 0x00e7 }
            goto L_0x00e9
        L_0x00e7:
            r8.l = r9     // Catch:{ Exception -> 0x03de }
        L_0x00e9:
            java.lang.String r9 = "key_name"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x00f9
            java.lang.String r9 = "key_name"
            java.lang.String r9 = r7.getString(r9)     // Catch:{ Exception -> 0x03de }
            r8.h = r9     // Catch:{ Exception -> 0x03de }
        L_0x00f9:
            java.lang.String r9 = "areacode"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x0109
            java.lang.String r9 = "areacode"
            java.lang.String r9 = r7.getString(r9)     // Catch:{ Exception -> 0x03de }
            r8.c = r9     // Catch:{ Exception -> 0x03de }
        L_0x0109:
            java.lang.String r9 = "status"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            r10 = 1
            if (r9 == 0) goto L_0x011a
            java.lang.String r9 = "status"
            int r9 = r7.optInt(r9, r10)     // Catch:{ Exception -> 0x03de }
            r8.o = r9     // Catch:{ Exception -> 0x03de }
        L_0x011a:
            java.lang.String r9 = "description"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x012a
            java.lang.String r9 = "description"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x03de }
            r8.A = r9     // Catch:{ Exception -> 0x03de }
        L_0x012a:
            java.lang.String r9 = "emergency"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x014f
            java.lang.String r9 = "emergency"
            org.json.JSONObject r9 = r7.getJSONObject(r9)     // Catch:{ Exception -> 0x03de }
            aue$a r11 = new aue$a     // Catch:{ Exception -> 0x03de }
            r11.<init>()     // Catch:{ Exception -> 0x03de }
            java.lang.String r12 = "state"
            int r12 = r9.optInt(r12)     // Catch:{ Exception -> 0x03de }
            r11.a = r12     // Catch:{ Exception -> 0x03de }
            java.lang.String r12 = "description"
            java.lang.String r9 = r9.optString(r12)     // Catch:{ Exception -> 0x03de }
            r11.b = r9     // Catch:{ Exception -> 0x03de }
            r8.E = r11     // Catch:{ Exception -> 0x03de }
        L_0x014f:
            java.lang.String r9 = "interval"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x015f
            java.lang.String r9 = "interval"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x03de }
            r8.z = r9     // Catch:{ Exception -> 0x03de }
        L_0x015f:
            java.lang.String r9 = "basic_price"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x016f
            java.lang.String r9 = "basic_price"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x03de }
            r8.d = r9     // Catch:{ Exception -> 0x03de }
        L_0x016f:
            java.lang.String r9 = "total_price"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x017f
            java.lang.String r9 = "total_price"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x03de }
            r8.e = r9     // Catch:{ Exception -> 0x03de }
        L_0x017f:
            java.lang.String r9 = "basic_price_air"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x018f
            java.lang.String r9 = "basic_price_air"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x03de }
            r8.f = r9     // Catch:{ Exception -> 0x03de }
        L_0x018f:
            java.lang.String r9 = "total_price_air"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x019f
            java.lang.String r9 = "total_price_air"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x03de }
            r8.g = r9     // Catch:{ Exception -> 0x03de }
        L_0x019f:
            java.lang.String r9 = "length"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x01c5
            java.lang.String r9 = "length"
            java.lang.String r9 = r7.getString(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x01c3
            java.lang.String r11 = r9.trim()     // Catch:{ Exception -> 0x03de }
            java.lang.String r12 = ""
            boolean r11 = r11.equals(r12)     // Catch:{ Exception -> 0x03de }
            if (r11 != 0) goto L_0x01c3
            double r11 = java.lang.Double.parseDouble(r9)     // Catch:{ Exception -> 0x03de }
            int r9 = (int) r11     // Catch:{ Exception -> 0x03de }
            r8.m = r9     // Catch:{ Exception -> 0x03de }
            goto L_0x01c5
        L_0x01c3:
            r8.m = r4     // Catch:{ Exception -> 0x03de }
        L_0x01c5:
            java.lang.String r9 = "is_realtime"
            int r9 = r7.optInt(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 != r10) goto L_0x01cf
            r9 = 1
            goto L_0x01d0
        L_0x01cf:
            r9 = 0
        L_0x01d0:
            r8.B = r9     // Catch:{ Exception -> 0x03de }
            java.lang.String r9 = "irregular_time"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x01e2
            java.lang.String r9 = "irregular_time"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x03de }
            r8.C = r9     // Catch:{ Exception -> 0x03de }
        L_0x01e2:
            java.lang.String r9 = "xs"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x025f
            java.lang.String r9 = "ys"
            boolean r9 = r7.has(r9)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x025f
            java.lang.String r9 = "xs"
            java.lang.String r9 = r7.getString(r9)     // Catch:{ Exception -> 0x03de }
            java.lang.String r11 = "ys"
            java.lang.String r11 = r7.getString(r11)     // Catch:{ Exception -> 0x03de }
            if (r9 == 0) goto L_0x025f
            java.lang.String r12 = r9.trim()     // Catch:{ Exception -> 0x03de }
            java.lang.String r13 = ""
            boolean r12 = r12.equals(r13)     // Catch:{ Exception -> 0x03de }
            if (r12 != 0) goto L_0x025f
            if (r11 == 0) goto L_0x025f
            java.lang.String r12 = r11.trim()     // Catch:{ Exception -> 0x03de }
            java.lang.String r13 = ""
            boolean r12 = r12.equals(r13)     // Catch:{ Exception -> 0x03de }
            if (r12 != 0) goto L_0x025f
            java.lang.String r12 = ","
            java.lang.String[] r9 = r9.split(r12)     // Catch:{ Exception -> 0x03de }
            java.lang.String r12 = ","
            java.lang.String[] r11 = r11.split(r12)     // Catch:{ Exception -> 0x03de }
            int r12 = r9.length     // Catch:{ Exception -> 0x03de }
            int r13 = r9.length     // Catch:{ Exception -> 0x03de }
            int[] r13 = new int[r13]     // Catch:{ Exception -> 0x03de }
            r8.p = r13     // Catch:{ Exception -> 0x03de }
            int r13 = r9.length     // Catch:{ Exception -> 0x03de }
            int[] r13 = new int[r13]     // Catch:{ Exception -> 0x03de }
            r8.q = r13     // Catch:{ Exception -> 0x03de }
            r13 = 0
        L_0x0232:
            if (r13 >= r12) goto L_0x025f
            int r14 = r11.length     // Catch:{ Exception -> 0x03de }
            if (r13 >= r14) goto L_0x0256
            r14 = r9[r13]     // Catch:{ Exception -> 0x03de }
            double r14 = java.lang.Double.parseDouble(r14)     // Catch:{ Exception -> 0x03de }
            r10 = r11[r13]     // Catch:{ Exception -> 0x03de }
            r16 = r5
            double r4 = java.lang.Double.parseDouble(r10)     // Catch:{ Exception -> 0x03de }
            android.graphics.Point r4 = defpackage.cfg.a(r4, r14)     // Catch:{ Exception -> 0x03de }
            int[] r5 = r8.p     // Catch:{ Exception -> 0x03de }
            int r10 = r4.x     // Catch:{ Exception -> 0x03de }
            r5[r13] = r10     // Catch:{ Exception -> 0x03de }
            int[] r5 = r8.q     // Catch:{ Exception -> 0x03de }
            int r4 = r4.y     // Catch:{ Exception -> 0x03de }
            r5[r13] = r4     // Catch:{ Exception -> 0x03de }
            goto L_0x0258
        L_0x0256:
            r16 = r5
        L_0x0258:
            int r13 = r13 + 1
            r5 = r16
            r4 = 0
            r10 = 1
            goto L_0x0232
        L_0x025f:
            r16 = r5
            java.lang.String r4 = "stations"
            boolean r4 = r7.has(r4)     // Catch:{ Exception -> 0x03de }
            if (r4 == 0) goto L_0x03ca
            java.lang.String r4 = "stations"
            org.json.JSONArray r4 = r7.getJSONArray(r4)     // Catch:{ Exception -> 0x03de }
            if (r4 == 0) goto L_0x03ca
            int r5 = r4.length()     // Catch:{ Exception -> 0x03de }
            java.lang.String[] r7 = new java.lang.String[r5]     // Catch:{ Exception -> 0x03de }
            r8.r = r7     // Catch:{ Exception -> 0x03de }
            int[] r7 = new int[r5]     // Catch:{ Exception -> 0x03de }
            r8.s = r7     // Catch:{ Exception -> 0x03de }
            int[] r7 = new int[r5]     // Catch:{ Exception -> 0x03de }
            r8.t = r7     // Catch:{ Exception -> 0x03de }
            java.lang.String[] r7 = new java.lang.String[r5]     // Catch:{ Exception -> 0x03de }
            r8.v = r7     // Catch:{ Exception -> 0x03de }
            long[] r7 = new long[r5]     // Catch:{ Exception -> 0x03de }
            r8.u = r7     // Catch:{ Exception -> 0x03de }
            int[] r7 = new int[r5]     // Catch:{ Exception -> 0x03de }
            r8.w = r7     // Catch:{ Exception -> 0x03de }
            java.lang.String[] r7 = new java.lang.String[r5]     // Catch:{ Exception -> 0x03de }
            r8.x = r7     // Catch:{ Exception -> 0x03de }
            java.lang.String[] r7 = new java.lang.String[r5]     // Catch:{ Exception -> 0x03de }
            r8.y = r7     // Catch:{ Exception -> 0x03de }
            r7 = 0
        L_0x0296:
            if (r7 >= r5) goto L_0x03ca
            org.json.JSONObject r9 = r4.getJSONObject(r7)     // Catch:{ Exception -> 0x03de }
            java.lang.String[] r10 = r8.r     // Catch:{ Exception -> 0x03de }
            java.lang.String r11 = "name"
            java.lang.String r11 = r9.getString(r11)     // Catch:{ Exception -> 0x03de }
            r10[r7] = r11     // Catch:{ Exception -> 0x03de }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x03de }
            r10.<init>()     // Catch:{ Exception -> 0x03de }
            java.lang.String r11 = "code"
            long r11 = r9.getLong(r11)     // Catch:{ Exception -> 0x03de }
            r10.append(r11)     // Catch:{ Exception -> 0x03de }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x03de }
            r8.c = r10     // Catch:{ Exception -> 0x03de }
            java.lang.String r10 = "xy_coords"
            java.lang.String r10 = r9.getString(r10)     // Catch:{ Exception -> 0x03de }
            java.lang.String r11 = ";"
            java.lang.String[] r10 = r10.split(r11)     // Catch:{ Exception -> 0x03de }
            int r11 = r10.length     // Catch:{ Exception -> 0x03de }
            r12 = 2
            if (r11 != r12) goto L_0x02e9
            r11 = 0
            r12 = r10[r11]     // Catch:{ Exception -> 0x03de }
            double r12 = java.lang.Double.parseDouble(r12)     // Catch:{ Exception -> 0x03de }
            r14 = 1
            r10 = r10[r14]     // Catch:{ Exception -> 0x03de }
            double r14 = java.lang.Double.parseDouble(r10)     // Catch:{ Exception -> 0x03de }
            android.graphics.Point r10 = defpackage.cfg.a(r14, r12)     // Catch:{ Exception -> 0x03de }
            int[] r12 = r8.s     // Catch:{ Exception -> 0x03de }
            int r13 = r10.x     // Catch:{ Exception -> 0x03de }
            r12[r7] = r13     // Catch:{ Exception -> 0x03de }
            int[] r12 = r8.t     // Catch:{ Exception -> 0x03de }
            int r10 = r10.y     // Catch:{ Exception -> 0x03de }
            r12[r7] = r10     // Catch:{ Exception -> 0x03de }
            goto L_0x02ea
        L_0x02e9:
            r11 = 0
        L_0x02ea:
            java.lang.String r10 = "name"
            java.lang.String r10 = r9.getString(r10)     // Catch:{ Exception -> 0x03de }
            java.lang.String[] r12 = r8.r     // Catch:{ Exception -> 0x03de }
            r12[r7] = r10     // Catch:{ Exception -> 0x03de }
            java.lang.String r12 = "station_id"
            java.lang.String r12 = r9.getString(r12)     // Catch:{ Exception -> 0x03de }
            java.lang.String[] r13 = r8.v     // Catch:{ Exception -> 0x03de }
            r13[r7] = r12     // Catch:{ Exception -> 0x03de }
            long[] r13 = r8.u     // Catch:{ Exception -> 0x03de }
            java.lang.String r14 = "code"
            long r14 = r9.optLong(r14)     // Catch:{ Exception -> 0x03de }
            r13[r7] = r14     // Catch:{ Exception -> 0x03de }
            java.lang.String r13 = "subways"
            boolean r13 = r9.has(r13)     // Catch:{ Exception -> 0x03de }
            if (r13 == 0) goto L_0x0381
            java.lang.String r13 = "subways"
            org.json.JSONArray r13 = r9.getJSONArray(r13)     // Catch:{ Exception -> 0x03de }
            if (r13 == 0) goto L_0x0380
            int r14 = r13.length()     // Catch:{ Exception -> 0x03de }
            if (r14 == 0) goto L_0x0380
            if (r13 != 0) goto L_0x0321
            goto L_0x0380
        L_0x0321:
            auf r14 = r8.D     // Catch:{ Exception -> 0x03de }
            if (r14 != 0) goto L_0x032c
            auf r14 = new auf     // Catch:{ Exception -> 0x03de }
            r14.<init>()     // Catch:{ Exception -> 0x03de }
            r8.D = r14     // Catch:{ Exception -> 0x03de }
        L_0x032c:
            r14 = 0
        L_0x032d:
            int r15 = r13.length()     // Catch:{ Exception -> 0x03de }
            if (r14 >= r15) goto L_0x0381
            org.json.JSONObject r15 = r13.getJSONObject(r14)     // Catch:{ Exception -> 0x03de }
            aug r11 = new aug     // Catch:{ Exception -> 0x03de }
            r11.<init>()     // Catch:{ Exception -> 0x03de }
            r11.d = r12     // Catch:{ Exception -> 0x03de }
            r11.c = r10     // Catch:{ Exception -> 0x03de }
            r17 = r3
            java.lang.String r3 = "line_key"
            java.lang.String r3 = r15.getString(r3)     // Catch:{ Exception -> 0x03de }
            r11.b = r3     // Catch:{ Exception -> 0x03de }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x03de }
            r18 = r4
            java.lang.String r4 = "#"
            r3.<init>(r4)     // Catch:{ Exception -> 0x03de }
            java.lang.String r4 = "color"
            java.lang.String r4 = r15.getString(r4)     // Catch:{ Exception -> 0x03de }
            r3.append(r4)     // Catch:{ Exception -> 0x03de }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x03de }
            r11.a = r3     // Catch:{ Exception -> 0x03de }
            auf r3 = r8.D     // Catch:{ Exception -> 0x03de }
            java.util.ArrayList<aug> r3 = r3.a     // Catch:{ Exception -> 0x03de }
            if (r3 != 0) goto L_0x0371
            auf r3 = r8.D     // Catch:{ Exception -> 0x03de }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Exception -> 0x03de }
            r4.<init>()     // Catch:{ Exception -> 0x03de }
            r3.a = r4     // Catch:{ Exception -> 0x03de }
        L_0x0371:
            auf r3 = r8.D     // Catch:{ Exception -> 0x03de }
            java.util.ArrayList<aug> r3 = r3.a     // Catch:{ Exception -> 0x03de }
            r3.add(r11)     // Catch:{ Exception -> 0x03de }
            int r14 = r14 + 1
            r3 = r17
            r4 = r18
            r11 = 0
            goto L_0x032d
        L_0x0380:
            return
        L_0x0381:
            r17 = r3
            r18 = r4
            java.lang.String r3 = "status"
            boolean r3 = r9.has(r3)     // Catch:{ Exception -> 0x03de }
            if (r3 == 0) goto L_0x0399
            int[] r3 = r8.w     // Catch:{ Exception -> 0x03de }
            java.lang.String r4 = "status"
            int r4 = r9.getInt(r4)     // Catch:{ Exception -> 0x03de }
            r3[r7] = r4     // Catch:{ Exception -> 0x03de }
            r4 = 1
            goto L_0x039e
        L_0x0399:
            int[] r3 = r8.w     // Catch:{ Exception -> 0x03de }
            r4 = 1
            r3[r7] = r4     // Catch:{ Exception -> 0x03de }
        L_0x039e:
            java.lang.String r3 = "poiid1"
            boolean r3 = r9.has(r3)     // Catch:{ Exception -> 0x03de }
            if (r3 == 0) goto L_0x03b0
            java.lang.String[] r3 = r8.x     // Catch:{ Exception -> 0x03de }
            java.lang.String r10 = "poiid1"
            java.lang.String r10 = r9.getString(r10)     // Catch:{ Exception -> 0x03de }
            r3[r7] = r10     // Catch:{ Exception -> 0x03de }
        L_0x03b0:
            java.lang.String r3 = "poiid2"
            boolean r3 = r9.has(r3)     // Catch:{ Exception -> 0x03de }
            if (r3 == 0) goto L_0x03c2
            java.lang.String[] r3 = r8.y     // Catch:{ Exception -> 0x03de }
            java.lang.String r10 = "poiid2"
            java.lang.String r9 = r9.getString(r10)     // Catch:{ Exception -> 0x03de }
            r3[r7] = r9     // Catch:{ Exception -> 0x03de }
        L_0x03c2:
            int r7 = r7 + 1
            r3 = r17
            r4 = r18
            goto L_0x0296
        L_0x03ca:
            r17 = r3
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a     // Catch:{ Exception -> 0x03de }
            aus r3 = r3.searchInfo     // Catch:{ Exception -> 0x03de }
            java.util.ArrayList<aue> r3 = r3.r     // Catch:{ Exception -> 0x03de }
            r3.add(r8)     // Catch:{ Exception -> 0x03de }
            int r6 = r6 + 1
            r5 = r16
            r3 = r17
            r4 = 0
            goto L_0x0069
        L_0x03de:
            r0 = move-exception
            r2 = r0
            defpackage.kf.a(r2)
            return
        L_0x03e4:
            java.lang.String r3 = "bus_list"
            boolean r3 = r2.has(r3)
            if (r3 == 0) goto L_0x052c
            java.lang.String r3 = "bus_list"
            org.json.JSONArray r2 = r2.getJSONArray(r3)     // Catch:{ JSONException -> 0x0526 }
            if (r2 != 0) goto L_0x03f5
            return
        L_0x03f5:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r3 = r3.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.s
            if (r3 != 0) goto L_0x040a
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r3 = r1.a
            aus r3 = r3.searchInfo
            java.util.ArrayList r4 = new java.util.ArrayList
            r5 = 10
            r4.<init>(r5)
            r3.s = r4
        L_0x040a:
            int r3 = r2.length()
            r4 = 0
        L_0x040f:
            if (r4 >= r3) goto L_0x052c
            com.autonavi.common.model.POI r5 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()
            r6 = 0
            org.json.JSONObject r7 = r2.getJSONObject(r4)     // Catch:{ JSONException -> 0x041c }
            r6 = r7
            goto L_0x0420
        L_0x041c:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0420:
            if (r6 != 0) goto L_0x0423
            return
        L_0x0423:
            java.lang.String r7 = "id"
            boolean r7 = r6.has(r7)
            if (r7 == 0) goto L_0x0434
            java.lang.String r7 = "id"
            java.lang.String r7 = r6.optString(r7)
            r5.setId(r7)
        L_0x0434:
            java.lang.String r7 = "name"
            boolean r7 = r6.has(r7)
            if (r7 == 0) goto L_0x0445
            java.lang.String r7 = "name"
            java.lang.String r7 = r6.optString(r7)
            r5.setName(r7)
        L_0x0445:
            java.lang.String r7 = "address"
            boolean r7 = r6.has(r7)
            if (r7 == 0) goto L_0x0456
            java.lang.String r7 = "address"
            java.lang.String r7 = r6.optString(r7)
            r5.setAddr(r7)
        L_0x0456:
            java.lang.String r7 = "businfo_line_keys"
            boolean r7 = r6.has(r7)
            if (r7 == 0) goto L_0x04a8
            java.lang.String r7 = "businfo_line_keys"
            java.lang.String r7 = r6.optString(r7)
            java.lang.String r8 = ";|\\|"
            java.lang.String[] r7 = r7.split(r8)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            if (r7 == 0) goto L_0x04a8
            java.util.HashMap r9 = new java.util.HashMap
            r9.<init>()
            r10 = 0
        L_0x0477:
            int r11 = r7.length
            if (r10 >= r11) goto L_0x0498
            r11 = r7[r10]
            boolean r11 = r9.containsKey(r11)
            if (r11 != 0) goto L_0x0495
            r11 = r7[r10]
            r12 = r7[r10]
            r9.put(r11, r12)
            if (r10 == 0) goto L_0x0490
            java.lang.String r11 = "/"
            r8.append(r11)
        L_0x0490:
            r11 = r7[r10]
            r8.append(r11)
        L_0x0495:
            int r10 = r10 + 1
            goto L_0x0477
        L_0x0498:
            r9.clear()
            java.util.HashMap r7 = r5.getPoiExtra()
            java.lang.String r9 = "station_lines"
            java.lang.String r8 = r8.toString()
            r7.put(r9, r8)
        L_0x04a8:
            java.lang.String r7 = "x"
            boolean r7 = r6.has(r7)
            r8 = 0
            if (r7 == 0) goto L_0x04b9
            java.lang.String r7 = "x"
            double r10 = r6.optDouble(r7)
            goto L_0x04ba
        L_0x04b9:
            r10 = r8
        L_0x04ba:
            java.lang.String r7 = "y"
            boolean r7 = r6.has(r7)
            if (r7 == 0) goto L_0x04c8
            java.lang.String r7 = "y"
            double r8 = r6.optDouble(r7)
        L_0x04c8:
            com.autonavi.common.model.GeoPoint r7 = r5.getPoint()
            r7.setLonLat(r10, r8)
            java.lang.String r7 = "areacode"
            boolean r7 = r6.has(r7)
            if (r7 == 0) goto L_0x04e0
            java.lang.String r7 = "areacode"
            java.lang.String r7 = r6.optString(r7)
            r5.setAdCode(r7)
        L_0x04e0:
            java.lang.String r7 = "businfo_lineids"
            boolean r7 = r6.has(r7)
            if (r7 == 0) goto L_0x04f7
            java.util.HashMap r7 = r5.getPoiExtra()
            java.lang.String r8 = "businfo_lineids"
            java.lang.String r9 = "businfo_lineids"
            java.lang.String r9 = r6.optString(r9)
            r7.put(r8, r9)
        L_0x04f7:
            java.lang.String r7 = "end_poi_extension"
            boolean r7 = r6.has(r7)
            if (r7 == 0) goto L_0x0508
            java.lang.String r7 = "end_poi_extension"
            java.lang.String r7 = r6.optString(r7)
            r5.setEndPoiExtension(r7)
        L_0x0508:
            java.lang.String r7 = "transparent"
            boolean r7 = r6.has(r7)
            if (r7 == 0) goto L_0x0519
            java.lang.String r7 = "transparent"
            java.lang.String r6 = r6.optString(r7)
            r5.setTransparent(r6)
        L_0x0519:
            com.autonavi.bundle.entity.infolite.internal.InfoliteResult r6 = r1.a
            aus r6 = r6.searchInfo
            java.util.ArrayList<com.autonavi.common.model.POI> r6 = r6.s
            r6.add(r5)
            int r4 = r4 + 1
            goto L_0x040f
        L_0x0526:
            r0 = move-exception
            r2 = r0
            defpackage.kf.a(r2)
            return
        L_0x052c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.els.d(org.json.JSONObject):void");
    }

    private static int a(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return -1;
        }
        try {
            if (!jSONObject.has(str)) {
                return -1;
            }
            String string = jSONObject.getString(str);
            if (!TextUtils.isEmpty(string)) {
                return Integer.parseInt(string);
            }
            return -1;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return -1;
        }
    }

    private static void a(JSONObject jSONObject, ArrayList<String> arrayList, ArrayList<CitySuggestion> arrayList2) {
        if (jSONObject != null && arrayList != null && arrayList2 != null) {
            try {
                arrayList.clear();
                arrayList2.clear();
                JSONObject optJSONObject = jSONObject.optJSONObject("suggestion");
                if (optJSONObject != null) {
                    JSONArray optJSONArray = optJSONObject.optJSONArray("keywords");
                    if (optJSONArray != null && optJSONArray.length() > 0) {
                        int length = optJSONArray.length();
                        for (int i = 0; i < length; i++) {
                            arrayList.add(optJSONArray.optString(i));
                        }
                    }
                    JSONArray optJSONArray2 = optJSONObject.optJSONArray("regions");
                    if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                        for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                            JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                            CitySuggestion citySuggestion = new CitySuggestion();
                            citySuggestion.name = optJSONObject2.optString("name");
                            citySuggestion.ename = optJSONObject2.optString("ename");
                            citySuggestion.adcode = optJSONObject2.optString(AutoJsonUtils.JSON_ADCODE);
                            citySuggestion.citycode = optJSONObject2.optString("areacode");
                            citySuggestion.resultnum = optJSONObject2.optInt("total");
                            arrayList2.add(citySuggestion);
                        }
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    private static void b(SearchPoi searchPoi, JSONObject jSONObject) {
        try {
            if (jSONObject.has("parkinfo")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("parkinfo");
                Iterator<String> keys = jSONObject2.keys();
                if (keys != null) {
                    while (keys.hasNext()) {
                        String next = keys.next();
                        String string = jSONObject2.getString(next);
                        HashMap<String, Serializable> poiExtra = searchPoi.getPoiExtra();
                        StringBuilder sb = new StringBuilder();
                        sb.append("parkinfo");
                        sb.append("_");
                        sb.append(next);
                        poiExtra.put(sb.toString(), string);
                        if ("inout_info".equals(next)) {
                            JSONArray jSONArray = jSONObject2.getJSONArray("inout_info");
                            if (jSONArray != null) {
                                for (int i = 0; i < jSONArray.length(); i++) {
                                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                                    if (jSONObject3 != null) {
                                        String e = agd.e(jSONObject3, "keytype");
                                        if ("2".equals(e) || "0".equals(e)) {
                                            try {
                                                Point a2 = bbw.a(jSONObject3.getDouble(DictionaryKeys.CTRLXY_Y), jSONObject3.getDouble(DictionaryKeys.CTRLXY_X));
                                                if (searchPoi.getEntranceList() == null) {
                                                    searchPoi.setEntranceList(new ArrayList());
                                                }
                                                searchPoi.getEntranceList().add(new GeoPoint(a2.x, a2.y));
                                            } catch (Exception e2) {
                                                kf.a((Throwable) e2);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e3) {
            kf.a((Throwable) e3);
        }
    }

    private static void c(SearchPoi searchPoi, JSONObject jSONObject) throws JSONException {
        if (jSONObject.has("stations") && !TextUtils.isEmpty(jSONObject.optString("stations")) && !jSONObject.optString("stations").equals("null")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("stations");
            if (jSONObject2.has("businfo_line_keys")) {
                String[] split = jSONObject2.getString("businfo_line_keys").split(";|\\|");
                StringBuilder sb = new StringBuilder();
                if (split != null) {
                    HashMap hashMap = new HashMap();
                    for (int i = 0; i < split.length; i++) {
                        if (!hashMap.containsKey(split[i])) {
                            hashMap.put(split[i], split[i]);
                            if (i != 0) {
                                sb.append("/");
                            }
                            sb.append(split[i]);
                        }
                    }
                    hashMap.clear();
                    searchPoi.getPoiExtra().put("station_lines", sb.toString());
                }
            }
            if (jSONObject2.has("businfo_lineids")) {
                searchPoi.getPoiExtra().put("businfo_lineids", (String) jSONObject2.get("businfo_lineids"));
            }
        }
    }

    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.util.Collection] */
    /* JADX WARNING: type inference failed for: r4v3, types: [java.util.Collection<? extends com.autonavi.common.model.POI>] */
    /* JADX WARNING: type inference failed for: r4v4, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r4v10, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], java.util.List, java.util.Collection<? extends com.autonavi.common.model.POI>]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], java.util.Collection, java.util.List]
      mth insns count: 93
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void d(com.autonavi.bundle.entity.common.searchpoi.SearchPoi r7, org.json.JSONObject r8) throws org.json.JSONException {
        /*
            java.lang.String r0 = "child_stations"
            boolean r0 = r8.has(r0)
            if (r0 == 0) goto L_0x0135
            java.lang.String r0 = "child_stations"
            java.lang.String r0 = r8.optString(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0135
            java.lang.String r0 = "child_stations"
            java.lang.String r0 = r8.optString(r0)
            java.lang.String r1 = "null"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0135
            java.lang.String r0 = "child_stations"
            org.json.JSONArray r8 = r8.getJSONArray(r0)
            org.json.JSONArray r0 = new org.json.JSONArray
            r0.<init>()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = 0
        L_0x0033:
            int r3 = r8.length()
            r4 = 0
            r5 = 1
            if (r2 >= r3) goto L_0x00ce
            org.json.JSONObject r3 = r8.getJSONObject(r2)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r6 = "businfo_lineids"
            java.lang.String r6 = r3.optString(r6)     // Catch:{ Exception -> 0x00c6 }
            r1.append(r6)     // Catch:{ Exception -> 0x00c6 }
            int r6 = r8.length()     // Catch:{ Exception -> 0x00c6 }
            int r6 = r6 - r5
            if (r2 >= r6) goto L_0x0054
            r5 = 59
            r1.append(r5)     // Catch:{ Exception -> 0x00c6 }
        L_0x0054:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r5 = r7.getPoiChildrenInfo()     // Catch:{ Exception -> 0x00c6 }
            if (r5 == 0) goto L_0x0062
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r4 = r7.getPoiChildrenInfo()     // Catch:{ Exception -> 0x00c6 }
            java.util.Collection<? extends com.autonavi.common.model.POI> r4 = r4.stationList     // Catch:{ Exception -> 0x00c6 }
            java.util.List r4 = (java.util.List) r4     // Catch:{ Exception -> 0x00c6 }
        L_0x0062:
            if (r4 == 0) goto L_0x00c2
            int r5 = r4.size()     // Catch:{ Exception -> 0x00c6 }
            int r6 = r2 + -1
            if (r5 < r6) goto L_0x00c2
            java.lang.Object r4 = r4.get(r2)     // Catch:{ Exception -> 0x00c6 }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r4 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r4     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "businfo_line_key"
            java.lang.String r6 = r4.getAddr()     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "x"
            com.autonavi.common.model.GeoPoint r6 = r4.getPoint()     // Catch:{ Exception -> 0x00c6 }
            int r6 = r6.x     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "y"
            com.autonavi.common.model.GeoPoint r6 = r4.getPoint()     // Catch:{ Exception -> 0x00c6 }
            int r6 = r6.y     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "name"
            java.lang.String r6 = r7.getName()     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "poiid2"
            java.lang.String r6 = r7.getId()     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "poiid"
            java.lang.String r6 = r4.getPoiId()     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = r7.getId()     // Catch:{ Exception -> 0x00c6 }
            r4.setPoiId2(r5)     // Catch:{ Exception -> 0x00c6 }
            java.util.HashMap r4 = r4.getPoiExtra()     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "businfo_lineids"
            java.lang.String r6 = "businfo_lineids"
            java.lang.String r6 = r3.optString(r6)     // Catch:{ Exception -> 0x00c6 }
            r4.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
        L_0x00c2:
            r0.put(r2, r3)     // Catch:{ Exception -> 0x00c6 }
            goto L_0x00ca
        L_0x00c6:
            r3 = move-exception
            r3.printStackTrace()
        L_0x00ca:
            int r2 = r2 + 1
            goto L_0x0033
        L_0x00ce:
            java.lang.String r8 = r1.toString()
            java.util.HashMap r1 = r7.getPoiExtra()
            java.lang.String r2 = "businfo_lineids"
            boolean r1 = r1.containsKey(r2)
            if (r1 != 0) goto L_0x00ed
            boolean r1 = android.text.TextUtils.isEmpty(r8)
            if (r1 != 0) goto L_0x00ed
            java.util.HashMap r1 = r7.getPoiExtra()
            java.lang.String r2 = "businfo_lineids"
            r1.put(r2, r8)
        L_0x00ed:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r8 = r7.getPoiChildrenInfo()
            int r8 = r8.childType
            if (r8 != r5) goto L_0x0135
            java.util.HashMap r8 = r7.getPoiExtra()
            java.lang.String r1 = "child_stations"
            java.lang.String r2 = r0.toString()
            r8.put(r1, r2)
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r8 = r7.getPoiChildrenInfo()
            if (r8 == 0) goto L_0x010e
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r8 = r7.getPoiChildrenInfo()
            java.util.Collection<? extends com.autonavi.common.model.POI> r4 = r8.stationList
        L_0x010e:
            if (r4 == 0) goto L_0x0135
            java.util.Iterator r8 = r4.iterator()
        L_0x0114:
            boolean r1 = r8.hasNext()
            if (r1 == 0) goto L_0x0135
            java.lang.Object r1 = r8.next()
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r1 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r1
            java.util.HashMap r2 = r1.getPoiExtra()
            java.lang.String r3 = "child_stations"
            java.lang.String r4 = r0.toString()
            r2.put(r3, r4)
            java.lang.String r2 = r7.getName()
            r1.setName(r2)
            goto L_0x0114
        L_0x0135:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.els.d(com.autonavi.bundle.entity.common.searchpoi.SearchPoi, org.json.JSONObject):void");
    }

    private static void e(SearchPoi searchPoi, JSONObject jSONObject) {
        String optString = jSONObject.optString("cpdata");
        if (!TextUtils.isEmpty(optString)) {
            String[] split = optString.split("\\|");
            ArrayList arrayList = new ArrayList();
            for (String split2 : split) {
                String[] split3 = split2.split(";");
                if (split3.length > 1) {
                    CpData cpData = new CpData();
                    cpData.setCpid(split3[0]);
                    cpData.setSource(split3[1]);
                    arrayList.add(cpData);
                }
            }
            searchPoi.getPoiExtra().put("Cpdata", JSONEncoder.encode(arrayList));
            return;
        }
        searchPoi.getPoiExtra().remove("Cpdata");
    }
}
