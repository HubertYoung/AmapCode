package android.support.v4.widget;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import com.alipay.mobile.nebula.search.H5SearchType;

class SearchViewCompatHoneycomb {

    interface OnCloseListenerCompatBridge {
        boolean a();
    }

    interface OnQueryTextListenerCompatBridge {
        boolean a(String str);

        boolean b(String str);
    }

    SearchViewCompatHoneycomb() {
    }

    public static View a(Context context) {
        return new SearchView(context);
    }

    public static void a(View view, ComponentName componentName) {
        SearchView searchView = (SearchView) view;
        searchView.setSearchableInfo(((SearchManager) searchView.getContext().getSystemService(H5SearchType.SEARCH)).getSearchableInfo(componentName));
    }

    public static Object a(final OnQueryTextListenerCompatBridge onQueryTextListenerCompatBridge) {
        return new OnQueryTextListener() {
            public final boolean onQueryTextSubmit(String str) {
                return onQueryTextListenerCompatBridge.a(str);
            }

            public final boolean onQueryTextChange(String str) {
                return onQueryTextListenerCompatBridge.b(str);
            }
        };
    }

    public static void a(Object obj, Object obj2) {
        ((SearchView) obj).setOnQueryTextListener((OnQueryTextListener) obj2);
    }

    public static Object a(final OnCloseListenerCompatBridge onCloseListenerCompatBridge) {
        return new OnCloseListener() {
            public final boolean onClose() {
                return onCloseListenerCompatBridge.a();
            }
        };
    }

    public static void b(Object obj, Object obj2) {
        ((SearchView) obj).setOnCloseListener((OnCloseListener) obj2);
    }

    public static CharSequence a(View view) {
        return ((SearchView) view).getQuery();
    }

    public static void a(View view, CharSequence charSequence, boolean z) {
        ((SearchView) view).setQuery(charSequence, z);
    }

    public static void a(View view, CharSequence charSequence) {
        ((SearchView) view).setQueryHint(charSequence);
    }

    public static void a(View view, boolean z) {
        ((SearchView) view).setIconified(z);
    }

    public static boolean b(View view) {
        return ((SearchView) view).isIconified();
    }

    public static void b(View view, boolean z) {
        ((SearchView) view).setSubmitButtonEnabled(z);
    }

    public static boolean c(View view) {
        return ((SearchView) view).isSubmitButtonEnabled();
    }

    public static void c(View view, boolean z) {
        ((SearchView) view).setQueryRefinementEnabled(z);
    }

    public static boolean d(View view) {
        return ((SearchView) view).isQueryRefinementEnabled();
    }

    public static void a(View view, int i) {
        ((SearchView) view).setMaxWidth(i);
    }
}
