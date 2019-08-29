package com.jiuyan.inimage.util;

import android.content.Context;
import android.os.Environment;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import java.io.File;

/* compiled from: InFolder */
public class g {
    public static String a;

    public static void a(Context context) {
        b(context);
    }

    private static void b(Context context) {
        a = Environment.getExternalStorageDirectory().getPath() + File.separator + "in" + File.separator + ".paster";
        File file = new File(a);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(a);
        if (!file2.isDirectory()) {
            file2.delete();
            file2.mkdirs();
        }
        File file3 = new File(a);
        if (!file3.exists() || !file3.isDirectory()) {
            a = context.getDir(ImageEditService.IN_EDIT_TYPE_PASTER, 0).getAbsolutePath();
            File file4 = new File(a);
            if (!file4.exists()) {
                file4.mkdirs();
            }
            File file5 = new File(a);
            if (!file5.isDirectory()) {
                file5.delete();
                file5.mkdirs();
            }
            File file6 = new File(a);
            if (!file6.exists() || file6.isDirectory()) {
            }
        }
    }
}
