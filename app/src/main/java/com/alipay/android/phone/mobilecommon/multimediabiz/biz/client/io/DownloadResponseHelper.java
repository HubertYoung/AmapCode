package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.ByteUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class DownloadResponseHelper {
    public static final int READ_BUFF_SIZE = 4096;

    public static class FileHeader {
        public long chunkNumber;
        public String fileId;
        public long length;
        public String md5;
        public short type;

        public String toString() {
            return "FileHeader [fileId=" + this.fileId + ", length=" + this.length + ", md5=" + this.md5 + ", type=" + this.type + ", chunkNo=" + this.chunkNumber + "]";
        }
    }

    public interface ReadBatchFileRespCallback {
        OutputStream onReadFile(FileHeader fileHeader, boolean z);
    }

    public void writeSingleFile(InputStream responseInputStream, OutputStream outputStream) {
        try {
            IOUtils.copyLarge(responseInputStream, outputStream);
        } finally {
            IOUtils.closeQuietly(responseInputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void writeSingleFile(java.io.InputStream r10, java.io.File r11, long r12, com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.TransferredListener r14) {
        /*
            r9 = this;
            r4 = 0
            java.io.RandomAccessFile r5 = new java.io.RandomAccessFile     // Catch:{ all -> 0x0036 }
            java.lang.String r6 = "rw"
            r5.<init>(r11, r6)     // Catch:{ all -> 0x0036 }
            r5.seek(r12)     // Catch:{ all -> 0x0024 }
            r2 = 0
            r6 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r6]     // Catch:{ all -> 0x0024 }
        L_0x0011:
            int r1 = r10.read(r0)     // Catch:{ all -> 0x0024 }
            r6 = -1
            if (r1 == r6) goto L_0x002f
            r6 = 0
            r5.write(r0, r6, r1)     // Catch:{ all -> 0x0024 }
            long r6 = (long) r1     // Catch:{ all -> 0x0024 }
            long r2 = r2 + r6
            if (r14 == 0) goto L_0x0011
            r14.onTransferred(r2)     // Catch:{ all -> 0x0024 }
            goto L_0x0011
        L_0x0024:
            r6 = move-exception
            r4 = r5
        L_0x0026:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r10)
            if (r4 == 0) goto L_0x002e
            r4.close()
        L_0x002e:
            throw r6
        L_0x002f:
            com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils.closeQuietly(r10)
            r5.close()
            return
        L_0x0036:
            r6 = move-exception
            goto L_0x0026
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.DownloadResponseHelper.writeSingleFile(java.io.InputStream, java.io.File, long, com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.TransferredListener):void");
    }

    public void writeBatchFiles(InputStream responseInputStream, ReadBatchFileRespCallback readBatchFileRespCallback) {
        while (true) {
            FileHeader fileHeader = readFileHeader(responseInputStream);
            if (fileHeader != null) {
                boolean isEmptyFile = fileHeader.length == 0;
                OutputStream outputStream = readBatchFileRespCallback.onReadFile(fileHeader, isEmptyFile);
                if (!isEmptyFile) {
                    a(fileHeader, responseInputStream, outputStream);
                }
                IOUtils.closeQuietly(outputStream);
            } else {
                IOUtils.closeQuietly(responseInputStream);
                return;
            }
        }
    }

    public FileHeader readFileHeader(InputStream inputStream) {
        byte[] header = new byte[66];
        int l = inputStream.read(header);
        if (l == -1 || l != 66) {
            return null;
        }
        FileHeader fileHeader = new FileHeader();
        ByteBuffer byteBuffer = ByteBuffer.wrap(header);
        byte[] tempBytes = new byte[32];
        byteBuffer.get(tempBytes);
        fileHeader.fileId = new String(tempBytes, "UTF-8");
        fileHeader.length = byteBuffer.getLong();
        byteBuffer.get(tempBytes, 0, 16);
        fileHeader.md5 = ByteUtil.byteArrayToHexString(tempBytes, 0, 16);
        fileHeader.type = byteBuffer.getShort();
        fileHeader.chunkNumber = byteBuffer.getLong();
        return fileHeader;
    }

    private static void a(FileHeader fileHeader, InputStream inputStream, OutputStream destOutputStream) {
        long fileSize = fileHeader.length;
        if (fileSize != 0) {
            if (destOutputStream == null) {
                IOUtils.skip(inputStream, fileSize);
                return;
            }
            IOUtils.copyLarge(inputStream, destOutputStream, 0, fileSize, new byte[4096]);
            destOutputStream.flush();
        }
    }
}
