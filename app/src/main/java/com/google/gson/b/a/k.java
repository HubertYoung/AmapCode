package com.google.gson.b.a;

import com.google.gson.Gson;
import com.google.gson.c.a;
import com.google.gson.d.b;
import com.google.gson.d.c;
import com.google.gson.r;
import com.google.gson.t;
import com.google.gson.u;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* compiled from: TimeTypeAdapter */
public final class k extends t<Time> {
    public static final u a = new u() {
        public final <T> t<T> a(Gson gson, a<T> aVar) {
            if (aVar.a() == Time.class) {
                return new k();
            }
            return null;
        }
    };
    private final DateFormat b = new SimpleDateFormat("hh:mm:ss a");

    /* renamed from: a */
    public final synchronized Time b(com.google.gson.d.a aVar) throws IOException {
        if (aVar.f() == b.NULL) {
            aVar.j();
            return null;
        }
        try {
            return new Time(this.b.parse(aVar.h()).getTime());
        } catch (ParseException e) {
            throw new r((Throwable) e);
        }
    }

    public final synchronized void a(c cVar, Time time) throws IOException {
        cVar.b(time == null ? null : this.b.format(time));
    }
}
