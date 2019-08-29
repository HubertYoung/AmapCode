package com.alipay.mobile.beehive.cityselect.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityFragmentModel;
import com.alipay.mobile.beehive.cityselect.model.CityPageModel;
import com.alipay.mobile.beehive.cityselect.model.CityTabModel;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.ui.CityAreaAdapter;
import com.alipay.mobile.beehive.cityselect.ui.CityListAdapter;
import com.alipay.mobile.beehive.cityselect.util.CityUtils;
import com.alipay.mobile.beehive.cityselect.util.Constants;
import com.alipay.mobile.beehive.cityselect.util.SectionComparator;
import com.alipay.mobile.beehive.cityselect.view.CityBladeView.OnCityBladeViewItemClickListener;
import com.alipay.mobile.beehive.compositeui.listview.ListViewHeader;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.OnLBSLocationListener;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CityPageUI extends AURelativeLayout {
    private static final String FORMAT = "^[a-z,A-Z].*$";
    public static final String TAG = new StringBuilder(Constants.BASE_TAG).append(CityPageUI.class.getSimpleName()).toString();
    /* access modifiers changed from: private */
    public CityListAdapter mAdapter;
    /* access modifiers changed from: private */
    public CityTabModel mCityListTabModel;
    /* access modifiers changed from: private */
    public AUListView mCityListView;
    /* access modifiers changed from: private */
    public final CityPageModel mCityPageModel;
    private final int mGridNum;
    /* access modifiers changed from: private */
    public int mHideHeaderCount;
    /* access modifiers changed from: private */
    public List<String> mHideSections = new ArrayList();
    /* access modifiers changed from: private */
    public Map<CityTabModel, View> mHideTabViewMap = new LinkedHashMap();
    /* access modifiers changed from: private */
    public Map<String, Integer> mIndexer = new HashMap();
    private List<View> mLocationFailTvList = new ArrayList();
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;
    private final List<OnLBSLocationListener> mOnLBSLocationListeners;
    /* access modifiers changed from: private */
    public List<Integer> mPositions = new ArrayList();
    /* access modifiers changed from: private */
    public List<CityVO> mSearchList = new ArrayList();
    protected CityBladeView mSectionBladeView;
    /* access modifiers changed from: private */
    public List<String> mSections = new ArrayList();
    /* access modifiers changed from: private */
    public Handler mUIHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public Map<String, List<CityVO>> tmpMap = new HashMap();

    public CityPageUI(Context context, CityPageModel cityPageModel, OnItemClickListener onItemClickListener, List<OnLBSLocationListener> onLBSLocationListeners, int gridNum) {
        super(context);
        this.mCityPageModel = cityPageModel;
        this.mOnItemClickListener = onItemClickListener;
        this.mOnLBSLocationListeners = onLBSLocationListeners;
        this.mGridNum = gridNum;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_city_page, this, true);
        this.mCityListView = (AUListView) findViewById(R.id.city_list);
        this.mSectionBladeView = (CityBladeView) findViewById(R.id.section_list);
        for (CityTabModel cityTabModel : this.mCityPageModel.cityTabModels) {
            LinearLayout linearLayout = null;
            switch (cityTabModel.type) {
                case 0:
                    linearLayout = new CityStaticTabView(getContext(), cityTabModel, this.mOnItemClickListener, this.mGridNum);
                    this.mCityListView.addHeaderView(new ListViewHeader(getContext(), linearLayout));
                    break;
                case 1:
                    linearLayout = new CityLocateTabView(getContext(), cityTabModel, this.mOnItemClickListener);
                    this.mCityListView.addHeaderView(new ListViewHeader(getContext(), linearLayout));
                    OnLBSLocationListener onLBSLocationListener = ((CityLocateTabView) linearLayout).getOnLBSLocationListener();
                    this.mLocationFailTvList.add(((CityLocateTabView) linearLayout).getLocationFailTv());
                    if (onLBSLocationListener != null) {
                        this.mOnLBSLocationListeners.add(onLBSLocationListener);
                        break;
                    }
                    break;
                case 2:
                    this.mCityListTabModel = cityTabModel;
                    break;
                case 3:
                    linearLayout = new CityLinkTabView(getContext(), cityTabModel, this.mGridNum);
                    this.mCityListView.addHeaderView(new ListViewHeader(getContext(), linearLayout));
                    break;
                case 4:
                    linearLayout = new CitySingleItemView(getContext(), cityTabModel, this.mOnItemClickListener);
                    this.mCityListView.addHeaderView(new ListViewHeader(getContext(), linearLayout));
                    if (TextUtils.isEmpty(cityTabModel.navName)) {
                        cityTabModel.navName = cityTabModel.name;
                        break;
                    }
                    break;
            }
            if (2 != cityTabModel.type) {
                this.mSections.add(cityTabModel.navName);
                this.tmpMap.put(cityTabModel.navName, getListOne(new CityVO()));
                if (cityTabModel.areaShowMode != 0) {
                    this.mHideTabViewMap.put(cityTabModel, linearLayout);
                }
            }
            if (cityTabModel.needSearch && cityTabModel.cityVOs != null) {
                fillSearchModel(cityTabModel);
            }
        }
        if (this.mCityListTabModel == null) {
            if (this.mCityPageModel.needDefaultList) {
                this.mCityListTabModel = createDefaultCityListTabModel(this.mCityPageModel.fillMainLand);
                if (this.mCityListTabModel.needSearch && this.mCityListTabModel.cityVOs != null) {
                    fillSearchModel(this.mCityListTabModel);
                }
            } else {
                this.mCityListTabModel = new CityTabModel();
                this.mCityListTabModel.type = 2;
                this.mCityListTabModel.cityVOs = new ArrayList();
            }
        }
        if (!TextUtils.isEmpty(this.mCityListTabModel.name)) {
            this.mCityListView.addHeaderView(new ListViewHeader(getContext(), new CityListNameView(getContext(), this.mCityListTabModel)));
            this.mSections.add(this.mCityListTabModel.name);
            this.mHideSections.add(this.mCityListTabModel.name);
            this.tmpMap.put(this.mCityListTabModel.name, getListOne(new CityVO()));
        }
        updateUI();
        if (this.mHideTabViewMap != null && !this.mHideTabViewMap.isEmpty()) {
            this.mOnLBSLocationListeners.add(new OnLBSLocationListener() {
                public final void onLocationUpdate(LBSLocation lbsLocation) {
                    if (lbsLocation != null && lbsLocation.getReGeocodeResult() != null) {
                        List tempSections = new ArrayList();
                        Map tempMap = new HashMap();
                        CityPageUI.this.mHideHeaderCount = 0;
                        for (Entry entry : CityPageUI.this.mHideTabViewMap.entrySet()) {
                            CityTabModel cityTabModel = (CityTabModel) entry.getKey();
                            final View tabView = (View) entry.getValue();
                            if ((!lbsLocation.getReGeocodeResult().isChineseMainLand() || cityTabModel.areaShowMode != 1) && (lbsLocation.getReGeocodeResult().isChineseMainLand() || cityTabModel.areaShowMode != 2)) {
                                CityPageUI.this.mUIHandler.post(new Runnable() {
                                    public final void run() {
                                        if (tabView != null) {
                                            tabView.setVisibility(8);
                                        }
                                    }
                                });
                                CityPageUI.this.mSearchList.removeAll(cityTabModel.cityVOs);
                                CityPageUI.this.mHideHeaderCount = CityPageUI.this.mHideHeaderCount + 1;
                            } else {
                                tempSections.add(cityTabModel.navName);
                                tempMap.put(cityTabModel.navName, CityPageUI.this.getListOne(new CityVO()));
                            }
                        }
                        if (CityPageUI.this.mHideTabViewMap.size() != tempSections.size()) {
                            CityPageUI.this.mSections = tempSections;
                            CityPageUI.this.tmpMap = tempMap;
                            CityPageUI.this.mPositions = new ArrayList();
                            CityPageUI.this.updateUI();
                        }
                    }
                }

                public final void onLocationFailed(int i) {
                    LoggerFactory.getTraceLogger().info(CityPageUI.TAG, "onLocationFailed:" + i);
                }
            });
        }
    }

    private void fillSearchModel(CityTabModel cityTabModel) {
        this.mSearchList.addAll(cityTabModel.cityVOs);
        for (CityVO cityVO : cityTabModel.cityVOs) {
            if (cityVO.cityFragmentModels != null) {
                for (CityFragmentModel cityFragmentModel : cityVO.cityFragmentModels) {
                    if (cityFragmentModel.cityPageModels != null) {
                        for (CityPageModel cityPageModel : cityFragmentModel.cityPageModels) {
                            if (cityPageModel.cityTabModels != null) {
                                for (CityTabModel cityTabModelSub : cityPageModel.cityTabModels) {
                                    if (cityTabModelSub.needSearch && cityTabModelSub.cityVOs != null) {
                                        this.mSearchList.addAll(cityTabModelSub.cityVOs);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void updateUI() {
        this.mCityListTabModel.cityVOs = prepareDataAndInitView(this.mCityListTabModel.cityVOs);
        this.mUIHandler.post(new Runnable() {
            public final void run() {
                CityPageUI.this.mAdapter = new CityAreaAdapter(CityPageUI.this.getContext(), CityPageUI.this.mCityListTabModel.cityVOs, CityPageUI.this.mSections, CityPageUI.this.mPositions, CityPageUI.this.mCityListView.getHeaderViewsCount() - CityPageUI.this.mHideHeaderCount);
                CityPageUI.this.mCityListView.setAdapter(CityPageUI.this.mAdapter);
                CityPageUI.this.mCityListView.setOnItemClickListener(CityPageUI.this.mOnItemClickListener);
                List sections = new ArrayList();
                sections.addAll(CityPageUI.this.mSections);
                sections.removeAll(CityPageUI.this.mHideSections);
                CityPageUI.this.mSectionBladeView.setSections(sections);
                CityPageUI.this.mSectionBladeView.setOnItemClickListener(new OnCityBladeViewItemClickListener() {
                    public final void onItemClick(String s) {
                        if (CityPageUI.this.mIndexer.get(s) != null) {
                            CityPageUI.this.mCityListView.setSelection(((Integer) CityPageUI.this.mIndexer.get(s)).intValue());
                        }
                    }
                });
                if (!CityPageUI.this.mCityPageModel.hasNavigation || CityPageUI.this.mSections.isEmpty()) {
                    CityPageUI.this.mSectionBladeView.setVisibility(8);
                } else {
                    CityPageUI.this.mSectionBladeView.setVisibility(0);
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public List<CityVO> prepareDataAndInitView(List<CityVO> cityList) {
        boolean z;
        List tmpCityList = new ArrayList(cityList);
        List tmpSections = new ArrayList();
        Map tmpIndexer = new HashMap();
        for (int i = 0; i < tmpCityList.size(); i++) {
            if (!TextUtils.isEmpty(((CityVO) tmpCityList.get(i)).pinyin)) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                String firstName = ((CityVO) tmpCityList.get(i)).pinyin.substring(0, 1).toUpperCase();
                if (firstName.matches(FORMAT)) {
                    if (tmpSections.contains(firstName)) {
                        this.tmpMap.get(firstName).add(tmpCityList.get(i));
                    } else {
                        tmpSections.add(firstName);
                        List list = new ArrayList();
                        list.add(tmpCityList.get(i));
                        this.tmpMap.put(firstName, list);
                    }
                }
            }
            if (tmpSections.contains(MetaRecord.LOG_SEPARATOR)) {
                this.tmpMap.get(MetaRecord.LOG_SEPARATOR).add(tmpCityList.get(i));
            } else {
                tmpSections.add(MetaRecord.LOG_SEPARATOR);
                List list2 = new ArrayList();
                list2.add(tmpCityList.get(i));
                this.tmpMap.put(MetaRecord.LOG_SEPARATOR, list2);
            }
        }
        Collections.sort(tmpSections, new SectionComparator());
        if (tmpSections.contains(MetaRecord.LOG_SEPARATOR)) {
            tmpSections.remove(MetaRecord.LOG_SEPARATOR);
            tmpSections.add(MetaRecord.LOG_SEPARATOR);
        }
        this.mSections.addAll(tmpSections);
        int position = 0;
        List tmpCityList2 = new ArrayList();
        for (int i2 = 0; i2 < this.mSections.size(); i2++) {
            tmpIndexer.put(this.mSections.get(i2), Integer.valueOf(position));
            this.mPositions.add(Integer.valueOf(position));
            if (this.tmpMap.get(this.mSections.get(i2)) != null) {
                position += this.tmpMap.get(this.mSections.get(i2)).size();
                List tempList = this.tmpMap.get(this.mSections.get(i2));
                if (!tempList.isEmpty() && !TextUtils.isEmpty(((CityVO) tempList.get(0)).city)) {
                    tmpCityList2.addAll(this.tmpMap.get(this.mSections.get(i2)));
                }
            }
        }
        this.mIndexer = tmpIndexer;
        return tmpCityList2;
    }

    /* access modifiers changed from: private */
    public List<CityVO> getListOne(CityVO cityVO) {
        List list = new ArrayList();
        list.add(cityVO);
        return list;
    }

    @NonNull
    private CityTabModel createDefaultCityListTabModel(int fillMainLand) {
        CityTabModel cityListTabModel = new CityTabModel();
        cityListTabModel.type = 2;
        cityListTabModel.cityVOs = CityUtils.loadCityListFromLocal(getContext(), R.array.cities_from_gaode, fillMainLand);
        return cityListTabModel;
    }

    public List<CityVO> getSearchList() {
        return this.mSearchList;
    }

    public List<View> getLocationFailTvList() {
        return this.mLocationFailTvList;
    }
}
