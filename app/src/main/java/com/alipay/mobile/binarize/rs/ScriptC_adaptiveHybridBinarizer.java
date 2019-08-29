package com.alipay.mobile.binarize.rs;

import android.annotation.TargetApi;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.Script.FieldID;
import android.renderscript.Script.InvokeID;
import android.renderscript.Script.KernelID;
import android.renderscript.Script.LaunchOptions;
import android.renderscript.ScriptC;
import android.renderscript.Type;

@TargetApi(24)
public class ScriptC_adaptiveHybridBinarizer extends ScriptC {
    private Element a;
    private Element b;
    private Element c;
    private Element d;
    private Allocation e;
    private Allocation f;
    private Allocation g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;

    public ScriptC_adaptiveHybridBinarizer(RenderScript rs) {
        super(rs, "adaptivehybridbinarizer", adaptiveHybridBinarizerBitCode.getBitCode32(), adaptiveHybridBinarizerBitCode.getBitCode64());
        this.a = Element.ALLOCATION(rs);
        this.b = Element.I32(rs);
        this.d = Element.U8(rs);
        this.c = Element.I32_3(rs);
    }

    public synchronized void set_gCurrentFrame(Allocation v) {
        setVar(0, v);
        this.e = v;
    }

    public Allocation get_gCurrentFrame() {
        return this.e;
    }

    public FieldID getFieldID_gCurrentFrame() {
        return createFieldID(0, null);
    }

    public synchronized void set_gTempAverageFrame(Allocation v) {
        setVar(1, v);
        this.f = v;
    }

    public Allocation get_gTempAverageFrame() {
        return this.f;
    }

    public FieldID getFieldID_gTempAverageFrame() {
        return createFieldID(1, null);
    }

    public synchronized void set_gAverageFrame(Allocation v) {
        setVar(2, v);
        this.g = v;
    }

    public Allocation get_gAverageFrame() {
        return this.g;
    }

    public FieldID getFieldID_gAverageFrame() {
        return createFieldID(2, null);
    }

    public synchronized void set_gMinDynamicRange(int v) {
        setVar(3, v);
        this.h = v;
    }

    public int get_gMinDynamicRange() {
        return this.h;
    }

    public FieldID getFieldID_gMinDynamicRange() {
        return createFieldID(3, null);
    }

    public synchronized void set_gWidth(int v) {
        setVar(4, v);
        this.i = v;
    }

    public int get_gWidth() {
        return this.i;
    }

    public FieldID getFieldID_gWidth() {
        return createFieldID(4, null);
    }

    public synchronized void set_gHeight(int v) {
        setVar(5, v);
        this.j = v;
    }

    public int get_gHeight() {
        return this.j;
    }

    public FieldID getFieldID_gHeight() {
        return createFieldID(5, null);
    }

    public synchronized void set_gBlockSize(int v) {
        setVar(6, v);
        this.k = v;
    }

    public int get_gBlockSize() {
        return this.k;
    }

    public FieldID getFieldID_gBlockSize() {
        return createFieldID(6, null);
    }

    public synchronized void set_gSubWidth(int v) {
        setVar(7, v);
        this.l = v;
    }

    public int get_gSubWidth() {
        return this.l;
    }

    public FieldID getFieldID_gSubWidth() {
        return createFieldID(7, null);
    }

    public synchronized void set_gSubHeight(int v) {
        setVar(8, v);
        this.m = v;
    }

    public int get_gSubHeight() {
        return this.m;
    }

    public FieldID getFieldID_gSubHeight() {
        return createFieldID(8, null);
    }

    public synchronized void set_gCount(int v) {
        setVar(9, v);
        this.n = v;
    }

    public int get_gCount() {
        return this.n;
    }

    public FieldID getFieldID_gCount() {
        return createFieldID(9, null);
    }

    public KernelID getKernelID_deNoiseByAverage() {
        return createKernelID(1, 59, null, null);
    }

    public void forEach_deNoiseByAverage(Allocation ain, Allocation aout) {
        forEach_deNoiseByAverage(ain, aout, null);
    }

    public void forEach_deNoiseByAverage(Allocation ain, Allocation aout, LaunchOptions sc) {
        if (!ain.getType().getElement().isCompatible(this.d)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        } else if (!aout.getType().getElement().isCompatible(this.d)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        } else {
            Type t0 = ain.getType();
            Type t1 = aout.getType();
            if (t0.getCount() == t1.getCount() && t0.getX() == t1.getX() && t0.getY() == t1.getY() && t0.getZ() == t1.getZ() && t0.hasFaces() == t1.hasFaces() && t0.hasMipmaps() == t1.hasMipmaps()) {
                forEach(1, ain, aout, null, sc);
                return;
            }
            throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
        }
    }

    public KernelID getKernelID_setBlack() {
        return createKernelID(2, 59, null, null);
    }

    public void forEach_setBlack(Allocation ain, Allocation aout) {
        forEach_setBlack(ain, aout, null);
    }

    public void forEach_setBlack(Allocation ain, Allocation aout, LaunchOptions sc) {
        if (!ain.getType().getElement().isCompatible(this.d)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        } else if (!aout.getType().getElement().isCompatible(this.d)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        } else {
            Type t0 = ain.getType();
            Type t1 = aout.getType();
            if (t0.getCount() == t1.getCount() && t0.getX() == t1.getX() && t0.getY() == t1.getY() && t0.getZ() == t1.getZ() && t0.hasFaces() == t1.hasFaces() && t0.hasMipmaps() == t1.hasMipmaps()) {
                forEach(2, ain, aout, null, sc);
                return;
            }
            throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
        }
    }

    public KernelID getKernelID_calAverage() {
        return createKernelID(3, 57, null, null);
    }

    public void forEach_calAverage(Allocation ain) {
        forEach_calAverage(ain, null);
    }

    public void forEach_calAverage(Allocation ain, LaunchOptions sc) {
        if (!ain.getType().getElement().isCompatible(this.d)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        forEach(3, ain, null, null, sc);
    }

    public KernelID getKernelID_calThreshold() {
        return createKernelID(4, 57, null, null);
    }

    public void forEach_calThreshold(Allocation ain) {
        forEach_calThreshold(ain, null);
    }

    public void forEach_calThreshold(Allocation ain, LaunchOptions sc) {
        if (!ain.getType().getElement().isCompatible(this.c)) {
            throw new RSRuntimeException("Type mismatch with I32_3!");
        }
        forEach(4, ain, null, null, sc);
    }

    public InvokeID getInvokeID_initSize() {
        return createInvokeID(0);
    }

    public void invoke_initSize(int width, int height, int blockSize, int minDynamicRange) {
        FieldPacker initSize_fp = new FieldPacker(16);
        initSize_fp.addI32(width);
        initSize_fp.addI32(height);
        initSize_fp.addI32(blockSize);
        initSize_fp.addI32(minDynamicRange);
        invoke(0, initSize_fp);
    }
}
