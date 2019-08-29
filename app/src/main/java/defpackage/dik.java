package defpackage;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.planhome.view.RouteEditView;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.State;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.search.fragment.DriveSearchCallbackFragment;
import com.autonavi.minimap.drive.search.fragment.SearchCallbackFragment;
import com.autonavi.minimap.search.view.SearchCallBackComponent;
import com.autonavi.minimap.search.view.SearchCallBackComponentWrapper;

/* renamed from: dik reason: default package */
/* compiled from: DriveSearchCallbackPresenter */
public final class dik extends sw<DriveSearchCallbackFragment, dij> implements bgo {
    public final boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public dik(DriveSearchCallbackFragment driveSearchCallbackFragment) {
        super(driveSearchCallbackFragment);
    }

    public final void onPageCreated() {
        DriveSearchCallbackFragment driveSearchCallbackFragment = (DriveSearchCallbackFragment) this.mPage;
        if (driveSearchCallbackFragment.a != null) {
            driveSearchCallbackFragment.a.clearFocus();
        }
        DriveSearchCallbackFragment driveSearchCallbackFragment2 = (DriveSearchCallbackFragment) this.mPage;
        driveSearchCallbackFragment2.w = (RelativeLayout) driveSearchCallbackFragment2.findViewById(R.id.search_callback_root_layout);
        driveSearchCallbackFragment2.a = (RouteEditView) driveSearchCallbackFragment2.findViewById(R.id.search_header);
        driveSearchCallbackFragment2.a.setKeepPlaceHolder(true);
        driveSearchCallbackFragment2.b = (SearchCallBackComponent) driveSearchCallbackFragment2.findViewById(R.id.search_component);
        driveSearchCallbackFragment2.c = new SearchCallBackComponentWrapper(driveSearchCallbackFragment2.getContext(), driveSearchCallbackFragment2, driveSearchCallbackFragment2.b);
        driveSearchCallbackFragment2.c.showHistory();
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (mapView != null) {
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            driveSearchCallbackFragment2.c.initSuggParam(glGeoPoint2GeoPoint, (long) glGeoPoint2GeoPoint.getAdCode(), 0, "poi|bus", DriveSearchCallbackFragment.d);
        }
        driveSearchCallbackFragment2.i = LayoutInflater.from(driveSearchCallbackFragment2.getContext()).inflate(R.layout.drive_search_callback_fragment_voice_window, null);
        driveSearchCallbackFragment2.j = new dke(driveSearchCallbackFragment2);
        driveSearchCallbackFragment2.l = driveSearchCallbackFragment2.getString(R.string.act_fromto_mid_input_hint);
        if (driveSearchCallbackFragment2.k != null) {
            String simpleName = SearchCallbackFragment.class.getSimpleName();
            StringBuilder sb = new StringBuilder("initHeaderView   mEditStatus:");
            sb.append(driveSearchCallbackFragment2.k.mEditStatus);
            AMapLog.i(simpleName, sb.toString());
            String simpleName2 = SearchCallbackFragment.class.getSimpleName();
            StringBuilder sb2 = new StringBuilder("initHeaderView   mWidgetId:");
            sb2.append(driveSearchCallbackFragment2.k.mWidgetId);
            AMapLog.i(simpleName2, sb2.toString());
            driveSearchCallbackFragment2.a.setStartHint(driveSearchCallbackFragment2.getString(R.string.act_fromto_from_input_hint));
            if (driveSearchCallbackFragment2.k.mStartPoi != null) {
                driveSearchCallbackFragment2.a.setStartText(driveSearchCallbackFragment2.k.mStartPoi.getName());
            }
            driveSearchCallbackFragment2.a.setEndHint(driveSearchCallbackFragment2.getString(R.string.act_fromto_to_input_hint));
            if (driveSearchCallbackFragment2.k.mEndPoi != null) {
                driveSearchCallbackFragment2.a.setEndText(driveSearchCallbackFragment2.k.mEndPoi.getName());
            }
            StringBuilder sb3 = new StringBuilder("daihq  initHeaderView    mRouteType:");
            sb3.append(driveSearchCallbackFragment2.g);
            AMapLog.i("DriveSearchCallbackFragment", sb3.toString());
            int i = 8;
            if (driveSearchCallbackFragment2.g != null) {
                if (driveSearchCallbackFragment2.c()) {
                    driveSearchCallbackFragment2.a.setAddExpectVisibility(0);
                    driveSearchCallbackFragment2.a(false);
                    driveSearchCallbackFragment2.g();
                } else {
                    driveSearchCallbackFragment2.a.setAddExpectVisibility(8);
                }
            }
            RouteEditView routeEditView = driveSearchCallbackFragment2.a;
            if (driveSearchCallbackFragment2.g == RouteType.CAR) {
                i = 0;
            }
            routeEditView.setVUIExpectVisibility(i);
            driveSearchCallbackFragment2.a.changeState(driveSearchCallbackFragment2.k.mEditStatus, false, null);
            driveSearchCallbackFragment2.a.setEnable(3, driveSearchCallbackFragment2.k.mCanExchange);
        }
        driveSearchCallbackFragment2.a.setOnTextChangeListener(driveSearchCallbackFragment2.y);
        driveSearchCallbackFragment2.c.setFavoriteListener(driveSearchCallbackFragment2.z);
        driveSearchCallbackFragment2.c.setPointListener(driveSearchCallbackFragment2.A);
        driveSearchCallbackFragment2.c.setPositionListener(driveSearchCallbackFragment2.B);
        driveSearchCallbackFragment2.c.setHistoryOnItemEventListener(driveSearchCallbackFragment2.C);
        driveSearchCallbackFragment2.c.setSuggOnItemEventListener(driveSearchCallbackFragment2.D);
        driveSearchCallbackFragment2.i.setOnClickListener(driveSearchCallbackFragment2.I);
        driveSearchCallbackFragment2.a.setOnRouteEditClickListener(driveSearchCallbackFragment2.J);
        driveSearchCallbackFragment2.a.setOnEditorActionListener(driveSearchCallbackFragment2.F);
        driveSearchCallbackFragment2.a.setOnEditFocusChangeListener(driveSearchCallbackFragment2.G);
        driveSearchCallbackFragment2.b.getmSuggListView().setOnTouchListener(driveSearchCallbackFragment2.x);
        driveSearchCallbackFragment2.b.getmHistoryListView().setOnTouchListener(driveSearchCallbackFragment2.x);
        DriveSearchCallbackFragment driveSearchCallbackFragment3 = (DriveSearchCallbackFragment) this.mPage;
        if (driveSearchCallbackFragment3.m) {
            TextUtils.isEmpty(driveSearchCallbackFragment3.e);
        }
    }

    public final void onStart() {
        DriveSearchCallbackFragment driveSearchCallbackFragment = (DriveSearchCallbackFragment) this.mPage;
        SuperId.getInstance().reset();
        if (driveSearchCallbackFragment.getArguments() != null && driveSearchCallbackFragment.getArguments().containsKey("SUPER_ID")) {
            driveSearchCallbackFragment.E = (String) driveSearchCallbackFragment.getArguments().get("SUPER_ID");
            if (UploadQueueMgr.MSGTYPE_REALTIME.equals(driveSearchCallbackFragment.E) && driveSearchCallbackFragment.g != null) {
                if (driveSearchCallbackFragment.g.equals(RouteType.BUS)) {
                    driveSearchCallbackFragment.E = "d";
                } else if (driveSearchCallbackFragment.g.equals(RouteType.CAR)) {
                    driveSearchCallbackFragment.E = "f";
                } else if (driveSearchCallbackFragment.g.equals(RouteType.ONFOOT)) {
                    driveSearchCallbackFragment.E = "e";
                }
            }
            SuperId.getInstance().setBit1(driveSearchCallbackFragment.E);
        }
        if (!driveSearchCallbackFragment.m || TextUtils.isEmpty(driveSearchCallbackFragment.e)) {
            if (driveSearchCallbackFragment.j != null) {
                driveSearchCallbackFragment.j.b();
            }
            if (driveSearchCallbackFragment.k.mEditStatus == State.EDIT) {
                if (!driveSearchCallbackFragment.p) {
                    driveSearchCallbackFragment.a(driveSearchCallbackFragment.o, driveSearchCallbackFragment.q, driveSearchCallbackFragment.r);
                } else {
                    driveSearchCallbackFragment.i();
                }
            }
            driveSearchCallbackFragment.o = -1;
            driveSearchCallbackFragment.q = 0;
            driveSearchCallbackFragment.r = 0;
            driveSearchCallbackFragment.p = false;
            if (driveSearchCallbackFragment.t) {
                if (driveSearchCallbackFragment.s == State.SUMMARY) {
                    driveSearchCallbackFragment.a.post(new Runnable() {
                        public final void run() {
                            DriveSearchCallbackFragment.this.k.mEditStatus = State.EDIT;
                            DriveSearchCallbackFragment.this.a.changeState(State.EDIT, true, new AnimatorListenerAdapter() {
                                public final void onAnimationStart(Animator animator) {
                                    super.onAnimationStart(animator);
                                    if (DriveSearchCallbackFragment.this.k.mChildPageClass == null || !AbstractBaseMapPage.class.isAssignableFrom(DriveSearchCallbackFragment.this.k.mChildPageClass)) {
                                        DriveSearchCallbackFragment.this.a((AnimatorListener) null);
                                        return;
                                    }
                                    DriveSearchCallbackFragment.this.w.setBackgroundColor(0);
                                    DriveSearchCallbackFragment.this.a(DriveSearchCallbackFragment.this.Y);
                                }

                                public final void onAnimationEnd(Animator animator) {
                                    super.onAnimationEnd(animator);
                                    DriveSearchCallbackFragment.this.a(DriveSearchCallbackFragment.this.k.mWidgetId);
                                }

                                public final void onAnimationCancel(Animator animator) {
                                    super.onAnimationCancel(animator);
                                    DriveSearchCallbackFragment.this.a(DriveSearchCallbackFragment.this.k.mWidgetId);
                                    DriveSearchCallbackFragment.this.w.setBackgroundColor(DriveSearchCallbackFragment.this.getResources().getColor(R.color.c_1));
                                }
                            });
                        }
                    });
                } else {
                    if (!driveSearchCallbackFragment.c() || driveSearchCallbackFragment.k == null || driveSearchCallbackFragment.k.mMidPois == null || driveSearchCallbackFragment.k.mMidPois.size() <= 0) {
                        driveSearchCallbackFragment.a(driveSearchCallbackFragment.k.mWidgetId);
                    } else {
                        driveSearchCallbackFragment.h();
                    }
                    driveSearchCallbackFragment.a((AnimatorListener) null);
                }
                if (driveSearchCallbackFragment.k.mWidgetId == 2) {
                    driveSearchCallbackFragment.b(true);
                } else if (driveSearchCallbackFragment.k.mWidgetId == 3) {
                    driveSearchCallbackFragment.c(true);
                }
            }
            driveSearchCallbackFragment.t = false;
            driveSearchCallbackFragment.l();
        }
    }

    public final void onStop() {
        ((DriveSearchCallbackFragment) this.mPage).l();
        DriveSearchCallbackFragment driveSearchCallbackFragment = (DriveSearchCallbackFragment) this.mPage;
        if (driveSearchCallbackFragment.j != null) {
            driveSearchCallbackFragment.j.a();
        }
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((DriveSearchCallbackFragment) this.mPage).k();
        return super.onBackPressed();
    }

    public final void onDestroy() {
        DriveSearchCallbackFragment driveSearchCallbackFragment = (DriveSearchCallbackFragment) this.mPage;
        if (driveSearchCallbackFragment.h != null && driveSearchCallbackFragment.h.isShowing()) {
            driveSearchCallbackFragment.h.dismiss();
        }
        if (driveSearchCallbackFragment.v != null) {
            driveSearchCallbackFragment.v.cancel();
        }
        if (driveSearchCallbackFragment.u != null) {
            driveSearchCallbackFragment.u.cancel();
        }
        driveSearchCallbackFragment.j();
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        DriveSearchCallbackFragment driveSearchCallbackFragment = (DriveSearchCallbackFragment) this.mPage;
        AMapLog.d("DriveSearchCallbackFragment", "onPageFragmentResult---requestCode=".concat(String.valueOf(i)));
        if (ResultType.OK.equals(resultType)) {
            driveSearchCallbackFragment.p = true;
            if (pageBundle != null) {
                if (pageBundle.containsKey("searchResult")) {
                    POI poi = (POI) pageBundle.getObject("searchResult");
                    driveSearchCallbackFragment.a(poi);
                    driveSearchCallbackFragment.a(poi, i, true);
                } else if (i == 1 && pageBundle.containsKey("SelectPoiFromMapFragment.MapClickResult")) {
                    POI poi2 = (POI) pageBundle.getObject("SelectPoiFromMapFragment.MapClickResult");
                    driveSearchCallbackFragment.a(poi2);
                    driveSearchCallbackFragment.a(poi2, i, false);
                } else if (i == 2) {
                    if (pageBundle.containsKey("result_poi")) {
                        POI poi3 = (POI) pageBundle.getObject("result_poi");
                        driveSearchCallbackFragment.a(poi3);
                        driveSearchCallbackFragment.a(poi3, i, false);
                    } else if (pageBundle.containsKey(TrafficUtil.KEYWORD)) {
                        driveSearchCallbackFragment.a(pageBundle.getString(TrafficUtil.KEYWORD));
                        driveSearchCallbackFragment.f = false;
                        SuperId.getInstance().setBit3("09");
                        driveSearchCallbackFragment.c((TipItem) null);
                    } else {
                        if (pageBundle.containsKey("search_net") && pageBundle.getBoolean("search_net") && !TextUtils.isEmpty(driveSearchCallbackFragment.e)) {
                            driveSearchCallbackFragment.a(driveSearchCallbackFragment.e);
                            driveSearchCallbackFragment.f = false;
                            driveSearchCallbackFragment.c((TipItem) null);
                        }
                    }
                }
            }
        } else {
            driveSearchCallbackFragment.p = false;
        }
    }

    public final /* synthetic */ su a() {
        return new dij(this);
    }
}
