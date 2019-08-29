package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.Window.Callback;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

/* renamed from: brd reason: default package */
/* compiled from: WindowCallbackObserver */
public final class brd implements Callback {
    private a a;
    private boolean b;
    private boolean c;

    /* renamed from: brd$a */
    /* compiled from: WindowCallbackObserver */
    public static class a {
        private Activity a;

        public a(Activity activity) {
            this.a = activity;
        }

        /* access modifiers changed from: 0000 */
        public final void a(String str, Bundle bundle) {
            String str2;
            Window window = this.a.getWindow();
            if (window == null) {
                str2 = null;
            } else {
                str2 = window.toString();
            }
            if (str2 != null) {
                this.a.sendBroadcast(new Intent(str).setPackage(this.a.getPackageName()).putExtra("windowToken", str2).putExtras(bundle));
            }
        }
    }

    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public final boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return false;
    }

    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        return false;
    }

    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public final boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    public final void onActionModeFinished(ActionMode actionMode) {
    }

    public final void onActionModeStarted(ActionMode actionMode) {
    }

    public final void onAttachedToWindow() {
    }

    public final void onContentChanged() {
    }

    public final boolean onCreatePanelMenu(int i, Menu menu) {
        return false;
    }

    @Nullable
    public final View onCreatePanelView(int i) {
        return null;
    }

    public final void onDetachedFromWindow() {
    }

    public final boolean onMenuItemSelected(int i, MenuItem menuItem) {
        return false;
    }

    public final boolean onMenuOpened(int i, Menu menu) {
        return false;
    }

    public final void onPanelClosed(int i, Menu menu) {
    }

    public final void onPointerCaptureChanged(boolean z) {
    }

    public final boolean onPreparePanel(int i, View view, Menu menu) {
        return false;
    }

    public final void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, @Nullable Menu menu, int i) {
    }

    public final boolean onSearchRequested() {
        return false;
    }

    public final boolean onSearchRequested(SearchEvent searchEvent) {
        return false;
    }

    public final void onWindowFocusChanged(boolean z) {
    }

    @Nullable
    public final ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Nullable
    public final ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
        return null;
    }

    public brd(Activity activity) {
        this.a = new a(activity);
    }

    public final void onWindowAttributesChanged(LayoutParams layoutParams) {
        boolean z = false;
        boolean z2 = (layoutParams.flags & 1024) == 1024 && (layoutParams.flags & 2048) != 2048;
        if (z2 != this.b) {
            a aVar = this.a;
            Bundle bundle = new Bundle();
            bundle.putBoolean("isFullScreen", z2);
            aVar.a("com.autonavi.minimap.FULLSCREEN_CHANGED", bundle);
            this.b = z2;
        }
        if ((layoutParams.flags & 128) == 128) {
            z = true;
        }
        if (z != this.c) {
            a aVar2 = this.a;
            Bundle bundle2 = new Bundle();
            bundle2.putBoolean("isKeepScreenOn", z);
            aVar2.a("com.autonavi.minimap.KEEPSCREENON_CHANGED", bundle2);
            this.c = z;
        }
    }
}
