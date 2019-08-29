package com.uc.webview.export.extension;

import com.uc.webview.export.annotations.Interface;

@Interface
/* compiled from: ProGuard */
public interface IEmbedViewContainer {

    @Interface
    /* compiled from: ProGuard */
    public interface OnParamChangedListener {
        void onParamChanged(String[] strArr, String[] strArr2);
    }

    @Interface
    /* compiled from: ProGuard */
    public interface OnStateChangedListener {
        void onAttachedToWebView();

        void onDestroy();

        void onDetachedFromWebView();
    }

    @Interface
    /* compiled from: ProGuard */
    public interface OnVisibilityChangedListener {
        void onVisibilityChanged(int i);
    }

    void changeViewSize(int i, int i2);

    void notifyEnterFullScreen();

    void notifyExitFullScreen();

    void sendViewData(String str);

    void setOnParamChangedListener(OnParamChangedListener onParamChangedListener);

    void setOnStateChangedListener(OnStateChangedListener onStateChangedListener);

    void setOnVisibilityChangedListener(OnVisibilityChangedListener onVisibilityChangedListener);

    void setPosterUrl(String str);
}
