package com.autonavi.minimap.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.TextView;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AutoAdapter<T> extends BaseAdapter implements Filterable {
    private Context mContext;
    private int mDropDownResource;
    private int mFieldId = 0;
    private ArrayFilter mFilter;
    private LayoutInflater mInflater;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private boolean mNotifyOnChange = true;
    /* access modifiers changed from: private */
    public List<T> mObjects;
    /* access modifiers changed from: private */
    public ArrayList<T> mOriginalValues;
    private int mResource;

    class ArrayFilter extends Filter {
        private ArrayFilter() {
        }

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence charSequence) {
            FilterResults filterResults = new FilterResults();
            if (AutoAdapter.this.mOriginalValues == null) {
                synchronized (AutoAdapter.this.mLock) {
                    AutoAdapter.this.mOriginalValues = new ArrayList(AutoAdapter.this.mObjects);
                }
            }
            if (charSequence == null || charSequence.length() == 0) {
                synchronized (AutoAdapter.this.mLock) {
                    ArrayList arrayList = new ArrayList(AutoAdapter.this.mOriginalValues);
                    filterResults.values = arrayList;
                    filterResults.count = arrayList.size();
                }
            } else {
                String lowerCase = charSequence.toString().toLowerCase();
                ArrayList access$100 = AutoAdapter.this.mOriginalValues;
                int size = access$100.size();
                ArrayList arrayList2 = new ArrayList(size);
                for (int i = 0; i < size; i++) {
                    Object obj = access$100.get(i);
                    String lowerCase2 = obj.toString().toLowerCase();
                    if (lowerCase2.startsWith(lowerCase)) {
                        arrayList2.add(obj);
                    } else {
                        String[] split = lowerCase2.split(Token.SEPARATOR);
                        int length = split.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                break;
                            } else if (split[i2].startsWith(lowerCase)) {
                                arrayList2.add(obj);
                                break;
                            } else {
                                i2++;
                            }
                        }
                    }
                }
                filterResults.values = arrayList2;
                filterResults.count = arrayList2.size();
            }
            return filterResults;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            AutoAdapter.this.mObjects = (List) filterResults.values;
            if (filterResults.count > 0) {
                AutoAdapter.this.notifyDataSetChanged();
            } else {
                AutoAdapter.this.notifyDataSetInvalidated();
            }
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public AutoAdapter(Context context, int i) {
        init(context, i, 0, new ArrayList());
    }

    public AutoAdapter(Context context, int i, int i2) {
        init(context, i, i2, new ArrayList());
    }

    public AutoAdapter(Context context, int i, T[] tArr) {
        init(context, i, 0, Arrays.asList(tArr));
    }

    public AutoAdapter(Context context, int i, int i2, T[] tArr) {
        init(context, i, i2, Arrays.asList(tArr));
    }

    public AutoAdapter(Context context, int i, List<T> list) {
        init(context, i, 0, list);
    }

    public AutoAdapter(Context context, int i, int i2, List<T> list) {
        init(context, i, i2, list);
    }

    public void add(T t) {
        if (this.mOriginalValues != null) {
            synchronized (this.mLock) {
                this.mOriginalValues.add(t);
                if (this.mNotifyOnChange) {
                    notifyDataSetChanged();
                }
            }
            return;
        }
        this.mObjects.add(t);
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void insert(T t, int i) {
        if (this.mOriginalValues != null) {
            synchronized (this.mLock) {
                this.mOriginalValues.add(i, t);
                if (this.mNotifyOnChange) {
                    notifyDataSetChanged();
                }
            }
            return;
        }
        this.mObjects.add(i, t);
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void remove(T t) {
        if (this.mOriginalValues != null) {
            synchronized (this.mLock) {
                this.mOriginalValues.remove(t);
            }
        } else {
            this.mObjects.remove(t);
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (this.mOriginalValues != null) {
            synchronized (this.mLock) {
                this.mOriginalValues.clear();
            }
        } else {
            this.mObjects.clear();
        }
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void sort(Comparator<? super T> comparator) {
        Collections.sort(this.mObjects, comparator);
        if (this.mNotifyOnChange) {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.mNotifyOnChange = true;
    }

    public void setNotifyOnChange(boolean z) {
        this.mNotifyOnChange = z;
    }

    private void init(Context context, int i, int i2, List<T> list) {
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        int i3 = R.layout.inputdata;
        this.mDropDownResource = i3;
        this.mResource = i3;
        this.mObjects = list;
        this.mFieldId = R.id.wap_data;
    }

    public Context getContext() {
        return this.mContext;
    }

    public int getCount() {
        return this.mObjects.size();
    }

    public T getItem(int i) {
        return this.mObjects.get(i);
    }

    public int getPosition(T t) {
        return this.mObjects.indexOf(t);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        return createViewFromResource(i, view, viewGroup, this.mResource);
    }

    private View createViewFromResource(int i, View view, ViewGroup viewGroup, int i2) {
        TextView textView;
        if (view == null) {
            view = this.mInflater.inflate(R.layout.inputdata, viewGroup, false);
        }
        try {
            if (this.mFieldId == 0) {
                textView = (TextView) view;
            } else {
                textView = (TextView) view.findViewById(this.mFieldId);
            }
            Object item = getItem(i);
            if (item instanceof CharSequence) {
                textView.setText((CharSequence) item);
            } else {
                textView.setText(item.toString());
            }
            return view;
        } catch (ClassCastException e) {
            throw new IllegalStateException("ArrayAdapter requires the resource ID to be a TextView", e);
        }
    }

    public void setDropDownViewResource(int i) {
        this.mDropDownResource = i;
    }

    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return createViewFromResource(i, view, viewGroup, this.mDropDownResource);
    }

    public static ArrayAdapter<CharSequence> createFromResource(Context context, int i, int i2) {
        return new ArrayAdapter<>(context, i2, context.getResources().getTextArray(i));
    }

    public Filter getFilter() {
        if (this.mFilter == null) {
            this.mFilter = new ArrayFilter<>();
        }
        return this.mFilter;
    }
}
