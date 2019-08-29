package com.alipay.android.phone.mobilecommon.multimediabiz.biz.security;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.io.IOUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.http.entity.BasicHttpEntity;

public class LegalHttpEntity extends BasicHttpEntity {
    private static final String TAG = "LegalHttpEntity";
    private InputStream in;
    private byte[] legalData;
    private File legalFile;

    public LegalHttpEntity(File legalFile2) {
        this.legalFile = legalFile2;
    }

    public LegalHttpEntity(byte[] legalData2) {
        this.legalData = legalData2;
    }

    public long getContentLength() {
        if (this.legalData != null) {
            return (long) this.legalData.length;
        }
        return this.legalFile != null ? this.legalFile.length() : LegalHttpEntity.super.getContentLength();
    }

    public InputStream getContent() {
        if (this.in == null) {
            if (this.legalData != null) {
                this.in = new ByteArrayInputStream(this.legalData);
            } else if (this.legalFile != null) {
                try {
                    this.in = new FileInputStream(this.legalFile);
                } catch (FileNotFoundException e) {
                    Logger.E((String) TAG, (Throwable) e, (String) "getContent error", new Object[0]);
                }
            }
        }
        if (this.in == null) {
            return LegalHttpEntity.super.getContent();
        }
        return this.in;
    }

    public void consumeContent() {
        if (this.in != null) {
            IOUtils.closeQuietly(this.in);
        }
        LegalHttpEntity.super.consumeContent();
    }

    public boolean isRepeatable() {
        return (this.legalFile == null && this.legalData == null && !LegalHttpEntity.super.isRepeatable()) ? false : true;
    }
}
