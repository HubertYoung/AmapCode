package com.alipay.android.phone.mobilecommon.multimedia.graphics.load;

import android.os.Bundle;

public interface APLoadStateListener {
    void onDiskCacheLoadState(boolean z, int i, Bundle bundle);

    void onLocalLoadState(boolean z, int i, Bundle bundle);

    void onMemLoadState(boolean z, int i, Bundle bundle);

    void onNetLoadState(boolean z, int i, Bundle bundle);
}
