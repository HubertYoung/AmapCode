package com.jiuyan.inimage.e;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.jiuyan.inalipay.face.InNativeImgProcess;
import java.util.Map;

/* compiled from: InFaceLabel */
public class i {
    public String a = null;
    private f b = null;
    private float c = 1.0f;
    private float d = 1.0f;
    private float e = 0.0f;
    private Rect f = null;
    private float g = 0.0f;
    private float h = 0.0f;
    private float i = 0.0f;
    private float j = 0.0f;
    private int k = 0;
    private int l = 0;

    private void a(Bitmap bitmap, float f2, float f3) {
        this.k = bitmap.getWidth();
        this.l = bitmap.getHeight();
        int[] iArr = new int[4];
        InNativeImgProcess.getMeasure(bitmap, iArr);
        this.f = new Rect(iArr[0], iArr[1], iArr[2], iArr[3]);
        this.c = (this.c * ((float) this.f.width())) / ((float) bitmap.getWidth());
        this.d = (this.d * ((float) this.f.height())) / ((float) bitmap.getHeight());
        this.g = this.f.exactCenterX() - (((float) bitmap.getWidth()) / 2.0f);
        this.h = this.f.exactCenterY() - (((float) bitmap.getHeight()) / 2.0f);
        if (this.b != null) {
            return;
        }
        if (this.a.equals("Head")) {
            this.b = new l(f2, f3);
        } else if (this.a.equals("Fix")) {
            this.b = new j(f2, f3);
            e.a();
        } else if (this.a.equals("FixXY")) {
            this.b = new k();
        } else {
            this.b = new m(this.a);
        }
    }

    public i(Map<String, String> map, Map<String, Bitmap> map2) {
        float f2 = 1.0f;
        float f3 = 0.0f;
        Bitmap bitmap = map2.get(map.get("Picture"));
        if (map.containsKey("Scale")) {
            this.c = Float.parseFloat(map.get("Scale"));
            this.d = this.c;
        }
        this.a = map.get("Position");
        f3 = map.containsKey("Angle") ? Float.parseFloat(map.get("Angle")) : f3;
        f2 = map.containsKey("Length") ? Float.parseFloat(map.get("Length")) : f2;
        if (map.containsKey("ExtraAngle")) {
            this.e = Float.parseFloat(map.get("ExtraAngle"));
        }
        if (map.containsKey("X")) {
            this.i = Float.parseFloat(map.get("X"));
        }
        if (map.containsKey("Y")) {
            this.j = Float.parseFloat(map.get("Y"));
        }
        a(bitmap, f2, f3);
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public h a(c cVar, int i2, int i3, int i4) {
        float width = this.c * ((float) cVar.a()[i2].width());
        int height = (int) ((((float) this.f.height()) * width) / ((float) this.f.width()));
        g a2 = this.b.a(cVar, i2, i3, i4, (int) width, height);
        h hVar = new h();
        if (this.b instanceof k) {
            hVar.a = this.i * ((float) i3);
            hVar.b = this.j * ((float) i4);
            hVar.c = this.e;
            hVar.d = (((((float) Math.min(i3, i4)) / ((float) Math.min(this.k, this.l))) * this.c) * ((float) this.k)) / ((float) this.f.width());
        } else {
            hVar.c = a2.c + this.e;
            hVar.a = a2.a - this.g;
            hVar.b = a2.b - this.h;
            hVar.d = width / ((float) this.f.width());
        }
        return hVar;
    }
}
