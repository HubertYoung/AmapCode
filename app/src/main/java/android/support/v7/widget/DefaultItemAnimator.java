package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DefaultItemAnimator extends SimpleItemAnimator {
    private ArrayList<ViewHolder> g = new ArrayList<>();
    private ArrayList<ViewHolder> h = new ArrayList<>();
    private ArrayList<MoveInfo> i = new ArrayList<>();
    private ArrayList<ChangeInfo> j = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<ArrayList<ViewHolder>> k = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<ArrayList<MoveInfo>> l = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<ArrayList<ChangeInfo>> m = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<ViewHolder> n = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<ViewHolder> o = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<ViewHolder> p = new ArrayList<>();
    /* access modifiers changed from: private */
    public ArrayList<ViewHolder> q = new ArrayList<>();

    static class ChangeInfo {
        public ViewHolder a;
        public ViewHolder b;
        public int c;
        public int d;
        public int e;
        public int f;

        /* synthetic */ ChangeInfo(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4, byte b2) {
            this(viewHolder, viewHolder2, i, i2, i3, i4);
        }

        private ChangeInfo(ViewHolder viewHolder, ViewHolder viewHolder2) {
            this.a = viewHolder;
            this.b = viewHolder2;
        }

        private ChangeInfo(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
            this(viewHolder, viewHolder2);
            this.c = i;
            this.d = i2;
            this.e = i3;
            this.f = i4;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ChangeInfo{oldHolder=");
            sb.append(this.a);
            sb.append(", newHolder=");
            sb.append(this.b);
            sb.append(", fromX=");
            sb.append(this.c);
            sb.append(", fromY=");
            sb.append(this.d);
            sb.append(", toX=");
            sb.append(this.e);
            sb.append(", toY=");
            sb.append(this.f);
            sb.append('}');
            return sb.toString();
        }
    }

    static class MoveInfo {
        public ViewHolder a;
        public int b;
        public int c;
        public int d;
        public int e;

        /* synthetic */ MoveInfo(ViewHolder viewHolder, int i, int i2, int i3, int i4, byte b2) {
            this(viewHolder, i, i2, i3, i4);
        }

        private MoveInfo(ViewHolder viewHolder, int i, int i2, int i3, int i4) {
            this.a = viewHolder;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = i4;
        }
    }

    static class VpaListenerAdapter implements ViewPropertyAnimatorListener {
        public void onAnimationCancel(View view) {
        }

        public void onAnimationEnd(View view) {
        }

        public void onAnimationStart(View view) {
        }

        private VpaListenerAdapter() {
        }

        /* synthetic */ VpaListenerAdapter(byte b) {
            this();
        }
    }

    public final void a() {
        long j2;
        long j3;
        boolean z = !this.g.isEmpty();
        boolean z2 = !this.i.isEmpty();
        boolean z3 = !this.j.isEmpty();
        boolean z4 = !this.h.isEmpty();
        if (z || z2 || z4 || z3) {
            Iterator<ViewHolder> it = this.g.iterator();
            while (it.hasNext()) {
                final ViewHolder next = it.next();
                final ViewPropertyAnimatorCompat animate = ViewCompat.animate(next.itemView);
                this.p.add(next);
                animate.setDuration(this.c).alpha(0.0f).setListener(new VpaListenerAdapter() {
                    public void onAnimationStart(View view) {
                    }

                    public void onAnimationEnd(View view) {
                        animate.setListener(null);
                        ViewCompat.setAlpha(view, 1.0f);
                        DefaultItemAnimator.this.f(next);
                        DefaultItemAnimator.this.p.remove(next);
                        DefaultItemAnimator.this.f();
                    }
                }).start();
            }
            this.g.clear();
            if (z2) {
                final ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.i);
                this.l.add(arrayList);
                this.i.clear();
                AnonymousClass1 r6 = new Runnable() {
                    public void run() {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            MoveInfo moveInfo = (MoveInfo) it.next();
                            DefaultItemAnimator.a(DefaultItemAnimator.this, moveInfo.a, moveInfo.b, moveInfo.c, moveInfo.d, moveInfo.e);
                        }
                        arrayList.clear();
                        DefaultItemAnimator.this.l.remove(arrayList);
                    }
                };
                if (z) {
                    ViewCompat.postOnAnimationDelayed(((MoveInfo) arrayList.get(0)).a.itemView, r6, this.c);
                } else {
                    r6.run();
                }
            }
            if (z3) {
                final ArrayList arrayList2 = new ArrayList();
                arrayList2.addAll(this.j);
                this.m.add(arrayList2);
                this.j.clear();
                AnonymousClass2 r62 = new Runnable() {
                    public void run() {
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            DefaultItemAnimator.a(DefaultItemAnimator.this, (ChangeInfo) it.next());
                        }
                        arrayList2.clear();
                        DefaultItemAnimator.this.m.remove(arrayList2);
                    }
                };
                if (z) {
                    ViewCompat.postOnAnimationDelayed(((ChangeInfo) arrayList2.get(0)).a.itemView, r62, this.c);
                } else {
                    r62.run();
                }
            }
            if (z4) {
                final ArrayList arrayList3 = new ArrayList();
                arrayList3.addAll(this.h);
                this.k.add(arrayList3);
                this.h.clear();
                AnonymousClass3 r5 = new Runnable() {
                    public void run() {
                        Iterator it = arrayList3.iterator();
                        while (it.hasNext()) {
                            DefaultItemAnimator.a(DefaultItemAnimator.this, (ViewHolder) it.next());
                        }
                        arrayList3.clear();
                        DefaultItemAnimator.this.k.remove(arrayList3);
                    }
                };
                if (z || z2 || z3) {
                    long j4 = 0;
                    if (z) {
                        j2 = this.c;
                    } else {
                        j2 = 0;
                    }
                    if (z2) {
                        j3 = this.d;
                    } else {
                        j3 = 0;
                    }
                    if (z3) {
                        j4 = this.e;
                    }
                    ViewCompat.postOnAnimationDelayed(((ViewHolder) arrayList3.get(0)).itemView, r5, j2 + Math.max(j3, j4));
                    return;
                }
                r5.run();
            }
        }
    }

    public final boolean a(ViewHolder viewHolder) {
        h(viewHolder);
        this.g.add(viewHolder);
        return true;
    }

    public final boolean b(ViewHolder viewHolder) {
        h(viewHolder);
        ViewCompat.setAlpha(viewHolder.itemView, 0.0f);
        this.h.add(viewHolder);
        return true;
    }

    public final boolean a(ViewHolder viewHolder, int i2, int i3, int i4, int i5) {
        View view = viewHolder.itemView;
        int translationX = (int) (((float) i2) + ViewCompat.getTranslationX(viewHolder.itemView));
        int translationY = (int) (((float) i3) + ViewCompat.getTranslationY(viewHolder.itemView));
        h(viewHolder);
        int i6 = i4 - translationX;
        int i7 = i5 - translationY;
        if (i6 == 0 && i7 == 0) {
            f(viewHolder);
            return false;
        }
        if (i6 != 0) {
            ViewCompat.setTranslationX(view, (float) (-i6));
        }
        if (i7 != 0) {
            ViewCompat.setTranslationY(view, (float) (-i7));
        }
        ArrayList<MoveInfo> arrayList = this.i;
        MoveInfo moveInfo = new MoveInfo(viewHolder, translationX, translationY, i4, i5, 0);
        arrayList.add(moveInfo);
        return true;
    }

    public final boolean a(ViewHolder viewHolder, ViewHolder viewHolder2, int i2, int i3, int i4, int i5) {
        ViewHolder viewHolder3 = viewHolder;
        ViewHolder viewHolder4 = viewHolder2;
        if (viewHolder3 == viewHolder4) {
            return a(viewHolder3, i2, i3, i4, i5);
        }
        float translationX = ViewCompat.getTranslationX(viewHolder3.itemView);
        float translationY = ViewCompat.getTranslationY(viewHolder3.itemView);
        float alpha = ViewCompat.getAlpha(viewHolder3.itemView);
        h(viewHolder);
        int i6 = (int) (((float) (i4 - i2)) - translationX);
        int i7 = (int) (((float) (i5 - i3)) - translationY);
        ViewCompat.setTranslationX(viewHolder3.itemView, translationX);
        ViewCompat.setTranslationY(viewHolder3.itemView, translationY);
        ViewCompat.setAlpha(viewHolder3.itemView, alpha);
        if (viewHolder4 != null) {
            h(viewHolder4);
            ViewCompat.setTranslationX(viewHolder4.itemView, (float) (-i6));
            ViewCompat.setTranslationY(viewHolder4.itemView, (float) (-i7));
            ViewCompat.setAlpha(viewHolder4.itemView, 0.0f);
        }
        ArrayList<ChangeInfo> arrayList = this.j;
        ChangeInfo changeInfo = new ChangeInfo(viewHolder3, viewHolder4, i2, i3, i4, i5, 0);
        arrayList.add(changeInfo);
        return true;
    }

    private void a(List<ChangeInfo> list, ViewHolder viewHolder) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ChangeInfo changeInfo = list.get(size);
            if (a(changeInfo, viewHolder) && changeInfo.a == null && changeInfo.b == null) {
                list.remove(changeInfo);
            }
        }
    }

    private void a(ChangeInfo changeInfo) {
        if (changeInfo.a != null) {
            a(changeInfo, changeInfo.a);
        }
        if (changeInfo.b != null) {
            a(changeInfo, changeInfo.b);
        }
    }

    private boolean a(ChangeInfo changeInfo, ViewHolder viewHolder) {
        if (changeInfo.b == viewHolder) {
            changeInfo.b = null;
        } else if (changeInfo.a != viewHolder) {
            return false;
        } else {
            changeInfo.a = null;
        }
        ViewCompat.setAlpha(viewHolder.itemView, 1.0f);
        ViewCompat.setTranslationX(viewHolder.itemView, 0.0f);
        ViewCompat.setTranslationY(viewHolder.itemView, 0.0f);
        f(viewHolder);
        return true;
    }

    public final void c(ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        ViewCompat.animate(view).cancel();
        int size = this.i.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else if (this.i.get(size).a == viewHolder) {
                ViewCompat.setTranslationY(view, 0.0f);
                ViewCompat.setTranslationX(view, 0.0f);
                f(viewHolder);
                this.i.remove(size);
            }
        }
        a((List<ChangeInfo>) this.j, viewHolder);
        if (this.g.remove(viewHolder)) {
            ViewCompat.setAlpha(view, 1.0f);
            f(viewHolder);
        }
        if (this.h.remove(viewHolder)) {
            ViewCompat.setAlpha(view, 1.0f);
            f(viewHolder);
        }
        for (int size2 = this.m.size() - 1; size2 >= 0; size2--) {
            ArrayList arrayList = this.m.get(size2);
            a((List<ChangeInfo>) arrayList, viewHolder);
            if (arrayList.isEmpty()) {
                this.m.remove(size2);
            }
        }
        for (int size3 = this.l.size() - 1; size3 >= 0; size3--) {
            ArrayList arrayList2 = this.l.get(size3);
            int size4 = arrayList2.size() - 1;
            while (true) {
                if (size4 < 0) {
                    break;
                } else if (((MoveInfo) arrayList2.get(size4)).a == viewHolder) {
                    ViewCompat.setTranslationY(view, 0.0f);
                    ViewCompat.setTranslationX(view, 0.0f);
                    f(viewHolder);
                    arrayList2.remove(size4);
                    if (arrayList2.isEmpty()) {
                        this.l.remove(size3);
                    }
                } else {
                    size4--;
                }
            }
        }
        for (int size5 = this.k.size() - 1; size5 >= 0; size5--) {
            ArrayList arrayList3 = this.k.get(size5);
            if (arrayList3.remove(viewHolder)) {
                ViewCompat.setAlpha(view, 1.0f);
                f(viewHolder);
                if (arrayList3.isEmpty()) {
                    this.k.remove(size5);
                }
            }
        }
        this.p.remove(viewHolder);
        this.n.remove(viewHolder);
        this.q.remove(viewHolder);
        this.o.remove(viewHolder);
        f();
    }

    private void h(ViewHolder viewHolder) {
        AnimatorCompatHelper.clearInterpolator(viewHolder.itemView);
        c(viewHolder);
    }

    public final boolean b() {
        return !this.h.isEmpty() || !this.j.isEmpty() || !this.i.isEmpty() || !this.g.isEmpty() || !this.o.isEmpty() || !this.p.isEmpty() || !this.n.isEmpty() || !this.q.isEmpty() || !this.l.isEmpty() || !this.k.isEmpty() || !this.m.isEmpty();
    }

    /* access modifiers changed from: private */
    public void f() {
        if (!b()) {
            d();
        }
    }

    public final void c() {
        int size = this.i.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            MoveInfo moveInfo = this.i.get(size);
            View view = moveInfo.a.itemView;
            ViewCompat.setTranslationY(view, 0.0f);
            ViewCompat.setTranslationX(view, 0.0f);
            f(moveInfo.a);
            this.i.remove(size);
        }
        for (int size2 = this.g.size() - 1; size2 >= 0; size2--) {
            f(this.g.get(size2));
            this.g.remove(size2);
        }
        int size3 = this.h.size();
        while (true) {
            size3--;
            if (size3 < 0) {
                break;
            }
            ViewHolder viewHolder = this.h.get(size3);
            ViewCompat.setAlpha(viewHolder.itemView, 1.0f);
            f(viewHolder);
            this.h.remove(size3);
        }
        for (int size4 = this.j.size() - 1; size4 >= 0; size4--) {
            a(this.j.get(size4));
        }
        this.j.clear();
        if (b()) {
            for (int size5 = this.l.size() - 1; size5 >= 0; size5--) {
                ArrayList arrayList = this.l.get(size5);
                for (int size6 = arrayList.size() - 1; size6 >= 0; size6--) {
                    MoveInfo moveInfo2 = (MoveInfo) arrayList.get(size6);
                    View view2 = moveInfo2.a.itemView;
                    ViewCompat.setTranslationY(view2, 0.0f);
                    ViewCompat.setTranslationX(view2, 0.0f);
                    f(moveInfo2.a);
                    arrayList.remove(size6);
                    if (arrayList.isEmpty()) {
                        this.l.remove(arrayList);
                    }
                }
            }
            for (int size7 = this.k.size() - 1; size7 >= 0; size7--) {
                ArrayList arrayList2 = this.k.get(size7);
                for (int size8 = arrayList2.size() - 1; size8 >= 0; size8--) {
                    ViewHolder viewHolder2 = (ViewHolder) arrayList2.get(size8);
                    ViewCompat.setAlpha(viewHolder2.itemView, 1.0f);
                    f(viewHolder2);
                    arrayList2.remove(size8);
                    if (arrayList2.isEmpty()) {
                        this.k.remove(arrayList2);
                    }
                }
            }
            for (int size9 = this.m.size() - 1; size9 >= 0; size9--) {
                ArrayList arrayList3 = this.m.get(size9);
                for (int size10 = arrayList3.size() - 1; size10 >= 0; size10--) {
                    a((ChangeInfo) arrayList3.get(size10));
                    if (arrayList3.isEmpty()) {
                        this.m.remove(arrayList3);
                    }
                }
            }
            a((List<ViewHolder>) this.p);
            a((List<ViewHolder>) this.o);
            a((List<ViewHolder>) this.n);
            a((List<ViewHolder>) this.q);
            d();
        }
    }

    private static void a(List<ViewHolder> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ViewCompat.animate(list.get(size).itemView).cancel();
        }
    }

    public final boolean a(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
        return !list.isEmpty() || super.a(viewHolder, list);
    }

    static /* synthetic */ void a(DefaultItemAnimator defaultItemAnimator, ViewHolder viewHolder, int i2, int i3, int i4, int i5) {
        View view = viewHolder.itemView;
        final int i6 = i4 - i2;
        final int i7 = i5 - i3;
        if (i6 != 0) {
            ViewCompat.animate(view).translationX(0.0f);
        }
        if (i7 != 0) {
            ViewCompat.animate(view).translationY(0.0f);
        }
        final ViewPropertyAnimatorCompat animate = ViewCompat.animate(view);
        defaultItemAnimator.o.add(viewHolder);
        ViewPropertyAnimatorCompat duration = animate.setDuration(defaultItemAnimator.d);
        final ViewHolder viewHolder2 = viewHolder;
        AnonymousClass6 r1 = new VpaListenerAdapter() {
            public void onAnimationStart(View view) {
            }

            public void onAnimationCancel(View view) {
                if (i6 != 0) {
                    ViewCompat.setTranslationX(view, 0.0f);
                }
                if (i7 != 0) {
                    ViewCompat.setTranslationY(view, 0.0f);
                }
            }

            public void onAnimationEnd(View view) {
                animate.setListener(null);
                DefaultItemAnimator.this.f(viewHolder2);
                DefaultItemAnimator.this.o.remove(viewHolder2);
                DefaultItemAnimator.this.f();
            }
        };
        duration.setListener(r1).start();
    }

    static /* synthetic */ void a(DefaultItemAnimator defaultItemAnimator, final ChangeInfo changeInfo) {
        View view;
        ViewHolder viewHolder = changeInfo.a;
        final View view2 = null;
        if (viewHolder == null) {
            view = null;
        } else {
            view = viewHolder.itemView;
        }
        ViewHolder viewHolder2 = changeInfo.b;
        if (viewHolder2 != null) {
            view2 = viewHolder2.itemView;
        }
        if (view != null) {
            final ViewPropertyAnimatorCompat duration = ViewCompat.animate(view).setDuration(defaultItemAnimator.e);
            defaultItemAnimator.q.add(changeInfo.a);
            duration.translationX((float) (changeInfo.e - changeInfo.c));
            duration.translationY((float) (changeInfo.f - changeInfo.d));
            duration.alpha(0.0f).setListener(new VpaListenerAdapter() {
                public void onAnimationStart(View view) {
                }

                public void onAnimationEnd(View view) {
                    duration.setListener(null);
                    ViewCompat.setAlpha(view, 1.0f);
                    ViewCompat.setTranslationX(view, 0.0f);
                    ViewCompat.setTranslationY(view, 0.0f);
                    DefaultItemAnimator.this.f(changeInfo.a);
                    DefaultItemAnimator.this.q.remove(changeInfo.a);
                    DefaultItemAnimator.this.f();
                }
            }).start();
        }
        if (view2 != null) {
            final ViewPropertyAnimatorCompat animate = ViewCompat.animate(view2);
            defaultItemAnimator.q.add(changeInfo.b);
            animate.translationX(0.0f).translationY(0.0f).setDuration(defaultItemAnimator.e).alpha(1.0f).setListener(new VpaListenerAdapter() {
                public void onAnimationStart(View view) {
                }

                public void onAnimationEnd(View view) {
                    animate.setListener(null);
                    ViewCompat.setAlpha(view2, 1.0f);
                    ViewCompat.setTranslationX(view2, 0.0f);
                    ViewCompat.setTranslationY(view2, 0.0f);
                    DefaultItemAnimator.this.f(changeInfo.b);
                    DefaultItemAnimator.this.q.remove(changeInfo.b);
                    DefaultItemAnimator.this.f();
                }
            }).start();
        }
    }

    static /* synthetic */ void a(DefaultItemAnimator defaultItemAnimator, final ViewHolder viewHolder) {
        final ViewPropertyAnimatorCompat animate = ViewCompat.animate(viewHolder.itemView);
        defaultItemAnimator.n.add(viewHolder);
        animate.alpha(1.0f).setDuration(defaultItemAnimator.b).setListener(new VpaListenerAdapter() {
            public void onAnimationStart(View view) {
            }

            public void onAnimationCancel(View view) {
                ViewCompat.setAlpha(view, 1.0f);
            }

            public void onAnimationEnd(View view) {
                animate.setListener(null);
                DefaultItemAnimator.this.f(viewHolder);
                DefaultItemAnimator.this.n.remove(viewHolder);
                DefaultItemAnimator.this.f();
            }
        }).start();
    }
}
