package com.uc.webview.export.extension;

import android.content.Context;
import android.webkit.ValueCallback;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.internal.interfaces.InvokeObject;

@Api
/* compiled from: ProGuard */
public interface IARSession extends InvokeObject {

    @Api
    /* compiled from: ProGuard */
    public static class ARSession implements IARSession {
        protected ValueCallback<Object> mCallback = null;

        public Object invoke(int i, Object[] objArr) {
            return null;
        }

        public void pause() {
        }

        public void resume() {
        }

        public void setDisplayGeometry(int i, int i2) {
        }

        public void stop() {
        }

        public void update(int i) {
        }

        public Size start(Context context, int i, int i2, int i3) {
            return new Size(0, 0);
        }

        public void setFrameCallback(ValueCallback<Object> valueCallback) {
            this.mCallback = valueCallback;
        }
    }

    @Api
    /* compiled from: ProGuard */
    public static class Size {
        public int height;
        public int width;

        public Size(int i, int i2) {
            this.width = i;
            this.height = i2;
        }
    }

    void pause();

    void resume();

    void setDisplayGeometry(int i, int i2);

    void setFrameCallback(ValueCallback<Object> valueCallback);

    Size start(Context context, int i, int i2, int i3);

    void stop();

    void update(int i);
}
