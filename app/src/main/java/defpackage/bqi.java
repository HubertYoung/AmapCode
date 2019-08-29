package defpackage;

import android.content.Context;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.model.POI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: bqi reason: default package */
/* compiled from: SearchIdqMaxDataController */
public final class bqi {
    bxi a;
    public bxh b = new bxh();
    public InfoliteResult c;
    public POI d;
    public POI e;
    public Map<String, POI> f = new HashMap();
    public List<POI> g = new ArrayList();

    public bqi(Context context) {
        this.a = new bxi(context);
    }

    public final String a() {
        return (this.c == null || this.c.mWrapper == null || this.c.mWrapper.keywords == null) ? "" : this.c.mWrapper.keywords;
    }
}
