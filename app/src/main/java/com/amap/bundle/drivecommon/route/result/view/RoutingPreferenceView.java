package com.amap.bundle.drivecommon.route.result.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.alipay.android.phone.mobilecommon.multimedia.video.data.CameraParams;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class RoutingPreferenceView extends LinearLayout {
    public static final String BUNDLE_KEY_BOOL_IS_NIGHT_MODE = "bundle_key_is_night_mode";
    public static final String BUNDLE_KEY_BOOL_IS_OFFLINE = "bundle_key_is_offline";
    public static final String BUNDLE_KEY_OBJ_ORIGIN = "bundle_key_origin";
    public static final int CAR_CHOICE = 0;
    public static final int MOTOR_CHOICE = 11;
    public static final int TRUCK_CHOICE = 1;
    private boolean isFromNavigation = false;
    private List<CheckBox> mCheckboxList = new ArrayList();
    /* access modifiers changed from: private */
    public int mChoiceType = 0;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public a mHolder;
    private boolean mIsAvoidJamChecked;
    private boolean mIsNightMode = false;
    private boolean mIsOfflineMode = false;
    private OnCheckedChangeListener mPreferenceCheckedListener = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            String str = "";
            if (compoundButton == RoutingPreferenceView.this.mHolder.b) {
                str = RoutingPreferenceView.this.mContext.getString(R.string.talkback_avoid_jam);
                RoutingPreferenceView.this.updateAvoidJam(z);
                if (!NetworkReachability.b()) {
                    ToastHelper.showLongToast(RoutingPreferenceView.this.mContext.getString(R.string.navi_setting_msg_for_tmc_under_offline));
                }
            } else if (compoundButton == RoutingPreferenceView.this.mHolder.e) {
                str = RoutingPreferenceView.this.mContext.getString(R.string.talkback_avoid_cost);
                if (z) {
                    RoutingPreferenceView.this.mHolder.k.setChecked(false);
                    if (RoutingPreferenceView.this.mChoiceType == 11) {
                        RoutingPreferenceView.this.mHolder.h.setChecked(false);
                    }
                    RoutingPreferenceView.this.motorActionLog(0);
                }
                RoutingPreferenceView.this.updateAvoidFee(z);
            } else if (compoundButton == RoutingPreferenceView.this.mHolder.h) {
                str = RoutingPreferenceView.this.mContext.getString(R.string.talkback_avoid_highway);
                if (z) {
                    RoutingPreferenceView.this.mHolder.k.setChecked(false);
                    if (RoutingPreferenceView.this.mChoiceType == 11) {
                        RoutingPreferenceView.this.mHolder.e.setChecked(false);
                    }
                    RoutingPreferenceView.this.motorActionLog(1);
                }
                RoutingPreferenceView.this.updateAvoidHighway(z);
            } else if (compoundButton == RoutingPreferenceView.this.mHolder.k) {
                str = RoutingPreferenceView.this.mContext.getString(R.string.talkback_highway_first);
                if (z) {
                    RoutingPreferenceView.this.mHolder.e.setChecked(false);
                    RoutingPreferenceView.this.mHolder.h.setChecked(false);
                    RoutingPreferenceView.this.motorActionLog(2);
                }
                RoutingPreferenceView.this.updateHighwayPreferred(z);
            }
            if (RoutingPreferenceView.this.mChoiceType == 1) {
                RoutingPreferenceView.this.actionLogSettingClick(z, str);
            }
            RoutingPreferenceView.this.saveRoutingPreference();
        }
    };

    static class a {
        View a;
        CheckBox b;
        ImageView c;
        View d;
        CheckBox e;
        ImageView f;
        View g;
        CheckBox h;
        ImageView i;
        View j;
        CheckBox k;
        ImageView l;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public RoutingPreferenceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate(context, R.layout.routing_preference_view, this);
        this.mContext = context;
        this.mHolder = new a(0);
        initViews(this);
    }

    private void initViews(View view) {
        this.mHolder.b = (CheckBox) view.findViewById(R.id.checkbox_avoid_jam);
        this.mHolder.a = view.findViewById(R.id.checkbox_avoid_jam_view);
        this.mHolder.a.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RoutingPreferenceView.this.mHolder.b.toggle();
            }
        });
        this.mHolder.c = (ImageView) view.findViewById(R.id.checkbox_ticker_avoid_jam);
        this.mCheckboxList.add(this.mHolder.b);
        updateView();
        this.mHolder.e = (CheckBox) view.findViewById(R.id.checkbox_avoid_fee);
        this.mHolder.d = view.findViewById(R.id.checkbox_avoid_fee_view);
        this.mHolder.d.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RoutingPreferenceView.this.mHolder.e.toggle();
            }
        });
        this.mHolder.f = (ImageView) view.findViewById(R.id.checkbox_ticker_avoid_fee);
        this.mCheckboxList.add(this.mHolder.e);
        this.mHolder.h = (CheckBox) view.findViewById(R.id.checkbox_avoid_highway);
        this.mHolder.g = view.findViewById(R.id.checkbox_avoid_highway_view);
        this.mHolder.g.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RoutingPreferenceView.this.mHolder.h.toggle();
            }
        });
        this.mHolder.i = (ImageView) view.findViewById(R.id.checkbox_ticker_avoid_highway);
        this.mCheckboxList.add(this.mHolder.h);
        this.mHolder.k = (CheckBox) view.findViewById(R.id.checkbox_highway_preferred);
        this.mHolder.j = view.findViewById(R.id.checkbox_highway_preferred_view);
        this.mHolder.j.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                RoutingPreferenceView.this.mHolder.k.toggle();
            }
        });
        this.mHolder.l = (ImageView) view.findViewById(R.id.checkbox_ticker_highway_preferred);
    }

    public void updateView() {
        if (this.mChoiceType == 11) {
            this.mHolder.a.setVisibility(8);
        } else {
            this.mHolder.a.setVisibility(0);
        }
    }

    public void setChoiceType(int i) {
        this.mChoiceType = i;
    }

    public void setPrebuiltData(@NonNull PageBundle pageBundle) {
        if (pageBundle.containsKey(BUNDLE_KEY_BOOL_IS_OFFLINE)) {
            this.mIsOfflineMode = pageBundle.getBoolean(BUNDLE_KEY_BOOL_IS_OFFLINE, false) && !NetworkReachability.b();
        }
        if (pageBundle.containsKey(BUNDLE_KEY_BOOL_IS_NIGHT_MODE)) {
            this.mIsNightMode = pageBundle.getBoolean(BUNDLE_KEY_BOOL_IS_NIGHT_MODE, false);
        }
        setData();
    }

    private void setData() {
        String str;
        if (this.mChoiceType == 11) {
            str = DriveUtil.getMotorRoutingChoice();
        } else if (this.mChoiceType == 1) {
            str = DriveUtil.getTruckRoutingChoice();
        } else {
            str = DriveUtil.getLastRoutingChoice();
        }
        if (!TextUtils.isEmpty(str)) {
            if (this.mIsOfflineMode) {
                this.mIsAvoidJamChecked = str.contains("2");
                this.mHolder.b.setChecked(false);
                this.mHolder.b.setClickable(false);
                this.mHolder.a.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        if (!RoutingPreferenceView.this.mHolder.b.isChecked()) {
                            ToastHelper.showToast(RoutingPreferenceView.this.mContext.getString(R.string.navi_setting_msg_for_tmc_under_offline));
                        }
                    }
                });
                updateAvoidJam(false);
                if (!this.isFromNavigation) {
                    setCheckBoxCompoundImage(this.mHolder.b, R.drawable.setting_congestion_night);
                    this.mHolder.b.setTextColor(this.mContext.getResources().getColor(R.color.f_c_2_a));
                }
            } else {
                boolean contains = str.contains("2");
                this.mHolder.b.setChecked(contains);
                updateAvoidJam(contains);
                this.mHolder.b.setOnCheckedChangeListener(this.mPreferenceCheckedListener);
            }
            boolean contains2 = str.contains("4");
            this.mHolder.e.setChecked(contains2);
            updateAvoidFee(contains2);
            this.mHolder.e.setOnCheckedChangeListener(this.mPreferenceCheckedListener);
            boolean contains3 = str.contains("8");
            this.mHolder.h.setChecked(contains3);
            updateAvoidHighway(contains3);
            this.mHolder.h.setOnCheckedChangeListener(this.mPreferenceCheckedListener);
            boolean contains4 = str.contains("16");
            this.mHolder.k.setChecked(contains4);
            updateHighwayPreferred(contains4);
            this.mHolder.k.setOnCheckedChangeListener(this.mPreferenceCheckedListener);
        }
    }

    /* access modifiers changed from: private */
    public void actionLogSettingClick(boolean z, String str) {
        String str2 = z ? CameraParams.FLASH_MODE_ON : CameraParams.FLASH_MODE_OFF;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(TrafficUtil.KEYWORD, str);
            jSONObject.put("type", str2);
            LogUtil.actionLogV2("P00475", "B004", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void motorActionLog(int i) {
        if (this.mChoiceType == 11) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00433", "B002", jSONObject);
            ku.a().c("motor_log", "[motorActionLog] pageId=P00433buttonId=B002 RoutingPreferenceView");
        }
    }

    /* access modifiers changed from: private */
    public void updateAvoidJam(boolean z) {
        setCheckBoxStatus(this.mHolder.a, this.mHolder.b, z, R.drawable.setting_congestion_selected, R.drawable.setting_congestion_day, R.drawable.setting_congestion_night, this.mHolder.c);
    }

    /* access modifiers changed from: private */
    public void updateAvoidFee(boolean z) {
        setCheckBoxStatus(this.mHolder.d, this.mHolder.e, z, R.drawable.ns_preference_avoid_fee_white, R.drawable.ns_preference_avoid_fee_black, R.drawable.ns_preference_avoid_fee_gray, this.mHolder.f);
    }

    /* access modifiers changed from: private */
    public void updateAvoidHighway(boolean z) {
        setCheckBoxStatus(this.mHolder.g, this.mHolder.h, z, R.drawable.ns_preference_avoid_highway_white, R.drawable.ns_preference_avoid_highway_black, R.drawable.ns_preference_avoid_highway_gray, this.mHolder.i);
    }

    /* access modifiers changed from: private */
    public void updateHighwayPreferred(boolean z) {
        setCheckBoxStatus(this.mHolder.j, this.mHolder.k, z, R.drawable.ns_preference_highway_preferred_white, R.drawable.ns_preference_highway_preferred_black, R.drawable.ns_preference_highway_preferred_gray, this.mHolder.l);
    }

    private void setCheckBoxStatus(@NonNull View view, @NonNull CheckBox checkBox, boolean z, int i, int i2, int i3, @NonNull ImageView imageView) {
        if (z) {
            setCheckBoxCompoundImage(checkBox, i);
            checkBox.setTextColor(this.mContext.getResources().getColor(R.color.f_c_1));
            imageView.setVisibility(0);
            imageView.setBackgroundResource(this.mIsNightMode ? R.drawable.setting_route_preference_check_night : R.drawable.setting_route_preference_check_day);
        } else {
            if (this.mIsNightMode) {
                setCheckBoxCompoundImage(checkBox, i3);
                checkBox.setTextColor(this.mContext.getResources().getColor(R.color.f_c_17));
            } else {
                setCheckBoxCompoundImage(checkBox, i2);
                checkBox.setTextColor(this.mContext.getResources().getColor(R.color.f_c_2));
            }
            imageView.setVisibility(4);
        }
        if (this.mIsNightMode) {
            view.setBackgroundResource(R.drawable.routing_preference_night_selector);
        } else {
            view.setBackgroundResource(R.drawable.routing_preference_day_selector);
        }
        view.setSelected(z);
    }

    private void setCheckBoxCompoundImage(CheckBox checkBox, int i) {
        Drawable drawable = this.mContext.getResources().getDrawable(i);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        checkBox.setCompoundDrawables(null, drawable, null, null);
    }

    /* access modifiers changed from: private */
    public void saveRoutingPreference() {
        String data = getData();
        if (this.mChoiceType == 11) {
            DriveUtil.putMotorRoutingChoice(data);
        } else if (this.mChoiceType == 1) {
            DriveUtil.putTruckRoutingChoice(data);
        } else {
            DriveUtil.putLastRoutingChoice(data);
        }
    }

    private String getData() {
        if (this.mChoiceType != 11) {
            StringBuilder sb = new StringBuilder();
            if (this.mHolder.b.isChecked() || (this.mIsOfflineMode && this.mIsAvoidJamChecked)) {
                sb.append("2");
                sb.append(MergeUtil.SEPARATOR_KV);
            }
            if (this.mHolder.e.isChecked()) {
                sb.append("4");
                sb.append(MergeUtil.SEPARATOR_KV);
            }
            if (this.mHolder.h.isChecked()) {
                sb.append("8");
                sb.append(MergeUtil.SEPARATOR_KV);
            }
            if (this.mHolder.k.isChecked()) {
                sb.append("16");
                sb.append(MergeUtil.SEPARATOR_KV);
            }
            if (sb.length() <= 0) {
                return "1";
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } else if (this.mHolder.e.isChecked()) {
            return "4";
        } else {
            if (this.mHolder.h.isChecked()) {
                return "8";
            }
            return this.mHolder.k.isChecked() ? "16" : "1";
        }
    }
}
