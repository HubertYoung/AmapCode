package defpackage;

import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.mine.feedback.network.FeedbackNetworkManager$1;
import com.autonavi.mine.feedback.network.FeedbackNetworkManager$2;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3DialogPage;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.feedback.FeedbackRequestHolder;
import com.autonavi.minimap.feedback.param.ReportSatisfactionRequest;
import com.autonavi.minimap.feedback.param.ReportUrgeRequest;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.tencent.open.SocialConstants;
import java.net.URLDecoder;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"feedback"})
/* renamed from: avb reason: default package */
/* compiled from: FeedbackRouter */
public class avb extends esk {
    private static final CharSequence a = "trackmyfeedback";

    private boolean a(Uri uri) {
        String host = uri.getHost();
        List<String> pathSegments = uri.getPathSegments();
        if (!TextUtils.equals(host, "feedback") || pathSegments == null || pathSegments.isEmpty()) {
            return false;
        }
        String queryParameter = uri.getQueryParameter("feedbackParam");
        if (!TextUtils.isEmpty(queryParameter)) {
            queryParameter = URLDecoder.decode(queryParameter);
        }
        String str = pathSegments.get(0);
        if (TextUtils.equals(str, "newPoi")) {
            col.a();
            if (TextUtils.isEmpty(queryParameter)) {
                int a2 = agt.a(uri, "sourcepage", -1);
                IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                if (iErrorReportStarter != null) {
                    col.a();
                    iErrorReportStarter.startAddPoi(AMapPageUtil.getPageContext(), a2);
                }
            } else if (!TextUtils.isEmpty(queryParameter)) {
                a(str, queryParameter);
            }
            return true;
        } else if (TextUtils.equals(str, "twiceReport")) {
            String queryParameter2 = uri.getQueryParameter("record_id");
            String queryParameter3 = uri.getQueryParameter("type");
            if (!TextUtils.isEmpty(queryParameter2) && !TextUtils.isEmpty(queryParameter3)) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putObject("twice_report_param", col.b(queryParameter2, queryParameter3));
                a(pageBundle, (String) "twiceReport");
            }
            return true;
        } else if (TextUtils.equals(str, "suggestions")) {
            int a3 = agt.a(uri, "sourcepage", -1);
            col.a();
            if (TextUtils.isEmpty(queryParameter)) {
                IErrorReportStarter iErrorReportStarter2 = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                if (iErrorReportStarter2 != null && (iErrorReportStarter2 instanceof cok)) {
                    ((cok) iErrorReportStarter2).a(a3);
                }
            } else {
                a(str, queryParameter);
            }
            return true;
        } else {
            if (TextUtils.equals(str, "twiceReportSatisify")) {
                String queryParameter4 = uri.getQueryParameter("record_id");
                if (!TextUtils.isEmpty(queryParameter4)) {
                    if (!TextUtils.isEmpty(queryParameter4)) {
                        ReportSatisfactionRequest reportSatisfactionRequest = new ReportSatisfactionRequest();
                        reportSatisfactionRequest.b = queryParameter4;
                        FeedbackRequestHolder.getInstance().sendReportSatisfaction(reportSatisfactionRequest, new FeedbackNetworkManager$1(queryParameter4));
                    }
                    return true;
                }
            } else if (TextUtils.equals(str, "recordUrge")) {
                String queryParameter5 = uri.getQueryParameter("record_id");
                if (!TextUtils.isEmpty(queryParameter5)) {
                    if (!TextUtils.isEmpty(queryParameter5)) {
                        ReportUrgeRequest reportUrgeRequest = new ReportUrgeRequest();
                        reportUrgeRequest.b = queryParameter5;
                        FeedbackRequestHolder.getInstance().sendReportUrge(reportUrgeRequest, new FeedbackNetworkManager$2(queryParameter5));
                    }
                    return true;
                }
            } else if (TextUtils.equals(str, "addPoint")) {
                int a4 = agt.a(uri, "sourcepage", -1);
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    PageBundle arguments = pageContext.getArguments();
                    if (arguments == null) {
                        arguments = new PageBundle();
                    }
                    if (a4 < 0) {
                        a4 = 23;
                    }
                    arguments.putInt("sourcepage", a4);
                    a((String) "2001");
                    a(arguments, str, queryParameter);
                }
                return true;
            } else if (TextUtils.equals(str, "addRoad")) {
                int a5 = agt.a(uri, "sourcepage", -1);
                bid pageContext2 = AMapPageUtil.getPageContext();
                if (pageContext2 != null) {
                    PageBundle arguments2 = pageContext2.getArguments();
                    if (arguments2 == null) {
                        arguments2 = new PageBundle();
                    }
                    if (a5 < 0) {
                        a5 = 23;
                    }
                    arguments2.putInt("sourcepage", a5);
                    a((String) "5001");
                    a(arguments2, str, queryParameter);
                }
                return true;
            } else if (TextUtils.equals(str, "addStation")) {
                int a6 = agt.a(uri, "sourcepage", -1);
                bid pageContext3 = AMapPageUtil.getPageContext();
                if (pageContext3 != null) {
                    PageBundle arguments3 = pageContext3.getArguments();
                    if (arguments3 == null) {
                        arguments3 = new PageBundle();
                    }
                    if (a6 < 0) {
                        a6 = 23;
                    }
                    arguments3.putInt("sourcepage", a6);
                    a((String) "3001");
                    a(arguments3, str, queryParameter);
                }
                return true;
            } else if (TextUtils.equals(str, "addLine")) {
                int a7 = agt.a(uri, "sourcepage", -1);
                bid pageContext4 = AMapPageUtil.getPageContext();
                if (pageContext4 != null) {
                    PageBundle arguments4 = pageContext4.getArguments();
                    if (arguments4 == null) {
                        arguments4 = new PageBundle();
                    }
                    if (a7 < 0) {
                        a7 = 23;
                    }
                    arguments4.putInt("sourcepage", a7);
                    a((String) "3001");
                    a(arguments4, str, queryParameter);
                }
                return true;
            } else if (TextUtils.equals(str, a)) {
                startPage((String) "amap.basemap.action.help_and_feedback_page", new PageBundle());
                return true;
            } else if (TextUtils.equals(str, "reporterror")) {
                int a8 = agt.a(uri, "sourcepage", 0);
                PageBundle pageBundle2 = new PageBundle();
                pageBundle2.putInt("sourcepage", a8);
                startPage((String) "amap.basemap.action.contribution_search_page", pageBundle2);
                return true;
            } else if (TextUtils.equals(str, "checkpoi")) {
                int a9 = agt.a(uri, "sourcepage", 0);
                PageBundle pageBundle3 = new PageBundle();
                pageBundle3.putInt("sourcepage", a9);
                startPage((String) "amap.basemap.action.contribution_search_map_page", pageBundle3);
                return true;
            } else if (TextUtils.equals(str, "drivingreport")) {
                int a10 = agt.a(uri, "sourcepage", 0);
                PageBundle pageBundle4 = new PageBundle();
                pageBundle4.putInt("sourcepage", a10);
                startPage((String) "amap.basemap.action.drive_navigation_issue", pageBundle4);
                return true;
            } else if (TextUtils.equals(str, "usingreport")) {
                int a11 = agt.a(uri, "sourcepage", 0);
                PageBundle pageBundle5 = new PageBundle();
                pageBundle5.putInt("sourcepage", a11);
                a(pageBundle5, str, queryParameter);
                return true;
            } else if (TextUtils.equals(str, "voice")) {
                PageBundle pageBundle6 = new PageBundle();
                pageBundle6.putInt("sourcepage", agt.a(uri, "sourcepage", 0));
                a(pageBundle6, str, queryParameter);
                return true;
            } else if (TextUtils.equals(str, "busLineError")) {
                bid pageContext5 = AMapPageUtil.getPageContext();
                if (pageContext5 == null) {
                    return false;
                }
                a(pageContext5.getArguments(), str, queryParameter);
                return true;
            } else if (TextUtils.equals(str, "busStationError")) {
                bid pageContext6 = AMapPageUtil.getPageContext();
                if (pageContext6 == null) {
                    return false;
                }
                a(pageContext6.getArguments(), str, queryParameter);
                return true;
            } else if (TextUtils.equals(str, "locationError")) {
                String queryParameter6 = uri.getQueryParameter("poi");
                if (TextUtils.isEmpty(queryParameter6)) {
                    return false;
                }
                try {
                    POI a12 = bnx.a(queryParameter6);
                    IErrorReportStarter iErrorReportStarter3 = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                    if (iErrorReportStarter3 == null || a12 == null) {
                        return false;
                    }
                    iErrorReportStarter3.startLocationError(a12);
                    return true;
                } catch (Exception unused) {
                    return false;
                }
            } else if (TextUtils.equals(str, "busOtherIssue")) {
                bid pageContext7 = AMapPageUtil.getPageContext();
                if (pageContext7 == null) {
                    return false;
                }
                a(pageContext7.getArguments(), str, queryParameter);
                return true;
            } else if ("poiError".equals(str)) {
                AnonymousClass2 r8 = new cge("20000") {
                    public final void a(PageBundle pageBundle) {
                        pageBundle.putString("page_action", "amap.basemap.action.contribution_search_page");
                    }

                    public final int a() {
                        return R.string.poi_issue;
                    }
                };
                PageBundle pageBundle7 = new PageBundle();
                pageBundle7.putInt("sourcepage", 13);
                r8.a(pageBundle7);
                if (pageBundle7.containsKey("page_action")) {
                    String string = pageBundle7.getString("page_action");
                    pageBundle7.putString(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, AMapAppGlobal.getApplication().getString(r8.a()));
                    startPageForResult(string, pageBundle7, 30000);
                }
                return true;
            } else if ("driveIssue".equals(str)) {
                a("amap.basemap.action.drive_navigation_issue", "驾车导航问题", str, agt.a(uri, "sourcepage", -1));
                return true;
            } else if ("locationIssue".equals(str)) {
                col.a();
                if (TextUtils.isEmpty(queryParameter)) {
                    final ProgressDlg progressDlg = new ProgressDlg(AMapAppGlobal.getTopActivity());
                    progressDlg.setMessage("截图中...");
                    MapManager mapManager = DoNotUseTool.getMapManager();
                    if (mapManager != null) {
                        progressDlg.show();
                        cfc.a().a(mapManager, (a) new a() {
                            public final void onPrepare() {
                            }

                            public final void onFailure() {
                                progressDlg.dismiss();
                            }

                            public final void onScreenShotFinish(String str) {
                                progressDlg.dismiss();
                                if (str == null) {
                                    str = "";
                                }
                                avb.a(avb.this, str);
                            }
                        });
                    }
                } else if (!TextUtils.isEmpty(queryParameter)) {
                    a(str, queryParameter);
                }
                return true;
            } else if ("otherIssue".equals(str)) {
                a("amap.basemap.action.common_feedback_page", "其他使用问题", str, agt.a(uri, "sourcepage", -1));
                return true;
            } else if (TextUtils.equals(str, "selectPoi")) {
                bid pageContext8 = AMapPageUtil.getPageContext();
                if (pageContext8 == null) {
                    return false;
                }
                PageBundle pageBundle8 = new PageBundle();
                pageBundle8.putObject("SelectPoiFromMapBean", b(uri.getQueryParameter("data")));
                pageContext8.startPageForResult((String) "amap.basemap.action.base_select_fix_poi_from_map_page", pageBundle8, 107);
                return true;
            } else if (TextUtils.equals(str, "openPoi")) {
                bid pageContext9 = AMapPageUtil.getPageContext();
                String queryParameter7 = uri.getQueryParameter("data");
                if (pageContext9 == null || TextUtils.isEmpty(queryParameter7)) {
                    return false;
                }
                PageBundle pageBundle9 = new PageBundle();
                pageBundle9.putObject("POI", bnx.a(queryParameter7));
                pageContext9.startPage((String) "amap.search.action.poidetail", pageBundle9);
                return true;
            } else if (TextUtils.equals("offlineMap", str)) {
                col.a();
                if (TextUtils.isEmpty(queryParameter)) {
                    IErrorReportStarter iErrorReportStarter4 = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
                    if (iErrorReportStarter4 != null) {
                        col.a();
                        iErrorReportStarter4.startOfflineMapError(AMapPageFramework.getPageContext());
                    }
                } else if (!TextUtils.isEmpty(queryParameter)) {
                    a(str, queryParameter);
                }
                return true;
            } else if (TextUtils.equals("entrylist", str)) {
                if (!TextUtils.isEmpty(queryParameter)) {
                    a(str, queryParameter);
                }
                return true;
            } else if (TextUtils.equals("router", str)) {
                String queryParameter8 = uri.getQueryParameter("pageKey");
                if (!TextUtils.isEmpty(queryParameter8) && !TextUtils.isEmpty(queryParameter)) {
                    a(queryParameter8, queryParameter);
                }
                return true;
            }
            return false;
        }
    }

    private void a(String str, String str2, String str3, int i) {
        PageBundle pageBundle = new PageBundle();
        LocationInstrument.getInstance().getLocationLog(AMapAppGlobal.getTopActivity());
        pageBundle.putString(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, str2);
        pageBundle.putString("retype", cqi.a());
        if (i >= 0) {
            pageBundle.putInt("sourcepage", i);
        } else {
            pageBundle.putInt("sourcepage", 13);
        }
        pageBundle.putBoolean("isMapScreenshotRequired", false);
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (mapManager != null) {
            bty mapView = mapManager.getMapView();
            if (mapView != null) {
                pageBundle.putInt("scaleaccuracy", mapView.w());
            }
        }
        if (!"amap.basemap.action.drive_navigation_issue".equals(str)) {
            col.a();
            a(pageBundle, str3);
            return;
        }
        startPage(str, pageBundle);
    }

    private static void a(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2(LogConstant.PAGE_ID_FEEDBACK_LIST, "B020", jSONObject);
    }

    private static SelectPoiFromMapBean b(String str) {
        SelectPoiFromMapBean selectPoiFromMapBean;
        if (TextUtils.isEmpty(str)) {
            DoNotUseTool.getMapView();
            selectPoiFromMapBean = ave.a(POIFactory.createPOI("我的位置", LocationInstrument.getInstance().getLatestPosition()));
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                selectPoiFromMapBean = ave.a(POIFactory.createPOI(jSONObject.optString("poiName", ""), new GeoPoint(jSONObject.optInt(DictionaryKeys.CTRLXY_X), jSONObject.optInt(DictionaryKeys.CTRLXY_Y))));
            } catch (Exception unused) {
                selectPoiFromMapBean = ave.a(POIFactory.createPOI("我的位置", LocationInstrument.getInstance().getLatestPosition()));
            }
        }
        selectPoiFromMapBean.setLevel(18);
        return selectPoiFromMapBean;
    }

    private void a(PageBundle pageBundle, String str) {
        if (pageBundle != null && !TextUtils.isEmpty(str)) {
            PageBundle c = col.c(pageBundle, str);
            if ("entrylist".equals(str)) {
                startPage(Ajx3DialogPage.class, c);
                return;
            }
            startPage(Ajx3Page.class, c);
        }
    }

    private void a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            PageBundle a2 = col.a(str, str2);
            if ("entrylist".equals(str)) {
                startPage(Ajx3DialogPage.class, a2);
                return;
            }
            startPage(Ajx3Page.class, a2);
        }
    }

    private void a(PageBundle pageBundle, String str, String str2) {
        col.a();
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            if (pageBundle != null && !TextUtils.isEmpty(str)) {
                a(pageBundle, str);
            }
            return;
        }
        a(str, str2);
    }

    public static void a(bid bid, PageBundle pageBundle, String str) {
        if (pageBundle != null && !TextUtils.isEmpty(str)) {
            PageBundle c = col.c(pageBundle, str);
            if ("entrylist".equals(str)) {
                bid.startPage(Ajx3DialogPage.class, c);
                return;
            }
            bid.startPage(Ajx3Page.class, c);
        }
    }

    public boolean start(ese ese) {
        return a(ese.a);
    }

    static /* synthetic */ void a(avb avb, String str) {
        String str2;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, "定位问题");
        LocationInstrument.getInstance().getLocationLog(AMapAppGlobal.getTopActivity());
        pageBundle.putString("retype", cqi.a());
        pageBundle.putInt("sourcepage", 13);
        int i = 0;
        pageBundle.putBoolean("isMapScreenshotRequired", false);
        pageBundle.putString("url", "path://amap_bundle_basemap_feedback/src/location/FeedbackLocation.page.js");
        LocationInstrument.getInstance().getLocationLog(AMapAppGlobal.getTopActivity());
        String str3 = "";
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (mapManager != null) {
            bty mapView = mapManager.getMapView();
            int w = mapView.w();
            Rect H = mapView.H();
            str3 = String.format("%d,%d|%d,%d", new Object[]{Integer.valueOf(H.right), Integer.valueOf(H.top), Integer.valueOf(H.left), Integer.valueOf(H.bottom)});
            i = w;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(SocialConstants.PARAM_AVATAR_URI, str);
            jSONObject2.put("sourcepage", 13);
            jSONObject2.put("scaleaccuracy", i);
            jSONObject2.put("diagonal", str3);
            jSONObject2.put("type", ave.a());
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
            if (!(latestPosition == null || latestPosition.x == 0 || latestPosition.y == 0)) {
                DPoint a2 = cfg.a((long) latestPosition.x, (long) latestPosition.y);
                jSONObject2.put("longitude", String.valueOf(a2.x));
                jSONObject2.put("latitude", String.valueOf(a2.y));
                int i2 = latestPosition.x;
                int i3 = latestPosition.y;
                if (!(i2 == 0 && i3 == 0)) {
                    lj b = li.a().b(i2, i3);
                    if (b != null) {
                        str2 = String.valueOf(b.j);
                        jSONObject2.put(AutoJsonUtils.JSON_ADCODE, str2);
                    }
                }
                str2 = null;
                jSONObject2.put(AutoJsonUtils.JSON_ADCODE, str2);
            }
            jSONObject.put("data", jSONObject2);
        } catch (Exception unused) {
        }
        pageBundle.putString("jsData", jSONObject.toString());
        avb.startPage(Ajx3Page.class, pageBundle);
    }
}
