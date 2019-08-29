package android.webkit;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class ConsoleMessage {
    private MessageLevel a;
    private String b;
    private String c;
    private int d;

    public enum MessageLevel {
        TIP,
        LOG,
        WARNING,
        ERROR,
        DEBUG
    }

    public ConsoleMessage(String message, String sourceId, int lineNumber, MessageLevel msgLevel) {
        this.b = message;
        this.c = sourceId;
        this.d = lineNumber;
        this.a = msgLevel;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public MessageLevel messageLevel() {
        return this.a;
    }

    public String message() {
        return this.b;
    }

    public String sourceId() {
        return this.c;
    }

    public int lineNumber() {
        return this.d;
    }
}
