package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus;
import com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.c;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b;
import com.autonavi.map.search.manager.SearchResultTipDetailViewManager.DetailLayerState;
import com.autonavi.minimap.ajx3.modules.ModuleAMap;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AttributeListener;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.json.JSONObject;

/* renamed from: bqg reason: default package */
/* compiled from: SearchIdqMaxAjxHandler */
public final class bqg implements bzn, bzp {
    bzo a;
    public ccs b;
    Context c;
    final double d;
    final double e;
    private a f;
    private bqf g;
    private c h = new c() {
        public final void a(String str) {
            if (!TextUtils.isEmpty(str)) {
                bqg.this.a.c().a(str);
            }
        }

        public final void a() {
            bqg.this.a.i_();
        }
    };
    private com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.a i = new com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.a() {
        public final void a(String str, String str2) {
        }

        public final void a(String str, String str2, boolean z) {
        }

        public final void a(int i) {
            bqg.this.a.a(bqg.this.e >= (bqg.this.d * 0.35d) - ((double) DimensionUtils.standardUnitToPixel(bqg.this.c, (float) i)));
        }
    };

    /* renamed from: bqg$a */
    /* compiled from: SearchIdqMaxAjxHandler */
    public class a extends defpackage.cbl.a {
        public a() {
        }

        @SuppressFBWarnings({"UW_UNCOND_WAIT", "WA_NOT_IN_LOOP"})
        public final void a(boolean z) {
            super.a(z);
            bqg.this.a.a((bzn) bqg.this);
            bqg.this.a.c().a(this.s);
            bqg.this.a.c().b(0);
            bqg.this.a(true);
        }
    }

    public final void a(cbr cbr) {
    }

    public final String d() {
        return "invalid";
    }

    public final boolean e() {
        return false;
    }

    public final void f() {
    }

    public final void g() {
    }

    public final int h() {
        return 0;
    }

    public bqg(bzo bzo) {
        this.a = bzo;
        this.c = bzo.c().a().getContext();
        this.d = (double) ccd.a(this.c);
        this.e = ((double) ccd.a(this.c)) * 0.08d;
    }

    public final a a(InfoliteResult infoliteResult, POI poi, PageBundle pageBundle) {
        if (this.g != null) {
            return this.f;
        }
        try {
            this.g = new bqf(this.a.c().a());
            this.g.g = this.b;
            JSONObject jSONObject = new JSONObject();
            if (infoliteResult != null) {
                jSONObject.put("oriResponse", infoliteResult.originalJson);
            }
            if (poi != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("lat", poi.getPoint().getLatitude());
                jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, poi.getPoint().getLongitude());
                jSONObject.put("centerPoint", jSONObject2);
            }
            this.f = new a();
            this.f.o = 8;
            POI poi2 = infoliteResult.searchInfo.l.get(0);
            pageBundle.putObject("POI", poi2);
            this.f.t = new bxw(poi2);
            a aVar = this.f;
            bqf bqf = this.g;
            FrameLayout b2 = this.a.b();
            bxw bxw = this.f.t;
            bqf.f = b2;
            bqf.f.addView(bqf.a);
            bqf.a.attachPage(bqf.i.getPageContext());
            bqf.a.setAttributeListener(new AttributeListener() {
                public final boolean handleAttr(String str, Object obj) {
                    ModuleIdqPlus moduleIdqPlus = (ModuleIdqPlus) bqf.this.a.getJsModule(ModuleIdqPlus.MODULE_NAME);
                    int i = 0;
                    if (moduleIdqPlus != null) {
                        if ("IDQMAX_TOP".equalsIgnoreCase(str)) {
                            if (obj instanceof Float) {
                                i = Math.round(((Float) obj).floatValue());
                            }
                            moduleIdqPlus.onTopHeightChange(i);
                            return true;
                        } else if ("AMapControlOpacity".equalsIgnoreCase(str)) {
                            bqf.this.g.setViewAlpha(obj instanceof Float ? ((Float) obj).floatValue() : 1.0f, 0);
                        }
                    }
                    ModuleAMap moduleAMap = (ModuleAMap) bqf.this.a.getJsModule(ModuleAMap.MODULE_NAME);
                    if (moduleAMap != null && "AmapBottomLine".equalsIgnoreCase(str)) {
                        float floatValue = obj instanceof Float ? ((Float) obj).floatValue() : 0.0f;
                        bqf.this.h = (int) floatValue;
                        if (bqf.this.a.getVisibility() == 0) {
                            moduleAMap.bottomLine(floatValue, 0);
                        }
                    }
                    return false;
                }
            });
            bqf.a.onAjxContextCreated(bqf.k);
            bqf.a.setAjxFragmentResultExecutor(bxw);
            bqf.a.setBackCallBack(bqf.j);
            bqf.a.load("path://amap_bundle_idqmax/src/pages/SearchIdqMaxPage.page.js", jSONObject, "idqmax", null);
            bqf.a.setVisibility(0);
            aVar.q = bqf.a;
            this.f.t.a((AbstractBaseMapPage) this.a.c().a(), pageBundle);
            this.g.a(pageBundle);
            bqf bqf2 = this.g;
            com.autonavi.bundle.searchresult.ajx.ModuleIdqPlus.a aVar2 = this.i;
            if (bqf2.a() == null) {
                bqf2.c = aVar2;
            } else {
                bqf2.a().setOnStateChangeListener(aVar2);
            }
            bqf bqf3 = this.g;
            c cVar = this.h;
            if (bqf3.a() == null) {
                bqf3.d = cVar;
            } else {
                bqf3.a().setOverlayEventListener(cVar);
            }
            return this.f;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final boolean a() {
        bqf bqf = this.g;
        return bqf.a != null && bqf.a.backPressed();
    }

    public final void a(AbstractBasePage abstractBasePage, int i2, ResultType resultType, PageBundle pageBundle) {
        JsAdapter b2 = this.g.b();
        if (b2 != null) {
            this.f.t.onFragmentResult(abstractBasePage, i2, resultType, pageBundle, b2);
        }
    }

    public final Point b() {
        int height = ags.a(this.c).height();
        int width = ags.a(this.c).width();
        if (height < width) {
            width = ags.a(this.c).height();
        }
        int bottom = this.a.f_().getBottom();
        return new Point(width / 2, ((this.a.f().getTop() - bottom) / 2) + bottom);
    }

    public final DetailLayerState c() {
        return DetailLayerState.COLLAPSED;
    }

    public final void a(boolean z) {
        this.f.r = z;
        bqf bqf = this.g;
        int i2 = z ? 0 : 8;
        if (bqf.a != null) {
            bqf.a.setVisibility(i2);
        }
    }

    public final void a(ViewGroup viewGroup) {
        cbl e_ = this.a.e_();
        if (e_ != null && viewGroup != null) {
            e_.a((ViewGroup) null, e_.b());
            b bVar = new b(this);
            bVar.o = 0;
            e_.a((defpackage.cbl.a) bVar, viewGroup);
        }
    }

    public final void i() {
        if (this.f == null || this.f.t == null) {
            this.g.a((String) null);
            return;
        }
        this.g.a(this.f.t.a);
        this.f.t.a = null;
    }

    public final void j() {
        bqf bqf = this.g;
        if (bqf.a != null) {
            bqf.a.onPause(false);
        }
    }

    public final void k() {
        bqf bqf = this.g;
        if (bqf.a != null) {
            bqf.a.onDestroy();
        }
    }

    public final ModuleIdqPlus l() {
        if (this.g != null) {
            return this.g.a();
        }
        return null;
    }

    public final void a(b bVar) {
        if (bVar.o == 0) {
            this.a.h_();
        }
    }

    public final void m() {
        this.a.c().a().finish();
    }

    public final int n() {
        if (this.g != null) {
            return this.g.h;
        }
        return 0;
    }
}
