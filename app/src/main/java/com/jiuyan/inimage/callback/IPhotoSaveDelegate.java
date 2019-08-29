package com.jiuyan.inimage.callback;

import android.graphics.Bitmap;
import com.alipay.android.hackbyte.ClassVerifier;

public interface IPhotoSaveDelegate {
    public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

    public interface IPhotoSaveCallback {
        public static final Class sInjector = (Boolean.TRUE.booleanValue() ? String.class : ClassVerifier.class);

        void onPhotoSaveResult(boolean z, String str);
    }

    void savePhoto(Bitmap bitmap, IPhotoSaveCallback iPhotoSaveCallback);
}
