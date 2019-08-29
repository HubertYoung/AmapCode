package com.google.zxing.client.result;

import com.autonavi.link.protocol.http.MultipartUtility;
import com.google.zxing.Result;
import java.util.ArrayList;
import java.util.List;

public final class AddressBookAUResultParser extends ResultParser {
    public final AddressBookParsedResult parse(Result result) {
        String massagedText = getMassagedText(result);
        String[] strArr = null;
        if (!massagedText.contains("MEMORY") || !massagedText.contains(MultipartUtility.LINE_FEED)) {
            return null;
        }
        String matchSinglePrefixedField = matchSinglePrefixedField("NAME1:", massagedText, 13, true);
        String matchSinglePrefixedField2 = matchSinglePrefixedField("NAME2:", massagedText, 13, true);
        String[] matchMultipleValuePrefix = matchMultipleValuePrefix("TEL", 3, massagedText, true);
        String[] matchMultipleValuePrefix2 = matchMultipleValuePrefix("MAIL", 3, massagedText, true);
        String matchSinglePrefixedField3 = matchSinglePrefixedField("MEMORY:", massagedText, 13, false);
        String matchSinglePrefixedField4 = matchSinglePrefixedField("ADD:", massagedText, 13, true);
        if (matchSinglePrefixedField4 != null) {
            strArr = new String[]{matchSinglePrefixedField4};
        }
        AddressBookParsedResult addressBookParsedResult = new AddressBookParsedResult(maybeWrap(matchSinglePrefixedField), null, matchSinglePrefixedField2, matchMultipleValuePrefix, null, matchMultipleValuePrefix2, null, null, matchSinglePrefixedField3, strArr, null, null, null, null, null, null);
        return addressBookParsedResult;
    }

    private static String[] matchMultipleValuePrefix(String str, int i, String str2, boolean z) {
        List list = null;
        for (int i2 = 1; i2 <= i; i2++) {
            StringBuilder sb = new StringBuilder(String.valueOf(str));
            sb.append(i2);
            sb.append(':');
            String matchSinglePrefixedField = matchSinglePrefixedField(sb.toString(), str2, 13, z);
            if (matchSinglePrefixedField == null) {
                break;
            }
            if (list == null) {
                list = new ArrayList(i);
            }
            list.add(matchSinglePrefixedField);
        }
        if (list == null) {
            return null;
        }
        return (String[]) list.toArray(new String[list.size()]);
    }
}
