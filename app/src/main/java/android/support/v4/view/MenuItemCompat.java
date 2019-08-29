package android.support.v4.view;

import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenuItem;
import android.view.MenuItem;
import android.view.View;

public class MenuItemCompat {
    static final MenuVersionImpl IMPL;
    public static final int SHOW_AS_ACTION_ALWAYS = 2;
    public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
    public static final int SHOW_AS_ACTION_IF_ROOM = 1;
    public static final int SHOW_AS_ACTION_NEVER = 0;
    public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
    private static final String TAG = "MenuItemCompat";

    static class BaseMenuVersionImpl implements MenuVersionImpl {
        public final MenuItem a(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
            return menuItem;
        }

        public final MenuItem a(MenuItem menuItem, View view) {
            return menuItem;
        }

        public final View a(MenuItem menuItem) {
            return null;
        }

        public final void a(MenuItem menuItem, int i) {
        }

        public final MenuItem b(MenuItem menuItem, int i) {
            return menuItem;
        }

        public final boolean b(MenuItem menuItem) {
            return false;
        }

        public final boolean c(MenuItem menuItem) {
            return false;
        }

        public final boolean d(MenuItem menuItem) {
            return false;
        }

        BaseMenuVersionImpl() {
        }
    }

    static class HoneycombMenuVersionImpl implements MenuVersionImpl {
        public MenuItem a(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
            return menuItem;
        }

        public boolean b(MenuItem menuItem) {
            return false;
        }

        public boolean c(MenuItem menuItem) {
            return false;
        }

        public boolean d(MenuItem menuItem) {
            return false;
        }

        HoneycombMenuVersionImpl() {
        }

        public final void a(MenuItem menuItem, int i) {
            MenuItemCompatHoneycomb.a(menuItem, i);
        }

        public final MenuItem a(MenuItem menuItem, View view) {
            return MenuItemCompatHoneycomb.a(menuItem, view);
        }

        public final MenuItem b(MenuItem menuItem, int i) {
            return MenuItemCompatHoneycomb.b(menuItem, i);
        }

        public final View a(MenuItem menuItem) {
            return MenuItemCompatHoneycomb.a(menuItem);
        }
    }

    static class IcsMenuVersionImpl extends HoneycombMenuVersionImpl {
        IcsMenuVersionImpl() {
        }

        public final boolean b(MenuItem menuItem) {
            return MenuItemCompatIcs.a(menuItem);
        }

        public final boolean c(MenuItem menuItem) {
            return MenuItemCompatIcs.b(menuItem);
        }

        public final boolean d(MenuItem menuItem) {
            return MenuItemCompatIcs.c(menuItem);
        }

        public final MenuItem a(MenuItem menuItem, final OnActionExpandListener onActionExpandListener) {
            if (onActionExpandListener == null) {
                return MenuItemCompatIcs.a(menuItem, null);
            }
            return MenuItemCompatIcs.a(menuItem, new SupportActionExpandProxy() {
                public final boolean a(MenuItem menuItem) {
                    return onActionExpandListener.onMenuItemActionExpand(menuItem);
                }

                public final boolean b(MenuItem menuItem) {
                    return onActionExpandListener.onMenuItemActionCollapse(menuItem);
                }
            });
        }
    }

    interface MenuVersionImpl {
        MenuItem a(MenuItem menuItem, OnActionExpandListener onActionExpandListener);

        MenuItem a(MenuItem menuItem, View view);

        View a(MenuItem menuItem);

        void a(MenuItem menuItem, int i);

        MenuItem b(MenuItem menuItem, int i);

        boolean b(MenuItem menuItem);

        boolean c(MenuItem menuItem);

        boolean d(MenuItem menuItem);
    }

    public interface OnActionExpandListener {
        boolean onMenuItemActionCollapse(MenuItem menuItem);

        boolean onMenuItemActionExpand(MenuItem menuItem);
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 14) {
            IMPL = new IcsMenuVersionImpl();
        } else if (i >= 11) {
            IMPL = new HoneycombMenuVersionImpl();
        } else {
            IMPL = new BaseMenuVersionImpl();
        }
    }

    public static void setShowAsAction(MenuItem menuItem, int i) {
        if (menuItem instanceof SupportMenuItem) {
            ((SupportMenuItem) menuItem).setShowAsAction(i);
        } else {
            IMPL.a(menuItem, i);
        }
    }

    public static MenuItem setActionView(MenuItem menuItem, View view) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).setActionView(view);
        }
        return IMPL.a(menuItem, view);
    }

    public static MenuItem setActionView(MenuItem menuItem, int i) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).setActionView(i);
        }
        return IMPL.b(menuItem, i);
    }

    public static View getActionView(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getActionView();
        }
        return IMPL.a(menuItem);
    }

    public static MenuItem setActionProvider(MenuItem menuItem, ActionProvider actionProvider) {
        return menuItem instanceof SupportMenuItem ? ((SupportMenuItem) menuItem).setSupportActionProvider(actionProvider) : menuItem;
    }

    public static ActionProvider getActionProvider(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).getSupportActionProvider();
        }
        return null;
    }

    public static boolean expandActionView(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).expandActionView();
        }
        return IMPL.b(menuItem);
    }

    public static boolean collapseActionView(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).collapseActionView();
        }
        return IMPL.c(menuItem);
    }

    public static boolean isActionViewExpanded(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).isActionViewExpanded();
        }
        return IMPL.d(menuItem);
    }

    public static MenuItem setOnActionExpandListener(MenuItem menuItem, OnActionExpandListener onActionExpandListener) {
        if (menuItem instanceof SupportMenuItem) {
            return ((SupportMenuItem) menuItem).setSupportOnActionExpandListener(onActionExpandListener);
        }
        return IMPL.a(menuItem, onActionExpandListener);
    }
}
