package com.squareup.picasso;

import android.graphics.Bitmap;
import com.squareup.picasso.Picasso.LoadedFrom;

class GetAction extends Action<Void> {
    /* access modifiers changed from: 0000 */
    public void complete(Bitmap bitmap, LoadedFrom loadedFrom) {
    }

    public void error() {
    }

    GetAction(Picasso picasso, Request request, int i, int i2, Object obj, String str) {
        super(picasso, null, request, i, i2, 0, null, str, obj, false);
    }
}
