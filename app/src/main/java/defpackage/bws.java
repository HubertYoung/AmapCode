package defpackage;

import android.graphics.Rect;
import android.os.Handler;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.POI;
import java.util.ArrayList;

/* renamed from: bws reason: default package */
/* compiled from: IPoiSearcherDispatch */
public interface bws {
    bbq a(InfoliteParam infoliteParam, Rect rect, boolean z);

    bbq a(String str, String str2, SuperId superId);

    bbq a(String str, boolean z, String str2, SuperId superId, long j);

    void a();

    void a(InfoliteResult infoliteResult, TipItem tipItem);

    void a(InfoliteResult infoliteResult, TipItem tipItem, int i, boolean z);

    void a(InfoliteResult infoliteResult, boolean z, int i);

    void a(InfoliteResult infoliteResult, boolean z, int i, boolean z2, boolean z3, boolean z4, String str);

    void a(POI poi, POI poi2);

    void a(ArrayList<String> arrayList, String str, String str2, Handler handler);

    boolean a(InfoliteResult infoliteResult);

    void b();

    void c();
}
