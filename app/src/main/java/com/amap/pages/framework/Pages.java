package com.amap.pages.framework;

import android.content.Context;
import android.os.Looper;
import android.util.AndroidRuntimeException;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

import defpackage.akc;
import defpackage.akd;
import defpackage.ake;
import defpackage.akf;
import defpackage.akg;
import defpackage.akh;
import defpackage.aki;
import defpackage.akj;
import defpackage.akl;
import defpackage.akm;

public final class Pages {
    public final akj a;
    public final akd b = new a(this);
    private final akf c;

    public static final class CalledFromWrongThreadException extends AndroidRuntimeException {
        public CalledFromWrongThreadException(String str) {
        }
    }

    static class a implements akd {
        WeakReference<Pages> a;

        a(Pages pages) {
            this.a = new WeakReference<>(pages);
        }

        public final void a( Class<? extends akc > cls, int i, akh akh, aki aki, ake ake) {
            Pages pages = (Pages) this.a.get();
            if (pages != null) {
                Pages.a();
                new StringBuilder("start page: c=").append(cls);
                if (cls != null) {
                    if (aki != null && aki.b == null) {
                        aki = null;
                    }
                    akm akm = new akm(pages.b, cls, i, akh, aki, ake);
                    akj akj = pages.a;
                    if (akm.c == null) {
                        akm.c = akj.g;
                    }
                    akj.e.add(new b(akm) {
                        final /* synthetic */ akm a;

                        {
                            this.a = r2;
                        }

                        /* JADX WARNING: Code restructure failed: missing block: B:95:0x025e, code lost:
                            if (r4 != false) goto L_0x0260;
                         */
                        /* JADX WARNING: Removed duplicated region for block: B:103:0x028c  */
                        /* JADX WARNING: Removed duplicated region for block: B:76:0x01ec  */
                        /* JADX WARNING: Removed duplicated region for block: B:91:0x0235  */
                        /* JADX WARNING: Removed duplicated region for block: B:97:0x0262  */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public final boolean a() {
                            /*
                                r18 = this;
                                r0 = r18
                                akj r1 = defpackage.akj.this
                                akm r2 = r0.a
                                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                                java.lang.String r4 = "do start: params="
                                r3.<init>(r4)
                                r3.append(r2)
                                boolean r3 = r1.j
                                r4 = 0
                                if (r3 == 0) goto L_0x0016
                                return r4
                            L_0x0016:
                                boolean r3 = r2.a
                                int r5 = r1.i
                                r6 = 2
                                if (r5 > r6) goto L_0x001e
                                r3 = 0
                            L_0x001e:
                                int r5 = r1.i
                                r7 = 1
                                if (r5 >= r6) goto L_0x0049
                                java.util.ArrayList<akj$a> r5 = r1.d
                                int r5 = r5.size()
                                int r5 = r5 - r7
                                int r5 = r1.a(r5, r4)
                                java.util.ArrayList<akj$a> r8 = r1.d
                                int r8 = r8.size()
                                r9 = r5
                            L_0x0035:
                                if (r5 < 0) goto L_0x0049
                                if (r9 >= r8) goto L_0x0049
                                java.util.ArrayList<akj$a> r10 = r1.d
                                java.lang.Object r10 = r10.get(r9)
                                akj$a r10 = (defpackage.akj.a) r10
                                android.view.View r10 = r10.c
                                defpackage.akf.b(r10)
                                int r9 = r9 + 1
                                goto L_0x0035
                            L_0x0049:
                                boolean r5 = r2.a(r6)
                                r6 = 0
                                if (r5 == 0) goto L_0x00d4
                                java.lang.String r5 = r2.l
                                boolean r5 = android.text.TextUtils.isEmpty(r5)
                                if (r5 != 0) goto L_0x005f
                                java.lang.String r5 = r2.l
                                int r5 = r1.b(r5, r4)
                                goto L_0x0070
                            L_0x005f:
                                java.lang.Class<?> r5 = r2.i
                                if (r5 == 0) goto L_0x006a
                                java.lang.Class<?> r5 = r2.i
                                int r5 = r1.c(r5, r4)
                                goto L_0x0070
                            L_0x006a:
                                java.lang.Class<? extends akc> r5 = r2.f
                                int r5 = r1.a(r5)
                            L_0x0070:
                                if (r5 < 0) goto L_0x01e7
                                java.util.ArrayList<akj$a> r8 = r1.d
                                java.lang.Object r8 = r8.get(r5)
                                akj$a r8 = (defpackage.akj.a) r8
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                java.lang.String r10 = "do start: single instance c="
                                r9.<init>(r10)
                                java.lang.String r10 = r8.g()
                                r9.append(r10)
                                java.util.ArrayList<akj$a> r9 = r1.d
                                int r9 = r9.size()
                                int r9 = r9 - r7
                                int r5 = r5 + r7
                                int r9 = r1.a(r9, r5)
                                if (r9 < 0) goto L_0x00c1
                                java.util.ArrayList r10 = new java.util.ArrayList
                                r10.<init>()
                                r11 = r9
                            L_0x009c:
                                java.util.ArrayList<akj$a> r12 = r1.d
                                int r12 = r12.size()
                                int r12 = r12 - r7
                                if (r11 > r12) goto L_0x00c2
                                java.util.ArrayList<akj$a> r12 = r1.d
                                java.lang.Object r12 = r12.get(r11)
                                akj$a r12 = (defpackage.akj.a) r12
                                java.lang.StringBuilder r13 = new java.lang.StringBuilder
                                java.lang.String r14 = "do start: single task: stop c="
                                r13.<init>(r14)
                                java.lang.String r14 = r12.g()
                                r13.append(r14)
                                r10.add(r12)
                                int r11 = r11 + 1
                                goto L_0x009c
                            L_0x00c1:
                                r10 = r6
                            L_0x00c2:
                                java.util.ArrayList r11 = defpackage.akj.a(r8)
                                if (r9 < 0) goto L_0x00c9
                                goto L_0x00cf
                            L_0x00c9:
                                java.util.ArrayList<akj$a> r9 = r1.d
                                int r9 = r9.size()
                            L_0x00cf:
                                r1.a(r11, r5, r9)
                                goto L_0x01ea
                            L_0x00d4:
                                r5 = 8
                                boolean r5 = r2.a(r5)
                                if (r5 == 0) goto L_0x013d
                                java.lang.String r5 = r2.l
                                boolean r5 = android.text.TextUtils.isEmpty(r5)
                                if (r5 != 0) goto L_0x00f2
                                java.lang.String r5 = r2.l
                                java.util.ArrayList<akj$a> r8 = r1.d
                                int r8 = r8.size()
                                int r8 = r8 - r7
                                akj$a r5 = r1.a(r5, r8)
                                goto L_0x0111
                            L_0x00f2:
                                java.lang.Class<?> r5 = r2.i
                                if (r5 == 0) goto L_0x0104
                                java.lang.Class<?> r5 = r2.i
                                java.util.ArrayList<akj$a> r8 = r1.d
                                int r8 = r8.size()
                                int r8 = r8 - r7
                                akj$a r5 = r1.b(r5, r8)
                                goto L_0x0111
                            L_0x0104:
                                java.lang.Class<? extends akc> r5 = r2.f
                                java.util.ArrayList<akj$a> r8 = r1.d
                                int r8 = r8.size()
                                int r8 = r8 - r7
                                akj$a r5 = r1.a(r5, r8)
                            L_0x0111:
                                if (r5 == 0) goto L_0x0136
                                java.util.ArrayList<akj$a> r8 = r1.d
                                int r8 = r8.indexOf(r5)
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                java.lang.String r10 = "do start: single top c="
                                r9.<init>(r10)
                                java.lang.String r10 = r5.g()
                                r9.append(r10)
                                java.util.ArrayList r9 = defpackage.akj.a(r5)
                                java.util.ArrayList<akj$a> r10 = r1.d
                                int r10 = r10.size()
                                int r8 = r8 + r7
                                r1.a(r9, r8, r10)
                                goto L_0x0138
                            L_0x0136:
                                r5 = r6
                                r9 = r5
                            L_0x0138:
                                r8 = r5
                                r10 = r6
                                r11 = r9
                                goto L_0x01ea
                            L_0x013d:
                                r5 = 4
                                boolean r5 = r2.a(r5)
                                if (r5 == 0) goto L_0x01cb
                                int r5 = r2.h
                                java.lang.String r8 = r2.l
                                boolean r8 = android.text.TextUtils.isEmpty(r8)
                                if (r8 != 0) goto L_0x0155
                                java.lang.String r8 = r2.l
                                int r5 = r1.b(r8, r5)
                                goto L_0x0166
                            L_0x0155:
                                java.lang.Class<?> r8 = r2.i
                                if (r8 == 0) goto L_0x0160
                                java.lang.Class<?> r8 = r2.i
                                int r5 = r1.c(r8, r5)
                                goto L_0x0166
                            L_0x0160:
                                java.lang.Class<? extends akc> r5 = r2.f
                                int r5 = r1.a(r5)
                            L_0x0166:
                                if (r5 < 0) goto L_0x01c2
                                java.util.ArrayList<akj$a> r8 = r1.d
                                java.lang.Object r8 = r8.get(r5)
                                akj$a r8 = (defpackage.akj.a) r8
                                java.lang.StringBuilder r9 = new java.lang.StringBuilder
                                java.lang.String r10 = "do start: single task c="
                                r9.<init>(r10)
                                java.lang.String r10 = r8.g()
                                r9.append(r10)
                                int r5 = r5 + r7
                                int r9 = r1.a(r5)
                                if (r9 < 0) goto L_0x01b0
                                java.util.ArrayList r10 = new java.util.ArrayList
                                r10.<init>()
                                r11 = r9
                            L_0x018b:
                                java.util.ArrayList<akj$a> r12 = r1.d
                                int r12 = r12.size()
                                int r12 = r12 - r7
                                if (r11 > r12) goto L_0x01b1
                                java.util.ArrayList<akj$a> r12 = r1.d
                                java.lang.Object r12 = r12.get(r11)
                                akj$a r12 = (defpackage.akj.a) r12
                                java.lang.StringBuilder r13 = new java.lang.StringBuilder
                                java.lang.String r14 = "do start: single task: destroy c="
                                r13.<init>(r14)
                                java.lang.String r14 = r12.g()
                                r13.append(r14)
                                r10.add(r12)
                                int r11 = r11 + 1
                                goto L_0x018b
                            L_0x01b0:
                                r10 = r6
                            L_0x01b1:
                                java.util.ArrayList r11 = defpackage.akj.a(r8)
                                if (r9 < 0) goto L_0x01b8
                                goto L_0x01be
                            L_0x01b8:
                                java.util.ArrayList<akj$a> r9 = r1.d
                                int r9 = r9.size()
                            L_0x01be:
                                r1.a(r11, r5, r9)
                                goto L_0x01c5
                            L_0x01c2:
                                r8 = r6
                                r10 = r8
                                r11 = r10
                            L_0x01c5:
                                r17 = r10
                                r10 = r6
                                r6 = r17
                                goto L_0x01ea
                            L_0x01cb:
                                r5 = 16
                                boolean r5 = r2.a(r5)
                                if (r5 != 0) goto L_0x01e7
                                boolean r5 = r2.a(r7)
                                if (r5 != 0) goto L_0x01e7
                                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                                java.lang.String r3 = "do start: unknown flags="
                                r1.<init>(r3)
                                int r2 = r2.g
                                r1.append(r2)
                                goto L_0x0295
                            L_0x01e7:
                                r8 = r6
                                r10 = r8
                                r11 = r10
                            L_0x01ea:
                                if (r8 == 0) goto L_0x0235
                                java.util.Iterator r5 = r11.iterator()
                            L_0x01f0:
                                boolean r9 = r5.hasNext()
                                if (r9 == 0) goto L_0x0202
                                java.lang.Object r9 = r5.next()
                                akj$a r9 = (defpackage.akj.a) r9
                                java.util.ArrayList<akj$a> r12 = r1.d
                                r12.remove(r9)
                                goto L_0x01f0
                            L_0x0202:
                                java.util.Iterator r5 = r11.iterator()
                            L_0x0206:
                                boolean r9 = r5.hasNext()
                                if (r9 == 0) goto L_0x0218
                                java.lang.Object r9 = r5.next()
                                akj$a r9 = (defpackage.akj.a) r9
                                java.util.ArrayList<akj$a> r12 = r1.d
                                r12.add(r9)
                                goto L_0x0206
                            L_0x0218:
                                java.util.ArrayList r5 = defpackage.akj.a(r11)
                                boolean r9 = defpackage.akj.f(r6)
                                if (r9 == 0) goto L_0x022d
                                boolean r9 = defpackage.akj.f(r10)
                                if (r9 == 0) goto L_0x022d
                                defpackage.akj.d(r5)
                                r3 = 0
                                goto L_0x0230
                            L_0x022d:
                                defpackage.akj.c(r5)
                            L_0x0230:
                                akh r4 = r2.j
                                r8.e = r4
                                goto L_0x0260
                            L_0x0235:
                                akj$a r5 = new akj$a
                                r5.<init>(r2)
                                r2.d = r5
                                java.util.ArrayList r10 = r1.a(r5, r7)
                                java.util.ArrayList r5 = defpackage.akj.a(r5)
                                java.util.ArrayList r5 = defpackage.akj.a(r5)
                                ajz r8 = r2.d
                                r8.a(r2)
                                ajz r8 = r2.d
                                android.view.View r8 = r8.d()
                                if (r8 != 0) goto L_0x025b
                                ajz r8 = r2.d
                                r8.c()
                                goto L_0x025e
                            L_0x025b:
                                r2.m = r8
                                r4 = 1
                            L_0x025e:
                                if (r4 == 0) goto L_0x0295
                            L_0x0260:
                                if (r3 == 0) goto L_0x028c
                                r1.j = r7
                                aka r12 = r2.b
                                akb r13 = r2.c
                                if (r6 == 0) goto L_0x0270
                                java.util.ArrayList r2 = defpackage.akj.b(r6)
                            L_0x026e:
                                r14 = r2
                                goto L_0x0275
                            L_0x0270:
                                java.util.ArrayList r2 = defpackage.akj.b(r10)
                                goto L_0x026e
                            L_0x0275:
                                java.util.ArrayList r15 = defpackage.akj.b(r5)
                                akj$5 r2 = new akj$5
                                r2.<init>(r6, r10, r5)
                                akf$a r3 = new akf$a
                                r11 = r3
                                r16 = r2
                                r11.<init>(r12, r13, r14, r15, r16)
                                akf r1 = r1.c
                                r1.a(r3)
                                goto L_0x0295
                            L_0x028c:
                                defpackage.akj.e(r6)
                                defpackage.akj.c(r10)
                                defpackage.akj.a(r5, r7)
                            L_0x0295:
                                return r7
                            */
                            throw new UnsupportedOperationException("Method not decompiled: defpackage.akj.AnonymousClass2.a():boolean");
                        }
                    });
                    akj.f.obtainMessage(0).sendToTarget();
                }
            }
        }

        public final void a( akg akg, ake ake) {
            Pages pages = (Pages) this.a.get();
            if (pages != null) {
                Pages.a();
                new StringBuilder("finish page: id=").append(akg);
                if (!(akg == null || (akg.a() == null && akg.b() == null))) {
                    akl akl = new akl(akg, ake);
                    akj akj = pages.a;
                    if (akl.c == null) {
                        akl.c = akj.g;
                    }
                    akj.e.add(new b(akl) {
                        final /* synthetic */ akl a;

                        {
                            this.a = r2;
                        }

                        /* JADX WARNING: Removed duplicated region for block: B:25:0x0080  */
                        /* JADX WARNING: Removed duplicated region for block: B:27:0x008e  */
                        /* Code decompiled incorrectly, please refer to instructions dump. */
                        public final boolean a() {
                            /*
                                r12 = this;
                                akj r0 = defpackage.akj.this
                                akl r1 = r12.a
                                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                                java.lang.String r3 = "do finish: params="
                                r2.<init>(r3)
                                r2.append(r1)
                                akg r2 = r1.e
                                akj$a r2 = r0.a(r2)
                                r3 = 1
                                if (r2 != 0) goto L_0x0018
                                return r3
                            L_0x0018:
                                r1.d = r2
                                akm r4 = r2.a
                                java.lang.Class<? extends akc> r4 = r4.f
                                r1.g = r4
                                android.view.View r4 = r2.c
                                r1.h = r4
                                boolean r4 = r2.h()
                                if (r4 == 0) goto L_0x0039
                                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                                java.lang.String r1 = "do finish: page is finished, do nothing: c="
                                r0.<init>(r1)
                                akm r1 = r2.a
                                java.lang.Class<? extends akc> r1 = r1.f
                                r0.append(r1)
                                return r3
                            L_0x0039:
                                boolean r4 = r0.j
                                r5 = 0
                                if (r4 == 0) goto L_0x003f
                                return r5
                            L_0x003f:
                                int r4 = r0.i
                                r6 = 2
                                if (r4 <= r6) goto L_0x0059
                                int r4 = r2.d
                                if (r4 < r6) goto L_0x004f
                                int r4 = r2.d
                                r6 = 6
                                if (r4 >= r6) goto L_0x004f
                                r4 = 1
                                goto L_0x0050
                            L_0x004f:
                                r4 = 0
                            L_0x0050:
                                if (r4 == 0) goto L_0x0059
                                boolean r4 = r1.a
                                if (r4 != 0) goto L_0x0057
                                goto L_0x0059
                            L_0x0057:
                                r4 = 0
                                goto L_0x007a
                            L_0x0059:
                                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                                java.lang.String r6 = "do finish: quick: as="
                                r4.<init>(r6)
                                int r6 = r0.i
                                r4.append(r6)
                                java.lang.String r6 = " s="
                                r4.append(r6)
                                int r6 = r2.d
                                r4.append(r6)
                                java.lang.String r6 = " animation="
                                r4.append(r6)
                                boolean r6 = r1.a
                                r4.append(r6)
                                r4 = 1
                            L_0x007a:
                                java.util.ArrayList r9 = r0.a(r2, r5)
                                if (r4 == 0) goto L_0x008e
                                ajz r0 = r1.d
                                r0.e()
                                ajz r0 = r1.d
                                r0.c()
                                defpackage.akj.a(r9, r3)
                                return r3
                            L_0x008e:
                                r0.j = r3
                                ajz r4 = r1.d
                                r4.f()
                                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                                java.lang.String r5 = "do finish: handle pause finish: c="
                                r4.<init>(r5)
                                java.lang.Class<? extends akc> r5 = r1.g
                                r4.append(r5)
                                r4 = 5
                                r2.d = r4
                                aka r7 = r1.b
                                akb r8 = r1.c
                                java.util.ArrayList r10 = new java.util.ArrayList
                                r10.<init>()
                                android.view.View r1 = r2.c
                                r10.add(r1)
                                akj$6 r11 = new akj$6
                                r11.<init>(r2, r9)
                                akf$c r1 = new akf$c
                                r6 = r1
                                r6.<init>(r7, r8, r9, r10, r11)
                                akf r0 = r0.c
                                r0.a(r1)
                                return r3
                            */
                            throw new UnsupportedOperationException("Method not decompiled: defpackage.akj.AnonymousClass3.a():boolean");
                        }
                    });
                    akj.f.obtainMessage(0).sendToTarget();
                }
            }
        }

        public final void a(akg akg, int i, akh akh) {
            Pages pages = (Pages) this.a.get();
            if (pages != null) {
                Pages.a();
                StringBuilder sb = new StringBuilder("set page result: rc=");
                sb.append(i);
                sb.append(" id=");
                sb.append(akg);
                if (akg != null) {
                    akj akj = pages.a;
                    if (Looper.getMainLooper() == Looper.myLooper()) {
                        akj.a(akg, i, akh);
                    } else {
                        akj.e.add(new b(akg, i, akh) {
                            final /* synthetic */ akg a;
                            final /* synthetic */ int b;
                            final /* synthetic */ akh c;

                            {
                                this.a = r2;
                                this.b = r3;
                                this.c = r4;
                            }

                            public final boolean a() {
                                return akj.this.a(this.a, this.b, this.c);
                            }
                        });
                        akj.f.obtainMessage(0).sendToTarget();
                    }
                }
            }
        }

        public final Class<?> a() {
            Pages pages = (Pages) this.a.get();
            if (pages == null) {
                return null;
            }
            Pages.a();
            akj akj = pages.a;
            if (akj.d.size() > 0) {
                return akj.d.get(akj.d.size() - 1).a.i;
            }
            return null;
        }

        public final akc b() {
            Pages pages = (Pages) this.a.get();
            if (pages == null) {
                return null;
            }
            Pages.a();
            akj akj = pages.a;
            if (akj.d.size() > 0) {
                return akj.d.get(akj.d.size() - 1).b;
            }
            return null;
        }

        public final ArrayList<akc> c() {
            Pages pages = (Pages) this.a.get();
            if (pages == null) {
                return null;
            }
            Pages.a();
            akj akj = pages.a;
            ArrayList<akc> arrayList = new ArrayList<>();
            Iterator<defpackage.akj.a> it = akj.d.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().b);
            }
            return arrayList;
        }
    }

    public Pages(Context context, LayoutInflater layoutInflater, ViewGroup viewGroup) {
        a();
        this.c = new akf(viewGroup);
        this.a = new akj(context, layoutInflater, this.c);
    }

    public static void a() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new CalledFromWrongThreadException("!!! PageFrameWork does not allow child thread access, 826 start limit, there are problems linked @ruoxin.jxt !!! ");
        }
    }
}
