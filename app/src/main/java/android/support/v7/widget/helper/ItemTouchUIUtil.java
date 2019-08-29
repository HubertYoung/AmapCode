package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface ItemTouchUIUtil {
    void a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i);

    void a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z);

    void a(View view);

    void b(View view);
}
