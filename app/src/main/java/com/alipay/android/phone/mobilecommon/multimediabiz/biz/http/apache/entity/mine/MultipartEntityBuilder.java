package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.ContentType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.ByteArrayBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.ContentBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.FileBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.InputStreamBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.content.StringBody;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.Args;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.http.HttpEntity;

public class MultipartEntityBuilder {
    private static final char[] a = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private String b = "form-data";
    private String c = null;
    private Charset d = null;
    private List<FormBodyPart> e = null;

    public static MultipartEntityBuilder create() {
        return new MultipartEntityBuilder();
    }

    MultipartEntityBuilder() {
    }

    public MultipartEntityBuilder setMode(HttpMultipartMode mode) {
        return this;
    }

    public MultipartEntityBuilder setLaxMode() {
        return this;
    }

    public MultipartEntityBuilder setBoundary(String boundary) {
        this.c = boundary;
        return this;
    }

    public MultipartEntityBuilder setCharset(Charset charset) {
        this.d = charset;
        return this;
    }

    private MultipartEntityBuilder a(FormBodyPart bodyPart) {
        if (this.e == null) {
            this.e = new ArrayList();
        }
        this.e.add(bodyPart);
        return this;
    }

    public MultipartEntityBuilder addPart(String name, ContentBody contentBody) {
        Args.notNull(name, "Name");
        Args.notNull(contentBody, "Content body");
        return a(new FormBodyPart(name, contentBody));
    }

    public MultipartEntityBuilder addTextBody(String name, String text, ContentType contentType) {
        return addPart(name, new StringBody(text, contentType));
    }

    public MultipartEntityBuilder addTextBody(String name, String text) {
        return addTextBody(name, text, ContentType.DEFAULT_TEXT);
    }

    public MultipartEntityBuilder addBinaryBody(String name, byte[] b2, ContentType contentType, String filename) {
        return addPart(name, new ByteArrayBody(b2, contentType, filename));
    }

    public MultipartEntityBuilder addBinaryBody(String name, byte[] b2) {
        return addBinaryBody(name, b2, ContentType.DEFAULT_BINARY, (String) null);
    }

    public MultipartEntityBuilder addBinaryBody(String name, File file, ContentType contentType, String filename) {
        return addPart(name, new FileBody(file, contentType, filename));
    }

    public MultipartEntityBuilder addBinaryBody(String name, File file) {
        return addBinaryBody(name, file, ContentType.DEFAULT_BINARY, file != null ? file.getName() : null);
    }

    public MultipartEntityBuilder addBinaryBody(String name, InputStream stream, ContentType contentType, String filename) {
        return addPart(name, new InputStreamBody(stream, contentType, filename));
    }

    public MultipartEntityBuilder addBinaryBody(String name, InputStream stream) {
        return addBinaryBody(name, stream, ContentType.DEFAULT_BINARY, (String) null);
    }

    private static String a(String boundary, Charset charset) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("multipart/form-data; boundary=");
        buffer.append(boundary);
        if (charset != null) {
            buffer.append("; charset=");
            buffer.append(charset.name());
        }
        return buffer.toString();
    }

    private static String a() {
        StringBuilder buffer = new StringBuilder();
        Random rand = new Random();
        int count = rand.nextInt(11) + 30;
        for (int i = 0; i < count; i++) {
            buffer.append(a[rand.nextInt(a.length)]);
        }
        return buffer.toString();
    }

    private MultipartFormEntity b() {
        List bps;
        String st = this.b != null ? this.b : "form-data";
        Charset cs = this.d;
        String b2 = this.c != null ? this.c : a();
        if (this.e != null) {
            bps = new ArrayList(this.e);
        } else {
            bps = Collections.emptyList();
        }
        AbstractMultipartForm form = new HttpBrowserCompatibleMultipart(st, cs, b2, bps);
        return new MultipartFormEntity(form, a(b2, cs), form.getTotalLength());
    }

    public HttpEntity build() {
        return b();
    }
}
