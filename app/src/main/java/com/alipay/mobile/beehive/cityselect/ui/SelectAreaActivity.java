package com.alipay.mobile.beehive.cityselect.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.antui.segement.AUSegment.TabSwitchListener;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityFragmentModel;
import com.alipay.mobile.beehive.cityselect.model.CityPageModel;
import com.alipay.mobile.beehive.cityselect.model.CityTabModel;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.ICityCallBack;
import com.alipay.mobile.beehive.cityselect.util.CityUtils;
import com.alipay.mobile.beehive.cityselect.util.Constants;
import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.beehive.eventbus.Subscribe;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseFragmentActivity;
import com.alipay.mobile.beehive.util.KeyBoardUtil;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;

@EActivity(resName = "activity_area_select")
public class SelectAreaActivity extends BeehiveBaseFragmentActivity {
    /* access modifiers changed from: private */
    public static final String TAG = new StringBuilder(Constants.BASE_TAG).append(SelectAreaActivity.class.getSimpleName()).toString();
    protected List<CityFragmentModel> mCityFragmentModels;
    private CitySelectService mCitySelectService;
    @ViewById(resName = "container")
    protected AUFrameLayout mContainer;
    protected Bundle mExtParams;
    @ViewById(resName = "titleBar")
    protected AUTitleBar mTitleBar;
    private int presetTabIndex;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCitySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
        EventBusManager.getInstance().register(this);
    }

    /* access modifiers changed from: protected */
    @AfterViews
    public void init() {
        init(null);
    }

    /* access modifiers changed from: protected */
    public void init(Intent intent) {
        handleParams(intent);
        if (this.mCityFragmentModels == null || this.mCityFragmentModels.isEmpty()) {
            this.mCityFragmentModels = createDefaultCityFragments(this.mExtParams);
        }
        if (this.mCityFragmentModels.size() > 1) {
            this.presetTabIndex = this.mExtParams.getInt(SelectCityActivity.EXTRA_PARAM_PRESET_TABINDEX, 0);
            String[] tabNames = new String[this.mCityFragmentModels.size()];
            for (int i = 0; i < this.mCityFragmentModels.size(); i++) {
                CityFragmentModel cityFragmentModel = this.mCityFragmentModels.get(i);
                tabNames[i] = cityFragmentModel.name;
                if (i == this.presetTabIndex) {
                    addFragment(i, cityFragmentModel, true);
                }
            }
            this.mTitleBar.setSegment(tabNames, new TabSwitchListener() {
                public final void onTabClick(int i, View view) {
                    LoggerFactory.getTraceLogger().debug(SelectAreaActivity.TAG, "onTabClick: tab=" + i);
                    if (i < SelectAreaActivity.this.mCityFragmentModels.size()) {
                        SelectAreaActivity.this.addFragment(i, SelectAreaActivity.this.mCityFragmentModels.get(i), false);
                        KeyBoardUtil.hideKeyBoard(SelectAreaActivity.this, SelectAreaActivity.this.mTitleBar);
                        return;
                    }
                    LoggerFactory.getTraceLogger().warn(SelectAreaActivity.TAG, (String) "onTabClick index bigger than models size");
                }
            });
            this.mTitleBar.getSegment().setCurrentSelTab(this.presetTabIndex);
            return;
        }
        CityFragmentModel cityFragmentModel2 = this.mCityFragmentModels.get(0);
        this.mTitleBar.setTitleText(cityFragmentModel2.name);
        addFragment(0, cityFragmentModel2, true);
    }

    /* access modifiers changed from: protected */
    public void handleParams(Intent intent) {
        if (intent == null) {
            intent = getIntent();
        }
        this.mExtParams = intent.getExtras();
        if (this.mExtParams == null) {
            this.mExtParams = new Bundle();
        }
        this.mCityFragmentModels = this.mCitySelectService.getCityFragmentModels();
    }

    /* access modifiers changed from: private */
    public void addFragment(int i, CityFragmentModel cityFragmentModel, boolean needNew) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("frag#" + i);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, getFragment(cityFragmentModel, this.mExtParams), "frag#" + i).commitAllowingStateLoss();
        } else if (needNew) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, getFragment(cityFragmentModel, this.mExtParams), "frag#" + i).commitAllowingStateLoss();
        } else {
            if (fragment instanceof SelectAreaFragment) {
                ((SelectAreaFragment) fragment).reset();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, "frag#" + i).commitAllowingStateLoss();
        }
    }

    private List<CityFragmentModel> createDefaultCityFragments(Bundle extParams) {
        List cityFragmentModels = new ArrayList();
        CityFragmentModel cityFragmentModel = new CityFragmentModel();
        cityFragmentModel.name = extParams.getString(SelectCityActivity.EXTRA_TITLE_NAME, getString(R.string.city_select_title));
        CityPageModel cityPageModel = new CityPageModel();
        cityPageModel.cityTabModels.add(createLocateCityTabModel(extParams));
        cityPageModel.cityTabModels.add(createDefaultHotCityTabModel(extParams));
        cityFragmentModel.cityPageModels.add(cityPageModel);
        cityFragmentModels.add(cityFragmentModel);
        return cityFragmentModels;
    }

    @NonNull
    private CityTabModel createLocateCityTabModel(Bundle extParams) {
        CityTabModel locateTabModel = new CityTabModel();
        locateTabModel.name = extParams.getString(SelectCityActivity.EXTRA_PARAM_LOCATEDCITYTITLE, getString(R.string.cityselect_located_city_title));
        locateTabModel.navName = extParams.getString(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_SECTION, getString(R.string.cityselect_located_city_section));
        locateTabModel.needSearch = false;
        locateTabModel.type = 1;
        return locateTabModel;
    }

    @NonNull
    private CityTabModel createDefaultHotCityTabModel(Bundle extParams) {
        CityTabModel hotTabModel = new CityTabModel();
        hotTabModel.name = extParams.getString(SelectCityActivity.EXTRA_PARAM_HOTCITYTITLE, getString(R.string.cityselect_hot_city_title));
        hotTabModel.navName = extParams.getString(SelectCityActivity.EXTRA_PARAM_HOT_CITY_SECTION, getString(R.string.cityselect_hot_city_section));
        hotTabModel.needSearch = false;
        hotTabModel.cityVOs = CityUtils.loadCityListFromLocal(this, R.array.hot_cities, 0);
        hotTabModel.type = 0;
        return hotTabModel;
    }

    private Fragment getFragment(CityFragmentModel cityFragmentModel, Bundle extParams) {
        SelectAreaFragment fragment = new SelectAreaFragment_();
        fragment.setArgs(cityFragmentModel, extParams, getCallBack());
        return fragment;
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        init(intent);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    public void onBackPressed() {
        super.onBackPressed();
        ICityCallBack cityCallBack = getCallBack();
        if (cityCallBack != null) {
            cityCallBack.onNothingSelected();
        }
    }

    /* access modifiers changed from: protected */
    public ICityCallBack getCallBack() {
        CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
        if (citySelectService != null) {
            return citySelectService.getCallBack();
        }
        return null;
    }

    @Subscribe(name = "EVENT_SUB_AREA_SELECTED")
    public void onLoadEvent(LoadData loadData) {
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBusManager.getInstance().unregister(this);
        KeyBoardUtil.hideKeyBoard(this, this.mTitleBar);
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b6336";
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }
}
