package com.alipay.mobile.binarize;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type.Builder;
import com.alipay.mobile.binarize.rs.ScriptC_adaptiveHybridBinarizer;

public class AdaptiveHybridBinarizer extends Binarizer {
    private boolean a = true;
    private boolean b = false;
    private Allocation c;
    private Allocation d;
    private Allocation e;
    private Allocation f;
    private byte[] g;
    private byte[] h;
    private Allocation i;
    private Allocation j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int[] o;
    private ScriptC_adaptiveHybridBinarizer p;
    private RenderScript q;

    public AdaptiveHybridBinarizer(Context context) {
        this.q = RenderScript.create(context);
        this.p = new ScriptC_adaptiveHybridBinarizer(this.q);
    }

    public void initialize(int width, int height) {
        if (this.k != width || this.l != height) {
            a();
            this.k = width;
            this.l = height;
            int rowSize = (int) Math.ceil((double) (((float) width) / 32.0f));
            this.h = new byte[(rowSize * height * 4)];
            Builder typeBuilder = new Builder(this.q, Element.U8(this.q)).setX(rowSize * 4).setY(height);
            this.e = Allocation.createTyped(this.q, typeBuilder.create(), 129);
            this.f = Allocation.createTyped(this.q, typeBuilder.create(), 129);
            this.g = new byte[(width * height)];
            Builder typeBuilder2 = new Builder(this.q, Element.U8(this.q)).setX(width).setY(height);
            this.i = Allocation.createTyped(this.q, typeBuilder2.create(), 129);
            this.j = Allocation.createTyped(this.q, typeBuilder2.create(), 129);
            this.m = ((width + 8) - 1) / 8;
            this.n = ((height + 8) - 1) / 8;
            this.c = Allocation.createTyped(this.q, new Builder(this.q, Element.I32_3(this.q)).setX(this.m).setY(this.n).create());
            this.d = Allocation.createTyped(this.q, new Builder(this.q, Element.U8(this.q)).setX(this.m).setY(this.n).create());
            this.p.invoke_initSize(width, height, 8, 24);
            this.p.set_gCurrentFrame(this.i);
            this.p.set_gTempAverageFrame(this.c);
            this.p.set_gAverageFrame(this.d);
            this.o = new int[(this.m * this.n * 4)];
        }
    }

    private void a() {
        if (this.e != null) {
            this.e.destroy();
            this.e.getType().destroy();
        }
        if (this.f != null) {
            this.f.destroy();
            this.f.getType().destroy();
        }
        if (this.d != null) {
            this.d.destroy();
            this.d.getType().destroy();
        }
        if (this.j != null) {
            this.j.destroy();
            this.j.getType().destroy();
        }
        if (this.i != null) {
            this.i.destroy();
            this.i.getType().destroy();
        }
        if (this.c != null) {
            this.c.destroy();
            this.c.getType().destroy();
        }
    }

    public void destroy() {
        a();
        if (this.p != null) {
            this.p.destroy();
        }
        if (this.q != null) {
            this.q.destroy();
        }
    }

    public void setDeNoiseByAvg(boolean deNoiseByAvg) {
        this.b = deNoiseByAvg;
    }

    public void setPreferWhite(boolean preferWhite) {
        this.a = preferWhite;
    }

    public BinarizeResult getBinarizedData(byte[] data) {
        int average;
        this.i.copyFrom(data);
        if (this.b) {
            this.p.forEach_deNoiseByAverage(this.i, this.j);
            this.j.copyTo(this.g);
            this.i.copyFrom(this.g);
            this.q.finish();
            this.p.forEach_calAverage(this.d);
        } else {
            this.p.set_gCurrentFrame(this.i);
            this.p.forEach_calAverage(this.d);
        }
        this.q.finish();
        this.c.copyTo(this.o);
        this.q.finish();
        for (int y = 0; y < this.n; y++) {
            for (int x = 0; x < this.m; x++) {
                int offset = ((this.m * y) + x) * 4;
                int minVal = this.o[offset + 1];
                int maxVal = this.o[offset + 2];
                if (maxVal - minVal <= 24) {
                    if (this.a) {
                        average = minVal >> 1;
                    } else {
                        average = minVal + ((maxVal - minVal) >> 1);
                    }
                    if (y > 0 && x > 0) {
                        int temp = (((y - 1) * this.m) + x) - 1;
                        int neibScore = ((this.o[(temp + 1) * 4] + (this.o[(this.m + temp) * 4] << 1)) + this.o[temp * 4]) >> 2;
                        if (minVal < neibScore) {
                            average = neibScore;
                        }
                    }
                    this.o[offset] = average;
                }
            }
        }
        this.c.copyFrom(this.o);
        this.p.forEach_calThreshold(this.c);
        this.q.finish();
        this.p.forEach_setBlack(this.e, this.f);
        this.f.copyTo(this.h);
        this.q.finish();
        BinarizeResult binarizeResult = new BinarizeResult();
        binarizeResult.bitMatrixData = this.h;
        binarizeResult.width = this.k;
        binarizeResult.height = this.l;
        return binarizeResult;
    }
}
