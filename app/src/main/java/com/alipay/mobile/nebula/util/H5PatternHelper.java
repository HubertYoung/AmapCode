package com.alipay.mobile.nebula.util;

import android.support.annotation.Nullable;
import android.util.LruCache;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class H5PatternHelper {
    private static final boolean DEBUG = H5Utils.isDebuggable(H5Utils.getContext());
    private static final String TAG = "H5PatternHelper";
    private static LruCache<String, Pattern> sPatternCache = new LruCache<>(20);

    public static boolean matchRegex(String regex, String input) {
        if (regex == null || input == null) {
            return false;
        }
        Pattern pattern = compile(regex);
        if (pattern != null) {
            return pattern.matcher(input).matches();
        }
        return false;
    }

    @Nullable
    public static Pattern compile(String patternStr) {
        if (patternStr == null) {
            return null;
        }
        Pattern pattern = sPatternCache.get(patternStr);
        if (pattern == null) {
            try {
                long start = System.currentTimeMillis();
                Pattern pattern2 = Pattern.compile(patternStr);
                sPatternCache.put(patternStr, pattern2);
                if (!DEBUG) {
                    return pattern2;
                }
                H5Log.d(TAG, "pattern cache miss, use time: " + (System.currentTimeMillis() - start) + Token.SEPARATOR + patternStr);
                return pattern2;
            } catch (PatternSyntaxException e) {
                H5Log.e(TAG, "pattern " + patternStr + " compile error!", e);
                return null;
            }
        } else if (!DEBUG) {
            return pattern;
        } else {
            H5Log.d(TAG, "pattern cache hit: " + patternStr);
            return pattern;
        }
    }
}
