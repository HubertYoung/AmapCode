package com.taobao.accs;

import android.content.Context;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import java.net.URL;
import java.util.Map;

public interface IACCSManager {
    void bindApp(Context context, String str, String str2, IAppReceiver iAppReceiver);

    void bindApp(Context context, String str, String str2, String str3, IAppReceiver iAppReceiver);

    void bindService(Context context, String str);

    void bindUser(Context context, String str);

    void bindUser(Context context, String str, boolean z);

    boolean cancel(Context context, String str);

    void clearLoginInfo(Context context);

    void forceDisableService(Context context);

    void forceEnableService(Context context);

    Map<String, Boolean> forceReConnectChannel() throws Exception;

    Map<String, Boolean> getChannelState() throws Exception;

    String getUserUnit();

    boolean isChannelError(int i);

    boolean isNetworkReachable(Context context);

    void registerDataListener(Context context, String str, AccsAbstractDataListener accsAbstractDataListener);

    void registerSerivce(Context context, String str, String str2);

    void sendBusinessAck(String str, String str2, String str3, short s, String str4, Map<Integer, String> map);

    String sendData(Context context, AccsRequest accsRequest);

    String sendData(Context context, String str, String str2, byte[] bArr, String str3);

    String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4);

    String sendData(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url);

    String sendPushResponse(Context context, AccsRequest accsRequest, ExtraInfo extraInfo);

    String sendRequest(Context context, AccsRequest accsRequest);

    String sendRequest(Context context, AccsRequest accsRequest, String str, boolean z);

    String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4);

    String sendRequest(Context context, String str, String str2, byte[] bArr, String str3, String str4, URL url);

    void setLoginInfo(Context context, ILoginInfo iLoginInfo);

    @Deprecated
    void setMode(Context context, int i);

    void setProxy(Context context, String str, int i);

    void startInAppConnection(Context context, String str, String str2, IAppReceiver iAppReceiver);

    void startInAppConnection(Context context, String str, String str2, String str3, IAppReceiver iAppReceiver);

    void unRegisterDataListener(Context context, String str);

    void unRegisterSerivce(Context context, String str);

    void unbindApp(Context context);

    void unbindService(Context context, String str);

    void unbindUser(Context context);

    void updateConfig(AccsClientConfig accsClientConfig);
}
