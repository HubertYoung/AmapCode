package org.eclipse.mat.parser.internal.snapshot;

import java.util.Set;
import org.eclipse.mat.SnapshotException;
import org.eclipse.mat.collect.BitField;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.parser.index.IIndexReader.IOne2ManyIndex;
import org.eclipse.mat.parser.internal.util.IntStack;
import org.eclipse.mat.snapshot.ExcludedReferencesDescriptor;
import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.snapshot.model.NamedReference;
import org.eclipse.mat.util.IProgressListener;
import org.eclipse.mat.util.IProgressListener.OperationCanceledException;

public class ObjectMarker {
    boolean[] bits;
    IOne2ManyIndex outbound;
    IProgressListener progressListener;
    int[] roots;

    public class DfsThread implements Runnable {
        int[] data = new int[10240];
        IntStack rootsStack;
        int size = 0;

        public DfsThread(IntStack intStack) {
            this.rootsStack = intStack;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002f, code lost:
            if (r9.size <= 0) goto L_0x0000;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0031, code lost:
            r0 = r9.data;
            r1 = r9.size - 1;
            r9.size = r1;
            r0 = r9.this$0.outbound.get(r0[r1]);
            r1 = r0.length;
            r3 = 0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
            if (r3 >= r1) goto L_0x002d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0046, code lost:
            r5 = r0[r3];
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x004e, code lost:
            if (r9.this$0.bits[r5] != false) goto L_0x0077;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0050, code lost:
            r9.this$0.bits[r5] = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x005b, code lost:
            if (r9.size != r9.data.length) goto L_0x006d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x005d, code lost:
            r6 = new int[(r9.data.length << 1)];
            java.lang.System.arraycopy(r9.data, 0, r6, 0, r9.data.length);
            r9.data = r6;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x006d, code lost:
            r6 = r9.data;
            r7 = r9.size;
            r9.size = r7 + 1;
            r6[r7] = r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0077, code lost:
            r3 = r3 + 1;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r9 = this;
            L_0x0000:
                org.eclipse.mat.parser.internal.util.IntStack r0 = r9.rootsStack
                monitor-enter(r0)
                org.eclipse.mat.parser.internal.snapshot.ObjectMarker r1 = org.eclipse.mat.parser.internal.snapshot.ObjectMarker.this     // Catch:{ all -> 0x007c }
                org.eclipse.mat.util.IProgressListener r1 = r1.progressListener     // Catch:{ all -> 0x007c }
                r2 = 1
                r1.worked(r2)     // Catch:{ all -> 0x007c }
                org.eclipse.mat.parser.internal.snapshot.ObjectMarker r1 = org.eclipse.mat.parser.internal.snapshot.ObjectMarker.this     // Catch:{ all -> 0x007c }
                org.eclipse.mat.util.IProgressListener r1 = r1.progressListener     // Catch:{ all -> 0x007c }
                boolean r1 = r1.isCanceled()     // Catch:{ all -> 0x007c }
                if (r1 == 0) goto L_0x0017
                monitor-exit(r0)     // Catch:{ all -> 0x007c }
                return
            L_0x0017:
                org.eclipse.mat.parser.internal.util.IntStack r1 = r9.rootsStack     // Catch:{ all -> 0x007c }
                int r1 = r1.size()     // Catch:{ all -> 0x007c }
                if (r1 <= 0) goto L_0x007a
                int[] r1 = r9.data     // Catch:{ all -> 0x007c }
                org.eclipse.mat.parser.internal.util.IntStack r3 = r9.rootsStack     // Catch:{ all -> 0x007c }
                int r3 = r3.pop()     // Catch:{ all -> 0x007c }
                r4 = 0
                r1[r4] = r3     // Catch:{ all -> 0x007c }
                r9.size = r2     // Catch:{ all -> 0x007c }
                monitor-exit(r0)     // Catch:{ all -> 0x007c }
            L_0x002d:
                int r0 = r9.size
                if (r0 <= 0) goto L_0x0000
                int[] r0 = r9.data
                int r1 = r9.size
                int r1 = r1 - r2
                r9.size = r1
                r0 = r0[r1]
                org.eclipse.mat.parser.internal.snapshot.ObjectMarker r1 = org.eclipse.mat.parser.internal.snapshot.ObjectMarker.this
                org.eclipse.mat.parser.index.IIndexReader$IOne2ManyIndex r1 = r1.outbound
                int[] r0 = r1.get(r0)
                int r1 = r0.length
                r3 = 0
            L_0x0044:
                if (r3 >= r1) goto L_0x002d
                r5 = r0[r3]
                org.eclipse.mat.parser.internal.snapshot.ObjectMarker r6 = org.eclipse.mat.parser.internal.snapshot.ObjectMarker.this
                boolean[] r6 = r6.bits
                boolean r6 = r6[r5]
                if (r6 != 0) goto L_0x0077
                org.eclipse.mat.parser.internal.snapshot.ObjectMarker r6 = org.eclipse.mat.parser.internal.snapshot.ObjectMarker.this
                boolean[] r6 = r6.bits
                r6[r5] = r2
                int r6 = r9.size
                int[] r7 = r9.data
                int r7 = r7.length
                if (r6 != r7) goto L_0x006d
                int[] r6 = r9.data
                int r6 = r6.length
                int r6 = r6 << r2
                int[] r6 = new int[r6]
                int[] r7 = r9.data
                int[] r8 = r9.data
                int r8 = r8.length
                java.lang.System.arraycopy(r7, r4, r6, r4, r8)
                r9.data = r6
            L_0x006d:
                int[] r6 = r9.data
                int r7 = r9.size
                int r8 = r7 + 1
                r9.size = r8
                r6[r7] = r5
            L_0x0077:
                int r3 = r3 + 1
                goto L_0x0044
            L_0x007a:
                monitor-exit(r0)     // Catch:{ all -> 0x007c }
                return
            L_0x007c:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x007c }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.internal.snapshot.ObjectMarker.DfsThread.run():void");
        }
    }

    public ObjectMarker(int[] iArr, boolean[] zArr, IOne2ManyIndex iOne2ManyIndex, IProgressListener iProgressListener) {
        this.roots = iArr;
        this.bits = zArr;
        this.outbound = iOne2ManyIndex;
        this.progressListener = iProgressListener;
    }

    public int markSingleThreaded() throws OperationCanceledException {
        int[] iArr;
        int[] iArr2;
        int[] iArr3 = new int[10240];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 : this.roots) {
            if (!this.bits[i4]) {
                if (i2 == iArr3.length) {
                    int[] iArr4 = new int[(iArr3.length << 1)];
                    System.arraycopy(iArr3, 0, iArr4, 0, iArr3.length);
                    iArr3 = iArr4;
                }
                iArr3[i2] = i4;
                this.bits[i4] = true;
                i3++;
                i++;
                i2++;
            }
        }
        this.progressListener.beginTask(Messages.ObjectMarker_CalculateRetainedSize, i);
        while (i2 > 0) {
            i2--;
            int i5 = iArr3[i2];
            if (i2 <= i) {
                i--;
                this.progressListener.worked(1);
                if (this.progressListener.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }
            for (int i6 : this.outbound.get(i5)) {
                if (!this.bits[i6]) {
                    if (i2 == iArr3.length) {
                        int[] iArr5 = new int[(iArr3.length << 1)];
                        System.arraycopy(iArr3, 0, iArr5, 0, iArr3.length);
                        iArr3 = iArr5;
                    }
                    iArr3[i2] = i6;
                    this.bits[i6] = true;
                    i3++;
                    i2++;
                }
            }
        }
        this.progressListener.done();
        return i3;
    }

    public int markSingleThreaded(ExcludedReferencesDescriptor[] excludedReferencesDescriptorArr, ISnapshot iSnapshot) throws SnapshotException, OperationCanceledException {
        int i;
        int[] iArr;
        int i2;
        int[] iArr2;
        boolean z;
        int[] iArr3;
        ExcludedReferencesDescriptor[] excludedReferencesDescriptorArr2 = excludedReferencesDescriptorArr;
        BitField bitField = new BitField(iSnapshot.getSnapshotInfo().getNumberOfObjects());
        for (ExcludedReferencesDescriptor objectIds : excludedReferencesDescriptorArr2) {
            for (int i3 : objectIds.getObjectIds()) {
                bitField.set(i3);
            }
        }
        int[] iArr4 = this.roots;
        int length = iArr4.length;
        int[] iArr5 = new int[10240];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (true) {
            i = 1;
            if (i4 >= length) {
                break;
            }
            int i8 = iArr4[i4];
            if (!this.bits[i8]) {
                if (i6 == iArr5.length) {
                    iArr3 = new int[(iArr5.length << 1)];
                    System.arraycopy(iArr5, 0, iArr3, 0, iArr5.length);
                } else {
                    iArr3 = iArr5;
                }
                iArr3[i6] = i8;
                this.bits[i8] = true;
                i7++;
                i5++;
                i6++;
                iArr5 = iArr3;
            }
            i4++;
        }
        this.progressListener.beginTask(Messages.ObjectMarker_CalculateRetainedSize, i5);
        while (i6 > 0) {
            int i9 = i6 - 1;
            int i10 = iArr5[i9];
            if (i9 <= i5) {
                i5--;
                this.progressListener.worked(i);
                if (this.progressListener.isCanceled()) {
                    throw new OperationCanceledException();
                }
            }
            int i11 = i5;
            int i12 = i7;
            int[] iArr6 = iArr5;
            int i13 = i9;
            int[] iArr7 = iArr6;
            for (int i14 : this.outbound.get(i10)) {
                if (!this.bits[i14]) {
                    iArr2 = iArr7;
                    i2 = i13;
                    if (!refersOnlyThroughExcluded(i10, i14, excludedReferencesDescriptorArr2, bitField, iSnapshot)) {
                        if (i2 == iArr2.length) {
                            z = true;
                            iArr7 = new int[(iArr2.length << 1)];
                            System.arraycopy(iArr2, 0, iArr7, 0, iArr2.length);
                        } else {
                            z = true;
                            iArr7 = iArr2;
                        }
                        i13 = i2 + 1;
                        iArr7[i2] = i14;
                        this.bits[i14] = z;
                        i12++;
                    }
                } else {
                    iArr2 = iArr7;
                    i2 = i13;
                }
                iArr7 = iArr2;
                i13 = i2;
            }
            int i15 = i13;
            iArr5 = iArr7;
            i6 = i15;
            i5 = i11;
            i7 = i12;
            i = 1;
        }
        this.progressListener.done();
        return i7;
    }

    public void markMultiThreaded(int i) throws InterruptedException {
        int[] iArr;
        IntStack intStack = new IntStack(this.roots.length);
        for (int i2 : this.roots) {
            if (!this.bits[i2]) {
                intStack.push(i2);
                this.bits[i2] = true;
            }
        }
        this.progressListener.beginTask(Messages.ObjectMarker_CalculateRetainedSize, intStack.size());
        DfsThread[] dfsThreadArr = new DfsThread[i];
        Thread[] threadArr = new Thread[i];
        int i3 = 0;
        while (i3 < i) {
            DfsThread dfsThread = new DfsThread(intStack);
            dfsThreadArr[i3] = dfsThread;
            StringBuilder sb = new StringBuilder("ObjectMarkerThread-");
            int i4 = i3 + 1;
            sb.append(i4);
            Thread thread = new Thread(dfsThread, sb.toString());
            thread.start();
            threadArr[i3] = thread;
            i3 = i4;
        }
        for (int i5 = 0; i5 < i; i5++) {
            threadArr[i5].join();
        }
        if (!this.progressListener.isCanceled()) {
            this.progressListener.done();
        }
    }

    private boolean refersOnlyThroughExcluded(int i, int i2, ExcludedReferencesDescriptor[] excludedReferencesDescriptorArr, BitField bitField, ISnapshot iSnapshot) throws SnapshotException {
        if (!bitField.get(i)) {
            return false;
        }
        IObject object = iSnapshot.getObject(i);
        Set<String> set = null;
        int length = excludedReferencesDescriptorArr.length;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            ExcludedReferencesDescriptor excludedReferencesDescriptor = excludedReferencesDescriptorArr[i3];
            if (excludedReferencesDescriptor.contains(i)) {
                set = excludedReferencesDescriptor.getFields();
                break;
            }
            i3++;
        }
        if (set == null) {
            return true;
        }
        long mapIdToAddress = iSnapshot.mapIdToAddress(i2);
        for (NamedReference next : object.getOutboundReferences()) {
            if (mapIdToAddress == next.getObjectAddress() && !set.contains(next.getName())) {
                return false;
            }
        }
        return true;
    }
}
