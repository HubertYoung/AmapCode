package com.alipay.mobile.beehive.cityselect.ui;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.base.config.ConfigService;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.api.BeehiveConstant;
import com.alipay.mobile.beehive.cityselect.model.CityInfo;
import com.alipay.mobile.beehive.cityselect.model.CountryInfo;
import com.alipay.mobile.beehive.cityselect.model.PageInfo;
import com.alipay.mobile.beehive.cityselect.model.ProvinceCodeMap;
import com.alipay.mobile.beehive.cityselect.model.ProvinceInfo;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.IProvinceCitySelectCallBack;
import com.alipay.mobile.beehive.cityselect.util.Constants;
import com.alipay.mobile.beehive.global.impl.BeehiveBaseActivity;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.common.lbs.LBSLocation;
import com.alipay.mobile.common.lbs.LBSLocationRequest;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.locale.LocaleHelper;
import com.alipay.mobile.framework.service.LBSLocationManagerService;
import com.alipay.mobile.framework.service.OnLBSLocationListener;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

@EActivity(resName = "activity_province_city_select")
public class ProvinceCityListActivity extends BeehiveBaseActivity {
    public static final String EXTRA_ISFROM_SERVICE = "isFromService";
    public static final String KEY_CITY = "location_city";
    public static final String KEY_CITY_CODE = "location_city_code";
    public static final String KEY_COUNTRY = "location_country";
    public static final String KEY_COUNTRY_CODE = "location_country_code";
    public static final String KEY_FILTER_COUNTRY = "filter_country";
    public static final String KEY_HIDE_LOCATE_HEADER = "hide_locate_header";
    public static final String KEY_PRE_DATA = "pre_data";
    public static final String KEY_PROVINCE = "location_province";
    public static final String KEY_PROVINCE_CODE = "location_province_code";
    public static final String KEY_RETURN_CODE = "return_code";
    public static final String KEY_USE_CHINESE = "use_chinese";
    private static final int REQUEST_LOCATION = 1;
    private static final String TAG = "cityselect_ProvinceCityListActivity";
    public static final int TYPE_CITY = 0;
    public static final int TYPE_COUNTRY = 2;
    public static final int TYPE_PROVINCE = 1;
    private List<CountryInfo> currentDatas = new ArrayList();
    /* access modifiers changed from: private */
    public int currentType = 2;
    private String filterCountry;
    private boolean isFromService;
    private boolean isHideLocateHeader = false;
    private boolean isReturnCode = false;
    private boolean isUseChinese;
    private View listFooter;
    private View listHeader;
    /* access modifiers changed from: private */
    public AUTextView locatedPosition;
    private View locationContent;
    private View locationHeader;
    private OnLBSLocationListener locationListener = new OnLBSLocationListener() {
        public final void onLocationUpdate(LBSLocation loca) {
            String locationStrShow;
            LoggerFactory.getTraceLogger().debug(ProvinceCityListActivity.TAG, "onLocationUpdate(): location=" + loca);
            String country = loca.getCountry();
            String province = loca.getProvince();
            String city = loca.getCity();
            String district = loca.getDistrict();
            LoggerFactory.getTraceLogger().debug(ProvinceCityListActivity.TAG, "country=" + country + ", city=" + city + ", district=" + district);
            String province2 = ProvinceCityListActivity.this.removeSuffix(province, 1);
            String city2 = ProvinceCityListActivity.this.removeSuffix(city, 0);
            if (!TextUtils.isEmpty(district)) {
                String adCode = loca.getAdCode();
                LoggerFactory.getTraceLogger().debug(ProvinceCityListActivity.TAG, "adcode = " + adCode);
                if (ProvinceCityListActivity.this.isUseMapKey(adCode)) {
                    district = (String) ProvinceCityListActivity.this.mDistrictMap.get(adCode);
                } else {
                    district = district.substring(0, district.length() - 1);
                }
            }
            if (province2 == null || (!province2.startsWith(ProvinceCityListActivity.this.getString(R.string.regionselect_beijing)) && !province2.startsWith(ProvinceCityListActivity.this.getString(R.string.regionselect_shanghai)) && !province2.startsWith(ProvinceCityListActivity.this.getString(R.string.regionselect_tianjin)) && !province2.startsWith(ProvinceCityListActivity.this.getString(R.string.regionselect_chongqing)))) {
                ProvinceCityListActivity.this.locationStr = country + "#@#" + province2 + "#@#" + city2;
                locationStrShow = country + Token.SEPARATOR + province2 + Token.SEPARATOR + city2;
            } else {
                String province3 = ProvinceCityListActivity.this.removeSuffix(province2, 0);
                ProvinceCityListActivity.this.locationStr = country + "#@#" + province3 + "#@#" + district;
                locationStrShow = country + Token.SEPARATOR + province3 + Token.SEPARATOR + district;
            }
            LoggerFactory.getTraceLogger().debug(ProvinceCityListActivity.TAG, "locationStr=" + ProvinceCityListActivity.this.locationStr);
            ProvinceCityListActivity.this.showLoactionInfo(locationStrShow);
        }

        public final void onLocationFailed(int arg0) {
            LoggerFactory.getTraceLogger().debug(ProvinceCityListActivity.TAG, "onLocationFailed()");
            ProvinceCityListActivity.this.runOnUiThread(new Runnable() {
                public final void run() {
                    ProvinceCityListActivity.this.locatedPosition.setText(ProvinceCityListActivity.this.getResources().getString(R.string.regionselect_lbs_fail));
                    ProvinceCityListActivity.this.locatedPosition.setClickable(false);
                }
            });
        }
    };
    /* access modifiers changed from: private */
    public String locationStr;
    private ProvinceCityListAdapter mAdapter;
    private List<CityInfo> mCities;
    /* access modifiers changed from: private */
    public HashMap<String, String> mDistrictMap = new HashMap<>();
    private List<ProvinceInfo> mProvinces;
    private List<ProvinceCodeMap> provinceCodeMaps;
    @ViewById(resName = "provincecitylist_listview")
    protected AUListView provinceList;
    private String selectedCity = "";
    private String selectedCountry = "";
    private String selectedProvince = "";
    private AUTextView selectedProvinceCityHeader;
    private String selectedProvinceCityStr;
    private Stack<PageInfo> stack = new Stack<>();
    @ViewById(resName = "provincecitylist_titleBar")
    protected AUTitleBar titleBar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateViews();
        getDataFromIntent();
        initDistrictList();
        if (!getIntent().hasExtra(SpmUtils.KEY_BEE_BIZTYPE)) {
            getIntent().putExtra(SpmUtils.KEY_BEE_BIZTYPE, "20000282");
        }
        if (!getIntent().hasExtra(SpmUtils.KEY_BEE_SOURCEPAGE)) {
            getIntent().putExtra(SpmUtils.KEY_BEE_SOURCEPAGE, "com.alipay.mobile.profilesetting.ui.SettingsActivity_");
        }
    }

    private void inflateViews() {
        this.locationHeader = getLayoutInflater().inflate(R.layout.provincecitylist_located_position_title, null);
        this.locationContent = getLayoutInflater().inflate(R.layout.provincecitylist_located_position, null);
        this.listHeader = getLayoutInflater().inflate(R.layout.provincecitylist_header, null);
        this.selectedProvinceCityHeader = (AUTextView) this.listHeader.findViewById(R.id.selected_province_city);
        this.listFooter = getLayoutInflater().inflate(R.layout.provincecitylist_footer, null, false);
        this.locatedPosition = (AUTextView) this.locationContent.findViewById(R.id.provincecity_located_region);
    }

    private void getDataFromIntent() {
        if (getIntent() != null) {
            this.isFromService = getIntent().getBooleanExtra(EXTRA_ISFROM_SERVICE, false);
        }
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (!TextUtils.isEmpty(bundle.getString(KEY_FILTER_COUNTRY))) {
                this.filterCountry = bundle.getString(KEY_FILTER_COUNTRY);
            }
            if (!TextUtils.isEmpty(bundle.getString(KEY_PROVINCE))) {
                this.selectedProvince = bundle.getString(KEY_PROVINCE);
            }
            if (!TextUtils.isEmpty(bundle.getString(KEY_CITY))) {
                this.selectedCity = bundle.getString(KEY_CITY);
            }
            if (!TextUtils.isEmpty(bundle.getString(KEY_PROVINCE)) || !TextUtils.isEmpty(bundle.getString(KEY_CITY))) {
                if (TextUtils.isEmpty(bundle.getString(KEY_COUNTRY))) {
                    this.selectedCountry = getString(R.string.regionselect_china);
                } else {
                    this.selectedCountry = bundle.getString(KEY_COUNTRY);
                }
            } else if (!TextUtils.isEmpty(bundle.getString(KEY_COUNTRY))) {
                this.selectedCountry = bundle.getString(KEY_COUNTRY);
            } else {
                this.selectedCountry = "";
            }
            if (!TextUtils.isEmpty(this.filterCountry)) {
                this.selectedCountry = this.filterCountry;
            }
            if (!TextUtils.isEmpty(bundle.getString(KEY_RETURN_CODE))) {
                this.isReturnCode = TextUtils.equals(bundle.getString(KEY_RETURN_CODE), "true");
            } else {
                this.isReturnCode = false;
            }
            if (!TextUtils.isEmpty(bundle.getString(KEY_USE_CHINESE))) {
                this.isUseChinese = TextUtils.equals(bundle.getString(KEY_USE_CHINESE), "true");
            } else {
                this.isUseChinese = false;
            }
            if (!TextUtils.isEmpty(bundle.getString(KEY_HIDE_LOCATE_HEADER))) {
                this.isHideLocateHeader = TextUtils.equals(bundle.getString(KEY_HIDE_LOCATE_HEADER), "true");
            } else {
                this.isHideLocateHeader = false;
            }
            if (!TextUtils.isEmpty(bundle.getString(KEY_PRE_DATA))) {
                try {
                    this.currentDatas = (List) JSON.parseObject(bundle.getString(KEY_PRE_DATA), (TypeReference<T>) new TypeReference<List<CountryInfo>>() {
                    }, new Feature[0]);
                } catch (Throwable tr) {
                    LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG, tr);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @Background
    public void initDistrictList() {
        HashMap tmpDistrictMap = new HashMap();
        for (String split : getResources().getStringArray(R.array.provincecityselect_district_list)) {
            String[] bits = split.split(",");
            if (bits.length == 2) {
                tmpDistrictMap.put(bits[0], bits[1]);
            }
        }
        this.mDistrictMap = tmpDistrictMap;
    }

    /* access modifiers changed from: protected */
    @Background
    public void loadDataFromLocal() {
        String[] lines;
        if (this.currentDatas == null || this.currentDatas.isEmpty()) {
            List tmpDatas = new ArrayList();
            CountryInfo countryInfo = null;
            ProvinceInfo provinceInfo = null;
            if (this.isUseChinese || LocaleHelper.getInstance().getAlipayLocaleFlag() == 1 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 3 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 2) {
                lines = getResources().getStringArray(R.array.provincecityselect_cnregion4alipay);
            } else {
                lines = getResources().getStringArray(R.array.provincecityselect_enregion4alipay);
            }
            for (String split : lines) {
                String[] strs = split.split("_|\\|");
                if (strs.length == 4) {
                    countryInfo = new CountryInfo();
                    countryInfo.setCountryName(strs[strs.length - 1]);
                    countryInfo.setCode(strs[strs.length - 2]);
                    tmpDatas.add(countryInfo);
                } else if (strs.length == 5) {
                    if (countryInfo != null) {
                        provinceInfo = new ProvinceInfo();
                        provinceInfo.setProvinceName(strs[strs.length - 1]);
                        provinceInfo.setCode(strs[strs.length - 2]);
                        countryInfo.addProvinces(provinceInfo);
                    }
                } else if (strs.length == 6 && provinceInfo != null) {
                    CityInfo cityInfo = new CityInfo();
                    cityInfo.setCityName(strs[strs.length - 1]);
                    cityInfo.setCode(strs[strs.length - 2]);
                    provinceInfo.addCity(cityInfo);
                }
            }
            getPatchDatas(tmpDatas);
            if (!(this.isUseChinese || LocaleHelper.getInstance().getAlipayLocaleFlag() == 1 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 3 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 2)) {
                Collections.sort(tmpDatas, new Comparator<CountryInfo>() {
                    public final /* synthetic */ int compare(Object obj, Object obj2) {
                        return a((CountryInfo) obj, (CountryInfo) obj2);
                    }

                    private static int a(CountryInfo lhs, CountryInfo rhs) {
                        if (TextUtils.isEmpty(lhs.getCountryName())) {
                            return 1;
                        }
                        if (TextUtils.isEmpty(rhs.getCountryName())) {
                            return -1;
                        }
                        return lhs.getCountryName().toUpperCase().compareTo(rhs.getCountryName().toUpperCase());
                    }
                });
            }
            this.currentDatas = tmpDatas;
        }
        if (TextUtils.isEmpty(this.filterCountry) || this.currentDatas == null) {
            refreshList(2, 0, this.selectedCountry, this.selectedProvince, this.selectedCity);
            return;
        }
        refreshList(1, getCountryIndex(this.currentDatas, this.selectedCountry), this.selectedCountry, this.selectedProvince, this.selectedCity);
    }

    private void getPatchDatas(List<CountryInfo> tmpDatas) {
        String patchKey;
        if (getIntent() == null || getIntent().getExtras() == null || TextUtils.isEmpty(getIntent().getExtras().getString(KEY_PRE_DATA))) {
            if (this.isUseChinese || LocaleHelper.getInstance().getAlipayLocaleFlag() == 1 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 3 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 2) {
                patchKey = "BEEProvinceCitysForHans";
            } else {
                patchKey = "BEEProvinceCitysForEn";
            }
            try {
                ConfigService configService = (ConfigService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(ConfigService.class.getName());
                if (configService != null) {
                    String patchValue = configService.getConfig(patchKey);
                    if (!TextUtils.isEmpty(patchValue)) {
                        for (CountryInfo patchCountryInfo : (List) JSON.parseObject(patchValue, (TypeReference<T>) new TypeReference<List<CountryInfo>>() {
                        }, new Feature[0])) {
                            int countryIndex = getCountryIndex(tmpDatas, patchCountryInfo.getCountryName());
                            if (countryIndex < 0) {
                                tmpDatas.add(patchCountryInfo);
                            } else {
                                CountryInfo localCountry = tmpDatas.get(countryIndex);
                                for (ProvinceInfo patchProvinceInfo : patchCountryInfo.getProvinces()) {
                                    int provinceIndex = getProvinceIndex(localCountry.getProvinces(), patchProvinceInfo.getProvinceName());
                                    if (provinceIndex < 0) {
                                        localCountry.getProvinces().add(patchProvinceInfo);
                                    } else {
                                        localCountry.getProvinces().get(provinceIndex).setCitys(patchProvinceInfo.getCitys());
                                        localCountry.getProvinces().get(provinceIndex).setCode(patchProvinceInfo.getCode());
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error((String) Constants.BASE_TAG, e);
            }
        }
    }

    /* access modifiers changed from: protected */
    @AfterViews
    public void afterBindViews() {
        setUpListView();
        loadDataFromLocal();
        this.titleBar.getBackButton().setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                ProvinceCityListActivity.this.handleBackBtn();
            }
        });
        if (!this.isHideLocateHeader) {
            this.locatedPosition.setOnClickListener(new OnClickListener() {
                public final void onClick(View v) {
                    LoggerFactory.getTraceLogger().debug(ProvinceCityListActivity.TAG, "locatedPosition clicked.");
                    if (TextUtils.isEmpty(ProvinceCityListActivity.this.locationStr)) {
                        ProvinceCityListActivity.this.startLocation(true);
                    } else {
                        ProvinceCityListActivity.this.setResultFinish();
                    }
                }
            });
            startLocation(false);
            return;
        }
        setHeaderGone();
    }

    private void setUpListView() {
        this.provinceList.addHeaderView(this.locationHeader, null, false);
        this.provinceList.addHeaderView(this.locationContent, null, false);
        this.provinceList.addHeaderView(this.listHeader, null, false);
        this.provinceList.addFooterView(this.listFooter, null, false);
        this.mAdapter = new ProvinceCityListAdapter(this);
        this.provinceList.setAdapter(this.mAdapter);
        this.provinceList.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = position - ProvinceCityListActivity.this.provinceList.getHeaderViewsCount();
                if (index >= 0) {
                    ProvinceCityListActivity.this.handleItemClick(ProvinceCityListActivity.this.currentType, index);
                }
            }
        });
    }

    public void startLocation(boolean requestPermission) {
        LoggerFactory.getTraceLogger().debug(TAG, "startLocation(): requestPermission=" + requestPermission);
        if (VERSION.SDK_INT < 23 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            LoggerFactory.getTraceLogger().debug(TAG, "ACCESS_FINE_LOCATION permission has already been granted, start location..");
            doStartLocation();
            return;
        }
        LoggerFactory.getTraceLogger().debug(TAG, "ACCESS_FINE_LOCATION permission not granted, don't perform location");
        this.locatedPosition.setText(getString(R.string.regionselect_lbs_fail));
        this.locatedPosition.setClickable(false);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 1) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        } else if (grantResults.length == 1 && grantResults[0] == 0) {
            LoggerFactory.getTraceLogger().debug(TAG, "request ACCESS_FINE_LOCATION permission succeeded, start location..");
            doStartLocation();
        } else {
            LoggerFactory.getTraceLogger().debug(TAG, "request ACCESS_FINE_LOCATION permission denied by user.");
            showLoactionInfo(getResources().getString(R.string.regionselect_lbs_fail_and_retry));
        }
    }

    public void doStartLocation() {
        this.locatedPosition.setText(getString(R.string.regionselect_lbs_on_location));
        this.locatedPosition.setClickable(false);
        LBSLocationManagerService lbsService = (LBSLocationManagerService) MicroServiceUtil.getExtServiceByInterface(LBSLocationManagerService.class);
        if (lbsService != null) {
            LBSLocationRequest lbsRequest = new LBSLocationRequest();
            lbsRequest.setNeedAddress(true);
            lbsRequest.setIsHighAccuracy(false);
            lbsRequest.setReGeoLevel(4);
            lbsRequest.setBizType("android-position-citySelector");
            lbsService.locationWithRequest(lbsRequest, this.locationListener);
        }
    }

    /* access modifiers changed from: protected */
    @UiThread
    public void showLoactionInfo(String locationStrShow) {
        this.locatedPosition.setText(locationStrShow);
        this.locatedPosition.setClickable(true);
    }

    /* access modifiers changed from: private */
    public String removeSuffix(String addr, int type) {
        if (type == 1) {
            if (TextUtils.isEmpty(addr) || !addr.endsWith(getString(R.string.regionselect_province_suffix))) {
                return addr;
            }
            return addr.substring(0, addr.length() - 1);
        } else if (type != 0 || TextUtils.isEmpty(addr) || !addr.endsWith(getString(R.string.regionselect_city_suffix))) {
            return addr;
        } else {
            return addr.substring(0, addr.length() - 1);
        }
    }

    /* access modifiers changed from: private */
    public boolean isUseMapKey(String code) {
        if (this.mDistrictMap == null || TextUtils.isEmpty(code)) {
            return false;
        }
        return this.mDistrictMap.containsKey(code);
    }

    /* access modifiers changed from: private */
    public void setResultFinish() {
        LoggerFactory.getTraceLogger().debug(TAG, "setResultFinish: locationStr=" + this.locationStr);
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(this.locationStr)) {
            String[] strs = this.locationStr.split("#@#");
            int len = strs.length;
            for (int i = 0; i < len; i++) {
                switch (i) {
                    case 0:
                        intent.putExtra(KEY_COUNTRY, strs[i]);
                        intent.putExtra(KEY_COUNTRY_CODE, this.isReturnCode ? getCountryCode(strs[i]) : "");
                        break;
                    case 1:
                        intent.putExtra(KEY_PROVINCE, strs[i]);
                        intent.putExtra(KEY_PROVINCE_CODE, this.isReturnCode ? getProvinceCode(strs[0], strs[i]) : "");
                        break;
                    case 2:
                        intent.putExtra(KEY_CITY, strs[i]);
                        intent.putExtra(KEY_CITY_CODE, this.isReturnCode ? getCityCode(strs[0], strs[1], strs[i]) : "");
                        break;
                }
            }
            if (this.isFromService) {
                IProvinceCitySelectCallBack callback = ((CitySelectService) this.mApp.getMicroApplicationContext().getExtServiceByInterface(CitySelectService.class.getName())).getProvinceCallBack();
                if (callback != null) {
                    callback.OnProvinceCitySelect(intent);
                }
            } else {
                setResult(-1, intent);
            }
        }
        finish();
    }

    private String getCountryCode(String countryName) {
        if (TextUtils.isEmpty(countryName)) {
            return "";
        }
        if (this.currentDatas == null || this.currentDatas.isEmpty()) {
            return "";
        }
        for (CountryInfo ci : this.currentDatas) {
            if (TextUtils.equals(ci.getCountryName(), countryName)) {
                return ci.getCode();
            }
        }
        return "";
    }

    private String getProvinceCode(String countryName, String provinceName) {
        if (TextUtils.isEmpty(countryName) || TextUtils.isEmpty(provinceName)) {
            return "";
        }
        if (this.currentDatas == null || this.currentDatas.isEmpty()) {
            return "";
        }
        CountryInfo info = null;
        Iterator<CountryInfo> it = this.currentDatas.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            CountryInfo ci = it.next();
            if (TextUtils.equals(ci.getCountryName(), countryName)) {
                info = ci;
                break;
            }
        }
        if (info == null) {
            return "";
        }
        if (info.getProvinces() == null || info.getProvinces().isEmpty()) {
            return "";
        }
        String code = "";
        Iterator<ProvinceInfo> it2 = info.getProvinces().iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            ProvinceInfo pi = it2.next();
            if (TextUtils.equals(pi.getProvinceName(), provinceName)) {
                code = pi.getCode();
                break;
            }
        }
        if (TextUtils.isEmpty(code)) {
            return code;
        }
        if (!TextUtils.equals(info.getCode(), "US") && !TextUtils.equals(info.getCode(), "CA")) {
            return code;
        }
        parseProvinceCodeMapLazy();
        for (ProvinceCodeMap map : this.provinceCodeMaps) {
            if (TextUtils.equals(map.countryCode, info.getCode()) && TextUtils.equals(map.provinceCode, code)) {
                return map.provinceIsoCode;
            }
        }
        return code;
    }

    private String getCityCode(String countryName, String provinceName, String cityName) {
        if (TextUtils.isEmpty(countryName) || TextUtils.isEmpty(provinceName)) {
            return "";
        }
        if (this.currentDatas == null || this.currentDatas.isEmpty()) {
            return "";
        }
        CountryInfo info = null;
        Iterator<CountryInfo> it = this.currentDatas.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            CountryInfo ci = it.next();
            if (TextUtils.equals(ci.getCountryName(), countryName)) {
                info = ci;
                break;
            }
        }
        if (info == null || info.getProvinces() == null || info.getProvinces().isEmpty()) {
            return "";
        }
        ProvinceInfo provinceInfo = null;
        Iterator<ProvinceInfo> it2 = info.getProvinces().iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            ProvinceInfo pi = it2.next();
            if (TextUtils.equals(pi.getProvinceName(), provinceName)) {
                provinceInfo = pi;
                break;
            }
        }
        if (provinceInfo == null || provinceInfo.getCitys() == null || provinceInfo.getCitys().isEmpty()) {
            return "";
        }
        for (CityInfo ci2 : provinceInfo.getCitys()) {
            if (TextUtils.equals(ci2.getCityName(), cityName)) {
                return ci2.getCode();
            }
        }
        return "";
    }

    private void parseProvinceCodeMapLazy() {
        if (this.provinceCodeMaps == null || this.provinceCodeMaps.isEmpty()) {
            this.provinceCodeMaps = new ArrayList();
            for (String split : getResources().getStringArray(R.array.provincecityselect_state_code_map)) {
                String[] strs = split.split("_|\\|");
                if (strs.length == 3) {
                    ProvinceCodeMap item = new ProvinceCodeMap();
                    item.countryCode = strs[0];
                    item.provinceCode = strs[1];
                    item.provinceIsoCode = strs[2];
                    this.provinceCodeMaps.add(item);
                }
            }
        }
    }

    private void setHeaderGone() {
        this.locationHeader.setVisibility(8);
        this.provinceList.removeHeaderView(this.locationHeader);
        this.locationContent.setVisibility(8);
        this.provinceList.removeHeaderView(this.locationContent);
        this.listHeader.setVisibility(0);
        this.mAdapter.notifyDataSetChanged();
    }

    /* access modifiers changed from: protected */
    public void setAdapterData(int type, int index, String country, String province, String city) {
        this.currentType = type;
        if (this.mAdapter != null) {
            setData(type, index, this.selectedCountry, this.selectedProvince, this.selectedCity);
        }
    }

    public void setData(int type, int index, String country, String province, String city) {
        if (this.currentDatas != null && index >= 0) {
            this.currentType = type;
            switch (type) {
                case 0:
                    if (this.mProvinces != null) {
                        this.mCities = this.mProvinces.get(index).getCitys();
                        if (!(this.isUseChinese || LocaleHelper.getInstance().getAlipayLocaleFlag() == 1 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 3 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 2)) {
                            Collections.sort(this.mCities, new Comparator<CityInfo>() {
                                public final /* synthetic */ int compare(Object obj, Object obj2) {
                                    return a((CityInfo) obj, (CityInfo) obj2);
                                }

                                private static int a(CityInfo lhs, CityInfo rhs) {
                                    if (TextUtils.isEmpty(lhs.getCityName())) {
                                        return 1;
                                    }
                                    if (TextUtils.isEmpty(rhs.getCityName())) {
                                        return -1;
                                    }
                                    return lhs.getCityName().toUpperCase().compareTo(rhs.getCityName().toUpperCase());
                                }
                            });
                        }
                    }
                    int currentCityIndex = -1;
                    if (city != null) {
                        currentCityIndex = getCityIndex(this.mCities, city);
                    }
                    if (currentCityIndex != -1) {
                        CityInfo cityInfo = this.mCities.get(currentCityIndex);
                        cityInfo.setSelected(true);
                        this.mCities.remove(currentCityIndex);
                        this.mCities.add(0, cityInfo);
                    }
                    this.mAdapter.setData(this.mCities);
                    return;
                case 1:
                    if (this.currentDatas.get(index) != null) {
                        this.mProvinces = this.currentDatas.get(index).getProvinces();
                        if (!(this.isUseChinese || LocaleHelper.getInstance().getAlipayLocaleFlag() == 1 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 3 || LocaleHelper.getInstance().getAlipayLocaleFlag() == 2)) {
                            Collections.sort(this.mProvinces, new Comparator<ProvinceInfo>() {
                                public final /* synthetic */ int compare(Object obj, Object obj2) {
                                    return a((ProvinceInfo) obj, (ProvinceInfo) obj2);
                                }

                                private static int a(ProvinceInfo lhs, ProvinceInfo rhs) {
                                    if (TextUtils.isEmpty(lhs.getProvinceName())) {
                                        return 1;
                                    }
                                    if (TextUtils.isEmpty(rhs.getProvinceName())) {
                                        return -1;
                                    }
                                    return lhs.getProvinceName().toUpperCase().compareTo(rhs.getProvinceName().toUpperCase());
                                }
                            });
                        }
                    }
                    int currentProvinceIndex = -1;
                    if (province != null) {
                        currentProvinceIndex = getProvinceIndex(this.mProvinces, province);
                    }
                    if (currentProvinceIndex != -1) {
                        ProvinceInfo provinceInfo = this.mProvinces.get(currentProvinceIndex);
                        provinceInfo.setSelected(true);
                        this.mProvinces.remove(currentProvinceIndex);
                        this.mProvinces.add(0, provinceInfo);
                    }
                    this.mAdapter.setData(this.mProvinces);
                    return;
                case 2:
                    int currentCountryIndex = -1;
                    if (country != null) {
                        currentCountryIndex = getCountryIndex(this.currentDatas, country);
                    }
                    if (currentCountryIndex != -1) {
                        CountryInfo countryInfo = this.currentDatas.get(currentCountryIndex);
                        if (countryInfo != null) {
                            countryInfo.setSelected(true);
                            this.currentDatas.remove(currentCountryIndex);
                            this.currentDatas.add(0, countryInfo);
                        }
                    }
                    this.mAdapter.setData(this.currentDatas);
                    return;
                default:
                    return;
            }
        }
    }

    private int getCountryIndex(List<CountryInfo> list, String countryName) {
        for (int i = 0; i < list.size(); i++) {
            if (countryName.equals(list.get(i).getCountryName())) {
                return i;
            }
        }
        return -1;
    }

    private int getProvinceIndex(List<ProvinceInfo> list, String provinceName) {
        for (int i = 0; i < list.size(); i++) {
            if (provinceName.equals(list.get(i).getProvinceName())) {
                return i;
            }
        }
        return -1;
    }

    private int getCityIndex(List<CityInfo> list, String cityName) {
        for (int i = 0; i < list.size(); i++) {
            if (cityName.equals(list.get(i).getCityName())) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    @UiThread
    public void refreshList(int type, int index, String country, String province, String city) {
        setAdapterData(type, index, country, province, city);
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: protected */
    @UiThread
    public void refreshList(int type, int index, int listIndex, int listTop) {
        setAdapterData(type, index, this.selectedCountry, this.selectedProvince, this.selectedCity);
        this.provinceList.setSelectionFromTop(listIndex, listTop);
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: protected */
    public void handleBackBtn() {
        switch (this.currentType) {
            case 0:
                PageInfo pop = null;
                PageInfo bTop = null;
                if (!this.stack.isEmpty()) {
                    pop = this.stack.pop();
                }
                if (!this.stack.isEmpty()) {
                    bTop = this.stack.peek();
                }
                setHeaderGone();
                if (!TextUtils.isEmpty(this.filterCountry)) {
                    refreshList(1, getCountryIndex(this.currentDatas, this.selectedCountry), this.selectedCountry, this.selectedProvince, this.selectedCity);
                    return;
                } else if (pop == null || bTop == null) {
                    refreshList(1, 0, this.selectedCountry, this.selectedProvince, this.selectedCity);
                    return;
                } else {
                    refreshList(1, bTop.getIndex(), pop.getListIndex(), pop.getListTop());
                    return;
                }
            case 1:
                if (!TextUtils.isEmpty(this.filterCountry)) {
                    finish();
                    return;
                }
                PageInfo pageInfo = null;
                if (!this.stack.isEmpty()) {
                    pageInfo = this.stack.pop();
                }
                setHeaderGone();
                if (pageInfo == null) {
                    refreshList(2, 0, this.selectedCountry, this.selectedProvince, this.selectedCity);
                    return;
                }
                refreshList(2, 0, pageInfo.getListIndex(), pageInfo.getListTop());
                return;
            case 2:
                if (!this.stack.isEmpty()) {
                    this.stack.pop();
                }
                finish();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void handleItemClick(int type, int position) {
        LoggerFactory.getTraceLogger().debug(TAG, "handleItemClick(): type=" + type + ", position=" + position);
        switch (type) {
            case 0:
                if (position >= 0 && position < this.mCities.size()) {
                    tracePage(type, position, this.mCities.get(position).getCityName());
                    this.locationStr = getFinalStr();
                    setResultFinish();
                    return;
                }
                return;
            case 1:
                setAdapterData(0, position, this.selectedCountry, this.selectedProvince, this.selectedCity);
                tracePage(type, position, this.mProvinces.get(position).getProvinceName());
                if (this.mCities == null || this.mCities.isEmpty()) {
                    this.locationStr = getFinalStr();
                    setResultFinish();
                    return;
                }
                setHeaderGone();
                this.provinceList.setSelection(0);
                return;
            case 2:
                setAdapterData(1, position, this.selectedCountry, this.selectedProvince, this.selectedCity);
                tracePage(type, position, this.currentDatas.get(position).getCountryName());
                if (this.mProvinces == null || this.mProvinces.isEmpty()) {
                    this.locationStr = getFinalStr();
                    setResultFinish();
                    return;
                }
                setHeaderGone();
                this.provinceList.post(new Runnable() {
                    public final void run() {
                        ProvinceCityListActivity.this.provinceList.setSelection(0);
                    }
                });
                return;
            default:
                return;
        }
    }

    private String getFinalStr() {
        StringBuilder sb = new StringBuilder();
        while (!this.stack.empty()) {
            sb.insert(0, this.stack.pop().getName() + "#@#");
        }
        if (!TextUtils.isEmpty(this.filterCountry)) {
            sb.insert(0, this.filterCountry + "#@#");
        }
        return sb.toString();
    }

    private void tracePage(int type, int position, String name) {
        int top = 0;
        PageInfo pageInfo = new PageInfo();
        pageInfo.setIndex(position);
        pageInfo.setName(name);
        pageInfo.setType(type);
        int index = this.provinceList.getFirstVisiblePosition();
        View v = this.provinceList.getChildAt(0);
        if (v != null) {
            top = v.getTop();
        }
        pageInfo.setListIndex(index);
        pageInfo.setListTop(top);
        this.stack.push(pageInfo);
        switch (type) {
            case 0:
                this.selectedProvinceCityStr += " - " + name;
                break;
            case 1:
                if (TextUtils.isEmpty(this.selectedProvinceCityStr)) {
                    this.selectedProvinceCityStr = this.selectedCountry;
                }
                this.selectedProvinceCityStr += " - " + name;
                break;
            case 2:
                this.selectedProvinceCityStr = name;
                break;
        }
        this.selectedProvinceCityHeader.setText(this.selectedProvinceCityStr);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(CitySelectService.class.getName());
        if (citySelectService != null) {
            citySelectService.setProvinceCallBack(null);
        }
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3482";
    }
}
