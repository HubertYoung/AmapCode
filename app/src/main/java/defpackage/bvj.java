package defpackage;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.album.utils.CommonUtils$2;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.AlertView;
import com.autonavi.widget.ui.AlertView.a;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/* renamed from: bvj reason: default package */
/* compiled from: CommonUtils */
public final class bvj {
    private static String a;
    private static File b;

    public static String a(long j) {
        new Date().setTime(j);
        return new SimpleDateFormat("yyyy/M/d", Locale.getDefault()).format(Long.valueOf(j));
    }

    public static String b(long j) {
        new Date().setTime(j);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Long.valueOf(j));
    }

    public static boolean a(String str) {
        String c = c(str);
        if (TextUtils.isEmpty(c)) {
            return false;
        }
        return new File(cby.c, c).exists();
    }

    public static String b(String str) {
        String c = c(str);
        if (TextUtils.isEmpty(c)) {
            return null;
        }
        return new File(cby.c, c).getAbsolutePath();
    }

    public static String c(String str) {
        String[] split = new File(str).getName().split("\\.");
        if (split.length < 2) {
            return null;
        }
        StringBuilder sb = new StringBuilder(".");
        sb.append(split[0]);
        sb.append("_temp.thumb");
        return sb.toString();
    }

    public static void a(AbstractBasePage abstractBasePage, cao cao, Intent intent, int i, cah cah) {
        Map<String, Object> a2 = adu.a(intent);
        if (cah != null) {
            String str = (String) a2.get("camera_pic_path");
            if (!TextUtils.isEmpty(str)) {
                boolean z = true;
                switch (i) {
                    case 4:
                        b = new File(str);
                        String str2 = cah.d;
                        if (Environment.getExternalStorageState().equals("mounted")) {
                            File file = new File(cby.c);
                            if (!file.exists()) {
                                file.mkdir();
                            }
                        } else {
                            z = false;
                        }
                        if (z && b != null) {
                            a = b.getAbsolutePath();
                            Intent intent2 = new Intent();
                            Uri fromFile = Uri.fromFile(b);
                            intent2.setAction("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                            intent2.setData(fromFile);
                            DoNotUseTool.getContext().sendBroadcast(intent2);
                            b = null;
                            bid pageContext = AMapPageUtil.getPageContext();
                            if (pageContext != null && !TextUtils.isEmpty(a)) {
                                PageBundle pageBundle = new PageBundle();
                                pageBundle.putObject("real_scene_photo_path", a);
                                pageBundle.putObject("activity_tip", cao);
                                pageContext.startPageForResult(str2, pageBundle, 110);
                                break;
                            }
                        }
                    case 5:
                        PageBundle pageBundle2 = new PageBundle();
                        pageBundle2.putString("CAMERA_RESULT_PHOTO_PATH", str);
                        pageBundle2.putInt("COMMENT_REQUEST_CODE", 5);
                        abstractBasePage.startPageForResult(cah.d, pageBundle2, 1);
                        return;
                    case 6:
                        PageBundle pageBundle3 = new PageBundle();
                        pageBundle3.putString("CAMERA_RESULT_PHOTO_PATH", str);
                        pageBundle3.putInt("COMMENT_REQUEST_CODE", 6);
                        abstractBasePage.startPageForResult(cah.d, pageBundle3, 2);
                        return;
                }
            }
        }
    }

    public static void a(Activity activity, int i) {
        try {
            StringBuilder sb = new StringBuilder("autonavi");
            sb.append(File.separator);
            sb.append("realscene");
            adu.a(sb.toString(), activity, i, new CommonUtils$2());
        } catch (ActivityNotFoundException unused) {
        }
    }

    public static GeoPoint a(double d, double d2) {
        Point a2 = cfg.a(d2, d);
        return cff.a(a2.x, a2.y);
    }

    public static void a(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public static Bitmap a(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        if (i != 0) {
            matrix.postRotate((float) i);
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        try {
            return Bitmap.createBitmap(bitmap, (width - Math.min(width, height)) / 2, (height - Math.min(width, height)) / 2, Math.min(width, height), Math.min(width, height), matrix, true);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void a(AbstractBasePage abstractBasePage, String str, cao cao, String str2) {
        if (abstractBasePage != null && !TextUtils.isEmpty(str)) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("real_scene_photo_path", str);
            pageBundle.putObject("activity_tip", cao);
            abstractBasePage.startPageForResult(str2, pageBundle, 110);
        }
    }

    public static void a() {
        StringBuilder sb = new StringBuilder();
        sb.append(FileUtil.getAppSDCardFileDir());
        sb.append(File.separator);
        sb.append("temp_dir/");
        final File file = new File(sb.toString());
        if (file.isDirectory()) {
            ahn.b().a(new Runnable() {
                public final void run() {
                    ahd.a(file);
                }
            }, 3);
        }
    }

    public static void a(final AbstractBasePage abstractBasePage, boolean z, final int i) {
        if (abstractBasePage != null) {
            if (!z || LocationInstrument.getInstance().isProviderEnabled(Provider.PROVIDER_GPS)) {
                kj.a(abstractBasePage.getActivity(), new String[]{"android.permission.CAMERA"}, (b) new b() {
                    public final void run() {
                        bvj.a(abstractBasePage.getActivity(), i);
                    }
                });
                return;
            }
            if (z && abstractBasePage != null) {
                final Activity activity = abstractBasePage.getActivity();
                a aVar = new a(abstractBasePage.getActivity());
                aVar.a(R.string.real_scene_gps_tip);
                aVar.a(R.string.audio_guide_set_gps, (a) new a() {
                    public final void onClick(AlertView alertView, int i) {
                        try {
                            Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                            intent.setFlags(268435456);
                            activity.startActivityForResult(intent, 4098);
                        } catch (ActivityNotFoundException unused) {
                            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.life_common_dlg_open_setting_failed));
                        } catch (SecurityException unused2) {
                            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.life_common_dlg_open_setting_failed));
                        }
                    }
                });
                aVar.b = new a() {
                    public final void onClick(AlertView alertView, int i) {
                    }
                };
                aVar.c = new a() {
                    public final void onClick(AlertView alertView, int i) {
                    }
                };
                aVar.a(true);
                AlertView a2 = aVar.a();
                abstractBasePage.showViewLayer(a2);
                a2.startAnimation();
            }
        }
    }
}
