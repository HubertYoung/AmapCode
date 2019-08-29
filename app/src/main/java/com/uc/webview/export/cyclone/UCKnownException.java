package com.uc.webview.export.cyclone;

@Constant
/* compiled from: ProGuard */
public class UCKnownException extends RuntimeException {
    private int mCode;

    public UCKnownException(int i, String str, Throwable th) {
        super(str, th);
        this.mCode = -99999;
        this.mCode = i;
    }

    public UCKnownException(int i, Throwable th) {
        this(th);
        this.mCode = i;
    }

    public UCKnownException(int i, String str) {
        super(str);
        this.mCode = -99999;
        this.mCode = i;
    }

    public UCKnownException(Throwable th) {
        super(th);
        this.mCode = -99999;
        while (th != null) {
            if (!(th instanceof UCKnownException)) {
                if (th.getCause() == null || th.getCause() == th) {
                    break;
                }
                th = th.getCause();
            } else {
                this.mCode = ((UCKnownException) th).mCode;
                return;
            }
        }
    }

    public static UCKnownException create(Throwable th, int i) {
        if (th instanceof UCKnownException) {
            return (UCKnownException) th;
        }
        return new UCKnownException(i, th);
    }

    public UCKnownException(String str) {
        super(str);
        this.mCode = -99999;
    }

    public final int errCode() {
        return this.mCode;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mCode);
        sb.append(":");
        sb.append(super.toString());
        return sb.toString();
    }

    public final Runnable toRunnable() {
        return new Runnable() {
            public void run() {
                throw UCKnownException.this;
            }
        };
    }

    public final Throwable getRootCause() {
        Throwable th = this;
        while (th.getCause() != null && th.getCause() != th) {
            th = th.getCause();
        }
        return th;
    }
}
