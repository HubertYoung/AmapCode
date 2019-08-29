package com.alipay.mobile.monitor.track;

import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.reflect.Field;

public class TrackReflector {
    private static TrackReflector f = null;
    Class<?> a = null;
    Field b = null;
    Field c = null;
    Class<?> d = null;
    Field e = null;

    private TrackReflector() {
        try {
            this.a = Class.forName("android.view.View");
        } catch (ClassCastException e2) {
            Log.e("Cast", "Class of view cast exception.");
        } catch (ClassNotFoundException e3) {
            Log.e("Reflection", "Class Not Found.");
        }
        if (VERSION.SDK_INT >= 14) {
            if (this.a != null) {
                try {
                    this.c = this.a.getDeclaredField("mListenerInfo");
                    this.c.setAccessible(true);
                } catch (NoSuchFieldException e4) {
                    Log.e("Reflection", "No Such Field.");
                }
            }
            if (this.a != null) {
                try {
                    this.d = Class.forName("android.view.View$ListenerInfo");
                    this.e = this.d.getDeclaredField("mOnClickListener");
                    this.e.setAccessible(true);
                } catch (NoSuchFieldException e5) {
                    Log.e("Reflection", "No Such Field.");
                } catch (ClassNotFoundException e6) {
                    Log.e("Reflection", "Class Not Found.");
                }
            }
        } else if (this.a != null) {
            try {
                this.b = this.a.getDeclaredField("mOnClickListener");
                if (!this.b.isAccessible()) {
                    this.b.setAccessible(true);
                }
            } catch (NoSuchFieldException e7) {
                Log.e("Reflection", "No Such Field.");
            }
        }
    }

    public static TrackReflector a() {
        if (f == null) {
            f = new TrackReflector();
        }
        return f;
    }

    public OnClickListener a(View view) {
        if (VERSION.SDK_INT >= 14) {
            return c(view);
        }
        return b(view);
    }

    public void a(View view, OnClickListener onClickListener) {
        if (VERSION.SDK_INT >= 14) {
            c(view, onClickListener);
        } else {
            b(view, onClickListener);
        }
    }

    private OnClickListener b(View view) {
        if (this.b == null) {
            return null;
        }
        boolean z = false;
        try {
            return (OnClickListener) this.b.get(view);
        } catch (IllegalAccessException e2) {
            Log.e("Reflection", "Illegal Access.");
            return z;
        }
    }

    private void b(View view, OnClickListener onClickListener) {
        if (this.b != null && view != null) {
            try {
                this.b.setAccessible(true);
                this.b.set(view, onClickListener);
            } catch (IllegalAccessException e2) {
                Log.e("Reflection", "Illegal Access.");
            }
        }
    }

    private OnClickListener c(View view) {
        if (this.c == null || this.e == null) {
            return null;
        }
        try {
            Object listenerInfo = this.c.get(view);
            if (listenerInfo != null) {
                return (OnClickListener) this.e.get(listenerInfo);
            }
            return null;
        } catch (IllegalAccessException e2) {
            Log.e("Reflection", "Illegal Access.");
            return null;
        }
    }

    private void c(View view, OnClickListener onClickListener) {
        if (this.c != null && this.e != null && view != null) {
            try {
                Object listenerInfo = this.c.get(view);
                if (listenerInfo != null) {
                    this.e.setAccessible(true);
                    this.e.set(listenerInfo, onClickListener);
                }
            } catch (IllegalAccessException e2) {
                Log.e("Reflection", "Illegal Access.");
            }
        }
    }
}
