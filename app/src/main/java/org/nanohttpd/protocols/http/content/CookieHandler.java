package org.nanohttpd.protocols.http.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.nanohttpd.protocols.http.response.Response;

public class CookieHandler implements Iterable<String> {
    private final HashMap<String, String> a = new HashMap<>();
    private final ArrayList<Cookie> b = new ArrayList<>();

    public CookieHandler(Map<String, String> httpHeaders) {
        String raw = httpHeaders.get("cookie");
        if (raw != null) {
            for (String trim : raw.split(";")) {
                String[] data = trim.trim().split("=");
                if (data.length == 2) {
                    this.a.put(data[0], data[1]);
                }
            }
        }
    }

    public void delete(String name) {
        set(name, "-delete-", -30);
    }

    public Iterator<String> iterator() {
        return this.a.keySet().iterator();
    }

    public String read(String name) {
        return this.a.get(name);
    }

    public void set(Cookie cookie) {
        this.b.add(cookie);
    }

    public void set(String name, String value, int expires) {
        this.b.add(new Cookie(name, value, Cookie.getHTTPTime(expires)));
    }

    public void unloadQueue(Response response) {
        Iterator<Cookie> it = this.b.iterator();
        while (it.hasNext()) {
            response.addCookieHeader(it.next().getHTTPHeader());
        }
    }
}
