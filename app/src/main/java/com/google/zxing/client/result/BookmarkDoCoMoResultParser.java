package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class BookmarkDoCoMoResultParser extends AbstractDoCoMoResultParser {
    public final URIParsedResult parse(Result result) {
        String text = result.getText();
        URIParsedResult uRIParsedResult = null;
        if (!text.startsWith("MEBKM:")) {
            return null;
        }
        String matchSingleDoCoMoPrefixedField = matchSingleDoCoMoPrefixedField("TITLE:", text, true);
        String[] matchDoCoMoPrefixedField = matchDoCoMoPrefixedField("URL:", text, true);
        if (matchDoCoMoPrefixedField == null) {
            return null;
        }
        String str = matchDoCoMoPrefixedField[0];
        if (URIResultParser.isBasicallyValidURI(str)) {
            uRIParsedResult = new URIParsedResult(str, matchSingleDoCoMoPrefixedField);
        }
        return uRIParsedResult;
    }
}
