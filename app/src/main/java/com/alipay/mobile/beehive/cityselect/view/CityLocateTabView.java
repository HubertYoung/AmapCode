package com.alipay.mobile.beehive.cityselect.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityTabModel;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.iprank.dao.IpRankSql;
import com.alipay.mobile.framework.service.OnLBSLocationListener;
import java.util.ArrayList;
import java.util.HashMap;

public class CityLocateTabView extends AULinearLayout {
    /* access modifiers changed from: private */
    public static final String TAG = CityLocateTabView.class.getSimpleName();
    /* access modifiers changed from: private */
    public CityVO locatedCity;
    /* access modifiers changed from: private */
    public Activity mActivity;
    private final CityTabModel mCityTabModel;
    /* access modifiers changed from: private */
    public TextView mLocatedTV;
    /* access modifiers changed from: private */
    public TextView mLocationFailTv;
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;
    private OnLBSLocationListener onLBSLocationListener;

    public CityLocateTabView(Context context, CityTabModel cityTabModel, OnItemClickListener onItemClickListener) {
        super(context);
        this.mCityTabModel = cityTabModel;
        this.mOnItemClickListener = onItemClickListener;
        this.mActivity = (Activity) context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_city_locate_tab, this, true);
        setOrientation(1);
        ((AUTextView) findViewById(R.id.city_grid_title)).setText(this.mCityTabModel.name);
        this.mLocationFailTv = (AUTextView) findViewById(R.id.location_fail);
        this.mLocatedTV = (AUTextView) findViewById(R.id.location);
        this.mLocatedTV.setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                if (CityLocateTabView.this.mOnItemClickListener != null) {
                    CityLocateTabView.this.mOnItemClickListener.onItemClick(null, CityLocateTabView.this.mLocatedTV, 0, 0);
                }
            }
        });
        if (this.mCityTabModel.cityVOs == null || this.mCityTabModel.cityVOs.isEmpty()) {
            updateLocatedCityText(this.mActivity.getString(R.string.regionselect_lbs_on_location), false);
            this.mLocationFailTv.setClickable(false);
            this.onLBSLocationListener = new OnLBSLocationListener() {
                public final void onLocationUpdate(LBSLocation location) {
                    LoggerFactory.getTraceLogger().debug(CityLocateTabView.TAG, "onLocationUpdate: location=" + location);
                    if (CityLocateTabView.this.mActivity != null && !CityLocateTabView.this.mActivity.isFinishing()) {
                        if (location == null) {
                            CityLocateTabView.this.showLocatedBtnFail();
                        } else {
                            CityLocateTabView.this.updateLocatedCity(location);
                        }
                    }
                }

                public final void onLocationFailed(int errCode) {
                    LoggerFactory.getTraceLogger().debug(CityLocateTabView.TAG, "onLocationFailed: errCode=" + errCode);
                    if (CityLocateTabView.this.mActivity != null && !CityLocateTabView.this.mActivity.isFinishing()) {
                        CityLocateTabView.this.showLocatedBtnFail();
                    }
                }
            };
            return;
        }
        updateLocatedCityText(this.mCityTabModel.cityVOs.get(0).city, true);
    }

    /* access modifiers changed from: private */
    public void updateLocatedCityText(String text, boolean isCity) {
        if (this.mLocatedTV != null && this.mLocationFailTv != null) {
            if (isCity) {
                this.mLocatedTV.setText(text);
                this.mLocatedTV.setVisibility(0);
                this.mLocationFailTv.setVisibility(8);
                return;
            }
            this.mLocationFailTv.setVisibility(0);
            this.mLocatedTV.setVisibility(8);
            this.mLocationFailTv.setText(text);
        }
    }

    /* access modifiers changed from: private */
    public void updateLocatedCity(LBSLocation location) {
        if (this.locatedCity == null) {
            this.locatedCity = new CityVO();
        }
        if (TextUtils.isEmpty(location.getCity()) && !TextUtils.isEmpty(location.getCountry())) {
            this.locatedCity.city = location.getCountry();
        } else if (location.getReGeocodeResult() == null || TextUtils.isEmpty(location.getReGeocodeResult().getCitySimpleName())) {
            this.locatedCity.city = removeCitySuffix(location.getCity());
        } else {
            this.locatedCity.city = location.getReGeocodeResult().getCitySimpleName();
        }
        this.locatedCity.adCode = location.getAdCode();
        this.locatedCity.province = location.getProvince();
        if (location.getReGeocodeResult() != null) {
            this.locatedCity.isMainLand = location.getReGeocodeResult().isChineseMainLand();
        }
        this.locatedCity.latitude = location.getLatitude();
        this.locatedCity.longitude = location.getLongitude();
        if (this.locatedCity.bizmap == null) {
            this.locatedCity.bizmap = new HashMap();
        }
        this.locatedCity.bizmap.put("citySource", IpRankSql.LBS_TABLE);
        this.mCityTabModel.cityVOs = new ArrayList();
        this.mCityTabModel.cityVOs.add(this.locatedCity);
        this.mActivity.runOnUiThread(new Runnable() {
            public final void run() {
                CityLocateTabView.this.mLocatedTV.setTag(R.layout.activity_area_select, CityLocateTabView.this.locatedCity);
                CityLocateTabView.this.updateLocatedCityText(CityLocateTabView.this.locatedCity.city, true);
                CityLocateTabView.this.mLocatedTV.setClickable(true);
            }
        });
    }

    /* access modifiers changed from: private */
    public void showLocatedBtnFail() {
        if (this.mActivity != null && !this.mActivity.isFinishing()) {
            this.mActivity.runOnUiThread(new Runnable() {
                public final void run() {
                    try {
                        CityLocateTabView.this.mLocationFailTv.setClickable(true);
                        CityLocateTabView.this.updateLocatedCityText(CityLocateTabView.this.mActivity.getString(R.string.cityselect_lbs_fail_and_retry), false);
                    } catch (Exception globalException) {
                        LoggerFactory.getTraceLogger().error(CityLocateTabView.TAG, "showLocatedBtnFail", globalException);
                    }
                }
            });
        }
    }

    private String removeCitySuffix(String city) {
        if (TextUtils.isEmpty(city) || !city.endsWith("市")) {
            return city;
        }
        return city.substring(0, city.length() - 1);
    }

    public OnLBSLocationListener getOnLBSLocationListener() {
        return this.onLBSLocationListener;
    }

    public TextView getLocationFailTv() {
        return this.mLocationFailTv;
    }
}
