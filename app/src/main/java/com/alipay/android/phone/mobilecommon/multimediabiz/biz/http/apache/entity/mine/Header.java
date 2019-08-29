package com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Header implements Iterable<MinimalField> {
    private final List<MinimalField> a = new LinkedList();
    private final Map<String, List<MinimalField>> b = new HashMap();

    public void addField(MinimalField field) {
        if (field != null) {
            String key = field.getName().toLowerCase(Locale.US);
            List values = this.b.get(key);
            if (values == null) {
                values = new LinkedList();
                this.b.put(key, values);
            }
            values.add(field);
            this.a.add(field);
        }
    }

    public List<MinimalField> getFields() {
        return new ArrayList(this.a);
    }

    public MinimalField getField(String name) {
        if (name == null) {
            return null;
        }
        List list = this.b.get(name.toLowerCase(Locale.US));
        if (list == null || list.isEmpty()) {
            return null;
        }
        return (MinimalField) list.get(0);
    }

    public List<MinimalField> getFields(String name) {
        if (name == null) {
            return null;
        }
        List list = this.b.get(name.toLowerCase(Locale.US));
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList(list);
    }

    public int removeFields(String name) {
        if (name == null) {
            return 0;
        }
        List removed = this.b.remove(name.toLowerCase(Locale.US));
        if (removed == null || removed.isEmpty()) {
            return 0;
        }
        this.a.removeAll(removed);
        return removed.size();
    }

    public void setField(MinimalField field) {
        if (field != null) {
            List list = this.b.get(field.getName().toLowerCase(Locale.US));
            if (list == null || list.isEmpty()) {
                addField(field);
                return;
            }
            list.clear();
            list.add(field);
            int firstOccurrence = -1;
            int index = 0;
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                if (it.next().getName().equalsIgnoreCase(field.getName())) {
                    it.remove();
                    if (firstOccurrence == -1) {
                        firstOccurrence = index;
                    }
                }
                index++;
            }
            this.a.add(firstOccurrence, field);
        }
    }

    public Iterator<MinimalField> iterator() {
        return Collections.unmodifiableList(this.a).iterator();
    }

    public String toString() {
        return this.a.toString();
    }
}
