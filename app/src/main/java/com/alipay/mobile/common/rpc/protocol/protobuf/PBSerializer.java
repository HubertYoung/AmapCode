package com.alipay.mobile.common.rpc.protocol.protobuf;

import android.annotation.TargetApi;
import android.util.Base64;
import com.alipay.mobile.common.rpc.protocol.AbstractSerializer;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import java.security.MessageDigest;

public class PBSerializer extends AbstractSerializer {
    private static final String TAG = "PBSerializer";
    public static final byte VERSION = 2;
    private byte[] data;
    private Object mExtParam;
    private int mId;

    public PBSerializer(int id, String operationType, Object params) {
        super(operationType, params);
        this.mId = id;
    }

    public void setExtParam(Object o) {
        this.mExtParam = o;
    }

    public byte[] packet() {
        return toByteArray();
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] toByteArray() {
        /*
            r7 = this;
            byte[] r4 = r7.data     // Catch:{ Throwable -> 0x0013 }
            if (r4 == 0) goto L_0x0007
            byte[] r4 = r7.data     // Catch:{ Throwable -> 0x0013 }
        L_0x0006:
            return r4
        L_0x0007:
            monitor-enter(r7)     // Catch:{ Throwable -> 0x0013 }
            byte[] r4 = r7.data     // Catch:{ all -> 0x0010 }
            if (r4 == 0) goto L_0x0025
            byte[] r4 = r7.data     // Catch:{ all -> 0x0010 }
            monitor-exit(r7)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x0010:
            r4 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x0010 }
            throw r4     // Catch:{ Throwable -> 0x0013 }
        L_0x0013:
            r1 = move-exception
            java.lang.String r4 = "PBSerializer"
            com.alipay.mobile.common.transport.utils.MonitorErrorLogHelper.log(r4, r1)
            com.alipay.mobile.common.rpc.RpcException r4 = new com.alipay.mobile.common.rpc.RpcException
            r5 = 20
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4.<init>(r5, r1)
            throw r4
        L_0x0025:
            java.lang.Object r4 = r7.mParams     // Catch:{ all -> 0x0010 }
            if (r4 != 0) goto L_0x0039
            java.lang.String r4 = "PBSerializer"
            java.lang.String r5 = "mParams is null."
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r4, r5)     // Catch:{ all -> 0x0010 }
            r4 = 0
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x0010 }
            r7.data = r4     // Catch:{ all -> 0x0010 }
            byte[] r4 = r7.data     // Catch:{ all -> 0x0010 }
            monitor-exit(r7)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x0039:
            java.lang.Object r4 = r7.mParams     // Catch:{ all -> 0x0010 }
            boolean r4 = r4 instanceof java.lang.Object[]     // Catch:{ all -> 0x0010 }
            if (r4 != 0) goto L_0x004f
            java.lang.String r4 = "PBSerializer"
            java.lang.String r5 = "mParams not instanceof Object[]."
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r4, r5)     // Catch:{ all -> 0x0010 }
            r4 = 0
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x0010 }
            r7.data = r4     // Catch:{ all -> 0x0010 }
            byte[] r4 = r7.data     // Catch:{ all -> 0x0010 }
            monitor-exit(r7)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x004f:
            java.lang.Object r4 = r7.mParams     // Catch:{ all -> 0x0010 }
            java.lang.Object[] r4 = (java.lang.Object[]) r4     // Catch:{ all -> 0x0010 }
            r0 = r4
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ all -> 0x0010 }
            r2 = r0
            int r4 = r2.length     // Catch:{ all -> 0x0010 }
            if (r4 > 0) goto L_0x006a
            java.lang.String r4 = "PBSerializer"
            java.lang.String r5 = "Protobuf mParams length=0"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r4, r5)     // Catch:{ all -> 0x0010 }
            r4 = 0
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x0010 }
            r7.data = r4     // Catch:{ all -> 0x0010 }
            byte[] r4 = r7.data     // Catch:{ all -> 0x0010 }
            monitor-exit(r7)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x006a:
            int r4 = r2.length     // Catch:{ all -> 0x0010 }
            r5 = 1
            if (r4 == r5) goto L_0x0086
            java.lang.String r4 = "PBSerializer"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0010 }
            java.lang.String r6 = "Protobuf mParams noly support one inParameter, params.length="
            r5.<init>(r6)     // Catch:{ all -> 0x0010 }
            int r6 = r2.length     // Catch:{ all -> 0x0010 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0010 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0010 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r4, r5)     // Catch:{ all -> 0x0010 }
            r4 = 0
            monitor-exit(r7)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x0086:
            com.alipay.mobile.common.transport.ext.ProtobufCodecImpl r3 = new com.alipay.mobile.common.transport.ext.ProtobufCodecImpl     // Catch:{ all -> 0x0010 }
            r3.<init>()     // Catch:{ all -> 0x0010 }
            r4 = 0
            r4 = r2[r4]     // Catch:{ all -> 0x0010 }
            boolean r4 = r3.isPBBean(r4)     // Catch:{ all -> 0x0010 }
            if (r4 != 0) goto L_0x00a5
            java.lang.String r4 = "PBSerializer"
            java.lang.String r5 = "mParams not protobuf bean!"
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r4, r5)     // Catch:{ all -> 0x0010 }
            r4 = 0
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x0010 }
            r7.data = r4     // Catch:{ all -> 0x0010 }
            byte[] r4 = r7.data     // Catch:{ all -> 0x0010 }
            monitor-exit(r7)     // Catch:{ all -> 0x0010 }
            goto L_0x0006
        L_0x00a5:
            r4 = 0
            r4 = r2[r4]     // Catch:{ all -> 0x0010 }
            byte[] r4 = r3.serialize(r4)     // Catch:{ all -> 0x0010 }
            r7.data = r4     // Catch:{ all -> 0x0010 }
            java.lang.String r4 = "PBSerializer"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00eb }
            java.lang.String r6 = "PB Data size="
            r5.<init>(r6)     // Catch:{ Exception -> 0x00eb }
            byte[] r6 = r7.data     // Catch:{ Exception -> 0x00eb }
            int r6 = r6.length     // Catch:{ Exception -> 0x00eb }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x00eb }
            java.lang.String r6 = ". PB Data="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x00eb }
            byte[] r6 = r7.data     // Catch:{ Exception -> 0x00eb }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00eb }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x00eb }
            java.lang.String r6 = ".PB Object String = "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x00eb }
            r6 = 0
            r6 = r2[r6]     // Catch:{ Exception -> 0x00eb }
            java.lang.String r6 = r3.toString(r6)     // Catch:{ Exception -> 0x00eb }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Exception -> 0x00eb }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00eb }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r4, r5)     // Catch:{ Exception -> 0x00eb }
        L_0x00e6:
            monitor-exit(r7)     // Catch:{ all -> 0x0010 }
            byte[] r4 = r7.data     // Catch:{ Throwable -> 0x0013 }
            goto L_0x0006
        L_0x00eb:
            r4 = move-exception
            goto L_0x00e6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.rpc.protocol.protobuf.PBSerializer.toByteArray():byte[]");
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    @TargetApi(8)
    public String getRequestDataDigest() {
        try {
            return new String(Base64.encode(MessageDigest.getInstance("MD5").digest(packet()), 0));
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, (Throwable) e);
            return "";
        }
    }
}
