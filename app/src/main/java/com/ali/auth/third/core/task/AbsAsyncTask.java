package com.ali.auth.third.core.task;

import android.os.AsyncTask;

public abstract class AbsAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    /* access modifiers changed from: protected */
    public abstract Result asyncExecute(Params... paramsArr);

    /* access modifiers changed from: protected */
    public abstract void doFinally();

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public Result doInBackground(Params... paramsArr) {
        try {
            Result asyncExecute = asyncExecute(paramsArr);
            doFinally();
            return asyncExecute;
        } catch (Throwable th) {
            doFinally();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public abstract void doWhenException(Throwable th);
}
