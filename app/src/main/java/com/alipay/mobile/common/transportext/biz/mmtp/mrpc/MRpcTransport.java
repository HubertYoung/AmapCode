package com.alipay.mobile.common.transportext.biz.mmtp.mrpc;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.audio.data.APAudioInfo.AudioOptions;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.utils.HeaderConstant;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcConnection;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.internal.MRpcStream;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcRequest;
import com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models.MRpcResponse;
import com.alipay.mobile.common.transportext.biz.util.AmnetLimitingHelper;
import java.util.Map;

public class MRpcTransport {
    private static final String TAG = "MRpcTransport";
    private MRpcRequest mMRpcRequest;
    private MRpcStream mRpcStream;

    public void sendRequest(MRpcRequest rpcRequest) {
        this.mMRpcRequest = rpcRequest;
        this.mRpcStream = getMRpcConneciton().newMRpcStream();
        rpcRequest.addHeader("RpcId", String.valueOf(this.mRpcStream.getStreamId()));
        rpcRequest.reqSeqId = this.mRpcStream.getStreamId();
        setTraceId(rpcRequest);
        setStreamTimeout(rpcRequest);
        this.mRpcStream.sendRequest(rpcRequest);
        LogCatUtil.debug(TAG, " rpcid = " + this.mRpcStream.getStreamId() + " API=" + rpcRequest.getHeaders().get("Operation-Type"));
    }

    private void setStreamTimeout(MRpcRequest mrpcRequest) {
        int waitTime = mrpcRequest.readTimeout;
        switch (MRpcConnection.getInstance().getConnectionState()) {
            case 0:
            case 1:
            case 5:
                waitTime += mrpcRequest.connTimeout + mrpcRequest.sslTimeout;
                LogCatUtil.info(TAG, "setStreamTimeout. STATE_SHUTTING. (+=" + mrpcRequest.connTimeout + "+" + mrpcRequest.sslTimeout + ")=" + waitTime);
                break;
            case 2:
            case 3:
                waitTime += mrpcRequest.sslTimeout;
                LogCatUtil.info(TAG, "setStreamTimeout. STATE_HANDSHAKING. (+=" + mrpcRequest.sslTimeout + ")=" + waitTime);
                break;
            default:
                LogCatUtil.info(TAG, "setStreamTimeout.STATE_ESTABLISHED:" + waitTime);
                break;
        }
        if (mrpcRequest.important) {
            waitTime += 5000;
            LogCatUtil.info(TAG, "setStreamTimeout. important is true. (+=5000)=" + waitTime);
        }
        this.mRpcStream.setTimeout(setTimeoutForSendMsg(mrpcRequest, waitTime));
    }

    private int setTimeoutForSendMsg(MRpcRequest mrpcRequest, int waitTime) {
        Map headers = mrpcRequest.getHeaders();
        if (headers == null || headers.isEmpty()) {
            return waitTime;
        }
        if (TextUtils.equals(headers.get("Operation-Type"), "alipay.mobilechat.sendMsg")) {
            waitTime = AudioOptions.MAX_DURATION;
            LogCatUtil.info(TAG, "setStreamTimeout,sendMsg timeout: 180000");
        }
        return waitTime;
    }

    public MRpcResponse execute(MRpcRequest rpcRequest) {
        if (AmnetLimitingHelper.isServerLimiting()) {
            return AmnetLimitingHelper.getLimitingMRpcResponse();
        }
        sendRequest(rpcRequest);
        return getResponse();
    }

    public MRpcResponse getResponse() {
        return this.mRpcStream.getResponse();
    }

    public MRpcConnection getMRpcConneciton() {
        return MRpcConnection.getInstance();
    }

    public String getTargetHost() {
        MRpcConnection conn = getMRpcConneciton();
        String currentTargetHost = conn.getCurrentTargetHost();
        if (!TextUtils.isEmpty(currentTargetHost)) {
            return currentTargetHost + ":" + conn.getCurrentTargetPort();
        }
        return "";
    }

    private void setTraceId(MRpcRequest rpcRequest) {
        try {
            String traceid = rpcRequest.getHeaders().get(HeaderConstant.HEADER_KEY_PARAM_TRACEID);
            if (!TextUtils.isEmpty(traceid)) {
                String traceid2 = traceid + "_" + rpcRequest.reqSeqId;
                rpcRequest.addHeader(HeaderConstant.HEADER_KEY_PARAM_TRACEID, traceid2);
                TransportContext transportContext = TransportContextThreadLocalUtils.getValue();
                if (transportContext != null) {
                    transportContext.rpcUUID = traceid2;
                }
            }
        } catch (Throwable ex) {
            LogCatUtil.error(TAG, "setTraceId exception", ex);
        }
    }
}
