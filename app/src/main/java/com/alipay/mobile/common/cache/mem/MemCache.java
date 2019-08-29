package com.alipay.mobile.common.cache.mem;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
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

    public MemCache() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public synchronized void put(String owner, String group, String key, T value) {
        Entity entity = makeEntity(owner, group, value);
        a(entity);
        this.mMap.put(key, entity);
        recordPut(entity);
    }

    private void a(Entity<T> entity) {
        String group = entity.getGroup();
        if (group != null) {
            Set entitys = this.mGroup.get(group);
            if (entitys == null) {
                entitys = new HashSet();
                this.mGroup.put(group, entitys);
            }
            entitys.add(entity);
        }
    }

    public synchronized T get(String owner, String key) {
        T t = null;
        synchronized (this) {
            if (this.mMap.containsKey(key)) {
                Entity entity = this.mMap.get(key);
                if (!entity.authenticate(owner)) {
                    remove(key);
                } else {
                    t = entity.getValue();
                }
            }
        }
        return t;
    }

    public synchronized T remove(String key) {
        T value;
        if (!this.mMap.containsKey(key)) {
            value = null;
        } else {
            Entity entity = this.mMap.get(key);
            this.mMap.remove(key);
            b(entity);
            recordRemove(entity);
            value = entity.getValue();
        }
        return value;
    }

    private void b(Entity<T> entity) {
        String group = entity.getGroup();
        if (group != null && this.mGroup.containsKey(group)) {
            new CopyOnWriteArraySet(this.mGroup.get(group)).remove(entity);
        }
    }

    public synchronized void removeByOwner(String owner) {
        if (owner != null) {
            CopyOnWriteArraySet entitys = new CopyOnWriteArraySet();
            for (Entry<String, Entity<T>> value : this.mMap.entrySet()) {
                Entity entity = (Entity) value.getValue();
                if (owner.equals(entity.getOwner())) {
                    entitys.add(entity);
                }
            }
            Iterator it = entitys.iterator();
            while (it.hasNext()) {
                Entity entity2 = (Entity) it.next();
                if (this.mMap.values().remove(entity2)) {
                    recordRemove(entity2);
                }
            }
        }
    }

    public synchronized void removeByGroup(String group) {
        if (group != null) {
            if (this.mGroup.containsKey(group)) {
                Iterator it = new CopyOnWriteArraySet(this.mGroup.get(group)).iterator();
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
