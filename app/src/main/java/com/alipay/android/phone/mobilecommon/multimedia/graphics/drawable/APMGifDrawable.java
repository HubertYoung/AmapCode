package com.alipay.android.phone.mobilecommon.multimedia.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public abstract class APMGifDrawable extends APMBitmapDrawable implements Reusable {
    public static final int RET_DECODER_NULL = -2;
    public static final int RET_ERR_INIT_OOM = -6;
    public static final int RET_ERR_NOT_START = -4;
    public static final int RET_ERR_NO_RES = -5;
    public static final int RET_ERR_PARAM = -7;
    public static final int RET_ERR_UNKNOWN = -8;
    public static final int RET_GIF_ZERO_SIDE = -3;
    public static final int RET_INIT_DECODER_ERROR = -1;
    public static final int RET_SUCCESS = 0;
    public static final int STATE_ATTACHED = 2;
    public static final int STATE_DETACHED = 3;
    public static final int STATE_INIT = 1;
    public static final int STATE_UNINIT = 0;
    protected WeakReference<View> mBindView;
    protected int mCurrentState;

    public static class GifInfo {
        public int height;
        public int width;

        public String toString() {
            return "GifInfo 【" + this.width + DictionaryKeys.CTRLXY_X + this.height + 12305;
        }
    }

    public abstract GifInfo getGifInfo();

    public abstract int pauseAnimation();

    public abstract int startAnimation();

    public abstract int stopAnimation();

    public APMGifDrawable() {
    }

    public APMGifDrawable(Resources res) {
        super(res);
    }

    public APMGifDrawable(Bitmap bitmap) {
        super(bitmap);
    }

    public APMGifDrawable(Resources res, Bitmap bitmap) {
        super(res, bitmap);
    }

    public APMGifDrawable(String filepath) {
        super(filepath);
    }

    public APMGifDrawable(Resources res, String filepath) {
        super(res, filepath);
    }

    public APMGifDrawable(InputStream is) {
        super(is);
    }

    public APMGifDrawable(Resources res, InputStream is) {
        super(res, is);
    }

    public int getCurrentSate() {
        return this.mCurrentState;
    }

    public void bindView(View view) {
        if (view != null) {
            this.mBindView = new WeakReference<>(view);
        }
    }

    public View getBindView() {
        if (this.mBindView == null) {
            return null;
        }
        return (View) this.mBindView.get();
    }
}
