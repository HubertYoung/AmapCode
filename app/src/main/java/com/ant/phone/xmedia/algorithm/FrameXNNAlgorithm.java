package com.ant.phone.xmedia.algorithm;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.alipaylogger.Log;
import com.alipay.android.phone.falcon.xmedia.XNNJNI;
import com.alipay.android.phone.falcon.xmedia.XNNResult;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.ant.phone.xmedia.XMediaEngine.XMediaCallback;
import com.ant.phone.xmedia.api.utils.FrameCapture;
import com.ant.phone.xmedia.api.utils.OtherUtils;
import com.ant.phone.xmedia.benchmark.AlgorithmBenchmark;
import com.ant.phone.xmedia.hybrid.H5XMediaPlugin.Filter;
import com.ant.phone.xmedia.params.ErrorInfo;
import com.ant.phone.xmedia.params.XMediaClassifyResult;
import com.ant.phone.xmedia.params.XMediaFilterItem;
import com.ant.phone.xmedia.params.XMediaResponse;
import com.ant.phone.xmedia.receiver.ImageReceiver;
import com.ant.phone.xmedia.receiver.ImageReceiver.ImageCallback;
import java.util.ArrayList;
import java.util.List;

public class FrameXNNAlgorithm {
    private ImageReceiver a;
    /* access modifiers changed from: private */
    public long b = 0;
    /* access modifiers changed from: private */
    public volatile boolean c = false;
    private HandlerThread d = null;
    private c e = null;
    /* access modifiers changed from: private */
    public byte[] f;
    /* access modifiers changed from: private */
    public int g = 2;
    private int h = 0;
    private int i = 0;
    /* access modifiers changed from: private */
    public volatile boolean j = false;
    /* access modifiers changed from: private */
    public int k = 0;
    private int l = 0;
    /* access modifiers changed from: private */
    public FrameCapture m;
    private long n = 0;
    /* access modifiers changed from: private */
    public Object o = new Object();
    private ImageCallback p = new a(this);
    private XMediaCallback q;
    private float[] r;
    private Filter s;

    static /* synthetic */ int a(FrameXNNAlgorithm x0) {
        int i2 = x0.l + 1;
        x0.l = i2;
        return i2;
    }

    public final void a(String model_path, int type, float[] rect, Filter filter, XMediaCallback callback) {
        Log.i("FrameXNNAlgorithm", "nxx init, model_path:" + model_path);
        this.q = callback;
        this.s = filter;
        this.r = rect;
        this.g = type;
        if (this.a == null) {
            this.a = new ImageReceiver();
            this.a.init();
            this.a.setCallback(this.p);
        }
        Message msg = Message.obtain();
        msg.what = 5;
        a(msg);
        Message msg2 = Message.obtain();
        msg2.what = 0;
        msg2.obj = model_path;
        a(msg2);
    }

    public final void a() {
        Log.i("FrameXNNAlgorithm", "nxx start");
        this.j = true;
    }

    public final void b() {
        this.j = false;
        if (this.a != null) {
            this.a.uninit();
        }
        a(0);
        a(1);
        a(2);
        a(3);
        a(4);
        Message msg = Message.obtain();
        msg.what = 5;
        a(msg);
        Message msg1 = Message.obtain();
        msg1.what = 6;
        a(msg1);
        synchronized (this.o) {
            try {
                this.o.wait(2500);
            } catch (InterruptedException e2) {
                Log.e("FrameXNNAlgorithm", "wait exp:", e2);
            }
        }
        Log.i("FrameXNNAlgorithm", "stop end");
    }

    /* access modifiers changed from: private */
    public void a(String model_path) {
        int i2 = 0;
        Log.i("FrameXNNAlgorithm", "handleInit, type:" + this.g);
        this.h = 0;
        this.n = 0;
        long t = System.currentTimeMillis();
        AlgorithmBenchmark.a((String) "KEY_INIT").putLong("ENGINE_INIT_START", t);
        if (this.b != 0) {
            Log.i("FrameXNNAlgorithm", "handleInit but already inited, just skip.");
        }
        switch (this.g) {
            case 1:
                this.b = XNNJNI.init(OtherUtils.a(model_path));
                break;
            case 2:
                this.b = XNNJNI.init(OtherUtils.a(model_path));
                break;
            default:
                this.b = XNNJNI.init(OtherUtils.a(model_path));
                break;
        }
        long end = System.currentTimeMillis();
        AlgorithmBenchmark.a((String) "KEY_INIT").putLong("ENGINE_INIT_END", end);
        Log.i("FrameXNNAlgorithm", "XNNDetectJNI.init done, mNativeInstance: " + this.b + ", cost time:" + (end - t) + RPCDataParser.TIME_MS);
        if (this.b == 0) {
            i2 = 1;
        }
        AlgorithmBenchmark.a(i2);
        if (this.b == 0) {
            Log.i("FrameXNNAlgorithm", "frame xnn algorithm init failed.");
            if (this.q != null) {
                XMediaResponse response = new XMediaResponse();
                response.mErrInfo = new ErrorInfo(10003, "frame xnn algorithm init failed.");
                response.mMode = this.g;
                this.q.onResponse(response);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0337 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:98:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r50, int r51) {
        /*
            r49 = this;
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.String r5 = "handleDetectYuv"
            com.alipay.alipaylogger.Log.i(r4, r5)
            r0 = r49
            long r4 = r0.b
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 != 0) goto L_0x0019
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.String r5 = "xnn not init. skip"
            com.alipay.alipaylogger.Log.w(r4, r5)
        L_0x0018:
            return
        L_0x0019:
            long r40 = java.lang.System.currentTimeMillis()
            r0 = r49
            int r4 = r0.h
            if (r4 != 0) goto L_0x0072
            java.lang.String r4 = "KEY_FRAME_DET"
            android.os.Bundle r4 = com.ant.phone.xmedia.benchmark.AlgorithmBenchmark.a(r4)
            java.lang.String r5 = "FRAME_DET_START"
            r0 = r40
            r4.putLong(r5, r0)
            java.lang.String r4 = "KEY_FRAME_DET_TRACK"
            android.os.Bundle r4 = com.ant.phone.xmedia.benchmark.AlgorithmBenchmark.a(r4)
            java.lang.String r5 = "FRAME_DET_START"
            r0 = r40
            r4.putLong(r5, r0)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r0 = r50
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r5 = "*"
            java.lang.StringBuilder r4 = r4.append(r5)
            r0 = r51
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r43 = r4.toString()
            java.lang.String r4 = "KEY_FRAME_DET"
            android.os.Bundle r4 = com.ant.phone.xmedia.benchmark.AlgorithmBenchmark.a(r4)
            java.lang.String r5 = "FILE_SIZE"
            r0 = r43
            r4.putString(r5, r0)
            java.lang.String r4 = "KEY_FRAME_DET_TRACK"
            android.os.Bundle r4 = com.ant.phone.xmedia.benchmark.AlgorithmBenchmark.a(r4)
            java.lang.String r5 = "FILE_SIZE"
            r0 = r43
            r4.putString(r5, r0)
        L_0x0072:
            r38 = 0
            java.util.ArrayList r46 = new java.util.ArrayList
            r46.<init>()
            java.util.ArrayList r17 = new java.util.ArrayList
            r17.<init>()
            r28 = r50
            r27 = r51
            r0 = r49
            int r4 = r0.k     // Catch:{ Throwable -> 0x0447 }
            r5 = 90
            if (r4 == r5) goto L_0x0092
            r0 = r49
            int r4 = r0.k     // Catch:{ Throwable -> 0x0447 }
            r5 = 270(0x10e, float:3.78E-43)
            if (r4 != r5) goto L_0x0096
        L_0x0092:
            r28 = r51
            r27 = r50
        L_0x0096:
            r20 = 0
            r21 = 0
            r19 = r28
            r18 = r27
            r0 = r49
            float[] r4 = r0.r     // Catch:{ Throwable -> 0x0447 }
            if (r4 == 0) goto L_0x0140
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = "handleDetectYuv with roi:("
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r6 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r7 = 0
            r6 = r6[r7]     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = ","
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r6 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r7 = 1
            r6 = r6[r7]     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = ","
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r6 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r7 = 2
            r6 = r6[r7]     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = ","
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r6 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r7 = 3
            r6 = r6[r7]     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = ")"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0447 }
            com.alipay.alipaylogger.Log.i(r4, r5)     // Catch:{ Throwable -> 0x0447 }
            r0 = r28
            float r4 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r5 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r6 = 0
            r5 = r5[r6]     // Catch:{ Throwable -> 0x0447 }
            float r4 = r4 * r5
            int r0 = (int) r4     // Catch:{ Throwable -> 0x0447 }
            r20 = r0
            r0 = r27
            float r4 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r5 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r6 = 1
            r5 = r5[r6]     // Catch:{ Throwable -> 0x0447 }
            float r4 = r4 * r5
            int r0 = (int) r4     // Catch:{ Throwable -> 0x0447 }
            r21 = r0
            r0 = r28
            float r4 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r5 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r6 = 2
            r5 = r5[r6]     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r6 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r7 = 0
            r6 = r6[r7]     // Catch:{ Throwable -> 0x0447 }
            float r5 = r5 - r6
            float r4 = r4 * r5
            int r0 = (int) r4     // Catch:{ Throwable -> 0x0447 }
            r19 = r0
            r0 = r27
            float r4 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r5 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r6 = 3
            r5 = r5[r6]     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r6 = r0.r     // Catch:{ Throwable -> 0x0447 }
            r7 = 1
            r6 = r6[r7]     // Catch:{ Throwable -> 0x0447 }
            float r5 = r5 - r6
            float r4 = r4 * r5
            int r0 = (int) r4     // Catch:{ Throwable -> 0x0447 }
            r18 = r0
        L_0x0140:
            r4 = 4
            int[] r9 = new int[r4]     // Catch:{ Throwable -> 0x0447 }
            r4 = 0
            r9[r4] = r20     // Catch:{ Throwable -> 0x0447 }
            r4 = 1
            r9[r4] = r21     // Catch:{ Throwable -> 0x0447 }
            r4 = 2
            r9[r4] = r19     // Catch:{ Throwable -> 0x0447 }
            r4 = 3
            r9[r4] = r18     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = "imageRect:("
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0447 }
            r6 = 0
            r6 = r9[r6]     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = ","
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            r6 = 1
            r6 = r9[r6]     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = ","
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            r6 = 2
            r6 = r9[r6]     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = ","
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            r6 = 3
            r6 = r9[r6]     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = ")"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0447 }
            com.alipay.alipaylogger.Log.i(r4, r5)     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            float[] r4 = r0.r     // Catch:{ Throwable -> 0x0447 }
            if (r4 != 0) goto L_0x019a
            r9 = 0
        L_0x019a:
            r0 = r49
            long r4 = r0.b     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            byte[] r6 = r0.f     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            int r10 = r0.k     // Catch:{ Throwable -> 0x0447 }
            r7 = r50
            r8 = r51
            com.alipay.android.phone.falcon.xmedia.XNNResult r38 = com.alipay.android.phone.falcon.xmedia.XNNJNI.detectYuv(r4, r6, r7, r8, r9, r10)     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            boolean r4 = r0.j     // Catch:{ Throwable -> 0x0447 }
            if (r4 != 0) goto L_0x01c2
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.String r5 = "handleDetectYuv done but not running yet, return."
            com.alipay.alipaylogger.Log.i(r4, r5)     // Catch:{ Throwable -> 0x0447 }
            r4 = 0
            r0 = r49
            r0.c = r4
            goto L_0x0018
        L_0x01c2:
            if (r38 == 0) goto L_0x0458
            r0 = r38
            int r4 = r0.retCode     // Catch:{ Throwable -> 0x0447 }
            if (r4 < 0) goto L_0x0458
            r0 = r38
            int r4 = r0.labelNums     // Catch:{ Throwable -> 0x0447 }
            if (r4 <= 0) goto L_0x03ef
            r0 = r38
            float[] r4 = r0.confArray     // Catch:{ Throwable -> 0x0447 }
            if (r4 == 0) goto L_0x03ef
            r0 = r38
            float[] r4 = r0.confArray     // Catch:{ Throwable -> 0x0447 }
            int r4 = r4.length     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            int r5 = r0.labelNums     // Catch:{ Throwable -> 0x0447 }
            if (r4 != r5) goto L_0x03ef
            r0 = r38
            float[] r4 = r0.posArray     // Catch:{ Throwable -> 0x0447 }
            if (r4 == 0) goto L_0x03ef
            r0 = r38
            float[] r4 = r0.posArray     // Catch:{ Throwable -> 0x0447 }
            int r4 = r4.length     // Catch:{ Throwable -> 0x0447 }
            int r4 = r4 / 4
            r0 = r38
            int r5 = r0.labelNums     // Catch:{ Throwable -> 0x0447 }
            if (r4 != r5) goto L_0x03ef
            r31 = 0
            r0 = r38
            java.lang.String r4 = r0.objectName     // Catch:{ Throwable -> 0x0447 }
            boolean r4 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0447 }
            if (r4 != 0) goto L_0x021f
            r0 = r38
            java.lang.String r4 = r0.objectName     // Catch:{ Throwable -> 0x0447 }
            r5 = 1
            r0 = r38
            java.lang.String r6 = r0.objectName     // Catch:{ Throwable -> 0x0447 }
            int r6 = r6.length()     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r39 = r4.substring(r5, r6)     // Catch:{ Throwable -> 0x0447 }
            boolean r4 = android.text.TextUtils.isEmpty(r39)     // Catch:{ Throwable -> 0x0447 }
            if (r4 != 0) goto L_0x021f
            java.lang.String r4 = "#"
            r0 = r39
            java.lang.String[] r31 = r0.split(r4)     // Catch:{ Throwable -> 0x0447 }
        L_0x021f:
            if (r31 == 0) goto L_0x02e7
            r0 = r31
            int r4 = r0.length     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            int r5 = r0.labelNums     // Catch:{ Throwable -> 0x0447 }
            if (r4 != r5) goto L_0x02e7
            r30 = 0
            r26 = 0
        L_0x022e:
            r0 = r38
            int r4 = r0.labelNums     // Catch:{ Throwable -> 0x0447 }
            r0 = r26
            if (r0 >= r4) goto L_0x02ee
            r4 = r31[r26]     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            float[] r5 = r0.confArray     // Catch:{ Throwable -> 0x0447 }
            r5 = r5[r26]     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            com.ant.phone.xmedia.hybrid.H5XMediaPlugin$Filter r6 = r0.s     // Catch:{ Throwable -> 0x0447 }
            r0 = r49
            boolean r4 = r0.a(r4, r5, r6)     // Catch:{ Throwable -> 0x0447 }
            if (r4 == 0) goto L_0x02e3
            com.ant.phone.xmedia.params.XMediaDetectResult r22 = new com.ant.phone.xmedia.params.XMediaDetectResult     // Catch:{ Throwable -> 0x0447 }
            r22.<init>()     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            float[] r4 = r0.confArray     // Catch:{ Throwable -> 0x0447 }
            r4 = r4[r26]     // Catch:{ Throwable -> 0x0447 }
            r0 = r22
            r0.mConfidence = r4     // Catch:{ Throwable -> 0x0447 }
            r4 = r31[r26]     // Catch:{ Throwable -> 0x0447 }
            r0 = r22
            r0.mLabel = r4     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            float[] r4 = r0.posArray     // Catch:{ Throwable -> 0x0447 }
            r33 = r4[r30]     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            float[] r4 = r0.posArray     // Catch:{ Throwable -> 0x0447 }
            int r5 = r30 + 1
            r35 = r4[r5]     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            float[] r4 = r0.posArray     // Catch:{ Throwable -> 0x0447 }
            int r5 = r30 + 2
            r34 = r4[r5]     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            float[] r4 = r0.posArray     // Catch:{ Throwable -> 0x0447 }
            int r5 = r30 + 3
            r36 = r4[r5]     // Catch:{ Throwable -> 0x0447 }
            r0 = r20
            float r4 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r19
            float r5 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            float r5 = r5 * r33
            float r44 = r4 + r5
            r0 = r21
            float r4 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r18
            float r5 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            float r5 = r5 * r35
            float r47 = r4 + r5
            float r4 = r34 - r33
            r0 = r19
            float r5 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            float r4 = r4 * r5
            float r45 = r44 + r4
            float r4 = r36 - r35
            r0 = r18
            float r5 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            float r4 = r4 * r5
            float r48 = r47 + r4
            com.ant.phone.xmedia.params.BoundingBox r4 = new com.ant.phone.xmedia.params.BoundingBox     // Catch:{ Throwable -> 0x0447 }
            r0 = r28
            float r5 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            float r5 = r44 / r5
            r0 = r27
            float r6 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            float r6 = r47 / r6
            float r7 = r45 - r44
            r0 = r28
            float r8 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            float r7 = r7 / r8
            float r8 = r48 - r47
            r0 = r27
            float r10 = (float) r0     // Catch:{ Throwable -> 0x0447 }
            float r8 = r8 / r10
            r4.<init>(r5, r6, r7, r8)     // Catch:{ Throwable -> 0x0447 }
            r0 = r22
            r0.mBoundingBox = r4     // Catch:{ Throwable -> 0x0447 }
            android.graphics.Rect r16 = new android.graphics.Rect     // Catch:{ Throwable -> 0x0447 }
            r0 = r44
            int r4 = (int) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r47
            int r5 = (int) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r45
            int r6 = (int) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r48
            int r7 = (int) r0     // Catch:{ Throwable -> 0x0447 }
            r0 = r16
            r0.<init>(r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0447 }
            r0 = r17
            r1 = r16
            r0.add(r1)     // Catch:{ Throwable -> 0x0447 }
            r0 = r46
            r1 = r22
            r0.add(r1)     // Catch:{ Throwable -> 0x0447 }
            int r30 = r30 + 4
        L_0x02e3:
            int r26 = r26 + 1
            goto L_0x022e
        L_0x02e7:
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.String r5 = "wrong result for this frame."
            com.alipay.alipaylogger.Log.w(r4, r5)     // Catch:{ Throwable -> 0x0447 }
        L_0x02ee:
            r4 = 0
            r0 = r49
            r0.c = r4
        L_0x02f3:
            r0 = r49
            int r4 = r0.h
            int r4 = r4 + 1
            r0 = r49
            r0.h = r4
            long r4 = java.lang.System.currentTimeMillis()
            long r23 = r4 - r40
            r0 = r49
            long r4 = r0.n
            long r4 = r4 + r23
            r0 = r49
            r0.n = r4
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "handleDetectYuv took "
            r5.<init>(r6)
            r0 = r23
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = "ms, frame index:"
            java.lang.StringBuilder r5 = r5.append(r6)
            r0 = r49
            int r6 = r0.h
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.alipay.alipaylogger.Log.i(r4, r5)
            r0 = r49
            com.ant.phone.xmedia.XMediaEngine$XMediaCallback r4 = r0.q
            if (r4 == 0) goto L_0x0018
            if (r38 == 0) goto L_0x0018
            r0 = r38
            int r4 = r0.labelNums
            if (r4 <= 0) goto L_0x0018
            r0 = r49
            com.ant.phone.xmedia.hybrid.H5XMediaPlugin$Filter r4 = r0.s
            if (r4 == 0) goto L_0x0351
            int r4 = r46.size()
            r0 = r49
            com.ant.phone.xmedia.hybrid.H5XMediaPlugin$Filter r5 = r0.s
            int r5 = r5.threshold
            if (r4 < r5) goto L_0x0018
        L_0x0351:
            com.ant.phone.xmedia.params.XMediaResponse r37 = new com.ant.phone.xmedia.params.XMediaResponse
            r37.<init>()
            com.ant.phone.xmedia.params.ErrorInfo r25 = new com.ant.phone.xmedia.params.ErrorInfo
            r4 = 0
            java.lang.String r5 = "no error"
            r0 = r25
            r0.<init>(r4, r5)
            r0 = r25
            r1 = r37
            r1.mErrInfo = r0
            r0 = r49
            int r4 = r0.g
            r0 = r37
            r0.mMode = r4
            r0 = r46
            r1 = r37
            r1.mResult = r0
            r0 = r49
            com.ant.phone.xmedia.XMediaEngine$XMediaCallback r4 = r0.q
            r0 = r37
            r4.onResponse(r0)
            r0 = r49
            int r4 = r0.g
            r5 = 9
            if (r4 != r5) goto L_0x0018
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.String r5 = "##############detect success and switch to track!"
            com.alipay.alipaylogger.Log.i(r4, r5)
            r4 = 0
            r0 = r49
            r0.j = r4
            java.util.ArrayList r32 = new java.util.ArrayList
            r32.<init>()
            r0 = r49
            int r4 = r0.k
            r0 = r17
            r1 = r32
            r2 = r50
            r3 = r51
            a(r0, r1, r2, r3, r4)
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            r26 = 0
        L_0x03ac:
            int r4 = r46.size()
            r0 = r26
            if (r0 >= r4) goto L_0x0476
            com.ant.phone.xmedia.params.TrackItem r29 = new com.ant.phone.xmedia.params.TrackItem
            r29.<init>()
            r0 = r46
            r1 = r26
            java.lang.Object r4 = r0.get(r1)
            com.ant.phone.xmedia.params.XMediaDetectResult r4 = (com.ant.phone.xmedia.params.XMediaDetectResult) r4
            float r4 = r4.mConfidence
            r0 = r29
            r0.mConfidence = r4
            r0 = r46
            r1 = r26
            java.lang.Object r4 = r0.get(r1)
            com.ant.phone.xmedia.params.XMediaDetectResult r4 = (com.ant.phone.xmedia.params.XMediaDetectResult) r4
            java.lang.String r4 = r4.mLabel
            r0 = r29
            r0.mLabel = r4
            r0 = r32
            r1 = r26
            java.lang.Object r4 = r0.get(r1)
            android.graphics.Rect r4 = (android.graphics.Rect) r4
            r0 = r29
            r0.mRect = r4
            r0 = r29
            r11.add(r0)
            int r26 = r26 + 1
            goto L_0x03ac
        L_0x03ef:
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = "function success but no target detected, label count:"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            int r6 = r0.labelNums     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0447 }
            com.alipay.alipaylogger.Log.w(r4, r5)     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            float[] r4 = r0.confArray     // Catch:{ Throwable -> 0x0447 }
            if (r4 == 0) goto L_0x0426
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = "confArray size:"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            float[] r6 = r0.confArray     // Catch:{ Throwable -> 0x0447 }
            int r6 = r6.length     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0447 }
            com.alipay.alipaylogger.Log.w(r4, r5)     // Catch:{ Throwable -> 0x0447 }
        L_0x0426:
            r0 = r38
            float[] r4 = r0.posArray     // Catch:{ Throwable -> 0x0447 }
            if (r4 == 0) goto L_0x02ee
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r6 = "posArray size:"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0447 }
            r0 = r38
            float[] r6 = r0.posArray     // Catch:{ Throwable -> 0x0447 }
            int r6 = r6.length     // Catch:{ Throwable -> 0x0447 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0447 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0447 }
            com.alipay.alipaylogger.Log.w(r4, r5)     // Catch:{ Throwable -> 0x0447 }
            goto L_0x02ee
        L_0x0447:
            r42 = move-exception
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.String r5 = "handleDetectYuv exp:"
            r0 = r42
            com.alipay.alipaylogger.Log.e(r4, r5, r0)     // Catch:{ all -> 0x046f }
            r4 = 0
            r0 = r49
            r0.c = r4
            goto L_0x02f3
        L_0x0458:
            r0 = r49
            int r4 = r0.i     // Catch:{ Throwable -> 0x0447 }
            int r5 = r4 + 1
            r0 = r49
            r0.i = r5     // Catch:{ Throwable -> 0x0447 }
            r5 = 10
            if (r4 >= r5) goto L_0x02ee
            java.lang.String r4 = "FrameXNNAlgorithm"
            java.lang.String r5 = "retcode < 0, something is wrong."
            com.alipay.alipaylogger.Log.w(r4, r5)     // Catch:{ Throwable -> 0x0447 }
            goto L_0x02ee
        L_0x046f:
            r4 = move-exception
            r5 = 0
            r0 = r49
            r0.c = r5
            throw r4
        L_0x0476:
            com.ant.phone.xmedia.XMediaEngine r10 = com.ant.phone.xmedia.XMediaEngine.getInstance()
            r0 = r49
            byte[] r14 = r0.f
            r0 = r49
            com.ant.phone.xmedia.XMediaEngine$XMediaCallback r15 = r0.q
            r12 = r50
            r13 = r51
            r10.startTrack(r11, r12, r13, r14, r15)
            goto L_0x0018
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ant.phone.xmedia.algorithm.FrameXNNAlgorithm.a(int, int):void");
    }

    private static void a(List<Rect> src, List<Rect> dest, int w, int h2, int rotation) {
        for (int i2 = 0; i2 < src.size(); i2++) {
            Rect detectRect = src.get(i2);
            int x0 = detectRect.left;
            int y0 = detectRect.top;
            int rectw = detectRect.width();
            int recth = detectRect.height();
            int x1 = 0;
            int y1 = 0;
            int x2 = 0;
            int y2 = 0;
            switch (rotation) {
                case 0:
                    x1 = x0;
                    y1 = y0;
                    x2 = x1 + rectw;
                    y2 = y1 + recth;
                    break;
                case 90:
                    x1 = y0;
                    y1 = (h2 - rectw) - x0;
                    x2 = x1 + recth;
                    y2 = y1 + rectw;
                    break;
                case 180:
                    x1 = (w - x0) - rectw;
                    y1 = (h2 - y0) - recth;
                    x2 = x1 + rectw;
                    y2 = y1 + recth;
                    break;
                case 270:
                    x1 = (w - recth) - y0;
                    y1 = x0;
                    x2 = x1 + recth;
                    y2 = y1 + rectw;
                    break;
            }
            dest.add(new Rect(x1, y1, x2, y2));
        }
    }

    private boolean a(String label, float confidence, Filter filter) {
        Log.i("FrameXNNAlgorithm", "doFilter, label:" + label + ", confidence:" + confidence);
        if (this.g != 9 || filter == null || filter.filter == null) {
            return true;
        }
        if (TextUtils.isEmpty(label)) {
            return false;
        }
        XMediaFilterItem item = filter.filter.get(label);
        if (item == null || confidence < item.mConfidence) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void b(int w, int h2) {
        Log.i("FrameXNNAlgorithm", "handleClassifyYuv.w=" + w + ",h=" + h2);
        if (this.b == 0) {
            Log.w("FrameXNNAlgorithm", "xnn not init. skip");
            return;
        }
        long t = System.currentTimeMillis();
        if (this.h == 0) {
            AlgorithmBenchmark.a((String) "KEY_FRAME_CLS").putLong("FRAME_CLS_START", t);
            AlgorithmBenchmark.a((String) "KEY_FRAME_CLS").putString("FILE_SIZE", w + "*" + h2);
        }
        XNNResult result = null;
        ArrayList xMediaResults = new ArrayList();
        int imagew = w;
        int imageh = h2;
        try {
            if (this.k == 90 || this.k == 270) {
                imagew = h2;
                imageh = w;
            }
            int[] absolute_roi = null;
            if (this.r != null) {
                RectF rectF = new RectF(this.r[0] * ((float) imagew), this.r[1] * ((float) imageh), (this.r[2] + this.r[0]) * ((float) imagew), (this.r[3] + this.r[1]) * ((float) imageh));
                Log.i("FrameXNNAlgorithm", "handleClassifyYuv with roi:" + rectF);
                absolute_roi = new int[]{(int) rectF.left, (int) rectF.top, (int) rectF.width(), (int) rectF.height()};
            }
            result = XNNJNI.classifyYuv(this.b, this.f, w, h2, absolute_roi, this.k);
            if (result == null || result.retCode < 0) {
                int i2 = this.i;
                this.i = i2 + 1;
                if (i2 < 10) {
                    Log.w("FrameXNNAlgorithm", "retcode < 0, something is wrong.");
                }
            } else if (result.labelNums <= 0 || result.confArray == null || result.confArray.length != result.labelNums) {
                Log.w("FrameXNNAlgorithm", "function success but no target classified, label count:" + result.labelNums);
                if (result.confArray != null) {
                    Log.w("FrameXNNAlgorithm", "confArray size:" + result.confArray.length);
                }
            } else {
                String[] lables = null;
                if (!TextUtils.isEmpty(result.objectName)) {
                    String str = result.objectName.substring(1, result.objectName.length());
                    if (!TextUtils.isEmpty(str)) {
                        lables = str.split(MetaRecord.LOG_SEPARATOR);
                    }
                }
                if (lables == null || lables.length != result.labelNums) {
                    Log.w("FrameXNNAlgorithm", "wrong result for this frame.");
                } else {
                    for (int i3 = 0; i3 < result.labelNums; i3++) {
                        if (a(lables[i3], result.confArray[i3], this.s)) {
                            XMediaClassifyResult classifyResult = new XMediaClassifyResult();
                            classifyResult.mConfidence = result.confArray[i3];
                            classifyResult.mLabel = lables[i3];
                            xMediaResults.add(classifyResult);
                        }
                    }
                }
            }
        } catch (Throwable throwable) {
            Log.e("FrameXNNAlgorithm", "handleClassifyYuv exp:", throwable);
        } finally {
            this.c = false;
        }
        this.h++;
        long duration = System.currentTimeMillis() - t;
        this.n += duration;
        Log.i("FrameXNNAlgorithm", "handleClassifyYuv took " + duration + "ms, frame index:" + this.h);
        if (this.q != null && result != null && result.labelNums > 0) {
            if (this.s == null || xMediaResults.size() >= this.s.threshold) {
                XMediaResponse response = new XMediaResponse();
                response.mErrInfo = new ErrorInfo(0, "no error");
                response.mMode = this.g;
                response.mResult = xMediaResults;
                this.q.onResponse(response);
            }
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        Log.i("FrameXNNAlgorithm", "handleUninit, mNativeInstance:" + this.b);
        long t = System.currentTimeMillis();
        AlgorithmBenchmark.a((String) "KEY_FRAME_DET").putLong("FRAME_DET_END", t);
        AlgorithmBenchmark.a((String) "KEY_FRAME_DET_TRACK").putLong("FRAME_DET_END", t);
        AlgorithmBenchmark.a((String) "KEY_FRAME_CLS").putLong("FRAME_CLS_END", t);
        AlgorithmBenchmark.a((String) "KEY_FRAME_DET").putInt("FRAME_TOTAL_COUNT_XNN", this.h);
        AlgorithmBenchmark.a((String) "KEY_FRAME_CLS").putInt("FRAME_TOTAL_COUNT_XNN", this.h);
        AlgorithmBenchmark.a((String) "KEY_FRAME_DET_TRACK").putInt("FRAME_TOTAL_COUNT_XNN", this.h);
        AlgorithmBenchmark.a((String) "KEY_FRAME_DET").putLong("ENGINE_TOTAL_TIME_XNN", this.n);
        AlgorithmBenchmark.a((String) "KEY_FRAME_DET_TRACK").putLong("ENGINE_TOTAL_TIME_XNN", this.n);
        AlgorithmBenchmark.a((String) "KEY_FRAME_CLS").putLong("ENGINE_TOTAL_TIME_XNN", this.n);
        if (this.b != 0) {
            switch (this.g) {
                case 1:
                    XNNJNI.release(this.b);
                    AlgorithmBenchmark.a();
                    break;
                case 2:
                    XNNJNI.release(this.b);
                    AlgorithmBenchmark.b();
                    break;
                default:
                    XNNJNI.release(this.b);
                    break;
            }
            this.b = 0;
        }
        this.c = false;
        this.i = 0;
        this.h = 0;
        this.n = 0;
        Log.i("FrameXNNAlgorithm", "handleUninit took " + (System.currentTimeMillis() - t) + RPCDataParser.TIME_MS);
    }

    private void a(int what) {
        if (this.e != null && this.d != null && this.d.isAlive() && this.e.getLooper() != null) {
            Log.i("FrameXNNAlgorithm", "removeMessages what: " + what);
            this.e.removeMessages(what);
        }
    }

    /* access modifiers changed from: private */
    public boolean a(Message msg) {
        if (this.e != null && this.d != null && this.d.isAlive() && this.e.getLooper() != null) {
            return this.e.sendMessage(msg);
        }
        e();
        return this.e.sendMessage(msg);
    }

    /* access modifiers changed from: private */
    public void d() {
        Thread.currentThread().setUncaughtExceptionHandler(new b(this));
    }

    public FrameXNNAlgorithm() {
        Log.i("FrameXNNAlgorithm", "FrameXNNAlgorithm construct, id:" + this);
        e();
    }

    private synchronized void e() {
        if (this.e == null) {
            this.d = new HandlerThread("XMedia_XNNThread_" + System.currentTimeMillis());
            this.d.start();
            this.e = new c(this, this, this.d.getLooper());
            Log.d("FrameXNNAlgorithm", "xnn work thread prepared.");
        }
    }
}
