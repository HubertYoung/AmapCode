package com.autonavi.minimap.life.order.base.page;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.Callback;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
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

public abstract class BaseByPhonePage extends AbstractBasePage<dpt> implements OnClickListener, LocationNone, d<ListView>, dpq {
    private final VerifyCallback A = new VerifyCallback(this, 0);
    protected PullToRefreshListView a;
    protected ListView b;
    public ArrayList<dpl> c;
    protected OrderListAdapterCommonOld d;
    protected View e;
    protected Handler f;
    public int g;
    public int h;
    public int i = 20;
    protected Boolean j = Boolean.FALSE;
    protected Boolean k = Boolean.FALSE;
    protected View l;
    protected Button m;
    protected Button n;
    protected Button o;
    protected Button p;
    protected a q;
    protected ProgressDlg r;
    protected EditText s;
    protected EditText t;
    protected TextView u;
    protected View v;
    protected TextView w;
    protected AosRequest x;
    private View y;
    /* access modifiers changed from: private */
    public CheckBox z;

    final class VerifyCallback implements Callback<dpn> {
        public final void error(Throwable th, boolean z) {
        }

        private VerifyCallback() {
        }

        /* synthetic */ VerifyCallback(BaseByPhonePage baseByPhonePage, byte b) {
            this();
        }

        public final void callback(dpn dpn) {
            BaseByPhonePage.b(BaseByPhonePage.this);
            if (dpn.c == 1) {
                ToastHelper.showToast(BaseByPhonePage.this.getString(R.string.life_order_phone_code_success));
                return;
            }
            BaseByPhonePage.this.q.cancel();
            BaseByPhonePage.this.q.onFinish();
            ToastHelper.showToast(dpn.d);
        }
    }

    public class a extends CountDownTimer {
        public a() {
            super(60000, 1000);
        }

        public final void onFinish() {
            BaseByPhonePage.this.n.setEnabled(true);
            BaseByPhonePage.this.u.setVisibility(8);
        }

        public final void onTick(long j) {
            BaseByPhonePage.this.u.setVisibility(0);
            BaseByPhonePage.this.n.setEnabled(false);
            TextView textView = BaseByPhonePage.this.u;
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
            String obj = BaseByPhonePage.this.t.getText().toString();
            if (this.a == BaseByPhonePage.this.s) {
                if (!TextUtils.isEmpty(BaseByPhonePage.this.s.getText().toString())) {
                    BaseByPhonePage.this.o.setVisibility(0);
                }
                if ((TextUtils.isEmpty(obj) || obj.length() >= 4) && bnz.b(BaseByPhonePage.this.s.getText().toString())) {
                    BaseByPhonePage.this.m.setEnabled(true);
                } else {
                    BaseByPhonePage.this.m.setEnabled(false);
                }
                if (bnz.b(BaseByPhonePage.this.s.getText().toString())) {
                    BaseByPhonePage.this.n.setEnabled(true);
                } else {
                    BaseByPhonePage.this.n.setEnabled(false);
                }
            } else {
                if (this.a == BaseByPhonePage.this.t) {
                    if ((TextUtils.isEmpty(obj) || obj.length() >= 4) && bnz.b(BaseByPhonePage.this.s.getText().toString())) {
                        BaseByPhonePage.this.m.setEnabled(true);
                    } else {
                        BaseByPhonePage.this.m.setEnabled(false);
                    }
                    if (!TextUtils.isEmpty(BaseByPhonePage.this.t.getText().toString())) {
                        BaseByPhonePage.this.p.setVisibility(0);
                    }
                }
            }
        }
    }

    class c implements Runnable {
        c() {
        }

        public final void run() {
            BaseByPhonePage.this.a.onRefreshComplete();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void a(int i2);

    /* access modifiers changed from: protected */
    public abstract void b();

    /* access modifiers changed from: protected */
    public abstract void c();

    /* access modifiers changed from: protected */
    public abstract void d();

    /* access modifiers changed from: protected */
    public abstract void e();

    /* access modifiers changed from: protected */
    public abstract void f();

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.order_phone_base_layout);
        this.f = new Handler();
        ((TextView) getContentView().findViewById(R.id.title_text_name)).setText(R.string.viewpoint_order_title);
        ((ImageButton) getContentView().findViewById(R.id.title_btn_left)).setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                BaseByPhonePage.this.finish();
            }
        });
        this.v = getContentView().findViewById(R.id.login_layout);
        this.w = (TextView) getContentView().findViewById(R.id.login_or_bind);
        this.w.setOnClickListener(this);
        this.a = (PullToRefreshListView) getContentView().findViewById(R.id.order_list);
        this.a.setMode(Mode.BOTH);
        this.b = (ListView) this.a.getRefreshableView();
        this.b.setSelector(R.drawable.transparent);
        this.b.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                BaseByPhonePage.this.a(i - 1);
            }
        });
        this.a.setOnRefreshListener((d<T>) this);
        if (this.j.booleanValue()) {
            this.l = getContentView().findViewById(R.id.phoneVerrify);
            this.l.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
            this.n = (Button) getContentView().findViewById(R.id.btn_verifi);
            this.n.setOnClickListener(this);
            this.n.setEnabled(false);
            this.m = (Button) getContentView().findViewById(R.id.btn_submit);
            this.m.setEnabled(false);
            this.m.setOnClickListener(this);
            this.o = (Button) getContentView().findViewById(R.id.btn_clean);
            this.o.setOnClickListener(this);
            this.p = (Button) getContentView().findViewById(R.id.btn_clean_verify);
            this.p.setOnClickListener(this);
            this.s = (EditText) getContentView().findViewById(R.id.edtPhone);
            this.s.setFocusable(true);
            this.s.requestFocus();
            this.s.addTextChangedListener(new b(this.s));
            this.t = (EditText) getContentView().findViewById(R.id.edtVerify);
            AnonymousClass5 r5 = new OnEditorActionListener() {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (textView.getId() == R.id.edtPhone && i == 5) {
                        BaseByPhonePage.this.t.requestFocus();
                    } else if (textView.getId() == R.id.edtVerify && i == 6 && !TextUtils.isEmpty(BaseByPhonePage.this.s.getText().toString().trim()) && !TextUtils.isEmpty(BaseByPhonePage.this.t.getText().toString().trim())) {
                        BaseByPhonePage.this.f();
                    }
                    return true;
                }
            };
            this.s.setOnEditorActionListener(r5);
            this.t.addTextChangedListener(new b(this.t));
            this.t.setOnEditorActionListener(r5);
            this.u = (TextView) getContentView().findViewById(R.id.text_count_down);
            this.l.setVisibility(0);
        }
        this.d = new OrderListAdapterCommonOld(getActivity(), null);
        this.d.setOnCheckChangedListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (BaseByPhonePage.this.z != null) {
                    BaseByPhonePage.this.z.setChecked(BaseByPhonePage.this.d.isSelectAll());
                }
            }
        });
        this.a.setAdapter(this.d);
        View findViewById = getContentView().findViewById(R.id.order_empty_layout_new);
        View findViewById2 = getContentView().findViewById(R.id.empty);
        this.e = this.k.booleanValue() ? findViewById : findViewById2;
        if (this.k.booleanValue()) {
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
        this.b.setEmptyView(this.e);
        a();
        d();
        View contentView = getContentView();
        this.y = contentView.findViewById(R.id.order_delete_layout);
        this.z = (CheckBox) contentView.findViewById(R.id.select_all_checkbox);
        if (this.z != null) {
            this.z.setOnClickListener(this);
        }
        View findViewById4 = contentView.findViewById(R.id.order_delete);
        if (findViewById4 != null) {
            findViewById4.setOnClickListener(this);
        }
        View findViewById5 = contentView.findViewById(R.id.order_select_all_layout);
        if (findViewById5 != null) {
            findViewById5.setOnClickListener(this);
        }
    }

    public final void a() {
        if (h() || this.j.booleanValue()) {
            this.v.setVisibility(8);
        } else {
            this.v.setVisibility(0);
        }
    }

    public final void a(boolean z2) {
        if (z2) {
            this.d.setDataList(this.c);
            this.d.notifyDataSetChanged();
            if (this.y != null && this.y.getVisibility() == 0) {
                bif.b(this.y, 100, null);
                this.y.setVisibility(8);
            }
            this.d.setEdit(false);
            this.d.setSelectAll(false);
            if (this.z != null) {
                this.z.setChecked(false);
            }
            this.d.notifyDataSetInvalidated();
        }
        if (this.a != null && this.a.isRefreshing()) {
            this.f.post(new c());
        }
    }

    public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        b();
    }

    public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
        if (this.g == 0 || this.h == 0 || this.h <= this.g * this.i) {
            ToastHelper.showLongToast(AMapAppGlobal.getApplication().getString(R.string.no_more_voucher));
            a(false);
            return;
        }
        c();
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
                            BaseByPhonePage.this.f.postDelayed(new Runnable() {
                                public final void run() {
                                    BaseByPhonePage.this.e();
                                }
                            }, 500);
                        }
                    }
                });
            }
        } else if (view.getId() == R.id.btn_submit) {
            f();
        } else if (view.getId() == R.id.btn_verifi) {
            String trim = this.s.getText().toString().trim();
            if (!bnz.b(trim)) {
                ToastHelper.showToast(getString(R.string.life_order_phone_invalid));
                return;
            }
            this.q = new a();
            this.q.start();
            this.t.requestFocus();
            this.t.setSelected(true);
            this.r = new ProgressDlg(getActivity(), "正在申请验证码,请稍候...");
            this.r.show();
            dpr.a(new RequestVerifycodeRequest("11", "2", trim, "1"), this.A);
        } else if (view.getId() == R.id.btn_clean) {
            this.s.setText("");
            this.o.setVisibility(8);
        } else if (view.getId() == R.id.btn_clean_verify) {
            this.t.setText("");
            this.p.setVisibility(8);
        } else if (id == R.id.order_delete) {
            final String oids = this.d.getOids();
            if (!TextUtils.isEmpty(oids)) {
                Builder builder = new Builder(getActivity());
                builder.setMessage(R.string.life_order_del_alert);
                builder.setNegativeButton(R.string.cancel, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                    public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    }
                });
                builder.setNeutralButton(R.string.Ok, (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                    public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                    }
                });
                getPageContext().startAlertDialogPage(builder);
            }
        } else {
            if (id == R.id.order_select_all_layout) {
                if (this.z != null) {
                    boolean isChecked = this.z.isChecked();
                    this.z.setChecked(!isChecked);
                    b(!isChecked);
                }
            } else if (id == R.id.select_all_checkbox) {
                if (this.z != null) {
                    b(this.z.isChecked());
                }
            } else if (id == R.id.go_ordering) {
                dnl.a(getContext(), dnk.a);
            }
        }
    }

    public final void g() {
        if (this.x != null) {
            in.a().a(this.x);
        }
    }

    private void b(boolean z2) {
        this.d.setSelectAll(z2);
        this.d.notifyDataSetInvalidated();
    }

    private static boolean h() {
        IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
        if (iAccountService == null) {
            return false;
        }
        return iAccountService.a();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dpt(this);
    }

    static /* synthetic */ void b(BaseByPhonePage baseByPhonePage) {
        if (baseByPhonePage.r != null) {
            baseByPhonePage.r.dismiss();
        }
    }
}
