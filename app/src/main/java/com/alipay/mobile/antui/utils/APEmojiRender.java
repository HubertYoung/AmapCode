package com.alipay.mobile.antui.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.text.Spannable;
import android.util.Log;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class APEmojiRender {
    public static final Map<String, Bitmap> resourceCache = new HashMap();
    private static String sEmoiRootCachePath;

    public static void renderEmoji(Context context, Spannable text, int emojiSize) {
        if (text != null) {
            renderEmoji(context, text, emojiSize, 0, text.length(), -1);
        }
    }

    public static void renderEmoji(Context context, Spannable text, int emojiSize, int index, int length, int maxCount) {
        renderEmojiReturncount(context, text, emojiSize, index, length, maxCount);
    }

    public static int renderEmojiReturncount(Context context, Spannable text, int emojiSize) {
        if (text == null) {
            return 0;
        }
        return renderEmojiReturncount(context, text, emojiSize, 0, text.length(), -1);
    }

    public static int renderEmojiReturncount(Context context, Spannable text, int emojiSize, int index, int length, int maxCount) {
        int skip;
        if (text == null) {
            return 0;
        }
        int replaceCount = 0;
        int textLength = text.length();
        int processLength = (length < 0 || length >= textLength - index) ? textLength : index + length;
        if (maxCount > 0) {
            EmojiImageSpan[] emojiSpanArray = (EmojiImageSpan[]) text.getSpans(0, text.length(), EmojiImageSpan.class);
            if (emojiSpanArray != null) {
                if (emojiSpanArray.length >= maxCount) {
                    return emojiSpanArray.length;
                }
                replaceCount = emojiSpanArray.length;
            }
        }
        for (int i = index; i < processLength; i += skip) {
            int pos = 0;
            skip = 0;
            char ch = text.charAt(i);
            if (EmojiUtil.isSoftBankEmoji(ch)) {
                pos = EmojiMap.getsbcodePos(ch);
                skip = pos > 0 ? 1 : 0;
            }
            if (pos == 0) {
                int unicode = Character.codePointAt(text, i);
                skip = Character.charCount(unicode);
                pos = EmojiMap.getUnicode1Pos(unicode);
                if (pos > 0) {
                    int needFollowUnicode = EmojiMap.getFollowUnicode(unicode);
                    if (needFollowUnicode > 0) {
                        if (i + skip < processLength) {
                            int followUnicode = Character.codePointAt(text, i + skip);
                            int followSkip = Character.charCount(followUnicode);
                            if (followUnicode == needFollowUnicode) {
                                skip += followSkip;
                            }
                        }
                        pos = 0;
                    }
                }
            }
            if (pos > 0) {
                try {
                    String filePath = "emoji" + File.separator + "emoji_" + (pos - 1) + ".alipaypng";
                    Bitmap bitmap = resourceCache.get(filePath);
                    File emojiFile = new File(getEmoiCacheFullPath(context) + filePath);
                    if (bitmap == null && emojiFile.exists()) {
                        bitmap = BitmapFactory.decodeFile(emojiFile.getAbsolutePath());
                    }
                    if (bitmap != null) {
                        resourceCache.put(filePath, bitmap);
                        BitmapDrawable drawable = new BitmapDrawable(bitmap);
                        drawable.setBounds(0, 0, emojiSize, emojiSize);
                        text.setSpan(new EmojiImageSpan(drawable), i, i + skip, 33);
                        replaceCount++;
                        if (maxCount > 0 && replaceCount >= maxCount) {
                            return replaceCount;
                        }
                    } else {
                        continue;
                    }
                } catch (Exception e) {
                    Log.d("APEmojiRender", "renderEmojiReturncount Exception = " + e);
                }
            }
        }
        return replaceCount;
    }

    public static final String getEmoiCacheFullPath(Context context) {
        if (sEmoiRootCachePath == null) {
            File file = getCacheDir(context);
            if (file == null) {
                return sEmoiRootCachePath;
            }
            String absolutePath = file.getAbsolutePath();
            sEmoiRootCachePath = absolutePath;
            if (absolutePath != null && !sEmoiRootCachePath.endsWith(File.separator)) {
                sEmoiRootCachePath += File.separator;
            }
        }
        return sEmoiRootCachePath;
    }

    public static File getCacheDir(Context context) {
        File cacheDir;
        if (isCanUseSDCard()) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "/alipay/" + context.getApplicationInfo().packageName + "/emojiFiles/");
        } else {
            cacheDir = context.getCacheDir();
        }
        if (cacheDir != null && !cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

    public static boolean isCanUseSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }
}
