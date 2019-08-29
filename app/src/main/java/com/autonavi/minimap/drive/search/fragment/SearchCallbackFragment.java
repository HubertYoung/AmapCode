package com.autonavi.minimap.drive.search.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.planhome.view.RouteEditView;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.Callback.a;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.model.RouteHeaderModel;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.drive.search.controller.ISearchCompleteListener;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.search.model.SearchConst;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.search.view.SearchSuggestList;
import com.autonavi.minimap.widget.ListDialog;
import com.autonavi.minimap.widget.SearchEdit;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@PageAction("search.fragment.SearchCallbackFragment")
public class SearchCallbackFragment extends DriveBasePage<dir> {
    public static String a = "isShowMappoint";
    public static int b;
    public Callback<POI> A = null;
    public View B;
    public dke C;
    public RouteHeaderModel D = null;
    public RouteEditView E;
    public boolean F = false;
    public OnTouchListener G = new OnTouchListener() {
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (SearchCallbackFragment.this.c != null) {
                SearchCallbackFragment.this.c.hideInputMethod();
            }
            return false;
        }
    };
    public final OnClickListener H = new OnClickListener() {
        public final void onClick(View view) {
            if (SearchCallbackFragment.this.c != null) {
                SearchCallbackFragment.this.c.hideInputMethod();
            }
            SearchCallbackFragment.this.a();
            SearchCallbackFragment.this.finish();
            LogManager.actionLogV2("P00260", "B012");
        }
    };
    public final OnClickListener I = new OnClickListener() {
        private long b = 0;

        public final void onClick(View view) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (Math.abs(elapsedRealtime - this.b) >= 600) {
                this.b = elapsedRealtime;
                if (SearchCallbackFragment.this.c != null && SearchCallbackFragment.this.isStarted() && (SearchCallbackFragment.this.g == null || !SearchCallbackFragment.this.g.isShowing())) {
                    SearchCallbackFragment.this.c.showIatDialog();
                }
                LogManager.actionLogV2("P00260", "B013");
            }
        }
    };
    public final OnClickListener J = new OnClickListener() {
        public final void onClick(View view) {
            SearchCallbackFragment.this.b();
        }
    };
    public final OnClickListener K = new OnClickListener() {
        public final void onClick(View view) {
            if (SearchCallbackFragment.this.c != null) {
                SearchCallbackFragment.this.c.hideInputMethod();
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("FetchFor", SearchCallbackFragment.this.p);
            pageBundle.putSerializable("SelectPoiFromMapBean", SearchCallbackFragment.this.o);
            SearchCallbackFragment.this.startPageForResult((String) "amap.basemap.action.base_select_poi_from_map_page", pageBundle, 1);
            LogManager.actionLogV2("P00260", "B010");
        }
    };
    public final OnClickListener L = new OnClickListener() {
        public final void onClick(View view) {
            SuperId.getInstance().reset();
            if (!SearchCallbackFragment.this.O.equals("")) {
                SuperId.getInstance().setBit1(SearchCallbackFragment.this.O);
                SuperId.getInstance().setBit2("03");
            }
            SearchCallbackFragment.this.a((TipItem) null);
        }
    };
    ISearchCompleteListener M = new ISearchCompleteListener() {
        public final void a(String str) {
            SearchCallbackFragment.this.f.setText(str);
        }

        public final void a(POI poi) {
            SearchCallbackFragment.this.a(poi, (String) null);
        }
    };
    public OnClickListener N = new OnClickListener() {
        public final void onClick(View view) {
            com com2 = (com) ank.a(com.class);
            cop b = com2 != null ? com2.b(com2.a()) : null;
            if (SearchCallbackFragment.this.g == null) {
                SearchCallbackFragment.this.g = new ListDialog(SearchCallbackFragment.this.getActivity());
                SearchCallbackFragment.this.g.setDlgTitle(SearchCallbackFragment.this.getResources().getString(R.string.title_save_points));
            }
            if (b == null || b.a() == 0) {
                SearchCallbackFragment.this.g.setAdapter(new ArrayAdapter(SearchCallbackFragment.this.getActivity(), R.layout.v3_list_dialog_empty, new String[]{SearchCallbackFragment.this.getResources().getString(R.string.save_is_empty_msg)}));
                SearchCallbackFragment.this.g.setOnItemClickListener(null);
                SearchCallbackFragment.this.g.setComfirmBtnVisibility(8);
            } else {
                List<FavoritePOI> e = b.e();
                SearchCallbackFragment.this.g.setAdapter(new ArrayAdapter(SearchCallbackFragment.this.getActivity(), R.layout.v3_list_dialog_item, SearchCallbackFragment.a((List) e)));
                SearchCallbackFragment.this.g.setOnItemClickListener(new OnItemClickListener(e) {
                    final /* synthetic */ List val$savePois;

                    {
                        this.val$savePois = r2;
                    }

                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        if (SearchCallbackFragment.this.g != null) {
                            SearchCallbackFragment.this.g.dismiss();
                        }
                        SearchCallbackFragment.this.f.setText("");
                        SearchCallbackFragment.this.a((POI) this.val$savePois.get(i), (String) "planend_collect");
                    }
                });
            }
            if (SearchCallbackFragment.this.c == null || !SearchCallbackFragment.this.c.isIatDialogShowing()) {
                SearchCallbackFragment.this.g.show();
                LogManager.actionLogV2("P00260", "B009");
            }
        }
    };
    public String O = "";
    public final a P = new a() {
    };
    /* access modifiers changed from: private */
    public ProgressDlg Q;
    private boolean R = false;
    public SearchEdit c;
    public Button d;
    public ImageButton e;
    public EditText f;
    public ListDialog g;
    public String h;
    public String i;
    public boolean j;
    public SearchFor k = SearchFor.DEFAULT_POI;
    int l = -1;
    public RouteType m = null;
    public boolean n = false;
    public SelectPoiFromMapBean o = null;
    public SelectFor p;
    public String q = "";
    public boolean r = false;
    TipItem s;
    public adx t;
    public ListView u;
    public SearchHistoryList v;
    public ListView w;
    public SearchSuggestList x;
    public boolean y = false;
    /* access modifiers changed from: 0000 */
    public boolean z = true;

    class GeoReverseCallback implements Callback<String> {
        private final POI mPoi;

        public GeoReverseCallback(POI poi) {
            this.mPoi = poi;
        }

        public void callback(String str) {
            if (SearchCallbackFragment.this.Q != null && SearchCallbackFragment.this.Q.isShowing()) {
                SearchCallbackFragment.this.Q.dismiss();
            }
            if (!TextUtils.isEmpty(str)) {
                this.mPoi.setName(str);
                SearchCallbackFragment.this.a(this.mPoi, (String) "planend_myloc");
            }
        }

        public void error(Throwable th, boolean z) {
            if (SearchCallbackFragment.this.Q != null && SearchCallbackFragment.this.Q.isShowing()) {
                SearchCallbackFragment.this.Q.dismiss();
            }
        }
    }

    public enum SearchFor {
        DEFAULT_POI,
        CUSTOM_ADDRESS,
        QUICK_NAVI
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.fragment_searchcallback);
        requestScreenOrientation(1);
    }

    public final void a() {
        if (this.c != null) {
            this.c.hideInputMethod();
        }
        setResult(ResultType.CANCEL, (PageBundle) null);
    }

    public final void a(POI poi, String str) {
        if (this.c != null) {
            this.c.hideInputMethod();
        }
        if (poi != null) {
            a(poi);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("result_poi", poi);
            pageBundle.putObject("selectedfor", this.p);
            if (!TextUtils.isEmpty(str)) {
                pageBundle.putString("bundle_key_from_page", str);
            }
            if (this.F) {
                pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, bnx.b(poi).toString());
            }
            setResult(ResultType.OK, pageBundle);
        }
        if (this.A != null) {
            this.A.callback(poi);
        }
        finish();
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
            if (this.s != null) {
                tipItem.iconinfo = this.s.iconinfo;
                if (this.s.x_entr > 0.0d) {
                    tipItem.x_entr = this.s.x_entr;
                }
                if (this.s.y_entr > 0.0d) {
                    tipItem.y_entr = this.s.y_entr;
                }
                if (this.s.isRating(this.s.richRating)) {
                    tipItem.richRating = this.s.richRating;
                } else {
                    tipItem.richRating = "";
                }
                if (tipItem.richRating == null || tipItem.richRating.isEmpty()) {
                    tipItem.numReview = "";
                } else {
                    tipItem.numReview = this.s.numReview;
                }
                tipItem.poiTag = this.s.poiTag;
                this.s = null;
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

    public final void a(@Nullable TipItem tipItem) {
        this.i = null;
        String obj = this.f.getText().toString();
        boolean z2 = false;
        if (TextUtils.isEmpty(obj)) {
            ToastHelper.showLongToast(getString(R.string.act_search_error_empty));
        } else if (TextUtils.isEmpty(obj.trim())) {
            ToastHelper.showLongToast(getString(R.string.act_search_error_empty));
        } else {
            z2 = true;
        }
        if (z2) {
            if ("我的位置".equals(obj)) {
                b();
                return;
            }
            this.i = obj;
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager != null) {
                bty mapView = mapManager.getMapView();
                if (mapView != null) {
                    ISearchService iSearchService = (ISearchService) a.a.a(ISearchService.class);
                    if (iSearchService != null) {
                        boolean z3 = this.r;
                        aen aen = new aen(this.i, mapView.H());
                        if (this.m != null) {
                            switch (this.m) {
                                case BUS:
                                    if (!this.n) {
                                        aen.c = "513000";
                                    } else if (this.l == 0) {
                                        aen.c = "512000";
                                    } else if (this.l == 1) {
                                        aen.c = "511000";
                                    }
                                    aen.f = ShowRouteActionProcessor.SEARCH_TYPE_BUS;
                                    break;
                                case CAR:
                                    if (!this.n) {
                                        aen.c = "533000";
                                    } else if (this.l == 0) {
                                        aen.c = "532000";
                                    } else if (this.l == 1) {
                                        aen.c = "531000";
                                    }
                                    aen.f = "car";
                                    break;
                                case ONFOOT:
                                    if (!this.n) {
                                        aen.c = "523000";
                                    } else if (this.l == 0) {
                                        aen.c = "522000";
                                    } else if (this.l == 1) {
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
                        dih dih = new dih(this, aen, this.r, this.h, this.M);
                        this.t = iSearchService.b(aew.a((aem) aen), z3 ? 1 : 0, dih);
                        dii.a(obj, this.t, getContext());
                    }
                }
            }
        }
    }

    static void a(String str) {
        LogManager.actionLogV2("P00260", str);
    }

    /* access modifiers changed from: private */
    public void b() {
        if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
            POI createPOI = POIFactory.createPOI(SearchConst.a, LocationInstrument.getInstance().getLatestPosition());
            if (this.k == SearchFor.CUSTOM_ADDRESS) {
                final a aVar = ReverseGeocodeManager.get(new GeoReverseCallback(createPOI), createPOI.getPoint());
                String string = getString(R.string.drive_please_wait2);
                if (this.Q == null) {
                    this.Q = new ProgressDlg(getActivity(), string, "");
                }
                this.Q.setMessage(string);
                this.Q.setCancelable(true);
                this.Q.setOnCancelListener(new OnCancelListener() {
                    public final void onCancel(DialogInterface dialogInterface) {
                        if (aVar != null) {
                            aVar.cancel();
                        }
                    }
                });
                this.Q.show();
            } else {
                a(createPOI, (String) "planend_myloc");
            }
        } else {
            ToastHelper.showLongToast(getString(R.string.drive_location_failed));
        }
        LogManager.actionLogV2("P00260", "B008");
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dir(this);
    }

    static /* synthetic */ void c(SearchCallbackFragment searchCallbackFragment) {
        if (searchCallbackFragment.y && !searchCallbackFragment.R) {
            searchCallbackFragment.R = true;
            LogManager.actionLogV2("P00260", "B007");
        }
    }

    static /* synthetic */ String[] a(List list) {
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
}
