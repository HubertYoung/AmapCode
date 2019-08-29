package com.autonavi.minimap.ajx3.modules;

import android.text.TextUtils;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import java.net.URI;
import java.util.HashMap;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

@AjxModule("socket")
public class ModuleSocket extends AbstractModule {
    public static final String MODULE_NAME = "socket";
    private static final String STATE_CLOSE = "close";
    private static final String STATE_ERROR = "error";
    private static final String STATE_MESSAGE = "message";
    private static final String STATE_OPEN = "open";
    /* access modifiers changed from: private */
    public static HashMap<String, MyWebSocketClient> mSocketClients = new HashMap<>();
    /* access modifiers changed from: private */
    public static HashMap<Integer, String> mSocketClientsAddress = new HashMap<>();

    static class MyWebSocketClient extends WebSocketClient {
        public JsFunctionCallback mJsCallback;
        public String mSocketId;
        public String mSocketUrl;

        public MyWebSocketClient(String str, String str2, JsFunctionCallback jsFunctionCallback) {
            super(URI.create(str2));
            this.mSocketId = str;
            this.mSocketUrl = str2;
            this.mJsCallback = jsFunctionCallback;
        }

        public void onOpen(ServerHandshake serverHandshake) {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback(ModuleSocket.STATE_OPEN, null);
            }
        }

        public void onMessage(String str) {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback("message", str);
            }
        }

        public void onClose(int i, String str, boolean z) {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback("close", null);
            }
            String str2 = (String) ModuleSocket.mSocketClientsAddress.remove(Integer.valueOf(hashCode()));
            if (str2 != null) {
                ModuleSocket.mSocketClients.remove(str2);
            }
        }

        public void onError(Exception exc) {
            if (this.mJsCallback != null) {
                this.mJsCallback.callback("error", exc.getMessage());
            }
        }
    }

    public ModuleSocket(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("create")
    public void create(String str, String str2, JsFunctionCallback jsFunctionCallback) {
        if (TextUtils.isEmpty(str)) {
            jsFunctionCallback.callback("error", "Id is null!");
        } else if (TextUtils.isEmpty(str2)) {
            jsFunctionCallback.callback("error", "Url is null!");
        } else {
            createSocket(str, str2, jsFunctionCallback);
        }
    }

    @AjxMethod("close")
    public void close(String str) {
        MyWebSocketClient remove = mSocketClients.remove(str);
        if (remove != null) {
            mSocketClientsAddress.remove(Integer.valueOf(remove.hashCode()));
            remove.close();
        }
    }

    @AjxMethod("send")
    public void sendMessage(String str, String str2) {
        MyWebSocketClient myWebSocketClient = mSocketClients.get(str);
        if (myWebSocketClient != null) {
            myWebSocketClient.send(str2);
        }
    }

    public static void createSocket(final String str, final String str2, final JsFunctionCallback jsFunctionCallback) {
        if (mSocketClients.containsKey(str)) {
            ToastHelper.showToast("ID already exists!");
        } else {
            new Thread(new Runnable() {
                public final void run() {
                    MyWebSocketClient myWebSocketClient = new MyWebSocketClient(str, str2, jsFunctionCallback);
                    myWebSocketClient.connect();
                    ModuleSocket.mSocketClients.put(str, myWebSocketClient);
                    ModuleSocket.mSocketClientsAddress.put(Integer.valueOf(myWebSocketClient.hashCode()), str);
                }
            }).start();
        }
    }

    public static void closeAllWebSocket() {
        for (MyWebSocketClient close : mSocketClients.values()) {
            close.close();
        }
        mSocketClients.clear();
        mSocketClientsAddress.clear();
    }
}
