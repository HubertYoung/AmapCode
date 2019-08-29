package com.alipay.mobile.beehive.cityselect.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.alipay.mobile.beehive.cityselect.model.CityFragmentModel;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.ICityCallBack;
import com.alipay.mobile.beehive.cityselect.util.Constants;
import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Param;
import com.googlecode.androidannotations.annotations.EActivity;
import java.util.List;

@EActivity(resName = "activity_area_select")
public class SelectAreaSubActivity extends SelectAreaActivity {
    /* access modifiers changed from: private */
    public static final String TAG = new StringBuilder(Constants.BASE_TAG).append(SelectAreaSubActivity.class.getSimpleName()).toString();
    public static List<CityFragmentModel> cityFragmentModelsParam;
    /* access modifiers changed from: private */
    public CitySelectService citySelectService;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
    }

    public void onDestroy() {
        super.onDestroy();
        cityFragmentModelsParam = null;
    }

    /* access modifiers changed from: protected */
    public void handleParams(Intent intent) {
        super.handleParams(intent);
        this.mCityFragmentModels = cityFragmentModelsParam;
    }

    /* access modifiers changed from: protected */
    public ICityCallBack getCallBack() {
        return new ICityCallBack() {
            public final void onCitySelect(CityVO cityInfo, Activity activity) {
                if (!(SelectAreaSubActivity.this.citySelectService == null || SelectAreaSubActivity.this.citySelectService.getCallBack() == null)) {
                    SelectAreaSubActivity.this.citySelectService.getCallBack().onCitySelect(cityInfo, activity);
                }
                EventBusManager.getInstance().post(new LoadData(), "EVENT_SUB_AREA_SELECTED");
            }

            public final void onNothingSelected() {
                LoggerFactory.getTraceLogger().info(SelectAreaSubActivity.TAG, H5Param.DEFAULT_LONG_BACK_BEHAVIOR);
            }
        };
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b6337";
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }
}
