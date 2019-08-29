package com.autonavi.minimap.ajx3.loader.picasso;

import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;

class FetchAction extends Action<Object> {
    private Callback callback;
    private final Object target = new Object();

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    FetchAction(Picasso picasso, Request request, int i, int i2, Object obj, String str, Callback callback2, boolean z, boolean z2) {
        super(picasso, null, request, i, i2, 0, null, str, obj, false, z, z2);
        this.callback = callback2;
    }

    /* access modifiers changed from: 0000 */
    public void complete(Image image, LoadedFrom loadedFrom) {
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
