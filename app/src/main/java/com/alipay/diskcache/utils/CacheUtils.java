package com.alipay.diskcache.utils;

import java.io.File;

public class CacheUtils {
    public static boolean checkCacheFile(String path) {
        return FileUtils.checkFile(path);
    }

    public static boolean checkCacheFile(File file) {
        return FileUtils.checkFile(file);
    }
}
