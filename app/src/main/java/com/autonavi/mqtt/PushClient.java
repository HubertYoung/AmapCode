package com.autonavi.mqtt;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PushClient {
    private static final int MAX_PUBLISH_QUEUE = 100;
    private static final int MESSAGE_INIT = 0;
    private static final int MESSAGE_NONE = -1;
    private static final int MESSAGE_PUBLISH = 3;
    private static final int MESSAGE_RESTART = 2;
    private static final int MESSAGE_UNINIT = 1;
    /* access modifiers changed from: private */
    public static MessageTask lastRestartTask;
    private static MessageThread mMessageThread;
    private static PushClient mSelf;
    /* access modifiers changed from: private */
    public static final BlockingQueue<MessageTask> queue = new LinkedBlockingQueue();
    private static volatile Object sync_obj = new Object();
    private String clientID;
    private String extraInfo;
    private int lastMessageId = -1;
    private String mAddress;
    private String mAosKey;
    private boolean mInited = false;
    private int mKeepAliveInterval = 0;
    /* access modifiers changed from: private */
    public IPushCallback mMqttCallback;
    /* access modifiers changed from: private */
    public PushCallback mPushCallback = new PushCallback() {
        public void onError(int i) {
            if (PushClient.this.mMqttCallback != null) {
                PushClient.this.mMqttCallback.onError(i);
            }
        }

        public void connectionLost(byte[] bArr) {
            String str;
            try {
                str = new String(bArr, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                str = null;
            }
            if (PushClient.this.mMqttCallback != null) {
                PushClient.this.mMqttCallback.connectionLost(str);
            }
        }

        public void deliveryComplete(int i) {
            if (PushClient.this.mMqttCallback != null) {
                PushClient.this.mMqttCallback.deliveryComplete(i);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x0022  */
        /* JADX WARNING: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void messageArrived(byte[] r4, byte[] r5) {
            /*
                r3 = this;
                r0 = 0
                java.lang.String r1 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x0014 }
                java.lang.String r2 = "utf-8"
                r1.<init>(r4, r2)     // Catch:{ UnsupportedEncodingException -> 0x0014 }
                java.lang.String r4 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x0012 }
                java.lang.String r2 = "utf-8"
                r4.<init>(r5, r2)     // Catch:{ UnsupportedEncodingException -> 0x0012 }
                goto L_0x001a
            L_0x0012:
                r4 = move-exception
                goto L_0x0016
            L_0x0014:
                r4 = move-exception
                r1 = r0
            L_0x0016:
                r4.printStackTrace()
                r4 = r0
            L_0x001a:
                com.autonavi.mqtt.PushClient r5 = com.autonavi.mqtt.PushClient.this
                com.autonavi.mqtt.IPushCallback r5 = r5.mMqttCallback
                if (r5 == 0) goto L_0x002b
                com.autonavi.mqtt.PushClient r5 = com.autonavi.mqtt.PushClient.this
                com.autonavi.mqtt.IPushCallback r5 = r5.mMqttCallback
                r5.messageArrived(r1, r4)
            L_0x002b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.mqtt.PushClient.AnonymousClass4.messageArrived(byte[], byte[]):void");
        }

        public void onConnected() {
            if (PushClient.this.mMqttCallback != null) {
                PushClient.this.mMqttCallback.onConnected();
            }
        }

        public void onUnInited() {
            if (PushClient.this.mMqttCallback != null) {
                PushClient.this.mMqttCallback.onUnInited();
            }
        }

        public void onLog(String str) {
            if (PushClient.this.mMqttCallback != null) {
                PushClient.this.mMqttCallback.onLog(str);
            }
        }

        public void onLog(byte[] bArr) {
            String str;
            try {
                str = new String(bArr, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                str = null;
            }
            onLog(str);
        }
    };

    interface IMessageTaskRunnable {
        void onClear();

        void onRun();
    }

    class MessageTask {
        private IMessageTaskRunnable mRunnable = null;
        private int mType = -1;

        public MessageTask(int i, IMessageTaskRunnable iMessageTaskRunnable) {
            this.mType = i;
            this.mRunnable = iMessageTaskRunnable;
        }

        public int getType() {
            return this.mType;
        }

        public IMessageTaskRunnable getRunnable() {
            return this.mRunnable;
        }
    }

    static class MessageThread extends Thread {
        boolean mStop;
        private WeakReference<PushClient> wf;

        private MessageThread(PushClient pushClient) {
            this.wf = null;
            this.mStop = false;
            this.wf = new WeakReference<>(pushClient);
            this.mStop = false;
            setName("MqttMessageThread");
        }

        public void run() {
            consume();
        }

        private void cancel() {
            this.mStop = true;
        }

        private void consume() {
            PushClient pushClient = (PushClient) this.wf.get();
            while (!this.mStop && this.wf.get() != null) {
                try {
                    MessageTask messageTask = (MessageTask) PushClient.queue.take();
                    if (messageTask != null) {
                        if (messageTask == PushClient.lastRestartTask || messageTask.getType() == 2) {
                            PushClient.lastRestartTask = null;
                        }
                        messageTask.getRunnable().onRun();
                    }
                    PushCallback access$300 = pushClient.mPushCallback;
                    StringBuilder sb = new StringBuilder("mqttSDK----从队列取走一个元素，队列剩余个数:");
                    sb.append(PushClient.queue.size());
                    access$300.onLog(sb.toString());
                } catch (InterruptedException e) {
                    PushCallback access$3002 = pushClient.mPushCallback;
                    StringBuilder sb2 = new StringBuilder("mqttSDK----MessageThread exception=");
                    sb2.append(e.getMessage());
                    access$3002.onLog(sb2.toString());
                    return;
                }
            }
        }
    }

    class PublishMessageTaskRunnable implements IMessageTaskRunnable {
        String mMessage;
        Object mToken;
        String mTopicName;

        public PublishMessageTaskRunnable(String str, String str2, Object obj) {
            this.mTopicName = str;
            this.mMessage = str2;
            this.mToken = obj;
        }

        public void onRun() {
            PushClient.this.mPushCallback.onLog(String.format("mqttSDK----publishMessage onRun(). before publishMessageInside. topicName: %s,  message: %s, token: %s", new Object[]{this.mTopicName, this.mMessage, this.mToken}));
            int access$400 = PushClient.this.publishMessageInside(this.mTopicName, this.mMessage);
            PushClient.this.mPushCallback.onLog(String.format("mqttSDK----publishMessage onRun(). after publishMessageInside. topicName: %s,  message: %s, token: %s", new Object[]{this.mTopicName, this.mMessage, this.mToken}));
            if (PushClient.this.mMqttCallback != null) {
                PushClient.this.mMqttCallback.messagePublished(this.mTopicName, this.mMessage, access$400, this.mToken);
            }
        }

        public void onClear() {
            if (PushClient.this.mMqttCallback != null) {
                PushClient.this.mMqttCallback.messageCleared(this.mTopicName, this.mMessage, this.mToken);
            }
        }

        public void onInternalError() {
            if (PushClient.this.mMqttCallback != null) {
                PushClient.this.mMqttCallback.messagePublished(this.mTopicName, this.mMessage, 0, this.mToken);
            }
        }
    }

    interface PushCallback {
        void connectionLost(byte[] bArr);

        void deliveryComplete(int i);

        void messageArrived(byte[] bArr, byte[] bArr2);

        void onConnected();

        void onError(int i);

        void onLog(String str);

        void onLog(byte[] bArr);

        void onUnInited();
    }

    private native boolean nativeInit(String str, String str2, String str3, int i, String str4);

    private native int nativePublishMessage(String str, String str2);

    private native boolean nativeRestart();

    private native boolean nativeSetKeepAliveInterval(int i);

    private static native String nativegetVersion();

    private native void nativeregisterCallback(Object obj);

    private native void nativesetDebug(boolean z);

    private native void nativeunInit();

    static {
        System.loadLibrary("AutoCrypto");
        System.loadLibrary("AutoSSL");
        System.loadLibrary("PushClient-1.0.18");
    }

    private PushClient() {
        MessageThread messageThread = new MessageThread();
        mMessageThread = messageThread;
        messageThread.start();
    }

    public static synchronized PushClient getInstance() {
        PushClient pushClient;
        synchronized (PushClient.class) {
            if (mSelf == null) {
                synchronized (sync_obj) {
                    if (mSelf == null) {
                        mSelf = new PushClient();
                    }
                }
            }
            pushClient = mSelf;
        }
        return pushClient;
    }

    public synchronized void registerCallback(IPushCallback iPushCallback) {
        this.mMqttCallback = iPushCallback;
    }

    public synchronized void init(String str, String str2, String str3, int i, String str4) {
        if (this.mInited) {
            reconnect();
            return;
        }
        this.clientID = str;
        this.mAddress = str2;
        this.mAosKey = str3;
        this.mKeepAliveInterval = i;
        this.extraInfo = str4;
        nativeregisterCallback(this.mPushCallback);
        this.mPushCallback.onLog((String) "mqttSDK----java---init");
        sendMes(0, new IMessageTaskRunnable() {
            public void onClear() {
            }

            public void onRun() {
                PushClient.this.initInside();
            }
        });
        this.mInited = true;
    }

    public synchronized void unInit() {
        if (this.mInited) {
            this.mPushCallback.onLog((String) "mqttSDK----java---unInit");
            sendMes(1, new IMessageTaskRunnable() {
                public void onClear() {
                }

                public void onRun() {
                    PushClient.this.unInitInside();
                }
            });
            this.mInited = false;
        }
    }

    public synchronized void publishMessage(String str, String str2, Object obj) {
        this.mPushCallback.onLog(String.format("mqttSDK----publishMessage mInited: %s, topicName: %s, message: %s, token: %s", new Object[]{Boolean.valueOf(this.mInited), str, str2, obj}));
        if (this.mInited) {
            sendMes(3, new PublishMessageTaskRunnable(str, str2, obj));
        }
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public void setExtraInfo(String str) {
        this.extraInfo = str;
    }

    public synchronized void reconnect() {
        if (this.mInited) {
            this.mPushCallback.onLog((String) "mqttSDK----java---reconnect");
            sendMes(2, new IMessageTaskRunnable() {
                public void onClear() {
                }

                public void onRun() {
                    PushClient.this.reconnectInside();
                }
            });
        }
    }

    public static String getVersion() {
        return nativegetVersion();
    }

    public void setDebug(boolean z) {
        if (!this.mInited) {
            nativesetDebug(z);
        }
    }

    public void setKeepAliveInterval(int i) {
        if (this.mInited) {
            this.mKeepAliveInterval = i;
            nativeSetKeepAliveInterval(i);
        }
    }

    /* access modifiers changed from: private */
    public void reconnectInside() {
        boolean nativeRestart = nativeRestart();
        if (this.mPushCallback != null) {
            this.mPushCallback.onLog("mqttSDK----startReconnectInside restartvalue=".concat(String.valueOf(nativeRestart)));
            if (nativeRestart) {
                this.mPushCallback.onConnected();
            }
        }
    }

    /* access modifiers changed from: private */
    public void initInside() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.clientID);
        sb.append(AUScreenAdaptTool.PREFIX_ID);
        sb.append(this.mAosKey);
        boolean nativeInit = nativeInit(this.clientID, this.mAddress, MD5(sb.toString()), this.mKeepAliveInterval, this.extraInfo);
        this.mPushCallback.onLog("mqttSDK----initInside initvalue=".concat(String.valueOf(nativeInit)));
        if (nativeInit && this.mPushCallback != null) {
            this.mPushCallback.onConnected();
        }
    }

    /* access modifiers changed from: private */
    public void unInitInside() {
        nativeunInit();
        if (this.mPushCallback != null) {
            this.mPushCallback.onLog((String) "mqttSDK----onUnInited");
            this.mPushCallback.onUnInited();
        }
    }

    /* access modifiers changed from: private */
    public int publishMessageInside(String str, String str2) {
        int nativePublishMessage = nativePublishMessage(str, str2);
        this.mPushCallback.onLog("mqttSDK----publishMessageInside return value=".concat(String.valueOf(nativePublishMessage)));
        return nativePublishMessage;
    }

    private final String MD5(String str) {
        byte[] digest;
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] bytes = str.getBytes();
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bytes);
            char[] cArr2 = new char[(r1 * 2)];
            int i = 0;
            for (byte b : instance.digest()) {
                int i2 = i + 1;
                cArr2[i] = cArr[(b >>> 4) & 15];
                i = i2 + 1;
                cArr2[i2] = cArr[b & 15];
            }
            return new String(cArr2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0047, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00ad, code lost:
        if (r1 == null) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00b3, code lost:
        if (r1.size() <= 0) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00b5, code lost:
        r7 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00bd, code lost:
        if (r7.hasNext() == false) goto L_0x00d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00bf, code lost:
        r8 = (com.autonavi.mqtt.PushClient.MessageTask) r7.next();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c9, code lost:
        if (r8.getRunnable() == null) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00cb, code lost:
        r8.getRunnable().onClear();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00d3, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendMes(int r7, com.autonavi.mqtt.PushClient.IMessageTaskRunnable r8) {
        /*
            r6 = this;
            if (r8 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.concurrent.BlockingQueue<com.autonavi.mqtt.PushClient$MessageTask> r0 = queue
            monitor-enter(r0)
            com.autonavi.mqtt.PushClient$PushCallback r1 = r6.mPushCallback     // Catch:{ all -> 0x00d4 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d4 }
            java.lang.String r3 = "mqttSDK----sendMes type："
            r2.<init>(r3)     // Catch:{ all -> 0x00d4 }
            java.lang.String r3 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x00d4 }
            r2.append(r3)     // Catch:{ all -> 0x00d4 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00d4 }
            r1.onLog(r2)     // Catch:{ all -> 0x00d4 }
            boolean r1 = r8 instanceof com.autonavi.mqtt.PushClient.PublishMessageTaskRunnable     // Catch:{ all -> 0x00d4 }
            if (r1 == 0) goto L_0x0039
            java.util.concurrent.BlockingQueue<com.autonavi.mqtt.PushClient$MessageTask> r1 = queue     // Catch:{ all -> 0x00d4 }
            int r1 = r1.size()     // Catch:{ all -> 0x00d4 }
            r2 = 100
            if (r1 < r2) goto L_0x0039
            com.autonavi.mqtt.PushClient$PushCallback r7 = r6.mPushCallback     // Catch:{ all -> 0x00d4 }
            java.lang.String r1 = "mqttSDK----sendMes. over publish queue max"
            r7.onLog(r1)     // Catch:{ all -> 0x00d4 }
            com.autonavi.mqtt.PushClient$PublishMessageTaskRunnable r8 = (com.autonavi.mqtt.PushClient.PublishMessageTaskRunnable) r8     // Catch:{ all -> 0x00d4 }
            r8.onInternalError()     // Catch:{ all -> 0x00d4 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
            return
        L_0x0039:
            int r1 = r6.lastMessageId     // Catch:{ all -> 0x00d4 }
            if (r1 != 0) goto L_0x003f
            if (r7 == 0) goto L_0x0046
        L_0x003f:
            int r1 = r6.lastMessageId     // Catch:{ all -> 0x00d4 }
            r2 = 1
            if (r1 != r2) goto L_0x0048
            if (r7 == 0) goto L_0x0048
        L_0x0046:
            monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
            return
        L_0x0048:
            r1 = 0
            if (r7 != r2) goto L_0x0071
            java.util.LinkedList r3 = new java.util.LinkedList     // Catch:{ all -> 0x00d4 }
            java.util.concurrent.BlockingQueue<com.autonavi.mqtt.PushClient$MessageTask> r4 = queue     // Catch:{ all -> 0x00d4 }
            r3.<init>(r4)     // Catch:{ all -> 0x00d4 }
            java.util.concurrent.BlockingQueue<com.autonavi.mqtt.PushClient$MessageTask> r4 = queue     // Catch:{ all -> 0x00d4 }
            r4.clear()     // Catch:{ all -> 0x00d4 }
            lastRestartTask = r1     // Catch:{ all -> 0x00d4 }
            com.autonavi.mqtt.PushClient$PushCallback r1 = r6.mPushCallback     // Catch:{ all -> 0x00d4 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d4 }
            java.lang.String r5 = "mqttSDK----清空队列："
            r4.<init>(r5)     // Catch:{ all -> 0x00d4 }
            int r5 = r3.size()     // Catch:{ all -> 0x00d4 }
            r4.append(r5)     // Catch:{ all -> 0x00d4 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00d4 }
            r1.onLog(r4)     // Catch:{ all -> 0x00d4 }
            r1 = r3
        L_0x0071:
            r3 = 0
            r4 = 2
            if (r7 != r4) goto L_0x0082
            com.autonavi.mqtt.PushClient$MessageTask r3 = lastRestartTask     // Catch:{ all -> 0x00d4 }
            if (r3 == 0) goto L_0x0083
            com.autonavi.mqtt.PushClient$PushCallback r7 = r6.mPushCallback     // Catch:{ all -> 0x00d4 }
            java.lang.String r8 = "mqttSDK----重复的reconnect消息!"
            r7.onLog(r8)     // Catch:{ all -> 0x00d4 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
            return
        L_0x0082:
            r2 = 0
        L_0x0083:
            r6.lastMessageId = r7     // Catch:{ all -> 0x00d4 }
            com.autonavi.mqtt.PushClient$MessageTask r3 = new com.autonavi.mqtt.PushClient$MessageTask     // Catch:{ all -> 0x00d4 }
            r3.<init>(r7, r8)     // Catch:{ all -> 0x00d4 }
            if (r2 == 0) goto L_0x008e
            lastRestartTask = r3     // Catch:{ all -> 0x00d4 }
        L_0x008e:
            java.util.concurrent.BlockingQueue<com.autonavi.mqtt.PushClient$MessageTask> r7 = queue     // Catch:{ all -> 0x00d4 }
            r7.add(r3)     // Catch:{ all -> 0x00d4 }
            com.autonavi.mqtt.PushClient$PushCallback r7 = r6.mPushCallback     // Catch:{ all -> 0x00d4 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d4 }
            java.lang.String r2 = "mqttSDK----向队列中插入一个元素，队列长度："
            r8.<init>(r2)     // Catch:{ all -> 0x00d4 }
            java.util.concurrent.BlockingQueue<com.autonavi.mqtt.PushClient$MessageTask> r2 = queue     // Catch:{ all -> 0x00d4 }
            int r2 = r2.size()     // Catch:{ all -> 0x00d4 }
            r8.append(r2)     // Catch:{ all -> 0x00d4 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x00d4 }
            r7.onLog(r8)     // Catch:{ all -> 0x00d4 }
            monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
            if (r1 == 0) goto L_0x00d3
            int r7 = r1.size()
            if (r7 <= 0) goto L_0x00d3
            java.util.Iterator r7 = r1.iterator()
        L_0x00b9:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x00d3
            java.lang.Object r8 = r7.next()
            com.autonavi.mqtt.PushClient$MessageTask r8 = (com.autonavi.mqtt.PushClient.MessageTask) r8
            com.autonavi.mqtt.PushClient$IMessageTaskRunnable r0 = r8.getRunnable()
            if (r0 == 0) goto L_0x00b9
            com.autonavi.mqtt.PushClient$IMessageTaskRunnable r8 = r8.getRunnable()
            r8.onClear()
            goto L_0x00b9
        L_0x00d3:
            return
        L_0x00d4:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00d4 }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.mqtt.PushClient.sendMes(int, com.autonavi.mqtt.PushClient$IMessageTaskRunnable):void");
    }
}
