package com.ali.auth.third.core.task;

import android.app.Activity;

public abstract class TaskWithDialog<Params, Progress, Result> extends AbsAsyncTask<Params, Progress, Result> {
    protected Activity activity;

    public TaskWithDialog(Activity activity2) {
        this.activity = activity2;
    }

    /* access modifiers changed from: protected */
    public void doFinally() {
    }

    /* access modifiers changed from: protected */
    public void onPreExecute() {
    }
}
