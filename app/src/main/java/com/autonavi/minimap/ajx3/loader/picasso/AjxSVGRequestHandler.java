package com.autonavi.minimap.ajx3.loader.picasso;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.minimap.ajx3.loader.action.AjxResourceLoadAction;
import com.autonavi.minimap.ajx3.loader.picasso.Picasso.LoadedFrom;
import com.autonavi.minimap.ajx3.loader.picasso.RequestHandler.Result;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;

public class AjxSVGRequestHandler extends RequestHandler {
    public boolean canHandleRequest(Request request) {
        return request.isSVG;
    }

    public Result load(Request request, int i) {
        Bitmap bitmap;
        String queryParameter = request.uri.getQueryParameter("data");
        int parseInt = Integer.parseInt(request.uri.getQueryParameter("width"));
        int parseInt2 = Integer.parseInt(request.uri.getQueryParameter("height"));
        int parseInt3 = Integer.parseInt(request.uri.getQueryParameter("color"));
        int argb = Color.argb(Color.red(parseInt3), Color.green(parseInt3), Color.blue(parseInt3), Color.alpha(parseInt3));
        if (TextUtils.isEmpty(queryParameter)) {
            bitmap = AjxFileInfo.getSVGByteByPath(AjxResourceLoadAction.loadRealPathWithUri(request.uri), AjxResourceLoadAction.getPatchIndexFromUri(request.uri), parseInt, parseInt2, argb);
        } else {
            bitmap = AjxFileInfo.getSVGByteByData(Uri.decode(queryParameter), parseInt, parseInt2, argb);
        }
        if (bitmap != null) {
            return new Result(bitmap, LoadedFrom.DISK);
        }
        return null;
    }
}
