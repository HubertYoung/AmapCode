package com.autonavi.miniapp.plugin.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.map.model.geocode.PoiItem;
import com.autonavi.minimap.R;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PoiListAdapter extends BaseAdapter {
    private static BigDecimal THOUSAND = new BigDecimal(1000);
    private Context context;
    private int currSelection = -1;
    private List<PoiItem> items;
    private String mKeyword;
    private boolean showPoiActions;
    private boolean showPoiDistance;
    private boolean showSelectionHighlight;

    static class ViewHolder {
        AUTextView addr;
        AUTextView distance;
        AUImageView gotoIcon;
        AUTextView name;
        AUImageView phoneIcon;
        AUImageView selectionIcon;

        public ViewHolder(AUTextView aUTextView, AUTextView aUTextView2, AUImageView aUImageView, AUTextView aUTextView3, AUImageView aUImageView2, AUImageView aUImageView3) {
            this.name = aUTextView;
            this.addr = aUTextView2;
            this.distance = aUTextView3;
            this.selectionIcon = aUImageView;
            this.phoneIcon = aUImageView2;
            this.gotoIcon = aUImageView3;
        }
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public PoiListAdapter(Context context2, @NonNull List<PoiItem> list, boolean z) {
        this.context = context2;
        this.items = list;
        this.showSelectionHighlight = z;
    }

    public int getCount() {
        return this.items.size();
    }

    public Object getItem(int i) {
        return this.items.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        String str;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.list_item_location, viewGroup, false);
        }
        if (view.getTag() == null) {
            ViewHolder viewHolder = new ViewHolder((AUTextView) view.findViewById(R.id.poiselect_title), (AUTextView) view.findViewById(R.id.poiselect_addr), (AUImageView) view.findViewById(R.id.poiselect_selection_mark), (AUTextView) view.findViewById(R.id.poiselect_distance), (AUImageView) view.findViewById(R.id.action_phone), (AUImageView) view.findViewById(R.id.action_goto));
            view.setTag(viewHolder);
        }
        ViewHolder viewHolder2 = (ViewHolder) view.getTag();
        PoiItem poiItem = (PoiItem) getItem(i);
        if (!TextUtils.isEmpty(poiItem.getTitle())) {
            viewHolder2.name.setText(highLightKeyword(poiItem.getTitle()));
            if (TextUtils.isEmpty(poiItem.getShopID())) {
                viewHolder2.name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }
        if (TextUtils.isEmpty(poiItem.getSnippet())) {
            viewHolder2.addr.setVisibility(8);
        } else {
            viewHolder2.addr.setVisibility(0);
            viewHolder2.addr.setText(highLightKeyword(poiItem.getSnippet()));
        }
        if (!this.showPoiDistance || poiItem.getDistance() < 0) {
            viewHolder2.distance.setVisibility(8);
        } else {
            viewHolder2.distance.setVisibility(0);
            if (poiItem.getDistance() < 0 || poiItem.getDistance() >= 1000) {
                str = String.format("%.1fkm", new Object[]{Float.valueOf(new BigDecimal(poiItem.getDistance()).divide(THOUSAND, 2, 6).floatValue())});
            } else {
                str = String.format("%dm", new Object[]{Integer.valueOf(poiItem.getDistance())});
            }
            viewHolder2.distance.setText(str);
        }
        if (this.showSelectionHighlight) {
            if (i == this.currSelection) {
                if (!this.showPoiActions) {
                    viewHolder2.selectionIcon.setVisibility(0);
                }
                viewHolder2.name.setTextColor(this.context.getResources().getColor(com.alipay.mobile.antui.R.color.AU_COLOR_LINK));
                viewHolder2.addr.setTextColor(this.context.getResources().getColor(com.alipay.mobile.antui.R.color.AU_COLOR_LINK));
                viewHolder2.distance.setTextColor(this.context.getResources().getColor(com.alipay.mobile.antui.R.color.AU_COLOR_LINK));
            } else {
                viewHolder2.selectionIcon.setVisibility(8);
                viewHolder2.name.setTextColor(this.context.getResources().getColor(R.color.poi_title_color));
                viewHolder2.addr.setTextColor(this.context.getResources().getColor(R.color.poi_desc_color));
                viewHolder2.distance.setTextColor(this.context.getResources().getColor(R.color.poi_desc_color));
            }
        }
        if (this.showPoiActions) {
            if (poiItem.getLatLonPoint() != null) {
                viewHolder2.gotoIcon.setVisibility(0);
                viewHolder2.gotoIcon.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                    }
                });
            } else {
                viewHolder2.gotoIcon.setVisibility(8);
            }
            if (!TextUtils.isEmpty(poiItem.getTel())) {
                viewHolder2.phoneIcon.setVisibility(0);
                viewHolder2.phoneIcon.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                    }
                });
            } else {
                viewHolder2.phoneIcon.setVisibility(8);
            }
        }
        return view;
    }

    public void setSelection(int i) {
        this.currSelection = i;
    }

    public int getSelection() {
        return this.currSelection;
    }

    public String getKeyword() {
        return this.mKeyword;
    }

    private SpannableString highLightKeyword(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        SpannableString spannableString = new SpannableString(str);
        if (TextUtils.isEmpty(this.mKeyword)) {
            return spannableString;
        }
        try {
            Matcher matcher = Pattern.compile(this.mKeyword).matcher(str);
            while (matcher.find()) {
                spannableString.setSpan(new ForegroundColorSpan(this.context.getResources().getColor(com.alipay.mobile.antui.R.color.AU_COLOR_LINK)), matcher.start(), matcher.end(), 33);
            }
        } catch (PatternSyntaxException e) {
            LoggerFactory.getTraceLogger().error((String) "poiselect", (Throwable) e);
        }
        return spannableString;
    }

    public void setKeyword(String str) {
        this.mKeyword = str;
    }

    public void setShowPoiActions(boolean z) {
        this.showPoiActions = z;
    }

    public void setShowPoiDistance(boolean z) {
        this.showPoiDistance = z;
    }
}
