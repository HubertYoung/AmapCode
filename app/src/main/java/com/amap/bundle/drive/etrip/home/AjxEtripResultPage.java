package com.amap.bundle.drive.etrip.home;

import com.amap.bundle.drive.ajx.module.ModuleRouteEtrip;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import java.util.List;
import org.json.JSONObject;

public class AjxEtripResultPage extends Ajx3Page implements bgm, bgo {
    private static final String a = "AjxEtripResultPage";
    private int b;
    private IRouteUI c;
    /* access modifiers changed from: private */
    public ModuleRouteEtrip d;
    private acg e;

    public void finishSelf() {
    }

    public bgo getPresenter() {
        return this;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 137438953472L;
    }

    public boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public void pageCreated() {
        super.pageCreated();
        this.e = (acg) a.a.a(acg.class);
    }

    public void onJsBack(Object obj, String str) {
        StringBuilder sb = new StringBuilder("jsBack: object ");
        sb.append(obj);
        sb.append(" pageID ");
        sb.append(str);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, obj);
        setResult(ResultType.OK, pageBundle);
        if (this.c instanceof AbstractBasePresenter) {
            ((AbstractBasePresenter) this.c).onBackPressed();
        } else {
            finish();
        }
    }

    public void loadJs() {
        this.b = getActivity().getWindow().getAttributes().softInputMode;
        pageCreated();
        this.mAjxView.onAjxContextCreated(new Callback<AmapAjxView>() {
            public void error(Throwable th, boolean z) {
            }

            public void callback(AmapAjxView amapAjxView) {
                AjxEtripResultPage.this.d = (ModuleRouteEtrip) AjxEtripResultPage.this.mAjxView.getJsModule(ModuleRouteEtrip.MODULE_NAME);
            }
        });
        this.mAjxView.setOnUiLoadCallback(new Callback<AmapAjxView>() {
            public void error(Throwable th, boolean z) {
            }

            public void callback(AmapAjxView amapAjxView) {
                AjxEtripResultPage.this.a();
            }
        });
        super.loadJs(0, 0);
    }

    public void resume() {
        super.resume();
        if (this.ajxPageStateInvoker.getAppSwitch()) {
            setSoftInputMode(16);
        }
        this.e.a((acy) new acy() {
            public final void onDataChange(POI poi, List<POI> list, POI poi2) {
                if (AjxEtripResultPage.this.d != null) {
                    boolean z = !bnx.a(poi2, AjxEtripResultPage.this.d.getEndPoi());
                    if ((!bnx.a(poi, AjxEtripResultPage.this.d.getStartPoi())) || z) {
                        AjxEtripResultPage.this.a();
                    }
                }
            }
        });
        if (this.c == null) {
            this.c = ((axd) getContentView().getParent()).getRouteInputUI();
        } else if (this.c.o() && this.d != null) {
            this.d.resetStartEndPoint();
        }
        a();
        this.e.a(this.e.f());
        this.e.b(this.e.h());
    }

    public void pause() {
        super.pause();
        if (this.b != 0) {
            setSoftInputMode(this.b);
        }
    }

    public void destroy() {
        super.destroy();
        if (this.c != null) {
            this.c.a((axe) null);
        }
    }

    public boolean backPressed() {
        return super.backPressed();
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.d != null) {
            this.d.requestRouteResult(this.e.f(), this.e.h());
            this.d.setCompassAngle(b());
        }
    }

    private int b() {
        MapManager mapManager = getMapManager();
        if (mapManager == null || mapManager.getOverlayManager() == null) {
            return 0;
        }
        return mapManager.getOverlayManager().getGpsAngle();
    }
}
