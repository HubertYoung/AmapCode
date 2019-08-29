package com.amap.bundle.utils.ui;

import android.app.Application;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.autonavi.minimap.R;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class ToastHelper {
    private static final int NAVI_TOAST_DIP_TEXT_SIZE = 17;
    private static a toastImpl;

    public static class SafeToast extends Toast {
        private static final String TAG = "SafeToast";

        class a implements Callback {
            private final Handler b;

            public a(Handler handler) {
                this.b = handler;
            }

            public final boolean handleMessage(Message message) {
                try {
                    this.b.handleMessage(message);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                return true;
            }
        }

        class b implements Runnable {
            private final Runnable b;

            public b(Runnable runnable) {
                this.b = runnable;
            }

            public final void run() {
                try {
                    this.b.run();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        }

        public SafeToast(Context context) {
            super(context);
        }

        public void show() {
            if (isAndroidN()) {
                tryToHack();
            }
            super.show();
        }

        /* access modifiers changed from: 0000 */
        public boolean isAndroidN() {
            return VERSION.SDK_INT == 25;
        }

        private void tryToHack() {
            try {
                Object fieldValue = getFieldValue((Object) this, (String) "mTN");
                if (fieldValue != null) {
                    boolean z = false;
                    Object fieldValue2 = getFieldValue(fieldValue, (String) "mShow");
                    if (fieldValue2 != null && (fieldValue2 instanceof Runnable)) {
                        z = setFieldValue(fieldValue, "mShow", new b((Runnable) fieldValue2));
                    }
                    if (!z) {
                        Object fieldValue3 = getFieldValue(fieldValue, (String) "mHandler");
                        if (fieldValue3 != null && (fieldValue3 instanceof Handler)) {
                            setFieldValue(fieldValue3, "mCallback", new a((Handler) fieldValue3));
                        }
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

        private static boolean setFieldValue(Object obj, String str, Object obj2) {
            Field declaredField = getDeclaredField(obj, str);
            if (declaredField != null) {
                try {
                    if (Modifier.isFinal(declaredField.getModifiers())) {
                        Field declaredField2 = Field.class.getDeclaredField("accessFlags");
                        declaredField2.setAccessible(true);
                        declaredField2.setInt(declaredField, declaredField.getModifiers() & -17);
                    }
                    if (!declaredField.isAccessible()) {
                        declaredField.setAccessible(true);
                    }
                    declaredField.set(obj, obj2);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        private static Object getFieldValue(Object obj, String str) {
            return getFieldValue(obj, getDeclaredField(obj, str));
        }

        private static Object getFieldValue(Object obj, Field field) {
            if (field != null) {
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    return field.get(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        private static Field getDeclaredField(Object obj, String str) {
            Class cls = obj.getClass();
            while (cls != Object.class) {
                try {
                    return cls.getDeclaredField(str);
                } catch (NoSuchFieldException unused) {
                    cls = cls.getSuperclass();
                }
            }
            return null;
        }
    }

    static class a {
        private static Handler d = new Handler(Looper.getMainLooper());
        /* access modifiers changed from: private */
        public Application a;
        /* access modifiers changed from: private */
        public LayoutInflater b;
        /* access modifiers changed from: private */
        public SoftReference<Toast> c;

        /* renamed from: com.amap.bundle.utils.ui.ToastHelper$a$a reason: collision with other inner class name */
        static class C0007a {
            int a;
            int b;
            int c;

            public C0007a(int i, int i2, int i3) {
                this.a = i;
                this.b = i2;
                this.c = i3;
            }
        }

        a(Application application) {
            this.a = application;
            this.b = LayoutInflater.from(application);
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            if (!(this.c == null || this.c.get() == null)) {
                try {
                    this.c.get().cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a(CharSequence charSequence, int i) {
            a(charSequence, i, null, 0);
        }

        /* access modifiers changed from: 0000 */
        public final void a(CharSequence charSequence, int i, int i2, int i3, int i4, int i5) {
            a(charSequence, i, new C0007a(i2, i3, i4), i5);
        }

        private void a(CharSequence charSequence, int i, C0007a aVar, int i2) {
            Handler handler = d;
            final int i3 = i;
            final C0007a aVar2 = aVar;
            final CharSequence charSequence2 = charSequence;
            final int i4 = i2;
            AnonymousClass1 r1 = new Runnable() {
                public final void run() {
                    SafeToast safeToast = new SafeToast(a.this.a);
                    safeToast.setDuration(i3);
                    if (aVar2 != null) {
                        safeToast.setGravity(aVar2.a, aVar2.b, aVar2.c);
                    } else {
                        safeToast.setGravity(81, 0, a.this.a.getResources().getDimensionPixelSize(R.dimen.toast_gravity_yOffset));
                    }
                    View inflate = a.this.b.inflate(R.layout.common_toast, null);
                    TextView textView = (TextView) inflate.findViewById(R.id.text_toast);
                    safeToast.setView(inflate);
                    textView.setText(charSequence2);
                    if (i4 > 0) {
                        textView.setTextSize(1, (float) i4);
                    }
                    synchronized (a.this) {
                        a.this.a();
                        a.this.c = new SoftReference(safeToast);
                        safeToast.show();
                    }
                }
            };
            handler.post(r1);
        }
    }

    public static void init(Application application) {
        toastImpl = new a(application);
    }

    private static void assertInit() {
        if (toastImpl == null) {
            throw new IllegalStateException("ToastHelper need be init first..");
        }
    }

    public static void cancel() {
        assertInit();
        toastImpl.a();
    }

    public static void showToast(CharSequence charSequence) {
        assertInit();
        toastImpl.a(charSequence, 0);
    }

    public static void showToast(CharSequence charSequence, int i) {
        assertInit();
        toastImpl.a(charSequence, i);
    }

    public static void showToast(CharSequence charSequence, int i, int i2, int i3) {
        assertInit();
        toastImpl.a(charSequence, 0, i, i2, i3, 0);
    }

    public static void showLongToast(CharSequence charSequence) {
        assertInit();
        toastImpl.a(charSequence, 1);
    }

    public static void showLongToast(CharSequence charSequence, int i, int i2, int i3) {
        assertInit();
        toastImpl.a(charSequence, 1, i, i2, i3, 0);
    }

    @Deprecated
    public static void showLongToast(CharSequence charSequence, int i, int i2, int i3, int i4) {
        assertInit();
        toastImpl.a(charSequence, 1, i, i2, i3, i4);
    }
}
