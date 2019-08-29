package com.autonavi.minimap.drive.search.fragment;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.planhome.view.RouteEditView;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.State;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.a;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.b;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.c;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.d;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.model.RouteHeaderModel;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.search.controller.ISearchCompleteListener;
import com.autonavi.minimap.drive.search.fragment.SearchCallbackFragment.SearchFor;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.search.model.SearchConst;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.autonavi.minimap.search.view.SearchCallBackComponent;
import com.autonavi.minimap.search.view.SearchCallBackComponentWrapper;
import com.autonavi.minimap.widget.ListDialog;
import com.autonavi.minimap.widget.SearchEdit.OnItemEventListener;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("drive.search.fragment.SearchCallbackFragment")
public class DriveSearchCallbackFragment extends DriveBasePage<dik> implements bgm, IVoiceCmdResponder, se {
    private static final GeoPoint W = new GeoPoint(0, 0);
    public static int d;
    public OnClickListener A = new OnClickListener() {
        public final void onClick(View view) {
            DriveSearchCallbackFragment.this.j();
            DriveSearchCallbackFragment.this.o = DriveSearchCallbackFragment.this.a.getFocusWidgetId();
            DriveSearchCallbackFragment.this.q = DriveSearchCallbackFragment.this.a.getEditSelectionStart(DriveSearchCallbackFragment.this.o);
            DriveSearchCallbackFragment.this.r = DriveSearchCallbackFragment.this.a.getEditSelectionEnd(DriveSearchCallbackFragment.this.o);
            SelectPoiFromMapBean g = DriveSearchCallbackFragment.g(DriveSearchCallbackFragment.this);
            DriveSearchCallbackFragment.h(DriveSearchCallbackFragment.this);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("FetchFor", DriveSearchCallbackFragment.this.K);
            pageBundle.putSerializable("SelectPoiFromMapBean", g);
            DriveSearchCallbackFragment.this.startPageForResult((String) "amap.basemap.action.base_select_poi_from_map_page", pageBundle, 1);
            LogManager.actionLogV2("P00260", "B010");
        }
    };
    public OnClickListener B = new OnClickListener() {
        public final void onClick(View view) {
            DriveSearchCallbackFragment.this.y();
        }
    };
    public OnItemEventListener C = new OnItemEventListener() {
        public final void onItemLongClicked(TipItem tipItem) {
        }

        public final void onRouteClicked(TipItem tipItem) {
        }

        public final void onItemClicked(TipItem tipItem, int i, boolean z) {
            DriveSearchCallbackFragment.this.b(tipItem);
        }

        public final void onAddClicked(TipItem tipItem, int i) {
            DriveSearchCallbackFragment.this.a(tipItem.name);
        }
    };
    public OnItemEventListener D = new OnItemEventListener() {
        public final void onItemLongClicked(TipItem tipItem) {
        }

        public final void onRouteClicked(TipItem tipItem) {
        }

        public final void onItemClicked(TipItem tipItem, int i, boolean z) {
            DriveSearchCallbackFragment driveSearchCallbackFragment = DriveSearchCallbackFragment.this;
            if (tipItem.type == 1) {
                tipItem.userInput = driveSearchCallbackFragment.m();
            }
            if (!TextUtils.isEmpty(tipItem.searchQuery)) {
                tipItem.name = tipItem.searchQuery;
            }
            SuperId.getInstance().setBit1(driveSearchCallbackFragment.E);
            if (tipItem.type == 0) {
                SuperId.getInstance().setBit2("02");
                if (!DriveSearchCallbackFragment.a(tipItem)) {
                    SuperId.getInstance().setBit3("06");
                } else {
                    SuperId.getInstance().setBit3("07");
                }
            } else {
                SuperId.getInstance().setBit2("01");
                if (TextUtils.isEmpty(tipItem.poiid) && z) {
                    SuperId.getInstance().setBit3("15");
                } else if ((tipItem.tipItemList == null || tipItem.tipItemList.size() <= 0) && !tipItem.isSugChildClick) {
                    if (DriveSearchCallbackFragment.a(tipItem)) {
                        SuperId.getInstance().setBit3("01");
                    }
                } else if (TextUtils.isEmpty(tipItem.poiid)) {
                    SuperId.getInstance().setBit3("02");
                } else {
                    SuperId.getInstance().setBit3("03");
                }
            }
            DriveSearchCallbackFragment.this.b(tipItem);
        }

        public final void onAddClicked(TipItem tipItem, int i) {
            DriveSearchCallbackFragment.this.a(tipItem.name);
        }
    };
    public String E = "";
    public b F = new b() {
        public final boolean a(int i) {
            if (i == 3) {
                DriveSearchCallbackFragment.n(DriveSearchCallbackFragment.this);
            }
            return true;
        }
    };
    public a G = new a() {
        public final void a(int i, View view, boolean z) {
            DriveSearchCallbackFragment.this.a.setImeOptions(i, 3);
            DriveSearchCallbackFragment.this.a.setInputType(i, 1);
            if (z && !DriveSearchCallbackFragment.this.Q) {
                DriveSearchCallbackFragment.this.Q = true;
                DriveSearchCallbackFragment.this.c.showHistory();
            }
            if (!z) {
                EditText d = acv.d(DriveSearchCallbackFragment.this.a, i);
                if (d != null) {
                    d.setSelection(0);
                }
            }
        }
    };
    ISearchCompleteListener H = new ISearchCompleteListener() {
        public final void a(String str) {
            DriveSearchCallbackFragment.this.a(str);
        }

        public final void a(final POI poi) {
            StringBuilder sb = new StringBuilder("complete----mIsAutoSearch=");
            sb.append(DriveSearchCallbackFragment.this.m);
            AMapLog.d("DriveSearchCallbackFragment", sb.toString());
            if (DriveSearchCallbackFragment.this.m) {
                if (poi != null) {
                    DriveSearchCallbackFragment.this.setResult(ResultType.OK, DriveSearchCallbackFragment.this.d(poi));
                }
                DriveSearchCallbackFragment.this.finish();
                return;
            }
            aho.a(new Runnable() {
                public final void run() {
                    DriveSearchCallbackFragment.this.e(poi);
                }
            }, 100);
        }
    };
    public final OnClickListener I = new OnClickListener() {
        private long b = 0;

        public final void onClick(View view) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (Math.abs(elapsedRealtime - this.b) >= 600) {
                this.b = elapsedRealtime;
                if (DriveSearchCallbackFragment.this.isStarted() && ((DriveSearchCallbackFragment.this.h == null || !DriveSearchCallbackFragment.this.h.isShowing()) && DriveSearchCallbackFragment.this.j != null)) {
                    DriveSearchCallbackFragment.this.j.c();
                }
                LogManager.actionLogV2("P00260", "B013");
            }
        }
    };
    public c J = new c() {
        public final void a(View view, int i) {
            if (i == 66) {
                DriveSearchCallbackFragment.this.a.removeMidView(66, true);
                DriveSearchCallbackFragment.this.a.setMidHint(DriveSearchCallbackFragment.this.c(DriveSearchCallbackFragment.this.a.getMidCount()));
                if (!(DriveSearchCallbackFragment.this.k == null || DriveSearchCallbackFragment.this.k.mMidPois == null || DriveSearchCallbackFragment.this.k.mMidPois.size() <= 0)) {
                    DriveSearchCallbackFragment.this.k.mMidPois.remove(0);
                }
                DriveSearchCallbackFragment.u(DriveSearchCallbackFragment.this);
                DriveSearchCallbackFragment.v(DriveSearchCallbackFragment.this);
            } else if (i == 82) {
                DriveSearchCallbackFragment.this.a.removeMidView(82, true);
                DriveSearchCallbackFragment.this.a.setMidHint(DriveSearchCallbackFragment.this.c(DriveSearchCallbackFragment.this.a.getMidCount()));
                if (!(DriveSearchCallbackFragment.this.k == null || DriveSearchCallbackFragment.this.k.mMidPois == null || DriveSearchCallbackFragment.this.k.mMidPois.size() <= 1)) {
                    DriveSearchCallbackFragment.this.k.mMidPois.remove(1);
                }
                DriveSearchCallbackFragment.u(DriveSearchCallbackFragment.this);
                DriveSearchCallbackFragment.v(DriveSearchCallbackFragment.this);
            } else if (i != 98) {
                switch (i) {
                    case 1:
                        DriveSearchCallbackFragment.this.k();
                        return;
                    case 2:
                        DriveSearchCallbackFragment.this.b(false);
                        DriveSearchCallbackFragment.t(DriveSearchCallbackFragment.this);
                        return;
                    case 3:
                        AMapLog.i(SearchCallbackFragment.class.getSimpleName(), "mRouteEditClickListener  click ID_EXCHANGE");
                        DriveSearchCallbackFragment.this.c(false);
                        DriveSearchCallbackFragment.x(DriveSearchCallbackFragment.this);
                        break;
                }
            } else {
                DriveSearchCallbackFragment.this.a.removeMidView(98, true);
                if (!(DriveSearchCallbackFragment.this.k == null || DriveSearchCallbackFragment.this.k.mMidPois == null || DriveSearchCallbackFragment.this.k.mMidPois.size() <= 2)) {
                    DriveSearchCallbackFragment.this.k.mMidPois.remove(2);
                }
                DriveSearchCallbackFragment.u(DriveSearchCallbackFragment.this);
                DriveSearchCallbackFragment.v(DriveSearchCallbackFragment.this);
            }
        }
    };
    /* access modifiers changed from: private */
    public SelectFor K;
    private SearchFor L = SearchFor.DEFAULT_POI;
    private adx M;
    private boolean N = false;
    private int O = -1;
    private TipItem P;
    /* access modifiers changed from: private */
    public boolean Q = true;
    private String R;
    /* access modifiers changed from: private */
    public ProgressDlg S;
    private RouteHeaderModel T = null;
    private int U = 0;
    /* access modifiers changed from: private */
    public String V = "";
    /* access modifiers changed from: private */
    public AnimatorListener X = new AnimatorListener() {
        public final void onAnimationCancel(Animator animator) {
        }

        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
        }

        public final void onAnimationEnd(Animator animator) {
            if (DriveSearchCallbackFragment.this.t()) {
                DriveSearchCallbackFragment.this.a.changeState(State.SUMMARY, true, DriveSearchCallbackFragment.this.aa);
            } else {
                DriveSearchCallbackFragment.this.a.setOnTextChangeListener(DriveSearchCallbackFragment.this.y);
            }
        }
    };
    /* access modifiers changed from: private */
    public AnimatorListener Y = new AnimatorListener() {
        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
        }

        public final void onAnimationEnd(Animator animator) {
            DriveSearchCallbackFragment.this.w.setBackgroundColor(DriveSearchCallbackFragment.this.getResources().getColor(R.color.c_1));
        }

        public final void onAnimationCancel(Animator animator) {
            DriveSearchCallbackFragment.this.w.setBackgroundColor(DriveSearchCallbackFragment.this.getResources().getColor(R.color.c_1));
        }
    };
    /* access modifiers changed from: private */
    public AnimatorListener Z = new AnimatorListener() {
        public final void onAnimationCancel(Animator animator) {
        }

        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
        }

        public final void onAnimationEnd(Animator animator) {
            DriveSearchCallbackFragment.this.w();
            DriveSearchCallbackFragment.this.finish();
        }
    };
    public RouteEditView a;
    /* access modifiers changed from: private */
    public AnimatorListenerAdapter aa = new AnimatorListenerAdapter() {
        public final void onAnimationCancel(Animator animator) {
        }

        public final void onAnimationRepeat(Animator animator) {
        }

        public final void onAnimationStart(Animator animator) {
            DriveSearchCallbackFragment.b(DriveSearchCallbackFragment.this, DriveSearchCallbackFragment.this.Z);
        }

        public final void onAnimationEnd(Animator animator) {
            View g = DriveSearchCallbackFragment.this.findViewById(R.id.header_white_bg);
            if (g != null) {
                g.setVisibility(0);
            }
        }
    };
    public SearchCallBackComponent b;
    public SearchCallBackComponentWrapper c;
    public String e;
    public boolean f = false;
    public RouteType g = null;
    public ListDialog h;
    public View i;
    public dke j;
    public RouteHeaderModel k = null;
    public String l = "";
    public boolean m;
    public Callback<POI> n = null;
    public int o = -1;
    public boolean p = false;
    public int q = 0;
    public int r = 0;
    public State s;
    public boolean t = true;
    public AnimatorSet u;
    public AnimatorSet v;
    public RelativeLayout w;
    public OnTouchListener x = new OnTouchListener() {
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            DriveSearchCallbackFragment.this.j();
            int focusWidgetId = DriveSearchCallbackFragment.this.a.getFocusWidgetId();
            CharSequence text = DriveSearchCallbackFragment.this.a.getText(focusWidgetId);
            if (!TextUtils.isEmpty(text)) {
                DriveSearchCallbackFragment.this.a.setEditSelection(focusWidgetId, text.length());
            }
            return false;
        }
    };
    public d y = new d() {
        public final void a(int i, Editable editable) {
            String trim = editable.toString().trim();
            if (!TextUtils.equals(trim, DriveSearchCallbackFragment.this.V)) {
                if (!TextUtils.isEmpty(trim)) {
                    DriveSearchCallbackFragment.this.Q = false;
                    DriveSearchCallbackFragment.this.c.showSugg(trim, null);
                } else {
                    DriveSearchCallbackFragment.this.Q = true;
                    DriveSearchCallbackFragment.this.c.showHistory();
                }
            }
            DriveSearchCallbackFragment.a(DriveSearchCallbackFragment.this, i, trim);
            DriveSearchCallbackFragment.this.V = trim;
        }
    };
    public OnClickListener z = new OnClickListener() {
        public final void onClick(View view) {
            com com2 = (com) ank.a(com.class);
            cop b = com2 != null ? com2.b(com2.a()) : null;
            if (DriveSearchCallbackFragment.this.h == null) {
                DriveSearchCallbackFragment.this.h = new ListDialog(DriveSearchCallbackFragment.this.getActivity());
                DriveSearchCallbackFragment.this.h.setDlgTitle(DriveSearchCallbackFragment.this.getResources().getString(R.string.title_save_points));
            }
            if (b == null || b.a() == 0) {
                DriveSearchCallbackFragment.this.h.setAdapter(new ArrayAdapter(DriveSearchCallbackFragment.this.getActivity(), R.layout.v3_list_dialog_empty, new String[]{DriveSearchCallbackFragment.this.getResources().getString(R.string.save_is_empty_msg)}));
                DriveSearchCallbackFragment.this.h.setOnItemClickListener(null);
                DriveSearchCallbackFragment.this.h.setComfirmBtnVisibility(8);
            } else {
                List<FavoritePOI> e = b.e();
                DriveSearchCallbackFragment.this.h.setAdapter(new ArrayAdapter(DriveSearchCallbackFragment.this.getActivity(), R.layout.v3_list_dialog_item, DriveSearchCallbackFragment.b((List) e)));
                DriveSearchCallbackFragment.this.h.setOnItemClickListener(new OnItemClickListener(e) {
                    final /* synthetic */ List val$savePois;

                    {
                        this.val$savePois = r2;
                    }

                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        if (DriveSearchCallbackFragment.this.h != null) {
                            DriveSearchCallbackFragment.this.h.dismiss();
                        }
                        DriveSearchCallbackFragment.this.e((POI) this.val$savePois.get(i));
                    }
                });
            }
            DriveSearchCallbackFragment.this.h.show();
            LogManager.actionLogV2("P00260", "B009");
        }
    };

    class GeoReverseCallback implements Callback<String> {
        private final POI mPoi;

        public GeoReverseCallback(POI poi) {
            this.mPoi = poi;
        }

        public void callback(String str) {
            if (DriveSearchCallbackFragment.this.S != null && DriveSearchCallbackFragment.this.S.isShowing()) {
                DriveSearchCallbackFragment.this.S.dismiss();
            }
            if (!TextUtils.isEmpty(str)) {
                this.mPoi.setName(str);
                DriveSearchCallbackFragment.this.e(this.mPoi);
            }
        }

        public void error(Throwable th, boolean z) {
            if (DriveSearchCallbackFragment.this.S != null && DriveSearchCallbackFragment.this.S.isShowing()) {
                DriveSearchCallbackFragment.this.S.dismiss();
            }
        }
    }

    public void finishSelf() {
    }

    public long getScene() {
        return 549755813888L;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 246290604621824L;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.fragment_drive_searchcallback);
        requestScreenOrientation(1);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            d = arguments.getInt("from_page");
            this.e = arguments.getString(TrafficUtil.KEYWORD);
            this.N = arguments.getBoolean("isOffline", false);
            int i2 = arguments.getInt("search_for", 1);
            if (i2 == 1) {
                this.L = SearchFor.DEFAULT_POI;
            } else if (i2 == 2) {
                this.L = SearchFor.CUSTOM_ADDRESS;
            } else if (i2 == 3) {
                this.L = SearchFor.QUICK_NAVI;
            }
            this.R = getString(R.string.confirm);
            this.K = (SelectFor) arguments.getObject("selectedfor");
            if (arguments.containsKey("route_type")) {
                Object object = arguments.getObject("route_type");
                if (object != null && (object instanceof RouteType)) {
                    this.g = (RouteType) object;
                }
            }
            this.n = (Callback) arguments.getObject("callback");
            this.m = arguments.getBoolean("auto_search");
            if (!TextUtils.isEmpty(this.e) && (this.e.equals("我的位置") || this.e.equals("已选择的位置") || this.e.equals(DriveUtil.MAP_PLACE_DES) || this.e.equals("地图选定位置") || TextUtils.equals(this.e, DriveUtil.MY_LOCATION_LOADING))) {
                this.e = "";
            }
            if (this.m && !TextUtils.isEmpty(this.e)) {
                c((TipItem) null);
            }
            this.T = (RouteHeaderModel) arguments.getObject(RouteHeaderModel.ROUTE_HEADER_MODEL_KEY);
            if (this.T != null) {
                this.k = new RouteHeaderModel();
                this.k.mWidgetId = this.T.mWidgetId;
                this.s = this.T.mEditStatus;
                this.k.mEditStatus = this.T.mEditStatus;
                this.k.mCanExchange = this.T.mCanExchange;
                this.k.mChildPageClass = this.T.mChildPageClass;
                if (this.T.mStartPoi != null) {
                    this.k.mStartPoi = this.T.mStartPoi.clone();
                }
                if (this.T.mEndPoi != null) {
                    this.k.mEndPoi = this.T.mEndPoi.clone();
                }
                if (this.T.mMidPois != null && this.T.mMidPois.size() > 0) {
                    this.k.mMidPois = new ArrayList(this.T.mMidPois.size());
                    for (int i3 = 0; i3 < this.T.mMidPois.size(); i3++) {
                        this.k.mMidPois.add(this.T.mMidPois.get(i3).clone());
                    }
                }
                this.U = b(this.k);
                if (this.g != null && c() && this.k.mMidPois != null && this.k.mMidPois.size() > 0 && (this.k.mWidgetId == 256 || this.k.mWidgetId == 768 || this.k.mWidgetId == 512 || this.k.mWidgetId == 3840)) {
                    this.k.mWidgetId = 33;
                }
            }
            if (this.s != State.SUMMARY) {
                this.k.mEditStatus = State.EDIT;
            }
        }
    }

    private void a(RouteHeaderModel routeHeaderModel) {
        if (routeHeaderModel != null) {
            if (b(this.k) >= this.U) {
                if (!(this.k == null || this.k.mStartPoi == null || TextUtils.isEmpty(this.k.mStartPoi.getName()))) {
                    this.a.setStartText(this.k.mStartPoi.getName());
                }
                if (!(this.k == null || this.k.mEndPoi == null || TextUtils.isEmpty(this.k.mEndPoi.getName()))) {
                    this.a.setEndText(this.k.mEndPoi.getName());
                }
            } else if (this.T != null) {
                if (this.T.mStartPoi != null) {
                    this.a.setStartText(this.T.mStartPoi.getName());
                }
                if (this.T.mEndPoi != null) {
                    this.a.setEndText(this.T.mEndPoi.getName());
                }
            }
        }
    }

    public final void a(boolean z2) {
        int size = (this.k == null || this.k.mMidPois == null) ? 0 : this.k.mMidPois.size();
        int midCount = this.a.getMidCount();
        if (size > midCount) {
            while (midCount < size) {
                this.a.addMidView(z2);
                midCount++;
            }
            return;
        }
        if (size < midCount) {
            while (midCount > size && midCount > 0) {
                this.a.removeMidView(66, z2);
                midCount--;
            }
        }
    }

    public final void g() {
        if (this.k != null) {
            int size = this.k.mMidPois != null ? this.k.mMidPois.size() : 0;
            if (size <= 0 || size != this.a.getMidCount()) {
                this.a.setMidTexts("", new CharSequence[0]);
                return;
            }
            String[] strArr = new String[size];
            for (int i2 = 0; i2 < size; i2++) {
                if (!TextUtils.isEmpty(this.k.mMidPois.get(i2).getName())) {
                    strArr[i2] = this.k.mMidPois.get(i2).getName();
                }
            }
            this.a.setMidHint(c(size));
            this.a.setMidTexts(RouteHeaderModel.getMidSummaryDes(this.k.mMidPois), strArr);
        }
    }

    public final void a(AnimatorListener animatorListener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.b, "TranslationY", new float[]{(float) ags.a((Context) AMapAppGlobal.getApplication()).height(), 0.0f});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.b, "alpha", new float[]{0.0f, 1.0f});
        this.v = new AnimatorSet();
        ofFloat.setDuration(375);
        ofFloat2.setDuration(375);
        if (animatorListener != null) {
            this.v.addListener(animatorListener);
        }
        this.v.play(ofFloat).with(ofFloat2);
        this.v.start();
    }

    public final void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            int u2 = u();
            if (u2 > 0) {
                this.a.setText(u2, str);
                this.a.setEditSelection(u2, str.length());
            }
        }
    }

    public final boolean h() {
        if (this.k != null) {
            if (this.k.mStartPoi == null || W.equals(this.k.mStartPoi.getPoint())) {
                a(17);
                return true;
            }
            if (c() && this.k.mMidPois != null) {
                int size = this.k.mMidPois.size();
                if (size == 1 && (this.k.mMidPois.get(0) == null || W.equals(this.k.mMidPois.get(0).getPoint()))) {
                    a(65);
                    return true;
                } else if (size == 2) {
                    if (this.k.mMidPois.get(0) == null || W.equals(this.k.mMidPois.get(0).getPoint())) {
                        a(65);
                        return true;
                    } else if (this.k.mMidPois.get(1) == null || W.equals(this.k.mMidPois.get(1).getPoint())) {
                        a(81);
                        return true;
                    }
                } else if (size == 3) {
                    if (this.k.mMidPois.get(0) == null || W.equals(this.k.mMidPois.get(0).getPoint())) {
                        a(65);
                        return true;
                    } else if (this.k.mMidPois.get(1) == null || W.equals(this.k.mMidPois.get(1).getPoint())) {
                        a(81);
                        return true;
                    } else if (this.k.mMidPois.get(2) == null || W.equals(this.k.mMidPois.get(2).getPoint())) {
                        a(97);
                        return true;
                    }
                }
            }
            if (this.k.mEndPoi == null || W.equals(this.k.mEndPoi.getPoint())) {
                a(33);
                return true;
            }
        }
        return false;
    }

    public final void i() {
        if (h()) {
            String m2 = m();
            if (TextUtils.isEmpty(m2.trim())) {
                this.c.showHistory();
                return;
            }
            this.c.showSugg(m2, null);
        }
    }

    public final void j() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        if (inputMethodManager != null) {
            try {
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(this.a.getWindowToken(), 0);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    static boolean a(TipItem tipItem) {
        return !TextUtils.isEmpty(tipItem.poiid) && tipItem.x > 0.0d && tipItem.y > 0.0d;
    }

    public final void b(TipItem tipItem) {
        if (tipItem != null) {
            this.P = tipItem;
            if (a(tipItem)) {
                POI createPOI = POIFactory.createPOI(tipItem.name, new GeoPoint(tipItem.x, tipItem.y));
                String str = TextUtils.isEmpty(tipItem.district) ? "" : tipItem.district;
                ISearchPoiData iSearchPoiData = (ISearchPoiData) createPOI.as(ISearchPoiData.class);
                iSearchPoiData.setId(tipItem.poiid);
                iSearchPoiData.setAdCode(tipItem.adcode);
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(tipItem.addr);
                iSearchPoiData.setAddr(sb.toString().trim());
                iSearchPoiData.setType(tipItem.newType);
                iSearchPoiData.getPoiExtra().put("icon_info", Integer.valueOf(tipItem.iconinfo));
                iSearchPoiData.setParent(tipItem.parent);
                iSearchPoiData.setChildType(tipItem.childType);
                iSearchPoiData.setTowardsAngle(tipItem.towardsAngle);
                iSearchPoiData.setFnona(tipItem.f_nona);
                iSearchPoiData.setEndPoiExtension(tipItem.endPoiExtension);
                iSearchPoiData.setTransparent(tipItem.transparent);
                if (!TextUtils.isEmpty(tipItem.strf_nona)) {
                    if (iSearchPoiData.getIndoorPoiInfo() == null) {
                        iSearchPoiData.setIndoorPoiInfo(new IndoorPoiData());
                    }
                    iSearchPoiData.getIndoorPoiInfo().floorName = tipItem.strf_nona;
                    iSearchPoiData.getPoiExtra().put("floor_id", tipItem.strf_nona);
                }
                if (tipItem.x_entr > 0.0d && tipItem.y_entr > 0.0d) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new GeoPoint(tipItem.x_entr, tipItem.y_entr));
                    iSearchPoiData.setEntranceList(arrayList);
                }
                e((POI) iSearchPoiData);
                LogManager.actionLogV2("P00260", "B011");
                return;
            }
            this.N = SearchUtils.isNaviOfflineSearch();
            this.f = true;
            this.O = tipItem.type;
            c(tipItem);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
        if (r0 == -1) goto L_0x0030;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0027, code lost:
        b(r0);
        r4.a.setEditSelectAll(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0030, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000b, code lost:
        r0 = 97;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:3:0x000e, code lost:
        r0 = 81;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        r0 = -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(int r5) {
        /*
            r4 = this;
            r0 = 65
            r1 = 81
            r2 = 97
            r3 = -1
            switch(r5) {
                case 2: goto L_0x001b;
                case 3: goto L_0x0017;
                case 16: goto L_0x0014;
                case 17: goto L_0x0014;
                case 32: goto L_0x0011;
                case 33: goto L_0x0011;
                case 48: goto L_0x0025;
                case 49: goto L_0x0025;
                case 64: goto L_0x0025;
                case 65: goto L_0x0025;
                case 80: goto L_0x000e;
                case 81: goto L_0x000e;
                case 96: goto L_0x000b;
                case 97: goto L_0x000b;
                case 256: goto L_0x0014;
                case 512: goto L_0x0011;
                case 768: goto L_0x0025;
                case 3840: goto L_0x0011;
                default: goto L_0x000a;
            }
        L_0x000a:
            goto L_0x0024
        L_0x000b:
            r0 = 97
            goto L_0x0025
        L_0x000e:
            r0 = 81
            goto L_0x0025
        L_0x0011:
            r0 = 33
            goto L_0x0025
        L_0x0014:
            r0 = 17
            goto L_0x0025
        L_0x0017:
            r4.i()
            goto L_0x0024
        L_0x001b:
            com.amap.bundle.planhome.view.RouteEditView r5 = r4.a
            int r5 = r5.getMidCount()
            switch(r5) {
                case 1: goto L_0x0025;
                case 2: goto L_0x000e;
                case 3: goto L_0x000b;
                default: goto L_0x0024;
            }
        L_0x0024:
            r0 = -1
        L_0x0025:
            if (r0 == r3) goto L_0x0030
            r4.b(r0)
            com.amap.bundle.planhome.view.RouteEditView r5 = r4.a
            r5.setEditSelectAll(r0)
            return
        L_0x0030:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.search.fragment.DriveSearchCallbackFragment.a(int):void");
    }

    public final void a(int i2, int i3, int i4) {
        if (i2 > 0) {
            b(i2);
            CharSequence text = this.a.getText(i2);
            if (!TextUtils.isEmpty(text)) {
                int length = text.length();
                if (i3 > length || i4 > length) {
                    i3 = 0;
                    i4 = text.length();
                }
            }
            this.a.setEditSelection(i2, i3, i4);
        }
    }

    /* access modifiers changed from: private */
    public void b(int i2) {
        this.a.requestFocusWithId(i2);
        this.a.setImeOptions(i2, 3);
        this.a.setInputType(i2, 1);
    }

    private int b(RouteHeaderModel routeHeaderModel) {
        if (routeHeaderModel == null) {
            return 0;
        }
        int i2 = (routeHeaderModel.mStartPoi == null || routeHeaderModel.mStartPoi.getPoint().equals(W)) ? 0 : 1;
        if (routeHeaderModel.mEndPoi != null && !routeHeaderModel.mEndPoi.getPoint().equals(W)) {
            i2++;
        }
        if (i2 != 2 || this.k == null || ((this.k.mMidPois != null && this.k.mMidPois.size() != 0) || !a(routeHeaderModel.mStartPoi, routeHeaderModel.mEndPoi))) {
            return i2;
        }
        return 0;
    }

    public final void k() {
        boolean z2 = false;
        if (this.s != State.SUMMARY) {
            d(false);
        } else if (b(this.k) >= this.U) {
            if (this.s == State.SUMMARY) {
                z2 = true;
            }
            d(z2);
        } else {
            w();
            finish();
        }
    }

    private void d(boolean z2) {
        j();
        if (z2) {
            if (this.k.mChildPageClass != null && AbstractBaseMapPage.class.isAssignableFrom(this.k.mChildPageClass)) {
                this.w.setBackgroundColor(0);
            }
            a(this.k);
            if (c()) {
                n();
                a(false);
                g();
            }
            this.a.changeState(State.SUMMARY, true, this.aa);
            return;
        }
        if (c()) {
            n();
            a(false);
            g();
        }
        w().putObject("selectedfor", this.K);
        finish();
        LogManager.actionLogV2("P00260", "B012");
    }

    private void n() {
        if (this.k != null && this.k.mMidPois != null && c()) {
            Iterator<POI> it = this.k.mMidPois.iterator();
            while (it.hasNext()) {
                POI next = it.next();
                if (next == null || TextUtils.isEmpty(next.getName()) || next.getPoint().equals(W)) {
                    it.remove();
                }
            }
            if (this.k.mMidPois.size() == 0) {
                this.k.mMidPois = null;
            }
        }
    }

    private void o() {
        if (this.k != null && this.k.mMidPois != null) {
            Iterator<POI> it = this.k.mMidPois.iterator();
            while (it.hasNext()) {
                POI next = it.next();
                if (bnx.a(next, this.k.mStartPoi) || bnx.a(next, this.k.mEndPoi)) {
                    it.remove();
                }
            }
        }
    }

    private boolean p() {
        int size = (this.T == null || this.T.mMidPois == null) ? 0 : this.T.mMidPois.size();
        int size2 = (this.k == null || this.k.mMidPois == null) ? 0 : this.k.mMidPois.size();
        if (size == 0 && size2 == 0) {
            return false;
        }
        if (size != size2) {
            return true;
        }
        for (int i2 = 0; i2 < this.T.mMidPois.size(); i2++) {
            if (!bnx.a(this.T.mMidPois.get(i2), this.k.mMidPois.get(i2))) {
                return true;
            }
        }
        return false;
    }

    private boolean q() {
        POI poi = null;
        POI poi2 = this.T == null ? null : this.T.mStartPoi;
        if (this.k != null) {
            poi = this.k.mStartPoi;
        }
        if (poi2 == null && poi == null) {
            return false;
        }
        return poi2 == null || poi == null || !bnx.a(poi2, poi);
    }

    private boolean r() {
        POI poi = null;
        POI poi2 = this.T == null ? null : this.T.mEndPoi;
        if (this.k != null) {
            poi = this.k.mEndPoi;
        }
        if (poi2 == null && poi == null) {
            return false;
        }
        return poi2 == null || poi == null || !bnx.a(poi2, poi);
    }

    private static boolean a(POI poi, POI poi2) {
        if (poi == null || poi2 == null) {
            return false;
        }
        if (TextUtils.equals(AMapAppGlobal.getApplication().getResources().getString(R.string.route_my_position), poi.getName()) && TextUtils.equals(poi2.getName(), poi.getName())) {
            return true;
        }
        if (TextUtils.equals(poi2.getName(), poi.getName()) && !TextUtils.isEmpty(poi.getId()) && TextUtils.equals(poi.getId(), poi2.getId())) {
            return true;
        }
        if (!poi.getPoint().equals(W) && !poi2.getPoint().equals(W) && poi.getPoint().equals(poi2.getPoint())) {
            return true;
        }
        if (poi.getPid().length() < 10 || poi2.getPid().length() < 10 || !poi.getPid().equals(poi2.getPid())) {
            return false;
        }
        return true;
    }

    private boolean a(POI poi, int i2) {
        if (!(this.k == null || this.k.mMidPois == null || this.k.mMidPois.size() <= 0)) {
            for (int i3 = 0; i3 < this.k.mMidPois.size(); i3++) {
                POI poi2 = this.k.mMidPois.get(i3);
                if (i3 != i2 && a(poi2, poi)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean s() {
        return c() && this.k != null && this.k.mMidPois != null && this.k.mMidPois.size() > 0;
    }

    private boolean b(POI poi) {
        boolean z2;
        int u2 = u();
        int i2 = 0;
        if (u2 != 17) {
            if (u2 != 33) {
                if (u2 == 65 || u2 == 81 || u2 == 97) {
                    if (this.k == null || this.k.mStartPoi == null || !a(this.k.mStartPoi, poi)) {
                        z2 = false;
                    } else {
                        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.drive_search_same_from_mid));
                        z2 = true;
                    }
                    if (!(this.k == null || this.k.mEndPoi == null || !a(this.k.mEndPoi, poi))) {
                        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.drive_search_same_from_mid));
                        z2 = true;
                    }
                    if (u2 != 65) {
                        i2 = u2 == 81 ? 1 : 2;
                    }
                    AMapLog.d("DriveSearchCallbackFragment", "checkInputSame---excludeIndex=".concat(String.valueOf(i2)));
                    if (!a(poi, i2)) {
                        return z2;
                    }
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.drive_search_same_mid_from_mid));
                    return true;
                }
            } else if (!s()) {
                if (!(this.k == null || this.k.mStartPoi == null || !a(this.k.mStartPoi, poi))) {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.drive_search_same_from_to));
                    return true;
                }
            } else if (a(poi, -1)) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.drive_search_same_end_to_mid));
                return true;
            }
        } else if (!s()) {
            if (this.k.mEndPoi != null && a(this.k.mEndPoi, poi)) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.drive_search_same_from_to));
                return true;
            }
        } else if (a(poi, -1)) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getResources().getString(R.string.drive_search_same_from_to_mid));
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean t() {
        int i2;
        boolean z2;
        if (!(this.k == null || this.k.mStartPoi == null || this.k.mStartPoi.getPoint().equals(W) || this.k.mEndPoi == null || this.k.mEndPoi.getPoint().equals(W))) {
            List<POI> list = this.k.mMidPois;
            if (list == null) {
                i2 = 0;
            } else {
                i2 = list.size();
            }
            if (i2 != 0) {
                int i3 = 0;
                while (true) {
                    if (i3 >= i2) {
                        z2 = true;
                        break;
                    }
                    POI poi = list.get(i3);
                    if (poi != null) {
                        StringBuilder sb = new StringBuilder("checkInputPointComplete   poi name:");
                        sb.append(poi.getName());
                        AMapLog.i("DriveSearchCallbackFragment", sb.toString());
                    }
                    StringBuilder sb2 = new StringBuilder("checkInputPointComplete   poi isPOIValid:");
                    sb2.append(bnx.a(poi));
                    AMapLog.i("DriveSearchCallbackFragment", sb2.toString());
                    if (poi != null && !bnx.a(poi)) {
                        z2 = false;
                        break;
                    }
                    i3++;
                }
                if (z2) {
                    return true;
                }
            } else if (a(this.k.mStartPoi, this.k.mEndPoi)) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void c(POI poi) {
        if (poi != null) {
            int u2 = u();
            if (u2 == 17) {
                this.k.mStartPoi = poi;
                if (!c()) {
                    o();
                }
            } else if (u2 == 33) {
                this.k.mEndPoi = poi;
                if (!c()) {
                    o();
                }
            } else if (u2 != 65) {
                if (u2 != 81) {
                    if (u2 == 97 && this.k != null && this.k.mMidPois != null && this.k.mMidPois.size() > 2) {
                        this.k.mMidPois.set(2, poi);
                    }
                } else if (!(this.k == null || this.k.mMidPois == null || this.k.mMidPois.size() <= 1)) {
                    this.k.mMidPois.set(1, poi);
                }
            } else if (!(this.k == null || this.k.mMidPois == null || this.k.mMidPois.size() <= 0)) {
                this.k.mMidPois.set(0, poi);
            }
            if (!TextUtils.equals(poi.getName(), this.a.getText(u2))) {
                a(poi.getName());
            }
        }
    }

    public final void a(POI poi, int i2, boolean z2) {
        StringBuilder sb = new StringBuilder("finishCurrentPage---mIsAutoSearch=");
        sb.append(this.m);
        AMapLog.d("DriveSearchCallbackFragment", sb.toString());
        if (this.m) {
            PageBundle d2 = d(poi);
            if (!z2) {
                if (i2 == 1) {
                    d2.putObject("SelectPoiFromMapFragment.MapClickResult", poi);
                } else {
                    if (i2 == 2) {
                        String str = this.Q ? "planend_record" : "planend_sug";
                        if (!TextUtils.isEmpty(str)) {
                            d2.putString("bundle_key_from_page", str);
                        }
                    }
                }
            }
            setResult(ResultType.OK, d2);
            finish();
        } else if (!b(poi)) {
            c(poi);
            if (t()) {
                n();
                w();
                finish();
                return;
            }
            i();
        } else {
            int u2 = u();
            if (u2 > 0) {
                this.a.setText(u2, "");
            }
        }
    }

    public final void l() {
        if (this.g.equals(RouteType.CAR)) {
            this.a.setVUIExpectVisibility(8);
        }
    }

    private int u() {
        int focusWidgetId = this.a.getFocusWidgetId();
        return (focusWidgetId >= 0 || this.o == -1) ? focusWidgetId : this.o;
    }

    /* access modifiers changed from: 0000 */
    public final String m() {
        return this.a.getText(u()).toString();
    }

    public final void c(@Nullable TipItem tipItem) {
        String str;
        v();
        if (this.m) {
            str = this.e;
        } else {
            this.o = this.a.getFocusWidgetId();
            str = tipItem != null ? tipItem.name : null;
        }
        AMapLog.d("DriveSearchCallbackFragment", "startSearch---keyword=".concat(String.valueOf(str)));
        if (c(str)) {
            if ("我的位置".equals(str)) {
                y();
                return;
            }
            bty mapView = DoNotUseTool.getMapManager().getMapView();
            if (mapView != null) {
                ISearchService iSearchService = (ISearchService) a.a.a(ISearchService.class);
                if (iSearchService != null) {
                    boolean z2 = this.N;
                    aen aen = new aen(str, mapView.H());
                    if (this.g != null) {
                        switch (this.g) {
                            case BUS:
                                if (!this.f) {
                                    aen.c = "513000";
                                } else if (this.O == 0) {
                                    aen.c = "512000";
                                } else if (this.O == 1) {
                                    aen.c = "511000";
                                }
                                aen.f = ShowRouteActionProcessor.SEARCH_TYPE_BUS;
                                break;
                            case CAR:
                                if (!this.f) {
                                    aen.c = "533000";
                                } else if (this.O == 0) {
                                    aen.c = "532000";
                                } else if (this.O == 1) {
                                    aen.c = "531000";
                                }
                                aen.f = "car";
                                break;
                            case ONFOOT:
                                if (!this.f) {
                                    aen.c = "523000";
                                } else if (this.O == 0) {
                                    aen.c = "522000";
                                } else if (this.O == 1) {
                                    aen.c = "521000";
                                }
                                aen.f = "foot";
                                break;
                        }
                    }
                    if (tipItem != null && !TextUtils.isEmpty(tipItem.adcode)) {
                        aen.a(tipItem.adcode);
                    }
                    aen.d = SuperId.getInstance().getScenceId();
                    dih dih = new dih(this, aen, this.N, this.R, this.H);
                    this.M = iSearchService.b(aew.a((aem) aen), z2 ? 1 : 0, dih);
                    dii.a(str, this.M, getContext());
                }
            }
        }
    }

    private void v() {
        String str = "";
        if (this.m && getArguments() != null) {
            str = getArguments().getString("hint");
        } else if (this.a != null) {
            CharSequence hint = this.a.getHint(this.a.getFocusWidgetId());
            if (!TextUtils.isEmpty(hint)) {
                str = hint.toString();
            }
        }
        String string = getString(R.string.drive_enter_text);
        if (str == null || !str.contains(string)) {
            this.R = str;
        } else {
            this.R = str.replace(string, getString(R.string.confirm));
        }
    }

    private boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            ToastHelper.showLongToast(getString(R.string.act_search_error_empty));
            return false;
        } else if (!TextUtils.isEmpty(str.trim())) {
            return true;
        } else {
            ToastHelper.showLongToast(getString(R.string.act_search_error_empty));
            return false;
        }
    }

    /* access modifiers changed from: private */
    public PageBundle d(POI poi) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("result_poi", poi);
        pageBundle.putObject("selectedfor", this.K);
        return pageBundle;
    }

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
    }

    /* access modifiers changed from: private */
    public void e(POI poi) {
        if (poi != null) {
            a(poi);
            if (!b(poi)) {
                c(poi);
                if (t()) {
                    d(this.s == State.SUMMARY);
                    if (this.n != null) {
                        this.n.callback(poi);
                    }
                } else {
                    i();
                }
            } else {
                int u2 = u();
                if (u2 > 0) {
                    this.a.setText(u2, "");
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public PageBundle w() {
        PageBundle pageBundle = new PageBundle();
        if (b(this.k) >= this.U) {
            x();
            pageBundle.putObject(RouteHeaderModel.ROUTE_HEADER_MODEL_KEY, this.k);
            td.a().a(1);
        }
        int i2 = 0;
        int i3 = (q() ? 1 : 0) + (r() ? 2 : 0);
        if (p()) {
            i2 = 4;
        }
        pageBundle.putObject(RouteHeaderModel.ROUTE_HEADER_MODEL_KEY_HAS_CHANGED, Integer.valueOf(i3 + i2));
        setResult(ResultType.OK, pageBundle);
        return pageBundle;
    }

    private void x() {
        if (this.k != null) {
            if (this.k.mStartPoi != null && W.equals(this.k.mStartPoi.getPoint())) {
                this.k.mStartPoi = null;
            }
            if (this.k.mEndPoi != null && W.equals(this.k.mEndPoi.getPoint())) {
                this.k.mEndPoi = null;
            }
            n();
        }
    }

    public final void a(POI poi) {
        if (poi != null && !getResources().getString(R.string.act_fromto_frommap).equals(poi.getName())) {
            TipItem tipItem = new TipItem();
            tipItem.poiid = poi.getId();
            tipItem.name = poi.getName();
            tipItem.adcode = poi.getAdCode();
            tipItem.addr = poi.getAddr();
            tipItem.x = poi.getPoint().getLongitude();
            tipItem.y = poi.getPoint().getLatitude();
            tipItem.time = new Date();
            if (poi.getEntranceList() != null && !poi.getEntranceList().isEmpty()) {
                Iterator<GeoPoint> it = poi.getEntranceList().iterator();
                while (it.hasNext()) {
                    GeoPoint next = it.next();
                    DPoint a2 = cfg.a((long) next.x, (long) next.y);
                    if (a2.x > 0.0d) {
                        tipItem.x_entr = a2.x;
                    }
                    if (a2.y > 0.0d) {
                        tipItem.y_entr = a2.y;
                    }
                }
            }
            if (this.P != null) {
                tipItem.iconinfo = this.P.iconinfo;
                if (this.P.x_entr > 0.0d) {
                    tipItem.x_entr = this.P.x_entr;
                }
                if (this.P.y_entr > 0.0d) {
                    tipItem.y_entr = this.P.y_entr;
                }
                if (this.P.isRating(this.P.richRating)) {
                    tipItem.richRating = this.P.richRating;
                } else {
                    tipItem.richRating = "";
                }
                if (tipItem.richRating == null || tipItem.richRating.isEmpty()) {
                    tipItem.numReview = "";
                } else {
                    tipItem.numReview = this.P.numReview;
                }
                tipItem.poiTag = this.P.poiTag;
                this.P = null;
            }
            tipItem.newType = poi.getType();
            tipItem.endPoiExtension = poi.getEndPoiExtension();
            tipItem.transparent = poi.getTransparent();
            ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
            tipItem.parent = iSearchPoiData.getParent();
            tipItem.childType = iSearchPoiData.getChildType();
            tipItem.towardsAngle = iSearchPoiData.getTowardsAngle();
            tipItem.f_nona = iSearchPoiData.getFnona();
            if (getContext() != null && !tipItem.name.equalsIgnoreCase(SearchConst.a)) {
                SearchHistoryHelper.getInstance(getContext()).saveTipItem(tipItem);
            }
        }
    }

    private void a(final Callback.a aVar) {
        String string = getString(R.string.drive_please_wait2);
        if (this.S == null) {
            this.S = new ProgressDlg(getActivity(), string, "");
        }
        this.S.setMessage(string);
        this.S.setCancelable(true);
        this.S.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (aVar != null) {
                    aVar.cancel();
                }
            }
        });
        this.S.show();
    }

    /* access modifiers changed from: private */
    public CharSequence[] c(int i2) {
        if (i2 == 1) {
            return new CharSequence[]{this.l};
        } else if (i2 == 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.l);
            sb.append(1);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.l);
            sb2.append(2);
            return new CharSequence[]{sb.toString(), sb2.toString()};
        } else if (i2 != 3) {
            return null;
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(this.l);
            sb3.append(1);
            StringBuilder sb4 = new StringBuilder();
            sb4.append(this.l);
            sb4.append(2);
            StringBuilder sb5 = new StringBuilder();
            sb5.append(this.l);
            sb5.append(3);
            return new CharSequence[]{sb3.toString(), sb4.toString(), sb5.toString()};
        }
    }

    public final void c(boolean z2) {
        if (t()) {
            j();
        }
        this.o = this.a.getFocusWidgetId();
        this.a.setOnTextChangeListener(null);
        POI poi = this.k.mStartPoi;
        this.k.mStartPoi = this.k.mEndPoi;
        this.k.mEndPoi = poi;
        if (c()) {
            int size = this.k.mMidPois != null ? this.k.mMidPois.size() : 0;
            CharSequence[] midTexts = this.a.getMidTexts();
            if (size > 0 && midTexts != null && size == midTexts.length) {
                for (int i2 = 0; i2 < size; i2++) {
                    if (this.k.mMidPois.get(i2) != null && TextUtils.isEmpty(this.k.mMidPois.get(i2).getName()) && !TextUtils.isEmpty(midTexts[i2])) {
                        this.k.mMidPois.get(i2).setName(midTexts[i2].toString());
                    }
                }
            }
            List<POI> list = this.k.mMidPois;
            if (list != null && list.size() > 1) {
                Collections.reverse(list);
            }
        }
        AnonymousClass7 r0 = new Runnable() {
            public final void run() {
                int editSelectionStart = DriveSearchCallbackFragment.this.a.getEditSelectionStart(DriveSearchCallbackFragment.this.o);
                int editSelectionEnd = DriveSearchCallbackFragment.this.a.getEditSelectionEnd(DriveSearchCallbackFragment.this.o);
                DriveSearchCallbackFragment.z(DriveSearchCallbackFragment.this);
                if (!DriveSearchCallbackFragment.this.t()) {
                    DriveSearchCallbackFragment.a(DriveSearchCallbackFragment.this, editSelectionStart, editSelectionEnd);
                }
                DriveSearchCallbackFragment.this.a.exchangeAnimator(DriveSearchCallbackFragment.this.X);
            }
        };
        if (!z2) {
            r0.run();
        } else {
            this.a.post(r0);
        }
    }

    public final boolean c() {
        return this.g != null && (this.g == RouteType.CAR || this.g == RouteType.TRUCK);
    }

    public final boolean a() {
        if (this.k != null) {
            return this.k.mCanExchange;
        }
        return false;
    }

    public final void b() {
        c(false);
    }

    public final void a(List<POI> list) {
        if (c()) {
            this.k.mMidPois = list;
            n();
            a(true);
            g();
        }
    }

    public final List<POI> d() {
        n();
        if (this.k != null) {
            return this.k.mMidPois;
        }
        return null;
    }

    public final POI e() {
        if (this.k != null) {
            return this.k.mStartPoi;
        }
        return null;
    }

    public final POI f() {
        if (this.k != null) {
            return this.k.mEndPoi;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void y() {
        if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
            POI createPOI = POIFactory.createPOI(SearchConst.a, LocationInstrument.getInstance().getLatestPosition());
            if (this.L == SearchFor.CUSTOM_ADDRESS) {
                a(ReverseGeocodeManager.get(new GeoReverseCallback(createPOI), createPOI.getPoint()));
            } else {
                e(createPOI);
            }
        } else {
            ToastHelper.showLongToast(getString(R.string.drive_location_failed));
        }
        LogManager.actionLogV2("P00260", "B008");
    }

    public final void b(boolean z2) {
        if (this.k != null) {
            if (this.k.mMidPois == null) {
                this.k.mMidPois = new ArrayList();
            }
            this.k.mMidPois.add(POIFactory.createPOI());
        }
        AnonymousClass6 r0 = new Runnable() {
            public final void run() {
                DriveSearchCallbackFragment.this.a.addMidView(true);
                switch (DriveSearchCallbackFragment.this.a.getMidCount()) {
                    case 1:
                        DriveSearchCallbackFragment.this.a.setHint(65, DriveSearchCallbackFragment.this.l);
                        DriveSearchCallbackFragment.this.b(65);
                        return;
                    case 2:
                        RouteEditView b = DriveSearchCallbackFragment.this.a;
                        StringBuilder sb = new StringBuilder();
                        sb.append(DriveSearchCallbackFragment.this.l);
                        sb.append(1);
                        b.setHint(65, sb.toString());
                        RouteEditView b2 = DriveSearchCallbackFragment.this.a;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(DriveSearchCallbackFragment.this.l);
                        sb2.append(2);
                        b2.setHint(81, sb2.toString());
                        DriveSearchCallbackFragment.this.b(81);
                        return;
                    case 3:
                        RouteEditView b3 = DriveSearchCallbackFragment.this.a;
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(DriveSearchCallbackFragment.this.l);
                        sb3.append(1);
                        b3.setHint(65, sb3.toString());
                        RouteEditView b4 = DriveSearchCallbackFragment.this.a;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(DriveSearchCallbackFragment.this.l);
                        sb4.append(2);
                        b4.setHint(81, sb4.toString());
                        RouteEditView b5 = DriveSearchCallbackFragment.this.a;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(DriveSearchCallbackFragment.this.l);
                        sb5.append(3);
                        b5.setHint(97, sb5.toString());
                        DriveSearchCallbackFragment.this.b(97);
                        break;
                }
            }
        };
        if (!z2) {
            r0.run();
        } else {
            this.a.post(r0);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dik(this);
    }

    static /* synthetic */ void a(DriveSearchCallbackFragment driveSearchCallbackFragment, int i2, String str) {
        if (i2 != 17) {
            if (i2 != 33) {
                if (i2 != 65) {
                    if (i2 != 81) {
                        if (i2 == 97 && driveSearchCallbackFragment.k != null && driveSearchCallbackFragment.k.mMidPois != null && driveSearchCallbackFragment.k.mMidPois.size() > 2 && !TextUtils.equals(str, driveSearchCallbackFragment.k.mMidPois.get(2).getName())) {
                            driveSearchCallbackFragment.k.mMidPois.get(2).setPoint(W);
                            driveSearchCallbackFragment.k.mMidPois.get(2).setName("");
                        }
                    } else if (driveSearchCallbackFragment.k != null && driveSearchCallbackFragment.k.mMidPois != null && driveSearchCallbackFragment.k.mMidPois.size() > 1 && !TextUtils.equals(str, driveSearchCallbackFragment.k.mMidPois.get(1).getName())) {
                        driveSearchCallbackFragment.k.mMidPois.get(1).setPoint(W);
                        driveSearchCallbackFragment.k.mMidPois.get(1).setName("");
                    }
                } else if (driveSearchCallbackFragment.k != null && driveSearchCallbackFragment.k.mMidPois != null && driveSearchCallbackFragment.k.mMidPois.size() > 0 && !TextUtils.equals(str, driveSearchCallbackFragment.k.mMidPois.get(0).getName())) {
                    driveSearchCallbackFragment.k.mMidPois.get(0).setPoint(W);
                    driveSearchCallbackFragment.k.mMidPois.get(0).setName("");
                }
            } else if (!(driveSearchCallbackFragment.k == null || driveSearchCallbackFragment.k.mEndPoi == null || TextUtils.equals(str, driveSearchCallbackFragment.k.mEndPoi.getName()))) {
                driveSearchCallbackFragment.k.mEndPoi.setPoint(W);
                driveSearchCallbackFragment.k.mEndPoi.setName("");
            }
        } else if (!(driveSearchCallbackFragment.k == null || driveSearchCallbackFragment.k.mStartPoi == null || TextUtils.equals(str, driveSearchCallbackFragment.k.mStartPoi.getName()))) {
            driveSearchCallbackFragment.k.mStartPoi.setPoint(W);
            driveSearchCallbackFragment.k.mStartPoi.setName("");
        }
    }

    static /* synthetic */ String[] b(List list) {
        int size = list.size();
        String[] strArr = new String[size];
        for (int i2 = 0; i2 < size; i2++) {
            FavoritePOI favoritePOI = (FavoritePOI) list.get(i2);
            String customName = favoritePOI.getCustomName();
            if (TextUtils.isEmpty(customName)) {
                customName = favoritePOI.getName();
            }
            strArr[i2] = customName;
        }
        return strArr;
    }

    static /* synthetic */ SelectPoiFromMapBean g(DriveSearchCallbackFragment driveSearchCallbackFragment) {
        SelectPoiFromMapBean selectPoiFromMapBean = new SelectPoiFromMapBean();
        if (driveSearchCallbackFragment.k.mStartPoi != null && !"我的位置".equals(driveSearchCallbackFragment.k.mStartPoi.getName()) && !W.equals(driveSearchCallbackFragment.k.mStartPoi.getPoint())) {
            selectPoiFromMapBean.setFromPOI(driveSearchCallbackFragment.k.mStartPoi);
        } else if (driveSearchCallbackFragment.k.mStartPoi != null && "我的位置".equals(driveSearchCallbackFragment.k.mStartPoi.getName()) && driveSearchCallbackFragment.o == 17) {
            POI createPOI = POIFactory.createPOI();
            createPOI.setPoint(LocationInstrument.getInstance().getLatestPosition());
            selectPoiFromMapBean.setFromPOI(createPOI);
        }
        if ((driveSearchCallbackFragment.g == RouteType.TRUCK || driveSearchCallbackFragment.g == RouteType.CAR) && driveSearchCallbackFragment.k.mMidPois != null) {
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < driveSearchCallbackFragment.k.mMidPois.size(); i2++) {
                arrayList.add(driveSearchCallbackFragment.k.mMidPois.get(i2));
            }
            selectPoiFromMapBean.setMidPOIs(arrayList);
        }
        if (driveSearchCallbackFragment.k.mEndPoi != null && !W.equals(driveSearchCallbackFragment.k.mEndPoi.getPoint())) {
            selectPoiFromMapBean.setToPOI(driveSearchCallbackFragment.k.mEndPoi);
        }
        return selectPoiFromMapBean;
    }

    static /* synthetic */ void h(DriveSearchCallbackFragment driveSearchCallbackFragment) {
        int i2 = driveSearchCallbackFragment.o;
        if (i2 == 17) {
            driveSearchCallbackFragment.K = SelectFor.FROM_POI;
        } else if (i2 == 33) {
            driveSearchCallbackFragment.K = SelectFor.TO_POI;
        } else if (i2 == 65) {
            if (((driveSearchCallbackFragment.k == null || driveSearchCallbackFragment.k.mMidPois == null) ? 0 : driveSearchCallbackFragment.k.mMidPois.size()) == 1) {
                driveSearchCallbackFragment.K = SelectFor.MID_POI;
            } else {
                driveSearchCallbackFragment.K = SelectFor.MID_POI_1;
            }
        } else if (i2 != 81) {
            if (i2 == 97) {
                driveSearchCallbackFragment.K = SelectFor.MID_POI_3;
            }
        } else {
            driveSearchCallbackFragment.K = SelectFor.MID_POI_2;
        }
    }

    static /* synthetic */ void n(DriveSearchCallbackFragment driveSearchCallbackFragment) {
        try {
            String m2 = driveSearchCallbackFragment.m();
            if (TextUtils.isEmpty(m2)) {
                ToastHelper.showLongToast(driveSearchCallbackFragment.getString(R.string.act_search_error_empty));
                return;
            }
            TipItem tipItem = new TipItem();
            tipItem.name = m2;
            tipItem.isFromRealSceneSearch = true;
            SuperId.getInstance().reset();
            SuperId.getInstance().setBit1(driveSearchCallbackFragment.E);
            SuperId.getInstance().setBit2("03");
            driveSearchCallbackFragment.b(tipItem);
        } catch (Exception e2) {
            kf.a((Throwable) e2);
        }
    }

    static /* synthetic */ void t(DriveSearchCallbackFragment driveSearchCallbackFragment) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (driveSearchCallbackFragment.s == State.SUMMARY) {
                jSONObject.put("type", "path_result");
            } else {
                jSONObject.put("type", "path_plan");
            }
            if (driveSearchCallbackFragment.g != null) {
                if (driveSearchCallbackFragment.g == RouteType.CAR) {
                    jSONObject.put("status", "0");
                } else if (driveSearchCallbackFragment.g == RouteType.TRUCK) {
                    jSONObject.put("status", "7");
                }
            }
            LogManager.actionLogV2("P00335", "B002", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARNING: type inference failed for: r1v0 */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r1v5, types: [int] */
    /* JADX WARNING: type inference failed for: r1v6, types: [boolean] */
    /* JADX WARNING: type inference failed for: r1v7 */
    /* JADX WARNING: type inference failed for: r1v8 */
    /* JADX WARNING: type inference failed for: r1v9 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r1v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], ?[boolean, int, float, short, byte, char], int]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], boolean]
      mth insns count: 37
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void u(com.autonavi.minimap.drive.search.fragment.DriveSearchCallbackFragment r3) {
        /*
            boolean r0 = r3.t()
            r1 = 0
            if (r0 == 0) goto L_0x0012
            com.autonavi.bundle.routecommon.inter.IRouteEditView$State r0 = r3.s
            com.autonavi.bundle.routecommon.inter.IRouteEditView$State r2 = com.autonavi.bundle.routecommon.inter.IRouteEditView.State.SUMMARY
            if (r0 != r2) goto L_0x000e
            r1 = 1
        L_0x000e:
            r3.d(r1)
            return
        L_0x0012:
            com.autonavi.common.model.RouteHeaderModel r0 = r3.k
            if (r0 == 0) goto L_0x0024
            com.autonavi.common.model.RouteHeaderModel r0 = r3.k
            java.util.List<com.autonavi.common.model.POI> r0 = r0.mMidPois
            if (r0 == 0) goto L_0x0024
            com.autonavi.common.model.RouteHeaderModel r0 = r3.k
            java.util.List<com.autonavi.common.model.POI> r0 = r0.mMidPois
            int r1 = r0.size()
        L_0x0024:
            if (r1 != 0) goto L_0x004f
            com.autonavi.common.model.RouteHeaderModel r0 = r3.k
            if (r0 == 0) goto L_0x0049
            com.autonavi.common.model.RouteHeaderModel r0 = r3.k
            com.autonavi.common.model.POI r0 = r0.mStartPoi
            com.autonavi.common.model.RouteHeaderModel r1 = r3.k
            com.autonavi.common.model.POI r1 = r1.mEndPoi
            boolean r0 = a(r0, r1)
            if (r0 == 0) goto L_0x0049
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.res.Resources r0 = r0.getResources()
            int r1 = com.autonavi.minimap.R.string.drive_search_same_from_to
            java.lang.String r0 = r0.getString(r1)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r0)
        L_0x0049:
            r0 = 33
            r3.a(r0)
            return
        L_0x004f:
            r3.i()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.search.fragment.DriveSearchCallbackFragment.u(com.autonavi.minimap.drive.search.fragment.DriveSearchCallbackFragment):void");
    }

    static /* synthetic */ void v(DriveSearchCallbackFragment driveSearchCallbackFragment) {
        int focusWidgetId = driveSearchCallbackFragment.a.getFocusWidgetId();
        if (focusWidgetId == 17) {
            driveSearchCallbackFragment.b(driveSearchCallbackFragment.k.mStartPoi);
            return;
        }
        if (focusWidgetId == 33) {
            driveSearchCallbackFragment.b(driveSearchCallbackFragment.k.mEndPoi);
        }
    }

    static /* synthetic */ void x(DriveSearchCallbackFragment driveSearchCallbackFragment) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (driveSearchCallbackFragment.s == State.SUMMARY) {
                jSONObject.put("type", "path_result");
            } else {
                jSONObject.put("type", "path_plan");
            }
            if (driveSearchCallbackFragment.g != null) {
                if (driveSearchCallbackFragment.g == RouteType.CAR) {
                    jSONObject.put("status", "0");
                } else if (driveSearchCallbackFragment.g == RouteType.BUS) {
                    jSONObject.put("status", "1");
                } else if (driveSearchCallbackFragment.g == RouteType.ONFOOT) {
                    jSONObject.put("status", "2");
                } else if (driveSearchCallbackFragment.g == RouteType.RIDE) {
                    jSONObject.put("status", "3");
                } else if (driveSearchCallbackFragment.g == RouteType.TRAIN) {
                    jSONObject.put("status", "4");
                } else if (driveSearchCallbackFragment.g == RouteType.COACH) {
                    jSONObject.put("status", "5");
                } else if (driveSearchCallbackFragment.g == RouteType.TAXI) {
                    jSONObject.put("status", "6");
                } else if (driveSearchCallbackFragment.g == RouteType.TRUCK) {
                    jSONObject.put("status", "7");
                } else if (driveSearchCallbackFragment.g == RouteType.FREERIDE) {
                    jSONObject.put("status", "8");
                }
            }
            LogManager.actionLogV2("P00335", "B001", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    static /* synthetic */ void z(DriveSearchCallbackFragment driveSearchCallbackFragment) {
        CharSequence startText = driveSearchCallbackFragment.a.getStartText();
        CharSequence endText = driveSearchCallbackFragment.a.getEndText();
        if (!TextUtils.isEmpty(endText)) {
            driveSearchCallbackFragment.a.setStartText(endText);
        } else {
            driveSearchCallbackFragment.a.setStartText(null);
        }
        if (!TextUtils.isEmpty(startText)) {
            driveSearchCallbackFragment.a.setEndText(startText);
        } else {
            driveSearchCallbackFragment.a.setEndText(null);
        }
        if (driveSearchCallbackFragment.c()) {
            driveSearchCallbackFragment.g();
        }
    }

    static /* synthetic */ void a(DriveSearchCallbackFragment driveSearchCallbackFragment, int i2, int i3) {
        if (driveSearchCallbackFragment.o == 17) {
            driveSearchCallbackFragment.o = 33;
        } else if (driveSearchCallbackFragment.o == 33) {
            driveSearchCallbackFragment.o = 17;
        } else {
            int midCount = driveSearchCallbackFragment.a.getMidCount();
            if (midCount == 2) {
                if (driveSearchCallbackFragment.o == 65) {
                    driveSearchCallbackFragment.o = 81;
                } else if (driveSearchCallbackFragment.o == 81) {
                    driveSearchCallbackFragment.o = 65;
                }
            }
            if (midCount == 3) {
                if (driveSearchCallbackFragment.o == 65) {
                    driveSearchCallbackFragment.o = 97;
                } else if (driveSearchCallbackFragment.o == 97) {
                    driveSearchCallbackFragment.o = 65;
                }
            }
        }
        driveSearchCallbackFragment.a(driveSearchCallbackFragment.o, i2, i3);
    }

    static /* synthetic */ void b(DriveSearchCallbackFragment driveSearchCallbackFragment, AnimatorListener animatorListener) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(driveSearchCallbackFragment.b, "TranslationY", new float[]{0.0f, (float) ags.a((Context) AMapAppGlobal.getApplication()).height()});
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(driveSearchCallbackFragment.b, "alpha", new float[]{1.0f, 0.0f});
        driveSearchCallbackFragment.u = new AnimatorSet();
        ofFloat.setDuration(375);
        ofFloat2.setDuration(375);
        driveSearchCallbackFragment.u.addListener(animatorListener);
        driveSearchCallbackFragment.u.play(ofFloat).with(ofFloat2);
        driveSearchCallbackFragment.u.start();
    }
}
