package defpackage;

/* renamed from: ezs reason: default package */
/* compiled from: ConfigItem */
public final class ezs {
    public String a;
    public String b;

    public ezs(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public final int hashCode() {
        return (this.a == null ? 0 : this.a.hashCode()) + 31;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ezs ezs = (ezs) obj;
        if (this.a == null) {
            if (ezs.a != null) {
                return false;
            }
        } else if (!this.a.equals(ezs.a)) {
            return false;
        }
        return true;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ConfigItem{mKey='");
        sb.append(this.a);
        sb.append('\'');
        sb.append(", mValue='");
        sb.append(this.b);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }
}
