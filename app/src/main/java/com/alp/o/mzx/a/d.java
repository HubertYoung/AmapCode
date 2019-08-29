package com.alp.o.mzx.a;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class d {
    private static Set<Character> a;
    private static d b = null;

    private enum a {
        NUMERIC(new b[]{b.NUMERIC, b.ALPHANUMERIC, b.BYTE}),
        ALPHANUMERIC(new b[]{b.ALPHANUMERIC, b.BYTE}),
        BYTE(new b[]{b.BYTE});
        
        b[] d;

        private a(b[] modes) {
            this.d = modes;
        }

        public final String toString() {
            if (this == NUMERIC) {
                return "NUMERIC";
            }
            if (this == ALPHANUMERIC) {
                return "ALPHANUMERIC";
            }
            return "BYTE";
        }
    }

    private class b {
        int a;
        a b;
        int c;
        int d;

        b(String content, String encoding, int s, a t, int l) {
            this.a = s;
            this.b = t;
            this.c = l;
            try {
                this.d = content.substring(s, s + l).getBytes(encoding).length;
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }

        public final String toString() {
            return String.format("s:%d len:%d bytes:%d type:%s", new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.c), Integer.valueOf(this.d), this.b});
        }
    }

    private class c {
        int a;
        int b;
        int c;
        b d;
        c e;

        c(a cost, int startPos, b mode, c lastState) {
            this.a = cost.a;
            this.c = cost.b;
            this.b = startPos;
            this.d = mode;
            this.e = lastState;
        }

        public final String toString() {
            return String.format("State b:%d s:%d m%s <- %s", new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b), this.d.name(), this.e});
        }

        /* access modifiers changed from: 0000 */
        public final boolean a(c state) {
            return this.a < state.a || (this.a == state.a && this.c < state.c);
        }
    }

    public static d a() {
        if (b != null) {
            return b;
        }
        d dVar = new d();
        b = dVar;
        return dVar;
    }

    private d() {
        a = new HashSet();
        for (int i = 0; i < " $%*+-./:".length(); i++) {
            a.add(Character.valueOf(" $%*+-./:".charAt(i)));
        }
    }

    private static a a(Character ch) {
        if ('0' <= ch.charValue() && ch.charValue() <= '9') {
            return a.NUMERIC;
        }
        if (('A' > ch.charValue() || ch.charValue() > 'Z') && !a.contains(ch)) {
            return a.BYTE;
        }
        return a.ALPHANUMERIC;
    }

    private ArrayList b(String content, String encoding) {
        ArrayList segs = new ArrayList();
        a lastType = null;
        int lastPos = 0;
        for (int i = 0; i < content.length(); i++) {
            a currentType = a(Character.valueOf(content.charAt(i)));
            if (i == 0 || currentType != lastType) {
                if (i != 0) {
                    segs.add(new b(content, encoding, lastPos, lastType, i - lastPos));
                }
                lastType = currentType;
                lastPos = i;
            }
        }
        segs.add(new b(content, encoding, lastPos, lastType, content.length() - lastPos));
        return segs;
    }

    private c a(ArrayList<b> segs, int versionSlot) {
        b[] bVarArr;
        c[] cur = new c[3];
        b[] bVarArr2 = segs.get(0).b.d;
        int length = bVarArr2.length;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= length) {
                break;
            }
            b encodeMode = bVarArr2[i2];
            cur[encodeMode.a()] = new c(e.a(segs.get(0).d, encodeMode, versionSlot), 0, encodeMode, null);
            i = i2 + 1;
        }
        for (int i3 = 1; i3 < segs.size(); i3++) {
            c[] next = new c[3];
            b seg = segs.get(i3);
            for (int curMethodId = 0; curMethodId < 3; curMethodId++) {
                if (cur[curMethodId] != null) {
                    c curState = cur[curMethodId];
                    for (b newMode : seg.b.d) {
                        c newState = a(curState, seg, newMode, versionSlot);
                        if (next[newMode.a()] == null || newState.a(next[newMode.a()])) {
                            next[newMode.a()] = newState;
                        }
                    }
                }
            }
            cur = next;
        }
        c bestMode = null;
        for (int i4 = 0; i4 < 3; i4++) {
            if (cur[i4] != null && (bestMode == null || cur[i4].a(bestMode))) {
                bestMode = cur[i4];
            }
        }
        return bestMode;
    }

    private c a(c curState, b seg, b newMode, int versionSlot) {
        if (curState.d != newMode) {
            a bitCount = e.a(seg.d, newMode, versionSlot);
            bitCount.a += curState.a;
            return new c(bitCount, seg.a, newMode, curState);
        }
        return new c(e.a(curState.a, curState.c, curState.d, seg.d), curState.b, newMode, curState.e);
    }

    public final c a(String content, String encoding) {
        ArrayList segs = b(content, encoding);
        ArrayList[] allModes = new ArrayList[3];
        int[] bitCounts = new int[3];
        for (int versionSlot = 0; versionSlot < 3; versionSlot++) {
            c bestMode = a(segs, versionSlot);
            allModes[versionSlot] = a(bestMode, content.length());
            bitCounts[versionSlot] = bestMode.a;
        }
        c mode = c.a(allModes, bitCounts);
        Log.e("MixedModeChooser", "mixed Mode is " + mode);
        return mode;
    }

    private static ArrayList a(c bestMode, int currentTail) {
        ArrayList modes = new ArrayList();
        while (bestMode != null) {
            modes.add(new f(bestMode.b, currentTail - bestMode.b, bestMode.d.b()));
            currentTail = bestMode.b;
            bestMode = bestMode.e;
        }
        Collections.reverse(modes);
        return modes;
    }
}
