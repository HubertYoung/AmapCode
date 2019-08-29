package android.support.v4.app;

import android.app.Activity;
import android.content.Intent;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

class ShareCompatICS {
    ShareCompatICS() {
    }

    public static void a(MenuItem menuItem, Activity activity, Intent intent) {
        ShareActionProvider shareActionProvider;
        ActionProvider actionProvider = menuItem.getActionProvider();
        if (!(actionProvider instanceof ShareActionProvider)) {
            shareActionProvider = new ShareActionProvider(activity);
        } else {
            shareActionProvider = (ShareActionProvider) actionProvider;
        }
        StringBuilder sb = new StringBuilder(".sharecompat_");
        sb.append(activity.getClass().getName());
        shareActionProvider.setShareHistoryFileName(sb.toString());
        shareActionProvider.setShareIntent(intent);
        menuItem.setActionProvider(shareActionProvider);
    }
}
