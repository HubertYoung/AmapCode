package com.taobao.accs.data;

import anet.channel.statist.StatObject;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.ut.monitor.AssembleMonitor;
import com.taobao.accs.utl.ALog;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AssembleMessage {
    private static final int DEFAULT_ASSEMBLE_TIMEOUT = 30000;
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final int SPLITTED_DATA_INDEX = 17;
    public static final int SPLITTED_DATA_MD5 = 18;
    public static final int SPLITTED_DATA_NUMS = 16;
    public static final int SPLITTED_TIME_OUT = 15;
    private static final int STATUS_COMPLETE = 2;
    private static final int STATUS_FAIL = 3;
    private static final int STATUS_TIMEOUT = 1;
    private static final int STATUS_VALID = 0;
    private static final String TAG = "AssembleMessage";
    /* access modifiers changed from: private */
    public Map<Integer, byte[]> burstMessages = new TreeMap(new Comparator<Integer>() {
        public int compare(Integer num, Integer num2) {
            return num.intValue() - num2.intValue();
        }
    });
    /* access modifiers changed from: private */
    public String dataId;
    private String dataMd5;
    private int dataNums;
    private long firstDataBurstTime;
    /* access modifiers changed from: private */
    public volatile int status = 0;
    private ScheduledFuture<?> timeoutFuture;

    public AssembleMessage(String str, int i, String str2) {
        this.dataId = str;
        this.dataNums = i;
        this.dataMd5 = str2;
    }

    public void setTimeOut(long j) {
        if (j <= 0) {
            j = StatisticConfig.MIN_UPLOAD_INTERVAL;
        }
        this.timeoutFuture = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new Runnable() {
            public void run() {
                synchronized (AssembleMessage.this) {
                    if (AssembleMessage.this.status == 0) {
                        ALog.e(AssembleMessage.TAG, "timeout", Constants.KEY_DATA_ID, AssembleMessage.this.dataId);
                        AssembleMessage.this.status = 1;
                        AssembleMessage.this.burstMessages.clear();
                        x.a().a((StatObject) new AssembleMonitor(AssembleMessage.this.dataId, String.valueOf(AssembleMessage.this.status)));
                    }
                }
            }
        }, j, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0144, code lost:
        return r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x015b, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] putBurst(int r13, int r14, byte[] r15) {
        /*
            r12 = this;
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.D
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)
            r1 = 4
            r2 = 3
            r3 = 1
            r4 = 2
            r5 = 0
            if (r0 == 0) goto L_0x0028
            java.lang.String r0 = "AssembleMessage"
            java.lang.String r6 = "putBurst"
            java.lang.Object[] r7 = new java.lang.Object[r1]
            java.lang.String r8 = "dataId"
            r7[r5] = r8
            java.lang.String r8 = r12.dataId
            r7[r3] = r8
            java.lang.String r8 = "index"
            r7[r4] = r8
            java.lang.Integer r8 = java.lang.Integer.valueOf(r13)
            r7[r2] = r8
            com.taobao.accs.utl.ALog.d(r0, r6, r7)
        L_0x0028:
            int r0 = r12.dataNums
            r6 = 0
            if (r14 == r0) goto L_0x0037
            java.lang.String r13 = "AssembleMessage"
            java.lang.String r14 = "putBurst fail as burstNums not match"
            java.lang.Object[] r15 = new java.lang.Object[r5]
            com.taobao.accs.utl.ALog.e(r13, r14, r15)
            return r6
        L_0x0037:
            if (r13 < 0) goto L_0x015f
            if (r13 < r14) goto L_0x003d
            goto L_0x015f
        L_0x003d:
            monitor-enter(r12)
            int r14 = r12.status     // Catch:{ all -> 0x015c }
            if (r14 != 0) goto L_0x0145
            java.util.Map<java.lang.Integer, byte[]> r14 = r12.burstMessages     // Catch:{ all -> 0x015c }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x015c }
            java.lang.Object r14 = r14.get(r0)     // Catch:{ all -> 0x015c }
            if (r14 == 0) goto L_0x0059
            java.lang.String r13 = "AssembleMessage"
            java.lang.String r14 = "putBurst fail as exist old"
            java.lang.Object[] r15 = new java.lang.Object[r5]     // Catch:{ all -> 0x015c }
            com.taobao.accs.utl.ALog.e(r13, r14, r15)     // Catch:{ all -> 0x015c }
            monitor-exit(r12)     // Catch:{ all -> 0x015c }
            return r6
        L_0x0059:
            java.util.Map<java.lang.Integer, byte[]> r14 = r12.burstMessages     // Catch:{ all -> 0x015c }
            boolean r14 = r14.isEmpty()     // Catch:{ all -> 0x015c }
            if (r14 == 0) goto L_0x0067
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x015c }
            r12.firstDataBurstTime = r7     // Catch:{ all -> 0x015c }
        L_0x0067:
            java.util.Map<java.lang.Integer, byte[]> r14 = r12.burstMessages     // Catch:{ all -> 0x015c }
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)     // Catch:{ all -> 0x015c }
            r14.put(r13, r15)     // Catch:{ all -> 0x015c }
            java.util.Map<java.lang.Integer, byte[]> r13 = r12.burstMessages     // Catch:{ all -> 0x015c }
            int r13 = r13.size()     // Catch:{ all -> 0x015c }
            int r14 = r12.dataNums     // Catch:{ all -> 0x015c }
            if (r13 != r14) goto L_0x015a
            java.util.Map<java.lang.Integer, byte[]> r13 = r12.burstMessages     // Catch:{ all -> 0x015c }
            java.util.Collection r13 = r13.values()     // Catch:{ all -> 0x015c }
            java.util.Iterator r13 = r13.iterator()     // Catch:{ all -> 0x015c }
            r14 = r6
        L_0x0085:
            boolean r15 = r13.hasNext()     // Catch:{ all -> 0x015c }
            if (r15 == 0) goto L_0x00a5
            java.lang.Object r15 = r13.next()     // Catch:{ all -> 0x015c }
            byte[] r15 = (byte[]) r15     // Catch:{ all -> 0x015c }
            if (r14 != 0) goto L_0x0095
            r14 = r15
            goto L_0x0085
        L_0x0095:
            int r0 = r14.length     // Catch:{ all -> 0x015c }
            int r7 = r15.length     // Catch:{ all -> 0x015c }
            int r0 = r0 + r7
            byte[] r0 = new byte[r0]     // Catch:{ all -> 0x015c }
            int r7 = r14.length     // Catch:{ all -> 0x015c }
            java.lang.System.arraycopy(r14, r5, r0, r5, r7)     // Catch:{ all -> 0x015c }
            int r14 = r14.length     // Catch:{ all -> 0x015c }
            int r7 = r15.length     // Catch:{ all -> 0x015c }
            java.lang.System.arraycopy(r15, r5, r0, r14, r7)     // Catch:{ all -> 0x015c }
            r14 = r0
            goto L_0x0085
        L_0x00a5:
            java.lang.String r13 = r12.dataMd5     // Catch:{ all -> 0x015c }
            boolean r13 = android.text.TextUtils.isEmpty(r13)     // Catch:{ all -> 0x015c }
            r15 = 5
            r0 = 6
            if (r13 != 0) goto L_0x00e6
            java.lang.String r13 = new java.lang.String     // Catch:{ all -> 0x015c }
            byte[] r7 = org.android.agoo.common.EncryptUtil.a(r14)     // Catch:{ all -> 0x015c }
            char[] r7 = encodeHex(r7)     // Catch:{ all -> 0x015c }
            r13.<init>(r7)     // Catch:{ all -> 0x015c }
            java.lang.String r7 = r12.dataMd5     // Catch:{ all -> 0x015c }
            boolean r7 = r7.equals(r13)     // Catch:{ all -> 0x015c }
            if (r7 != 0) goto L_0x00e6
            java.lang.String r14 = "AssembleMessage"
            java.lang.String r7 = "putBurst fail"
            java.lang.Object[] r8 = new java.lang.Object[r0]     // Catch:{ all -> 0x015c }
            java.lang.String r9 = "dataId"
            r8[r5] = r9     // Catch:{ all -> 0x015c }
            java.lang.String r9 = r12.dataId     // Catch:{ all -> 0x015c }
            r8[r3] = r9     // Catch:{ all -> 0x015c }
            java.lang.String r9 = "dataMd5"
            r8[r4] = r9     // Catch:{ all -> 0x015c }
            java.lang.String r9 = r12.dataMd5     // Catch:{ all -> 0x015c }
            r8[r2] = r9     // Catch:{ all -> 0x015c }
            java.lang.String r9 = "finalDataMd5"
            r8[r1] = r9     // Catch:{ all -> 0x015c }
            r8[r15] = r13     // Catch:{ all -> 0x015c }
            com.taobao.accs.utl.ALog.w(r14, r7, r8)     // Catch:{ all -> 0x015c }
            r12.status = r2     // Catch:{ all -> 0x015c }
            r14 = r6
        L_0x00e6:
            r6 = 0
            if (r14 == 0) goto L_0x011c
            int r13 = r14.length     // Catch:{ all -> 0x015c }
            long r6 = (long) r13     // Catch:{ all -> 0x015c }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x015c }
            long r10 = r12.firstDataBurstTime     // Catch:{ all -> 0x015c }
            r13 = 0
            long r8 = r8 - r10
            r12.status = r4     // Catch:{ all -> 0x015c }
            java.lang.String r13 = "AssembleMessage"
            java.lang.String r10 = "putBurst completed"
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x015c }
            java.lang.String r11 = "dataId"
            r0[r5] = r11     // Catch:{ all -> 0x015c }
            java.lang.String r11 = r12.dataId     // Catch:{ all -> 0x015c }
            r0[r3] = r11     // Catch:{ all -> 0x015c }
            java.lang.String r3 = "length"
            r0[r4] = r3     // Catch:{ all -> 0x015c }
            java.lang.Long r3 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x015c }
            r0[r2] = r3     // Catch:{ all -> 0x015c }
            java.lang.String r2 = "cost"
            r0[r1] = r2     // Catch:{ all -> 0x015c }
            java.lang.Long r1 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x015c }
            r0[r15] = r1     // Catch:{ all -> 0x015c }
            com.taobao.accs.utl.ALog.i(r13, r10, r0)     // Catch:{ all -> 0x015c }
            goto L_0x011d
        L_0x011c:
            r8 = r6
        L_0x011d:
            com.taobao.accs.ut.monitor.AssembleMonitor r13 = new com.taobao.accs.ut.monitor.AssembleMonitor     // Catch:{ all -> 0x015c }
            java.lang.String r15 = r12.dataId     // Catch:{ all -> 0x015c }
            int r0 = r12.status     // Catch:{ all -> 0x015c }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x015c }
            r13.<init>(r15, r0)     // Catch:{ all -> 0x015c }
            r13.assembleLength = r6     // Catch:{ all -> 0x015c }
            r13.assembleTimes = r8     // Catch:{ all -> 0x015c }
            z r15 = defpackage.x.a()     // Catch:{ all -> 0x015c }
            r15.a(r13)     // Catch:{ all -> 0x015c }
            java.util.Map<java.lang.Integer, byte[]> r13 = r12.burstMessages     // Catch:{ all -> 0x015c }
            r13.clear()     // Catch:{ all -> 0x015c }
            java.util.concurrent.ScheduledFuture<?> r13 = r12.timeoutFuture     // Catch:{ all -> 0x015c }
            if (r13 == 0) goto L_0x0143
            java.util.concurrent.ScheduledFuture<?> r13 = r12.timeoutFuture     // Catch:{ all -> 0x015c }
            r13.cancel(r5)     // Catch:{ all -> 0x015c }
        L_0x0143:
            monitor-exit(r12)     // Catch:{ all -> 0x015c }
            return r14
        L_0x0145:
            java.lang.String r13 = "AssembleMessage"
            java.lang.String r14 = "putBurst fail"
            java.lang.Object[] r15 = new java.lang.Object[r4]     // Catch:{ all -> 0x015c }
            java.lang.String r0 = "status"
            r15[r5] = r0     // Catch:{ all -> 0x015c }
            int r0 = r12.status     // Catch:{ all -> 0x015c }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ all -> 0x015c }
            r15[r3] = r0     // Catch:{ all -> 0x015c }
            com.taobao.accs.utl.ALog.e(r13, r14, r15)     // Catch:{ all -> 0x015c }
        L_0x015a:
            monitor-exit(r12)     // Catch:{ all -> 0x015c }
            return r6
        L_0x015c:
            r13 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x015c }
            throw r13
        L_0x015f:
            java.lang.String r13 = "AssembleMessage"
            java.lang.String r14 = "putBurst fail as burstIndex invalid"
            java.lang.Object[] r15 = new java.lang.Object[r5]
            com.taobao.accs.utl.ALog.e(r13, r14, r15)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.AssembleMessage.putBurst(int, int, byte[]):byte[]");
    }

    private static char[] encodeHex(byte[] bArr) {
        int length = bArr.length;
        char[] cArr = new char[(length << 1)];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr[i] = DIGITS_LOWER[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr[i3] = DIGITS_LOWER[bArr[i2] & 15];
        }
        return cArr;
    }
}
