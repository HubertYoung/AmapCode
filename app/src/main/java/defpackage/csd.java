package defpackage;

import com.amap.bundle.statistics.LogManager;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.LineOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.LineOverlay.LineType;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PolygonOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.LineOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PolygonOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.Arrays;

/* renamed from: csd reason: default package */
/* compiled from: TrafficAffectLayer */
public final class csd extends BaseLayer {
    private LineOverlay a = new LineOverlay(this, "TrafficAffectStroke");
    private LineOverlay b = new LineOverlay(this, "TrafficAffectLine");
    private PolygonOverlay c = new PolygonOverlay(this, "TrafficAffectPolygon");

    public csd(IVPageContext iVPageContext) {
        super(iVPageContext);
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final void clear() {
        super.clear();
    }

    public final void a(bsa bsa) {
        LineType lineType;
        if (bsa.a != null && bsa.a.a()) {
            a aVar = bsa.a;
            ArrayList<ArrayList<GeoPoint>> arrayList = aVar.b;
            int i = aVar.c;
            int i2 = aVar.d;
            boolean z = aVar.e;
            if (arrayList != null && arrayList.size() > 0) {
                if (z) {
                    clear();
                }
                if (!(this.a == null || arrayList == null || arrayList.size() <= 0)) {
                    for (int i3 = 0; i3 < arrayList.size(); i3++) {
                        ArrayList arrayList2 = arrayList.get(i3);
                        int size = arrayList2.size();
                        Coord[] coordArr = new Coord[size];
                        for (int i4 = 0; i4 < size; i4++) {
                            Coord coord = new Coord();
                            coord.lon = ((GeoPoint) arrayList2.get(i4)).getLongitude();
                            coord.lat = ((GeoPoint) arrayList2.get(i4)).getLatitude();
                            coordArr[i4] = coord;
                        }
                        LineOverlayItem lineOverlayItem = new LineOverlayItem(LineType.LineTypeColor);
                        lineOverlayItem.points.addAll(Arrays.asList(coordArr));
                        lineOverlayItem.fillColor = i;
                        lineOverlayItem.setFillTextureResource(R.drawable.map_lr);
                        this.a.addItem(lineOverlayItem);
                    }
                }
                if (!(this.c == null || arrayList == null)) {
                    for (int i5 = 0; i5 < arrayList.size(); i5++) {
                        ArrayList arrayList3 = arrayList.get(i5);
                        Coord[] coordArr2 = new Coord[arrayList3.size()];
                        int size2 = arrayList3.size();
                        for (int i6 = 0; i6 < size2; i6++) {
                            Coord coord2 = new Coord();
                            coord2.lon = ((GeoPoint) arrayList3.get(i6)).getLongitude();
                            coord2.lat = ((GeoPoint) arrayList3.get(i6)).getLatitude();
                            coordArr2[i6] = coord2;
                        }
                        PolygonOverlayItem polygonOverlayItem = new PolygonOverlayItem();
                        polygonOverlayItem.fillColor = i2;
                        polygonOverlayItem.points.addAll(Arrays.asList(coordArr2));
                        this.c.addItem(polygonOverlayItem);
                    }
                }
            }
            if (aVar.a) {
                LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B006");
            }
        }
        if (bsa.b != null && bsa.b.a()) {
            b bVar = bsa.b;
            ArrayList<ArrayList<GeoPoint>> arrayList4 = bVar.a;
            int i7 = bVar.d;
            boolean z2 = bVar.b;
            int i8 = bVar.c;
            if (arrayList4 != null && arrayList4.size() > 0) {
                for (int i9 = 0; i9 < arrayList4.size(); i9++) {
                    ArrayList arrayList5 = arrayList4.get(i9);
                    int size3 = arrayList5.size();
                    Coord[] coordArr3 = new Coord[size3];
                    for (int i10 = 0; i10 < size3; i10++) {
                        Coord coord3 = new Coord();
                        coord3.lon = ((GeoPoint) arrayList5.get(i10)).getLongitude();
                        coord3.lat = ((GeoPoint) arrayList5.get(i10)).getLatitude();
                        coordArr3[i10] = coord3;
                    }
                    if (!z2) {
                        lineType = LineType.LineTypeColor;
                    } else {
                        lineType = LineType.LineTypeDotColor;
                    }
                    LineOverlayItem lineOverlayItem2 = new LineOverlayItem(lineType);
                    lineOverlayItem2.points.addAll(Arrays.asList(coordArr3));
                    lineOverlayItem2.lineWidth = i7;
                    lineOverlayItem2.borderWidth = i7;
                    lineOverlayItem2.fillColor = i8;
                    if (!z2) {
                        lineOverlayItem2.setFillTextureResource(R.drawable.map_lr);
                    } else {
                        lineOverlayItem2.setFillTextureResource(R.drawable.ic_cross_road_dash);
                    }
                    this.b.addItem(lineOverlayItem2);
                }
            }
        }
    }
}
