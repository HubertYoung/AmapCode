package com.alipay.android.phone.bluetoothsdk.scan.message.protocol;

import android.support.v4.util.ArrayMap;

public class MessageComposer {
    public static final String TAG = "MessageComposer";
    private static MessageComposer mInstance;
    private final int CACHE_LEN = 5;
    private ArrayMap<Long, byte[][]> mCaches = new ArrayMap<>(5);

    public static MessageComposer getComposer() {
        if (mInstance == null) {
            synchronized (MessageComposer.class) {
                try {
                    if (mInstance == null) {
                        mInstance = new MessageComposer();
                    }
                }
            }
        }
        return mInstance;
    }

    private MessageComposer() {
    }

    public synchronized String receiveFragment(byte[] data) {
        String str = null;
        synchronized (this) {
            try {
                MessageFragment messageFragment = new MessageFragment(null);
                messageFragment.initFromOutData(data);
                Long key = messageFragment.getSha1Key();
                int index = messageFragment.getIndex();
                int totalNum = messageFragment.getTotalNum();
                byte[] originData = messageFragment.getOriginData();
                if (key != null && index >= 0 && totalNum >= 0 && originData != null) {
                    if (this.mCaches.get(key) != null) {
                        byte[][] totalData = (byte[][]) this.mCaches.get(key);
                        if (totalData[index] == null) {
                            totalData[index] = originData;
                        }
                        str = tryRecoveryData(totalData);
                    } else if (this.mCaches.size() < 5) {
                        byte[][] totalData2 = new byte[totalNum][];
                        totalData2[index] = originData;
                        this.mCaches.put(key, totalData2);
                        if (index == totalNum - 1) {
                            str = tryRecoveryData(totalData2);
                        }
                    } else {
                        popMostUseless();
                        byte[][] totalData3 = new byte[totalNum][];
                        totalData3[index] = originData;
                        this.mCaches.put(key, totalData3);
                        if (index == totalNum - 1) {
                            str = tryRecoveryData(totalData3);
                        }
                    }
                }
            }
        }
        return str;
    }

    private synchronized void popMostUseless() {
        Long worstKey = null;
        int worstLen = 0;
        for (Long key : this.mCaches.keySet()) {
            byte[][] data = (byte[][]) this.mCaches.get(key);
            int inadequacyNum = 0;
            for (byte[] bArr : data) {
                if (bArr == null) {
                    inadequacyNum++;
                }
            }
            if (inadequacyNum >= worstLen) {
                worstKey = key;
                worstLen = inadequacyNum;
            }
        }
        if (worstKey != null) {
            this.mCaches.remove(worstKey);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0019, code lost:
        r4 = new byte[r2];
        r5 = 0;
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x001e, code lost:
        if (r1 >= r10.length) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0020, code lost:
        r3 = r10[r1].length;
        java.lang.System.arraycopy(r10[r1], 0, r4, r5, r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0029, code lost:
        r5 = r5 + r3;
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0034, code lost:
        r6 = new java.lang.String(r4, "utf8");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0036, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        android.util.Log.e(TAG, "tryRecoveryData failed", r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.lang.String tryRecoveryData(byte[][] r10) {
        /*
            r9 = this;
            r6 = 0
            monitor-enter(r9)
            if (r10 == 0) goto L_0x0007
            int r7 = r10.length     // Catch:{ all -> 0x003f }
            if (r7 != 0) goto L_0x0009
        L_0x0007:
            monitor-exit(r9)
            return r6
        L_0x0009:
            r2 = 0
            r1 = 0
        L_0x000b:
            int r7 = r10.length     // Catch:{ all -> 0x003f }
            if (r1 >= r7) goto L_0x0019
            r7 = r10[r1]     // Catch:{ all -> 0x003f }
            if (r7 == 0) goto L_0x0007
            r7 = r10[r1]     // Catch:{ all -> 0x003f }
            int r7 = r7.length     // Catch:{ all -> 0x003f }
            int r2 = r2 + r7
            int r1 = r1 + 1
            goto L_0x000b
        L_0x0019:
            byte[] r4 = new byte[r2]     // Catch:{ all -> 0x003f }
            r5 = 0
            r1 = 0
        L_0x001d:
            int r7 = r10.length     // Catch:{ all -> 0x003f }
            if (r1 >= r7) goto L_0x002d
            r7 = r10[r1]     // Catch:{ all -> 0x003f }
            int r3 = r7.length     // Catch:{ all -> 0x003f }
            r7 = r10[r1]     // Catch:{ all -> 0x003f }
            r8 = 0
            java.lang.System.arraycopy(r7, r8, r4, r5, r3)     // Catch:{ all -> 0x003f }
            int r5 = r5 + r3
            int r1 = r1 + 1
            goto L_0x001d
        L_0x002d:
            java.lang.String r7 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x0036 }
            java.lang.String r8 = "utf8"
            r7.<init>(r4, r8)     // Catch:{ UnsupportedEncodingException -> 0x0036 }
            r6 = r7
            goto L_0x0007
        L_0x0036:
            r0 = move-exception
            java.lang.String r7 = "MessageComposer"
            java.lang.String r8 = "tryRecoveryData failed"
            android.util.Log.e(r7, r8, r0)     // Catch:{ all -> 0x003f }
            goto L_0x0007
        L_0x003f:
            r6 = move-exception
            monitor-exit(r9)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.bluetoothsdk.scan.message.protocol.MessageComposer.tryRecoveryData(byte[][]):java.lang.String");
    }
}
