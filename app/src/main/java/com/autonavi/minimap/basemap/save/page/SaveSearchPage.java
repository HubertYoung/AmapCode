package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.MiningUserInfo;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.miniapp.plugin.constant.ConstantCompat.SaveSearchResultMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.search.model.SearchConst;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.minimap.search.view.SearchHistoryList;
import com.autonavi.minimap.search.view.SearchSuggestList;
import com.autonavi.minimap.widget.SearchEdit;
import com.autonavi.minimap.widget.SearchEdit.ISearchEditEventListener;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.basemap.action.save_search_page")
public class SaveSearchPage extends AbstractBasePage<crn> {
    private final OnClickListener A = new OnClickListener() {
        public final void onClick(View view) {
            SaveSearchPage.this.a.hideInputMethod();
            SaveSearchPage.this.a();
            SaveSearchPage.a(8);
        }
    };
    private final OnClickListener B = new OnClickListener() {
        public final void onClick(View view) {
            SaveSearchPage.this.d();
        }
    };
    private final OnClickListener C = new OnClickListener() {
        public final void onClick(View view) {
            SaveSearchPage.this.a.hideInputMethod();
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("FetchFor", SelectFor.SAVE_POI);
            if (SaveSearchPage.this.t != null) {
                pageBundle.putSerializable("SelectPoiFromMapBean", SaveSearchPage.this.t);
            }
            SaveSearchPage.this.startPageForResult((String) "amap.basemap.action.base_select_poi_from_map_page", pageBundle, 1);
        }
    };
    private final OnClickListener D = new OnClickListener() {
        public final void onClick(View view) {
            if (SaveSearchPage.this.k) {
                SaveSearchPage.this.h.setVisibility(8);
            }
            SuperId.getInstance().reset();
            if (!SaveSearchPage.this.f.equals("")) {
                SuperId.getInstance().setBit1(SaveSearchPage.this.f);
                SuperId.getInstance().setBit2("03");
            }
            SaveSearchPage.this.a((String) null);
        }
    };
    private OnTouchListener E = new OnTouchListener() {
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            if (SaveSearchPage.this.a != null) {
                SaveSearchPage.this.a.hideInputMethod();
            }
            return false;
        }
    };
    private OnClickListener F = new OnClickListener() {
        public final void onClick(View view) {
            POI createPOI = POIFactory.createPOI(SaveSearchPage.this.u.name, new GeoPoint(Double.parseDouble(SaveSearchPage.this.u.longitude), Double.parseDouble(SaveSearchPage.this.u.latitude)));
            if (SaveSearchPage.this.y.getText() != null && SaveSearchPage.this.y.getText().equals(SaveSearchPage.this.getString(R.string.go_home))) {
                auz auz = (auz) a.a.a(auz.class);
                if (auz != null) {
                    auz.a(createPOI);
                }
            }
            if (SaveSearchPage.this.y.getText() != null && SaveSearchPage.this.y.getText().equals(SaveSearchPage.this.getString(R.string.to_company))) {
                auz auz2 = (auz) a.a.a(auz.class);
                if (auz2 != null) {
                    auz2.b(createPOI);
                }
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("result_poi", createPOI);
            SaveSearchPage.this.setResult(ResultType.OK, pageBundle);
            if (SaveSearchPage.this.l != null) {
                SaveSearchPage.this.l.callback(createPOI);
            }
            SaveSearchPage.this.finish();
        }
    };
    public SearchEdit a;
    public EditText b;
    /* access modifiers changed from: 0000 */
    public String c;
    /* access modifiers changed from: 0000 */
    public int d = -1;
    public boolean e = false;
    public String f;
    public String g = "";
    public ListView h;
    public SearchHistoryList i;
    public SearchSuggestList j;
    /* access modifiers changed from: 0000 */
    public boolean k = false;
    public Callback<POI> l = null;
    private final int m = UCMPackageInfo.getRepairApolloRoot;
    /* access modifiers changed from: private */
    public Button n;
    private ImageButton o;
    /* access modifiers changed from: private */
    public ProgressDlg p;
    private String q = "";
    /* access modifiers changed from: private */
    public String r;
    /* access modifiers changed from: private */
    public ListView s;
    /* access modifiers changed from: private */
    public SelectPoiFromMapBean t = null;
    /* access modifiers changed from: private */
    public MiningUserInfo u;
    private View v;
    private TextView w;
    private TextView x;
    /* access modifiers changed from: private */
    public TextView y;
    private View z;

    class GeoReverseCallback implements Callback<String> {
        private final POI mPoi;

        public GeoReverseCallback(POI poi) {
            this.mPoi = poi;
        }

        @SuppressFBWarnings({"NP_LOAD_OF_KNOWN_NULL_VALUE"})
        public void callback(String str) {
            SaveSearchPage.this.b();
            if (!TextUtils.isEmpty(str)) {
                this.mPoi.setName(str);
                POI poi = this.mPoi;
                AMapAppGlobal.getApplication().getString(R.string.my_location);
                aud b = SaveSearchPage.a(poi);
                if (b != null) {
                    ArrayList<POI> arrayList = b.b.d;
                    if (arrayList == null || arrayList.size() <= 0) {
                        ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.save_search_no_mylocation));
                        return;
                    }
                    final POI poi2 = arrayList.get(0);
                    if (poi2 == null) {
                        SaveSearchPage.this.a(false, poi2);
                    } else if (cpf.b(cpm.b().a()).c(poi2)) {
                        a aVar = new a(SaveSearchPage.this.getContext());
                        aVar.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.dulicate_save_point_modify));
                        AnonymousClass1 r1 = new a() {
                            public final void onClick(AlertView alertView, int i) {
                                SaveSearchPage.this.dismissViewLayer(alertView);
                            }
                        };
                        aVar.b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.cancel), (a) r1);
                        aVar.c = r1;
                        aVar.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.modify_confirm), (a) new a() {
                            public final void onClick(AlertView alertView, int i) {
                                SaveSearchPage.this.dismissViewLayer(alertView);
                                SaveSearchPage.this.a(true, poi2);
                            }
                        });
                        aVar.c = new a() {
                            public final void onClick(AlertView alertView, int i) {
                            }
                        };
                        SaveSearchPage.this.showViewLayer(aVar.a());
                    } else {
                        SaveSearchPage.this.a(false, poi2);
                    }
                } else {
                    ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.save_search_no_mylocation));
                }
            }
        }

        public void error(Throwable th, boolean z) {
            SaveSearchPage.this.b();
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.save_search_fragment);
        View contentView = getContentView();
        this.a = (SearchEdit) contentView.findViewById(R.id.search_search_layout);
        this.n = (Button) contentView.findViewById(R.id.btn_search);
        this.n.setOnClickListener(this.D);
        this.o = (ImageButton) contentView.findViewById(R.id.btn_search_back);
        this.o.setOnClickListener(this.A);
        contentView.findViewById(R.id.mylocation).setOnClickListener(this.B);
        contentView.findViewById(R.id.mappoint).setOnClickListener(this.C);
        this.h = (ListView) contentView.findViewById(R.id.history_list_view);
        this.i = new SearchHistoryList(getContext(), this.h, UCMPackageInfo.getRepairApolloRoot, 0);
        this.z = LayoutInflater.from(context).inflate(R.layout.fragment_search_mining_list_header, this.h, false);
        this.v = (RelativeLayout) this.z.findViewById(R.id.mining_suggestion_data);
        this.w = (TextView) this.z.findViewById(R.id.mining_address_name);
        this.x = (TextView) this.z.findViewById(R.id.address_detail);
        this.y = (TextView) this.z.findViewById(R.id.set_address);
        this.y.setOnClickListener(this.F);
        this.s = (ListView) contentView.findViewById(R.id.search_sug_container);
        SearchSuggestList searchSuggestList = new SearchSuggestList(getContext(), this.a, this.s, UCMPackageInfo.getRepairApolloRoot, "", false);
        this.j = searchSuggestList;
        this.j.setPageContext(this);
        this.h.setOnTouchListener(this.E);
        this.s.setOnTouchListener(this.E);
        AMapPageUtil.setPageStateListener(this, new IPageStateListener() {
            public final void onCover() {
            }

            public final void onAppear() {
                SaveSearchPage.a(SaveSearchPage.this);
            }
        });
        this.b = this.a.getEditText();
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.q = arguments.getString("search_hint");
            this.c = arguments.getString(TrafficUtil.KEYWORD);
            if (arguments.containsKey("bundle_key_real_scene_get_poi")) {
                this.k = arguments.getBoolean("bundle_key_real_scene_get_poi");
            }
            if (arguments.containsKey("bundle_key_real_scene_map_center")) {
                this.t = (SelectPoiFromMapBean) arguments.getSerializable("bundle_key_real_scene_map_center");
            }
            this.r = arguments.getString("address");
            if (arguments.containsKey("address")) {
                String string = arguments.getString("address");
                if (!TextUtils.isEmpty(string)) {
                    if (string.equals(crt.b)) {
                        this.y.setText(getString(R.string.go_home));
                        if (brj.c().size() > 0) {
                            this.u = brj.c().get(0);
                        }
                    }
                    if (string.equals(crt.c)) {
                        this.y.setText(getString(R.string.to_company));
                        if (brj.c().size() > 1) {
                            this.u = brj.c().get(1);
                        }
                    }
                    if (this.u != null) {
                        if (!TextUtils.isEmpty(this.u.name)) {
                            this.w.setText(this.u.name);
                            this.w.setVisibility(0);
                        }
                        if (!TextUtils.isEmpty(this.u.address)) {
                            this.x.setText(this.u.address);
                            this.x.setVisibility(0);
                        }
                        if (!TextUtils.isEmpty(this.u.name) || !TextUtils.isEmpty(this.u.address)) {
                            this.h.addHeaderView(this.z);
                            this.v.setVisibility(0);
                        }
                    }
                }
            }
            if (arguments.containsKey("SUPER_ID")) {
                this.g = arguments.getString("SUPER_ID");
            }
            this.l = (Callback) arguments.getObject("callback");
        }
        if (this.k) {
            this.n.setVisibility(8);
        }
        GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(DoNotUseTool.getMapView().n());
        this.j.initPosSearch(glGeoPoint2GeoPoint, (long) glGeoPoint2GeoPoint.getAdCode(), 0, "poi|bus", UCMPackageInfo.getRepairApolloRoot);
        this.i.setOnItemEventListener(this.a.onItemEventListener);
        this.j.setOnItemEventListener(this.a.onItemEventListener);
        this.a.setSearchEditEventListener(new ISearchEditEventListener() {
            String a;

            public final void onItemLongClicked(TipItem tipItem) {
            }

            public final void onRoute(TipItem tipItem) {
            }

            public final void onItemClicked(TipItem tipItem) {
                SaveSearchPage saveSearchPage = SaveSearchPage.this;
                if (tipItem != null) {
                    saveSearchPage.c = tipItem.name;
                    if (!SearchHistoryHelper.isUserfulPoi(tipItem)) {
                        saveSearchPage.e = true;
                        saveSearchPage.d = tipItem.type;
                        if (!saveSearchPage.k) {
                            saveSearchPage.a(tipItem.adcode);
                        } else if (!tipItem.isFromRealSceneSearch || !(tipItem.x == 0.0d || tipItem.y == 0.0d)) {
                            PageBundle pageBundle = new PageBundle();
                            FavoritePOI favoritePOI = (FavoritePOI) POIFactory.createPOI(tipItem.name, new GeoPoint().setLonLat(tipItem.x, tipItem.y)).as(FavoritePOI.class);
                            favoritePOI.setId(tipItem.poiid);
                            favoritePOI.setFnona(tipItem.f_nona);
                            favoritePOI.setChildType(tipItem.childType);
                            favoritePOI.setParent(tipItem.parent);
                            favoritePOI.setTowardsAngle(tipItem.towardsAngle);
                            favoritePOI.setEndPoiExtension(tipItem.endPoiExtension);
                            favoritePOI.setTransparent(tipItem.transparent);
                            pageBundle.putObject("result_poi", favoritePOI);
                            saveSearchPage.setResult(ResultType.OK, pageBundle);
                            if (saveSearchPage.l != null) {
                                saveSearchPage.l.callback(favoritePOI);
                            }
                            saveSearchPage.finish();
                        } else {
                            if (saveSearchPage.a != null) {
                                String text = saveSearchPage.a.getText();
                                saveSearchPage.a.setText("");
                                saveSearchPage.a.setText(text);
                            }
                        }
                    } else {
                        FavoritePOI favoritePOI2 = (FavoritePOI) POIFactory.createPOI(tipItem.name, new GeoPoint().setLonLat(tipItem.x, tipItem.y)).as(FavoritePOI.class);
                        favoritePOI2.setId(tipItem.poiid);
                        favoritePOI2.setAdCode(tipItem.adcode);
                        StringBuilder sb = new StringBuilder();
                        sb.append(tipItem.district);
                        sb.append(tipItem.addr);
                        favoritePOI2.setAddr(sb.toString().trim());
                        favoritePOI2.setType(tipItem.newType);
                        favoritePOI2.setFnona(tipItem.f_nona);
                        favoritePOI2.setChildType(tipItem.childType);
                        favoritePOI2.setParent(tipItem.parent);
                        favoritePOI2.setTowardsAngle(tipItem.towardsAngle);
                        favoritePOI2.setEndPoiExtension(tipItem.endPoiExtension);
                        favoritePOI2.setTransparent(tipItem.transparent);
                        if (tipItem.x_entr > 0.0d && tipItem.y_entr > 0.0d) {
                            ArrayList arrayList = new ArrayList();
                            arrayList.add(new GeoPoint(tipItem.x_entr, tipItem.y_entr));
                            favoritePOI2.setEntranceList(arrayList);
                        }
                        tipItem.historyType = 0;
                        tipItem.time = new Date();
                        ahm.a(new Runnable(tipItem) {
                            final /* synthetic */ TipItem a;

                            {
                                this.a = r1;
                            }

                            public final void run() {
                                SearchHistoryHelper.getInstance(AMapAppGlobal.getApplication()).saveTipItem(this.a);
                            }
                        });
                        if (!saveSearchPage.k) {
                            favoritePOI2.getName();
                            saveSearchPage.a(SaveSearchPage.a((POI) favoritePOI2));
                            SaveSearchPage.a(6);
                        } else if (tipItem.x != 0.0d && tipItem.y != 0.0d) {
                            PageBundle pageBundle2 = new PageBundle();
                            pageBundle2.putObject("result_poi", favoritePOI2);
                            saveSearchPage.setResult(ResultType.OK, pageBundle2);
                            if (saveSearchPage.l != null) {
                                saveSearchPage.l.callback(favoritePOI2);
                            }
                            saveSearchPage.finish();
                        }
                    }
                }
            }

            public final void onClearEdit() {
                onHideSugg();
                SaveSearchPage.this.h.setVisibility(0);
            }

            public final void onShowHistory(int i) {
                if (SaveSearchPage.this.k) {
                    SaveSearchPage.this.h.setVisibility(8);
                } else if (SaveSearchPage.this.h.getVisibility() != 0) {
                    SaveSearchPage.this.h.setVisibility(0);
                    SaveSearchPage.this.i.showHistory();
                    SaveSearchPage.this.j.cancelSuggestNetWork();
                }
            }

            public final void onHideHistory() {
                SaveSearchPage.this.i.cancelTask();
                if (SaveSearchPage.this.h.getVisibility() != 8) {
                    SaveSearchPage.this.h.setVisibility(8);
                }
            }

            public final void onShowSugg(int i) {
                if (!TextUtils.isEmpty(this.a)) {
                    SaveSearchPage.this.j.clearSuggData();
                    SaveSearchPage.this.s.setVisibility(0);
                    SaveSearchPage.this.j.showSuggest(this.a);
                }
            }

            public final void onHideSugg() {
                SaveSearchPage.this.s.setVisibility(8);
                SaveSearchPage.this.j.clearSuggData();
                SaveSearchPage.this.j.cancelSuggestNetWork();
            }

            public final boolean afterTextChanged(Editable editable) {
                this.a = editable.toString();
                if (this.a.length() > 0) {
                    if (!SaveSearchPage.this.k) {
                        SaveSearchPage.this.n.setVisibility(0);
                    }
                } else if (this.a.length() == 0 && SaveSearchPage.this.n.getVisibility() == 0) {
                    SaveSearchPage.this.n.setVisibility(8);
                    SaveSearchPage.this.j.cancelSuggestNetWork();
                }
                return false;
            }
        });
        this.b.setText("");
        if (!TextUtils.isEmpty(this.q)) {
            this.b.setHint(this.q);
        } else if (this.k) {
            this.b.setHint(R.string.save_search_real_scene_position);
        } else {
            this.b.setHint(R.string.save_search_hint);
        }
        if (!TextUtils.isEmpty(this.c)) {
            this.a.setSelfCall(true);
            this.b.setText(this.c);
            Editable text = this.b.getText();
            if (text != null) {
                Selection.setSelection(text, text.length());
            }
        }
        if (this.a != null) {
            this.a.requestEditFocus();
            this.a.showInputMethod();
        }
        this.i.showHistory();
    }

    /* access modifiers changed from: 0000 */
    public final void a(final aud aud) {
        getActivity().runOnUiThread(new Runnable() {
            public final void run() {
                SaveSearchPage.this.a.hideInputMethod();
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("poi_search_result", aud);
                pageBundle.putString(TrafficUtil.KEYWORD, SaveSearchPage.this.c);
                pageBundle.putString("address", SaveSearchPage.this.r);
                SaveSearchPage.this.startPageForResult(SaveSearchResultMapPage.class, pageBundle, 2);
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0041 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r8) {
        /*
            r7 = this;
            r0 = 0
            r7.c = r0
            android.widget.EditText r0 = r7.b
            android.text.Editable r0 = r0.getText()
            java.lang.String r0 = r0.toString()
            android.widget.EditText r1 = r7.b
            java.lang.CharSequence r1 = r1.getHint()
            java.lang.String r1 = r1.toString()
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x002a
            int r2 = com.autonavi.minimap.R.string.act_search_error_empty
            java.lang.String r2 = r7.getString(r2)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r2)
        L_0x0028:
            r2 = 0
            goto L_0x003f
        L_0x002a:
            java.lang.String r2 = r0.trim()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 == 0) goto L_0x003e
            int r2 = com.autonavi.minimap.R.string.act_search_error_empty
            java.lang.String r2 = r7.getString(r2)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r2)
            goto L_0x0028
        L_0x003e:
            r2 = 1
        L_0x003f:
            if (r2 != 0) goto L_0x0042
            return
        L_0x0042:
            android.app.Application r2 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r5 = com.autonavi.minimap.R.string.my_location
            java.lang.String r2 = r2.getString(r5)
            boolean r2 = r2.equals(r0)
            if (r2 == 0) goto L_0x0056
            r7.d()
            return
        L_0x0056:
            com.autonavi.minimap.search.view.SearchSuggestList r2 = r7.j
            r2.cancelSuggestNetWork()
            r7.c = r0
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r5 = "Keyword"
            int r6 = r0.length()     // Catch:{ JSONException -> 0x0081 }
            if (r6 > 0) goto L_0x007c
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0081 }
            r6.<init>()     // Catch:{ JSONException -> 0x0081 }
            r6.append(r1)     // Catch:{ JSONException -> 0x0081 }
            java.lang.String r1 = "-热词"
            r6.append(r1)     // Catch:{ JSONException -> 0x0081 }
            java.lang.String r1 = r6.toString()     // Catch:{ JSONException -> 0x0081 }
            goto L_0x007d
        L_0x007c:
            r1 = r0
        L_0x007d:
            r2.put(r5, r1)     // Catch:{ JSONException -> 0x0081 }
            goto L_0x0085
        L_0x0081:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0085:
            com.autonavi.minimap.search.view.SearchSuggestList r1 = r7.j
            r1.cancelSuggestNetWork()
            com.autonavi.minimap.widget.SearchEdit r1 = r7.a
            r1.setVoiceSearch(r4)
            com.autonavi.map.core.MapManager r1 = com.autonavi.map.fragmentcontainer.page.DoNotUseTool.getMapManager()
            if (r1 != 0) goto L_0x0096
            return
        L_0x0096:
            bty r1 = r1.getMapView()
            if (r1 != 0) goto L_0x009d
            return
        L_0x009d:
            aen r2 = new aen
            android.graphics.Rect r1 = r1.H()
            r2.<init>(r0, r1)
            r2.e = r3
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 != 0) goto L_0x00b1
            r2.a(r8)
        L_0x00b1:
            boolean r8 = r7.e
            if (r8 == 0) goto L_0x00c7
            int r8 = r7.d
            if (r8 != 0) goto L_0x00be
            java.lang.String r8 = "532000"
            r2.c = r8
            goto L_0x00cb
        L_0x00be:
            int r8 = r7.d
            if (r8 != r3) goto L_0x00cb
            java.lang.String r8 = "531000"
            r2.c = r8
            goto L_0x00cb
        L_0x00c7:
            java.lang.String r8 = "533000"
            r2.c = r8
        L_0x00cb:
            r7.e = r4
            com.autonavi.common.SuperId r8 = com.autonavi.common.SuperId.getInstance()
            java.lang.String r8 = r8.getScenceId()
            r2.d = r8
            cqz r8 = new cqz
            android.content.Context r0 = r7.getContext()
            com.autonavi.minimap.basemap.save.page.SaveSearchPage$12 r1 = new com.autonavi.minimap.basemap.save.page.SaveSearchPage$12
            r1.<init>()
            r8.<init>(r0, r2, r1)
            java.lang.String r0 = r7.c
            r8.c = r0
            r8.a()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.basemap.save.page.SaveSearchPage.a(java.lang.String):void");
    }

    public final void a(boolean z2, POI poi) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("result_poi", poi);
        if (this.k) {
            pageBundle.putBoolean("bundle_key_is_get_location", true);
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("result_poi", bnx.b(poi));
            jSONObject.put("result_code", getRequestCode());
            pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        pageBundle.putBoolean(SaveSearchResultMapPage.HAS_DUPLICATE_POINT_KEY, z2);
        setResult(ResultType.OK, pageBundle);
        if (this.l != null) {
            this.l.callback(poi);
        }
        finish();
    }

    public final void a() {
        setResult(ResultType.OK, new PageBundle());
        finish();
    }

    /* access modifiers changed from: 0000 */
    public static aud a(POI poi) {
        aud aud = new aud();
        ArrayList<POI> arrayList = new ArrayList<>();
        arrayList.add(poi);
        aud.b = new auc();
        aud.b.d = arrayList;
        return aud;
    }

    public final void b() {
        if (this.p != null && this.p.isShowing()) {
            this.p.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        if (LocationInstrument.getInstance().getLatestPosition(5) == null) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.save_search_no_mylocation));
        } else if (!aaw.c(getActivity())) {
            ToastHelper.showToast(getString(R.string.net_error_message));
        } else {
            POI createPOI = POIFactory.createPOI(SearchConst.a, LocationInstrument.getInstance().getLatestPosition());
            if (this.k) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putBoolean("bundle_key_is_get_location", true);
                pageBundle.putObject("result_poi", createPOI);
                setResult(ResultType.OK, pageBundle);
                if (this.l != null) {
                    this.l.callback(createPOI);
                }
                finish();
                return;
            }
            final Callback.a aVar = ReverseGeocodeManager.get(new GeoReverseCallback(createPOI), createPOI.getPoint());
            String string = getString(R.string.hint_waiting);
            if (this.p == null) {
                this.p = new ProgressDlg(getActivity(), string, "");
            }
            this.p.setMessage(string);
            this.p.setCancelable(true);
            this.p.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface dialogInterface) {
                    if (aVar != null) {
                        aVar.cancel();
                    }
                }
            });
            this.p.getLoadingView().setOnCloseClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (aVar != null && SaveSearchPage.this.p != null) {
                        aVar.cancel();
                        SaveSearchPage.this.p.dismiss();
                    }
                }
            });
            this.p.show();
        }
        LogManager.actionLog(LogConstant.PAGE_ID_SEARCH_CALLBACK, 2);
    }

    /* access modifiers changed from: 0000 */
    public static void a(int i2) {
        LogManager.actionLog(LogConstant.PAGE_ID_SEARCH_CALLBACK, i2);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new crn(this);
    }

    static /* synthetic */ void a(SaveSearchPage saveSearchPage) {
        if (saveSearchPage.b != null) {
            Editable text = saveSearchPage.b.getText();
            if (text != null && text.length() > 0) {
                Selection.setSelection(text, text.length());
            }
        }
    }
}
