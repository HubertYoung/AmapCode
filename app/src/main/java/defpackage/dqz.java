package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.jni.ae.gmap.scenic.Label3rd;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.sketchscenic.ScenicGuidePoiEntity;
import com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: dqz reason: default package */
/* compiled from: ScenicGuideLayer */
public final class dqz extends BaseLayer {
    public PointOverlay<drb> a;
    public ConcurrentHashMap<String, ArrayList<Label3rd>> b = new ConcurrentHashMap<>();
    public List<ScenicGuidePoi> c = new ArrayList();
    @NonNull
    private final Context d;
    @NonNull
    private final bck e;

    public dqz(@NonNull IVPageContext iVPageContext, @NonNull bck bck) {
        super(iVPageContext);
        this.d = iVPageContext.getContext();
        this.e = bck;
        this.a = new PointOverlay<>(this, "GGC");
        this.a.setVisible(true);
    }

    public final void clear() {
        super.clear();
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final void a(String str, List<ScenicGuidePoiEntity> list, bty bty, String str2, ScenicGuidePoi scenicGuidePoi) {
        List<ScenicGuidePoiEntity> list2 = list;
        String str3 = str2;
        if (this.c != null) {
            this.c.clear();
        }
        int i = 0;
        boolean z = true;
        LogManager.actionLogV25("P00383", "B021", new SimpleEntry("type", str3), new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, str));
        while (i < list.size()) {
            ScenicGuidePoi scenicGuidePoi2 = (ScenicGuidePoi) POIFactory.createPOI(ScenicGuidePoi.class);
            scenicGuidePoi2.setAddr(list2.get(i).c);
            scenicGuidePoi2.setName(list2.get(i).b);
            scenicGuidePoi2.setPid(list2.get(i).a);
            scenicGuidePoi2.setId(list2.get(i).a);
            scenicGuidePoi2.setIconType(str3);
            scenicGuidePoi2.setIsScenicGuidePoi(z);
            scenicGuidePoi2.setClearFocus(list2.get(i).a);
            double doubleValue = Double.valueOf(list2.get(i).e).doubleValue();
            double doubleValue2 = Double.valueOf(list2.get(i).f).doubleValue();
            scenicGuidePoi2.setPoint(new GeoPointHD(doubleValue, doubleValue2));
            if (this.c != null) {
                this.c.add(scenicGuidePoi2);
            }
            drb drb = new drb(scenicGuidePoi2);
            int i2 = R.drawable.icon_default;
            int i3 = R.drawable.icon_default_selected;
            if (!TextUtils.isEmpty(str2)) {
                StringBuilder sb = new StringBuilder("icon_");
                sb.append(str2.toLowerCase());
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder("icon_");
                sb3.append(str2.toLowerCase());
                sb3.append("_selected");
                String sb4 = sb3.toString();
                int a2 = bby.a(sb2, this.d);
                int a3 = bby.a(sb4, this.d);
                if (a2 != 0) {
                    i2 = a2;
                }
                if (a3 != 0) {
                    i3 = a3;
                }
            }
            drb.defaultTexture = makeTextureParam(i2, 0.5f, 0.87f);
            drb.focusTexture = makeTextureParam(i3, 0.5f, 0.87f);
            drb.coord = new Coord(doubleValue, doubleValue2);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(drb.getID());
            scenicGuidePoi2.setIdentityId(sb5.toString());
            this.a.addItem(drb);
            if (scenicGuidePoi != null && TextUtils.equals(scenicGuidePoi2.getPid(), scenicGuidePoi.getPid())) {
                this.a.setFocus(drb.getID());
            }
            i++;
            z = true;
        }
        a(str, list, bty);
    }

    private void a(String str, List<ScenicGuidePoiEntity> list, bty bty) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Label3rd label3rd = new Label3rd();
            label3rd.mLabelName = list.get(i).b;
            GeoPointHD geoPointHD = new GeoPointHD(Double.valueOf(list.get(i).e).doubleValue(), Double.valueOf(list.get(i).f).doubleValue());
            label3rd.mP20X = geoPointHD.x;
            label3rd.mP20Y = geoPointHD.y;
            String str2 = list.get(i).k;
            if (!TextUtils.isEmpty(str2) && (str2.startsWith("0x") || str2.startsWith("0X"))) {
                label3rd.mAnchor = (int) dri.b(str2.substring(2));
            }
            label3rd.mPoiId = list.get(i).a;
            label3rd.mRank = (float) dri.a(list.get(i).i);
            label3rd.mSubkey = dri.a(list.get(i).h);
            label3rd.mMainkey = dri.a(list.get(i).g);
            label3rd.mMinzoom = dri.a(list.get(i).j);
            arrayList.add(label3rd);
        }
        if (arrayList.size() > 0) {
            this.b.put(str, arrayList);
            a(arrayList, bty);
        }
    }

    public static void a(ArrayList<Label3rd> arrayList, bty bty) {
        bty.a(20190000, (Label3rd[]) arrayList.toArray(new Label3rd[arrayList.size()]), true);
    }
}
