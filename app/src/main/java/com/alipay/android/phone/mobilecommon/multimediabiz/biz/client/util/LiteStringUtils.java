package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LiteStringUtils {
    public static final String EMPTY = "";
    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final int INDEX_NOT_FOUND = -1;

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String clean(String str) {
        return str == null ? "" : str.trim();
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    public static String trimToNull(String str) {
        String ts = trim(str);
        if (isEmpty(ts)) {
            return null;
        }
        return ts;
    }

    public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    public static String strip(String str) {
        return strip(str, null);
    }

    public static String stripToNull(String str) {
        if (str == null) {
            return null;
        }
        String str2 = strip(str, null);
        if (str2.length() == 0) {
            return null;
        }
        return str2;
    }

    public static String stripToEmpty(String str) {
        return str == null ? "" : strip(str, null);
    }

    public static String strip(String str, String stripChars) {
        return isEmpty(str) ? str : stripEnd(stripStart(str, stripChars), stripChars);
    }

    public static String stripStart(String str, String stripChars) {
        if (str == null) {
            return str;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while (start != strLen && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
                start++;
            }
        }
        return str.substring(start);
    }

    public static String stripEnd(String str, String stripChars) {
        if (str == null) {
            return str;
        }
        int end = str.length();
        if (end == 0) {
            return str;
        }
        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    public static String[] stripAll(String[] strs) {
        return stripAll(strs, null);
    }

    public static String[] stripAll(String[] strs, String stripChars) {
        if (strs != null) {
            int strsLen = strs.length;
            if (strsLen != 0) {
                String[] newArr = new String[strsLen];
                for (int i = 0; i < strsLen; i++) {
                    newArr[i] = strip(strs[i], stripChars);
                }
                return newArr;
            }
        }
        return strs;
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }
        return str1.equalsIgnoreCase(str2);
    }

    public static int indexOf(String str, char searchChar) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(searchChar);
    }

    public static int indexOf(String str, char searchChar, int startPos) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.indexOf(searchChar, startPos);
    }

    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }

    public static int indexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        if (searchStr.length() != 0 || startPos < str.length()) {
            return str.indexOf(searchStr, startPos);
        }
        return str.length();
    }

    public static int lastIndexOf(String str, char searchChar) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(searchChar);
    }

    public static int lastIndexOf(String str, char searchChar, int startPos) {
        if (isEmpty(str)) {
            return -1;
        }
        return str.lastIndexOf(searchChar, startPos);
    }

    public static int lastIndexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr);
    }

    public static int lastIndexOf(String str, String searchStr, int startPos) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.lastIndexOf(searchStr, startPos);
    }

    public static boolean contains(String str, char searchChar) {
        if (!isEmpty(str) && str.indexOf(searchChar) >= 0) {
            return true;
        }
        return false;
    }

    public static boolean contains(String str, String searchStr) {
        if (str == null || searchStr == null || str.indexOf(searchStr) < 0) {
            return false;
        }
        return true;
    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return contains(str.toUpperCase(Locale.getDefault()), searchStr.toUpperCase());
    }

    public static boolean containsAny(String str, char[] searchChars) {
        if (str == null || str.length() == 0 || searchChars == null || searchChars.length == 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            for (char c : searchChars) {
                if (c == ch) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsAny(String str, String searchChars) {
        if (searchChars == null) {
            return false;
        }
        return containsAny(str, searchChars.toCharArray());
    }

    public static boolean containsNone(String str, char[] invalidChars) {
        if (str == null || invalidChars == null) {
            return true;
        }
        int strSize = str.length();
        for (int i = 0; i < strSize; i++) {
            char ch = str.charAt(i);
            for (char c : invalidChars) {
                if (c == ch) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }
        if (start < 0) {
            start += str.length();
        }
        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return "";
        }
        return str.substring(start);
    }

    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }
        if (end < 0) {
            end += str.length();
        }
        if (start < 0) {
            start += str.length();
        }
        if (end > str.length()) {
            end = str.length();
        }
        if (start > end) {
            return "";
        }
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }
        return str.substring(start, end);
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        return str.length() > len ? str.substring(0, len) : str;
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return "";
        }
        return str.length() > len ? str.substring(str.length() - len) : str;
    }

    public static String[] split(String str) {
        return split(str, null, -1);
    }

    public static String[] split(String str, char separatorChar) {
        return a(str, separatorChar);
    }

    public static String[] split(String str, String separatorChars) {
        return a(str, separatorChars, -1);
    }

    public static String[] split(String str, String separatorChars, int max) {
        return a(str, separatorChars, max);
    }

    private static String[] a(String str, char separatorChar) {
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return EMPTY_STRING_ARRAY;
        }
        List list = new ArrayList();
        int i = 0;
        int start = 0;
        boolean match = false;
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }
                i++;
                start = i;
            } else {
                match = true;
                i++;
            }
        }
        if (match) {
            list.add(str.substring(start, i));
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    private static String[] a(String str, String separatorChars, int max) {
        int sizePlus1;
        int i;
        int sizePlus12;
        int i2;
        int sizePlus13;
        int sizePlus14;
        if (str == null) {
            return null;
        }
        int len = str.length();
        if (len == 0) {
            return EMPTY_STRING_ARRAY;
        }
        List list = new ArrayList();
        int i3 = 0;
        int start = 0;
        boolean match = false;
        if (separatorChars == null) {
            sizePlus1 = 1;
            while (i3 < len) {
                if (Character.isWhitespace(str.charAt(i3))) {
                    if (match) {
                        sizePlus14 = sizePlus1 + 1;
                        if (sizePlus1 == max) {
                            i3 = len;
                        }
                        list.add(str.substring(start, i3));
                        match = false;
                    } else {
                        sizePlus14 = sizePlus1;
                    }
                    i3++;
                    start = i3;
                    sizePlus1 = sizePlus14;
                } else {
                    match = true;
                    i3++;
                }
            }
        } else if (separatorChars.length() == 1) {
            char sep = separatorChars.charAt(0);
            int sizePlus15 = 1;
            while (i3 < len) {
                if (str.charAt(i3) == sep) {
                    if (match) {
                        sizePlus13 = sizePlus15 + 1;
                        if (sizePlus15 == max) {
                            i3 = len;
                        }
                        list.add(str.substring(start, i3));
                        match = false;
                    } else {
                        sizePlus13 = sizePlus15;
                    }
                    i2 = i3 + 1;
                    start = i2;
                    sizePlus15 = sizePlus13;
                } else {
                    match = true;
                    i2 = i3 + 1;
                }
            }
            int i4 = sizePlus15;
        } else {
            sizePlus1 = 1;
            while (i3 < len) {
                if (separatorChars.indexOf(str.charAt(i3)) >= 0) {
                    if (match) {
                        sizePlus12 = sizePlus1 + 1;
                        if (sizePlus1 == max) {
                            i3 = len;
                        }
                        list.add(str.substring(start, i3));
                        match = false;
                    } else {
                        sizePlus12 = sizePlus1;
                    }
                    i = i3 + 1;
                    start = i;
                    sizePlus1 = sizePlus12;
                } else {
                    match = true;
                    i = i3 + 1;
                }
            }
        }
        if (match) {
            list.add(str.substring(start, i3));
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public static String concatenate(Object[] array) {
        return join(array, (String) null);
    }

    public static String join(Object[] array) {
        return join(array, (String) null);
    }

    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, char separator, int startIndex, int endIndex) {
        int length;
        if (array == null) {
            return null;
        }
        int bufSize = endIndex - startIndex;
        if (bufSize <= 0) {
            return "";
        }
        if (array[startIndex] == null) {
            length = 16;
        } else {
            length = array[startIndex].toString().length();
        }
        StringBuffer buf = new StringBuffer(bufSize * (length + 1));
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        int length;
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }
        int bufSize = endIndex - startIndex;
        if (bufSize <= 0) {
            return "";
        }
        if (array[startIndex] == null) {
            length = 16;
        } else {
            length = array[startIndex].toString().length();
        }
        StringBuffer buf = new StringBuffer(bufSize * (length + separator.length()));
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    public static String remove(String str, String remove) {
        return (isEmpty(str) || isEmpty(remove)) ? str : replace(str, remove, "", -1);
    }

    public static String remove(String str, char remove) {
        if (isEmpty(str) || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos] = chars[i];
                pos++;
            }
        }
        return new String(chars, 0, pos);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        int i = 64;
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, 0);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        if (increase < 0) {
            increase = 0;
        }
        if (max < 0) {
            i = 16;
        } else if (max <= 64) {
            i = max;
        }
        StringBuffer buf = new StringBuffer(text.length() + (increase * i));
        while (end != -1) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            max--;
            if (max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    private static String a(int repeat, char padChar) {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public static String rightPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (pads > 8192) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(a(pads, padChar));
    }

    public static String rightPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = Token.SEPARATOR;
        }
        int padLen = padStr.length();
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (padLen == 1 && pads <= 8192) {
            return rightPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return str.concat(padStr);
        }
        if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[i % padLen];
        }
        return str.concat(new String(padding));
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (pads > 8192) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return a(pads, padChar).concat(str);
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = Token.SEPARATOR;
        }
        int padLen = padStr.length();
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        if (padLen == 1 && pads <= 8192) {
            return leftPad(str, size, padStr.charAt(0));
        }
        if (pads == padLen) {
            return padStr.concat(str);
        }
        if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        }
        char[] padding = new char[pads];
        char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[i % padLen];
        }
        return new String(padding).concat(str);
    }

    public static int length(String str) {
        if (str == null) {
            return 0;
        }
        return str.length();
    }

    public static String capitalize(String str) {
        if (str == null) {
            return str;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return str;
        }
        return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String capitalise(String str) {
        return capitalize(str);
    }

    public static String uncapitalize(String str) {
        if (str == null) {
            return str;
        }
        int strLen = str.length();
        if (strLen == 0) {
            return str;
        }
        return new StringBuffer(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }

    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }

    public static String abbreviate(String str, int offset, int maxWidth) {
        if (str == null) {
            return null;
        }
        if (maxWidth < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        } else if (str.length() <= maxWidth) {
            return str;
        } else {
            if (offset > str.length()) {
                offset = str.length();
            }
            if (str.length() - offset < maxWidth - 3) {
                offset = str.length() - (maxWidth - 3);
            }
            if (offset <= 4) {
                return str.substring(0, maxWidth - 3) + "...";
            }
            if (maxWidth < 7) {
                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
            } else if ((maxWidth - 3) + offset < str.length()) {
                return "..." + abbreviate(str.substring(offset), maxWidth - 3);
            } else {
                return "..." + str.substring(str.length() - (maxWidth - 3));
            }
        }
    }

    public static boolean startsWith(String str, String prefix) {
        return a(str, prefix, false);
    }

    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return a(str, prefix, true);
    }

    private static boolean a(String str, String prefix, boolean ignoreCase) {
        if (str == null || prefix == null) {
            if (str == null && prefix == null) {
                return true;
            }
            return false;
        } else if (prefix.length() > str.length()) {
            return false;
        } else {
            return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
        }
    }

    public static boolean endsWith(String str, String suffix) {
        return b(str, suffix, false);
    }

    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return b(str, suffix, true);
    }

    private static boolean b(String str, String suffix, boolean ignoreCase) {
        if (str == null || suffix == null) {
            if (str == null && suffix == null) {
                return true;
            }
            return false;
        } else if (suffix.length() > str.length()) {
            return false;
        } else {
            return str.regionMatches(ignoreCase, str.length() - suffix.length(), suffix, 0, suffix.length());
        }
    }
}
