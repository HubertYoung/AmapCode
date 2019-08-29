package com.amap.bundle.drive.setting.quicknaviwidget.accessibility;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import org.json.JSONObject;

public class QuickAutoNaviAccessibilitySettings extends DriveBasePage<qm> implements OnClickListener, OnCheckedChangeListener {
    private View mBluetoothSpeaker;
    private CheckBox mBluetoothSpeakerCb;
    private View mDownloadRealMap;
    private CheckBox mDownloadRealMapCb;
    private int mFrom = 1;
    private View mLightIntensity;
    private CheckBox mLightIntensityCb;
    private View mParkingRecommend;
    private CheckBox mParkingReconmendCb;
    private View mRadarAuto;
    private CheckBox mRadarAutoCb;
    private LinearLayout mReal3DNavigation;
    private CheckBox mReal3DNavigationCb;
    private CheckBox mVoiceControlCb;
    private View mVoiceControlView;

    /* access modifiers changed from: protected */
    public qm createPresenter() {
        return new qm(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navi_settings_accessibility);
    }

    public void init() {
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("amap.extra.prefer.from")) {
            this.mFrom = arguments.getInt("amap.extra.prefer.from");
        }
        View contentView = getContentView();
        TitleBar titleBar = (TitleBar) contentView.findViewById(R.id.title);
        if (titleBar != null) {
            titleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    QuickAutoNaviAccessibilitySettings.this.finish();
                }
            });
        }
        this.mParkingRecommend = contentView.findViewById(R.id.parking_recommend);
        this.mParkingReconmendCb = (CheckBox) contentView.findViewById(R.id.parking_recommend_checkbox);
        CheckBox checkBox = this.mParkingReconmendCb;
        Context context = getContext();
        DriveSpUtil.removeKey(context, "ParkingRecommend");
        checkBox.setChecked(DriveSpUtil.getBool(context, DriveSpUtil.PARKING_RECOMMEND, false));
        this.mParkingRecommend.setOnClickListener(this);
        this.mParkingReconmendCb.setOnCheckedChangeListener(this);
        this.mLightIntensity = getContentView().findViewById(R.id.light_intensity);
        this.mLightIntensityCb = (CheckBox) getContentView().findViewById(R.id.light_intensity_checkbox);
        this.mDownloadRealMap = contentView.findViewById(R.id.intersection_of_real_map_layout);
        this.mDownloadRealMapCb = (CheckBox) contentView.findViewById(R.id.chk_download_real_map);
        if (this.mFrom == 4) {
            this.mDownloadRealMapCb.setChecked(re.i() == 1);
        } else {
            this.mDownloadRealMapCb.setChecked(DriveSpUtil.getBool(getContext(), DriveSpUtil.DOWNLOAD_INTERSECTION_OF_REAL_MAP, true));
        }
        this.mDownloadRealMap.setOnClickListener(this);
        this.mDownloadRealMapCb.setOnCheckedChangeListener(this);
        this.mReal3DNavigation = (LinearLayout) contentView.findViewById(R.id.real_3d_navigation);
        this.mReal3DNavigationCb = (CheckBox) contentView.findViewById(R.id.chk_real_3d_navigation);
        if (this.mFrom == 4) {
            this.mReal3DNavigationCb.setChecked(re.j() == 1);
        } else {
            this.mReal3DNavigationCb.setChecked(DriveSpUtil.getBool(getContext(), DriveSpUtil.REAL_3D_NAVIGATION, true));
        }
        this.mReal3DNavigation.setOnClickListener(this);
        this.mReal3DNavigationCb.setOnCheckedChangeListener(this);
        ku a = ku.a();
        StringBuilder sb = new StringBuilder("DriveOfflineManager.getInstance().hasOfflineData()=");
        sb.append(oh.a().d());
        a.c("DriveOfflineManager", sb.toString());
        if (oh.a().d()) {
            this.mReal3DNavigation.setVisibility(0);
        } else {
            this.mReal3DNavigation.setVisibility(8);
        }
        this.mVoiceControlView = contentView.findViewById(R.id.voice_control_layout);
        this.mVoiceControlCb = (CheckBox) contentView.findViewById(R.id.chk_voice_control);
        CheckBox checkBox2 = this.mVoiceControlCb;
        getContext();
        checkBox2.setChecked(false);
        this.mVoiceControlView.setOnClickListener(this);
        this.mVoiceControlCb.setOnCheckedChangeListener(this);
        this.mBluetoothSpeaker = contentView.findViewById(R.id.speaker_play_layout);
        this.mBluetoothSpeakerCb = (CheckBox) contentView.findViewById(R.id.chk_bluetooth_speaker_play);
        this.mBluetoothSpeakerCb.setChecked(!re.a((String) "speaker_paly_sound", false));
        this.mBluetoothSpeaker.setOnClickListener(this);
        this.mBluetoothSpeakerCb.setOnCheckedChangeListener(this);
        ((LinearLayout) contentView.findViewById(R.id.speaker_line)).setVisibility(8);
        this.mBluetoothSpeaker.setVisibility(8);
        this.mRadarAuto = contentView.findViewById(R.id.navigation_radar_auto_enter);
        this.mRadarAutoCb = (CheckBox) contentView.findViewById(R.id.chk_navigation_radar_auto_enter);
        this.mRadarAutoCb.setChecked(re.a((String) "radar_auto_enter", true));
        this.mRadarAuto.setOnClickListener(this);
        this.mRadarAutoCb.setOnCheckedChangeListener(this);
        if (this.mFrom == 1) {
            this.mRadarAuto.setVisibility(0);
            contentView.findViewById(R.id.navigation_radar_auto_enter_divider).setVisibility(0);
            return;
        }
        this.mRadarAuto.setVisibility(8);
        contentView.findViewById(R.id.navigation_radar_auto_enter_divider).setVisibility(8);
    }

    public void onClick(View view) {
        if (this.mParkingRecommend == view) {
            this.mParkingReconmendCb.toggle();
        } else if (this.mDownloadRealMap == view) {
            this.mDownloadRealMapCb.toggle();
        } else if (this.mLightIntensity == view) {
            this.mLightIntensityCb.toggle();
        } else if (this.mVoiceControlView == view) {
            this.mVoiceControlCb.toggle();
        } else if (this.mReal3DNavigation == view) {
            this.mReal3DNavigationCb.toggle();
        } else if (this.mRadarAuto == view) {
            this.mRadarAutoCb.toggle();
        } else {
            if (this.mBluetoothSpeaker == view) {
                if (hasCall()) {
                    ToastHelper.showToast(getContext().getString(R.string.speaker_using_later));
                } else if (!this.mBluetoothSpeakerCb.isChecked() || checkAudioFunceion()) {
                    this.mBluetoothSpeakerCb.toggle();
                } else {
                    ToastHelper.showToast(getContext().getString(R.string.speaker_canot_use));
                }
            }
        }
    }

    private boolean checkAudioFunceion() {
        if (getContext() == null) {
            return false;
        }
        AudioManager audioManager = (AudioManager) getContext().getSystemService("audio");
        int mode = audioManager.getMode();
        int i = 3;
        if (mode == 3) {
            i = 0;
        }
        audioManager.setMode(i);
        if (mode == audioManager.getMode()) {
            return false;
        }
        audioManager.setMode(mode);
        return true;
    }

    private boolean hasCall() {
        return getContext() == null || ((TelephonyManager) getContext().getSystemService("phone")).getCallState() != 0;
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mParkingReconmendCb == compoundButton) {
            Context context = getContext();
            DriveSpUtil.removeKey(context, "ParkingRecommend");
            DriveSpUtil.setBool(context, DriveSpUtil.PARKING_RECOMMEND, z);
            LogUtil.actionLogV2("P00184", "B012", LogUtil.createJSONObj(LogUtil.switchActionLogInfo(z)));
        } else if (this.mLightIntensityCb == compoundButton) {
            if (this.mFrom == 4) {
                ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_AUXILIARY_LOWBRIGHT_KEY, String.valueOf(z));
            } else {
                re.d(getContext(), z);
            }
        } else if (this.mDownloadRealMapCb == compoundButton) {
            if (this.mFrom == 4) {
                ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_AUXILIARY_REALMAP_DOWNLOAD_KEY, String.valueOf(z));
            } else {
                re.f(getContext(), z);
            }
            NaviManager.a().a(52, z ? "1" : "0");
            LogUtil.actionLogV2("P00184", "B007", LogUtil.createJSONObj(LogUtil.switchActionLogInfo(z)));
        } else if (this.mVoiceControlCb == compoundButton) {
            DriveSpUtil.setBool(getContext(), DriveSpUtil.NAVIGATION_VOICE_CONTROL, z);
        } else if (this.mReal3DNavigationCb == compoundButton) {
            if (this.mFrom == 4) {
                ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_AUXILIARY_3DREALMAP_KEY, String.valueOf(z ? 1 : 0));
            } else {
                re.g(getContext(), z);
            }
            LogUtil.actionLogV2("P00184", "B013", LogUtil.createJSONObj(LogUtil.switchActionLogInfo(z)));
        } else if (this.mBluetoothSpeakerCb == compoundButton) {
            re.b((String) "speaker_paly_sound", !z);
            actionLogV2("B011", LogUtil.createJSONObj(LogUtil.switchActionLogInfo(z)));
        } else {
            if (this.mRadarAutoCb == compoundButton) {
                re.b((String) "radar_auto_enter", z);
            }
        }
    }

    private void actionLogV2(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        LogUtil.actionLogV2("P00184", str, jSONObject);
    }

    public void setViewData() {
        boolean z = false;
        if (this.mFrom == 4) {
            CheckBox checkBox = this.mLightIntensityCb;
            if (re.g() == 1) {
                z = true;
            }
            checkBox.setChecked(z);
        } else {
            this.mLightIntensityCb.setChecked(DriveSpUtil.getBool(getContext(), DriveSpUtil.LIGHT_INTENSITY, false));
        }
        this.mLightIntensity.setOnClickListener(this);
        this.mLightIntensityCb.setOnCheckedChangeListener(this);
    }
}
