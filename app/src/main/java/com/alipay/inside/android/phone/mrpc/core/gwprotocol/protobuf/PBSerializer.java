package com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf;

import android.annotation.TargetApi;
import android.util.Base64;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.inside.android.phone.mrpc.core.RpcException;
import com.alipay.inside.android.phone.mrpc.core.gwprotocol.AbstractSerializer;
import java.security.MessageDigest;

public class PBSerializer extends AbstractSerializer {
    private static final String TAG = "PBSerializer";
    public static final byte VERSION = 2;
    private byte[] data;
    private Object mExtParam;
    private int mId;

    public PBSerializer(int i, String str, Object obj) {
        super(str, obj);
        this.mId = i;
    }

    public void setExtParam(Object obj) {
        this.mExtParam = obj;
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("PBSerializer::setExtParam >");
        sb.append(this.mExtParam);
        f.b((String) "rpc", sb.toString());
    }

    public byte[] packet() throws RpcException {
        return toByteArray();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:37|38|39|40|41|42|43|44) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x00dd */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private byte[] toByteArray() {
        /*
            r7 = this;
            byte[] r0 = r7.data     // Catch:{ Throwable -> 0x00e4 }
            if (r0 == 0) goto L_0x0007
            byte[] r0 = r7.data     // Catch:{ Throwable -> 0x00e4 }
            return r0
        L_0x0007:
            monitor-enter(r7)     // Catch:{ Throwable -> 0x00e4 }
            byte[] r0 = r7.data     // Catch:{ all -> 0x00e1 }
            if (r0 == 0) goto L_0x0010
            byte[] r0 = r7.data     // Catch:{ all -> 0x00e1 }
            monitor-exit(r7)     // Catch:{ all -> 0x00e1 }
            return r0
        L_0x0010:
            java.lang.Object r0 = r7.mParams     // Catch:{ all -> 0x00e1 }
            r1 = 0
            if (r0 != 0) goto L_0x0028
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = "PBSerializer"
            java.lang.String r3 = "mParams is null."
            r0.c(r2, r3)     // Catch:{ all -> 0x00e1 }
            byte[] r0 = new byte[r1]     // Catch:{ all -> 0x00e1 }
            r7.data = r0     // Catch:{ all -> 0x00e1 }
            byte[] r0 = r7.data     // Catch:{ all -> 0x00e1 }
            monitor-exit(r7)     // Catch:{ all -> 0x00e1 }
            return r0
        L_0x0028:
            java.lang.Object r0 = r7.mParams     // Catch:{ all -> 0x00e1 }
            boolean r0 = r0 instanceof java.lang.Object[]     // Catch:{ all -> 0x00e1 }
            if (r0 != 0) goto L_0x0041
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = "PBSerializer"
            java.lang.String r3 = "mParams not instanceof Object[]."
            r0.c(r2, r3)     // Catch:{ all -> 0x00e1 }
            byte[] r0 = new byte[r1]     // Catch:{ all -> 0x00e1 }
            r7.data = r0     // Catch:{ all -> 0x00e1 }
            byte[] r0 = r7.data     // Catch:{ all -> 0x00e1 }
            monitor-exit(r7)     // Catch:{ all -> 0x00e1 }
            return r0
        L_0x0041:
            java.lang.Object r0 = r7.mParams     // Catch:{ all -> 0x00e1 }
            java.lang.Object[] r0 = (java.lang.Object[]) r0     // Catch:{ all -> 0x00e1 }
            int r2 = r0.length     // Catch:{ all -> 0x00e1 }
            if (r2 > 0) goto L_0x005b
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = "PBSerializer"
            java.lang.String r3 = "Protobuf mParams length=0"
            r0.c(r2, r3)     // Catch:{ all -> 0x00e1 }
            byte[] r0 = new byte[r1]     // Catch:{ all -> 0x00e1 }
            r7.data = r0     // Catch:{ all -> 0x00e1 }
            byte[] r0 = r7.data     // Catch:{ all -> 0x00e1 }
            monitor-exit(r7)     // Catch:{ all -> 0x00e1 }
            return r0
        L_0x005b:
            int r2 = r0.length     // Catch:{ all -> 0x00e1 }
            r3 = 1
            if (r2 == r3) goto L_0x007a
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = "PBSerializer"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00e1 }
            java.lang.String r4 = "Protobuf mParams noly support one inParameter, params.length="
            r3.<init>(r4)     // Catch:{ all -> 0x00e1 }
            int r0 = r0.length     // Catch:{ all -> 0x00e1 }
            r3.append(r0)     // Catch:{ all -> 0x00e1 }
            java.lang.String r0 = r3.toString()     // Catch:{ all -> 0x00e1 }
            r1.c(r2, r0)     // Catch:{ all -> 0x00e1 }
            r0 = 0
            monitor-exit(r7)     // Catch:{ all -> 0x00e1 }
            return r0
        L_0x007a:
            r2 = r0[r1]     // Catch:{ all -> 0x00e1 }
            java.lang.Class r2 = r2.getClass()     // Catch:{ all -> 0x00e1 }
            com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.ProtobufCodec r2 = com.alipay.inside.android.phone.mrpc.core.gwprotocol.util.ProtobufCodecUtil.getProtobufCodec(r2)     // Catch:{ all -> 0x00e1 }
            r3 = r0[r1]     // Catch:{ all -> 0x00e1 }
            boolean r3 = r2.isPBBean(r3)     // Catch:{ all -> 0x00e1 }
            if (r3 != 0) goto L_0x009f
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r0 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x00e1 }
            java.lang.String r2 = "PBSerializer"
            java.lang.String r3 = "mParams not protobuf bean!"
            r0.c(r2, r3)     // Catch:{ all -> 0x00e1 }
            byte[] r0 = new byte[r1]     // Catch:{ all -> 0x00e1 }
            r7.data = r0     // Catch:{ all -> 0x00e1 }
            byte[] r0 = r7.data     // Catch:{ all -> 0x00e1 }
            monitor-exit(r7)     // Catch:{ all -> 0x00e1 }
            return r0
        L_0x009f:
            r3 = r0[r1]     // Catch:{ all -> 0x00e1 }
            byte[] r3 = r2.serialize(r3)     // Catch:{ all -> 0x00e1 }
            r7.data = r3     // Catch:{ all -> 0x00e1 }
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r3 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ Exception -> 0x00dd }
            java.lang.String r4 = "PBSerializer"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00dd }
            java.lang.String r6 = "PB Data size="
            r5.<init>(r6)     // Catch:{ Exception -> 0x00dd }
            byte[] r6 = r7.data     // Catch:{ Exception -> 0x00dd }
            int r6 = r6.length     // Catch:{ Exception -> 0x00dd }
            r5.append(r6)     // Catch:{ Exception -> 0x00dd }
            java.lang.String r6 = ". PB Data="
            r5.append(r6)     // Catch:{ Exception -> 0x00dd }
            byte[] r6 = r7.data     // Catch:{ Exception -> 0x00dd }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x00dd }
            r5.append(r6)     // Catch:{ Exception -> 0x00dd }
            java.lang.String r6 = ".PB Object String = "
            r5.append(r6)     // Catch:{ Exception -> 0x00dd }
            r0 = r0[r1]     // Catch:{ Exception -> 0x00dd }
            java.lang.String r0 = r2.toString(r0)     // Catch:{ Exception -> 0x00dd }
            r5.append(r0)     // Catch:{ Exception -> 0x00dd }
            java.lang.String r0 = r5.toString()     // Catch:{ Exception -> 0x00dd }
            r3.a(r4, r0)     // Catch:{ Exception -> 0x00dd }
        L_0x00dd:
            monitor-exit(r7)     // Catch:{ all -> 0x00e1 }
            byte[] r0 = r7.data     // Catch:{ Throwable -> 0x00e4 }
            return r0
        L_0x00e1:
            r0 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x00e1 }
            throw r0     // Catch:{ Throwable -> 0x00e4 }
        L_0x00e4:
            r0 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "PBSerializer"
            java.lang.String r3 = "toByteArray ex:"
            r1.b(r2, r3, r0)
            com.alipay.inside.android.phone.mrpc.core.RpcException r1 = new com.alipay.inside.android.phone.mrpc.core.RpcException
            r2 = 20
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.inside.android.phone.mrpc.core.gwprotocol.protobuf.PBSerializer.toByteArray():byte[]");
    }

    public int getId() {
        return this.mId;
    }

    public void setId(int i) {
        this.mId = i;
    }

    @TargetApi(8)
    public String getRequestDataDigest() {
        try {
            return new String(Base64.encode(MessageDigest.getInstance("MD5").digest(packet()), 0));
        } catch (Exception e) {
            LoggerFactory.f().a((String) TAG, (Throwable) e);
            return "";
        }
    }
}
