package defpackage;

import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.gmap.scenic.Label3rd;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.mat.hprof.AbstractParser.Constants.DumpSegment;

/* renamed from: cce reason: default package */
/* compiled from: SearchDataRender */
public final class cce {
    public boolean a = false;
    public List<POI> b;
    public bty c = null;

    public cce(bty bty) {
        this.c = bty;
    }

    public static boolean a(bty bty, List<POI> list) {
        return (bty == null || list == null || list.size() <= 0) ? false : true;
    }

    public static boolean a(bty bty, Label3rd[] label3rdArr, int i) {
        if (label3rdArr != null && label3rdArr.length > 0) {
            bty.a(i, label3rdArr, false);
        }
        return true;
    }

    public final void a(List<POI> list, int i, int i2) {
        if (90000 == i) {
            this.b = list;
        }
        if (a(this.c, this.b)) {
            a(this.c, b(list, i, i2), i);
        }
    }

    public final void a(int i) {
        if (a(this.c, this.b)) {
            this.c.b(90000, false);
            a(this.c, b(this.b, 90000, i), 90000);
        }
    }

    public final void a(aus aus, int i, int i2) {
        if (this.c != null && aus != null && aus.l != null) {
            ArrayList<POI> arrayList = aus.l;
            if (arrayList.size() != 0) {
                POI poi = null;
                if (i >= 0 && i < arrayList.size()) {
                    poi = arrayList.get(i);
                }
                if (poi != null && -1 == i2) {
                    aum recommendMode = ((SearchPoi) poi.as(SearchPoi.class)).getRecommendMode();
                    if (!(recommendMode == null || recommendMode.b == null || recommendMode.b.size() <= 0)) {
                        b(3);
                        a(recommendMode.b);
                    }
                }
            }
        }
    }

    private void a(List<aut> list) {
        if (this.c != null && list.size() != 0) {
            this.c.a(3, b(list), false);
        }
    }

    private static Label3rd[] b(List<aut> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        int size = list.size();
        Label3rd[] label3rdArr = new Label3rd[size];
        for (int i = 0; i < size; i++) {
            aut aut = list.get(i);
            label3rdArr[i] = new Label3rd();
            label3rdArr[i].mPoiId = aut.d;
            label3rdArr[i].mP20X = aut.b;
            label3rdArr[i].mP20Y = aut.c;
            label3rdArr[i].mAnchor = aut.e;
            label3rdArr[i].mLabelName = aut.a;
            label3rdArr[i].mRank = aut.j;
            label3rdArr[i].mMainkey = aut.f;
            label3rdArr[i].mSubkey = aut.g;
            label3rdArr[i].mMaxzoom = aut.i;
            label3rdArr[i].mMinzoom = aut.h;
        }
        return label3rdArr;
    }

    public final void a(POI poi, int i, int i2) {
        if (this.c != null || !a(poi)) {
            a(i, true);
            Label3rd b2 = b(poi, i, i2);
            if (b2 != null) {
                this.c.a(i, new Label3rd[]{b2}, true);
            }
        }
    }

    public final void b(int i) {
        if (this.c != null) {
            this.c.b(i, true);
            this.c.b(i, false);
        }
    }

    public final void a(int i, boolean z) {
        if (this.c != null) {
            this.c.b(i, z);
        }
    }

    public final void a() {
        if (this.c != null) {
            this.c.b(90000, true);
            this.c.b(2, true);
            this.c.b(3, true);
        }
    }

    public final void b() {
        if (this.c != null) {
            b(90000);
            b(2);
            b(3);
        }
    }

    private static Label3rd b(POI poi, int i, int i2) {
        if (poi == null) {
            return null;
        }
        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
        if (searchPoi == null || searchPoi.getIDynamicRenderInfo() == null) {
            return null;
        }
        Label3rd label3rd = new Label3rd();
        if (i2 == 1) {
            label3rd.mLabelName = searchPoi.getName();
            if (i == 2) {
                label3rd.mLabelName = searchPoi.getShortName();
            }
        }
        label3rd.mP20X = searchPoi.getPoint().x;
        label3rd.mP20Y = searchPoi.getPoint().y;
        label3rd.mPoiId = searchPoi.getId();
        label3rd.mAnchor = searchPoi.getIDynamicRenderInfo().anchor;
        if (i == 90000) {
            label3rd.mMainkey = 10010;
            label3rd.mSubkey = DumpSegment.ANDROID_ROOT_JNI_MONITOR;
        } else {
            label3rd.mMainkey = searchPoi.getIDynamicRenderInfo().mainStyle;
            label3rd.mSubkey = searchPoi.getIDynamicRenderInfo().subStyle;
        }
        label3rd.mMinzoom = searchPoi.getIDynamicRenderInfo().minizoom;
        label3rd.mRank = searchPoi.getIDynamicRenderInfo().fRank;
        if (!(poi.getPoiExtra() == null || poi.getPoiExtra().get("poi_deep_info") == null)) {
            ArrayList arrayList = (ArrayList) poi.getPoiExtra().get("poi_deep_info");
            if (arrayList != null) {
                label3rd.mdepthInfoArray = arrayList;
            }
        }
        return label3rd;
    }

    public static Label3rd[] b(List<POI> list, int i, int i2) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (POI b2 : list) {
            arrayList.add(b(b2, i, i2));
        }
        return (Label3rd[]) arrayList.toArray(new Label3rd[arrayList.size()]);
    }

    private static boolean a(POI poi) {
        if (poi == null) {
            return false;
        }
        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
        if (searchPoi == null || (searchPoi.getIDynamicRenderInfo() != null && !searchPoi.getIDynamicRenderInfo().bFlag)) {
            return false;
        }
        return true;
    }
}
