package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import com.autonavi.minimap.basemap.traffic.bean.TwiceReportType;
import com.autonavi.minimap.basemap.traffic.page.TrafficSubmitPage;

/* renamed from: csr reason: default package */
/* compiled from: TrafficButtonUtils */
public final class csr {
    public static void a(View view, int i, TrafficTopic trafficTopic, boolean z) {
        final PageBundle pageBundle;
        final bid pageContext = AMapPageFramework.getPageContext();
        if (pageContext != null) {
            if (i == 4) {
                pageBundle = new PageBundle();
                pageBundle.putObject("intent_twice_report_type", TwiceReportType.a(trafficTopic, 1));
                pageBundle.putBoolean("key_open_traffic_later", z);
            } else if (i == 8) {
                pageBundle = new PageBundle();
                pageBundle.putObject("intent_twice_report_type", TwiceReportType.a(trafficTopic, 2));
                pageBundle.putBoolean("intent_report_page_simple_version", true);
                pageBundle.putBoolean("key_open_traffic_later", z);
            } else if (i == 16) {
                pageBundle = new PageBundle();
                pageBundle.putObject("intent_twice_report_type", TwiceReportType.a(trafficTopic, 3));
                pageBundle.putBoolean("intent_report_page_simple_version", true);
                pageBundle.putBoolean("key_open_traffic_later", z);
            } else {
                return;
            }
            view.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    pageContext.startPage(TrafficSubmitPage.class, pageBundle);
                }
            });
        }
    }

    public static void a(View view, TrafficTopic trafficTopic) {
        final bid pageContext = AMapPageFramework.getPageContext();
        if (pageContext != null) {
            String showUrl = trafficTopic.getShowUrl();
            if (!TextUtils.isEmpty(showUrl)) {
                final aja aja = new aja(showUrl);
                view.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        Context context = pageContext.getContext();
                        if (context != null) {
                            if (!aaw.c(context)) {
                                ToastHelper.showToast(context.getString(R.string.network_error));
                                return;
                            }
                            aix aix = (aix) a.a.a(aix.class);
                            if (aix != null) {
                                aix.a(AMapPageUtil.getPageContext(), aja);
                            }
                        }
                    }
                });
            }
        }
    }

    public static String a(Context context, int i, int i2, int i3) {
        if (i3 == 2) {
            if (i2 == 1) {
                return String.format(context.getString(R.string.traffic_report_like), new Object[]{Integer.valueOf(i)});
            } else if (i2 != 2) {
                return "";
            } else {
                return String.format(context.getString(R.string.traffic_report_dislike), new Object[]{Integer.valueOf(i)});
            }
        } else if (i3 <= 2) {
            return "";
        } else {
            if (i2 == 1 || i2 == 2) {
                return String.valueOf(i);
            }
            return "";
        }
    }
}
