package mtopsdk.mtop.common;

import android.os.Handler;

public class ApiID implements ffb {
    private static final String TAG = "mtopsdk.ApiID";
    private volatile fge call;
    private fdf mtopContext;

    public ApiID(fge fge, fdf fdf) {
        this.call = fge;
        this.mtopContext = fdf;
    }

    public fdf getMtopContext() {
        return this.mtopContext;
    }

    public fge getCall() {
        return this.call;
    }

    public void setCall(fge fge) {
        this.call = fge;
    }

    public boolean cancelApiCall() {
        if (this.call != null) {
            this.call.b();
        }
        return true;
    }

    public ApiID retryApiCall(Handler handler) {
        if (this.mtopContext == null) {
            return null;
        }
        this.mtopContext.d.handler = handler;
        fdy fdy = this.mtopContext.a.c.K;
        if (fdy != null) {
            fdy.a(null, this.mtopContext);
        }
        fed.a(fdy, this.mtopContext);
        return new ApiID(null, this.mtopContext);
    }

    public ApiID retryApiCall() {
        return retryApiCall(null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("ApiID [call=");
        sb.append(this.call);
        sb.append(", mtopContext=");
        sb.append(this.mtopContext);
        sb.append("]");
        return sb.toString();
    }
}
