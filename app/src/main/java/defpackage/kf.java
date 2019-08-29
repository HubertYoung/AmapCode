package defpackage;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/* renamed from: kf reason: default package */
/* compiled from: CatchExceptionUtil */
public final class kf {
    public static void a(Error error) {
        error.printStackTrace();
        throw new RuntimeException(error);
    }

    public static void a(Throwable th) {
        th.printStackTrace();
        if (!b(th)) {
            throw new RuntimeException(th);
        }
    }

    private static boolean b(Throwable th) {
        if (!(th instanceof InterruptedException) && !(th instanceof SocketException) && !(th instanceof ConnectException) && !(th instanceof IOException) && !(th instanceof SocketTimeoutException) && !(th instanceof UnknownHostException)) {
            return false;
        }
        return true;
    }
}
