package com.autonavi.minimap.route.run.page;

import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.footresult.ajx.ModuleFoot;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.run.adapter.RunningHistoryAdapter;
import com.autonavi.minimap.route.run.beans.RunTraceHistory;
import com.autonavi.minimap.route.run.beans.RunTraceHistory.RunType;
import com.autonavi.minimap.route.run.view.PinnedSectionListView;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.TitleBar;
import java.io.File;
import java.text.DecimalFormat;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class RunningHistoryPage extends AbstractBasePage<efp> {
    /* access modifiers changed from: private */
    public static a a;
    /* access modifiers changed from: private */
    public static String b;
    private View c;
    /* access modifiers changed from: private */
    public AlertView d;

    static class a extends ecs<RunningHistoryPage> {
        a(RunningHistoryPage runningHistoryPage) {
            super(runningHistoryPage);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            RunningHistoryPage runningHistoryPage = (RunningHistoryPage) a();
            if (runningHistoryPage != null && message.what == 0 && (message.obj instanceof List)) {
                List list = (List) message.obj;
                if (list.size() == 0) {
                    RunningHistoryPage.a(runningHistoryPage, true);
                    runningHistoryPage.a(0, 0, 0);
                    return;
                }
                RunningHistoryAdapter runningHistoryAdapter = new RunningHistoryAdapter(AMapAppGlobal.getApplication(), list);
                RunningHistoryPage.a(runningHistoryPage, false);
                runningHistoryAdapter.setHistoryItemListener(new com.autonavi.minimap.route.run.adapter.RunningHistoryAdapter.a() {
                    public final void a(RunTraceHistory runTraceHistory) {
                        eft.a("performance-", "clickFootHistoryRecord");
                        if (runTraceHistory != null) {
                            PageBundle pageBundle = new PageBundle();
                            if (runTraceHistory.j == RunType.FOOT_TYPE) {
                                pageBundle.putString("url", ModuleFoot.URL_FOOT_END);
                                String b = efu.b(runTraceHistory);
                                if (!TextUtils.isEmpty(b)) {
                                    pageBundle.putString("jsData", b);
                                }
                                pageBundle.putBoolean("bundle_key_page_from_history", true);
                                avi avi = (avi) defpackage.esb.a.a.a(avi.class);
                                if (avi != null) {
                                    avi.c().a(3, pageBundle);
                                }
                            } else {
                                pageBundle.putObject("data", runTraceHistory);
                                RunningHistoryPage.this.startPage(RunFinishMapPage.class, pageBundle);
                            }
                            LogManager.actionLogV2("P00288", "D003");
                        }
                    }

                    public final void b(RunTraceHistory runTraceHistory) {
                        RunningHistoryPage.a(RunningHistoryPage.this, runTraceHistory);
                    }
                });
                ((PinnedSectionListView) runningHistoryPage.findViewById(R.id.pinnedsectionlistview)).setAdapter((ListAdapter) runningHistoryAdapter);
                int i = 0;
                int i2 = 0;
                int i3 = 0;
                for (int i4 = 0; i4 < list.size(); i4++) {
                    i += ((RunTraceHistory) list.get(i4)).c;
                    i2 += ((RunTraceHistory) list.get(i4)).b;
                    i3 += ((RunTraceHistory) list.get(i4)).d;
                }
                runningHistoryPage.a(i, i2, i3);
            }
        }
    }

    static {
        StringBuilder sb = new StringBuilder("{\"eventType\":11,\"naviState\":4,\"viewState\":2,\"action\":2,\"isArrived\":true,\"isOnline\":false,\"trackInfo\":\"{\\\"startPoint\\\":{\\\"lon\\\":116.47267150878906,\\\"lat\\\":39.993453979492188,\\\"x\\\":221066047,\\\"y\\\":101630440},\\\"endPoint\\\":{\\\"lon\\\":116.47322845458984,\\\"lat\\\":39.995357513427734,\\\"x\\\":221066462,\\\"y\\\":101628588},\\\"exitPoint\\\":{\\\"lon\\\":0,\\\"lat\\\":0,\\\"x\\\":134217728,\\\"y\\\":134217728},\\\"trackPoints\\\":[{\\\"lon\\\":116.47272,\\\"lat\\\":39.9934,\\\"x\\\":221066083,\\\"y\\\":101630493},{\\\"lon\\\":116.472672,\\\"lat\\\":39.99332,\\\"x\\\":221066047,\\\"y\\\":101630571},{\\\"lon\\\":116.472696,\\\"lat\\\":39.993352,\\\"x\\\":221066065,\\\"y\\\":101630540},{\\\"lon\\\":116.472624,\\\"lat\\\":39.993444,\\\"x\\\":221066011,\\\"y\\\":101630450},{\\\"lon\\\":116.472752,\\\"lat\\\":39.993556,\\\"x\\\":221066107,\\\"y\\\":101630341},{\\\"lon\\\":116.472888,\\\"lat\\\":39.993556,\\\"x\\\":221066208,\\\"y\\\":101630341},{\\\"lon\\\":116.473024,\\\"lat\\\":39.993636,\\\"x\\\":221066309,\\\"y\\\":101630263},{\\\"lon\\\":116.473,\\\"lat\\\":39.993744,\\\"x\\\":221066292,\\\"y\\\":101630158},{\\\"lon\\\":116.473032,\\\"lat\\\":39.993836,\\\"x\\\":221066315,\\\"y\\\":101630068},{\\\"lon\\\":116.473192,\\\"lat\\\":39.9939,\\\"x\\\":221066435,\\\"y\\\":101630006},{\\\"lon\\\":116.473168,\\\"lat\\\":39.994004,\\\"x\\\":221066417,\\\"y\\\":101629905},{\\\"lon\\\":116.473016,\\\"lat\\\":39.994104,\\\"x\\\":221066303,\\\"y\\\":101629808},{\\\"lon\\\":116.472872,\\\"lat\\\":39.9942,\\\"x\\\":221066196,\\\"y\\\":101629714},{\\\"lon\\\":116.47272,\\\"lat\\\":39.994304,\\\"x\\\":221066083,\\\"y\\\":101629613},{\\\"lon\\\":116.472584,\\\"lat\\\":39.9944,\\\"x\\\":221065981,\\\"y\\\":101629520},{\\\"lon\\\":116.472504,\\\"lat\\\":39.994536,\\\"x\\\":221065922,\\\"y\\\":101629387},{\\\"lon\\\":116.472584,\\\"lat\\\":39.994648,\\\"x\\\":221065981,\\\"y\\\":101629278},{\\\"lon\\\":116.472704,\\\"lat\\\":39.994768,\\\"x\\\":221066071,\\\"y\\\":101629161},{\\\"lon\\\":116.472832,\\\"lat\\\":39.994884,\\\"x\\\":221066166,\\\"y\\\":101629048},{\\\"lon\\\":116.472944,\\\"lat\\\":39.995,\\\"x\\\":221066250,\\\"y\\\":101628936},{\\\"lon\\\":116.473072,\\\"lat\\\":39.995116,\\\"x\\\":221066345,\\\"y\\\":101628823},{\\\"lon\\\":116.473192,\\\"lat\\\":39.995232,\\\"x\\\":221066435,\\\"y\\\":101628710},{\\\"lon\\\":116.473288,\\\"lat\\\":39.99532,\\\"x\\\":221066506,\\\"y\\\":101628624},{\\\"lon\\\":116.473288,\\\"lat\\\":39.99532,\\\"x\\\":221066506,\\\"y\\\":101628624}],\\\"yamPoints\\\":[],\\\"startName\\\":\\\"北京市朝阳区阜荣街10号\\\",\\\"endName\\\":\\\"北京市朝阳区阜通西大街421号\\\",\\\"imagePath\\\":\\\"\\\",\\\"startTime\\\":");
        sb.append(ebb.a);
        sb.append(",\\\"endTime\\\":");
        sb.append(System.currentTimeMillis());
        sb.append(",\\\"distance\\\":352,\\\"calorie\\\":17,\\\"drivenTime\\\":27,\\\"averageSpeed\\\":46,\\\"maxSpeed\\\":60}\"}");
        b = sb.toString();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        eft.a("performance-", "RunningHistoryPage  onCreate");
        requestScreenOrientation(1);
        setContentView(R.layout.running_history_layout);
        a = new a(this);
        ((TitleBar) findViewById(R.id.title_bar)).setOnBackClickListener((OnClickListener) new OnClickListener() {
            public final void onClick(View view) {
                RunningHistoryPage.this.finish();
            }
        });
        this.c = getContentView();
        efx.a((TextView) this.c.findViewById(R.id.running_history_length));
        efx.a((TextView) this.c.findViewById(R.id.running_history_time_cost));
        efx.a((TextView) this.c.findViewById(R.id.running_history_heat_cost));
        View contentView = getContentView();
        if (contentView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory());
            sb.append("/testfootdata");
            if (new File(sb.toString()).exists()) {
                Button button = (Button) contentView.findViewById(R.id.test_addtrace);
                button.setVisibility(0);
                button.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        efu.a(RunningHistoryPage.b);
                    }
                });
            }
        }
    }

    public final void a(int i, int i2, int i3) {
        if (this.c != null) {
            TextView textView = (TextView) this.c.findViewById(R.id.running_history_time_cost);
            TextView textView2 = (TextView) this.c.findViewById(R.id.running_history_time_cost_tip);
            TextView textView3 = (TextView) this.c.findViewById(R.id.running_history_heat_cost);
            TextView textView4 = (TextView) this.c.findViewById(R.id.running_history_heat_cost_tip);
            String[] a2 = efv.a(i);
            String[] strArr = {String.valueOf(i3), getString(R.string.running_route_start_running_heat_unit_kcal)};
            ((TextView) this.c.findViewById(R.id.running_history_length)).setText(a2[0]);
            ((TextView) this.c.findViewById(R.id.running_history_length_tip)).setText(String.format("%s(%s)", new Object[]{getString(R.string.ride_history_total_dis), a2[1]}));
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            if (i2 >= 3600) {
                textView.setText(efv.a((long) i2));
                textView.setText(decimalFormat.format(((double) i2) / 3600.0d));
                textView2.setText(getString(R.string.runnnig_history_time_h));
            } else {
                textView.setText(decimalFormat.format(((double) i2) / 60.0d));
                textView2.setText(getString(R.string.running_history_time_m));
            }
            textView3.setText(strArr[0]);
            textView4.setText(String.format("%s(%s)", new Object[]{getString(R.string.running_history_heat), strArr[1]}));
        }
    }

    public final void a() {
        ebr.a(false).post(new Runnable() {
            public final void run() {
                List<RunTraceHistory> a2 = efu.a();
                Message obtainMessage = RunningHistoryPage.a.obtainMessage();
                if (a2 == null || a2.size() == 0) {
                    obtainMessage.what = 0;
                    obtainMessage.obj = a2;
                    RunningHistoryPage.a.sendMessage(obtainMessage);
                    return;
                }
                if (a2.size() > 0) {
                    obtainMessage.what = 0;
                    obtainMessage.obj = a2;
                    RunningHistoryPage.a.sendMessage(obtainMessage);
                }
            }
        });
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new efp(this);
    }

    static /* synthetic */ void a(RunningHistoryPage runningHistoryPage, final RunTraceHistory runTraceHistory) {
        if (runTraceHistory != null) {
            if (runningHistoryPage.d == null) {
                SpannableString spannableString = new SpannableString(AMapAppGlobal.getApplication().getString(R.string.delete));
                spannableString.setSpan(new ForegroundColorSpan(runningHistoryPage.getResources().getColor(R.color.f_c_8)), 0, spannableString.length(), 33);
                com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(AMapAppGlobal.getApplication());
                aVar.a((CharSequence) AMapAppGlobal.getApplication().getString(R.string.running_delete_record_msg)).b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.running_delete_record_tip)).b((CharSequence) AMapAppGlobal.getApplication().getString(R.string.cancel), (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        RunningHistoryPage.this.dismissViewLayer(alertView);
                        RunningHistoryPage.this.d = null;
                    }
                }).a((CharSequence) spannableString, (defpackage.ern.a) new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        btg btg;
                        RunningHistoryPage.this.dismissViewLayer(alertView);
                        RunningHistoryPage.this.d = null;
                        AMapPageUtil.getAppContext();
                        bsq a2 = bsq.a();
                        RunTraceHistory runTraceHistory = runTraceHistory;
                        if (runTraceHistory != null) {
                            btg = new btg();
                            btg.a = runTraceHistory.a;
                            btg.e = Double.valueOf(runTraceHistory.e);
                            btg.d = Integer.valueOf(runTraceHistory.d);
                            btg.f = Long.valueOf(runTraceHistory.f);
                            btg.g = Long.valueOf(runTraceHistory.g);
                            btg.c = Integer.valueOf(runTraceHistory.c);
                            btg.i = RunTraceHistory.a(runTraceHistory.i);
                            btg.b = Integer.valueOf(runTraceHistory.b);
                            btg.h = runTraceHistory.h;
                            btg.k = 1;
                            btg.j = runTraceHistory.j.getValue();
                        } else {
                            btg = null;
                        }
                        a2.a(btg);
                        RunningHistoryPage.this.a();
                    }
                }).c = new defpackage.ern.a() {
                    public final void onClick(AlertView alertView, int i) {
                        RunningHistoryPage.this.dismissViewLayer(alertView);
                        RunningHistoryPage.this.d = null;
                    }
                };
                aVar.a(true);
                runningHistoryPage.d = aVar.a();
            }
            runningHistoryPage.showViewLayer(runningHistoryPage.d);
        }
    }

    static /* synthetic */ void a(RunningHistoryPage runningHistoryPage, boolean z) {
        if (z) {
            View findViewById = runningHistoryPage.c.findViewById(R.id.running_history_no_item_tip);
            findViewById.setVisibility(0);
            findViewById.findViewById(R.id.running_history_to_navi).setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (ebm.a(RunningHistoryPage.this.getActivity())) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("from", 3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        LogManager.actionLogV2("P00288", "D002", jSONObject);
                        RunningHistoryPage.this.startPage(RouteFootRunMapPage.class, new PageBundle());
                    }
                }
            });
            runningHistoryPage.c.findViewById(R.id.pinnedsectionlistview).setVisibility(4);
            return;
        }
        runningHistoryPage.c.findViewById(R.id.running_history_no_item_tip).setVisibility(4);
        runningHistoryPage.c.findViewById(R.id.pinnedsectionlistview).setVisibility(0);
    }
}
