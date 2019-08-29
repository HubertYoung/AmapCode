package com.autonavi.mine.page.setting.sysmapset.page;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.support.v4.internal.view.SupportMenu;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import com.autonavi.widget.ui.AmapSwitch;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.basemap.action.sys_map_setting_page")
public class SysMapSettingPage extends AbstractBasePage<cgt> implements OnClickListener, OnCheckedChangeListener, LocationNone {
    private TextView blind_mode_title_tv;
    private AmapSwitch checkBoxPoiListShowPic;
    private boolean mAbPageSwitchState;
    private ImageView mBlindBadgeView;
    private LinearLayout mBlindModeDemoLayout;
    private RelativeLayout mBlindModeLayout;
    private AmapSwitch mCheckBoxBlindModeStatus;
    private AmapSwitch mCheckBoxIndoorMap;
    private AmapSwitch mCheckBoxLockRotation;
    private AmapSwitch mCheckBoxMapRoadStatus;
    private AmapSwitch mCheckBoxNewMainMap;
    private AmapSwitch mCheckBoxScreen;
    private AmapSwitch mCheckBoxShowZoomBtn;
    private AmapSwitch mCheckBoxWifiOffineMapData;
    private IViewLayer mClearFrequentLocationAlertView;
    private View mIndoorMapView;
    private RelativeLayout mLockRotation;
    private RelativeLayout mMapRoadStatus;
    private MapSharePreference mMapSp = new MapSharePreference(SharePreferenceName.SharedPreferences);
    private RelativeLayout mMapTextSetEntryRL;
    private RelativeLayout mNewMainMap;
    private View mNewMainMapLine;
    private RelativeLayout mScreen;
    private View mShowZoomBtnView;
    private AmapSwitch mSwitchFrequentLocationBtn;
    private TitleBar mTitleBar;
    private RelativeLayout mWifiOfflineMapDataSwitch;
    private RelativeLayout poiListShowPic;

    /* access modifiers changed from: protected */
    public cgt createPresenter() {
        return new cgt(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.mAbPageSwitchState = new bnv().a();
        setContentView(R.layout.setting_map_fragment);
        View contentView = getContentView();
        initView(contentView);
        initViewState();
        if (!this.mAbPageSwitchState) {
            initFrequentLocationView(contentView);
        }
    }

    private void initView(View view) {
        this.mNewMainMap = (RelativeLayout) view.findViewById(R.id.new_main_map);
        this.mNewMainMapLine = view.findViewById(R.id.new_main_map_line);
        this.mLockRotation = (RelativeLayout) view.findViewById(R.id.lock_rotation);
        this.mScreen = (RelativeLayout) view.findViewById(R.id.screen_on);
        this.mWifiOfflineMapDataSwitch = (RelativeLayout) view.findViewById(R.id.auto_download_offline);
        this.poiListShowPic = (RelativeLayout) view.findViewById(R.id.poi_list_show_pic_setting);
        this.mMapRoadStatus = (RelativeLayout) view.findViewById(R.id.map_road_status);
        this.mMapTextSetEntryRL = (RelativeLayout) view.findViewById(R.id.map_text_set_entry_rl);
        this.mIndoorMapView = view.findViewById(R.id.view_indoor_map);
        this.mShowZoomBtnView = view.findViewById(R.id.view_show_zoom_btn);
        this.mMapTextSetEntryRL.setOnClickListener(this);
        this.mCheckBoxNewMainMap = (AmapSwitch) view.findViewById(R.id.check_new_main_map);
        this.mCheckBoxLockRotation = (AmapSwitch) view.findViewById(R.id.check_lock_rotation);
        this.mCheckBoxScreen = (AmapSwitch) view.findViewById(R.id.check_screen_on);
        this.mCheckBoxWifiOffineMapData = (AmapSwitch) view.findViewById(R.id.check_auto_download_offline);
        this.checkBoxPoiListShowPic = (AmapSwitch) view.findViewById(R.id.check_poi_list_show_pic);
        this.mCheckBoxMapRoadStatus = (AmapSwitch) view.findViewById(R.id.check_map_road_status);
        this.mCheckBoxIndoorMap = (AmapSwitch) view.findViewById(R.id.check_indoor_map);
        this.mCheckBoxShowZoomBtn = (AmapSwitch) view.findViewById(R.id.check_show_zoom_btn);
        this.mTitleBar = (TitleBar) view.findViewById(R.id.title);
        this.mTitleBar.setTitle(getString(R.string.map_setting));
        this.mTitleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                SysMapSettingPage.this.finish();
            }
        });
        this.mBlindModeLayout = (RelativeLayout) view.findViewById(R.id.blind_mode_on_layout);
        this.mBlindModeDemoLayout = (LinearLayout) view.findViewById(R.id.blindMode_demo_layout);
        this.mCheckBoxBlindModeStatus = (AmapSwitch) view.findViewById(R.id.check_blind_mode_status);
        this.blind_mode_title_tv = (TextView) view.findViewById(R.id.blind_mode_title_tv);
        this.mBlindBadgeView = (ImageView) view.findViewById(R.id.blind_mode_isnew);
        this.mLockRotation.setOnClickListener(this);
        this.mWifiOfflineMapDataSwitch.setOnClickListener(this);
        this.mNewMainMap.setOnClickListener(this);
        this.mScreen.setOnClickListener(this);
        this.poiListShowPic.setOnClickListener(this);
        this.mMapRoadStatus.setOnClickListener(this);
        this.mBlindModeLayout.setOnClickListener(this);
        this.mIndoorMapView.setOnClickListener(this);
        this.mShowZoomBtnView.setOnClickListener(this);
    }

    private void initSwitchs() {
        this.mCheckBoxLockRotation.setChecked(bim.aa().k((String) "201"));
        this.mCheckBoxScreen.setChecked(bim.aa().k((String) "202"));
        this.mCheckBoxWifiOffineMapData.setChecked(bim.aa().k((String) UploadConstants.STATUS_NET_NOT_MATCH));
        this.checkBoxPoiListShowPic.setChecked(this.mMapSp.getBooleanValue("poi_list_show_pic", false));
        this.mCheckBoxMapRoadStatus.setChecked(bim.aa().k((String) UploadConstants.STATUS_FILE_UPLOADING_RETRY));
        updateBlindModeLayout(this.mMapSp.getBooleanValue("blind_mode_status", false));
        this.mCheckBoxIndoorMap.setChecked(brv.a());
        this.mCheckBoxShowZoomBtn.setChecked(bim.aa().k((String) UploadConstants.STATUS_TASK_BY_PUSH));
        this.mCheckBoxNewMainMap.setOnCheckedChangeListener(this);
        this.mCheckBoxLockRotation.setOnCheckedChangeListener(this);
        this.mCheckBoxScreen.setOnCheckedChangeListener(this);
        this.mCheckBoxWifiOffineMapData.setOnCheckedChangeListener(this);
        this.checkBoxPoiListShowPic.setOnCheckedChangeListener(this);
        this.mCheckBoxMapRoadStatus.setOnCheckedChangeListener(this);
        this.mCheckBoxBlindModeStatus.setOnCheckedChangeListener(this);
        this.mCheckBoxIndoorMap.setOnCheckedChangeListener(this);
        this.mCheckBoxShowZoomBtn.setOnCheckedChangeListener(this);
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton == this.mCheckBoxLockRotation) {
            lockRotationClick();
        } else if (compoundButton == this.mCheckBoxScreen) {
            if (this.mCheckBoxScreen.isChecked()) {
                getActivity().getWindow().addFlags(128);
            } else {
                getActivity().getWindow().clearFlags(128);
            }
            if (this.mCheckBoxScreen.isChecked()) {
                bim.aa().a((String) "202", 1);
            } else {
                bim.aa().a((String) "202", 0);
            }
        } else if (compoundButton == this.mCheckBoxWifiOffineMapData) {
            autoDownloadOfflineMapDataClick();
        } else if (compoundButton == this.checkBoxPoiListShowPic) {
            poiListShowPicSettingClick();
        } else if (compoundButton == this.mCheckBoxMapRoadStatus) {
            if (this.mCheckBoxMapRoadStatus.isChecked()) {
                bim.aa().a((String) UploadConstants.STATUS_FILE_UPLOADING_RETRY, 1);
            } else {
                bim.aa().a((String) UploadConstants.STATUS_FILE_UPLOADING_RETRY, 0);
            }
        } else if (compoundButton == this.mCheckBoxBlindModeStatus) {
            onBlindModeClick();
        } else if (compoundButton.getId() == R.id.switch_frequent_location_btn) {
            bia bia = (bia) a.a.a(bia.class);
            if (bia != null) {
                bia.a(z);
            }
        } else if (compoundButton == this.mCheckBoxIndoorMap) {
            onIndoorMapSwitchClicked();
        } else {
            if (compoundButton == this.mCheckBoxShowZoomBtn) {
                onShowZoomClicked();
            }
        }
    }

    public void refreshUI_onPause() {
        bim.aa().a((biw) null);
        Editor edit = new MapSharePreference((String) "SharedPreferences").sharedPrefs().edit();
        edit.putBoolean("poi_list_show_pic", this.checkBoxPoiListShowPic.isChecked());
        edit.apply();
        if (DoNotUseTool.getMapView().J() <= 0.0f) {
            if (this.mCheckBoxLockRotation.isChecked()) {
                DoNotUseTool.getMapView().e(false);
                return;
            }
            DoNotUseTool.getMapView().B();
        }
    }

    public void onClick(View view) {
        if (view == this.mMapRoadStatus) {
            this.mCheckBoxMapRoadStatus.toggle();
        } else if (view == this.mScreen) {
            this.mCheckBoxScreen.toggle();
        } else if (view.equals(this.mNewMainMap)) {
            this.mCheckBoxNewMainMap.toggle();
        } else if (view == this.mMapTextSetEntryRL) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", Ajx3Path.SETTING_FONT_SIZE_PATH);
            startPage(Ajx3Page.class, pageBundle);
        } else if (view == this.mBlindModeLayout) {
            this.mCheckBoxBlindModeStatus.toggle();
        } else if (view == this.mLockRotation) {
            this.mCheckBoxLockRotation.toggle();
        } else if (view == this.mWifiOfflineMapDataSwitch) {
            this.mCheckBoxWifiOffineMapData.toggle();
        } else if (view == this.poiListShowPic) {
            this.checkBoxPoiListShowPic.toggle();
        } else if (view.getId() == R.id.clear_frequent_location_btn) {
            showClearFrequentLocationAlert();
        } else if (view == this.mIndoorMapView) {
            this.mCheckBoxIndoorMap.toggle();
        } else if (view == this.mShowZoomBtnView) {
            this.mCheckBoxShowZoomBtn.toggle();
        } else {
            if (view.getId() == R.id.switch_frequent_location_root_view) {
                this.mSwitchFrequentLocationBtn.toggle();
            }
        }
    }

    private void showClearFrequentLocationAlert() {
        if (this.mClearFrequentLocationAlertView != null) {
            showViewLayer(this.mClearFrequentLocationAlertView);
            return;
        }
        SpannableString spannableString = new SpannableString(getString(R.string.clear_frequent_location_data_layer_position));
        spannableString.setSpan(new ForegroundColorSpan(SupportMenu.CATEGORY_MASK), 0, spannableString.length(), 33);
        this.mClearFrequentLocationAlertView = new a(getContext()).a(R.string.clear_frequent_location_data_layer_tips).a((CharSequence) spannableString, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                SysMapSettingPage.this.dismissViewLayer(alertView);
                cgt cgt = (cgt) SysMapSettingPage.this.mPresenter;
                bia bia = (bia) a.a.a(bia.class);
                boolean z = true;
                int a2 = bia != null ? bia.a() : 1;
                int removeFrequentAddress = bim.aa().n().removeFrequentAddress();
                if (!(a2 == 0 && removeFrequentAddress == 0)) {
                    z = false;
                }
                if (z) {
                    ToastHelper.showToast(((SysMapSettingPage) cgt.mPage).getString(R.string.clear_frequent_location_success_tips));
                } else {
                    ToastHelper.showToast(((SysMapSettingPage) cgt.mPage).getString(R.string.clear_frequent_location_failure_tips));
                }
            }
        }).b(R.string.clear_frequent_location_data_layer_negative, (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                SysMapSettingPage.this.dismissViewLayer(alertView);
            }
        }).a(false).a();
        showViewLayer(this.mClearFrequentLocationAlertView);
    }

    public boolean isClearFrequentLocationShown() {
        if (this.mClearFrequentLocationAlertView == null) {
            return false;
        }
        View view = this.mClearFrequentLocationAlertView.getView();
        if (view == null || !view.isShown()) {
            return false;
        }
        return true;
    }

    private void initViewState() {
        lt.a().c();
        this.mNewMainMap.setVisibility(8);
        this.mNewMainMapLine.setVisibility(8);
        initSwitchs();
        updateBadgeView();
    }

    private void updateBadgeView() {
        this.mBlindBadgeView.setVisibility(this.mMapSp.getBooleanValue("blind_mode_isnew", true) ? 0 : 8);
    }

    private void updateBlindModeLayout(boolean z) {
        this.mCheckBoxBlindModeStatus.setChecked(z);
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            mapView.t(z);
            this.mBlindModeDemoLayout.setVisibility(z ? 0 : 8);
        }
    }

    private JSONObject lockRotationClick() {
        if (this.mCheckBoxLockRotation.isChecked()) {
            bim.aa().a((String) "201", 1);
        } else {
            bim.aa().a((String) "201", 0);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", this.mCheckBoxLockRotation.isChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private JSONObject autoDownloadOfflineMapDataClick() {
        if (this.mCheckBoxWifiOffineMapData.isChecked()) {
            bim.aa().a((String) UploadConstants.STATUS_NET_NOT_MATCH, 1);
        } else {
            bim.aa().a((String) UploadConstants.STATUS_NET_NOT_MATCH, 0);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", this.mCheckBoxWifiOffineMapData.isChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private void onBlindModeClick() {
        this.mMapSp.putBooleanValue("blind_mode_isnew", false);
        updateBadgeView();
        boolean isChecked = this.mCheckBoxBlindModeStatus.isChecked();
        updateBlindModeLayout(isChecked);
        this.mMapSp.putBooleanValue("blind_mode_status", isChecked);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(TrafficUtil.KEYWORD, isChecked ? 1 : 0);
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, getAdCode());
        } catch (Exception e) {
            kf.a((Throwable) e);
        }
        LogManager.actionLogV2("P00219", "B007", jSONObject);
    }

    public void onIndoorMapSwitchClicked() {
        boolean isChecked = this.mCheckBoxIndoorMap.isChecked();
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("indoor_map_switch", isChecked);
        DoNotUseTool.getMapManager().getMapView().s(isChecked);
    }

    public void onShowZoomClicked() {
        if (this.mCheckBoxShowZoomBtn.isChecked()) {
            bim.aa().a((String) UploadConstants.STATUS_TASK_BY_PUSH, 1);
        } else {
            bim.aa().a((String) UploadConstants.STATUS_TASK_BY_PUSH, 0);
        }
        cdd.n().h();
    }

    private JSONObject poiListShowPicSettingClick() {
        return new JSONObject();
    }

    public void updateUIWithCloudData() {
        boolean k = bim.aa().k((String) "201");
        boolean k2 = bim.aa().k((String) "202");
        boolean k3 = bim.aa().k((String) UploadConstants.STATUS_NET_NOT_MATCH);
        boolean k4 = bim.aa().k((String) UploadConstants.STATUS_FILE_UPLOADING_RETRY);
        bim.aa().k((String) UploadConstants.STATUS_TASK_BY_CONFIG);
        boolean k5 = bim.aa().k((String) UploadConstants.STATUS_TASK_BY_PUSH);
        this.mCheckBoxLockRotation.setChecked(k);
        this.mCheckBoxScreen.setChecked(k2);
        this.mCheckBoxWifiOffineMapData.setChecked(k3);
        this.mCheckBoxMapRoadStatus.setChecked(k4);
        this.mCheckBoxShowZoomBtn.setChecked(k5);
    }

    private String getAdCode() {
        String str = "";
        bty mapView = DoNotUseTool.getMapView();
        if (!(mapView == null || mapView.n() == null)) {
            str = String.valueOf(GeoPoint.glGeoPoint2GeoPoint(mapView.n()).getAdCode());
        }
        return TextUtils.isEmpty(str) ? "" : str;
    }

    private void initFrequentLocationView(View view) {
        bia bia = (bia) a.a.a(bia.class);
        if (bia != null && bia.c()) {
            ((ViewStub) view.findViewById(R.id.item_frequent_location_layout_view_stub)).inflate();
            view.findViewById(R.id.clear_frequent_location_btn).setOnClickListener(this);
            this.mSwitchFrequentLocationBtn = (AmapSwitch) view.findViewById(R.id.switch_frequent_location_btn);
            this.mSwitchFrequentLocationBtn.setChecked(bia.d());
            this.mSwitchFrequentLocationBtn.setOnCheckedChangeListener(this);
            view.findViewById(R.id.switch_frequent_location_root_view).setOnClickListener(this);
        }
    }
}
