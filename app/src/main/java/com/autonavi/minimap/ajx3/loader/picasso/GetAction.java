package com.autonavi.minimap.ajx3.loader.picasso;

import com.autonavi.minimap.ajx3.image.ImageCache.Image;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;

class GetAction extends Action<Void> {
    /* access modifiers changed from: 0000 */
    public void complete(Image image, LoadedFrom loadedFrom) {
    }

    public void error() {
    }

    GetAction(Picasso picasso, Request request, int i, int i2, Object obj, String str, boolean z, boolean z2) {
        super(picasso, null, request, i, i2, 0, null, str, obj, false, z, z2);
    }
}
