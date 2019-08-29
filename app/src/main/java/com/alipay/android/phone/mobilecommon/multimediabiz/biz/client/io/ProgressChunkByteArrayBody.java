package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.output.ProgressOutputStream;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.ByteArrayBody;
import java.io.OutputStream;

public class ProgressChunkByteArrayBody extends ByteArrayBody implements TransferredListener {
    private int a;
    private int b;
    private int c;
    private ChunkTransferredListener d;

    public ProgressChunkByteArrayBody(byte[] bytes, int chunkSequence, int chunkSize, String name, ChunkTransferredListener chunkTransferredListener) {
        super(bytes, name);
        this.a = chunkSequence;
        this.b = chunkSequence - 1;
        this.c = chunkSize;
        this.d = chunkTransferredListener;
    }

    public void writeTo(OutputStream out) {
        new ProgressOutputStream(out, this).write(getData(), this.b * this.c, (int) getContentLength());
    }

    public void onTransferred(long transferredCount) {
        if (this.d != null) {
            this.d.onChunkTransferred(this.a, transferredCount);
        }
    }

    public long getContentLength() {
        byte[] data = getData();
        if (data.length < this.c) {
            return (long) data.length;
        }
        if (this.a * this.c < data.length) {
            return (long) this.c;
        }
        return (long) (data.length % this.c);
    }
}
