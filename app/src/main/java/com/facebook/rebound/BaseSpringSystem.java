package com.facebook.rebound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class BaseSpringSystem {
    private final Set<Spring> mActiveSprings = new CopyOnWriteArraySet();
    private boolean mIdle = true;
    private final CopyOnWriteArraySet<SpringSystemListener> mListeners = new CopyOnWriteArraySet<>();
    private final SpringLooper mSpringLooper;
    private final Map<String, Spring> mSpringRegistry = new HashMap();

    public BaseSpringSystem(SpringLooper springLooper) {
        if (springLooper == null) {
            throw new IllegalArgumentException("springLooper is required");
        }
        this.mSpringLooper = springLooper;
        this.mSpringLooper.setSpringSystem(this);
    }

    public boolean getIsIdle() {
        return this.mIdle;
    }

    public Spring createSpring() {
        Spring spring = new Spring(this);
        registerSpring(spring);
        return spring;
    }

    public Spring getSpringById(String str) {
        if (str != null) {
            return this.mSpringRegistry.get(str);
        }
        throw new IllegalArgumentException("id is required");
    }

    public List<Spring> getAllSprings() {
        List list;
        Collection<Spring> values = this.mSpringRegistry.values();
        if (values instanceof List) {
            list = (List) values;
        } else {
            list = new ArrayList(values);
        }
        return Collections.unmodifiableList(list);
    }

    /* access modifiers changed from: 0000 */
    public void registerSpring(Spring spring) {
        if (spring == null) {
            throw new IllegalArgumentException("spring is required");
        } else if (this.mSpringRegistry.containsKey(spring.getId())) {
            throw new IllegalArgumentException("spring is already registered");
        } else {
            this.mSpringRegistry.put(spring.getId(), spring);
        }
    }

    /* access modifiers changed from: 0000 */
    public void deregisterSpring(Spring spring) {
        if (spring == null) {
            throw new IllegalArgumentException("spring is required");
        }
        this.mActiveSprings.remove(spring);
        this.mSpringRegistry.remove(spring.getId());
    }

    /* access modifiers changed from: 0000 */
    public void advance(double d) {
        for (Spring next : this.mActiveSprings) {
            if (next.systemShouldAdvance()) {
                next.advance(d / 1000.0d);
            } else {
                this.mActiveSprings.remove(next);
            }
        }
    }

    public void loop(double d) {
        Iterator<SpringSystemListener> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onBeforeIntegrate(this);
        }
        advance(d);
        if (this.mActiveSprings.isEmpty()) {
            this.mIdle = true;
        }
        Iterator<SpringSystemListener> it2 = this.mListeners.iterator();
        while (it2.hasNext()) {
            it2.next().onAfterIntegrate(this);
        }
        if (this.mIdle) {
            this.mSpringLooper.stop();
        }
    }

    /* access modifiers changed from: 0000 */
    public void activateSpring(String str) {
        Spring spring = this.mSpringRegistry.get(str);
        if (spring == null) {
            StringBuilder sb = new StringBuilder("springId ");
            sb.append(str);
            sb.append(" does not reference a registered spring");
            throw new IllegalArgumentException(sb.toString());
        }
        this.mActiveSprings.add(spring);
        if (getIsIdle()) {
            this.mIdle = false;
            this.mSpringLooper.start();
        }
    }

    public void addListener(SpringSystemListener springSystemListener) {
        if (springSystemListener == null) {
            throw new IllegalArgumentException("newListener is required");
        }
        this.mListeners.add(springSystemListener);
    }

    public void removeListener(SpringSystemListener springSystemListener) {
        if (springSystemListener == null) {
            throw new IllegalArgumentException("listenerToRemove is required");
        }
        this.mListeners.remove(springSystemListener);
    }

    public void removeAllListeners() {
        this.mListeners.clear();
    }
}
