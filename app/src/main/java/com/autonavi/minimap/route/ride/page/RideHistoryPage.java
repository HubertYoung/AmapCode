package com.autonavi.minimap.route.ride.page;

import android.content.Context;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.ajx.module.ModuleValues;
import com.autonavi.minimap.route.ride.adapter.RideHistoryAdapter;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;
import com.autonavi.minimap.route.run.view.PinnedSectionListView;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import java.text.DecimalFormat;
import java.util.List;

public class RideHistoryPage extends AbstractBasePage<een> implements OnClickListener {
    /* access modifiers changed from: private */
    public static a a;
    private TextView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private TextView f;
    private TextView g;
    private TextView h;
    private View i;
    private TextView j;
    private PinnedSectionListView k;
    /* access modifiers changed from: private */
    public AlertView l;

    static class a extends ecs<RideHistoryPage> {
        a(RideHistoryPage rideHistoryPage) {
            super(rideHistoryPage);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            RideHistoryPage rideHistoryPage = (RideHistoryPage) a();
            if (rideHistoryPage != null && message.what == 0 && (message.obj instanceof List)) {
                List list = (List) message.obj;
                if (list.size() == 0) {
                    RideHistoryPage.a(rideHistoryPage, true);
                    rideHistoryPage.a(0, 0, 0.0d, 0.0d);
                    return;
                }
                RideHistoryAdapter rideHistoryAdapter = new RideHistoryAdapter(list);
                RideHistoryPage.a(rideHistoryPage, false);
                rideHistoryAdapter.setHistoryItemListener(new com.autonavi.minimap.route.ride.adapter.RideHistoryAdapter.a() {
                    public final void a(RideTraceHistory rideTraceHistory) {
                        if (rideTraceHistory != null) {
                            LogManager.actionLogV2("P00276", "B001");
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putObject("data", rideTraceHistory);
                            pageBundle.putString("bundle_key_page_from", "2");
                            pageBundle.putString("bundle_key_back_page", "page_go_back_last_page");
                            if (rideTraceHistory.k == RideType.SHARE_RIDE_TYPE) {
                                eab a2 = eaa.a().a(rideTraceHistory.i);
                                if (a2 != null) {
                                    pageBundle.putString("bundle_orderid_key", a2.a);
                                    pageBundle.putString("bundle_cpsource_key", a2.b);
                                }
                                bdf bdf = (bdf) defpackage.esb.a.a.a(bdf.class);
                                if (bdf != null) {
                                    RideHistoryPage.this.startPage(bdf.b().a(2), pageBundle);
                                }
                            } else if (rideTraceHistory.k == RideType.RIDE_TYPE) {
                                RideHistoryPage.this.startPage(RideFinishMapPage.class, pageBundle);
                            } else {
                                eer.a("performance-", "clickRideHistoryRecord");
                                PageBundle pageBundle2 = new PageBundle();
                                pageBundle2.putString("url", ModuleValues.URL_RIDE_END);
                                String b = ees.b(rideTraceHistory);
                                if (!TextUtils.isEmpty(b)) {
                                    pageBundle2.putString("jsData", b);
                                }
                                pageBundle2.putBoolean("bundle_key_page_from_history", true);
                                aww aww = (aww) defpackage.esb.a.a.a(aww.class);
                                if (aww != null) {
                                    aww.a().a(2, pageBundle2);
                                }
                            }
                        }
                    }

                    public final void b(RideTraceHistory rideTraceHistory) {
                        RideHistoryPage.a(RideHistoryPage.this, rideTraceHistory);
                    }
                });
                ((PinnedSectionListView) rideHistoryPage.findViewById(R.id.pinnedsectionlistview)).setAdapter((ListAdapter) rideHistoryAdapter);
                double d = 0.0d;
                int i = 0;
                int i2 = 0;
                for (int i3 = 0; i3 < list.size(); i3++) {
                    i += ((RideTraceHistory) list.get(i3)).c;
                    i2 += ((RideTraceHistory) list.get(i3)).b;
                    if (d < ((RideTraceHistory) list.get(i3)).f) {
                        d = ((RideTraceHistory) list.get(i3)).f;
                    }
                }
                double d2 = i2 != 0 ? (((double) i) / ((double) i2)) * 3.6d : 0.0d;
                if (d2 >= d) {
                    d = 1.12d * d2;
                }
                rideHistoryPage.a(i, i2, d, d2);
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.ride_history_layout);
        a = new a(this);
        View contentView = getContentView();
        ((TitleBar) findViewById(R.id.title_bar)).setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RideHistoryPage.this.finish();
            }
        });
        this.c = (TextView) contentView.findViewById(R.id.running_history_length);
        this.d = (TextView) contentView.findViewById(R.id.running_history_length_tip);
        this.e = (TextView) contentView.findViewById(R.id.running_history_time_cost);
        this.f = (TextView) contentView.findViewById(R.id.running_history_time_cost_tip);
        this.g = (TextView) contentView.findViewById(R.id.history_topspeed);
        this.h = (TextView) contentView.findViewById(R.id.ride_average_speed_cost);
        efx.a(this.c);
        efx.a(this.e);
        efx.a(this.g);
        efx.a(this.h);
        this.i = contentView.findViewById(R.id.running_history_no_item_tip);
        this.j = (TextView) contentView.findViewById(R.id.running_history_to_navi);
        this.k = (PinnedSectionListView) contentView.findViewById(R.id.pinnedsectionlistview);
        this.j.setOnClickListener(this);
        this.b = (TextView) contentView.findViewById(R.id.ride_history_login);
        this.b.setOnClickListener(this);
    }

    public final void a(int i2, int i3, double d2, double d3) {
        this.h.setText(efv.b(d3));
        String[] a2 = efv.a(i2);
        this.c.setText(a2[0]);
        this.d.setText(String.format("%s(%s)", new Object[]{getString(R.string.ride_history_total_dis), a2[1]}));
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        if (i3 >= 3600) {
            this.e.setText(efv.a((long) i3));
            this.e.setText(decimalFormat.format(((double) i3) / 3600.0d));
            this.f.setText(getString(R.string.ride_history_time_h));
        } else {
            this.e.setText(decimalFormat.format(((double) i3) / 60.0d));
            this.f.setText(getString(R.string.ride_history_time_m));
        }
        this.g.setText(efv.b(d2));
    }

    public final void a() {
        ebr.a(false).post(new Runnable() {
            public final void run() {
                List<RideTraceHistory> b = ees.b();
                Message obtainMessage = RideHistoryPage.a.obtainMessage();
                if (b == null || b.size() == 0) {
                    obtainMessage.what = 0;
                    obtainMessage.obj = b;
                    RideHistoryPage.a.sendMessage(obtainMessage);
                    return;
                }
                if (b.size() > 0) {
                    obtainMessage.what = 0;
                    obtainMessage.obj = b;
                    RideHistoryPage.a.sendMessage(obtainMessage);
                }
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.running_history_to_navi) {
            if (id == R.id.ride_history_login) {
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null && !iAccountService.a()) {
                    LogManager.actionLogV2(LogConstant.PAGE_MORE, "B010");
                    iAccountService.a(getPageContext(), (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                            if (z) {
                                brn brn = (brn) ank.a(brn.class);
                                if (brn != null) {
                                    brn.b();
                                }
                            }
                        }
                    });
                }
            }
        } else if (ebm.a(getActivity())) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("bundle_key_page_from", "3");
            startPage(RouteFootRideMapPage.class, pageBundle);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new een(this);
    }

    static /* synthetic */ void a(RideHistoryPage rideHistoryPage, final RideTraceHistory rideTraceHistory) {
        if (rideTraceHistory != null) {
            if (rideHistoryPage.l == null) {
                SpannableString spannableString = new SpannableString(AMapAppGlobal.getApplication().getString(R.string.delete));
                spannableString.setSpan(new ForegroundColorSpan(rideHistoryPage.getResources().getColor(R.color.f_c_8)), 0, spannableString.length(), 33);
                com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
                aVar.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.ride_delete_record_msg)).b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.ride_delete_record_tip)).b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.cancel), (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        RideHistoryPage.this.dismissViewLayer(alertView);
                        RideHistoryPage.this.l = null;
                    }
                }).a((CharSequence) spannableString, (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        bte bte;
                        RideHistoryPage.this.dismissViewLayer(alertView);
                        RideHistoryPage.this.l = null;
                        AMapPageUtil.getAppContext();
                        bsp a2 = bsp.a();
                        RideTraceHistory rideTraceHistory = rideTraceHistory;
                        if (rideTraceHistory != null) {
                            bte = new bte();
                            bte.a = rideTraceHistory.a;
                            bte.e = Double.valueOf(rideTraceHistory.e);
                            bte.d = Integer.valueOf(rideTraceHistory.d);
                            bte.f = Long.valueOf(rideTraceHistory.g);
                            bte.g = Long.valueOf(rideTraceHistory.h);
                            bte.c = Integer.valueOf(rideTraceHistory.c);
                            bte.i = RideTraceHistory.a(rideTraceHistory.j);
                            bte.b = Integer.valueOf(rideTraceHistory.b);
                            bte.h = rideTraceHistory.i;
                            bte.k = 1;
                            bte.j = rideTraceHistory.k.getValue();
                            bte.l = Double.valueOf(rideTraceHistory.f);
                        } else {
                            bte = null;
                        }
                        a2.a(bte);
                        RideHistoryPage.this.a();
                    }
                }).c = new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        RideHistoryPage.this.dismissViewLayer(alertView);
                        RideHistoryPage.this.l = null;
                    }
                };
                aVar.a(true);
                rideHistoryPage.l = aVar.a();
            }
            rideHistoryPage.showViewLayer(rideHistoryPage.l);
        }
    }

    static /* synthetic */ void a(RideHistoryPage rideHistoryPage, boolean z) {
        if (z) {
            rideHistoryPage.i.setVisibility(0);
            rideHistoryPage.k.setVisibility(4);
            return;
        }
        rideHistoryPage.i.setVisibility(4);
        rideHistoryPage.k.setVisibility(0);
    }
}
