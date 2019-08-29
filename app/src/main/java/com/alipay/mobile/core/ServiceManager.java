package com.alipay.mobile.core;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.alipay.mobile.framework.service.MicroService;

public interface ServiceManager {
    void exit();

    <T> T findServiceByInterface(String str);

    void onDestroyService(MicroService microService);

    <T> boolean registerService(String str, T t);

    void restoreState(SharedPreferences sharedPreferences);

    void saveState(Editor editor);

    <T> T unregisterService(String str);
}
