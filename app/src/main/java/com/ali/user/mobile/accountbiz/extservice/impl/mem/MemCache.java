package com.ali.user.mobile.accountbiz.extservice.impl.mem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class MemCache<T> {
    protected HashMap<String, Set<Entity<T>>> mGroup;
    protected HashMap<String, Entity<T>> mMap;

    /* access modifiers changed from: protected */
    public abstract Entity<T> makeEntity(String str, String str2, T t);

    /* access modifiers changed from: protected */
    public abstract void recordPut(Entity<T> entity);

    /* access modifiers changed from: protected */
    public abstract void recordRemove(Entity<T> entity);

    public synchronized void put(String str, String str2, String str3, T t) {
        Entity makeEntity = makeEntity(str, str2, t);
        addGroup(makeEntity);
        this.mMap.put(str3, makeEntity);
        recordPut(makeEntity);
    }

    private void addGroup(Entity<T> entity) {
        String group = entity.getGroup();
        if (group != null) {
            Set set = this.mGroup.get(group);
            if (set == null) {
                set = new HashSet();
                this.mGroup.put(group, set);
            }
            set.add(entity);
        }
    }

    public synchronized T get(String str, String str2) {
        try {
            if (!this.mMap.containsKey(str2)) {
                return null;
            }
            Entity entity = this.mMap.get(str2);
            if (!entity.authenticate(str)) {
                remove(str2);
                return null;
            }
            return entity.getValue();
        }
    }

    public synchronized T remove(String str) {
        try {
            if (!this.mMap.containsKey(str)) {
                return null;
            }
            Entity entity = this.mMap.get(str);
            this.mMap.remove(str);
            removeGroup(entity);
            recordRemove(entity);
            return entity.getValue();
        }
    }

    private void removeGroup(Entity<T> entity) {
        String group = entity.getGroup();
        if (group != null && this.mGroup.containsKey(group)) {
            new CopyOnWriteArraySet(this.mGroup.get(group)).remove(entity);
        }
    }

    public synchronized void removeByGroup(String str) {
        if (str != null) {
            if (this.mGroup.containsKey(str)) {
                Iterator it = new CopyOnWriteArraySet(this.mGroup.get(str)).iterator();
                while (it.hasNext()) {
                    Entity entity = (Entity) it.next();
                    if (this.mMap.values().remove(entity)) {
                        recordRemove(entity);
                    }
                }
            }
        }
    }

    public int getCacheCount() {
        return this.mMap.size();
    }

    public String toString() {
        return this.mMap.toString();
    }
}
