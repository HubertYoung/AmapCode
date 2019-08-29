package defpackage;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ViewAnimator;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.drivecommon.map.db.helper.RdCameraDBHelper;
import com.amap.bundle.drivecommon.map.db.model.RdCameraPaymentItem;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.carowner.roadcamera.model.RdCameraPaymentListModel;
import com.autonavi.carowner.roadcamera.model.RdCameraPaymentListModel.PaymentAdapter;
import com.autonavi.carowner.roadcamera.page.RdCameraPaymentListPage;
import com.autonavi.carowner.roadcamera.presenter.RdCameraPaymentListPresenter$2;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.feedback.FeedbackRequestHolder;
import com.autonavi.minimap.feedback.param.UserpayListRequest;
import com.autonavi.minimap.widget.AmapTextView;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase;
import com.autonavi.widget.pulltorefresh.PullToRefreshBase.d;
import com.autonavi.widget.pulltorefresh.PullToRefreshListView;
import com.autonavi.widget.pulltorefresh.internal.LoadingLayout;
import com.autonavi.widget.ui.TitleBar;
import java.util.List;
import org.json.JSONObject;

/* renamed from: bhv reason: default package */
/* compiled from: RdCameraPaymentListPresenter */
public final class bhv extends sw<RdCameraPaymentListPage, RdCameraPaymentListModel> {
    public boolean c = false;
    public int d;
    public int e = 0;

    public bhv(RdCameraPaymentListPage rdCameraPaymentListPage) {
        super(rdCameraPaymentListPage);
    }

    public final void onPageCreated() {
        int i;
        super.onPageCreated();
        RdCameraPaymentListPage rdCameraPaymentListPage = (RdCameraPaymentListPage) this.mPage;
        View contentView = rdCameraPaymentListPage.getContentView();
        rdCameraPaymentListPage.a = (TitleBar) contentView.findViewById(R.id.title);
        rdCameraPaymentListPage.a.setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RdCameraPaymentListPage.this.finish();
            }
        });
        rdCameraPaymentListPage.a.setOnActionClickListener(rdCameraPaymentListPage.k);
        ((RadioGroup) contentView.findViewById(R.id.apply_tab)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.not_apply) {
                    RdCameraPaymentListPage.this.d.showPrevious();
                    RdCameraPaymentListPage.this.a(1);
                    return;
                }
                if (i == R.id.has_applied) {
                    RdCameraPaymentListPage.this.d.showNext();
                    RdCameraPaymentListPage.this.a(2);
                    RdCameraPaymentListPage.this.f.setVisibility(RdCameraPaymentListPage.e() ? 0 : 8);
                    if (((bhv) RdCameraPaymentListPage.this.mPresenter).e == 0 && RdCameraPaymentListPage.e()) {
                        ((bhv) RdCameraPaymentListPage.this.mPresenter).a(1);
                    }
                }
            }
        });
        rdCameraPaymentListPage.c = (RadioButton) contentView.findViewById(R.id.not_apply);
        rdCameraPaymentListPage.b = (RadioButton) contentView.findViewById(R.id.has_applied);
        rdCameraPaymentListPage.d = (ViewAnimator) contentView.findViewById(R.id.animator_list);
        View findViewById = contentView.findViewById(R.id.not_apply_layout);
        View findViewById2 = findViewById.findViewById(R.id.count_layout);
        ((TextView) findViewById2.findViewById(R.id.information)).setText(R.string.rd_camera_payment_not_apply_count_info);
        rdCameraPaymentListPage.e = (TextView) findViewById2.findViewById(R.id.apply_count);
        rdCameraPaymentListPage.e.setText(Html.fromHtml(rdCameraPaymentListPage.getContext().getString(R.string.rd_camera_payment_not_apply_count, new Object[]{Integer.valueOf(0)})));
        ListView listView = (ListView) findViewById.findViewById(R.id.naviinfo_list);
        bhv bhv = (bhv) rdCameraPaymentListPage.mPresenter;
        PaymentAdapter b = ((RdCameraPaymentListModel) bhv.b).b();
        b.registerDataSetObserver(new DataSetObserver() {
            public final void onChanged() {
                super.onChanged();
                ((RdCameraPaymentListPage) bhv.this.mPage).a(2);
            }
        });
        listView.setAdapter(b);
        View findViewById3 = findViewById.findViewById(R.id.empty_layout);
        AmapTextView amapTextView = (AmapTextView) findViewById3.findViewById(R.id.look_over_activities_view);
        amapTextView.setText(Html.fromHtml(rdCameraPaymentListPage.getContext().getString(R.string.rd_camera_more_info)));
        amapTextView.setOnClickListener(rdCameraPaymentListPage.k);
        listView.setEmptyView(findViewById3);
        Button button = (Button) findViewById3.findViewById(R.id.button1);
        ((TextView) findViewById3.findViewById(R.id.info)).setText(R.string.rd_camera_payment_not_apply_list_empty_info);
        button.setText(R.string.rd_camera_payment_not_apply_list_empty_button_info);
        button.setBackgroundResource(R.drawable.rd_carmera_blue_corner_btn_bg);
        button.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("amap.extra.route.route");
                intent.setPackage(RdCameraPaymentListPage.this.getContext().getPackageName());
                PageBundle pageBundle = new PageBundle(intent);
                pageBundle.putObject("bundle_key_route_type", RouteType.CAR);
                bax bax = (bax) a.a.a(bax.class);
                if (bax != null) {
                    bax.a(pageBundle);
                }
            }
        });
        View findViewById4 = contentView.findViewById(R.id.apply_already_layout);
        rdCameraPaymentListPage.f = findViewById4.findViewById(R.id.count_layout);
        ((TextView) rdCameraPaymentListPage.f.findViewById(R.id.information)).setText(R.string.rd_camera_payment_apply_already_count_info);
        rdCameraPaymentListPage.g = (TextView) rdCameraPaymentListPage.f.findViewById(R.id.apply_count);
        rdCameraPaymentListPage.g.setText(Html.fromHtml(rdCameraPaymentListPage.getContext().getString(R.string.rd_camera_payment_apply_already_count, new Object[]{Integer.valueOf(0)})));
        rdCameraPaymentListPage.h = (PullToRefreshListView) findViewById4.findViewById(R.id.refresh_naviinfo_list);
        PullToRefreshListView pullToRefreshListView = rdCameraPaymentListPage.h;
        bhv bhv2 = (bhv) rdCameraPaymentListPage.mPresenter;
        PaymentAdapter c2 = ((RdCameraPaymentListModel) bhv2.b).c();
        c2.registerDataSetObserver(new DataSetObserver() {
            public final void onChanged() {
                super.onChanged();
                ((RdCameraPaymentListPage) bhv.this.mPage).a(2);
            }
        });
        pullToRefreshListView.setAdapter(c2);
        rdCameraPaymentListPage.h.setParentWindowClass(rdCameraPaymentListPage.getClass().getName());
        LoadingLayout changeFooter = rdCameraPaymentListPage.h.changeFooter();
        changeFooter.setVisibility(0);
        rdCameraPaymentListPage.h.mLvFooterLoadingFrame.removeView(rdCameraPaymentListPage.h.mFooterLoadingView);
        ((ListView) rdCameraPaymentListPage.h.getRefreshableView()).addFooterView(changeFooter, null, false);
        ((ListView) rdCameraPaymentListPage.h.getRefreshableView()).setSelector(rdCameraPaymentListPage.getContext().getResources().getDrawable(R.color.transparent));
        ((ListView) rdCameraPaymentListPage.h.getRefreshableView()).setDividerHeight(0);
        rdCameraPaymentListPage.h.setFootershowflag(false);
        rdCameraPaymentListPage.h.setOnRefreshListener((d<T>) new d<ListView>() {
            public final void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                ((bhv) RdCameraPaymentListPage.this.mPresenter).a(1);
            }

            public final void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (((bhv) RdCameraPaymentListPage.this.mPresenter).e()) {
                    ((bhv) RdCameraPaymentListPage.this.mPresenter).a(((bhv) RdCameraPaymentListPage.this.mPresenter).d + 1);
                } else {
                    RdCameraPaymentListPage.this.h.onRefreshComplete();
                }
            }
        });
        View findViewById5 = findViewById4.findViewById(R.id.empty_layout);
        AmapTextView amapTextView2 = (AmapTextView) findViewById5.findViewById(R.id.look_over_activities_view);
        amapTextView2.setText(Html.fromHtml(rdCameraPaymentListPage.getContext().getString(R.string.rd_camera_more_info)));
        amapTextView2.setOnClickListener(rdCameraPaymentListPage.k);
        rdCameraPaymentListPage.h.setEmptyView(findViewById5);
        rdCameraPaymentListPage.i = (TextView) findViewById5.findViewById(R.id.info);
        rdCameraPaymentListPage.i.setText(R.string.rd_camera_payment_apply_already_list_empty_info_sign_out);
        rdCameraPaymentListPage.j = (Button) findViewById5.findViewById(R.id.button1);
        rdCameraPaymentListPage.j.setText(R.string.rd_camera_payment_apply_already_list_empty_button_info);
        rdCameraPaymentListPage.j.setBackgroundResource(R.drawable.activities_orange_corner_btn_bg);
        rdCameraPaymentListPage.j.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                IAccountService iAccountService = (IAccountService) a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    iAccountService.a(RdCameraPaymentListPage.this.getPageContext(), (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                            if (z) {
                                ((bhv) RdCameraPaymentListPage.this.mPresenter).a(1);
                            }
                            RdCameraPaymentListPage.this.b();
                            RdCameraPaymentListPage.this.f.setVisibility(z ? 0 : 8);
                        }
                    });
                }
            }
        });
        rdCameraPaymentListPage.a();
        rdCameraPaymentListPage.b();
        RdCameraPaymentListModel rdCameraPaymentListModel = (RdCameraPaymentListModel) this.b;
        PageBundle arguments = ((RdCameraPaymentListPage) this.mPage).getArguments();
        if (arguments != null && arguments.containsKey("amap.extra.road.camera.pageparam")) {
            JSONObject jSONObject = (JSONObject) arguments.getObject("amap.extra.road.camera.pageparam");
            String obj = jSONObject.opt("pageID").toString();
            if ("1".equals(obj)) {
                i = 1;
            } else {
                boolean equals = "2".equals(obj);
                i = 2;
            }
            rdCameraPaymentListModel.c = new bhs(i);
            JSONObject optJSONObject = jSONObject.optJSONObject("carInfo");
            if (optJSONObject != null) {
                rdCameraPaymentListModel.c.b = optJSONObject.optString("carNum");
                rdCameraPaymentListModel.c.c = optJSONObject.optString("tel");
                rdCameraPaymentListModel.c.d = optJSONObject.optString("carCode");
                rdCameraPaymentListModel.c.e = optJSONObject.optString("carDriveCode");
            }
        }
        RdCameraPaymentListPage rdCameraPaymentListPage2 = (RdCameraPaymentListPage) this.mPage;
        bhs bhs = ((RdCameraPaymentListModel) this.b).c;
        if (bhs.a == 1) {
            rdCameraPaymentListPage2.c.setChecked(true);
            return;
        }
        if (bhs.a == 2) {
            rdCameraPaymentListPage2.b.setChecked(true);
        }
    }

    public final void onStart() {
        super.onStart();
        RdCameraPaymentListPage rdCameraPaymentListPage = (RdCameraPaymentListPage) this.mPage;
        RdCameraPaymentListModel rdCameraPaymentListModel = (RdCameraPaymentListModel) this.b;
        List<RdCameraPaymentItem> all = RdCameraDBHelper.getInstance(rdCameraPaymentListModel.a()).getAll();
        boolean z = false;
        if (all.size() > 0) {
            long longValue = all.get(0).navi_timestamp.longValue() - 2592000;
            if (longValue <= 0) {
                longValue = 1;
            }
            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).navi_timestamp.longValue() < longValue) {
                    all.remove(i);
                }
            }
            RdCameraDBHelper.getInstance(rdCameraPaymentListModel.a()).deleteDataBefore(Long.valueOf(longValue));
        }
        if (!((bhv) rdCameraPaymentListPage.mPresenter).c || all != null) {
            PaymentAdapter c2 = ((bhv) rdCameraPaymentListPage.mPresenter).c();
            c2.setData(all);
            rdCameraPaymentListPage.a(1, all.size());
            bhv bhv = (bhv) rdCameraPaymentListPage.mPresenter;
            if (c2.getCount() > 0) {
                z = true;
            }
            bhv.c = z;
            rdCameraPaymentListPage.a(1);
        }
        ((RdCameraPaymentListPage) this.mPage).a();
        ((RdCameraPaymentListPage) this.mPage).b();
    }

    public final ON_BACK_TYPE onBackPressed() {
        return ON_BACK_TYPE.TYPE_NORMAL;
    }

    public final void a(int i) {
        UserpayListRequest userpayListRequest = new UserpayListRequest();
        userpayListRequest.c = "10";
        userpayListRequest.d = String.valueOf(i);
        userpayListRequest.b = "4";
        FeedbackRequestHolder.getInstance().sendUserPayList(userpayListRequest, new RdCameraPaymentListPresenter$2(this, i));
        a((AosRequest) userpayListRequest, b().getString(R.string.rd_camera_payment_loading));
    }

    public final PaymentAdapter c() {
        return ((RdCameraPaymentListModel) this.b).b();
    }

    public final void a(AosRequest aosRequest, String str) {
        ((RdCameraPaymentListPage) this.mPage).a(aosRequest, str);
    }

    public final void d() {
        ((RdCameraPaymentListPage) this.mPage).c();
    }

    public static int a(@NonNull PaymentAdapter paymentAdapter) {
        return paymentAdapter.getCount() > 0 ? 0 : 8;
    }

    public final boolean e() {
        return this.e > this.d * 10;
    }

    public final /* synthetic */ su a() {
        return new RdCameraPaymentListModel(this);
    }
}
