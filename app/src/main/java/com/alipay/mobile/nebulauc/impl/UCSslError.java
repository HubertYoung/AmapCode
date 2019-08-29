package com.alipay.mobile.nebulauc.impl;

import android.net.http.SslCertificate;
import android.net.http.SslError;

class UCSslError extends SslError {
    private SslError mSslError;

    UCSslError(int error, SslCertificate certificate, SslError sslError) {
        super(error, certificate);
        this.mSslError = sslError;
    }

    public SslCertificate getCertificate() {
        return this.mSslError.getCertificate();
    }

    public boolean addError(int error) {
        if (this.mSslError != null) {
            return this.mSslError.addError(error);
        }
        return false;
    }

    public boolean hasError(int error) {
        if (this.mSslError != null) {
            return this.mSslError.hasError(error);
        }
        return false;
    }

    public int getPrimaryError() {
        if (this.mSslError != null) {
            return this.mSslError.getPrimaryError();
        }
        return 0;
    }
}
