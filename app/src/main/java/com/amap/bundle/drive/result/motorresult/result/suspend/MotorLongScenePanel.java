package com.amap.bundle.drive.result.motorresult.result.suspend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView;

public class MotorLongScenePanel extends FrameLayout implements OnClickListener {
    private AGroupSuspendView mBtnAGroup;
    private View mBtnServiceArea;
    private View mBtnSupply;
    private View mBtnWeather;
    private a mPanelActionListener;
    private ImageView mServiceAreaIcon;
    private ImageView mSupplyIcon;
    private ImageView mWeatherIcon;

    public interface a {
        void a(int i, boolean z);
    }

    public MotorLongScenePanel(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MotorLongScenePanel(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.route_motorbike_result_map_long_scene_action, this, true);
        this.mBtnSupply = findViewById(R.id.route_btn_supply);
        this.mBtnServiceArea = findViewById(R.id.route_btn_service_area);
        this.mBtnAGroup = (AGroupSuspendView) findViewById(R.id.route_btn_agroup);
        this.mBtnWeather = findViewById(R.id.route_btn_weather_area);
        this.mSupplyIcon = (ImageView) findViewById(R.id.route_supply_icon);
        this.mServiceAreaIcon = (ImageView) findViewById(R.id.route_service_area_icon);
        this.mWeatherIcon = (ImageView) findViewById(R.id.route_weather_icon);
        this.mBtnSupply.setOnClickListener(this);
        this.mBtnServiceArea.setOnClickListener(this);
        this.mBtnWeather.setOnClickListener(this);
    }

    public void setAGroupVisibility(boolean z) {
        if (z) {
            this.mBtnAGroup.setVisibility(0);
        } else {
            this.mBtnAGroup.setVisibility(8);
        }
    }

    public void setWeatherBtVisibility(boolean z) {
        if (z) {
            this.mBtnWeather.setVisibility(0);
            this.mBtnWeather.setTag(Boolean.FALSE);
            this.mWeatherIcon.setImageResource(R.drawable.default_icon_weather_normal);
            this.mSupplyIcon.setImageResource(R.drawable.icon_supply);
            return;
        }
        this.mBtnWeather.setVisibility(8);
    }

    public void setSupplyBtVisibility(boolean z) {
        if (z) {
            this.mServiceAreaIcon.setBackgroundResource(R.drawable.icon_c_bg_up);
            this.mBtnSupply.setVisibility(0);
            this.mBtnSupply.setTag(Boolean.FALSE);
            this.mWeatherIcon.setImageResource(R.drawable.default_icon_weather_normal);
            this.mWeatherIcon.setBackgroundResource(R.drawable.icon_c_bg_mid);
            this.mServiceAreaIcon.setImageResource(R.drawable.icon_c10);
            return;
        }
        this.mBtnSupply.setVisibility(8);
        this.mServiceAreaIcon.setBackgroundResource(R.drawable.icon_c_bg_up);
        this.mWeatherIcon.setBackgroundResource(R.drawable.icon_c_bg_down);
    }

    public AGroupSuspendView getLongSceneAGroup() {
        return this.mBtnAGroup;
    }

    public void releaseAGroupView() {
        if (this.mBtnAGroup != null) {
            this.mBtnAGroup.destroy();
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        Object tag = view.getTag();
        ku.a().c("15239730", "RouteCarLongScenePanel   onClick   obj:".concat(String.valueOf(tag)));
        if (this.mPanelActionListener != null) {
            if (id == R.id.route_btn_supply) {
                this.mBtnServiceArea.setTag(Boolean.FALSE);
                this.mServiceAreaIcon.setImageResource(R.drawable.icon_c10);
                this.mBtnWeather.setTag(Boolean.FALSE);
                this.mWeatherIcon.setImageResource(R.drawable.default_icon_weather_normal);
                if (tag == null) {
                    view.setTag(Boolean.TRUE);
                    this.mSupplyIcon.setImageResource(R.drawable.icon_supply_hl);
                    this.mPanelActionListener.a(1, true);
                } else if (((Boolean) tag).booleanValue()) {
                    view.setTag(Boolean.FALSE);
                    this.mSupplyIcon.setImageResource(R.drawable.icon_supply);
                    this.mPanelActionListener.a(1, false);
                } else {
                    view.setTag(Boolean.TRUE);
                    this.mSupplyIcon.setImageResource(R.drawable.icon_supply_hl);
                    this.mPanelActionListener.a(1, true);
                }
            } else if (id == R.id.route_btn_service_area) {
                this.mBtnSupply.setTag(Boolean.FALSE);
                this.mSupplyIcon.setImageResource(R.drawable.icon_supply);
                this.mBtnWeather.setTag(Boolean.FALSE);
                this.mWeatherIcon.setImageResource(R.drawable.default_icon_weather_normal);
                if (tag == null) {
                    view.setTag(Boolean.TRUE);
                    this.mServiceAreaIcon.setImageResource(R.drawable.icon_c10_a);
                    this.mPanelActionListener.a(2, true);
                } else if (((Boolean) tag).booleanValue()) {
                    view.setTag(Boolean.FALSE);
                    this.mServiceAreaIcon.setImageResource(R.drawable.icon_c10);
                    this.mPanelActionListener.a(2, false);
                } else {
                    view.setTag(Boolean.TRUE);
                    this.mServiceAreaIcon.setImageResource(R.drawable.icon_c10_a);
                    this.mPanelActionListener.a(2, true);
                }
            } else if (id == R.id.route_btn_weather_area) {
                this.mBtnSupply.setTag(Boolean.FALSE);
                this.mSupplyIcon.setImageResource(R.drawable.icon_supply);
                this.mBtnServiceArea.setTag(Boolean.FALSE);
                this.mServiceAreaIcon.setImageResource(R.drawable.icon_c10);
                if (tag == null || !((Boolean) tag).booleanValue()) {
                    view.setTag(Boolean.TRUE);
                    this.mWeatherIcon.setImageResource(R.drawable.default_icon_weather_highlight);
                    this.mPanelActionListener.a(3, true);
                } else {
                    view.setTag(Boolean.FALSE);
                    this.mWeatherIcon.setImageResource(R.drawable.default_icon_weather_normal);
                    this.mPanelActionListener.a(3, false);
                }
            }
        }
    }

    public void setPanelActionListener(a aVar) {
        this.mPanelActionListener = aVar;
    }

    public void initBtnSupply(boolean z) {
        if (z) {
            this.mBtnSupply.setTag(Boolean.TRUE);
            this.mSupplyIcon.setImageResource(R.drawable.icon_supply_hl);
            return;
        }
        this.mBtnSupply.setTag(Boolean.FALSE);
        this.mSupplyIcon.setImageResource(R.drawable.icon_supply);
    }

    public void initBtnServiceArea(boolean z) {
        if (z) {
            this.mBtnServiceArea.setTag(Boolean.TRUE);
            this.mServiceAreaIcon.setImageResource(R.drawable.icon_c10_a);
            this.mBtnSupply.setTag(Boolean.FALSE);
            this.mSupplyIcon.setImageResource(R.drawable.icon_supply);
            return;
        }
        this.mBtnServiceArea.setTag(Boolean.FALSE);
        this.mServiceAreaIcon.setImageResource(R.drawable.icon_c10);
    }

    public void initBtnWeather(boolean z) {
        if (z) {
            this.mBtnWeather.setTag(Boolean.TRUE);
            this.mWeatherIcon.setImageResource(R.drawable.default_icon_weather_highlight);
            return;
        }
        this.mBtnWeather.setTag(Boolean.FALSE);
        this.mWeatherIcon.setImageResource(R.drawable.default_icon_weather_normal);
    }

    public boolean needLoadServiceArea() {
        Object tag = this.mBtnServiceArea.getTag();
        return tag != null && ((Boolean) tag).booleanValue();
    }

    public void initAllBtn() {
        initBtnServiceArea(false);
        initBtnSupply(false);
        initBtnWeather(false);
    }

    public void resetAll() {
        if (isTargetViewSelected(this.mBtnServiceArea)) {
            initBtnServiceArea(false);
            if (this.mPanelActionListener != null) {
                this.mPanelActionListener.a(2, false);
            }
        }
        if (isTargetViewSelected(this.mBtnSupply)) {
            initBtnSupply(false);
            if (this.mPanelActionListener != null) {
                this.mPanelActionListener.a(1, false);
            }
        }
        if (isTargetViewSelected(this.mBtnWeather)) {
            initBtnWeather(false);
            if (this.mPanelActionListener != null) {
                this.mPanelActionListener.a(3, false);
            }
        }
    }

    private boolean isTargetViewSelected(View view) {
        return (view == null || view.getTag() == null || !((Boolean) view.getTag()).booleanValue()) ? false : true;
    }

    public boolean getWeatherSwitch() {
        if (this.mBtnWeather.getTag() != null) {
            return ((Boolean) this.mBtnWeather.getTag()).booleanValue();
        }
        return false;
    }

    public boolean isWeatherShown() {
        return this.mBtnWeather.getVisibility() == 0;
    }
}
