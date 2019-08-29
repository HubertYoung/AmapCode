package org.eclipse.mat.parser.internal.snapshot;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.mat.collect.HashMapIntLong;
import org.eclipse.mat.hprof.Messages;
import org.eclipse.mat.parser.model.XSnapshotInfo;

public class RetainedSizeCache {
    private String filename;
    private HashMapIntLong id2size;
    private boolean isDirty = false;

    public RetainedSizeCache(XSnapshotInfo xSnapshotInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append(xSnapshotInfo.getPrefix());
        sb.append("i2sv2.index");
        this.filename = sb.toString();
        readId2Size(xSnapshotInfo.getPrefix());
    }

    public long get(int i) {
        try {
            return this.id2size.get(i);
        } catch (NoSuchElementException unused) {
            return 0;
        }
    }

    public void put(int i, long j) {
        this.id2size.put(i, j);
        this.isDirty = true;
    }

    public void close() {
        int[] allKeys;
        if (this.isDirty) {
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(new File(this.filename)));
                for (int i : this.id2size.getAllKeys()) {
                    dataOutputStream.writeInt(i);
                    dataOutputStream.writeLong(this.id2size.get(i));
                }
                dataOutputStream.close();
                this.isDirty = false;
            } catch (IOException e) {
                Logger.getLogger(RetainedSizeCache.class.getName()).log(Level.WARNING, Messages.RetainedSizeCache_Warning_IgnoreError.pattern, e);
            }
        }
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x006c */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0069 A[SYNTHETIC, Splitter:B:26:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0073 A[SYNTHETIC, Splitter:B:34:0x0073] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void doRead(java.io.File r7, boolean r8) {
        /*
            r6 = this;
            r0 = 0
            org.eclipse.mat.collect.HashMapIntLong r1 = new org.eclipse.mat.collect.HashMapIntLong     // Catch:{ IOException -> 0x004e }
            long r2 = r7.length()     // Catch:{ IOException -> 0x004e }
            int r2 = (int) r2     // Catch:{ IOException -> 0x004e }
            int r2 = r2 / 8
            r1.<init>(r2)     // Catch:{ IOException -> 0x004e }
            r6.id2size = r1     // Catch:{ IOException -> 0x004e }
            java.io.DataInputStream r1 = new java.io.DataInputStream     // Catch:{ IOException -> 0x004e }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x004e }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x004e }
            r3.<init>(r7)     // Catch:{ IOException -> 0x004e }
            r2.<init>(r3)     // Catch:{ IOException -> 0x004e }
            r1.<init>(r2)     // Catch:{ IOException -> 0x004e }
        L_0x001e:
            int r0 = r1.available()     // Catch:{ IOException -> 0x0049, all -> 0x0046 }
            if (r0 <= 0) goto L_0x0041
            int r0 = r1.readInt()     // Catch:{ IOException -> 0x0049, all -> 0x0046 }
            long r2 = r1.readLong()     // Catch:{ IOException -> 0x0049, all -> 0x0046 }
            r4 = 0
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 >= 0) goto L_0x003b
            if (r8 == 0) goto L_0x003b
            r4 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            long r2 = r2 - r4
            long r2 = -r2
        L_0x003b:
            org.eclipse.mat.collect.HashMapIntLong r4 = r6.id2size     // Catch:{ IOException -> 0x0049, all -> 0x0046 }
            r4.put(r0, r2)     // Catch:{ IOException -> 0x0049, all -> 0x0046 }
            goto L_0x001e
        L_0x0041:
            r1.close()     // Catch:{ IOException -> 0x0045 }
            return
        L_0x0045:
            return
        L_0x0046:
            r7 = move-exception
            r0 = r1
            goto L_0x0071
        L_0x0049:
            r8 = move-exception
            r0 = r1
            goto L_0x004f
        L_0x004c:
            r7 = move-exception
            goto L_0x0071
        L_0x004e:
            r8 = move-exception
        L_0x004f:
            java.lang.Class<org.eclipse.mat.parser.internal.snapshot.RetainedSizeCache> r1 = org.eclipse.mat.parser.internal.snapshot.RetainedSizeCache.class
            java.lang.String r1 = r1.getName()     // Catch:{ all -> 0x004c }
            java.util.logging.Logger r1 = java.util.logging.Logger.getLogger(r1)     // Catch:{ all -> 0x004c }
            java.util.logging.Level r2 = java.util.logging.Level.WARNING     // Catch:{ all -> 0x004c }
            org.eclipse.mat.hprof.Messages r3 = org.eclipse.mat.hprof.Messages.RetainedSizeCache_ErrorReadingRetainedSizes     // Catch:{ all -> 0x004c }
            java.lang.String r3 = r3.pattern     // Catch:{ all -> 0x004c }
            r1.log(r2, r3, r8)     // Catch:{ all -> 0x004c }
            org.eclipse.mat.collect.HashMapIntLong r8 = r6.id2size     // Catch:{ all -> 0x004c }
            r8.clear()     // Catch:{ all -> 0x004c }
            if (r0 == 0) goto L_0x006c
            r0.close()     // Catch:{ IOException -> 0x006c }
        L_0x006c:
            r7.delete()     // Catch:{ RuntimeException -> 0x0070 }
            return
        L_0x0070:
            return
        L_0x0071:
            if (r0 == 0) goto L_0x0076
            r0.close()     // Catch:{ IOException -> 0x0076 }
        L_0x0076:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.mat.parser.internal.snapshot.RetainedSizeCache.doRead(java.io.File, boolean):void");
    }

    private void readId2Size(String str) {
        File file = new File(this.filename);
        if (file.exists()) {
            doRead(file, false);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("i2s.index");
        File file2 = new File(sb.toString());
        if (file2.exists()) {
            doRead(file2, true);
        } else {
            this.id2size = new HashMapIntLong();
        }
    }
}
