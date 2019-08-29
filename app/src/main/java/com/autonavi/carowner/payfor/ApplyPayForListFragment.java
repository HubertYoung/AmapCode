package com.autonavi.carowner.payfor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.amap.bundle.aosservice.request.AosPostRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosByteResponse;
import com.amap.bundle.aosservice.response.AosResponse;
import com.amap.bundle.aosservice.response.AosResponseCallbackOnUi;
import com.amap.bundle.aosservice.response.AosResponseException;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.drivecommon.inter.NetConstant;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.drivecommon.payfor.PayforNaviData;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.carowner.payfor.view.NaviInfoChildItemView;
import com.autonavi.carowner.payfor.view.NaviInfoChildItemView.a;
import com.autonavi.carowner.payfor.view.NaviInfoGroupItemView;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.usepay.UsePayRequestHolder;
import com.autonavi.minimap.usepay.param.ApplyCheckRequest;
import com.autonavi.minimap.usepay.param.ListsRequest;
import com.autonavi.minimap.usepay.param.RelateRequest;
import com.autonavi.minimap.widget.AmapTextView;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

@PageAction("apply_pay_main_page")
public class ApplyPayForListFragment extends DriveBasePage<bhi> implements OnClickListener, OnCheckedChangeListener, a, LocationNone, launchModeSingleTask {
    public static final String KEY_PAGE_TYPE = "showPage";
    public static final int PAGE_APPLYED = 2;
    private static final int PAGE_SIZE = 10;
    public static final int PAGE_UNAPPLYED = 1;
    public static final int REQUESTCODE_TYPECHOOSE = 1;
    public static final String TAG = "ApplyPayForListFragment";
    private Button doconfirmmappoint;
    private ViewAnimator mAnimatorList;
    private RadioGroup mApplyTab;
    /* access modifiers changed from: private */
    public int mCurPage = 0;
    /* access modifiers changed from: private */
    public boolean mIsActivityEnd = false;
    private int mLocaleTotalItem = 0;
    private Button mLoginButton;
    private AmapTextView mLookOverActivitiesView;
    private PayforListAdapter mNativeDataAdapter;
    private TextView mNativeDataCountView;
    private ListView mNativeNaviDataListView;
    private RadioButton mNativeRadio;
    /* access modifiers changed from: private */
    public boolean mNeedChangePage = true;
    /* access modifiers changed from: private */
    public boolean mNeedRefresh = true;
    /* access modifiers changed from: private */
    public PayforListAdapter mNetDataAdapter;
    /* access modifiers changed from: private */
    public TextView mNetDataCountView;
    private TextView mNetDataEmptyInfoText;
    /* access modifiers changed from: private */
    public PullToRefreshListView mNetNaviDataListView;
    private RadioButton mNetRadio;
    /* access modifiers changed from: private */
    public int mTotalItem = 0;
    private ProgressDlg progressDialog;

    class PayforExpandableListAdapter extends BaseExpandableListAdapter {
        /* access modifiers changed from: private */
        public Map<String, List<PayforNaviData>> mDatas;
        private List<String> mSortedKey;

        public long getChildId(int i, int i2) {
            return (long) i2;
        }

        public long getGroupId(int i) {
            return (long) i;
        }

        public boolean hasStableIds() {
            return false;
        }

        public boolean isChildSelectable(int i, int i2) {
            return false;
        }

        private PayforExpandableListAdapter() {
        }

        public void changeDatas(List<String> list, Map<String, List<PayforNaviData>> map) {
            this.mSortedKey = list;
            this.mDatas = map;
        }

        private List<PayforNaviData> getGroupList(int i) {
            return this.mDatas.get(this.mSortedKey.get(i));
        }

        public int getGroupCount() {
            return this.mSortedKey.size();
        }

        public int getChildrenCount(int i) {
            return getGroupList(i).size();
        }

        public Object getGroup(int i) {
            return getGroupList(i);
        }

        public Object getChild(int i, int i2) {
            return getGroupList(i).get(i2);
        }

        public View getGroupView(int i, boolean z, View view, ViewGroup viewGroup) {
            NaviInfoGroupItemView naviInfoGroupItemView;
            if (view == null || !(view instanceof NaviInfoGroupItemView)) {
                naviInfoGroupItemView = new NaviInfoGroupItemView(ApplyPayForListFragment.this.getContext());
            } else {
                naviInfoGroupItemView = (NaviInfoGroupItemView) view;
            }
            String str = this.mSortedKey.get(i);
            if (naviInfoGroupItemView != null) {
                naviInfoGroupItemView.setDate(str);
                naviInfoGroupItemView.setFirst(i == 0);
            }
            return naviInfoGroupItemView;
        }

        public View getChildView(int i, int i2, boolean z, View view, ViewGroup viewGroup) {
            NaviInfoChildItemView naviInfoChildItemView;
            if (view == null || !(view instanceof NaviInfoChildItemView)) {
                naviInfoChildItemView = new NaviInfoChildItemView(ApplyPayForListFragment.this.getContext());
            } else {
                naviInfoChildItemView = (NaviInfoChildItemView) view;
            }
            naviInfoChildItemView.setData((PayforNaviData) this.mDatas.get(this.mSortedKey.get(i)).get(i2), ApplyPayForListFragment.this.mIsActivityEnd);
            naviInfoChildItemView.setOnApplyBtnClickListener(ApplyPayForListFragment.this);
            return naviInfoChildItemView;
        }
    }

    class PayforListAdapter extends BaseAdapter {
        private List<PayforNaviData> dataList;
        private Map<String, List<PayforNaviData>> dataMap;
        private final PayforExpandableListAdapter expandableAdapter;
        private List<String> sortedKey;

        class a {
            int a;
            int b;
            boolean c;

            a() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public PayforListAdapter(List<PayforNaviData> list) {
            this.expandableAdapter = new PayforExpandableListAdapter();
            setNaviData(list);
        }

        public void setNaviData(List<PayforNaviData> list) {
            this.dataList = list;
            this.dataMap = sortNaviData(null, this.dataList);
            this.sortedKey = new ArrayList(this.dataMap.keySet());
            this.expandableAdapter.changeDatas(this.sortedKey, this.dataMap);
        }

        public void addNaviData(List<PayforNaviData> list) {
            this.dataList.addAll(list);
            this.dataMap = sortNaviData(this.expandableAdapter.mDatas, list);
            this.sortedKey = new ArrayList(this.dataMap.keySet());
            this.expandableAdapter.changeDatas(this.sortedKey, this.dataMap);
        }

        public void clearData() {
            this.dataList.clear();
            this.dataMap.clear();
            this.sortedKey.clear();
            this.expandableAdapter.changeDatas(this.sortedKey, this.dataMap);
        }

        public int getCount() {
            return this.dataList.size() + this.dataMap.size();
        }

        public Object getItem(int i) {
            a position = getPosition(i);
            if (position.b == -1) {
                return this.expandableAdapter.getGroup(position.a);
            }
            return this.expandableAdapter.getChild(position.a, position.b);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            a position = getPosition(i);
            if (position.b == -1) {
                return this.expandableAdapter.getGroupView(position.a, true, view, viewGroup);
            }
            return this.expandableAdapter.getChildView(position.a, position.b, position.c, view, viewGroup);
        }

        private Map<String, List<PayforNaviData>> sortNaviData(Map<String, List<PayforNaviData>> map, List<PayforNaviData> list) {
            if (map == null) {
                map = new LinkedHashMap<>();
            }
            for (PayforNaviData next : list) {
                List list2 = map.get(next.date);
                if (list2 == null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(next);
                    map.put(next.date, arrayList);
                } else {
                    list2.add(next);
                }
            }
            return map;
        }

        private a getPosition(int i) {
            a aVar = new a();
            boolean z = false;
            int i2 = i;
            int i3 = 0;
            while (true) {
                if (i3 >= this.sortedKey.size()) {
                    break;
                }
                int childrenCount = this.expandableAdapter.getChildrenCount(i3);
                if (i2 <= childrenCount) {
                    aVar.a = i3;
                    aVar.b = i2 - 1;
                    if (i2 == childrenCount) {
                        z = true;
                    }
                    aVar.c = z;
                } else {
                    i2 = (i2 - childrenCount) - 1;
                    i3++;
                }
            }
            return aVar;
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.activities_apply_payfor_list);
    }

    /* access modifiers changed from: protected */
    public bhi createPresenter() {
        return new bhi(this);
    }

    public void initView() {
        if (euk.a()) {
            int a = euk.a(getContext());
            View contentView = getContentView();
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop() + a, contentView.getPaddingRight(), contentView.getPaddingBottom());
        }
        getContentView().findViewById(R.id.btn_back).setOnClickListener(this);
        this.mApplyTab = (RadioGroup) getContentView().findViewById(R.id.apply_tab);
        this.mApplyTab.setOnCheckedChangeListener(this);
        this.mNativeRadio = (RadioButton) getContentView().findViewById(R.id.not_apply);
        this.mNetRadio = (RadioButton) getContentView().findViewById(R.id.have_apply);
        this.mAnimatorList = (ViewAnimator) getContentView().findViewById(R.id.animator_list);
        this.doconfirmmappoint = (Button) getContentView().findViewById(R.id.doconfirmmappoint);
        this.doconfirmmappoint.setOnClickListener(this);
        initNativeDataView(this.mAnimatorList.getChildAt(0));
        initNetDataView(this.mAnimatorList.getChildAt(1));
    }

    private void initNativeDataView(View view) {
        ((TextView) view.findViewById(R.id.information)).setText(R.string.activities_destination_record);
        this.mNativeDataCountView = (TextView) view.findViewById(R.id.apply_count);
        this.mNativeNaviDataListView = (ListView) view.findViewById(R.id.naviinfo_list);
        this.mLookOverActivitiesView = (AmapTextView) view.findViewById(R.id.look_over_activities_view);
        this.mLookOverActivitiesView.setText(Html.fromHtml(getContext().getString(R.string.error_more)));
        this.mLookOverActivitiesView.setOnClickListener(this);
        View findViewById = view.findViewById(R.id.empty_view);
        this.mNativeNaviDataListView.setEmptyView(findViewById);
        Button button = (Button) findViewById.findViewById(R.id.button1);
        ((TextView) findViewById.findViewById(R.id.info)).setText(R.string.activities_native_navi_date_empty_info);
        button.setText(R.string.activities_go_to_navigation);
        button.setBackgroundResource(R.drawable.activities_blue_corner_btn_bg);
        button.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("amap.extra.route.route");
                intent.setPackage(AMapAppGlobal.getApplication().getPackageName());
                PageBundle pageBundle = new PageBundle(intent);
                pageBundle.putObject("bundle_key_route_type", RouteType.CAR);
                bax bax = (bax) a.a.a(bax.class);
                if (bax != null) {
                    bax.a(pageBundle);
                }
            }
        });
    }

    private void initNetDataView(View view) {
        ((TextView) view.findViewById(R.id.information)).setText(R.string.activities_destination_applied_record);
        this.mNetDataCountView = (TextView) view.findViewById(R.id.apply_count);
        this.mNetDataCountView.setText(Html.fromHtml(getContext().getString(R.string.activities_destination_record_count, new Object[]{Integer.valueOf(0)})));
        this.mNetNaviDataListView = (PullToRefreshListView) view.findViewById(R.id.refresh_naviinfo_list);
        this.mLookOverActivitiesView = (AmapTextView) view.findViewById(R.id.look_over_activities_view);
        this.mLookOverActivitiesView.setText(Html.fromHtml(getContext().getString(R.string.error_more)));
        this.mLookOverActivitiesView.setOnClickListener(this);
        this.mNetNaviDataListView.setParentWindowClass(getClass().getName());
        LoadingLayout changeFooter = this.mNetNaviDataListView.changeFooter();
        changeFooter.setVisibility(0);
        this.mNetNaviDataListView.mLvFooterLoadingFrame.removeView(this.mNetNaviDataListView.mFooterLoadingView);
        ((ListView) this.mNetNaviDataListView.getRefreshableView()).addFooterView(changeFooter, null, false);
        this.mNetNaviDataListView.setFootershowflag(false);
        this.mNetNaviDataListView.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                ApplyPayForListFragment.this.getNetNaviInfo(1);
            }

            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (ApplyPayForListFragment.this.mTotalItem > ApplyPayForListFragment.this.mCurPage * 10) {
                    ApplyPayForListFragment.this.getNetNaviInfo(ApplyPayForListFragment.this.mCurPage + 1);
                } else {
                    ApplyPayForListFragment.this.mNetNaviDataListView.onRefreshComplete();
                }
            }
        });
        View findViewById = view.findViewById(R.id.empty_view);
        this.mNetNaviDataListView.setEmptyView(findViewById);
        this.mNetDataEmptyInfoText = (TextView) findViewById.findViewById(R.id.info);
        this.mLoginButton = (Button) findViewById.findViewById(R.id.button1);
        this.mNetDataEmptyInfoText.setText(R.string.activities_not_login_empty_list_info);
        this.mLoginButton.setText(R.string.activities_go_to_login);
        this.mLoginButton.setBackgroundResource(R.drawable.activities_orange_corner_btn_bg);
        this.mLoginButton.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    iAccountService.a(ApplyPayForListFragment.this.getPageContext(), (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                            if (z) {
                                ApplyPayForListFragment.this.mNeedRefresh = true;
                                ApplyPayForListFragment.this.onStart();
                            }
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void setNetListRefreshMode() {
        this.mNetNaviDataListView.setHeaderText(getString(R.string.oper_pull_to_refresh), getString(R.string.oper_loosen_to_refresh), getString(R.string.oper_refreshing));
        if (this.mTotalItem > this.mCurPage * 10) {
            this.mNetNaviDataListView.setFooterText(getString(R.string.oper_pull_more), getString(R.string.oper_loosen_more), getString(R.string.oper_loading));
            this.mNetNaviDataListView.setMode(Mode.BOTH);
            this.mNetNaviDataListView.changeFooter().setVisibility(0);
            this.mNetNaviDataListView.showImageFoot();
        } else if (this.mTotalItem == 0) {
            if (isLogin()) {
                this.mNetNaviDataListView.setMode(Mode.PULL_FROM_START);
            } else {
                this.mNetNaviDataListView.setMode(Mode.DISABLED);
            }
            this.mNetNaviDataListView.changeFooter().setVisibility(8);
        } else {
            this.mNetNaviDataListView.setMode(Mode.PULL_FROM_START);
            this.mNetNaviDataListView.setFooterText(getString(R.string.oper_no_more), getString(R.string.oper_no_more), getString(R.string.oper_no_more));
            this.mNetNaviDataListView.changeFooter().setVisibility(0);
            this.mNetNaviDataListView.hideImageFoot();
        }
    }

    public void onPageResume() {
        PageBundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("ApplyPayForTypeChooseFragment.resultData")) {
            this.mNeedRefresh = arguments.getBoolean("ApplyPayForTypeChooseFragment.resultData");
        }
        if (this.mNeedChangePage) {
            PageBundle arguments2 = getArguments();
            if (arguments2 != null) {
                int i = arguments2.getInt(KEY_PAGE_TYPE);
                if (i == 1) {
                    this.mNeedChangePage = false;
                    this.mNativeRadio.setChecked(true);
                } else if (i == 2) {
                    this.mNeedChangePage = false;
                    this.mNetRadio.setChecked(true);
                }
            }
        }
        if (isLogin()) {
            this.mNetDataEmptyInfoText.setText(R.string.activities_net_navi_data_empty_list_info);
            this.mLoginButton.setVisibility(8);
        } else {
            this.mNetDataEmptyInfoText.setText(R.string.activities_not_login_empty_list_info);
            this.mLoginButton.setVisibility(0);
        }
        showNativeNaviData();
        setNetListRefreshMode();
        if (this.mNeedRefresh || this.mNetDataAdapter == null || this.mNetDataAdapter.getCount() == 0) {
            this.mNeedRefresh = false;
            getNetNaviInfo(1);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_back) {
            if (isAlive()) {
                finish();
            }
        } else if (id == R.id.doconfirmmappoint || id == R.id.look_over_activities_view) {
            StringBuilder sb = new StringBuilder();
            sb.append(ConfigerHelper.getInstance().getActivitiesUrl());
            sb.append("/activity/payError/index.html?applypage=1");
            aja aja = new aja(sb.toString());
            aja.b = new ajf();
            aix aix = (aix) a.a.a(aix.class);
            if (aix != null) {
                aix.a((bid) this, aja);
            }
        }
    }

    public void onApplyPayfor(PayforNaviData payforNaviData) {
        applyPayFor(payforNaviData);
    }

    private void showNativeNaviData() {
        List<PayforNaviData> nativeNaviDatas = PayforNaviData.getNativeNaviDatas();
        if (this.mNativeDataAdapter == null) {
            this.mNativeDataAdapter = new PayforListAdapter(nativeNaviDatas);
            this.mNativeNaviDataListView.setAdapter(this.mNativeDataAdapter);
        } else {
            this.mNativeDataAdapter.setNaviData(nativeNaviDatas);
            this.mNativeDataAdapter.notifyDataSetChanged();
        }
        this.mLocaleTotalItem = nativeNaviDatas.size();
        setDataCount(this.mNativeDataCountView, this.mLocaleTotalItem);
    }

    /* access modifiers changed from: private */
    public void addNetNaviData(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(new PayforNaviData(jSONArray.optJSONObject(i)));
        }
        if (arrayList.size() != 0) {
            if (this.mNetDataAdapter == null) {
                this.mNetDataAdapter = new PayforListAdapter(arrayList);
                this.mNetNaviDataListView.setAdapter(this.mNetDataAdapter);
            } else {
                this.mNetDataAdapter.addNaviData(arrayList);
                this.mNetDataAdapter.notifyDataSetChanged();
            }
            this.mNetNaviDataListView.setVisibility(0);
            return;
        }
        this.mNetNaviDataListView.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void setActivityEnd(boolean z) {
        if (this.mIsActivityEnd != z) {
            this.mIsActivityEnd = z;
            this.mNativeDataAdapter.notifyDataSetChanged();
        }
        if (this.mNeedChangePage) {
            this.mNeedChangePage = false;
            if (this.mIsActivityEnd) {
                this.mNetRadio.setChecked(true);
                return;
            }
            this.mNativeRadio.setChecked(true);
        }
    }

    /* access modifiers changed from: private */
    public void setDataCount(TextView textView, int i) {
        textView.setText(Html.fromHtml(getContext().getString(R.string.activities_destination_record_count, new Object[]{Integer.valueOf(i)})));
    }

    /* access modifiers changed from: 0000 */
    public void setSubmitVisible(int i) {
        if (this.doconfirmmappoint != null) {
            if (i > 0) {
                this.doconfirmmappoint.setVisibility(0);
            } else {
                this.doconfirmmappoint.setVisibility(8);
            }
        }
    }

    private void showProgressDialog(AosPostRequest aosPostRequest, int i) {
        showProgressDialog(aosPostRequest, getContext().getString(i));
    }

    private void showProgressDialog(AosPostRequest aosPostRequest) {
        showProgressDialog(aosPostRequest, getString(R.string.oper_loading));
    }

    private void showProgressDialog(final AosPostRequest aosPostRequest, String str) {
        dismissProgressDialog();
        this.progressDialog = new ProgressDlg(getActivity());
        this.progressDialog.setMessage(str);
        this.progressDialog.setOnCancelListener(new OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                if (aosPostRequest != null) {
                    aosPostRequest.cancel();
                }
            }
        });
        this.progressDialog.show();
    }

    /* access modifiers changed from: private */
    public void dismissProgressDialog() {
        if (this.progressDialog != null) {
            this.progressDialog.dismiss();
            this.progressDialog = null;
        }
    }

    /* access modifiers changed from: private */
    public void showNetErrorMessage() {
        showMessage(R.string.ic_net_error_tipinfo);
    }

    /* access modifiers changed from: private */
    public void showMessage(int i) {
        ToastHelper.showLongToast(getContext().getString(i));
    }

    /* access modifiers changed from: private */
    public void getNetNaviInfo(final int i) {
        ListsRequest listsRequest = new ListsRequest();
        listsRequest.d = String.valueOf(i);
        listsRequest.c = "10";
        UsePayRequestHolder.getInstance().sendLists(listsRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                ApplyPayForListFragment.this.dismissProgressDialog();
                ApplyPayForListFragment.this.mNetNaviDataListView.onRefreshComplete();
                JSONObject jSONObject = null;
                if (aosByteResponse != null) {
                    try {
                        byte[] bArr = (byte[]) aosByteResponse.getResult();
                        if (bArr != null) {
                            String str = new String(bArr, "UTF-8");
                            if (!TextUtils.isEmpty(str)) {
                                jSONObject = new JSONObject(str);
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
                if (jSONObject != null) {
                    DebugLog.logBigStr(ApplyPayForListFragment.TAG, jSONObject.toString());
                    if (jSONObject.optInt("code") != 1) {
                        ApplyPayForListFragment.this.showNetErrorMessage();
                        ApplyPayForListFragment.this.mNeedChangePage = false;
                        return;
                    }
                    ApplyPayForListFragment.this.setActivityEnd(jSONObject.optBoolean(NetConstant.KEY_ACTIVITY_END, true));
                    ApplyPayForListFragment.this.mTotalItem = jSONObject.optInt("total", 0);
                    ApplyPayForListFragment.this.setDataCount(ApplyPayForListFragment.this.mNetDataCountView, ApplyPayForListFragment.this.mTotalItem);
                    ApplyPayForListFragment.this.mCurPage = i;
                    JSONArray optJSONArray = jSONObject.optJSONArray(NetConstant.KEY_USER_APPLIED_NAVI_LIST);
                    if (optJSONArray != null && optJSONArray.length() > 0) {
                        if (ApplyPayForListFragment.this.mCurPage == 1 && ApplyPayForListFragment.this.mNetDataAdapter != null) {
                            ApplyPayForListFragment.this.mNetDataAdapter.clearData();
                            ApplyPayForListFragment.this.mNetDataAdapter.notifyDataSetChanged();
                        }
                        ApplyPayForListFragment.this.addNetNaviData(optJSONArray);
                    }
                    ApplyPayForListFragment.this.setNetListRefreshMode();
                }
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                ApplyPayForListFragment.this.mNeedChangePage = false;
                ApplyPayForListFragment.this.dismissProgressDialog();
                ApplyPayForListFragment.this.mNetNaviDataListView.onRefreshComplete();
                ApplyPayForListFragment.this.showNetErrorMessage();
                aosResponseException.printStackTrace();
            }
        });
        showProgressDialog(listsRequest);
    }

    private void applyPayFor(PayforNaviData payforNaviData) {
        if (TextUtils.isEmpty(payforNaviData.recordId)) {
            checkCanApplyCheck(payforNaviData);
        } else if (isLogin()) {
            associatPayforInfo(payforNaviData);
        } else {
            loginForAssociat(payforNaviData);
        }
    }

    private void checkCanApplyCheck(final PayforNaviData payforNaviData) {
        ApplyCheckRequest applyCheckRequest = new ApplyCheckRequest();
        applyCheckRequest.b = payforNaviData.poiid;
        applyCheckRequest.c = String.valueOf(payforNaviData.distance);
        UsePayRequestHolder.getInstance().sendApplyCheck(applyCheckRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                ApplyPayForListFragment.this.dismissProgressDialog();
                JSONObject jSONObject = null;
                if (aosByteResponse != null) {
                    try {
                        byte[] bArr = (byte[]) aosByteResponse.getResult();
                        if (bArr != null) {
                            String str = new String(bArr, "UTF-8");
                            if (!TextUtils.isEmpty(str)) {
                                jSONObject = new JSONObject(str);
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
                if (jSONObject != null) {
                    int optInt = jSONObject.optInt("code");
                    if (optInt == 101) {
                        ApplyPayForListFragment.this.showMessage(R.string.activities_cannot_apply_payfor);
                    } else if (optInt != 1) {
                        ApplyPayForListFragment.this.showNetErrorMessage();
                    } else {
                        payforNaviData.moneyMaypayed = jSONObject.optDouble(NetConstant.KEY_MONEY_ACCOUNT);
                        PageBundle pageBundle = new PageBundle();
                        pageBundle.putObject("ApplyPayForTypeChooseFragment.PayforNaviData", payforNaviData);
                        ApplyPayForListFragment.this.startPageForResult(ApplyPayForTypeChooseFragment.class, pageBundle, 1);
                    }
                }
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                ApplyPayForListFragment.this.dismissProgressDialog();
                ApplyPayForListFragment.this.showNetErrorMessage();
            }
        });
        showProgressDialog(applyCheckRequest);
    }

    public void onPageResult(int i, ResultType resultType, PageBundle pageBundle) {
        if (i == 1 && ResultType.OK.equals(resultType) && pageBundle != null && pageBundle.containsKey("ApplyPayForTypeChooseFragment.resultData")) {
            this.mNeedRefresh = pageBundle.getBoolean("ApplyPayForTypeChooseFragment.resultData");
        }
    }

    /* access modifiers changed from: private */
    public void associatPayforInfo(final PayforNaviData payforNaviData) {
        RelateRequest relateRequest = new RelateRequest();
        relateRequest.b = payforNaviData.recordId;
        UsePayRequestHolder.getInstance().sendRelate(relateRequest, new AosResponseCallbackOnUi<AosByteResponse>() {
            public /* synthetic */ void onSuccess(AosResponse aosResponse) {
                AosByteResponse aosByteResponse = (AosByteResponse) aosResponse;
                ApplyPayForListFragment.this.dismissProgressDialog();
                JSONObject jSONObject = null;
                if (aosByteResponse != null) {
                    try {
                        byte[] bArr = (byte[]) aosByteResponse.getResult();
                        if (bArr != null) {
                            String str = new String(bArr, "UTF-8");
                            if (!TextUtils.isEmpty(str)) {
                                jSONObject = new JSONObject(str);
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
                if (jSONObject != null) {
                    int optInt = jSONObject.optInt("code");
                    if (optInt == 1) {
                        ApplyPayForListFragment.this.showMessage(R.string.activities_applied_payfor);
                        payforNaviData.delete();
                    } else if (optInt == 101) {
                        ApplyPayForListFragment.this.showMessage(R.string.activities_cannot_apply_payfor);
                    } else if (optInt == 14) {
                        ApplyPayForListFragment.this.loginForAssociat(payforNaviData);
                        return;
                    } else {
                        ApplyPayForListFragment.this.showNetErrorMessage();
                    }
                    ApplyPayForListFragment.this.mNeedRefresh = true;
                    ApplyPayForListFragment.this.onStart();
                }
            }

            public void onFailure(AosRequest aosRequest, AosResponseException aosResponseException) {
                ApplyPayForListFragment.this.dismissProgressDialog();
                ApplyPayForListFragment.this.showNetErrorMessage();
                aosResponseException.printStackTrace();
            }
        });
        showProgressDialog((AosPostRequest) relateRequest, R.string.activities_applying_payfor);
    }

    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.not_apply) {
            slideToLeft();
            setSubmitVisible(this.mLocaleTotalItem);
            return;
        }
        if (i == R.id.have_apply) {
            slideToRight();
            setSubmitVisible(this.mTotalItem);
        }
    }

    private void slideToLeft() {
        this.mAnimatorList.showPrevious();
    }

    private void slideToRight() {
        this.mAnimatorList.showNext();
    }

    private boolean isLogin() {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    /* access modifiers changed from: private */
    public void loginForAssociat(final PayforNaviData payforNaviData) {
        IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
        if (iAccountService != null) {
            iAccountService.a(getPageContext(), (anq) new anq() {
                public final void loginOrBindCancel() {
                }

                public final void onComplete(boolean z) {
                    if (z) {
                        ApplyPayForListFragment.this.associatPayforInfo(payforNaviData);
                    }
                }
            });
        }
    }
}
