package com.autonavi.indoor.simulator;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.autonavi.indoor.entity.ScanPair;
import com.autonavi.indoor.pdr.MatStepData;
import com.autonavi.indoor.provider.IProvider;
import com.autonavi.indoor.util.L;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileDataProvider extends IProvider {
    private static volatile FileDataProvider f;
    List<a> a = new ArrayList();
    int b = 0;
    long c = 0;
    String d;
    MatStepData e = null;
    private boolean g = false;
    /* access modifiers changed from: private */
    public ArrayList<Handler> h = new ArrayList<>();
    public boolean mEnablePdr = false;
    public double mLastAngle = 0.0d;
    public double mLastPress = 0.0d;

    static class a {
        public long a;
        public int b;
        public int c;
        public double d;
        public double e;
        public List<ScanPair> f;
        public MatStepData g;

        public a(long j, int i, double d2) {
            this.g = null;
            this.a = j;
            this.c = i;
            this.d = d2;
            this.b = 1;
        }

        public a(long j, double d2) {
            this.g = null;
            this.a = j;
            this.e = d2;
            this.b = 2;
        }

        public a(long j, List<ScanPair> list) {
            this.g = null;
            this.a = j;
            this.f = list;
            this.b = 3;
        }

        public a(long j, int i, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, double d10, double d11, double d12, double d13, double d14, double d15, int i2, double d16, double d17) {
            this.g = null;
            this.b = 4;
            this.g = new MatStepData();
            this.g.timestamp_ = j;
            this.g.step_ = i;
            this.g.angle_ = d2;
            this.g.mat_num_ = 1;
            this.g.rotate_rate_x_ = d10;
            this.g.rotate_rate_y_ = d11;
            this.g.rotate_rate_z_ = d12;
            this.g.mat_data_[0] = d3;
            this.g.mat_data_[1] = d4;
            this.g.mat_data_[2] = d5;
            this.g.step_len_ = d13;
            this.g.step_len_f_ = d14;
            this.g.step_len_v_ = d15;
            this.g.move_direction_ = i2;
            this.g.q1_ = d6;
            this.g.q2_ = d7;
            this.g.q3_ = d8;
            this.g.q4_ = d9;
            this.g.angle_no_mag_ = d16;
            this.g.move_state_score_ = d17;
        }
    }

    static class b extends Handler {
        private final WeakReference<FileDataProvider> a;

        public b(FileDataProvider fileDataProvider) {
            this.a = new WeakReference<>(fileDataProvider);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:50:0x024f, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0250, code lost:
            r1 = r0;
            r4 = r3;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x031c, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:72:0x031d, code lost:
            r1 = r0;
            r4 = r106;
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [B:36:0x00e4, B:45:0x01bc] */
        /* JADX WARNING: Removed duplicated region for block: B:111:0x03ba  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void handleMessage(android.os.Message r107) {
            /*
                r106 = this;
                r1 = r106
                java.lang.ref.WeakReference<com.autonavi.indoor.simulator.FileDataProvider> r2 = r1.a
                java.lang.Object r2 = r2.get()
                com.autonavi.indoor.simulator.FileDataProvider r2 = (com.autonavi.indoor.simulator.FileDataProvider) r2
                if (r2 != 0) goto L_0x000d
                return
            L_0x000d:
                r3 = r107
                int r3 = r3.what     // Catch:{ Throwable -> 0x03b3 }
                r4 = 1200(0x4b0, float:1.682E-42)
                if (r3 != r4) goto L_0x03b1
                boolean r3 = r2.mEnablePdr     // Catch:{ Throwable -> 0x03b3 }
                r5 = 10
                if (r3 != 0) goto L_0x001e
                r3 = 100
                goto L_0x0020
            L_0x001e:
                r3 = 10
            L_0x0020:
                int r6 = r2.b     // Catch:{ Throwable -> 0x03b3 }
                java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r7 = r2.a     // Catch:{ Throwable -> 0x03b3 }
                int r7 = r7.size()     // Catch:{ Throwable -> 0x03b3 }
                if (r6 < r7) goto L_0x0034
                boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x03b3 }
                if (r2 == 0) goto L_0x0033
                java.lang.String r2 = "Simulate file is end!"
                com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x03b3 }
            L_0x0033:
                return
            L_0x0034:
                java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r6 = r2.a     // Catch:{ Throwable -> 0x03b3 }
                int r7 = r2.b     // Catch:{ Throwable -> 0x03b3 }
                java.lang.Object r6 = r6.get(r7)     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.simulator.FileDataProvider$a r6 = (com.autonavi.indoor.simulator.FileDataProvider.a) r6     // Catch:{ Throwable -> 0x03b3 }
                long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x03b3 }
                r6.a = r7     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                if (r7 == 0) goto L_0x0050
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x03b3 }
                r7.timestamp_ = r8     // Catch:{ Throwable -> 0x03b3 }
            L_0x0050:
                int r7 = r6.b     // Catch:{ Throwable -> 0x03b3 }
                r8 = 111(0x6f, float:1.56E-43)
                r9 = 1
                r10 = 0
                if (r7 != r9) goto L_0x0098
                boolean r5 = r2.mEnablePdr     // Catch:{ Throwable -> 0x03b3 }
                if (r5 == 0) goto L_0x0091
                com.autonavi.indoor.pdr.MatStepData r5 = new com.autonavi.indoor.pdr.MatStepData     // Catch:{ Throwable -> 0x03b3 }
                int r12 = r6.c     // Catch:{ Throwable -> 0x03b3 }
                double r13 = r6.d     // Catch:{ Throwable -> 0x03b3 }
                r15 = 0
                r17 = 0
                r19 = 0
                r21 = 4607182418800017408(0x3ff0000000000000, double:1.0)
                r23 = 0
                r25 = 0
                r27 = 0
                r29 = 0
                r31 = 0
                r33 = 0
                double r10 = r6.d     // Catch:{ Throwable -> 0x03b3 }
                r37 = 0
                r39 = 0
                r41 = 0
                r42 = 0
                r44 = 0
                r35 = r10
                r11 = r5
                r11.<init>(r12, r13, r15, r17, r19, r21, r23, r25, r27, r29, r31, r33, r35, r37, r39, r41, r42, r44)     // Catch:{ Throwable -> 0x03b3 }
                long r10 = r6.a     // Catch:{ Throwable -> 0x03b3 }
                r5.timestamp_ = r10     // Catch:{ Throwable -> 0x03b3 }
                r1.a(r2, r8, r5)     // Catch:{ Throwable -> 0x03b3 }
                int r10 = r3 * 5
            L_0x0091:
                double r5 = r6.d     // Catch:{ Throwable -> 0x03b3 }
                r2.mLastAngle = r5     // Catch:{ Throwable -> 0x03b3 }
                r4 = r1
                goto L_0x0394
            L_0x0098:
                int r7 = r6.b     // Catch:{ Throwable -> 0x03b3 }
                r11 = 4
                r12 = 2
                if (r7 != r11) goto L_0x030c
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                if (r7 == 0) goto L_0x030c
                boolean r5 = r2.mEnablePdr     // Catch:{ Throwable -> 0x03b3 }
                if (r5 == 0) goto L_0x0305
                com.autonavi.indoor.pdr.MatStepData r5 = r2.e     // Catch:{ Throwable -> 0x03b3 }
                if (r5 != 0) goto L_0x0140
                com.autonavi.indoor.pdr.MatStepData r5 = new com.autonavi.indoor.pdr.MatStepData     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                int r14 = r7.step_     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                double r7 = r7.angle_     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r11 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                double[] r11 = r11.mat_data_     // Catch:{ Throwable -> 0x03b3 }
                r17 = r11[r10]     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r10 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                double[] r10 = r10.mat_data_     // Catch:{ Throwable -> 0x03b3 }
                r19 = r10[r9]     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r10 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                double[] r10 = r10.mat_data_     // Catch:{ Throwable -> 0x03b3 }
                r21 = r10[r12]     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r10 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                double r10 = r10.q1_     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r12 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                double r12 = r12.q2_     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r15 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                r48 = r10
                double r9 = r15.q3_     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r11 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                r50 = r5
                double r4 = r11.q4_     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r11 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                r51 = r12
                double r11 = r11.rotate_rate_x_     // Catch:{ Throwable -> 0x03b3 }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x03b3 }
                r53 = r2
                double r1 = r13.rotate_rate_y_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r54 = r1
                double r1 = r13.rotate_rate_z_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r56 = r1
                double r1 = r13.step_len_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r58 = r1
                double r1 = r13.step_len_f_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r60 = r1
                double r1 = r13.step_len_v_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                int r15 = r13.move_direction_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r62 = r1
                double r1 = r13.angle_no_mag_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r64 = r1
                double r1 = r13.move_state_score_     // Catch:{ Throwable -> 0x031c }
                r25 = r51
                r13 = r50
                r43 = r15
                r15 = r7
                r23 = r48
                r27 = r9
                r29 = r4
                r31 = r11
                r33 = r54
                r35 = r56
                r37 = r58
                r39 = r60
                r41 = r62
                r44 = r64
                r46 = r1
                r13.<init>(r14, r15, r17, r19, r21, r23, r25, r27, r29, r31, r33, r35, r37, r39, r41, r43, r44, r46)     // Catch:{ Throwable -> 0x031c }
                r1 = r50
                r2 = r53
                r2.e = r1     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r4 = r6.g     // Catch:{ Throwable -> 0x031c }
                long r4 = r4.timestamp_     // Catch:{ Throwable -> 0x031c }
                r1.timestamp_ = r4     // Catch:{ Throwable -> 0x031c }
                r67 = r3
                goto L_0x0303
            L_0x0140:
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                int r1 = r1.step_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r4 = r6.g     // Catch:{ Throwable -> 0x031c }
                int r4 = r4.step_     // Catch:{ Throwable -> 0x031c }
                if (r1 != r4) goto L_0x0254
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                long r4 = r1.timestamp_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                long r13 = r1.timestamp_     // Catch:{ Throwable -> 0x031c }
                r1 = 0
                long r4 = r4 - r13
                r13 = 2000(0x7d0, double:9.88E-321)
                int r1 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
                if (r1 >= 0) goto L_0x01a8
                com.autonavi.indoor.pdr.MatStepData r13 = r2.e     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                double[] r1 = r1.mat_data_     // Catch:{ Throwable -> 0x031c }
                r14 = r1[r10]     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                double[] r1 = r1.mat_data_     // Catch:{ Throwable -> 0x031c }
                r4 = 1
                r16 = r1[r4]     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                double[] r1 = r1.mat_data_     // Catch:{ Throwable -> 0x031c }
                r18 = r1[r12]     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                double r4 = r1.q1_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                double r7 = r1.q2_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                double r9 = r1.q3_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                double r11 = r1.q4_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                r66 = r2
                double r1 = r1.rotate_rate_x_     // Catch:{ Throwable -> 0x031c }
                r67 = r3
                com.autonavi.indoor.pdr.MatStepData r3 = r6.g     // Catch:{ Throwable -> 0x031c }
                r68 = r1
                double r1 = r3.rotate_rate_y_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r3 = r6.g     // Catch:{ Throwable -> 0x031c }
                r70 = r1
                double r1 = r3.rotate_rate_z_     // Catch:{ Throwable -> 0x031c }
                r20 = r4
                r22 = r7
                r24 = r9
                r26 = r11
                r28 = r68
                r30 = r70
                r32 = r1
                r13.AddMatData(r14, r16, r18, r20, r22, r24, r26, r28, r30, r32)     // Catch:{ Throwable -> 0x031c }
                r2 = r66
                goto L_0x0303
            L_0x01a8:
                r67 = r3
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r3 = r6.g     // Catch:{ Throwable -> 0x031c }
                double r3 = r3.angle_     // Catch:{ Throwable -> 0x031c }
                r1.showangle = r3     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                double r3 = r2.mLastPress     // Catch:{ Throwable -> 0x031c }
                r1.pressure = r3     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                r3 = r106
                r3.a(r2, r8, r1)     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r1 = new com.autonavi.indoor.pdr.MatStepData     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r4 = r6.g     // Catch:{ Throwable -> 0x024f }
                int r14 = r4.step_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r4 = r6.g     // Catch:{ Throwable -> 0x024f }
                double r4 = r4.angle_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x024f }
                double[] r7 = r7.mat_data_     // Catch:{ Throwable -> 0x024f }
                r17 = r7[r10]     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x024f }
                double[] r7 = r7.mat_data_     // Catch:{ Throwable -> 0x024f }
                r8 = 1
                r19 = r7[r8]     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x024f }
                double[] r7 = r7.mat_data_     // Catch:{ Throwable -> 0x024f }
                r21 = r7[r12]     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x024f }
                double r7 = r7.q1_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r9 = r6.g     // Catch:{ Throwable -> 0x024f }
                double r9 = r9.q2_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r11 = r6.g     // Catch:{ Throwable -> 0x024f }
                double r11 = r11.q3_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x024f }
                r72 = r2
                double r2 = r13.q4_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r73 = r2
                double r2 = r13.rotate_rate_x_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r75 = r2
                double r2 = r13.rotate_rate_y_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r77 = r2
                double r2 = r13.rotate_rate_z_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r79 = r2
                double r2 = r13.step_len_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r81 = r2
                double r2 = r13.step_len_f_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r83 = r2
                double r2 = r13.step_len_v_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                int r15 = r13.move_direction_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r85 = r2
                double r2 = r13.angle_no_mag_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r87 = r2
                double r2 = r13.move_state_score_     // Catch:{ Throwable -> 0x031c }
                r13 = r1
                r43 = r15
                r15 = r4
                r23 = r7
                r25 = r9
                r27 = r11
                r29 = r73
                r31 = r75
                r33 = r77
                r35 = r79
                r37 = r81
                r39 = r83
                r41 = r85
                r44 = r87
                r46 = r2
                r13.<init>(r14, r15, r17, r19, r21, r23, r25, r27, r29, r31, r33, r35, r37, r39, r41, r43, r44, r46)     // Catch:{ Throwable -> 0x031c }
                r2 = r72
                r2.e = r1     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r3 = r6.g     // Catch:{ Throwable -> 0x031c }
                long r3 = r3.timestamp_     // Catch:{ Throwable -> 0x031c }
                r1.timestamp_ = r3     // Catch:{ Throwable -> 0x031c }
                goto L_0x0303
            L_0x024f:
                r0 = move-exception
                r1 = r0
                r4 = r3
                goto L_0x03b6
            L_0x0254:
                r67 = r3
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                int r1 = r1.step_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r3 = r6.g     // Catch:{ Throwable -> 0x031c }
                int r3 = r3.step_     // Catch:{ Throwable -> 0x031c }
                if (r1 == r3) goto L_0x0303
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r3 = r6.g     // Catch:{ Throwable -> 0x031c }
                double r3 = r3.angle_     // Catch:{ Throwable -> 0x031c }
                r1.showangle = r3     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                double r3 = r2.mLastPress     // Catch:{ Throwable -> 0x031c }
                r1.pressure = r3     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                r3 = r106
                r3.a(r2, r8, r1)     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r1 = new com.autonavi.indoor.pdr.MatStepData     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r4 = r6.g     // Catch:{ Throwable -> 0x024f }
                int r14 = r4.step_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r4 = r6.g     // Catch:{ Throwable -> 0x024f }
                double r4 = r4.angle_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x024f }
                double[] r7 = r7.mat_data_     // Catch:{ Throwable -> 0x024f }
                r17 = r7[r10]     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x024f }
                double[] r7 = r7.mat_data_     // Catch:{ Throwable -> 0x024f }
                r8 = 1
                r19 = r7[r8]     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x024f }
                double[] r7 = r7.mat_data_     // Catch:{ Throwable -> 0x024f }
                r21 = r7[r12]     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r7 = r6.g     // Catch:{ Throwable -> 0x024f }
                double r7 = r7.q1_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r9 = r6.g     // Catch:{ Throwable -> 0x024f }
                double r9 = r9.q2_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r11 = r6.g     // Catch:{ Throwable -> 0x024f }
                double r11 = r11.q3_     // Catch:{ Throwable -> 0x024f }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x024f }
                r89 = r2
                double r2 = r13.q4_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r90 = r2
                double r2 = r13.rotate_rate_x_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r92 = r2
                double r2 = r13.rotate_rate_y_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r94 = r2
                double r2 = r13.rotate_rate_z_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r96 = r2
                double r2 = r13.step_len_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r98 = r2
                double r2 = r13.step_len_f_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r100 = r2
                double r2 = r13.step_len_v_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                int r15 = r13.move_direction_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r102 = r2
                double r2 = r13.angle_no_mag_     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r13 = r6.g     // Catch:{ Throwable -> 0x031c }
                r104 = r2
                double r2 = r13.move_state_score_     // Catch:{ Throwable -> 0x031c }
                r13 = r1
                r43 = r15
                r15 = r4
                r23 = r7
                r25 = r9
                r27 = r11
                r29 = r90
                r31 = r92
                r33 = r94
                r35 = r96
                r37 = r98
                r39 = r100
                r41 = r102
                r44 = r104
                r46 = r2
                r13.<init>(r14, r15, r17, r19, r21, r23, r25, r27, r29, r31, r33, r35, r37, r39, r41, r43, r44, r46)     // Catch:{ Throwable -> 0x031c }
                r2 = r89
                r2.e = r1     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r1 = r2.e     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.pdr.MatStepData r3 = r6.g     // Catch:{ Throwable -> 0x031c }
                long r3 = r3.timestamp_     // Catch:{ Throwable -> 0x031c }
                r1.timestamp_ = r3     // Catch:{ Throwable -> 0x031c }
            L_0x0303:
                int r10 = r67 * 5
            L_0x0305:
                com.autonavi.indoor.pdr.MatStepData r1 = r6.g     // Catch:{ Throwable -> 0x031c }
                double r3 = r1.angle_     // Catch:{ Throwable -> 0x031c }
                r2.mLastAngle = r3     // Catch:{ Throwable -> 0x031c }
                goto L_0x0318
            L_0x030c:
                r67 = r3
                int r1 = r6.b     // Catch:{ Throwable -> 0x03ad }
                if (r1 != r12) goto L_0x0322
                int r10 = r67 * 2
                double r3 = r6.e     // Catch:{ Throwable -> 0x031c }
                r2.mLastPress = r3     // Catch:{ Throwable -> 0x031c }
            L_0x0318:
                r4 = r106
                goto L_0x0394
            L_0x031c:
                r0 = move-exception
                r1 = r0
                r4 = r106
                goto L_0x03b6
            L_0x0322:
                int r1 = r6.b     // Catch:{ Throwable -> 0x03ad }
                r3 = 3
                if (r1 != r3) goto L_0x0318
                java.util.List<com.autonavi.indoor.entity.ScanPair> r1 = r6.f     // Catch:{ Throwable -> 0x03ad }
                int r1 = r1.size()     // Catch:{ Throwable -> 0x03ad }
                if (r1 <= 0) goto L_0x0318
                java.util.List<com.autonavi.indoor.entity.ScanPair> r1 = r6.f     // Catch:{ Throwable -> 0x03ad }
                java.lang.Object r1 = r1.get(r10)     // Catch:{ Throwable -> 0x03ad }
                com.autonavi.indoor.entity.ScanPair r1 = (com.autonavi.indoor.entity.ScanPair) r1     // Catch:{ Throwable -> 0x03ad }
                java.lang.String r1 = r1.mID     // Catch:{ Throwable -> 0x03ad }
                int r1 = r1.length()     // Catch:{ Throwable -> 0x03ad }
                if (r1 <= r3) goto L_0x0318
                java.util.List<com.autonavi.indoor.entity.ScanPair> r1 = r6.f     // Catch:{ Throwable -> 0x03ad }
                java.lang.Object r1 = r1.get(r10)     // Catch:{ Throwable -> 0x03ad }
                com.autonavi.indoor.entity.ScanPair r1 = (com.autonavi.indoor.entity.ScanPair) r1     // Catch:{ Throwable -> 0x03ad }
                java.lang.String r1 = r1.mID     // Catch:{ Throwable -> 0x03ad }
                char r1 = r1.charAt(r12)     // Catch:{ Throwable -> 0x03ad }
                r3 = 95
                r4 = 1201(0x4b1, float:1.683E-42)
                if (r1 != r3) goto L_0x0356
                r1 = 1201(0x4b1, float:1.683E-42)
                goto L_0x0359
            L_0x0356:
                r1 = 1202(0x4b2, float:1.684E-42)
                r10 = 1
            L_0x0359:
                boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x03ad }
                if (r3 == 0) goto L_0x0384
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x031c }
                java.lang.String r7 = "type:"
                r3.<init>(r7)     // Catch:{ Throwable -> 0x031c }
                if (r1 != r4) goto L_0x036a
                java.lang.String r4 = "WIFI"
                goto L_0x036c
            L_0x036a:
                java.lang.String r4 = "BLE"
            L_0x036c:
                r3.append(r4)     // Catch:{ Throwable -> 0x031c }
                java.lang.String r4 = ", mScandataList:"
                r3.append(r4)     // Catch:{ Throwable -> 0x031c }
                java.util.List<com.autonavi.indoor.entity.ScanPair> r4 = r6.f     // Catch:{ Throwable -> 0x031c }
                int r4 = r4.size()     // Catch:{ Throwable -> 0x031c }
                r3.append(r4)     // Catch:{ Throwable -> 0x031c }
                java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x031c }
                com.autonavi.indoor.util.L.d(r3)     // Catch:{ Throwable -> 0x031c }
            L_0x0384:
                com.autonavi.indoor.entity.ScanData r3 = new com.autonavi.indoor.entity.ScanData     // Catch:{ Throwable -> 0x03ad }
                long r7 = r6.a     // Catch:{ Throwable -> 0x03ad }
                java.util.List<com.autonavi.indoor.entity.ScanPair> r4 = r6.f     // Catch:{ Throwable -> 0x03ad }
                r3.<init>(r7, r10, r4)     // Catch:{ Throwable -> 0x03ad }
                r4 = r106
                r4.a(r2, r1, r3)     // Catch:{ Throwable -> 0x03ab }
                int r10 = r67 * 10
            L_0x0394:
                android.os.Handler r1 = r2.mInnerHandler     // Catch:{ Throwable -> 0x03ab }
                if (r1 == 0) goto L_0x03a4
                android.os.Handler r1 = r2.mInnerHandler     // Catch:{ Throwable -> 0x03ab }
                long r5 = (long) r10     // Catch:{ Throwable -> 0x03ab }
                r3 = 1200(0x4b0, float:1.682E-42)
                r1.sendEmptyMessageDelayed(r3, r5)     // Catch:{ Throwable -> 0x03ab }
            L_0x03a4:
                int r1 = r2.b     // Catch:{ Throwable -> 0x03ab }
                r3 = 1
                int r1 = r1 + r3
                r2.b = r1     // Catch:{ Throwable -> 0x03ab }
                goto L_0x03b2
            L_0x03ab:
                r0 = move-exception
                goto L_0x03b5
            L_0x03ad:
                r0 = move-exception
                r4 = r106
                goto L_0x03b5
            L_0x03b1:
                r4 = r1
            L_0x03b2:
                return
            L_0x03b3:
                r0 = move-exception
                r4 = r1
            L_0x03b5:
                r1 = r0
            L_0x03b6:
                boolean r2 = com.autonavi.indoor.util.L.isLogging
                if (r2 == 0) goto L_0x03bd
                com.autonavi.indoor.util.L.d(r1)
            L_0x03bd:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.simulator.FileDataProvider.b.handleMessage(android.os.Message):void");
        }

        /* access modifiers changed from: 0000 */
        public void a(FileDataProvider fileDataProvider, int i, Object obj) {
            synchronized (this) {
                Iterator it = fileDataProvider.h.iterator();
                while (it.hasNext()) {
                    Handler handler = (Handler) it.next();
                    Message obtainMessage = handler.obtainMessage(i);
                    obtainMessage.obj = obj;
                    handler.sendMessage(obtainMessage);
                }
            }
        }
    }

    protected FileDataProvider() {
    }

    public static FileDataProvider getInstance() {
        if (f == null) {
            synchronized (FileDataProvider.class) {
                try {
                    if (f == null) {
                        f = new FileDataProvider();
                    }
                }
            }
        }
        return f;
    }

    /* JADX WARNING: Removed duplicated region for block: B:67:0x033e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean loadData(java.lang.String r100) {
        /*
            r99 = this;
            r1 = r99
            r2 = 0
            java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r3 = r1.a     // Catch:{ IOException -> 0x0338 }
            r3.clear()     // Catch:{ IOException -> 0x0338 }
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ IOException -> 0x0338 }
            if (r3 == 0) goto L_0x0019
            java.lang.String r3 = "load Data : "
            java.lang.String r4 = java.lang.String.valueOf(r100)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ IOException -> 0x0338 }
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ IOException -> 0x0338 }
        L_0x0019:
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x0338 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0338 }
            r5 = r100
            r4.<init>(r5)     // Catch:{ IOException -> 0x0338 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x0338 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0338 }
            r4.<init>(r3)     // Catch:{ IOException -> 0x0338 }
            boolean r5 = r3.ready()     // Catch:{ IOException -> 0x0338 }
            if (r5 != 0) goto L_0x003a
            boolean r5 = com.autonavi.indoor.util.L.isLogging     // Catch:{ IOException -> 0x0338 }
            if (r5 == 0) goto L_0x003a
            java.lang.String r5 = "打开Log文件失败"
            com.autonavi.indoor.util.L.d(r5)     // Catch:{ IOException -> 0x0338 }
        L_0x003a:
            java.lang.String r5 = r4.readLine()     // Catch:{ IOException -> 0x0338 }
            r6 = 1
            if (r5 == 0) goto L_0x02f8
            int r7 = r5.length()     // Catch:{ IOException -> 0x0338 }
            r8 = 3
            if (r7 < r8) goto L_0x003a
            r5.substring(r2, r8)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r7 = r5.substring(r2, r8)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r9 = "odo"
            boolean r7 = r7.equals(r9)     // Catch:{ IOException -> 0x0338 }
            r9 = 2
            r10 = 4
            if (r7 == 0) goto L_0x0085
            java.lang.String r5 = r5.substring(r10)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r7 = ","
            java.lang.String[] r5 = r5.split(r7)     // Catch:{ IOException -> 0x0338 }
            int r7 = r5.length     // Catch:{ IOException -> 0x0338 }
            if (r7 != r8) goto L_0x003a
            java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r7 = r1.a     // Catch:{ IOException -> 0x0338 }
            com.autonavi.indoor.simulator.FileDataProvider$a r8 = new com.autonavi.indoor.simulator.FileDataProvider$a     // Catch:{ IOException -> 0x0338 }
            r10 = r5[r2]     // Catch:{ IOException -> 0x0338 }
            long r11 = java.lang.Long.parseLong(r10)     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            int r13 = java.lang.Integer.parseInt(r6)     // Catch:{ IOException -> 0x0338 }
            r5 = r5[r9]     // Catch:{ IOException -> 0x0338 }
            float r5 = java.lang.Float.parseFloat(r5)     // Catch:{ IOException -> 0x0338 }
            double r14 = (double) r5     // Catch:{ IOException -> 0x0338 }
            r10 = r8
            r10.<init>(r11, r13, r14)     // Catch:{ IOException -> 0x0338 }
            r7.add(r8)     // Catch:{ IOException -> 0x0338 }
            goto L_0x003a
        L_0x0085:
            java.lang.String r7 = r5.substring(r2, r8)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r11 = "pre"
            boolean r7 = r7.equals(r11)     // Catch:{ IOException -> 0x0338 }
            if (r7 == 0) goto L_0x00b6
            java.lang.String r5 = r5.substring(r10)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r7 = ","
            java.lang.String[] r5 = r5.split(r7)     // Catch:{ IOException -> 0x0338 }
            int r7 = r5.length     // Catch:{ IOException -> 0x0338 }
            if (r7 != r9) goto L_0x003a
            java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r7 = r1.a     // Catch:{ IOException -> 0x0338 }
            com.autonavi.indoor.simulator.FileDataProvider$a r8 = new com.autonavi.indoor.simulator.FileDataProvider$a     // Catch:{ IOException -> 0x0338 }
            r9 = r5[r2]     // Catch:{ IOException -> 0x0338 }
            long r9 = java.lang.Long.parseLong(r9)     // Catch:{ IOException -> 0x0338 }
            r5 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            float r5 = java.lang.Float.parseFloat(r5)     // Catch:{ IOException -> 0x0338 }
            double r5 = (double) r5     // Catch:{ IOException -> 0x0338 }
            r8.<init>(r9, r5)     // Catch:{ IOException -> 0x0338 }
            r7.add(r8)     // Catch:{ IOException -> 0x0338 }
            goto L_0x003a
        L_0x00b6:
            java.lang.String r7 = r5.substring(r2, r8)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r11 = "ble"
            boolean r7 = r7.equals(r11)     // Catch:{ IOException -> 0x0338 }
            if (r7 == 0) goto L_0x0110
            java.lang.String r5 = r5.substring(r10)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r7 = ","
            java.lang.String[] r5 = r5.split(r7)     // Catch:{ IOException -> 0x0338 }
            int r7 = r5.length     // Catch:{ IOException -> 0x0338 }
            if (r7 != r9) goto L_0x003a
            r7 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            java.lang.String r8 = "\\$"
            java.lang.String[] r7 = r7.split(r8)     // Catch:{ IOException -> 0x0338 }
            java.util.ArrayList r8 = new java.util.ArrayList     // Catch:{ IOException -> 0x0338 }
            r8.<init>()     // Catch:{ IOException -> 0x0338 }
            r10 = 0
        L_0x00dd:
            int r11 = r7.length     // Catch:{ IOException -> 0x0338 }
            if (r10 >= r11) goto L_0x00fe
            r11 = r7[r10]     // Catch:{ IOException -> 0x0338 }
            java.lang.String r12 = "#"
            java.lang.String[] r11 = r11.split(r12)     // Catch:{ IOException -> 0x0338 }
            int r12 = r11.length     // Catch:{ IOException -> 0x0338 }
            if (r12 != r9) goto L_0x00fb
            com.autonavi.indoor.entity.ScanPair r12 = new com.autonavi.indoor.entity.ScanPair     // Catch:{ IOException -> 0x0338 }
            r13 = r11[r2]     // Catch:{ IOException -> 0x0338 }
            r11 = r11[r6]     // Catch:{ IOException -> 0x0338 }
            int r11 = java.lang.Integer.parseInt(r11)     // Catch:{ IOException -> 0x0338 }
            r12.<init>(r13, r11)     // Catch:{ IOException -> 0x0338 }
            r8.add(r12)     // Catch:{ IOException -> 0x0338 }
        L_0x00fb:
            int r10 = r10 + 1
            goto L_0x00dd
        L_0x00fe:
            java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r6 = r1.a     // Catch:{ IOException -> 0x0338 }
            com.autonavi.indoor.simulator.FileDataProvider$a r7 = new com.autonavi.indoor.simulator.FileDataProvider$a     // Catch:{ IOException -> 0x0338 }
            r5 = r5[r2]     // Catch:{ IOException -> 0x0338 }
            long r9 = java.lang.Long.parseLong(r5)     // Catch:{ IOException -> 0x0338 }
            r7.<init>(r9, r8)     // Catch:{ IOException -> 0x0338 }
            r6.add(r7)     // Catch:{ IOException -> 0x0338 }
            goto L_0x003a
        L_0x0110:
            java.lang.String r7 = r5.substring(r2, r8)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r11 = "mat"
            boolean r7 = r7.equals(r11)     // Catch:{ IOException -> 0x0338 }
            if (r7 == 0) goto L_0x02ed
            java.lang.String r5 = r5.substring(r10)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r7 = ","
            java.lang.String[] r5 = r5.split(r7)     // Catch:{ IOException -> 0x0338 }
            int r7 = r5.length     // Catch:{ IOException -> 0x0338 }
            r16 = 7
            r17 = 6
            r18 = 5
            r19 = 16
            r11 = 18
            if (r7 != r11) goto L_0x0206
            r7 = r5[r19]     // Catch:{ IOException -> 0x0338 }
            float r7 = java.lang.Float.parseFloat(r7)     // Catch:{ IOException -> 0x0338 }
            double r12 = (double) r7     // Catch:{ IOException -> 0x0338 }
            com.autonavi.indoor.simulator.FileDataProvider$a r7 = new com.autonavi.indoor.simulator.FileDataProvider$a     // Catch:{ IOException -> 0x0338 }
            r11 = r5[r2]     // Catch:{ IOException -> 0x0338 }
            long r21 = java.lang.Long.parseLong(r11)     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            int r23 = java.lang.Integer.parseInt(r6)     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r9]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r57 = r3
            double r2 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r8]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            double r8 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r10]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            double r10 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r18]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            double r14 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r17]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r58 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r16]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r60 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = 8
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r62 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = 9
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r64 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = 10
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r66 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = 11
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r68 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = 12
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r70 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = 13
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r72 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = 14
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r74 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = 15
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r76 = r12
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = 17
            r5 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            int r52 = java.lang.Integer.parseInt(r5)     // Catch:{ IOException -> 0x0338 }
            r55 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r20 = r7
            r24 = r2
            r26 = r8
            r28 = r10
            r30 = r14
            r32 = r60
            r34 = r62
            r36 = r64
            r38 = r66
            r40 = r68
            r42 = r70
            r44 = r72
            r46 = r74
            r48 = r76
            r50 = r12
            r53 = r58
            r20.<init>(r21, r23, r24, r26, r28, r30, r32, r34, r36, r38, r40, r42, r44, r46, r48, r50, r52, r53, r55)     // Catch:{ IOException -> 0x0338 }
            java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r2 = r1.a     // Catch:{ IOException -> 0x0338 }
            r2.add(r7)     // Catch:{ IOException -> 0x0338 }
            r3 = r57
            goto L_0x02f5
        L_0x0206:
            r57 = r3
            int r2 = r5.length     // Catch:{ IOException -> 0x0338 }
            r3 = 19
            if (r2 != r3) goto L_0x02ef
            r2 = r5[r19]     // Catch:{ IOException -> 0x0338 }
            float r2 = java.lang.Float.parseFloat(r2)     // Catch:{ IOException -> 0x0338 }
            double r2 = (double) r2     // Catch:{ IOException -> 0x0338 }
            com.autonavi.indoor.simulator.FileDataProvider$a r7 = new com.autonavi.indoor.simulator.FileDataProvider$a     // Catch:{ IOException -> 0x0338 }
            r12 = 0
            r13 = r5[r12]     // Catch:{ IOException -> 0x0338 }
            long r21 = java.lang.Long.parseLong(r13)     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r6]     // Catch:{ IOException -> 0x0338 }
            int r23 = java.lang.Integer.parseInt(r6)     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r9]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            double r12 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r8]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            double r8 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r10]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            double r14 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r18]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r78 = r12
            double r11 = (double) r6     // Catch:{ IOException -> 0x0338 }
            r6 = r5[r17]     // Catch:{ IOException -> 0x0338 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x0338 }
            r80 = r2
            double r1 = (double) r6
            r3 = r5[r16]     // Catch:{ IOException -> 0x02e9 }
            float r3 = java.lang.Float.parseFloat(r3)     // Catch:{ IOException -> 0x02e9 }
            r82 = r4
            double r3 = (double) r3     // Catch:{ IOException -> 0x02e9 }
            r6 = 8
            r6 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x02e9 }
            r83 = r3
            double r3 = (double) r6     // Catch:{ IOException -> 0x02e9 }
            r6 = 9
            r6 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x02e9 }
            r85 = r3
            double r3 = (double) r6     // Catch:{ IOException -> 0x02e9 }
            r6 = 10
            r6 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x02e9 }
            r87 = r3
            double r3 = (double) r6     // Catch:{ IOException -> 0x02e9 }
            r6 = 11
            r6 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x02e9 }
            r89 = r3
            double r3 = (double) r6     // Catch:{ IOException -> 0x02e9 }
            r6 = 12
            r6 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x02e9 }
            r91 = r3
            double r3 = (double) r6     // Catch:{ IOException -> 0x02e9 }
            r6 = 13
            r6 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x02e9 }
            r93 = r3
            double r3 = (double) r6     // Catch:{ IOException -> 0x02e9 }
            r6 = 14
            r6 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x02e9 }
            r95 = r3
            double r3 = (double) r6     // Catch:{ IOException -> 0x02e9 }
            r6 = 15
            r6 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            float r6 = java.lang.Float.parseFloat(r6)     // Catch:{ IOException -> 0x02e9 }
            r97 = r3
            double r3 = (double) r6     // Catch:{ IOException -> 0x02e9 }
            r6 = 17
            r6 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            int r52 = java.lang.Integer.parseInt(r6)     // Catch:{ IOException -> 0x02e9 }
            r6 = 18
            r5 = r5[r6]     // Catch:{ IOException -> 0x02e9 }
            float r5 = java.lang.Float.parseFloat(r5)     // Catch:{ IOException -> 0x02e9 }
            double r5 = (double) r5     // Catch:{ IOException -> 0x02e9 }
            r20 = r7
            r24 = r78
            r26 = r8
            r28 = r14
            r30 = r11
            r32 = r1
            r34 = r83
            r36 = r85
            r38 = r87
            r40 = r89
            r42 = r91
            r44 = r93
            r46 = r95
            r48 = r97
            r50 = r3
            r53 = r80
            r55 = r5
            r20.<init>(r21, r23, r24, r26, r28, r30, r32, r34, r36, r38, r40, r42, r44, r46, r48, r50, r52, r53, r55)     // Catch:{ IOException -> 0x02e9 }
            r1 = r99
            java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r2 = r1.a     // Catch:{ IOException -> 0x0338 }
            r2.add(r7)     // Catch:{ IOException -> 0x0338 }
            goto L_0x02f1
        L_0x02e9:
            r0 = move-exception
            r1 = r99
            goto L_0x0339
        L_0x02ed:
            r57 = r3
        L_0x02ef:
            r82 = r4
        L_0x02f1:
            r3 = r57
            r4 = r82
        L_0x02f5:
            r2 = 0
            goto L_0x003a
        L_0x02f8:
            r57 = r3
            r82 = r4
            r1.b = r2     // Catch:{ IOException -> 0x0338 }
            java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r3 = r1.a     // Catch:{ IOException -> 0x0338 }
            int r3 = r3.size()     // Catch:{ IOException -> 0x0338 }
            if (r3 <= 0) goto L_0x0312
            java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r3 = r1.a     // Catch:{ IOException -> 0x0338 }
            java.lang.Object r3 = r3.get(r2)     // Catch:{ IOException -> 0x0338 }
            com.autonavi.indoor.simulator.FileDataProvider$a r3 = (com.autonavi.indoor.simulator.FileDataProvider.a) r3     // Catch:{ IOException -> 0x0338 }
            long r2 = r3.a     // Catch:{ IOException -> 0x0338 }
            r1.c = r2     // Catch:{ IOException -> 0x0338 }
        L_0x0312:
            boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ IOException -> 0x0338 }
            if (r2 == 0) goto L_0x032d
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0338 }
            java.lang.String r3 = "load Data end: "
            r2.<init>(r3)     // Catch:{ IOException -> 0x0338 }
            java.util.List<com.autonavi.indoor.simulator.FileDataProvider$a> r3 = r1.a     // Catch:{ IOException -> 0x0338 }
            int r3 = r3.size()     // Catch:{ IOException -> 0x0338 }
            r2.append(r3)     // Catch:{ IOException -> 0x0338 }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x0338 }
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ IOException -> 0x0338 }
        L_0x032d:
            r2 = r82
            r2.close()     // Catch:{ IOException -> 0x0338 }
            r2 = r57
            r2.close()     // Catch:{ IOException -> 0x0338 }
            return r6
        L_0x0338:
            r0 = move-exception
        L_0x0339:
            r2 = r0
            boolean r3 = com.autonavi.indoor.util.L.isLogging
            if (r3 == 0) goto L_0x0341
            com.autonavi.indoor.util.L.d(r2)
        L_0x0341:
            r2 = 0
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.simulator.FileDataProvider.loadData(java.lang.String):boolean");
    }

    public int init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("FileDataProvider mConfiguration can not be initialized with null");
        }
        if (this.mContext == null) {
            if (L.isLogging) {
                L.d((String) "Initialize FileDataProvider.");
            }
            this.mContext = context;
        } else if (L.isLogging) {
            L.d((String) "Try to initialize FileDataProvider which had already been initialized before. To re-init FileDataProvider with new mConfiguration call FileDataProvider.destroy() at first.");
        }
        return 0;
    }

    public void registerListener(Handler handler) {
        checkConfiguration();
        if (L.isLogging) {
            L.d("registerListener: ".concat(String.valueOf(handler)));
        }
        synchronized (this) {
            if (handler != null) {
                try {
                    if (this.h.indexOf(handler) == -1) {
                        this.h.add(handler);
                        if (this.h.size() > 0 && !this.g) {
                            start();
                        }
                    }
                } finally {
                }
            }
            if (L.isLogging) {
                L.d((String) "Handler already exist");
            }
            start();
        }
    }

    public void unregisterListener(Handler handler) {
        checkConfiguration();
        synchronized (this) {
            this.h.remove(handler);
            if (this.h.isEmpty()) {
                stop();
            }
        }
    }

    public void setSimulatFile(String str) {
        this.d = str;
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder("setSimulatFile:");
            sb.append(this.d);
            L.d(sb.toString());
        }
    }

    public int start() {
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder("start FileDataProvider:");
            sb.append(this.d);
            L.d(sb.toString());
        }
        this.b = 0;
        if (!loadData(this.d)) {
            throw new IllegalArgumentException("FileDataProvider Load simulate file error.");
        }
        this.mInnerHandler = new b(this);
        this.mInnerHandler.sendEmptyMessageDelayed(1200, 2000);
        this.g = true;
        this.e = null;
        return 0;
    }

    public boolean stop() {
        if (!this.g) {
            return true;
        }
        try {
            if (L.isLogging) {
                L.d((String) " STOP FileDataProvider Scan");
            }
            this.mInnerHandler.removeMessages(1200);
            this.a.clear();
            this.b = 0;
            this.g = false;
            this.mInnerHandler = null;
            return true;
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
            return false;
        }
    }
}
