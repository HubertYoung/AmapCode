package defpackage;

import com.autonavi.minimap.ajx3.core.JsFunctionCallback;

/* renamed from: ccs reason: default package */
/* compiled from: IAMapSuspendView */
public interface ccs {
    void addControl(String str, JsFunctionCallback jsFunctionCallback);

    void hideControl(String str);

    void setCommonControl(String str, JsFunctionCallback jsFunctionCallback);

    void setMarginBottom(int i, int i2);

    void setMarginTop(int i, int i2);

    void setVerticalMargin(int i, int i2, int i3, int i4);

    void setViewAlpha(float f, int i);

    void showControl(String str, boolean z);

    void updateControl(String str);
}
