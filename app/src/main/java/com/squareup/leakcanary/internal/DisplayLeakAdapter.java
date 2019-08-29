package com.squareup.leakcanary.internal;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.squareup.leakcanary.LeakTrace;
import com.squareup.leakcanary.LeakTraceElement;
import com.squareup.leakcanary.LeakTraceElement.Holder;
import com.squareup.leakcanary.R;
import com.squareup.leakcanary.internal.DisplayLeakConnectorView.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class DisplayLeakAdapter extends BaseAdapter {
    private static final int NORMAL_ROW = 1;
    private static final int TOP_ROW = 0;
    private List<LeakTraceElement> elements = Collections.emptyList();
    private boolean[] opened = new boolean[0];
    private String referenceKey;
    private String referenceName = "";

    public final long getItemId(int i) {
        return (long) i;
    }

    public final int getItemViewType(int i) {
        return i == 0 ? 0 : 1;
    }

    public final int getViewTypeCount() {
        return 2;
    }

    DisplayLeakAdapter() {
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if (getItemViewType(i) == 0) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.__leak_canary_ref_top_row, viewGroup, false);
            }
            ((TextView) findById(view, R.id.__leak_canary_row_text)).setText(context.getPackageName());
        } else {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.__leak_canary_ref_row, viewGroup, false);
            }
            TextView textView = (TextView) findById(view, R.id.__leak_canary_row_text);
            boolean z = true;
            boolean z2 = i == 1;
            if (i != getCount() - 1) {
                z = false;
            }
            String elementToHtmlString = elementToHtmlString(getItem(i), z2, this.opened[i]);
            if (z && !this.referenceName.equals("") && this.opened[i]) {
                StringBuilder sb = new StringBuilder();
                sb.append(elementToHtmlString);
                sb.append(" <font color='#919191'>");
                sb.append(this.referenceName);
                sb.append("</font>");
                elementToHtmlString = sb.toString();
            }
            textView.setText(Html.fromHtml(elementToHtmlString));
            DisplayLeakConnectorView displayLeakConnectorView = (DisplayLeakConnectorView) findById(view, R.id.__leak_canary_row_connector);
            if (z2) {
                displayLeakConnectorView.setType(Type.START);
            } else if (z) {
                displayLeakConnectorView.setType(Type.END);
            } else {
                displayLeakConnectorView.setType(Type.NODE);
            }
            ((MoreDetailsView) findById(view, R.id.__leak_canary_row_more)).setOpened(this.opened[i]);
        }
        return view;
    }

    private String elementToHtmlString(LeakTraceElement leakTraceElement, boolean z, boolean z2) {
        String str;
        String str2;
        String str3;
        String str4 = "";
        if (leakTraceElement.referenceName == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str4);
            sb.append("leaks ");
            str4 = sb.toString();
        } else if (!z) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str4);
            sb2.append("references ");
            str4 = sb2.toString();
        }
        if (leakTraceElement.type == LeakTraceElement.Type.STATIC_FIELD) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str4);
            sb3.append("<font color='#c48a47'>static</font> ");
            str4 = sb3.toString();
        }
        if (leakTraceElement.holder == Holder.ARRAY || leakTraceElement.holder == Holder.THREAD) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str4);
            sb4.append("<font color='#f3cf83'>");
            sb4.append(leakTraceElement.holder.name().toLowerCase());
            sb4.append("</font> ");
            str4 = sb4.toString();
        }
        int lastIndexOf = leakTraceElement.className.lastIndexOf(46);
        if (lastIndexOf == -1) {
            str = "";
            str2 = leakTraceElement.className;
        } else {
            int i = lastIndexOf + 1;
            String substring = leakTraceElement.className.substring(0, i);
            str2 = leakTraceElement.className.substring(i);
            str = substring;
        }
        if (z2) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str4);
            sb5.append("<font color='#919191'>");
            sb5.append(str);
            sb5.append("</font>");
            str4 = sb5.toString();
        }
        StringBuilder sb6 = new StringBuilder("<font color='#ffffff'>");
        sb6.append(str2);
        sb6.append("</font>");
        String sb7 = sb6.toString();
        StringBuilder sb8 = new StringBuilder();
        sb8.append(str4);
        sb8.append(sb7);
        String sb9 = sb8.toString();
        if (leakTraceElement.referenceName != null) {
            StringBuilder sb10 = new StringBuilder();
            sb10.append(sb9);
            sb10.append(".<font color='#998bb5'>");
            sb10.append(leakTraceElement.referenceName.replaceAll(SimpleComparison.LESS_THAN_OPERATION, "&lt;").replaceAll(SimpleComparison.GREATER_THAN_OPERATION, "&gt;"));
            sb10.append("</font>");
            str3 = sb10.toString();
        } else {
            StringBuilder sb11 = new StringBuilder();
            sb11.append(sb9);
            sb11.append(" <font color='#f3cf83'>instance</font>");
            str3 = sb11.toString();
        }
        if (!z2 || leakTraceElement.extra == null) {
            return str3;
        }
        StringBuilder sb12 = new StringBuilder();
        sb12.append(str3);
        sb12.append(" <font color='#919191'>");
        sb12.append(leakTraceElement.extra);
        sb12.append("</font>");
        return sb12.toString();
    }

    public final void update(LeakTrace leakTrace, String str, String str2) {
        if (!str.equals(this.referenceKey)) {
            this.referenceKey = str;
            this.referenceName = str2;
            this.elements = new ArrayList(leakTrace.elements);
            this.opened = new boolean[(this.elements.size() + 1)];
            notifyDataSetChanged();
        }
    }

    public final void toggleRow(int i) {
        boolean[] zArr = this.opened;
        zArr[i] = !zArr[i];
        notifyDataSetChanged();
    }

    public final int getCount() {
        return this.elements.size() + 1;
    }

    public final LeakTraceElement getItem(int i) {
        if (getItemViewType(i) == 0) {
            return null;
        }
        return this.elements.get(i - 1);
    }

    private static <T extends View> T findById(View view, int i) {
        return view.findViewById(i);
    }
}
