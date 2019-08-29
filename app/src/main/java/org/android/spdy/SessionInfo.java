package org.android.spdy;

public class SessionInfo {
    private static int INVALID_PUBLIC_SEQNUM = -1;
    private String certHost = null;
    private int connTimeoutMs;
    private String domain;
    private String host;
    private int mode;
    private int port;
    private String proxyHost;
    private int proxyPort;
    private int pubkey_seqnum;
    private SessionCb sessionCb;
    private Object sessionUserData;

    public SessionInfo(String str, int i, String str2, String str3, int i2, Object obj, SessionCb sessionCb2, int i3) {
        this.host = str;
        this.port = i;
        this.domain = str2;
        this.proxyHost = str3;
        this.proxyPort = i2;
        this.sessionUserData = obj;
        this.sessionCb = sessionCb2;
        this.mode = i3;
        this.pubkey_seqnum = INVALID_PUBLIC_SEQNUM;
        this.connTimeoutMs = -1;
    }

    /* access modifiers changed from: 0000 */
    public String getAuthority() {
        if (this.proxyHost == null || this.proxyPort == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.host);
            sb.append(":");
            sb.append(this.port);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.host);
        sb2.append(":");
        sb2.append(this.port);
        sb2.append("/");
        sb2.append(this.proxyHost);
        sb2.append(":");
        sb2.append(this.proxyPort);
        return sb2.toString();
    }

    /* access modifiers changed from: 0000 */
    public String getCertHost() {
        if (this.pubkey_seqnum != INVALID_PUBLIC_SEQNUM) {
            return null;
        }
        return this.certHost;
    }

    public void setCertHost(String str) {
        this.certHost = str;
    }

    /* access modifiers changed from: 0000 */
    public Object getSessonUserData() {
        return this.sessionUserData;
    }

    /* access modifiers changed from: 0000 */
    public SessionCb getSessionCb() {
        return this.sessionCb;
    }

    /* access modifiers changed from: 0000 */
    public int getMode() {
        return this.mode;
    }

    /* access modifiers changed from: 0000 */
    public String getDomain() {
        return this.domain;
    }

    public void setConnectionTimeoutMs(int i) {
        this.connTimeoutMs = i;
    }

    /* access modifiers changed from: 0000 */
    public int getConnectionTimeoutMs() {
        return this.connTimeoutMs;
    }

    public void setPubKeySeqNum(int i) {
        this.pubkey_seqnum = i;
    }

    /* access modifiers changed from: 0000 */
    public int getPubKeySeqNum() {
        return this.pubkey_seqnum;
    }
}
