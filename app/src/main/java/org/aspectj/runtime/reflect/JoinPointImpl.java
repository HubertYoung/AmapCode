package org.aspectj.runtime.reflect;

import org.aspectj.lang.JoinPoint.EnclosingStaticPart;
import org.aspectj.lang.JoinPoint.StaticPart;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

class JoinPointImpl implements ProceedingJoinPoint {
    Object _this;
    private AroundClosure a;
    Object[] args;
    StaticPart staticPart;
    Object target;

    class EnclosingStaticPartImpl extends StaticPartImpl implements EnclosingStaticPart {
        public EnclosingStaticPartImpl(int count, String kind, Signature signature, SourceLocation sourceLocation) {
            super(count, kind, signature, sourceLocation);
        }
    }

    class StaticPartImpl implements StaticPart {
        private int a;
        String kind;
        Signature signature;
        SourceLocation sourceLocation;

        public StaticPartImpl(int id, String kind2, Signature signature2, SourceLocation sourceLocation2) {
            this.kind = kind2;
            this.signature = signature2;
            this.sourceLocation = sourceLocation2;
            this.a = id;
        }

        public int getId() {
            return this.a;
        }

        public String getKind() {
            return this.kind;
        }

        public Signature getSignature() {
            return this.signature;
        }

        public SourceLocation getSourceLocation() {
            return this.sourceLocation;
        }

        /* access modifiers changed from: 0000 */
        public String toString(StringMaker sm) {
            StringBuffer buf = new StringBuffer();
            buf.append(sm.makeKindName(getKind()));
            buf.append("(");
            buf.append(((SignatureImpl) getSignature()).toString(sm));
            buf.append(")");
            return buf.toString();
        }

        public final String toString() {
            return toString(StringMaker.middleStringMaker);
        }

        public final String toShortString() {
            return toString(StringMaker.shortStringMaker);
        }

        public final String toLongString() {
            return toString(StringMaker.longStringMaker);
        }
    }

    public JoinPointImpl(StaticPart staticPart2, Object _this2, Object target2, Object[] args2) {
        this.staticPart = staticPart2;
        this._this = _this2;
        this.target = target2;
        this.args = args2;
    }

    public Object getThis() {
        return this._this;
    }

    public Object getTarget() {
        return this.target;
    }

    public Object[] getArgs() {
        if (this.args == null) {
            this.args = new Object[0];
        }
        Object[] argsCopy = new Object[this.args.length];
        System.arraycopy(this.args, 0, argsCopy, 0, this.args.length);
        return argsCopy;
    }

    public StaticPart getStaticPart() {
        return this.staticPart;
    }

    public String getKind() {
        return this.staticPart.getKind();
    }

    public Signature getSignature() {
        return this.staticPart.getSignature();
    }

    public SourceLocation getSourceLocation() {
        return this.staticPart.getSourceLocation();
    }

    public final String toString() {
        return this.staticPart.toString();
    }

    public final String toShortString() {
        return this.staticPart.toShortString();
    }

    public final String toLongString() {
        return this.staticPart.toLongString();
    }

    public void set$AroundClosure(AroundClosure arc) {
        this.a = arc;
    }

    public Object proceed() {
        if (this.a == null) {
            return null;
        }
        return this.a.run(this.a.getState());
    }

    public Object proceed(Object[] adviceBindings) {
        char c;
        if (this.a == null) {
            return null;
        }
        int flags = this.a.getFlags();
        boolean thisTargetTheSame = (65536 & flags) != 0;
        boolean hasThis = (flags & 4096) != 0;
        boolean bindsThis = (flags & 256) != 0;
        boolean hasTarget = (flags & 16) != 0;
        boolean bindsTarget = (flags & 1) != 0;
        Object[] state = this.a.getState();
        int firstArgumentIndexIntoAdviceBindings = 0;
        int firstArgumentIndexIntoState = (hasThis ? 1 : 0) + 0 + ((!hasTarget || thisTargetTheSame) ? 0 : 1);
        if (hasThis && bindsThis) {
            firstArgumentIndexIntoAdviceBindings = 1;
            state[0] = adviceBindings[0];
        }
        if (hasTarget && bindsTarget) {
            if (thisTargetTheSame) {
                firstArgumentIndexIntoAdviceBindings = (bindsThis ? 1 : 0) + 1;
                if (bindsThis) {
                    c = 1;
                } else {
                    c = 0;
                }
                state[0] = adviceBindings[c];
            } else {
                firstArgumentIndexIntoAdviceBindings = ((!hasThis || !bindsThis) ? 0 : 1) + ((!hasTarget || !bindsTarget || thisTargetTheSame) ? 0 : 1);
                state[hasThis ? (char) 1 : 0] = adviceBindings[(!hasThis || !bindsThis) ? 0 : 1];
            }
        }
        for (int i = firstArgumentIndexIntoAdviceBindings; i < adviceBindings.length; i++) {
            state[(i - firstArgumentIndexIntoAdviceBindings) + firstArgumentIndexIntoState] = adviceBindings[i];
        }
        return this.a.run(state);
    }
}
