package defpackage;

import android.graphics.Path.FillType;
import android.support.annotation.Nullable;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.autonavi.common.SuperId;
import org.json.JSONObject;

/* renamed from: hu reason: default package */
/* compiled from: ShapeFill */
public final class hu implements hn {
    public final FillType a;
    public final String b;
    @Nullable
    public final gx c;
    @Nullable
    public final ha d;
    private final boolean e;

    /* renamed from: hu$a */
    /* compiled from: ShapeFill */
    static class a {
        static hu a(JSONObject jSONObject, ev evVar) {
            String optString = jSONObject.optString(LogItem.MM_C18_K4_NM);
            JSONObject optJSONObject = jSONObject.optJSONObject(SuperId.BIT_1_NEARBY_SEARCH);
            gx a = optJSONObject != null ? defpackage.gx.a.a(optJSONObject, evVar) : null;
            JSONObject optJSONObject2 = jSONObject.optJSONObject("o");
            hu huVar = new hu(optString, jSONObject.optBoolean("fillEnabled"), jSONObject.optInt(UploadQueueMgr.MSGTYPE_REALTIME, 1) == 1 ? FillType.WINDING : FillType.EVEN_ODD, a, optJSONObject2 != null ? defpackage.ha.a.a(optJSONObject2, evVar) : null, 0);
            return huVar;
        }
    }

    /* synthetic */ hu(String str, boolean z, FillType fillType, gx gxVar, ha haVar, byte b2) {
        this(str, z, fillType, gxVar, haVar);
    }

    private hu(String str, boolean z, FillType fillType, @Nullable gx gxVar, @Nullable ha haVar) {
        this.b = str;
        this.e = z;
        this.a = fillType;
        this.c = gxVar;
        this.d = haVar;
    }

    public final fe a(ew ewVar, hx hxVar) {
        return new fi(ewVar, hxVar, this);
    }

    public final String toString() {
        String str;
        Object obj;
        StringBuilder sb = new StringBuilder("ShapeFill{color=");
        if (this.c == null) {
            str = "null";
        } else {
            str = Integer.toHexString(((Integer) this.c.b()).intValue());
        }
        sb.append(str);
        sb.append(", fillEnabled=");
        sb.append(this.e);
        sb.append(", opacity=");
        if (this.d == null) {
            obj = "null";
        } else {
            obj = (Integer) this.d.b;
        }
        sb.append(obj);
        sb.append('}');
        return sb.toString();
    }
}
