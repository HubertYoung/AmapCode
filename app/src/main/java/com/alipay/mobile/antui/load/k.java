package com.alipay.mobile.antui.load;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: GridViewWithHeaderAndFooter */
final class k implements Filterable, WrapperListAdapter {
    static final ArrayList<i> a = new ArrayList<>();
    ArrayList<i> b;
    ArrayList<i> c;
    boolean d;
    private final DataSetObservable e = new DataSetObservable();
    private final ListAdapter f;
    private int g = 1;
    private int h = -1;
    private final boolean i;
    private boolean j = true;
    private boolean k = false;

    public k(ArrayList<i> headerViewInfos, ArrayList<i> footViewInfos, ListAdapter adapter) {
        boolean z = true;
        this.f = adapter;
        this.i = adapter instanceof Filterable;
        if (headerViewInfos == null) {
            this.b = a;
        } else {
            this.b = headerViewInfos;
        }
        if (footViewInfos == null) {
            this.c = a;
        } else {
            this.c = footViewInfos;
        }
        this.d = (!a(this.b) || !a(this.c)) ? false : z;
    }

    public final void a(int numColumns) {
        if (numColumns > 0 && this.g != numColumns) {
            this.g = numColumns;
            a();
        }
    }

    public final void b(int height) {
        this.h = height;
    }

    private int b() {
        return this.b.size();
    }

    private int c() {
        return this.c.size();
    }

    public final boolean isEmpty() {
        return (this.f == null || this.f.isEmpty()) && b() == 0 && c() == 0;
    }

    private static boolean a(ArrayList<i> infos) {
        if (infos != null) {
            Iterator<i> it = infos.iterator();
            while (it.hasNext()) {
                if (!it.next().d) {
                    return false;
                }
            }
        }
        return true;
    }

    public final boolean a(View v) {
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            if (this.b.get(i2).a == v) {
                this.b.remove(i2);
                this.d = a(this.b) && a(this.c);
                this.e.notifyChanged();
                return true;
            }
        }
        return false;
    }

    public final boolean b(View v) {
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            if (this.c.get(i2).a == v) {
                this.c.remove(i2);
                this.d = a(this.b) && a(this.c);
                this.e.notifyChanged();
                return true;
            }
        }
        return false;
    }

    public final int getCount() {
        if (this.f != null) {
            return ((c() + b()) * this.g) + d();
        }
        return (c() + b()) * this.g;
    }

    public final boolean areAllItemsEnabled() {
        if (this.f == null) {
            return true;
        }
        if (!this.d || !this.f.areAllItemsEnabled()) {
            return false;
        }
        return true;
    }

    private int d() {
        return (int) (Math.ceil((double) ((1.0f * ((float) this.f.getCount())) / ((float) this.g))) * ((double) this.g));
    }

    public final boolean isEnabled(int position) {
        int numHeadersAndPlaceholders = b() * this.g;
        if (position >= numHeadersAndPlaceholders) {
            int adjPosition = position - numHeadersAndPlaceholders;
            int adapterCount = 0;
            if (this.f != null) {
                adapterCount = d();
                if (adjPosition < adapterCount) {
                    return adjPosition < this.f.getCount() && this.f.isEnabled(adjPosition);
                }
            }
            int footerPosition = adjPosition - adapterCount;
            return footerPosition % this.g == 0 && this.c.get(footerPosition / this.g).d;
        } else if (position % this.g != 0 || !this.b.get(position / this.g).d) {
            return false;
        } else {
            return true;
        }
    }

    public final Object getItem(int position) {
        int numHeadersAndPlaceholders = b() * this.g;
        if (position >= numHeadersAndPlaceholders) {
            int adjPosition = position - numHeadersAndPlaceholders;
            int adapterCount = 0;
            if (this.f != null) {
                adapterCount = d();
                if (adjPosition < adapterCount) {
                    if (adjPosition < this.f.getCount()) {
                        return this.f.getItem(adjPosition);
                    }
                    return null;
                }
            }
            int footerPosition = adjPosition - adapterCount;
            if (footerPosition % this.g == 0) {
                return this.c.get(footerPosition).c;
            }
            return null;
        } else if (position % this.g == 0) {
            return this.b.get(position / this.g).c;
        } else {
            return null;
        }
    }

    public final long getItemId(int position) {
        int numHeadersAndPlaceholders = b() * this.g;
        if (this.f != null && position >= numHeadersAndPlaceholders) {
            int adjPosition = position - numHeadersAndPlaceholders;
            if (adjPosition < this.f.getCount()) {
                return this.f.getItemId(adjPosition);
            }
        }
        return -1;
    }

    public final boolean hasStableIds() {
        if (this.f != null) {
            return this.f.hasStableIds();
        }
        return false;
    }

    public final View getView(int position, View convertView, ViewGroup parent) {
        int numHeadersAndPlaceholders = b() * this.g;
        if (position < numHeadersAndPlaceholders) {
            View headerViewContainer = this.b.get(position / this.g).b;
            if (position % this.g == 0) {
                return headerViewContainer;
            }
            if (convertView == null) {
                convertView = new View(parent.getContext());
            }
            convertView.setVisibility(4);
            convertView.setMinimumHeight(headerViewContainer.getHeight());
            return convertView;
        }
        int adjPosition = position - numHeadersAndPlaceholders;
        int adapterCount = 0;
        if (this.f != null) {
            adapterCount = d();
            if (adjPosition < adapterCount) {
                if (adjPosition < this.f.getCount()) {
                    return this.f.getView(adjPosition, convertView, parent);
                }
                if (convertView == null) {
                    convertView = new View(parent.getContext());
                }
                convertView.setVisibility(4);
                convertView.setMinimumHeight(this.h);
                return convertView;
            }
        }
        int footerPosition = adjPosition - adapterCount;
        if (footerPosition < getCount()) {
            View headerViewContainer2 = this.c.get(footerPosition / this.g).b;
            if (position % this.g == 0) {
                return headerViewContainer2;
            }
            if (convertView == null) {
                convertView = new View(parent.getContext());
            }
            convertView.setVisibility(4);
            convertView.setMinimumHeight(headerViewContainer2.getHeight());
            return convertView;
        }
        throw new ArrayIndexOutOfBoundsException(position);
    }

    public final int getItemViewType(int position) {
        int adapterViewTypeStart;
        int numHeadersAndPlaceholders = b() * this.g;
        if (this.f == null) {
            adapterViewTypeStart = 0;
        } else {
            adapterViewTypeStart = this.f.getViewTypeCount() - 1;
        }
        int type = -2;
        if (this.j && position < numHeadersAndPlaceholders) {
            if (position == 0 && this.k) {
                type = this.b.size() + adapterViewTypeStart + this.c.size() + 1 + 1;
            }
            if (position % this.g != 0) {
                type = adapterViewTypeStart + (position / this.g) + 1;
            }
        }
        int adjPosition = position - numHeadersAndPlaceholders;
        int adapterCount = 0;
        if (this.f != null) {
            adapterCount = d();
            if (adjPosition >= 0 && adjPosition < adapterCount) {
                if (adjPosition < this.f.getCount()) {
                    type = this.f.getItemViewType(adjPosition);
                } else if (this.j) {
                    type = this.b.size() + adapterViewTypeStart + 1;
                }
            }
        }
        if (!this.j) {
            return type;
        }
        int footerPosition = adjPosition - adapterCount;
        if (footerPosition < 0 || footerPosition >= getCount() || footerPosition % this.g == 0) {
            return type;
        }
        return this.b.size() + adapterViewTypeStart + 1 + (footerPosition / this.g) + 1;
    }

    public final int getViewTypeCount() {
        int count = this.f == null ? 1 : this.f.getViewTypeCount();
        if (!this.j) {
            return count;
        }
        int offset = this.b.size() + 1 + this.c.size();
        if (this.k) {
            offset++;
        }
        return count + offset;
    }

    public final void registerDataSetObserver(DataSetObserver observer) {
        this.e.registerObserver(observer);
        if (this.f != null) {
            this.f.registerDataSetObserver(observer);
        }
    }

    public final void unregisterDataSetObserver(DataSetObserver observer) {
        this.e.unregisterObserver(observer);
        if (this.f != null) {
            this.f.unregisterDataSetObserver(observer);
        }
    }

    public final Filter getFilter() {
        if (this.i) {
            return ((Filterable) this.f).getFilter();
        }
        return null;
    }

    public final ListAdapter getWrappedAdapter() {
        return this.f;
    }

    public final void a() {
        this.e.notifyChanged();
    }
}
