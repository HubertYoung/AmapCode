package org.androidannotations.api;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ViewServer implements Runnable {
    private static ViewServer j;
    private ServerSocket a;
    private final int b;
    private Thread c;
    private ExecutorService d;
    private final List<WindowListener> e;
    /* access modifiers changed from: private */
    public final Map<View, String> f;
    /* access modifiers changed from: private */
    public final ReentrantReadWriteLock g;
    /* access modifiers changed from: private */
    public View h;
    /* access modifiers changed from: private */
    public final ReentrantReadWriteLock i;

    final class NoopViewServer extends ViewServer {
        private NoopViewServer() {
            super();
        }

        public final boolean start() {
            return false;
        }

        public final boolean stop() {
            return false;
        }

        public final boolean isRunning() {
            return false;
        }

        public final void addWindow(Activity activity) {
        }

        public final void removeWindow(Activity activity) {
        }

        public final void addWindow(View view, String name) {
        }

        public final void removeWindow(View view) {
        }

        public final void setFocusedWindow(Activity activity) {
        }

        public final void setFocusedWindow(View view) {
        }

        public final void run() {
        }
    }

    class UncloseableOuputStream extends OutputStream {
        private final OutputStream a;

        UncloseableOuputStream(OutputStream stream) {
            this.a = stream;
        }

        public void close() {
        }

        public boolean equals(Object o) {
            return this.a.equals(o);
        }

        public void flush() {
            this.a.flush();
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            return this.a.toString();
        }

        public void write(byte[] buffer, int offset, int count) {
            this.a.write(buffer, offset, count);
        }

        public void write(byte[] buffer) {
            this.a.write(buffer);
        }

        public void write(int oneByte) {
            this.a.write(oneByte);
        }
    }

    class ViewServerWorker implements Runnable, WindowListener {
        private Socket a;
        private boolean b;
        private boolean c;
        private final Object[] d;

        private ViewServerWorker(Socket client) {
            this.d = new Object[0];
            this.a = client;
            this.b = false;
            this.c = false;
        }

        /* JADX WARNING: Removed duplicated region for block: B:44:0x00bb A[SYNTHETIC, Splitter:B:44:0x00bb] */
        /* JADX WARNING: Removed duplicated region for block: B:48:0x00c2 A[SYNTHETIC, Splitter:B:48:0x00c2] */
        /* JADX WARNING: Removed duplicated region for block: B:56:0x00d5 A[SYNTHETIC, Splitter:B:56:0x00d5] */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x00dc A[SYNTHETIC, Splitter:B:60:0x00dc] */
        /* JADX WARNING: Removed duplicated region for block: B:74:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r11 = this;
                r2 = 0
                java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ IOException -> 0x00b1 }
                java.io.InputStreamReader r8 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x00b1 }
                java.net.Socket r9 = r11.a     // Catch:{ IOException -> 0x00b1 }
                java.io.InputStream r9 = r9.getInputStream()     // Catch:{ IOException -> 0x00b1 }
                r8.<init>(r9)     // Catch:{ IOException -> 0x00b1 }
                r9 = 1024(0x400, float:1.435E-42)
                r3.<init>(r8, r9)     // Catch:{ IOException -> 0x00b1 }
                java.lang.String r6 = r3.readLine()     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                r8 = 32
                int r4 = r6.indexOf(r8)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                r8 = -1
                if (r4 != r8) goto L_0x0057
                r0 = r6
                java.lang.String r5 = ""
            L_0x0023:
                java.lang.String r8 = "PROTOCOL"
                boolean r8 = r8.equalsIgnoreCase(r0)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                if (r8 == 0) goto L_0x0063
                java.net.Socket r8 = r11.a     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                java.lang.String r9 = "4"
                boolean r7 = org.androidannotations.api.ViewServer.a(r8, r9)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
            L_0x0033:
                if (r7 != 0) goto L_0x0049
                java.lang.String r8 = "ViewServer"
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                java.lang.String r10 = "An error occurred with the command: "
                r9.<init>(r10)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                java.lang.StringBuilder r9 = r9.append(r0)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                java.lang.String r9 = r9.toString()     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                android.util.Log.w(r8, r9)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
            L_0x0049:
                r3.close()     // Catch:{ IOException -> 0x00a6 }
            L_0x004c:
                java.net.Socket r8 = r11.a
                if (r8 == 0) goto L_0x00f2
                java.net.Socket r8 = r11.a     // Catch:{ IOException -> 0x00ab }
                r8.close()     // Catch:{ IOException -> 0x00ab }
                r2 = r3
            L_0x0056:
                return
            L_0x0057:
                r8 = 0
                java.lang.String r0 = r6.substring(r8, r4)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                int r8 = r4 + 1
                java.lang.String r5 = r6.substring(r8)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                goto L_0x0023
            L_0x0063:
                java.lang.String r8 = "SERVER"
                boolean r8 = r8.equalsIgnoreCase(r0)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                if (r8 == 0) goto L_0x0074
                java.net.Socket r8 = r11.a     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                java.lang.String r9 = "4"
                boolean r7 = org.androidannotations.api.ViewServer.a(r8, r9)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                goto L_0x0033
            L_0x0074:
                java.lang.String r8 = "LIST"
                boolean r8 = r8.equalsIgnoreCase(r0)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                if (r8 == 0) goto L_0x0083
                java.net.Socket r8 = r11.a     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                boolean r7 = r11.a(r8)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                goto L_0x0033
            L_0x0083:
                java.lang.String r8 = "GET_FOCUS"
                boolean r8 = r8.equalsIgnoreCase(r0)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                if (r8 == 0) goto L_0x0092
                java.net.Socket r8 = r11.a     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                boolean r7 = r11.b(r8)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                goto L_0x0033
            L_0x0092:
                java.lang.String r8 = "AUTOLIST"
                boolean r8 = r8.equalsIgnoreCase(r0)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                if (r8 == 0) goto L_0x009f
                boolean r7 = r11.a()     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                goto L_0x0033
            L_0x009f:
                java.net.Socket r8 = r11.a     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                boolean r7 = r11.a(r8, r0, r5)     // Catch:{ IOException -> 0x00ef, all -> 0x00ec }
                goto L_0x0033
            L_0x00a6:
                r8 = move-exception
                r8.printStackTrace()
                goto L_0x004c
            L_0x00ab:
                r8 = move-exception
                r8.printStackTrace()
                r2 = r3
                goto L_0x0056
            L_0x00b1:
                r1 = move-exception
            L_0x00b2:
                java.lang.String r8 = "ViewServer"
                java.lang.String r9 = "Connection error: "
                android.util.Log.w(r8, r9, r1)     // Catch:{ all -> 0x00d2 }
                if (r2 == 0) goto L_0x00be
                r2.close()     // Catch:{ IOException -> 0x00cd }
            L_0x00be:
                java.net.Socket r8 = r11.a
                if (r8 == 0) goto L_0x0056
                java.net.Socket r8 = r11.a     // Catch:{ IOException -> 0x00c8 }
                r8.close()     // Catch:{ IOException -> 0x00c8 }
                goto L_0x0056
            L_0x00c8:
                r8 = move-exception
                r8.printStackTrace()
                goto L_0x0056
            L_0x00cd:
                r8 = move-exception
                r8.printStackTrace()
                goto L_0x00be
            L_0x00d2:
                r8 = move-exception
            L_0x00d3:
                if (r2 == 0) goto L_0x00d8
                r2.close()     // Catch:{ IOException -> 0x00e2 }
            L_0x00d8:
                java.net.Socket r9 = r11.a
                if (r9 == 0) goto L_0x00e1
                java.net.Socket r9 = r11.a     // Catch:{ IOException -> 0x00e7 }
                r9.close()     // Catch:{ IOException -> 0x00e7 }
            L_0x00e1:
                throw r8
            L_0x00e2:
                r9 = move-exception
                r9.printStackTrace()
                goto L_0x00d8
            L_0x00e7:
                r9 = move-exception
                r9.printStackTrace()
                goto L_0x00e1
            L_0x00ec:
                r8 = move-exception
                r2 = r3
                goto L_0x00d3
            L_0x00ef:
                r1 = move-exception
                r2 = r3
                goto L_0x00b2
            L_0x00f2:
                r2 = r3
                goto L_0x0056
            */
            throw new UnsupportedOperationException("Method not decompiled: org.androidannotations.api.ViewServer.ViewServerWorker.run():void");
        }

        /* JADX WARNING: Removed duplicated region for block: B:31:0x00c4 A[SYNTHETIC, Splitter:B:31:0x00c4] */
        /* JADX WARNING: Removed duplicated region for block: B:37:0x00ce A[SYNTHETIC, Splitter:B:37:0x00ce] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean a(java.net.Socket r15, java.lang.String r16, java.lang.String r17) {
            /*
                r14 = this;
                r7 = 1
                r5 = 0
                r9 = 32
                r0 = r17
                int r4 = r0.indexOf(r9)     // Catch:{ Exception -> 0x009e }
                r9 = -1
                if (r4 != r9) goto L_0x0011
                int r4 = r17.length()     // Catch:{ Exception -> 0x009e }
            L_0x0011:
                r9 = 0
                r0 = r17
                java.lang.String r9 = r0.substring(r9, r4)     // Catch:{ Exception -> 0x009e }
                r10 = 16
                long r9 = java.lang.Long.parseLong(r9, r10)     // Catch:{ Exception -> 0x009e }
                int r3 = (int) r9     // Catch:{ Exception -> 0x009e }
                int r9 = r17.length()     // Catch:{ Exception -> 0x009e }
                if (r4 >= r9) goto L_0x0035
                int r9 = r4 + 1
                r0 = r17
                java.lang.String r17 = r0.substring(r9)     // Catch:{ Exception -> 0x009e }
            L_0x002d:
                android.view.View r8 = r14.a(r3)     // Catch:{ Exception -> 0x009e }
                if (r8 != 0) goto L_0x0038
                r9 = 0
            L_0x0034:
                return r9
            L_0x0035:
                java.lang.String r17 = ""
                goto L_0x002d
            L_0x0038:
                java.lang.Class<android.view.ViewDebug> r9 = android.view.ViewDebug.class
                java.lang.String r10 = "dispatchCommand"
                r11 = 4
                java.lang.Class[] r11 = new java.lang.Class[r11]     // Catch:{ Exception -> 0x009e }
                r12 = 0
                java.lang.Class<android.view.View> r13 = android.view.View.class
                r11[r12] = r13     // Catch:{ Exception -> 0x009e }
                r12 = 1
                java.lang.Class<java.lang.String> r13 = java.lang.String.class
                r11[r12] = r13     // Catch:{ Exception -> 0x009e }
                r12 = 2
                java.lang.Class<java.lang.String> r13 = java.lang.String.class
                r11[r12] = r13     // Catch:{ Exception -> 0x009e }
                r12 = 3
                java.lang.Class<java.io.OutputStream> r13 = java.io.OutputStream.class
                r11[r12] = r13     // Catch:{ Exception -> 0x009e }
                java.lang.reflect.Method r1 = r9.getDeclaredMethod(r10, r11)     // Catch:{ Exception -> 0x009e }
                r9 = 1
                r1.setAccessible(r9)     // Catch:{ Exception -> 0x009e }
                r9 = 0
                r10 = 4
                java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ Exception -> 0x009e }
                r11 = 0
                r10[r11] = r8     // Catch:{ Exception -> 0x009e }
                r11 = 1
                r10[r11] = r16     // Catch:{ Exception -> 0x009e }
                r11 = 2
                r10[r11] = r17     // Catch:{ Exception -> 0x009e }
                r11 = 3
                org.androidannotations.api.ViewServer$UncloseableOuputStream r12 = new org.androidannotations.api.ViewServer$UncloseableOuputStream     // Catch:{ Exception -> 0x009e }
                java.io.OutputStream r13 = r15.getOutputStream()     // Catch:{ Exception -> 0x009e }
                r12.<init>(r13)     // Catch:{ Exception -> 0x009e }
                r10[r11] = r12     // Catch:{ Exception -> 0x009e }
                r1.invoke(r9, r10)     // Catch:{ Exception -> 0x009e }
                boolean r9 = r15.isOutputShutdown()     // Catch:{ Exception -> 0x009e }
                if (r9 != 0) goto L_0x0094
                java.io.BufferedWriter r6 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x009e }
                java.io.OutputStreamWriter r9 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x009e }
                java.io.OutputStream r10 = r15.getOutputStream()     // Catch:{ Exception -> 0x009e }
                r9.<init>(r10)     // Catch:{ Exception -> 0x009e }
                r6.<init>(r9)     // Catch:{ Exception -> 0x009e }
                java.lang.String r9 = "DONE\n"
                r6.write(r9)     // Catch:{ Exception -> 0x00d7, all -> 0x00d4 }
                r6.flush()     // Catch:{ Exception -> 0x00d7, all -> 0x00d4 }
                r5 = r6
            L_0x0094:
                if (r5 == 0) goto L_0x0099
                r5.close()     // Catch:{ IOException -> 0x009b }
            L_0x0099:
                r9 = r7
                goto L_0x0034
            L_0x009b:
                r9 = move-exception
                r7 = 0
                goto L_0x0099
            L_0x009e:
                r2 = move-exception
            L_0x009f:
                java.lang.String r9 = "ViewServer"
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x00cb }
                java.lang.String r11 = "Could not send command "
                r10.<init>(r11)     // Catch:{ all -> 0x00cb }
                r0 = r16
                java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ all -> 0x00cb }
                java.lang.String r11 = " with parameters "
                java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ all -> 0x00cb }
                r0 = r17
                java.lang.StringBuilder r10 = r10.append(r0)     // Catch:{ all -> 0x00cb }
                java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x00cb }
                android.util.Log.w(r9, r10, r2)     // Catch:{ all -> 0x00cb }
                r7 = 0
                if (r5 == 0) goto L_0x0099
                r5.close()     // Catch:{ IOException -> 0x00c8 }
                goto L_0x0099
            L_0x00c8:
                r9 = move-exception
                r7 = 0
                goto L_0x0099
            L_0x00cb:
                r9 = move-exception
            L_0x00cc:
                if (r5 == 0) goto L_0x00d1
                r5.close()     // Catch:{ IOException -> 0x00d2 }
            L_0x00d1:
                throw r9
            L_0x00d2:
                r10 = move-exception
                goto L_0x00d1
            L_0x00d4:
                r9 = move-exception
                r5 = r6
                goto L_0x00cc
            L_0x00d7:
                r2 = move-exception
                r5 = r6
                goto L_0x009f
            */
            throw new UnsupportedOperationException("Method not decompiled: org.androidannotations.api.ViewServer.ViewServerWorker.a(java.net.Socket, java.lang.String, java.lang.String):boolean");
        }

        /* JADX INFO: finally extract failed */
        private View a(int hashCode) {
            if (hashCode == -1) {
                ViewServer.this.g.readLock().lock();
                try {
                    return ViewServer.this.h;
                } finally {
                    ViewServer.this.g.readLock().unlock();
                }
            } else {
                ViewServer.this.g.readLock().lock();
                try {
                    for (Entry entry : ViewServer.this.f.entrySet()) {
                        if (System.identityHashCode(entry.getKey()) == hashCode) {
                            View window = (View) entry.getKey();
                            ViewServer.this.g.readLock().unlock();
                            return window;
                        }
                    }
                    ViewServer.this.g.readLock().unlock();
                    return null;
                } catch (Throwable th) {
                    ViewServer.this.g.readLock().unlock();
                    throw th;
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x006e A[SYNTHETIC, Splitter:B:12:0x006e] */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x00a3 A[SYNTHETIC, Splitter:B:28:0x00a3] */
        /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean a(java.net.Socket r9) {
            /*
                r8 = this;
                r5 = 1
                r3 = 0
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this     // Catch:{ Exception -> 0x00ac, all -> 0x0093 }
                java.util.concurrent.locks.ReentrantReadWriteLock r6 = r6.g     // Catch:{ Exception -> 0x00ac, all -> 0x0093 }
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r6 = r6.readLock()     // Catch:{ Exception -> 0x00ac, all -> 0x0093 }
                r6.lock()     // Catch:{ Exception -> 0x00ac, all -> 0x0093 }
                java.io.OutputStream r0 = r9.getOutputStream()     // Catch:{ Exception -> 0x00ac, all -> 0x0093 }
                java.io.BufferedWriter r4 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x00ac, all -> 0x0093 }
                java.io.OutputStreamWriter r6 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x00ac, all -> 0x0093 }
                r6.<init>(r0)     // Catch:{ Exception -> 0x00ac, all -> 0x0093 }
                r7 = 8192(0x2000, float:1.14794E-41)
                r4.<init>(r6, r7)     // Catch:{ Exception -> 0x00ac, all -> 0x0093 }
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                java.util.Map r6 = r6.f     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                java.util.Set r6 = r6.entrySet()     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                java.util.Iterator r2 = r6.iterator()     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
            L_0x002d:
                boolean r6 = r2.hasNext()     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                if (r6 == 0) goto L_0x0072
                java.lang.Object r1 = r2.next()     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                java.util.Map$Entry r1 = (java.util.Map.Entry) r1     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                java.lang.Object r6 = r1.getKey()     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                int r6 = java.lang.System.identityHashCode(r6)     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                java.lang.String r6 = java.lang.Integer.toHexString(r6)     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                r4.write(r6)     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                r6 = 32
                r4.write(r6)     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                java.lang.Object r6 = r1.getValue()     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                java.lang.CharSequence r6 = (java.lang.CharSequence) r6     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                r4.append(r6)     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                r6 = 10
                r4.write(r6)     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                goto L_0x002d
            L_0x005c:
                r6 = move-exception
                r3 = r4
            L_0x005e:
                r5 = 0
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this
                java.util.concurrent.locks.ReentrantReadWriteLock r6 = r6.g
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r6 = r6.readLock()
                r6.unlock()
                if (r3 == 0) goto L_0x0071
                r3.close()     // Catch:{ IOException -> 0x0090 }
            L_0x0071:
                return r5
            L_0x0072:
                java.lang.String r6 = "DONE.\n"
                r4.write(r6)     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                r4.flush()     // Catch:{ Exception -> 0x005c, all -> 0x00a9 }
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this
                java.util.concurrent.locks.ReentrantReadWriteLock r6 = r6.g
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r6 = r6.readLock()
                r6.unlock()
                r4.close()     // Catch:{ IOException -> 0x008c }
                r3 = r4
                goto L_0x0071
            L_0x008c:
                r6 = move-exception
                r5 = 0
                r3 = r4
                goto L_0x0071
            L_0x0090:
                r6 = move-exception
                r5 = 0
                goto L_0x0071
            L_0x0093:
                r6 = move-exception
            L_0x0094:
                org.androidannotations.api.ViewServer r7 = org.androidannotations.api.ViewServer.this
                java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.g
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()
                r7.unlock()
                if (r3 == 0) goto L_0x00a6
                r3.close()     // Catch:{ IOException -> 0x00a7 }
            L_0x00a6:
                throw r6
            L_0x00a7:
                r7 = move-exception
                goto L_0x00a6
            L_0x00a9:
                r6 = move-exception
                r3 = r4
                goto L_0x0094
            L_0x00ac:
                r6 = move-exception
                goto L_0x005e
            */
            throw new UnsupportedOperationException("Method not decompiled: org.androidannotations.api.ViewServer.ViewServerWorker.a(java.net.Socket):boolean");
        }

        /* JADX WARNING: Removed duplicated region for block: B:27:0x0094 A[SYNTHETIC, Splitter:B:27:0x0094] */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x00ae A[SYNTHETIC, Splitter:B:38:0x00ae] */
        /* JADX WARNING: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean b(java.net.Socket r9) {
            /*
                r8 = this;
                r5 = 1
                r3 = 0
                java.io.OutputStream r0 = r9.getOutputStream()     // Catch:{ Exception -> 0x00ba, all -> 0x00b8 }
                java.io.BufferedWriter r4 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x00ba, all -> 0x00b8 }
                java.io.OutputStreamWriter r6 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x00ba, all -> 0x00b8 }
                r6.<init>(r0)     // Catch:{ Exception -> 0x00ba, all -> 0x00b8 }
                r7 = 8192(0x2000, float:1.14794E-41)
                r4.<init>(r6, r7)     // Catch:{ Exception -> 0x00ba, all -> 0x00b8 }
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock r6 = r6.i     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r6 = r6.readLock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r6.lock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this     // Catch:{ all -> 0x0080 }
                android.view.View r2 = r6.h     // Catch:{ all -> 0x0080 }
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock r6 = r6.i     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r6 = r6.readLock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r6.unlock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                if (r2 == 0) goto L_0x0073
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock r6 = r6.g     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r6 = r6.readLock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r6.lock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this     // Catch:{ all -> 0x009b }
                java.util.Map r6 = r6.f     // Catch:{ all -> 0x009b }
                org.androidannotations.api.ViewServer r7 = org.androidannotations.api.ViewServer.this     // Catch:{ all -> 0x009b }
                android.view.View r7 = r7.h     // Catch:{ all -> 0x009b }
                java.lang.Object r1 = r6.get(r7)     // Catch:{ all -> 0x009b }
                java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x009b }
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock r6 = r6.g     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r6 = r6.readLock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r6.unlock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                int r6 = java.lang.System.identityHashCode(r2)     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.lang.String r6 = java.lang.Integer.toHexString(r6)     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r4.write(r6)     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r6 = 32
                r4.write(r6)     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r4.append(r1)     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
            L_0x0073:
                r6 = 10
                r4.write(r6)     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r4.flush()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r4.close()     // Catch:{ IOException -> 0x00b2 }
                r3 = r4
            L_0x007f:
                return r5
            L_0x0080:
                r6 = move-exception
                org.androidannotations.api.ViewServer r7 = org.androidannotations.api.ViewServer.this     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.i     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r7.unlock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                throw r6     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
            L_0x008f:
                r6 = move-exception
                r3 = r4
            L_0x0091:
                r5 = 0
                if (r3 == 0) goto L_0x007f
                r3.close()     // Catch:{ IOException -> 0x0098 }
                goto L_0x007f
            L_0x0098:
                r6 = move-exception
                r5 = 0
                goto L_0x007f
            L_0x009b:
                r6 = move-exception
                org.androidannotations.api.ViewServer r7 = org.androidannotations.api.ViewServer.this     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock r7 = r7.g     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r7 = r7.readLock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                r7.unlock()     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
                throw r6     // Catch:{ Exception -> 0x008f, all -> 0x00aa }
            L_0x00aa:
                r6 = move-exception
                r3 = r4
            L_0x00ac:
                if (r3 == 0) goto L_0x00b1
                r3.close()     // Catch:{ IOException -> 0x00b6 }
            L_0x00b1:
                throw r6
            L_0x00b2:
                r6 = move-exception
                r5 = 0
                r3 = r4
                goto L_0x007f
            L_0x00b6:
                r7 = move-exception
                goto L_0x00b1
            L_0x00b8:
                r6 = move-exception
                goto L_0x00ac
            L_0x00ba:
                r6 = move-exception
                goto L_0x0091
            */
            throw new UnsupportedOperationException("Method not decompiled: org.androidannotations.api.ViewServer.ViewServerWorker.b(java.net.Socket):boolean");
        }

        public void windowsChanged() {
            synchronized (this.d) {
                this.b = true;
                this.d.notifyAll();
            }
        }

        public void focusChanged() {
            synchronized (this.d) {
                this.c = true;
                this.d.notifyAll();
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:46:0x0071 A[SYNTHETIC, Splitter:B:46:0x0071] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean a() {
            /*
                r7 = this;
                org.androidannotations.api.ViewServer r5 = org.androidannotations.api.ViewServer.this
                r5.a(r7)
                r3 = 0
                java.io.BufferedWriter r4 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x008c }
                java.io.OutputStreamWriter r5 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x008c }
                java.net.Socket r6 = r7.a     // Catch:{ Exception -> 0x008c }
                java.io.OutputStream r6 = r6.getOutputStream()     // Catch:{ Exception -> 0x008c }
                r5.<init>(r6)     // Catch:{ Exception -> 0x008c }
                r4.<init>(r5)     // Catch:{ Exception -> 0x008c }
            L_0x0016:
                boolean r5 = java.lang.Thread.interrupted()     // Catch:{ Exception -> 0x0032, all -> 0x006d }
                if (r5 != 0) goto L_0x007a
                r2 = 0
                r1 = 0
                java.lang.Object[] r6 = r7.d     // Catch:{ Exception -> 0x0032, all -> 0x006d }
                monitor-enter(r6)     // Catch:{ Exception -> 0x0032, all -> 0x006d }
            L_0x0021:
                boolean r5 = r7.b     // Catch:{ all -> 0x002f }
                if (r5 != 0) goto L_0x0047
                boolean r5 = r7.c     // Catch:{ all -> 0x002f }
                if (r5 != 0) goto L_0x0047
                java.lang.Object[] r5 = r7.d     // Catch:{ all -> 0x002f }
                r5.wait()     // Catch:{ all -> 0x002f }
                goto L_0x0021
            L_0x002f:
                r5 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x002f }
                throw r5     // Catch:{ Exception -> 0x0032, all -> 0x006d }
            L_0x0032:
                r0 = move-exception
                r3 = r4
            L_0x0034:
                java.lang.String r5 = "ViewServer"
                java.lang.String r6 = "Connection error: "
                android.util.Log.w(r5, r6, r0)     // Catch:{ all -> 0x008a }
                if (r3 == 0) goto L_0x0040
                r3.close()     // Catch:{ IOException -> 0x0086 }
            L_0x0040:
                org.androidannotations.api.ViewServer r5 = org.androidannotations.api.ViewServer.this
                r5.b(r7)
            L_0x0045:
                r5 = 1
                return r5
            L_0x0047:
                boolean r5 = r7.b     // Catch:{ all -> 0x002f }
                if (r5 == 0) goto L_0x004f
                r5 = 0
                r7.b = r5     // Catch:{ all -> 0x002f }
                r2 = 1
            L_0x004f:
                boolean r5 = r7.c     // Catch:{ all -> 0x002f }
                if (r5 == 0) goto L_0x0057
                r5 = 0
                r7.c = r5     // Catch:{ all -> 0x002f }
                r1 = 1
            L_0x0057:
                monitor-exit(r6)     // Catch:{ all -> 0x002f }
                if (r2 == 0) goto L_0x0062
                java.lang.String r5 = "LIST UPDATE\n"
                r4.write(r5)     // Catch:{ Exception -> 0x0032, all -> 0x006d }
                r4.flush()     // Catch:{ Exception -> 0x0032, all -> 0x006d }
            L_0x0062:
                if (r1 == 0) goto L_0x0016
                java.lang.String r5 = "FOCUS UPDATE\n"
                r4.write(r5)     // Catch:{ Exception -> 0x0032, all -> 0x006d }
                r4.flush()     // Catch:{ Exception -> 0x0032, all -> 0x006d }
                goto L_0x0016
            L_0x006d:
                r5 = move-exception
                r3 = r4
            L_0x006f:
                if (r3 == 0) goto L_0x0074
                r3.close()     // Catch:{ IOException -> 0x0088 }
            L_0x0074:
                org.androidannotations.api.ViewServer r6 = org.androidannotations.api.ViewServer.this
                r6.b(r7)
                throw r5
            L_0x007a:
                r4.close()     // Catch:{ IOException -> 0x0084 }
            L_0x007d:
                org.androidannotations.api.ViewServer r5 = org.androidannotations.api.ViewServer.this
                r5.b(r7)
                r3 = r4
                goto L_0x0045
            L_0x0084:
                r5 = move-exception
                goto L_0x007d
            L_0x0086:
                r5 = move-exception
                goto L_0x0040
            L_0x0088:
                r6 = move-exception
                goto L_0x0074
            L_0x008a:
                r5 = move-exception
                goto L_0x006f
            L_0x008c:
                r0 = move-exception
                goto L_0x0034
            */
            throw new UnsupportedOperationException("Method not decompiled: org.androidannotations.api.ViewServer.ViewServerWorker.a():boolean");
        }
    }

    interface WindowListener {
        void focusChanged();

        void windowsChanged();
    }

    public static ViewServer get(Context context) {
        ApplicationInfo info = context.getApplicationInfo();
        if (!"user".equals(Build.TYPE) || (info.flags & 2) == 0) {
            j = new NoopViewServer();
        } else {
            if (j == null) {
                j = new ViewServer(0);
            }
            if (!j.isRunning()) {
                try {
                    j.start();
                } catch (IOException e2) {
                    Log.d("ViewServer", "Error:", e2);
                }
            }
        }
        return j;
    }

    private ViewServer() {
        this.e = new CopyOnWriteArrayList();
        this.f = new HashMap();
        this.g = new ReentrantReadWriteLock();
        this.i = new ReentrantReadWriteLock();
        this.b = -1;
    }

    private ViewServer(byte b2) {
        this.e = new CopyOnWriteArrayList();
        this.f = new HashMap();
        this.g = new ReentrantReadWriteLock();
        this.i = new ReentrantReadWriteLock();
        this.b = 4939;
    }

    public boolean start() {
        if (this.c != null) {
            return false;
        }
        this.c = new Thread(this, "Local View Server [port=" + this.b + "]");
        this.d = Executors.newFixedThreadPool(10);
        this.c.start();
        return true;
    }

    /* JADX INFO: finally extract failed */
    public boolean stop() {
        if (this.c != null) {
            this.c.interrupt();
            if (this.d != null) {
                try {
                    this.d.shutdownNow();
                } catch (SecurityException e2) {
                    Log.w("ViewServer", "Could not stop all view server threads");
                }
            }
            this.d = null;
            this.c = null;
            try {
                this.a.close();
                this.a = null;
                return true;
            } catch (IOException e3) {
                Log.w("ViewServer", "Could not close the view server");
            }
        }
        this.g.writeLock().lock();
        try {
            this.f.clear();
            this.g.writeLock().unlock();
            this.i.writeLock().lock();
            try {
                this.h = null;
                this.i.writeLock().unlock();
                return false;
            } catch (Throwable th) {
                this.i.writeLock().unlock();
                throw th;
            }
        } catch (Throwable th2) {
            this.g.writeLock().unlock();
            throw th2;
        }
    }

    public boolean isRunning() {
        return this.c != null && this.c.isAlive();
    }

    public void addWindow(Activity activity) {
        String name;
        String name2 = activity.getTitle().toString();
        if (TextUtils.isEmpty(name2)) {
            name = activity.getClass().getCanonicalName() + "/0x" + System.identityHashCode(activity);
        } else {
            name = name2 + "(" + activity.getClass().getCanonicalName() + ")";
        }
        addWindow(activity.getWindow().getDecorView(), name);
    }

    public void removeWindow(Activity activity) {
        removeWindow(activity.getWindow().getDecorView());
    }

    /* JADX INFO: finally extract failed */
    public void addWindow(View view, String name) {
        this.g.writeLock().lock();
        try {
            this.f.put(view.getRootView(), name);
            this.g.writeLock().unlock();
            a();
        } catch (Throwable th) {
            this.g.writeLock().unlock();
            throw th;
        }
    }

    /* JADX INFO: finally extract failed */
    public void removeWindow(View view) {
        this.g.writeLock().lock();
        try {
            this.f.remove(view.getRootView());
            this.g.writeLock().unlock();
            a();
        } catch (Throwable th) {
            this.g.writeLock().unlock();
            throw th;
        }
    }

    public void setFocusedWindow(Activity activity) {
        setFocusedWindow(activity.getWindow().getDecorView());
    }

    /* JADX INFO: finally extract failed */
    public void setFocusedWindow(View view) {
        this.i.writeLock().lock();
        try {
            this.h = view == null ? null : view.getRootView();
            this.i.writeLock().unlock();
            b();
        } catch (Throwable th) {
            this.i.writeLock().unlock();
            throw th;
        }
    }

    public void run() {
        try {
            this.a = new ServerSocket(this.b, 10, InetAddress.getLocalHost());
        } catch (Exception e2) {
            Log.w("ViewServer", "Starting ServerSocket error: ", e2);
        }
        while (this.a != null && Thread.currentThread() == this.c) {
            try {
                Socket client = this.a.accept();
                if (this.d != null) {
                    this.d.submit(new ViewServerWorker(client));
                } else {
                    try {
                        client.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                }
            } catch (Exception e4) {
                Log.w("ViewServer", "Connection error: ", e4);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002a A[SYNTHETIC, Splitter:B:14:0x002a] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0034 A[SYNTHETIC, Splitter:B:20:0x0034] */
    /* JADX WARNING: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.net.Socket r6, java.lang.String r7) {
        /*
            r1 = 0
            java.io.OutputStream r0 = r6.getOutputStream()     // Catch:{ Exception -> 0x0026, all -> 0x0031 }
            java.io.BufferedWriter r2 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x0026, all -> 0x0031 }
            java.io.OutputStreamWriter r4 = new java.io.OutputStreamWriter     // Catch:{ Exception -> 0x0026, all -> 0x0031 }
            r4.<init>(r0)     // Catch:{ Exception -> 0x0026, all -> 0x0031 }
            r5 = 8192(0x2000, float:1.14794E-41)
            r2.<init>(r4, r5)     // Catch:{ Exception -> 0x0026, all -> 0x0031 }
            r2.write(r7)     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            java.lang.String r4 = "\n"
            r2.write(r4)     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            r2.flush()     // Catch:{ Exception -> 0x003d, all -> 0x003a }
            r3 = 1
            r2.close()     // Catch:{ IOException -> 0x0022 }
            r1 = r2
        L_0x0021:
            return r3
        L_0x0022:
            r4 = move-exception
            r3 = 0
            r1 = r2
            goto L_0x0021
        L_0x0026:
            r4 = move-exception
        L_0x0027:
            r3 = 0
            if (r1 == 0) goto L_0x0021
            r1.close()     // Catch:{ IOException -> 0x002e }
            goto L_0x0021
        L_0x002e:
            r4 = move-exception
            r3 = 0
            goto L_0x0021
        L_0x0031:
            r4 = move-exception
        L_0x0032:
            if (r1 == 0) goto L_0x0037
            r1.close()     // Catch:{ IOException -> 0x0038 }
        L_0x0037:
            throw r4
        L_0x0038:
            r5 = move-exception
            goto L_0x0037
        L_0x003a:
            r4 = move-exception
            r1 = r2
            goto L_0x0032
        L_0x003d:
            r4 = move-exception
            r1 = r2
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: org.androidannotations.api.ViewServer.a(java.net.Socket, java.lang.String):boolean");
    }

    private void a() {
        for (WindowListener windowsChanged : this.e) {
            windowsChanged.windowsChanged();
        }
    }

    private void b() {
        for (WindowListener focusChanged : this.e) {
            focusChanged.focusChanged();
        }
    }

    /* access modifiers changed from: private */
    public void a(WindowListener listener) {
        if (!this.e.contains(listener)) {
            this.e.add(listener);
        }
    }

    /* access modifiers changed from: private */
    public void b(WindowListener listener) {
        this.e.remove(listener);
    }
}
