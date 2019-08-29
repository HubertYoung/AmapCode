package com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal;

import com.alipay.mobile.common.amnet.api.model.ResultFeedback;
import com.alipay.mobile.common.transport.monitor.SignalStateHelper;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transportext.biz.diagnose.network.DiagnoseBySystemCall;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcRequest;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse;

public class MRpcStream {
    private static final int DEFAULT_TIMEOUT = 10000;
    private static final String TAG = "MRpcStream";
    private static int mmtpErrorCount = 0;
    private MRpcConnection mMRpcConnection;
    private MRpcRequest mMRpcRequest;
    private MRpcResponse mRpcResponse;
    private MRpcResultCode mRpcResultCode = null;
    private int reqSize;
    private int streamId;
    private int timeout;

    public MRpcStream(int streamId2, MRpcConnection mRpcConnection) {
        this.mMRpcConnection = mRpcConnection;
        this.streamId = streamId2;
    }

    public void sendRequest(MRpcRequest mRpcRequest) {
        this.mMRpcRequest = mRpcRequest;
        this.mMRpcConnection.sendRequest(mRpcRequest);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0039, code lost:
        if (r2 == null) goto L_0x003b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse getResponse() {
        /*
            r9 = this;
            monitor-enter(r9)
            r3 = 10000(0x2710, float:1.4013E-41)
            int r5 = r9.timeout     // Catch:{ InterruptedException -> 0x004c }
            if (r5 <= 0) goto L_0x0009
            int r3 = r9.timeout     // Catch:{ InterruptedException -> 0x004c }
        L_0x0009:
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse r5 = r9.mRpcResponse     // Catch:{ InterruptedException -> 0x004c }
            if (r5 != 0) goto L_0x0015
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r5 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            if (r5 != 0) goto L_0x0015
            long r5 = (long) r3     // Catch:{ InterruptedException -> 0x004c }
            r9.wait(r5)     // Catch:{ InterruptedException -> 0x004c }
        L_0x0015:
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse r5 = r9.mRpcResponse     // Catch:{ InterruptedException -> 0x004c }
            if (r5 == 0) goto L_0x0025
            r5 = -1
            r6 = 0
            r9.notifyAmnetRespResult(r5, r6)     // Catch:{ InterruptedException -> 0x004c }
            r5 = 0
            mmtpErrorCount = r5     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse r2 = r9.mRpcResponse     // Catch:{ InterruptedException -> 0x004c }
        L_0x0023:
            monitor-exit(r9)
            return r2
        L_0x0025:
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r5 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            if (r5 == 0) goto L_0x0059
            r5 = 0
            r9.notifyAmnetRespResult(r3, r5)     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r5 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            int r5 = r5.code     // Catch:{ InterruptedException -> 0x004c }
            r6 = 2001(0x7d1, float:2.804E-42)
            if (r5 != r6) goto L_0x003b
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse r2 = com.alipay.mobile.common.transportext.biz.util.AmnetLimitingHelper.getLimitingMRpcResponse()     // Catch:{ InterruptedException -> 0x004c }
            if (r2 != 0) goto L_0x0023
        L_0x003b:
            r9.tryNetworkDiagnose()     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transport.ext.MMTPException r5 = new com.alipay.mobile.common.transport.ext.MMTPException     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r6 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            int r6 = r6.code     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r7 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r7 = r7.msg     // Catch:{ InterruptedException -> 0x004c }
            r5.<init>(r6, r7)     // Catch:{ InterruptedException -> 0x004c }
            throw r5     // Catch:{ InterruptedException -> 0x004c }
        L_0x004c:
            r0 = move-exception
            java.io.InterruptedIOException r4 = new java.io.InterruptedIOException     // Catch:{ all -> 0x0056 }
            r4.<init>()     // Catch:{ all -> 0x0056 }
            r4.initCause(r0)     // Catch:{ all -> 0x0056 }
            throw r4     // Catch:{ all -> 0x0056 }
        L_0x0056:
            r5 = move-exception
            monitor-exit(r9)
            throw r5
        L_0x0059:
            r5 = 1
            r9.notifyAmnetRespResult(r3, r5)     // Catch:{ InterruptedException -> 0x004c }
            r1 = 0
        L_0x005e:
            r5 = 100
            if (r1 >= r5) goto L_0x006e
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r5 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            if (r5 != 0) goto L_0x006e
            r5 = 1
            java.lang.Thread.sleep(r5)     // Catch:{ InterruptedException -> 0x004c }
            int r1 = r1 + 1
            goto L_0x005e
        L_0x006e:
            r9.tryNetworkDiagnose()     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r5 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            if (r5 != 0) goto L_0x0092
            com.alipay.mobile.common.transport.ext.MMTPException r5 = new com.alipay.mobile.common.transport.ext.MMTPException     // Catch:{ InterruptedException -> 0x004c }
            r6 = 1001(0x3e9, float:1.403E-42)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r8 = "stream read timeout!  timeout=["
            r7.<init>(r8)     // Catch:{ InterruptedException -> 0x004c }
            java.lang.StringBuilder r7 = r7.append(r3)     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r8 = "]"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r7 = r7.toString()     // Catch:{ InterruptedException -> 0x004c }
            r5.<init>(r6, r7)     // Catch:{ InterruptedException -> 0x004c }
            throw r5     // Catch:{ InterruptedException -> 0x004c }
        L_0x0092:
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r5 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            int r5 = r5.code     // Catch:{ InterruptedException -> 0x004c }
            if (r5 == 0) goto L_0x00c3
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r5 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r5 = r5.msg     // Catch:{ InterruptedException -> 0x004c }
            boolean r5 = com.alipay.mobile.common.transport.ext.MMTPException.isTlsError(r5)     // Catch:{ InterruptedException -> 0x004c }
            if (r5 == 0) goto L_0x00b5
            javax.net.ssl.SSLException r5 = new javax.net.ssl.SSLException     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transport.ext.MMTPException r6 = new com.alipay.mobile.common.transport.ext.MMTPException     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r7 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            int r7 = r7.code     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r8 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r8 = r8.msg     // Catch:{ InterruptedException -> 0x004c }
            r6.<init>(r7, r8)     // Catch:{ InterruptedException -> 0x004c }
            r5.<init>(r6)     // Catch:{ InterruptedException -> 0x004c }
            throw r5     // Catch:{ InterruptedException -> 0x004c }
        L_0x00b5:
            com.alipay.mobile.common.transport.ext.MMTPException r5 = new com.alipay.mobile.common.transport.ext.MMTPException     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r6 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            int r6 = r6.code     // Catch:{ InterruptedException -> 0x004c }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r7 = r9.mRpcResultCode     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r7 = r7.msg     // Catch:{ InterruptedException -> 0x004c }
            r5.<init>(r6, r7)     // Catch:{ InterruptedException -> 0x004c }
            throw r5     // Catch:{ InterruptedException -> 0x004c }
        L_0x00c3:
            com.alipay.mobile.common.transport.ext.MMTPException r5 = new com.alipay.mobile.common.transport.ext.MMTPException     // Catch:{ InterruptedException -> 0x004c }
            r6 = 1001(0x3e9, float:1.403E-42)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r8 = "stream read timeout!  timeout=["
            r7.<init>(r8)     // Catch:{ InterruptedException -> 0x004c }
            java.lang.StringBuilder r7 = r7.append(r3)     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r8 = "]"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ InterruptedException -> 0x004c }
            java.lang.String r7 = r7.toString()     // Catch:{ InterruptedException -> 0x004c }
            r5.<init>(r6, r7)     // Catch:{ InterruptedException -> 0x004c }
            throw r5     // Catch:{ InterruptedException -> 0x004c }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream.getResponse():com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse");
    }

    private void notifyAmnetRespResult(int timeout2, boolean sync) {
        if (this.mMRpcRequest != null) {
            ResultFeedback alerting = new ResultFeedback();
            alerting.id = 1;
            alerting.receipt = (long) this.mMRpcRequest.reqSeqId;
            alerting.duration = (long) timeout2;
            if (sync) {
                AmnetHelper.notifyResultFeedback(alerting);
            } else {
                AmnetHelper.asyncNotifyResultFeedback(alerting);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void receiveResponse(MRpcResponse mRpcResponse2) {
        this.mRpcResponse = mRpcResponse2;
        synchronized (this) {
            notifyAll();
        }
        this.mMRpcConnection.removeStream(this.streamId);
    }

    /* access modifiers changed from: 0000 */
    public void close(MRpcResultCode MRpcResultCode) {
        this.mRpcResultCode = MRpcResultCode;
        synchronized (this) {
            try {
                notifyAll();
            } catch (Throwable e) {
                LogCatUtil.warn((String) TAG, "notifyAll error: " + e.toString());
            }
        }
        this.mMRpcConnection.removeStream(this.streamId);
    }

    public void setTimeout(int timeout2) {
        this.timeout = timeout2;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public int getStreamId() {
        return this.streamId;
    }

    public void setStreamId(int streamId2) {
        this.streamId = streamId2;
    }

    public int getReqSize() {
        return this.reqSize;
    }

    public void setReqSize(int reqSize2) {
        this.reqSize = reqSize2;
    }

    private void tryNetworkDiagnose() {
        try {
            int i = mmtpErrorCount + 1;
            mmtpErrorCount = i;
            if (i >= 5) {
                mmtpErrorCount = 0;
                NetworkAsyncTaskExecutor.executeLazy(new Runnable() {
                    public void run() {
                        LogCatUtil.debug(MRpcStream.TAG, "mmtpErrorCount>=5,tryNetworkDiagnose");
                        SignalStateHelper.getInstance().reportNetStateInfo();
                        AlipayQosService.getInstance().getQosLevel();
                        DiagnoseBySystemCall.diagnoseNotify();
                    }
                });
            }
            AlipayQosService.getInstance().estimate(5000.0d, 1);
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, "tryNetworkDiagnose ex:" + ex.toString());
        }
    }
}
