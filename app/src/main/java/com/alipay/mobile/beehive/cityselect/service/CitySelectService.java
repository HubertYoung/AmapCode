package com.alipay.mobile.beehive.cityselect.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.alipay.mobile.beehive.cityselect.model.CityFragmentModel;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.framework.service.ext.ExternalService;
import java.util.List;

public abstract class CitySelectService extends ExternalService {

    public static abstract class CitySelectHandler implements ICityCallBack {
        public abstract void onLocateFinish(CityVO cityVO, Activity activity, Bundle bundle);
    }

    public interface ICityCallBack {
        void onCitySelect(CityVO cityVO, Activity activity);

        void onNothingSelected();
    }

    public interface ICitySelectCallBack {
        void OnCitySelect(CityVO cityVO);
    }

    public interface ICitySelectCallBack2 {
        void onCitySelect(CityVO cityVO);

        void onNothingSelected();
    }

    public interface IModifyCityCallBack {
        void onModifyFailed(int i);

        void onModifySuccess(CityVO cityVO);
    }

    public interface IProvinceCitySelectCallBack {
        void OnProvinceCitySelect(Intent intent);
    }

    public abstract void callCitySelect(CityVO cityVO, List<CityVO> list, List<CityVO> list2, ICitySelectCallBack iCitySelectCallBack, boolean z, String str);

    public abstract void callCitySelect(CitySelectHandler citySelectHandler, Bundle bundle);

    public abstract void callCitySelect(ICityCallBack iCityCallBack, Bundle bundle);

    public abstract void callCitySelect(ICityCallBack iCityCallBack, Bundle bundle, List<CityVO> list);

    public abstract void callCitySelect(ICitySelectCallBack2 iCitySelectCallBack2, Bundle bundle);

    public abstract void callCitySelect(ICitySelectCallBack iCitySelectCallBack, Bundle bundle);

    public abstract void callCitySelect(ICitySelectCallBack iCitySelectCallBack, Bundle bundle, List<CityVO> list);

    public abstract void callProvinceCitySelect(IProvinceCitySelectCallBack iProvinceCitySelectCallBack);

    public abstract void callProvinceCitySelect(IProvinceCitySelectCallBack iProvinceCitySelectCallBack, Bundle bundle);

    public abstract ICityCallBack getCallBack();

    public abstract List<CityFragmentModel> getCityFragmentModels();

    public abstract List<CityVO> getMainCityList();

    public abstract IModifyCityCallBack getModifyCallBack();

    public abstract IProvinceCitySelectCallBack getProvinceCallBack();

    public abstract void notifySetCitySelectView(Bundle bundle);

    public abstract void openOrUpdateCitySelectUI(List<CityFragmentModel> list, Bundle bundle, ICityCallBack iCityCallBack);

    public abstract void setCallBack(ICityCallBack iCityCallBack);

    public abstract void setModifyCallBack(IModifyCityCallBack iModifyCityCallBack);

    public abstract void setProvinceCallBack(IProvinceCitySelectCallBack iProvinceCitySelectCallBack);

    public abstract void updateCityData(Bundle bundle);

    public abstract void updateCityData(Bundle bundle, List<CityVO> list);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public void onDestroy(Bundle bundle) {
    }
}
