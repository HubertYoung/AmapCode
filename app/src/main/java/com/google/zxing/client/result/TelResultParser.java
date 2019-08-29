package com.google.zxing.client.result;

import com.google.zxing.Result;
import com.uc.webview.export.WebView;

public final class TelResultParser extends ResultParser {
    public final TelParsedResult parse(Result result) {
        String str;
        String massagedText = getMassagedText(result);
        if (!massagedText.startsWith(WebView.SCHEME_TEL) && !massagedText.startsWith("TEL:")) {
            return null;
        }
        if (massagedText.startsWith("TEL:")) {
            StringBuilder sb = new StringBuilder(WebView.SCHEME_TEL);
            sb.append(massagedText.substring(4));
            str = sb.toString();
        } else {
            str = massagedText;
        }
        int indexOf = massagedText.indexOf(63, 4);
        return new TelParsedResult(indexOf < 0 ? massagedText.substring(4) : massagedText.substring(4, indexOf), str, null);
    }
}
