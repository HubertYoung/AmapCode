package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.Bitmap;
import com.autonavi.minimap.ajx3.loader.action.AjxMemoryLoadAction;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.RequestHandler.Result;
import java.io.IOException;

public class AjxMemoryRequestHandler extends RequestHandler {
    public boolean canHandleRequest(Request request) {
        return AjxMemoryLoadAction.SCHEME_AJX3_MEMORY.equals(request.uri.getScheme());
    }

    public Result load(Request request, int i) throws IOException {
        Bitmap loadBitmapWithUri = AjxMemoryLoadAction.loadBitmapWithUri(request.uri);
        if (loadBitmapWithUri != null) {
            return new Result(loadBitmapWithUri, LoadedFrom.DISK);
        }
        return null;
    }
}
