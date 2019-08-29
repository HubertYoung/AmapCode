package defpackage;

import android.text.TextUtils;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;
import com.autonavi.minimap.basemap.multipoint.page.MultiPointMapPage;
import com.autonavi.minimap.basemap.multipoint.page.MultiPointMapPage.MultiPointAdapter;
import com.autonavi.minimap.basemap.multipoint.page.MultiPointMapPage.a;
import com.autonavi.minimap.basemap.multipoint.page.MultiPointMapPage.b;
import java.util.List;

/* renamed from: cqh reason: default package */
/* compiled from: MultiPointMapPresenter */
public final class cqh extends MapBasePresenter<MultiPointMapPage> {
    public int a = -1;

    public final void onPageCreated() {
        super.onPageCreated();
        MultiPointMapPage multiPointMapPage = (MultiPointMapPage) this.mPage;
        PageBundle arguments = ((MultiPointMapPage) this.mPage).getArguments();
        if (arguments == null || !arguments.containsKey("key_multi_points")) {
            multiPointMapPage.a.setVisibility(8);
            return;
        }
        List list = (List) arguments.getObject("key_multi_points");
        if (list == null || list.size() <= 0) {
            multiPointMapPage.a.setVisibility(8);
            return;
        }
        if (arguments.containsKey("key_title")) {
            String string = arguments.getString("key_title");
            if (!TextUtils.isEmpty(string)) {
                multiPointMapPage.d.setText(string);
            }
        }
        if (arguments.containsKey("key_focus_index")) {
            ((cqh) multiPointMapPage.mPresenter).a = arguments.getInt("key_focus_index");
        } else {
            multiPointMapPage.g = true;
            ((cqh) multiPointMapPage.mPresenter).a = 0;
        }
        multiPointMapPage.f.clear();
        multiPointMapPage.f.addAll(list);
        multiPointMapPage.c = new MultiPointAdapter(multiPointMapPage.f);
        multiPointMapPage.a.setAdapter(multiPointMapPage.c);
        multiPointMapPage.b = new b(multiPointMapPage, 0);
        multiPointMapPage.a.setOnPageChangeListener(multiPointMapPage.b);
        multiPointMapPage.c.notifyDataSetChanged();
        multiPointMapPage.getSuspendManager().d().f();
        multiPointMapPage.getSuspendManager().d().a(false);
        multiPointMapPage.e.setMoveToFocus(true);
        multiPointMapPage.e.setOnItemClickListener(new a(multiPointMapPage, 0));
    }

    public final void onStart() {
        super.onStart();
        MultiPointMapPage multiPointMapPage = (MultiPointMapPage) this.mPage;
        if (multiPointMapPage.f != null && multiPointMapPage.f.size() > 0) {
            multiPointMapPage.e.clear();
            for (int i = 0; i < multiPointMapPage.f.size(); i++) {
                multiPointMapPage.e.addItem(new cqf(multiPointMapPage.f.get(i), i));
            }
            if (multiPointMapPage.g) {
                multiPointMapPage.g = false;
                if (multiPointMapPage.f != null && multiPointMapPage.f.size() > 0) {
                    int i2 = 0;
                    int i3 = 0;
                    int i4 = 0;
                    int i5 = 0;
                    for (int i6 = 0; i6 < multiPointMapPage.f.size(); i6++) {
                        GeoPoint point = multiPointMapPage.f.get(i6).getPoint();
                        if (i6 == 0) {
                            i2 = point.x;
                            i3 = point.y;
                            i4 = i2;
                            i5 = i3;
                        } else {
                            i2 = Math.min(i2, point.x);
                            i3 = Math.min(i3, point.y);
                            i4 = Math.max(i4, point.x);
                            i5 = Math.max(i5, point.y);
                        }
                    }
                    if (i2 > 0 && i3 > 0 && i4 > 0 && i5 > 0) {
                        float a2 = multiPointMapPage.getMapView().a(i2, i3, i4, i5);
                        multiPointMapPage.getMapView().f((float) ((int) Math.floor((double) a2)));
                        multiPointMapPage.getMapView().a(i2 + ((i4 - i2) >> 1), i3 + ((i5 - i3) >> 1));
                    }
                }
                if (multiPointMapPage.f.get(0) != null) {
                    multiPointMapPage.e.setFocus(0, false);
                    multiPointMapPage.showViewFooter(multiPointMapPage.a);
                }
                return;
            }
            GeoPoint geoPoint = null;
            int i7 = ((cqh) multiPointMapPage.mPresenter).a;
            if (i7 >= 0) {
                POI poi = multiPointMapPage.f.get(i7);
                if (poi != null) {
                    geoPoint = poi.getPoint();
                    multiPointMapPage.e.setFocus(i7, false);
                    if (multiPointMapPage.a.getCurrentItem() != i7) {
                        multiPointMapPage.b.a = true;
                        multiPointMapPage.a.setCurrentItem(i7, false);
                    }
                    multiPointMapPage.showViewFooter(multiPointMapPage.a);
                }
            } else {
                POI poi2 = multiPointMapPage.f.get(0);
                if (poi2 != null) {
                    geoPoint = poi2.getPoint();
                    multiPointMapPage.dismissViewFooter();
                }
            }
            if (geoPoint != null && !multiPointMapPage.getMapView().H().contains(geoPoint.x, geoPoint.y)) {
                multiPointMapPage.getMapView().a(geoPoint.x, geoPoint.y);
            }
        }
    }

    public final void onStop() {
        super.onStop();
        this.a = ((MultiPointMapPage) this.mPage).e.getLastFocusedIndex();
    }

    public final boolean onFocusClear() {
        MultiPointMapPage multiPointMapPage = (MultiPointMapPage) this.mPage;
        ((cqh) multiPointMapPage.mPresenter).a = -1;
        multiPointMapPage.e.clearFocus();
        multiPointMapPage.dismissViewFooter();
        return true;
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public cqh(MultiPointMapPage multiPointMapPage) {
        super(multiPointMapPage);
    }

    public final ON_BACK_TYPE onBackPressed() {
        return super.onBackPressed();
    }
}
