package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.c.a;
import com.google.gson.d.b;
import com.google.gson.d.c;
import com.google.gson.r;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* compiled from: SqlDateTypeAdapter */
public final class j extends t<Date> {
    public static final u a = new u() {
        public final <T> t<T> a(Gson gson, a<T> aVar) {
            if (aVar.a() == Date.class) {
                return new j();
            }
            return null;
        }
    };
    private final DateFormat b = new SimpleDateFormat("MMM d, yyyy");

    /* renamed from: a */
    public final synchronized Date b(com.google.gson.d.a aVar) throws IOException {
        if (aVar.f() == b.NULL) {
            aVar.j();
            return null;
        }
        try {
            return new Date(this.b.parse(aVar.h()).getTime());
        } catch (ParseException e) {
            throw new r((Throwable) e);
        }
    }

    public final synchronized void a(c cVar, Date date) throws IOException {
        cVar.b(date == null ? null : this.b.format(date));
    }
}
