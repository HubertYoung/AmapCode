package com.autonavi.link.adapter.client.b.b;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.link.adapter.b.c;
import com.autonavi.link.adapter.endian.EndianConversion;
import com.autonavi.link.adapter.endian.LittleEndianInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/* compiled from: SocketClient */
public class a {
    Socket a = null;
    protected InputStream b;
    protected OutputStream c;
    private ByteArrayOutputStream d;
    private byte[] e;
    private byte[] f;
    private boolean g = false;
    /* access modifiers changed from: private */
    @SuppressLint({"HandlerLeak"})
    public final Handler h = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == 111) {
                a.this.h.removeMessages(101);
                try {
                    a.this.b();
                } catch (IOException unused) {
                }
            }
        }
    };

    public void a(String str, int i) throws IOException {
        this.a = new Socket();
        this.a.setOOBInline(true);
        this.a.setKeepAlive(true);
        this.a.connect(new InetSocketAddress(str, i), 2000);
        this.b = this.a.getInputStream();
        this.c = this.a.getOutputStream();
        this.d = new ByteArrayOutputStream(512000);
        this.f = new byte[512000];
    }

    public OutputStream a() {
        return this.c;
    }

    public void a(com.autonavi.link.adapter.a.a aVar) throws IOException {
        this.g = true;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(this.b, 512000);
        LittleEndianInputStream littleEndianInputStream = new LittleEndianInputStream(bufferedInputStream);
        while (true) {
            if (!this.g) {
                break;
            }
            try {
                int readInt = littleEndianInputStream.readInt();
                if (readInt > 512000) {
                    break;
                } else if (readInt <= 0) {
                    break;
                } else {
                    this.d.reset();
                    this.d.write(readInt & 255);
                    this.d.write((byte) (readInt >>> 8));
                    this.d.write((byte) (readInt >>> 16));
                    this.d.write((byte) (readInt >>> 24));
                    int i = readInt + 0;
                    int i2 = 0;
                    while (i > 0) {
                        byte[] bArr = this.f;
                        if (i > 512000) {
                            i = 512000;
                        }
                        int read = bufferedInputStream.read(bArr, 0, i);
                        if (read == -1) {
                            break;
                        }
                        this.d.write(this.f, 0, read);
                        i2 += read;
                        i = readInt - i2;
                    }
                    this.e = this.d.toByteArray();
                    int little_bytesToInt = EndianConversion.little_bytesToInt(new byte[]{this.e[6], this.e[7]});
                    if (c.b(this.e)) {
                        aVar.a(this.e, little_bytesToInt);
                    }
                }
            } catch (IOException e2) {
                throw e2;
            }
        }
        b();
    }

    public void b() throws IOException {
        this.g = false;
        if (this.c != null) {
            this.c.close();
        }
        if (this.b != null) {
            this.b.close();
        }
        if (this.a != null) {
            this.a.close();
        }
        this.a = null;
        this.c = null;
        this.b = null;
    }
}
