package pl.droidsonroids.gif;

import android.support.annotation.NonNull;
import java.io.IOException;

public class GifIOException extends IOException {
    private static final long serialVersionUID = 13038402904505L;
    private final String mErrnoMessage;
    @NonNull
    public final GifError reason;

    public String getMessage() {
        if (this.mErrnoMessage == null) {
            return this.reason.a();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.reason.a());
        sb.append(": ");
        sb.append(this.mErrnoMessage);
        return sb.toString();
    }

    private GifIOException(int i, String str) {
        this.reason = GifError.a(i);
        this.mErrnoMessage = str;
    }

    static GifIOException a(int i) {
        if (i == GifError.NO_ERROR.a) {
            return null;
        }
        return new GifIOException(i, null);
    }
}
