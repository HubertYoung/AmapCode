package com.amap.bundle.drive.setting.quicknaviwidget.broadcast;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.TripSpUtil;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import com.iflytek.tts.TtsService.PlaySoundUtils;

public class QuickAutoNaviBroadcastSettings extends DriveBasePage<qo> implements OnClickListener, OnCheckedChangeListener {
    private View mBackButton;
    private View mCallingSpeakTTS;
    private CheckBox mCallingSpeakTTSCb;
    /* access modifiers changed from: private */
    public TextView mDetailedReport;
    private View mEdogSwitch;
    private CheckBox mEdogSwitchCb;
    private int mFrom = 1;
    private View mPlayRouteTrafficSwitch;
    private CheckBox mPlayRouteTrafficSwitchCb;
    /* access modifiers changed from: private */
    public TextView mSimplifiedReport;
    private TextView mTTSMixedMusicCb;
    private TextView mTTSMixedMusicPrompt;
    private TextView mTTSPauseMusicCb;
    private View mVolumeGainControl;
    private CheckBox mVolumeGainControlCb;

    /* access modifiers changed from: protected */
    public qo createPresenter() {
        return new qo(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navi_settings_broadcast);
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
                    QuickAutoNaviBroadcastSettings.this.finish();
                }
            });
        }
        this.mDetailedReport = (TextView) contentView.findViewById(R.id.detailed_report);
        this.mSimplifiedReport = (TextView) contentView.findViewById(R.id.simplified_report);
        int i = DriveSpUtil.getInt(getContext(), DriveSpUtil.BROADCAST_MODE, 2);
        boolean z = false;
        if (!TripSpUtil.getTripBroadCastState(getContext())) {
            this.mSimplifiedReport.setSelected(false);
            this.mDetailedReport.setSelected(false);
        } else if (i == 2) {
            this.mDetailedReport.setSelected(true);
            this.mSimplifiedReport.setSelected(false);
        } else if (i == 1) {
            this.mSimplifiedReport.setSelected(true);
            this.mDetailedReport.setSelected(false);
        }
        NoDBClickUtil.a((View) this.mDetailedReport, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (!QuickAutoNaviBroadcastSettings.this.mDetailedReport.isSelected()) {
                    QuickAutoNaviBroadcastSettings.this.mDetailedReport.setSelected(true);
                    QuickAutoNaviBroadcastSettings.this.mSimplifiedReport.setSelected(false);
                    TripSpUtil.setTripBroadCastState(QuickAutoNaviBroadcastSettings.this.getContext(), true);
                    re.a(QuickAutoNaviBroadcastSettings.this.getContext(), 2);
                    QuickAutoNaviBroadcastSettings.this.setPlayStyle();
                }
            }
        });
        NoDBClickUtil.a((View) this.mSimplifiedReport, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (!QuickAutoNaviBroadcastSettings.this.mSimplifiedReport.isSelected()) {
                    QuickAutoNaviBroadcastSettings.this.mSimplifiedReport.setSelected(true);
                    QuickAutoNaviBroadcastSettings.this.mDetailedReport.setSelected(false);
                    TripSpUtil.setTripBroadCastState(QuickAutoNaviBroadcastSettings.this.getContext(), true);
                    re.a(QuickAutoNaviBroadcastSettings.this.getContext(), 1);
                    QuickAutoNaviBroadcastSettings.this.setPlayStyle();
                }
            }
        });
        this.mEdogSwitch = contentView.findViewById(R.id.edog_play_switch);
        this.mEdogSwitchCb = (CheckBox) this.mEdogSwitch.findViewById(R.id.edog_play_switch_checkbox);
        if (this.mFrom == 4) {
            this.mEdogSwitchCb.setChecked(re.e() == 1);
        } else {
            this.mEdogSwitchCb.setChecked(DriveSpUtil.getBool(getContext(), DriveSpUtil.PLAY_ELE_EYE, true));
        }
        this.mEdogSwitch.setOnClickListener(this);
        this.mEdogSwitchCb.setOnCheckedChangeListener(this);
        this.mPlayRouteTrafficSwitch = contentView.findViewById(R.id.play_route_traffic);
        this.mPlayRouteTrafficSwitchCb = (CheckBox) this.mPlayRouteTrafficSwitch.findViewById(R.id.play_route_traffic_checkbox);
        if (this.mFrom == 4) {
            this.mPlayRouteTrafficSwitchCb.setChecked(re.f() == 1);
        } else {
            this.mPlayRouteTrafficSwitchCb.setChecked(DriveSpUtil.getBool(getContext(), DriveSpUtil.PLAY_ROUTE_TRAFFIC, true));
        }
        this.mPlayRouteTrafficSwitch.setOnClickListener(this);
        this.mPlayRouteTrafficSwitchCb.setOnCheckedChangeListener(this);
        this.mVolumeGainControl = contentView.findViewById(R.id.navigation_volume_gain_control);
        this.mVolumeGainControlCb = (CheckBox) contentView.findViewById(R.id.chk_navigation_volume_control);
        if (this.mFrom == 4) {
            this.mVolumeGainControlCb.setChecked(re.n() == 1);
        } else {
            this.mVolumeGainControlCb.setChecked(DriveSpUtil.getBool(getContext(), DriveSpUtil.NAVIGATION_VOLUME_GAIN_SWITCH, false));
        }
        this.mVolumeGainControl.setOnClickListener(this);
        this.mVolumeGainControlCb.setOnCheckedChangeListener(this);
        this.mCallingSpeakTTS = contentView.findViewById(R.id.calling_speak_tts_layout);
        this.mCallingSpeakTTSCb = (CheckBox) contentView.findViewById(R.id.chk_calling_tts_speak);
        if (this.mFrom == 4) {
            this.mCallingSpeakTTSCb.setChecked(re.m() == 1);
        } else {
            this.mCallingSpeakTTSCb.setChecked(DriveSpUtil.getBool(getContext(), DriveSpUtil.CALLING_SPEAK_TTS, false));
        }
        this.mCallingSpeakTTS.setOnClickListener(this);
        this.mCallingSpeakTTSCb.setOnCheckedChangeListener(this);
        boolean z2 = this.mFrom == 4 ? re.k() == 1 : re.l();
        this.mTTSMixedMusicCb = (TextView) contentView.findViewById(R.id.chk_tts_mixed_music);
        this.mTTSMixedMusicCb.setSelected(z2);
        this.mTTSMixedMusicCb.setOnClickListener(this);
        this.mTTSPauseMusicCb = (TextView) contentView.findViewById(R.id.chk_tts_pause_music);
        TextView textView = this.mTTSPauseMusicCb;
        if (!z2) {
            z = true;
        }
        textView.setSelected(z);
        this.mTTSPauseMusicCb.setOnClickListener(this);
        this.mTTSMixedMusicPrompt = (TextView) contentView.findViewById(R.id.tts_mixed_music_prompt);
        this.mTTSMixedMusicPrompt.setText(getContext().getText(z2 ? R.string.tts_mixed_music_prompt : R.string.tts_pause_music_prompt));
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mEdogSwitchCb == compoundButton) {
            if (this.mFrom == 4) {
                ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_BOARDCAST_CAMERA_KEY, String.valueOf(z));
            } else {
                re.b(getContext(), z);
            }
        } else if (this.mPlayRouteTrafficSwitchCb == compoundButton) {
            if (this.mFrom == 4) {
                ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_BOARDCAST_ROADINFO_KEY, String.valueOf(z));
            } else {
                re.c(getContext(), z);
            }
        } else if (this.mCallingSpeakTTSCb != compoundButton) {
            if (this.mVolumeGainControlCb == compoundButton) {
                if (this.mFrom == 4) {
                    ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_BOARDCAST_IMPROVE_VOICE_KEY, String.valueOf(z ? 1 : 0));
                    return;
                }
                re.i(getContext(), z);
            }
        } else if (this.mFrom == 4) {
            ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_BOARDCAST_INCALLING_KEY, String.valueOf(z));
        } else {
            re.h(getContext(), z);
        }
    }

    private void setTTSMixedMusic(boolean z) {
        this.mTTSMixedMusicCb.setSelected(z);
        this.mTTSPauseMusicCb.setSelected(!z);
        this.mTTSMixedMusicPrompt.setText(getContext().getText(z ? R.string.tts_mixed_music_prompt : R.string.tts_pause_music_prompt));
        if (this.mFrom == 4) {
            ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_TTS_MIXED_MUSIC_KEY, String.valueOf(z ? 1 : 0));
        } else {
            re.a(z);
        }
        PlaySoundUtils.getInstance().setTTSMixedMusic(z);
    }

    public void onClick(View view) {
        if (this.mEdogSwitch == view) {
            this.mEdogSwitchCb.toggle();
        } else if (this.mPlayRouteTrafficSwitch == view) {
            this.mPlayRouteTrafficSwitchCb.toggle();
        } else if (this.mCallingSpeakTTS == view) {
            this.mCallingSpeakTTSCb.toggle();
        } else if (this.mVolumeGainControl == view) {
            this.mVolumeGainControlCb.toggle();
        } else if (this.mTTSMixedMusicCb == view) {
            setTTSMixedMusic(true);
        } else {
            if (this.mTTSPauseMusicCb == view) {
                setTTSMixedMusic(false);
            }
        }
    }

    /* access modifiers changed from: private */
    public void setPlayStyle() {
        NaviManager.a().a(22, String.valueOf(DriveSpUtil.getInt(getContext(), DriveSpUtil.BROADCAST_MODE, 2)));
        NaviManager.a().c(12, String.valueOf(DriveSpUtil.getInt(getContext(), DriveSpUtil.BROADCAST_MODE, 2)));
    }
}
