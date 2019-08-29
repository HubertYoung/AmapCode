package defpackage;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.text.TextUtils;
import com.amap.bundle.headunit.HeadunitServiceImpl$4;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.tripgroup.api.IAutoRemoteController.ConnectionType;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.model.POI;
import com.autonavi.link.LinkSDK;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.minimap.tservice.TserviceRequestHolder;
import com.autonavi.minimap.tservice.param.SendPoi2CarRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

@BundleInterface(vp.class)
/* renamed from: vm reason: default package */
/* compiled from: HeadunitServiceImpl */
public class vm implements vp {
    /* access modifiers changed from: private */
    public boolean a = false;
    /* access modifiers changed from: private */
    public int b = 0;
    /* access modifiers changed from: private */
    public vq c;
    private OnSharedPreferenceChangeListener d;

    public vm() {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        this.d = new OnSharedPreferenceChangeListener() {
            public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
                if ("car_login_update_flag".equals(str)) {
                    vm.d(vm.this);
                    if (vm.this.c != null) {
                        vm.this.c.onHeadunitLoginStateChanged(vm.this.b);
                    }
                }
            }
        };
        mapSharePreference.sharedPrefs().registerOnSharedPreferenceChangeListener(this.d);
    }

    public final boolean b() {
        return c() || d();
    }

    public final void a(POI poi, final vo voVar) {
        if (!this.a) {
            voVar.onError("", "Headunit Cloud Ctl close");
        } else if (poi == null) {
            voVar.onError("", "poi is null");
        } else {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("address", poi.getAddr());
                jSONObject.put("name", poi.getName());
                jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, poi.getPoint().getLongitude());
                jSONObject.put("lat", poi.getPoint().getLatitude());
                jSONObject.put("poiID", poi.getId());
                final String jSONObject2 = jSONObject.toString();
                vl.a("HeadunitServiceImpl", "sendPoiToHeadunit    poiInfo:".concat(String.valueOf(jSONObject2)));
                if (c()) {
                    if (!TextUtils.isEmpty(jSONObject2)) {
                        ahn.b().execute(new Runnable() {
                            public final void run() {
                                byte[] bytes = jSONObject2.getBytes();
                                Map e = vm.e();
                                IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
                                if (iAutoRemoteController != null) {
                                    try {
                                        byte[] postBytes = iAutoRemoteController.postBytes("/autoservice/accept/navi/poi_result", e, bytes);
                                        StringBuilder sb = new StringBuilder("sendRouteToHeadunitByLinkSDK    result:");
                                        sb.append(postBytes == null ? null : new String(postBytes));
                                        vl.a("HeadunitServiceImpl", sb.toString());
                                        if (postBytes != null) {
                                            JSONObject jSONObject = new JSONObject(new String(postBytes).trim());
                                            if (!"true".equals(jSONObject.optString("result"))) {
                                                String optString = jSONObject.optString("code");
                                                String optString2 = jSONObject.optString("message");
                                                if (voVar != null) {
                                                    voVar.onError(optString, optString2);
                                                }
                                            } else if (voVar != null) {
                                                voVar.onSuccess(0);
                                            }
                                        }
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    } catch (Exception unused) {
                                    }
                                }
                            }
                        });
                    }
                    return;
                }
                if (d()) {
                    vl.a("HeadunitServiceImpl", "sendPoiToHeadunitByAos    poiInfo:".concat(String.valueOf(jSONObject2)));
                    if (!TextUtils.isEmpty(jSONObject2)) {
                        SendPoi2CarRequest sendPoi2CarRequest = new SendPoi2CarRequest();
                        sendPoi2CarRequest.b = jSONObject2;
                        sendPoi2CarRequest.c = "true";
                        sendPoi2CarRequest.d = "86400";
                        sendPoi2CarRequest.e = "auto_amap";
                        sendPoi2CarRequest.f = "amap";
                        sendPoi2CarRequest.g = "aimpoi";
                        a(sendPoi2CarRequest, voVar);
                    }
                }
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("sendPoiToHeadunit  Exception ");
                sb.append(e.getMessage());
                voVar.onError("", sb.toString());
            }
        }
    }

    public final void a(final String str, final vo voVar) {
        vl.a("HeadunitServiceImpl", "sendRouteToHeadunit    routeInfo:".concat(String.valueOf(str)));
        if (!this.a) {
            if (voVar != null) {
                voVar.onError("", "Headunit Cloud Ctl close");
            }
        } else if (TextUtils.isEmpty(str)) {
            if (voVar != null) {
                voVar.onError("", "route info is null");
            }
        } else if (c()) {
            if (!TextUtils.isEmpty(str)) {
                ahn.b().execute(new Runnable() {
                    public final void run() {
                        byte[] bytes = str.getBytes();
                        Map e = vm.e();
                        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
                        if (iAutoRemoteController != null) {
                            try {
                                byte[] postBytes = iAutoRemoteController.postBytes("/autoservice/accept/navi/route_restore", e, bytes);
                                StringBuilder sb = new StringBuilder("sendRouteToHeadunitByLinkSDK    result:");
                                sb.append(postBytes == null ? null : new String(postBytes));
                                vl.a("HeadunitServiceImpl", sb.toString());
                                if (postBytes != null) {
                                    JSONObject jSONObject = new JSONObject(new String(postBytes).trim());
                                    if (!"true".equals(jSONObject.optString("result"))) {
                                        String optString = jSONObject.optString("code");
                                        String optString2 = jSONObject.optString("message");
                                        if (voVar != null) {
                                            voVar.onError(optString, optString2);
                                        }
                                    } else if (voVar != null) {
                                        voVar.onSuccess(0);
                                    }
                                }
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            } catch (Exception unused) {
                            }
                        }
                    }
                });
            }
        } else {
            if (d()) {
                vl.a("HeadunitServiceImpl", "sendRouteToHeadunitByAos    routeInfo:".concat(String.valueOf(str)));
                if (!TextUtils.isEmpty(str)) {
                    SendPoi2CarRequest sendPoi2CarRequest = new SendPoi2CarRequest();
                    sendPoi2CarRequest.b = str;
                    sendPoi2CarRequest.c = "true";
                    sendPoi2CarRequest.d = "86400";
                    sendPoi2CarRequest.e = "auto_amap";
                    sendPoi2CarRequest.f = "amap";
                    sendPoi2CarRequest.g = "aimline";
                    a(sendPoi2CarRequest, voVar);
                }
            }
        }
    }

    public final boolean c() {
        boolean isConnect = LinkSDK.getInstance().getWifiInstance().getIsConnect();
        boolean g = g();
        vl.a("HeadunitServiceImpl", " isConnectedWifi:".concat(String.valueOf(isConnect)));
        return this.a && isConnect && g;
    }

    public final void a(vq vqVar) {
        this.c = vqVar;
    }

    private void a(SendPoi2CarRequest sendPoi2CarRequest, vo voVar) {
        TserviceRequestHolder.getInstance().sendSendPoi2Car(sendPoi2CarRequest, new HeadunitServiceImpl$4(this, voVar));
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                int optInt = new JSONObject(str).optInt("send_switch");
                boolean z = true;
                if (optInt != 1) {
                    z = false;
                }
                this.a = z;
                StringBuilder sb = new StringBuilder("updateHeadunitCtlCloudOpen  mIsHeadunitCtlCloudOpen:");
                sb.append(this.a);
                vl.a("HeadunitServiceImpl", sb.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public final void a() {
        String a2 = lo.a().a((String) "send_to_auto");
        vl.a("HeadunitServiceImpl", "isHeadunitCtlCloudOpen   ".concat(String.valueOf(a2)));
        if (!TextUtils.isEmpty(a2)) {
            a(a2);
            if (this.a) {
                h();
            }
        } else {
            lo.a().a((String) "send_to_auto", (lp) new lp() {
                public final void onConfigCallBack(int i) {
                }

                public final void onConfigResultCallBack(int i, String str) {
                    vl.a("HeadunitServiceImpl", "onConfigResultCallBack  state:".concat(String.valueOf(i)));
                    vl.a("HeadunitServiceImpl", "onConfigResultCallBack  result:".concat(String.valueOf(str)));
                    if (!vm.this.a) {
                        vm.this.a(str);
                        if (vm.this.a) {
                            vm.h();
                            if (vm.this.c != null) {
                                vm.this.c.onHeadunitLoginStateChanged(vm.this.b);
                            }
                        }
                    }
                }
            });
        }
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            iAutoRemoteController.setRemoteControlConnectListener(new aga() {
                public final void a(ConnectionType connectionType) {
                    vl.a("HeadunitServiceImpl", "RemoteControlConnectListener  onConnected  type:".concat(String.valueOf(connectionType)));
                    if (vm.this.c != null) {
                        vm.this.c.onHeadunitWifiConnectStateChanged(true);
                    }
                }

                public final void b(ConnectionType connectionType) {
                    vl.a("HeadunitServiceImpl", "RemoteControlConnectListener  onDisConnected  type:".concat(String.valueOf(connectionType)));
                    if (vm.this.c != null) {
                        vm.this.c.onHeadunitWifiConnectStateChanged(false);
                    }
                }
            });
        }
    }

    public final boolean d() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null || !iAccountService.a()) {
            vl.a("HeadunitServiceImpl", "isHeadunitLoginUser  未登录");
            return false;
        }
        boolean z = this.b == 1 || this.b == 3;
        if (!this.a || !z) {
            return false;
        }
        return true;
    }

    private static boolean g() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            DiscoverInfo wifiDiscoverInfo = iAutoRemoteController.getWifiDiscoverInfo();
            if (wifiDiscoverInfo != null) {
                String str = wifiDiscoverInfo.appVersion;
                if (!TextUtils.isEmpty(str) && str.length() > 2 && str.contains(".")) {
                    int indexOf = str.indexOf(".");
                    if (indexOf > 0) {
                        String substring = str.substring(0, indexOf);
                        if (!TextUtils.isEmpty(substring)) {
                            try {
                                if (Integer.parseInt(substring) >= 4) {
                                    return true;
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static void h() {
        IAutoRemoteController iAutoRemoteController = (IAutoRemoteController) a.a.a(IAutoRemoteController.class);
        if (iAutoRemoteController != null) {
            vl.a("HeadunitServiceImpl", "startAlinkWifi");
            iAutoRemoteController.startAlinkWifi(null);
        }
    }

    static /* synthetic */ Map e() {
        HashMap hashMap = new HashMap(2);
        hashMap.put("sessionid", String.valueOf((new Random().nextInt(999999) % 900000) + 100000));
        hashMap.put("source", "amap");
        return hashMap;
    }

    static /* synthetic */ void d(vm vmVar) {
        vmVar.b = new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue("car_login_flag", 0);
        StringBuilder sb = new StringBuilder("isHeadunitLoginUser  mHeadunitLoginFlag:");
        sb.append(vmVar.b);
        vl.a("HeadunitServiceImpl", sb.toString());
    }
}
