package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

class HttpBrowserCompatibleMultipart extends AbstractMultipartForm {
    private final List<FormBodyPart> a;

    public HttpBrowserCompatibleMultipart(String subType, Charset charset, String boundary, List<FormBodyPart> parts) {
        super(subType, charset, boundary);
        this.a = parts;
    }

    public List<FormBodyPart> getBodyParts() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void formatMultipartHeader(FormBodyPart part, OutputStream out) {
        Header header = part.getHeader();
        writeField(header.getField(MIME.CONTENT_DISPOSITION), this.charset, out);
        if (part.getBody().getFilename() != null) {
            writeField(header.getField("Content-Type"), this.charset, out);
        }
    }
}
