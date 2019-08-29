package defpackage;

import android.support.annotation.NonNull;
import android.util.Pair;
import com.autonavi.bundle.amaphome.vui.VoiceOperationManagerImpl$1;
import com.autonavi.bundle.amaphome.vui.VoiceOperationManagerImpl$2;
import com.autonavi.bundle.amaphome.vui.VoiceOperationManagerImpl$3;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.mapinterface.IMapRequestManager;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.sdk.location.LocationInstrument;
import com.tencent.connect.common.Constants;
import com.uc.webview.export.internal.SDKFactory;
import java.util.ArrayList;
import java.util.List;

/* renamed from: arv reason: default package */
/* compiled from: VoiceOperationManagerImpl */
public class arv implements apu {
    private static List<Pair<String, Object>> a;

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(int r3, com.autonavi.common.model.POI r4) {
        /*
            r2 = this;
            java.lang.Class<com> r0 = defpackage.com.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com r0 = (defpackage.com) r0
            if (r0 == 0) goto L_0x0019
            java.lang.String r1 = r0.a()
            cop r0 = r0.b(r1)
            if (r0 == 0) goto L_0x0019
            r0.f(r4)
            r4 = 1
            goto L_0x001a
        L_0x0019:
            r4 = 0
        L_0x001a:
            if (r4 == 0) goto L_0x002d
            ku r4 = defpackage.ku.a()
            java.lang.String r0 = "voiceoperation"
            java.lang.String r1 = "updateHomePOI success"
            r4.a(r0, r1)
            r4 = 10000(0x2710, float:1.4013E-41)
            b(r3, r4)
            return
        L_0x002d:
            ku r4 = defpackage.ku.a()
            java.lang.String r0 = "voiceoperation"
            java.lang.String r1 = "updateHomePOI failed"
            r4.a(r0, r1)
            r4 = 10020(0x2724, float:1.4041E-41)
            b(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.arv.a(int, com.autonavi.common.model.POI):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(int r3, com.autonavi.common.model.POI r4) {
        /*
            r2 = this;
            java.lang.Class<com> r0 = defpackage.com.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com r0 = (defpackage.com) r0
            if (r0 == 0) goto L_0x0019
            java.lang.String r1 = r0.a()
            cop r0 = r0.b(r1)
            if (r0 == 0) goto L_0x0019
            r0.e(r4)
            r4 = 1
            goto L_0x001a
        L_0x0019:
            r4 = 0
        L_0x001a:
            if (r4 == 0) goto L_0x002d
            ku r4 = defpackage.ku.a()
            java.lang.String r0 = "voiceoperation"
            java.lang.String r1 = "updateCompanyPOI success"
            r4.a(r0, r1)
            r4 = 10000(0x2710, float:1.4013E-41)
            b(r3, r4)
            return
        L_0x002d:
            ku r4 = defpackage.ku.a()
            java.lang.String r0 = "voiceoperation"
            java.lang.String r1 = "updateCompanyPOI success"
            r4.a(r0, r1)
            r4 = 10020(0x2724, float:1.4041E-41)
            b(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.arv.b(int, com.autonavi.common.model.POI):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void c(int r3, com.autonavi.common.model.POI r4) {
        /*
            r2 = this;
            java.lang.Class<com> r0 = defpackage.com.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com r0 = (defpackage.com) r0
            if (r0 == 0) goto L_0x0019
            java.lang.String r1 = r0.a()
            cop r0 = r0.b(r1)
            if (r0 == 0) goto L_0x0019
            r0.b(r4)
            r4 = 1
            goto L_0x001a
        L_0x0019:
            r4 = 0
        L_0x001a:
            if (r4 == 0) goto L_0x0022
            r4 = 10000(0x2710, float:1.4013E-41)
            b(r3, r4)
            return
        L_0x0022:
            r4 = 10020(0x2724, float:1.4041E-41)
            b(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.arv.c(int, com.autonavi.common.model.POI):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0022  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(int r3) {
        /*
            r2 = this;
            java.lang.Class<com.autonavi.minimap.bundle.maphome.service.IMainMapService> r0 = com.autonavi.minimap.bundle.maphome.service.IMainMapService.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com.autonavi.minimap.bundle.maphome.service.IMainMapService r0 = (com.autonavi.minimap.bundle.maphome.service.IMainMapService) r0
            if (r0 == 0) goto L_0x0019
            java.lang.Class<czn> r1 = defpackage.czn.class
            czi r0 = r0.a(r1)
            czn r0 = (defpackage.czn) r0
            if (r0 == 0) goto L_0x0019
            boolean r0 = r0.voiceOpenTraffic()
            goto L_0x001a
        L_0x0019:
            r0 = 0
        L_0x001a:
            if (r0 == 0) goto L_0x0022
            r0 = 10000(0x2710, float:1.4013E-41)
            b(r3, r0)
            return
        L_0x0022:
            r0 = 10020(0x2724, float:1.4041E-41)
            b(r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.arv.a(int):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(int r4) {
        /*
            r3 = this;
            java.lang.Class<com.autonavi.minimap.bundle.maphome.service.IMainMapService> r0 = com.autonavi.minimap.bundle.maphome.service.IMainMapService.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com.autonavi.minimap.bundle.maphome.service.IMainMapService r0 = (com.autonavi.minimap.bundle.maphome.service.IMainMapService) r0
            if (r0 == 0) goto L_0x0019
            java.lang.Class<czn> r1 = defpackage.czn.class
            czi r0 = r0.a(r1)
            czn r0 = (defpackage.czn) r0
            if (r0 == 0) goto L_0x0019
            boolean r0 = r0.voiceCloseTraffic()
            goto L_0x001a
        L_0x0019:
            r0 = 0
        L_0x001a:
            if (r0 == 0) goto L_0x002d
            ku r0 = defpackage.ku.a()
            java.lang.String r1 = "voiceoperation"
            java.lang.String r2 = "closeTraffic success"
            r0.a(r1, r2)
            r0 = 10000(0x2710, float:1.4013E-41)
            b(r4, r0)
            return
        L_0x002d:
            ku r0 = defpackage.ku.a()
            java.lang.String r1 = "voiceoperation"
            java.lang.String r2 = "closeTraffic failed"
            r0.a(r1, r2)
            r0 = 10020(0x2724, float:1.4041E-41)
            b(r4, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.arv.b(int):void");
    }

    public final void c(int i) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            czn czn = (czn) iMainMapService.a(czn.class);
            if (czn != null) {
                b(i, czn.voiceZoomInMainMap());
            } else {
                b(i, (int) SDKFactory.getCoreType);
            }
        } else {
            b(i, (int) SDKFactory.getCoreType);
        }
    }

    public final void d(int i) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            czn czn = (czn) iMainMapService.a(czn.class);
            if (czn != null) {
                b(i, czn.voiceZoomOutMainMap());
            } else {
                b(i, (int) SDKFactory.getCoreType);
            }
        } else {
            b(i, (int) SDKFactory.getCoreType);
        }
    }

    public final void a(int i, float f) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            czn czn = (czn) iMainMapService.a(czn.class);
            if (czn != null) {
                a(i, czn.voiceZoomInDiffMainMap(f), a(czn.voiceMixMaxZoom()));
            } else {
                b(i, (int) SDKFactory.getCoreType);
            }
        } else {
            b(i, (int) SDKFactory.getCoreType);
        }
    }

    public final void b(int i, float f) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            czn czn = (czn) iMainMapService.a(czn.class);
            if (czn != null) {
                a(i, czn.voiceZoomOutDiffMainMap(f), a(czn.voiceMixMaxZoom()));
            } else {
                b(i, (int) SDKFactory.getCoreType);
            }
        } else {
            b(i, (int) SDKFactory.getCoreType);
        }
    }

    private static List<Pair<String, Object>> a(float[] fArr) {
        if (a == null) {
            a = new ArrayList();
        } else {
            a.clear();
        }
        if (fArr.length > 1) {
            a.add(new Pair("mixlevel", Float.valueOf(fArr[0])));
            a.add(new Pair("maxlevel", Float.valueOf(fArr[1])));
            a.add(new Pair("zoomlevel", Float.valueOf(fArr[2])));
        }
        return a;
    }

    public final void a(int i, int i2) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            czn czn = (czn) iMainMapService.a(czn.class);
            if (czn != null) {
                czn.moveMapView(i, i2);
            }
        }
    }

    public final void f(int i) {
        ku.a().a((String) "voiceoperation", (String) "getMyLocationAndOpenMainMapCard");
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition == null) {
            LocationInstrument.getInstance().addStatusCallback(new VoiceOperationManagerImpl$2(this, i), null);
        } else {
            a(i, latestPosition, true);
        }
    }

    /* access modifiers changed from: private */
    @NonNull
    public void a(int i, GeoPoint geoPoint, boolean z) {
        IMapRequestManager iMapRequestManager = (IMapRequestManager) ank.a(IMapRequestManager.class);
        if (iMapRequestManager != null) {
            iMapRequestManager.getReverseGeocodeResult(geoPoint, new VoiceOperationManagerImpl$3(this, i, z));
        }
    }

    public final void e(int i) {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (latestPosition == null) {
            LocationInstrument.getInstance().addStatusCallback(new VoiceOperationManagerImpl$1(this, i), null);
        } else {
            a(i, latestPosition, false);
        }
    }

    /* access modifiers changed from: private */
    public static void b(int i, int i2) {
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.a(i, i2, (Pair<String, Object>) null);
        }
    }

    private static void a(int i, int i2, List<Pair<String, Object>> list) {
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.a(i, i2, list);
        }
    }

    public static /* synthetic */ void a(int i, Pair pair) {
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.a(i, 10000, pair);
        }
    }

    public static /* synthetic */ void a() {
        ku.a().a((String) "voiceoperation", (String) "openMainMapCard");
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString(Constants.KEY_ACTION, "action_move_to_current_no_3d");
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage((String) "amap.basemap.action.default_page", pageBundle);
        }
    }
}
