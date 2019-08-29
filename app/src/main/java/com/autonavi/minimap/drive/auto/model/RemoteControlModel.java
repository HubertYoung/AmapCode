package com.autonavi.minimap.drive.auto.model;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.nebula.search.H5SearchType;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.searchservice.api.ISearchService;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.modules.ModuleNavi;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.auto.page.RemoteControlFragment;
import com.autonavi.minimap.drive.search.controller.ISearchCompleteListener;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class RemoteControlModel extends su<deq> {
    public fbl c;
    public String d = "";
    public boolean e = false;
    public boolean f = false;
    public fbm g = new fbm() {
        public final void a(String str, int i) {
            if (i == 1) {
                ToastHelper.showLongToast(RemoteControlModel.this.a().getString(R.string.remote_control_connected));
                ((deq) RemoteControlModel.this.a).a(true);
                return;
            }
            ToastHelper.showLongToast(RemoteControlModel.this.a().getString(R.string.remote_control_disconnected));
            ((deq) RemoteControlModel.this.a).a(false);
        }
    };
    ISearchCompleteListener h = new ISearchCompleteListener() {
        public final void a(String str) {
        }

        public final void a(POI poi) {
            RemoteControlModel.this.a(poi);
        }
    };

    class MyNetRequestCallback implements AosResponseCallbackOnUi<AosByteResponse> {
        final /* synthetic */ RemoteControlModel a;
        private String b;

        public /* synthetic */ void onSuccess(AosResponse aosResponse) {
            AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
            if (aosByteResponse == null || aosByteResponse.getResult() == null || ((byte[]) aosByteResponse.getResult()).length == 0) {
                a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(new String((byte[]) aosByteResponse.getResult()));
                int optInt = jSONObject.optInt("code");
                if (optInt == 1) {
                    JSONObject optJSONObject = jSONObject.optJSONObject("voice_result");
                    JSONObject optJSONObject2 = optJSONObject != null ? optJSONObject : jSONObject.optJSONObject("voice_result");
                    String optString = optJSONObject2.optString("task_type");
                    String optString2 = optJSONObject2.optString("sub_task_type");
                    if (TextUtils.isEmpty(optString) || optString2 == null) {
                        a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                    } else if (optString.equals(H5SearchType.SEARCH)) {
                        this.a.a(this.a.f, this.b);
                    } else if (optString.equals(H5Param.DEFAULT_LONG_BACK_BEHAVIOR)) {
                        POI pOICompany = DriveUtil.getPOICompany();
                        POI pOIHome = DriveUtil.getPOIHome();
                        if (!"back_home".equals(optString2)) {
                            if ("back_company".equals(optString2)) {
                                if (pOICompany == null) {
                                    a(new Exception("请先设置公司的地址"), false);
                                    PlaySoundUtils.getInstance().playSound("请先设置公司的地址");
                                    return;
                                }
                                this.a.a(pOICompany);
                            }
                        } else if (pOIHome == null) {
                            a(new Exception("请先设置家的地址"), false);
                            PlaySoundUtils.getInstance().playSound("请先设置家的地址");
                        } else {
                            this.a.a(pOIHome);
                        }
                    } else if (optString.equals(AutoConstants.AUTO_FILE_ROUTE)) {
                        if (optJSONObject == null) {
                            a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                            return;
                        }
                        JSONObject optJSONObject3 = optJSONObject.optJSONObject("navigation");
                        if (optJSONObject3 == null) {
                            a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                            return;
                        }
                        JSONArray optJSONArray = optJSONObject3.optJSONArray("from");
                        if (optJSONArray != null && optJSONArray.length() > 0) {
                            JSONObject optJSONObject4 = optJSONArray.optJSONObject(0);
                            if (optJSONObject4 != null) {
                                String optString3 = optJSONObject4.optString("name");
                                if (!TextUtils.isEmpty(optString3) && !optString3.equals(this.a.a().getString(R.string.route_my_position))) {
                                    a(new Exception(this.a.a().getString(R.string.voice_recognizer_dialog_not_support)), false);
                                    return;
                                }
                            }
                        }
                        String optString4 = optJSONObject3.optString("end_keyword");
                        String optString5 = optJSONObject3.optString("navi_type");
                        if (TextUtils.isEmpty(optString4)) {
                            a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                        } else if (TextUtils.isEmpty(optString5) || (!optString5.equals("car") && !optString5.equals("default"))) {
                            a(new Exception(this.a.a().getString(R.string.voice_recognizer_dialog_not_support)), false);
                        } else {
                            Resources resources = AMapAppGlobal.getApplication().getResources();
                            if (resources.getString(R.string.home).equals(optString4)) {
                                POI pOIHome2 = DriveUtil.getPOIHome();
                                if (pOIHome2 == null) {
                                    a(new Exception("请先设置家的地址"), false);
                                    PlaySoundUtils.getInstance().playSound("请先设置家的地址");
                                    return;
                                }
                                this.a.a(pOIHome2);
                            } else if (resources.getString(R.string.company).equals(optString4)) {
                                POI pOICompany2 = DriveUtil.getPOICompany();
                                if (pOICompany2 == null) {
                                    a(new Exception("请先设置公司的地址"), false);
                                    PlaySoundUtils.getInstance().playSound("请先设置公司的地址");
                                    return;
                                }
                                this.a.a(pOICompany2);
                            } else {
                                this.a.a(this.a.f, optString4);
                            }
                        }
                    } else if (!optString.equals(ModuleNavi.MODULE_NAME)) {
                        a(new Exception(this.a.a().getString(R.string.voice_recognizer_dialog_not_support)), false);
                    } else if (optJSONObject == null) {
                        a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                    } else {
                        JSONObject optJSONObject5 = optJSONObject.optJSONObject("navigation");
                        if (optJSONObject5 == null) {
                            a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                            return;
                        }
                        JSONArray optJSONArray2 = optJSONObject5.optJSONArray("to");
                        if (optJSONArray2 == null || optJSONArray2.length() <= 0) {
                            a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                            return;
                        }
                        JSONObject optJSONObject6 = optJSONArray2.optJSONObject(0);
                        if (optJSONObject6 == null) {
                            a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                            return;
                        }
                        String optString6 = optJSONObject6.optString("name");
                        if (TextUtils.isEmpty(optString6)) {
                            a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                        } else {
                            this.a.a(this.a.f, optString6);
                        }
                    }
                } else if (optInt == 7) {
                    a(new Exception(this.a.a().getString(R.string.voice_tips_no_result)), false);
                } else {
                    a(new Exception(this.a.a().getString(R.string.voice_iflytek_error_tip)), false);
                }
            } catch (JSONException e) {
                a(e, true);
            }
        }

        public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
            ((deq) this.a.a).a(this.a.a().getString(R.string.voice_iflytek_error_tip));
        }

        private void a(Throwable th, boolean z) {
            String str;
            if (z) {
                str = this.a.a().getString(R.string.voice_iflytek_error_tip);
            } else {
                str = th.getMessage();
            }
            ((deq) this.a.a).a(str);
        }
    }

    public RemoteControlModel(deq deq) {
        super(deq);
    }

    private int b(POI poi) {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        JSONObject jSONObject4 = new JSONObject();
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        try {
            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, latestPosition.getLongitude());
            jSONObject.put("lat", latestPosition.getLatitude());
            jSONObject.put("addr", "我的位置");
            jSONObject.put("name", "");
            jSONObject.put(TrafficUtil.POIID, "");
            jSONObject.put("time", System.currentTimeMillis());
            jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, poi.getPoint().getLongitude());
            jSONObject2.put("lat", poi.getPoint().getLatitude());
            jSONObject2.put("addr", poi.getAddr());
            jSONObject2.put("name", poi.getName());
            jSONObject2.put(TrafficUtil.POIID, poi.getId());
            jSONObject2.put("time", System.currentTimeMillis());
            if (poi.getEntranceList() == null || poi.getEntranceList().size() <= 0) {
                jSONObject3.put(LocationParams.PARA_FLP_AUTONAVI_LON, poi.getPoint().getLongitude());
                jSONObject3.put("lat", poi.getPoint().getLatitude());
                jSONObject3.put("addr", poi.getAddr());
                jSONObject3.put("name", poi.getName());
                jSONObject3.put(TrafficUtil.POIID, poi.getId());
                jSONObject3.put("time", System.currentTimeMillis());
            } else {
                jSONObject3.put(LocationParams.PARA_FLP_AUTONAVI_LON, poi.getEntranceList().get(0).getLongitude());
                jSONObject3.put("lat", poi.getEntranceList().get(0).getLatitude());
                jSONObject3.put("addr", poi.getAddr());
                jSONObject3.put("name", poi.getName());
                jSONObject3.put(TrafficUtil.POIID, poi.getId());
                jSONObject3.put("time", System.currentTimeMillis());
            }
            jSONObject4.put("startPoint", jSONObject);
            jSONObject4.put("showPoint", jSONObject2);
            jSONObject4.put("endPoint", jSONObject3);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        StringBuilder sb = new StringBuilder("sendToAliAuto     msg:");
        sb.append(a(jSONObject4).toString());
        AMapLog.i("RemoteControlModel", sb.toString());
        return this.c.b(6, a(jSONObject4).toString());
    }

    private static JSONObject a(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("msgId", UUID.randomUUID());
            jSONObject2.put("data", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject2;
    }

    public final void a(boolean z, String str) {
        if (!TextUtils.isEmpty(str)) {
            if (str.equals(a().getString(R.string.my_location))) {
                ToastHelper.showLongToast(a().getString(R.string.autonavi_quick_navi_not_found));
                return;
            }
            bty bty = null;
            MapManager mapManager = ((DriveBasePage) ((deq) this.a).mPage) != null ? DoNotUseTool.getMapManager() : null;
            if (mapManager != null) {
                bty = mapManager.getMapView();
            }
            if (bty != null) {
                ISearchService iSearchService = (ISearchService) a.a.a(ISearchService.class);
                if (iSearchService != null) {
                    boolean z2 = this.f;
                    aen aen = new aen(str, bty.H());
                    aen.c = "533000";
                    aen.f = "car";
                    aen.d = SuperId.getInstance().getScenceId();
                    dih dih = new dih((DriveBasePage) ((deq) this.a).mPage, aen, z, a().getString(R.string.autonavi_quick_navi_confirm_destination), this.h);
                    dii.a(str, iSearchService.b(aew.a((aem) aen), z2 ? 1 : 0, dih), ((deq) this.a).b());
                }
            }
        }
    }

    public final void a(POI poi) {
        Boolean bool;
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        boolean booleanValue = mapSharePreference.getBooleanValue("ali_auto_car_connected", false);
        boolean booleanValue2 = mapSharePreference.getBooleanValue("amap_auto_car_connected", false);
        if (booleanValue || booleanValue2) {
            bool = Boolean.TRUE;
        } else {
            bool = Boolean.FALSE;
        }
        POI poi2 = null;
        if (!bool.booleanValue()) {
            ((deq) this.a).a(-1, (String) null);
        } else if (poi != null) {
            StringBuilder sb = new StringBuilder("sendPoiToCar    endPoi.getName():");
            sb.append(poi.getName());
            AMapLog.i("RemoteControlModel", sb.toString());
            StringBuilder sb2 = new StringBuilder("sendPoiToCar    endPoi.getAddr():");
            sb2.append(poi.getAddr());
            AMapLog.i("RemoteControlModel", sb2.toString());
            if (TextUtils.isEmpty(poi.getAddr()) && !TextUtils.isEmpty(poi.getName())) {
                poi.setAddr(poi.getName());
            } else if (!TextUtils.isEmpty(poi.getAddr()) && TextUtils.isEmpty(poi.getName())) {
                poi.setName(poi.getAddr());
            }
            if (this.c != null && this.c.b() && agb.d().equals("ali_auto_wifi")) {
                ((deq) this.a).a(b(poi), poi.getName());
                DriveUtil.saveNaviHitory(poi);
            }
            if (agb.c().booleanValue()) {
                RemoteControlFragment remoteControlFragment = (RemoteControlFragment) ((deq) this.a).mPage;
                if (NetworkReachability.b()) {
                    if (!agb.c().booleanValue()) {
                        ToastHelper.showToast(remoteControlFragment.getString(R.string.remote_control_disconnected));
                        return;
                    }
                    Activity activity = remoteControlFragment.getActivity();
                    String string = remoteControlFragment.getString(R.string.send_end_to_car);
                    if (activity != null && !activity.isFinishing()) {
                        remoteControlFragment.q = new ProgressDlg(activity, string);
                        remoteControlFragment.q.setOnDismissListener(new OnDismissListener() {
                            public final void onDismiss(DialogInterface dialogInterface) {
                                if (RemoteControlFragment.this.s != null) {
                                    RemoteControlFragment.this.s.cancel();
                                }
                            }
                        });
                        if (!remoteControlFragment.q.isShowing()) {
                            remoteControlFragment.q.setCanceledOnTouchOutside(false);
                            remoteControlFragment.q.setCancelable(true);
                            remoteControlFragment.q.show();
                        }
                    }
                    remoteControlFragment.a = (new Random().nextInt(999999) % 900000) + 100000;
                    if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                        poi2 = POIFactory.createPOI("我的位置", LocationInstrument.getInstance().getLatestPosition());
                    }
                    remoteControlFragment.g = poi2;
                    remoteControlFragment.h = poi;
                    if (poi != null && poi.getName() != null) {
                        remoteControlFragment.i = poi.getName();
                    } else if (!(poi == null || poi.getAddr() == null)) {
                        remoteControlFragment.i = poi.getAddr();
                    }
                    ahn.b().execute(new Runnable(remoteControlFragment.c(), poi) {
                        final /* synthetic */ Map a;
                        final /* synthetic */ POI b;

                        {
                            this.a = r2;
                            this.b = r3;
                        }

                        public final void run() {
                            RemoteControlFragment.this.r = null;
                            try {
                                IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) defpackage.esb.a.a.a(IAutoRemoteController.class);
                                if (iAutoRemoteController == null) {
                                    RemoteControlFragment remoteControlFragment = RemoteControlFragment.this;
                                    String string = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                                    String string2 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                                    RemoteControlFragment.this.getString(R.string.drive_confirm);
                                    remoteControlFragment.a(string, string2);
                                    return;
                                }
                                byte[] bytes = iAutoRemoteController.getBytes("/autoservice/accept/navi/route_request", this.a);
                                if (bytes == null) {
                                    RemoteControlFragment remoteControlFragment2 = RemoteControlFragment.this;
                                    String string3 = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                                    String string4 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                                    RemoteControlFragment.this.getString(R.string.drive_confirm);
                                    remoteControlFragment2.a(string3, string4);
                                    return;
                                }
                                JSONObject jSONObject = new JSONObject(new String(bytes).trim());
                                String optString = jSONObject.optString("result");
                                if (TextUtils.isEmpty(optString) || !optString.equals("false")) {
                                    RemoteControlFragment.this.r = jSONObject.optString("request_url");
                                    aho.a(new Runnable() {
                                        public final void run() {
                                            DriveUtil.saveNaviHitory(AnonymousClass2.this.b);
                                            if (TextUtils.isEmpty(RemoteControlFragment.this.r)) {
                                                RemoteControlFragment remoteControlFragment = RemoteControlFragment.this;
                                                String string = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                                                String string2 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                                                RemoteControlFragment.this.getString(R.string.drive_confirm);
                                                remoteControlFragment.a(string, string2);
                                                return;
                                            }
                                            RemoteControlFragment.b(RemoteControlFragment.this, RemoteControlFragment.this.r);
                                        }
                                    });
                                    return;
                                }
                                jSONObject.optString("code");
                                jSONObject.optString("message");
                                RemoteControlFragment remoteControlFragment3 = RemoteControlFragment.this;
                                String string5 = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                                String string6 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                                RemoteControlFragment.this.getString(R.string.drive_confirm);
                                remoteControlFragment3.a(string5, string6);
                            } catch (JSONException unused) {
                                RemoteControlFragment remoteControlFragment4 = RemoteControlFragment.this;
                                String string7 = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                                String string8 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                                RemoteControlFragment.this.getString(R.string.drive_confirm);
                                remoteControlFragment4.a(string7, string8);
                            } catch (IOException unused2) {
                                RemoteControlFragment remoteControlFragment5 = RemoteControlFragment.this;
                                String string9 = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                                String string10 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                                RemoteControlFragment.this.getString(R.string.drive_confirm);
                                remoteControlFragment5.a(string9, string10);
                            }
                        }
                    });
                }
            }
        }
    }
}
