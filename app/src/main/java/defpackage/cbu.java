package defpackage;

import android.view.View;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.util.TipLogHelper;
import com.autonavi.map.search.tip.SearchPoiTipView.a;

/* renamed from: cbu reason: default package */
/* compiled from: SearchPoiTipBehavior */
public final class cbu {
    public cbp a = new cbp();
    public InfoliteResult b;
    public SearchPoi c;
    public a d;
    public cbr e;
    public a f;
    public bxj g;
    private View h;
    private int i;

    public cbu(View view) {
        this.h = view;
    }

    public final void a(InfoliteResult infoliteResult, SearchPoi searchPoi, int i2) {
        this.b = infoliteResult;
        this.c = searchPoi;
        if (i2 >= 0) {
            if (TipLogHelper.a(searchPoi)) {
                i2 = 0;
            }
            this.i = i2;
        }
    }
}
