package defpackage;

/* renamed from: cak reason: default package */
/* compiled from: DateInfo */
public final class cak {
    public String a;
    public boolean b = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        cak cak = (cak) obj;
        return this.a == null ? cak.a == null : this.a.equals(cak.a);
    }

    public final int hashCode() {
        return (this.a != null ? this.a.hashCode() : 0) * 31;
    }
}
