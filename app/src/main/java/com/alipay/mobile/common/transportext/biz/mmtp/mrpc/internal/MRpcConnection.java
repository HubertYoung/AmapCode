package com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal;

import android.annotation.TargetApi;
import android.text.TextUtils;
import com.alipay.mobile.common.amnet.api.AmnetListenerAdpter;
import com.alipay.mobile.common.amnet.api.model.AcceptedData;
import com.alipay.mobile.common.amnet.api.model.AmnetPost;
import com.alipay.mobile.common.transport.monitor.MonitorLoggerUtils;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.amnet.Initialization.RspInit;
import com.alipay.mobile.common.transportext.biz.mmtp.amnetadapt.AmnetHelper;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcRequest;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MRpcConnection {
    private static MRpcConnection MRPC_CONNECTION = null;
    private static final String TAG = "MRpcConnection";
    /* access modifiers changed from: private */
    public String clientIp = "";
    /* access modifiers changed from: private */
    public int connectionState = -1;
    /* access modifiers changed from: private */
    public String currentTargetHost = "";
    /* access modifiers changed from: private */
    public String currentTargetPort = "";
    /* access modifiers changed from: private */
    public String limitPrompt = "";
    /* access modifiers changed from: private */
    public long limitingEndTime = -1;
    /* access modifiers changed from: private */
    public Map<String, String> mrpcConnContext = new HashMap();
    private int nextStreamId = 0;
    private Map<Integer, MRpcStream> streamMap = new HashMap();

    class ReaderListener extends AmnetListenerAdpter {
        private Map<String, Double> logReport = Collections.synchronizedMap(new HashMap(5));

        ReaderListener() {
        }

        public void change(int state) {
            LogCatUtil.debug(MRpcConnection.TAG, "ReaderListener#change");
            MRpcConnection.this.connectionState = state;
        }

        public void panic(int err, String inf) {
            LogCatUtil.debug(MRpcConnection.TAG, "ReaderListener#panic");
        }

        public void onFinalErrorEvent(long receiptId, int errorCode, String errorMsg, Map<String, String> params) {
            MRpcResponse mRpcResponse = new MRpcResponse();
            mRpcResponse.resultCode = errorCode;
            mRpcResponse.resultMsg = errorMsg;
            mRpcResponse.streamId = (int) receiptId;
            mRpcResponse.clientIp = MRpcConnection.this.clientIp;
            setLibVersion(receiptId, mRpcResponse, params);
            String mtag = MRpcConnection.this.splitMtag(errorMsg);
            if (!TextUtils.isEmpty(mtag)) {
                mRpcResponse.mtag = mtag;
            }
            if (!TextUtils.isEmpty(MRpcConnection.this.currentTargetHost) && !TextUtils.isEmpty(MRpcConnection.this.currentTargetPort)) {
                mRpcResponse.targetHost = String.format("%s:%s", new Object[]{MRpcConnection.this.currentTargetHost, MRpcConnection.this.currentTargetPort});
            }
            MRpcConnection.this.processResponse(mRpcResponse);
        }

        private void setLibVersion(long receiptId, MRpcResponse mRpcResponse, Map<String, String> params) {
            if (params != null) {
                String val = params.get("amnet_lib_version");
                LogCatUtil.debug(MRpcConnection.TAG, "onFinalErrorEvent, receiptId:" + receiptId + ", LIBV:" + val);
                if (TextUtils.equals("bifrost_lib", val)) {
                    mRpcResponse.isUseBifrost = true;
                    MRpcConnection.this.mrpcConnContext.put(MonitorLoggerUtils.LIB_VERSION, MonitorLoggerUtils.LIB_VERSION_BIFROST);
                } else if (TextUtils.equals("bifrost_http2_lib", val)) {
                    mRpcResponse.isUseBifrost = true;
                    mRpcResponse.isUseHttp2 = true;
                    MRpcConnection.this.mrpcConnContext.put(MonitorLoggerUtils.LIB_VERSION, MonitorLoggerUtils.LIB_VERSION_BIFROST_HTTP2);
                } else if (TextUtils.equals("old_lib", val)) {
                    mRpcResponse.isUseBifrost = false;
                    mRpcResponse.isUseHttp2 = false;
                    MRpcConnection.this.mrpcConnContext.put(MonitorLoggerUtils.LIB_VERSION, MonitorLoggerUtils.LIB_VERSION_OLD);
                }
            }
        }

        public void report(String key, double val) {
            LogCatUtil.debug(MRpcConnection.TAG, "ReaderListener#report,key:" + key + ",val:" + val);
            this.logReport.put(key, Double.valueOf(val));
        }

        public void onAcceptedDataEvent(AcceptedData acceptedData) {
            LogCatUtil.debug(MRpcConnection.TAG, "ReaderListener#onAcceptedDataEvent");
            MRpcResponse mRpcResponse = new MRpcResponse();
            mRpcResponse.body = acceptedData.data;
            mRpcResponse.headers = acceptedData.headers;
            mRpcResponse.respSize = acceptedData.compressSize;
            mRpcResponse.resultCode = 2000;
            acceptedData.ipcP2m = ((double) System.currentTimeMillis()) - acceptedData.ipcP2m;
            mRpcResponse.clientIp = MRpcConnection.this.clientIp;
            fillReport(acceptedData, mRpcResponse);
            MRpcConnection.this.processResponse(mRpcResponse);
        }

        private void fillReport(AcceptedData acceptedData, MRpcResponse mRpcResponse) {
            mRpcResponse.readTiming = (int) Math.round(acceptedData.readTiming);
            mRpcResponse.ipcP2m = (int) Math.round(acceptedData.ipcP2m);
            mRpcResponse.jtcTiming = (int) Math.round(acceptedData.jtcTIme);
            mRpcResponse.amnetWaitTiming = (int) Math.round(acceptedData.amnetWaitTime);
            mRpcResponse.retried = acceptedData.retried;
            mRpcResponse.amnetStalledTime = (int) Math.round(acceptedData.amnetStalledTime);
            mRpcResponse.airTime = (int) Math.round(acceptedData.airTime);
            mRpcResponse.streamId = (int) acceptedData.receipt;
            mRpcResponse.saTime = (int) Math.round(acceptedData.saTime);
            mRpcResponse.isOnShort = acceptedData.isOnShort;
            mRpcResponse.useShort = acceptedData.useShort;
            mRpcResponse.targetHostShort = acceptedData.targetHostShort;
            mRpcResponse.mtag = acceptedData.mtag;
            mRpcResponse.qoeCur = acceptedData.qoeCur;
            mRpcResponse.queneStorage = acceptedData.queneStorage;
            mRpcResponse.ctjOutTime = (int) Math.round(acceptedData.ctjOutTime);
            mRpcResponse.ntIOTime = (int) Math.round(acceptedData.ntIOTime);
            mRpcResponse.queueOutTime = (int) Math.round(acceptedData.queueOutTime);
            mRpcResponse.amnetHungTime = (int) Math.round(acceptedData.amnetHungTime);
            mRpcResponse.amnetEncodeTime = (int) Math.round(acceptedData.amnetEncodeTime);
            mRpcResponse.amnetAllTime = (int) Math.round(acceptedData.amnetAllTime);
            mRpcResponse.cid = acceptedData.cid;
            mRpcResponse.isFlexible = acceptedData.isFlexible;
            mRpcResponse.targetHost = acceptedData.targetHostLong;
            mRpcResponse.reqZipType = acceptedData.reqZipType;
            mRpcResponse.rspZipType = acceptedData.rspZipType;
            mRpcResponse.isUseBifrost = acceptedData.isUseBifrost;
            mRpcResponse.isUseHttp2 = acceptedData.isUseHttp2;
            if (!this.logReport.isEmpty()) {
                Double dnsTiming = this.logReport.get("DNS");
                if (dnsTiming != null) {
                    mRpcResponse.dnsTiming = (int) dnsTiming.doubleValue();
                }
                Double tcpTiming = this.logReport.get("JVM TCP connect");
                if (tcpTiming != null) {
                    mRpcResponse.tcpTiming = (int) tcpTiming.doubleValue();
                }
                Double sslTiming = this.logReport.get("JVM SSL handshake");
                if (sslTiming != null) {
                    mRpcResponse.sslTiming = (int) sslTiming.doubleValue();
                }
                Double tcpNtv = this.logReport.get("native TCP connect");
                if (tcpNtv != null) {
                    mRpcResponse.tcpNtv = (int) tcpNtv.doubleValue();
                }
                Double sslNtv = this.logReport.get("native SSL handshake");
                if (sslNtv != null) {
                    mRpcResponse.sslNtv = (int) sslNtv.doubleValue();
                }
                this.logReport.clear();
            }
        }

        public void touch(String ipLocal, String ipRemote, String portLocal, String portRemote) {
            MRpcConnection.this.currentTargetHost = ipRemote;
            MRpcConnection.this.currentTargetPort = portRemote;
            LogCatUtil.debug(MRpcConnection.TAG, "touch,currentTargetHost:" + ipRemote);
        }

        public void restrict(int delay, String inf) {
            LogCatUtil.info(MRpcConnection.TAG, "restrict delay=[" + delay + "] inf=[" + inf + "]");
            MRpcConnection.this.limitingEndTime = System.currentTimeMillis() + ((long) (delay * 1000));
            MRpcConnection.this.limitPrompt = inf;
            MRpcConnection.this.letUsDisband(2001, inf);
        }

        public void tell(byte channelType, long reqId, int uncompressSize, int compressSize) {
            LogCatUtil.printInfo(MRpcConnection.TAG, "tell reqId=[" + reqId + "] uncompressSize=[" + uncompressSize + "]  compressSize=[" + compressSize + "]");
            MRpcStream stream = MRpcConnection.this.getStream((int) reqId);
            if (stream == null) {
                LogCatUtil.info(MRpcConnection.TAG, "tell.  Not found reqId=[" + reqId + "]");
            } else {
                stream.setReqSize(compressSize);
            }
        }

        public void notifyInitResponse(RspInit rspInit) {
            MRpcConnection.this.clientIp = rspInit.clientIp;
        }
    }

    public static MRpcConnection getInstance() {
        if (MRPC_CONNECTION != null) {
            return MRPC_CONNECTION;
        }
        synchronized (MRpcConnection.class) {
            if (MRPC_CONNECTION != null) {
                MRpcConnection mRpcConnection = MRPC_CONNECTION;
                return mRpcConnection;
            }
            MRpcConnection mRpcConnection2 = new MRpcConnection();
            MRPC_CONNECTION = mRpcConnection2;
            return mRpcConnection2;
        }
    }

    private MRpcConnection() {
        init();
    }

    @TargetApi(9)
    private void init() {
        ReaderListener readerListener = new ReaderListener();
        AmnetHelper.getAmnetManager().addGeneraEventListener(readerListener);
        AmnetHelper.getAmnetManager().addRpcAcceptDataListener(readerListener);
    }

    public synchronized MRpcStream newMRpcStream() {
        MRpcStream createStream;
        try {
            if (this.nextStreamId == 0 || this.nextStreamId >= 2147483646) {
                this.nextStreamId = ((int) ((System.currentTimeMillis() % 1000000) / 1000)) * 1000;
            }
            createStream = createStream(this.nextStreamId);
            this.nextStreamId++;
        } catch (Throwable th) {
            this.nextStreamId++;
            throw th;
        }
        return createStream;
    }

    private MRpcStream createStream(int streamId) {
        MRpcStream mRpcStream = new MRpcStream(streamId, this);
        this.streamMap.put(Integer.valueOf(streamId), mRpcStream);
        return mRpcStream;
    }

    public void sendRequest(MRpcRequest mRpcRequest) {
        AmnetPost amnetPost = new AmnetPost();
        amnetPost.body = mRpcRequest.getDatas();
        amnetPost.header = mRpcRequest.getHeaders();
        amnetPost.channel = 1;
        amnetPost.reqSeqId = mRpcRequest.reqSeqId;
        amnetPost.important = mRpcRequest.important;
        AmnetHelper.post(amnetPost);
    }

    public MRpcStream getStream(int streamId) {
        return this.streamMap.get(Integer.valueOf(streamId));
    }

    public void removeStream(int streamId) {
        this.streamMap.remove(Integer.valueOf(streamId));
    }

    public Map<String, String> getMrpcConnContext() {
        return this.mrpcConnContext;
    }

    /* access modifiers changed from: private */
    public void processResponse(MRpcResponse mRpcResponse) {
        try {
            if (this.streamMap.isEmpty()) {
                LogCatUtil.debug(TAG, "There is no stream!");
            } else if (mRpcResponse.resultCode == 2000) {
                handleSuccess(mRpcResponse);
            } else {
                handleFailure(mRpcResponse);
            }
        } catch (Exception e) {
            LogCatUtil.error((String) TAG, (Throwable) e);
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleFailure(com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse r10) {
        /*
            r9 = this;
            java.lang.String r4 = "MRpcConnection"
            java.lang.String r5 = "handleFailure"
            com.alipay.mobile.common.transport.utils.LogCatUtil.info(r4, r5)
            monitor-enter(r9)
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode r1 = new com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcResultCode     // Catch:{ all -> 0x0063 }
            r1.<init>()     // Catch:{ all -> 0x0063 }
            int r4 = r10.resultCode     // Catch:{ all -> 0x0063 }
            r1.code = r4     // Catch:{ all -> 0x0063 }
            java.lang.String r4 = r10.resultMsg     // Catch:{ all -> 0x0063 }
            r1.msg = r4     // Catch:{ all -> 0x0063 }
            int r4 = r10.streamId     // Catch:{ all -> 0x0063 }
            r5 = -1
            if (r4 != r5) goto L_0x0068
            java.util.Map<java.lang.Integer, com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream> r4 = r9.streamMap     // Catch:{ all -> 0x0063 }
            boolean r4 = r4.isEmpty()     // Catch:{ all -> 0x0063 }
            if (r4 == 0) goto L_0x0024
            monitor-exit(r9)     // Catch:{ all -> 0x0063 }
        L_0x0023:
            return
        L_0x0024:
            java.util.Map<java.lang.Integer, com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream> r4 = r9.streamMap     // Catch:{ all -> 0x0063 }
            java.util.Collection r4 = r4.values()     // Catch:{ all -> 0x0063 }
            java.util.Map<java.lang.Integer, com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream> r5 = r9.streamMap     // Catch:{ all -> 0x0063 }
            int r5 = r5.size()     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream[] r5 = new com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream[r5]     // Catch:{ all -> 0x0063 }
            java.lang.Object[] r2 = r4.toArray(r5)     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream[] r2 = (com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream[]) r2     // Catch:{ all -> 0x0063 }
            java.util.Map<java.lang.Integer, com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream> r4 = r9.streamMap     // Catch:{ all -> 0x0063 }
            r4.clear()     // Catch:{ all -> 0x0063 }
            int r5 = r2.length     // Catch:{ all -> 0x0063 }
            r4 = 0
        L_0x003f:
            if (r4 >= r5) goto L_0x0066
            r3 = r2[r4]     // Catch:{ all -> 0x0063 }
            r3.close(r1)     // Catch:{ Exception -> 0x0049 }
        L_0x0046:
            int r4 = r4 + 1
            goto L_0x003f
        L_0x0049:
            r0 = move-exception
            java.lang.String r6 = "MRpcConnection"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0063 }
            java.lang.String r8 = "error message : "
            r7.<init>(r8)     // Catch:{ all -> 0x0063 }
            java.lang.String r8 = r0.toString()     // Catch:{ all -> 0x0063 }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ all -> 0x0063 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r6, r7)     // Catch:{ all -> 0x0063 }
            goto L_0x0046
        L_0x0063:
            r4 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0063 }
            throw r4
        L_0x0066:
            monitor-exit(r9)     // Catch:{ all -> 0x0063 }
            goto L_0x0023
        L_0x0068:
            java.util.Map<java.lang.Integer, com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream> r4 = r9.streamMap     // Catch:{ all -> 0x0063 }
            int r5 = r10.streamId     // Catch:{ all -> 0x0063 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x0063 }
            java.lang.Object r3 = r4.get(r5)     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream r3 = (com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream) r3     // Catch:{ all -> 0x0063 }
            if (r3 == 0) goto L_0x007b
            r3.close(r1)     // Catch:{ Exception -> 0x007d }
        L_0x007b:
            monitor-exit(r9)     // Catch:{ all -> 0x0063 }
            goto L_0x0023
        L_0x007d:
            r0 = move-exception
            java.lang.String r4 = "MRpcConnection"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0063 }
            java.lang.String r6 = "error message : "
            r5.<init>(r6)     // Catch:{ all -> 0x0063 }
            java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x0063 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0063 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0063 }
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r4, r5)     // Catch:{ all -> 0x0063 }
            goto L_0x007b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcConnection.handleFailure(com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse):void");
    }

    private void handleSuccess(MRpcResponse mRpcResponse) {
        LogCatUtil.info(TAG, "handleSuccess");
        String rpcId = mRpcResponse.headers.get("RpcId");
        if (TextUtils.isEmpty(rpcId)) {
            throw new IllegalArgumentException("stream no exist,  streamId=[" + rpcId + "]");
        }
        MRpcStream stream = getStream(Integer.parseInt(rpcId));
        if (stream == null) {
            throw new IllegalArgumentException("stream no exist,  streamId=[" + rpcId + "]");
        }
        mRpcResponse.reqSize = stream.getReqSize();
        stream.receiveResponse(mRpcResponse);
    }

    /* access modifiers changed from: protected */
    public void letUsDisband(int err, String inf) {
        MRpcResponse mRpcResponse = new MRpcResponse();
        mRpcResponse.resultCode = err;
        mRpcResponse.resultMsg = inf;
        mRpcResponse.streamId = -1;
        if (!TextUtils.isEmpty(this.currentTargetHost) && !TextUtils.isEmpty(this.currentTargetPort)) {
            mRpcResponse.targetHost = String.format("%s:%s", new Object[]{this.currentTargetHost, this.currentTargetPort});
        }
        processResponse(mRpcResponse);
    }

    public String getCurrentTargetHost() {
        return this.currentTargetHost;
    }

    public String getCurrentTargetPort() {
        return this.currentTargetPort;
    }

    public int getConnectionState() {
        return this.connectionState;
    }

    public String getLimitPrompt() {
        if (!isServerLimiting()) {
            this.limitPrompt = "";
            return "";
        } else if (TextUtils.isEmpty(this.limitPrompt)) {
            return "";
        } else {
            return this.limitPrompt;
        }
    }

    public boolean isServerLimiting() {
        if (this.limitingEndTime == -1) {
            return false;
        }
        if (System.currentTimeMillis() < this.limitingEndTime) {
            return true;
        }
        this.limitingEndTime = -1;
        return false;
    }

    public long getLimitingEndTime() {
        return this.limitingEndTime;
    }

    /* access modifiers changed from: private */
    public String splitMtag(String errorMsg) {
        try {
            if (!TextUtils.isEmpty(errorMsg) && errorMsg.contains("_")) {
                return errorMsg.substring(errorMsg.indexOf("_") + 1);
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) TAG, ex);
        }
        return "";
    }
}
