package com.google.zxing.client.result;

public final class ISBNParsedResult extends ParsedResult {
    private final String isbn;

    ISBNParsedResult(String str) {
        super(ParsedResultType.ISBN);
        this.isbn = str;
    }

    public final String getISBN() {
        return this.isbn;
    }

    public final String getDisplayResult() {
        return this.isbn;
    }
}
