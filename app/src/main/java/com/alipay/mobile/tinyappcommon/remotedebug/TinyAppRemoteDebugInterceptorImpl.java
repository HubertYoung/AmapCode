package com.alipay.mobile.tinyappcommon.remotedebug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.transport.utils.SwitchMonitorLogUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5BaseFragment;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.h5plugin.TinyAppStoragePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.tinyappcommon.api.TinyAppMixActionService;
import com.alipay.mobile.tinyappcommon.api.TinyAppService;
import com.alipay.mobile.tinyappcommon.remotedebug.a.C0101a;
import com.alipay.mobile.tinyappcommon.remotedebug.state.RemoteDebugState;
import com.alipay.mobile.worker.WebWorker;
import com.alipay.mobile.worker.WorkerManager;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptor;
import com.alipay.mobile.worker.remotedebug.TinyAppRemoteDebugInterceptorManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TinyAppRemoteDebugInterceptorImpl implements C0101a, com.alipay.mobile.tinyappcommon.remotedebug.state.b.a, TinyAppRemoteDebugInterceptor {
    private static final String CHANNEL_ID = "channelId";
    public static final String TAG = TinyAppRemoteDebugInterceptorImpl.class.getSimpleName();
    private static final String WEBSOCKET_HOST_URL = "wss://hpmweb.alipay.com/host/";
    private H5Event h5Event;
    private boolean isExitCMD;
    private a mConnectParams;
    private Map<String, Boolean> mConnectedStateMap;
    private a mDataChannel;
    private b mNetInfoReceiver;
    /* access modifiers changed from: private */
    public com.alipay.mobile.tinyappcommon.remotedebug.state.b mStateManager;
    private String mWorkerId;

    private static class a {
        String a;
        JSONObject b;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    private class b extends BroadcastReceiver {
        private b() {
        }

        /* synthetic */ b(TinyAppRemoteDebugInterceptorImpl x0, byte b) {
            this();
        }

        public final void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                boolean isNetAvailable = b.a(context);
                boolean noNet = intent.getBooleanExtra("noConnectivity", true);
                if (!isNetAvailable && noNet) {
                    TinyAppRemoteDebugInterceptorImpl.this.mStateManager.a(RemoteDebugState.STATE_NETWORK_UNAVAILABLE);
                }
            }
        }
    }

    private static class c {
        public static final TinyAppRemoteDebugInterceptorImpl a = new TinyAppRemoteDebugInterceptorImpl();
    }

    private TinyAppRemoteDebugInterceptorImpl() {
        initDataChannel();
        this.mStateManager = new com.alipay.mobile.tinyappcommon.remotedebug.state.b();
        this.mStateManager.a((com.alipay.mobile.tinyappcommon.remotedebug.state.b.a) this);
        this.mConnectedStateMap = new ConcurrentHashMap();
    }

    public static TinyAppRemoteDebugInterceptorImpl getInstance() {
        return c.a;
    }

    private void initDataChannel() {
        if (getDataChannel() == DataChannelEnum.WEBSOCKET_CHANNEL) {
            this.mDataChannel = new com.alipay.mobile.tinyappcommon.remotedebug.a.a();
        } else {
            this.mDataChannel = new com.alipay.mobile.tinyappcommon.remotedebug.a.a();
        }
        this.mDataChannel.a((C0101a) this);
    }

    private DataChannelEnum getDataChannel() {
        return DataChannelEnum.WEBSOCKET_CHANNEL;
    }

    public void registerWorker(String workerId, H5Event h5Event2) {
        TinyAppMixActionService mixActionService = TinyAppService.get().getMixActionService();
        if (mixActionService != null && !mixActionService.supportRemoteDebugMode()) {
            TinyAppRemoteDebugInterceptorManager.get().setRemoteDebug(false);
            H5Log.d(TAG, "registerWorker...remoteDebug is not open");
        } else if (h5Event2 != null) {
            H5Page h5Page = h5Event2.getH5page();
            if (h5Page != null) {
                this.h5Event = h5Event2;
                String channelId = H5Utils.getString(h5Page.getParams(), (String) "channelId");
                if (TextUtils.isEmpty(channelId)) {
                    H5Log.d(TAG, "registerWorker...channelId is null");
                    return;
                }
                this.mWorkerId = workerId;
                initDataChannel();
                this.mStateManager = new com.alipay.mobile.tinyappcommon.remotedebug.state.b();
                this.mStateManager.a((com.alipay.mobile.tinyappcommon.remotedebug.state.b.a) this);
                this.mStateManager.a(h5Event2.getActivity());
                this.mConnectedStateMap = new ConcurrentHashMap();
                registerNetInfoReceiver();
                H5Log.d(TAG, "registerWorker..." + channelId);
                String websocketUrl = new StringBuilder(WEBSOCKET_HOST_URL).append(channelId).toString();
                this.mConnectParams = new a(0);
                this.mConnectParams.b = null;
                this.mConnectParams.a = websocketUrl;
                this.mDataChannel.a(websocketUrl, h5Event2);
            }
        }
    }

    public void remoteLoadUrl(String url) {
        H5Log.d(TAG, "remoteLoadUrl");
        if (this.mDataChannel != null) {
            this.mDataChannel.a(url);
        }
    }

    public void sendMessageToRemoteWorker(String message) {
        H5Log.d(TAG, "sendMessageToRemoteWorker");
        if (this.mDataChannel != null) {
            this.mDataChannel.a(message);
        }
    }

    public boolean isRemoteDebugConnected(String appId) {
        if (TextUtils.isEmpty(appId)) {
            return false;
        }
        Boolean isConnected = this.mConnectedStateMap.get(appId);
        if (isConnected == null || !isConnected.booleanValue()) {
            return false;
        }
        return true;
    }

    public void onConnectSuccess(String appId) {
        H5Log.d(TAG, "onConnectSuccess");
        this.mConnectedStateMap.put(appId, Boolean.valueOf(true));
        this.mStateManager.a(RemoteDebugState.STATE_CONNECTED);
        this.mDataChannel.a(String.format("CMD:REGISTER_WORKER:%s:%s", new Object[]{this.mWorkerId, com.alipay.android.phone.a.a.a.a}));
        H5Utils.runOnMain(new Runnable() {
            public final void run() {
                H5Page h5Page = TinyAppRemoteDebugInterceptorImpl.this.getTopH5Page();
                if (h5Page != null) {
                    h5Page.sendEvent(TinyAppStoragePlugin.SEND_TINY_LOCAL_STORAGE_TO_IDE, null);
                }
            }
        }, 2000);
        WebWorker worker = WorkerManager.getWorker(this.mWorkerId);
        if (worker == null) {
            H5Log.d(TAG, "recv...worker is null: " + this.mWorkerId);
        } else {
            worker.onAlipayJSBridgeReady();
        }
    }

    public void onConnectFailed() {
        H5Log.d(TAG, "onConnectFailed");
        this.mStateManager.a(RemoteDebugState.STATE_CONNECT_FAILED);
    }

    public void onConnectClosed(String appId) {
        H5Log.d(TAG, "onConnectClosed");
        this.mConnectedStateMap.put(appId, Boolean.valueOf(false));
        if (!this.isExitCMD) {
            this.mStateManager.a(RemoteDebugState.STATE_CONNECT_FAILED);
        }
    }

    public void onConnectError(String appId, int error, String errorMessage) {
        H5Log.d(TAG, "onConnectError...error:" + error + ",errorMessage:" + errorMessage);
        this.mConnectedStateMap.put(appId, Boolean.valueOf(false));
        this.mStateManager.a(RemoteDebugState.STATE_CONNECT_FAILED);
    }

    public void recv(String message) {
        H5Log.d(TAG, "recv..." + message);
        if (TextUtils.isEmpty(message)) {
            H5Log.d(TAG, "recv...message is null");
        } else if (message.startsWith("CMD:REMOTE_DISCONNECTED")) {
            this.mStateManager.a(RemoteDebugState.STATE_REMOTE_DISCONNECTED);
        } else if (message.startsWith("CMD:HIT_BREAKPOINT")) {
            this.mStateManager.a(RemoteDebugState.STATE_HIT_BREAKPOINT);
        } else if (message.startsWith("CMD:RELEASE_BREAKPOINT")) {
            this.mStateManager.a(RemoteDebugState.STATE_RELEASE_BREAKPOINT);
        } else if (message.startsWith("CMD:RECV_RENDER_DEBUG:")) {
            recvRenderDebugMessage(message);
        } else {
            WebWorker worker = WorkerManager.getWorker(this.mWorkerId);
            if (worker == null) {
                H5Log.d(TAG, "recv...worker is null: " + this.mWorkerId);
            } else {
                worker.getWorkerControllerProvider().handleMsgFromWorker(message);
            }
        }
    }

    private void recvRenderDebugMessage(String text) {
        String msg = text.replaceFirst("CMD:RECV_RENDER_DEBUG:", "");
        if (TextUtils.isEmpty(msg)) {
            H5Log.d(TAG, "recvRenderDebugMessage...msg is null");
            return;
        }
        H5Page h5Page = getTopH5Page();
        if (h5Page == null) {
            H5Log.d(TAG, "recvRenderDebugMessage...h5Page is null");
            return;
        }
        try {
            JSONObject data = new JSONObject();
            data.put((String) "data", (Object) msg);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "data", (Object) data);
            h5Page.getBridge().sendToWeb("renderDebugMessage", jsonObject, null);
        } catch (Throwable e) {
            H5Log.e(TAG, "recvRenderDebugMessage...e=" + e);
        }
    }

    /* access modifiers changed from: private */
    public H5Page getTopH5Page() {
        try {
            H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
            if (h5Service == null) {
                H5Log.d(TAG, "getTopH5Page ");
                return null;
            }
            H5BaseFragment h5BaseFragment = h5Service.getTopH5BaseFragment();
            if (h5BaseFragment == null || h5BaseFragment.getH5Page() == null) {
                return h5Service.getTopH5PageForTiny();
            }
            return h5BaseFragment.getH5Page();
        } catch (Throwable e) {
            H5Log.d(TAG, "getTopH5Page...e:" + e);
            return null;
        }
    }

    public void recv(byte[] bytes) {
        H5Log.d(TAG, SwitchMonitorLogUtil.SUB_TYPE_RECV);
        recv(new String(bytes));
    }

    public void exitDebugMode() {
        H5Log.d(TAG, "exitDebugMode");
        this.isExitCMD = true;
        this.mDataChannel.a((String) "CMD:LOCAL_DISCONNECTED");
        this.mDataChannel.b("user_exit_debug");
        this.mDataChannel = null;
        unregisterNetInfoReceiver();
        this.mStateManager.b();
        H5Page h5Page = getTopH5Page();
        if (h5Page != null) {
            h5Page.sendEvent(CommonEvents.EXIT_SESSION, null);
        }
    }

    private void registerNetInfoReceiver() {
        try {
            if (this.h5Event != null) {
                this.mNetInfoReceiver = new b(this, 0);
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                this.h5Event.getActivity().registerReceiver(this.mNetInfoReceiver, filter);
            }
        } catch (Exception e) {
            H5Log.e(TAG, "registerNetInfoReceiver: [ Exception=" + e + " ]");
        }
    }

    private void unregisterNetInfoReceiver() {
        try {
            if (!(this.mNetInfoReceiver == null || this.h5Event == null)) {
                this.h5Event.getActivity().unregisterReceiver(this.mNetInfoReceiver);
            }
        } catch (Exception e) {
            H5Log.e(TAG, "unregisterActionReceiver: [ Exception=" + e + " ]");
        }
        this.mNetInfoReceiver = null;
    }
}
