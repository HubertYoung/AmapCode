package com.autonavi.minimap.basemap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
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
import android.os.Binder;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.CompatDialog;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.minimap.R;
import java.io.IOException;
import java.io.InputStream;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class MIUIV6Tips extends CompatDialog implements OnClickListener {
    public static final String SP_KEY_MIUI_LOCATION_SHOW_TIME = "MIUI_LOCATION_SHOW_TIME";
    public static final String SP_KEY_MIUI_V6_SHOW_TIMES = "MIUI_V6_SHOW_TIMES";
    private Activity mContext;
    ImageView mIvTipContent;
    private Bitmap mLocationGuideSetBmp;

    public static void checkToTips(Activity activity) {
        if (agf.a() && !isOpenGPSOPS(activity) && getGpsStatus(activity)) {
            int startTimes = getStartTimes();
            if (startTimes == 0 || ((startTimes > 0 && startTimes < 3 && isDayInterval()) || (startTimes >= 3 && isWeekInterval()))) {
                new MIUIV6Tips(activity).showSetOPSGuideDialog();
                saveClickTime(System.currentTimeMillis());
            }
        }
    }

    public MIUIV6Tips(Activity activity) {
        super(activity, R.style.custom_dlg);
        this.mContext = activity;
        setContentView(R.layout.miuiv6_tips_ops_location_set);
    }

    public void showSetOPSGuideDialog() {
        if (!isShowing()) {
            Resources resources = this.mContext.getResources();
            findViewById(R.id.ib_miuiv6_ops_close).setOnClickListener(this);
            Button button = (Button) findViewById(R.id.bt_miuiv6_tips_ops_location);
            button.setText(resources.getString(R.string.miuiv6_tips_but_set1));
            button.setOnClickListener(this);
            ((TextView) findViewById(R.id.tv_miuiv6_tips1)).setText(resources.getString(R.string.miuiv6_tips_not_allow_location_ops_tip1_1));
            ((TextView) findViewById(R.id.tv_miuiv6_tips2)).setText(resources.getString(R.string.miuiv6_tips_not_allow_location_ops_tip1_2));
            this.mIvTipContent = (ImageView) findViewById(R.id.iv_tip_content);
            this.mLocationGuideSetBmp = decodeBitmapForSize(this.mContext, R.drawable.miuiv6_ops_location_guide_set, (float) this.mIvTipContent.getWidth(), (float) this.mIvTipContent.getHeight(), true);
            if (this.mLocationGuideSetBmp != null) {
                this.mIvTipContent.setImageBitmap(this.mLocationGuideSetBmp);
            }
            show();
        }
    }

    public void show() {
        super.show();
        addShowTime();
    }

    public void dismiss() {
        super.dismiss();
        if (this.mIvTipContent != null) {
            Drawable drawable = this.mIvTipContent.getDrawable();
            if (drawable != null) {
                drawable.setCallback(null);
            }
            this.mIvTipContent.setImageDrawable(null);
            if (VERSION.SDK_INT >= 16) {
                this.mIvTipContent.setBackground(null);
            }
            this.mIvTipContent = null;
        }
        if (this.mLocationGuideSetBmp != null && !this.mLocationGuideSetBmp.isRecycled()) {
            this.mLocationGuideSetBmp.recycle();
            this.mLocationGuideSetBmp = null;
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.ib_miuiv6_ops_close) {
            dismiss();
            LogManager.actionLogV2("P00001", "B036");
            return;
        }
        if (view.getId() == R.id.bt_miuiv6_tips_ops_location) {
            Activity activity = this.mContext;
            String packageName = this.mContext.getPackageName();
            if (!TextUtils.isEmpty(packageName)) {
                try {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.parse("package:".concat(String.valueOf(packageName))));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dismiss();
            LogManager.actionLogV2("P00001", "B037");
        }
    }

    private Bitmap decodeBitmapForSize(Context context, int i, float f, float f2, boolean z) {
        if (i == 0) {
            return null;
        }
        InputStream openRawResource = context.getResources().openRawResource(i);
        Options bitmapOption = getBitmapOption(this.mContext, openRawResource, (int) f, (int) f2, z);
        try {
            openRawResource.reset();
        } catch (IOException e) {
            e.printStackTrace();
            openRawResource = context.getResources().openRawResource(i);
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(openRawResource, null, bitmapOption);
        if (openRawResource != null) {
            try {
                openRawResource.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return decodeStream;
    }

    private Options getBitmapOption(Context context, InputStream inputStream, int i, int i2, boolean z) {
        Options options = new Options();
        options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
        options.inScaled = true;
        if (z) {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
        }
        options.inDither = false;
        options.inPreferredConfig = Config.RGB_565;
        options.inSampleSize = 1;
        if (z) {
            int i3 = options.outWidth;
            int i4 = options.outHeight;
            if (!(i3 == 0 || i4 == 0 || i == 0 || i2 == 0)) {
                options.inSampleSize = ((i3 / i) + (i4 / i2)) / 2;
            }
        }
        options.inJustDecodeBounds = false;
        return options;
    }

    /* access modifiers changed from: private */
    public static int getStartTimes() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue(SP_KEY_MIUI_V6_SHOW_TIMES, 0);
    }

    private static boolean isDayInterval() {
        return System.currentTimeMillis() - new MapSharePreference(SharePreferenceName.SharedPreferences).getLongValue(SP_KEY_MIUI_LOCATION_SHOW_TIME, 0) > 86400000;
    }

    private static boolean isWeekInterval() {
        return System.currentTimeMillis() - new MapSharePreference(SharePreferenceName.SharedPreferences).getLongValue(SP_KEY_MIUI_LOCATION_SHOW_TIME, 0) > FileCache.EXPIRE_TIME;
    }

    private static void saveClickTime(long j) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putLongValue(SP_KEY_MIUI_LOCATION_SHOW_TIME, j);
    }

    @SuppressLint({"CommitPrefEdits"})
    private void addShowTime() {
        ahm.a(new Runnable() {
            public final void run() {
                new MapSharePreference(SharePreferenceName.SharedPreferences).putIntValue(MIUIV6Tips.SP_KEY_MIUI_V6_SHOW_TIMES, MIUIV6Tips.getStartTimes() + 1);
            }
        });
    }

    @SuppressLint({"InlinedApi"})
    @TargetApi(19)
    private static boolean isOpenGPSOPS(Context context) {
        if (VERSION.SDK_INT >= 19 && ((AppOpsManager) context.getApplicationContext().getSystemService("appops")).checkOpNoThrow("android:fine_location", Binder.getCallingUid(), context.getPackageName()) != 0) {
            return false;
        }
        return true;
    }

    private static boolean getGpsStatus(Context context) {
        try {
            return ((LocationManager) context.getSystemService("location")).isProviderEnabled(WidgetType.GPS);
        } catch (SecurityException unused) {
            return false;
        }
    }
}
