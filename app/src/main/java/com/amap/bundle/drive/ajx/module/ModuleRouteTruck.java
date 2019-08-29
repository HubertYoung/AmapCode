package com.amap.bundle.drive.ajx.module;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.drive.ajx.tools.DriveCarOwnerAjxTools;
import com.amap.bundle.drive.ajx.tools.DriveRouteAjxTools;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPageHost;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.modules.AbstractModule;
import com.autonavi.minimap.ajx3.modules.AjxMethod;
import com.autonavi.minimap.ajx3.modules.AjxModule;
import com.autonavi.minimap.widget.ListDialog;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@KeepClassMembers
@AjxModule("route_truck")
@Keep
public final class ModuleRouteTruck extends AbstractModule {
    private static final String CARINFOMINE = "carInfoMine";
    private static final String CARINFONATIVE = "carInfoNative";
    public static final String MODULE_NAME = "route_truck";
    private static final String NAVINFO = "navInfo";
    public static final String TAG = "ModuleDriveRoute";
    private MyCallback carInfoCallback = new MyCallback(CARINFONATIVE);
    /* access modifiers changed from: private */
    public JsFunctionCallback mCarInfoCallback;
    /* access modifiers changed from: private */
    public ListDialog mFavoriteDialog;
    /* access modifiers changed from: private */
    public IRouteTruckModuleListener mModuleListener;
    /* access modifiers changed from: private */
    public JsFunctionCallback mPreferCallback;
    private MyCallback preferCallback = new MyCallback(NAVINFO);

    public interface IRouteTruckModuleListener {
        boolean startRouteTruckResultPage(String str);
    }

    class MyCallback implements Callback<String> {
        private String type;

        public void error(Throwable th, boolean z) {
        }

        public MyCallback(String str) {
            this.type = str;
        }

        public void callback(String str) {
            try {
                String stringValue = new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue(DriveSpUtil.TRUCK_METHOD, "1");
                if (TextUtils.isEmpty(stringValue)) {
                    stringValue = "1";
                }
                int i = stringValue.contains("2") ? 2 : 0;
                if (stringValue.contains("4")) {
                    i += 4;
                }
                if (stringValue.contains("8")) {
                    i += 8;
                }
                if (stringValue.contains("16")) {
                    i += 16;
                }
                String valueOf = String.valueOf(i);
                if (DriveUtil.getTruckAvoidSwitch()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(valueOf);
                    sb.append("|path");
                    valueOf = sb.toString();
                }
                if (DriveUtil.getTruckAvoidLimitedLoad()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(valueOf);
                    sb2.append("|load");
                    valueOf = sb2.toString();
                }
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject(str);
                jSONObject.put(ModuleRouteTruck.NAVINFO, valueOf);
                jSONObject.put("truckInfo", jSONObject2);
                if (ModuleRouteTruck.NAVINFO.equals(this.type) && ModuleRouteTruck.this.mPreferCallback != null) {
                    ModuleRouteTruck.this.mPreferCallback.callback(jSONObject.toString());
                }
                if (ModuleRouteTruck.CARINFONATIVE.equals(this.type) && ModuleRouteTruck.this.mCarInfoCallback != null) {
                    ModuleRouteTruck.this.mCarInfoCallback.callback(jSONObject.toString());
                }
                StringBuilder sb3 = new StringBuilder("truckInfoCallBack callback（）json=");
                sb3.append(jSONObject.toString());
                tj.b(ModuleRouteTruck.TAG, sb3.toString());
            } catch (Exception unused) {
                if (ModuleRouteTruck.NAVINFO.equals(this.type) && ModuleRouteTruck.this.mPreferCallback != null) {
                    ModuleRouteTruck.this.mPreferCallback.callback(bny.c);
                }
                if (ModuleRouteTruck.CARINFONATIVE.equals(this.type) && ModuleRouteTruck.this.mCarInfoCallback != null) {
                    ModuleRouteTruck.this.mCarInfoCallback.callback(bny.c);
                }
                tj.b(ModuleRouteTruck.TAG, "truckInfoCallBack callback（）json={}");
            }
        }
    }

    public ModuleRouteTruck(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("setSettingInfo")
    public final void setSettingInfo(String str, JsFunctionCallback jsFunctionCallback) {
        if (str.equalsIgnoreCase(NAVINFO)) {
            this.mPreferCallback = jsFunctionCallback;
            DriveRouteAjxTools.startTruckSettingPage();
            return;
        }
        if (str.equalsIgnoreCase(CARINFOMINE)) {
            Intent intent = new Intent();
            intent.setData(Uri.parse("amapuri://carownerservice/homepage"));
            DoNotUseTool.startScheme(intent);
        }
    }

    @AjxMethod("getSettingInfo")
    public final void getSettingInfo(String str, JsFunctionCallback jsFunctionCallback) {
        tj.b(TAG, "getSettingInfo");
        if (str.equalsIgnoreCase(CARINFONATIVE)) {
            this.mCarInfoCallback = jsFunctionCallback;
            DriveCarOwnerAjxTools.getTruckDBData(this.carInfoCallback);
            return;
        }
        if (str.equalsIgnoreCase(NAVINFO)) {
            this.mPreferCallback = jsFunctionCallback;
            DriveCarOwnerAjxTools.getTruckDBData(this.preferCallback);
        }
    }

    @AjxMethod("requestRoute")
    public final void requestRoute(final String str) {
        tj.b(TAG, str);
        aho.a(new Runnable() {
            public void run() {
                bid pageContext = AMapPageUtil.getPageContext();
                IPageHost iPageHost = (pageContext == null || pageContext.getActivity() == null) ? null : (IPageHost) pageContext.getActivity();
                if ((iPageHost == null || !iPageHost.isHostPaused()) && ModuleRouteTruck.this.mModuleListener != null) {
                    ModuleRouteTruck.this.mModuleListener.startRouteTruckResultPage(str);
                }
            }
        });
    }

    public final void setManagerListener(IRouteTruckModuleListener iRouteTruckModuleListener) {
        this.mModuleListener = iRouteTruckModuleListener;
    }

    public final void release() {
        this.mPreferCallback = null;
        this.mCarInfoCallback = null;
        this.preferCallback = null;
        this.carInfoCallback = null;
    }

    @AjxMethod("openAddFavoritePage")
    public final void openAddFavoritePage(JsFunctionCallback jsFunctionCallback) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            Activity activity = pageContext.getActivity();
            if (activity != null) {
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString("search_hint", activity.getResources().getString(R.string.add_fav_poi));
                pageContext.startPageForResult((String) "amap.basemap.action.save_search_page", pageBundle, 240);
            }
        }
    }

    @AjxMethod("openFavoriteListView")
    public final void openFavoriteListView(JsFunctionCallback jsFunctionCallback) {
        onClickFavoritesBtn();
    }

    public final void onClickFavoritesBtn() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            Activity activity = pageContext.getActivity();
            if (activity != null) {
                com com2 = (com) ank.a(com.class);
                cop b = com2 != null ? com2.b(com2.a()) : null;
                if (this.mFavoriteDialog == null) {
                    this.mFavoriteDialog = new ListDialog(activity);
                    this.mFavoriteDialog.setDlgTitle(activity.getResources().getString(R.string.title_save_points));
                }
                if (b == null || b.a() == 0) {
                    this.mFavoriteDialog.setAdapter(new ArrayAdapter(activity, R.layout.v3_list_dialog_empty, new String[]{activity.getResources().getString(R.string.add_fav_poi)}));
                    this.mFavoriteDialog.setOnItemClickListener(null);
                    this.mFavoriteDialog.setComfirmBtnVisibility(8);
                } else {
                    final List<FavoritePOI> e = b.e();
                    this.mFavoriteDialog.setAdapter(new ArrayAdapter(activity, R.layout.v3_list_dialog_item, getSavePointHint(e)));
                    this.mFavoriteDialog.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapterView, View view, final int i, long j) {
                            if (ModuleRouteTruck.this.mFavoriteDialog != null) {
                                ModuleRouteTruck.this.mFavoriteDialog.dismiss();
                                aho.a(new Runnable() {
                                    public void run() {
                                        if (ModuleRouteTruck.this.mModuleListener != null) {
                                            JSONObject jSONObject = new JSONObject();
                                            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
                                            GeoPoint geoPoint = new GeoPoint(latestLocation.getLongitude(), latestLocation.getLatitude());
                                            try {
                                                JSONObject jSONObject2 = new JSONObject();
                                                jSONObject2.put("latitude", String.format(Locale.ENGLISH, "%.6f", new Object[]{Double.valueOf(geoPoint.getLatitude())}));
                                                jSONObject2.put("longitude", String.format(Locale.ENGLISH, "%.6f", new Object[]{Double.valueOf(geoPoint.getLongitude())}));
                                                jSONObject2.put("altitude", latestLocation.getAltitude());
                                                jSONObject2.put(CameraControllerManager.MY_POILOCATION_ACR, (double) latestLocation.getAccuracy());
                                                jSONObject2.put("speed", (double) latestLocation.getSpeed());
                                                jSONObject2.put("timestamp", latestLocation.getTime());
                                                jSONObject2.put("name", "我的位置");
                                                FavoritePOI favoritePOI = (FavoritePOI) e.get(i);
                                                bnx.b(favoritePOI);
                                                JSONObject b = bnx.b(favoritePOI);
                                                jSONObject.put("start_poi", jSONObject2);
                                                jSONObject.put("end_poi", b);
                                                jSONObject.put("type", DriveUtil.NAVI_TYPE_TRUCK);
                                                jSONObject.put("source", "plan_favoritelist");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            ModuleRouteTruck.this.mModuleListener.startRouteTruckResultPage(jSONObject.toString());
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                this.mFavoriteDialog.show();
            }
        }
    }

    private String[] getSavePointHint(List<FavoritePOI> list) {
        int size = list.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            FavoritePOI favoritePOI = list.get(i);
            String customName = favoritePOI.getCustomName();
            if (TextUtils.isEmpty(customName)) {
                customName = favoritePOI.getName();
            }
            strArr[i] = customName;
        }
        return strArr;
    }

    public final void updatePreference() {
        if (this.mPreferCallback != null) {
            DriveCarOwnerAjxTools.getTruckDBData(this.preferCallback);
        }
    }

    public final void updateCarInfo() {
        if (this.mCarInfoCallback != null) {
            DriveCarOwnerAjxTools.getTruckDBData(this.carInfoCallback);
        }
    }
}
