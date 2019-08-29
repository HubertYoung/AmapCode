package com.alipay.mobile.beehive.cityselect.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AUSearchInputBox;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.api.BeehiveService;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.CitySelectHandler;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.IModifyCityCallBack;
import com.alipay.mobile.beehive.cityselect.util.CityComparator;
import com.alipay.mobile.beehive.cityselect.util.CityFilter;
import com.alipay.mobile.beehive.cityselect.util.SectionComparator;
import com.alipay.mobile.beehive.cityselect.view.CityBladeView;
import com.alipay.mobile.beehive.cityselect.view.CityBladeView.OnCityBladeViewItemClickListener;
import com.alipay.mobile.beehive.cityselect.view.ExpandableGridView;
import com.alipay.mobile.beehive.eventbus.EventBusManager;
import com.alipay.mobile.beehive.eventbus.IEventSubscriber;
import com.alipay.mobile.beehive.eventbus.ThreadMode;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseFragment;
import com.alipay.mobile.beehive.util.JumpUtil;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationRequest;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.iprank.dao.IpRankSql;
import com.alipay.mobile.common.utils.LogCatUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.LBSLocationManagerService;
import com.alipay.mobile.framework.service.OnLBSLocationListener;
import com.alipay.mobile.rome.longlinkservice.LongLinkMsgConstants;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EFragment(resName = "fragment_city_select")
public class SelectCityFragment extends BeehiveBaseFragment implements TextWatcher {
    private static final String FORMAT = "^[a-z,A-Z].*$";
    public static final String TAG = "cityselect_SelectCityFragment";
    public static final int TYPE_DEFAULT = -1;
    public static final int TYPE_HOME = 0;
    public static final int TYPE_OVERSEA = 1;
    private String HISTORY_CITY_SECTION;
    private String HOT_CITY_SECTION;
    private String LOCATED_CITY_SECTION;
    private List<CityVO> cityList;
    private boolean citySortDisable;
    private Map<String, List<CityVO>> customCityMap;
    private boolean customCityVisible = false;
    private String[] customSections;
    private IEventSubscriber eventHandler;
    private List<CityVO> historyCityList;
    private boolean historyCityVisible = true;
    private List<CityVO> hotCityList;
    private boolean hotCityVisible = true;
    private boolean isSetLocatedCity;
    private int launchFrom = 0;
    /* access modifiers changed from: private */
    public CityVO locatedCity;
    private boolean locatedCityVisible = true;
    private CityListAdapter mAdapter;
    /* access modifiers changed from: private */
    public BeehiveService mBeehiveService;
    @ViewById(resName = "city_list")
    protected AUListView mCityListView;
    protected ViewGroup mHistoryCityLayout;
    protected ViewGroup mHotCityLayout;
    /* access modifiers changed from: private */
    public Map<String, Integer> mIndexer = new HashMap();
    protected ExpandableGridView mLocatedCityGrid;
    protected ViewGroup mLocatedCityLayout;
    protected AUTextView mLocationFailTv;
    private List<Integer> mPositions = new ArrayList();
    protected ViewGroup mRecommendLinkLayout;
    @ViewById(resName = "search_bar")
    protected AUSearchInputBox mSearchBar;
    @ViewById(resName = "search_no_result")
    protected AUTextView mSearchNoResult;
    @ViewById(resName = "section_list")
    protected CityBladeView mSectionBladeView;
    private List<String> mSections = new ArrayList();
    private boolean needFinish = true;
    private List<CityVO> originCityList;
    private String presetHistorycitytitle;
    private String presetHotcitytitle;
    private String presetLocatedcitytitle;
    private String recommendLinkName;
    private String recommendLinkSection;
    private String recommendLinkTitle;
    /* access modifiers changed from: private */
    public String recommendLinkUrl;
    private String searchBarHint;
    /* access modifiers changed from: private */
    public String serviceId;
    private boolean showSearchBar;
    private int type = -1;

    public void setArgs(int type2, int launchFrom2, boolean showSearchBar2, List<CityVO> cityList2, boolean historyCityVisible2, List<CityVO> historyCityList2, boolean hotCityVisible2, List<CityVO> hotCityList2, boolean customCityVisible2, Map<String, List<CityVO>> customCityMap2, String[] customSections2, boolean currentCityVisible, CityVO currentCity, boolean locatedCityVisible2, CityVO locatedCity2, boolean needFinish2, boolean needReverse, Intent intent, Activity activity) {
        LoggerFactory.getTraceLogger().debug(TAG, "setArgs: type=" + type2 + ", launchFrom=" + launchFrom2);
        this.type = type2;
        this.showSearchBar = showSearchBar2;
        this.locatedCityVisible = locatedCityVisible2;
        this.historyCityVisible = historyCityVisible2;
        this.hotCityVisible = hotCityVisible2;
        this.locatedCity = locatedCity2;
        this.launchFrom = launchFrom2;
        this.originCityList = cityList2;
        this.customCityVisible = customCityVisible2;
        this.customCityMap = customCityMap2;
        this.customSections = customSections2;
        this.needFinish = needFinish2;
        this.searchBarHint = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_SEARCHBARHINT, activity.getString(R.string.city_search_box_hint));
        this.HOT_CITY_SECTION = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_HOT_CITY_SECTION, activity.getString(R.string.cityselect_hot_city_section));
        this.citySortDisable = intent.getBooleanExtra(SelectCityActivity.EXTRA_PARAM_CITYSORT_DISABLE, false);
        this.HISTORY_CITY_SECTION = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_HISTORY_CITY_SECTION, activity.getString(R.string.cityselect_history_city_section));
        this.LOCATED_CITY_SECTION = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_SECTION, activity.getString(R.string.cityselect_located_city_section));
        this.isSetLocatedCity = intent.getBooleanExtra(SelectCityActivity.EXTRA_PARAM_SET_LOCATED_CITY, false);
        this.serviceId = getValueFromIntent(intent, "serviceId", "");
        getParamsByType(type2, cityList2, historyCityList2, hotCityList2, intent, activity);
    }

    private void getParamsByType(int type2, List<CityVO> cityList2, List<CityVO> historyCityList2, List<CityVO> hotCityList2, Intent intent, Activity activity) {
        if (type2 == 1) {
            this.cityList = filterCityListByMainLand(cityList2, false);
            this.hotCityList = filterCityListByMainLand(hotCityList2, false);
            this.historyCityList = filterCityListByMainLand(historyCityList2, false);
            this.presetLocatedcitytitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_LOCATEDCITYTITLE_OVERSEA, activity.getString(R.string.cityselect_located_city_title));
            this.presetHistorycitytitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_HISTORYCITYTITLE_OVERSEA, activity.getString(R.string.cityselect_history_city_title));
            this.presetHotcitytitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_HOTCITYTITLE_OVERSEA, activity.getString(R.string.cityselect_oversea_hot_city_title));
            this.recommendLinkTitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKTITLE_OVERSEA, "");
            this.recommendLinkName = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKNAME_OVERSEA, "");
            this.recommendLinkUrl = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKURL_OVERSEA, "");
            this.recommendLinkSection = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKSECTION_OVERSEA, "");
        } else if (type2 == 0) {
            this.cityList = filterCityListByMainLand(cityList2, true);
            this.hotCityList = filterCityListByMainLand(hotCityList2, true);
            this.historyCityList = filterCityListByMainLand(historyCityList2, true);
            this.presetLocatedcitytitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_LOCATEDCITYTITLE, activity.getString(R.string.cityselect_located_city_title));
            this.presetHistorycitytitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_HISTORYCITYTITLE, activity.getString(R.string.cityselect_history_city_title));
            this.presetHotcitytitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_HOTCITYTITLE, activity.getString(R.string.cityselect_domestic_hot_city_title));
        } else {
            this.cityList = new ArrayList(cityList2);
            this.hotCityList = new ArrayList(hotCityList2);
            this.historyCityList = new ArrayList(historyCityList2);
            this.presetLocatedcitytitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_LOCATEDCITYTITLE, activity.getString(R.string.cityselect_located_city_title));
            this.presetHistorycitytitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_HISTORYCITYTITLE, activity.getString(R.string.cityselect_history_city_title));
            this.presetHotcitytitle = getValueFromIntent(intent, SelectCityActivity.EXTRA_PARAM_HOTCITYTITLE, activity.getString(R.string.cityselect_hot_city_title));
        }
    }

    private String getValueFromIntent(Intent intent, String key, String defaultValue) {
        if (intent == null) {
            return defaultValue;
        }
        String value = intent.getStringExtra(key);
        if (TextUtils.isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoggerFactory.getTraceLogger().debug(TAG, "onCreate()");
        this.mBeehiveService = (BeehiveService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(BeehiveService.class.getName());
        this.eventHandler = new IEventSubscriber() {
            public final void onEvent(String eventName, Object data) {
                LoggerFactory.getTraceLogger().info(SelectCityFragment.TAG, String.format("receive event: %s,%s", new Object[]{eventName, data}));
                if (data instanceof Bundle) {
                    String paramServiceId = ((Bundle) data).getString("serviceId");
                    if (!TextUtils.equals(paramServiceId, SelectCityFragment.this.serviceId)) {
                        LoggerFactory.getTraceLogger().info(SelectCityFragment.TAG, String.format("wrong serviceId:%s", new Object[]{paramServiceId}));
                        CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
                        if (citySelectService != null) {
                            IModifyCityCallBack mfCallback = citySelectService.getModifyCallBack();
                            if (mfCallback != null) {
                                mfCallback.onModifyFailed(13);
                            }
                        }
                    } else if (TextUtils.equals(eventName, SelectCityActivity.EVENT_SET_CITY_SELECT_VIEW)) {
                        SelectCityFragment.this.updateViewFromEvent(data);
                    }
                }
            }
        };
        EventBusManager.getInstance().register(this.eventHandler, ThreadMode.UI, SelectCityActivity.EVENT_SET_CITY_SELECT_VIEW);
    }

    public void onDestroy() {
        super.onDestroy();
        EventBusManager.getInstance().unregister(this.eventHandler, SelectCityActivity.EVENT_SET_CITY_SELECT_VIEW);
    }

    /* access modifiers changed from: protected */
    @AfterViews
    public void afterViews() {
        this.mCityListView.setVisibility(8);
        this.mSearchBar.setVisibility(8);
        this.mSectionBladeView.setVisibility(8);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showCityList();
    }

    /* access modifiers changed from: protected */
    public void showCityList() {
        LoggerFactory.getTraceLogger().debug(TAG, "showCityList: ");
        if (this.cityList == null || this.cityList.isEmpty()) {
            loadCitys();
        } else {
            prepareDataAndInitView(this.cityList);
        }
    }

    private ViewGroup getCityGridLayout(String title, List<CityVO> data) {
        ViewGroup cityGridLayout = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.cityselect_city_grid_layout, this.mCityListView, false);
        ((AUTextView) cityGridLayout.findViewById(R.id.city_grid_title)).setText(title);
        ExpandableGridView gridView = (ExpandableGridView) cityGridLayout.findViewById(R.id.city_grid);
        gridView.setExpanded(true);
        gridView.setAdapter(new CityGridAdapter(getActivity(), data, this));
        return cityGridLayout;
    }

    /* access modifiers changed from: protected */
    @Background
    public void loadCitys() {
        LoggerFactory.getTraceLogger().debug(TAG, "got empty cityList, use built-in data instead.");
        List cityList2 = loadCityListFromLocal(R.array.cities_from_gaode);
        this.originCityList = new ArrayList(cityList2);
        if (this.type == 1) {
            cityList2 = filterCityListByMainLand(cityList2, false);
        }
        prepareDataAndInitView(cityList2);
    }

    /* access modifiers changed from: protected */
    @Background
    @SuppressLint({"DefaultLocale"})
    public void prepareDataAndInitView(List<CityVO> cityList2) {
        LoggerFactory.getTraceLogger().debug(TAG, "prepareDataAndInitView()");
        if (cityList2 != null && !cityList2.isEmpty()) {
            List tmpCityList = new ArrayList(cityList2);
            List tmpSections = new ArrayList();
            Map tmpMap = new HashMap();
            List tmpPositions = new ArrayList();
            Map tmpIndexer = new HashMap();
            if (!this.citySortDisable) {
                Collections.sort(tmpCityList, new CityComparator());
            }
            for (int i = 0; i < tmpCityList.size(); i++) {
                if (!TextUtils.isEmpty(((CityVO) tmpCityList.get(i)).pinyin)) {
                    String firstName = ((CityVO) tmpCityList.get(i)).pinyin.substring(0, 1).toUpperCase();
                    if (firstName.matches(FORMAT)) {
                        if (tmpSections.contains(firstName)) {
                            ((List) tmpMap.get(firstName)).add(tmpCityList.get(i));
                        } else {
                            tmpSections.add(firstName);
                            List list = new ArrayList();
                            list.add(tmpCityList.get(i));
                            tmpMap.put(firstName, list);
                        }
                    }
                }
                if (tmpSections.contains(MetaRecord.LOG_SEPARATOR)) {
                    ((List) tmpMap.get(MetaRecord.LOG_SEPARATOR)).add(tmpCityList.get(i));
                } else {
                    tmpSections.add(MetaRecord.LOG_SEPARATOR);
                    List list2 = new ArrayList();
                    list2.add(tmpCityList.get(i));
                    tmpMap.put(MetaRecord.LOG_SEPARATOR, list2);
                }
            }
            Collections.sort(tmpSections, new SectionComparator());
            if (!(!this.customCityVisible || this.customCityMap == null || this.customSections == null)) {
                for (int i2 = this.customSections.length - 1; i2 >= 0; i2--) {
                    String section = this.customSections[i2];
                    List cityVOList = this.customCityMap.get(section);
                    if (!TextUtils.isEmpty(section) && cityVOList != null && !cityVOList.isEmpty()) {
                        tmpCityList.addAll(0, cityVOList);
                        tmpSections.add(0, section);
                        tmpMap.put(section, cityVOList);
                    }
                }
            }
            if (this.hotCityVisible && this.hotCityList != null && !this.hotCityList.isEmpty()) {
                if (!tmpSections.contains(this.HOT_CITY_SECTION)) {
                    tmpSections.add(0, this.HOT_CITY_SECTION);
                }
                tmpMap.put(this.HOT_CITY_SECTION, getListOne(new CityVO()));
            }
            if (!TextUtils.isEmpty(this.recommendLinkTitle) && !TextUtils.isEmpty(this.recommendLinkName) && !TextUtils.isEmpty(this.recommendLinkUrl) && !TextUtils.isEmpty(this.recommendLinkSection)) {
                if (!tmpSections.contains(this.recommendLinkSection)) {
                    tmpSections.add(0, this.recommendLinkSection);
                }
                tmpMap.put(this.recommendLinkSection, getListOne(new CityVO()));
            }
            if (this.historyCityVisible && this.historyCityList != null && !this.historyCityList.isEmpty()) {
                if (!tmpSections.contains(this.HISTORY_CITY_SECTION)) {
                    tmpSections.add(0, this.HISTORY_CITY_SECTION);
                }
                tmpMap.put(this.HISTORY_CITY_SECTION, getListOne(new CityVO()));
            }
            if (this.locatedCityVisible) {
                if (!tmpSections.contains(this.LOCATED_CITY_SECTION)) {
                    tmpSections.add(0, this.LOCATED_CITY_SECTION);
                }
                tmpMap.put(this.LOCATED_CITY_SECTION, getListOne(new CityVO()));
            }
            if (tmpSections.contains(MetaRecord.LOG_SEPARATOR)) {
                tmpSections.remove(MetaRecord.LOG_SEPARATOR);
                tmpSections.add(MetaRecord.LOG_SEPARATOR);
            }
            int position = 0;
            List tmpCityList2 = new ArrayList();
            for (int i3 = 0; i3 < tmpSections.size(); i3++) {
                tmpIndexer.put(tmpSections.get(i3), Integer.valueOf(position));
                tmpPositions.add(Integer.valueOf(position));
                if (tmpMap.get(tmpSections.get(i3)) != null) {
                    position += ((List) tmpMap.get(tmpSections.get(i3))).size();
                    List tempList = (List) tmpMap.get(tmpSections.get(i3));
                    if (!tempList.isEmpty() && !TextUtils.isEmpty(((CityVO) tempList.get(0)).city)) {
                        tmpCityList2.addAll((Collection) tmpMap.get(tmpSections.get(i3)));
                    }
                }
            }
            this.cityList = tmpCityList2;
            this.mSections = tmpSections;
            this.mPositions = tmpPositions;
            this.mIndexer = tmpIndexer;
            initView();
        }
    }

    /* access modifiers changed from: protected */
    @UiThread
    public void initView() {
        String title;
        LoggerFactory.getTraceLogger().debug(TAG, "initView()");
        removeAllHeaderViews();
        this.mSearchBar.getSearchEditView().addTextChangedListener(this);
        this.mSearchBar.getSearchEditView().setHint(this.searchBarHint);
        this.mSectionBladeView.setSections(this.mSections);
        this.mSectionBladeView.setOnItemClickListener(new OnCityBladeViewItemClickListener() {
            public final void onItemClick(String s) {
                if (SelectCityFragment.this.mIndexer.get(s) != null) {
                    SelectCityFragment.this.mCityListView.setSelection(((Integer) SelectCityFragment.this.mIndexer.get(s)).intValue());
                }
            }
        });
        if (this.locatedCityVisible) {
            if (this.mLocatedCityLayout == null) {
                this.mLocatedCityLayout = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.cityselect_located_city_layout, this.mCityListView, false);
            }
            AUTextView locatedCityTitle = (AUTextView) this.mLocatedCityLayout.findViewById(R.id.city_grid_title);
            if (!TextUtils.isEmpty(this.presetLocatedcitytitle)) {
                locatedCityTitle.setText(this.presetLocatedcitytitle);
            } else {
                locatedCityTitle.setText(getString(R.string.cityselect_located_city_title));
            }
            this.mLocatedCityGrid = (ExpandableGridView) this.mLocatedCityLayout.findViewById(R.id.city_grid);
            this.mLocationFailTv = (AUTextView) this.mLocatedCityLayout.findViewById(R.id.location_fail);
            List tmpCityList = new ArrayList();
            tmpCityList.add(new CityVO());
            this.mLocatedCityGrid.setAdapter(new CityGridAdapter(getActivity(), tmpCityList, this));
            if (this.locatedCity == null || TextUtils.isEmpty(this.locatedCity.city)) {
                startLocation();
            } else {
                tryUpdateLocatedCityText();
            }
            this.mLocationFailTv.setOnClickListener(new OnClickListener() {
                public final void onClick(View v) {
                    if (SelectCityFragment.this.locatedCity != null && !TextUtils.isEmpty(SelectCityFragment.this.locatedCity.city)) {
                        return;
                    }
                    if (SelectCityFragment.this.needShowLPSBtn()) {
                        SelectCityFragment.this.mBeehiveService.getLocationPermissionSettingExecutor().jumpPermissionPage(SelectCityFragment.this.getActivity());
                    } else {
                        SelectCityFragment.this.startLocation();
                    }
                }
            });
            this.mCityListView.addHeaderView(this.mLocatedCityLayout);
        }
        if (this.historyCityVisible && this.historyCityList != null && !this.historyCityList.isEmpty()) {
            if (this.mHistoryCityLayout == null) {
                this.mHistoryCityLayout = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.cityselect_city_grid_layout, this.mCityListView, false);
            }
            AUTextView historyCityTitle = (AUTextView) this.mHistoryCityLayout.findViewById(R.id.city_grid_title);
            if (!TextUtils.isEmpty(this.presetHistorycitytitle)) {
                historyCityTitle.setText(this.presetHistorycitytitle);
            } else {
                historyCityTitle.setText(R.string.cityselect_history_city_title);
            }
            ExpandableGridView historyCityGrid = (ExpandableGridView) this.mHistoryCityLayout.findViewById(R.id.city_grid);
            historyCityGrid.setExpanded(true);
            historyCityGrid.setAdapter(new CityGridAdapter(getActivity(), this.historyCityList, this));
            this.mCityListView.addHeaderView(this.mHistoryCityLayout);
        }
        if (!TextUtils.isEmpty(this.recommendLinkTitle) && !TextUtils.isEmpty(this.recommendLinkName) && !TextUtils.isEmpty(this.recommendLinkUrl) && !TextUtils.isEmpty(this.recommendLinkSection)) {
            if (this.mRecommendLinkLayout == null) {
                this.mRecommendLinkLayout = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.cityselect_city_grid_layout, this.mCityListView, false);
            }
            ((AUTextView) this.mRecommendLinkLayout.findViewById(R.id.city_grid_title)).setText(this.recommendLinkTitle);
            ExpandableGridView gridView = (ExpandableGridView) this.mRecommendLinkLayout.findViewById(R.id.city_grid);
            gridView.setExpanded(true);
            List datas = new ArrayList();
            datas.add(this.recommendLinkName);
            gridView.setAdapter(new LinkGridAdapter(getActivity(), datas));
            gridView.setOnItemClickListener(new OnItemClickListener() {
                public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    JumpUtil.processSchema(SelectCityFragment.this.recommendLinkUrl);
                }
            });
            this.mCityListView.addHeaderView(this.mRecommendLinkLayout);
        }
        if (this.hotCityVisible) {
            if (!TextUtils.isEmpty(this.presetHotcitytitle)) {
                title = this.presetHotcitytitle;
            } else if (this.type == 0) {
                title = getString(R.string.cityselect_domestic_hot_city_title);
            } else if (this.type == 1) {
                title = getString(R.string.cityselect_oversea_hot_city_title);
            } else {
                title = getString(R.string.cityselect_hot_city_title);
            }
            if (this.hotCityList != null && !this.hotCityList.isEmpty()) {
                if (this.mHotCityLayout == null) {
                    this.mHotCityLayout = getCityGridLayout(title, this.hotCityList);
                }
                this.mCityListView.addHeaderView(this.mHotCityLayout);
            }
        }
        this.mAdapter = new CityListAdapter(getActivity(), this.cityList, this.mSections, this.mPositions, this.mCityListView.getHeaderViewsCount());
        this.mCityListView.setAdapter(this.mAdapter);
        this.mCityListView.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item != null && (item instanceof CityVO)) {
                    SelectCityFragment.this.notifyCitySelectResult((CityVO) item);
                }
            }
        });
        if (this.showSearchBar) {
            this.mSearchBar.setVisibility(0);
        } else {
            this.mSearchBar.setVisibility(8);
        }
        this.mCityListView.setVisibility(0);
        this.mSectionBladeView.setVisibility(0);
    }

    private void updateLocatedCityText(String text, boolean isCity) {
        if (this.mLocatedCityGrid != null && this.mLocatedCityGrid.getAdapter().getCount() > 0 && this.mLocationFailTv != null) {
            if (isCity) {
                ((CityVO) this.mLocatedCityGrid.getAdapter().getItem(0)).city = text;
                ((BaseAdapter) this.mLocatedCityGrid.getAdapter()).notifyDataSetChanged();
                this.mLocatedCityGrid.setVisibility(0);
                this.mLocationFailTv.setVisibility(8);
                return;
            }
            this.mLocationFailTv.setVisibility(0);
            this.mLocatedCityGrid.setVisibility(8);
            this.mLocationFailTv.setText(text);
        }
    }

    private void updateLocatedCityText(CityVO locatedCity2, boolean isCity) {
        if (this.mLocatedCityGrid != null && this.mLocatedCityGrid.getAdapter().getCount() > 0 && this.mLocationFailTv != null) {
            if (isCity) {
                CityVO cvo = (CityVO) this.mLocatedCityGrid.getAdapter().getItem(0);
                cvo.city = locatedCity2.city;
                cvo.latitude = locatedCity2.latitude;
                cvo.longitude = locatedCity2.longitude;
                cvo.adCode = locatedCity2.adCode;
                ((BaseAdapter) this.mLocatedCityGrid.getAdapter()).notifyDataSetChanged();
                this.mLocatedCityGrid.setVisibility(0);
                this.mLocationFailTv.setVisibility(8);
                return;
            }
            this.mLocationFailTv.setVisibility(0);
            this.mLocatedCityGrid.setVisibility(8);
            this.mLocationFailTv.setText(locatedCity2.city);
        }
    }

    public void onResume() {
        super.onResume();
        tryUpdateLocatedCityText();
    }

    private void removeAllHeaderViews() {
        if (this.mLocatedCityLayout != null) {
            this.mCityListView.removeHeaderView(this.mLocatedCityLayout);
        }
        if (this.mHistoryCityLayout != null) {
            this.mCityListView.removeHeaderView(this.mHistoryCityLayout);
        }
        if (this.mHotCityLayout != null) {
            this.mCityListView.removeHeaderView(this.mHotCityLayout);
        }
        if (this.mRecommendLinkLayout != null) {
            this.mCityListView.removeHeaderView(this.mRecommendLinkLayout);
        }
    }

    private List<CityVO> getListOne(CityVO cityVO) {
        List list = new ArrayList();
        list.add(cityVO);
        return list;
    }

    private List<CityVO> loadCityListFromLocal(int resId) {
        List cityList2 = new ArrayList();
        Set overseaPrefixes = getOverseaCityPrefix();
        if (getActivity() != null) {
            for (String line : getResources().getStringArray(resId)) {
                CityVO cityVO = new CityVO();
                String[] bits = line.split(",");
                if (bits.length >= 2) {
                    cityVO.adCode = bits[0];
                    cityVO.city = bits[1];
                    if (bits.length >= 3) {
                        cityVO.pinyin = bits[2];
                        if (bits.length >= 4) {
                            cityVO.abbreviation = Arrays.asList(bits[3].split("\\|"));
                        }
                    }
                    if (TextUtils.isEmpty(cityVO.adCode) || cityVO.adCode.length() <= 1 || !overseaPrefixes.contains(cityVO.adCode.substring(0, 2))) {
                        cityVO.isMainLand = true;
                    } else {
                        cityVO.isMainLand = false;
                    }
                    cityList2.add(cityVO);
                }
            }
        }
        return cityList2;
    }

    private Set<String> getOverseaCityPrefix() {
        Set result = new HashSet();
        if (getActivity() != null) {
            String[] prefixes = getResources().getStringArray(R.array.oversea_cities_adcode_prefix);
            if (prefixes != null) {
                result.addAll(Arrays.asList(prefixes));
            }
        }
        return result;
    }

    public void afterTextChanged(Editable arg0) {
    }

    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
        String inputedStr = s.toString().trim();
        if (inputedStr.length() == 0) {
            addHeaderViews();
            this.mSectionBladeView.setVisibility(0);
            this.mAdapter.setIsSearchResult(false);
            this.mCityListView.setSelection(0);
            this.mCityListView.setVisibility(0);
            this.mSearchNoResult.setVisibility(8);
            this.mAdapter.updateData(this.cityList);
            return;
        }
        removeAllHeaderViews();
        this.mSectionBladeView.setVisibility(8);
        this.mAdapter.setIsSearchResult(true);
        this.mAdapter.notifyDataSetChanged();
        List result = CityFilter.getMatchedCityList(inputedStr, this.originCityList);
        if (result == null || result.isEmpty()) {
            this.mSearchNoResult.setVisibility(0);
            this.mCityListView.setVisibility(8);
            return;
        }
        this.mCityListView.setVisibility(0);
        this.mCityListView.requestFocusFromTouch();
        this.mCityListView.setSelection(0);
        this.mSearchNoResult.setVisibility(8);
        this.mAdapter.updateData(result);
        this.mSearchBar.getSearchEditView().requestFocus();
    }

    private void addHeaderViews() {
        if (this.mCityListView.getHeaderViewsCount() <= 0) {
            if (this.locatedCityVisible && this.mLocatedCityLayout != null) {
                this.mCityListView.addHeaderView(this.mLocatedCityLayout);
            }
            if (this.historyCityVisible && this.mHistoryCityLayout != null) {
                this.mCityListView.addHeaderView(this.mHistoryCityLayout);
            }
            if (this.mRecommendLinkLayout != null) {
                this.mCityListView.addHeaderView(this.mRecommendLinkLayout);
            }
            if (this.hotCityVisible && this.mHotCityLayout != null) {
                this.mCityListView.addHeaderView(this.mHotCityLayout);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void notifyCitySelectResult(CityVO city) {
        LoggerFactory.getTraceLogger().debug(TAG, "notifyCitySelectResult: city=" + city);
        if (getActivity() != null && !getActivity().isFinishing()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService("input_method");
            if (!(imm == null || !imm.isActive() || this.mSearchBar.getSearchEditView() == null)) {
                imm.hideSoftInputFromWindow(this.mSearchBar.getSearchEditView().getWindowToken(), 2);
            }
            Intent intent = new Intent();
            intent.putExtra(SelectCityActivity.SELECT_PROVINCE, city.province);
            intent.putExtra(SelectCityActivity.SELCTCITY_FROM_CITYLIST, city.city);
            intent.putExtra(SelectCityActivity.SELECT_ADCODE, city.adCode);
            intent.putExtra(SelectCityActivity.SELECT_CITY_PINYIN, city.pinyin);
            intent.putExtra(SelectCityActivity.SELECT_CITY_IS_MAINLAND, city.isMainLand);
            if (this.launchFrom == 2) {
                intent.setAction(SelectCityActivity.BROADCAST_CITY_CHANGE);
                LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(intent);
            } else if (this.launchFrom == 3) {
                CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
                if (citySelectService.getCallBack() != null) {
                    citySelectService.getCallBack().onCitySelect(city, getActivity());
                }
            } else {
                getActivity().setResult(-1, intent);
            }
            if (this.needFinish) {
                getActivity().finish();
                CitySelectService citySelectService2 = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
                if (citySelectService2 != null) {
                    citySelectService2.setCallBack(null);
                }
            }
        }
    }

    public void startLocation() {
        LoggerFactory.getTraceLogger().debug(TAG, "startLocation: ");
        updateLocatedCityText(getString(R.string.regionselect_lbs_on_location), false);
        this.mLocationFailTv.setClickable(false);
        doStartLocation();
    }

    /* access modifiers changed from: private */
    public void updateLocatedBtnFail() {
        if (needShowLPSBtn()) {
            updateLocatedCityText(getString(R.string.cityselect_lbs_fail_and_goto_open), false);
        } else {
            updateLocatedCityText(getString(R.string.cityselect_lbs_fail_and_retry), false);
        }
    }

    /* access modifiers changed from: private */
    public boolean needShowLPSBtn() {
        return this.mBeehiveService != null && this.mBeehiveService.getLocationPermissionSettingExecutor() != null && !this.mBeehiveService.getLocationPermissionSettingExecutor().isHasLocation(getActivity()) && this.mBeehiveService.getLocationPermissionSettingExecutor().isSupportGuide(getActivity());
    }

    public void doStartLocation() {
        LoggerFactory.getTraceLogger().debug(TAG, "doStartLocation: ");
        LBSLocationManagerService lbsService = (LBSLocationManagerService) MicroServiceUtil.getExtServiceByInterface(LBSLocationManagerService.class);
        if (lbsService != null) {
            LBSLocationRequest lbsRequest = new LBSLocationRequest();
            lbsRequest.setNeedAddress(true);
            lbsRequest.setIsHighAccuracy(false);
            lbsRequest.setReGeoLevel(4);
            lbsRequest.setBizType("android-position-citySelector");
            lbsService.locationWithRequest(lbsRequest, new OnLBSLocationListener() {
                public final void onLocationUpdate(LBSLocation location) {
                    LoggerFactory.getTraceLogger().debug(SelectCityFragment.TAG, "onLocationUpdate: location=" + location);
                    if (SelectCityFragment.this.getActivity() != null && !SelectCityFragment.this.getActivity().isFinishing()) {
                        if (location == null) {
                            SelectCityFragment.this.showLocatedBtnFail();
                        } else {
                            a(location);
                        }
                    }
                }

                private void a(LBSLocation location) {
                    if (SelectCityFragment.this.locatedCity == null) {
                        SelectCityFragment.this.locatedCity = new CityVO();
                    }
                    if (!TextUtils.isEmpty(location.getCity()) || TextUtils.isEmpty(location.getCountry())) {
                        SelectCityFragment.this.locatedCity.city = SelectCityFragment.this.removeCitySuffix(location.getCity());
                    } else {
                        SelectCityFragment.this.locatedCity.city = location.getCountry();
                    }
                    SelectCityFragment.this.locatedCity.adCode = location.getAdCode();
                    SelectCityFragment.this.locatedCity.province = location.getProvince();
                    if (location.getReGeocodeResult() != null) {
                        SelectCityFragment.this.locatedCity.isMainLand = location.getReGeocodeResult().isChineseMainLand();
                    }
                    SelectCityFragment.this.locatedCity.latitude = location.getLatitude();
                    SelectCityFragment.this.locatedCity.longitude = location.getLongitude();
                    if (SelectCityFragment.this.locatedCity.bizmap == null) {
                        SelectCityFragment.this.locatedCity.bizmap = new HashMap();
                    }
                    SelectCityFragment.this.locatedCity.bizmap.put("citySource", IpRankSql.LBS_TABLE);
                    SelectCityFragment.this.getActivity().runOnUiThread(new Runnable() {
                        public final void run() {
                            SelectCityFragment.this.tryUpdateLocatedCityText();
                            SelectCityFragment.this.tryNotifyCitySelectLocateResult(SelectCityFragment.this.locatedCity);
                        }
                    });
                }

                public final void onLocationFailed(int errCode) {
                    LoggerFactory.getTraceLogger().debug(SelectCityFragment.TAG, "onLocationFailed: errCode=" + errCode);
                    if (SelectCityFragment.this.getActivity() != null && !SelectCityFragment.this.getActivity().isFinishing()) {
                        SelectCityFragment.this.showLocatedBtnFail();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void tryUpdateLocatedCityText() {
        if (this.locatedCityVisible && !this.isSetLocatedCity) {
            if (this.locatedCity == null || TextUtils.isEmpty(this.locatedCity.city)) {
                updateLocatedBtnFail();
            } else {
                showLocatedCityText(this.locatedCity);
            }
        }
    }

    /* access modifiers changed from: private */
    public void showLocatedBtnFail() {
        getActivity().runOnUiThread(new Runnable() {
            public final void run() {
                SelectCityFragment.this.mLocationFailTv.setClickable(true);
                SelectCityFragment.this.updateLocatedBtnFail();
            }
        });
    }

    /* access modifiers changed from: private */
    public String removeCitySuffix(String city) {
        if (TextUtils.isEmpty(city) || !city.endsWith("市")) {
            return city;
        }
        return city.substring(0, city.length() - 1);
    }

    private List<CityVO> filterCityListByMainLand(List<CityVO> cityList2, boolean isMainLand) {
        List result = new ArrayList();
        if (cityList2 != null) {
            for (CityVO cityVO : cityList2) {
                if (isMainLand && cityVO.isMainLand) {
                    result.add(cityVO);
                } else if (!isMainLand && !cityVO.isMainLand) {
                    result.add(cityVO);
                }
            }
        }
        return result;
    }

    /* access modifiers changed from: private */
    public void tryNotifyCitySelectLocateResult(CityVO city) {
        if (this.launchFrom == 3) {
            CitySelectService service = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
            if (service.getCallBack() != null && (service.getCallBack() instanceof CitySelectHandler) && this.isSetLocatedCity && this.locatedCityVisible) {
                Bundle b = new Bundle();
                b.putString("serviceId", this.serviceId);
                ((CitySelectHandler) service.getCallBack()).onLocateFinish(city, getActivity(), b);
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateViewFromEvent(Object data) {
        try {
            if (data instanceof Bundle) {
                Bundle b = (Bundle) data;
                if (TextUtils.equals(b.getString("action"), SelectCityActivity.EXTRA_PARAM_SET_LOCATED_CITY)) {
                    String payload = b.getString(LongLinkMsgConstants.LONGLINK_APPDATA);
                    if (!TextUtils.isEmpty(payload)) {
                        JSONObject jo = JSON.parseObject(payload);
                        String cityName = jo.getString("locatedCityName");
                        String adCode = jo.getString("locatedCityAdCode");
                        String pinyin = jo.getString(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_PINYIN);
                        if (this.locatedCity != null) {
                            this.locatedCity.city = cityName;
                            if (!TextUtils.isEmpty(adCode)) {
                                this.locatedCity.adCode = adCode;
                            }
                            if (!TextUtils.isEmpty(pinyin)) {
                                this.locatedCity.pinyin = pinyin;
                            }
                        }
                        showLocatedCityText(this.locatedCity);
                        CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
                        if (citySelectService != null) {
                            IModifyCityCallBack mfCallback = citySelectService.getModifyCallBack();
                            if (mfCallback != null) {
                                mfCallback.onModifySuccess(new CityVO(this.locatedCity.city, true));
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LogCatUtil.warn((String) TAG, (Throwable) ex);
        }
    }

    private void showLocatedCityText(CityVO cityVO) {
        updateLocatedCityText(cityVO, true);
        if (this.mLocatedCityGrid != null) {
            this.mLocatedCityGrid.setClickable(true);
        }
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3480";
    }
}
