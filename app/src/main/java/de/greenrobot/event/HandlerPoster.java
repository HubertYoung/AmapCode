package de.greenrobot.event;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

final class HandlerPoster extends Handler {
    private final EventBus eventBus;
    private boolean handlerActive;
    private final int maxMillisInsideHandleMessage;
    private final PendingPostQueue queue = new PendingPostQueue();

    HandlerPoster(EventBus eventBus2, Looper looper, int i) {
        super(looper);
        this.eventBus = eventBus2;
        this.maxMillisInsideHandleMessage = i;
    }

    /* access modifiers changed from: 0000 */
    public final void enqueue(Subscription subscription, Object obj) {
        PendingPost obtainPendingPost = PendingPost.obtainPendingPost(subscription, obj);
        synchronized (this) {
            this.queue.enqueue(obtainPendingPost);
            if (!this.handlerActive) {
                this.handlerActive = true;
                if (!sendMessage(obtainMessage())) {
                    throw new EventBusException((String) "Could not send handler message");
                }
            }
        }
    }

    public final void handleMessage(Message message) {
        try {
            long uptimeMillis = SystemClock.uptimeMillis();
            do {
                PendingPost poll = this.queue.poll();
                if (poll == null) {
                    synchronized (this) {
                        poll = this.queue.poll();
                        if (poll == null) {
                            this.handlerActive = false;
                            this.handlerActive = false;
                            return;
                        }
                    }
                }
                this.eventBus.invokeSubscriber(poll);
            } while (SystemClock.uptimeMillis() - uptimeMillis < ((long) this.maxMillisInsideHandleMessage));
            if (!sendMessage(obtainMessage())) {
                throw new EventBusException((String) "Could not send handler message");
            }
            this.handlerActive = true;
        } catch (Throwable th) {
            this.handlerActive = false;
            throw th;
        }
    }
}
