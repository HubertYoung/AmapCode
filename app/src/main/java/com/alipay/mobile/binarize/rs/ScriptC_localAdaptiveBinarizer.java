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
public class ScriptC_localAdaptiveBinarizer extends ScriptC {
    private Element a;
    private Element b;
    private Element c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private Allocation k;
    private Allocation l;
    private Allocation m;

    public ScriptC_localAdaptiveBinarizer(RenderScript rs) {
        super(rs, "localadaptivebinarizer", localAdaptiveBinarizerBitCode.getBitCode32(), localAdaptiveBinarizerBitCode.getBitCode64());
        this.b = Element.I32(rs);
        this.a = Element.ALLOCATION(rs);
        this.c = Element.U8(rs);
    }

    public synchronized void set_windowSize(int v) {
        setVar(0, v);
        this.d = v;
    }

    public int get_windowSize() {
        return this.d;
    }

    public FieldID getFieldID_windowSize() {
        return createFieldID(0, null);
    }

    public synchronized void set_blockWidth(int v) {
        setVar(1, v);
        this.e = v;
    }

    public int get_blockWidth() {
        return this.e;
    }

    public FieldID getFieldID_blockWidth() {
        return createFieldID(1, null);
    }

    public synchronized void set_blockHeight(int v) {
        setVar(2, v);
        this.f = v;
    }

    public int get_blockHeight() {
        return this.f;
    }

    public FieldID getFieldID_blockHeight() {
        return createFieldID(2, null);
    }

    public synchronized void set_blockMatrixWidth(int v) {
        setVar(3, v);
        this.g = v;
    }

    public int get_blockMatrixWidth() {
        return this.g;
    }

    public FieldID getFieldID_blockMatrixWidth() {
        return createFieldID(3, null);
    }

    public synchronized void set_blockMatrixHeight(int v) {
        setVar(4, v);
        this.h = v;
    }

    public int get_blockMatrixHeight() {
        return this.h;
    }

    public FieldID getFieldID_blockMatrixHeight() {
        return createFieldID(4, null);
    }

    public synchronized void set_width(int v) {
        setVar(5, v);
        this.i = v;
    }

    public int get_width() {
        return this.i;
    }

    public FieldID getFieldID_width() {
        return createFieldID(5, null);
    }

    public synchronized void set_height(int v) {
        setVar(6, v);
        this.j = v;
    }

    public int get_height() {
        return this.j;
    }

    public FieldID getFieldID_height() {
        return createFieldID(6, null);
    }

    public synchronized void set_gCurrentFrame(Allocation v) {
        setVar(7, v);
        this.k = v;
    }

    public Allocation get_gCurrentFrame() {
        return this.k;
    }

    public FieldID getFieldID_gCurrentFrame() {
        return createFieldID(7, null);
    }

    public synchronized void set_gBlockAllocation(Allocation v) {
        setVar(8, v);
        this.l = v;
    }

    public Allocation get_gBlockAllocation() {
        return this.l;
    }

    public FieldID getFieldID_gBlockAllocation() {
        return createFieldID(8, null);
    }

    public synchronized void set_gThresholdAllocation(Allocation v) {
        setVar(9, v);
        this.m = v;
    }

    public Allocation get_gThresholdAllocation() {
        return this.m;
    }

    public FieldID getFieldID_gThresholdAllocation() {
        return createFieldID(9, null);
    }

    public KernelID getKernelID_calculateBlock() {
        return createKernelID(1, 41, null, null);
    }

    public void forEach_calculateBlock(Allocation ain) {
        forEach_calculateBlock(ain, null);
    }

    public void forEach_calculateBlock(Allocation ain, LaunchOptions sc) {
        if (!ain.getType().getElement().isCompatible(this.c)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        forEach(1, ain, null, null, sc);
    }

    public KernelID getKernelID_calculateThresholdForBlock() {
        return createKernelID(2, 43, null, null);
    }

    public void forEach_calculateThresholdForBlock(Allocation ain, Allocation aout) {
        forEach_calculateThresholdForBlock(ain, aout, null);
    }

    public void forEach_calculateThresholdForBlock(Allocation ain, Allocation aout, LaunchOptions sc) {
        if (!ain.getType().getElement().isCompatible(this.c)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        } else if (!aout.getType().getElement().isCompatible(this.c)) {
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

    public KernelID getKernelID_setBlack() {
        return createKernelID(3, 59, null, null);
    }

    public void forEach_setBlack(Allocation ain, Allocation aout) {
        forEach_setBlack(ain, aout, null);
    }

    public void forEach_setBlack(Allocation ain, Allocation aout, LaunchOptions sc) {
        if (!ain.getType().getElement().isCompatible(this.c)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        } else if (!aout.getType().getElement().isCompatible(this.c)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        } else {
            Type t0 = ain.getType();
            Type t1 = aout.getType();
            if (t0.getCount() == t1.getCount() && t0.getX() == t1.getX() && t0.getY() == t1.getY() && t0.getZ() == t1.getZ() && t0.hasFaces() == t1.hasFaces() && t0.hasMipmaps() == t1.hasMipmaps()) {
                forEach(3, ain, aout, null, sc);
                return;
            }
            throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
        }
    }

    public InvokeID getInvokeID_initLocalBinarizer() {
        return createInvokeID(0);
    }

    public void invoke_initLocalBinarizer(int w, int h2, int blockMW, int blockMH, int blockW, int blockH, int windowS) {
        FieldPacker initLocalBinarizer_fp = new FieldPacker(28);
        initLocalBinarizer_fp.addI32(w);
        initLocalBinarizer_fp.addI32(h2);
        initLocalBinarizer_fp.addI32(blockMW);
        initLocalBinarizer_fp.addI32(blockMH);
        initLocalBinarizer_fp.addI32(blockW);
        initLocalBinarizer_fp.addI32(blockH);
        initLocalBinarizer_fp.addI32(windowS);
        invoke(0, initLocalBinarizer_fp);
    }
}
