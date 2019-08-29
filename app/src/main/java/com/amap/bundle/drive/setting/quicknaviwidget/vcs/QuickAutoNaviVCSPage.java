package com.amap.bundle.drive.setting.quicknaviwidget.vcs;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.amap.bundle.drive.setting.quicknaviwidget.helpcenter.QuickAutoNaviHelpCenterPage;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.statistics.util.LogUtil;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;

public class QuickAutoNaviVCSPage extends DriveBasePage<qy> implements OnClickListener, OnCheckedChangeListener {
    private CheckBox mVoiceControlSwitch;
    private View mVoiceControlView;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navi_settings_vcs);
    }

    public void onClick(View view) {
        if (this.mVoiceControlView == view) {
            this.mVoiceControlSwitch.toggle();
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mVoiceControlSwitch == compoundButton) {
            if (z) {
                kj.a(AMapAppGlobal.getTopActivity(), new String[]{"android.permission.RECORD_AUDIO"});
            }
            re.k(getContext(), z);
            LogUtil.actionLogV2("P00381", "B001", LogUtil.createJSONObj(z ? CameraParams.FLASH_MODE_ON : CameraParams.FLASH_MODE_OFF));
        }
    }

    /* access modifiers changed from: protected */
    public qy createPresenter() {
        return new qy(this);
    }

    public void initView() {
        TitleBar titleBar = (TitleBar) findViewById(R.id.title);
        if (titleBar != null) {
            titleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    QuickAutoNaviVCSPage.this.finish();
                }
            });
        }
        this.mVoiceControlView = findViewById(R.id.navi_voice_control);
        this.mVoiceControlSwitch = (CheckBox) findViewById(R.id.navi_voice_control_checkbox);
        View findViewById = findViewById(R.id.navi_help_center);
        if (findViewById != null) {
            findViewById.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    ebr.a(true).post(new Runnable() {
                        public final void run() {
                            bid pageContext = AMapPageUtil.getPageContext();
                            if (pageContext != null) {
                                pageContext.startPage(QuickAutoNaviHelpCenterPage.class, (PageBundle) null);
                            }
                        }
                    });
                }
            });
        }
    }

    public void setData() {
        this.mVoiceControlSwitch.setChecked(DriveSpUtil.getBool(getContext(), DriveSpUtil.VOICE_CONTROL_SWITCH, false));
    }

    public void setListeners() {
        this.mVoiceControlView.setOnClickListener(this);
        this.mVoiceControlSwitch.setOnCheckedChangeListener(this);
    }
}
