package defpackage;

import android.graphics.PointF;
import android.support.annotation.Nullable;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.mobilecommon.multimedia.api.cache.APCacheInfo;
import com.alipay.mobile.h5container.api.H5Param;
import com.autonavi.common.SuperId;
import java.util.Collections;
import org.json.JSONObject;

/* renamed from: hi reason: default package */
/* compiled from: AnimatableTransform */
public final class hi implements hn {
    public final hb a;
    public final hj<PointF, PointF> b;
    public final hd c;
    public final gy d;
    public final ha e;
    @Nullable
    public final gy f;
    @Nullable
    public final gy g;

    /* renamed from: hi$a */
    /* compiled from: AnimatableTransform */
    public static class a {
        public static hi a() {
            hi hiVar = new hi(new hb(), new hb(), new hd(0), defpackage.gy.a.a(), new ha(0), defpackage.gy.a.a(), defpackage.gy.a.a(), 0);
            return hiVar;
        }

        public static hi a(JSONObject jSONObject, ev evVar) {
            hb hbVar;
            hj hjVar;
            hd hdVar;
            gy gyVar;
            ha haVar;
            JSONObject optJSONObject = jSONObject.optJSONObject("a");
            if (optJSONObject != null) {
                hbVar = new hb(optJSONObject.opt(SuperId.BIT_1_REALTIMEBUS_BUSSTATION), evVar);
            } else {
                hbVar = new hb();
            }
            hb hbVar2 = hbVar;
            JSONObject optJSONObject2 = jSONObject.optJSONObject(SuperId.BIT_1_MAIN_VOICE_ASSISTANT);
            if (optJSONObject2 != null) {
                hjVar = hb.a(optJSONObject2, evVar);
            } else {
                a("position");
                hjVar = null;
            }
            JSONObject optJSONObject3 = jSONObject.optJSONObject("s");
            if (optJSONObject3 != null) {
                a a = hk.a(optJSONObject3, 1.0f, evVar, defpackage.gw.a.a).a();
                hdVar = new hd(a.a, (gw) a.b);
            } else {
                hdVar = new hd(Collections.emptyList(), new gw());
            }
            JSONObject optJSONObject4 = jSONObject.optJSONObject(UploadQueueMgr.MSGTYPE_REALTIME);
            if (optJSONObject4 == null) {
                optJSONObject4 = jSONObject.optJSONObject("rz");
            }
            if (optJSONObject4 != null) {
                gyVar = defpackage.gy.a.a(optJSONObject4, evVar, false);
            } else {
                a(APCacheInfo.EXTRA_ROTATION);
                gyVar = null;
            }
            JSONObject optJSONObject5 = jSONObject.optJSONObject("o");
            if (optJSONObject5 != null) {
                haVar = defpackage.ha.a.a(optJSONObject5, evVar);
            } else {
                haVar = new ha(Collections.emptyList(), Integer.valueOf(100));
            }
            ha haVar2 = haVar;
            JSONObject optJSONObject6 = jSONObject.optJSONObject(H5Param.SHOW_OPTION_MENU);
            gy a2 = optJSONObject6 != null ? defpackage.gy.a.a(optJSONObject6, evVar, false) : null;
            JSONObject optJSONObject7 = jSONObject.optJSONObject("eo");
            hi hiVar = new hi(hbVar2, hjVar, hdVar, gyVar, haVar2, a2, optJSONObject7 != null ? defpackage.gy.a.a(optJSONObject7, evVar, false) : null, 0);
            return hiVar;
        }

        private static void a(String str) {
            throw new IllegalArgumentException("Missing transform for ".concat(String.valueOf(str)));
        }
    }

    @Nullable
    public final fe a(ew ewVar, hx hxVar) {
        return null;
    }

    /* synthetic */ hi(hb hbVar, hj hjVar, hd hdVar, gy gyVar, ha haVar, gy gyVar2, gy gyVar3, byte b2) {
        this(hbVar, hjVar, hdVar, gyVar, haVar, gyVar2, gyVar3);
    }

    private hi(hb hbVar, hj<PointF, PointF> hjVar, hd hdVar, gy gyVar, ha haVar, @Nullable gy gyVar2, @Nullable gy gyVar3) {
        this.a = hbVar;
        this.b = hjVar;
        this.c = hdVar;
        this.d = gyVar;
        this.e = haVar;
        this.f = gyVar2;
        this.g = gyVar3;
    }

    public final gj a() {
        return new gj(this);
    }
}
