package com.alipay.mobile.beehive.cityselect.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.beehive.cityselect.model.CityFragmentModel;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.model.CityVOList;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.CitySelectHandler;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.ICityCallBack;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.ICitySelectCallBack;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.ICitySelectCallBack2;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.IModifyCityCallBack;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.IProvinceCitySelectCallBack;
import com.alipay.mobile.beehive.cityselect.ui.ProvinceCityListActivity;
import com.alipay.mobile.beehive.cityselect.ui.ProvinceCityListActivity_;
import com.alipay.mobile.beehive.cityselect.ui.SelectAreaActivity_;
import com.alipay.mobile.beehive.cityselect.ui.SelectCityActivity;
import com.alipay.mobile.beehive.cityselect.ui.SelectCityActivity_;
import com.alipay.mobile.beehive.cityselect.ui.SelectCityTinyActivity_;
import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.utils.StringUtils;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.framework.app.MicroApplication;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class CitySelectServiceImpl extends CitySelectService {
    private ICityCallBack callBack;
    /* access modifiers changed from: private */
    public List<CityFragmentModel> cityFragmentModels;
    private String citySelectServiceId;
    private int citySelectServiceIdSeed = 0;
    private Boolean configStartSingleTop = null;
    private IProvinceCitySelectCallBack mProvinceCallBack;
    private List<CityVO> mainCityList;
    private IModifyCityCallBack modifyCallback;

    public void callCitySelect(CityVO currentCity, List<CityVO> citys, List<CityVO> hotCitys, final ICitySelectCallBack callback, boolean showHotCitys, String title) {
        this.callBack = new ICityCallBack() {
            public final void onCitySelect(CityVO cityInfo, Activity activity) {
                callback.OnCitySelect(cityInfo);
            }

            public final void onNothingSelected() {
            }
        };
        Intent intent = new Intent(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext(), SelectCityActivity_.class);
        intent.putExtra("from", 3);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(SelectCityActivity.EXTRA_TITLE_NAME, title);
        }
        if (currentCity != null) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENT_CITY, currentCity.city);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENT_ADCODE, currentCity.adCode);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENT_PROVINCE, currentCity.province);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENT_CITY_PINYIN, currentCity.pinyin);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENTCITY_VISIABLE, true);
        } else {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENTCITY_VISIABLE, false);
        }
        if (citys != null && !citys.isEmpty()) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CITY_LIST, new CityVOList(citys));
        }
        if (hotCitys != null && !hotCitys.isEmpty()) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_HOTCITY_LIST, new CityVOList(hotCitys));
        }
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_SHOW_HOT_CITY, showHotCitys);
        MicroApplication app = getMicroApplicationContext().findTopRunningApp();
        SpmUtils.addSourceAndBizTypeByTop(app, (Activity) getMicroApplicationContext().getTopActivity().get(), intent.getExtras());
        tryAddIntentFlag(intent);
        LauncherApplicationAgent.getInstance().getMicroApplicationContext().startActivity(app, intent);
    }

    public void callCitySelect(final ICitySelectCallBack callBack2, Bundle params) {
        callCitySelect((ICityCallBack) new ICityCallBack() {
            public final void onCitySelect(CityVO cityInfo, Activity activity) {
                callBack2.OnCitySelect(cityInfo);
                if (cityInfo != null) {
                    LoggerFactory.getTraceLogger().debug("CitySelectService", cityInfo.toString());
                }
            }

            public final void onNothingSelected() {
            }
        }, params);
    }

    public void callCitySelect(final ICitySelectCallBack2 callBack2, Bundle params) {
        callCitySelect((ICityCallBack) new ICityCallBack() {
            public final void onCitySelect(CityVO cityInfo, Activity activity) {
                callBack2.onCitySelect(cityInfo);
                if (cityInfo != null) {
                    LoggerFactory.getTraceLogger().debug("CitySelectService", cityInfo.toString());
                }
            }

            public final void onNothingSelected() {
                callBack2.onNothingSelected();
            }
        }, params);
    }

    public void callCitySelect(ICitySelectCallBack callBack2, Bundle params, List<CityVO> mainCityList2) {
        setMainCityList(params, mainCityList2);
        callCitySelect(callBack2, params);
    }

    public void callCitySelect(ICityCallBack callBack2, Bundle params, List<CityVO> mainCityList2) {
        setMainCityList(params, mainCityList2);
        callCitySelect(callBack2, params);
    }

    private void setMainCityList(Bundle params, List<CityVO> mainCityList2) {
        this.mainCityList = mainCityList2;
        params.putBoolean(SelectCityActivity.EXTRA_PARAM_USE_MAINCITYLIST, true);
    }

    public void callCitySelect(ICityCallBack callBack2, Bundle params) {
        this.callBack = callBack2;
        Intent intent = buildIntent(params);
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        microApplicationContext.startActivity(microApplicationContext.findTopRunningApp(), intent);
    }

    public void callCitySelect(CitySelectHandler handler, Bundle params) {
        this.callBack = handler;
        Intent intent = buildIntent(params);
        MicroApplicationContext microApplicationContext = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        microApplicationContext.startActivity(microApplicationContext.findTopRunningApp(), intent);
    }

    private Intent buildIntent(Bundle params) {
        Intent intent;
        SpmUtils.addSourceAndBizTypeByTop(getMicroApplicationContext().findTopRunningApp(), (Activity) getMicroApplicationContext().getTopActivity().get(), params);
        int tabCount = params.getInt(SelectCityActivity.EXTRA_PARAM_TAB_COUNT, -1);
        CityVO currentCity = (CityVO) params.getParcelable(SelectCityActivity.EXTRA_PARAM_CURRENT_CITY);
        CityVO locatedCity = (CityVO) params.getParcelable(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY);
        List historyCityList = params.getParcelableArrayList(SelectCityActivity.EXTRA_PARAM_HISTORY_CITY_LIST);
        List hotCityList = params.getParcelableArrayList(SelectCityActivity.EXTRA_PARAM_HOTCITY_LIST);
        List cityList = params.getParcelableArrayList(SelectCityActivity.EXTRA_PARAM_CITY_LIST);
        Serializable customCityMap = params.getSerializable(SelectCityActivity.EXTRA_PARAM_CUSTOM_CITY_MAP);
        String[] customSections = params.getStringArray(SelectCityActivity.EXTRA_PARAM_CUSTOM_SECTION_LIST);
        boolean currentCityVisible = params.getBoolean(SelectCityActivity.EXTRA_PARAM_CURRENTCITY_VISIABLE, false) || currentCity != null;
        boolean locatedCityVisible = params.getBoolean(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_VISIBLE, false) || locatedCity != null;
        boolean hotCityVisible = params.getBoolean(SelectCityActivity.EXTRA_PARAM_SHOW_HOT_CITY, false) || (hotCityList != null && !hotCityList.isEmpty());
        boolean historyCityVisible = params.getBoolean(SelectCityActivity.EXTRA_PARAM_SHOW_HISTORY_CITY, false) || (historyCityList != null && !historyCityList.isEmpty());
        boolean customCityVisible = params.getBoolean(SelectCityActivity.EXTRA_PARAM_SHOW_CUSTOM_CITY, false);
        boolean showSearchBar = params.getBoolean(SelectCityActivity.EXTRA_PARAM_SHOW_SEARCH_BAR, true);
        if (params.getBoolean("isTinyApp", false)) {
            intent = new Intent(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext(), SelectCityTinyActivity_.class);
        } else {
            intent = new Intent(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext(), SelectCityActivity_.class);
            tryAddIntentFlag(intent);
        }
        intent.putExtra("from", 3);
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_SHOW_SEARCH_BAR, showSearchBar);
        if (tabCount > 0) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_TAB_COUNT, tabCount);
        }
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENTCITY_VISIABLE, currentCityVisible);
        if (currentCity != null) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENT_CITY, currentCity.city);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENT_ADCODE, currentCity.adCode);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENT_CITY_PINYIN, currentCity.pinyin);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENT_PROVINCE, currentCity.province);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CURRENT_CITY_IS_MAINLAND, currentCity.isMainLand);
        }
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_VISIBLE, locatedCityVisible);
        if (locatedCity != null) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY, locatedCity.city);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_ADCODE, locatedCity.adCode);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_PINYIN, locatedCity.pinyin);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_PROVINCE, locatedCity.province);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_IS_MAINLAND, locatedCity.isMainLand);
        }
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_SHOW_HISTORY_CITY, historyCityVisible);
        if (historyCityList != null && !historyCityList.isEmpty()) {
            CityVOList cityVOList = new CityVOList(historyCityList);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_HISTORY_CITY_LIST, cityVOList);
        }
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_SHOW_HOT_CITY, hotCityVisible);
        if (hotCityList != null && !hotCityList.isEmpty()) {
            CityVOList cityVOList2 = new CityVOList(hotCityList);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_HOTCITY_LIST, cityVOList2);
        }
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_SHOW_CUSTOM_CITY, customCityVisible);
        if (customCityMap != null && (customCityMap instanceof HashMap)) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CUSTOM_CITY_MAP, customCityMap);
        }
        if (customSections != null) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CUSTOM_SECTION_LIST, customSections);
        }
        if (cityList != null && !cityList.isEmpty()) {
            CityVOList cityVOList3 = new CityVOList(cityList);
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CITY_LIST, cityVOList3);
        }
        String title = params.getString(SelectCityActivity.EXTRA_TITLE_NAME);
        if (!StringUtils.isEmpty(title)) {
            intent.putExtra(SelectCityActivity.EXTRA_TITLE_NAME, title);
        }
        String tab1 = params.getString(SelectCityActivity.EXTRA_PARAM_TAB1);
        String tab2 = params.getString(SelectCityActivity.EXTRA_PARAM_TAB2);
        if (!StringUtils.isEmpty(tab1)) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_TAB1, tab1);
        }
        if (!StringUtils.isEmpty(tab2)) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_TAB2, tab2);
        }
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_NEED_FINISH, params.getBoolean(SelectCityActivity.EXTRA_PARAM_NEED_FINISH, true));
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_NEED_REVERSE, params.getBoolean(SelectCityActivity.EXTRA_PARAM_NEED_REVERSE, false));
        for (String key : params.keySet()) {
            char c = 65535;
            switch (key.hashCode()) {
                case -1719591178:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_SECTION)) {
                        c = 9;
                        break;
                    }
                    break;
                case -1669533067:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_HISTORYCITYTITLE_OVERSEA)) {
                        c = 5;
                        break;
                    }
                    break;
                case -1292671235:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKNAME_OVERSEA)) {
                        c = 11;
                        break;
                    }
                    break;
                case -681605518:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_SEARCHBARHINT)) {
                        c = 0;
                        break;
                    }
                    break;
                case -617455141:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_HOT_CITY_SECTION)) {
                        c = 7;
                        break;
                    }
                    break;
                case -502680135:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_HISTORYCITYTITLE)) {
                        c = 2;
                        break;
                    }
                    break;
                case -438115042:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKTITLE_OVERSEA)) {
                        c = 10;
                        break;
                    }
                    break;
                case -427716745:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_LOCATEDCITYTITLE_OVERSEA)) {
                        c = 4;
                        break;
                    }
                    break;
                case 349932508:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_HOTCITYTITLE_OVERSEA)) {
                        c = 6;
                        break;
                    }
                    break;
                case 380637746:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_CITYSORT_DISABLE)) {
                        c = 16;
                        break;
                    }
                    break;
                case 506573044:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_HISTORY_CITY_SECTION)) {
                        c = 8;
                        break;
                    }
                    break;
                case 533702176:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_HOTCITYTITLE)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1052013755:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_LOCATEDCITYTITLE)) {
                        c = 3;
                        break;
                    }
                    break;
                case 1141000747:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKSECTION_OVERSEA)) {
                        c = 13;
                        break;
                    }
                    break;
                case 1242158956:
                    if (key.equals(SpmUtils.KEY_BEE_SOURCEPAGE)) {
                        c = 15;
                        break;
                    }
                    break;
                case 1549476875:
                    if (key.equals(SpmUtils.KEY_BEE_BIZTYPE)) {
                        c = 14;
                        break;
                    }
                    break;
                case 1828273077:
                    if (key.equals(SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKURL_OVERSEA)) {
                        c = 12;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    intent.putExtra(key, params.getString(key));
                    break;
                case 16:
                    intent.putExtra(key, params.getBoolean(key));
                    break;
            }
        }
        if (params.containsKey(SelectCityActivity.EXTRA_PARAM_USE_MAINCITYLIST)) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_USE_MAINCITYLIST, params.getBoolean(SelectCityActivity.EXTRA_PARAM_USE_MAINCITYLIST, false));
        }
        if (params.containsKey(SelectCityActivity.EXTRA_PARAM_PRESET_TABINDEX)) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_PRESET_TABINDEX, params.getInt(SelectCityActivity.EXTRA_PARAM_PRESET_TABINDEX));
        }
        if (params.containsKey(SelectCityActivity.EXTRA_PARAM_SET_LOCATED_CITY)) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_SET_LOCATED_CITY, params.getBoolean(SelectCityActivity.EXTRA_PARAM_SET_LOCATED_CITY));
        }
        intent.putExtra("serviceId", generateNextServiceId());
        return intent;
    }

    public void updateCityData(Bundle params) {
        MicroApplication app = getMicroApplicationContext().findTopRunningApp();
        SpmUtils.addSourceAndBizTypeByTop(app, (Activity) getMicroApplicationContext().getTopActivity().get(), params);
        int tabCount = params.getInt(SelectCityActivity.EXTRA_PARAM_TAB_COUNT, -1);
        List historyCityList = params.getParcelableArrayList(SelectCityActivity.EXTRA_PARAM_HISTORY_CITY_LIST);
        List hotCityList = params.getParcelableArrayList(SelectCityActivity.EXTRA_PARAM_HOTCITY_LIST);
        List cityList = params.getParcelableArrayList(SelectCityActivity.EXTRA_PARAM_CITY_LIST);
        boolean hotCityVisible = params.getBoolean(SelectCityActivity.EXTRA_PARAM_SHOW_HOT_CITY, true);
        boolean historyCityVisible = params.getBoolean(SelectCityActivity.EXTRA_PARAM_SHOW_HISTORY_CITY, true);
        Intent intent = new Intent(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getApplicationContext(), SelectCityActivity_.class);
        if (tabCount > 0) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_TAB_COUNT, tabCount);
        }
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_SHOW_HISTORY_CITY, historyCityVisible);
        if (historyCityList != null && !historyCityList.isEmpty()) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_HISTORY_CITY_LIST, new CityVOList(historyCityList));
        }
        intent.putExtra(SelectCityActivity.EXTRA_PARAM_SHOW_HOT_CITY, hotCityVisible);
        if (hotCityList != null && !hotCityList.isEmpty()) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_HOTCITY_LIST, new CityVOList(hotCityList));
        }
        if (cityList != null && !cityList.isEmpty()) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_CITY_LIST, new CityVOList(cityList));
        }
        if (params.containsKey(SelectCityActivity.EXTRA_PARAM_USE_MAINCITYLIST)) {
            intent.putExtra(SelectCityActivity.EXTRA_PARAM_USE_MAINCITYLIST, params.getBoolean(SelectCityActivity.EXTRA_PARAM_USE_MAINCITYLIST, false));
        }
        intent.putExtra(SpmUtils.KEY_BEE_BIZTYPE, params.getString(SpmUtils.KEY_BEE_BIZTYPE));
        intent.putExtra(SpmUtils.KEY_BEE_SOURCEPAGE, params.getString(SpmUtils.KEY_BEE_SOURCEPAGE));
        intent.putExtra("serviceId", generateNextServiceId());
        tryAddIntentFlag(intent);
        getMicroApplicationContext().startActivity(app, intent);
    }

    public void updateCityData(Bundle params, List<CityVO> mainCityList2) {
        setMainCityList(params, mainCityList2);
        updateCityData(params);
    }

    public void notifySetCitySelectView(Bundle params) {
        EventBusManager.getInstance().post(params, SelectCityActivity.EVENT_SET_CITY_SELECT_VIEW);
    }

    public void setModifyCallBack(IModifyCityCallBack callback) {
        this.modifyCallback = callback;
    }

    public IModifyCityCallBack getModifyCallBack() {
        return this.modifyCallback;
    }

    public ICityCallBack getCallBack() {
        return this.callBack;
    }

    public void callProvinceCitySelect(IProvinceCitySelectCallBack callback) {
        callProvinceCitySelect(callback, new Bundle());
    }

    public void callProvinceCitySelect(IProvinceCitySelectCallBack callback, Bundle params) {
        this.mProvinceCallBack = callback;
        MicroApplication app = getMicroApplicationContext().findTopRunningApp();
        Intent intent = new Intent();
        SpmUtils.addSourceAndBizTypeByTop(app, (Activity) getMicroApplicationContext().getTopActivity().get(), params);
        intent.putExtras(params);
        intent.putExtra(ProvinceCityListActivity.EXTRA_ISFROM_SERVICE, true);
        intent.setClass(getMicroApplicationContext().getApplicationContext(), ProvinceCityListActivity_.class);
        getMicroApplicationContext().startActivity(app, intent);
    }

    public IProvinceCitySelectCallBack getProvinceCallBack() {
        return this.mProvinceCallBack;
    }

    public void setCallBack(ICityCallBack callBack2) {
        this.callBack = callBack2;
    }

    public void setProvinceCallBack(IProvinceCitySelectCallBack mProvinceCallBack2) {
        this.mProvinceCallBack = mProvinceCallBack2;
    }

    public List<CityVO> getMainCityList() {
        return this.mainCityList;
    }

    public void openOrUpdateCitySelectUI(List<CityFragmentModel> cityFragmentModels2, Bundle extParams, final ICityCallBack callback) {
        this.cityFragmentModels = cityFragmentModels2;
        this.callBack = new ICityCallBack() {
            public final void onCitySelect(CityVO cityInfo, Activity activity) {
                if (callback != null) {
                    callback.onCitySelect(cityInfo, activity);
                }
                CitySelectServiceImpl.this.cityFragmentModels = null;
            }

            public final void onNothingSelected() {
                if (callback != null) {
                    callback.onNothingSelected();
                }
                CitySelectServiceImpl.this.cityFragmentModels = null;
            }
        };
        MicroApplication app = getMicroApplicationContext().findTopRunningApp();
        Intent intent = new Intent();
        SpmUtils.addSourceAndBizTypeByTop(app, (Activity) getMicroApplicationContext().getTopActivity().get(), extParams);
        intent.putExtras(extParams);
        intent.setClass(getMicroApplicationContext().getApplicationContext(), SelectAreaActivity_.class);
        tryAddIntentFlag(intent);
        getMicroApplicationContext().startActivity(app, intent);
    }

    public List<CityFragmentModel> getCityFragmentModels() {
        return this.cityFragmentModels;
    }

    private String generateNextServiceId() {
        this.citySelectServiceId = String.valueOf(this.citySelectServiceIdSeed);
        this.citySelectServiceIdSeed++;
        return this.citySelectServiceId;
    }

    private void tryAddIntentFlag(Intent intent) {
        if (isConfigSingleTopOpen()) {
            intent.setFlags(536870912);
        }
    }

    private boolean isConfigSingleTopOpen() {
        if (this.configStartSingleTop == null) {
            try {
                this.configStartSingleTop = Boolean.valueOf(true);
                String s = ((ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(ConfigService.class.getName())).getConfig("beehive_citySelectSingleTop");
                if (TextUtils.equals("false", s) || TextUtils.equals("0", s)) {
                    this.configStartSingleTop = Boolean.valueOf(false);
                }
            } catch (Exception ex) {
                LoggerFactory.getTraceLogger().warn((String) "CitySelectService", (Throwable) ex);
            }
        }
        return this.configStartSingleTop.booleanValue();
    }
}
