package com.alipay.mobile.common.task.transaction;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.ArrayDeque;
import java.util.Iterator;

public class TransactionExecutor {
    static final String TAG = "TransactionExecutor";
    volatile Transaction mActive;
    final ArrayDeque<Transaction> mTransactions = new ArrayDeque<>();

    public TransactionExecutor() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public String addTransaction(Transaction transaction) {
        synchronized (this.mTransactions) {
            try {
                this.mTransactions.offer(transaction);
            }
        }
        if (this.mActive == null) {
            a();
        } else {
            com.alipay.mobile.common.task.Log.v(TAG, "TransactionExecutor.execute(a transaction is running, so don't call scheduleNext())");
        }
        return transaction.id;
    }

    public void removeTransaction(String id) {
        Iterator<Transaction> it = this.mTransactions.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Transaction transaction = it.next();
            if (transaction.id.equals(id)) {
                synchronized (this.mTransactions) {
                    this.mTransactions.remove(transaction);
                    break;
                }
            }
        }
        if (this.mActive != null && this.mActive.id.equals(id)) {
            this.mActive = null;
        }
        if (this.mActive == null) {
            a();
        } else {
            com.alipay.mobile.common.task.Log.v(TAG, "TransactionExecutor.execute(a transaction is running, so don't call scheduleNext())");
        }
    }

    private void a() {
        Transaction transaction;
        synchronized (this.mTransactions) {
            this.mActive = this.mTransactions.poll();
            transaction = this.mActive;
        }
        if (this.mActive != null) {
            com.alipay.mobile.common.task.Log.d(TAG, "TransactionExecutor.scheduleNext()");
            transaction.run();
            return;
        }
        com.alipay.mobile.common.task.Log.d(TAG, "TransactionExecutor.scheduleNext(mTransactions is empty)");
    }

    public void shutdown() {
        this.mTransactions.clear();
    }
}
