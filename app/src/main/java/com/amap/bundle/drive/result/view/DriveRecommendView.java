package com.amap.bundle.drive.result.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class DriveRecommendView extends LinearLayout implements OnClickListener {
    public static final int POP_WINDOW_ANIMATION_DURATION = 300;
    private static final int POP_WINDOW_ANIMATION_DURATION_RESPONSE = 300;
    public static final int ROUTE_TYPE_CAR = 0;
    public static final int ROUTE_TYPE_MOTOR = 11;
    public static final int ROUTE_TYPE_TRUCK = 1;
    private int MAIN_ENTRY_ITEM_HEIGHT;
    private int MAIN_ENTRY_ITEM_PADDING;
    private int RECOMMENDVIEW_MARGIN_LEFT;
    private boolean isOfflineRoute = false;
    private CheckBox mChargesCb;
    private List<CheckBox> mCheckboxList = new ArrayList();
    private CheckBox mCongestionCb;
    private Context mContext;
    private OnClickListener mHideSettingsViewListener;
    private CheckBox mHighSpeedCb;
    private CheckBox mHightSpeedFirstCb;
    private View mRecommendContainerLayout;
    private RelativeLayout mRecommendLayout;
    private View mRecommendView;
    private int mRouteType = 0;
    private String routingChoice;

    public DriveRecommendView(Context context, OnClickListener onClickListener, int i) {
        super(context);
        this.mHideSettingsViewListener = onClickListener;
        this.mContext = context;
        this.mRouteType = i;
        initSizeData();
        initView(context);
        initData();
    }

    public void setIsOffline(boolean z) {
        this.isOfflineRoute = z;
    }

    public void setShowLocation(int i, int i2) {
        LayoutParams layoutParams = (LayoutParams) this.mRecommendContainerLayout.getLayoutParams();
        layoutParams.topMargin = i2;
        this.mRecommendContainerLayout.setLayoutParams(layoutParams);
    }

    public void setBottomDistance(int i) {
        LayoutParams layoutParams = (LayoutParams) this.mRecommendContainerLayout.getLayoutParams();
        layoutParams.bottomMargin = i;
        this.mRecommendContainerLayout.setLayoutParams(layoutParams);
    }

    public int getContentHeight() {
        if (this.mRecommendContainerLayout.getMeasuredHeight() <= 0) {
            this.mRecommendContainerLayout.measure(0, 0);
        }
        return this.mRecommendContainerLayout.getMeasuredHeight();
    }

    private void initSizeData() {
        this.MAIN_ENTRY_ITEM_HEIGHT = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_item_height);
        this.MAIN_ENTRY_ITEM_PADDING = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_item_padding);
        this.MAIN_ENTRY_ITEM_HEIGHT += this.MAIN_ENTRY_ITEM_PADDING;
        this.RECOMMENDVIEW_MARGIN_LEFT = this.mContext.getResources().getDimensionPixelSize(R.dimen.main_compass_margin_left);
    }

    public void showRecommendViewAnim() {
        float f = (float) this.RECOMMENDVIEW_MARGIN_LEFT;
        float f2 = (float) this.MAIN_ENTRY_ITEM_HEIGHT;
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.05f, 0.0f, 1.05f, 0, f, 0, f2);
        scaleAnimation.setDuration(300);
        scaleAnimation.setInterpolator(new FastOutSlowInInterpolator());
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.05f, 0.94f, 1.05f, 0.94f, f, f2);
        scaleAnimation2.setDuration(300);
        scaleAnimation2.setInterpolator(new FastOutSlowInInterpolator());
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(scaleAnimation2);
        animationSet.addAnimation(getAlphaAnimation(true));
        animationSet.setFillAfter(true);
        this.mRecommendContainerLayout.startAnimation(animationSet);
    }

    public void hideRecommendViewAnim(AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 0, (float) this.RECOMMENDVIEW_MARGIN_LEFT, 0, (float) this.MAIN_ENTRY_ITEM_HEIGHT);
        scaleAnimation.setDuration(400);
        scaleAnimation.setInterpolator(new DecelerateInterpolator());
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(getAlphaAnimation(false));
        animationSet.setAnimationListener(animationListener);
        animationSet.setFillAfter(true);
        this.mRecommendContainerLayout.startAnimation(animationSet);
    }

    private Animation getAlphaAnimation(boolean z) {
        float f = 1.0f;
        float f2 = z ? 0.0f : 1.0f;
        if (!z) {
            f = 0.0f;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }

    private void initData() {
        if (this.mRouteType == 11) {
            this.routingChoice = DriveUtil.getMotorRoutingChoice();
        } else if (this.mRouteType == 1) {
            this.routingChoice = DriveUtil.getTruckRoutingChoice();
        } else {
            this.routingChoice = DriveUtil.getLastRoutingChoice();
        }
        if (!TextUtils.isEmpty(this.routingChoice)) {
            boolean contains = this.routingChoice.contains("2");
            this.mCongestionCb.setChecked(contains);
            updateItemstatus(this.mCongestionCb, contains);
            boolean contains2 = this.routingChoice.contains("4");
            this.mChargesCb.setChecked(contains2);
            updateItemstatus(this.mChargesCb, contains2);
            boolean contains3 = this.routingChoice.contains("8");
            this.mHighSpeedCb.setChecked(contains3);
            updateItemstatus(this.mHighSpeedCb, contains3);
            boolean contains4 = this.routingChoice.contains("16");
            this.mHightSpeedFirstCb.setChecked(contains4);
            updateItemstatus(this.mHightSpeedFirstCb, contains4);
        }
    }

    public void setRouteType(int i) {
        this.mRouteType = i;
    }

    private void initView(Context context) {
        this.mRecommendView = inflate(context, R.layout.recommendview_layout, this);
        this.mRecommendContainerLayout = this.mRecommendView.findViewById(R.id.recommend_container_layout);
        this.mRecommendView.setOnTouchListener(new OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.mRecommendLayout = (RelativeLayout) this.mRecommendView.findViewById(R.id.recommend_layout);
        NoDBClickUtil.a((View) this.mRecommendLayout, this.mHideSettingsViewListener);
        this.mCongestionCb = (CheckBox) this.mRecommendView.findViewById(R.id.congestion_rb);
        this.mCongestionCb.setOnClickListener(this);
        this.mCheckboxList.add(this.mCongestionCb);
        if (this.mRouteType == 11) {
            this.mCongestionCb.setVisibility(8);
        } else {
            this.mCongestionCb.setVisibility(0);
        }
        this.mChargesCb = (CheckBox) this.mRecommendView.findViewById(R.id.charges_rb);
        this.mChargesCb.setOnClickListener(this);
        this.mCheckboxList.add(this.mChargesCb);
        this.mHighSpeedCb = (CheckBox) this.mRecommendView.findViewById(R.id.highspeed_rb);
        this.mHighSpeedCb.setOnClickListener(this);
        this.mCheckboxList.add(this.mHighSpeedCb);
        this.mHightSpeedFirstCb = (CheckBox) this.mRecommendView.findViewById(R.id.highspeed_first_rb);
        this.mHightSpeedFirstCb.setOnClickListener(this);
        this.mCheckboxList.add(this.mHightSpeedFirstCb);
    }

    public void setRecommendLayoutBg() {
        this.mRecommendLayout.setBackgroundColor(ContextCompat.getColor(this.mContext, R.color.transparent));
    }

    private void updateItemstatus(CheckBox checkBox, boolean z) {
        checkBox.setChecked(z);
        checkBox.setTextColor(this.mContext.getResources().getColor(z ? R.color.f_c_6 : R.color.f_c_2));
    }

    public boolean selectedHasChange() {
        String str;
        if (this.mRouteType == 11) {
            str = DriveUtil.getMotorRoutingChoice();
        } else if (this.mRouteType == 1) {
            str = DriveUtil.getTruckRoutingChoice();
        } else {
            str = DriveUtil.getLastRoutingChoice();
        }
        if (!TextUtils.equals(this.routingChoice, str)) {
            return true;
        }
        return false;
    }

    public void onClick(View view) {
        if (view.getId() != R.id.congestion_rb || isCongestionEnable()) {
            if (this.mRouteType == 11) {
                if (view.getId() == R.id.highspeed_rb) {
                    updateItemstatus(this.mHighSpeedCb, this.mHighSpeedCb.isChecked());
                    updateItemstatus(this.mChargesCb, false);
                    updateItemstatus(this.mHightSpeedFirstCb, false);
                    if (this.mHighSpeedCb.isChecked()) {
                        motorActionLog(1);
                    }
                } else if (view.getId() == R.id.highspeed_first_rb) {
                    updateItemstatus(this.mHightSpeedFirstCb, this.mHightSpeedFirstCb.isChecked());
                    updateItemstatus(this.mChargesCb, false);
                    updateItemstatus(this.mHighSpeedCb, false);
                    if (this.mHightSpeedFirstCb.isChecked()) {
                        motorActionLog(2);
                    }
                } else {
                    updateItemstatus(this.mChargesCb, this.mChargesCb.isChecked());
                    updateItemstatus(this.mHighSpeedCb, false);
                    updateItemstatus(this.mHightSpeedFirstCb, false);
                    if (this.mChargesCb.isChecked()) {
                        motorActionLog(0);
                    }
                }
                DriveUtil.putMotorRoutingChoice(getData());
                return;
            }
            if (TextUtils.equals((String) view.getTag(), "single")) {
                int size = this.mCheckboxList.size();
                for (int i = 0; i < size; i++) {
                    CheckBox checkBox = this.mCheckboxList.get(i);
                    boolean equals = TextUtils.equals((String) checkBox.getTag(), "single");
                    checkBox.setChecked(equals);
                    updateItemstatus(checkBox, equals);
                }
            } else {
                CheckBox checkBox2 = (CheckBox) view;
                updateItemstatus(checkBox2, checkBox2.isChecked());
            }
            checkHighwayAndCharge(view);
            updateViewStatus();
            String data = getData();
            if (this.mRouteType == 1) {
                DriveUtil.putTruckRoutingChoice(data);
            } else {
                DriveUtil.putLastRoutingChoice(data);
            }
        } else {
            CheckBox checkBox3 = (CheckBox) view;
            checkBox3.setChecked(!checkBox3.isChecked());
            updateViewStatus();
            ToastHelper.showLongToast(this.mContext.getString(R.string.navi_setting_msg_for_tmc_under_offline));
        }
    }

    private void checkHighwayAndCharge(View view) {
        if (view.getId() == R.id.charges_rb) {
            if (this.mChargesCb.isChecked()) {
                updateItemstatus(this.mHightSpeedFirstCb, false);
            }
        } else if (view.getId() == R.id.highspeed_rb) {
            if (this.mHighSpeedCb.isChecked()) {
                updateItemstatus(this.mHightSpeedFirstCb, false);
            }
        } else if (view.getId() == R.id.highspeed_first_rb && this.mHightSpeedFirstCb.isChecked()) {
            updateItemstatus(this.mChargesCb, false);
            updateItemstatus(this.mHighSpeedCb, false);
        }
    }

    private String getData() {
        StringBuilder sb = new StringBuilder();
        if (this.mRouteType != 11) {
            if (this.mCongestionCb != null && this.mCongestionCb.isChecked()) {
                sb.append("2");
                sb.append(MergeUtil.SEPARATOR_KV);
            }
            if (this.mChargesCb.isChecked()) {
                sb.append("4");
                sb.append(MergeUtil.SEPARATOR_KV);
            }
            if (this.mHighSpeedCb.isChecked()) {
                sb.append("8");
                sb.append(MergeUtil.SEPARATOR_KV);
            }
            if (this.mHightSpeedFirstCb.isChecked()) {
                sb.append("16");
                sb.append(MergeUtil.SEPARATOR_KV);
            }
            if (sb.length() <= 0) {
                return "1";
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        } else if (this.mChargesCb.isChecked()) {
            return "4";
        } else {
            if (this.mHighSpeedCb.isChecked()) {
                return "8";
            }
            return this.mHightSpeedFirstCb.isChecked() ? "16" : "1";
        }
    }

    public void updateViewStatus() {
        if (this.mCongestionCb != null) {
            if (!isCongestionEnable()) {
                boolean isChecked = this.mCongestionCb.isChecked();
                Drawable drawable = this.mContext.getResources().getDrawable(isChecked ? R.drawable.congestion_disable_selected : R.drawable.congestion_disable);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                this.mCongestionCb.setCompoundDrawables(null, drawable, null, null);
                this.mCongestionCb.setTextColor(this.mContext.getResources().getColor(isChecked ? R.color.f_c_6_a : R.color.f_c_2_a));
                return;
            }
            Drawable drawable2 = this.mContext.getResources().getDrawable(R.drawable.congestion_selector);
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            this.mCongestionCb.setCompoundDrawables(null, drawable2, null, null);
            updateItemstatus(this.mCongestionCb, this.mCongestionCb.isChecked());
        }
    }

    private boolean isCongestionEnable() {
        return this.isOfflineRoute ? NetworkReachability.a() : NetworkReachability.a() || NetworkReachability.b();
    }

    private void motorActionLog(int i) {
        if (this.mRouteType == 11) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00435", "B004", jSONObject);
        }
    }
}
