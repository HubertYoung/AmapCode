package com.alipay.mobile.nebula.process;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import java.util.Map;

public interface H5EventHandler {
    public static final String BIZ = "nebulaApp";
    public static final String chooseImage = "chooseImage";
    public static final String chooseVideo = "chooseVideo";
    public static final String getAuthCode = "getAuthCode";
    public static final String getAuthUserInfo = "getAuthUserInfo";

    void clientSenMsg(String str, Message message);

    boolean enableHandler(String str);

    H5IpcServer getH5IpcServerImpl();

    <T> T getIpcProxy(Class<T> cls);

    int getLitePid();

    String getMultiProcessTag();

    void handlerAction(H5Event h5Event, H5BridgeContext h5BridgeContext);

    H5HttpRequestResult httpRequest(String str, String str2, Map<String, String> map, byte[] bArr, long j, String str3, String str4, boolean z, H5Page h5Page);

    boolean isAllLiteProcessHide();

    void moveTaskToBack(Object obj);

    void moveTaskToBackAndStop(Activity activity, boolean z);

    boolean moveTaskToFront(Activity activity, boolean z, Bundle bundle);

    void notifyUcInitSuccess();

    void onLiteProcessPreloadComplete();

    void onTinyAppProcessEvent(String str, String str2);

    void onUcInitAbandonedInLiteProcess();

    void onUcReInitSuccessInLiteProcess();

    void preConnectSpdy();

    void preLoadInTinyProcess();

    void prepare();

    void registerLiteProcessActivityClass(Class cls, int i, boolean z);

    void registerReqBizHandler(String str, Handler handler);

    void registerRspBizHandler(String str, Handler handler);

    void registerServiceBean(String str, Object obj);

    void reply(Messenger messenger, String str, Message message);

    void sendDataToTinyProcess(String str, Bundle bundle);

    void sendDataToTinyProcessWithMsgType(String str, Bundle bundle, int i);

    void showTinyLoadingView(boolean z);

    boolean startLiteProcessAsync();

    void stopLiteProcessByAppIdInServer(String str);

    void stopSelfProcess();

    void unregisterRspBizHandler(String str);
}
