package com.sina.weibo.sdk.network.base;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;

public class UriUtils {
    public static Uri buildCompleteUri(Uri uri, Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return uri;
        }
        Builder buildUpon = uri.buildUpon();
        for (String str : bundle.keySet()) {
            buildUpon.appendQueryParameter(str, String.valueOf(bundle.get(str)));
        }
        return buildUpon.build();
    }

    public static String buildCompleteUri(String str, Bundle bundle) {
        Uri buildCompleteUri = buildCompleteUri(Uri.parse(str), bundle);
        if (buildCompleteUri != null) {
            return buildCompleteUri.toString();
        }
        return null;
    }
}
