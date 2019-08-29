package defpackage;

import android.support.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/* renamed from: bpk reason: default package */
/* compiled from: HttpResponse */
public abstract class bpk<T> {
    protected byte[] mBodyData;
    private bpa mImpl;
    private bph mRequest;
    protected T mResult;

    /* access modifiers changed from: protected */
    public abstract T parseResult();

    public void setRequest(bph bph) {
        this.mRequest = bph;
    }

    public bph getRequest() {
        return this.mRequest;
    }

    public void setImpl(bpa bpa) {
        this.mImpl = bpa;
    }

    public int getStatusCode() {
        return this.mImpl.getStatusCode();
    }

    public long getContentLength() {
        return this.mImpl.getContentLength();
    }

    public String getHeader(String str) {
        return this.mImpl.getHeader(str);
    }

    public Map<String, List<String>> getHeaders() {
        return this.mImpl.getHeaders();
    }

    public long getTtl() {
        try {
            return Date.parse(getHeader("Expires"));
        } catch (Exception unused) {
            return Long.MAX_VALUE;
        }
    }

    @Nullable
    public InputStream getBodyInputStream() {
        return this.mImpl.getBodyInputStream();
    }

    @Nullable
    public byte[] getResponseBodyData() {
        if (this.mBodyData == null) {
            InputStream bodyInputStream = getBodyInputStream();
            if (bodyInputStream != null) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(bodyInputStream);
                byte[] bArr = new byte[512];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    try {
                        int read = bufferedInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    } catch (IOException unused) {
                        this.mBodyData = null;
                    } catch (Throwable th) {
                        bow.a(bufferedInputStream);
                        throw th;
                    }
                }
                this.mBodyData = byteArrayOutputStream.toByteArray();
                bow.a(bufferedInputStream);
            }
        }
        return this.mBodyData;
    }

    @Nullable
    public String getResponseBodyString() {
        byte[] responseBodyData = getResponseBodyData();
        if (responseBodyData == null) {
            return null;
        }
        try {
            return new String(responseBodyData, bps.a(getHeaders(), "UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            return new String(responseBodyData);
        }
    }

    public void parse() {
        this.mResult = parseResult();
    }

    @Nullable
    public T getResult() {
        return this.mResult;
    }
}
