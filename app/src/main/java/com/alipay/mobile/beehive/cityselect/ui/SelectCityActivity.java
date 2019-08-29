package com.alipay.mobile.beehive.cityselect.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.antui.segement.AUSegment.TabSwitchListener;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.model.CityVOList;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService;
import com.alipay.mobile.beehive.cityselect.util.Constants;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.app.ui.BaseFragmentActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EActivity(resName = "activity_city_select")
public class SelectCityActivity extends BaseFragmentActivity {
    public static final String BROADCAST_CITY_CHANGE = "com.alipay.mobile.common.ui.SelectCityActivity.CITY_CHANGE";
    public static final String BROADCAST_UPDATE_CITYS = "com.alipay.mobile.common.ui.SelectCityActivity.UPDATE_CITYS";
    private static final int DEFAULT_TAB_COUNT = 1;
    public static final String EVENT_SET_CITY_SELECT_VIEW = "com.alipay.mobile.common.ui.SelectCityActivity.SET_VIEW";
    public static final String EXTRA_GOCITYLIST_FROM = "from";
    public static final String EXTRA_ISTINYAPP = "isTinyApp";
    public static final String EXTRA_PARAM_CITYSORT_DISABLE = "citysort_disable";
    public static final String EXTRA_PARAM_CITY_LIST = "cityList";
    public static final String EXTRA_PARAM_CURRENTCITY_VISIABLE = "currentCityVisible";
    public static final String EXTRA_PARAM_CURRENT_ADCODE = "currentAdCode";
    public static final String EXTRA_PARAM_CURRENT_CITY = "currentCity";
    public static final String EXTRA_PARAM_CURRENT_CITY_IS_MAINLAND = "currentCityIsMainLand";
    public static final String EXTRA_PARAM_CURRENT_CITY_PINYIN = "currentCityPinyin";
    public static final String EXTRA_PARAM_CURRENT_PROVINCE = "currentProvince";
    public static final String EXTRA_PARAM_CUSTOM_CITY_MAP = "customCityMap";
    public static final String EXTRA_PARAM_CUSTOM_SECTION_LIST = "customSectionList";
    public static final String EXTRA_PARAM_HISTORYCITYTITLE = "historycitytitle";
    public static final String EXTRA_PARAM_HISTORYCITYTITLE_OVERSEA = "historycitytitle_oversea";
    public static final String EXTRA_PARAM_HISTORY_CITY_LIST = "historyCityList";
    public static final String EXTRA_PARAM_HISTORY_CITY_SECTION = "cityselect_history_city_section";
    public static final String EXTRA_PARAM_HOTCITYTITLE = "hotcitytitle";
    public static final String EXTRA_PARAM_HOTCITYTITLE_OVERSEA = "hotcitytitle_oversea";
    public static final String EXTRA_PARAM_HOTCITY_LIST = "hotCityList";
    public static final String EXTRA_PARAM_HOT_CITY_SECTION = "cityselect_hot_city_section";
    public static final String EXTRA_PARAM_LOCATEDCITYTITLE = "locatedcitytitle";
    public static final String EXTRA_PARAM_LOCATEDCITYTITLE_OVERSEA = "locatedcitytitle_oversea";
    public static final String EXTRA_PARAM_LOCATED_CITY = "locatedCity";
    public static final String EXTRA_PARAM_LOCATED_CITY_ADCODE = "locatedCityAdcode";
    public static final String EXTRA_PARAM_LOCATED_CITY_IS_MAINLAND = "locatedCityIsMainLand";
    public static final String EXTRA_PARAM_LOCATED_CITY_PINYIN = "locatedCityPinyin";
    public static final String EXTRA_PARAM_LOCATED_CITY_PROVINCE = "locatedCityProvince";
    public static final String EXTRA_PARAM_LOCATED_CITY_SECTION = "cityselect_located_city_section";
    public static final String EXTRA_PARAM_LOCATED_CITY_VISIBLE = "locatedCityVisible";
    public static final String EXTRA_PARAM_NEED_FINISH = "needFinish";
    public static final String EXTRA_PARAM_NEED_REVERSE = "needReverse";
    public static final String EXTRA_PARAM_PRESET_TABINDEX = "preset_tab_index";
    public static final String EXTRA_PARAM_RECOMMENDLINKNAME_OVERSEA = "recommendlinkname_oversea";
    public static final String EXTRA_PARAM_RECOMMENDLINKSECTION_OVERSEA = "recommendlinksection_oversea";
    public static final String EXTRA_PARAM_RECOMMENDLINKTITLE_OVERSEA = "recommendlinktitle_oversea";
    public static final String EXTRA_PARAM_RECOMMENDLINKURL_OVERSEA = "recommendlinkurl_oversea";
    public static final String EXTRA_PARAM_SEARCHBARHINT = "searchbarhint";
    public static final String EXTRA_PARAM_SERVICE_ID = "serviceId";
    public static final String EXTRA_PARAM_SET_LOCATED_CITY = "setLocatedCity";
    public static final String EXTRA_PARAM_SHOW_CUSTOM_CITY = "customCityVisible";
    public static final String EXTRA_PARAM_SHOW_HISTORY_CITY = "historyCityVisible";
    public static final String EXTRA_PARAM_SHOW_HOT_CITY = "hotCityVisible";
    public static final String EXTRA_PARAM_SHOW_LOCATED_CITY = "showLocatedCity";
    public static final String EXTRA_PARAM_SHOW_SEARCH_BAR = "searchBarVisible";
    public static final String EXTRA_PARAM_TAB1 = "tab1";
    public static final String EXTRA_PARAM_TAB2 = "tab2";
    public static final String EXTRA_PARAM_TAB_COUNT = "tabCount";
    public static final String EXTRA_PARAM_USE_MAINCITYLIST = "useMainCityList";
    public static final String EXTRA_TITLE_NAME = "extraTitleName";
    public static final int ISFROM_FOR_BROADCAST = 2;
    public static final int ISFROM_FOR_CITY_SELECT_SERVICE = 3;
    public static final int ISFROM_FROMFORM = 1;
    public static final String SELCTCITY_FROM_CITYLIST = "selctcity_from_citylist";
    public static final String SELECT_ADCODE = "select_adcode";
    public static final String SELECT_CITY_IS_MAINLAND = "select_city_is_mainland";
    public static final String SELECT_CITY_PINYIN = "select_city_pinyin";
    public static final String SELECT_PROVINCE = "select_province";
    /* access modifiers changed from: private */
    public static final String TAG = new StringBuilder(Constants.BASE_TAG).append(SelectCityActivity.class.getSimpleName()).toString();
    private BroadcastReceiver broadcastReceiver;
    private List<CityVO> cityList = new ArrayList();
    private CityVO currentCity;
    private boolean currentCityVisible = false;
    /* access modifiers changed from: private */
    public int currentTabIndex = 0;
    private Map<String, List<CityVO>> customCityMap = new HashMap();
    private boolean customCityVisible = false;
    private String[] customSections;
    private List<CityVO> historyCityList = new ArrayList();
    private boolean historyCityVisible = false;
    private List<CityVO> hotCityList = new ArrayList();
    private boolean hotCityVisible = false;
    private int launchFrom;
    private CityVO locatedCity;
    private boolean locatedCityVisible = false;
    @ViewById(resName = "titleBar")
    protected AUTitleBar mTitleBar;
    private boolean needFinish = true;
    private boolean needReverse = false;
    private int presetTabIndex = 0;
    @ViewById(resName = "content")
    protected ViewGroup root;
    private boolean showSearchBar = true;
    private int tabCount;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoggerFactory.getTraceLogger().debug(TAG, "onCreate: intent=" + getIntent() + " activity=" + toString() + " taskId=" + getTaskId());
        Intent intent = getIntent();
        if (intent != null) {
            getDataFromIntent(intent);
        }
        this.broadcastReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                LoggerFactory.getTraceLogger().debug(SelectCityActivity.TAG, "received broadcast: action=BROADCAST_UPDATE_CITYS");
                LocalBroadcastManager.getInstance(context.getApplicationContext()).unregisterReceiver(this);
                SelectCityActivity.this.updateDataFromIntent(intent);
            }
        };
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.broadcastReceiver, getIntentFilter());
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LoggerFactory.getTraceLogger().debug(TAG, "onNewIntent: intent=" + intent + " activity=" + toString() + " taskId=" + getTaskId());
        updateDataFromIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    private void getDataFromIntent(Intent intent) {
        boolean z;
        LoggerFactory.getTraceLogger().debug(TAG, "getDataFromIntent: ");
        this.launchFrom = intent.getIntExtra("from", 1);
        this.tabCount = intent.getIntExtra(EXTRA_PARAM_TAB_COUNT, -1);
        this.showSearchBar = intent.getBooleanExtra(EXTRA_PARAM_SHOW_SEARCH_BAR, true);
        this.needFinish = intent.getBooleanExtra(EXTRA_PARAM_NEED_FINISH, true);
        this.needReverse = intent.getBooleanExtra(EXTRA_PARAM_NEED_REVERSE, false);
        this.presetTabIndex = intent.getIntExtra(EXTRA_PARAM_PRESET_TABINDEX, this.currentTabIndex);
        if (this.presetTabIndex < 0 || this.presetTabIndex > 1) {
            this.presetTabIndex = 0;
        }
        if (intent.getBooleanExtra(EXTRA_PARAM_CURRENTCITY_VISIABLE, false) || !TextUtils.isEmpty(intent.getStringExtra(EXTRA_PARAM_CURRENT_CITY))) {
            z = true;
        } else {
            z = false;
        }
        this.currentCityVisible = z;
        if (this.currentCityVisible) {
            this.currentCity = new CityVO();
            this.currentCity.city = intent.getStringExtra(EXTRA_PARAM_CURRENT_CITY);
            this.currentCity.pinyin = intent.getStringExtra(EXTRA_PARAM_CURRENT_CITY_PINYIN);
            this.currentCity.adCode = intent.getStringExtra(EXTRA_PARAM_CURRENT_ADCODE);
            this.currentCity.province = intent.getStringExtra(EXTRA_PARAM_CURRENT_PROVINCE);
            this.currentCity.isMainLand = intent.getBooleanExtra(EXTRA_PARAM_CURRENT_CITY_IS_MAINLAND, true);
        }
        this.locatedCityVisible = intent.getBooleanExtra(EXTRA_PARAM_LOCATED_CITY_VISIBLE, false) || !TextUtils.isEmpty(intent.getStringExtra(EXTRA_PARAM_LOCATED_CITY));
        if (this.locatedCityVisible) {
            this.locatedCity = new CityVO();
            this.locatedCity.city = intent.getStringExtra(EXTRA_PARAM_LOCATED_CITY);
            this.locatedCity.pinyin = intent.getStringExtra(EXTRA_PARAM_LOCATED_CITY_PINYIN);
            this.locatedCity.adCode = intent.getStringExtra(EXTRA_PARAM_LOCATED_CITY_ADCODE);
            this.locatedCity.province = intent.getStringExtra(EXTRA_PARAM_LOCATED_CITY_PROVINCE);
            this.locatedCity.isMainLand = intent.getBooleanExtra(EXTRA_PARAM_LOCATED_CITY_IS_MAINLAND, true);
        }
        if (intent.hasExtra(EXTRA_PARAM_CITY_LIST)) {
            Serializable data = intent.getSerializableExtra(EXTRA_PARAM_CITY_LIST);
            if (data != null && (data instanceof CityVOList)) {
                CityVOList cityVOList = (CityVOList) data;
                if (cityVOList.cityList != null && !cityVOList.cityList.isEmpty()) {
                    this.cityList = cityVOList.cityList;
                    LoggerFactory.getTraceLogger().debug(TAG, "get city list: size=" + this.cityList.size());
                }
            }
        }
        if (intent.getBooleanExtra(EXTRA_PARAM_USE_MAINCITYLIST, false)) {
            getMainCityListByService();
        }
        if (this.tabCount < 0) {
            this.tabCount = detectTabCountFromCityList(this.cityList);
            LoggerFactory.getTraceLogger().debug(TAG, "detectTabCountFromCityList: tabCount=" + this.tabCount);
        }
        this.historyCityVisible = intent.getBooleanExtra(EXTRA_PARAM_SHOW_HISTORY_CITY, false);
        if (this.historyCityVisible) {
            Serializable data2 = intent.getSerializableExtra(EXTRA_PARAM_HISTORY_CITY_LIST);
            if (data2 == null || !(data2 instanceof CityVOList)) {
                this.historyCityVisible = false;
            } else {
                CityVOList cityVOList2 = (CityVOList) data2;
                if (cityVOList2.cityList == null || cityVOList2.cityList.isEmpty()) {
                    this.historyCityVisible = false;
                } else {
                    this.historyCityList = cityVOList2.cityList;
                    LoggerFactory.getTraceLogger().debug(TAG, "historyCityVisible=true, get history city list: size=" + this.historyCityList.size());
                    for (CityVO cityVO : this.historyCityList) {
                        LoggerFactory.getTraceLogger().debug(TAG, cityVO.toString());
                    }
                }
            }
        }
        this.hotCityVisible = intent.getBooleanExtra(EXTRA_PARAM_SHOW_HOT_CITY, false);
        if (this.hotCityVisible) {
            Serializable data3 = intent.getSerializableExtra(EXTRA_PARAM_HOTCITY_LIST);
            if (data3 == null || !(data3 instanceof CityVOList)) {
                LoggerFactory.getTraceLogger().debug(TAG, "hotCityVisible=true, load default hot city list.");
                this.hotCityList = loadCityListFromLocal(R.array.hot_cities);
            } else {
                CityVOList cityVOList3 = (CityVOList) data3;
                if (cityVOList3.cityList != null && !cityVOList3.cityList.isEmpty()) {
                    this.hotCityList = cityVOList3.cityList;
                    LoggerFactory.getTraceLogger().debug(TAG, "hotCityVisible=true, get hot city list: size=" + this.hotCityList.size());
                }
            }
        }
        this.customCityVisible = intent.getBooleanExtra(EXTRA_PARAM_SHOW_CUSTOM_CITY, false);
        if (this.customCityVisible) {
            this.customSections = intent.getStringArrayExtra(EXTRA_PARAM_CUSTOM_SECTION_LIST);
            Serializable data4 = intent.getSerializableExtra(EXTRA_PARAM_CUSTOM_CITY_MAP);
            if (data4 != null && (data4 instanceof HashMap)) {
                Map tmpCustomCityMap = (Map) data4;
                if (!tmpCustomCityMap.isEmpty()) {
                    this.customCityMap = tmpCustomCityMap;
                    LoggerFactory.getTraceLogger().debug(TAG, "customCityVisible=true, get custom city map: size=" + this.customCityMap.size());
                }
            }
        }
        if (this.tabCount == 1 && this.currentCityVisible && this.currentCity != null) {
            this.locatedCityVisible = true;
            this.locatedCity = this.currentCity;
        }
    }

    private void getMainCityListByService() {
        CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
        if (citySelectService != null && citySelectService.getMainCityList() != null) {
            this.cityList = citySelectService.getMainCityList();
        }
    }

    /* access modifiers changed from: protected */
    @AfterViews
    public void afterViews() {
        if (this.root != null) {
            this.root.setBackgroundColor(-1);
        }
        initTab();
    }

    private void initTab() {
        initSwitchTab();
        if (this.tabCount > 1) {
            this.mTitleBar.setTitleText("");
            this.currentTabIndex = this.presetTabIndex;
            getSupportFragmentManager().beginTransaction().add(R.id.container, getFragmentByType(this.presetTabIndex), "frag#" + this.presetTabIndex).commitAllowingStateLoss();
            return;
        }
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(EXTRA_TITLE_NAME)) {
            this.mTitleBar.setTitleText(getString(R.string.city_select_title));
        } else {
            this.mTitleBar.setTitleText(intent.getStringExtra(EXTRA_TITLE_NAME));
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container, getFragmentByType(-1), null).commitAllowingStateLoss();
    }

    public void onDestroy() {
        super.onDestroy();
        LoggerFactory.getTraceLogger().debug(TAG, "onDestroy()");
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.broadcastReceiver);
    }

    private void initSwitchTab() {
        if (this.tabCount > 1) {
            Intent intent = getIntent();
            String tab1 = getString(R.string.cityselect_tab1);
            String tab2 = getString(R.string.cityselect_tab2);
            if (intent != null && !TextUtils.isEmpty(intent.getStringExtra(EXTRA_PARAM_TAB1))) {
                tab1 = intent.getStringExtra(EXTRA_PARAM_TAB1);
            }
            if (intent != null && !TextUtils.isEmpty(intent.getStringExtra(EXTRA_PARAM_TAB2))) {
                tab2 = intent.getStringExtra(EXTRA_PARAM_TAB2);
            }
            this.currentTabIndex = 0;
            this.mTitleBar.setSegment(new String[]{tab1, tab2}, new TabSwitchListener() {
                public final void onTabClick(int i, View view) {
                    LoggerFactory.getTraceLogger().debug(SelectCityActivity.TAG, "onTabClick: tab=" + i);
                    SelectCityActivity.this.currentTabIndex = i;
                    Fragment fragment = SelectCityActivity.this.getSupportFragmentManager().findFragmentByTag("frag#" + i);
                    if (fragment == null) {
                        fragment = SelectCityActivity.this.getFragmentByType(i);
                    }
                    SelectCityActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, "frag#" + i).commit();
                }
            });
        }
    }

    private int detectTabCountFromCityList(List<CityVO> cityList2) {
        if (cityList2 == null || cityList2.isEmpty()) {
            return 1;
        }
        for (CityVO cityVO : cityList2) {
            if (!cityVO.isMainLand) {
                return 2;
            }
        }
        return 1;
    }

    private List<CityVO> loadCityListFromLocal(int resId) {
        List cityList2 = new ArrayList();
        Set overseaPrefixes = getOverseaCityPrefix();
        for (String line : getResources().getStringArray(resId)) {
            CityVO cityVO = new CityVO();
            String[] bits = line.split(",");
            if (bits.length >= 2) {
                cityVO.adCode = bits[0];
                cityVO.city = bits[1];
                if (bits.length >= 3) {
                    cityVO.pinyin = bits[2];
                }
                if (TextUtils.isEmpty(cityVO.adCode) || cityVO.adCode.length() <= 1 || !overseaPrefixes.contains(cityVO.adCode.substring(0, 2))) {
                    cityVO.isMainLand = true;
                } else {
                    cityVO.isMainLand = false;
                }
                cityList2.add(cityVO);
            }
        }
        return cityList2;
    }

    private Set<String> getOverseaCityPrefix() {
        Set result = new HashSet();
        String[] prefixes = getResources().getStringArray(R.array.oversea_cities_adcode_prefix);
        if (prefixes != null) {
            result.addAll(Arrays.asList(prefixes));
        }
        return result;
    }

    /* access modifiers changed from: private */
    public SelectCityFragment getFragmentByType(int type) {
        SelectCityFragment fragment = new SelectCityFragment_();
        fragment.setArgs(type, this.launchFrom, this.showSearchBar, this.cityList, this.historyCityVisible, this.historyCityList, this.hotCityVisible, this.hotCityList, this.customCityVisible, this.customCityMap, this.customSections, this.currentCityVisible, this.currentCity, this.locatedCityVisible, this.locatedCity, this.needFinish, this.needReverse, getIntent(), this);
        return fragment;
    }

    private IntentFilter getIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_UPDATE_CITYS);
        return intentFilter;
    }

    /* access modifiers changed from: private */
    public void updateDataFromIntent(Intent intent) {
        LoggerFactory.getTraceLogger().debug(TAG, "updateDataFromIntent: ");
        CityVOList cityVOList = null;
        CityVOList hotCityVOList = null;
        CityVOList historyCityVOList = null;
        if (intent.hasExtra(EXTRA_PARAM_CITY_LIST)) {
            Serializable data = intent.getSerializableExtra(EXTRA_PARAM_CITY_LIST);
            if (data instanceof CityVOList) {
                cityVOList = (CityVOList) data;
            }
        }
        if (intent.getBooleanExtra(EXTRA_PARAM_USE_MAINCITYLIST, false)) {
            getMainCityListByService();
        }
        this.hotCityVisible = intent.getBooleanExtra(EXTRA_PARAM_SHOW_HOT_CITY, true);
        if (this.hotCityVisible && intent.hasExtra(EXTRA_PARAM_HOTCITY_LIST)) {
            Serializable data2 = intent.getSerializableExtra(EXTRA_PARAM_HOTCITY_LIST);
            if (data2 instanceof CityVOList) {
                hotCityVOList = (CityVOList) data2;
            }
        }
        this.historyCityVisible = intent.getBooleanExtra(EXTRA_PARAM_SHOW_HISTORY_CITY, true);
        if (this.historyCityVisible && intent.hasExtra(EXTRA_PARAM_HISTORY_CITY_LIST)) {
            Serializable data3 = intent.getSerializableExtra(EXTRA_PARAM_HISTORY_CITY_LIST);
            if (data3 instanceof CityVOList) {
                historyCityVOList = (CityVOList) data3;
            }
        }
        if (!(cityVOList == null || cityVOList.cityList == null)) {
            LoggerFactory.getTraceLogger().debug(TAG, "update city list from intent, size=" + cityVOList.cityList.size());
            this.cityList = cityVOList.cityList;
        }
        if (!(!this.hotCityVisible || hotCityVOList == null || hotCityVOList.cityList == null)) {
            LoggerFactory.getTraceLogger().debug(TAG, "update hot city list from intent, size=" + hotCityVOList.cityList.size());
            this.hotCityList = hotCityVOList.cityList;
        }
        if (!(!this.historyCityVisible || historyCityVOList == null || historyCityVOList.cityList == null)) {
            LoggerFactory.getTraceLogger().debug(TAG, "update history city list from intent, size=" + historyCityVOList.cityList.size());
            this.historyCityList = historyCityVOList.cityList;
            for (CityVO cityVO : this.historyCityList) {
                LoggerFactory.getTraceLogger().debug(TAG, cityVO.toString());
            }
        }
        int newTabCount = intent.getIntExtra(EXTRA_PARAM_TAB_COUNT, -1);
        if (newTabCount != -1) {
            this.tabCount = newTabCount;
        }
        this.presetTabIndex = intent.getIntExtra(EXTRA_PARAM_PRESET_TABINDEX, this.currentTabIndex);
        initTab();
    }

    public void onBackPressed() {
        super.onBackPressed();
        CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
        if (citySelectService != null && citySelectService.getCallBack() != null) {
            citySelectService.getCallBack().onNothingSelected();
        }
    }
}
