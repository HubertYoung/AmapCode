package com.autonavi.minimap.ajx3.loader.picasso;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.RequestHandler.Result;
import com.autonavi.minimap.ajx3.util.PathUtils;
import java.io.IOException;
import pl.droidsonroids.gif.GifDrawable;

class ResourceRequestHandler extends RequestHandler {
    private final Context context;

    ResourceRequestHandler(Context context2) {
        this.context = context2;
    }

    public boolean canHandleRequest(Request request) {
        if (request.resourceId != 0) {
            return true;
        }
        return "android.resource".equals(request.uri.getScheme());
    }

    public Result load(Request request, int i) throws IOException {
        Resources resources = Utils.getResources(this.context, request);
        int resourceId = Utils.getResourceId(resources, request);
        if (PathUtils.isGif(request.uri)) {
            return new Result(new GifDrawable(resources, resourceId), LoadedFrom.DISK);
        }
        return new Result(decodeResource(resources, resourceId, request), LoadedFrom.DISK);
    }

    private static Bitmap decodeResource(Resources resources, int i, Request request) {
        Options createBitmapOptions = createBitmapOptions(request);
        if (requiresInSampleSize(createBitmapOptions)) {
            BitmapFactory.decodeResource(resources, i, createBitmapOptions);
            calculateInSampleSize(request.targetWidth, request.targetHeight, createBitmapOptions, request);
        }
        return BitmapFactory.decodeResource(resources, i, createBitmapOptions);
    }
}
