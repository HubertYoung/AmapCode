package com.alipay.android.phone.mobilesdk.socketcraft.api;

import com.alipay.android.phone.mobilesdk.socketcraft.client.WebSocketClient;
import com.alipay.android.phone.mobilesdk.socketcraft.drafts.Draft;
import com.alipay.android.phone.mobilesdk.socketcraft.drafts.Draft_17;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata;
import com.alipay.android.phone.mobilesdk.socketcraft.framing.Framedata.Opcode;
import com.alipay.android.phone.mobilesdk.socketcraft.handshake.ServerHandshake;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorHelper;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.logcat.SCLogCatUtil;
import com.alipay.android.phone.mobilesdk.socketcraft.platform.threadpool.SCNetworkAsyncTaskUtil;
import com.alipay.android.phone.mobilesdk.socketcraft.util.Charsetfunctions;
import com.alipay.android.phone.mobilesdk.socketcraft.util.WsMessageConstants;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLSocketFactory;

public class DefaultWebSocketClient extends WebSocketClient {
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final int MIN_CONNECTION_TIMEOUT = 4000;
    private static final String TAG = "WebSocketClient";
    private long connectTimeout;
    private Framedata firstFragmentFrame;
    private MonitorHelper monitorHelper;
    private boolean myselfClosed;
    private ScheduledFuture<?> timeoutScheduleFuture;
    private String url;
    private WebSocketCallback webSocketCallback;
    private WebSocketContext webSocketContext;

    class SCConnectionTimerRunnable implements Runnable {
        private static final String TAG = "SCConnectionTimerRunnable";
        private DefaultWebSocketClient defaultWebSocketClient;
        public WebSocketCallback webSocketCallback;

        SCConnectionTimerRunnable(DefaultWebSocketClient defaultWebSocketClient2, WebSocketCallback webSocketCallback2) {
            this.defaultWebSocketClient = defaultWebSocketClient2;
            this.webSocketCallback = webSocketCallback2;
        }

        public void run() {
            SCLogCatUtil.error((String) TAG, (String) "connect response time out");
            try {
                DefaultWebSocketClient.this.onError((String) WsMessageConstants.MSG_CONNECTION_TIME_OUT);
            } catch (Throwable e) {
                SCLogCatUtil.error(TAG, "onSocketError error", e);
            }
            try {
                this.defaultWebSocketClient.close();
            } catch (Throwable e2) {
                SCLogCatUtil.error(TAG, "close socket error", e2);
            }
        }
    }

    public DefaultWebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout2, WebSocketCallback webSocketCallback2, WebSocketContext webSocketContext2) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout2);
        this.myselfClosed = false;
        this.webSocketCallback = webSocketCallback2;
        this.url = serverUri.toString();
        this.webSocketContext = webSocketContext2;
        if (connectTimeout2 > 4000) {
            this.connectTimeout = (long) connectTimeout2;
        } else {
            this.connectTimeout = 4000;
        }
        this.monitorHelper = new MonitorHelper(this);
    }

    public DefaultWebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout2, WebSocketCallback webSocketCallback2) {
        this(serverUri, protocolDraft, httpHeaders, connectTimeout2, webSocketCallback2, new BasicWebSocketContext());
    }

    public DefaultWebSocketClient(URI serverUri, Map<String, String> httpHeaders, WebSocketCallback webSocketCallback2) {
        this(serverUri, new Draft_17(), httpHeaders, 60000, webSocketCallback2);
    }

    public DefaultWebSocketClient(URI serverUri, Map<String, String> httpHeaders, WebSocketCallback webSocketCallback2, WebSocketContext webSocketContext2) {
        this(serverUri, new Draft_17(), httpHeaders, 60000, webSocketCallback2, webSocketContext2);
    }

    public void connectBlockingWithSSL() {
        setSslSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
        connectBlocking();
    }

    public boolean connectBlocking() {
        beforeConnect();
        return super.connectBlocking();
    }

    public void connectWithSSL() {
        setSslSocketFactory((SSLSocketFactory) SSLSocketFactory.getDefault());
        connect();
    }

    public void connect() {
        beforeConnect();
        super.connect();
        startConnectionTimer();
    }

    public void send(String text) {
        if (text == null) {
            SCLogCatUtil.warn((String) TAG, (String) "[send] text is null");
            return;
        }
        if (text.length() <= 0) {
            SCLogCatUtil.warn((String) TAG, (String) "[send] text is empty, but continue send.");
        }
        super.send(text);
        this.monitorHelper.recordMonitorOfSndMsg(text.length());
        this.monitorHelper.noteTraficConsume(new DataflowMonitorModel(this.url, getBizUniqId(), DataflowMonitorModel.METHOD_NAME_SEND, text.length(), 0));
    }

    public void send(byte[] data) {
        if (data == null || data.length <= 0) {
            SCLogCatUtil.warn((String) TAG, (String) "[send] byte[] is null or length <= 0.");
            return;
        }
        super.send(data);
        this.monitorHelper.recordMonitorOfSndMsg(data.length);
        this.monitorHelper.noteTraficConsume(new DataflowMonitorModel(this.url, getBizUniqId(), DataflowMonitorModel.METHOD_NAME_SEND, data.length, 0));
    }

    public void send(ByteBuffer bytes) {
        if (bytes == null || bytes.array().length <= 0) {
            SCLogCatUtil.warn((String) TAG, (String) "[send] ByteBuffer is null or length <= 0.");
            return;
        }
        super.send(bytes);
        this.monitorHelper.recordMonitorOfSndMsg(bytes.array().length);
        this.monitorHelper.noteTraficConsume(new DataflowMonitorModel(this.url, getBizUniqId(), DataflowMonitorModel.METHOD_NAME_SEND, bytes.array().length, 0));
    }

    public void onDns(String ip, long cost) {
        this.monitorHelper.recordDnsTime(cost);
        this.monitorHelper.recordTargetHost(ip);
    }

    public void onTcpConnect(String ip, long cost) {
        this.monitorHelper.recordTcpTime(cost);
        this.monitorHelper.recordTargetHost(ip);
    }

    public void onSSLHandshake(long cost) {
        this.monitorHelper.recordSSLTime(cost);
    }

    public void onWsHandshake(long cost) {
        SCLogCatUtil.info(TAG, "onWsHandshake cost: " + cost);
        this.monitorHelper.recordWsHsTime(cost);
    }

    public void onOpen(ServerHandshake handshakedata) {
        afterConnect();
        cancelTimeoutScheduleFuture();
        SCLogCatUtil.info(TAG, String.format("onOpen. url is %s , state: opened", new Object[]{this.url}));
        this.webSocketCallback.onSocketOpen();
        this.monitorHelper.noteTraficConsume(new DataflowMonitorModel(this.url, getBizUniqId(), "connect", this.url.length() + WSContextConstant.HANDSHAKE_SEND_SIZE, WSContextConstant.HANDSHAKE_RECEIVE_SIZE));
    }

    public void onMessage(String message) {
        Object[] objArr = new Object[2];
        objArr[0] = this.url;
        objArr[1] = Integer.valueOf(message != null ? message.length() : -1);
        SCLogCatUtil.info(TAG, String.format("onMessage. url is %s ,socket onmessage length :%d", objArr));
        if (message != null && message.length() > 0) {
            this.webSocketCallback.onSocketMessage(message);
            this.monitorHelper.recordMonitorOfRecvMsg(message.length());
            this.monitorHelper.noteTraficConsume(new DataflowMonitorModel(this.url, getBizUniqId(), DataflowMonitorModel.METHOD_NAME_RECEIVE, 0, message.length()));
        }
    }

    public void onMessage(ByteBuffer byteBuffer) {
        Object[] objArr = new Object[2];
        objArr[0] = this.url;
        objArr[1] = Integer.valueOf(byteBuffer != null ? byteBuffer.position() : -1);
        SCLogCatUtil.info(TAG, String.format("onMessage. url is %s , socket onMessage buffer length : %d", objArr));
        if (byteBuffer != null) {
            this.webSocketCallback.onSocketMessage(byteBuffer);
            this.monitorHelper.recordMonitorOfRecvMsg(byteBuffer.position());
            this.monitorHelper.noteTraficConsume(new DataflowMonitorModel(this.url, getBizUniqId(), DataflowMonitorModel.METHOD_NAME_RECEIVE, 0, byteBuffer.array().length));
        }
    }

    public void onClose(int code, String reason, boolean remote) {
        cancelTimeoutScheduleFuture();
        SCLogCatUtil.info(TAG, String.format("onClose. url is %s ,state: closed ,reason: %s, errCode = %d, remote = %s", new Object[]{this.url, reason, Integer.valueOf(code), Boolean.toString(remote)}));
        if (this.myselfClosed || !(code == -1 || code == -2 || code == -3)) {
            this.webSocketCallback.onSocketClose();
        } else {
            onError(reason);
        }
        this.monitorHelper.printDisconnMonitorLog();
        this.monitorHelper.noteTraficConsume(new DataflowMonitorModel(this.url, getBizUniqId(), DataflowMonitorModel.METHOD_NAME_CLOSE, 0, 0));
    }

    public void onError(Exception ex) {
        SCLogCatUtil.error(TAG, String.format("onError. url is %s ,error is %s", new Object[]{this.url, ex.toString()}), ex);
        onSocketError("exception: " + ex.getMessage());
    }

    public void onError(String err) {
        SCLogCatUtil.error((String) TAG, String.format("onError. url is %s ,error is %s", new Object[]{this.url, err}));
        onSocketError(err);
    }

    public void onSocketError(String err) {
        this.webSocketCallback.onSocketError(err);
        this.monitorHelper.printErrorMonitorLog("1", err);
    }

    public void onFragment(Framedata pFrame) {
        if (pFrame.getOpcode() != Opcode.CONTINUOUS && !pFrame.isFin()) {
            this.firstFragmentFrame = pFrame;
        } else if (pFrame.getOpcode() == Opcode.CONTINUOUS && this.firstFragmentFrame != null) {
            if (this.firstFragmentFrame.getPayloadData().position() > 10485760) {
                SCLogCatUtil.warn((String) TAG, (String) "onFragment. Pending frame exploded");
                onError((Exception) new RuntimeException(WsMessageConstants.MSG_PENDING_FRAME_EXPLODED));
                close();
                this.firstFragmentFrame = null;
                return;
            }
            try {
                this.firstFragmentFrame.append(pFrame);
            } catch (Throwable e) {
                SCLogCatUtil.warn(TAG, "onFragment. append frame err. ", e);
            }
            if (pFrame.isFin()) {
                if (this.firstFragmentFrame.getOpcode() == Opcode.BINARY) {
                    onMessage(this.firstFragmentFrame.getPayloadData());
                } else if (this.firstFragmentFrame.getOpcode() == Opcode.TEXT) {
                    try {
                        String playloadDataStr = Charsetfunctions.stringUtf8(this.firstFragmentFrame.getPayloadData());
                        if (playloadDataStr == null) {
                            playloadDataStr = "";
                        }
                        onMessage(playloadDataStr);
                    } catch (Throwable e2) {
                        SCLogCatUtil.warn(TAG, "onFragment. ByteBuffer to String err ", e2);
                    }
                }
                this.firstFragmentFrame = null;
            }
        }
    }

    public void close() {
        try {
            SCLogCatUtil.info(TAG, "close. try to close socket");
            this.myselfClosed = true;
            super.close();
        } catch (Throwable e) {
            SCLogCatUtil.error(TAG, "send err. ", e);
        }
    }

    public void close(int code, String reason) {
        try {
            SCLogCatUtil.info(TAG, "close. try to close socket");
            this.myselfClosed = true;
            super.close(code, reason);
        } catch (Throwable e) {
            SCLogCatUtil.error(TAG, "send err. ", e);
        }
    }

    private void startConnectionTimer() {
        cancelTimeoutScheduleFuture();
        this.timeoutScheduleFuture = SCNetworkAsyncTaskUtil.schedule((Runnable) new SCConnectionTimerRunnable(this, this.webSocketCallback), this.connectTimeout, TimeUnit.MILLISECONDS);
    }

    private void cancelTimeoutScheduleFuture() {
        try {
            SCLogCatUtil.info(TAG, "cancelTimeoutScheduleFuture. try to stop connectTimer");
            if (this.timeoutScheduleFuture != null) {
                this.timeoutScheduleFuture.cancel(true);
                this.timeoutScheduleFuture = null;
            }
        } catch (Throwable e) {
            SCLogCatUtil.error(TAG, "cancelTimeoutScheduleFuture. Cancel old timeoutScheduleFuture error", e);
        }
    }

    public WebSocketContext getWebSocketContext() {
        if (this.webSocketContext != null) {
            return this.webSocketContext;
        }
        synchronized (this) {
            if (this.webSocketContext != null) {
                WebSocketContext webSocketContext2 = this.webSocketContext;
                return webSocketContext2;
            }
            this.webSocketContext = new BasicWebSocketContext();
            return this.webSocketContext;
        }
    }

    public void setWebSocketContext(WebSocketContext webSocketContext2) {
        this.webSocketContext = webSocketContext2;
    }

    public String getUrl() {
        return this.url;
    }

    public String getBizUniqId() {
        Object obj = getWebSocketContext().getAttribute(WSContextConstant.BIZ_UNIQUE_ID);
        if (obj == null || !(obj instanceof String)) {
            return "unkown";
        }
        return String.valueOf(obj);
    }

    private void beforeConnect() {
        SCLogCatUtil.info(TAG, "enter beforeConnect");
        this.monitorHelper.recordStartConnAllTime();
    }

    private void afterConnect() {
        SCLogCatUtil.info(TAG, "enter afterConnect");
        this.monitorHelper.recordConnectedTime();
        this.monitorHelper.printConnMonitorLog();
    }
}
