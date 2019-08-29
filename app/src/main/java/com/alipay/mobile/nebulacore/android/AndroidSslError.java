package com.alipay.mobile.nebulacore.android;

import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.support.annotation.NonNull;

class AndroidSslError extends SslError {
    private SslError a;

    private class NullSslError extends SslError {
        public NullSslError(int error, SslCertificate certificate) {
            super(error, certificate);
        }

        @NonNull
        public String getUrl() {
            return null;
        }

        public boolean addError(int error) {
            return false;
        }

        public boolean hasError(int error) {
            return false;
        }

        public int getPrimaryError() {
            return super.getPrimaryError();
        }

        public String toString() {
            return "Null SslError instance";
        }
    }

    AndroidSslError(SslError sslError) {
        super(0, (SslCertificate) null);
        if (sslError == null) {
            this.a = new NullSslError(0, null);
        } else {
            this.a = sslError;
        }
    }

    public SslCertificate getCertificate() {
        return this.a.getCertificate();
    }

    public String getUrl() {
        return null;
    }

    public boolean addError(int error) {
        return this.a != null && this.a.addError(error);
    }

    public boolean hasError(int error) {
        return this.a.hasError(error);
    }

    public int getPrimaryError() {
        return this.a.getPrimaryError();
    }
}
