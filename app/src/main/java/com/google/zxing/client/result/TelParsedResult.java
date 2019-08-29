package com.google.zxing.client.result;

public final class TelParsedResult extends ParsedResult {
    private final String number;
    private final String telURI;
    private final String title;

    public TelParsedResult(String str, String str2, String str3) {
        super(ParsedResultType.TEL);
        this.number = str;
        this.telURI = str2;
        this.title = str3;
    }

    public final String getNumber() {
        return this.number;
    }

    public final String getTelURI() {
        return this.telURI;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getDisplayResult() {
        StringBuilder sb = new StringBuilder(20);
        maybeAppend(this.number, sb);
        maybeAppend(this.title, sb);
        return sb.toString();
    }
}
