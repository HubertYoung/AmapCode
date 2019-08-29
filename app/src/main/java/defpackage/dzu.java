package defpackage;

import android.content.Context;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.coach.widget.FilterDataItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* renamed from: dzu reason: default package */
/* compiled from: FilterResult */
public final class dzu {
    public Context a;
    public boolean b = true;
    public HashMap<String, Boolean> c = new HashMap<>();
    public HashMap<String, Boolean> d = new HashMap<>();
    private List<FilterDataItem> e;
    private List<FilterDataItem> f;
    private List<FilterDataItem> g;
    private HashMap<String, Boolean> h = new HashMap<>();

    public dzu(Context context) {
        this.a = context;
    }

    public final synchronized void a() {
        this.b = true;
        if (this.e != null) {
            this.e.clear();
        }
        if (this.f != null) {
            this.f.clear();
        }
        if (this.g != null) {
            this.g.clear();
        }
        this.c.clear();
        this.d.clear();
        this.h.clear();
    }

    public final synchronized void a(List<String> list, List<String> list2) {
        ArrayList arrayList = new ArrayList();
        FilterDataItem filterDataItem = new FilterDataItem(0, this.a.getString(R.string.filter_by_none));
        int i = 1;
        if (this.c.containsKey(filterDataItem.toString())) {
            filterDataItem.setChecked(this.c.get(filterDataItem.toString()).booleanValue());
        } else if (this.c.isEmpty()) {
            filterDataItem.setChecked(true);
            this.c.put(filterDataItem.toString(), Boolean.TRUE);
        }
        arrayList.add(filterDataItem);
        if (!(list == null || list.size() == 0)) {
            int i2 = 1;
            for (String next : list) {
                FilterDataItem filterDataItem2 = new FilterDataItem(i2, next);
                if (this.c.containsKey(next)) {
                    filterDataItem2.setChecked(this.c.get(next).booleanValue());
                }
                arrayList.add(filterDataItem2);
                i2++;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        FilterDataItem filterDataItem3 = new FilterDataItem(0, this.a.getString(R.string.filter_by_none));
        if (this.d.containsKey(filterDataItem3.toString())) {
            filterDataItem3.setChecked(this.d.get(filterDataItem3.toString()).booleanValue());
        } else if (this.d.isEmpty()) {
            filterDataItem3.setChecked(true);
            this.d.put(filterDataItem3.toString(), Boolean.TRUE);
        }
        arrayList2.add(filterDataItem3);
        if (!(list2 == null || list.size() == 0)) {
            for (String next2 : list2) {
                FilterDataItem filterDataItem4 = new FilterDataItem(i, next2);
                if (this.d.containsKey(next2)) {
                    filterDataItem4.setChecked(this.d.get(next2).booleanValue());
                }
                arrayList2.add(filterDataItem4);
                i++;
            }
        }
        if (this.e == null) {
            this.e = new ArrayList();
        }
        this.e.clear();
        this.e.addAll(arrayList);
        if (this.f == null) {
            this.f = new ArrayList();
        }
        this.f.clear();
        this.f.addAll(arrayList2);
    }

    public final synchronized List<FilterDataItem> b() {
        try {
            if (this.e == null) {
                this.e = new ArrayList();
            }
            if (this.e.isEmpty()) {
                FilterDataItem filterDataItem = new FilterDataItem(0, this.a.getString(R.string.filter_by_none));
                filterDataItem.setChecked(true);
                this.e.add(filterDataItem);
            }
        }
        return this.e;
    }

    public final synchronized List<FilterDataItem> c() {
        try {
            if (this.f == null) {
                this.f = new ArrayList();
            }
            if (this.f.isEmpty()) {
                FilterDataItem filterDataItem = new FilterDataItem(0, this.a.getString(R.string.filter_by_none));
                filterDataItem.setChecked(true);
                this.f.add(filterDataItem);
            }
        }
        return this.f;
    }

    public final synchronized List<FilterDataItem> d() {
        try {
            if (this.g == null) {
                this.g = new ArrayList();
            }
            if (this.g.isEmpty()) {
                int i = 0;
                FilterDataItem filterDataItem = new FilterDataItem(0, this.a.getString(R.string.filter_by_none));
                if (this.h.containsKey(filterDataItem.toString())) {
                    filterDataItem.setChecked(this.h.get(filterDataItem.toString()).booleanValue());
                } else if (this.h.isEmpty()) {
                    filterDataItem.setChecked(true);
                    this.h.put(filterDataItem.toString(), Boolean.TRUE);
                }
                this.g.add(filterDataItem);
                String[] stringArray = this.a.getResources().getStringArray(R.array.filter_by_time_bucket_array);
                int[] intArray = this.a.getResources().getIntArray(R.array.filter_by_time_bucket_start_array);
                int[] intArray2 = this.a.getResources().getIntArray(R.array.filter_by_time_bucket_end_array);
                int length = stringArray.length;
                int i2 = 0;
                while (i < length) {
                    int i3 = i2 + 1;
                    FilterDataItem filterDataItem2 = new FilterDataItem(i3, stringArray[i]);
                    filterDataItem2.setTime(intArray[i2], intArray2[i2]);
                    if (this.h.containsKey(filterDataItem2.toString())) {
                        filterDataItem2.setChecked(this.h.get(filterDataItem2.toString()).booleanValue());
                    }
                    this.g.add(filterDataItem2);
                    i++;
                    i2 = i3;
                }
            }
        }
        return this.g;
    }

    public final synchronized void a(List<FilterDataItem> list) {
        this.e = list;
    }

    public final synchronized void b(List<FilterDataItem> list) {
        this.f = list;
    }

    public final synchronized void e() {
        if (this.e != null && !this.e.isEmpty()) {
            this.c.clear();
            for (FilterDataItem next : this.e) {
                if (next != null && next.isChecked()) {
                    this.c.put(next.toString(), Boolean.TRUE);
                }
            }
        }
        if (this.f != null && !this.f.isEmpty()) {
            this.d.clear();
            for (FilterDataItem next2 : this.f) {
                if (next2 != null && next2.isChecked()) {
                    this.d.put(next2.toString(), Boolean.TRUE);
                }
            }
        }
    }

    public final synchronized void f() {
        if (this.e != null && !this.e.isEmpty()) {
            for (FilterDataItem next : this.e) {
                if (this.c.containsKey(next.toString())) {
                    next.setChecked(this.c.get(next.toString()).booleanValue());
                } else {
                    next.setChecked(false);
                }
            }
        }
        if (this.f != null && !this.f.isEmpty()) {
            for (FilterDataItem next2 : this.f) {
                if (this.d.containsKey(next2.toString())) {
                    next2.setChecked(this.d.get(next2.toString()).booleanValue());
                } else {
                    next2.setChecked(false);
                }
            }
        }
    }

    public final synchronized void g() {
        if (this.g != null && !this.g.isEmpty()) {
            this.h.clear();
            for (FilterDataItem next : this.g) {
                if (next != null && next.isChecked()) {
                    this.h.put(next.toString(), Boolean.TRUE);
                }
            }
        }
    }

    public final synchronized void h() {
        if (this.g != null && !this.g.isEmpty()) {
            for (FilterDataItem next : this.g) {
                if (this.h.containsKey(next.toString())) {
                    next.setChecked(this.h.get(next.toString()).booleanValue());
                } else {
                    next.setChecked(false);
                }
            }
        }
    }

    public final synchronized boolean a(String str, String str2) {
        try {
            if (this.h.isEmpty()) {
                return true;
            }
            String string = this.a.getString(R.string.filter_by_none);
            if (this.h.containsKey(string) && this.h.get(string).booleanValue()) {
                return true;
            }
            if (this.g != null) {
                int size = this.g.size();
                for (int i = 1; i < size; i++) {
                    FilterDataItem filterDataItem = this.g.get(i);
                    if (filterDataItem != null && filterDataItem.matchPeriod(str, str2)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public final boolean i() {
        return this.g != null && this.g.size() > 0 && !this.g.get(0).isChecked();
    }

    public final boolean j() {
        if ((this.e == null || this.e.size() <= 0 || this.e.get(0).isChecked()) && (this.f == null || this.f.size() <= 0 || this.f.get(0).isChecked())) {
            return false;
        }
        return true;
    }
}
