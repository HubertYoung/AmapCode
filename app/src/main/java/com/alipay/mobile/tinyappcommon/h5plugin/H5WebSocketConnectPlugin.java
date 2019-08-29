package com.alipay.mobile.tinyappcommon.h5plugin;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.android.phone.mobilesdk.socketcraft.api.BasicWebSocketContext;
import com.alipay.android.phone.mobilesdk.socketcraft.api.DefaultWebSocketClient;
import com.alipay.android.phone.mobilesdk.socketcraft.api.WSContextConstant;
import com.alipay.mobile.common.transport.utils.ZURLEncodedUtil;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.tinyappcommon.ws.WSResultEnum;
import com.alipay.mobile.tinyappcommon.ws.a;
import com.alipay.mobile.tinyappcommon.ws.b;
import com.alipay.mobile.tinyappcommon.ws.c;
import com.alipay.mobile.tinyappcommon.ws.d;
import com.alipay.mobile.tinyappcommon.ws.e;
import java.net.URI;
import java.util.Map;

public class H5WebSocketConnectPlugin extends H5SimplePlugin {
    public static final String CONNECT_SOCKET = "connectSocket";
    private static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
    private static final int EACH_TINY_APP_MAX_SOCKET_SIZE = 2;
    public static final String TAG = "WS_H5WebSocketPlugin";
    private String appId = null;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction(CONNECT_SOCKET);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (!TextUtils.equals(CONNECT_SOCKET, event.getAction())) {
            return false;
        }
        try {
            connectSocket(event, context);
        } catch (Throwable e) {
            H5Log.e(TAG, "connectSocket unknow error", e);
            b.a(context, "exception: " + e.toString());
        }
        return true;
    }

    private void connectSocket(H5Event event, H5BridgeContext bridgeContext) {
        this.appId = b.a(event);
        if (TextUtils.isEmpty(this.appId)) {
            H5Log.e((String) TAG, (String) "appId is empty.");
        } else {
            startConnectSocket(this.appId, event, bridgeContext);
        }
    }

    public void startConnectSocket(String appId2, H5Event event, H5BridgeContext bridgeContext) {
        e oldWebSocketSession;
        DefaultWebSocketClient webSocketClient;
        this.appId = appId2;
        String socketTaskID = b.b(event);
        boolean isMultiple = b.c(event);
        H5Log.d(TAG, String.format("enter connectSocket, appId: %s", new Object[]{appId2}));
        if (!isMultiple || TextUtils.isEmpty(socketTaskID) || d.a().a(appId2) < 2) {
            if (!isMultiple || TextUtils.isEmpty(socketTaskID)) {
                oldWebSocketSession = a.a().a(appId2);
            } else {
                oldWebSocketSession = d.a().a(appId2, socketTaskID);
            }
            if (!(oldWebSocketSession == null || oldWebSocketSession.a == null)) {
                if (oldWebSocketSession.b()) {
                    H5Log.d(TAG, "the already appid has a websocket");
                    return;
                } else if (!isMultiple || TextUtils.isEmpty(socketTaskID)) {
                    a.a().b(appId2);
                } else {
                    d.a().a(appId2, socketTaskID, true);
                }
            }
            JSONObject paramJson = event.getParam();
            String url = paramJson.getString("url");
            if (TextUtils.isEmpty(url)) {
                H5Log.d(TAG, "url is null");
                b.a(bridgeContext, WSResultEnum.URL_IS_NULL_MSG);
                return;
            }
            H5Log.d(TAG, String.format("url is %s", new Object[]{url}));
            try {
                URI uri = new URI(url);
                if (TextUtils.isEmpty(uri.getScheme())) {
                    H5Log.d(TAG, String.format("connect fail : %s ", new Object[]{url}));
                    b.a(bridgeContext, WSResultEnum.URL_NOT_WELL_FORMAT);
                    return;
                }
            } catch (Throwable e) {
                H5Log.e((String) TAG, e);
            }
            H5Log.d(TAG, String.format("send request ok, url is : %s ,appid: %s", new Object[]{url, appId2}));
            JSONObject dataJson = paramJson.getJSONObject("data");
            if (dataJson != null && !dataJson.isEmpty()) {
                url = b.a(url, dataJson);
            }
            String url2 = ZURLEncodedUtil.urlEncode(url);
            try {
                URI uri2 = new URI(url2);
                Map headers = b.a(paramJson);
                headers.put(H5AppHttpRequest.HEADER_UA, b.d(event));
                headers.remove("referer");
                String[] protocolsArray = getStringArr(paramJson, "protocols", null);
                if (protocolsArray != null) {
                    String protocols = "";
                    int protocolsLength = protocolsArray.length;
                    for (int i = 0; i < protocolsLength; i++) {
                        protocols = protocols + protocolsArray[i];
                        if (i != protocolsLength - 1) {
                            protocols = protocols + ",";
                        }
                    }
                    headers.put("Sec-WebSocket-Protocol", protocols);
                    H5Log.d(TAG, "protocols: " + protocols);
                }
                H5Log.d(TAG, String.format("connectSocket, url= %s, timeout = %d", new Object[]{url2, Integer.valueOf(60000)}));
                c h5WebSocketCallback = new c(bridgeContext, socketTaskID, isMultiple);
                try {
                    BasicWebSocketContext webSocketContext = new BasicWebSocketContext();
                    webSocketContext.setAttribute(WSContextConstant.BIZ_UNIQUE_ID, appId2);
                    webSocketClient = new DefaultWebSocketClient(uri2, headers, h5WebSocketCallback, webSocketContext);
                } catch (Throwable e2) {
                    H5Log.w(TAG, "New constructor can't find, will use the original constructor。" + e2.toString());
                    webSocketClient = new DefaultWebSocketClient(uri2, headers, h5WebSocketCallback);
                }
                e webSocketSession = new e();
                webSocketSession.a = webSocketClient;
                try {
                    if (url2.startsWith("ws://")) {
                        H5Log.d(TAG, String.format("url is %s ,user ws connect", new Object[]{url2}));
                        webSocketClient.connect();
                    } else if (url2.startsWith("wss://")) {
                        H5Log.d(TAG, String.format("url is %s ,user wss connect", new Object[]{url2}));
                        webSocketClient.connectWithSSL();
                    } else {
                        H5Log.d(TAG, String.format("url error: %s not ws:// or wss://", new Object[]{url2}));
                        b.a(bridgeContext, WSResultEnum.URL_NOT_WS_OR_WSS);
                        return;
                    }
                    if (!isMultiple || TextUtils.isEmpty(socketTaskID)) {
                        a.a().a(appId2, webSocketSession);
                        b.b(bridgeContext, "");
                    } else if (d.a().a(appId2) <= 2) {
                        d.a().a(appId2, socketTaskID, webSocketSession);
                        b.b(bridgeContext, "");
                    }
                } catch (Throwable e3) {
                    H5Log.e(TAG, String.format("url %s exception ", new Object[]{url2}), e3);
                    b.a(bridgeContext, "exception: " + e3.toString());
                }
            } catch (Throwable th) {
                H5Log.d(TAG, String.format("connect fail : %s ", new Object[]{url2}));
                b.a(bridgeContext, WSResultEnum.URL_NOT_WELL_FORMAT);
            }
        } else {
            H5Log.e((String) TAG, (String) "over each tiny app max socket count");
            b.a(bridgeContext, (String) "exceed each tiny app max socket count");
        }
    }

    public void onRelease() {
        super.onRelease();
        d.a().b(this.appId);
        a.a().b(this.appId);
        H5Log.w(TAG, "onRelease. Removed web socket session , appId: " + this.appId);
    }

    public static String[] getStringArr(JSONObject object, String key, String[] defaultVal) {
        String[] ret = defaultVal;
        if (object == null) {
            return ret;
        }
        JSONArray arr = object.getJSONArray(key);
        if (arr == null || arr.size() <= 0) {
            return ret;
        }
        String[] ret2 = new String[arr.size()];
        arr.toArray(ret2);
        return ret2;
    }
}
