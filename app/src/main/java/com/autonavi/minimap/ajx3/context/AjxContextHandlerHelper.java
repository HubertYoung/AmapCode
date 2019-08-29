package com.autonavi.minimap.ajx3.context;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import java.lang.ref.WeakReference;

class AjxContextHandlerHelper {
    private final InnerHandler mHandler;

    static final class InnerHandler extends Handler {
        private final SparseArray<WeakReference<AjxContextHandlerCallback>> mCallbacks = new SparseArray<>();
        private int mIndex = 0;

        public InnerHandler(Context context) {
            super(context.getMainLooper());
        }

        public final boolean post(AjxContextHandlerCallback ajxContextHandlerCallback, Message message, long j) {
            int generateIndex = generateIndex();
            removeMessages(generateIndex);
            this.mCallbacks.put(generateIndex, new WeakReference(ajxContextHandlerCallback));
            Message obtainMessage = obtainMessage();
            obtainMessage.what = generateIndex;
            obtainMessage.obj = message;
            return sendMessageDelayed(obtainMessage, j);
        }

        public final void destroy() {
            removeCallbacksAndMessages(null);
            this.mIndex = 0;
            this.mCallbacks.clear();
        }

        public final void handleMessage(Message message) {
            int i = message.what;
            WeakReference weakReference = this.mCallbacks.get(i);
            this.mCallbacks.delete(i);
            if (weakReference != null && weakReference.get() != null) {
                AjxContextHandlerCallback ajxContextHandlerCallback = (AjxContextHandlerCallback) weakReference.get();
                if ((message.obj instanceof Message) || message.obj == null) {
                    ajxContextHandlerCallback.handleCallback((Message) message.obj);
                }
            }
        }

        private int generateIndex() {
            if (this.mIndex == Integer.MAX_VALUE) {
                this.mIndex = 0;
            }
            int i = this.mIndex;
            this.mIndex = i + 1;
            return i;
        }
    }

    public AjxContextHandlerHelper(Context context) {
        this.mHandler = new InnerHandler(context);
    }

    public boolean post(AjxContextHandlerCallback ajxContextHandlerCallback, Message message, long j) {
        return this.mHandler.post(ajxContextHandlerCallback, message, j);
    }

    public void destroy() {
        this.mHandler.destroy();
    }
}
