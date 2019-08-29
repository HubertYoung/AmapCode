package android.support.v7.widget.helper;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class ItemTouchUIUtilImpl {

    static class Gingerbread implements ItemTouchUIUtil {
        Gingerbread() {
        }

        private static void a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2) {
            canvas.save();
            canvas.translate(f, f2);
            recyclerView.drawChild(canvas, view, 0);
            canvas.restore();
        }

        public final void a(View view) {
            view.setVisibility(0);
        }

        public final void b(View view) {
            view.setVisibility(4);
        }

        public final void a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (i != 2) {
                a(canvas, recyclerView, view, f, f2);
            }
        }

        public final void a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i) {
            if (i == 2) {
                a(canvas, recyclerView, view, f, f2);
            }
        }
    }

    static class Honeycomb implements ItemTouchUIUtil {
        public final void a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i) {
        }

        public final void b(View view) {
        }

        Honeycomb() {
        }

        public void a(View view) {
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
        }

        public void a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            ViewCompat.setTranslationX(view, f);
            ViewCompat.setTranslationY(view, f2);
        }
    }

    static class Lollipop extends Honeycomb {
        Lollipop() {
        }

        public final void a(Canvas canvas, RecyclerView recyclerView, View view, float f, float f2, int i, boolean z) {
            if (z && view.getTag(R.id.item_touch_helper_previous_elevation) == null) {
                Float valueOf = Float.valueOf(ViewCompat.getElevation(view));
                int childCount = recyclerView.getChildCount();
                float f3 = 0.0f;
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = recyclerView.getChildAt(i2);
                    if (childAt != view) {
                        float elevation = ViewCompat.getElevation(childAt);
                        if (elevation > f3) {
                            f3 = elevation;
                        }
                    }
                }
                ViewCompat.setElevation(view, 1.0f + f3);
                view.setTag(R.id.item_touch_helper_previous_elevation, valueOf);
            }
            super.a(canvas, recyclerView, view, f, f2, i, z);
        }

        public final void a(View view) {
            Object tag = view.getTag(R.id.item_touch_helper_previous_elevation);
            if (tag != null && (tag instanceof Float)) {
                ViewCompat.setElevation(view, ((Float) tag).floatValue());
            }
            view.setTag(R.id.item_touch_helper_previous_elevation, null);
            super.a(view);
        }
    }

    ItemTouchUIUtilImpl() {
    }
}
