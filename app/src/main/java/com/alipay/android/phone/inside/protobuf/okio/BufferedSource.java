package com.alipay.android.phone.inside.protobuf.okio;

import java.io.IOException;
import java.nio.charset.Charset;

public interface BufferedSource extends Source {
    String a(long j, Charset charset) throws IOException;

    void a(long j) throws IOException;

    boolean a() throws IOException;

    byte b() throws IOException;

    ByteString b(long j) throws IOException;

    int c() throws IOException;

    void c(long j) throws IOException;

    long d() throws IOException;
}
