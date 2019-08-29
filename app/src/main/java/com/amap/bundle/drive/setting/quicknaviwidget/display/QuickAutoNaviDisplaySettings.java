package com.amap.bundle.drive.setting.quicknaviwidget.display;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;

public class QuickAutoNaviDisplaySettings extends DriveBasePage<qs> implements OnClickListener, OnCheckedChangeListener {
    /* access modifiers changed from: private */
    public TextView mAuto;
    /* access modifiers changed from: private */
    public TextView mCarHead;
    /* access modifiers changed from: private */
    public TextView mDay;
    /* access modifiers changed from: private */
    public int mFrom = 1;
    /* access modifiers changed from: private */
    public TextView mNight;
    /* access modifiers changed from: private */
    public TextView mNorthHead;
    private View mScaleAutoChange;
    private CheckBox mScaleAutoChangeCb;

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.navi_settings_display);
    }

    /* access modifiers changed from: protected */
    public qs createPresenter() {
        return new qs(this);
    }

    public void onPageViewCreated() {
        inintData();
        initViews();
        setViewData();
        setListeners();
    }

    private void inintData() {
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("amap.extra.prefer.from")) {
            this.mFrom = arguments.getInt("amap.extra.prefer.from");
        }
    }

    private void initViews() {
        ((TitleBar) getContentView().findViewById(R.id.title)).setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                QuickAutoNaviDisplaySettings.this.finish();
            }
        });
        this.mCarHead = (TextView) getContentView().findViewById(R.id.carhead);
        this.mNorthHead = (TextView) getContentView().findViewById(R.id.northhead);
        this.mAuto = (TextView) getContentView().findViewById(R.id.auto);
        this.mDay = (TextView) getContentView().findViewById(R.id.day);
        this.mNight = (TextView) getContentView().findViewById(R.id.night);
        this.mScaleAutoChange = getContentView().findViewById(R.id.scale_auto_change_layout);
        this.mScaleAutoChangeCb = (CheckBox) getContentView().findViewById(R.id.chk_scale_auto_change);
        boolean z = true;
        if (this.mFrom == 4) {
            CheckBox checkBox = this.mScaleAutoChangeCb;
            if (re.h() != 1) {
                z = false;
            }
            checkBox.setChecked(z);
        } else {
            this.mScaleAutoChangeCb.setChecked(DriveSpUtil.getBool(getContext(), DriveSpUtil.SCALE_AUTO_CHANGE, true));
        }
        this.mScaleAutoChange.setOnClickListener(this);
        this.mScaleAutoChangeCb.setOnCheckedChangeListener(this);
    }

    private void setViewData() {
        boolean z = this.mFrom == 4 ? re.d() == 1 : DriveSpUtil.getBool(getContext(), "NaviMapMode", true);
        if (z) {
            this.mCarHead.setSelected(true);
            this.mNorthHead.setSelected(false);
            return;
        }
        this.mNorthHead.setSelected(true);
        this.mCarHead.setSelected(false);
    }

    private void setListeners() {
        NoDBClickUtil.a((View) this.mCarHead, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (!QuickAutoNaviDisplaySettings.this.mCarHead.isSelected()) {
                    QuickAutoNaviDisplaySettings.this.carHeadClicked();
                }
            }
        });
        NoDBClickUtil.a((View) this.mNorthHead, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (!QuickAutoNaviDisplaySettings.this.mNorthHead.isSelected()) {
                    QuickAutoNaviDisplaySettings.this.northHeadClicked();
                }
            }
        });
        if (!(this.mDay == null || this.mNight == null)) {
            if (this.mFrom == 4) {
                int c = re.c();
                if (c == 1) {
                    this.mDay.setSelected(true);
                    this.mAuto.setSelected(false);
                    this.mNight.setSelected(false);
                } else if (c == 2) {
                    this.mNight.setSelected(true);
                    this.mDay.setSelected(false);
                    this.mAuto.setSelected(false);
                } else if (c == 0) {
                    this.mAuto.setSelected(true);
                    this.mNight.setSelected(false);
                    this.mDay.setSelected(false);
                }
            } else {
                int b = re.b(getContext());
                if (b == 17) {
                    this.mDay.setSelected(true);
                    this.mAuto.setSelected(false);
                    this.mNight.setSelected(false);
                } else if (b == 18) {
                    this.mNight.setSelected(true);
                    this.mDay.setSelected(false);
                    this.mAuto.setSelected(false);
                } else if (b == 16) {
                    this.mAuto.setSelected(true);
                    this.mNight.setSelected(false);
                    this.mDay.setSelected(false);
                }
            }
        }
        NoDBClickUtil.a((View) this.mAuto, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (!QuickAutoNaviDisplaySettings.this.mAuto.isSelected()) {
                    QuickAutoNaviDisplaySettings.this.mAuto.setSelected(true);
                    QuickAutoNaviDisplaySettings.this.mDay.setSelected(false);
                    QuickAutoNaviDisplaySettings.this.mNight.setSelected(false);
                    if (QuickAutoNaviDisplaySettings.this.mFrom == 4) {
                        re.a(0);
                        return;
                    }
                    re.b(QuickAutoNaviDisplaySettings.this.getContext(), 16);
                }
            }
        });
        NoDBClickUtil.a((View) this.mDay, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (!QuickAutoNaviDisplaySettings.this.mDay.isSelected()) {
                    QuickAutoNaviDisplaySettings.this.mDay.setSelected(true);
                    QuickAutoNaviDisplaySettings.this.mAuto.setSelected(false);
                    QuickAutoNaviDisplaySettings.this.mNight.setSelected(false);
                    if (QuickAutoNaviDisplaySettings.this.mFrom == 4) {
                        re.a(1);
                        return;
                    }
                    re.b(QuickAutoNaviDisplaySettings.this.getContext(), 17);
                }
            }
        });
        NoDBClickUtil.a((View) this.mNight, (OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                if (!QuickAutoNaviDisplaySettings.this.mNight.isSelected()) {
                    QuickAutoNaviDisplaySettings.this.mNight.setSelected(true);
                    QuickAutoNaviDisplaySettings.this.mAuto.setSelected(false);
                    QuickAutoNaviDisplaySettings.this.mDay.setSelected(false);
                    if (QuickAutoNaviDisplaySettings.this.mFrom == 4) {
                        re.a(2);
                        return;
                    }
                    re.b(QuickAutoNaviDisplaySettings.this.getContext(), 18);
                }
            }
        });
    }

    public void onClick(View view) {
        if (this.mScaleAutoChange == view) {
            this.mScaleAutoChangeCb.toggle();
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (this.mScaleAutoChangeCb == compoundButton) {
            if (this.mFrom == 4) {
                ro.b(SharePreferenceName.user_route_method_info.toString(), DriveSpUtil.MOTOR_PATH_MAPVIEW_SCALEZOOM_KEY, String.valueOf(z ? 1 : 0));
                return;
            }
            re.e(getContext(), z);
        }
    }

    /* access modifiers changed from: private */
    public void carHeadClicked() {
        this.mCarHead.setSelected(true);
        this.mNorthHead.setSelected(false);
        if (this.mFrom == 4) {
            re.b(1);
        } else {
            re.a(getContext(), true);
        }
    }

    /* access modifiers changed from: private */
    public void northHeadClicked() {
        this.mNorthHead.setSelected(true);
        this.mCarHead.setSelected(false);
        if (this.mFrom == 4) {
            re.b(0);
        } else {
            re.a(getContext(), false);
        }
    }
}
