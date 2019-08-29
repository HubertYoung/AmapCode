package defpackage;

import android.content.Context;
import android.provider.Settings.System;
import com.amap.bundle.drivecommon.tools.DriveUtil;

/* renamed from: rf reason: default package */
/* compiled from: LightnessControlUtil */
public final class rf {
    public static boolean a(Context context) {
        try {
            if (System.getInt(context.getContentResolver(), "screen_brightness_mode") == 1) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int b(Context context) {
        try {
            return System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void a(Context context, int i) {
        if (DriveUtil.isLightnessControlInfoShow()) {
            kj.b(context);
        }
        if (kj.a(context)) {
            try {
                System.putInt(context.getContentResolver(), "screen_brightness", i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DriveUtil.setLightnessControltInfoShow(false);
    }
}
