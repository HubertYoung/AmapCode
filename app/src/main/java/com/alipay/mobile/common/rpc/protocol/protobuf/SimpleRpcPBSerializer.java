package com.alipay.mobile.common.rpc.protocol.protobuf;

public class SimpleRpcPBSerializer extends PBSerializer {
    public static final String TAG = "SimpleRpcPBSerializer";
    private byte[] data;

    public SimpleRpcPBSerializer(int id, String operationType, Object params) {
        super(id, operationType, params);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public byte[] packet() {
        /*
            r6 = this;
            java.lang.String r3 = "SimpleRpc"
            java.lang.String r4 = "====SimpleRpcPBSerializer====packet"
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r3, r4)     // Catch:{ Throwable -> 0x001a }
            byte[] r3 = r6.data     // Catch:{ Throwable -> 0x001a }
            if (r3 == 0) goto L_0x000e
            byte[] r3 = r6.data     // Catch:{ Throwable -> 0x001a }
        L_0x000d:
            return r3
        L_0x000e:
            monitor-enter(r6)     // Catch:{ Throwable -> 0x001a }
            byte[] r3 = r6.data     // Catch:{ all -> 0x0017 }
            if (r3 == 0) goto L_0x002c
            byte[] r3 = r6.data     // Catch:{ all -> 0x0017 }
            monitor-exit(r6)     // Catch:{ all -> 0x0017 }
            goto L_0x000d
        L_0x0017:
            r3 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0017 }
            throw r3     // Catch:{ Throwable -> 0x001a }
        L_0x001a:
            r1 = move-exception
            java.lang.String r3 = "SimpleRpcPBSerializer"
            com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper.log(r3, r1)
            com.alipay.mobile.common.rpc.RpcException r3 = new com.alipay.mobile.common.rpc.RpcException
            r4 = 20
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3.<init>(r4, r1)
            throw r3
        L_0x002c:
            java.lang.Object r3 = r6.mParams     // Catch:{ all -> 0x0017 }
            if (r3 != 0) goto L_0x0040
            java.lang.String r3 = "SimpleRpcPBSerializer"
            java.lang.String r4 = "mParams is null."
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r3, r4)     // Catch:{ all -> 0x0017 }
            r3 = 0
            byte[] r3 = new byte[r3]     // Catch:{ all -> 0x0017 }
            r6.data = r3     // Catch:{ all -> 0x0017 }
            byte[] r3 = r6.data     // Catch:{ all -> 0x0017 }
            monitor-exit(r6)     // Catch:{ all -> 0x0017 }
            goto L_0x000d
        L_0x0040:
            java.lang.Object r3 = r6.mParams     // Catch:{ all -> 0x0017 }
            boolean r3 = r3 instanceof java.lang.Object[]     // Catch:{ all -> 0x0017 }
            if (r3 != 0) goto L_0x0056
            java.lang.String r3 = "SimpleRpcPBSerializer"
            java.lang.String r4 = "mParams not instanceof Object[]."
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r3, r4)     // Catch:{ all -> 0x0017 }
            r3 = 0
            byte[] r3 = new byte[r3]     // Catch:{ all -> 0x0017 }
            r6.data = r3     // Catch:{ all -> 0x0017 }
            byte[] r3 = r6.data     // Catch:{ all -> 0x0017 }
            monitor-exit(r6)     // Catch:{ all -> 0x0017 }
            goto L_0x000d
        L_0x0056:
            java.lang.Object r3 = r6.mParams     // Catch:{ all -> 0x0017 }
            java.lang.Object[] r3 = (java.lang.Object[]) r3     // Catch:{ all -> 0x0017 }
            r0 = r3
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ all -> 0x0017 }
            r2 = r0
            int r3 = r2.length     // Catch:{ all -> 0x0017 }
            if (r3 > 0) goto L_0x0071
            java.lang.String r3 = "SimpleRpcPBSerializer"
            java.lang.String r4 = "Protobuf mParams length=0"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r3, r4)     // Catch:{ all -> 0x0017 }
            r3 = 0
            byte[] r3 = new byte[r3]     // Catch:{ all -> 0x0017 }
            r6.data = r3     // Catch:{ all -> 0x0017 }
            byte[] r3 = r6.data     // Catch:{ all -> 0x0017 }
            monitor-exit(r6)     // Catch:{ all -> 0x0017 }
            goto L_0x000d
        L_0x0071:
            int r3 = r2.length     // Catch:{ all -> 0x0017 }
            r4 = 3
            if (r3 == r4) goto L_0x008d
            java.lang.String r3 = "SimpleRpcPBSerializer"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0017 }
            java.lang.String r5 = "SimpleRpcService should be 3 params, params.length="
            r4.<init>(r5)     // Catch:{ all -> 0x0017 }
            int r5 = r2.length     // Catch:{ all -> 0x0017 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ all -> 0x0017 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0017 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r3, r4)     // Catch:{ all -> 0x0017 }
            r3 = 0
            monitor-exit(r6)     // Catch:{ all -> 0x0017 }
            goto L_0x000d
        L_0x008d:
            r3 = 1
            r3 = r2[r3]     // Catch:{ all -> 0x0017 }
            byte[] r3 = (byte[]) r3     // Catch:{ all -> 0x0017 }
            byte[] r3 = (byte[]) r3     // Catch:{ all -> 0x0017 }
            r6.data = r3     // Catch:{ all -> 0x0017 }
            java.lang.String r3 = "SimpleRpcPBSerializer"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c2 }
            java.lang.String r5 = "PB Data size="
            r4.<init>(r5)     // Catch:{ Exception -> 0x00c2 }
            byte[] r5 = r6.data     // Catch:{ Exception -> 0x00c2 }
            int r5 = r5.length     // Catch:{ Exception -> 0x00c2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x00c2 }
            java.lang.String r5 = ",PB Data="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x00c2 }
            byte[] r5 = r6.data     // Catch:{ Exception -> 0x00c2 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00c2 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x00c2 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x00c2 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r3, r4)     // Catch:{ Exception -> 0x00c2 }
        L_0x00bd:
            monitor-exit(r6)     // Catch:{ all -> 0x0017 }
            byte[] r3 = r6.data     // Catch:{ Throwable -> 0x001a }
            goto L_0x000d
        L_0x00c2:
            r3 = move-exception
            goto L_0x00bd
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.rpc.protocol.protobuf.SimpleRpcPBSerializer.packet():byte[]");
    }
}
