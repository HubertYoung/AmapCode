package defpackage;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

/* renamed from: cwt reason: default package */
/* compiled from: BatInterface */
public abstract class cwt {

    /* renamed from: cwt$a */
    /* compiled from: BatInterface */
    public interface a {
        void a(View view);
    }

    /* renamed from: cwt$b */
    /* compiled from: BatInterface */
    public interface b {
        boolean batOnChildClick(ExpandableListView expandableListView, View view, int i, int i2, long j);
    }

    /* renamed from: cwt$c */
    /* compiled from: BatInterface */
    public interface c {
        boolean batOnGroupClick(ExpandableListView expandableListView, View view, int i, long j);
    }

    /* renamed from: cwt$d */
    /* compiled from: BatInterface */
    public interface d {
        void a(View view, View view2);

        void b(View view, View view2);
    }

    /* renamed from: cwt$e */
    /* compiled from: BatInterface */
    public interface e {
        void batOnItemClick(AdapterView<?> adapterView, View view, int i, long j);
    }

    /* renamed from: cwt$f */
    /* compiled from: BatInterface */
    public interface f {
        boolean a(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    /* renamed from: cwt$g */
    /* compiled from: BatInterface */
    public interface g {
        void a();
    }

    /* renamed from: cwt$h */
    /* compiled from: BatInterface */
    public interface h {
        boolean a(View view, MotionEvent motionEvent);
    }

    /* renamed from: cwt$i */
    /* compiled from: BatInterface */
    public interface i {
        void a(View view, int i);
    }
}
