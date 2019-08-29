package android.support.v7.widget;

import android.support.v4.util.Pools.Pool;
import android.support.v4.util.Pools.SimplePool;
import android.support.v7.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.List;

class AdapterHelper implements Callback {
    final ArrayList<UpdateOp> a;
    final ArrayList<UpdateOp> b;
    final Callback c;
    Runnable d;
    final boolean e;
    final OpReorderer f;
    int g;
    private Pool<UpdateOp> h;

    interface Callback {
        ViewHolder a(int i);

        void a(int i, int i2);

        void a(int i, int i2, Object obj);

        void a(UpdateOp updateOp);

        void b(int i, int i2);

        void b(UpdateOp updateOp);

        void c(int i, int i2);

        void d(int i, int i2);
    }

    static class UpdateOp {
        int a;
        int b;
        Object c;
        int d;

        UpdateOp(int i, int i2, int i3, Object obj) {
            this.a = i;
            this.b = i2;
            this.d = i3;
            this.c = obj;
        }

        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append("[");
            int i = this.a;
            if (i == 4) {
                str = "up";
            } else if (i != 8) {
                switch (i) {
                    case 1:
                        str = "add";
                        break;
                    case 2:
                        str = "rm";
                        break;
                    default:
                        str = "??";
                        break;
                }
            } else {
                str = "mv";
            }
            sb.append(str);
            sb.append(",s:");
            sb.append(this.b);
            sb.append("c:");
            sb.append(this.d);
            sb.append(",p:");
            sb.append(this.c);
            sb.append("]");
            return sb.toString();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            UpdateOp updateOp = (UpdateOp) obj;
            if (this.a != updateOp.a) {
                return false;
            }
            if (this.a == 8 && Math.abs(this.d - this.b) == 1 && this.d == updateOp.b && this.b == updateOp.d) {
                return true;
            }
            if (this.d != updateOp.d || this.b != updateOp.b) {
                return false;
            }
            if (this.c != null) {
                if (!this.c.equals(updateOp.c)) {
                    return false;
                }
            } else if (updateOp.c != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.a * 31) + this.b) * 31) + this.d;
        }
    }

    AdapterHelper(Callback callback) {
        this(callback, 0);
    }

    private AdapterHelper(Callback callback, byte b2) {
        this.h = new SimplePool(30);
        this.a = new ArrayList<>();
        this.b = new ArrayList<>();
        this.g = 0;
        this.c = callback;
        this.e = false;
        this.f = new OpReorderer(this);
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        a((List<UpdateOp>) this.a);
        a((List<UpdateOp>) this.b);
        this.g = 0;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0004 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0084  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x0177  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x01a0  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01a4  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x01ae  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01b3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b() {
        /*
            r15 = this;
            android.support.v7.widget.OpReorderer r0 = r15.f
            java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r1 = r15.a
        L_0x0004:
            int r2 = android.support.v7.widget.OpReorderer.a(r1)
            r3 = -1
            r4 = 2
            r5 = 0
            r6 = 4
            r7 = 0
            r8 = 1
            if (r2 == r3) goto L_0x01b8
            int r3 = r2 + 1
            java.lang.Object r9 = r1.get(r2)
            android.support.v7.widget.AdapterHelper$UpdateOp r9 = (android.support.v7.widget.AdapterHelper.UpdateOp) r9
            java.lang.Object r10 = r1.get(r3)
            android.support.v7.widget.AdapterHelper$UpdateOp r10 = (android.support.v7.widget.AdapterHelper.UpdateOp) r10
            int r11 = r10.a
            if (r11 == r6) goto L_0x0145
            switch(r11) {
                case 1: goto L_0x0140;
                case 2: goto L_0x0026;
                default: goto L_0x0025;
            }
        L_0x0025:
            goto L_0x0004
        L_0x0026:
            int r6 = r9.b
            int r11 = r9.d
            if (r6 >= r11) goto L_0x0040
            int r6 = r10.b
            int r11 = r9.b
            if (r6 != r11) goto L_0x003e
            int r6 = r10.d
            int r11 = r9.d
            int r12 = r9.b
            int r11 = r11 - r12
            if (r6 != r11) goto L_0x003e
            r6 = 0
        L_0x003c:
            r7 = 1
            goto L_0x0053
        L_0x003e:
            r6 = 0
            goto L_0x0053
        L_0x0040:
            int r6 = r10.b
            int r11 = r9.d
            int r11 = r11 + r8
            if (r6 != r11) goto L_0x0052
            int r6 = r10.d
            int r11 = r9.b
            int r12 = r9.d
            int r11 = r11 - r12
            if (r6 != r11) goto L_0x0052
            r6 = 1
            goto L_0x003c
        L_0x0052:
            r6 = 1
        L_0x0053:
            int r11 = r9.d
            int r12 = r10.b
            if (r11 >= r12) goto L_0x005f
            int r11 = r10.b
            int r11 = r11 - r8
            r10.b = r11
            goto L_0x007e
        L_0x005f:
            int r11 = r9.d
            int r12 = r10.b
            int r13 = r10.d
            int r12 = r12 + r13
            if (r11 >= r12) goto L_0x007e
            int r2 = r10.d
            int r2 = r2 - r8
            r10.d = r2
            r9.a = r4
            r9.d = r8
            int r2 = r10.d
            if (r2 != 0) goto L_0x0004
            r1.remove(r3)
            android.support.v7.widget.OpReorderer$Callback r2 = r0.a
            r2.a(r10)
            goto L_0x0004
        L_0x007e:
            int r11 = r9.b
            int r12 = r10.b
            if (r11 > r12) goto L_0x008a
            int r4 = r10.b
            int r4 = r4 + r8
            r10.b = r4
            goto L_0x00ab
        L_0x008a:
            int r11 = r9.b
            int r12 = r10.b
            int r13 = r10.d
            int r12 = r12 + r13
            if (r11 >= r12) goto L_0x00ab
            int r11 = r10.b
            int r12 = r10.d
            int r11 = r11 + r12
            int r12 = r9.b
            int r11 = r11 - r12
            android.support.v7.widget.OpReorderer$Callback r12 = r0.a
            int r13 = r9.b
            int r13 = r13 + r8
            android.support.v7.widget.AdapterHelper$UpdateOp r5 = r12.a(r4, r13, r11, r5)
            int r4 = r9.b
            int r8 = r10.b
            int r4 = r4 - r8
            r10.d = r4
        L_0x00ab:
            if (r7 == 0) goto L_0x00ba
            r1.set(r2, r10)
            r1.remove(r3)
            android.support.v7.widget.OpReorderer$Callback r2 = r0.a
            r2.a(r9)
            goto L_0x0004
        L_0x00ba:
            if (r6 == 0) goto L_0x00f3
            if (r5 == 0) goto L_0x00d8
            int r4 = r9.b
            int r6 = r5.b
            if (r4 <= r6) goto L_0x00cb
            int r4 = r9.b
            int r6 = r5.d
            int r4 = r4 - r6
            r9.b = r4
        L_0x00cb:
            int r4 = r9.d
            int r6 = r5.b
            if (r4 <= r6) goto L_0x00d8
            int r4 = r9.d
            int r6 = r5.d
            int r4 = r4 - r6
            r9.d = r4
        L_0x00d8:
            int r4 = r9.b
            int r6 = r10.b
            if (r4 <= r6) goto L_0x00e5
            int r4 = r9.b
            int r6 = r10.d
            int r4 = r4 - r6
            r9.b = r4
        L_0x00e5:
            int r4 = r9.d
            int r6 = r10.b
            if (r4 <= r6) goto L_0x0129
            int r4 = r9.d
            int r6 = r10.d
            int r4 = r4 - r6
            r9.d = r4
            goto L_0x0129
        L_0x00f3:
            if (r5 == 0) goto L_0x010f
            int r4 = r9.b
            int r6 = r5.b
            if (r4 < r6) goto L_0x0102
            int r4 = r9.b
            int r6 = r5.d
            int r4 = r4 - r6
            r9.b = r4
        L_0x0102:
            int r4 = r9.d
            int r6 = r5.b
            if (r4 < r6) goto L_0x010f
            int r4 = r9.d
            int r6 = r5.d
            int r4 = r4 - r6
            r9.d = r4
        L_0x010f:
            int r4 = r9.b
            int r6 = r10.b
            if (r4 < r6) goto L_0x011c
            int r4 = r9.b
            int r6 = r10.d
            int r4 = r4 - r6
            r9.b = r4
        L_0x011c:
            int r4 = r9.d
            int r6 = r10.b
            if (r4 < r6) goto L_0x0129
            int r4 = r9.d
            int r6 = r10.d
            int r4 = r4 - r6
            r9.d = r4
        L_0x0129:
            r1.set(r2, r10)
            int r4 = r9.b
            int r6 = r9.d
            if (r4 == r6) goto L_0x0136
            r1.set(r3, r9)
            goto L_0x0139
        L_0x0136:
            r1.remove(r3)
        L_0x0139:
            if (r5 == 0) goto L_0x0004
            r1.add(r2, r5)
            goto L_0x0004
        L_0x0140:
            android.support.v7.widget.OpReorderer.a(r1, r2, r9, r3, r10)
            goto L_0x0004
        L_0x0145:
            int r4 = r9.d
            int r7 = r10.b
            if (r4 >= r7) goto L_0x0151
            int r4 = r10.b
            int r4 = r4 - r8
            r10.b = r4
            goto L_0x016a
        L_0x0151:
            int r4 = r9.d
            int r7 = r10.b
            int r11 = r10.d
            int r7 = r7 + r11
            if (r4 >= r7) goto L_0x016a
            int r4 = r10.d
            int r4 = r4 - r8
            r10.d = r4
            android.support.v7.widget.OpReorderer$Callback r4 = r0.a
            int r7 = r9.b
            java.lang.Object r11 = r10.c
            android.support.v7.widget.AdapterHelper$UpdateOp r4 = r4.a(r6, r7, r8, r11)
            goto L_0x016b
        L_0x016a:
            r4 = r5
        L_0x016b:
            int r7 = r9.b
            int r11 = r10.b
            if (r7 > r11) goto L_0x0177
            int r6 = r10.b
            int r6 = r6 + r8
            r10.b = r6
            goto L_0x0199
        L_0x0177:
            int r7 = r9.b
            int r11 = r10.b
            int r12 = r10.d
            int r11 = r11 + r12
            if (r7 >= r11) goto L_0x0199
            int r5 = r10.b
            int r7 = r10.d
            int r5 = r5 + r7
            int r7 = r9.b
            int r5 = r5 - r7
            android.support.v7.widget.OpReorderer$Callback r7 = r0.a
            int r11 = r9.b
            int r11 = r11 + r8
            java.lang.Object r8 = r10.c
            android.support.v7.widget.AdapterHelper$UpdateOp r6 = r7.a(r6, r11, r5, r8)
            int r7 = r10.d
            int r7 = r7 - r5
            r10.d = r7
            r5 = r6
        L_0x0199:
            r1.set(r3, r9)
            int r3 = r10.d
            if (r3 <= 0) goto L_0x01a4
            r1.set(r2, r10)
            goto L_0x01ac
        L_0x01a4:
            r1.remove(r2)
            android.support.v7.widget.OpReorderer$Callback r3 = r0.a
            r3.a(r10)
        L_0x01ac:
            if (r4 == 0) goto L_0x01b1
            r1.add(r2, r4)
        L_0x01b1:
            if (r5 == 0) goto L_0x0004
            r1.add(r2, r5)
            goto L_0x0004
        L_0x01b8:
            java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r0 = r15.a
            int r0 = r0.size()
            r1 = 0
        L_0x01bf:
            if (r1 >= r0) goto L_0x0296
            java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r2 = r15.a
            java.lang.Object r2 = r2.get(r1)
            android.support.v7.widget.AdapterHelper$UpdateOp r2 = (android.support.v7.widget.AdapterHelper.UpdateOp) r2
            int r9 = r2.a
            if (r9 == r6) goto L_0x0235
            r10 = 8
            if (r9 == r10) goto L_0x0231
            switch(r9) {
                case 1: goto L_0x022d;
                case 2: goto L_0x01d6;
                default: goto L_0x01d4;
            }
        L_0x01d4:
            goto L_0x0289
        L_0x01d6:
            int r9 = r2.b
            int r10 = r2.b
            int r11 = r2.d
            int r10 = r10 + r11
            int r11 = r2.b
            r12 = 0
            r13 = -1
        L_0x01e1:
            if (r11 >= r10) goto L_0x0218
            android.support.v7.widget.AdapterHelper$Callback r14 = r15.c
            android.support.v7.widget.RecyclerView$ViewHolder r14 = r14.a(r11)
            if (r14 != 0) goto L_0x0200
            boolean r14 = r15.c(r11)
            if (r14 == 0) goto L_0x01f2
            goto L_0x0200
        L_0x01f2:
            if (r13 != r8) goto L_0x01fd
            android.support.v7.widget.AdapterHelper$UpdateOp r13 = r15.a(r4, r9, r12, r5)
            r15.c(r13)
            r13 = 1
            goto L_0x01fe
        L_0x01fd:
            r13 = 0
        L_0x01fe:
            r14 = 0
            goto L_0x020d
        L_0x0200:
            if (r13 != 0) goto L_0x020b
            android.support.v7.widget.AdapterHelper$UpdateOp r13 = r15.a(r4, r9, r12, r5)
            r15.b(r13)
            r13 = 1
            goto L_0x020c
        L_0x020b:
            r13 = 0
        L_0x020c:
            r14 = 1
        L_0x020d:
            if (r13 == 0) goto L_0x0213
            int r11 = r11 - r12
            int r10 = r10 - r12
            r12 = 1
            goto L_0x0215
        L_0x0213:
            int r12 = r12 + 1
        L_0x0215:
            int r11 = r11 + r8
            r13 = r14
            goto L_0x01e1
        L_0x0218:
            int r10 = r2.d
            if (r12 == r10) goto L_0x0223
            r15.a(r2)
            android.support.v7.widget.AdapterHelper$UpdateOp r2 = r15.a(r4, r9, r12, r5)
        L_0x0223:
            if (r13 != 0) goto L_0x0229
            r15.b(r2)
            goto L_0x0289
        L_0x0229:
            r15.c(r2)
            goto L_0x0289
        L_0x022d:
            r15.c(r2)
            goto L_0x0289
        L_0x0231:
            r15.c(r2)
            goto L_0x0289
        L_0x0235:
            int r9 = r2.b
            int r10 = r2.b
            int r11 = r2.d
            int r10 = r10 + r11
            int r11 = r2.b
            r13 = r9
            r9 = 0
            r12 = -1
        L_0x0241:
            if (r11 >= r10) goto L_0x0273
            android.support.v7.widget.AdapterHelper$Callback r14 = r15.c
            android.support.v7.widget.RecyclerView$ViewHolder r14 = r14.a(r11)
            if (r14 != 0) goto L_0x0261
            boolean r14 = r15.c(r11)
            if (r14 == 0) goto L_0x0252
            goto L_0x0261
        L_0x0252:
            if (r12 != r8) goto L_0x025f
            java.lang.Object r12 = r2.c
            android.support.v7.widget.AdapterHelper$UpdateOp r9 = r15.a(r6, r13, r9, r12)
            r15.c(r9)
            r13 = r11
            r9 = 0
        L_0x025f:
            r12 = 0
            goto L_0x026f
        L_0x0261:
            if (r12 != 0) goto L_0x026e
            java.lang.Object r12 = r2.c
            android.support.v7.widget.AdapterHelper$UpdateOp r9 = r15.a(r6, r13, r9, r12)
            r15.b(r9)
            r13 = r11
            r9 = 0
        L_0x026e:
            r12 = 1
        L_0x026f:
            int r9 = r9 + r8
            int r11 = r11 + 1
            goto L_0x0241
        L_0x0273:
            int r10 = r2.d
            if (r9 == r10) goto L_0x0280
            java.lang.Object r10 = r2.c
            r15.a(r2)
            android.support.v7.widget.AdapterHelper$UpdateOp r2 = r15.a(r6, r13, r9, r10)
        L_0x0280:
            if (r12 != 0) goto L_0x0286
            r15.b(r2)
            goto L_0x0289
        L_0x0286:
            r15.c(r2)
        L_0x0289:
            java.lang.Runnable r2 = r15.d
            if (r2 == 0) goto L_0x0292
            java.lang.Runnable r2 = r15.d
            r2.run()
        L_0x0292:
            int r1 = r1 + 1
            goto L_0x01bf
        L_0x0296:
            java.util.ArrayList<android.support.v7.widget.AdapterHelper$UpdateOp> r0 = r15.a
            r0.clear()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AdapterHelper.b():void");
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            this.c.b(this.b.get(i));
        }
        a((List<UpdateOp>) this.b);
        this.g = 0;
    }

    private void b(UpdateOp updateOp) {
        int i;
        if (updateOp.a == 1 || updateOp.a == 8) {
            throw new IllegalArgumentException("should not dispatch add or move for pre layout");
        }
        int b2 = b(updateOp.b, updateOp.a);
        int i2 = updateOp.b;
        int i3 = updateOp.a;
        if (i3 == 2) {
            i = 0;
        } else if (i3 != 4) {
            throw new IllegalArgumentException("op should be remove or update.".concat(String.valueOf(updateOp)));
        } else {
            i = 1;
        }
        int i4 = b2;
        int i5 = i2;
        int i6 = 1;
        for (int i7 = 1; i7 < updateOp.d; i7++) {
            int b3 = b(updateOp.b + (i * i7), updateOp.a);
            int i8 = updateOp.a;
            if (i8 == 2 ? b3 == i4 : i8 == 4 && b3 == i4 + 1) {
                i6++;
            } else {
                UpdateOp a2 = a(updateOp.a, i4, i6, updateOp.c);
                a(a2, i5);
                a(a2);
                if (updateOp.a == 4) {
                    i5 += i6;
                }
                i4 = b3;
                i6 = 1;
            }
        }
        Object obj = updateOp.c;
        a(updateOp);
        if (i6 > 0) {
            UpdateOp a3 = a(updateOp.a, i4, i6, obj);
            a(a3, i5);
            a(a3);
        }
    }

    private void a(UpdateOp updateOp, int i) {
        this.c.a(updateOp);
        int i2 = updateOp.a;
        if (i2 == 2) {
            this.c.a(i, updateOp.d);
        } else if (i2 != 4) {
            throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        } else {
            this.c.a(i, updateOp.d, updateOp.c);
        }
    }

    private int b(int i, int i2) {
        int i3;
        int i4;
        for (int size = this.b.size() - 1; size >= 0; size--) {
            UpdateOp updateOp = this.b.get(size);
            if (updateOp.a == 8) {
                if (updateOp.b < updateOp.d) {
                    i4 = updateOp.b;
                    i3 = updateOp.d;
                } else {
                    i4 = updateOp.d;
                    i3 = updateOp.b;
                }
                if (i < i4 || i > i3) {
                    if (i < updateOp.b) {
                        if (i2 == 1) {
                            updateOp.b++;
                            updateOp.d++;
                        } else if (i2 == 2) {
                            updateOp.b--;
                            updateOp.d--;
                        }
                    }
                } else if (i4 == updateOp.b) {
                    if (i2 == 1) {
                        updateOp.d++;
                    } else if (i2 == 2) {
                        updateOp.d--;
                    }
                    i++;
                } else {
                    if (i2 == 1) {
                        updateOp.b++;
                    } else if (i2 == 2) {
                        updateOp.b--;
                    }
                    i--;
                }
            } else if (updateOp.b <= i) {
                if (updateOp.a == 1) {
                    i -= updateOp.d;
                } else if (updateOp.a == 2) {
                    i += updateOp.d;
                }
            } else if (i2 == 1) {
                updateOp.b++;
            } else if (i2 == 2) {
                updateOp.b--;
            }
        }
        for (int size2 = this.b.size() - 1; size2 >= 0; size2--) {
            UpdateOp updateOp2 = this.b.get(size2);
            if (updateOp2.a == 8) {
                if (updateOp2.d == updateOp2.b || updateOp2.d < 0) {
                    this.b.remove(size2);
                    a(updateOp2);
                }
            } else if (updateOp2.d <= 0) {
                this.b.remove(size2);
                a(updateOp2);
            }
        }
        return i;
    }

    private boolean c(int i) {
        int size = this.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            UpdateOp updateOp = this.b.get(i2);
            if (updateOp.a == 8) {
                if (a(updateOp.d, i2 + 1) == i) {
                    return true;
                }
            } else if (updateOp.a == 1) {
                int i3 = updateOp.b + updateOp.d;
                for (int i4 = updateOp.b; i4 < i3; i4++) {
                    if (a(i4, i2 + 1) == i) {
                        return true;
                    }
                }
                continue;
            } else {
                continue;
            }
        }
        return false;
    }

    private void c(UpdateOp updateOp) {
        this.b.add(updateOp);
        int i = updateOp.a;
        if (i == 4) {
            this.c.a(updateOp.b, updateOp.d, updateOp.c);
        } else if (i != 8) {
            switch (i) {
                case 1:
                    this.c.c(updateOp.b, updateOp.d);
                    return;
                case 2:
                    this.c.b(updateOp.b, updateOp.d);
                    return;
                default:
                    throw new IllegalArgumentException("Unknown update op type for ".concat(String.valueOf(updateOp)));
            }
        } else {
            this.c.d(updateOp.b, updateOp.d);
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean d() {
        return this.a.size() > 0;
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(int i) {
        return (i & this.g) != 0;
    }

    /* access modifiers changed from: 0000 */
    public final int b(int i) {
        return a(i, 0);
    }

    /* access modifiers changed from: 0000 */
    public final int a(int i, int i2) {
        int size = this.b.size();
        while (i2 < size) {
            UpdateOp updateOp = this.b.get(i2);
            if (updateOp.a == 8) {
                if (updateOp.b == i) {
                    i = updateOp.d;
                } else {
                    if (updateOp.b < i) {
                        i--;
                    }
                    if (updateOp.d <= i) {
                        i++;
                    }
                }
            } else if (updateOp.b > i) {
                continue;
            } else if (updateOp.a == 2) {
                if (i < updateOp.b + updateOp.d) {
                    return -1;
                }
                i -= updateOp.d;
            } else if (updateOp.a == 1) {
                i += updateOp.d;
            }
            i2++;
        }
        return i;
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        c();
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            UpdateOp updateOp = this.a.get(i);
            int i2 = updateOp.a;
            if (i2 == 4) {
                this.c.b(updateOp);
                this.c.a(updateOp.b, updateOp.d, updateOp.c);
            } else if (i2 != 8) {
                switch (i2) {
                    case 1:
                        this.c.b(updateOp);
                        this.c.c(updateOp.b, updateOp.d);
                        break;
                    case 2:
                        this.c.b(updateOp);
                        this.c.a(updateOp.b, updateOp.d);
                        break;
                }
            } else {
                this.c.b(updateOp);
                this.c.d(updateOp.b, updateOp.d);
            }
            if (this.d != null) {
                this.d.run();
            }
        }
        a((List<UpdateOp>) this.a);
        this.g = 0;
    }

    public final UpdateOp a(int i, int i2, int i3, Object obj) {
        UpdateOp updateOp = (UpdateOp) this.h.acquire();
        if (updateOp == null) {
            return new UpdateOp(i, i2, i3, obj);
        }
        updateOp.a = i;
        updateOp.b = i2;
        updateOp.d = i3;
        updateOp.c = obj;
        return updateOp;
    }

    public final void a(UpdateOp updateOp) {
        if (!this.e) {
            updateOp.c = null;
            this.h.release(updateOp);
        }
    }

    private void a(List<UpdateOp> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            a(list.get(i));
        }
        list.clear();
    }
}
