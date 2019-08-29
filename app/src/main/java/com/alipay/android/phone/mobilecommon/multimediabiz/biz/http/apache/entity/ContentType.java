package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.Consts;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.message.BasicHeaderValueFormatterHC4;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.message.BasicHeaderValueParserHC4;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

public final class ContentType implements Serializable {
    public static final ContentType APPLICATION_ATOM_XML = create((String) "application/atom+xml", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_FORM_URLENCODED = create((String) "application/x-www-form-urlencoded", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_JSON = create((String) "application/json", Consts.UTF_8);
    public static final ContentType APPLICATION_OCTET_STREAM = create((String) FilePart.DEFAULT_CONTENT_TYPE, (Charset) null);
    public static final ContentType APPLICATION_SVG_XML = create((String) "application/svg+xml", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_XHTML_XML = create((String) "application/xhtml+xml", Consts.ISO_8859_1);
    public static final ContentType APPLICATION_XML = create((String) "application/xml", Consts.ISO_8859_1);
    public static final ContentType DEFAULT_BINARY = APPLICATION_OCTET_STREAM;
    public static final ContentType DEFAULT_TEXT = TEXT_PLAIN;
    public static final ContentType MULTIPART_FORM_DATA = create((String) "multipart/form-data", Consts.ISO_8859_1);
    public static final ContentType TEXT_HTML = create((String) "text/html", Consts.ISO_8859_1);
    public static final ContentType TEXT_PLAIN = create((String) "text/plain", Consts.ISO_8859_1);
    public static final ContentType TEXT_XML = create((String) "text/xml", Consts.ISO_8859_1);
    public static final ContentType WILDCARD = create((String) "*/*", (Charset) null);
    private static final long serialVersionUID = -7768694718232371896L;
    private final Charset charset;
    private final String mimeType;
    private final NameValuePair[] params;

    ContentType(String mimeType2, Charset charset2) {
        this.mimeType = mimeType2;
        this.charset = charset2;
        this.params = null;
    }

    ContentType(String mimeType2, NameValuePair[] params2) {
        this.mimeType = mimeType2;
        this.params = params2;
        String s = getParameter("charset");
        this.charset = !TextUtils.isBlank(s) ? Charset.forName(s) : null;
    }

    public final String getMimeType() {
        return this.mimeType;
    }

    public final Charset getCharset() {
        return this.charset;
    }

    public final String getParameter(String name) {
        NameValuePair[] nameValuePairArr;
        Args.notEmpty(name, (String) "Parameter name");
        if (this.params == null) {
            return null;
        }
        for (NameValuePair param : this.params) {
            if (param.getName().equalsIgnoreCase(name)) {
                return param.getValue();
            }
        }
        return null;
    }

    public final String toString() {
        CharArrayBuffer buf = new CharArrayBuffer(64);
        buf.append(this.mimeType);
        if (this.params != null) {
            buf.append("; ");
            BasicHeaderValueFormatterHC4.INSTANCE.formatParameters(buf, this.params, false);
        } else if (this.charset != null) {
            buf.append("; charset=");
            buf.append(this.charset.name());
        }
        return buf.toString();
    }

    private static boolean valid(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '\"' || ch == ',' || ch == ';') {
                return false;
            }
        }
        return true;
    }

    public static ContentType create(String mimeType2, Charset charset2) {
        String type = ((String) Args.notBlank(mimeType2, "MIME type")).toLowerCase(Locale.US);
        Args.check(valid(type), "MIME type may not contain reserved characters");
        return new ContentType(type, charset2);
    }

    public static ContentType create(String mimeType2) {
        return new ContentType(mimeType2, (Charset) null);
    }

    public static ContentType create(String mimeType2, String charset2) {
        return create(mimeType2, !TextUtils.isBlank(charset2) ? Charset.forName(charset2) : null);
    }

    private static ContentType create(HeaderElement helem) {
        String mimeType2 = helem.getName();
        NameValuePair[] params2 = helem.getParameters();
        if (params2 == null || params2.length <= 0) {
            params2 = null;
        }
        return new ContentType(mimeType2, params2);
    }

    public static ContentType parse(String s) {
        Args.notNull(s, "Content type");
        CharArrayBuffer buf = new CharArrayBuffer(s.length());
        buf.append(s);
        HeaderElement[] elements = BasicHeaderValueParserHC4.INSTANCE.parseElements(buf, new ParserCursor(0, s.length()));
        if (elements.length > 0) {
            return create(elements[0]);
        }
        throw new ParseException("Invalid content type: " + s);
    }

    public static ContentType get(HttpEntity entity) {
        if (entity == null) {
            return null;
        }
        Header header = entity.getContentType();
        if (header == null) {
            return null;
        }
        HeaderElement[] elements = header.getElements();
        if (elements.length > 0) {
            return create(elements[0]);
        }
        return null;
    }

    public static ContentType getOrDefault(HttpEntity entity) {
        ContentType contentType = get(entity);
        return contentType != null ? contentType : DEFAULT_TEXT;
    }

    public final ContentType withCharset(Charset charset2) {
        return create(getMimeType(), charset2);
    }

    public final ContentType withCharset(String charset2) {
        return create(getMimeType(), charset2);
    }
}
