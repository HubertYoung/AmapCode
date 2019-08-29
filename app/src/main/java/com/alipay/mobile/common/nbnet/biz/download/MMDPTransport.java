package com.alipay.mobile.common.nbnet.biz.download;

import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPReq;
import com.alipay.mobile.common.nbnet.api.download.proto.MMDPResp;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetNoResponseException;
import com.alipay.mobile.common.nbnet.biz.exception.NBNetProtocolException;
import com.alipay.mobile.common.nbnet.biz.log.MonitorLogUtil;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.transport.Route;
import com.alipay.mobile.common.nbnet.biz.transport.Transport;
import com.alipay.mobile.common.nbnet.biz.util.MD5Utils;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoConstants;
import com.squareup.wire.Wire;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public abstract class MMDPTransport implements Transport<MMDPReq, MMDPResp> {
    private static byte d = 1;
    private static int e = 10240;
    private static int f = AutoConstants.DATASERVICE_ERRORCODE_FILE_NOT_EXIST;
    protected NBNetContext a;
    protected final Route b;
    protected final ByteArrayOutputStream c = new ByteArrayOutputStream();

    public abstract OutputStream a();

    public MMDPTransport(Route route) {
        this.b = route;
    }

    public final void a(MMDPReq request) {
        f().write(request.toByteArray());
    }

    public final long b() {
        DataOutputStream output = new DataOutputStream(a());
        long startSentTime = System.currentTimeMillis();
        try {
            byte[] requestBody = this.c.toByteArray();
            byte[] requestBodyMD5 = MD5Utils.a(requestBody);
            output.writeByte(f);
            int requestLength = requestBodyMD5.length + 9 + requestBody.length;
            output.writeInt(requestLength);
            output.writeInt(0);
            output.writeByte(d);
            output.write(requestBodyMD5);
            output.write(requestBody);
            output.flush();
            this.c.reset();
            return (long) requestLength;
        } finally {
            MonitorLogUtil.f(this.a, System.currentTimeMillis() - startSentTime);
        }
    }

    private OutputStream f() {
        return this.c;
    }

    public final MMDPResp a_() {
        DataInputStream input = new DataInputStream(c());
        long readHeadStartTime = System.currentTimeMillis();
        int headerLen = input.readInt();
        MonitorLogUtil.g(this.a, System.currentTimeMillis() - readHeadStartTime);
        NBNetLogCat.b((String) "MMDPTransport", String.format("monitor_perf: read first byte. len: %d, cost_time: %d", new Object[]{Integer.valueOf(headerLen), Long.valueOf(System.currentTimeMillis() - readHeadStartTime)}));
        if (headerLen > e) {
            throw new NBNetProtocolException("response body length to large:" + headerLen);
        } else if (headerLen == -1) {
            throw new NBNetNoResponseException();
        } else {
            byte[] respPbMD5Bytes = new byte[16];
            if (input.read(respPbMD5Bytes) == -1) {
                throw new NBNetNoResponseException();
            }
            int respPbLen = (headerLen - 4) - 16;
            if (respPbLen <= 0) {
                throw new NBNetProtocolException("Illegal respPbLen: " + respPbLen + " , because illegal headerLen: " + headerLen);
            }
            byte[] respPbBytes = new byte[respPbLen];
            long readPbStartTime = System.currentTimeMillis();
            int readedRespPbBytesLen = input.read(respPbBytes);
            NBNetLogCat.b((String) "MMDPTransport", String.format("monitor_perf: read respPbBytes. len: %d, cost_time: %d", new Object[]{Integer.valueOf(readedRespPbBytesLen), Long.valueOf(System.currentTimeMillis() - readPbStartTime)}));
            if (readedRespPbBytesLen == -1) {
                throw new NBNetNoResponseException();
            } else if (!Arrays.equals(MD5Utils.a(respPbBytes), respPbMD5Bytes)) {
                throw new NBNetProtocolException("verify response body md5 fail");
            } else {
                try {
                    return (MMDPResp) new Wire((Class<?>[]) new Class[0]).parseFrom(respPbBytes, MMDPResp.class);
                } catch (IOException e2) {
                    throw new NBNetProtocolException("parse download response fail", e2);
                }
            }
        }
    }

    public final void a(NBNetContext sessionContext) {
        this.a = sessionContext;
    }
}
