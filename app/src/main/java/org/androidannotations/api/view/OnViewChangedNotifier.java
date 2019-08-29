package org.androidannotations.api.view;

import java.util.LinkedHashSet;
import java.util.Set;

public class OnViewChangedNotifier {
    private static OnViewChangedNotifier a;
    private final Set<OnViewChangedListener> b = new LinkedHashSet();

    public static OnViewChangedNotifier replaceNotifier(OnViewChangedNotifier notifier) {
        OnViewChangedNotifier previousNotifier = a;
        a = notifier;
        return previousNotifier;
    }

    public static void registerOnViewChangedListener(OnViewChangedListener listener) {
        if (a != null) {
            a.b.add(listener);
        }
    }

    public void notifyViewChanged(HasViews hasViews) {
        for (OnViewChangedListener onViewChanged : this.b) {
            onViewChanged.onViewChanged(hasViews);
        }
    }
}
