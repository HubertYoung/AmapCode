package defpackage;

import android.support.annotation.Nullable;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* renamed from: bpa reason: default package */
/* compiled from: INetResponse */
public interface bpa {
    InputStream getBodyInputStream();

    long getContentLength();

    @Nullable
    String getHeader(String str);

    Map<String, List<String>> getHeaders();

    int getStatusCode();
}
