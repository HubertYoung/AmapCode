package com.alipay.mobile.common.transportext.biz.spdy;

import android.text.TextUtils;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Base64;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

public interface OkAuthenticator {

    public final class Challenge {
        private final String realm;
        private final String scheme;

        public Challenge(String scheme2, String realm2) {
            this.scheme = scheme2;
            this.realm = realm2;
        }

        public final String getScheme() {
            return this.scheme;
        }

        public final String getRealm() {
            return this.realm;
        }

        public final boolean equals(Object o) {
            return (o instanceof Challenge) && TextUtils.equals(((Challenge) o).scheme, this.scheme) && TextUtils.equals(((Challenge) o).realm, this.realm);
        }

        public final int hashCode() {
            return this.scheme.hashCode() + (this.realm.hashCode() * 31);
        }

        public final String toString() {
            return this.scheme + " realm=\"" + this.realm + "\"";
        }
    }

    public final class Credential {
        private final String headerValue;

        private Credential(String headerValue2) {
            this.headerValue = headerValue2;
        }

        public static Credential basic(String userName, String password) {
            try {
                return new Credential("Basic " + Base64.encode((userName + ":" + password).getBytes("ISO-8859-1")));
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError();
            }
        }

        public final String getHeaderValue() {
            return this.headerValue;
        }

        public final boolean equals(Object o) {
            return (o instanceof Credential) && TextUtils.equals(((Credential) o).headerValue, this.headerValue);
        }

        public final int hashCode() {
            return this.headerValue.hashCode();
        }

        public final String toString() {
            return this.headerValue;
        }
    }

    Credential authenticate(Proxy proxy, URL url, List<Challenge> list);

    Credential authenticateProxy(Proxy proxy, URL url, List<Challenge> list);
}
