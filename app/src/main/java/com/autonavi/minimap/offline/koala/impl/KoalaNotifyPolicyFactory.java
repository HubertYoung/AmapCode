package com.autonavi.minimap.offline.koala.impl;

import com.autonavi.minimap.offline.koala.intf.IKoalaNotifyPredicate;
import com.autonavi.minimap.offline.koala.intf.IKoalaNotifyPredicateFactory;

public class KoalaNotifyPolicyFactory {

    static class a implements IKoalaNotifyPredicate {
        private long a;
        private long b;

        public a(long j) {
            this.a = j;
        }

        public final boolean canNotify(long j, long j2) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.b < this.a) {
                return false;
            }
            this.b = currentTimeMillis;
            return true;
        }
    }

    static class b implements IKoalaNotifyPredicateFactory {
        private long a;

        public b(long j) {
            this.a = j;
        }

        public final IKoalaNotifyPredicate create() {
            return new a(this.a);
        }
    }

    static class c implements IKoalaNotifyPredicate {
        private float a;
        private float b;

        public c(float f) {
            this.a = f;
        }

        public final boolean canNotify(long j, long j2) {
            float f = (((float) j2) * 1.0f) / ((float) j);
            if (f - this.b <= this.a) {
                return false;
            }
            this.b = f;
            return true;
        }
    }

    static class d implements IKoalaNotifyPredicateFactory {
        private float a;

        public d(float f) {
            this.a = f;
        }

        public final IKoalaNotifyPredicate create() {
            return new c(this.a);
        }
    }

    public static IKoalaNotifyPredicateFactory createPercentPolicy(float f) {
        return new d(f);
    }

    public static IKoalaNotifyPredicateFactory createIntervalPolicy(long j) {
        return new b(j);
    }
}
