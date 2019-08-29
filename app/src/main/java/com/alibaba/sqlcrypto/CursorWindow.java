package com.alibaba.sqlcrypto;

import android.database.CharArrayBuffer;
import android.util.Log;
import android.util.SparseIntArray;
import com.alibaba.sqlcrypto.sqlite.SQLiteClosable;
import com.alipay.sdk.util.h;

public class CursorWindow extends SQLiteClosable {
    private static final String STATS_TAG = "CursorWindowStats";
    private static final int sCursorWindowSize = 2097152;
    private static final SparseIntArray sWindowToPidMap = new SparseIntArray();
    private final String mName;
    private int mStartPos;
    public int mWindowPtr;

    private static native boolean nativeAllocRow(int i);

    private static native void nativeClear(int i);

    private static native void nativeCopyStringToBuffer(int i, int i2, int i3, CharArrayBuffer charArrayBuffer);

    private static native int nativeCreate(String str, int i);

    private static native void nativeDispose(int i);

    private static native void nativeFreeLastRow(int i);

    private static native byte[] nativeGetBlob(int i, int i2, int i3);

    private static native double nativeGetDouble(int i, int i2, int i3);

    private static native long nativeGetLong(int i, int i2, int i3);

    private static native String nativeGetName(int i);

    private static native int nativeGetNumRows(int i);

    private static native String nativeGetString(int i, int i2, int i3);

    private static native int nativeGetType(int i, int i2, int i3);

    private static native boolean nativePutBlob(int i, byte[] bArr, int i2, int i3);

    private static native boolean nativePutDouble(int i, double d, int i2, int i3);

    private static native boolean nativePutLong(int i, long j, int i2, int i3);

    private static native boolean nativePutNull(int i, int i2, int i3);

    private static native boolean nativePutString(int i, String str, int i2, int i3);

    private static native boolean nativeSetNumColumns(int i, int i2);

    public CursorWindow(String name) {
        this.mStartPos = 0;
        this.mName = (name == null || name.length() == 0) ? "<unnamed>" : name;
        this.mWindowPtr = nativeCreate(this.mName, 2097152);
        if (this.mWindowPtr == 0) {
            throw new CursorWindowAllocationException("Cursor window allocation of 2048 kb failed. " + printStats());
        }
    }

    @Deprecated
    public CursorWindow(boolean localWindow) {
        this((String) null);
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        try {
            dispose();
        } finally {
            super.finalize();
        }
    }

    private void dispose() {
        if (this.mWindowPtr != 0) {
            recordClosingOfWindow(this.mWindowPtr);
            nativeDispose(this.mWindowPtr);
            this.mWindowPtr = 0;
        }
    }

    public String getName() {
        return this.mName;
    }

    public void clear() {
        acquireReference();
        try {
            this.mStartPos = 0;
            nativeClear(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public int getStartPosition() {
        return this.mStartPos;
    }

    public void setStartPosition(int pos) {
        this.mStartPos = pos;
    }

    public int getNumRows() {
        acquireReference();
        try {
            return nativeGetNumRows(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public boolean setNumColumns(int columnNum) {
        acquireReference();
        try {
            return nativeSetNumColumns(this.mWindowPtr, columnNum);
        } finally {
            releaseReference();
        }
    }

    public boolean allocRow() {
        acquireReference();
        try {
            return nativeAllocRow(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    public void freeLastRow() {
        acquireReference();
        try {
            nativeFreeLastRow(this.mWindowPtr);
        } finally {
            releaseReference();
        }
    }

    @Deprecated
    public boolean isNull(int row, int column) {
        return getType(row, column) == 0;
    }

    @Deprecated
    public boolean isBlob(int row, int column) {
        int type = getType(row, column);
        return type == 4 || type == 0;
    }

    @Deprecated
    public boolean isLong(int row, int column) {
        return getType(row, column) == 1;
    }

    @Deprecated
    public boolean isFloat(int row, int column) {
        return getType(row, column) == 2;
    }

    @Deprecated
    public boolean isString(int row, int column) {
        int type = getType(row, column);
        return type == 3 || type == 0;
    }

    public int getType(int row, int column) {
        acquireReference();
        try {
            return nativeGetType(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public byte[] getBlob(int row, int column) {
        acquireReference();
        try {
            return nativeGetBlob(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public String getString(int row, int column) {
        acquireReference();
        try {
            return nativeGetString(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public void copyStringToBuffer(int row, int column, CharArrayBuffer buffer) {
        if (buffer == null) {
            throw new IllegalArgumentException("CharArrayBuffer should not be null");
        }
        acquireReference();
        try {
            nativeCopyStringToBuffer(this.mWindowPtr, row - this.mStartPos, column, buffer);
        } finally {
            releaseReference();
        }
    }

    public long getLong(int row, int column) {
        acquireReference();
        try {
            return nativeGetLong(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public double getDouble(int row, int column) {
        acquireReference();
        try {
            return nativeGetDouble(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public short getShort(int row, int column) {
        return (short) ((int) getLong(row, column));
    }

    public int getInt(int row, int column) {
        return (int) getLong(row, column);
    }

    public float getFloat(int row, int column) {
        return (float) getDouble(row, column);
    }

    public boolean putBlob(byte[] value, int row, int column) {
        acquireReference();
        try {
            return nativePutBlob(this.mWindowPtr, value, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public boolean putString(String value, int row, int column) {
        acquireReference();
        try {
            return nativePutString(this.mWindowPtr, value, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public boolean putLong(long value, int row, int column) {
        acquireReference();
        try {
            return nativePutLong(this.mWindowPtr, value, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public boolean putDouble(double value, int row, int column) {
        acquireReference();
        try {
            return nativePutDouble(this.mWindowPtr, value, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    public boolean putNull(int row, int column) {
        acquireReference();
        try {
            return nativePutNull(this.mWindowPtr, row - this.mStartPos, column);
        } finally {
            releaseReference();
        }
    }

    /* access modifiers changed from: protected */
    public void onAllReferencesReleased() {
        dispose();
    }

    private void recordNewWindow(int pid, int window) {
        synchronized (sWindowToPidMap) {
            sWindowToPidMap.put(window, pid);
            if (Log.isLoggable(STATS_TAG, 2)) {
                Log.i(STATS_TAG, "Created a new Cursor. " + printStats());
            }
        }
    }

    private void recordClosingOfWindow(int window) {
        synchronized (sWindowToPidMap) {
            if (sWindowToPidMap.size() != 0) {
                sWindowToPidMap.delete(window);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0036, code lost:
        r5 = r7.size();
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003b, code lost:
        if (r1 >= r5) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003d, code lost:
        r0.append(" (# cursors opened by ");
        r6 = r7.keyAt(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0046, code lost:
        if (r6 != r3) goto L_0x006e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0048, code lost:
        r0.append("this proc=");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004d, code lost:
        r4 = r7.get(r6);
        r0.append(r4 + ")");
        r10 = r10 + r4;
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x006e, code lost:
        r0.append("pid " + r6 + "=");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008b, code lost:
        if (r0.length() <= 980) goto L_0x00a7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008d, code lost:
        r8 = r0.substring(0, 980);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a7, code lost:
        r8 = r0.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        return "# Open Cursors=" + r10 + r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String printStats() {
        /*
            r15 = this;
            r14 = 980(0x3d4, float:1.373E-42)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            int r3 = android.os.Process.myPid()
            r10 = 0
            android.util.SparseIntArray r7 = new android.util.SparseIntArray
            r7.<init>()
            android.util.SparseIntArray r13 = sWindowToPidMap
            monitor-enter(r13)
            android.util.SparseIntArray r12 = sWindowToPidMap     // Catch:{ all -> 0x006b }
            int r9 = r12.size()     // Catch:{ all -> 0x006b }
            if (r9 != 0) goto L_0x0020
            java.lang.String r12 = ""
            monitor-exit(r13)     // Catch:{ all -> 0x006b }
        L_0x001f:
            return r12
        L_0x0020:
            r2 = 0
        L_0x0021:
            if (r2 >= r9) goto L_0x0035
            android.util.SparseIntArray r12 = sWindowToPidMap     // Catch:{ all -> 0x006b }
            int r6 = r12.valueAt(r2)     // Catch:{ all -> 0x006b }
            int r11 = r7.get(r6)     // Catch:{ all -> 0x006b }
            int r11 = r11 + 1
            r7.put(r6, r11)     // Catch:{ all -> 0x006b }
            int r2 = r2 + 1
            goto L_0x0021
        L_0x0035:
            monitor-exit(r13)     // Catch:{ all -> 0x006b }
            int r5 = r7.size()
            r1 = 0
        L_0x003b:
            if (r1 >= r5) goto L_0x0087
            java.lang.String r12 = " (# cursors opened by "
            r0.append(r12)
            int r6 = r7.keyAt(r1)
            if (r6 != r3) goto L_0x006e
            java.lang.String r12 = "this proc="
            r0.append(r12)
        L_0x004d:
            int r4 = r7.get(r6)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.StringBuilder r12 = r12.append(r4)
            java.lang.String r13 = ")"
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            r0.append(r12)
            int r10 = r10 + r4
            int r1 = r1 + 1
            goto L_0x003b
        L_0x006b:
            r12 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x006b }
            throw r12
        L_0x006e:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "pid "
            r12.<init>(r13)
            java.lang.StringBuilder r12 = r12.append(r6)
            java.lang.String r13 = "="
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.String r12 = r12.toString()
            r0.append(r12)
            goto L_0x004d
        L_0x0087:
            int r12 = r0.length()
            if (r12 <= r14) goto L_0x00a7
            r12 = 0
            java.lang.String r8 = r0.substring(r12, r14)
        L_0x0092:
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r13 = "# Open Cursors="
            r12.<init>(r13)
            java.lang.StringBuilder r12 = r12.append(r10)
            java.lang.StringBuilder r12 = r12.append(r8)
            java.lang.String r12 = r12.toString()
            goto L_0x001f
        L_0x00a7:
            java.lang.String r8 = r0.toString()
            goto L_0x0092
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sqlcrypto.CursorWindow.printStats():java.lang.String");
    }

    public String toString() {
        return getName() + " {" + Integer.toHexString(this.mWindowPtr) + h.d;
    }
}
