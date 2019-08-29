package com.autonavi.minimap.ajx3.loader.picasso;

import android.net.Uri;
import com.autonavi.minimap.ajx3.loader.action.AjxWebLoadAction;
import com.autonavi.minimap.ajx3.loader.picasso.Request.Builder;
import com.autonavi.minimap.ajx3.loader.picasso.RequestHandler.Result;
import java.io.IOException;

public class WebRequestHandler extends NetworkRequestHandler {
    public WebRequestHandler(Downloader downloader, Stats stats, AbstractDiskCache abstractDiskCache) {
        super(downloader, stats, abstractDiskCache, true);
    }

    public boolean canHandleRequest(Request request) {
        return AjxWebLoadAction.SCHEME_AJX3_WEB.equals(request.uri.getScheme());
    }

    public Result load(Request request, int i) throws IOException {
        return super.load(new Builder(request).setUri(Uri.parse(request.uri.getQueryParameter(AjxWebLoadAction.REAL_PATH))).build(), i);
    }
}
