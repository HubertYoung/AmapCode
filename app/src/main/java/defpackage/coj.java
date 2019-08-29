package defpackage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.TextView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.IBusErrorReportRemind;
import com.autonavi.minimap.basemap.errorback.inter.impl.BusErrorReportRemindImpl$4;
import com.autonavi.minimap.feedback.FeedbackRequestHolder;
import com.autonavi.minimap.feedback.param.BindRequest;

/* renamed from: coj reason: default package */
/* compiled from: BusErrorReportRemindImpl */
public class coj implements OnTouchListener, IBusErrorReportRemind {
    /* access modifiers changed from: private */
    public AlertDialog a;
    private Handler b = new Handler();
    /* access modifiers changed from: private */
    public boolean c;
    private OnClickListener d = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.btn_detail) {
                StringBuilder sb = new StringBuilder();
                sb.append(ConfigerHelper.getInstance().getKeyValue("bus_activity_url"));
                sb.append("?nofooter");
                aja aja = new aja(sb.toString());
                aja.b = new ajf() {
                    public final String b() {
                        return AMapPageUtil.getAppContext().getString(R.string.event_details);
                    }
                };
                aix aix = (aix) defpackage.esb.a.a.a(aix.class);
                if (aix != null) {
                    aix.a(AMapPageUtil.getPageContext(), aja);
                }
                coj.this.dismissDialog();
                return;
            }
            if (view.getId() == R.id.btn_cancel) {
                if (coj.this.a != null && coj.this.a.isShowing()) {
                    coj.this.a.cancel();
                    coj.this.a = null;
                }
            } else if (view.getId() == R.id.btn_confirm) {
                coj.this.dismissDialog();
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService != null) {
                    iAccountService.a(AMapPageUtil.getPageContext(), (anq) new anq() {
                        public final void loginOrBindCancel() {
                        }

                        public final void onComplete(boolean z) {
                            if (z) {
                                if (coj.this.a != null) {
                                    coj.this.a.cancel();
                                }
                                if (!TextUtils.isEmpty(coj.this.f)) {
                                    coj.this.a();
                                }
                            }
                        }
                    });
                }
            }
        }
    };
    private a e;
    /* access modifiers changed from: private */
    public String f;

    /* renamed from: coj$a */
    /* compiled from: BusErrorReportRemindImpl */
    class a extends CompatDialog {
        public String a;

        public a(Activity activity, int i) {
            super(activity, i);
        }

        /* access modifiers changed from: protected */
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            setContentView(R.layout.layout_bus_error_remind);
            findViewById(R.id.ll_bus_report_remind).setOnTouchListener(coj.this);
            TextView textView = (TextView) findViewById(R.id.tv_content);
            if (!TextUtils.isEmpty(this.a)) {
                textView.setText(this.a);
            }
        }
    }

    public void handlePageOnResume(final Activity activity, final int i) {
        if (this.c) {
            a((Context) activity);
            return;
        }
        if (i == 4) {
            this.b.postDelayed(new Runnable() {
                public final void run() {
                    coj.a(coj.this, activity, i);
                }
            }, 330);
        }
    }

    public void handleResume(Context context) {
        if (this.c) {
            a(context);
        }
    }

    public boolean isOn(Context context) {
        return context.getSharedPreferences("error_report_prefrences", 0).getBoolean("is_bus_need_remind", false);
    }

    public void setContentAndState(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("error_report_prefrences", 0);
        if (TextUtils.isEmpty(str)) {
            sharedPreferences.edit().putBoolean("is_bus_need_remind", false).putString("remind_content", "").commit();
        } else {
            sharedPreferences.edit().putBoolean("is_bus_need_remind", true).putString("remind_content", str).commit();
        }
    }

    public void dismissDialog() {
        if (this.e != null && this.e.isShowing()) {
            this.e.dismiss();
            this.e = null;
        }
        if (this.a != null && this.a.isShowing()) {
            this.a.dismiss();
            this.a = null;
        }
    }

    public final void a(Context context) {
        dismissDialog();
        if (this.a == null || this.a.getContext() != context) {
            this.a = new Builder(context).create();
            this.a.setCancelable(false);
            try {
                this.a.show();
            } catch (Throwable th) {
                DebugLog.error(th);
            }
            this.a.setOnCancelListener(new OnCancelListener() {
                public final void onCancel(DialogInterface dialogInterface) {
                    coj.this.c = false;
                }
            });
            Window window = this.a.getWindow();
            window.setContentView(R.layout.layout_bus_error_login_remind);
            window.setGravity(17);
            window.setLayout(-2, -2);
            this.c = true;
            window.findViewById(R.id.btn_detail).setOnClickListener(this.d);
            window.findViewById(R.id.btn_cancel).setOnClickListener(this.d);
            window.findViewById(R.id.btn_confirm).setOnClickListener(this.d);
        }
        if (!this.a.isShowing()) {
            this.a.show();
            this.c = true;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        dismissDialog();
        return false;
    }

    public final void a() {
        if (!TextUtils.isEmpty(this.f)) {
            BindRequest bindRequest = new BindRequest();
            bindRequest.b = this.f;
            FeedbackRequestHolder.getInstance().sendBind(bindRequest, new BusErrorReportRemindImpl$4(this));
        }
    }

    public void tryToRecord(String str, final Context context) {
        String str2;
        boolean z = false;
        if (context.getSharedPreferences("error_report_prefrences", 0).getBoolean("is_bus_need_remind", false)) {
            IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
            if (iAccountService == null) {
                str2 = "";
            } else {
                ant e2 = iAccountService.e();
                if (e2 == null) {
                    str2 = "";
                } else {
                    str2 = e2.a;
                }
            }
            if (TextUtils.isEmpty(str2)) {
                z = true;
            }
        }
        if (z) {
            this.f = str;
            this.b.postDelayed(new Runnable() {
                public final void run() {
                    coj.this.a(context);
                }
            }, 500);
            return;
        }
        this.f = str;
        a();
    }

    static /* synthetic */ void a(coj coj, Activity activity, int i) {
        boolean z;
        String str;
        boolean z2;
        SharedPreferences sharedPreferences = activity.getSharedPreferences("error_report_prefrences", 0);
        if (sharedPreferences != null) {
            str = sharedPreferences.getString("remind_content", "");
            z = sharedPreferences.getBoolean("is_bus_need_remind", false);
            z2 = i < 0 || (sharedPreferences.getInt("bus_remind_page_sp", 0) & i) == i;
        } else {
            str = null;
            z2 = false;
            z = false;
        }
        if (z && !z2) {
            if (coj.e == null || coj.e.getContext() != activity) {
                coj.e = new a(activity, R.style.FullScreenDialog);
                coj.e.setCancelable(true);
                coj.e.setCanceledOnTouchOutside(true);
                coj.e.a = str;
                coj.e.show();
            }
            if (!coj.e.isShowing()) {
                coj.e.show();
            }
            if (activity != null) {
                SharedPreferences sharedPreferences2 = activity.getSharedPreferences("error_report_prefrences", 0);
                if (sharedPreferences2 != null) {
                    sharedPreferences2.edit().putInt("bus_remind_page_sp", sharedPreferences2.getInt("bus_remind_page_sp", 0) | i).commit();
                }
            }
        }
    }
}
