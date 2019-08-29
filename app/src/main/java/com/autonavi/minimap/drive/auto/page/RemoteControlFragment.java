package com.autonavi.minimap.drive.auto.page;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.tripgroup.api.IAutoRemoteController;
import com.amap.bundle.tripgroup.api.IAutoRemoteController.ConnectionType;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.banner.view.DBanner;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.core.network.inter.response.ByteResponse;
import com.autonavi.core.network.inter.response.ResponseException;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.drive.auto.model.RemoteControlModel;
import com.autonavi.minimap.drive.quicknaviwidget.RemoteControlFragmentHistoryView;
import com.autonavi.minimap.drive.widget.RouteFragmentHomeAddressView;
import com.autonavi.minimap.drive.widget.RouteFragmentHomeAddressView.b;
import com.autonavi.minimap.search.model.SelectPoiFromMapBean;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import com.iflytek.tts.TtsService.Tts;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.drive.action.remote.control")
public class RemoteControlFragment extends DriveBasePage<deq> implements launchModeSingleTask {
    public int a;
    public boolean b = false;
    public RouteFragmentHomeAddressView c;
    public RemoteControlFragmentHistoryView d;
    public DBanner e;
    public View f;
    public POI g;
    public POI h;
    public String i;
    public boolean j = true;
    public TitleBar k;
    public AlertView l;
    public final OnClickListener m = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.quick_autonavi) {
                RemoteControlFragment.a(RemoteControlFragment.this, 3, null, Tts.TTS_STATE_DESTROY, RemoteControlFragment.this.getString(R.string.act_fromto_dest_input_hint), true, SelectFor.TO_POI, true);
            }
        }
    };
    public aga n = new aga() {
        public final void a(ConnectionType connectionType) {
            RemoteControlFragment.this.a(true);
        }

        public final void b(ConnectionType connectionType) {
            RemoteControlFragment.this.b();
            RemoteControlFragment.this.a(false);
            ToastHelper.showLongToast(RemoteControlFragment.this.getString(R.string.remote_control_disconnected));
            if (connectionType == ConnectionType.BLUETOOTH) {
                RemoteControlFragment.this.startPage(AutoBluetoothLinkManagerPage.class, new PageBundle());
            }
        }
    };
    Handler o = new a(this);
    public final b p = new b() {
        public final void a(POI poi, boolean z) {
            if (poi == null) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", "home");
                    if (z) {
                        LogManager.actionLogV2(LogConstant.PAGE_ID_MAIN_REMOTE, "B011", jSONObject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                RemoteControlFragment.a(RemoteControlFragment.this, 2, null, 257, RemoteControlFragment.this.getString(R.string.commute_set_home_hint), false, SelectFor.SAVE_POI, true);
            } else if (z) {
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("type", "home");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.PAGE_ID_MAIN_REMOTE, "B011", jSONObject2);
                RemoteControlFragment.a(RemoteControlFragment.this, 2, poi.getName(), 257, RemoteControlFragment.this.getString(R.string.commute_set_home_hint), false, SelectFor.SAVE_POI, false);
            } else {
                ((deq) RemoteControlFragment.this.mPresenter).a(poi);
            }
        }

        public final void b(POI poi, boolean z) {
            if (poi == null) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", "company");
                    if (z) {
                        LogManager.actionLogV2(LogConstant.PAGE_ID_MAIN_REMOTE, "B011", jSONObject);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                RemoteControlFragment.a(RemoteControlFragment.this, 2, null, Tts.TTS_STATE_INVALID_DATA, RemoteControlFragment.this.getString(R.string.act_fromto_company_input_hint), false, SelectFor.SAVE_POI, true);
            } else if (z) {
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("type", "company");
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                LogManager.actionLogV2(LogConstant.PAGE_ID_MAIN_REMOTE, "B011", jSONObject2);
                RemoteControlFragment.a(RemoteControlFragment.this, 2, poi.getName(), Tts.TTS_STATE_INVALID_DATA, RemoteControlFragment.this.getString(R.string.act_fromto_company_input_hint), false, SelectFor.SAVE_POI, false);
            } else {
                ((deq) RemoteControlFragment.this.mPresenter).a(poi);
            }
        }
    };
    public ProgressDlg q;
    /* access modifiers changed from: private */
    public String r;
    /* access modifiers changed from: private */
    public com.autonavi.common.Callback.a s;

    static class a extends Handler {
        private WeakReference<RemoteControlFragment> a;

        a(RemoteControlFragment remoteControlFragment) {
            this.a = new WeakReference<>(remoteControlFragment);
        }

        public final void handleMessage(Message message) {
            RemoteControlFragment remoteControlFragment = (RemoteControlFragment) this.a.get();
            if (remoteControlFragment != null && remoteControlFragment.isAlive() && message.what == 200) {
                ((deq) remoteControlFragment.mPresenter).a((POI) message.obj);
                remoteControlFragment.d.setEnabled(true);
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.remote_control_fragment);
        requestScreenOrientation(1);
    }

    public final void a(boolean z) {
        if (!z) {
            a(R.drawable.auto_connection_state_icon_disconnection);
        } else if (((RemoteControlModel) ((deq) this.mPresenter).b).e) {
            a(R.drawable.auto_connection_state_icon_wifi);
        } else {
            a(R.drawable.auto_connection_state_icon_bluetooth);
        }
        this.j = z;
    }

    public final void a() {
        this.c.showTmcBar(this);
    }

    public final void b() {
        if (this.q != null) {
            this.q.dismiss();
        }
    }

    public final Map<String, String> c() {
        HashMap hashMap = new HashMap();
        hashMap.put("sessionid", String.valueOf(this.a));
        hashMap.put("source", "amap");
        hashMap.put("request", d().toString());
        return hashMap;
    }

    private JSONObject d() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (this.g != null) {
                jSONObject.put("startPOI", a(this.g));
            }
            jSONObject.put("endPOI", a(this.h));
            jSONObject.put("midPOIs", null);
            jSONObject.put("dev", 0);
            jSONObject.put("method", "");
            jSONObject.put("car_plate", "");
            jSONObject.put("contentoptions", "");
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
        }
        return jSONObject;
    }

    private static JSONObject a(POI poi) throws JSONException {
        if (poi == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, poi.getPoint().getLongitude());
        jSONObject2.put("lat", poi.getPoint().getLatitude());
        jSONObject.put("lonlat", jSONObject2);
        if (!TextUtils.isEmpty(poi.getName())) {
            jSONObject.put("name", poi.getName());
        }
        jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId());
        jSONObject.put("type", poi.getType());
        ArrayList<GeoPoint> entranceList = poi.getEntranceList();
        if (entranceList != null && entranceList.size() > 0) {
            jSONObject.put("entranceList", a(entranceList));
        }
        ArrayList<GeoPoint> exitList = poi.getExitList();
        if (exitList != null && exitList.size() > 0) {
            jSONObject.put("exitList", a(exitList));
        }
        return jSONObject;
    }

    private static JSONArray a(ArrayList<GeoPoint> arrayList) throws JSONException {
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, arrayList.get(i2).getLongitude());
            jSONObject.put("lat", arrayList.get(i2).getLatitude());
            arrayList2.add(jSONObject);
        }
        return new JSONArray(arrayList2);
    }

    public final void a(final String str, final String str2) {
        aho.a(new Runnable() {
            public final void run() {
                if (RemoteControlFragment.this.q == null || RemoteControlFragment.this.q.isShowing()) {
                    RemoteControlFragment.this.b();
                    com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
                    aVar.a((CharSequence) str).b((CharSequence) str2).a((CharSequence) RemoteControlFragment.this.getString(R.string.Ok), (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            RemoteControlFragment.this.d.loadHistory();
                            RemoteControlFragment.this.dismissViewLayer(alertView);
                            RemoteControlFragment.this.l = null;
                        }
                    });
                    aVar.a(true);
                    RemoteControlFragment.this.showViewLayer(aVar.a());
                }
            }
        });
    }

    public final void a(final String str, final String str2, final String str3) {
        aho.a(new Runnable() {
            public final void run() {
                if (RemoteControlFragment.this.q == null || RemoteControlFragment.this.q.isShowing()) {
                    RemoteControlFragment.this.b();
                    com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
                    com.autonavi.widget.ui.AlertView.a a2 = aVar.a((CharSequence) str);
                    StringBuilder sb = new StringBuilder();
                    sb.append(RemoteControlFragment.a(str2));
                    sb.append("\n");
                    sb.append(RemoteControlFragment.b(str3));
                    a2.b((CharSequence) sb.toString()).a((CharSequence) RemoteControlFragment.this.getString(R.string.Ok), (defpackage.ern.a) new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            RemoteControlFragment.this.dismissViewLayer(alertView);
                            RemoteControlFragment.this.l = null;
                            RemoteControlFragment.this.d.loadHistory();
                        }
                    });
                    aVar.a(true);
                    aVar.c = new defpackage.ern.a() {
                        public final void onClick(AlertView alertView, int i) {
                            RemoteControlFragment.this.dismissViewLayer(alertView);
                            RemoteControlFragment.this.l = null;
                            RemoteControlFragment.this.d.loadHistory();
                        }
                    };
                    RemoteControlFragment.this.l = aVar.a();
                    RemoteControlFragment.this.showViewLayer(RemoteControlFragment.this.l);
                }
            }
        });
    }

    public static SpannableString a(String str) {
        if (TextUtils.isEmpty(str)) {
            return new SpannableString("");
        }
        StringBuilder sb = new StringBuilder("从“");
        sb.append(str);
        sb.append("”");
        String sb2 = sb.toString();
        SpannableString spannableString = new SpannableString(sb2);
        spannableString.setSpan(new AbsoluteSizeSpan(13, true), 0, sb2.length(), 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), 0, 2, 33);
        spannableString.setSpan(new ForegroundColorSpan(-16739841), 2, str.length() + 2, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), sb2.length() - 1, sb2.length(), 33);
        return spannableString;
    }

    public static SpannableString b(String str) {
        if (TextUtils.isEmpty(str)) {
            return new SpannableString("");
        }
        StringBuilder sb = new StringBuilder("到“");
        sb.append(str);
        sb.append("”");
        String sb2 = sb.toString();
        SpannableString spannableString = new SpannableString(sb2);
        spannableString.setSpan(new AbsoluteSizeSpan(13, true), 0, sb2.length(), 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), 0, 2, 33);
        spannableString.setSpan(new ForegroundColorSpan(-16739841), 2, str.length() + 2, 33);
        spannableString.setSpan(new ForegroundColorSpan(-13421773), sb2.length() - 1, sb2.length(), 33);
        return spannableString;
    }

    private void a(@DrawableRes int i2) {
        StringBuilder sb = new StringBuilder("    ");
        sb.append(getString(R.string.remote_control));
        sb.append("    ");
        SpannableString spannableString = new SpannableString(sb.toString());
        Drawable drawable = getResources().getDrawable(i2);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        spannableString.setSpan(new ImageSpan(drawable, 1), 14, 15, 17);
        this.k.setTitle(spannableString);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new deq(this);
    }

    static /* synthetic */ void a(RemoteControlFragment remoteControlFragment, int i2, String str, int i3, String str2, boolean z, SelectFor selectFor, boolean z2) {
        GeoPoint geoPoint;
        SelectPoiFromMapBean selectPoiFromMapBean = new SelectPoiFromMapBean();
        if (i3 == 259) {
            POI createPOI = POIFactory.createPOI("我的位置", new GeoPoint());
            if (LocationInstrument.getInstance().getLatestPosition(5) == null) {
                geoPoint = new GeoPoint();
                MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                geoPoint.x = mapSharePreference.getIntValue("X", 221010326);
                if (geoPoint.x == 0) {
                    geoPoint.x = 221010326;
                }
                geoPoint.y = mapSharePreference.getIntValue("Y", 101713397);
                if (geoPoint.y == 0) {
                    geoPoint.y = 101713397;
                }
            } else {
                geoPoint = LocationInstrument.getInstance().getLatestPosition();
            }
            createPOI.setPoint(geoPoint);
            selectPoiFromMapBean.setFromPOI(createPOI);
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("search_for", i2);
        pageBundle.putObject("route_type", RouteType.CAR);
        pageBundle.putString("hint", str2);
        pageBundle.putString(TrafficUtil.KEYWORD, str);
        pageBundle.putBoolean("isHideMyPosition", z);
        pageBundle.putBoolean("auto_search", z2);
        pageBundle.putObject("SelectPoiFromMapBean", selectPoiFromMapBean);
        pageBundle.putObject("selectedfor", selectFor);
        pageBundle.putBoolean("isOffline", remoteControlFragment.b);
        pageBundle.putInt("from_page", 10062);
        String str3 = "0";
        if (i3 == 259) {
            str3 = SuperId.BIT_1_NAVI;
        } else if (i3 == 257 || i3 == 258) {
            str3 = "j";
        }
        pageBundle.putString("SUPER_ID", str3);
        remoteControlFragment.startPageForResult((String) "search.fragment.SearchCallbackFragment", pageBundle, i3);
    }

    static /* synthetic */ void a(RemoteControlFragment remoteControlFragment, final byte[] bArr) {
        final Map<String, String> c2 = remoteControlFragment.c();
        ahn.b().execute(new Runnable() {
            public final void run() {
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
                    byte[] postBytes = iAutoRemoteController.postBytes("/autoservice/accept/navi/route_result", c2, bArr);
                    if (postBytes == null) {
                        RemoteControlFragment remoteControlFragment2 = RemoteControlFragment.this;
                        String string3 = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                        String string4 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                        RemoteControlFragment.this.getString(R.string.drive_confirm);
                        remoteControlFragment2.a(string3, string4);
                        return;
                    }
                    JSONObject jSONObject = new JSONObject(new String(postBytes).trim());
                    String optString = jSONObject.optString("result");
                    if (TextUtils.isEmpty(optString) || !optString.equals("true")) {
                        jSONObject.optString("code");
                        jSONObject.optString("message");
                        RemoteControlFragment remoteControlFragment3 = RemoteControlFragment.this;
                        String string5 = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                        String string6 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                        RemoteControlFragment.this.getString(R.string.drive_confirm);
                        remoteControlFragment3.a(string5, string6);
                        return;
                    }
                    RemoteControlFragment.this.a((String) "已在汽车上为您规划路线", (String) "当前位置", RemoteControlFragment.this.i);
                } catch (JSONException e) {
                    kf.a((Throwable) e);
                    RemoteControlFragment remoteControlFragment4 = RemoteControlFragment.this;
                    String string7 = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                    String string8 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                    RemoteControlFragment.this.getString(R.string.drive_confirm);
                    remoteControlFragment4.a(string7, string8);
                } catch (IOException e2) {
                    kf.a((Throwable) e2);
                    RemoteControlFragment remoteControlFragment5 = RemoteControlFragment.this;
                    String string9 = RemoteControlFragment.this.getString(R.string.drive_send_fail);
                    String string10 = RemoteControlFragment.this.getString(R.string.drive_confirm_and_resend);
                    RemoteControlFragment.this.getString(R.string.drive_confirm);
                    remoteControlFragment5.a(string9, string10);
                }
            }
        });
    }

    static /* synthetic */ void b(RemoteControlFragment remoteControlFragment, String str) {
        bpf bpf = new bpf();
        bpf.setUrl(str);
        box.a();
        box.a((bph) bpf, (bpl<T>) new bpm<ByteResponse>() {
            public final /* synthetic */ void onSuccess(bpk bpk) {
                ByteResponse byteResponse = (ByteResponse) bpk;
                if (byteResponse != null) {
                    RemoteControlFragment.a(RemoteControlFragment.this, (byte[]) byteResponse.getResult());
                }
            }

            public final void onFailure(bph bph, ResponseException responseException) {
                RemoteControlFragment.this.b();
            }
        });
    }
}
