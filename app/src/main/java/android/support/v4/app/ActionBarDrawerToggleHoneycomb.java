package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

class ActionBarDrawerToggleHoneycomb {
    private static final int[] a = {16843531};

    static class SetIndicatorInfo {
        public Method a;
        public Method b;
        public ImageView c;

        SetIndicatorInfo(Activity activity) {
            try {
                this.a = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", new Class[]{Drawable.class});
                this.b = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", new Class[]{Integer.TYPE});
            } catch (NoSuchMethodException unused) {
                View findViewById = activity.findViewById(16908332);
                if (findViewById != null) {
                    ViewGroup viewGroup = (ViewGroup) findViewById.getParent();
                    if (viewGroup.getChildCount() == 2) {
                        View childAt = viewGroup.getChildAt(0);
                        View childAt2 = viewGroup.getChildAt(1);
                        childAt2 = childAt.getId() != 16908332 ? childAt : childAt2;
                        if (childAt2 instanceof ImageView) {
                            this.c = (ImageView) childAt2;
                        }
                    }
                }
            }
        }
    }

    ActionBarDrawerToggleHoneycomb() {
    }

    public static Object a(Object obj, Activity activity, Drawable drawable, int i) {
        if (obj == null) {
            obj = new SetIndicatorInfo(activity);
        }
        SetIndicatorInfo setIndicatorInfo = (SetIndicatorInfo) obj;
        if (setIndicatorInfo.a != null) {
            try {
                ActionBar actionBar = activity.getActionBar();
                setIndicatorInfo.a.invoke(actionBar, new Object[]{drawable});
                setIndicatorInfo.b.invoke(actionBar, new Object[]{Integer.valueOf(i)});
            } catch (Exception unused) {
            }
        } else if (setIndicatorInfo.c != null) {
            setIndicatorInfo.c.setImageDrawable(drawable);
        }
        return obj;
    }

    public static Object a(Object obj, Activity activity, int i) {
        if (obj == null) {
            obj = new SetIndicatorInfo(activity);
        }
        SetIndicatorInfo setIndicatorInfo = (SetIndicatorInfo) obj;
        if (setIndicatorInfo.a != null) {
            try {
                ActionBar actionBar = activity.getActionBar();
                setIndicatorInfo.b.invoke(actionBar, new Object[]{Integer.valueOf(i)});
                if (VERSION.SDK_INT <= 19) {
                    actionBar.setSubtitle(actionBar.getSubtitle());
                }
            } catch (Exception unused) {
            }
        }
        return obj;
    }

    public static Drawable a(Activity activity) {
        TypedArray obtainStyledAttributes = activity.obtainStyledAttributes(a);
        Drawable drawable = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        return drawable;
    }
}
