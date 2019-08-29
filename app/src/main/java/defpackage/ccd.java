package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;

/* renamed from: ccd reason: default package */
/* compiled from: SearchCommonUtils */
public final class ccd {
    public static String a(int i) {
        return i == 0 ? "全图" : i == 2 ? "全表" : i == 1 ? "图表" : i == 4 ? ModulePoi.TIPS : "";
    }

    public static String a(InfoliteResult infoliteResult) {
        int i;
        if (bcy.d(infoliteResult)) {
            if (infoliteResult.searchInfo.a.b == 0) {
                i = 2;
            } else if (infoliteResult.searchInfo.a.b == 1) {
                i = 1;
            }
            if (bcy.b(infoliteResult) && infoliteResult.searchInfo.l.size() == 1) {
                i = 4;
            }
            return a(i);
        }
        i = 0;
        i = 4;
        return a(i);
    }

    public static int a(Context context) {
        Point point = new Point();
        ((WindowManager) context.getApplicationContext().getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getSize(point);
        return Math.max(point.x, point.y);
    }

    public static int a(View view) {
        if (view == null) {
            return 0;
        }
        try {
            view.measure(MeasureSpec.makeMeasureSpec(view.getResources().getDisplayMetrics().widthPixels, Integer.MIN_VALUE), 0);
            return view.getMeasuredHeight();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
