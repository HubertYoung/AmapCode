package android.support.v4.widget;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

public class SearchViewCompat {
    /* access modifiers changed from: private */
    public static final SearchViewCompatImpl IMPL;

    public static abstract class OnCloseListenerCompat {
        final Object mListener = SearchViewCompat.IMPL.a(this);

        public boolean onClose() {
            return false;
        }
    }

    public static abstract class OnQueryTextListenerCompat {
        final Object mListener = SearchViewCompat.IMPL.a(this);

        public boolean onQueryTextChange(String str) {
            return false;
        }

        public boolean onQueryTextSubmit(String str) {
            return false;
        }
    }

    static class SearchViewCompatHoneycombImpl extends SearchViewCompatStubImpl {
        SearchViewCompatHoneycombImpl() {
        }

        public View a(Context context) {
            return SearchViewCompatHoneycomb.a(context);
        }

        public final void a(View view, ComponentName componentName) {
            SearchViewCompatHoneycomb.a(view, componentName);
        }

        public final Object a(final OnQueryTextListenerCompat onQueryTextListenerCompat) {
            return SearchViewCompatHoneycomb.a((OnQueryTextListenerCompatBridge) new OnQueryTextListenerCompatBridge() {
                public final boolean a(String str) {
                    return onQueryTextListenerCompat.onQueryTextSubmit(str);
                }

                public final boolean b(String str) {
                    return onQueryTextListenerCompat.onQueryTextChange(str);
                }
            });
        }

        public final void a(Object obj, Object obj2) {
            SearchViewCompatHoneycomb.a(obj, obj2);
        }

        public final Object a(final OnCloseListenerCompat onCloseListenerCompat) {
            return SearchViewCompatHoneycomb.a((OnCloseListenerCompatBridge) new OnCloseListenerCompatBridge() {
                public final boolean a() {
                    return onCloseListenerCompat.onClose();
                }
            });
        }

        public final void b(Object obj, Object obj2) {
            SearchViewCompatHoneycomb.b(obj, obj2);
        }

        public final CharSequence a(View view) {
            return SearchViewCompatHoneycomb.a(view);
        }

        public final void a(View view, CharSequence charSequence, boolean z) {
            SearchViewCompatHoneycomb.a(view, charSequence, z);
        }

        public final void a(View view, CharSequence charSequence) {
            SearchViewCompatHoneycomb.a(view, charSequence);
        }

        public final void a(View view, boolean z) {
            SearchViewCompatHoneycomb.a(view, z);
        }

        public final boolean b(View view) {
            return SearchViewCompatHoneycomb.b(view);
        }

        public final void b(View view, boolean z) {
            SearchViewCompatHoneycomb.b(view, z);
        }

        public final boolean c(View view) {
            return SearchViewCompatHoneycomb.c(view);
        }

        public final void c(View view, boolean z) {
            SearchViewCompatHoneycomb.c(view, z);
        }

        public final boolean d(View view) {
            return SearchViewCompatHoneycomb.d(view);
        }

        public final void a(View view, int i) {
            SearchViewCompatHoneycomb.a(view, i);
        }
    }

    static class SearchViewCompatIcsImpl extends SearchViewCompatHoneycombImpl {
        SearchViewCompatIcsImpl() {
        }

        public final View a(Context context) {
            return SearchViewCompatIcs.a(context);
        }

        public final void b(View view, int i) {
            SearchViewCompatIcs.a(view, i);
        }

        public final void c(View view, int i) {
            SearchViewCompatIcs.b(view, i);
        }
    }

    interface SearchViewCompatImpl {
        View a(Context context);

        CharSequence a(View view);

        Object a(OnCloseListenerCompat onCloseListenerCompat);

        Object a(OnQueryTextListenerCompat onQueryTextListenerCompat);

        void a(View view, int i);

        void a(View view, ComponentName componentName);

        void a(View view, CharSequence charSequence);

        void a(View view, CharSequence charSequence, boolean z);

        void a(View view, boolean z);

        void a(Object obj, Object obj2);

        void b(View view, int i);

        void b(View view, boolean z);

        void b(Object obj, Object obj2);

        boolean b(View view);

        void c(View view, int i);

        void c(View view, boolean z);

        boolean c(View view);

        boolean d(View view);
    }

    static class SearchViewCompatStubImpl implements SearchViewCompatImpl {
        public View a(Context context) {
            return null;
        }

        public CharSequence a(View view) {
            return null;
        }

        public Object a(OnCloseListenerCompat onCloseListenerCompat) {
            return null;
        }

        public Object a(OnQueryTextListenerCompat onQueryTextListenerCompat) {
            return null;
        }

        public void a(View view, int i) {
        }

        public void a(View view, ComponentName componentName) {
        }

        public void a(View view, CharSequence charSequence) {
        }

        public void a(View view, CharSequence charSequence, boolean z) {
        }

        public void a(View view, boolean z) {
        }

        public void a(Object obj, Object obj2) {
        }

        public void b(View view, int i) {
        }

        public void b(View view, boolean z) {
        }

        public void b(Object obj, Object obj2) {
        }

        public boolean b(View view) {
            return true;
        }

        public void c(View view, int i) {
        }

        public void c(View view, boolean z) {
        }

        public boolean c(View view) {
            return false;
        }

        public boolean d(View view) {
            return false;
        }

        SearchViewCompatStubImpl() {
        }
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new SearchViewCompatIcsImpl();
        } else if (VERSION.SDK_INT >= 11) {
            IMPL = new SearchViewCompatHoneycombImpl();
        } else {
            IMPL = new SearchViewCompatStubImpl();
        }
    }

    private SearchViewCompat(Context context) {
    }

    public static View newSearchView(Context context) {
        return IMPL.a(context);
    }

    public static void setSearchableInfo(View view, ComponentName componentName) {
        IMPL.a(view, componentName);
    }

    public static void setImeOptions(View view, int i) {
        IMPL.b(view, i);
    }

    public static void setInputType(View view, int i) {
        IMPL.c(view, i);
    }

    public static void setOnQueryTextListener(View view, OnQueryTextListenerCompat onQueryTextListenerCompat) {
        IMPL.a((Object) view, onQueryTextListenerCompat.mListener);
    }

    public static void setOnCloseListener(View view, OnCloseListenerCompat onCloseListenerCompat) {
        IMPL.b((Object) view, onCloseListenerCompat.mListener);
    }

    public static CharSequence getQuery(View view) {
        return IMPL.a(view);
    }

    public static void setQuery(View view, CharSequence charSequence, boolean z) {
        IMPL.a(view, charSequence, z);
    }

    public static void setQueryHint(View view, CharSequence charSequence) {
        IMPL.a(view, charSequence);
    }

    public static void setIconified(View view, boolean z) {
        IMPL.a(view, z);
    }

    public static boolean isIconified(View view) {
        return IMPL.b(view);
    }

    public static void setSubmitButtonEnabled(View view, boolean z) {
        IMPL.b(view, z);
    }

    public static boolean isSubmitButtonEnabled(View view) {
        return IMPL.c(view);
    }

    public static void setQueryRefinementEnabled(View view, boolean z) {
        IMPL.c(view, z);
    }

    public static boolean isQueryRefinementEnabled(View view) {
        return IMPL.d(view);
    }

    public static void setMaxWidth(View view, int i) {
        IMPL.a(view, i);
    }
}
