package com.taobao.accs.flowcontrol;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.utl.ALog;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FlowControl {
    public static final int DELAY_MAX = -1;
    public static final int DELAY_MAX_BRUSH = -1000;
    public static final int HIGH_FLOW_CTRL = 2;
    public static final int HIGH_FLOW_CTRL_BRUSH = 3;
    public static final int LOW_FLOW_CTRL = 1;
    public static final int NO_FLOW_CTRL = 0;
    public static final String SERVICE_ALL = "ALL";
    public static final String SERVICE_ALL_BRUSH = "ALL_BRUSH";
    public static final int STATUS_FLOW_CTRL_ALL = 420;
    public static final int STATUS_FLOW_CTRL_BRUSH = 422;
    public static final int STATUS_FLOW_CTRL_CUR = 421;
    private static final String TAG = "FlowControl";
    private Context mContext;
    private FlowCtrlInfoHolder mFlowCtrlHolder;

    public static class FlowControlInfo implements Serializable {
        private static final long serialVersionUID = -2259991484877844919L;
        public String bizId;
        public long delayTime;
        public long expireTime;
        public String serviceId;
        public long startTime;
        public int status;

        public FlowControlInfo(String str, String str2, int i, long j, long j2, long j3) {
            this.serviceId = str;
            this.bizId = str2;
            this.status = i;
            this.delayTime = j;
            long j4 = 0;
            this.expireTime = j2 <= 0 ? 0 : j2;
            this.startTime = j3 > 0 ? j3 : j4;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - (this.startTime + this.expireTime) > 0;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("flow ctrl serviceId:");
            stringBuffer.append(this.serviceId);
            stringBuffer.append(" bizId:");
            stringBuffer.append(this.bizId);
            stringBuffer.append(" status:");
            stringBuffer.append(this.status);
            stringBuffer.append(" delayTime:");
            stringBuffer.append(this.delayTime);
            stringBuffer.append(" startTime:");
            stringBuffer.append(this.startTime);
            stringBuffer.append(" expireTime:");
            stringBuffer.append(this.expireTime);
            return stringBuffer.toString();
        }
    }

    public static class FlowCtrlInfoHolder implements Serializable {
        private static final long serialVersionUID = 6307563052429742524L;
        Map<String, FlowControlInfo> flowCtrlMap = null;

        public void put(String str, String str2, FlowControlInfo flowControlInfo) {
            if (!TextUtils.isEmpty(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("_");
                sb.append(str2);
                str = sb.toString();
            }
            if (this.flowCtrlMap == null) {
                this.flowCtrlMap = new HashMap();
            }
            this.flowCtrlMap.put(str, flowControlInfo);
        }

        public FlowControlInfo get(String str, String str2) {
            if (this.flowCtrlMap == null) {
                return null;
            }
            if (!TextUtils.isEmpty(str2)) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("_");
                sb.append(str2);
                str = sb.toString();
            }
            return this.flowCtrlMap.get(str);
        }
    }

    public FlowControl(Context context) {
        this.mContext = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:71:0x014f  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0151  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int updateFlowCtrlInfo(java.util.Map<java.lang.Integer, java.lang.String> r24, java.lang.String r25) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
            r3 = 422(0x1a6, float:5.91E-43)
            r4 = 0
            r6 = 0
            if (r2 == 0) goto L_0x0148
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r7 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_STATUS     // Catch:{ Throwable -> 0x0138 }
            int r7 = r7.ordinal()     // Catch:{ Throwable -> 0x0138 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0138 }
            java.lang.Object r7 = r2.get(r7)     // Catch:{ Throwable -> 0x0138 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Throwable -> 0x0138 }
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r8 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_DELAY     // Catch:{ Throwable -> 0x0138 }
            int r8 = r8.ordinal()     // Catch:{ Throwable -> 0x0138 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ Throwable -> 0x0138 }
            java.lang.Object r8 = r2.get(r8)     // Catch:{ Throwable -> 0x0138 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Throwable -> 0x0138 }
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r9 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_EXPIRE     // Catch:{ Throwable -> 0x0138 }
            int r9 = r9.ordinal()     // Catch:{ Throwable -> 0x0138 }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x0138 }
            java.lang.Object r9 = r2.get(r9)     // Catch:{ Throwable -> 0x0138 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ Throwable -> 0x0138 }
            com.taobao.accs.base.TaoBaseService$ExtHeaderType r10 = com.taobao.accs.base.TaoBaseService.ExtHeaderType.TYPE_BUSINESS     // Catch:{ Throwable -> 0x0138 }
            int r10 = r10.ordinal()     // Catch:{ Throwable -> 0x0138 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x0138 }
            java.lang.Object r2 = r2.get(r10)     // Catch:{ Throwable -> 0x0138 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x0138 }
            boolean r10 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Throwable -> 0x0138 }
            if (r10 == 0) goto L_0x0053
            r7 = 0
            goto L_0x005b
        L_0x0053:
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0138 }
            int r7 = r7.intValue()     // Catch:{ Throwable -> 0x0138 }
        L_0x005b:
            boolean r10 = android.text.TextUtils.isEmpty(r8)     // Catch:{ Throwable -> 0x0133 }
            if (r10 == 0) goto L_0x0063
            r14 = r4
            goto L_0x006c
        L_0x0063:
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ Throwable -> 0x0133 }
            long r10 = r8.longValue()     // Catch:{ Throwable -> 0x0133 }
            r14 = r10
        L_0x006c:
            boolean r8 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x012e }
            if (r8 == 0) goto L_0x0074
            r8 = r4
            goto L_0x007c
        L_0x0074:
            java.lang.Long r8 = java.lang.Long.valueOf(r9)     // Catch:{ Throwable -> 0x012e }
            long r8 = r8.longValue()     // Catch:{ Throwable -> 0x012e }
        L_0x007c:
            r10 = 421(0x1a5, float:5.9E-43)
            r11 = 420(0x1a4, float:5.89E-43)
            if (r7 == r11) goto L_0x0086
            if (r7 == r10) goto L_0x0086
            if (r7 != r3) goto L_0x008c
        L_0x0086:
            boolean r12 = r1.checkFlowCtrlInfo(r14, r8)     // Catch:{ Throwable -> 0x012e }
            if (r12 != 0) goto L_0x008d
        L_0x008c:
            return r6
        L_0x008d:
            monitor-enter(r23)     // Catch:{ Throwable -> 0x012e }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r12 = r1.mFlowCtrlHolder     // Catch:{ all -> 0x0124 }
            if (r12 != 0) goto L_0x00a0
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r12 = new com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder     // Catch:{ all -> 0x009a }
            r12.<init>()     // Catch:{ all -> 0x009a }
            r1.mFlowCtrlHolder = r12     // Catch:{ all -> 0x009a }
            goto L_0x00a0
        L_0x009a:
            r0 = move-exception
            r2 = r0
            r20 = r14
            goto L_0x0128
        L_0x00a0:
            r12 = 0
            if (r7 != r11) goto L_0x00c0
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r2 = new com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo     // Catch:{ all -> 0x0124 }
            java.lang.String r11 = "ALL"
            java.lang.String r12 = ""
            long r18 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0124 }
            r10 = r2
            r13 = r7
            r20 = r14
            r16 = r8
            r10.<init>(r11, r12, r13, r14, r16, r18)     // Catch:{ all -> 0x012c }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r8 = r1.mFlowCtrlHolder     // Catch:{ all -> 0x012c }
            java.lang.String r9 = "ALL"
            java.lang.String r10 = ""
            r8.put(r9, r10, r2)     // Catch:{ all -> 0x012c }
            goto L_0x0106
        L_0x00c0:
            r20 = r14
            if (r7 != r3) goto L_0x00e1
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r2 = new com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo     // Catch:{ all -> 0x012c }
            java.lang.String r11 = "ALL_BRUSH"
            java.lang.String r12 = ""
            long r18 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x012c }
            r10 = r2
            r13 = r7
            r14 = r20
            r16 = r8
            r10.<init>(r11, r12, r13, r14, r16, r18)     // Catch:{ all -> 0x012c }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r8 = r1.mFlowCtrlHolder     // Catch:{ all -> 0x012c }
            java.lang.String r9 = "ALL_BRUSH"
            java.lang.String r10 = ""
            r8.put(r9, r10, r2)     // Catch:{ all -> 0x012c }
            goto L_0x0106
        L_0x00e1:
            if (r7 != r10) goto L_0x0105
            boolean r10 = android.text.TextUtils.isEmpty(r25)     // Catch:{ all -> 0x012c }
            if (r10 != 0) goto L_0x0105
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r14 = new com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo     // Catch:{ all -> 0x012c }
            long r18 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x012c }
            r10 = r14
            r11 = r25
            r12 = r2
            r13 = r7
            r3 = r14
            r14 = r20
            r16 = r8
            r10.<init>(r11, r12, r13, r14, r16, r18)     // Catch:{ all -> 0x012c }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r8 = r1.mFlowCtrlHolder     // Catch:{ all -> 0x012c }
            r9 = r25
            r8.put(r9, r2, r3)     // Catch:{ all -> 0x012c }
            r2 = r3
            goto L_0x0106
        L_0x0105:
            r2 = r12
        L_0x0106:
            if (r2 == 0) goto L_0x0122
            java.lang.String r3 = "FlowControl"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x012c }
            java.lang.String r9 = "updateFlowCtrlInfo "
            r8.<init>(r9)     // Catch:{ all -> 0x012c }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x012c }
            r8.append(r2)     // Catch:{ all -> 0x012c }
            java.lang.String r2 = r8.toString()     // Catch:{ all -> 0x012c }
            java.lang.Object[] r8 = new java.lang.Object[r6]     // Catch:{ all -> 0x012c }
            com.taobao.accs.utl.ALog.e(r3, r2, r8)     // Catch:{ all -> 0x012c }
        L_0x0122:
            monitor-exit(r23)     // Catch:{ all -> 0x012c }
            goto L_0x014b
        L_0x0124:
            r0 = move-exception
            r20 = r14
        L_0x0127:
            r2 = r0
        L_0x0128:
            monitor-exit(r23)     // Catch:{ all -> 0x012c }
            throw r2     // Catch:{ Throwable -> 0x012a }
        L_0x012a:
            r0 = move-exception
            goto L_0x0131
        L_0x012c:
            r0 = move-exception
            goto L_0x0127
        L_0x012e:
            r0 = move-exception
            r20 = r14
        L_0x0131:
            r2 = r0
            goto L_0x013d
        L_0x0133:
            r0 = move-exception
            r2 = r0
            r20 = r4
            goto L_0x013d
        L_0x0138:
            r0 = move-exception
            r2 = r0
            r20 = r4
            r7 = 0
        L_0x013d:
            java.lang.String r3 = "FlowControl"
            java.lang.String r8 = "updateFlowCtrlInfo"
            java.lang.Object[] r9 = new java.lang.Object[r6]
            com.taobao.accs.utl.ALog.e(r3, r8, r2, r9)
            goto L_0x014b
        L_0x0148:
            r20 = r4
            r7 = 0
        L_0x014b:
            int r2 = (r20 > r4 ? 1 : (r20 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x0151
            r2 = 1
            return r2
        L_0x0151:
            if (r2 != 0) goto L_0x0154
            return r6
        L_0x0154:
            r2 = 422(0x1a6, float:5.91E-43)
            if (r2 != r7) goto L_0x015a
            r2 = 3
            return r2
        L_0x015a:
            r2 = 2
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.flowcontrol.FlowControl.updateFlowCtrlInfo(java.util.Map, java.lang.String):int");
    }

    private boolean checkFlowCtrlInfo(long j, long j2) {
        if (j != 0 && j2 > 0) {
            return true;
        }
        ALog.e(TAG, "error flow ctrl info", new Object[0]);
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004e  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0073  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getFlowCtrlDelay(java.lang.String r14, java.lang.String r15) {
        /*
            r13 = this;
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r0 = r13.mFlowCtrlHolder
            r1 = 0
            if (r0 == 0) goto L_0x00df
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r0 = r13.mFlowCtrlHolder
            java.util.Map<java.lang.String, com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo> r0 = r0.flowCtrlMap
            if (r0 == 0) goto L_0x00df
            boolean r0 = android.text.TextUtils.isEmpty(r14)
            if (r0 == 0) goto L_0x0014
            goto L_0x00df
        L_0x0014:
            monitor-enter(r13)
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r0 = r13.mFlowCtrlHolder     // Catch:{ all -> 0x00dc }
            java.lang.String r3 = "ALL"
            r4 = 0
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r0 = r0.get(r3, r4)     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r3 = r13.mFlowCtrlHolder     // Catch:{ all -> 0x00dc }
            java.lang.String r5 = "ALL_BRUSH"
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r3 = r3.get(r5, r4)     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r5 = r13.mFlowCtrlHolder     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r4 = r5.get(r14, r4)     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowCtrlInfoHolder r5 = r13.mFlowCtrlHolder     // Catch:{ all -> 0x00dc }
            com.taobao.accs.flowcontrol.FlowControl$FlowControlInfo r5 = r5.get(r14, r15)     // Catch:{ all -> 0x00dc }
            if (r0 == 0) goto L_0x003e
            boolean r6 = r0.isExpired()     // Catch:{ all -> 0x00dc }
            if (r6 == 0) goto L_0x003b
            goto L_0x003e
        L_0x003b:
            long r6 = r0.delayTime     // Catch:{ all -> 0x00dc }
            goto L_0x003f
        L_0x003e:
            r6 = r1
        L_0x003f:
            if (r3 == 0) goto L_0x004b
            boolean r8 = r3.isExpired()     // Catch:{ all -> 0x00dc }
            if (r8 == 0) goto L_0x0048
            goto L_0x004b
        L_0x0048:
            long r8 = r3.delayTime     // Catch:{ all -> 0x00dc }
            goto L_0x004c
        L_0x004b:
            r8 = r1
        L_0x004c:
            if (r4 == 0) goto L_0x0058
            boolean r3 = r4.isExpired()     // Catch:{ all -> 0x00dc }
            if (r3 == 0) goto L_0x0055
            goto L_0x0058
        L_0x0055:
            long r3 = r4.delayTime     // Catch:{ all -> 0x00dc }
            goto L_0x0059
        L_0x0058:
            r3 = r1
        L_0x0059:
            if (r5 == 0) goto L_0x0064
            boolean r10 = r5.isExpired()     // Catch:{ all -> 0x00dc }
            if (r10 == 0) goto L_0x0062
            goto L_0x0064
        L_0x0062:
            long r1 = r5.delayTime     // Catch:{ all -> 0x00dc }
        L_0x0064:
            r10 = -1
            int r12 = (r6 > r10 ? 1 : (r6 == r10 ? 0 : -1))
            if (r12 == 0) goto L_0x0088
            int r12 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r12 == 0) goto L_0x0088
            int r12 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r12 != 0) goto L_0x0073
            goto L_0x0088
        L_0x0073:
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 != 0) goto L_0x007a
            r8 = -1000(0xfffffffffffffc18, double:NaN)
            goto L_0x0089
        L_0x007a:
            int r8 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r8 <= 0) goto L_0x0080
            r8 = r6
            goto L_0x0081
        L_0x0080:
            r8 = r1
        L_0x0081:
            int r10 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r10 <= 0) goto L_0x0086
            goto L_0x0089
        L_0x0086:
            r8 = r3
            goto L_0x0089
        L_0x0088:
            r8 = r10
        L_0x0089:
            if (r5 == 0) goto L_0x0091
            boolean r5 = r5.isExpired()     // Catch:{ all -> 0x00dc }
            if (r5 != 0) goto L_0x0099
        L_0x0091:
            if (r0 == 0) goto L_0x009c
            boolean r0 = r0.isExpired()     // Catch:{ all -> 0x00dc }
            if (r0 == 0) goto L_0x009c
        L_0x0099:
            r13.checkFlowCtrl()     // Catch:{ all -> 0x00dc }
        L_0x009c:
            monitor-exit(r13)     // Catch:{ all -> 0x00dc }
            java.lang.String r0 = "FlowControl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r10 = "getFlowCtrlDelay service "
            r5.<init>(r10)
            r5.append(r14)
            java.lang.String r14 = " biz "
            r5.append(r14)
            r5.append(r15)
            java.lang.String r14 = " result:"
            r5.append(r14)
            r5.append(r8)
            java.lang.String r14 = " global:"
            r5.append(r14)
            r5.append(r6)
            java.lang.String r14 = " serviceDelay:"
            r5.append(r14)
            r5.append(r3)
            java.lang.String r14 = " bidDelay:"
            r5.append(r14)
            r5.append(r1)
            java.lang.String r14 = r5.toString()
            r15 = 0
            java.lang.Object[] r15 = new java.lang.Object[r15]
            com.taobao.accs.utl.ALog.e(r0, r14, r15)
            return r8
        L_0x00dc:
            r14 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x00dc }
            throw r14
        L_0x00df:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.flowcontrol.FlowControl.getFlowCtrlDelay(java.lang.String, java.lang.String):long");
    }

    private void checkFlowCtrl() {
        if (this.mFlowCtrlHolder != null && this.mFlowCtrlHolder.flowCtrlMap != null) {
            synchronized (this) {
                Iterator<Entry<String, FlowControlInfo>> it = this.mFlowCtrlHolder.flowCtrlMap.entrySet().iterator();
                while (it.hasNext()) {
                    if (((FlowControlInfo) it.next().getValue()).isExpired()) {
                        it.remove();
                    }
                }
            }
        }
    }
}
