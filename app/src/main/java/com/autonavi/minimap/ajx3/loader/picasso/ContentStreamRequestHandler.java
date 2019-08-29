package com.autonavi.minimap.ajx3.loader.picasso;

import android.content.Context;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.RequestHandler.Result;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

class ContentStreamRequestHandler extends RequestHandler {
    final Context context;

    ContentStreamRequestHandler(Context context2) {
        this.context = context2;
    }

    public boolean canHandleRequest(Request request) {
        return "content".equals(request.uri.getScheme());
    }

    public Result load(Request request, int i) throws IOException {
        return new Result(getInputStream(request), LoadedFrom.DISK);
    }

    /* access modifiers changed from: 0000 */
    public InputStream getInputStream(Request request) throws FileNotFoundException {
        return this.context.getContentResolver().openInputStream(request.uri);
    }
}
