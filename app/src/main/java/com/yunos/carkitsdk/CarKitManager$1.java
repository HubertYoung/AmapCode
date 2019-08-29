package com.yunos.carkitsdk;

import android.os.RemoteException;
import com.iflytek.tts.TtsService.Tts;
import com.yunos.carkitsdk.IServiceStatusCallBack.Stub;
import java.util.List;
import java.util.TreeSet;

public class CarKitManager$1 extends Stub {
    final /* synthetic */ fbl this$0;

    public CarKitManager$1(fbl fbl) {
        this.this$0 = fbl;
    }

    public void onFoundCar(List<String> list) throws RemoteException {
        this.this$0.m.obtainMessage(256, list).sendToTarget();
    }

    public void onReceiveMsgNotify(long j, int i, String str) throws RemoteException {
        "onReceiveMsgNotify src=".concat(String.valueOf(j));
        this.this$0.m.obtainMessage(Tts.TTS_STATE_CREATED, new a(j, i, str)).sendToTarget();
    }

    public void onReceiveFileNotify(TransferInfo transferInfo) throws RemoteException {
        this.this$0.m.obtainMessage(261, transferInfo).sendToTarget();
    }

    public void onSendFileNotify(TransferInfo transferInfo) throws RemoteException {
        this.this$0.m.obtainMessage(Tts.TTS_STATE_DESTROY, transferInfo).sendToTarget();
    }

    public void onConnectionStatusNotify(ConnectionStatusInfo connectionStatusInfo) throws RemoteException {
        new StringBuilder("onConnectionStatusNotify ").append(connectionStatusInfo.b);
        if (this.this$0.h == connectionStatusInfo.c && this.this$0.j == connectionStatusInfo.b && this.this$0.i == connectionStatusInfo.d && this.this$0.k != null) {
            this.this$0.k.equals(connectionStatusInfo.a);
        }
        this.this$0.h = connectionStatusInfo.c;
        this.this$0.j = connectionStatusInfo.b;
        this.this$0.k = connectionStatusInfo.a;
        this.this$0.i = connectionStatusInfo.d;
        this.this$0.m.obtainMessage(Tts.TTS_STATE_INVALID_DATA, connectionStatusInfo).sendToTarget();
    }

    public void onRegisteredComponents(List<String> list) throws RemoteException {
        TreeSet treeSet = new TreeSet();
        for (String parseLong : list) {
            treeSet.add(Long.valueOf(Long.parseLong(parseLong)));
        }
        this.this$0.g = treeSet;
        new StringBuilder("current registered com: ").append(this.this$0.g);
    }

    public void onRemoteComponents(List<String> list) throws RemoteException {
        TreeSet treeSet = new TreeSet();
        for (String parseLong : list) {
            treeSet.add(Long.valueOf(Long.parseLong(parseLong)));
        }
        this.this$0.f = treeSet;
        new StringBuilder("current peer com: ").append(this.this$0.f);
    }

    public void onUnregisteredByOther() throws RemoteException {
        this.this$0.m.obtainMessage(262).sendToTarget();
    }
}
