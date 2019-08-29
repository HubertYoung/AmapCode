package defpackage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.MIUIV6Tips;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: aqz reason: default package */
/* compiled from: MIUIV6Dialog */
public class aqz implements OnClickListener, cof {
    private Activity a = null;
    private ImageView b = null;
    private CompatDialog c = null;
    private Bitmap d = null;

    private void b() {
        if (!this.c.isShowing()) {
            Resources resources = this.a.getResources();
            this.c.findViewById(R.id.ib_miuiv6_ops_close).setOnClickListener(this);
            Button button = (Button) this.c.findViewById(R.id.bt_miuiv6_tips_ops_location);
            button.setText(resources.getString(R.string.miuiv6_tips_but_set1));
            button.setOnClickListener(this);
            ((TextView) this.c.findViewById(R.id.tv_miuiv6_tips1)).setText(resources.getString(R.string.miuiv6_tips_not_allow_location_ops_tip1_1));
            ((TextView) this.c.findViewById(R.id.tv_miuiv6_tips2)).setText(resources.getString(R.string.miuiv6_tips_not_allow_location_ops_tip1_2));
            this.b = (ImageView) this.c.findViewById(R.id.iv_tip_content);
            this.d = a(this.a, R.drawable.miuiv6_ops_location_guide_set, (float) this.b.getWidth(), (float) this.b.getHeight());
            if (this.d != null) {
                this.b.setImageBitmap(this.d);
            }
            c();
        }
    }

    private void c() {
        this.c.show();
        f();
    }

    private void d() {
        this.c.dismiss();
        if (this.b != null) {
            Drawable drawable = this.b.getDrawable();
            if (drawable != null) {
                drawable.setCallback(null);
            }
            this.b.setImageDrawable(null);
            if (VERSION.SDK_INT >= 16) {
                this.b.setBackground(null);
            }
            this.b = null;
        }
        if (this.d != null && !this.d.isRecycled()) {
            this.d.recycle();
            this.d = null;
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ib_miuiv6_ops_close) {
            d();
            LogManager.actionLogV2("P00001", "B036");
        } else if (view.getId() == R.id.bt_miuiv6_tips_ops_location) {
            Activity activity = this.a;
            String packageName = this.a.getPackageName();
            if (!TextUtils.isEmpty(packageName)) {
                try {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.parse("package:".concat(String.valueOf(packageName))));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            d();
            LogManager.actionLogV2("P00001", "B037");
        }
        if (view.getId() == R.id.txt_navi_error_prompt) {
            b();
            ((View) view.getTag()).setVisibility(8);
            return;
        }
        if (view.getId() == R.id.img_navi_error_close) {
            ((View) view.getTag()).setVisibility(8);
        }
    }

    private Bitmap a(Context context, int i, float f, float f2) {
        if (i == 0) {
            return null;
        }
        InputStream openRawResource = context.getResources().openRawResource(i);
        Activity activity = this.a;
        int i2 = (int) f;
        int i3 = (int) f2;
        Options options = new Options();
        options.inTargetDensity = activity.getResources().getDisplayMetrics().densityDpi;
        options.inScaled = true;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(openRawResource, null, options);
        options.inDither = false;
        options.inPreferredConfig = Config.RGB_565;
        options.inSampleSize = 1;
        int i4 = options.outWidth;
        int i5 = options.outHeight;
        if (!(i4 == 0 || i5 == 0 || i2 == 0 || i3 == 0)) {
            options.inSampleSize = ((i4 / i2) + (i5 / i3)) / 2;
        }
        options.inJustDecodeBounds = false;
        try {
            openRawResource.reset();
        } catch (IOException e) {
            e.printStackTrace();
            openRawResource = context.getResources().openRawResource(i);
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(openRawResource, null, options);
        if (openRawResource != null) {
            try {
                openRawResource.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return decodeStream;
    }

    /* access modifiers changed from: private */
    public static int e() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue(MIUIV6Tips.SP_KEY_MIUI_V6_SHOW_TIMES, 0);
    }

    @SuppressLint({"CommitPrefEdits"})
    private void f() {
        ahm.a(new Runnable() {
            public final void run() {
                new MapSharePreference(SharePreferenceName.SharedPreferences).putIntValue(MIUIV6Tips.SP_KEY_MIUI_V6_SHOW_TIMES, aqz.e() + 1);
            }
        });
    }

    private static boolean a(Context context) {
        try {
            return ((LocationManager) context.getSystemService("location")).isProviderEnabled(WidgetType.GPS);
        } catch (SecurityException unused) {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00a7, code lost:
        if ((java.lang.System.currentTimeMillis() - new com.amap.bundle.mapstorage.MapSharePreference(com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences).getLongValue(com.autonavi.minimap.basemap.MIUIV6Tips.SP_KEY_MIUI_LOCATION_SHOW_TIME, 0) > 86400000) == false) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00c5, code lost:
        if (r3 != false) goto L_0x00c7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.app.Activity r11) {
        /*
            r10 = this;
            java.lang.String r0 = android.os.Build.MODEL
            java.util.Locale r1 = java.util.Locale.getDefault()
            java.lang.String r0 = r0.toUpperCase(r1)
            java.lang.String r1 = android.os.Build.BRAND
            java.util.Locale r2 = java.util.Locale.getDefault()
            java.lang.String r1 = r1.toUpperCase(r2)
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            r3 = 0
            r4 = 1
            if (r2 != 0) goto L_0x004c
            java.lang.String r2 = "MI"
            boolean r2 = r0.contains(r2)
            if (r2 != 0) goto L_0x003a
            java.lang.String r2 = "HM"
            boolean r0 = r0.contains(r2)
            if (r0 != 0) goto L_0x003a
            boolean r0 = android.text.TextUtils.isEmpty(r1)
            if (r0 != 0) goto L_0x004c
            java.lang.String r0 = "XIAOMI"
            boolean r0 = r1.contains(r0)
            if (r0 == 0) goto L_0x004c
        L_0x003a:
            aht r0 = defpackage.aht.a()
            java.lang.String r1 = "ro.miui.ui.version.name"
            java.lang.String r0 = r0.a(r1)
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x004c
            r0 = 1
            goto L_0x004d
        L_0x004c:
            r0 = 0
        L_0x004d:
            if (r0 == 0) goto L_0x00ec
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 19
            if (r0 >= r1) goto L_0x0057
        L_0x0055:
            r0 = 1
            goto L_0x0075
        L_0x0057:
            android.content.Context r0 = r11.getApplicationContext()
            java.lang.String r1 = "appops"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.app.AppOpsManager r0 = (android.app.AppOpsManager) r0
            java.lang.String r1 = "android:fine_location"
            int r2 = android.os.Binder.getCallingUid()
            java.lang.String r5 = r11.getPackageName()
            int r0 = r0.checkOpNoThrow(r1, r2, r5)
            if (r0 != 0) goto L_0x0074
            goto L_0x0055
        L_0x0074:
            r0 = 0
        L_0x0075:
            if (r0 == 0) goto L_0x0078
            return
        L_0x0078:
            boolean r0 = a(r11)
            if (r0 == 0) goto L_0x00ec
            int r0 = e()
            if (r0 == 0) goto L_0x00c7
            r1 = 0
            r5 = 3
            if (r0 <= 0) goto L_0x00a9
            if (r0 >= r5) goto L_0x00a9
            com.amap.bundle.mapstorage.MapSharePreference r6 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r7 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r6.<init>(r7)
            java.lang.String r7 = "MIUI_LOCATION_SHOW_TIME"
            long r6 = r6.getLongValue(r7, r1)
            long r8 = java.lang.System.currentTimeMillis()
            long r8 = r8 - r6
            r6 = 86400000(0x5265c00, double:4.2687272E-316)
            int r6 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x00a6
            r6 = 1
            goto L_0x00a7
        L_0x00a6:
            r6 = 0
        L_0x00a7:
            if (r6 != 0) goto L_0x00c7
        L_0x00a9:
            if (r0 < r5) goto L_0x00ec
            com.amap.bundle.mapstorage.MapSharePreference r0 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r5 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r0.<init>(r5)
            java.lang.String r5 = "MIUI_LOCATION_SHOW_TIME"
            long r0 = r0.getLongValue(r5, r1)
            long r5 = java.lang.System.currentTimeMillis()
            long r5 = r5 - r0
            r0 = 604800000(0x240c8400, double:2.988109026E-315)
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 <= 0) goto L_0x00c5
            r3 = 1
        L_0x00c5:
            if (r3 == 0) goto L_0x00ec
        L_0x00c7:
            r10.a = r11
            com.amap.bundle.utils.ui.CompatDialog r0 = new com.amap.bundle.utils.ui.CompatDialog
            int r1 = com.autonavi.minimap.R.style.custom_dlg
            r0.<init>(r11, r1)
            r10.c = r0
            com.amap.bundle.utils.ui.CompatDialog r11 = r10.c
            int r0 = com.autonavi.minimap.R.layout.miuiv6_tips_ops_location_set
            r11.setContentView(r0)
            r10.b()
            long r0 = java.lang.System.currentTimeMillis()
            com.amap.bundle.mapstorage.MapSharePreference r11 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r2 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r11.<init>(r2)
            java.lang.String r2 = "MIUI_LOCATION_SHOW_TIME"
            r11.putLongValue(r2, r0)
        L_0x00ec:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aqz.a(android.app.Activity):void");
    }
}
