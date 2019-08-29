package defpackage;

import android.os.Handler;
import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.train.page.TrainDataPage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* renamed from: ejk reason: default package */
/* compiled from: TrainDataPresenter */
public final class ejk extends eaf<TrainDataPage> {
    private long a = 0;

    public ejk(TrainDataPage trainDataPage) {
        super(trainDataPage);
    }

    public final void onPageCreated() {
        int i;
        super.onPageCreated();
        PageBundle arguments = ((TrainDataPage) this.mPage).getArguments();
        if (arguments != null) {
            this.a = arguments.getLong("bundle_ticket_time");
            String string = arguments.getString("date_type_key", "");
            if (TextUtils.equals(string, "coach_date")) {
                eis.a = "CoachDateSelected";
                eis.b = "CoachDate";
                i = 1;
            } else {
                if (TextUtils.equals(string, "train_date")) {
                    eis.a = "TrainDataSelected";
                    eis.b = "TrainData";
                } else {
                    eis.a = "TrainDataSelected";
                    eis.b = "TrainData";
                }
                i = 0;
            }
            TrainDataPage trainDataPage = (TrainDataPage) this.mPage;
            long j = this.a;
            if (trainDataPage.a != null) {
                trainDataPage.b = (ExtendedWebView) trainDataPage.a.findViewById(R.id.train_data_webView);
                trainDataPage.c = new JsAdapter((bid) trainDataPage, (a) trainDataPage.b);
                trainDataPage.b.initializeWebView((Object) trainDataPage.c, (Handler) null, true, false);
                trainDataPage.b.setVisibility(0);
                trainDataPage.b.clearView();
                bgx bgx = (bgx) a.a.a(bgx.class);
                if (bgx != null) {
                    try {
                        String encode = URLEncoder.encode("请选择出发日期", "UTF-8");
                        ExtendedWebView extendedWebView = trainDataPage.b;
                        StringBuilder sb = new StringBuilder("exHotelCalendar.html?showTitleBar=1&type=common&toast=");
                        sb.append(encode);
                        sb.append("&range=");
                        sb.append(ejs.a().a(i));
                        sb.append("&date=");
                        sb.append(j);
                        extendedWebView.loadUrl(bgx.getUrl(sb.toString()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
