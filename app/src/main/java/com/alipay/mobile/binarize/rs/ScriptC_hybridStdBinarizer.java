package com.alipay.mobile.binarize.rs;

import android.annotation.TargetApi;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.Script.FieldID;
import android.renderscript.Script.InvokeID;
import android.renderscript.Script.KernelID;
import android.renderscript.Script.LaunchOptions;
import android.renderscript.ScriptC;

@TargetApi(24)
public class ScriptC_hybridStdBinarizer extends ScriptC {
    private Element a;
    private Element b;
    private Element c;
    private RenderScript d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private Allocation p;
    private Allocation q;
    private Allocation r;
    private Allocation s;

    public class result_int {
        /* access modifiers changed from: private */
        public Allocation[] a;
        private Allocation b;
        private boolean c;
        private int d;

        /* synthetic */ result_int(Allocation x0, byte b2) {
            this(x0);
        }

        public int get() {
            if (!this.c) {
                int[] outArray = new int[1];
                this.b.copyTo(outArray);
                this.d = outArray[0];
                this.b.destroy();
                this.b = null;
                if (this.a != null) {
                    for (Allocation destroy : this.a) {
                        destroy.destroy();
                    }
                    this.a = null;
                }
                this.c = true;
            }
            return this.d;
        }

        private result_int(Allocation out) {
            this.a = null;
            this.b = out;
            this.c = false;
        }
    }

    public ScriptC_hybridStdBinarizer(RenderScript rs) {
        super(rs, "hybridstdbinarizer", hybridStdBinarizerBitCode.getBitCode32(), hybridStdBinarizerBitCode.getBitCode64());
        this.b = Element.I32(rs);
        this.a = Element.ALLOCATION(rs);
        this.d = rs;
        this.c = Element.U8(rs);
    }

    public synchronized void set_log_nBlockSize(int v) {
        setVar(0, v);
        this.e = v;
    }

    public int get_log_nBlockSize() {
        return this.e;
    }

    public FieldID getFieldID_log_nBlockSize() {
        return createFieldID(0, null);
    }

    public synchronized void set_nBlockSize(int v) {
        setVar(1, v);
        this.f = v;
    }

    public int get_nBlockSize() {
        return this.f;
    }

    public FieldID getFieldID_nBlockSize() {
        return createFieldID(1, null);
    }

    public synchronized void set_width(int v) {
        setVar(2, v);
        this.g = v;
    }

    public int get_width() {
        return this.g;
    }

    public FieldID getFieldID_width() {
        return createFieldID(2, null);
    }

    public synchronized void set_height(int v) {
        setVar(3, v);
        this.h = v;
    }

    public int get_height() {
        return this.h;
    }

    public FieldID getFieldID_height() {
        return createFieldID(3, null);
    }

    public synchronized void set_subWidth(int v) {
        setVar(4, v);
        this.i = v;
    }

    public int get_subWidth() {
        return this.i;
    }

    public FieldID getFieldID_subWidth() {
        return createFieldID(4, null);
    }

    public synchronized void set_subHeight(int v) {
        setVar(5, v);
        this.j = v;
    }

    public int get_subHeight() {
        return this.j;
    }

    public FieldID getFieldID_subHeight() {
        return createFieldID(5, null);
    }

    public synchronized void set_areaSize(int v) {
        setVar(6, v);
        this.k = v;
    }

    public int get_areaSize() {
        return this.k;
    }

    public FieldID getFieldID_areaSize() {
        return createFieldID(6, null);
    }

    public synchronized void set_subAreaSize(int v) {
        setVar(7, v);
        this.l = v;
    }

    public int get_subAreaSize() {
        return this.l;
    }

    public FieldID getFieldID_subAreaSize() {
        return createFieldID(7, null);
    }

    public synchronized void set_bitMatrixRowSize(int v) {
        setVar(8, v);
        this.m = v;
    }

    public int get_bitMatrixRowSize() {
        return this.m;
    }

    public FieldID getFieldID_bitMatrixRowSize() {
        return createFieldID(8, null);
    }

    public synchronized void set__var_th(int v) {
        setVar(9, v);
        this.n = v;
    }

    public int get__var_th() {
        return this.n;
    }

    public FieldID getFieldID__var_th() {
        return createFieldID(9, null);
    }

    public synchronized void set_avgSum(int v) {
        setVar(10, v);
        this.o = v;
    }

    public int get_avgSum() {
        return this.o;
    }

    public FieldID getFieldID_avgSum() {
        return createFieldID(10, null);
    }

    public synchronized void set_gCurrentFrame(Allocation v) {
        setVar(11, v);
        this.p = v;
    }

    public Allocation get_gCurrentFrame() {
        return this.p;
    }

    public FieldID getFieldID_gCurrentFrame() {
        return createFieldID(11, null);
    }

    public synchronized void set_gTypeAllocation(Allocation v) {
        setVar(12, v);
        this.q = v;
    }

    public Allocation get_gTypeAllocation() {
        return this.q;
    }

    public FieldID getFieldID_gTypeAllocation() {
        return createFieldID(12, null);
    }

    public synchronized void set_gAverageBlockAllocation(Allocation v) {
        setVar(13, v);
        this.r = v;
    }

    public Allocation get_gAverageBlockAllocation() {
        return this.r;
    }

    public FieldID getFieldID_gAverageBlockAllocation() {
        return createFieldID(13, null);
    }

    public synchronized void set_gBitMatrixAllocation(Allocation v) {
        setVar(14, v);
        this.s = v;
    }

    public Allocation get_gBitMatrixAllocation() {
        return this.s;
    }

    public FieldID getFieldID_gBitMatrixAllocation() {
        return createFieldID(14, null);
    }

    public KernelID getKernelID_calAverage() {
        return createKernelID(1, 41, null, null);
    }

    public void forEach_calAverage(Allocation ain) {
        forEach_calAverage(ain, null);
    }

    public void forEach_calAverage(Allocation ain, LaunchOptions sc) {
        if (!ain.getType().getElement().isCompatible(this.c)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        forEach(1, ain, null, null, sc);
    }

    public KernelID getKernelID_setBlack() {
        return createKernelID(2, 41, null, null);
    }

    public void forEach_setBlack(Allocation ain) {
        forEach_setBlack(ain, null);
    }

    public void forEach_setBlack(Allocation ain, LaunchOptions sc) {
        if (!ain.getType().getElement().isCompatible(this.c)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        forEach(2, ain, null, null, sc);
    }

    public result_int reduce_produceAverage(byte[] in1) {
        if (in1 == null) {
            throw new RSIllegalArgumentException("Array \"in1\" is null!");
        }
        Allocation ain1 = Allocation.createSized(this.d, this.c, in1.length);
        ain1.setAutoPadding(true);
        ain1.copyFrom(in1);
        result_int result = reduce_produceAverage(ain1, null);
        result.a = new Allocation[]{ain1};
        return result;
    }

    public result_int reduce_produceAverage(Allocation ain1) {
        return reduce_produceAverage(ain1, null);
    }

    public result_int reduce_produceAverage(Allocation ain1, LaunchOptions sc) {
        if (!ain1.getType().getElement().isCompatible(this.c)) {
            throw new RSRuntimeException("Type mismatch with U8!");
        }
        Allocation aout = Allocation.createSized(this.d, this.b, 1);
        aout.setAutoPadding(true);
        reduce(0, new Allocation[]{ain1}, aout, sc);
        return new result_int(aout, 0);
    }

    public InvokeID getInvokeID_initBinarizer() {
        return createInvokeID(0);
    }

    public void invoke_initBinarizer(int w, int h2, int var_th, int log_n, int rowSize) {
        FieldPacker initBinarizer_fp = new FieldPacker(20);
        initBinarizer_fp.addI32(w);
        initBinarizer_fp.addI32(h2);
        initBinarizer_fp.addI32(var_th);
        initBinarizer_fp.addI32(log_n);
        initBinarizer_fp.addI32(rowSize);
        invoke(0, initBinarizer_fp);
    }
}
