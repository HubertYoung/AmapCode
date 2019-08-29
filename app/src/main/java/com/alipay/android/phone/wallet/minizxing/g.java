package com.alipay.android.phone.wallet.minizxing;

import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class g extends n {
    public final BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        if (format == BarcodeFormat.CODE_128) {
            return super.encode(contents, format, width, height, hints);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got " + format);
    }

    public final boolean[] a(String contents) {
        int newCodeSet;
        int patternIndex;
        int patternIndex2;
        int length = contents.length();
        if (length <= 0 || length > 80) {
            throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
        }
        for (int i = 0; i < length; i++) {
            char c = contents.charAt(i);
            if (c < ' ' || c > '~') {
                switch (c) {
                    case FavoritesPointFragment.REQUEST_HOME /*241*/:
                    case FavoritesPointFragment.REQUEST_COMPNAY /*242*/:
                    case FavoritesPointFragment.REQUEST_EDIT_POINT /*243*/:
                    case 244:
                        break;
                    default:
                        throw new IllegalArgumentException("Bad character in input: " + c);
                }
            }
        }
        Collection<int[]> patterns = new ArrayList<>();
        int checkSum = 0;
        int checkWeight = 1;
        int codeSet = 0;
        int position = 0;
        while (position < length) {
            if (a(contents, position, codeSet == 99 ? 2 : 4)) {
                newCodeSet = 99;
            } else {
                newCodeSet = 100;
            }
            if (newCodeSet == codeSet) {
                switch (contents.charAt(position)) {
                    case FavoritesPointFragment.REQUEST_HOME /*241*/:
                        patternIndex2 = 102;
                        break;
                    case FavoritesPointFragment.REQUEST_COMPNAY /*242*/:
                        patternIndex2 = 97;
                        break;
                    case FavoritesPointFragment.REQUEST_EDIT_POINT /*243*/:
                        patternIndex2 = 96;
                        break;
                    case 244:
                        patternIndex2 = 100;
                        break;
                    default:
                        if (codeSet != 100) {
                            patternIndex2 = Integer.parseInt(contents.substring(position, position + 2));
                            position++;
                            break;
                        } else {
                            patternIndex2 = contents.charAt(position) - ' ';
                            break;
                        }
                }
                position++;
            } else {
                if (codeSet != 0) {
                    patternIndex = newCodeSet;
                } else if (newCodeSet == 100) {
                    patternIndex = 104;
                } else {
                    patternIndex = 105;
                }
                codeSet = newCodeSet;
            }
            patterns.add(f.a[patternIndex2]);
            checkSum += patternIndex2 * checkWeight;
            if (position != 0) {
                checkWeight++;
            }
        }
        patterns.add(f.a[checkSum % 103]);
        patterns.add(f.a[106]);
        int codeWidth = 0;
        for (int[] iArr : patterns) {
            for (int i2 = 0; i2 < iArr.length; i2++) {
                codeWidth += iArr[i2];
            }
        }
        boolean[] result = new boolean[codeWidth];
        int pos = 0;
        for (int[] pattern : patterns) {
            pos += a(result, pos, pattern);
        }
        return result;
    }

    private static boolean a(CharSequence value, int start, int length) {
        int end = start + length;
        int last = value.length();
        int i = start;
        while (i < end && i < last) {
            char c = value.charAt(i);
            if (c < '0' || c > '9') {
                if (c != 241) {
                    return false;
                }
                end++;
            }
            i++;
        }
        if (end <= last) {
            return true;
        }
        return false;
    }
}
