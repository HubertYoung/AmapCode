package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController.DetailLayerState;
import com.autonavi.map.fragmentcontainer.page.MapBasePresenter;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.agroup.api.IDataService;
import com.autonavi.minimap.bundle.agroup.api.IDataService.TeamStatus;
import com.autonavi.minimap.bundle.agroup.api.IDataService.a;
import com.autonavi.minimap.bundle.maphome.octactivity.octactivity.RescuePlayManager;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.index.page.DefaultPage;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.oss.OssRequestHolder;
import com.autonavi.minimap.oss.param.MonopolyRescuePlayRequest;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.sdk.location.LocationInstrument;
import com.tencent.connect.common.Constants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

/* renamed from: dld reason: default package */
/* compiled from: DefaultPresenter */
public final class dld extends MapBasePresenter<DefaultPage> implements bgo {
    public static boolean f = false;
    public dlb a;
    public dkx b;
    public boolean c = false;
    public boolean d = false;
    Runnable e = new Runnable() {
        public final void run() {
            if (dld.this.a != null) {
                PageBundle arguments = dld.this.mPage != null ? ((DefaultPage) dld.this.mPage).getArguments() : null;
                if (arguments == null || !arguments.containsKey("key_schema_realtime_bus_show")) {
                    dlb a2 = dld.this.a;
                    Resources resources = AMapAppGlobal.getApplication().getResources();
                    if (!brj.a()) {
                        ToastHelper.showToast(resources.getString(R.string.tip_realtimebus_unsupport));
                    } else if (!brj.b(a2.a.getMapManager().getMapView()) || !brj.a(a2.a.getMapManager().getMapView())) {
                        ToastHelper.showToast(resources.getString(R.string.tip_realtimebus_unsupport));
                    } else {
                        bmn.b();
                        bmn.b(true);
                        a2.a(true, true);
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("status", "open");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LogManager.actionLogV2("P00367", "B004", jSONObject);
                    }
                } else {
                    boolean z = arguments.getBoolean("key_schema_realtime_bus_show");
                    dlb a3 = dld.this.a;
                    Resources resources2 = AMapAppGlobal.getApplication().getResources();
                    if (!brj.a()) {
                        ToastHelper.showToast(resources2.getString(R.string.tip_realtimebus_unsupport));
                    } else if (!brj.b(a3.a.getMapManager().getMapView()) || !brj.a(a3.a.getMapManager().getMapView())) {
                        ToastHelper.showToast(resources2.getString(R.string.tip_realtimebus_unsupport));
                    } else {
                        bmn.b();
                        if (z) {
                            bmn.b(true);
                            a3.a(true, true);
                            JSONObject jSONObject2 = new JSONObject();
                            try {
                                jSONObject2.put("status", "open");
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            LogManager.actionLogV2("P00367", "B004", jSONObject2);
                        }
                    }
                }
            }
        }
    };
    IDataService g = null;
    a h = new a() {
        public final void a() {
            if (dld.this.q != null && dld.this.q.booleanValue()) {
                dld.this.g();
            }
        }

        public final void b() {
            if (dld.this.q != null && dld.this.q.booleanValue()) {
                dld.this.g();
            }
        }
    };
    private Context i;
    private RescuePlayManager j;
    private dla k;
    private boolean l = false;
    private final a m = new a() {
        public final void a() {
            dld dld = dld.this;
            czj d = dld.d();
            if (d != null) {
                d.c();
            }
            if (dld.a != null) {
                dlb dlb = dld.a;
                if (dlb.b != null) {
                    bmn.b();
                    brj.a();
                    dlb.b.b().a(dlb.a, dlb.a.getTipContainer(), false);
                }
            }
        }
    };
    private long n = 0;
    private a o = new a() {
        public final void onMemberLocationInfoChanged() {
        }

        public final void onTeamInfoChanged() {
            dld.this.l();
        }

        public final void onMemberBaseInfoChanged() {
            dld.this.l();
        }

        public final void onTeamStatusChanged(TeamStatus teamStatus) {
            dld.this.l();
        }

        public final void onSuperGroupInfoChanged() {
            dld.this.l();
        }
    };
    private final Object p = new Object();
    /* access modifiers changed from: private */
    public Boolean q = null;

    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public dld(DefaultPage defaultPage) {
        super(defaultPage);
        this.i = defaultPage.getContext();
    }

    public final void onPageCreated() {
        la.e("presenter");
        super.onPageCreated();
        this.k = new dla((bid) this.mPage);
        this.k.b = this.l;
        this.a = new dlb((DefaultPage) this.mPage);
        brm brm = (brm) ank.a(brm.class);
        if (brm != null) {
            brm.a(((DefaultPage) this.mPage).getSuspendManager(), ((DefaultPage) this.mPage).getMapManager());
        }
        this.b = new dkx((DefaultPage) this.mPage);
        Real3DManager.a().c((bro) c());
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            this.g = cuh.l();
            if (this.g != null) {
                this.g.a(this.o);
            }
        }
        bty k2 = k();
        if (k2 != null) {
            k2.a((b) this.h);
        }
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.a(new aih() {
                public final boolean a() {
                    return (dld.this.mPage == null || ((DefaultPage) dld.this.mPage).getCQLayerController() == null || ((DefaultPage) dld.this.mPage).getCQLayerController().getDetailLayerState() != DetailLayerState.COLLAPSED) ? false : true;
                }
            });
        }
        la.e("onPresenterCreated");
        if (this.b != null) {
            dkx dkx = this.b;
            if (dkx.a != null) {
                dkx.a.a();
            }
        }
    }

    public final void onStart() {
        la.e("presenterStart");
        super.onStart();
        if (this.c) {
            Real3DManager.a().c((bro) c());
            this.c = false;
            AMapLog.i("zyl", "onEnterMainMap");
        }
        djk djk = (djk) ank.a(djk.class);
        if (djk != null) {
            djk.a();
        }
        LocationInstrument.getInstance().subscribe(this.i, LOCATION_SCENE.TYPE_MAINMAP);
        dlb dlb = this.a;
        cdd.n().a((ccz) dlb);
        brj.a();
        if (dlb.b != null) {
            bmn.b();
            dlb.b.b().a(dlb.a, dlb.a.getTipContainer(), false);
        }
        MapCustomizeManager mapCustomizeManager = ((DefaultPage) this.mPage).getMapCustomizeManager();
        if (mapCustomizeManager != null) {
            mapCustomizeManager.getMapLayerDialogCustomActions().a = 5;
        }
        if (k() != null) {
            awo awo = (awo) a.a.a(awo.class);
            if (awo != null) {
                awo.a((List<Integer>) awo.j());
            }
        }
        MapSharePreference mapSharePreference = new MapSharePreference((String) "search_history");
        mapSharePreference.putIntValue(SearchHistoryList.SP_KEY_max_display_history_count, 20);
        mapSharePreference.putIntValue(SearchHistoryList.SP_KEY_current_display_history_count, 20);
        dla dla = this.k;
        if (bim.aa().l()) {
            bim.aa().j(false);
        }
        if (bim.aa().k() && bim.aa().h()) {
            bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_failagain_tip), (String) "androidamap://openFeature?featureName=Favorite&sourceApplication=tongbu", 171, (String) "1");
            bim.aa().h(false);
            bim.aa().e(false);
        }
        if (bim.aa().m() && bim.aa().h()) {
            bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_complete_tip), (String) null, 172, (String) "2");
            bim.aa().i(false);
            bim.aa().e(false);
        }
        if (bim.aa().w()) {
            bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_copyhistory_tip), (String) "amapuri://map/showmergedialog", 171, (String) "1");
        }
        if (bim.aa().i()) {
            brn brn = (brn) ank.a(brn.class);
            if (dla.a) {
                if (brn != null) {
                    brn.d();
                }
            } else if (brn != null) {
                brn.e();
            }
            bim.aa().f(false);
            if (dla.b) {
                boolean k2 = bim.aa().k((String) "103");
                awo awo2 = (awo) a.a.a(awo.class);
                if (awo2 != null) {
                    if (k2) {
                        awo2.e();
                    } else {
                        awo2.f();
                    }
                }
            }
        }
        if (bim.aa().j()) {
            dla.a();
            ToastHelper.showToast(DoNotUseTool.getContext().getResources().getString(R.string.sync_loginout_tip));
            bim.aa().g(false);
        }
        bim.aa().a((bis) new bis() {
            public final void a() {
                dla.a();
                ToastHelper.showToast(DoNotUseTool.getContext().getResources().getString(R.string.sync_loginout_tip));
                bim.aa().g(false);
            }
        });
        bim.aa().a((biu) new biu() {
            public final void a() {
                bim.aa().j(false);
            }
        });
        bim.aa().a((biy) new biy() {
            public final void updateSuccess() {
                if (bim.aa().h()) {
                    bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_complete_tip), (String) null, 172, (String) "2");
                    bim.aa().i(false);
                    bim.aa().e(false);
                }
            }
        });
        bim.aa().a((bit) new bit() {
            public final void showDialog() {
                bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_copyhistory_tip), (String) "amapuri://map/showmergedialog", 171, (String) "1");
            }
        });
        bim.aa().a((bix) new bix() {
            public final void a() {
                if (bim.aa().h()) {
                    bim.aa().a(DoNotUseTool.getContext().getResources().getString(R.string.sync_failagain_tip), (String) "androidamap://openFeature?featureName=Favorite&sourceApplication=tongbu", 171, (String) "1");
                    bim.aa().h(false);
                    bim.aa().e(false);
                }
            }
        });
        bim.aa().a((biw) new biw() {
            public final void a() {
                bim.aa().f(false);
                brn brn = (brn) ank.a(brn.class);
                if (dla.this.a) {
                    if (brn != null) {
                        brn.d();
                    }
                } else if (brn != null) {
                    brn.e();
                }
                if (dla.this.b) {
                    boolean k = bim.aa().k((String) "103");
                    awo awo = (awo) a.a.a(awo.class);
                    if (awo != null) {
                        if (k) {
                            awo.e();
                            return;
                        }
                        awo.f();
                    }
                }
            }
        });
        brn brn2 = (brn) ank.a(brn.class);
        if (brn2 != null) {
            FavoriteLayer g2 = brn2.g();
            boolean k3 = bim.aa().k((String) "104");
            this.k.a = k3;
            g2.setVisible(k3);
            brn2.d();
        }
        dfm dfm = (dfm) ank.a(dfm.class);
        if (dfm != null) {
            dfm.a(bim.aa().k((String) UploadConstants.STATUS_FILE_UPLOADING_RETRY));
        }
        if (this.j == null) {
            Activity topActivity = AMapAppGlobal.getTopActivity();
            if (topActivity != null) {
                this.j = new RescuePlayManager(topActivity);
            }
        }
        if (this.j != null) {
            RescuePlayManager rescuePlayManager = this.j;
            if (!rescuePlayManager.a && !rescuePlayManager.b.isShowing()) {
                new Thread(new Runnable(cza.a()) {
                    final /* synthetic */ String a;

                    {
                        this.a = r2;
                    }

                    public final void run() {
                        String str;
                        boolean z;
                        MapSharePreference mapSharePreference = new MapSharePreference((String) "RescuePlayAct");
                        String str2 = this.a;
                        if (TextUtils.isEmpty(str2)) {
                            str = "";
                        } else {
                            int indexOf = str2.indexOf("$");
                            int lastIndexOf = str2.lastIndexOf("$");
                            if (indexOf == -1 || lastIndexOf == -1 || indexOf >= lastIndexOf || lastIndexOf - indexOf != 15) {
                                str = "";
                            } else {
                                String substring = str2.substring(indexOf + 1, lastIndexOf);
                                if (!substring.substring(6, 8).equalsIgnoreCase("gd")) {
                                    str = "";
                                } else {
                                    StringBuilder sb = new StringBuilder("$");
                                    sb.append(substring);
                                    sb.append("$");
                                    str = sb.toString();
                                }
                            }
                        }
                        if (!TextUtils.isEmpty(str)) {
                            Map<String, ?> all = mapSharePreference.sharedPrefs().getAll();
                            if (all != null && all.size() > 0) {
                                Iterator<Entry<String, ?>> it = all.entrySet().iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        if (str.contentEquals((CharSequence) it.next().getKey())) {
                                            z = true;
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (!z && !TextUtils.isEmpty(str)) {
                                    RescuePlayManager.this.a(true);
                                    MonopolyRescuePlayRequest monopolyRescuePlayRequest = new MonopolyRescuePlayRequest();
                                    monopolyRescuePlayRequest.c = str;
                                    OssRequestHolder.getInstance().sendMonopolyRescuePlay(monopolyRescuePlayRequest, new RescuePlayCallback(str));
                                }
                            }
                            z = false;
                            RescuePlayManager.this.a(true);
                            MonopolyRescuePlayRequest monopolyRescuePlayRequest2 = new MonopolyRescuePlayRequest();
                            monopolyRescuePlayRequest2.c = str;
                            OssRequestHolder.getInstance().sendMonopolyRescuePlay(monopolyRescuePlayRequest2, new RescuePlayCallback(str));
                        }
                    }
                }).start();
            }
        }
        if (((DefaultPage) this.mPage).getSuspendManager().h() != null) {
            new Object() {
            };
        }
        la.e("presenterStarted");
    }

    public final void onResume() {
        super.onResume();
        la.e("presenterResume");
        ccy suspendWidgetHelper = ((DefaultPage) this.mPage).getSuspendWidgetHelper();
        if (suspendWidgetHelper != null) {
            suspendWidgetHelper.a(this.m);
        }
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null) {
                dlb.b.b().e();
            }
        }
        if (this.b != null) {
            dkx dkx = this.b;
            if (dkx.a != null) {
                dkx.a.b();
            }
        }
        l();
        a(true);
        la.e("presenterResumed");
    }

    public static void a(String str) {
        brm brm = (brm) ank.a(brm.class);
        if (brm != null) {
            brm.a(str);
        }
    }

    private static void h() {
        brm brm = (brm) ank.a(brm.class);
        if (brm != null) {
            brm.i();
        }
    }

    private dlc i() {
        return ((DefaultPage) this.mPage).b;
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null) {
                dlb.b.b().h();
            }
        }
        if (((DefaultPage) this.mPage).c) {
            ((DefaultPage) this.mPage).f();
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        ON_BACK_TYPE onBackPressed = super.onBackPressed();
        if (!(((DefaultPage) this.mPage).getSuspendManager() == null || i().e == null)) {
            f fVar = i().e.a;
            if (fVar != null && fVar.f()) {
                fVar.d();
                onBackPressed = ON_BACK_TYPE.TYPE_IGNORE;
            }
        }
        if (((DefaultPage) this.mPage).hasViewLayer()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        if (onBackPressed != ON_BACK_TYPE.TYPE_NORMAL) {
            return onBackPressed;
        }
        if (!((DefaultPage) this.mPage).dismissViewFooter()) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        }
        DefaultPage defaultPage = (DefaultPage) this.mPage;
        if (defaultPage.a != null) {
            defaultPage.a.clear();
        }
        j().E();
        if (!(c() == null || c().getOverlayManager() == null)) {
            c().getOverlayManager().clearScenicSelectMapPois();
        }
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public final void a() {
        a((String) "2");
        this.d = false;
        if (this.b != null) {
            this.b.a();
        }
    }

    public final void b() {
        if (this.b != null) {
            this.b.b();
        }
    }

    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            ags.d(this.i);
        }
    }

    private bty j() {
        return ((DefaultPage) this.mPage).getMapView();
    }

    private bty k() {
        return ((DefaultPage) this.mPage).getGLMapView();
    }

    public final MapManager c() {
        return ((DefaultPage) this.mPage).getMapManager();
    }

    @SuppressFBWarnings({"RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT"})
    static czj d() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService == null) {
            return null;
        }
        return (czj) iMainMapService.a(czj.class);
    }

    @SuppressFBWarnings({"RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT"})
    public final boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        czj d2 = d();
        if (d2 != null) {
            d2.a(motionEvent, geoPoint);
        }
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null) {
                awu b2 = dlb.b.b();
                dlb.a.getTipContainer();
                b2.c();
            }
        }
        if (this.a != null) {
            dlb dlb2 = this.a;
            if (dlb2.b != null) {
                awu b3 = dlb2.b.b();
                dlb2.a.getTipContainer();
                b3.b();
            }
        }
        return super.onMapLongPress(motionEvent, geoPoint);
    }

    public final boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null) {
                awu b2 = dlb.b.b();
                dlb.a.getTipContainer();
                b2.b();
            }
        }
        return super.onMapSingleClick(motionEvent, geoPoint);
    }

    public final boolean onMapLevelChange(boolean z) {
        super.onMapLevelChange(z);
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null && brj.a()) {
                awu b2 = dlb.b.b();
                dlb.a.getTipContainer();
                b2.a();
            }
        }
        return true;
    }

    public final boolean onPointOverlayClick(long j2, int i2) {
        super.onPointOverlayClick(j2, i2);
        return true;
    }

    public final void onMapRenderCompleted() {
        super.onMapRenderCompleted();
        la.f();
        cqb cqb = (cqb) ank.a(cqb.class);
        if (cqb != null) {
            cqb.a();
            cqb.a(j());
        }
        if (bny.a) {
            j().f(0);
        }
    }

    public final void e() {
        if (this.a != null) {
            this.a.d();
        }
    }

    public final void f() {
        if (this.a != null) {
            this.a.e();
        }
    }

    @SuppressFBWarnings({"RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT"})
    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null) {
                dlb.b.b().d();
            }
        }
        boolean z = true;
        boolean z2 = this.n == 0 || this.n != motionEvent.getEventTime();
        if (action == 0) {
            if (((DefaultPage) this.mPage).getCQLayerController() == null || !((DefaultPage) this.mPage).getCQLayerController().isShowing() || !(((DefaultPage) this.mPage).getCQLayerController().getMapPointTipView() instanceof AbstractGpsTipView)) {
                z = false;
            }
            if (z && z2) {
                this.d = ((DefaultPage) this.mPage).dismissViewFooter();
            }
        }
        czj d2 = d();
        if (d2 != null) {
            d2.a(motionEvent);
        }
        boolean onMapTouchEvent = super.onMapTouchEvent(motionEvent);
        this.n = motionEvent.getEventTime();
        return onMapTouchEvent;
    }

    public final boolean onMapMotionStop() {
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null) {
                dlb.b.b();
            }
        }
        return super.onMapMotionStop();
    }

    public final void onMapAnimationFinished(aln aln) {
        super.onMapAnimationFinished(aln);
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null) {
                dlb.b.b();
            }
        }
    }

    public final boolean onLabelClick(List<als> list) {
        super.onLabelClick(list);
        ((DefaultPage) this.mPage).getSuspendManager();
        if (list != null && list.size() > 0) {
            boolean z = false;
            als als = list.get(0);
            awo awo = (awo) a.a.a(awo.class);
            if (awo != null) {
                z = awo.d(als.i);
            }
            if (z) {
                j().a(new GLGeoPoint(als.e, als.f));
            }
        }
        return true;
    }

    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 != 82) {
            return super.onKeyDown(i2, keyEvent);
        }
        PageBundle pageBundle = new PageBundle();
        apr apr = (apr) a.a.a(apr.class);
        if (apr != null) {
            apr.b((bid) this.mPage, pageBundle);
        }
        return true;
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        if (((DefaultPage) this.mPage).getCQLayerController() != null) {
            ((DefaultPage) this.mPage).getCQLayerController().collapseCQLayer();
        }
        try {
            String string = pageBundle.getString(Constants.KEY_ACTION);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("action", string);
            Set<String> keySet = pageBundle.keySet();
            JSONObject jSONObject2 = new JSONObject();
            for (String next : keySet) {
                jSONObject2.put(next, pageBundle.get(next));
            }
            jSONObject.put("result", jSONObject2.toString());
            String str = "";
            if (pageBundle.containsKey("key_schema_short_url_intent")) {
                str = ((Intent) pageBundle.getObject("key_schema_short_url_intent")).getDataString();
            }
            jSONObject.put("url", str);
            LogManager.actionLogV2("P00001", "B264", jSONObject);
        } catch (Throwable unused) {
        }
    }

    public final void onPause() {
        super.onPause();
        ccy suspendWidgetHelper = ((DefaultPage) this.mPage).getSuspendWidgetHelper();
        if (suspendWidgetHelper != null) {
            suspendWidgetHelper.a((a) null);
        }
        dlc i2 = i();
        if (!(i2 == null || i2.f == null)) {
            dam dam = i2.f;
            if (dam.a != null) {
                dam.a.setTipViewVisibility(8);
            }
        }
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null) {
                dlb.b.b().f();
            }
        }
        if (this.b != null) {
            dkx dkx = this.b;
            if (dkx.a != null) {
                dkx.a.c();
            }
        }
        f = true;
    }

    public final void onMoveBegin() {
        super.onMoveBegin();
        h();
    }

    public final void onScaleRotateBegin() {
        super.onScaleRotateBegin();
        h();
    }

    public final boolean onEngineActionGesture(alg alg) {
        if (alg != null && (alg.a == 5 || alg.a == 2 || alg.a == 3 || alg.a == 4 || alg.a == 6 || alg.a == 7 || alg.a == 1)) {
            h();
        }
        return super.onEngineActionGesture(alg);
    }

    /* access modifiers changed from: private */
    public void l() {
        if (this.g != null && !f && this.g.l()) {
            synchronized (this.p) {
                if (this.g != null && !f && this.g.l()) {
                    f = true;
                    if (this.g.j() > 0) {
                        this.g.j();
                    } else {
                        this.g.h();
                    }
                }
            }
        }
    }

    public final void a(boolean z) {
        if (this.q == null || z != this.q.booleanValue()) {
            this.q = Boolean.valueOf(z);
            g();
        }
    }

    public final void g() {
        bty k2 = k();
        if (k2 != null) {
            boolean z = false;
            int p2 = k2.p(false);
            if (this.q.booleanValue() && p2 == 0) {
                z = true;
            }
            k2.c(z);
        }
    }

    public final void onStop() {
        LocationInstrument.getInstance().unsubscribe(this.i);
        dlb dlb = this.a;
        if (dlb.b != null) {
            dlb.b.b().a(dlb.a, dlb.a.getTipContainer(), false);
        }
        cdd.n().a((ccz) null);
        bim.aa().a((biu) null);
        bim.aa().a((biy) null);
        bim.aa().a((bix) null);
        bim.aa().a((bit) null);
        bim.aa().a((bis) null);
        bim.aa().a((biw) null);
        ((DefaultPage) this.mPage).getMapManager().getOverlayManager().setIMapPointRequestingCallBack(null);
        super.onStop();
    }

    public final void onDestroy() {
        if (this.a != null) {
            dlb dlb = this.a;
            if (dlb.b != null) {
                dlb.b.b().l();
            }
        }
        super.onDestroy();
        elc.a();
        bck bck = (bck) a.a.a(bck.class);
        if (bck != null) {
            bck.d();
        }
        agb.c(false);
        agb.b(false);
        if (((DefaultPage) this.mPage).b != null) {
            dlc dlc = ((DefaultPage) this.mPage).b;
            aho.b(dlc.k);
            if (dlc.j != null) {
                dlc.j.setOnClickListener(null);
                dlc.j.getCompassWidget().setAngleListener(null);
            }
        }
        brm brm = (brm) ank.a(brm.class);
        if (brm != null) {
            brm.j();
        }
        if (this.b != null) {
            dkx dkx = this.b;
            if (dkx.a != null) {
                dkx.a.d();
                dkx.a = null;
            }
            this.b = null;
        }
        if (this.g != null) {
            this.g.a(this.o);
        }
        bty k2 = k();
        if (k2 != null) {
            k2.b((b) this.h);
        }
    }

    public final void onMapSurfaceCreated() {
        this.l = true;
        if (this.k != null) {
            this.k.b = true;
        }
        super.onMapSurfaceCreated();
        if (this.a != null) {
            cdd.n().a((ccz) this.a);
        }
    }
}
