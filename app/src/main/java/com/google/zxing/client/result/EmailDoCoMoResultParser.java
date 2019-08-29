package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Pattern;

public final class EmailDoCoMoResultParser extends AbstractDoCoMoResultParser {
    private static final Pattern ATEXT_ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");

    public final EmailAddressParsedResult parse(Result result) {
        String massagedText = getMassagedText(result);
        if (!massagedText.startsWith("MATMSG:")) {
            return null;
        }
        String[] matchDoCoMoPrefixedField = matchDoCoMoPrefixedField("TO:", massagedText, true);
        if (matchDoCoMoPrefixedField == null) {
            return null;
        }
        for (String isBasicallyValidEmailAddress : matchDoCoMoPrefixedField) {
            if (!isBasicallyValidEmailAddress(isBasicallyValidEmailAddress)) {
                return null;
            }
        }
        EmailAddressParsedResult emailAddressParsedResult = new EmailAddressParsedResult(matchDoCoMoPrefixedField, null, null, matchSingleDoCoMoPrefixedField("SUB:", massagedText, false), matchSingleDoCoMoPrefixedField("BODY:", massagedText, false));
        return emailAddressParsedResult;
    }

    static boolean isBasicallyValidEmailAddress(String str) {
        return str != null && ATEXT_ALPHANUMERIC.matcher(str).matches() && str.indexOf(64) >= 0;
    }
}
