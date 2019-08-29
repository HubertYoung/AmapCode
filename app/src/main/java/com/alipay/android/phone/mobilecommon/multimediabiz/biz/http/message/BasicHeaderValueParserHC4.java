package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.message;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HeaderElement;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.HeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

public class BasicHeaderValueParserHC4 implements HeaderValueParser {
    @Deprecated
    public static final BasicHeaderValueParserHC4 DEFAULT = new BasicHeaderValueParserHC4();
    public static final BasicHeaderValueParserHC4 INSTANCE = new BasicHeaderValueParserHC4();
    private static final char[] a = {';', ','};

    public static HeaderElement[] parseElements(String value, HeaderValueParser parser) {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        if (parser == null) {
            parser = INSTANCE;
        }
        return parser.parseElements(buffer, cursor);
    }

    public HeaderElement[] parseElements(CharArrayBuffer buffer, ParserCursor cursor) {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        List elements = new ArrayList();
        while (!cursor.atEnd()) {
            HeaderElement element = parseHeaderElement(buffer, cursor);
            if (element.getName().length() != 0 || element.getValue() != null) {
                elements.add(element);
            }
        }
        return (HeaderElement[]) elements.toArray(new HeaderElement[elements.size()]);
    }

    public static HeaderElement parseHeaderElement(String value, HeaderValueParser parser) {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        if (parser == null) {
            parser = INSTANCE;
        }
        return parser.parseHeaderElement(buffer, cursor);
    }

    public HeaderElement parseHeaderElement(CharArrayBuffer buffer, ParserCursor cursor) {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        NameValuePair nvp = parseNameValuePair(buffer, cursor);
        NameValuePair[] params = null;
        if (!cursor.atEnd() && buffer.charAt(cursor.getPos() - 1) != ',') {
            params = parseParameters(buffer, cursor);
        }
        return createHeaderElement(nvp.getName(), nvp.getValue(), params);
    }

    /* access modifiers changed from: protected */
    public HeaderElement createHeaderElement(String name, String value, NameValuePair[] params) {
        return new BasicHeaderElement(name, value, params);
    }

    public static NameValuePair[] parseParameters(String value, HeaderValueParser parser) {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        if (parser == null) {
            parser = INSTANCE;
        }
        return parser.parseParameters(buffer, cursor);
    }

    public NameValuePair[] parseParameters(CharArrayBuffer buffer, ParserCursor cursor) {
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        int pos = cursor.getPos();
        int indexTo = cursor.getUpperBound();
        while (pos < indexTo && HTTP.isWhitespace(buffer.charAt(pos))) {
            pos++;
        }
        cursor.updatePos(pos);
        if (cursor.atEnd()) {
            return new NameValuePair[0];
        }
        List params = new ArrayList();
        while (!cursor.atEnd()) {
            params.add(parseNameValuePair(buffer, cursor));
            if (buffer.charAt(cursor.getPos() - 1) == ',') {
                break;
            }
        }
        return (NameValuePair[]) params.toArray(new NameValuePair[params.size()]);
    }

    public static NameValuePair parseNameValuePair(String value, HeaderValueParser parser) {
        Args.notNull(value, "Value");
        CharArrayBuffer buffer = new CharArrayBuffer(value.length());
        buffer.append(value);
        ParserCursor cursor = new ParserCursor(0, value.length());
        if (parser == null) {
            parser = INSTANCE;
        }
        return parser.parseNameValuePair(buffer, cursor);
    }

    public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor) {
        return parseNameValuePair(buffer, cursor, a);
    }

    private static boolean a(char ch, char[] chs) {
        if (chs == null) {
            return false;
        }
        for (char ch2 : chs) {
            if (ch == ch2) {
                return true;
            }
        }
        return false;
    }

    public NameValuePair parseNameValuePair(CharArrayBuffer buffer, ParserCursor cursor, char[] delimiters) {
        String name;
        Args.notNull(buffer, "Char array buffer");
        Args.notNull(cursor, "Parser cursor");
        boolean terminated = false;
        int pos = cursor.getPos();
        int indexFrom = cursor.getPos();
        int indexTo = cursor.getUpperBound();
        while (true) {
            if (pos >= indexTo) {
                break;
            }
            char ch = buffer.charAt(pos);
            if (ch == '=') {
                break;
            } else if (a(ch, delimiters)) {
                terminated = true;
                break;
            } else {
                pos++;
            }
        }
        if (pos == indexTo) {
            terminated = true;
            name = buffer.substringTrimmed(indexFrom, indexTo);
        } else {
            name = buffer.substringTrimmed(indexFrom, pos);
            pos++;
        }
        if (terminated) {
            cursor.updatePos(pos);
            return createNameValuePair(name, null);
        }
        int i1 = pos;
        boolean qouted = false;
        boolean escaped = false;
        while (true) {
            if (pos >= indexTo) {
                break;
            }
            char ch2 = buffer.charAt(pos);
            if (ch2 == '\"' && !escaped) {
                qouted = !qouted;
            }
            if (!qouted && !escaped && a(ch2, delimiters)) {
                terminated = true;
                break;
            }
            escaped = !escaped && qouted && ch2 == '\\';
            pos++;
        }
        int i2 = pos;
        while (i1 < i2 && HTTP.isWhitespace(buffer.charAt(i1))) {
            i1++;
        }
        while (i2 > i1 && HTTP.isWhitespace(buffer.charAt(i2 - 1))) {
            i2--;
        }
        if (i2 - i1 >= 2 && buffer.charAt(i1) == '\"' && buffer.charAt(i2 - 1) == '\"') {
            i1++;
            i2--;
        }
        String value = buffer.substring(i1, i2);
        if (terminated) {
            pos++;
        }
        cursor.updatePos(pos);
        return createNameValuePair(name, value);
    }

    /* access modifiers changed from: protected */
    public NameValuePair createNameValuePair(String name, String value) {
        return new BasicNameValuePair(name, value);
    }
}
