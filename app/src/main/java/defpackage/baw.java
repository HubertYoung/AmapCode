package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: baw reason: default package */
/* compiled from: RouteSaveServiceImpl */
public class baw implements btr {
    public final String a(Object obj, int i) {
        String str = null;
        if (i != 0) {
            switch (i) {
                case 2:
                    atb atb = (atb) a.a.a(atb.class);
                    if (atb != null) {
                        return atb.e().a(obj);
                    }
                    break;
                case 3:
                    try {
                        avi avi = (avi) a.a.a(avi.class);
                        if (avi != null) {
                            str = avi.b().a(obj);
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        break;
                    }
                    break;
            }
        } else {
            asy asy = (asy) a.a.a(asy.class);
            if (asy != null) {
                return asy.c().a(obj);
            }
        }
        return str;
    }

    public final Object a(String str, int i) {
        if (i != 0) {
            switch (i) {
                case 2:
                    atb atb = (atb) a.a.a(atb.class);
                    if (atb != null) {
                        return atb.e().b(new JSONObject(str));
                    }
                    break;
                case 3:
                    try {
                        avi avi = (avi) a.a.a(avi.class);
                        if (avi != null) {
                            avi.b().a(str);
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        break;
                    }
                    break;
            }
        } else {
            asy asy = (asy) a.a.a(asy.class);
            if (asy != null) {
                asy.c().b(new JSONObject(str));
            }
        }
        return null;
    }

    public final boolean b(Object obj, int i) {
        if (i != 0) {
            switch (i) {
                case 2:
                    atb atb = (atb) a.a.a(atb.class);
                    return atb != null && atb.e().b(obj);
                case 3:
                    avi avi = (avi) a.a.a(avi.class);
                    if (avi != null) {
                        avi.b();
                    }
                    return false;
                default:
                    return false;
            }
        } else {
            atb atb2 = (atb) a.a.a(atb.class);
            return atb2 != null && atb2.e().c(obj);
        }
    }
}
