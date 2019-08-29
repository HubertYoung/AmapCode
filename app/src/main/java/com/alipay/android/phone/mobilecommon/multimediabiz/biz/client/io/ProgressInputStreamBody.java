package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.output.ProgressOutputStream;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.InputStreamBody;
import java.io.InputStream;
import java.io.OutputStream;

public class ProgressInputStreamBody extends InputStreamBody {
    private long a;
    protected TransferredListener transferedChangedListener;

    public ProgressInputStreamBody(InputStream inputStream, String fileName, long contentLength, TransferredListener transferedChangedListener2) {
        super(inputStream, fileName);
        this.a = -1;
        this.transferedChangedListener = transferedChangedListener2;
        this.a = contentLength;
    }

    public ProgressInputStreamBody(InputStream inputStream, String fileName, TransferredListener transferedChangedListener2) {
        this(inputStream, fileName, (long) inputStream.available(), transferedChangedListener2);
    }

    public void writeTo(OutputStream out) {
        super.writeTo(createProgressOutputStream(out));
    }

    /* access modifiers changed from: protected */
    public ProgressOutputStream createProgressOutputStream(OutputStream out) {
        return new ProgressOutputStream(out, this.transferedChangedListener);
    }

    public long getContentLength() {
        return this.a;
    }

    public void setContentLength(long contentLength) {
        this.a = contentLength;
    }
}
