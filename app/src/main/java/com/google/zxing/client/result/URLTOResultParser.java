package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class URLTOResultParser extends ResultParser {
    public final URIParsedResult parse(Result result) {
        String massagedText = getMassagedText(result);
        String str = null;
        if (!massagedText.startsWith("urlto:") && !massagedText.startsWith("URLTO:")) {
            return null;
        }
        int indexOf = massagedText.indexOf(58, 6);
        if (indexOf < 0) {
            return null;
        }
        if (indexOf > 6) {
            str = massagedText.substring(6, indexOf);
        }
        return new URIParsedResult(massagedText.substring(indexOf + 1), str);
    }
}
