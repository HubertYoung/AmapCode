package com.sina.weibo.sdk.network.base;

import com.sina.weibo.sdk.network.exception.RequestException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WbResponseBody {
    private InputStream inputStream;
    private long length;

    public WbResponseBody(InputStream inputStream2, long j) {
        this.inputStream = inputStream2;
        this.length = j;
    }

    public String string() throws RequestException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = this.inputStream.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    this.inputStream.close();
                    byteArrayOutputStream.close();
                    return new String(byteArrayOutputStream.toByteArray());
                }
            }
        } catch (IOException e) {
            throw new RequestException(e.toString());
        }
    }

    public InputStream byteStream() {
        return this.inputStream;
    }

    public long contentLength() {
        return this.length;
    }
}
