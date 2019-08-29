package android.support.v4.view;

import android.view.MenuItem;
import android.view.View;

class MenuItemCompatHoneycomb {
    MenuItemCompatHoneycomb() {
    }

    public static void a(MenuItem menuItem, int i) {
        menuItem.setShowAsAction(i);
    }

    public static MenuItem a(MenuItem menuItem, View view) {
        return menuItem.setActionView(view);
    }

    public static MenuItem b(MenuItem menuItem, int i) {
        return menuItem.setActionView(i);
    }

    public static View a(MenuItem menuItem) {
        return menuItem.getActionView();
    }
}
