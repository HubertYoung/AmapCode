package com.alipay.mobile.tinyappcommon.api;

import android.os.Bundle;
import android.os.Messenger;

public interface TinyAppLiteProcessService {
    boolean isLiteProcess();

    void replyDataToLiteProcess(Messenger messenger, int i, Bundle bundle);

    void sendDataToMainProcess(int i, Bundle bundle);
}
