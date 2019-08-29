package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.util.StateSet;
import android.view.View;
import android.view.WindowManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.amap.app.AMapAppGlobal;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;

/* renamed from: cnu reason: default package */
/* compiled from: Util */
public final class cnu {
    private static cnu a = new cnu();

    public static int a(Context context) {
        if (context == null) {
            context = AMapAppGlobal.getApplication();
        }
        return ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getWidth();
    }

    public static int b(Context context) {
        if (context == null) {
            context = AMapAppGlobal.getApplication();
        }
        return ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getHeight();
    }

    public static float a(float f) {
        return (float) Math.round(f * (((float) Resources.getSystem().getDisplayMetrics().densityDpi) / 160.0f));
    }

    public static int a(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = fArr[2] * 0.9f;
        return Color.HSVToColor(fArr);
    }

    public static int b(int i) {
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        fArr[2] = fArr[2] * 1.1f;
        return Color.HSVToColor(fArr);
    }

    public static void a(View view, int i, int i2, int i3) {
        int i4 = i * 2;
        WeakReference weakReference = new WeakReference(Bitmap.createBitmap(i4, i4, Config.ARGB_8888));
        Canvas canvas = new Canvas((Bitmap) weakReference.get());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(i2);
        float f = (float) i;
        canvas.drawCircle(f, f, f, paint);
        WeakReference weakReference2 = new WeakReference(Bitmap.createBitmap(i4, i4, Config.ARGB_8888));
        Canvas canvas2 = new Canvas((Bitmap) weakReference2.get());
        Paint paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(i3);
        canvas2.drawCircle(f, f, f, paint2);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, new BitmapDrawable(view.getContext().getResources(), (Bitmap) weakReference.get()));
        stateListDrawable.addState(StateSet.WILD_CARD, new BitmapDrawable(view.getContext().getResources(), (Bitmap) weakReference2.get()));
        if (VERSION.SDK_INT >= 16) {
            view.setBackground(stateListDrawable);
        } else {
            view.setBackgroundDrawable(stateListDrawable);
        }
    }

    public static float b(float f) {
        return new BigDecimal(Float.toString(f)).setScale(2, 4).floatValue();
    }

    public static cnu a() {
        return a;
    }

    private cnu() {
    }
}
