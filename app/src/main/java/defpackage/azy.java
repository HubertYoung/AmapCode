package defpackage;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseLayer;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.IClickListener;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.PointOverlay;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.BaseOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.OverlayTextureParam;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper;
import com.autonavi.jni.eyrie.amap.redesign.maps.texture.TextureWrapper.Area;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Rect;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.IVPageContext;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import java.util.ArrayList;

/* renamed from: azy reason: default package */
/* compiled from: DriveCommuteTipsGuideLayer */
public final class azy extends BaseLayer {
    private final String a = "DriveCommuteTipsGuideLayer";
    private final String b = "redesign://basemap/RouteCommute/guide/";
    private PointOverlay<bab> c = new PointOverlay<>(this, "Commute");
    private bab d = new bab();
    private View e = LayoutInflater.from(AMapAppGlobal.getApplication()).inflate(R.layout.drive_commute_guide_tips_layout, null);
    private View f = this.e.findViewById(R.id.commute_tips_close);
    private ImageView g = ((ImageView) this.e.findViewById(R.id.commute_tips_icon));
    private TextView h = ((TextView) this.e.findViewById(R.id.commute_tips_title));
    private TextView i = ((TextView) this.e.findViewById(R.id.commute_tips_description));

    /* renamed from: azy$a */
    /* compiled from: DriveCommuteTipsGuideLayer */
    public interface a {
        void a();

        void b();
    }

    public azy(IVPageContext iVPageContext) {
        super(iVPageContext);
        this.c.addItem(this.d);
    }

    public final TextureWrapper loadTexture(OverlayTextureParam overlayTextureParam) {
        AMapLog.d("DriveCommuteTipsGuideLayer", "loadTexture   param:".concat(String.valueOf(overlayTextureParam)));
        if (!overlayTextureParam.uri.startsWith("redesign://basemap/RouteCommute/guide/") || !(overlayTextureParam.data instanceof bab)) {
            return null;
        }
        bab bab = (bab) overlayTextureParam.data;
        this.g.setImageResource(bab.a);
        this.h.setText(bab.b);
        this.i.setText(bab.c);
        View view = this.e;
        this.e.measure(0, 0);
        int measuredWidth = this.e.getMeasuredWidth();
        int measuredHeight = this.e.getMeasuredHeight();
        StringBuilder sb = new StringBuilder("loadTexture   tipViewWidth:");
        sb.append(measuredWidth);
        sb.append("   tipViewHeight:");
        sb.append(measuredHeight);
        AMapLog.d("DriveCommuteTipsGuideLayer", sb.toString());
        this.f.measure(0, 0);
        int measuredWidth2 = this.f.getMeasuredWidth();
        int measuredHeight2 = this.f.getMeasuredHeight();
        StringBuilder sb2 = new StringBuilder("loadTexture   closeViewWidth:");
        sb2.append(measuredWidth2);
        sb2.append("   closeViewHeight:");
        sb2.append(measuredHeight2);
        AMapLog.d("DriveCommuteTipsGuideLayer", sb2.toString());
        ArrayList arrayList = new ArrayList();
        Area area = new Area();
        area.areaId = 0;
        int a2 = agn.a(AMapPageUtil.getAppContext(), 20.0f);
        int a3 = (measuredWidth - agn.a(AMapPageUtil.getAppContext(), 30.0f)) - (measuredWidth2 / 2);
        int i2 = measuredHeight2 / 2;
        StringBuilder sb3 = new StringBuilder("loadTexture   maiView:");
        sb3.append(a2);
        sb3.append("   ");
        sb3.append(a3);
        sb3.append("   ");
        sb3.append(i2);
        sb3.append("   ");
        sb3.append(measuredHeight);
        AMapLog.d("DriveCommuteTipsGuideLayer", sb3.toString());
        area.rect = new Rect((float) a2, (float) a3, (float) i2, (float) measuredHeight);
        arrayList.add(area);
        Area area2 = new Area();
        area2.areaId = 1;
        int i3 = measuredWidth - measuredWidth2;
        StringBuilder sb4 = new StringBuilder("loadTexture   closeView:");
        sb4.append(i3);
        sb4.append("   ");
        sb4.append(measuredWidth);
        sb4.append("   0   ");
        sb4.append(measuredHeight2);
        AMapLog.d("DriveCommuteTipsGuideLayer", sb4.toString());
        area2.rect = new Rect((float) i3, (float) measuredWidth, 0.0f, (float) measuredHeight2);
        arrayList.add(area2);
        if (view == null) {
            return null;
        }
        return makeTextureWrapper(view, arrayList);
    }

    public final void a(int i2, GeoPoint geoPoint) {
        AMapLog.d("DriveCommuteTipsGuideLayer", "updateItem   commuteType:".concat(String.valueOf(i2)));
        if (geoPoint != null) {
            azp b2 = defpackage.azn.a.a.b();
            if (b2 != null) {
                b bVar = b2.a;
                if (bVar != null) {
                    int i3 = 0;
                    Resources resources = AMapAppGlobal.getApplication().getResources();
                    if (i2 == 11) {
                        String string = TextUtils.isEmpty(bVar.a) ? resources.getString(R.string.route_commute_drive_guide_tip_go_company_title) : bVar.a;
                        String string2 = TextUtils.isEmpty(bVar.b) ? resources.getString(R.string.route_commute_drive_guide_tip_go_company_desc) : bVar.b;
                        i3 = R.drawable.drive_commute_tips_work;
                        this.d.a = i3;
                        this.d.b = ayr.a(10, string);
                        this.d.c = ayr.a(12, string2);
                    } else if (i2 == 12) {
                        String string3 = TextUtils.isEmpty(bVar.c) ? resources.getString(R.string.route_commute_drive_guide_tip_go_home_title) : bVar.c;
                        String string4 = TextUtils.isEmpty(bVar.d) ? resources.getString(R.string.route_commute_drive_guide_tip_go_home_desc) : bVar.d;
                        i3 = R.drawable.drive_commute_tips_home;
                        this.d.a = i3;
                        this.d.b = ayr.a(10, string3);
                        this.d.c = ayr.a(12, string4);
                    }
                    if (i3 != 0) {
                        this.d.defaultTexture = makeCustomTextureParam("redesign://basemap/RouteCommute/guide/".concat(String.valueOf(i3)), 0.5f, 1.0f, this.d);
                        this.d.coord = new Coord(geoPoint.getLongitude(), geoPoint.getLatitude());
                        this.c.updateItem(this.d);
                    }
                }
            }
        }
    }

    public final void a(final a aVar) {
        this.c.setClickListener(new IClickListener() {
            public final void onClick(BaseOverlay baseOverlay, BaseOverlayItem baseOverlayItem, int i) {
                AMapLog.d("DriveCommuteTipsGuideLayer", "RouteCommutePointOverlay   onClick  i:".concat(String.valueOf(i)));
                if (aVar != null) {
                    if (i == 0) {
                        aVar.a();
                    } else if (i == 1) {
                        aVar.b();
                    }
                }
            }
        });
    }
}
