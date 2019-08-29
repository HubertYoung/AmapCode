package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.ContentType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.AbstractContentBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.ContentBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;

public class FormBodyPart {
    private final String a;
    private final Header b = new Header();
    private final ContentBody c;

    public FormBodyPart(String name, ContentBody body) {
        Args.notNull(name, "Name");
        Args.notNull(body, "Body");
        this.a = name;
        this.c = body;
        generateContentDisp(body);
        generateContentType(body);
        generateTransferEncoding(body);
    }

    public String getName() {
        return this.a;
    }

    public ContentBody getBody() {
        return this.c;
    }

    public Header getHeader() {
        return this.b;
    }

    public void addField(String name, String value) {
        Args.notNull(name, "Field name");
        this.b.addField(new MinimalField(name, value));
    }

    /* access modifiers changed from: protected */
    public void generateContentDisp(ContentBody body) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("form-data; name=\"");
        buffer.append(getName());
        buffer.append("\"");
        if (body.getFilename() != null) {
            buffer.append("; filename=\"");
            buffer.append(body.getFilename());
            buffer.append("\"");
        }
        addField(MIME.CONTENT_DISPOSITION, buffer.toString());
    }

    /* access modifiers changed from: protected */
    public void generateContentType(ContentBody body) {
        ContentType contentType;
        if (body instanceof AbstractContentBody) {
            contentType = ((AbstractContentBody) body).getContentType();
        } else {
            contentType = null;
        }
        if (contentType != null) {
            addField("Content-Type", contentType.toString());
            return;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(body.getMimeType());
        if (body.getCharset() != null) {
            buffer.append("; charset=");
            buffer.append(body.getCharset());
        }
        addField("Content-Type", buffer.toString());
    }

    /* access modifiers changed from: protected */
    public void generateTransferEncoding(ContentBody body) {
        addField(MIME.CONTENT_TRANSFER_ENC, body.getTransferEncoding());
    }
}
