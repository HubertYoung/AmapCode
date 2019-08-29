package com.alipay.streammedia.mmengine.picture.gif;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.alipay.streammedia.mmengine.picture.gif.NSGifDecoder.Frame;

public class NSGifView extends ImageView {
    private boolean animating = false;
    /* access modifiers changed from: private */
    public Frame curFrame = null;
    /* access modifiers changed from: private */
    public NSGifDecoder gifDecoder = null;
    private RefreshThread refreshThread;
    /* access modifiers changed from: private */
    public UpdateAction updateAction = null;

    class RefreshThread implements Runnable {
        boolean stop = false;

        RefreshThread() {
        }

        public void run() {
            while (!this.stop) {
                NSGifView.this.post(NSGifView.this.updateAction);
                try {
                    Thread.sleep((long) NSGifView.this.curFrame.delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (this.stop) {
                    return;
                }
            }
        }
    }

    class UpdateAction implements Runnable {
        /* access modifiers changed from: private */
        public boolean stop = false;

        UpdateAction() {
        }

        public void run() {
            if (!this.stop) {
                if (NSGifView.this.gifDecoder != null) {
                    NSGifView.this.curFrame = NSGifView.this.gifDecoder.getNextFrame();
                }
                NSGifView.this.invalidate();
            }
        }
    }

    public NSGifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NSGifView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (this.updateAction != null) {
            this.updateAction.stop = true;
        }
        this.updateAction = new UpdateAction();
    }

    public void setImagePath(String filePath) {
        synchronized (this) {
            if (this.animating) {
                this.animating = false;
            }
            if (this.gifDecoder != null) {
                this.gifDecoder.recycle();
            }
            this.gifDecoder = new NSGifDecoder();
            if (this.gifDecoder.load(filePath) < 0) {
                this.gifDecoder.recycle();
                this.gifDecoder = null;
                return;
            }
            this.curFrame = this.gifDecoder.getFirstFrame();
            setImageBitmap(this.curFrame.bitmap);
        }
    }

    public void startAnimation() {
        synchronized (this) {
            if (this.refreshThread != null) {
                this.refreshThread.stop = true;
            }
            this.refreshThread = new RefreshThread();
            init();
            new Thread(this.refreshThread).start();
        }
    }

    public void stopAnimation() {
        synchronized (this) {
            if (this.refreshThread != null) {
                this.refreshThread.stop = true;
                this.refreshThread = null;
            }
            if (this.updateAction != null) {
                this.updateAction.stop = true;
                this.updateAction = null;
            }
        }
    }

    public boolean isAnimating() {
        return this.animating;
    }
}
