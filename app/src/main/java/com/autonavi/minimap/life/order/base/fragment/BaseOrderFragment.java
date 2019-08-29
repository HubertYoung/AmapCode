package com.autonavi.minimap.life.order.base.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.auth.param.RequestVerifycodeRequest;
import com.autonavi.minimap.life.order.base.adapter.OrderListAdapterCommonOld;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.Mode;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import java.util.ArrayList;

public abstract class BaseOrderFragment extends Fragment implements OnClickListener, LocationNone, d<ListView>, dpq {
    protected EditText edtPhone;
    protected EditText edtVerify;
    protected View emptyView;
    protected TextView login;
    protected View loginLayout;
    protected Button mBtnCleanPhone;
    protected Button mBtnCleanVerify;
    protected Button mBtnSubmit;
    protected Button mBtnVerifi;
    private View mDeleteLayout;
    protected Fragment mFragment;
    protected Handler mHandler;
    protected OrderListAdapterCommonOld mListAdapter;
    protected ListView mListView;
    protected AosRequest mNetCancel;
    protected bid mPageContext;
    protected PullToRefreshListView mPullRefreshListView;
    /* access modifiers changed from: private */
    public CheckBox mSelectAll;
    public ArrayList<dpl> mTotalOrdersList;
    private final VerifyCallback mVerifyCallback = new VerifyCallback(this, 0);
    public int page;
    public int pagesize = 20;
    protected View phoneVerrify;
    protected ProgressDlg progressDlg;
    protected Boolean showPhoneVerify = Boolean.FALSE;
    protected TextView text_count_down;
    protected a timer;
    public int total;
    protected Boolean useNewEmptyView = Boolean.FALSE;

    final class VerifyCallback implements Callback<dpn> {
        public final void error(Throwable th, boolean z) {
        }

        private VerifyCallback() {
        }

        /* synthetic */ VerifyCallback(BaseOrderFragment baseOrderFragment, byte b) {
            this();
        }

        public final void callback(dpn dpn) {
            BaseOrderFragment.this.dissProgressDialog();
            if (dpn.c == 1) {
                ToastHelper.showToast(BaseOrderFragment.this.getString(R.string.life_order_phone_code_success));
                return;
            }
            BaseOrderFragment.this.timer.cancel();
            BaseOrderFragment.this.timer.onFinish();
            ToastHelper.showToast(dpn.d);
        }
    }

    public class a extends CountDownTimer {
        public a() {
            super(60000, 1000);
        }

        public final void onFinish() {
            BaseOrderFragment.this.mBtnVerifi.setEnabled(true);
            BaseOrderFragment.this.text_count_down.setVisibility(8);
        }

        public final void onTick(long j) {
            BaseOrderFragment.this.text_count_down.setVisibility(0);
            BaseOrderFragment.this.mBtnVerifi.setEnabled(false);
            TextView textView = BaseOrderFragment.this.text_count_down;
            StringBuilder sb = new StringBuilder();
            sb.append(j / 1000);
            sb.append("秒后可重试");
            textView.setText(sb.toString());
        }
    }

    class b implements TextWatcher {
        EditText a;

        public final void afterTextChanged(Editable editable) {
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        b(EditText editText) {
            this.a = editText;
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            String obj = BaseOrderFragment.this.edtVerify.getText().toString();
            if (this.a == BaseOrderFragment.this.edtPhone) {
                if (!TextUtils.isEmpty(BaseOrderFragment.this.edtPhone.getText().toString())) {
                    BaseOrderFragment.this.mBtnCleanPhone.setVisibility(0);
                }
                if ((TextUtils.isEmpty(obj) || obj.length() >= 4) && bnz.b(BaseOrderFragment.this.edtPhone.getText().toString())) {
                    BaseOrderFragment.this.mBtnSubmit.setEnabled(true);
                } else {
                    BaseOrderFragment.this.mBtnSubmit.setEnabled(false);
                }
                if (bnz.b(BaseOrderFragment.this.edtPhone.getText().toString())) {
                    BaseOrderFragment.this.mBtnVerifi.setEnabled(true);
                } else {
                    BaseOrderFragment.this.mBtnVerifi.setEnabled(false);
                }
            } else {
                if (this.a == BaseOrderFragment.this.edtVerify) {
                    if ((TextUtils.isEmpty(obj) || obj.length() >= 4) && bnz.b(BaseOrderFragment.this.edtPhone.getText().toString())) {
                        BaseOrderFragment.this.mBtnSubmit.setEnabled(true);
                    } else {
                        BaseOrderFragment.this.mBtnSubmit.setEnabled(false);
                    }
                    if (!TextUtils.isEmpty(BaseOrderFragment.this.edtVerify.getText().toString())) {
                        BaseOrderFragment.this.mBtnCleanVerify.setVisibility(0);
                    }
                }
            }
        }
    }

    class c implements Runnable {
        c() {
        }

        public final void run() {
            BaseOrderFragment.this.mPullRefreshListView.onRefreshComplete();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void afterLogin();

    /* access modifiers changed from: protected */
    public abstract void initDate();

    /* access modifiers changed from: protected */
    public abstract void onDelete(String str);

    /* access modifiers changed from: protected */
    public abstract void onListItemClick(AdapterView<?> adapterView, View view, int i, long j);

    /* access modifiers changed from: protected */
    public abstract void onPullDownToRefresh();

    /* access modifiers changed from: protected */
    public abstract void onPullUpToRefresh();

    /* access modifiers changed from: protected */
    public abstract void onSubmitOnClick();

    public abstract void verifyLog();

    public BaseOrderFragment(bid bid) {
        this.mPageContext = bid;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mFragment = this;
        this.mHandler = new Handler();
        return layoutInflater.inflate(R.layout.order_base_layout, null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView();
        initEmptyView();
        initLogin();
        initDate();
        initDeleteLayout(view);
    }

    public bid getPageContext() {
        return this.mPageContext;
    }

    public void initLogin() {
        if (isLogin() || this.showPhoneVerify.booleanValue()) {
            this.loginLayout.setVisibility(8);
        } else {
            this.loginLayout.setVisibility(0);
        }
    }

    /* access modifiers changed from: protected */
    public void initView() {
        if (getView() != null) {
            this.loginLayout = getView().findViewById(R.id.login_layout);
            this.login = (TextView) getView().findViewById(R.id.login_or_bind);
            this.login.setOnClickListener(this);
            this.mPullRefreshListView = (PullToRefreshListView) getView().findViewById(R.id.order_list);
            this.mPullRefreshListView.setMode(Mode.BOTH);
            this.mListView = (ListView) this.mPullRefreshListView.getRefreshableView();
            this.mListView.setSelector(R.drawable.transparent);
            this.mListView.setOnItemClickListener(getItemClickListener());
            this.mPullRefreshListView.setOnRefreshListener((d<T>) this);
            initPhoneVerify();
            this.mListAdapter = new OrderListAdapterCommonOld(getActivity(), null);
            this.mListAdapter.setOnCheckChangedListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (BaseOrderFragment.this.mSelectAll != null) {
                        BaseOrderFragment.this.mSelectAll.setChecked(BaseOrderFragment.this.mListAdapter.isSelectAll());
                    }
                }
            });
            this.mPullRefreshListView.setAdapter(this.mListAdapter);
        }
    }

    private OnItemClickListener getItemClickListener() {
        return new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                BaseOrderFragment.this.onListItemClick(adapterView, view, i - 1, j);
            }
        };
    }

    private void initEmptyView() {
        if (getView() != null) {
            View findViewById = getView().findViewById(R.id.order_empty_layout_new);
            View findViewById2 = getView().findViewById(R.id.empty);
            this.emptyView = this.useNewEmptyView.booleanValue() ? findViewById : findViewById2;
            if (this.useNewEmptyView.booleanValue()) {
                if (findViewById2 != null) {
                    findViewById2.setVisibility(8);
                }
                if (findViewById != null) {
                    findViewById.setVisibility(0);
                    View findViewById3 = findViewById.findViewById(R.id.go_ordering);
                    findViewById3.setVisibility(0);
                    findViewById3.setOnClickListener(this);
                }
            } else {
                if (findViewById != null) {
                    findViewById.setVisibility(8);
                }
                if (findViewById2 != null) {
                    findViewById2.setVisibility(0);
                }
            }
            this.mListView.setEmptyView(this.emptyView);
        }
    }

    private void initDeleteLayout(View view) {
        this.mDeleteLayout = view.findViewById(R.id.order_delete_layout);
        this.mSelectAll = (CheckBox) view.findViewById(R.id.select_all_checkbox);
        if (this.mSelectAll != null) {
            this.mSelectAll.setOnClickListener(this);
        }
        View findViewById = view.findViewById(R.id.order_delete);
        if (findViewById != null) {
            findViewById.setOnClickListener(this);
        }
        View findViewById2 = view.findViewById(R.id.order_select_all_layout);
        if (findViewById2 != null) {
            findViewById2.setOnClickListener(this);
        }
    }

    private void initPhoneVerify() {
        if (this.showPhoneVerify.booleanValue() && getView() != null) {
            this.phoneVerrify = getView().findViewById(R.id.phoneVerrify);
            this.phoneVerrify.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            this.mBtnVerifi = (Button) getView().findViewById(R.id.btn_verifi);
            this.mBtnVerifi.setOnClickListener(this);
            this.mBtnVerifi.setEnabled(false);
            this.mBtnSubmit = (Button) getView().findViewById(R.id.btn_submit);
            this.mBtnSubmit.setEnabled(false);
            this.mBtnSubmit.setOnClickListener(this);
            this.mBtnCleanPhone = (Button) getView().findViewById(R.id.btn_clean);
            this.mBtnCleanPhone.setOnClickListener(this);
            this.mBtnCleanVerify = (Button) getView().findViewById(R.id.btn_clean_verify);
            this.mBtnCleanVerify.setOnClickListener(this);
            this.edtPhone = (EditText) getView().findViewById(R.id.edtPhone);
            this.edtPhone.setFocusable(true);
            this.edtPhone.requestFocus();
            this.edtPhone.addTextChangedListener(new b(this.edtPhone));
            this.edtVerify = (EditText) getView().findViewById(R.id.edtVerify);
            AnonymousClass4 r0 = new OnEditorActionListener() {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (textView.getId() == R.id.edtPhone && i == 5) {
                        BaseOrderFragment.this.edtVerify.requestFocus();
                    } else if (textView.getId() == R.id.edtVerify && i == 6 && !TextUtils.isEmpty(BaseOrderFragment.this.edtPhone.getText().toString().trim()) && !TextUtils.isEmpty(BaseOrderFragment.this.edtVerify.getText().toString().trim())) {
                        BaseOrderFragment.this.onSubmitOnClick();
                    }
                    return true;
                }
            };
            this.edtPhone.setOnEditorActionListener(r0);
            this.edtVerify.addTextChangedListener(new b(this.edtVerify));
            this.edtVerify.setOnEditorActionListener(r0);
            this.text_count_down = (TextView) getView().findViewById(R.id.text_count_down);
            this.phoneVerrify.setVisibility(0);
        }
    }

    public void invalidateUI(boolean z) {
        if (z) {
            this.mListAdapter.setDataList(this.mTotalOrdersList);
            this.mListAdapter.notifyDataSetChanged();
            cancelEditMode();
        }
        if (this.mPullRefreshListView != null && this.mPullRefreshListView.isRefreshing()) {
            this.mHandler.post(new c());
        }
    }

    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        onPullDownToRefresh();
    }

    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        if (this.page == 0 || this.total == 0 || this.total <= this.page * this.pagesize) {
            ToastHelper.showLongToast(getStringById(R.string.no_more_voucher));
            invalidateUI(false);
            return;
        }
        onPullUpToRefresh();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (view.getId() == R.id.login_or_bind) {
            IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
            if (iAccountService != null) {
                iAccountService.a(getPageContext(), (anq) new anq() {
                    public final void loginOrBindCancel() {
                    }

                    public final void onComplete(boolean z) {
                        if (z) {
                            BaseOrderFragment.this.mHandler.postDelayed(new Runnable() {
                                public final void run() {
                                    BaseOrderFragment.this.afterLogin();
                                }
                            }, 500);
                        }
                    }
                });
            }
        } else if (view.getId() == R.id.btn_submit) {
            onSubmitOnClick();
        } else if (view.getId() == R.id.btn_verifi) {
            verifyLog();
            String trim = this.edtPhone.getText().toString().trim();
            if (!bnz.b(trim)) {
                ToastHelper.showToast(getString(R.string.life_order_phone_invalid));
                return;
            }
            this.timer = new a();
            this.timer.start();
            this.edtVerify.requestFocus();
            this.edtVerify.setSelected(true);
            this.progressDlg = new ProgressDlg(this.mFragment.getActivity(), "正在申请验证码,请稍候...");
            this.progressDlg.show();
            dpr.a(new RequestVerifycodeRequest("11", "2", trim, "1"), this.mVerifyCallback);
        } else if (view.getId() == R.id.btn_clean) {
            this.edtPhone.setText("");
            this.mBtnCleanPhone.setVisibility(8);
        } else if (view.getId() == R.id.btn_clean_verify) {
            this.edtVerify.setText("");
            this.mBtnCleanVerify.setVisibility(8);
        } else if (id == R.id.order_delete) {
            String oids = this.mListAdapter.getOids();
            if (!TextUtils.isEmpty(oids)) {
                startDeleteAlertDialog(oids);
            }
        } else {
            if (id == R.id.order_select_all_layout) {
                if (this.mSelectAll != null) {
                    boolean isChecked = this.mSelectAll.isChecked();
                    this.mSelectAll.setChecked(!isChecked);
                    listviewCheckedChanged(!isChecked);
                }
            } else if (id == R.id.select_all_checkbox) {
                if (this.mSelectAll != null) {
                    listviewCheckedChanged(this.mSelectAll.isChecked());
                }
            } else if (id == R.id.go_ordering) {
                dnl.a(getContext(), dnk.a);
            }
        }
    }

    public void onDestroy() {
        if (this.mNetCancel != null) {
            in.a().a(this.mNetCancel);
        }
        super.onDestroy();
    }

    private void listviewCheckedChanged(boolean z) {
        this.mListAdapter.setSelectAll(z);
        this.mListAdapter.notifyDataSetInvalidated();
    }

    /* access modifiers changed from: private */
    public void dissProgressDialog() {
        if (this.progressDlg != null) {
            this.progressDlg.dismiss();
        }
    }

    private void cancelEditMode() {
        if (this.mDeleteLayout != null && this.mDeleteLayout.getVisibility() == 0) {
            bif.b(this.mDeleteLayout, 100, null);
            this.mDeleteLayout.setVisibility(8);
        }
        this.mListAdapter.setEdit(false);
        this.mListAdapter.setSelectAll(false);
        if (this.mSelectAll != null) {
            this.mSelectAll.setChecked(false);
        }
        this.mListAdapter.notifyDataSetInvalidated();
    }

    public void startDeleteAlertDialog(final String str) {
        Builder builder = new Builder(getActivity());
        builder.setMessage(R.string.life_order_del_alert);
        builder.setNegativeButton(R.string.cancel, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
            }
        });
        builder.setNeutralButton(R.string.Ok, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
            public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                BaseOrderFragment.this.onDelete(str);
            }
        });
        this.mPageContext.startAlertDialogPage(builder);
    }

    private boolean isLogin() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    public String getStringById(int i) {
        return AMapAppGlobal.getApplication().getString(i);
    }
}
