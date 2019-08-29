package com.autonavi.minimap.ajx3.widget.view.toast;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import java.util.Comparator;

public class ToastLooper extends Handler {
    private static final int MSG_REMOVE = 2;
    private final PriorityQueue<Operator> mQueue;

    public interface Operator {
        boolean display();

        long getDuration();

        int getPriority();

        long getTimestamp();

        boolean hide();

        boolean isShowing();

        void setTimestamp(long j);
    }

    static class SingletonHolder {
        /* access modifiers changed from: private */
        public static final ToastLooper INSTANCE = new ToastLooper();

        private SingletonHolder() {
        }
    }

    public static ToastLooper instance() {
        return SingletonHolder.INSTANCE;
    }

    private ToastLooper() {
        super(Looper.getMainLooper());
        this.mQueue = new PriorityQueue<>((Comparator<? super E>) new Comparator<Operator>() {
            public int compare(Operator operator, Operator operator2) {
                if (operator2.isShowing()) {
                    return 1;
                }
                if (operator.getTimestamp() == operator2.getTimestamp()) {
                    return 0;
                }
                if (operator.getTimestamp() < operator2.getTimestamp()) {
                    return -1;
                }
                return 1;
            }
        });
    }

    public void add(@NonNull Operator operator) {
        notifyNewToast(operator);
    }

    public void clear() {
        removeMessages(2);
        if (!this.mQueue.isEmpty()) {
            remove((Operator) this.mQueue.peek(), false);
        }
        this.mQueue.clear();
    }

    private void notifyNewToast(@NonNull Operator operator) {
        boolean isShowing = isShowing();
        if (operator.getTimestamp() <= 0) {
            operator.setTimestamp(System.currentTimeMillis());
        }
        this.mQueue.add(operator);
        if (!isShowing) {
            showNextToast();
        } else if (this.mQueue.size() == 2) {
            Operator operator2 = (Operator) this.mQueue.peek();
            if (operator.getPriority() >= operator2.getPriority()) {
                sendRemoveMsg(operator2);
            }
        }
    }

    private void showNextToast() {
        while (!this.mQueue.isEmpty()) {
            Operator operator = (Operator) this.mQueue.peek();
            if (operator == null) {
                this.mQueue.poll();
            } else if (this.mQueue.size() <= 1) {
                displayToast(operator);
                return;
            } else if (((Operator) this.mQueue.get(1)).getPriority() >= operator.getPriority()) {
                remove(operator, true);
                return;
            } else {
                displayToast(operator);
                return;
            }
        }
    }

    private boolean isShowing() {
        return this.mQueue.size() > 0;
    }

    private void sendRemoveMsgDelay(Operator operator) {
        removeMessages(2);
        Message obtainMessage = obtainMessage(2);
        obtainMessage.obj = operator;
        sendMessageDelayed(obtainMessage, operator.getDuration());
    }

    private void sendRemoveMsg(Operator operator) {
        removeMessages(2);
        Message obtainMessage = obtainMessage(2);
        obtainMessage.obj = operator;
        sendMessage(obtainMessage);
    }

    private void displayToast(Operator operator) {
        boolean z;
        try {
            z = operator.display();
        } catch (Exception unused) {
            z = false;
        }
        if (z) {
            sendRemoveMsgDelay(operator);
            return;
        }
        this.mQueue.remove(operator);
        showNextToast();
    }

    private void remove(Operator operator, boolean z) {
        this.mQueue.remove(operator);
        try {
            operator.hide();
        } catch (Exception unused) {
        }
        if (z) {
            showNextToast();
        }
    }

    public void handleMessage(Message message) {
        if (message != null && message.what == 2) {
            remove((Operator) message.obj, true);
        }
    }
}
