package com.alipay.mobile.nebula.provider;

import android.graphics.Bitmap;
import com.alipay.mobile.h5container.api.H5ImageUploadListener;

public interface H5ImageUploadProvider {
    void uploadImage(Bitmap bitmap, H5ImageUploadListener h5ImageUploadListener);
}
