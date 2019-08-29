package com.autonavi.minimap.ajx3.widget.view.video.player;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public abstract class AbstractHandlerPlayer extends AbstractPlayer {
    private static final int MSG_PREPARE = 1;
    private static final int MSG_RELEASE = 3;
    private static final int MSG_STOP = 2;
    private static final String TAG = "VideoMediaPlayer";
    private MediaHandler mMediaHandler;
    private HandlerThread mMediaHandlerThread = new HandlerThread(TAG);

    class MediaHandler extends Handler {
        public MediaHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    AbstractHandlerPlayer.this.onHandlePrepare();
                    return;
                case 2:
                    AbstractHandlerPlayer.this.onHandleStop();
                    break;
                case 3:
                    AbstractHandlerPlayer.this.onHandleRelease();
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onHandlePrepare() {
    }

    /* access modifiers changed from: protected */
    public void onHandleRelease() {
    }

    /* access modifiers changed from: protected */
    public void onHandleStop() {
    }

    AbstractHandlerPlayer() {
        this.mMediaHandlerThread.start();
        this.mMediaHandler = new MediaHandler(this.mMediaHandlerThread.getLooper());
    }

    /* access modifiers changed from: protected */
    public void prepare() {
        this.mMediaHandler.obtainMessage(1).sendToTarget();
    }

    public void stop() {
        this.mMediaHandler.obtainMessage(3).sendToTarget();
    }

    public void release() {
        this.mMediaHandler.obtainMessage(3).sendToTarget();
    }
}
