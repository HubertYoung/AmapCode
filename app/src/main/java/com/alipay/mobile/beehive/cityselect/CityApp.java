package com.alipay.mobile.beehive.cityselect;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.alipay.mobile.antui.basic.AUToast;
import com.alipay.mobile.beehive.cityselect.model.CityFragmentModel;
import com.alipay.mobile.beehive.cityselect.model.CityPageModel;
import com.alipay.mobile.beehive.cityselect.model.CityTabModel;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.ICityCallBack;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ActivityApplication;
import java.util.ArrayList;
import java.util.List;

public class CityApp extends ActivityApplication {
    private Bundle params;

    public String getEntryClassName() {
        return null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        this.params = bundle;
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        dispatchParams();
    }

    /* access modifiers changed from: protected */
    public void onRestart(Bundle bundle) {
        this.params = bundle;
        dispatchParams();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
        this.params = null;
    }

    private void dispatchParams() {
        List cityFragmentModels = new ArrayList();
        CityFragmentModel cityFragmentModelMock = new CityFragmentModel();
        cityFragmentModelMock.name = "境内";
        CityPageModel cityPageModel = new CityPageModel();
        cityPageModel.cityTabModels = new ArrayList();
        cityPageModel.fillMainLand = 1;
        CityTabModel locateTabModel = new CityTabModel();
        locateTabModel.name = "你所在的地区";
        locateTabModel.navName = "当前";
        locateTabModel.needSearch = false;
        locateTabModel.type = 1;
        cityPageModel.cityTabModels.add(locateTabModel);
        cityFragmentModelMock.cityPageModels.add(cityPageModel);
        cityFragmentModels.add(cityFragmentModelMock);
        CityFragmentModel cityFragmentModelMock2 = new CityFragmentModel();
        cityFragmentModelMock2.name = "境外";
        CityPageModel cityPageModel2 = new CityPageModel();
        cityPageModel2.fillMainLand = 2;
        cityFragmentModelMock2.cityPageModels.add(cityPageModel2);
        cityFragmentModels.add(cityFragmentModelMock2);
        CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
        if (citySelectService != null) {
            citySelectService.openOrUpdateCitySelectUI(cityFragmentModels, new Bundle(), new ICityCallBack() {
                public final void onCitySelect(CityVO cityInfo, Activity activity) {
                    AUToast.makeToast((Context) CityApp.this.getTopActivity(), 0, (CharSequence) cityInfo.city, 1).show();
                }

                public final void onNothingSelected() {
                    AUToast.makeToast((Context) CityApp.this.getTopActivity(), 0, (CharSequence) "取消", 1).show();
                }
            });
        }
    }
}
