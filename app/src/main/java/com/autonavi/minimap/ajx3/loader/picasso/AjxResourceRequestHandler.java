package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.Bitmap;
import com.autonavi.minimap.ajx3.loader.action.AjxResourceLoadAction;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.RequestHandler.Result;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.io.IOException;
import pl.droidsonroids.gif.GifDrawable;

public class AjxResourceRequestHandler extends RequestHandler {
    public boolean canHandleRequest(Request request) {
        return AjxResourceLoadAction.SCHEME_AJX3_RESOURCE.equals(request.uri.getScheme());
    }

    public Result load(Request request, int i) throws IOException {
        if (PathUtils.isGif(request.uri)) {
            GifDrawable loadAjxGifDrawableWithUri = AjxResourceLoadAction.loadAjxGifDrawableWithUri(request.uri);
            if (loadAjxGifDrawableWithUri != null) {
                return new Result(loadAjxGifDrawableWithUri, LoadedFrom.DISK);
            }
        } else {
            Bitmap loadAjxBitmapWithUri = AjxResourceLoadAction.loadAjxBitmapWithUri(request.uri);
            if (loadAjxBitmapWithUri != null) {
                return new Result(loadAjxBitmapWithUri, LoadedFrom.DISK);
            }
        }
        return null;
    }
}
