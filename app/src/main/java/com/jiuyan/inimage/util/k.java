package com.jiuyan.inimage.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaFileService;
import com.alipay.android.phone.mobilecommon.multimedia.file.data.APFileQueryResult;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.jiuyan.inimage.bean.BeanDataPaster;
import com.jiuyan.inimage.bean.BeanPaster;
import com.jiuyan.inimage.paster.b;
import com.jiuyan.inimage.paster.g;
import com.jiuyan.inimage.paster.h;
import java.io.File;
import java.util.Random;

/* compiled from: PasterUtils */
public class k {
    public static l a(float f, float f2, g gVar) {
        float f3 = f / f2;
        Matrix matrix = new Matrix(gVar.e());
        matrix.postScale(f3, f3, gVar.a().e.x, gVar.a().e.y);
        PointF pointF = gVar.a().e;
        float f4 = pointF.x * f3;
        float f5 = f3 * pointF.y;
        l lVar = new l();
        lVar.a = gVar.f();
        lVar.b = g.a(matrix);
        lVar.c = g.b(matrix);
        lVar.d = new PointF(f4, f5);
        return lVar;
    }

    public static Matrix a(float f, float f2, float f3, float f4, float f5, float f6, Bitmap bitmap) {
        float f7 = f2 / f;
        Matrix matrix = new Matrix();
        matrix.reset();
        float f8 = f5 * f7;
        float f9 = f6 * f7;
        matrix.postTranslate(f8 - ((float) (bitmap.getWidth() / 2)), f9 - ((float) (bitmap.getHeight() / 2)));
        matrix.postScale(f3, f3, f8, f9);
        matrix.postScale(f7, f7, f8, f9);
        matrix.postRotate(f4, f8, f9);
        return matrix;
    }

    public static Matrix a(float f, float f2, float f3, float f4, float f5, float f6, int i, int i2) {
        float f7 = f2 / f;
        Matrix matrix = new Matrix();
        matrix.reset();
        float f8 = f5 * f7;
        float f9 = f6 * f7;
        matrix.postTranslate(f8 - ((float) (i / 2)), f9 - ((float) (i2 / 2)));
        matrix.postScale(f3, f3, f8, f9);
        matrix.postScale(f7, f7, f8, f9);
        matrix.postRotate(f4, f8, f9);
        return matrix;
    }

    public static l a(float f, float f2, b bVar) {
        float f3 = f / f2;
        Matrix matrix = new Matrix(bVar.e());
        matrix.postScale(f3, f3, bVar.a().e.x, bVar.a().e.y);
        PointF pointF = bVar.a().e;
        float f4 = pointF.x * f3;
        float f5 = f3 * pointF.y;
        l lVar = new l();
        lVar.a = bVar.f();
        lVar.b = g.a(matrix);
        lVar.c = g.b(matrix);
        lVar.d = new PointF(f4, f5);
        return lVar;
    }

    public static l a(float f, float f2, h hVar) {
        float f3 = f / f2;
        Matrix matrix = new Matrix(hVar.h());
        matrix.postScale(f3, f3, hVar.f().e.x, hVar.f().e.y);
        PointF pointF = hVar.f().e;
        float f4 = pointF.x * f3;
        float f5 = f3 * pointF.y;
        l lVar = new l();
        lVar.a = false;
        lVar.b = g.a(matrix);
        lVar.c = g.b(matrix);
        lVar.d = new PointF(f4, f5);
        return lVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005b, code lost:
        if (r11 != 2) goto L_0x005d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x005d, code lost:
        r0 = 0;
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0071, code lost:
        if (r11 == 3) goto L_0x001d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0093, code lost:
        if (r11 == 4) goto L_0x001d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int[] a(int r6, int r7, int r8, int r9, int r10, int r11) {
        /*
            r3 = 3
            r1 = 0
            r5 = 2
            r4 = 1
            switch(r10) {
                case 1: goto L_0x0024;
                case 2: goto L_0x003b;
                case 3: goto L_0x004c;
                case 4: goto L_0x0060;
                case 5: goto L_0x0074;
                default: goto L_0x0007;
            }
        L_0x0007:
            int r0 = r6 - r8
            int r0 = r0 / 2
            int r2 = r8 / 4
            int r2 = a(r2)
            int r2 = r2 + r0
            int r0 = r7 - r9
            int r0 = r0 / 2
            int r3 = r8 / 4
            int r3 = a(r3)
            int r0 = r0 + r3
        L_0x001d:
            int[] r3 = new int[r5]
            r3[r1] = r2
            r3[r4] = r0
            return r3
        L_0x0024:
            int r0 = r6 - r8
            int r0 = r0 / 2
            int r2 = r8 / 4
            int r2 = a(r2)
            int r2 = r2 + r0
            int r0 = r7 - r9
            int r0 = r0 / 2
            int r3 = r8 / 4
            int r3 = a(r3)
            int r0 = r0 + r3
            goto L_0x001d
        L_0x003b:
            int r2 = r6 / 2
            if (r11 != 0) goto L_0x0045
            int r2 = r2 - r8
            int r0 = r7 - r9
            int r0 = r0 / 2
            goto L_0x001d
        L_0x0045:
            if (r11 != r4) goto L_0x005d
            int r0 = r7 - r9
            int r0 = r0 / 2
            goto L_0x001d
        L_0x004c:
            int r2 = r6 / 2
            int r0 = r7 / 2
            if (r11 != 0) goto L_0x0057
            int r3 = r8 / 2
            int r2 = r2 - r3
            int r0 = r0 - r9
            goto L_0x001d
        L_0x0057:
            if (r11 != r4) goto L_0x005b
            int r2 = r2 - r8
            goto L_0x001d
        L_0x005b:
            if (r11 == r5) goto L_0x001d
        L_0x005d:
            r0 = r1
            r2 = r1
            goto L_0x001d
        L_0x0060:
            int r2 = r6 / 2
            int r0 = r7 / 2
            if (r11 != 0) goto L_0x0069
            int r2 = r2 - r8
            int r0 = r0 - r9
            goto L_0x001d
        L_0x0069:
            if (r11 != r4) goto L_0x006d
            int r0 = r0 - r9
            goto L_0x001d
        L_0x006d:
            if (r11 != r5) goto L_0x0071
            int r2 = r2 - r8
            goto L_0x001d
        L_0x0071:
            if (r11 != r3) goto L_0x005d
            goto L_0x001d
        L_0x0074:
            int r2 = r6 / 2
            int r0 = r7 / 2
            if (r11 != 0) goto L_0x0080
            int r2 = r2 - r8
            int r3 = r8 / 2
            int r2 = r2 - r3
            int r0 = r0 - r9
            goto L_0x001d
        L_0x0080:
            if (r11 != r4) goto L_0x0087
            int r3 = r8 / 2
            int r2 = r2 - r3
            int r0 = r0 - r9
            goto L_0x001d
        L_0x0087:
            if (r11 != r5) goto L_0x008e
            int r3 = r8 / 2
            int r2 = r2 + r3
            int r0 = r0 - r9
            goto L_0x001d
        L_0x008e:
            if (r11 != r3) goto L_0x0092
            int r2 = r2 - r8
            goto L_0x001d
        L_0x0092:
            r3 = 4
            if (r11 != r3) goto L_0x005d
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jiuyan.inimage.util.k.a(int, int, int, int, int, int):int[]");
    }

    public static int a(int i) {
        Random random = new Random();
        return random.nextInt(i) - random.nextInt(i);
    }

    public static Bitmap a(String str) {
        Bitmap bitmap = null;
        if (!TextUtils.isEmpty(str)) {
            Bitmap decodeFile = BitmapFactory.decodeFile(g.a + File.separator + f.a(str));
            if (decodeFile == null) {
                APFileQueryResult queryCacheFile = ((MultimediaFileService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaFileService.class.getName())).queryCacheFile(str);
                if (queryCacheFile != null && !TextUtils.isEmpty(queryCacheFile.path)) {
                    bitmap = BitmapFactory.decodeFile(queryCacheFile.path);
                }
            }
            bitmap = decodeFile;
        }
        if (bitmap == null) {
            q.a("get pasterBm fail, uri:" + str);
        }
        return bitmap;
    }

    public static BeanPaster a(BeanDataPaster beanDataPaster) {
        BeanPaster beanPaster = new BeanPaster();
        beanPaster.id = beanDataPaster.id;
        beanPaster.name = beanDataPaster.name;
        beanPaster.url = beanDataPaster.url;
        beanPaster.type = beanDataPaster.from;
        return beanPaster;
    }
}
