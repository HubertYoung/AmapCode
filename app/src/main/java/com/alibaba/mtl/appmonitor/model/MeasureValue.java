package com.alibaba.mtl.appmonitor.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.pool.Reusable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeasureValue implements Parcelable, Reusable, IMerge<MeasureValue> {
    public static final Creator<MeasureValue> CREATOR = new Creator<MeasureValue>() {
        public final MeasureValue createFromParcel(Parcel parcel) {
            return MeasureValue.readFromParcel(parcel);
        }

        public final MeasureValue[] newArray(int i) {
            return new MeasureValue[i];
        }
    };
    private List<Bucket> buckets;
    private boolean finish;
    private Double offset;
    private double value;

    class Bucket {
        /* access modifiers changed from: private */
        public long count = 0;
        /* access modifiers changed from: private */
        public Double max;
        /* access modifiers changed from: private */
        public Double min;

        public Bucket(Double d, Double d2) {
            this.min = d;
            this.max = d2;
        }

        public boolean in(Double d) {
            if (d == null) {
                return false;
            }
            Double d2 = this.min;
            Double d3 = this.max;
            if (d2 == null) {
                d2 = Double.valueOf(Double.MIN_VALUE);
            }
            if (d3 == null) {
                d3 = Double.valueOf(Double.MAX_VALUE);
            }
            if (d.doubleValue() < d2.doubleValue() || d.doubleValue() >= d3.doubleValue()) {
                return false;
            }
            return true;
        }

        public void increase() {
            this.count++;
        }
    }

    public int describeContents() {
        return 0;
    }

    @Deprecated
    public MeasureValue() {
    }

    @Deprecated
    public MeasureValue(double d) {
        this.value = d;
    }

    @Deprecated
    public MeasureValue(double d, double d2) {
        this.offset = Double.valueOf(d2);
        this.value = d;
        this.finish = false;
    }

    public static MeasureValue create() {
        return (MeasureValue) BalancedPool.getInstance().poll(MeasureValue.class, new Object[0]);
    }

    public static MeasureValue create(double d) {
        return (MeasureValue) BalancedPool.getInstance().poll(MeasureValue.class, Double.valueOf(d));
    }

    public static MeasureValue create(double d, double d2) {
        return (MeasureValue) BalancedPool.getInstance().poll(MeasureValue.class, Double.valueOf(d), Double.valueOf(d2));
    }

    public Double getOffset() {
        return this.offset;
    }

    public boolean isFinish() {
        return this.finish;
    }

    public void setFinish(boolean z) {
        this.finish = z;
    }

    public void setOffset(double d) {
        this.offset = Double.valueOf(d);
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double d) {
        this.value = d;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0045, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void merge(com.alibaba.mtl.appmonitor.model.MeasureValue r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            if (r6 != 0) goto L_0x0005
            monitor-exit(r5)
            return
        L_0x0005:
            double r0 = r5.value     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            double r2 = r6.getValue()     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            r4 = 0
            double r0 = r0 + r2
            r5.value = r0     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            java.lang.Double r0 = r6.getOffset()     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            if (r0 == 0) goto L_0x0037
            java.lang.Double r0 = r5.offset     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            if (r0 != 0) goto L_0x0021
            r0 = 0
            java.lang.Double r0 = java.lang.Double.valueOf(r0)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            r5.offset = r0     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
        L_0x0021:
            java.lang.Double r0 = r5.offset     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            double r0 = r0.doubleValue()     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            java.lang.Double r2 = r6.getOffset()     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            double r2 = r2.doubleValue()     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            r4 = 0
            double r0 = r0 + r2
            java.lang.Double r0 = java.lang.Double.valueOf(r0)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            r5.offset = r0     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
        L_0x0037:
            double r0 = r6.getValue()     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            com.alibaba.mtl.appmonitor.model.MeasureValue$Bucket r6 = r5.getBucket(r0)     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
            if (r6 == 0) goto L_0x0044
            r6.increase()     // Catch:{ Throwable -> 0x0049, all -> 0x0046 }
        L_0x0044:
            monitor-exit(r5)
            return
        L_0x0046:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        L_0x0049:
            monitor-exit(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.mtl.appmonitor.model.MeasureValue.merge(com.alibaba.mtl.appmonitor.model.MeasureValue):void");
    }

    public synchronized void clean() {
        this.value = 0.0d;
        this.offset = null;
        this.finish = false;
        this.buckets = null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void fill(java.lang.Object... r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 != 0) goto L_0x0005
            monitor-exit(r4)
            return
        L_0x0005:
            int r0 = r5.length     // Catch:{ all -> 0x0021 }
            r1 = 0
            if (r0 <= 0) goto L_0x0013
            r0 = r5[r1]     // Catch:{ all -> 0x0021 }
            java.lang.Double r0 = (java.lang.Double) r0     // Catch:{ all -> 0x0021 }
            double r2 = r0.doubleValue()     // Catch:{ all -> 0x0021 }
            r4.value = r2     // Catch:{ all -> 0x0021 }
        L_0x0013:
            int r0 = r5.length     // Catch:{ all -> 0x0021 }
            r2 = 1
            if (r0 <= r2) goto L_0x001f
            r5 = r5[r2]     // Catch:{ all -> 0x0021 }
            java.lang.Double r5 = (java.lang.Double) r5     // Catch:{ all -> 0x0021 }
            r4.offset = r5     // Catch:{ all -> 0x0021 }
            r4.finish = r1     // Catch:{ all -> 0x0021 }
        L_0x001f:
            monitor-exit(r4)
            return
        L_0x0021:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.mtl.appmonitor.model.MeasureValue.fill(java.lang.Object[]):void");
    }

    public void writeToParcel(Parcel parcel, int i) {
        try {
            parcel.writeInt(this.finish ? 1 : 0);
            parcel.writeDouble(this.offset == null ? 0.0d : this.offset.doubleValue());
            parcel.writeDouble(this.value);
        } catch (Throwable unused) {
        }
    }

    static MeasureValue readFromParcel(Parcel parcel) {
        MeasureValue measureValue = null;
        try {
            boolean z = parcel.readInt() != 0;
            Double valueOf = Double.valueOf(parcel.readDouble());
            double readDouble = parcel.readDouble();
            MeasureValue create = create();
            try {
                create.finish = z;
                create.offset = valueOf;
                create.value = readDouble;
                return create;
            } catch (Throwable th) {
                Throwable th2 = th;
                measureValue = create;
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            th.printStackTrace();
            return measureValue;
        }
    }

    public synchronized Map<String, Double> getBuckets() {
        if (this.buckets == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (Bucket next : this.buckets) {
            if (next.count > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(next.min == null ? "-∞" : next.min);
                sb.append(",");
                sb.append(next.max == null ? "∞" : next.max);
                hashMap.put(sb.toString(), Long.valueOf(next.count));
            }
        }
        return hashMap;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0049, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void setBuckets(com.alibaba.mtl.appmonitor.model.Measure r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.util.List r6 = r6.getBounds()     // Catch:{ all -> 0x004c }
            if (r6 == 0) goto L_0x004a
            int r0 = r6.size()     // Catch:{ all -> 0x004c }
            r1 = 2
            if (r0 >= r1) goto L_0x000f
            goto L_0x004a
        L_0x000f:
            java.util.List<com.alibaba.mtl.appmonitor.model.MeasureValue$Bucket> r0 = r5.buckets     // Catch:{ all -> 0x004c }
            if (r0 == 0) goto L_0x0015
            monitor-exit(r5)
            return
        L_0x0015:
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x004c }
            r0.<init>()     // Catch:{ all -> 0x004c }
            r5.buckets = r0     // Catch:{ all -> 0x004c }
            r0 = 0
        L_0x001d:
            int r1 = r0 + 1
            int r2 = r6.size()     // Catch:{ all -> 0x004c }
            if (r1 >= r2) goto L_0x003d
            java.util.List<com.alibaba.mtl.appmonitor.model.MeasureValue$Bucket> r2 = r5.buckets     // Catch:{ all -> 0x004c }
            com.alibaba.mtl.appmonitor.model.MeasureValue$Bucket r3 = new com.alibaba.mtl.appmonitor.model.MeasureValue$Bucket     // Catch:{ all -> 0x004c }
            java.lang.Object r0 = r6.get(r0)     // Catch:{ all -> 0x004c }
            java.lang.Double r0 = (java.lang.Double) r0     // Catch:{ all -> 0x004c }
            java.lang.Object r4 = r6.get(r1)     // Catch:{ all -> 0x004c }
            java.lang.Double r4 = (java.lang.Double) r4     // Catch:{ all -> 0x004c }
            r3.<init>(r0, r4)     // Catch:{ all -> 0x004c }
            r2.add(r3)     // Catch:{ all -> 0x004c }
            r0 = r1
            goto L_0x001d
        L_0x003d:
            double r0 = r5.value     // Catch:{ all -> 0x004c }
            com.alibaba.mtl.appmonitor.model.MeasureValue$Bucket r6 = r5.getBucket(r0)     // Catch:{ all -> 0x004c }
            if (r6 == 0) goto L_0x0048
            r6.increase()     // Catch:{ all -> 0x004c }
        L_0x0048:
            monitor-exit(r5)
            return
        L_0x004a:
            monitor-exit(r5)
            return
        L_0x004c:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.mtl.appmonitor.model.MeasureValue.setBuckets(com.alibaba.mtl.appmonitor.model.Measure):void");
    }

    private Bucket getBucket(double d) {
        if (this.buckets != null) {
            for (int i = 0; i < this.buckets.size(); i++) {
                if (this.buckets.get(i).in(Double.valueOf(d))) {
                    return this.buckets.get(i);
                }
            }
        }
        return null;
    }
}
