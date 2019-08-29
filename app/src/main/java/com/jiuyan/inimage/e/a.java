package com.jiuyan.inimage.e;

import android.graphics.Point;
import android.graphics.Rect;
import com.jiuyan.inimage.util.q;

/* compiled from: AliPayInfo */
public class a extends c {
    private float[] a = null;
    private Rect[] b = null;
    private int c = 0;

    public a(int[][] iArr) {
        this.a = new float[(iArr.length * 36)];
        for (int i = 0; i < iArr.length; i++) {
            if (iArr[i] == null || iArr[i].length != 36) {
                q.a((String) "facePoint count is invalid");
            } else {
                for (int i2 = 0; i2 < iArr[i].length; i2++) {
                    this.a[(i * 36) + i2] = (float) iArr[i][i2];
                }
            }
        }
        if (this.a != null) {
            this.c = this.a.length / 36;
            this.b = e();
        }
    }

    private Point[] a(int[] iArr) {
        if (this.c == 0) {
            q.a("calcTransDataForFaces", "faceRes.length==0");
            q.a("calcTransDataForFaces", "faceRes.length==0");
            return null;
        }
        Point[] pointArr = new Point[this.c];
        for (int i = 0; i < this.c; i++) {
            int i2 = 0;
            int i3 = 0;
            for (int i4 : iArr) {
                i3 += (int) this.a[(i * 36) + (i4 * 2)];
                i2 += (int) this.a[(i4 * 2) + (i * 36) + 1];
            }
            pointArr[i] = new Point(i3 / iArr.length, i2 / iArr.length);
        }
        return pointArr;
    }

    public Point[] a(int i) {
        if (this.c == 0) {
            return null;
        }
        Point[] pointArr = new Point[this.c];
        switch (i) {
            case 0:
                return a(d.am);
            case 1:
                return a(d.an);
            case 2:
                return a(d.ao);
            case 3:
                return a(d.ap);
            case 4:
                return a(d.aq);
            case 5:
                return a(d.ar);
            case 6:
                return a(d.as);
            case 7:
                for (int i2 = 0; i2 < this.c; i2++) {
                    pointArr[i2] = new Point((int) this.a[(i2 * 36) + (d.at[0] * 2)], ((int) (0.8f * ((float) Math.abs(((int) this.a[(i2 * 36) + (d.at[2] * 2)]) - ((int) this.a[(i2 * 36) + (d.at[1] * 2)]))))) + ((int) this.a[(i2 * 36) + (d.at[0] * 2) + 1]));
                }
                return pointArr;
            case 8:
                return a(d.au);
            case 9:
                for (int i3 = 0; i3 < this.c; i3++) {
                    pointArr[i3] = new Point((int) this.a[(i3 * 36) + (d.av[1] * 2)], (int) (((1.0f - 0.75f) * ((float) ((int) this.a[(i3 * 36) + (d.av[0] * 2) + 1]))) + (((float) ((int) this.a[(i3 * 36) + (d.av[1] * 2) + 1])) * 0.75f)));
                }
                return pointArr;
            case 10:
                return a(d.aw);
            case 11:
                return a(d.ax);
            case 12:
                return a(d.ay);
            case 13:
                return a(d.az);
            case 14:
                return a(d.aA);
            case 15:
                return a(d.aB);
            case 16:
                for (int i4 = 0; i4 < this.c; i4++) {
                    pointArr[i4] = new Point((int) this.a[(i4 * 36) + (d.aC[1] * 2)], (((int) this.a[((i4 * 36) + (d.aC[1] * 2)) + 1]) * 2) - ((int) this.a[((i4 * 36) + (d.aC[0] * 2)) + 1]));
                }
                return pointArr;
            case 17:
                return a(d.aD);
            case 18:
                return a(d.aE);
            case 19:
                return a(d.aF);
            case 20:
                for (int i5 = 0; i5 < this.c; i5++) {
                    pointArr[i5] = new Point((((int) this.a[(i5 * 36) + (d.aG[0] * 2)]) + ((int) this.a[(i5 * 36) + (d.aG[1] * 2)])) / 2, (int) this.a[(i5 * 36) + (d.aG[2] * 2) + 1]);
                }
                return pointArr;
            case 21:
                for (int i6 = 0; i6 < this.c; i6++) {
                    pointArr[i6] = new Point((((int) this.a[(i6 * 36) + (d.aH[0] * 2)]) + ((int) this.a[(i6 * 36) + (d.aH[1] * 2)])) / 2, (int) this.a[(i6 * 36) + (d.aH[2] * 2) + 1]);
                }
                return pointArr;
            case 22:
                return a(d.aI);
            case 23:
                return a(d.aJ);
            case 24:
                return a(d.aK);
            case 25:
                return a(d.aL);
            default:
                return pointArr;
        }
    }

    private Rect[] e() {
        if (this.c == 0) {
            return null;
        }
        Rect[] rectArr = new Rect[this.c];
        for (int i = 0; i < this.c; i++) {
            int i2 = -1;
            int i3 = Integer.MAX_VALUE;
            int i4 = -1;
            int i5 = Integer.MAX_VALUE;
            for (int i6 = 0; i6 < 18; i6++) {
                if (this.a[(i * 36) + (i6 * 2)] > ((float) i4)) {
                    i4 = (int) this.a[(i * 36) + (i6 * 2)];
                }
                if (this.a[(i * 36) + (i6 * 2)] < ((float) i5)) {
                    i5 = (int) this.a[(i * 36) + (i6 * 2)];
                }
                if (this.a[(i * 36) + (i6 * 2) + 1] < ((float) i3)) {
                    i3 = (int) this.a[(i * 36) + (i6 * 2) + 1];
                }
                if (this.a[(i * 36) + (i6 * 2) + 1] > ((float) i2)) {
                    i2 = (int) this.a[(i * 36) + (i6 * 2) + 1];
                }
            }
            rectArr[i] = new Rect(i5, i3, i4, i2);
        }
        return rectArr;
    }

    public Rect[] a() {
        if (this.c == 0) {
            return null;
        }
        return this.b;
    }

    public b[] b() {
        if (this.c == 0) {
            return null;
        }
        b[] bVarArr = new b[this.c];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.c) {
                return bVarArr;
            }
            Point point = a(3)[i2];
            Point point2 = a(4)[i2];
            Point point3 = a(15)[i2];
            Point point4 = a(6)[i2];
            float f = (float) (point2.x - point.x);
            float f2 = (float) (point2.y - point.y);
            float f3 = (float) point4.x;
            float f4 = (float) point4.y;
            float f5 = ((float) point3.y) - f4;
            bVarArr[i2] = new b(f3, f4, ((float) Math.sqrt((double) (((((float) point3.x) - f3) * (((float) point3.x) - f3)) + (f5 * (((float) point3.y) - f4))))) * 1.0f, (((float) Math.sqrt((double) ((f2 * f2) + (f * f)))) / 2.0f) * 1.9f, (float) ((Math.atan((double) (f2 / f)) / 3.141592653589793d) * 180.0d));
            i = i2 + 1;
        }
    }

    public int c() {
        return this.c;
    }

    public void d() {
    }
}
