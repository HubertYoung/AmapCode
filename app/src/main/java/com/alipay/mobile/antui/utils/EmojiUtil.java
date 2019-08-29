package com.alipay.mobile.antui.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiUtil {
    private static String emojiRegular = "\\[emoji\\]([\\s\\S]*?)\\[/emoji\\]";

    public static String ubb2utf(String input) {
        if (TextUtils.isEmpty(input)) {
            return input;
        }
        String source = input;
        Matcher matcher = Pattern.compile(emojiRegular).matcher(source);
        while (matcher.find()) {
            source = source.replace(matcher.group(), decodeUnicode(matcher.group(1)));
        }
        return utfScan(source, false);
    }

    @Deprecated
    public static SpannableStringBuilder skipUnKnowEmoji(SpannableStringBuilder builder) {
        return builder;
    }

    private static String decodeUnicode(String dataStr) {
        String charStr;
        try {
            if (!dataStr.contains("\\u")) {
                return dataStr;
            }
            int start = 0;
            StringBuffer buffer = new StringBuffer();
            while (start >= 0) {
                int end = dataStr.indexOf("\\u", start + 2);
                if (end == -1) {
                    charStr = dataStr.substring(start + 2, dataStr.length());
                } else {
                    charStr = dataStr.substring(start + 2, end);
                }
                buffer.append(Character.valueOf((char) Integer.parseInt(charStr, 16)));
                start = end;
            }
            return buffer.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private static String encodeUnicode(String dataStr) {
        String returnStr = "";
        if (dataStr == null) {
            return returnStr;
        }
        for (int i = 0; i < dataStr.length(); i++) {
            returnStr = returnStr + "\\u" + Integer.toHexString(dataStr.charAt(i));
        }
        return returnStr;
    }

    public static String utf2ubb(Context context, String input) {
        return utfScan(input, true);
    }

    public static String utfScan(String input, boolean toUbb) {
        int i;
        if (TextUtils.isEmpty(input)) {
            return input;
        }
        int textLength = input.length();
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        while (i2 < textLength) {
            int pos = 0;
            int skip = 0;
            char ch = input.charAt(i2);
            if (isSoftBankEmoji(ch)) {
                pos = EmojiMap.getsbcodePos(ch);
                if (pos > 0) {
                    skip = 1;
                } else {
                    skip = 0;
                }
            }
            if (pos == 0) {
                int unicode = Character.codePointAt(input, i2);
                skip = Character.charCount(unicode);
                pos = EmojiMap.getUnicode1Pos(unicode);
                if (pos > 0) {
                    int needFollowUnicode = EmojiMap.getFollowUnicode(unicode);
                    if (needFollowUnicode > 0) {
                        if (i2 + skip < textLength) {
                            int followUnicode = Character.codePointAt(input, i2 + skip);
                            int followSkip = Character.charCount(followUnicode);
                            if (followUnicode == needFollowUnicode) {
                                skip += followSkip;
                            }
                        }
                        pos = 0;
                    }
                }
            }
            if (pos <= 0) {
                sb.append(input.substring(i2, i2 + skip < textLength ? i2 + skip : textLength));
            } else if (toUbb) {
                StringBuilder append = sb.append("[emoji]");
                if (i2 + skip < textLength) {
                    i = i2 + skip;
                } else {
                    i = textLength;
                }
                append.append(encodeUnicode(input.substring(i2, i))).append("[/emoji]");
            } else {
                sb.append(input.substring(i2, i2 + skip < textLength ? i2 + skip : textLength));
            }
            i2 += skip;
        }
        return sb.toString();
    }

    public static boolean isSoftBankEmoji(char c) {
        return (c >> 12) == 14;
    }

    public static int getEmojiLength(Context context, String input) {
        if (TextUtils.isEmpty(input)) {
            return 0;
        }
        int textLength = input.length();
        int count = 0;
        int i = 0;
        while (i < textLength) {
            int pos = 0;
            int skip = 0;
            char ch = input.charAt(i);
            if (isSoftBankEmoji(ch)) {
                pos = EmojiMap.getsbcodePos(ch);
                if (pos > 0) {
                    skip = 1;
                } else {
                    skip = 0;
                }
            }
            if (pos == 0) {
                int unicode = Character.codePointAt(input, i);
                skip = Character.charCount(unicode);
                if (EmojiMap.getUnicode1Pos(unicode) > 0) {
                    int needFollowUnicode = EmojiMap.getFollowUnicode(unicode);
                    if (needFollowUnicode > 0 && i + skip < textLength) {
                        int followUnicode = Character.codePointAt(input, i + skip);
                        int followSkip = Character.charCount(followUnicode);
                        if (followUnicode == needFollowUnicode) {
                            skip += followSkip;
                        }
                    }
                }
            }
            count++;
            i += skip;
        }
        return count;
    }

    public static String emojiCut(String sourceString, int length) {
        if (TextUtils.isEmpty(sourceString)) {
            return sourceString;
        }
        int realLength = length;
        int target = length - 4;
        while (true) {
            if (target >= length) {
                break;
            }
            if (target >= 0) {
                int unicode = Character.codePointAt(sourceString, length - 4);
                if (EmojiMap.getUnicode1Pos(unicode) > 0) {
                    realLength = target + Character.charCount(unicode);
                    break;
                }
            }
            target++;
        }
        return sourceString.substring(0, realLength);
    }
}
