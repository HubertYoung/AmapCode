package defpackage;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.URISyntaxException;
import java.util.HashSet;
import org.json.JSONException;

/* renamed from: bom reason: default package */
/* compiled from: HurlRetryHandler */
final class bom {
    static HashSet<Class<?>> a;
    int b;

    static {
        HashSet<Class<?>> hashSet = new HashSet<>();
        a = hashSet;
        hashSet.add(MalformedURLException.class);
        a.add(URISyntaxException.class);
        a.add(NoRouteToHostException.class);
        a.add(PortUnreachableException.class);
        a.add(NullPointerException.class);
        a.add(FileNotFoundException.class);
        a.add(JSONException.class);
    }

    public bom() {
        this(3);
    }

    public bom(int i) {
        this.b = i;
    }
}
