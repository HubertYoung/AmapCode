package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.output.ProgressOutputStream;
import java.io.File;
import java.io.OutputStream;

public class ProgressChunkFileBody extends ChunkFileBody implements TransferredListener {
    private ChunkTransferredListener a;

    public ProgressChunkFileBody(File file, int chunkSequence, long chunkSize, ChunkTransferredListener chunkTransferredListener) {
        super(file, chunkSequence, chunkSize);
        this.a = chunkTransferredListener;
    }

    public void writeTo(OutputStream out) {
        super.writeTo(new ProgressOutputStream(out, this));
    }

    public void onTransferred(long transferredCount) {
        if (this.a != null) {
            this.a.onChunkTransferred(getChunkSequence(), transferredCount);
        }
    }
}
