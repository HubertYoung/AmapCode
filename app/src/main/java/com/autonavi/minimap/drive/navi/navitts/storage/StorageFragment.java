package com.autonavi.minimap.drive.navi.navitts.storage;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.amap.bundle.drivecommon.mvp.view.DriveBasePage;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.drive.navi.navitts_dependencies.DriveOfflineSDK;
import com.autonavi.minimap.offline.utils.UserReport;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.text.DecimalFormat;
import java.util.List;

public class StorageFragment extends DriveBasePage<dgp> implements OnClickListener, dgs {
    public LinearLayout a;
    public ProgressDlg b;
    public dgt c;
    public Context d;
    public View e;
    private final DecimalFormat f = new DecimalFormat(DiskFormatter.FORMAT);

    public void onCreate(Context context) {
        super.onCreate(context);
        this.d = getContext();
        setContentView(R.layout.storagecard);
    }

    public static ON_BACK_TYPE a() {
        return ON_BACK_TYPE.TYPE_NORMAL;
    }

    public void onClick(View view) {
        int i = 0;
        if (view.getId() == R.id.title_btn_left) {
            dgp dgp = (dgp) this.mPresenter;
            dgp.c();
            if (dgp.c != null) {
                dgp.c.e();
            }
            LogManager.actionLogV2(UserReport.PAGE_OFFLINEDATA_STORAGEINFO, "B004", 0, 0);
            return;
        }
        Integer num = (Integer) view.getTag();
        if (num != null) {
            dgp dgp2 = (dgp) this.mPresenter;
            int intValue = num.intValue();
            if (intValue >= 0 && intValue < dgp2.e.size()) {
                dgn dgn = dgp2.e.get(intValue);
                if (!TextUtils.isEmpty(dgp2.d) && !dgp2.d.equals(dgn.a) && dgp2.c != null) {
                    DriveOfflineSDK.e();
                    if (DriveOfflineSDK.a() == 2) {
                        i = R.string.offline_storage_warn_downing_navitts;
                    }
                    if (i != 0) {
                        ToastHelper.showToast(dhd.a(i));
                        return;
                    }
                    dgp2.c.b(dgn);
                }
            }
        }
    }

    public final void a(int i) {
        switch (i) {
            case 1:
                ToastHelper.showToast(this.d.getString(R.string.offline_storage_finish));
                return;
            case 2:
                ToastHelper.showLongToast(getResources().getString(R.string.no_Storage));
                return;
            case 3:
                Activity activity = getActivity();
                activity.runOnUiThread(new Runnable(activity) {
                    final /* synthetic */ Activity a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        if (this.a != null && !this.a.isFinishing()) {
                            Application application = AMapAppGlobal.getApplication();
                            final bid pageContext = AMapPageUtil.getPageContext();
                            a aVar = new a(application);
                            aVar.a((CharSequence) application.getString(R.string.offline_navi_tts_path_fail_dialog_title)).a((CharSequence) application.getString(R.string.drive_taxi_compare_agree), (a) new a() {
                                public final void onClick(AlertView alertView, int i) {
                                    if (pageContext != null) {
                                        pageContext.dismissViewLayer(alertView);
                                    }
                                }
                            });
                            aVar.a(true);
                            if (pageContext != null) {
                                pageContext.showViewLayer(aVar.a());
                            }
                        }
                    }
                });
                return;
            case 4:
                ToastHelper.showLongToast(this.d.getString(R.string.offline_storage_cancle));
                break;
        }
    }

    public final void a(dgn dgn) {
        StringBuilder sb = new StringBuilder();
        sb.append(d(dgn));
        sb.append(getString(R.string.offline_storage_size_msg));
        ToastHelper.showLongToast(sb.toString());
    }

    public final void a(List<dgn> list, String str) {
        this.a.removeAllViews();
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        for (int i = 0; i < list.size(); i++) {
            View inflate = layoutInflater.inflate(R.layout.storagecard_item, null);
            TextView textView = (TextView) inflate.findViewById(R.id.storage_name);
            TextView textView2 = (TextView) inflate.findViewById(R.id.storage_Available_size);
            TextView textView3 = (TextView) inflate.findViewById(R.id.storage_path);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_storage_status);
            dgn dgn = list.get(i);
            if (dgn.a.equals(str)) {
                imageView.setVisibility(0);
            }
            textView.setText(d(dgn));
            String a2 = dhd.a(dhd.a(dgn.b, dgn.c));
            String a3 = dhd.a(dgn.c);
            textView2.setText(dhd.a(R.string.offline_sdcard_size, "", a2, a3));
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.offline_storage_path));
            sb.append(dgn.a);
            textView3.setText(sb.toString());
            inflate.setTag(Integer.valueOf(i));
            inflate.setOnClickListener(this);
            this.a.addView(inflate);
        }
    }

    public final void b() {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            if (this.b == null) {
                this.b = new ProgressDlg(activity);
                this.b.setCanceledOnTouchOutside(false);
                this.b.setOnCancelListener(new OnCancelListener() {
                    public final void onCancel(DialogInterface dialogInterface) {
                        dgp dgp = (dgp) StorageFragment.this.mPresenter;
                        if (dgp.c != null) {
                            dgp.c.e();
                        }
                    }
                });
            }
            this.b.setMessage("检查数据大小中");
            if (!this.b.isShowing()) {
                this.b.show();
            }
        }
    }

    public final void b(int i) {
        String str;
        if (i >= 0 && i <= 100) {
            Activity activity = getActivity();
            if (activity != null && !activity.isFinishing()) {
                if (this.c == null) {
                    this.c = new dgt(activity);
                    this.c.setCancelable(true);
                    this.c.setCanceledOnTouchOutside(false);
                    this.c.setOnCancelListener(new OnCancelListener() {
                        public final void onCancel(DialogInterface dialogInterface) {
                            dgp dgp = (dgp) StorageFragment.this.mPresenter;
                            dgp.f.cancel();
                            if (dgp.c != null) {
                                dgp.c.a(4);
                            }
                        }
                    });
                }
                if (i < 10) {
                    StringBuilder sb = new StringBuilder(",  ");
                    sb.append(i);
                    sb.append("%");
                    str = sb.toString();
                } else if (i < 100) {
                    StringBuilder sb2 = new StringBuilder(", ");
                    sb2.append(i);
                    sb2.append("%");
                    str = sb2.toString();
                } else {
                    StringBuilder sb3 = new StringBuilder(",");
                    sb3.append(i);
                    sb3.append("%");
                    str = sb3.toString();
                }
                dgt dgt = this.c;
                StringBuilder sb4 = new StringBuilder();
                sb4.append(this.d.getString(R.string.offline_storage_progress_msg));
                sb4.append(str);
                String sb5 = sb4.toString();
                if (sb5 != null && !sb5.equals("")) {
                    dgt.a.setText(sb5);
                }
                if (!this.c.isShowing()) {
                    this.c.show();
                }
            }
        }
    }

    public final void c() {
        if (this.b != null && this.b.isShowing()) {
            this.b.dismiss();
        }
        if (this.c != null && this.c.isShowing()) {
            this.c.dismiss();
        }
    }

    public final void b(final dgn dgn) {
        a aVar = new a(AMapAppGlobal.getApplication());
        aVar.a((CharSequence) getString(R.string.offline_storage_warn_change, d(dgn))).a((CharSequence) getString(R.string.Ok), (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                StorageFragment.this.dismissViewLayer(alertView);
                ((dgp) StorageFragment.this.mPresenter).a(dgn);
                LogManager.actionLogV2(UserReport.PAGE_OFFLINEDATA_STORAGEINFO, "B003", 0, 0);
            }
        }).b((CharSequence) getString(R.string.cancle), (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                StorageFragment.this.dismissViewLayer(alertView);
            }
        });
        aVar.a(true);
        showViewLayer(aVar.a());
    }

    public final void d() {
        ToastHelper.showLongToast(getString(R.string.offline_storage_fail_msg));
    }

    public final void c(final dgn dgn) {
        a aVar = new a(AMapAppGlobal.getApplication());
        StringBuilder sb = new StringBuilder();
        sb.append(d(dgn));
        sb.append(getString(R.string.offline_storage_access_msg));
        aVar.a((CharSequence) sb.toString()).a((CharSequence) getString(R.string.retry), (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                StorageFragment.this.dismissViewLayer(alertView);
                ((dgp) StorageFragment.this.mPresenter).a(dgn);
            }
        }).b((CharSequence) getString(R.string.cancle), (a) new a() {
            public final void onClick(AlertView alertView, int i) {
                StorageFragment.this.dismissViewLayer(alertView);
            }
        });
        aVar.a(true);
        showViewLayer(aVar.a());
    }

    public final void e() {
        finish();
    }

    public final void a(ResultType resultType) {
        setResult(resultType, new PageBundle());
    }

    private String d(dgn dgn) {
        if (!dgn.d) {
            return getString(R.string.offline_storage_inner);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.offline_storage_extern));
        sb.append(dgn.e);
        return sb.toString();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return dgp.a(this, this);
    }
}
