package com.autonavi.minimap.basemap.traffic;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.bundle.banner.view.DBanner;
import com.autonavi.bundle.banner.view.DBanner.BannerActionListener;
import com.autonavi.bundle.banner.view.DBanner.BannerListener;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;

public final class TrafficReportResultDialog extends CompatDialog implements OnClickListener {
    private View a = findViewById(R.id.btn_ok);
    /* access modifiers changed from: private */
    public DBanner b;

    public enum LogEvent {
        BANNER,
        OK,
        SHOW
    }

    public static class a implements BannerListener {
        private WeakReference<TrafficReportResultDialog> a;

        public a(TrafficReportResultDialog trafficReportResultDialog) {
            this.a = new WeakReference<>(trafficReportResultDialog);
        }

        public final void onFinish(boolean z) {
            if (!(this.a == null || this.a.get() == null || ((TrafficReportResultDialog) this.a.get()).b == null)) {
                if (z) {
                    ((TrafficReportResultDialog) this.a.get()).b.setVisibility(0);
                    return;
                }
                ((TrafficReportResultDialog) this.a.get()).b.setVisibility(8);
            }
        }
    }

    public TrafficReportResultDialog(Activity activity) {
        super(activity, R.style.transparent_dialog);
        setContentView(R.layout.traffic_report_result_dialog);
        this.a.setOnClickListener(this);
        this.b = (DBanner) findViewById(R.id.banner);
        this.b.setImageScaleType(ScaleType.FIT_CENTER);
        this.b.setBgColor(-1);
        this.b.initTrafficReportBanner(true, new a(this));
        this.b.setBannerActionListener(new BannerActionListener() {
            public final void onBannerItemClick(String str) {
                TrafficReportResultDialog.b(LogEvent.BANNER);
            }
        });
        getWindow().setLayout(-1, -2);
        getWindow().setGravity(80);
        setCanceledOnTouchOutside(true);
    }

    public final void show() {
        super.show();
        b(LogEvent.SHOW);
    }

    public final void onClick(View view) {
        if (view == this.a) {
            b(LogEvent.OK);
            dismiss();
        }
    }

    /* access modifiers changed from: private */
    public static void b(LogEvent logEvent) {
        String str = "";
        switch (logEvent) {
            case BANNER:
                str = "B001";
                break;
            case OK:
                str = "B002";
                break;
            case SHOW:
                str = "B003";
                break;
        }
        if (!TextUtils.isEmpty(LogConstant.MAIN_MAP_TRAFFIC_REPORT_RESULT) && !TextUtils.isEmpty(str)) {
            LogManager.actionLogV2(LogConstant.MAIN_MAP_TRAFFIC_REPORT_RESULT, str);
        }
    }
}
