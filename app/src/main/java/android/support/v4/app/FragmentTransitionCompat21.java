package android.support.v4.app;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.Transition.EpicenterCallback;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class FragmentTransitionCompat21 {

    public static class EpicenterView {
        public View epicenter;
    }

    public interface ViewRetriever {
        View getView();
    }

    FragmentTransitionCompat21() {
    }

    public static String a(View view) {
        return view.getTransitionName();
    }

    public static Object a(Object obj) {
        return obj != null ? ((Transition) obj).clone() : obj;
    }

    public static Object a(Object obj, View view, ArrayList<View> arrayList, Map<String, View> map, View view2) {
        if (obj == null) {
            return obj;
        }
        b(arrayList, view);
        if (map != null) {
            arrayList.removeAll(map.values());
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        arrayList.add(view2);
        b((Object) (Transition) obj, arrayList);
        return obj;
    }

    public static void a(Object obj, View view, boolean z) {
        ((Transition) obj).excludeTarget(view, z);
    }

    public static void a(ViewGroup viewGroup, Object obj) {
        TransitionManager.beginDelayedTransition(viewGroup, (Transition) obj);
    }

    public static void a(Object obj, View view) {
        final Rect c = c(view);
        ((Transition) obj).setEpicenterCallback(new EpicenterCallback() {
            public final Rect onGetEpicenter(Transition transition) {
                return c;
            }
        });
    }

    public static Object b(Object obj) {
        if (obj == null) {
            return null;
        }
        Transition transition = (Transition) obj;
        if (transition == null) {
            return null;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(transition);
        return transitionSet;
    }

    public static void a(Object obj, Object obj2, View view, ViewRetriever viewRetriever, View view2, EpicenterView epicenterView, Map<String, String> map, ArrayList<View> arrayList, Map<String, View> map2, Map<String, View> map3, ArrayList<View> arrayList2) {
        Object obj3 = obj2;
        final View view3 = view2;
        if (obj != null || obj3 != null) {
            Transition transition = (Transition) obj;
            if (transition != null) {
                transition.addTarget(view3);
            }
            if (obj3 != null) {
                a(obj3, view3, map2, arrayList2);
            }
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            final View view4 = view;
            final Transition transition2 = transition;
            final ViewRetriever viewRetriever2 = viewRetriever;
            final Map<String, String> map4 = map;
            final Map<String, View> map5 = map3;
            final ArrayList<View> arrayList3 = arrayList;
            AnonymousClass2 r0 = new OnPreDrawListener() {
                public final boolean onPreDraw() {
                    view4.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (transition2 != null) {
                        transition2.removeTarget(view3);
                    }
                    View view = viewRetriever2.getView();
                    if (view != null) {
                        if (!map4.isEmpty()) {
                            FragmentTransitionCompat21.a(map5, view);
                            map5.keySet().retainAll(map4.values());
                            for (Entry entry : map4.entrySet()) {
                                View view2 = (View) map5.get((String) entry.getValue());
                                if (view2 != null) {
                                    view2.setTransitionName((String) entry.getKey());
                                }
                            }
                        }
                        if (transition2 != null) {
                            FragmentTransitionCompat21.b(arrayList3, view);
                            arrayList3.removeAll(map5.values());
                            arrayList3.add(view3);
                            FragmentTransitionCompat21.b((Object) transition2, arrayList3);
                        }
                    }
                    return true;
                }
            };
            viewTreeObserver.addOnPreDrawListener(r0);
            if (transition != null) {
                final EpicenterView epicenterView2 = epicenterView;
                transition.setEpicenterCallback(new EpicenterCallback() {
                    private Rect b;

                    public final Rect onGetEpicenter(Transition transition) {
                        if (this.b == null && epicenterView2.epicenter != null) {
                            this.b = FragmentTransitionCompat21.c(epicenterView2.epicenter);
                        }
                        return this.b;
                    }
                });
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0040, code lost:
        if (r1 != null) goto L_0x003a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object a(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3, boolean r4) {
        /*
            android.transition.Transition r1 = (android.transition.Transition) r1
            android.transition.Transition r2 = (android.transition.Transition) r2
            android.transition.Transition r3 = (android.transition.Transition) r3
            r0 = 1
            if (r1 == 0) goto L_0x000c
            if (r2 == 0) goto L_0x000c
            goto L_0x000d
        L_0x000c:
            r4 = 1
        L_0x000d:
            if (r4 == 0) goto L_0x0024
            android.transition.TransitionSet r4 = new android.transition.TransitionSet
            r4.<init>()
            if (r1 == 0) goto L_0x0019
            r4.addTransition(r1)
        L_0x0019:
            if (r2 == 0) goto L_0x001e
            r4.addTransition(r2)
        L_0x001e:
            if (r3 == 0) goto L_0x0053
            r4.addTransition(r3)
            goto L_0x0053
        L_0x0024:
            r4 = 0
            if (r2 == 0) goto L_0x003c
            if (r1 == 0) goto L_0x003c
            android.transition.TransitionSet r4 = new android.transition.TransitionSet
            r4.<init>()
            android.transition.TransitionSet r2 = r4.addTransition(r2)
            android.transition.TransitionSet r1 = r2.addTransition(r1)
            android.transition.TransitionSet r1 = r1.setOrdering(r0)
        L_0x003a:
            r4 = r1
            goto L_0x0043
        L_0x003c:
            if (r2 == 0) goto L_0x0040
            r4 = r2
            goto L_0x0043
        L_0x0040:
            if (r1 == 0) goto L_0x0043
            goto L_0x003a
        L_0x0043:
            if (r3 == 0) goto L_0x0053
            android.transition.TransitionSet r1 = new android.transition.TransitionSet
            r1.<init>()
            if (r4 == 0) goto L_0x004f
            r1.addTransition(r4)
        L_0x004f:
            r1.addTransition(r3)
            r4 = r1
        L_0x0053:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentTransitionCompat21.a(java.lang.Object, java.lang.Object, java.lang.Object, boolean):java.lang.Object");
    }

    public static void a(Object obj, View view, Map<String, View> map, ArrayList<View> arrayList) {
        TransitionSet transitionSet = (TransitionSet) obj;
        arrayList.clear();
        arrayList.addAll(map.values());
        List targets = transitionSet.getTargets();
        targets.clear();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            a(targets, arrayList.get(i));
        }
        arrayList.add(view);
        b((Object) transitionSet, arrayList);
    }

    private static void a(List<View> list, View view) {
        int size = list.size();
        if (!a(list, view, size)) {
            list.add(view);
            for (int i = size; i < list.size(); i++) {
                View view2 = list.get(i);
                if (view2 instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view2;
                    int childCount = viewGroup.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = viewGroup.getChildAt(i2);
                        if (!a(list, childAt, size)) {
                            list.add(childAt);
                        }
                    }
                }
            }
        }
    }

    private static boolean a(List<View> list, View view, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (list.get(i2) == view) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static Rect c(View view) {
        Rect rect = new Rect();
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        rect.set(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight());
        return rect;
    }

    /* access modifiers changed from: private */
    public static void b(ArrayList<View> arrayList, View view) {
        if (view.getVisibility() == 0) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                if (viewGroup.isTransitionGroup()) {
                    arrayList.add(viewGroup);
                    return;
                }
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    b(arrayList, viewGroup.getChildAt(i));
                }
                return;
            }
            arrayList.add(view);
        }
    }

    public static void a(Map<String, View> map, View view) {
        if (view.getVisibility() == 0) {
            String transitionName = view.getTransitionName();
            if (transitionName != null) {
                map.put(transitionName, view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    a(map, viewGroup.getChildAt(i));
                }
            }
        }
    }

    public static void a(View view, View view2, Object obj, ArrayList<View> arrayList, Object obj2, ArrayList<View> arrayList2, Object obj3, ArrayList<View> arrayList3, Object obj4, ArrayList<View> arrayList4, Map<String, View> map) {
        final Transition transition = (Transition) obj;
        final Transition transition2 = (Transition) obj2;
        final Transition transition3 = (Transition) obj3;
        final Transition transition4 = (Transition) obj4;
        if (transition4 != null) {
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            final View view3 = view;
            final ArrayList<View> arrayList5 = arrayList;
            final ArrayList<View> arrayList6 = arrayList2;
            final ArrayList<View> arrayList7 = arrayList3;
            final Map<String, View> map2 = map;
            final ArrayList<View> arrayList8 = arrayList4;
            final View view4 = view2;
            AnonymousClass4 r0 = new OnPreDrawListener() {
                public final boolean onPreDraw() {
                    view3.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (transition != null) {
                        FragmentTransitionCompat21.a((Object) transition, arrayList5);
                    }
                    if (transition2 != null) {
                        FragmentTransitionCompat21.a((Object) transition2, arrayList6);
                    }
                    if (transition3 != null) {
                        FragmentTransitionCompat21.a((Object) transition3, arrayList7);
                    }
                    for (Entry entry : map2.entrySet()) {
                        ((View) entry.getValue()).setTransitionName((String) entry.getKey());
                    }
                    int size = arrayList8.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        transition4.excludeTarget((View) arrayList8.get(i2), false);
                    }
                    transition4.excludeTarget(view4, false);
                    return true;
                }
            };
            viewTreeObserver.addOnPreDrawListener(r0);
        }
    }

    public static void a(Object obj, ArrayList<View> arrayList) {
        Transition transition = (Transition) obj;
        if (transition instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition;
            int transitionCount = transitionSet.getTransitionCount();
            for (int i = 0; i < transitionCount; i++) {
                a((Object) transitionSet.getTransitionAt(i), arrayList);
            }
            return;
        }
        if (!a(transition)) {
            List<View> targets = transition.getTargets();
            if (targets != null && targets.size() == arrayList.size() && targets.containsAll(arrayList)) {
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    transition.removeTarget(arrayList.get(size));
                }
            }
        }
    }

    public static void b(Object obj, ArrayList<View> arrayList) {
        Transition transition = (Transition) obj;
        int i = 0;
        if (transition instanceof TransitionSet) {
            TransitionSet transitionSet = (TransitionSet) transition;
            int transitionCount = transitionSet.getTransitionCount();
            while (i < transitionCount) {
                b((Object) transitionSet.getTransitionAt(i), arrayList);
                i++;
            }
            return;
        }
        if (!a(transition) && a((List) transition.getTargets())) {
            int size = arrayList.size();
            while (i < size) {
                transition.addTarget(arrayList.get(i));
                i++;
            }
        }
    }

    private static boolean a(Transition transition) {
        return !a((List) transition.getTargetIds()) || !a((List) transition.getTargetNames()) || !a((List) transition.getTargetTypes());
    }

    private static boolean a(List list) {
        return list == null || list.isEmpty();
    }
}
