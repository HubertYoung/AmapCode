package com.alipay.android.phone.mobilecommon.multimedia.graphics.data;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APGifController;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APLoadStateListener;

public class APGifLoadRequest extends APImageLoadRequest {
    public APGifController gifController;
    public APLoadStateListener loadStateListener;

    public APGifLoadRequest() {
        this.detectedGif = true;
        this.width = Integer.MAX_VALUE;
        this.height = Integer.MAX_VALUE;
    }
}
