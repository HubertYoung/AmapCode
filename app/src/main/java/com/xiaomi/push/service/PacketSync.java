package com.xiaomi.push.service;

import android.content.Context;
import android.os.Parcelable;
import android.text.TextUtils;
import com.xiaomi.network.Fallback;
import com.xiaomi.network.HostManager;
import com.xiaomi.push.protobuf.b.C0082b;
import com.xiaomi.push.protobuf.b.i;
import com.xiaomi.push.protobuf.b.j;
import com.xiaomi.push.protobuf.b.k;
import com.xiaomi.push.service.aq.c;
import com.xiaomi.smack.b;
import com.xiaomi.smack.packet.a;
import com.xiaomi.smack.packet.d;
import com.xiaomi.smack.util.g;
import com.xiaomi.stats.h;
import java.util.Date;

public class PacketSync {
    private XMPushService a;

    public interface PacketReceiveHandler extends Parcelable {
    }

    PacketSync(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    private void a(a aVar) {
        String c = aVar.c();
        if (!TextUtils.isEmpty(c)) {
            String[] split = c.split(";");
            Fallback fallbacksByHost = HostManager.getInstance().getFallbacksByHost(b.b(), false);
            if (fallbacksByHost != null && split.length > 0) {
                fallbacksByHost.a(split);
                this.a.a(20, (Exception) null);
                this.a.a(true);
            }
        }
    }

    private void b(d dVar) {
        String m = dVar.m();
        String l = dVar.l();
        if (!TextUtils.isEmpty(m) && !TextUtils.isEmpty(l)) {
            aq.b b = aq.a().b(l, m);
            if (b != null) {
                g.a(this.a, b.a, (long) g.a(dVar.c()), true, System.currentTimeMillis());
            }
        }
    }

    private void c(com.xiaomi.slim.b bVar) {
        String j = bVar.j();
        String num = Integer.toString(bVar.c());
        if (!TextUtils.isEmpty(j) && !TextUtils.isEmpty(num)) {
            aq.b b = aq.a().b(num, j);
            if (b != null) {
                g.a(this.a, b.a, (long) bVar.l(), true, System.currentTimeMillis());
            }
        }
    }

    public void a(com.xiaomi.slim.b bVar) {
        if (5 != bVar.c()) {
            c(bVar);
        }
        try {
            b(bVar);
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("handle Blob chid = ");
            sb.append(bVar.c());
            sb.append(" cmd = ");
            sb.append(bVar.a());
            sb.append(" packetid = ");
            sb.append(bVar.h());
            sb.append(" failure ");
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString(), (Throwable) e);
        }
    }

    public void a(d dVar) {
        if (!"5".equals(dVar.l())) {
            b(dVar);
        }
        String l = dVar.l();
        if (TextUtils.isEmpty(l)) {
            l = "1";
            dVar.l(l);
        }
        if (l.equals("0")) {
            StringBuilder sb = new StringBuilder("Received wrong packet with chid = 0 : ");
            sb.append(dVar.c());
            com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
        }
        if (dVar instanceof com.xiaomi.smack.packet.b) {
            a p = dVar.p("kick");
            if (p != null) {
                String m = dVar.m();
                String a2 = p.a((String) "type");
                String a3 = p.a((String) "reason");
                StringBuilder sb2 = new StringBuilder("kicked by server, chid=");
                sb2.append(l);
                sb2.append(" userid=");
                sb2.append(m);
                sb2.append(" type=");
                sb2.append(a2);
                sb2.append(" reason=");
                sb2.append(a3);
                com.xiaomi.channel.commonutils.logger.b.a(sb2.toString());
                if ("wait".equals(a2)) {
                    aq.b b = aq.a().b(l, m);
                    if (b != null) {
                        this.a.a(b);
                        b.a(c.unbind, 3, 0, a3, a2);
                    }
                    return;
                }
                this.a.a(l, m, 3, a3, a2);
                aq.a().a(l, m);
                return;
            }
        } else if (dVar instanceof com.xiaomi.smack.packet.c) {
            com.xiaomi.smack.packet.c cVar = (com.xiaomi.smack.packet.c) dVar;
            if ("redir".equals(cVar.a())) {
                a p2 = cVar.p("hosts");
                if (p2 != null) {
                    a(p2);
                }
                return;
            }
        }
        this.a.e().a(this.a, l, dVar);
    }

    public void b(com.xiaomi.slim.b bVar) {
        c cVar;
        int i;
        int i2;
        String a2 = bVar.a();
        if (bVar.c() != 0) {
            String num = Integer.toString(bVar.c());
            if ("SECMSG".equals(bVar.a())) {
                if (!bVar.d()) {
                    this.a.e().a(this.a, num, bVar);
                    return;
                }
                StringBuilder sb = new StringBuilder("Recv SECMSG errCode = ");
                sb.append(bVar.e());
                sb.append(" errStr = ");
                sb.append(bVar.f());
                com.xiaomi.channel.commonutils.logger.b.a(sb.toString());
            } else if ("BIND".equals(a2)) {
                com.xiaomi.push.protobuf.b.d b = com.xiaomi.push.protobuf.b.d.b(bVar.k());
                String j = bVar.j();
                aq.b b2 = aq.a().b(num, j);
                if (b2 != null) {
                    if (b.d()) {
                        StringBuilder sb2 = new StringBuilder("SMACK: channel bind succeeded, chid=");
                        sb2.append(bVar.c());
                        com.xiaomi.channel.commonutils.logger.b.a(sb2.toString());
                        b2.a(c.binded, 1, 0, (String) null, (String) null);
                        return;
                    }
                    String f = b.f();
                    if ("auth".equals(f)) {
                        if ("invalid-sig".equals(b.h())) {
                            StringBuilder sb3 = new StringBuilder("SMACK: bind error invalid-sig token = ");
                            sb3.append(b2.c);
                            sb3.append(" sec = ");
                            sb3.append(b2.i);
                            com.xiaomi.channel.commonutils.logger.b.a(sb3.toString());
                            h.a(0, com.xiaomi.push.thrift.a.BIND_INVALID_SIG.a(), 1, null, 0);
                        }
                        cVar = c.unbind;
                        i = 1;
                        i2 = 5;
                    } else if ("cancel".equals(f)) {
                        cVar = c.unbind;
                        i = 1;
                        i2 = 7;
                    } else {
                        if ("wait".equals(f)) {
                            this.a.a(b2);
                            b2.a(c.unbind, 1, 7, b.h(), f);
                        }
                        StringBuilder sb4 = new StringBuilder("SMACK: channel bind failed, chid=");
                        sb4.append(num);
                        sb4.append(" reason=");
                        sb4.append(b.h());
                        com.xiaomi.channel.commonutils.logger.b.a(sb4.toString());
                    }
                    b2.a(cVar, i, i2, b.h(), f);
                    aq.a().a(num, j);
                    StringBuilder sb42 = new StringBuilder("SMACK: channel bind failed, chid=");
                    sb42.append(num);
                    sb42.append(" reason=");
                    sb42.append(b.h());
                    com.xiaomi.channel.commonutils.logger.b.a(sb42.toString());
                }
            } else if ("KICK".equals(a2)) {
                com.xiaomi.push.protobuf.b.g b3 = com.xiaomi.push.protobuf.b.g.b(bVar.k());
                String j2 = bVar.j();
                String d = b3.d();
                String f2 = b3.f();
                StringBuilder sb5 = new StringBuilder("kicked by server, chid=");
                sb5.append(num);
                sb5.append(" userid=");
                sb5.append(j2);
                sb5.append(" type=");
                sb5.append(d);
                sb5.append(" reason=");
                sb5.append(f2);
                com.xiaomi.channel.commonutils.logger.b.a(sb5.toString());
                if ("wait".equals(d)) {
                    aq.b b4 = aq.a().b(num, j2);
                    if (b4 != null) {
                        this.a.a(b4);
                        b4.a(c.unbind, 3, 0, f2, d);
                    }
                    return;
                }
                this.a.a(num, j2, 3, f2, d);
                aq.a().a(num, j2);
            }
        } else if ("PING".equals(a2)) {
            byte[] k = bVar.k();
            if (k != null && k.length > 0) {
                j b5 = j.b(k);
                if (b5.f()) {
                    ba.a().a(b5.g());
                }
            }
            if ("1".equals(bVar.h())) {
                this.a.a();
            } else {
                h.b();
            }
            this.a.i();
        } else if ("SYNC".equals(a2)) {
            if ("CONF".equals(bVar.b())) {
                ba.a().a(C0082b.b(bVar.k()));
            } else if (TextUtils.equals("U", bVar.b())) {
                k b6 = k.b(bVar.k());
                com.xiaomi.push.log.b.a((Context) this.a).a(b6.d(), b6.f(), new Date(b6.h()), new Date(b6.j()), b6.n() * 1024, b6.l());
                com.xiaomi.slim.b bVar2 = new com.xiaomi.slim.b();
                bVar2.a(0);
                bVar2.a(bVar.a(), (String) "UCA");
                bVar2.a(bVar.h());
                this.a.a((XMPushService.h) new az(this.a, bVar2));
            } else if (TextUtils.equals("P", bVar.b())) {
                i b7 = i.b(bVar.k());
                com.xiaomi.slim.b bVar3 = new com.xiaomi.slim.b();
                bVar3.a(0);
                bVar3.a(bVar.a(), (String) "PCA");
                bVar3.a(bVar.h());
                i iVar = new i();
                if (b7.e()) {
                    iVar.a(b7.d());
                }
                bVar3.a(iVar.c(), (String) null);
                this.a.a((XMPushService.h) new az(this.a, bVar3));
                StringBuilder sb6 = new StringBuilder("ACK msgP: id = ");
                sb6.append(bVar.h());
                com.xiaomi.channel.commonutils.logger.b.a(sb6.toString());
            }
        } else if ("NOTIFY".equals(bVar.a())) {
            com.xiaomi.push.protobuf.b.h b8 = com.xiaomi.push.protobuf.b.h.b(bVar.k());
            StringBuilder sb7 = new StringBuilder("notify by server err = ");
            sb7.append(b8.d());
            sb7.append(" desc = ");
            sb7.append(b8.f());
            com.xiaomi.channel.commonutils.logger.b.a(sb7.toString());
        }
    }
}
