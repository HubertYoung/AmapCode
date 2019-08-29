package android.support.v4.view;

import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;

class MenuItemCompatIcs {

    static class OnActionExpandListenerWrapper implements OnActionExpandListener {
        private SupportActionExpandProxy a;

        public OnActionExpandListenerWrapper(SupportActionExpandProxy supportActionExpandProxy) {
            this.a = supportActionExpandProxy;
        }

        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return this.a.a(menuItem);
        }

        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return this.a.b(menuItem);
        }
    }

    interface SupportActionExpandProxy {
        boolean a(MenuItem menuItem);

        boolean b(MenuItem menuItem);
    }

    MenuItemCompatIcs() {
    }

    public static boolean a(MenuItem menuItem) {
        return menuItem.expandActionView();
    }

    public static boolean b(MenuItem menuItem) {
        return menuItem.collapseActionView();
    }

    public static boolean c(MenuItem menuItem) {
        return menuItem.isActionViewExpanded();
    }

    public static MenuItem a(MenuItem menuItem, SupportActionExpandProxy supportActionExpandProxy) {
        return menuItem.setOnActionExpandListener(new OnActionExpandListenerWrapper(supportActionExpandProxy));
    }
}
