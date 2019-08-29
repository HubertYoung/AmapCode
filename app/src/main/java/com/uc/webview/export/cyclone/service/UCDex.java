package com.uc.webview.export.cyclone.service;

import android.content.Context;
import com.uc.webview.export.cyclone.Constant;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import java.io.IOException;

@Constant
/* compiled from: ProGuard */
public interface UCDex extends UCServiceInterface {
    DexClassLoader createDexClassLoader(Context context, Boolean bool, String str, String str2, String str3, ClassLoader classLoader);

    DexFile createDexFile(Context context, Boolean bool, String str, String str2, int i) throws IOException;

    void run();
}
