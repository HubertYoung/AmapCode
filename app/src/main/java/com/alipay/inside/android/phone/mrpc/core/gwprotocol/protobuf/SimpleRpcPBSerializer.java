package com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf;

public class SimpleRpcPBSerializer extends PBSerializer {
    public static final String TAG = "SimpleRpcPBSerializer";
    private byte[] data;

    public SimpleRpcPBSerializer(int i, String str, Object obj) {
        super(i, str, obj);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:32|33|34|35|36|37|38|39) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00b4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] packet() throws com.alipay.inside.android.phone.mrpc.core.RpcException {
        /*
            r5 = this;
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Throwable -> 0x00bb }
            java.lang.String r1 = "SimpleRpc"
            java.lang.String r2 = "====SimpleRpcPBSerializer====packet"
            r0.a(r1, r2)     // Catch:{ Throwable -> 0x00bb }
            byte[] r0 = r5.data     // Catch:{ Throwable -> 0x00bb }
            if (r0 == 0) goto L_0x0012
            byte[] r0 = r5.data     // Catch:{ Throwable -> 0x00bb }
            return r0
        L_0x0012:
            monitor-enter(r5)     // Catch:{ Throwable -> 0x00bb }
            byte[] r0 = r5.data     // Catch:{ all -> 0x00b8 }
            if (r0 == 0) goto L_0x001b
            byte[] r0 = r5.data     // Catch:{ all -> 0x00b8 }
            monitor-exit(r5)     // Catch:{ all -> 0x00b8 }
            return r0
        L_0x001b:
            java.lang.Object r0 = r5.mParams     // Catch:{ all -> 0x00b8 }
            r1 = 0
            if (r0 != 0) goto L_0x0033
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00b8 }
            java.lang.String r2 = "SimpleRpcPBSerializer"
            java.lang.String r3 = "mParams is null."
            r0.c(r2, r3)     // Catch:{ all -> 0x00b8 }
            byte[] r0 = new byte[r1]     // Catch:{ all -> 0x00b8 }
            r5.data = r0     // Catch:{ all -> 0x00b8 }
            byte[] r0 = r5.data     // Catch:{ all -> 0x00b8 }
            monitor-exit(r5)     // Catch:{ all -> 0x00b8 }
            return r0
        L_0x0033:
            java.lang.Object r0 = r5.mParams     // Catch:{ all -> 0x00b8 }
            boolean r0 = r0 instanceof java.lang.Object[]     // Catch:{ all -> 0x00b8 }
            if (r0 != 0) goto L_0x004c
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00b8 }
            java.lang.String r2 = "SimpleRpcPBSerializer"
            java.lang.String r3 = "mParams not instanceof Object[]."
            r0.c(r2, r3)     // Catch:{ all -> 0x00b8 }
            byte[] r0 = new byte[r1]     // Catch:{ all -> 0x00b8 }
            r5.data = r0     // Catch:{ all -> 0x00b8 }
            byte[] r0 = r5.data     // Catch:{ all -> 0x00b8 }
            monitor-exit(r5)     // Catch:{ all -> 0x00b8 }
            return r0
        L_0x004c:
            java.lang.Object r0 = r5.mParams     // Catch:{ all -> 0x00b8 }
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ all -> 0x00b8 }
            int r2 = r0.length     // Catch:{ all -> 0x00b8 }
            if (r2 > 0) goto L_0x0066
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00b8 }
            java.lang.String r2 = "SimpleRpcPBSerializer"
            java.lang.String r3 = "Protobuf mParams length=0"
            r0.c(r2, r3)     // Catch:{ all -> 0x00b8 }
            byte[] r0 = new byte[r1]     // Catch:{ all -> 0x00b8 }
            r5.data = r0     // Catch:{ all -> 0x00b8 }
            byte[] r0 = r5.data     // Catch:{ all -> 0x00b8 }
            monitor-exit(r5)     // Catch:{ all -> 0x00b8 }
            return r0
        L_0x0066:
            int r1 = r0.length     // Catch:{ all -> 0x00b8 }
            r2 = 3
            if (r1 == r2) goto L_0x0085
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00b8 }
            java.lang.String r2 = "SimpleRpcPBSerializer"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b8 }
            java.lang.String r4 = "SimpleRpcService should be 3 params, params.length="
            r3.<init>(r4)     // Catch:{ all -> 0x00b8 }
            int r0 = r0.length     // Catch:{ all -> 0x00b8 }
            r3.append(r0)     // Catch:{ all -> 0x00b8 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x00b8 }
            r1.c(r2, r0)     // Catch:{ all -> 0x00b8 }
            r0 = 0
            monitor-exit(r5)     // Catch:{ all -> 0x00b8 }
            return r0
        L_0x0085:
            r1 = 1
            r0 = r0[r1]     // Catch:{ all -> 0x00b8 }
            byte[] r0 = (byte[]) r0     // Catch:{ all -> 0x00b8 }
            r5.data = r0     // Catch:{ all -> 0x00b8 }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r1 = "SimpleRpcPBSerializer"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r3 = "PB Data size="
            r2.<init>(r3)     // Catch:{ Exception -> 0x00b4 }
            byte[] r3 = r5.data     // Catch:{ Exception -> 0x00b4 }
            int r3 = r3.length     // Catch:{ Exception -> 0x00b4 }
            r2.append(r3)     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r3 = ",PB Data="
            r2.append(r3)     // Catch:{ Exception -> 0x00b4 }
            byte[] r3 = r5.data     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00b4 }
            r2.append(r3)     // Catch:{ Exception -> 0x00b4 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00b4 }
            r0.a(r1, r2)     // Catch:{ Exception -> 0x00b4 }
        L_0x00b4:
            monitor-exit(r5)     // Catch:{ all -> 0x00b8 }
            byte[] r0 = r5.data     // Catch:{ Throwable -> 0x00bb }
            return r0
        L_0x00b8:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x00b8 }
            throw r0     // Catch:{ Throwable -> 0x00bb }
        L_0x00bb:
            r0 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "SimpleRpcPBSerializer"
            r1.b(r2, r0)
            com.alipay.inside.android.phone.mrpc.core.RpcException r1 = new com.alipay.inside.android.phone.mrpc.core.RpcException
            r2 = 9
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.SimpleRpcPBSerializer.packet():byte[]");
    }
}
