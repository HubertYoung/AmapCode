package defpackage;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.graphics.Path.Op;
import android.os.Build.VERSION;
import com.airbnb.lottie.model.content.MergePaths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@TargetApi(19)
/* renamed from: fm reason: default package */
/* compiled from: MergePathsContent */
public final class fm implements fl, fn {
    private final Path a = new Path();
    private final Path b = new Path();
    private final Path c = new Path();
    private final String d;
    private final List<fn> e = new ArrayList();
    private final MergePaths f;

    public fm(MergePaths mergePaths) {
        if (VERSION.SDK_INT < 19) {
            throw new IllegalStateException("Merge paths are not supported pre-KitKat.");
        }
        this.d = mergePaths.a;
        this.f = mergePaths;
    }

    public final void a(ListIterator<fe> listIterator) {
        while (listIterator.hasPrevious()) {
            if (listIterator.previous() == this) {
                break;
            }
        }
        while (listIterator.hasPrevious()) {
            fe previous = listIterator.previous();
            if (previous instanceof fn) {
                this.e.add((fn) previous);
                listIterator.remove();
            }
        }
    }

    public final void a(List<fe> list, List<fe> list2) {
        for (int i = 0; i < this.e.size(); i++) {
            this.e.get(i).a(list, list2);
        }
    }

    public final Path e() {
        this.c.reset();
        switch (this.f.b) {
            case Merge:
                for (int i = 0; i < this.e.size(); i++) {
                    this.c.addPath(this.e.get(i).e());
                }
                break;
            case Add:
                a(Op.UNION);
                break;
            case Subtract:
                a(Op.REVERSE_DIFFERENCE);
                break;
            case Intersect:
                a(Op.INTERSECT);
                break;
            case ExcludeIntersections:
                a(Op.XOR);
                break;
        }
        return this.c;
    }

    public final String b() {
        return this.d;
    }

    @TargetApi(19)
    private void a(Op op) {
        this.b.reset();
        this.a.reset();
        for (int size = this.e.size() - 1; size > 0; size--) {
            fn fnVar = this.e.get(size);
            if (fnVar instanceof ff) {
                ff ffVar = (ff) fnVar;
                List<fn> c2 = ffVar.c();
                for (int size2 = c2.size() - 1; size2 >= 0; size2--) {
                    Path e2 = c2.get(size2).e();
                    e2.transform(ffVar.d());
                    this.b.addPath(e2);
                }
            } else {
                this.b.addPath(fnVar.e());
            }
        }
        fn fnVar2 = this.e.get(0);
        if (fnVar2 instanceof ff) {
            ff ffVar2 = (ff) fnVar2;
            List<fn> c3 = ffVar2.c();
            for (int i = 0; i < c3.size(); i++) {
                Path e3 = c3.get(i).e();
                e3.transform(ffVar2.d());
                this.a.addPath(e3);
            }
        } else {
            this.a.set(fnVar2.e());
        }
        this.c.op(this.a, this.b, op);
    }
}
