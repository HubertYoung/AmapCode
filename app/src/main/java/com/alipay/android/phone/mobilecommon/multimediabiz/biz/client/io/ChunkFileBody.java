package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io;

import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.FileBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.io.File;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class ChunkFileBody extends FileBody {
    private int a;
    private long b;
    private long c;
    private long d;
    private long e;
    private long f;

    public ChunkFileBody(File file, int chunkSequence, long chunkSize) {
        super(file);
        a(chunkSequence, chunkSize);
    }

    private void a(int chunkSequence, long chunkSize) {
        if (chunkSize <= 0 || chunkSequence <= 0) {
            throw new IllegalArgumentException("Pls check parameter chunkSize [" + chunkSize + "] and chunkSequence [" + chunkSequence + "] !");
        }
        this.a = chunkSequence;
        this.b = chunkSize;
        this.c = getFile().length();
        this.d = this.c / chunkSize;
        if (this.c % chunkSize != 0) {
            this.d++;
        }
        this.e = ((long) (chunkSequence - 1)) * chunkSize;
        if (this.e + chunkSize > this.c) {
            chunkSize = this.c - this.e;
        }
        this.f = chunkSize;
    }

    public void writeTo(OutputStream out) {
        if (((long) this.a) > this.d) {
            Logger.W(DjangoClient.LOG_TAG, "ChunkSequence greater than ChunkNumber,quit !", new Object[0]);
            return;
        }
        RandomAccessFile randomFile = null;
        try {
            RandomAccessFile randomFile2 = new RandomAccessFile(getFile(), UploadQueueMgr.MSGTYPE_REALTIME);
            try {
                randomFile2.seek(this.e);
                int readLength = (int) (this.f > 4096 ? 4096 : this.f);
                byte[] tmp = new byte[4096];
                long readCount = 0;
                while (true) {
                    int actulReadedLenght = randomFile2.read(tmp, 0, readLength);
                    if (actulReadedLenght == -1) {
                        break;
                    }
                    out.write(tmp, 0, actulReadedLenght);
                    readCount += (long) actulReadedLenght;
                    if (4096 + readCount > this.f) {
                        readLength = (int) (this.f - readCount);
                        if (readLength <= 0) {
                            break;
                        }
                    }
                }
                out.flush();
                randomFile2.close();
            } catch (Throwable th) {
                th = th;
                randomFile = randomFile2;
            }
        } catch (Throwable th2) {
            th = th2;
            if (randomFile != null) {
                randomFile.close();
            }
            throw th;
        }
    }

    public long getContentLength() {
        return this.f;
    }

    public long getChunkSize() {
        return this.b;
    }

    public int getChunkSequence() {
        return this.a;
    }
}
