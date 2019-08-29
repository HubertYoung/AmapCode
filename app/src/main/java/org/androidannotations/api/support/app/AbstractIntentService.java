package org.androidannotations.api.support.app;

import android.app.IntentService;
import android.content.Intent;

public abstract class AbstractIntentService extends IntentService {
    public AbstractIntentService(String name) {
        super(name);
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
    }
}
