package defpackage;

import com.autonavi.minimap.bl.net.HttpRequest;

/* renamed from: ctb reason: default package */
/* compiled from: NetworkFeature */
public final class ctb {
    private static final String[] a = {"flag_request_cdn_mac", "flag_request_record_point"};

    public static boolean a(HttpRequest httpRequest) {
        return httpRequest.getHeaders() != null && httpRequest.getHeaders().containsKey("flag_request_cdn_mac");
    }

    public static boolean b(HttpRequest httpRequest) {
        return httpRequest.getHeaders() != null && httpRequest.getHeaders().containsKey("flag_request_record_point");
    }

    public static boolean a(String str) {
        for (String equals : a) {
            if (equals.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static String c(HttpRequest httpRequest) {
        return b(httpRequest) ? httpRequest.getHeaders().get("flag_request_record_point") : "";
    }
}
