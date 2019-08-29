package com.alipay.mobile.beehive.cityselect.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.alipay.mobile.antui.bar.AUVerticalTabView;
import com.alipay.mobile.antui.basic.AUFrameLayout;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AUSearchInputBox;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.cityselect.model.CityFragmentModel;
import com.alipay.mobile.beehive.cityselect.model.CityPageModel;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.ICityCallBack;
import com.alipay.mobile.beehive.cityselect.util.CityFilter;
import com.alipay.mobile.beehive.cityselect.util.Constants;
import com.alipay.mobile.beehive.cityselect.view.CityPageUI;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseFragment;
import com.alipay.mobile.beehive.util.JumpUtil;
import com.alipay.mobile.beehive.util.KeyBoardUtil;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationRequest;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.service.LBSLocationManagerService;
import com.alipay.mobile.framework.service.OnLBSLocationListener;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;

@EFragment(resName = "fragment_area_select")
public class SelectAreaFragment extends BeehiveBaseFragment implements TextWatcher {
    public static final String TAG = new StringBuilder(Constants.BASE_TAG).append(SelectAreaFragment.class.getSimpleName()).toString();
    private List<CityPageUI> cityPageUIs = new ArrayList();
    private int currentPageIndex;
    /* access modifiers changed from: private */
    public Activity mActivity;
    /* access modifiers changed from: private */
    public ICityCallBack mCityCallBack;
    private CityFragmentModel mCityFragmentModel;
    private Bundle mExtParams;
    /* access modifiers changed from: private */
    public List<View> mLocationFailTvList = new ArrayList();
    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
        public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Object object = view.getTag(R.layout.activity_area_select);
            if (object instanceof CityVO) {
                CityVO result = (CityVO) object;
                if (result.cityFragmentModels == null || result.cityFragmentModels.isEmpty() || (SelectAreaFragment.this.getActivity() instanceof SelectAreaSubActivity)) {
                    if (SelectAreaFragment.this.mCityCallBack != null) {
                        SelectAreaFragment.this.mCityCallBack.onCitySelect(result, SelectAreaFragment.this.getActivity());
                    }
                    SelectAreaFragment.this.getActivity().finish();
                    return;
                }
                SelectAreaSubActivity_.cityFragmentModelsParam = result.cityFragmentModels;
                JumpUtil.startActivity(null, SelectAreaSubActivity_.class);
            }
        }
    };
    /* access modifiers changed from: private */
    public List<OnLBSLocationListener> mOnLBSLocationListeners = new ArrayList();
    @ViewById(resName = "right_container")
    protected AUFrameLayout mRightContainer;
    @ViewById(resName = "search_city_list")
    protected AUListView mSearchCityList;
    @ViewById(resName = "search_bar")
    protected AUSearchInputBox mSearchInputBox;
    private List<CityVO> mSearchList = new ArrayList();
    @ViewById(resName = "search_no_result")
    protected AUTextView mTVNoResult;
    @ViewById(resName = "left_tabs")
    protected AUVerticalTabView mVerticalTabView;
    private List<String> tabNameItems;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoggerFactory.getTraceLogger().debug(TAG, "onCreate()");
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setArgs(CityFragmentModel cityFragmentModel, Bundle extParams, ICityCallBack cityCallBack) {
        this.mCityFragmentModel = cityFragmentModel;
        this.mExtParams = extParams;
        this.mCityCallBack = cityCallBack;
    }

    /* access modifiers changed from: protected */
    @AfterViews
    public void afterViews() {
        if (!handleParams()) {
            initSearchBar();
            if (this.mCityFragmentModel.cityPageModels.size() > 1) {
                this.tabNameItems = new ArrayList();
                for (CityPageModel cityPageModel : this.mCityFragmentModel.cityPageModels) {
                    this.tabNameItems.add(cityPageModel.name);
                    this.cityPageUIs.add(createCityPageUI(cityPageModel, 2));
                }
                this.mVerticalTabView.setItems(this.tabNameItems);
                this.mVerticalTabView.setOnItemClickListener(new AUVerticalTabView.OnItemClickListener() {
                    public final void onItemClick(View view, int i) {
                        SelectAreaFragment.this.setPageIndex(i);
                        KeyBoardUtil.hideKeyBoard(SelectAreaFragment.this.getContext(), view);
                    }
                });
                this.mVerticalTabView.setSelected(this.currentPageIndex);
                setPageIndex(this.currentPageIndex);
            } else {
                this.mVerticalTabView.setVisibility(8);
                this.cityPageUIs.add(createCityPageUI(this.mCityFragmentModel.cityPageModels.get(0), 3));
            }
            for (CityPageUI cityPageUI : this.cityPageUIs) {
                this.mRightContainer.addView(cityPageUI);
                this.mSearchList.addAll(cityPageUI.getSearchList());
                this.mLocationFailTvList.addAll(cityPageUI.getLocationFailTvList());
            }
            doStartLocation();
        }
    }

    /* access modifiers changed from: private */
    public void setPageIndex(int i) {
        this.currentPageIndex = i;
        for (int index = 0; index < this.cityPageUIs.size(); index++) {
            CityPageUI cityPageUI = this.cityPageUIs.get(index);
            if (index == i) {
                cityPageUI.setVisibility(0);
            } else {
                cityPageUI.setVisibility(8);
            }
        }
    }

    @NonNull
    private CityPageUI createCityPageUI(CityPageModel cityPageModel, int gridNum) {
        CityPageUI cityPageUI = new CityPageUI(this.mActivity, cityPageModel, this.mOnItemClickListener, this.mOnLBSLocationListeners, gridNum);
        cityPageUI.setTag(cityPageModel);
        return cityPageUI;
    }

    private void initSearchBar() {
        if (this.mCityFragmentModel.hasSearchBar) {
            String searchBarHint = this.mCityFragmentModel.searchBarHint;
            if (TextUtils.isEmpty(searchBarHint)) {
                searchBarHint = this.mExtParams.getString(SelectCityActivity.EXTRA_PARAM_SEARCHBARHINT, this.mActivity.getString(R.string.city_search_box_hint));
            }
            this.mSearchInputBox.getSearchEditView().setHint(searchBarHint);
        } else {
            this.mSearchInputBox.setVisibility(8);
        }
        this.mSearchInputBox.getSearchEditView().addTextChangedListener(this);
    }

    private boolean handleParams() {
        this.mActivity = getActivity();
        if (this.mCityFragmentModel == null || this.mCityFragmentModel.cityPageModels == null || this.mCityFragmentModel.cityPageModels.isEmpty() || this.mActivity == null) {
            if (this.mActivity != null) {
                this.mActivity.finish();
            }
            return true;
        }
        if (this.mExtParams == null) {
            this.mExtParams = new Bundle();
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3480";
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchUpdateUI(s.toString().trim());
    }

    private void searchUpdateUI(String inputedStr) {
        if (inputedStr.length() == 0) {
            if (this.tabNameItems != null && !this.tabNameItems.isEmpty()) {
                this.mVerticalTabView.setVisibility(0);
            }
            this.mRightContainer.setVisibility(0);
            this.mTVNoResult.setVisibility(8);
            this.mSearchCityList.setVisibility(8);
            return;
        }
        List result = CityFilter.getMatchedCityList(inputedStr, this.mSearchList);
        this.mVerticalTabView.setVisibility(8);
        this.mRightContainer.setVisibility(8);
        if (result == null || result.isEmpty()) {
            this.mSearchCityList.setVisibility(8);
            this.mTVNoResult.setVisibility(0);
            return;
        }
        this.mSearchCityList.setVisibility(0);
        this.mTVNoResult.setVisibility(8);
        if (getActivity() != null && !getActivity().isFinishing()) {
            this.mSearchCityList.setAdapter(new CityAreaAdapter(getActivity(), result, new ArrayList(), new ArrayList(), 0));
            this.mSearchCityList.setOnItemClickListener(this.mOnItemClickListener);
        }
    }

    public void afterTextChanged(Editable s) {
    }

    public void doStartLocation() {
        LBSLocationManagerService lbsService = (LBSLocationManagerService) MicroServiceUtil.getExtServiceByInterface(LBSLocationManagerService.class);
        if (lbsService != null) {
            LBSLocationRequest lbsRequest = new LBSLocationRequest();
            lbsRequest.setNeedAddress(true);
            lbsRequest.setIsHighAccuracy(false);
            lbsRequest.setReGeoLevel(4);
            lbsRequest.setBizType("android-position-citySelector");
            lbsService.locationWithRequest(lbsRequest, new OnLBSLocationListener() {
                public final void onLocationUpdate(LBSLocation location) {
                    LoggerFactory.getTraceLogger().debug(SelectAreaFragment.TAG, "onLocationUpdate: location=" + location);
                    if (SelectAreaFragment.this.mActivity != null && !SelectAreaFragment.this.mActivity.isFinishing()) {
                        for (OnLBSLocationListener onLocationUpdate : SelectAreaFragment.this.mOnLBSLocationListeners) {
                            onLocationUpdate.onLocationUpdate(location);
                        }
                    }
                }

                public final void onLocationFailed(int errCode) {
                    LoggerFactory.getTraceLogger().debug(SelectAreaFragment.TAG, "onLocationFailed: errCode=" + errCode);
                    if (SelectAreaFragment.this.mActivity != null && !SelectAreaFragment.this.mActivity.isFinishing()) {
                        for (OnLBSLocationListener onLocationFailed : SelectAreaFragment.this.mOnLBSLocationListeners) {
                            onLocationFailed.onLocationFailed(errCode);
                        }
                        SelectAreaFragment.this.mActivity.runOnUiThread(new Runnable() {
                            public final void run() {
                                for (View locationFailTv : SelectAreaFragment.this.mLocationFailTvList) {
                                    if (locationFailTv != null) {
                                        locationFailTv.setOnClickListener(new OnClickListener() {
                                            public final void onClick(View v) {
                                                SelectAreaFragment.this.doStartLocation();
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    public void reset() {
        if (this.mSearchInputBox != null) {
            this.mSearchInputBox.getSearchEditView().setText("");
            this.mSearchInputBox.getSearchEditView().clearFocus();
        }
        searchUpdateUI("");
    }
}
