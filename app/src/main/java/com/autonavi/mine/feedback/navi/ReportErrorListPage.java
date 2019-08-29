package com.autonavi.mine.feedback.navi;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.mine.feedback.navi.adapter.ReportListPageAdapter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.modules.ModuleNavi;
import com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager;
import com.autonavi.minimap.basemap.errorback.inter.IReportErrorManager.UserContact;
import com.autonavi.minimap.basemap.errorback.model.ReportErrorBean;
import com.autonavi.minimap.operation.inter.IReportErrorFragment;
import com.autonavi.widget.ui.TitleBar;
import java.util.ArrayList;

@PageAction("amap.basemap.action.feedback_report_error_list_page")
public class ReportErrorListPage extends AbstractBasePage<cgg> implements IReportErrorFragment {
    public IReportErrorManager a;
    public ReportListPageAdapter b;
    public EditText c;
    public String d = "";
    public boolean e = false;
    public ReportErrorBean f = null;
    private View g;
    private ListView h;
    private ArrayList<ReportErrorBean> i;
    private View j;
    private Context k;
    /* access modifiers changed from: private */
    public UserContact l;
    private LinearLayout m;
    private String n = "";

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.report_error_list_layout);
        this.g = getContentView();
        ((TitleBar) this.g.findViewById(R.id.title)).setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                ReportErrorListPage.this.setResult(ResultType.CANCEL, (PageBundle) null);
                ReportErrorListPage.this.finish();
            }
        });
        this.h = (ListView) this.g.findViewById(R.id.listview);
        this.m = (LinearLayout) this.g.findViewById(R.id.ll_top);
        this.j = this.g.findViewById(R.id.empty);
        this.h.setEmptyView(this.j);
        this.a = (IReportErrorManager) ank.a(IReportErrorManager.class);
        this.c = (EditText) this.g.findViewById(R.id.edt_concact_way);
        this.k = AMapPageUtil.getAppContext();
        this.l = (UserContact) bic.a(UserContact.class);
        this.d = this.l.getContact();
        this.c.addTextChangedListener(new TextWatcher() {
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void afterTextChanged(Editable editable) {
                if (ReportErrorListPage.this.d.equals(ReportErrorListPage.this.c.getText().toString())) {
                    ReportErrorListPage.this.l.setContact(ReportErrorListPage.this.l.getContact());
                } else {
                    ReportErrorListPage.this.l.setContact(ReportErrorListPage.this.c.getText().toString());
                }
            }
        });
        if (!(this.l == null || this.l.getContact() == null)) {
            this.c.setText(this.l.getContact());
        }
        PageBundle arguments = getArguments();
        this.m.removeAllViews();
        if (arguments != null && arguments.containsKey("ReportErrorListFragment.naviId")) {
            String string = arguments.getString("ReportErrorListFragment.naviId");
            if (this.a != null) {
                this.i = (ArrayList) this.a.getList(string);
            }
            this.n = arguments.getString("navi_type", "");
            this.b = new ReportListPageAdapter(getContext(), this.i, this.n, this);
            if (this.i != null && this.i.size() > 0) {
                ReportErrorBean reportErrorBean = this.i.get(0);
                if (reportErrorBean != null) {
                    if (!TextUtils.isEmpty(reportErrorBean.title) && reportErrorBean.title.equals(a(R.string.report_error_location))) {
                        reportErrorBean.title = a(R.string.my_location);
                    } else if (TextUtils.isEmpty(reportErrorBean.title)) {
                        reportErrorBean.title = a(R.string.my_location);
                    }
                    if (!(this.h == null || reportErrorBean.endPoi == null)) {
                        LinearLayout linearLayout = this.m;
                        String str = reportErrorBean.title;
                        String name = reportErrorBean.endPoi.getName();
                        TextView textView = new TextView(getContext());
                        int a2 = a(15.0f);
                        textView.setPadding(a2, a(12.0f), a2, a(12.0f));
                        textView.setBackgroundColor(0);
                        textView.setTextSize(13.0f);
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                        spannableStringBuilder.append(getString(R.string.oper_from));
                        int length = spannableStringBuilder.length();
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(-13421773), 0, length, 33);
                        spannableStringBuilder.append(str);
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(-16739841), length, str.length() + length, 33);
                        int length2 = spannableStringBuilder.length();
                        spannableStringBuilder.append(getString(R.string.oper_to));
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(-13421773), length2, length2 + 1, 33);
                        int length3 = spannableStringBuilder.length();
                        spannableStringBuilder.append(name);
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(-16739841), length3, name.length() + length3, 33);
                        textView.setText(spannableStringBuilder);
                        linearLayout.addView(textView);
                    }
                }
            }
            if (this.h != null) {
                this.h.setAdapter(this.b);
            }
        }
        if (this.i != null && this.i.size() == 1) {
            this.e = true;
            a(this.i.get(0), this.n);
        }
    }

    public final void a(ReportErrorBean reportErrorBean, String str) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("ReportErrorDescFragment.ReportErrorBean", reportErrorBean);
        this.f = reportErrorBean;
        if (DriveUtil.NAVI_TYPE_TRUCK.equals(str)) {
            pageBundle.putInt("sourcepage", 43);
        } else {
            pageBundle.putInt("sourcepage", 10);
        }
        pageBundle.putString("navi_type", str);
        col.a();
        startPageForResult(Ajx3Page.class, col.c(pageBundle, ModuleNavi.MODULE_NAME), 16400);
    }

    private int a(float f2) {
        return (int) ((f2 * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    private static String a(int i2) {
        return AMapPageUtil.getAppContext().getResources().getString(i2);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cgg(this);
    }
}
