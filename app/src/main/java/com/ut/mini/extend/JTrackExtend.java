package com.ut.mini.extend;

import android.app.Activity;
import android.net.Uri;
import com.taobao.ju.track.JTrack.Page;
import java.util.Map;

public class JTrackExtend {
    public static String getPageName(String str) {
        return Page.getPageName(str);
    }

    public static Map<String, String> getArgsMap(String str, Uri uri) {
        return Page.getArgsMap(str, uri);
    }

    public static Map<String, String> getArgsMap(Activity activity, Uri uri) {
        return Page.getArgsMap(activity, uri);
    }
}
