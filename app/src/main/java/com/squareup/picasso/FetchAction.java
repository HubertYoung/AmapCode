package com.squareup.picasso;

import android.graphics.Bitmap;
import com.squareup.picasso.Picasso.LoadedFrom;

class FetchAction extends Action<Object> {
    private Callback callback;
    private final Object target = new Object();

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    FetchAction(Picasso picasso, Request request, int i, int i2, Object obj, String str, Callback callback2) {
        super(picasso, null, request, i, i2, 0, null, str, obj, false);
        this.callback = callback2;
    }

    /* access modifiers changed from: 0000 */
    public void complete(Bitmap bitmap, LoadedFrom loadedFrom) {
        if (this.callback != null) {
            this.callback.onSuccess();
        }
    }

    /* access modifiers changed from: 0000 */
    public void error() {
        if (this.callback != null) {
            this.callback.onError();
        }
    }

    /* access modifiers changed from: 0000 */
    public void cancel() {
        super.cancel();
        this.callback = null;
    }

    /* access modifiers changed from: 0000 */
    public Object getTarget() {
        return this.target;
    }
}
