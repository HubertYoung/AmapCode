package com.alipay.mobile.framework;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public interface MicroContent {
    void restoreState(SharedPreferences sharedPreferences);

    void saveState(Editor editor);
}
