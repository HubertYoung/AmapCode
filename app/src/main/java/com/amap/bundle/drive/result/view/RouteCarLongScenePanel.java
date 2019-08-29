package com.amap.bundle.drive.result.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class RouteCarLongScenePanel extends FrameLayout implements OnClickListener {
    private View mBtnInspectionStation;
    private View mBtnServiceArea;
    private View mBtnViaCity;
    private View mBtnViaRoad;
    private View mBtnWeather;
    private List<ImageView> mIconList;
    private ImageView mInspectionStationIcon;
    private a mPanelActionListener;
    private ImageView mServiceAreaIcon;
    private ImageView mViaCityIcon;
    private ImageView mViaRoadIcon;
    private ImageView mWeatherIcon;

    public interface a {
        void a(boolean z);

        void b(boolean z);

        void c(boolean z);

        void d(boolean z);

        void e(boolean z);
    }

    public RouteCarLongScenePanel(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RouteCarLongScenePanel(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIconList = new ArrayList();
        initView(context);
    }

    public RouteCarLongScenePanel(Context context) {
        super(context);
        this.mIconList = new ArrayList();
        initView(context);
    }

    private void initView(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.route_car_result_map_long_scene_action, this, true);
        this.mBtnViaRoad = findViewById(R.id.route_btn_viaroad);
        this.mBtnViaCity = findViewById(R.id.route_btn_viacity);
        this.mBtnServiceArea = findViewById(R.id.route_btn_service_area);
        this.mBtnInspectionStation = findViewById(R.id.route_btn_inspection_station);
        this.mBtnWeather = findViewById(R.id.route_btn_weather_area);
        this.mViaRoadIcon = (ImageView) findViewById(R.id.route_viaroad_icon);
        this.mViaCityIcon = (ImageView) findViewById(R.id.route_viacity_icon);
        this.mServiceAreaIcon = (ImageView) findViewById(R.id.route_service_area_icon);
        this.mInspectionStationIcon = (ImageView) findViewById(R.id.route_inspection_station_icon);
        this.mWeatherIcon = (ImageView) findViewById(R.id.route_weather_icon);
        this.mIconList.add(this.mViaRoadIcon);
        this.mIconList.add(this.mViaCityIcon);
        this.mIconList.add(this.mServiceAreaIcon);
        this.mIconList.add(this.mInspectionStationIcon);
        this.mIconList.add(this.mWeatherIcon);
        this.mBtnViaRoad.setOnClickListener(this);
        this.mBtnViaCity.setOnClickListener(this);
        this.mBtnServiceArea.setOnClickListener(this);
        this.mBtnInspectionStation.setOnClickListener(this);
        this.mBtnWeather.setOnClickListener(this);
    }

    public void hiddenViacityView() {
        this.mBtnViaCity.setVisibility(8);
        updateIconsBg();
    }

    public void showViacityView() {
        this.mBtnViaCity.setVisibility(0);
        updateIconsBg();
    }

    public void showServieArea() {
        this.mBtnServiceArea.setVisibility(0);
        updateIconsBg();
    }

    public void showViaRoadView() {
        this.mBtnViaRoad.setVisibility(0);
        updateIconsBg();
    }

    public void showAllView() {
        for (ImageView next : this.mIconList) {
            ((ViewGroup) next.getParent()).setVisibility(0);
            next.setSelected(false);
        }
        updateIconsBg();
    }

    public void setInspectionStationBtnVisibility(boolean z) {
        this.mBtnInspectionStation.setVisibility(z ? 0 : 8);
        this.mInspectionStationIcon.setSelected(false);
        updateIconsBg();
    }

    public void setWeatherBtVisibility(boolean z) {
        this.mBtnWeather.setVisibility(z ? 0 : 8);
        this.mWeatherIcon.setSelected(false);
        updateIconsBg();
    }

    public void hideAllButInspectionStation() {
        for (ImageView next : this.mIconList) {
            ViewGroup viewGroup = (ViewGroup) next.getParent();
            viewGroup.setVisibility(viewGroup == this.mBtnInspectionStation ? 0 : 8);
            next.setSelected(false);
        }
        updateIconsBg();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (this.mPanelActionListener != null) {
            if (id == R.id.route_btn_viaroad) {
                boolean z = !this.mViaRoadIcon.isSelected();
                initBtnViaRoad(z);
                this.mPanelActionListener.a(z);
            } else if (id == R.id.route_btn_viacity) {
                boolean z2 = !this.mViaCityIcon.isSelected();
                initBtnViaCity(z2);
                this.mPanelActionListener.b(z2);
            } else if (id == R.id.route_btn_service_area) {
                boolean z3 = !this.mServiceAreaIcon.isSelected();
                initBtnServiceArea(z3);
                this.mPanelActionListener.c(z3);
            } else if (id == R.id.route_btn_inspection_station) {
                boolean z4 = !this.mInspectionStationIcon.isSelected();
                initBtnInspectionStation(z4);
                this.mPanelActionListener.d(z4);
            } else if (id == R.id.route_btn_weather_area) {
                boolean z5 = !this.mWeatherIcon.isSelected();
                initBtnWeather(z5);
                this.mPanelActionListener.e(z5);
            }
        }
    }

    public void setPanelActionListener(a aVar) {
        this.mPanelActionListener = aVar;
    }

    public void initBtnViaRoad(boolean z) {
        updateIcons(this.mViaRoadIcon, z);
    }

    public void initBtnViaCity(boolean z) {
        updateIcons(this.mViaCityIcon, z);
    }

    public void initBtnServiceArea(boolean z) {
        updateIcons(this.mServiceAreaIcon, z);
    }

    public void initBtnInspectionStation(boolean z) {
        updateIcons(this.mInspectionStationIcon, z);
    }

    private void initBtnWeather(boolean z) {
        updateIcons(this.mWeatherIcon, z);
    }

    private void updateIcons(View view, boolean z) {
        if (z) {
            for (ImageView next : this.mIconList) {
                next.setSelected(next == view);
            }
            return;
        }
        view.setSelected(false);
    }

    private void updateIconsBg() {
        ArrayList arrayList = new ArrayList();
        for (ImageView parent : this.mIconList) {
            ViewGroup viewGroup = (ViewGroup) parent.getParent();
            if (viewGroup.getVisibility() == 0) {
                arrayList.add(viewGroup);
            }
        }
        if (arrayList.size() == 1) {
            ((ViewGroup) arrayList.get(0)).getChildAt(0).setBackgroundResource(R.drawable.icon_c_bg_single);
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (i == 0) {
                ((ViewGroup) arrayList.get(i)).getChildAt(0).setBackgroundResource(R.drawable.icon_c_bg_up);
            } else if (i == arrayList.size() - 1) {
                ((ViewGroup) arrayList.get(i)).getChildAt(0).setBackgroundResource(R.drawable.icon_c_bg_down);
            } else {
                ((ViewGroup) arrayList.get(i)).getChildAt(0).setBackgroundResource(R.drawable.icon_c_bg_mid);
            }
        }
    }

    public boolean needLoadViaRoadScene() {
        return this.mViaRoadIcon.isSelected();
    }

    public boolean needLoadServiceArea() {
        return this.mServiceAreaIcon.isSelected();
    }

    public boolean needLoadViaCityScene() {
        return this.mViaCityIcon.isSelected();
    }

    public void initAllBtn() {
        initBtnServiceArea(false);
        initBtnViaCity(false);
        initBtnViaRoad(false);
        initBtnWeather(false);
    }

    public boolean getWeatherSwitch() {
        return this.mWeatherIcon.isSelected();
    }

    public boolean isWeatherShown() {
        return this.mBtnWeather.getVisibility() == 0;
    }

    public void resetAll() {
        if (isTargetViewSelected(this.mServiceAreaIcon)) {
            initBtnServiceArea(false);
            if (this.mPanelActionListener != null) {
                this.mPanelActionListener.c(false);
            }
        }
        if (isTargetViewSelected(this.mViaCityIcon)) {
            initBtnViaCity(false);
            if (this.mPanelActionListener != null) {
                this.mPanelActionListener.b(false);
            }
        }
        if (isTargetViewSelected(this.mViaRoadIcon)) {
            initBtnViaRoad(false);
            if (this.mPanelActionListener != null) {
                this.mPanelActionListener.a(false);
            }
        }
        if (isTargetViewSelected(this.mWeatherIcon)) {
            initBtnWeather(false);
            if (this.mPanelActionListener != null) {
                this.mPanelActionListener.e(false);
            }
        }
        if (isTargetViewSelected(this.mInspectionStationIcon)) {
            initBtnInspectionStation(false);
            if (this.mPanelActionListener != null) {
                this.mPanelActionListener.d(false);
            }
        }
    }

    private boolean isTargetViewSelected(View view) {
        return view != null && view.isSelected();
    }
}
