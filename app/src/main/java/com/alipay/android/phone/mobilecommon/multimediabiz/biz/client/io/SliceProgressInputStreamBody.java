package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.output.ProgressOutputStream;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.ContentType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.InputStreamBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import java.io.InputStream;
import java.io.OutputStream;

public class SliceProgressInputStreamBody extends InputStreamBody {
    private int a;
    private int b = -1;
    private TransferredListener c;

    public SliceProgressInputStreamBody(InputStream in, String mimeType, String filename) {
        super(in, mimeType, filename);
    }

    public SliceProgressInputStreamBody(InputStream in, String filename) {
        super(in, filename);
    }

    public SliceProgressInputStreamBody(InputStream in, ContentType contentType, String filename) {
        super(in, contentType, filename);
    }

    public SliceProgressInputStreamBody(InputStream in, ContentType contentType) {
        super(in, contentType);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public SliceProgressInputStreamBody(InputStream in, String filename, int startPos, int endPos, TransferredListener listener) {
        // int i = -1;
        super(in, filename);
        this.a = startPos;
        this.c = listener;
        this.b = endPos > 0 ? (endPos - startPos) + 1 : i;
    }

    private InputStream a(long offset) {
        InputStream in = getInputStream();
        if (offset > 0) {
            long totalSkip = 0;
            do {
                totalSkip += in.skip(offset);
            } while (totalSkip < offset);
        }
        return in;
    }

    public void writeTo(OutputStream out) {
        int bl;
        Args.notNull(out, "check out");
        OutputStream out2 = new ProgressOutputStream(out, this.c);
        InputStream in = a((long) this.a);
        try {
            byte[] tmp = new byte[4096];
            int bl2 = (this.b < 0 || this.b > 4096) ? 4096 : this.b;
            int r = this.b == -1 ? -1 : this.b;
            while (true) {
                int l = in.read(tmp, 0, bl2);
                if (l == -1 || (r <= 0 && this.b != -1)) {
                    out2.flush();
                } else {
                    out2.write(tmp, 0, l);
                    r -= l;
                    if (r < 0 || r > 4096) {
                        bl = 4096;
                    } else {
                        bl = r;
                    }
                }
            }
            out2.flush();
        } finally {
            in.close();
        }
    }

    public long getContentLength() {
        return (long) this.b;
    }
}
