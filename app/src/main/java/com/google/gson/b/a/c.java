package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.b.e;
import com.google.gson.b.j;
import com.google.gson.c.a;
import com.google.gson.d.b;
import com.google.gson.r;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* compiled from: DateTypeAdapter */
public final class c extends t<Date> {
    public static final u a = new u() {
        public final <T> t<T> a(Gson gson, a<T> aVar) {
            if (aVar.a() == Date.class) {
                return new c();
            }
            return null;
        }
    };
    private final List<DateFormat> b = new ArrayList();

    public c() {
        this.b.add(DateFormat.getDateTimeInstance(2, 2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.b.add(DateFormat.getDateTimeInstance(2, 2));
        }
        if (e.b()) {
            this.b.add(j.a(2, 2));
        }
    }

    /* renamed from: a */
    public final Date b(com.google.gson.d.a aVar) throws IOException {
        if (aVar.f() != b.NULL) {
            return a(aVar.h());
        }
        aVar.j();
        return null;
    }

    private synchronized Date a(String str) {
        for (DateFormat parse : this.b) {
            try {
                return parse.parse(str);
            } catch (ParseException unused) {
            }
        }
        try {
            return com.google.gson.b.a.a.a.a(str, new ParsePosition(0));
        } catch (ParseException e) {
            throw new r(str, e);
        }
    }

    public final synchronized void a(com.google.gson.d.c cVar, Date date) throws IOException {
        if (date == null) {
            cVar.f();
        } else {
            cVar.b(this.b.get(0).format(date));
        }
    }
}
