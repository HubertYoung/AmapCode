package com.alipay.mobile.mrtc.api.wwj;

import android.os.Bundle;

public interface APToyMListener {
    void onEvent(int i, String str, Bundle bundle);

    void onInfo(StreamerErrorCode streamerErrorCode, String str, Object obj);

    void onNetworkChanged(int i);

    void onStateChanged(int i, String str, Bundle bundle);
}
