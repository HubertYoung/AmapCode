package com.alipay.mobile.binarize;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type.Builder;
import com.alipay.mobile.binarize.rs.ScriptC_hybridStdBinarizer;
import com.alipay.mobile.bqcscanservice.Logger;

public class HybridStdBinarizer extends Binarizer {
    private RenderScript a;
    private ScriptC_hybridStdBinarizer b = new ScriptC_hybridStdBinarizer(this.a);
    private Allocation c;
    private Allocation d;
    private Allocation e;
    private Allocation f;
    private byte[] g;
    private byte[] h;
    private int i;
    private int j;

    public HybridStdBinarizer(Context context) {
        this.a = RenderScript.create(context);
    }

    public void initialize(int width, int height) {
        if (this.i != width || this.j != height) {
            a();
            this.i = width;
            this.j = height;
            int rowSizeInWord = (int) Math.ceil((double) (((float) width) / 32.0f));
            int bitMatrixLength = rowSizeInWord * height * 4;
            Logger.d("HybridStdBinarizer", "bitMatrixLength is " + bitMatrixLength);
            this.h = new byte[bitMatrixLength];
            this.f = Allocation.createTyped(this.a, new Builder(this.a, Element.U8(this.a)).setX(rowSizeInWord * 4).setY(height).create(), 129);
            int subWidth = width >> 3;
            int subHeight = height >> 3;
            this.g = new byte[(subWidth * subHeight)];
            Builder typeBuilder = new Builder(this.a, Element.U8(this.a)).setX(subWidth * subHeight);
            this.c = Allocation.createTyped(this.a, typeBuilder.create());
            this.d = Allocation.createTyped(this.a, typeBuilder.create());
            this.e = Allocation.createTyped(this.a, new Builder(this.a, Element.U8(this.a)).setX(width * height).create(), 129);
            this.b.set_gCurrentFrame(this.e);
            this.b.set_gAverageBlockAllocation(this.c);
            this.b.set_gTypeAllocation(this.d);
            this.b.set_gBitMatrixAllocation(this.f);
            this.b.invoke_initBinarizer(width, height, 25, 3, rowSizeInWord * 4);
        }
    }

    private void a() {
        if (this.f != null) {
            this.f.destroy();
            this.f.getType().destroy();
        }
        if (this.c != null) {
            this.c.destroy();
            this.c.getType().destroy();
        }
        if (this.d != null) {
            this.d.destroy();
            this.d.getType().destroy();
        }
        if (this.e != null) {
            this.e.destroy();
            this.e.getType().destroy();
        }
    }

    public void destroy() {
        a();
        if (this.b != null) {
            this.b.destroy();
        }
        if (this.a != null) {
            this.a.destroy();
        }
    }

    public BinarizeResult getBinarizedData(byte[] data) {
        this.e.copyFrom(data);
        this.b.forEach_calAverage(this.c);
        this.a.finish();
        this.c.copyTo(this.g);
        this.b.set_avgSum(this.b.reduce_produceAverage(this.g).get());
        this.a.finish();
        this.b.forEach_setBlack(this.c);
        this.f.copyTo(this.h);
        this.a.finish();
        BinarizeResult binarizeResult = new BinarizeResult();
        binarizeResult.bitMatrixData = this.h;
        binarizeResult.width = this.i;
        binarizeResult.height = this.j;
        return binarizeResult;
    }
}
