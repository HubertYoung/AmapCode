package com.alipay.mobile.nebula.view;

import android.os.Handler;
import android.os.Message;
import com.alipay.mobile.nebula.util.H5Log;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ManagerToastLikeDialog extends Handler {
    private static final String TAG = "ManagerToastLikeDialog";
    private static ManagerToastLikeDialog mManagerDialog;
    private final Queue<H5ToastLikeDialog> mQueue = new LinkedBlockingQueue();

    private static final class Messages {
        private static final int ADD_DIALOG = 4608852;
        private static final int DISPLAY_DIALOG = 4543316;
        private static final int REMOVE_DIALOG = 4674388;

        private Messages() {
        }
    }

    private ManagerToastLikeDialog() {
    }

    public static synchronized ManagerToastLikeDialog getInstance() {
        ManagerToastLikeDialog managerToastLikeDialog;
        synchronized (ManagerToastLikeDialog.class) {
            try {
                if (mManagerDialog != null) {
                    managerToastLikeDialog = mManagerDialog;
                } else {
                    managerToastLikeDialog = new ManagerToastLikeDialog();
                    mManagerDialog = managerToastLikeDialog;
                }
            }
        }
        return managerToastLikeDialog;
    }

    /* access modifiers changed from: protected */
    public void add(H5ToastLikeDialog dialog) {
        this.mQueue.add(dialog);
        showNextSuperToast();
    }

    private void showNextSuperToast() {
        if (!this.mQueue.isEmpty()) {
            H5ToastLikeDialog h5ToastLikeDialog = this.mQueue.peek();
            if (!h5ToastLikeDialog.isShowing()) {
                Message message = obtainMessage(4608852);
                message.obj = h5ToastLikeDialog;
                sendMessage(message);
                return;
            }
            sendMessageDelayed(h5ToastLikeDialog, 4543316, getDuration(h5ToastLikeDialog));
        }
    }

    private void sendMessageDelayed(H5ToastLikeDialog h5ToastLikeDialog, int messageId, long delay) {
        Message message = obtainMessage(messageId);
        message.obj = h5ToastLikeDialog;
        sendMessageDelayed(message, delay);
    }

    private long getDuration(H5ToastLikeDialog toastLikeDialog) {
        return (long) toastLikeDialog.getDuration();
    }

    public void handleMessage(Message message) {
        H5ToastLikeDialog h5ToastLikeDialog = (H5ToastLikeDialog) message.obj;
        switch (message.what) {
            case 4543316:
                showNextSuperToast();
                return;
            case 4608852:
                displayToastLikeDialog(h5ToastLikeDialog);
                return;
            case 4674388:
                removeToastLikeDialog(h5ToastLikeDialog);
                return;
            default:
                super.handleMessage(message);
                return;
        }
    }

    private void displayToastLikeDialog(H5ToastLikeDialog h5ToastLikeDialog) {
        if (!h5ToastLikeDialog.isShowing()) {
            try {
                h5ToastLikeDialog.show();
                sendMessageDelayed(h5ToastLikeDialog, 4674388, (long) h5ToastLikeDialog.getDuration());
            } catch (Throwable tr) {
                H5Log.e((String) TAG, tr.getMessage());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeToastLikeDialog(H5ToastLikeDialog h5ToastLikeDialog) {
        h5ToastLikeDialog.dismiss();
    }

    /* access modifiers changed from: protected */
    public void confirmRemoveDialog(H5ToastLikeDialog h5ToastLikeDialog) {
        if (this.mQueue.peek() == h5ToastLikeDialog) {
            H5Log.d(TAG, "remove dialog");
            this.mQueue.poll();
            sendMessageDelayed(h5ToastLikeDialog, 4543316, 500);
        }
    }

    public static boolean hasManagerInstance() {
        return mManagerDialog != null;
    }

    public void cancelAllToastLikeDialogs() {
        removeMessages(4608852);
        removeMessages(4543316);
        removeMessages(4674388);
        for (H5ToastLikeDialog h5ToastLikeDialog : this.mQueue) {
            if (h5ToastLikeDialog.isShowing()) {
                h5ToastLikeDialog.dismiss();
            }
        }
        this.mQueue.clear();
    }
}
