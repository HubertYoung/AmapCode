package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transportext.biz.spdy.OkAuthenticator;
import com.alipay.mobile.common.transportext.biz.spdy.OkAuthenticator.Challenge;
import com.alipay.mobile.common.transportext.biz.spdy.OkAuthenticator.Credential;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.net.Authenticator;
import java.net.Authenticator.RequestorType;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class HttpAuthenticator {
    public static final OkAuthenticator SYSTEM_DEFAULT = new OkAuthenticator() {
        public final Credential authenticate(Proxy proxy, URL url, List<Challenge> challenges) {
            for (Challenge challenge : challenges) {
                if ("Basic".equalsIgnoreCase(challenge.getScheme())) {
                    PasswordAuthentication auth = Authenticator.requestPasswordAuthentication(url.getHost(), getConnectToInetAddress(proxy, url), url.getPort(), url.getProtocol(), challenge.getRealm(), challenge.getScheme(), url, RequestorType.SERVER);
                    if (auth != null) {
                        return Credential.basic(auth.getUserName(), new String(auth.getPassword()));
                    }
                }
            }
            return null;
        }

        public final Credential authenticateProxy(Proxy proxy, URL url, List<Challenge> challenges) {
            for (Challenge challenge : challenges) {
                if ("Basic".equalsIgnoreCase(challenge.getScheme())) {
                    InetSocketAddress proxyAddress = (InetSocketAddress) proxy.address();
                    PasswordAuthentication auth = Authenticator.requestPasswordAuthentication(proxyAddress.getHostName(), getConnectToInetAddress(proxy, url), proxyAddress.getPort(), url.getProtocol(), challenge.getRealm(), challenge.getScheme(), url, RequestorType.PROXY);
                    if (auth != null) {
                        return Credential.basic(auth.getUserName(), new String(auth.getPassword()));
                    }
                }
            }
            return null;
        }

        private InetAddress getConnectToInetAddress(Proxy proxy, URL url) {
            if (proxy == null || proxy.type() == Type.DIRECT) {
                return InetAddress.getByName(url.getHost());
            }
            return ((InetSocketAddress) proxy.address()).getAddress();
        }
    };

    private HttpAuthenticator() {
    }

    public static boolean processAuthHeader(OkAuthenticator authenticator, int responseCode, RawHeaders responseHeaders, RawHeaders successorRequestHeaders, Proxy proxy, URL url) {
        String responseField;
        String requestField;
        Credential credential;
        if (responseCode == 401) {
            responseField = "WWW-Authenticate";
            requestField = "Authorization";
        } else if (responseCode == 407) {
            responseField = "Proxy-Authenticate";
            requestField = "Proxy-Authorization";
        } else {
            throw new IllegalArgumentException();
        }
        List challenges = parseChallenges(responseHeaders, responseField);
        if (challenges.isEmpty()) {
            return false;
        }
        if (responseHeaders.getResponseCode() == 407) {
            credential = authenticator.authenticateProxy(proxy, url, challenges);
        } else {
            credential = authenticator.authenticate(proxy, url, challenges);
        }
        if (credential == null) {
            return false;
        }
        successorRequestHeaders.set(requestField, credential.getHeaderValue());
        return true;
    }

    private static List<Challenge> parseChallenges(RawHeaders responseHeaders, String challengeHeader) {
        List result = new ArrayList();
        for (int h = 0; h < responseHeaders.length(); h++) {
            if (challengeHeader.equalsIgnoreCase(responseHeaders.getFieldName(h))) {
                String value = responseHeaders.getValue(h);
                int pos = 0;
                while (pos < value.length()) {
                    int tokenStart = pos;
                    int pos2 = HeaderParser.skipUntil(value, pos, Token.SEPARATOR);
                    String scheme = value.substring(tokenStart, pos2).trim();
                    int pos3 = HeaderParser.skipWhitespace(value, pos2);
                    if (!value.regionMatches(true, pos3, "realm=\"", 0, 7)) {
                        break;
                    }
                    int pos4 = pos3 + 7;
                    int realmStart = pos4;
                    int pos5 = HeaderParser.skipUntil(value, pos4, "\"");
                    String realm = value.substring(realmStart, pos5);
                    pos = HeaderParser.skipWhitespace(value, HeaderParser.skipUntil(value, pos5 + 1, ",") + 1);
                    result.add(new Challenge(scheme, realm));
                }
            }
        }
        return result;
    }
}
