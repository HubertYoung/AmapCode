package com.amap.bundle.utils.ui;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public final class NoDBClickUtil {

    static class OnItemDBClickListener implements OnItemClickListener {
        private a mInterceptor = new a();
        private OnItemClickListener mTarget;

        public OnItemDBClickListener(OnItemClickListener onItemClickListener) {
            this.mTarget = onItemClickListener;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            if (!this.mInterceptor.a()) {
                this.mTarget.onItemClick(adapterView, view, i, j);
            }
        }
    }

    static class a {
        private long a;

        a() {
        }

        public final boolean a() {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < this.a) {
                this.a = currentTimeMillis;
                return true;
            } else if (currentTimeMillis - this.a < 500) {
                return true;
            } else {
                this.a = currentTimeMillis;
                return false;
            }
        }
    }

    static class b implements OnClickListener {
        private OnClickListener a;
        private a b = new a();

        public b(OnClickListener onClickListener) {
            this.a = onClickListener;
        }

        public final void onClick(final View view) {
            if (!this.b.a()) {
                view.setEnabled(false);
                view.postDelayed(new Runnable() {
                    public final void run() {
                        view.setEnabled(true);
                    }
                }, 500);
                this.a.onClick(view);
            }
        }
    }

    public static void a(View view, OnClickListener onClickListener) {
        if (onClickListener == null) {
            view.setOnClickListener(null);
        } else {
            view.setOnClickListener(new b(onClickListener));
        }
    }

    public static void a(AdapterView<?> adapterView, OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            adapterView.setOnItemClickListener(null);
        } else {
            adapterView.setOnItemClickListener(new OnItemDBClickListener(onItemClickListener));
        }
    }
}
