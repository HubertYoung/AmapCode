package defpackage;

import android.graphics.PointF;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import org.json.JSONObject;

/* renamed from: hr reason: default package */
/* compiled from: RectangleShape */
public final class hr implements hn {
    public final String a;
    public final hj<PointF, PointF> b;
    public final hc c;
    public final gy d;

    /* renamed from: hr$a */
    /* compiled from: RectangleShape */
    static class a {
        static hr a(JSONObject jSONObject, ev evVar) {
            hr hrVar = new hr(jSONObject.optString(LogItem.MM_C18_K4_NM), hb.a(jSONObject.optJSONObject(SuperId.BIT_1_MAIN_VOICE_ASSISTANT), evVar), defpackage.hc.a.a(jSONObject.optJSONObject("s"), evVar), defpackage.gy.a.a(jSONObject.optJSONObject(UploadQueueMgr.MSGTYPE_REALTIME), evVar, true), 0);
            return hrVar;
        }
    }

    /* synthetic */ hr(String str, hj hjVar, hc hcVar, gy gyVar, byte b2) {
        this(str, hjVar, hcVar, gyVar);
    }

    private hr(String str, hj<PointF, PointF> hjVar, hc hcVar, gy gyVar) {
        this.a = str;
        this.b = hjVar;
        this.c = hcVar;
        this.d = gyVar;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new fp(ewVar, hxVar, this);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("RectangleShape{cornerRadius=");
        sb.append((Float) this.d.b);
        sb.append(", position=");
        sb.append(this.b);
        sb.append(", size=");
        sb.append(this.c);
        sb.append('}');
        return sb.toString();
    }
}
