package defpackage;

import android.content.res.Configuration;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.planhome.view.RouteEditView;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.model.RouteHeaderModel;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.search.fragment.SearchCallbackFragment;
import com.autonavi.minimap.drive.search.fragment.SearchCallbackFragment.SearchFor;
import com.autonavi.minimap.search.model.SearchConst;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.search.view.SearchSuggestList;
import com.autonavi.minimap.widget.SearchEdit;
import com.autonavi.minimap.widget.SearchEdit.ISearchEditEventListener;
import java.util.ArrayList;

/* renamed from: dir reason: default package */
/* compiled from: SearchCallbackPresenter */
public final class dir extends sw<SearchCallbackFragment, dio> {
    public dir(SearchCallbackFragment searchCallbackFragment) {
        super(searchCallbackFragment);
    }

    public final void onPageCreated() {
        SearchCallbackFragment searchCallbackFragment = (SearchCallbackFragment) this.mPage;
        if (searchCallbackFragment.c != null) {
            searchCallbackFragment.c.clearFocus();
        }
        SearchCallbackFragment searchCallbackFragment2 = (SearchCallbackFragment) this.mPage;
        PageBundle arguments = searchCallbackFragment2.getArguments();
        if (arguments != null) {
            SearchCallbackFragment.b = arguments.getInt("from_page");
            searchCallbackFragment2.D = (RouteHeaderModel) arguments.getObject("header_data");
        }
        searchCallbackFragment2.u = (ListView) searchCallbackFragment2.getContentView().findViewById(R.id.history_list_view);
        searchCallbackFragment2.w = (ListView) searchCallbackFragment2.getContentView().findViewById(R.id.search_sug_container);
        View inflate = LayoutInflater.from(searchCallbackFragment2.getContext()).inflate(R.layout.fragment_search_callback_list_header, searchCallbackFragment2.u, false);
        View inflate2 = LayoutInflater.from(searchCallbackFragment2.getContext()).inflate(R.layout.fragment_search_callback_list_header, searchCallbackFragment2.w, false);
        inflate.findViewById(R.id.mylocation).setOnClickListener(searchCallbackFragment2.J);
        inflate.findViewById(R.id.mappoint).setOnClickListener(searchCallbackFragment2.K);
        inflate.findViewById(R.id.myfavorite).setOnClickListener(searchCallbackFragment2.N);
        inflate2.findViewById(R.id.mylocation).setOnClickListener(searchCallbackFragment2.J);
        inflate2.findViewById(R.id.mappoint).setOnClickListener(searchCallbackFragment2.K);
        inflate2.findViewById(R.id.myfavorite).setOnClickListener(searchCallbackFragment2.N);
        searchCallbackFragment2.u.addHeaderView(inflate);
        searchCallbackFragment2.w.addHeaderView(inflate2);
        searchCallbackFragment2.v = new SearchHistoryList(searchCallbackFragment2.getContext(), searchCallbackFragment2.u, SearchCallbackFragment.b, 0);
        searchCallbackFragment2.c = (SearchEdit) searchCallbackFragment2.getContentView().findViewById(R.id.search_search_layout);
        searchCallbackFragment2.d = (Button) searchCallbackFragment2.getContentView().findViewById(R.id.btn_search);
        searchCallbackFragment2.d.setOnClickListener(searchCallbackFragment2.L);
        searchCallbackFragment2.e = (ImageButton) searchCallbackFragment2.getContentView().findViewById(R.id.btn_search_back);
        searchCallbackFragment2.e.setOnClickListener(searchCallbackFragment2.H);
        SearchSuggestList searchSuggestList = new SearchSuggestList(searchCallbackFragment2.getContext(), searchCallbackFragment2.c, searchCallbackFragment2.w, SearchCallbackFragment.b, "", false);
        searchCallbackFragment2.x = searchSuggestList;
        searchCallbackFragment2.x.setPageContext(searchCallbackFragment2);
        searchCallbackFragment2.f = searchCallbackFragment2.c.getEditText();
        searchCallbackFragment2.u.setOnTouchListener(searchCallbackFragment2.G);
        searchCallbackFragment2.w.setOnTouchListener(searchCallbackFragment2.G);
        if (arguments != null) {
            searchCallbackFragment2.F = arguments.getBoolean("key_is_set_result");
            if (arguments.containsKey("route_type")) {
                Object object = arguments.getObject("route_type");
                if (object != null && (object instanceof RouteType)) {
                    searchCallbackFragment2.m = (RouteType) object;
                }
            }
            int i = arguments.getInt("search_for", 1);
            if (i == 1) {
                searchCallbackFragment2.k = SearchFor.DEFAULT_POI;
            } else if (i == 2) {
                searchCallbackFragment2.k = SearchFor.CUSTOM_ADDRESS;
            } else if (i == 3) {
                searchCallbackFragment2.k = SearchFor.QUICK_NAVI;
            }
            searchCallbackFragment2.p = (SelectFor) arguments.getObject("selectedfor");
            if (arguments.getBoolean("isHideMyPosition", false)) {
                inflate.findViewById(R.id.mylocation).setVisibility(8);
                inflate2.findViewById(R.id.mylocation).setVisibility(8);
            } else {
                inflate.findViewById(R.id.mylocation).setVisibility(0);
                inflate2.findViewById(R.id.mylocation).setVisibility(0);
            }
            if (!arguments.getBoolean(SearchCallbackFragment.a, true)) {
                inflate.findViewById(R.id.mappoint).setVisibility(8);
                inflate2.findViewById(R.id.mappoint).setVisibility(8);
            }
            searchCallbackFragment2.q = arguments.getString("hint");
            String string = searchCallbackFragment2.getString(R.string.drive_enter_text);
            if (searchCallbackFragment2.q == null || !searchCallbackFragment2.q.contains(string)) {
                searchCallbackFragment2.h = searchCallbackFragment2.q;
            } else {
                searchCallbackFragment2.h = searchCallbackFragment2.q.replace(string, searchCallbackFragment2.getString(R.string.confirm));
            }
            if (arguments.containsKey("SelectPoiFromMapBean")) {
                searchCallbackFragment2.o = (SelectPoiFromMapBean) arguments.getSerializable("SelectPoiFromMapBean");
            }
            searchCallbackFragment2.i = arguments.getString(TrafficUtil.KEYWORD);
            searchCallbackFragment2.r = arguments.getBoolean("isOffline", false);
            searchCallbackFragment2.v.showHistory();
            searchCallbackFragment2.j = arguments.getBoolean("auto_search");
            searchCallbackFragment2.A = (Callback) arguments.getObject("callback");
        }
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (mapView != null) {
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            searchCallbackFragment2.x.initPosSearch(glGeoPoint2GeoPoint, (long) glGeoPoint2GeoPoint.getAdCode(), 0, "poi|bus", SearchCallbackFragment.b);
        }
        searchCallbackFragment2.v.setOnItemEventListener(searchCallbackFragment2.c.onItemEventListener);
        searchCallbackFragment2.x.setOnItemEventListener(searchCallbackFragment2.c.onItemEventListener);
        searchCallbackFragment2.c.setSearchEditEventListener(new ISearchEditEventListener() {
            String a;

            public final void onItemLongClicked(TipItem tipItem) {
            }

            public final void onRoute(TipItem tipItem) {
            }

            public final void onItemClicked(TipItem tipItem) {
                SearchCallbackFragment searchCallbackFragment = SearchCallbackFragment.this;
                if (tipItem != null) {
                    searchCallbackFragment.s = tipItem;
                    if (SearchHistoryHelper.isUserfulPoi(tipItem)) {
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
                        searchCallbackFragment.a((POI) iSearchPoiData, searchCallbackFragment.z ? "planend_record" : "planend_sug");
                        SearchCallbackFragment.a((String) "B011");
                        return;
                    }
                    searchCallbackFragment.r = SearchUtils.isNaviOfflineSearch();
                    searchCallbackFragment.n = true;
                    searchCallbackFragment.l = tipItem.type;
                    searchCallbackFragment.a(tipItem);
                }
            }

            public final boolean afterTextChanged(Editable editable) {
                this.a = editable.toString();
                if (this.a.length() > 0) {
                    SearchCallbackFragment.this.d.setVisibility(0);
                    SearchCallbackFragment.this.d.setEnabled(true);
                } else if (this.a.length() == 0 && SearchCallbackFragment.this.d.getVisibility() == 0) {
                    SearchCallbackFragment.this.d.setVisibility(8);
                    SearchCallbackFragment.this.d.setEnabled(false);
                    SearchCallbackFragment.this.x.cancelSuggestNetWork();
                }
                SearchCallbackFragment.c(SearchCallbackFragment.this);
                return false;
            }

            public final void onClearEdit() {
                onHideSugg();
                SearchCallbackFragment.this.u.setVisibility(0);
            }

            public final void onShowHistory(int i) {
                SearchCallbackFragment.this.u.setVisibility(0);
                SearchCallbackFragment.this.v.showHistory();
                SearchCallbackFragment.this.x.cancelSuggestNetWork();
                SearchCallbackFragment.this.z = true;
            }

            public final void onHideHistory() {
                SearchCallbackFragment.this.v.cancelTask();
                SearchCallbackFragment.this.u.setVisibility(8);
            }

            public final void onShowSugg(int i) {
                if (!TextUtils.isEmpty(this.a)) {
                    SearchCallbackFragment.this.x.clearSuggData();
                    SearchCallbackFragment.this.w.setVisibility(0);
                    SearchCallbackFragment.this.x.showSuggest(this.a);
                    SearchCallbackFragment.this.z = false;
                }
            }

            public final void onHideSugg() {
                SearchCallbackFragment.this.w.setVisibility(8);
                SearchCallbackFragment.this.x.clearSuggData();
                SearchCallbackFragment.this.x.cancelSuggestNetWork();
            }
        });
        if (!TextUtils.isEmpty(searchCallbackFragment2.i) && (searchCallbackFragment2.i.equals("我的位置") || searchCallbackFragment2.i.equals("已选择的位置") || searchCallbackFragment2.i.equals(DriveUtil.MAP_PLACE_DES) || searchCallbackFragment2.i.equals("地图选定位置") || TextUtils.equals(searchCallbackFragment2.i, DriveUtil.MY_LOCATION_LOADING))) {
            searchCallbackFragment2.i = "";
        }
        if (!searchCallbackFragment2.j || TextUtils.isEmpty(searchCallbackFragment2.i)) {
            searchCallbackFragment2.f.setText("");
        } else {
            searchCallbackFragment2.f.setText(searchCallbackFragment2.i);
            searchCallbackFragment2.a((TipItem) null);
        }
        searchCallbackFragment2.f.setHint(searchCallbackFragment2.q);
        searchCallbackFragment2.B = LayoutInflater.from(searchCallbackFragment2.getContext()).inflate(R.layout.drive_search_callback_fragment_voice_window, null);
        searchCallbackFragment2.C = new dke(searchCallbackFragment2);
        searchCallbackFragment2.C.d = searchCallbackFragment2.P;
        searchCallbackFragment2.B.setOnClickListener(searchCallbackFragment2.I);
        searchCallbackFragment2.E = (RouteEditView) searchCallbackFragment2.getContentView().findViewById(R.id.search_header);
        if (searchCallbackFragment2.D != null) {
            if (searchCallbackFragment2.D.mStartPoi != null) {
                searchCallbackFragment2.E.setStartText(searchCallbackFragment2.D.mStartPoi.getName());
            } else {
                searchCallbackFragment2.E.setStartHint("输入起点");
            }
            if (searchCallbackFragment2.D.mEndPoi != null) {
                searchCallbackFragment2.E.setEndText(searchCallbackFragment2.D.mEndPoi.getName());
            } else {
                searchCallbackFragment2.E.setEndHint("输入终点");
            }
            String simpleName = SearchCallbackFragment.class.getSimpleName();
            StringBuilder sb = new StringBuilder("initHeaderView   mEditStatus:");
            sb.append(searchCallbackFragment2.D.mEditStatus);
            AMapLog.i(simpleName, sb.toString());
        }
        SearchCallbackFragment searchCallbackFragment3 = (SearchCallbackFragment) this.mPage;
        if (!searchCallbackFragment3.j || TextUtils.isEmpty(searchCallbackFragment3.i)) {
            if (!TextUtils.isEmpty(searchCallbackFragment3.i)) {
                if (searchCallbackFragment3.c != null) {
                    searchCallbackFragment3.c.setSelfCall(true);
                }
                searchCallbackFragment3.f.setText(searchCallbackFragment3.i);
                if (searchCallbackFragment3.i.equals(SearchConst.a) || searchCallbackFragment3.i.equals(SearchConst.b)) {
                    searchCallbackFragment3.f.selectAll();
                } else {
                    Editable text = searchCallbackFragment3.f.getText();
                    Selection.setSelection(text, text.length());
                }
            }
            if (!searchCallbackFragment3.y) {
                searchCallbackFragment3.y = true;
            }
            if (searchCallbackFragment3.c != null) {
                searchCallbackFragment3.c.requestEditFocus();
                searchCallbackFragment3.c.showInputMethod();
            }
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        SearchCallbackFragment searchCallbackFragment = (SearchCallbackFragment) this.mPage;
        if (searchCallbackFragment.v != null) {
            searchCallbackFragment.v.initNoHistoryTipText();
        }
    }

    public final void onStart() {
        SearchCallbackFragment searchCallbackFragment = (SearchCallbackFragment) this.mPage;
        SuperId.getInstance().reset();
        if (searchCallbackFragment.getArguments().containsKey("SUPER_ID")) {
            searchCallbackFragment.O = (String) searchCallbackFragment.getArguments().get("SUPER_ID");
            if (UploadQueueMgr.MSGTYPE_REALTIME.equals(searchCallbackFragment.O) && searchCallbackFragment.m != null) {
                if (searchCallbackFragment.m.equals(RouteType.BUS)) {
                    searchCallbackFragment.O = "d";
                } else if (searchCallbackFragment.m.equals(RouteType.CAR)) {
                    searchCallbackFragment.O = "f";
                } else if (searchCallbackFragment.m.equals(RouteType.ONFOOT)) {
                    searchCallbackFragment.O = "e";
                }
            }
            if (searchCallbackFragment.c != null) {
                searchCallbackFragment.c.setSuperIdBit1(searchCallbackFragment.O);
            }
            SuperId.getInstance().setBit1(searchCallbackFragment.O);
        }
        if (!searchCallbackFragment.j || TextUtils.isEmpty(searchCallbackFragment.i)) {
            if (searchCallbackFragment.c != null) {
                searchCallbackFragment.c.requestEditFocus();
                searchCallbackFragment.c.showInputMethod();
            }
            searchCallbackFragment.C.b();
        }
    }

    public final void onStop() {
        SearchCallbackFragment searchCallbackFragment = (SearchCallbackFragment) this.mPage;
        if (searchCallbackFragment.c != null) {
            searchCallbackFragment.c.hideInputMethod();
            searchCallbackFragment.c.dissmissIatDialog();
        }
        SearchCallbackFragment searchCallbackFragment2 = (SearchCallbackFragment) this.mPage;
        searchCallbackFragment2.v.cancelTask();
        searchCallbackFragment2.x.cancelTask();
        searchCallbackFragment2.C.a();
    }

    public final ON_BACK_TYPE onBackPressed() {
        ((SearchCallbackFragment) this.mPage).a();
        return super.onBackPressed();
    }

    public final void onDestroy() {
        SearchCallbackFragment searchCallbackFragment = (SearchCallbackFragment) this.mPage;
        if (searchCallbackFragment.c != null) {
            searchCallbackFragment.c.clearFocus();
            searchCallbackFragment.c.setSearchEditEventListener(null);
            searchCallbackFragment.c.onItemEventListener = null;
            searchCallbackFragment.c.onDestory();
            searchCallbackFragment.c = null;
        }
        if (searchCallbackFragment.t != null && !searchCallbackFragment.t.b()) {
            searchCallbackFragment.t.a();
            searchCallbackFragment.t = null;
        }
        if (searchCallbackFragment.x != null) {
            searchCallbackFragment.x.onDestroy();
            searchCallbackFragment.x.setOnItemEventListener(null);
            searchCallbackFragment.x = null;
        }
        if (searchCallbackFragment.v != null) {
            searchCallbackFragment.v.setOnItemEventListener(null);
            searchCallbackFragment.v = null;
        }
        if (searchCallbackFragment.e != null) {
            searchCallbackFragment.e.setOnClickListener(null);
        }
        if (searchCallbackFragment.u != null) {
            searchCallbackFragment.u.setOnTouchListener(null);
        }
        if (searchCallbackFragment.w != null) {
            searchCallbackFragment.w.setOnTouchListener(null);
        }
        if (searchCallbackFragment.G != null) {
            searchCallbackFragment.G = null;
        }
        if (searchCallbackFragment.g != null) {
            if (searchCallbackFragment.g.isShowing()) {
                searchCallbackFragment.g.dismiss();
            }
            searchCallbackFragment.g = null;
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        SearchCallbackFragment searchCallbackFragment = (SearchCallbackFragment) this.mPage;
        searchCallbackFragment.F = 11 == i;
        if (ResultType.OK.equals(resultType) && pageBundle != null) {
            if (pageBundle.containsKey("searchResult")) {
                POI poi = (POI) pageBundle.getObject("searchResult");
                searchCallbackFragment.a(poi);
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putObject("result_poi", poi);
                pageBundle2.putObject("selectedfor", searchCallbackFragment.p);
                searchCallbackFragment.setResult(ResultType.OK, pageBundle2);
                if (searchCallbackFragment.A != null) {
                    searchCallbackFragment.A.callback(poi);
                }
                searchCallbackFragment.finish();
            } else if (i == 1 && pageBundle.containsKey("SelectPoiFromMapFragment.MapClickResult")) {
                POI poi2 = (POI) pageBundle.getObject("SelectPoiFromMapFragment.MapClickResult");
                searchCallbackFragment.a(poi2);
                PageBundle pageBundle3 = new PageBundle();
                pageBundle3.putObject("result_poi", poi2);
                pageBundle3.putObject("SelectPoiFromMapFragment.MapClickResult", poi2);
                pageBundle3.putObject("selectedfor", searchCallbackFragment.p);
                searchCallbackFragment.setResult(ResultType.OK, pageBundle3);
                if (searchCallbackFragment.A != null) {
                    searchCallbackFragment.A.callback(poi2);
                }
                searchCallbackFragment.finish();
            } else if (i == 2) {
                if (pageBundle.containsKey("result_poi")) {
                    searchCallbackFragment.a((POI) pageBundle.getObject("result_poi"), (String) null);
                } else if (pageBundle.containsKey(TrafficUtil.KEYWORD)) {
                    searchCallbackFragment.f.setText(pageBundle.getString(TrafficUtil.KEYWORD));
                    searchCallbackFragment.n = false;
                    SuperId.getInstance().setBit3("09");
                    searchCallbackFragment.a((TipItem) null);
                } else if (pageBundle.containsKey("search_net") && pageBundle.getBoolean("search_net") && !TextUtils.isEmpty(searchCallbackFragment.i)) {
                    searchCallbackFragment.f.setText(searchCallbackFragment.i);
                    searchCallbackFragment.n = false;
                    searchCallbackFragment.a((TipItem) null);
                }
            }
        }
    }

    public final /* synthetic */ su a() {
        return new dio(this);
    }
}
