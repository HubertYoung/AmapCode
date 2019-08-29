package defpackage;

import android.view.ViewGroup;
import java.util.Iterator;
import java.util.List;

/* renamed from: cbk reason: default package */
/* compiled from: PoiDetailInnerPageHelper */
final class cbk {
    private List<a> a;

    cbk(List<a> list) {
        this.a = list;
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(a aVar, ViewGroup viewGroup) {
        int lastIndexOf = this.a.lastIndexOf(aVar);
        if (lastIndexOf != -1) {
            a(viewGroup, lastIndexOf + 1, this.a.size() - 1);
        }
        if (lastIndexOf != -1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public final void b(a aVar, ViewGroup viewGroup) {
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next != null && aVar.o == next.o) {
                it.remove();
                viewGroup.removeView(next.q);
                next.b();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(ViewGroup viewGroup) {
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next != null) {
                it.remove();
                viewGroup.removeView(next.q);
                next.b();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(ViewGroup viewGroup) {
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (!(next == null || next.o == 8)) {
                it.remove();
                viewGroup.removeView(next.q);
                next.b();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void c(ViewGroup viewGroup) {
        int size = this.a.size();
        if (size > 0) {
            a remove = this.a.remove(size - 1);
            viewGroup.removeView(remove.q);
            remove.b();
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean d(ViewGroup viewGroup) {
        if (this.a.size() <= 10) {
            return false;
        }
        a(viewGroup, 0, 0);
        return true;
    }

    /* access modifiers changed from: 0000 */
    public final void e(ViewGroup viewGroup) {
        Iterator<a> it = this.a.iterator();
        while (it.hasNext()) {
            a next = it.next();
            if (next != null) {
                it.remove();
                if (!(viewGroup == null || next.q == null)) {
                    viewGroup.removeView(next.q);
                }
                next.b();
            }
        }
    }

    private void a(ViewGroup viewGroup, int i, int i2) {
        if (this.a != null && i2 >= i) {
            int size = this.a.size();
            if (size > i && size > i2) {
                while (i2 >= i) {
                    a remove = this.a.remove(i2);
                    viewGroup.removeView(remove.q);
                    remove.b();
                    i2--;
                }
            }
        }
    }
}
