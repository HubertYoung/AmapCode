package com.alipay.mobile.h5container.api;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class H5EventFilter {
    private Set<String> actions = new HashSet();

    public void addAction(String action) {
        this.actions.add(action);
    }

    public void setEventsList(List<String> eventList) {
        if (eventList != null && !eventList.isEmpty()) {
            for (String event : eventList) {
                this.actions.add(event);
            }
        }
    }

    public Iterator<String> actionIterator() {
        return this.actions.iterator();
    }
}
