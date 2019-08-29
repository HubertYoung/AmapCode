package com.alibaba.wireless.security.open.securitybodysdk;

import android.view.KeyEvent;
import android.view.MotionEvent;
import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

@InterfacePluginInfo(pluginName = "securitybody")
public interface ISecurityBodyPageTrack extends IComponent {
    public static final String PAGE_ID_KEY = "pageId";

    void addKeyEvent(String str, KeyEvent keyEvent) throws SecException;

    void addScrollEvent(String str, int i, int i2, int i3, int i4) throws SecException;

    void addTouchEvent(String str, MotionEvent motionEvent) throws SecException;

    void onPageDestroy(String str) throws SecException;

    void onPageStart(String str) throws SecException;
}
