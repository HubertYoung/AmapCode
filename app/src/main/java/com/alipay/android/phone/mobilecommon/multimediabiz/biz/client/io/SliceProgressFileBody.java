package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io;

import android.text.TextUtils;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.output.ProgressOutputStream;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.ContentType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.FileBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class SliceProgressFileBody extends FileBody {
    private int a;
    private int b = -1;
    private TransferredListener c;
    private String d;

    public SliceProgressFileBody(File file, String filename, String mimeType, String charset) {
        super(file, filename, mimeType, charset);
    }

    public SliceProgressFileBody(File file, String mimeType, String charset) {
        super(file, mimeType, charset);
    }

    public SliceProgressFileBody(File file, String mimeType) {
        super(file, mimeType);
    }

    public SliceProgressFileBody(File file) {
        super(file);
    }

    public SliceProgressFileBody(File file, ContentType contentType, String filename) {
        super(file, contentType, filename);
    }

    public SliceProgressFileBody(File file, ContentType contentType) {
        super(file, contentType);
    }

    public SliceProgressFileBody(File file, String fileName, int startPos, int endPos, TransferredListener listener) {
        super(file);
        this.a = startPos;
        this.c = listener;
        if (endPos != -1) {
            this.b = (endPos - startPos) + 1;
        }
        if (!TextUtils.isEmpty(fileName)) {
            this.d = fileName;
        }
    }

    public void writeTo(OutputStream out) {
        int bl;
        Args.notNull(out, "Output stream");
        RandomAccessFile in = new RandomAccessFile(getFile(), UploadQueueMgr.MSGTYPE_REALTIME);
        in.seek((long) this.a);
        OutputStream out2 = new ProgressOutputStream(out, this.c);
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

    public String getFilename() {
        return TextUtils.isEmpty(this.d) ? super.getFilename() : this.d;
    }

    public long getContentLength() {
        return this.b == -1 ? getFile().length() - ((long) this.a) : (long) this.b;
    }
}
