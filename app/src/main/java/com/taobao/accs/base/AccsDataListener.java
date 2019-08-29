package com.taobao.accs.base;

import com.taobao.accs.base.TaoBaseService.ConnectInfo;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;

public interface AccsDataListener {
    void onAntiBrush(boolean z, ExtraInfo extraInfo);

    void onBind(String str, int i, ExtraInfo extraInfo);

    void onConnected(ConnectInfo connectInfo);

    void onData(String str, String str2, String str3, byte[] bArr, ExtraInfo extraInfo);

    void onDisconnected(ConnectInfo connectInfo);

    void onResponse(String str, String str2, int i, byte[] bArr, ExtraInfo extraInfo);

    void onSendData(String str, String str2, int i, ExtraInfo extraInfo);

    void onUnbind(String str, int i, ExtraInfo extraInfo);
}
