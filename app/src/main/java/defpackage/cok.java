package defpackage;

import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseIntArray;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.mobile.tinyappcustom.h5plugin.H5ContactPlugin;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3DialogPage;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.base.overlay.MapPointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.basemap.errorback.inter.impl.ErrorReportStarterImpl$1;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cok reason: default package */
/* compiled from: ErrorReportStarterImpl */
public class cok implements IErrorReportStarter {
    @SuppressFBWarnings({"MS_MUTABLE_COLLECTION_PKGPROTECT"})
    public static final List<String> a = new ErrorReportStarterImpl$1();
    public static boolean b = false;
    private static final SparseIntArray d = new SparseIntArray() {
        {
            put(0, R.string.feed_back_source_0);
            put(1, R.string.feed_back_source_1);
            put(2, R.string.feed_back_source_2);
            put(3, R.string.feed_back_source_3);
            put(4, R.string.feed_back_source_4);
            put(5, R.string.feed_back_source_5);
            put(6, R.string.feed_back_source_6);
            put(7, R.string.feed_back_source_7);
            put(8, R.string.feed_back_source_8);
            put(9, R.string.feed_back_source_9);
            put(10, R.string.feed_back_source_10);
            put(11, R.string.feed_back_source_11);
            put(12, R.string.feed_back_source_12);
            put(13, R.string.feed_back_source_13);
            put(14, R.string.feed_back_source_14);
            put(15, R.string.feed_back_source_15);
            put(16, R.string.feed_back_source_16);
            put(17, R.string.feed_back_source_17);
            put(18, R.string.feed_back_source_18);
            put(19, R.string.feed_back_source_19);
            put(20, R.string.feed_back_source_20);
            put(21, R.string.feed_back_source_21);
            put(22, R.string.feed_back_source_22);
            put(23, R.string.feed_back_source_23);
            put(25, R.string.feed_back_source_25);
            put(27, R.string.feed_back_source_27);
            put(28, R.string.feed_back_source_28);
            put(30, R.string.feed_back_source_30);
            put(31, R.string.feed_back_source_31);
            put(32, R.string.feed_back_source_32);
            put(33, R.string.feed_back_source_33);
            put(34, R.string.feedback_source_34);
            put(35, R.string.feedback_source_35);
            put(36, R.string.feed_back_source_36);
            put(37, R.string.feed_back_source_37);
        }
    };
    /* access modifiers changed from: private */
    public boolean c = false;

    public void startAddIndoorPoi(bid bid, POI poi) {
    }

    @Deprecated
    public void startAddPoiWhenLocation(bid bid, POI poi, PageBundle pageBundle) {
    }

    public void doFastReportError(String str) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            b(getNameBySourcePage(13));
            LocationInstrument.getInstance().getLocationLog(DoNotUseTool.getContext());
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("page_id", 4);
            pageBundle.putString("error_pic_path", str);
            pageBundle.putString("retype", cqi.a());
            pageBundle.putInt("sourcepage", 13);
            if (pageContext != null) {
                MapManager mapManager = DoNotUseTool.getMapManager();
                if (mapManager != null) {
                    bty mapView = mapManager.getMapView();
                    if (mapView != null) {
                        pageBundle.putInt("scaleaccuracy", mapView.w());
                    }
                }
            }
            pageContext.startPage((String) "com.basemap.action.feedback_entry_list", pageBundle);
        }
    }

    public void doReportError(MapManager mapManager, final coi coi) {
        final ProgressDlg progressDlg = new ProgressDlg(AMapAppGlobal.getTopActivity(), AMapAppGlobal.getApplication().getString(R.string.report_error_screenshoting));
        progressDlg.setTextMsgGravity(1);
        progressDlg.setCancelable(false);
        progressDlg.show();
        cfc.a().a(mapManager, (c) new c() {
            public final void a(String str) {
                progressDlg.dismiss();
                if (str == null) {
                    ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.screenshot_fail));
                } else {
                    coi.doReportError(str);
                }
            }
        });
    }

    public void startAddPOIFromXYSelectPoint(final POI poi) {
        final bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null && DoNotUseTool.getActivity() != null) {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager != null) {
                final ProgressDlg progressDlg = new ProgressDlg(DoNotUseTool.getActivity(), AMapAppGlobal.getApplication().getString(R.string.report_error_screenshoting));
                progressDlg.setTextMsgGravity(1);
                progressDlg.setCancelable(false);
                progressDlg.show();
                cfc.a().a(mapManager, (a) new a() {
                    public final void onFailure() {
                    }

                    public final void onPrepare() {
                    }

                    public final void onScreenShotFinish(String str) {
                        progressDlg.dismiss();
                        if (str == null) {
                            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.screenshot_fail));
                        } else {
                            cok.a(cok.this, pageContext, poi, str);
                        }
                    }
                });
            }
        }
    }

    public void startOfflineMapError(bid bid) {
        b(getNameBySourcePage(22));
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("sourcepage", 22);
        pageBundle.putString("error_title", AMapAppGlobal.getApplication().getString(R.string.error_offline_map));
        pageBundle.putString("detail_top", AMapAppGlobal.getApplication().getString(R.string.oper_open_question));
        col.a();
        bid.startPage(Ajx3Page.class, col.c(pageBundle, "offlineMap"));
    }

    public void startAddPoi(POI poi) {
        startAddPoi(poi, (JSONObject) null);
    }

    public void startAddPoi(POI poi, JSONObject jSONObject) {
        boolean z;
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
            boolean z2 = false;
            int i = (poiExtra == null || !poiExtra.containsKey("is_gpspoint")) ? false : ((Boolean) poiExtra.get("is_gpspoint")).booleanValue() ? 17 : 19;
            b(getNameBySourcePage(i));
            if (jSONObject != null) {
                z2 = true;
                z = jSONObject.optString("address", "").equals("main");
            } else {
                z = false;
            }
            if (z2) {
                a(pageContext, poi, 1, i, z);
            } else {
                b(pageContext, poi, i, null, null);
            }
        }
    }

    public void startAddPoi(bid bid, int i) {
        Object obj;
        b(getNameBySourcePage(i));
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("sourcePage", i);
            jSONObject.put("mapScreenShot", "");
            jSONObject.put(KEY_TYPE.PLUGINREPORTTYPE, -1);
            jSONObject.put(H5ContactPlugin.CONTACT, avd.a());
        } catch (Exception unused) {
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("type", 6);
            jSONObject2.put("subType", -1);
            jSONObject2.put("data", jSONObject);
            obj = jSONObject2.toString();
        } catch (JSONException unused2) {
            obj = null;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_basemap_feedback/src/newpoi_feedback/NewPoiFeedback.page.js");
        pageBundle.putObject("jsData", obj);
        col.a();
        PageBundle pageBundle2 = new PageBundle();
        pageBundle2.putInt("sourcepage", i);
        pageBundle2.putInt("page_id", 15);
        if (!(i == 34 || i == 9)) {
            pageBundle2.putInt("auto_screenshot", 1);
        }
        Object object = pageBundle.getObject("jsData");
        if (object != null) {
            pageBundle2.putObject("old_poi_param", object);
        }
        bid.startPage(Ajx3DialogPage.class, col.c(pageBundle2, "entrylist"));
    }

    public void startLocationError(final POI poi) {
        if (AMapPageUtil.getPageContext() != null && DoNotUseTool.getActivity() != null) {
            final MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager != null) {
                final ProgressDlg progressDlg = new ProgressDlg(DoNotUseTool.getActivity(), AMapAppGlobal.getApplication().getString(R.string.report_error_screenshoting));
                progressDlg.setTextMsgGravity(1);
                progressDlg.setCancelable(false);
                progressDlg.show();
                cfc.a().a(mapManager, (a) new a() {
                    public final void onPrepare() {
                    }

                    public final void onFailure() {
                        progressDlg.dismiss();
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.screenshot_fail));
                    }

                    public final void onScreenShotFinish(String str) {
                        progressDlg.dismiss();
                        if (str == null) {
                            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.screenshot_fail));
                            return;
                        }
                        LocationInstrument.getInstance().getLocationLog(DoNotUseTool.getContext());
                        cok.b(cok.this.getNameBySourcePage(17));
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putInt("page_id", 23);
                        pageBundle.putObject("points", poi);
                        pageBundle.putString("address", poi.getName());
                        pageBundle.putString("error_pic_path", str);
                        pageBundle.putInt("delete_screenshot_file", 1);
                        pageBundle.putString("retype", cqi.a());
                        pageBundle.putInt("sourcepage", 17);
                        JSONObject b2 = bnx.b(poi);
                        if (b2 == null) {
                            b2 = new JSONObject();
                        }
                        try {
                            b2.put("sourcePage", 17);
                            b2.put("mapScreenShot", str);
                            b2.put(KEY_TYPE.PLUGINREPORTTYPE, -1);
                            b2.put(H5ContactPlugin.CONTACT, avd.a());
                        } catch (Exception unused) {
                        }
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("type", 6);
                            jSONObject.put("subType", -1);
                            jSONObject.put("data", b2);
                        } catch (JSONException unused2) {
                        }
                        pageBundle.putObject("old_poi_param", jSONObject);
                        bty mapView = mapManager.getMapView();
                        if (mapView != null) {
                            pageBundle.putInt("scaleaccuracy", mapView.w());
                            Rect H = mapView.H();
                            pageBundle.putString("diagonal", String.format("%d,%d|%d,%d", new Object[]{Integer.valueOf(H.right), Integer.valueOf(H.top), Integer.valueOf(H.left), Integer.valueOf(H.bottom)}));
                        }
                        cok.this.startFeedback(pageBundle);
                    }
                });
            }
        }
    }

    public void startStationError(bid bid, POI poi, String str) {
        b(getNameBySourcePage(18));
        PageBundle pageBundle = new PageBundle();
        if (poi.getPoint() != null) {
            pageBundle.putString(AutoJsonUtils.JSON_ADCODE, String.valueOf(poi.getPoint().getAdCode()));
        }
        pageBundle.putObject("points", poi);
        pageBundle.putString("name", poi.getName());
        pageBundle.putString("address", poi.getAddr() == null ? "" : poi.getAddr());
        pageBundle.putString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId());
        pageBundle.putString("tel", poi.getPhone());
        pageBundle.putBoolean("bundle_key_boolean_default", false);
        pageBundle.putString("lines", str);
        pageBundle.putInt("sourcepage", 18);
        pageBundle.putInt("page_id", 11);
        startFeedback(pageBundle);
    }

    public void startStationError(bid bid, POI poi) {
        startStationError(bid, poi, "");
    }

    public void startPOIError(bid bid, POI poi, JSONObject jSONObject) {
        if (poi.getType() == null || !a.contains(poi.getType())) {
            b(getNameBySourcePage(0));
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("points", poi);
            pageBundle.putString("name", poi.getName());
            pageBundle.putString("tel", poi.getPhone());
            pageBundle.putString("address", poi.getAddr());
            pageBundle.putObject("poi_json", jSONObject);
            if (jSONObject != null && jSONObject.has("shape")) {
                pageBundle.putBoolean("boundary", true);
            }
            bid.startPage((String) "amap.basemap.action.feedback_poi_detail_normal", pageBundle);
            return;
        }
        a(bid, poi);
    }

    public void startPOIError(bid bid, POI poi) {
        startPOIError(bid, poi, null);
    }

    private void a(bid bid, POI poi) {
        b(getNameBySourcePage(12));
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("points", poi);
        pageBundle.putString("name", poi.getName());
        pageBundle.putString("address", poi.getAddr() == null ? "" : poi.getAddr());
        pageBundle.putString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId());
        pageBundle.putString("tel", poi.getPhone());
        pageBundle.putBoolean("bundle_key_boolean_default", false);
        pageBundle.putInt("page_id", 13);
        pageBundle.putInt("sourcepage", 12);
        col.a();
        bid.startPage(Ajx3DialogPage.class, col.c(pageBundle, "entrylist"));
    }

    @Deprecated
    public void startIndoorError(bid bid, POI poi) {
        b(getNameBySourcePage(2));
    }

    public void startAddPoiFromSearch(bid bid, String str) {
        b(getNameBySourcePage(20));
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("sourcepage", 20);
        pageBundle.putBoolean("nl", true);
        pageBundle.putBoolean("filled", true);
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("name", str);
        }
        pageBundle.putInt("page_id", 22);
        bid.startPageForResult((String) "com.basemap.action.feedback_entry_list", pageBundle, 16400);
    }

    @Deprecated
    public void startAddPoiFeedback(bid bid, POI poi) {
        throw new UnsupportedOperationException("Deprecated method.");
    }

    @Deprecated
    public void startAddPoiFastReport(bid bid, POI poi) {
        throw new UnsupportedOperationException("Deprecated method.");
    }

    public void startAddPoiFromSearch(POI poi) {
        b(getNameBySourcePage(20));
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("sourcepage", 20);
        if (poi != null) {
            pageBundle.putString("name", poi.getName());
        }
        pageBundle.putString(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, AMapAppGlobal.getApplication().getString(R.string.feedback_add_poi_building_facility));
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            col.a();
            pageContext.startPageForResult(Ajx3DialogPage.class, col.c(pageBundle, "entrylist"), 16400);
        }
    }

    public void startFeedback(bid bid) {
        b(getNameBySourcePage(9));
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("error_type", 19);
        bid.startPageForResult((String) "amap.basemap.action.help_and_feedback_page", pageBundle, 2);
    }

    public void startFeedbackReport() {
        a(-1);
    }

    public final void a(int i) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            b(getNameBySourcePage(9));
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("retype", cqi.a());
            LocationInstrument.getInstance().getLocationLog(DoNotUseTool.getContext());
            if (pageContext != null) {
                MapManager mapManager = DoNotUseTool.getMapManager();
                if (mapManager != null) {
                    pageBundle.putInt("scaleaccuracy", mapManager.getMapView().w());
                }
            }
            if (i < 0) {
                i = 9;
            }
            pageBundle.putInt("sourcepage", i);
            pageBundle.putInt("page_id", 19);
            col.a();
            pageContext.startPage(Ajx3DialogPage.class, col.c(pageBundle, "entrylist"));
        }
    }

    /* access modifiers changed from: private */
    public static void b(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_FEEDBACK_ENTRANCE, "B001", jSONObject);
    }

    @Deprecated
    public void startNormalFeedbackPage(bid bid, String str) {
        throw new UnsupportedOperationException("Deprecated method.");
    }

    public void startFeedback(PageBundle pageBundle) {
        cok cok;
        final PageBundle pageBundle2 = pageBundle;
        if (pageBundle2 == null) {
            PrintStream printStream = System.err;
            return;
        }
        final int b2 = col.b(pageBundle2, (String) "sourcepage");
        if (b2 != -1) {
            cok = this;
            b(cok.getNameBySourcePage(b2));
        } else {
            cok = this;
        }
        final bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            String string = pageBundle2.getString("page_action");
            if (TextUtils.isEmpty(string)) {
                string = "com.basemap.action.feedback_entry_list";
            }
            int a2 = col.a(pageBundle2, (String) "page_id");
            col.a();
            if (b2 == 7 || b2 == 56) {
                String string2 = pageBundle2.getString(AutoJsonUtils.JSON_ADCODE, "");
                String string3 = pageBundle2.getString("lineid", "");
                String string4 = pageBundle2.getString("linename", "");
                String string5 = pageBundle2.getString("stationid", "");
                String string6 = pageBundle2.getString("stationname", "");
                int i = pageBundle2.getInt("stationX", 0);
                int i2 = pageBundle2.getInt("stationY", 0);
                boolean z = pageBundle2.getBoolean("isRealTime", false);
                Object object = pageBundle2.getObject("bus_all_stations");
                MapManager mapManager = DoNotUseTool.getMapManager();
                if (mapManager != null) {
                    ProgressDlg progressDlg = new ProgressDlg(DoNotUseTool.getActivity(), AMapAppGlobal.getApplication().getString(R.string.report_error_screenshoting));
                    progressDlg.setTextMsgGravity(1);
                    progressDlg.setCancelable(false);
                    progressDlg.show();
                    ProgressDlg progressDlg2 = progressDlg;
                    cok cok2 = cok;
                    MapManager mapManager2 = mapManager;
                    final ProgressDlg progressDlg3 = progressDlg2;
                    final String str = string2;
                    final String str2 = string3;
                    final String str3 = string4;
                    AnonymousClass5 r8 = r0;
                    final String str4 = string5;
                    AnonymousClass5 r9 = r8;
                    final String str5 = string6;
                    AnonymousClass5 r10 = r9;
                    final int i3 = i;
                    AnonymousClass5 r11 = r10;
                    final int i4 = i2;
                    AnonymousClass5 r12 = r11;
                    final boolean z2 = z;
                    MapManager mapManager3 = mapManager2;
                    AnonymousClass5 r15 = r12;
                    final Object obj = object;
                    AnonymousClass5 r0 = new c() {
                        final /* synthetic */ boolean l = true;

                        public final void a(String str) {
                            progressDlg3.dismiss();
                            PageBundle a2 = avd.a(b2, str, str, str2, str3, str4, str5, i3, i4, z2, obj);
                            if (!this.l) {
                                pageContext.startPage(Ajx3DialogPage.class, a2);
                                return;
                            }
                            Object object = a2.getObject("jsData");
                            if (object != null) {
                                pageBundle2.putObject("old_bus_line_param", object);
                            }
                            pageBundle2.putObject("bus_line_param", avd.a(str2, str3, str4, str5, i3, i4, z2, obj));
                            pageContext.startPage(Ajx3DialogPage.class, col.c(pageBundle2, "entrylist"));
                        }
                    };
                    cfc.a().a(mapManager3, (c) r15);
                }
            } else if (a2 != 14 || b) {
                if (b2 == 3 || b2 == 14) {
                    Object object2 = ave.a(pageBundle).getObject("jsData");
                    if (object2 != null) {
                        pageBundle2.putObject("old_bus_plan_param", object2);
                    }
                    pageBundle2.putInt("delete_screenshot_file", 1);
                    pageContext.startPage(Ajx3DialogPage.class, col.c(pageBundle2, "entrylist"));
                } else if (TextUtils.isEmpty(string) || string.equals("com.basemap.action.feedback_entry_list") || !TextUtils.isEmpty(col.a(a2))) {
                    if (b2 == 30 || b2 == 16 || b2 == 35) {
                        pageBundle2.putInt("delete_screenshot_file", 1);
                    }
                    pageContext.startPage(Ajx3DialogPage.class, col.c(pageBundle2, "entrylist"));
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        pageContext.startPage(string, pageBundle2);
                    }
                }
            } else if (b) {
                pageContext.startPage(Ajx3DialogPage.class, col.c(pageBundle2, "entrylist"));
            } else {
                POI poi = (POI) pageBundle2.getSerializable("points");
                if (poi != null) {
                    pageContext.startPage(Ajx3DialogPage.class, avd.a(poi, pageBundle2.getString("error_pic_path"), 0));
                }
            }
        }
    }

    public void startVoiceSearch() {
        bid pageContext = AMapPageUtil.getPageContext();
        b(getNameBySourcePage(28));
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("sourcepage", 28);
        if (pageContext != null) {
            pageContext.startPage((String) "amap.basemap.action.feedback_voice_search", pageBundle);
        }
    }

    public void startPoiDetailFeedback(bid bid, POI poi, int i, JSONObject jSONObject) {
        if (poi.getType() != null && a.contains(poi.getType())) {
            a(bid, poi);
        } else if (i == 0) {
            b(getNameBySourcePage(i));
            if (jSONObject != null) {
                col.a();
                a(bid, poi, 2, i, false);
                return;
            }
            PageBundle pageBundle = new PageBundle();
            pageBundle.putInt("sourcepage", i);
            pageBundle.putObject("points", poi);
            pageBundle.putString("name", poi.getName());
            pageBundle.putString("tel", poi.getPhone());
            pageBundle.putString("address", poi.getAddr());
            pageBundle.putObject("poi_json", jSONObject);
            if (jSONObject != null && jSONObject.has("shape")) {
                pageBundle.putBoolean("boundary", true);
            }
            new StringBuilder("~~~~~~~~~~~~~~~~~~~~~~~~~~~~feedback_middle_page, start time = ").append(System.currentTimeMillis());
            bid.startPage((String) "amap.basemap.action.feedback_poi_detail_normal", pageBundle);
        } else if (i == 33 || i == 37) {
            b(getNameBySourcePage(i));
            PageBundle pageBundle2 = new PageBundle();
            pageBundle2.putInt("sourcepage", i);
            pageBundle2.putObject("points", poi);
            pageBundle2.putString("name", poi.getName());
            pageBundle2.putString("tel", poi.getPhone());
            pageBundle2.putString("address", poi.getAddr());
            col.a();
            bid.startPage(Ajx3DialogPage.class, col.c(pageBundle2, "entrylist"));
        } else {
            if (i == 32 || i == 36) {
                b(getNameBySourcePage(i));
                PageBundle pageBundle3 = new PageBundle();
                pageBundle3.putInt("sourcepage", i);
                pageBundle3.putObject("points", poi);
                pageBundle3.putString("name", poi.getName());
                pageBundle3.putString("tel", poi.getPhone());
                pageBundle3.putString("address", poi.getAddr());
                col.a();
                bid.startPage(Ajx3DialogPage.class, col.c(pageBundle3, "entrylist"));
            }
        }
    }

    private void a(bid bid, POI poi, int i, int i2, boolean z) {
        if (!this.c && c(i)) {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager != null && poi != null) {
                GeoPoint point = poi.getPoint();
                if (point != null) {
                    this.c = true;
                    DimensionUtils.standardUnitToPixel((float) (((int) DimensionUtils.pixelToStandardUnit((float) ags.a(bid.getContext()).width())) / 3));
                    bty mapView = mapManager.getMapView();
                    GeoPoint a2 = a(mapManager);
                    if (a2 != null) {
                        point = a2;
                    }
                    mapView.a(point.x, point.y);
                    final ProgressDlg progressDlg = new ProgressDlg(DoNotUseTool.getActivity(), AMapAppGlobal.getApplication().getString(R.string.report_error_screenshoting));
                    progressDlg.setTextMsgGravity(1);
                    progressDlg.setCancelable(false);
                    progressDlg.show();
                    cfc a3 = cfc.a();
                    final bid bid2 = bid;
                    final POI poi2 = poi;
                    final int i3 = i;
                    final int i4 = i2;
                    final boolean z2 = z;
                    final MapManager mapManager2 = mapManager;
                    AnonymousClass6 r0 = new a() {
                        public final void onPrepare() {
                        }

                        public final void onFailure() {
                            cok.this.c = false;
                            progressDlg.dismiss();
                            if (bid2.isAlive()) {
                                PageBundle a2 = avd.a(poi2, "", i3);
                                if (i3 == 1) {
                                    cok.b(bid2, poi2, i4, a2, null);
                                    return;
                                }
                                new StringBuilder("~~~~~~~~~~~~~~~~~~~~~~~~~~~~feedback_middle_page, start time = ").append(System.currentTimeMillis());
                                StringBuilder sb = new StringBuilder("feedback_middle_page, start time = ");
                                sb.append(System.currentTimeMillis());
                                cjy.a(sb.toString());
                                if (cok.c(i3)) {
                                    bid2.startPage(Ajx3DialogPage.class, a2);
                                }
                            }
                        }

                        public final void onScreenShotFinish(String str) {
                            cok.this.c = false;
                            if (!bid2.isAlive()) {
                                progressDlg.dismiss();
                                return;
                            }
                            final PageBundle a2 = avd.a(poi2, str, i3);
                            if (i3 != 1) {
                                progressDlg.dismiss();
                                new StringBuilder("~~~~~~~~~~~~~~~~~~~~~~~~~~~~feedback_middle_page, start time = ").append(System.currentTimeMillis());
                                StringBuilder sb = new StringBuilder("feedback_middle_page, start time = ");
                                sb.append(System.currentTimeMillis());
                                cjy.a(sb.toString());
                                if (cok.c(i3)) {
                                    bid2.startPage(Ajx3DialogPage.class, a2);
                                }
                            } else if (z2) {
                                cfc.a().a(mapManager2, (a) new a() {
                                    public final void onPrepare() {
                                    }

                                    public final void onFailure() {
                                        cok.this.c = false;
                                        progressDlg.dismiss();
                                        if (bid2.isAlive()) {
                                            PageBundle a2 = avd.a(poi2, "", i3);
                                            if (i3 == 1) {
                                                cok.b(bid2, poi2, i4, a2, null);
                                                return;
                                            }
                                            new StringBuilder("~~~~~~~~~~~~~~~~~~~~~~~~~~~~feedback_middle_page, start time = ").append(System.currentTimeMillis());
                                            StringBuilder sb = new StringBuilder("feedback_middle_page, start time = ");
                                            sb.append(System.currentTimeMillis());
                                            cjy.a(sb.toString());
                                            if (cok.c(i3)) {
                                                bid2.startPage(Ajx3DialogPage.class, a2);
                                            }
                                        }
                                    }

                                    public final void onScreenShotFinish(String str) {
                                        cok.this.c = false;
                                        progressDlg.dismiss();
                                        if (str == null) {
                                            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.screenshot_fail));
                                        } else {
                                            cok.b(bid2, poi2, i4, a2, str);
                                        }
                                    }
                                });
                            } else {
                                progressDlg.dismiss();
                                cok.b(bid2, poi2, i4, a2, str);
                            }
                        }
                    };
                    a3.a(mapManager, (a) r0);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(bid bid, POI poi, int i, @Nullable PageBundle pageBundle, String str) {
        Object obj = null;
        JSONObject b2 = poi != null ? bnx.b(poi) : null;
        if (b2 == null) {
            b2 = new JSONObject();
        }
        try {
            b2.put("sourcePage", i);
            b2.put("mapScreenShot", str);
            b2.put(KEY_TYPE.PLUGINREPORTTYPE, -1);
            b2.put(H5ContactPlugin.CONTACT, avd.a());
        } catch (Exception unused) {
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", 6);
            jSONObject.put("subType", 1);
            jSONObject.put("data", b2);
            obj = jSONObject.toString();
        } catch (JSONException unused2) {
        }
        PageBundle pageBundle2 = new PageBundle();
        if (pageBundle != null) {
            pageBundle2.putObject("key_new_feedback_ajx_bundle", pageBundle);
        }
        pageBundle2.putString("url", "path://amap_bundle_basemap_feedback/src/newpoi_feedback/NewPoiFeedback.page.js");
        if (poi != null) {
            pageBundle2.putObject("points", poi);
        }
        pageBundle2.putObject("jsData", obj);
        col.a();
        PageBundle pageBundle3 = new PageBundle();
        pageBundle3.putInt("sourcepage", i);
        pageBundle3.putString("error_pic_path", str);
        pageBundle3.putInt("delete_screenshot_file", 1);
        if (poi != null) {
            pageBundle3.putObject("points", poi);
        }
        Object object = pageBundle2.getObject("jsData");
        if (object != null) {
            pageBundle3.putObject("old_poi_param", object);
        }
        bid.startPage(Ajx3DialogPage.class, col.c(pageBundle3, "entrylist"));
    }

    /* access modifiers changed from: private */
    public static boolean c(int i) {
        if (i == 1) {
            return true;
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if ((pageContext instanceof Ajx3DialogPage) && TextUtils.equals(((Ajx3DialogPage) pageContext).getAjx3Url(), "path://amap_bundle_feedback/src/poi/entry/MainPage.page.js")) {
            return false;
        }
        return true;
    }

    @Nullable
    private static GeoPoint a(MapManager mapManager) {
        PointOverlayItem pointOverlayItem;
        MapPointOverlay mapPointOverlay = mapManager.getOverlayManager().getMapPointOverlay();
        if (mapPointOverlay != null) {
            int size = mapPointOverlay.getSize();
            pointOverlayItem = null;
            for (int i = 0; i < size; i++) {
                pointOverlayItem = (PointOverlayItem) mapPointOverlay.getItem(i);
                if (pointOverlayItem != null) {
                    break;
                }
            }
        } else {
            pointOverlayItem = null;
        }
        if (pointOverlayItem == null) {
            brn brn = (brn) ank.a(brn.class);
            if (brn != null) {
                FavoriteLayer g = brn.g();
                if (g != null) {
                    pointOverlayItem = g.getFocusItem();
                }
            }
        }
        if (pointOverlayItem != null) {
            return pointOverlayItem.getGeoPoint();
        }
        return null;
    }

    public String getNameBySourcePage(int i) {
        Integer valueOf = Integer.valueOf(d.get(i));
        if (valueOf == null || valueOf.intValue() <= 0) {
            return "";
        }
        return AMapAppGlobal.getApplication().getString(valueOf.intValue());
    }

    static /* synthetic */ void a(cok cok, bid bid, POI poi, String str) {
        HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
        int i = (poiExtra == null || !poiExtra.containsKey("is_gpspoint")) ? false : ((Boolean) poiExtra.get("is_gpspoint")).booleanValue() ? 17 : 19;
        b(cok.getNameBySourcePage(i));
        col.a();
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("sourcepage", i);
        pageBundle.putString("error_pic_path", str);
        pageBundle.putObject("points", poi);
        bid.startPage(Ajx3DialogPage.class, col.c(pageBundle, "entrylist"));
    }
}
