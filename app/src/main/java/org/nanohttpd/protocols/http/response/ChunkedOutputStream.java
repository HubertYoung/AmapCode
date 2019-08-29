package org.nanohttpd.protocols.http.response;

import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.FilterOutputStream;
import java.io.OutputStream;

public class ChunkedOutputStream extends FilterOutputStream {
    public ChunkedOutputStream(OutputStream out) {
        super(out);
    }

    public void write(int b) {
        write(new byte[]{(byte) b}, 0, 1);
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        if (len != 0) {
            this.out.write(String.format("%x\r\n", new Object[]{Integer.valueOf(len)}).getBytes());
            this.out.write(b, off, len);
            this.out.write(MultipartUtility.LINE_FEED.getBytes());
        }
    }

    public void finish() {
        this.out.write("0\r\n\r\n".getBytes());
    }
}
