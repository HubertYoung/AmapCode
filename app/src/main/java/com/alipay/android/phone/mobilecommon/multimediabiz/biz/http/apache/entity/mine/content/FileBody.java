package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.ContentType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileBody extends AbstractContentBody {
    private final File a;
    private final String b;

    @Deprecated
    public FileBody(File file, String filename, String mimeType, String charset) {
        this(file, ContentType.create(mimeType, charset), filename);
    }

    @Deprecated
    public FileBody(File file, String mimeType, String charset) {
        this(file, null, mimeType, charset);
    }

    @Deprecated
    public FileBody(File file, String mimeType) {
        this(file, ContentType.create(mimeType), (String) null);
    }

    public FileBody(File file) {
        this(file, ContentType.DEFAULT_BINARY, file != null ? file.getName() : null);
    }

    public FileBody(File file, ContentType contentType, String filename) {
        super(contentType);
        Args.notNull(file, "File");
        this.a = file;
        this.b = filename;
    }

    public FileBody(File file, ContentType contentType) {
        this(file, contentType, (String) null);
    }

    public InputStream getInputStream() {
        return new FileInputStream(this.a);
    }

    public void writeTo(OutputStream out) {
        Args.notNull(out, "Output stream");
        InputStream in = new FileInputStream(this.a);
        try {
            byte[] tmp = new byte[4096];
            while (true) {
                int l = in.read(tmp);
                if (l != -1) {
                    out.write(tmp, 0, l);
                } else {
                    out.flush();
                    return;
                }
            }
        } finally {
            in.close();
        }
    }

    public String getTransferEncoding() {
        return "binary";
    }

    public long getContentLength() {
        return this.a.length();
    }

    public String getFilename() {
        return this.b;
    }

    public File getFile() {
        return this.a;
    }
}
