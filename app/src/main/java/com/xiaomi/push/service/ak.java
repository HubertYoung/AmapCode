package com.xiaomi.push.service;

import android.os.Process;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.xiaomi.channel.commonutils.file.a;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.network.Host;
import com.xiaomi.push.protobuf.a.C0081a;
import com.xiaomi.stats.f;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class ak {
    private static final Pattern a = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
    private static long b;
    private static ThreadPoolExecutor c;

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, new LinkedBlockingQueue());
        c = threadPoolExecutor;
    }

    public static void a() {
        long currentTimeMillis = System.currentTimeMillis();
        if ((c.getActiveCount() <= 0 || currentTimeMillis - b >= TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL) && f.a().c()) {
            C0081a d = ba.a().d();
            if (d != null && d.m() > 0) {
                b = currentTimeMillis;
                a(d.l(), true);
            }
        }
    }

    public static void a(List<String> list, boolean z) {
        c.execute(new al(list, z));
    }

    public static void b() {
        String c2 = c("/proc/self/net/tcp");
        if (!TextUtils.isEmpty(c2)) {
            StringBuilder sb = new StringBuilder("dump tcp for uid = ");
            sb.append(Process.myUid());
            b.a(sb.toString());
            b.a(c2);
        }
        String c3 = c("/proc/self/net/tcp6");
        if (!TextUtils.isEmpty(c3)) {
            StringBuilder sb2 = new StringBuilder("dump tcp6 for uid = ");
            sb2.append(Process.myUid());
            b.a(sb2.toString());
            b.a(c3);
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            b.a("ConnectivityTest: begin to connect to ".concat(String.valueOf(str)));
            Socket socket = new Socket();
            socket.connect(Host.b(str, 5222), 5000);
            socket.setTcpNoDelay(true);
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            StringBuilder sb = new StringBuilder("ConnectivityTest: connect to ");
            sb.append(str);
            sb.append(" in ");
            sb.append(currentTimeMillis2);
            b.a(sb.toString());
            socket.close();
            return true;
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder("ConnectivityTest: could not connect to:");
            sb2.append(str);
            sb2.append(" exception: ");
            sb2.append(th.getClass().getSimpleName());
            sb2.append(" description: ");
            sb2.append(th.getMessage());
            b.d(sb2.toString());
            return false;
        }
    }

    private static String c(String str) {
        BufferedReader bufferedReader;
        Throwable th;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(str)));
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        sb.append("\n");
                        sb.append(readLine);
                    } else {
                        String sb2 = sb.toString();
                        a.a((Reader) bufferedReader);
                        return sb2;
                    }
                }
            } catch (Exception unused) {
                a.a((Reader) bufferedReader);
                return null;
            } catch (Throwable th2) {
                th = th2;
                a.a((Reader) bufferedReader);
                throw th;
            }
        } catch (Exception unused2) {
            bufferedReader = null;
            a.a((Reader) bufferedReader);
            return null;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
            a.a((Reader) bufferedReader);
            throw th;
        }
    }
}
