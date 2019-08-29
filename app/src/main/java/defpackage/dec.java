package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.drivecommon.restrictedarea.RestrictedAreaParam;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.tripgroup.api.IVoicePackageManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.Router;
import com.autonavi.carowner.roadcamera.page.RdCameraPaymentListPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import com.autonavi.minimap.drive.auto.page.AliCarLinkManagerPage;
import com.autonavi.minimap.drive.sticker.page.StickersPage;
import com.autonavi.minimap.drive.trafficboard.page.TrafficBoardPage;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

@Router({"openFeature", "schoolbus", "drive", "carRestrict", "truckRestrict"})
/* renamed from: dec reason: default package */
/* compiled from: DriveRouter */
public class dec extends esk {
    private boolean a = false;
    private long b = 0;
    private String c = null;
    private volatile boolean d = false;
    private long e = 0;

    private void a() {
        if (!NetworkReachability.b()) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.network_error_message));
        } else {
            startPage(StickersPage.class, new PageBundle());
        }
    }

    private static void a(int i) {
        bid pageContext = AMapPageUtil.getPageContext();
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("bundle_key_car_or_truck", i);
        if (pageContext != null) {
            pageContext.startPage((String) "amap.basemap.action.car_restrict_city_list", pageBundle);
        }
    }

    private boolean a(Intent intent) {
        int i;
        Uri data = intent.getData();
        String host = data.getHost();
        if (host == null) {
            return false;
        }
        if ((!TextUtils.equals(host, "drive") && !TextUtils.equals(host, "carRestrict") && !TextUtils.equals(host, "truckRestrict")) || data.getPathSegments() == null || data.getPathSegments().size() == 0) {
            return false;
        }
        String str = data.getPathSegments().get(0).split("&")[0];
        if (str == null || str.length() <= 0) {
            return false;
        }
        bid pageContext = AMapPageUtil.getPageContext();
        if (TextUtils.equals(host, "drive")) {
            intent.getBooleanExtra("param_no_need_remove_page", false);
            if (TextUtils.equals(str, "avoidparkingticket")) {
                a();
                return true;
            } else if (TextUtils.equals(str, "openCarConnection")) {
                vp vpVar = (vp) a.a.a(vp.class);
                if ((vpVar == null || !vpVar.c()) && (agb.b() || TextUtils.isEmpty(agb.d().trim()))) {
                    String d2 = agb.d();
                    if (!d2.equals("amap_bluetooth") && !d2.equals("amap_bluetooth_20")) {
                        startPage(AliCarLinkManagerPage.class, new PageBundle());
                    } else if (pageContext != null) {
                        pageContext.startPage((String) "amap.drive.action.alicar.manage", new PageBundle());
                    }
                } else if (pageContext != null) {
                    pageContext.startPage((String) "amap.drive.action.alicar.manage", new PageBundle());
                }
                return true;
            } else if (TextUtils.equals(str, "commute")) {
                return false;
            }
        } else if (TextUtils.equals(host, "carRestrict")) {
            if (TextUtils.equals(str, "openRestrictCities")) {
                a(0);
                return true;
            } else if (TextUtils.equals(str, "showRestrictDetail")) {
                String queryParameter = data.getQueryParameter("citycode");
                String queryParameter2 = data.getQueryParameter("cartype");
                if (!TextUtils.isEmpty(queryParameter) && !TextUtils.isEmpty(queryParameter2)) {
                    String queryParameter3 = data.getQueryParameter("carplate");
                    try {
                        i = Integer.parseInt(queryParameter2);
                    } catch (NumberFormatException e2) {
                        if (bno.a) {
                            e2.printStackTrace();
                        }
                        i = 0;
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putString("url", "path://amap_bundle_drive/src/car/restrict_page/CarRestrictPage.page.js");
                    pageBundle.putInt("source", 1);
                    pageBundle.putObject(AutoJsonUtils.JSON_ADCODE, RestrictedAreaParam.buildCityRestrictPolicyParam(queryParameter, queryParameter3, i));
                    pageBundle.putInt("cartype", i);
                    startPage((String) "amap.basemap.action.car_restrict", pageBundle);
                    return true;
                }
            } else if (TextUtils.equals(str, "openCarConnection")) {
                startPage(AliCarLinkManagerPage.class, new PageBundle());
                return true;
            }
        } else if (TextUtils.equals(host, "truckRestrict") && TextUtils.equals(str, "openRestrictCities")) {
            a(1);
            return true;
        }
        return false;
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        Intent intent = ese.b;
        if (uri == null) {
            return false;
        }
        String host = uri.getHost();
        if (TextUtils.isEmpty(host)) {
            return false;
        }
        if (host.equalsIgnoreCase(BaseIntentDispatcher.HOST_OPENFEATURE)) {
            Uri data = intent.getData();
            String queryParameter = data.getQueryParameter("featureName");
            if (TextUtils.isEmpty(queryParameter)) {
                ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.intent_open_fail_param_error));
            } else {
                StringBuilder sb = new StringBuilder("doOpenFeature uri ");
                sb.append(data.toString());
                sb.append(" , params ");
                sb.append(queryParameter);
                AMapLog.d("DriveIntentDispatcher", sb.toString());
                if (queryParameter.equalsIgnoreCase("openStickers")) {
                    a();
                } else if (queryParameter.equalsIgnoreCase("TRCCompensate")) {
                    String queryParameter2 = data.getQueryParameter("pageID");
                    int i = 2;
                    if ("1".equals(queryParameter2)) {
                        i = 1;
                    } else {
                        boolean equals = "2".equals(queryParameter2);
                    }
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("pageID", i);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putObject("amap.extra.road.camera.pageparam", jSONObject);
                    bid pageContext = AMapPageUtil.getPageContext();
                    if (pageContext != null) {
                        pageContext.startPage(RdCameraPaymentListPage.class, pageBundle);
                    }
                } else if (queryParameter.equalsIgnoreCase("openTrafficTopBoard")) {
                    bid pageContext2 = AMapPageUtil.getPageContext();
                    if (pageContext2 != null) {
                        pageContext2.startPage(TrafficBoardPage.class, (PageBundle) null);
                    }
                } else if (queryParameter.equalsIgnoreCase("tts")) {
                    IVoicePackageManager iVoicePackageManager = (IVoicePackageManager) ank.a(IVoicePackageManager.class);
                    if (iVoicePackageManager != null) {
                        Intent intent2 = new Intent();
                        bid pageContext3 = AMapPageUtil.getPageContext();
                        String queryParameter3 = data.getQueryParameter("page");
                        String queryParameter4 = data.getQueryParameter(DriveUtil.SOURCE_APPLICATION);
                        if ("naviVoiceRecord".equalsIgnoreCase(queryParameter3)) {
                            intent2.putExtra(IVoicePackageManager.ENTRANCE_RECORD_CUSTOMIZED_VOICES, true);
                            if (pageContext3 != null) {
                                iVoicePackageManager.deal(pageContext3, intent2);
                            }
                        } else {
                            int i2 = -1;
                            if (IVoicePackageManager.SHOW_TTS_FROM_KEY_SCHEME_AUDIO_GUIDE.equals(queryParameter4)) {
                                i2 = 101;
                            } else if (IVoicePackageManager.SHOW_TTS_FROM_KEY_SCHEME_TRIP.equals(queryParameter4)) {
                                i2 = 102;
                            }
                            intent2.putExtra(IVoicePackageManager.SHOW_TTS_FROM_KEY, i2);
                            intent2.putExtra(IVoicePackageManager.ENTRANCE_VOICE_SQUARE, true);
                            if (pageContext3 != null) {
                                iVoicePackageManager.deal(pageContext3, intent2);
                            }
                        }
                    }
                } else if (!queryParameter.equalsIgnoreCase("Mine")) {
                    return false;
                } else {
                    String queryParameter5 = data.getQueryParameter("page");
                    if (TextUtils.isEmpty(queryParameter5) || !queryParameter5.equals("ToolBox")) {
                        bid pageContext4 = AMapPageUtil.getPageContext();
                        if (pageContext4 != null) {
                            PageBundle pageBundle2 = new PageBundle();
                            new bnv();
                            pageBundle2.putString("jsData", bnv.b());
                            apr apr = (apr) a.a.a(apr.class);
                            if (apr != null) {
                                apr.b(pageContext4, pageBundle2);
                            }
                        }
                        return true;
                    }
                    String queryParameter6 = data.getQueryParameter(RouteItem.ITEM_TAG);
                    if (TextUtils.isEmpty(queryParameter6) || queryParameter6.equals("TakeTaxi")) {
                        return true;
                    }
                    return false;
                }
            }
            return true;
        } else if (TextUtils.equals(host, "drive") || TextUtils.equals(host, "carRestrict") || TextUtils.equals(host, "truckRestrict")) {
            return a(intent);
        } else {
            return false;
        }
    }
}
