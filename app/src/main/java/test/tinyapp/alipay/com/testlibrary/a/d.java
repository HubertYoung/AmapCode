package test.tinyapp.alipay.com.testlibrary.a;

import android.view.WindowManager;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;

/* compiled from: ScreenUtil */
public final class d {
    private static int a = Integer.MAX_VALUE;
    private static int b = -1;
    private static int c = -1;

    static {
        b();
        c();
    }

    private static int b() {
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            a = H5Utils.getContext().getResources().getDimensionPixelSize(Integer.parseInt(clazz.getField("status_bar_height").get(clazz.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public static int a() {
        if (b == -1) {
            c();
        }
        return b;
    }

    private static void c() {
        WindowManager windowManager = (WindowManager) H5Utils.getContext().getSystemService(TemplateTinyApp.WINDOW_KEY);
        b = windowManager.getDefaultDisplay().getWidth();
        c = windowManager.getDefaultDisplay().getHeight();
    }

    public static int a(int dp) {
        return (int) (((double) (((float) dp) * H5Utils.getContext().getResources().getDisplayMetrics().density)) + 0.5d);
    }
}
