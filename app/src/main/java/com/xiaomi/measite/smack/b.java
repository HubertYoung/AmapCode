package com.xiaomi.measite.smack;

import com.xiaomi.smack.a;
import com.xiaomi.smack.d;
import java.util.Date;

class b implements d {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void a(a aVar) {
        StringBuilder sb = new StringBuilder("[Slim] ");
        sb.append(this.a.b.format(new Date()));
        sb.append(" Connection reconnected (");
        sb.append(this.a.c.hashCode());
        sb.append(")");
        com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
    }

    public void a(a aVar, int i, Exception exc) {
        StringBuilder sb = new StringBuilder("[Slim] ");
        sb.append(this.a.b.format(new Date()));
        sb.append(" Connection closed (");
        sb.append(this.a.c.hashCode());
        sb.append(")");
        com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
    }

    public void a(a aVar, Exception exc) {
        StringBuilder sb = new StringBuilder("[Slim] ");
        sb.append(this.a.b.format(new Date()));
        sb.append(" Reconnection failed due to an exception (");
        sb.append(this.a.c.hashCode());
        sb.append(")");
        com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
        exc.printStackTrace();
    }

    public void b(a aVar) {
        StringBuilder sb = new StringBuilder("[Slim] ");
        sb.append(this.a.b.format(new Date()));
        sb.append(" Connection started (");
        sb.append(this.a.c.hashCode());
        sb.append(")");
        com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
    }
}
