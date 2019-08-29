package org.json.alipay.inside;

public class JSONException extends Exception {
    private Throwable cause;

    public JSONException(String str) {
        super(str);
    }

    public JSONException(Throwable th) {
        super(th.getMessage());
        this.cause = th;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
