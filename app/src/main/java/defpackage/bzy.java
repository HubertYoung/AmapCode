package defpackage;

import android.graphics.Rect;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bl.search.OfflineParam;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.Callback.a;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.search.model.SearchConst.SearchFor;

/* renamed from: bzy reason: default package */
/* compiled from: SearchPageRequestBuilder */
public final class bzy {
    private bck a = ((bck) a.a.a(bck.class));

    public final bbq a(String str, String str2, String str3, Rect rect) {
        InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), str, rect);
        a2.search_operate = 1;
        a2.transfer_mode = str2;
        a2.sc_stype = str3;
        if (this.a == null) {
            return a();
        }
        bcl bcl = new bcl();
        bcl.c = str;
        bcl.b = 0;
        bcl.a = SearchFor.DEFAULT;
        bcl.i = false;
        return this.a.a(a2, bcl);
    }

    public final bbq a(String str, String str2, TipItem tipItem, Rect rect) {
        if (this.a == null) {
            return a();
        }
        bcl bcl = new bcl();
        bcl.c = str;
        bcl.b = 0;
        bcl.a = SearchFor.DEFAULT;
        bcl.d = tipItem;
        bcl.e = true;
        bcl.i = false;
        InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), str, rect);
        a2.city = str2;
        a2.cur_adcode = str2;
        a2.transparent = tipItem.transparent;
        return this.a.a(a2, bcl);
    }

    public final bbq a(TipItem tipItem, Rect rect) {
        if (this.a == null) {
            return a();
        }
        InfoliteParam a2 = bbv.a(AppManager.getInstance().getUserLocInfo(), tipItem.name, tipItem.poiid);
        a2.geoobj = bbw.a(rect);
        a2.sugpoiname = tipItem.name;
        a2.sugadcode = tipItem.adcode;
        a2.superid = SuperId.getInstance().getScenceId();
        a2.offline_param = new OfflineParam();
        a2.offline_param.longitude = tipItem.x;
        a2.offline_param.latitude = tipItem.y;
        bcl bcl = new bcl();
        bcl.c = tipItem.name;
        bcl.b = 0;
        bcl.a = SearchFor.DEFAULT;
        bcl.d = tipItem;
        bcl.i = false;
        bcl.f = a2;
        return this.a.a(a2, bcl);
    }

    private bbq a() {
        return new bbq((a) new a() {
            public final void cancel() {
            }

            public final boolean isCancelled() {
                return false;
            }
        });
    }
}
