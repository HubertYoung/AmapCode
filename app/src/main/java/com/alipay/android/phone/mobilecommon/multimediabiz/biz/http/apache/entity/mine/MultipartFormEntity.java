package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.InputStreamBody;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import org.apache.http.Header;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicHeader;

public class MultipartFormEntity extends AbstractHttpEntity {
    private final AbstractMultipartForm a;
    private final Header b;
    private final long c;

    MultipartFormEntity(AbstractMultipartForm multipart, String contentType, long contentLength) {
        this.a = multipart;
        this.b = new BasicHeader("Content-Type", contentType);
        this.c = contentLength;
    }

    public boolean isRepeatable() {
        return this.c != -1;
    }

    public boolean isChunked() {
        return !isRepeatable();
    }

    public boolean isStreaming() {
        return !isRepeatable();
    }

    public long getContentLength() {
        return this.c;
    }

    public Header getContentType() {
        return this.b;
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent() {
        if (isStreaming()) {
            throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() {
        throw new UnsupportedOperationException("Multipart form entity does not implement #getContent()");
    }

    public void writeTo(OutputStream outstream) {
        this.a.writeTo(outstream);
    }

    public boolean isRepeatableEntity() {
        boolean ret = true;
        try {
            Iterator it = this.a.getBodyParts().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().getBody() instanceof InputStreamBody) {
                        ret = false;
                        break;
                    }
                } else {
                    break;
                }
            }
            return ret;
        } catch (Exception e) {
            return true;
        }
    }
}
