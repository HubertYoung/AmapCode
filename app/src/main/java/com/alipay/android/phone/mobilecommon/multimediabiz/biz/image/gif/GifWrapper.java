package com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.gif;

import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APGifController;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.load.APLoadStateListener;
import java.lang.ref.SoftReference;

public class GifWrapper {
    private SoftReference<APGifController> a;
    private SoftReference<APLoadStateListener> b;

    public GifWrapper(APGifController controller, APLoadStateListener loadStateListener) {
        if (controller != null) {
            this.a = new SoftReference<>(controller);
        }
        if (loadStateListener != null) {
            this.b = new SoftReference<>(loadStateListener);
        }
    }

    public APGifController getGifController() {
        if (this.a == null) {
            return null;
        }
        return this.a.get();
    }

    public APLoadStateListener getAPLoadStateListener() {
        if (this.b == null) {
            return null;
        }
        return this.b.get();
    }

    public void setGifController(APGifController controller) {
        if (this.a != null) {
            this.a = new SoftReference<>(controller);
        }
    }

    public void setAPLoadStateListener(APLoadStateListener listener) {
        if (listener != null) {
            this.b = new SoftReference<>(listener);
        }
    }
}
