package android.net.http;

import android.annotation.TargetApi;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.security.cert.X509Certificate;

public class SslError {
    static final /* synthetic */ boolean $assertionsDisabled = (!SslError.class.desiredAssertionStatus());
    public static final int SSL_DATE_INVALID = 4;
    public static final int SSL_EXPIRED = 1;
    public static final int SSL_IDMISMATCH = 2;
    public static final int SSL_INVALID = 5;
    @Deprecated
    public static final int SSL_MAX_ERROR = 6;
    public static final int SSL_NOTYETVALID = 0;
    public static final int SSL_UNTRUSTED = 3;
    final SslCertificate mCertificate;
    int mErrors;
    final String mUrl;

    @TargetApi(14)
    @Deprecated
    public SslError(int error, SslCertificate certificate) {
        this(error, certificate, (String) "");
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    @TargetApi(14)
    @Deprecated
    public SslError(int error, X509Certificate certificate) {
        this(error, certificate, (String) "");
    }

    public SslError(int error, SslCertificate certificate, String url) {
        if (!$assertionsDisabled && certificate == null) {
            throw new AssertionError();
        } else if ($assertionsDisabled || url != null) {
            addError(error);
            this.mCertificate = certificate;
            this.mUrl = url;
        } else {
            throw new AssertionError();
        }
    }

    @TargetApi(14)
    public SslError(int error, X509Certificate certificate, String url) {
        this(error, new SslCertificate(certificate), url);
    }

    @TargetApi(14)
    public static SslError SslErrorFromChromiumErrorCode(int error, SslCertificate cert, String url) {
        if (!$assertionsDisabled && (error < -299 || error > -200)) {
            throw new AssertionError();
        } else if (error == -200) {
            return new SslError(2, cert, url);
        } else {
            if (error == -201) {
                return new SslError(4, cert, url);
            }
            if (error == -202) {
                return new SslError(3, cert, url);
            }
            return new SslError(5, cert, url);
        }
    }

    public SslCertificate getCertificate() {
        return this.mCertificate;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public boolean addError(int error) {
        boolean rval = error >= 0 && error < 6;
        if (rval) {
            this.mErrors = (1 << error) | this.mErrors;
        }
        return rval;
    }

    public boolean hasError(int error) {
        boolean rval;
        if (error < 0 || error >= 6) {
            rval = false;
        } else {
            rval = true;
        }
        if (!rval) {
            return rval;
        }
        if ((this.mErrors & (1 << error)) != 0) {
            return true;
        }
        return false;
    }

    public int getPrimaryError() {
        if (this.mErrors != 0) {
            for (int error = 5; error >= 0; error--) {
                if ((this.mErrors & (1 << error)) != 0) {
                    return error;
                }
            }
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
        }
        return -1;
    }

    @TargetApi(14)
    public String toString() {
        return "primary error: " + getPrimaryError() + " certificate: " + getCertificate() + " on URL: " + getUrl();
    }
}
