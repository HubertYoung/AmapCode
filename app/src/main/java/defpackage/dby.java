package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.blutils.FileUtil;
import java.io.File;
import java.io.FileOutputStream;

/* renamed from: dby reason: default package */
/* compiled from: QRCodeCreatorForAjx */
public final class dby {
    private static String a;

    /* renamed from: dby$a */
    /* compiled from: QRCodeCreatorForAjx */
    public interface a {
        void a(int i);

        void a(String str);
    }

    static {
        String appSDCardFileDir = FileUtil.getAppSDCardFileDir();
        a = appSDCardFileDir;
        if (appSDCardFileDir == null) {
            a = Environment.getExternalStorageDirectory().toString();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a);
        sb.append("/cache/qrcode/");
        a = sb.toString();
    }

    public static final void a(final String str, final int i, @NonNull final a aVar) {
        ahm.a(new Runnable() {
            final /* synthetic */ int c = 2;

            public final void run() {
                awt awt = (awt) defpackage.esb.a.a.a(awt.class);
                if (awt != null) {
                    Bitmap a2 = awt.a(str, i);
                    if (a2 == null) {
                        if ((this.c & 1) > 0) {
                            aVar.a(1);
                        }
                        if ((this.c & 2) > 0) {
                            aVar.a(2);
                        }
                    } else if ((this.c & 2) > 0) {
                        String a3 = dby.b(a2);
                        if (!TextUtils.isEmpty(a3)) {
                            aVar.a(a3);
                            return;
                        }
                        aVar.a(2);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static String b(Bitmap bitmap) {
        String str;
        if (bitmap == null) {
            return null;
        }
        File file = new File(a);
        if (file.exists()) {
            ahd.c(file);
        }
        file.mkdirs();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("qrCode.jpg");
            File file2 = new File(sb.toString());
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            str = file2.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        return str;
    }
}
