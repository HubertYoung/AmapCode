package com.alipay.mobile.binarize;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type.Builder;
import com.alipay.mobile.binarize.rs.ScriptC_localAdaptiveBinarizer;
import com.alipay.mobile.bqcscanservice.Logger;

public class LocalAdaptiveBinarizer extends Binarizer {
    private ScriptC_localAdaptiveBinarizer a = new ScriptC_localAdaptiveBinarizer(this.b);
    private RenderScript b;
    private Allocation c;
    private Allocation d;
    private Allocation e;
    private Allocation f;
    private Allocation g;
    private byte[] h;
    private int i;
    private int j;

    public LocalAdaptiveBinarizer(Context context) {
        this.b = RenderScript.create(context);
    }

    public void initialize(int width, int height) {
        if (this.i != width || this.j != height) {
            a();
            this.i = width;
            this.j = height;
            int rowSize = (int) Math.ceil((double) (((float) width) / 32.0f));
            this.h = new byte[(rowSize * height * 4)];
            Builder typeBuilder = new Builder(this.b, Element.U8(this.b)).setX(rowSize * 4).setY(height);
            this.f = Allocation.createTyped(this.b, typeBuilder.create(), 129);
            this.g = Allocation.createTyped(this.b, typeBuilder.create(), 129);
            int maxBlockX = ((width + 7) >> 3) / 8;
            int maxBlockY = ((height + 7) >> 3) / 8;
            int logblockw = 1;
            while (logblockw < 5 && (1 << logblockw) < maxBlockX) {
                logblockw++;
            }
            int logblockh = 1;
            while (logblockh < 5 && (1 << logblockh) < maxBlockY) {
                logblockh++;
            }
            int blockWidth = 1 << logblockw;
            int blockHeight = 1 << logblockh;
            int blockMatrixWidth = (int) Math.ceil(((double) width) / ((double) blockWidth));
            int blockMatrixHeight = (int) Math.ceil(((double) height) / ((double) blockHeight));
            Logger.d("LocalAdaptiveBinarizer", "blockWidth:" + blockWidth + ",blockHeight:" + blockHeight);
            this.c = Allocation.createTyped(this.b, new Builder(this.b, Element.U8(this.b)).setX(width * height).create(), 129);
            Builder typeBuilder2 = new Builder(this.b, Element.U8(this.b)).setX(blockMatrixWidth * blockMatrixHeight);
            this.d = Allocation.createTyped(this.b, typeBuilder2.create());
            this.e = Allocation.createTyped(this.b, typeBuilder2.create());
            this.a.set_gCurrentFrame(this.c);
            this.a.set_gBlockAllocation(this.d);
            this.a.set_gThresholdAllocation(this.e);
            this.a.invoke_initLocalBinarizer(width, height, blockMatrixWidth, blockMatrixHeight, blockWidth, blockHeight, 8);
        }
    }

    private void a() {
        if (this.c != null) {
            this.c.destroy();
            this.c.getType().destroy();
        }
        if (this.e != null) {
            this.e.destroy();
            this.e.getType().destroy();
        }
        if (this.d != null) {
            this.d.destroy();
            this.d.getType().destroy();
        }
        if (this.f != null) {
            this.f.destroy();
            this.f.getType().destroy();
        }
        if (this.g != null) {
            this.g.destroy();
            this.g.getType().destroy();
        }
    }

    public void destroy() {
        a();
        if (this.a != null) {
            this.a.destroy();
        }
        if (this.b != null) {
            this.b.destroy();
        }
    }

    public BinarizeResult getBinarizedData(byte[] data) {
        this.c.copyFrom(data);
        this.a.forEach_calculateBlock(this.d);
        this.b.finish();
        this.a.forEach_calculateThresholdForBlock(this.d, this.e);
        this.b.finish();
        this.a.forEach_setBlack(this.f, this.g);
        this.g.copyTo(this.h);
        this.b.finish();
        BinarizeResult binarizeResult = new BinarizeResult();
        binarizeResult.bitMatrixData = this.h;
        binarizeResult.width = this.i;
        binarizeResult.height = this.j;
        return binarizeResult;
    }
}
