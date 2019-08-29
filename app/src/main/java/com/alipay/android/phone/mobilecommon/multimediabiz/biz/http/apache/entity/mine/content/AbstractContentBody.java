package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.ContentType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import java.nio.charset.Charset;

public abstract class AbstractContentBody implements ContentBody {
    private final ContentType a;

    public AbstractContentBody(ContentType contentType) {
        Args.notNull(contentType, "Content type");
        this.a = contentType;
    }

    @Deprecated
    public AbstractContentBody(String mimeType) {
        this(ContentType.parse(mimeType));
    }

    public ContentType getContentType() {
        return this.a;
    }

    public String getMimeType() {
        return this.a.getMimeType();
    }

    public String getMediaType() {
        String mimeType = this.a.getMimeType();
        int i = mimeType.indexOf(47);
        if (i != -1) {
            return mimeType.substring(0, i);
        }
        return mimeType;
    }

    public String getSubType() {
        String mimeType = this.a.getMimeType();
        int i = mimeType.indexOf(47);
        if (i != -1) {
            return mimeType.substring(i + 1);
        }
        return null;
    }

    public String getCharset() {
        Charset charset = this.a.getCharset();
        if (charset != null) {
            return charset.name();
        }
        return null;
    }
}
