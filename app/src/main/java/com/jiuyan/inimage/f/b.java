package com.jiuyan.inimage.f;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inimage.bean.BeanAKeyUseLocation;
import com.jiuyan.inimage.bean.BeanPaster;
import com.jiuyan.inimage.paster.ViewOperation;
import com.jiuyan.inimage.paster.a;
import com.jiuyan.inimage.paster.d;
import com.jiuyan.inimage.paster.f;
import com.jiuyan.inimage.paster.g;
import com.jiuyan.inimage.util.k;
import java.util.List;

/* compiled from: CoreLayerDecor */
public abstract class b<RootView extends ViewOperation, Attachment> extends a<RootView, Attachment> {
    protected Context b;
    protected d c;
    f d = new c(this);

    public b(Context context, d dVar) {
        this.b = context;
        this.c = dVar;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public boolean a() {
        List d2 = d();
        if (d2 == null || d2.size() <= 0) {
            return false;
        }
        return true;
    }

    public int b() {
        int i = -1;
        for (int i2 = 0; i2 < ((ViewOperation) this.a).getObjects().size(); i2++) {
            i = i2;
        }
        return i + 1;
    }

    /* access modifiers changed from: protected */
    public void a(List<BeanPaster> list, boolean z) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                BeanPaster beanPaster = list.get(i);
                Bitmap a = k.a(beanPaster.url);
                if (a != null) {
                    int[] a2 = k.a(this.c.a()[3], this.c.a()[2], a.getWidth(), a.getHeight(), list.size(), i);
                    if ("diy".equals(beanPaster.type)) {
                        b(a2[0], a2[1], beanPaster, a, z);
                    } else {
                        a(a2[0], a2[1], beanPaster, a, z);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public g a(int i, int i2, BeanPaster beanPaster, Bitmap bitmap, boolean z) {
        g gVar = new g(this.b, bitmap);
        gVar.a(beanPaster.id);
        gVar.c(beanPaster.url);
        gVar.b(beanPaster.fromWhere);
        Matrix a = a(beanPaster.location, (float) bitmap.getWidth(), (float) bitmap.getHeight(), (float) this.c.a()[3], (float) this.c.a()[2]);
        if (a == null) {
            a = new Matrix();
            a.reset();
            a.postTranslate((float) i, (float) i2);
        }
        boolean z2 = false;
        if (beanPaster.location != null) {
            z2 = beanPaster.location.f;
        }
        gVar.a(a, z2);
        gVar.a((a) this.a);
        ((ViewOperation) this.a).a(gVar, b(), z);
        gVar.a(this.d);
        return gVar;
    }

    /* access modifiers changed from: protected */
    public d b(int i, int i2, BeanPaster beanPaster, Bitmap bitmap, boolean z) {
        com.jiuyan.inimage.paster.b bVar = new com.jiuyan.inimage.paster.b(this.b, bitmap);
        bVar.a(beanPaster.id);
        bVar.b(beanPaster.url);
        Matrix a = a(beanPaster.location, (float) bitmap.getWidth(), (float) bitmap.getHeight(), (float) this.c.a()[3], (float) this.c.a()[2]);
        if (a == null) {
            a = new Matrix();
            a.reset();
            a.postTranslate((float) i, (float) i2);
        }
        boolean z2 = false;
        if (beanPaster.location != null) {
            z2 = beanPaster.location.f;
        }
        bVar.a(a, z2);
        bVar.a((a) this.a);
        ((ViewOperation) this.a).a(bVar, b(), z);
        bVar.a(this.d);
        return bVar;
    }

    /* access modifiers changed from: protected */
    public g a(int i, int i2, BeanPaster beanPaster, Bitmap bitmap) {
        g gVar = new g(this.b, bitmap);
        gVar.a(beanPaster.id);
        gVar.c(beanPaster.url);
        gVar.b(beanPaster.type);
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.postTranslate((float) i, (float) i2);
        gVar.a(matrix, false);
        gVar.a((a) this.a);
        ((ViewOperation) this.a).a(gVar, b(), true);
        gVar.a(this.d);
        return gVar;
    }

    public void c() {
        ((ViewOperation) this.a).d();
    }

    public Matrix a(BeanAKeyUseLocation beanAKeyUseLocation, float f, float f2, float f3, float f4) {
        if (!(beanAKeyUseLocation == null || beanAKeyUseLocation.rect == null)) {
            try {
                String[] split = beanAKeyUseLocation.rect.split(",");
                String str = split[0];
                String str2 = split[1];
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    Matrix matrix = new Matrix();
                    matrix.reset();
                    float floatValue = Float.valueOf(str).floatValue() * f3;
                    float floatValue2 = Float.valueOf(str2).floatValue() * f4;
                    beanAKeyUseLocation.centerX = floatValue;
                    beanAKeyUseLocation.centerY = floatValue2;
                    matrix.postTranslate(floatValue - (f / 2.0f), floatValue2 - (f2 / 2.0f));
                    if (beanAKeyUseLocation.s != null) {
                        float floatValue3 = Float.valueOf(beanAKeyUseLocation.s).floatValue();
                        matrix.postScale(floatValue3, floatValue3, floatValue, floatValue2);
                    }
                    if (beanAKeyUseLocation.r == null) {
                        return matrix;
                    }
                    matrix.postRotate(Float.valueOf(beanAKeyUseLocation.r).floatValue(), floatValue, floatValue2);
                    return matrix;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
