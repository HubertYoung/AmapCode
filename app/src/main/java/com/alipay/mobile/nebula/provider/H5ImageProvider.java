package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5ImageListener;
import com.alipay.mobile.h5container.api.H5ResInputListen;

public interface H5ImageProvider {
    void getImageWithByte(String str, H5ResInputListen h5ResInputListen);

    void loadImage(String str, H5ImageListener h5ImageListener);

    void loadImageKeepSize(String str, H5ImageListener h5ImageListener);

    void loadImageWithSize(String str, int i, int i2, H5ImageListener h5ImageListener);
}
