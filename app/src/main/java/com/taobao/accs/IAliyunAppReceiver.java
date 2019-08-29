package com.taobao.accs;

import com.taobao.accs.ErrorCode.INIT_ERROR;

public interface IAliyunAppReceiver {
    void onBindApp(INIT_ERROR init_error);
}
