package com.alipay.multimedia.img.base;

import com.alipay.mobile.common.utils.load.LibraryLoadUtils;
import tv.danmaku.ijk.media.player.IjkLibLoader;

public class SoLibLoader implements IjkLibLoader {
    public void loadLibrary(String libName) {
        LibraryLoadUtils.loadLibrary(libName, false);
    }
}
