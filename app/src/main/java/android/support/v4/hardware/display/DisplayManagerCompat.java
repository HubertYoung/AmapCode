package android.support.v4.hardware.display;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import java.util.WeakHashMap;

public abstract class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap<>();

    static class JellybeanMr1Impl extends DisplayManagerCompat {
        private final Object a;

        public JellybeanMr1Impl(Context context) {
            this.a = DisplayManagerJellybeanMr1.a(context);
        }

        public Display getDisplay(int i) {
            return DisplayManagerJellybeanMr1.a(this.a, i);
        }

        public Display[] getDisplays() {
            return DisplayManagerJellybeanMr1.a(this.a);
        }

        public Display[] getDisplays(String str) {
            return DisplayManagerJellybeanMr1.a(this.a, str);
        }
    }

    static class LegacyImpl extends DisplayManagerCompat {
        private final WindowManager a;

        public LegacyImpl(Context context) {
            this.a = (WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY);
        }

        public Display getDisplay(int i) {
            Display defaultDisplay = this.a.getDefaultDisplay();
            if (defaultDisplay.getDisplayId() == i) {
                return defaultDisplay;
            }
            return null;
        }

        public Display[] getDisplays() {
            return new Display[]{this.a.getDefaultDisplay()};
        }

        public Display[] getDisplays(String str) {
            return str == null ? getDisplays() : new Display[0];
        }
    }

    public abstract Display getDisplay(int i);

    public abstract Display[] getDisplays();

    public abstract Display[] getDisplays(String str);

    DisplayManagerCompat() {
    }

    public static DisplayManagerCompat getInstance(Context context) {
        DisplayManagerCompat displayManagerCompat;
        synchronized (sInstances) {
            displayManagerCompat = sInstances.get(context);
            if (displayManagerCompat == null) {
                if (VERSION.SDK_INT >= 17) {
                    displayManagerCompat = new JellybeanMr1Impl(context);
                } else {
                    displayManagerCompat = new LegacyImpl(context);
                }
                sInstances.put(context, displayManagerCompat);
            }
        }
        return displayManagerCompat;
    }
}
