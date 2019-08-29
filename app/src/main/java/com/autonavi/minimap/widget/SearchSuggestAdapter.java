package com.autonavi.minimap.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.view.OneClickListener;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.sugg.TipItem;
import com.autonavi.map.db.helper.SearchHistoryHelper;
import com.autonavi.minimap.R;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import com.autonavi.minimap.search.view.SearchSuggChildNodeManager;
import com.autonavi.minimap.search.view.SearchSuggChildNodeManager.a;
import com.autonavi.minimap.widget.SearchEdit.OnItemEventListener;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchSuggestAdapter extends BaseAdapter implements a {
    private static final int CHILD_ICON_LIST_MIN_SIZE = 3;
    private static final int CHILD_NAME_MAX_LENGTH = 3;
    private static final int ITEM_TYPE_CHILD_NODE_ICON = 4;
    private static final int ITEM_TYPE_COMMON = 0;
    private static final int ITEM_TYPE_HAVING_CHILD = 1;
    private static final int ITEM_TYPE_NO_ADDRESS = 2;
    private static final int ITEM_TYPE_RECT_SEARCH = 3;
    /* access modifiers changed from: private */
    public int FROM_PAGE;
    /* access modifiers changed from: private */
    public Context mContext;
    private LayoutInflater mInflater;
    private String mKeyWord;
    /* access modifiers changed from: private */
    public OnItemEventListener mOnItemEventListener;
    private boolean mShowRoute;
    private List<TipItem> mTipItemList;
    /* access modifiers changed from: private */
    public TopListSchemaCallback mTopListSchemaCallback;

    class FiliationAdapter extends BaseAdapter {
        public int mCountAd;
        private LayoutInflater mInflaterAd;
        private List<TipItem> mListAd = null;

        class ChildItemHolder {
            EllipsizedChildPoiView a;

            ChildItemHolder() {
            }
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public FiliationAdapter(List<TipItem> list, LayoutInflater layoutInflater, Context context) {
            this.mListAd = list;
            this.mInflaterAd = layoutInflater;
            if (list != null) {
                this.mCountAd = list.size();
            }
        }

        public int getCount() {
            return this.mCountAd;
        }

        public Object getItem(int i) {
            return this.mListAd.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2;
            ChildItemHolder childItemHolder;
            if (view == null) {
                childItemHolder = new ChildItemHolder();
                view2 = this.mInflaterAd.inflate(R.layout.search_dialog_listview_item_filiation_sugg_child, null);
                childItemHolder.a = (EllipsizedChildPoiView) view2.findViewById(R.id.child_poi_view);
                view2.setTag(childItemHolder);
            } else {
                view2 = view;
                childItemHolder = (ChildItemHolder) view.getTag();
            }
            TipItem tipItem = this.mListAd.get(i);
            if (!(childItemHolder == null || childItemHolder.a == null)) {
                String str = !TextUtils.isEmpty(tipItem.shortname) ? tipItem.shortname : !TextUtils.isEmpty(tipItem.name) ? tipItem.name : "";
                String str2 = "";
                if (SearchSuggestAdapter.this.FROM_PAGE == 10049 || SearchSuggestAdapter.this.FROM_PAGE == 10050 || SearchSuggestAdapter.this.FROM_PAGE == 10300) {
                    str2 = tipItem.label;
                }
                childItemHolder.a.setData(str, str2);
            }
            return view2;
        }

        public View getView(Context context, TipItem tipItem) {
            float f = context.getResources().getDisplayMetrics().density;
            TextView textView = new TextView(context);
            textView.setGravity(16);
            textView.setIncludeFontPadding(false);
            textView.setBackgroundResource(R.drawable.sugg_child_item_bg);
            textView.setTextSize(1, 13.0f);
            textView.setTextColor(context.getResources().getColor(R.color.f_c_3));
            textView.setEllipsize(TruncateAt.END);
            textView.setLines(1);
            LayoutParams layoutParams = new LayoutParams(-2, (int) ((32.0f * f) + 0.5f));
            layoutParams.setMargins(0, 0, (int) ((8.0f * f) + 0.5f), 0);
            textView.setLayoutParams(layoutParams);
            int i = (int) ((f * 16.0f) + 0.5f);
            textView.setPadding(i, 0, i, 0);
            if (!TextUtils.isEmpty(tipItem.shortname)) {
                textView.setText(tipItem.shortname);
            } else if (!TextUtils.isEmpty(tipItem.name)) {
                textView.setText(tipItem.name);
            } else {
                textView.setText("");
            }
            return textView;
        }
    }

    static class SuggChildNodeHolder {
        SearchSuggChildNodeManager a;
        TextView b;
        TextView c;
        TextView d;
        TextView e;
        View f;
        ImageView g;
        ImageView h;
        TextView i;
        View j;
        TextView k;

        SuggChildNodeHolder() {
        }
    }

    static class SuggHavingChildHolder {
        TextView a;
        TextView b;
        TextView c;
        TextView d;
        View e;
        ImageView f;
        ImageView g;
        TextView h;
        GridView i;

        SuggHavingChildHolder() {
        }
    }

    static class SuggRectSearchHolder {
        TextView a;

        SuggRectSearchHolder() {
        }
    }

    static class SuggTypeCommonHolder {
        TextView a;
        TextView b;
        TextView c;
        TextView d;
        View e;
        ImageView f;
        ImageView g;
        TextView h;
        RatingBar i;
        TextView j;
        TextView k;
        LinearLayout l;
        RelativeLayout m;
        RelativeLayout n;
        SearchListColorBlockView o;

        SuggTypeCommonHolder() {
        }
    }

    static class SuggTypeNoAddressHolder {
        TextView a;
        TextView b;
        TextView c;
        TextView d;
        View e;
        ImageView f;
        ImageView g;
        TextView h;
        LinearLayout i;

        SuggTypeNoAddressHolder() {
        }
    }

    public interface TopListSchemaCallback {
        void start(Intent intent);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getViewTypeCount() {
        return 5;
    }

    public SearchSuggestAdapter(Context context, List<TipItem> list, int i, String str, boolean z) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.FROM_PAGE = i;
        this.mTipItemList = list;
        this.mKeyWord = str;
        this.mShowRoute = z;
    }

    public void setTopListSchemaCallback(TopListSchemaCallback topListSchemaCallback) {
        this.mTopListSchemaCallback = topListSchemaCallback;
    }

    public void setKeyWord(String str) {
        this.mKeyWord = str;
    }

    private int getItemType(TipItem tipItem) {
        if (tipItem.tipItemList == null || tipItem.tipItemList.size() <= 0) {
            return tipItem.isRectSearch ? 3 : 0;
        }
        if (TextUtils.isEmpty(tipItem.poiid)) {
            return isChildNodeIconType(tipItem.tipItemList) ? 4 : 2;
        }
        return 1;
    }

    private SpannableString getMarkColorString(Context context, String str, String str2) {
        SpannableString spannableString = new SpannableString(str);
        if (str2 != null) {
            try {
                if (str.contains(str2)) {
                    int indexOf = str.indexOf(str2);
                    int length = str2.length() + indexOf;
                    if (indexOf >= 0 && length > 0 && indexOf < length) {
                        spannableString.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.f_c_6)), indexOf, length, 33);
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
        return spannableString;
    }

    public void setOnItemEventListener(OnItemEventListener onItemEventListener) {
        this.mOnItemEventListener = onItemEventListener;
    }

    public int getCount() {
        return this.mTipItemList.size();
    }

    public TipItem getItem(int i) {
        return this.mTipItemList.get(i);
    }

    public int getItemViewType(int i) {
        return getItemType(this.mTipItemList.get(i));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0316, code lost:
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0317, code lost:
        r14 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x03e5, code lost:
        r12 = r10;
        r1 = null;
        r10 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x018c, code lost:
        r12 = null;
        r13 = null;
        r14 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r20, android.view.View r21, android.view.ViewGroup r22) {
        /*
            r19 = this;
            r7 = r19
            r8 = r20
            int r0 = r19.getItemViewType(r20)
            com.autonavi.bundle.entity.sugg.TipItem r9 = r19.getItem(r20)
            android.content.Context r1 = r7.mContext
            android.content.res.Resources r1 = r1.getResources()
            android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
            float r1 = r1.density
            com.autonavi.minimap.widget.SearchSuggestAdapter$FiliationAdapter r2 = new com.autonavi.minimap.widget.SearchSuggestAdapter$FiliationAdapter
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r3 = r9.tipItemList
            android.view.LayoutInflater r4 = r7.mInflater
            android.content.Context r5 = r7.mContext
            r2.<init>(r3, r4, r5)
            com.autonavi.minimap.widget.SearchSuggestAdapter$1 r3 = new com.autonavi.minimap.widget.SearchSuggestAdapter$1
            r3.<init>(r2, r8)
            r5 = 1090519040(0x41000000, float:8.0)
            r6 = 1115684864(0x42800000, float:64.0)
            r10 = 0
            r11 = 0
            if (r21 != 0) goto L_0x0308
            switch(r0) {
                case 1: goto L_0x0273;
                case 2: goto L_0x0191;
                case 3: goto L_0x0171;
                case 4: goto L_0x00e0;
                default: goto L_0x0033;
            }
        L_0x0033:
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggTypeCommonHolder r1 = new com.autonavi.minimap.widget.SearchSuggestAdapter$SuggTypeCommonHolder
            r1.<init>()
            android.view.LayoutInflater r2 = r7.mInflater
            int r3 = com.autonavi.minimap.R.layout.search_dialog_listview_item_add
            r10 = 0
            android.view.View r2 = r2.inflate(r3, r10)
            int r3 = com.autonavi.minimap.R.id.text
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.a = r3
            int r3 = com.autonavi.minimap.R.id.more_station_tv
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.k = r3
            int r3 = com.autonavi.minimap.R.id.text_tag
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.c = r3
            int r3 = com.autonavi.minimap.R.id.addr
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.b = r3
            int r3 = com.autonavi.minimap.R.id.tvPoiTag
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.d = r3
            int r3 = com.autonavi.minimap.R.id.divider_point
            android.view.View r3 = r2.findViewById(r3)
            r1.e = r3
            int r3 = com.autonavi.minimap.R.id.img_view
            android.view.View r3 = r2.findViewById(r3)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r1.f = r3
            int r3 = com.autonavi.minimap.R.id.img_plus_view
            android.view.View r3 = r2.findViewById(r3)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r1.g = r3
            int r3 = com.autonavi.minimap.R.id.distance
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.h = r3
            int r3 = com.autonavi.minimap.R.id.rating_bar
            android.view.View r3 = r2.findViewById(r3)
            android.widget.RatingBar r3 = (android.widget.RatingBar) r3
            r1.i = r3
            int r3 = com.autonavi.minimap.R.id.num_review
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.j = r3
            int r3 = com.autonavi.minimap.R.id.super_addr
            android.view.View r3 = r2.findViewById(r3)
            android.widget.LinearLayout r3 = (android.widget.LinearLayout) r3
            r1.l = r3
            int r3 = com.autonavi.minimap.R.id.ll_content
            android.view.View r3 = r2.findViewById(r3)
            android.widget.RelativeLayout r3 = (android.widget.RelativeLayout) r3
            r1.m = r3
            int r3 = com.autonavi.minimap.R.id.main_content_rl
            android.view.View r3 = r2.findViewById(r3)
            android.widget.RelativeLayout r3 = (android.widget.RelativeLayout) r3
            r1.n = r3
            int r3 = com.autonavi.minimap.R.id.super_addr_color_block
            android.view.View r3 = r2.findViewById(r3)
            com.autonavi.minimap.widget.SearchListColorBlockView r3 = (com.autonavi.minimap.widget.SearchListColorBlockView) r3
            r1.o = r3
            r2.setTag(r1)
            r15 = r2
            r12 = r10
            r13 = r12
            r14 = r13
            r10 = r1
            r1 = r14
            goto L_0x03ea
        L_0x00e0:
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggChildNodeHolder r1 = new com.autonavi.minimap.widget.SearchSuggestAdapter$SuggChildNodeHolder
            r1.<init>()
            android.view.LayoutInflater r2 = r7.mInflater
            int r3 = com.autonavi.minimap.R.layout.suggest_child_node_icon_layout
            r4 = r22
            android.view.View r2 = r2.inflate(r3, r4, r11)
            int r3 = com.autonavi.minimap.R.id.search_child_node_gird_view
            android.view.View r3 = r2.findViewById(r3)
            android.widget.GridView r3 = (android.widget.GridView) r3
            com.autonavi.minimap.search.view.SearchSuggChildNodeManager r4 = new com.autonavi.minimap.search.view.SearchSuggChildNodeManager
            android.content.Context r5 = r7.mContext
            r4.<init>(r5, r3)
            r1.a = r4
            com.autonavi.minimap.search.view.SearchSuggChildNodeManager r3 = r1.a
            r3.setChildClickListener(r7)
            int r3 = com.autonavi.minimap.R.id.top_list_container
            android.view.View r3 = r2.findViewById(r3)
            r1.j = r3
            android.view.View r3 = r1.j
            int r4 = com.autonavi.minimap.R.id.top_list_name_view
            android.view.View r3 = r3.findViewById(r4)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.k = r3
            int r3 = com.autonavi.minimap.R.id.text
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.b = r3
            int r3 = com.autonavi.minimap.R.id.text_tag
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.d = r3
            int r3 = com.autonavi.minimap.R.id.addr
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.c = r3
            int r3 = com.autonavi.minimap.R.id.tvPoiTag
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.e = r3
            int r3 = com.autonavi.minimap.R.id.divider_point
            android.view.View r3 = r2.findViewById(r3)
            r1.f = r3
            int r3 = com.autonavi.minimap.R.id.img_view
            android.view.View r3 = r2.findViewById(r3)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r1.g = r3
            int r3 = com.autonavi.minimap.R.id.img_plus_view
            android.view.View r3 = r2.findViewById(r3)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r1.h = r3
            int r3 = com.autonavi.minimap.R.id.distance
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.i = r3
            r2.setTag(r1)
            r14 = r1
            r15 = r2
        L_0x016c:
            r1 = r10
            r12 = r1
            r13 = r12
            goto L_0x03ea
        L_0x0171:
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggRectSearchHolder r1 = new com.autonavi.minimap.widget.SearchSuggestAdapter$SuggRectSearchHolder
            r1.<init>()
            android.view.LayoutInflater r2 = r7.mInflater
            int r3 = com.autonavi.minimap.R.layout.sugguest_list_rect_search_layout
            android.view.View r2 = r2.inflate(r3, r10)
            int r3 = com.autonavi.minimap.R.id.text
            android.view.View r3 = r2.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r1.a = r3
            r2.setTag(r1)
            r15 = r2
        L_0x018c:
            r12 = r10
            r13 = r12
            r14 = r13
            goto L_0x03ea
        L_0x0191:
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggTypeNoAddressHolder r12 = new com.autonavi.minimap.widget.SearchSuggestAdapter$SuggTypeNoAddressHolder
            r12.<init>()
            android.view.LayoutInflater r13 = r7.mInflater
            int r14 = com.autonavi.minimap.R.layout.search_dialog_listview_item_filiation_tquery
            android.view.View r13 = r13.inflate(r14, r10)
            int r14 = com.autonavi.minimap.R.id.search_dlg_father_container
            android.view.View r14 = r13.findViewById(r14)
            android.widget.LinearLayout r14 = (android.widget.LinearLayout) r14
            r12.i = r14
            int r14 = com.autonavi.minimap.R.id.text
            android.view.View r14 = r13.findViewById(r14)
            android.widget.TextView r14 = (android.widget.TextView) r14
            r12.a = r14
            int r14 = com.autonavi.minimap.R.id.text_tag
            android.view.View r14 = r13.findViewById(r14)
            android.widget.TextView r14 = (android.widget.TextView) r14
            r12.c = r14
            int r14 = com.autonavi.minimap.R.id.addr
            android.view.View r14 = r13.findViewById(r14)
            android.widget.TextView r14 = (android.widget.TextView) r14
            r12.b = r14
            int r14 = com.autonavi.minimap.R.id.tvPoiTag
            android.view.View r14 = r13.findViewById(r14)
            android.widget.TextView r14 = (android.widget.TextView) r14
            r12.d = r14
            int r14 = com.autonavi.minimap.R.id.divider_point
            android.view.View r14 = r13.findViewById(r14)
            r12.e = r14
            int r14 = com.autonavi.minimap.R.id.img_view
            android.view.View r14 = r13.findViewById(r14)
            android.widget.ImageView r14 = (android.widget.ImageView) r14
            r12.f = r14
            int r14 = com.autonavi.minimap.R.id.img_plus_view
            android.view.View r14 = r13.findViewById(r14)
            android.widget.ImageView r14 = (android.widget.ImageView) r14
            r12.g = r14
            int r14 = com.autonavi.minimap.R.id.distance
            android.view.View r14 = r13.findViewById(r14)
            android.widget.TextView r14 = (android.widget.TextView) r14
            r12.h = r14
            android.content.Context r14 = r7.mContext
            android.content.res.Resources r14 = r14.getResources()
            android.util.DisplayMetrics r14 = r14.getDisplayMetrics()
            int r14 = r14.widthPixels
            float r14 = (float) r14
            float r6 = r6 * r1
            float r14 = r14 - r6
            int r6 = (int) r14
            android.widget.LinearLayout r14 = r12.i
            r14.removeAllViews()
            r14 = 0
            r15 = 0
        L_0x020e:
            int r10 = r2.getCount()
            if (r14 >= r10) goto L_0x0268
            android.content.Context r10 = r7.mContext
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r4 = r9.tipItemList
            java.lang.Object r4 = r4.get(r14)
            com.autonavi.bundle.entity.sugg.TipItem r4 = (com.autonavi.bundle.entity.sugg.TipItem) r4
            android.view.View r4 = r2.getView(r10, r4)
            android.widget.TextView r4 = (android.widget.TextView) r4
            if (r4 == 0) goto L_0x0265
            r4.measure(r11, r11)
            int r10 = r4.getMeasuredWidth()
            float r10 = (float) r10
            float r17 = r1 * r5
            float r10 = r10 + r17
            int r10 = (int) r10
            int r15 = r15 + r10
            if (r14 == 0) goto L_0x0256
            if (r15 <= r6) goto L_0x0256
            int r15 = r15 - r10
            int r6 = r6 - r15
            float r2 = (float) r6
            r5 = 1119092736(0x42b40000, float:90.0)
            float r1 = r1 * r5
            int r1 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x0268
            r4.setWidth(r6)
            android.widget.LinearLayout r1 = r12.i
            r1.addView(r4)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r14)
            r4.setTag(r1)
            r4.setOnClickListener(r3)
            goto L_0x0268
        L_0x0256:
            android.widget.LinearLayout r10 = r12.i
            r10.addView(r4)
            java.lang.Integer r10 = java.lang.Integer.valueOf(r14)
            r4.setTag(r10)
            r4.setOnClickListener(r3)
        L_0x0265:
            int r14 = r14 + 1
            goto L_0x020e
        L_0x0268:
            r13.setTag(r12)
            r15 = r13
            r1 = 0
            r10 = 0
            r14 = 0
            r13 = r12
            r12 = 0
            goto L_0x03ea
        L_0x0273:
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggHavingChildHolder r10 = new com.autonavi.minimap.widget.SearchSuggestAdapter$SuggHavingChildHolder
            r10.<init>()
            android.view.LayoutInflater r1 = r7.mInflater
            int r3 = com.autonavi.minimap.R.layout.search_dialog_listview_item_filiation
            r4 = 0
            android.view.View r1 = r1.inflate(r3, r4)
            int r3 = com.autonavi.minimap.R.id.search_dlg_father_gridview
            android.view.View r3 = r1.findViewById(r3)
            android.widget.GridView r3 = (android.widget.GridView) r3
            r10.i = r3
            int r3 = com.autonavi.minimap.R.id.text
            android.view.View r3 = r1.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r10.a = r3
            int r3 = com.autonavi.minimap.R.id.text_tag
            android.view.View r3 = r1.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r10.c = r3
            int r3 = com.autonavi.minimap.R.id.addr
            android.view.View r3 = r1.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r10.b = r3
            int r3 = com.autonavi.minimap.R.id.tvPoiTag
            android.view.View r3 = r1.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r10.d = r3
            int r3 = com.autonavi.minimap.R.id.divider_point
            android.view.View r3 = r1.findViewById(r3)
            r10.e = r3
            int r3 = com.autonavi.minimap.R.id.img_view
            android.view.View r3 = r1.findViewById(r3)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r10.f = r3
            int r3 = com.autonavi.minimap.R.id.img_plus_view
            android.view.View r3 = r1.findViewById(r3)
            android.widget.ImageView r3 = (android.widget.ImageView) r3
            r10.g = r3
            int r3 = com.autonavi.minimap.R.id.distance
            android.view.View r3 = r1.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r10.h = r3
            android.widget.GridView r3 = r10.i
            int r4 = r9.column
            r5 = 3
            if (r4 >= r5) goto L_0x02e2
            int r5 = r9.column
        L_0x02e2:
            r3.setNumColumns(r5)
            int r3 = r2.mCountAd
            int r4 = r9.column
            int r4 = r4 * 2
            if (r3 <= r4) goto L_0x02f3
            int r3 = r9.column
            int r3 = r3 * 2
            r2.mCountAd = r3
        L_0x02f3:
            android.widget.GridView r3 = r10.i
            r3.setAdapter(r2)
            android.widget.GridView r2 = r10.i
            com.autonavi.minimap.widget.SearchSuggestAdapter$2 r3 = new com.autonavi.minimap.widget.SearchSuggestAdapter$2
            r3.<init>(r9)
            r2.setOnItemClickListener(r3)
            r1.setTag(r10)
            r15 = r1
            goto L_0x03e5
        L_0x0308:
            switch(r0) {
                case 1: goto L_0x03bf;
                case 2: goto L_0x032f;
                case 3: goto L_0x0325;
                case 4: goto L_0x031a;
                default: goto L_0x030b;
            }
        L_0x030b:
            java.lang.Object r1 = r21.getTag()
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggTypeCommonHolder r1 = (com.autonavi.minimap.widget.SearchSuggestAdapter.SuggTypeCommonHolder) r1
            r15 = r21
            r10 = r1
            r1 = 0
            r12 = 0
        L_0x0316:
            r13 = 0
        L_0x0317:
            r14 = 0
            goto L_0x03ea
        L_0x031a:
            java.lang.Object r1 = r21.getTag()
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggChildNodeHolder r1 = (com.autonavi.minimap.widget.SearchSuggestAdapter.SuggChildNodeHolder) r1
            r15 = r21
            r14 = r1
            goto L_0x016c
        L_0x0325:
            java.lang.Object r1 = r21.getTag()
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggRectSearchHolder r1 = (com.autonavi.minimap.widget.SearchSuggestAdapter.SuggRectSearchHolder) r1
            r15 = r21
            goto L_0x018c
        L_0x032f:
            java.lang.Object r4 = r21.getTag()
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggTypeNoAddressHolder r4 = (com.autonavi.minimap.widget.SearchSuggestAdapter.SuggTypeNoAddressHolder) r4
            android.content.Context r13 = r7.mContext
            android.content.res.Resources r13 = r13.getResources()
            android.util.DisplayMetrics r13 = r13.getDisplayMetrics()
            int r13 = r13.widthPixels
            float r13 = (float) r13
            float r6 = r6 * r1
            float r13 = r13 - r6
            int r6 = (int) r13
            android.widget.LinearLayout r13 = r4.i
            int r13 = r13.getChildCount()
            if (r13 <= 0) goto L_0x0353
            android.widget.LinearLayout r13 = r4.i
            r13.removeAllViews()
        L_0x0353:
            r13 = 0
            r14 = 0
        L_0x0355:
            int r15 = r2.getCount()
            if (r13 >= r15) goto L_0x03b7
            android.content.Context r15 = r7.mContext
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r10 = r9.tipItemList
            java.lang.Object r10 = r10.get(r13)
            com.autonavi.bundle.entity.sugg.TipItem r10 = (com.autonavi.bundle.entity.sugg.TipItem) r10
            android.view.View r10 = r2.getView(r15, r10)
            android.widget.TextView r10 = (android.widget.TextView) r10
            if (r10 == 0) goto L_0x03af
            r10.measure(r11, r11)
            int r15 = r10.getMeasuredWidth()
            float r15 = (float) r15
            float r16 = r1 * r5
            float r15 = r15 + r16
            int r15 = (int) r15
            int r14 = r14 + r15
            if (r13 == 0) goto L_0x039d
            if (r14 <= r6) goto L_0x039d
            int r14 = r14 - r15
            int r6 = r6 - r14
            float r2 = (float) r6
            r15 = 1119092736(0x42b40000, float:90.0)
            float r1 = r1 * r15
            int r1 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r1 <= 0) goto L_0x03b7
            r10.setWidth(r6)
            android.widget.LinearLayout r1 = r4.i
            r1.addView(r10)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            r10.setTag(r1)
            r10.setOnClickListener(r3)
            goto L_0x03b7
        L_0x039d:
            r15 = 1119092736(0x42b40000, float:90.0)
            android.widget.LinearLayout r5 = r4.i
            r5.addView(r10)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r13)
            r10.setTag(r5)
            r10.setOnClickListener(r3)
            goto L_0x03b1
        L_0x03af:
            r15 = 1119092736(0x42b40000, float:90.0)
        L_0x03b1:
            int r13 = r13 + 1
            r5 = 1090519040(0x41000000, float:8.0)
            r10 = 0
            goto L_0x0355
        L_0x03b7:
            r15 = r21
            r13 = r4
            r1 = 0
            r10 = 0
            r12 = 0
            goto L_0x0317
        L_0x03bf:
            java.lang.Object r1 = r21.getTag()
            r10 = r1
            com.autonavi.minimap.widget.SearchSuggestAdapter$SuggHavingChildHolder r10 = (com.autonavi.minimap.widget.SearchSuggestAdapter.SuggHavingChildHolder) r10
            int r1 = r2.mCountAd
            int r3 = r9.column
            int r3 = r3 * 2
            if (r1 <= r3) goto L_0x03d4
            int r1 = r9.column
            int r1 = r1 * 2
            r2.mCountAd = r1
        L_0x03d4:
            android.widget.GridView r1 = r10.i
            r1.setAdapter(r2)
            android.widget.GridView r1 = r10.i
            com.autonavi.minimap.widget.SearchSuggestAdapter$3 r2 = new com.autonavi.minimap.widget.SearchSuggestAdapter$3
            r2.<init>(r9)
            r1.setOnItemClickListener(r2)
            r15 = r21
        L_0x03e5:
            r12 = r10
            r1 = 0
            r10 = 0
            goto L_0x0316
        L_0x03ea:
            r6 = 8
            switch(r0) {
                case 1: goto L_0x04d5;
                case 2: goto L_0x049a;
                case 3: goto L_0x0490;
                case 4: goto L_0x0437;
                default: goto L_0x03ef;
            }
        L_0x03ef:
            r13 = 8
            android.widget.TextView r0 = r10.h
            r7.processDistance(r0, r9)
            android.widget.ImageView r1 = r10.g
            boolean r2 = r7.mShowRoute
            java.lang.String r4 = r7.mKeyWord
            android.widget.TextView r6 = r10.h
            r0 = r7
            r3 = r9
            r5 = r8
            r0.processRoute(r1, r2, r3, r4, r5, r6)
            android.widget.ImageView r0 = r10.f
            r7.processSuggLogo(r0, r9)
            android.widget.TextView r0 = r10.a
            java.lang.String r1 = r7.mKeyWord
            r7.processSugFont(r0, r9, r1)
            android.widget.TextView r0 = r10.c
            r7.processSameNameTip(r0, r9)
            android.widget.TextView r0 = r10.d
            android.view.View r1 = r10.e
            r7.processtvPoiTag(r0, r1, r9)
            android.widget.LinearLayout r2 = r10.l
            android.widget.TextView r3 = r10.b
            android.view.View r4 = r10.e
            r0 = r7
            r1 = r10
            r5 = r9
            r0.processAddress(r1, r2, r3, r4, r5)
            android.widget.RatingBar r0 = r10.i
            r7.processRatingBar(r9, r0)
            android.widget.TextView r0 = r10.j
            r7.processNumReview(r9, r0)
            r7.compareWidth(r10, r9)
            goto L_0x050d
        L_0x0437:
            com.autonavi.minimap.search.view.SearchSuggChildNodeManager r0 = r14.a
            java.util.List<com.autonavi.bundle.entity.sugg.TipItem> r1 = r9.tipItemList
            r0.setData(r1)
            boolean r0 = r7.hasTopListData(r9)
            if (r0 == 0) goto L_0x0451
            android.view.View r0 = r14.j
            r0.setVisibility(r11)
            android.view.View r0 = r14.j
            android.widget.TextView r1 = r14.k
            r7.processTopListData(r0, r1, r9)
            goto L_0x0456
        L_0x0451:
            android.view.View r0 = r14.j
            r0.setVisibility(r6)
        L_0x0456:
            android.widget.TextView r0 = r14.i
            r7.processDistance(r0, r9)
            android.widget.ImageView r1 = r14.h
            boolean r2 = r7.mShowRoute
            java.lang.String r4 = r7.mKeyWord
            android.widget.TextView r10 = r14.i
            r0 = r7
            r3 = r9
            r5 = r8
            r12 = 8
            r6 = r10
            r0.processRoute(r1, r2, r3, r4, r5, r6)
            android.widget.ImageView r0 = r14.g
            r7.processSuggLogo(r0, r9)
            android.widget.TextView r0 = r14.b
            java.lang.String r1 = r7.mKeyWord
            r7.processSugFont(r0, r9, r1)
            android.widget.TextView r0 = r14.d
            r7.processSameNameTip(r0, r9)
            android.widget.TextView r0 = r14.e
            android.view.View r1 = r14.f
            r7.processtvPoiTag(r0, r1, r9)
            r1 = 0
            r2 = 0
            android.widget.TextView r3 = r14.c
            android.view.View r4 = r14.f
            r0 = r7
            r5 = r9
            r0.processAddress(r1, r2, r3, r4, r5)
            goto L_0x04d2
        L_0x0490:
            r12 = 8
            android.widget.TextView r0 = r1.a
            java.lang.String r1 = r7.mKeyWord
            r0.setText(r1)
            goto L_0x04d2
        L_0x049a:
            r12 = 8
            android.widget.TextView r0 = r13.h
            r7.processDistance(r0, r9)
            android.widget.ImageView r1 = r13.g
            boolean r2 = r7.mShowRoute
            java.lang.String r4 = r7.mKeyWord
            android.widget.TextView r6 = r13.h
            r0 = r7
            r3 = r9
            r5 = r8
            r0.processRoute(r1, r2, r3, r4, r5, r6)
            android.widget.ImageView r0 = r13.f
            r7.processSuggLogo(r0, r9)
            android.widget.TextView r0 = r13.a
            java.lang.String r1 = r7.mKeyWord
            r7.processSugFont(r0, r9, r1)
            android.widget.TextView r0 = r13.c
            r7.processSameNameTip(r0, r9)
            android.widget.TextView r0 = r13.d
            android.view.View r1 = r13.e
            r7.processtvPoiTag(r0, r1, r9)
            r1 = 0
            r2 = 0
            android.widget.TextView r3 = r13.b
            android.view.View r4 = r13.e
            r0 = r7
            r5 = r9
            r0.processAddress(r1, r2, r3, r4, r5)
        L_0x04d2:
            r13 = 8
            goto L_0x050d
        L_0x04d5:
            r13 = 8
            android.widget.TextView r0 = r12.h
            r7.processDistance(r0, r9)
            android.widget.ImageView r1 = r12.g
            boolean r2 = r7.mShowRoute
            java.lang.String r4 = r7.mKeyWord
            android.widget.TextView r6 = r12.h
            r0 = r7
            r3 = r9
            r5 = r8
            r0.processRoute(r1, r2, r3, r4, r5, r6)
            android.widget.ImageView r0 = r12.f
            r7.processSuggLogo(r0, r9)
            android.widget.TextView r0 = r12.a
            java.lang.String r1 = r7.mKeyWord
            r7.processSugFont(r0, r9, r1)
            android.widget.TextView r0 = r12.c
            r7.processSameNameTip(r0, r9)
            android.widget.TextView r0 = r12.d
            android.view.View r1 = r12.e
            r7.processtvPoiTag(r0, r1, r9)
            r1 = 0
            r2 = 0
            android.widget.TextView r3 = r12.b
            android.view.View r4 = r12.e
            r0 = r7
            r5 = r9
            r0.processAddress(r1, r2, r3, r4, r5)
        L_0x050d:
            r15.clearFocus()
            com.autonavi.minimap.widget.SearchSuggestAdapter$4 r0 = new com.autonavi.minimap.widget.SearchSuggestAdapter$4
            r0.<init>(r9, r8)
            r15.setOnClickListener(r0)
            int r0 = com.autonavi.minimap.R.id.bottom_driver
            android.view.View r0 = r15.findViewById(r0)
            if (r0 == 0) goto L_0x052f
            int r1 = r19.getCount()
            int r1 = r1 + -1
            if (r8 != r1) goto L_0x052c
            r0.setVisibility(r13)
            goto L_0x052f
        L_0x052c:
            r0.setVisibility(r11)
        L_0x052f:
            return r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.SearchSuggestAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    /* access modifiers changed from: private */
    public void addLog(TipItem tipItem, int i) {
        String str;
        int i2 = tipItem.iconinfo;
        if (i2 != 4) {
            switch (i2) {
                case 0:
                    if (!TextUtils.isEmpty(tipItem.poiid)) {
                        str = "IDQ";
                        break;
                    } else {
                        str = "tquery";
                        break;
                    }
                case 1:
                    str = "busline";
                    break;
                default:
                    str = "others";
                    break;
            }
        } else {
            str = "tquery";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", str);
            jSONObject.put("itemId", i + 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2("P00004", "B043", jSONObject);
    }

    private void compareWidth(SuggTypeCommonHolder suggTypeCommonHolder, TipItem tipItem) {
        suggTypeCommonHolder.a.measure(0, 0);
        suggTypeCommonHolder.c.measure(0, 0);
        if (isMaxWidth(suggTypeCommonHolder, tipItem)) {
            suggTypeCommonHolder.a.getLayoutParams().width = getWidth(suggTypeCommonHolder) - suggTypeCommonHolder.c.getMeasuredWidth();
        }
    }

    private boolean isMaxWidth(SuggTypeCommonHolder suggTypeCommonHolder, TipItem tipItem) {
        return suggTypeCommonHolder.c.getVisibility() == 0 && tipItem.dataType == 1 && suggTypeCommonHolder.a.getMeasuredWidth() > getWidth(suggTypeCommonHolder) - suggTypeCommonHolder.c.getMeasuredWidth();
    }

    private int getWidth(SuggTypeCommonHolder suggTypeCommonHolder) {
        int width = ags.a(this.mContext).width();
        suggTypeCommonHolder.g.measure(0, 0);
        suggTypeCommonHolder.f.measure(0, 0);
        suggTypeCommonHolder.h.measure(0, 0);
        return (((width - suggTypeCommonHolder.f.getMeasuredWidth()) - suggTypeCommonHolder.g.getMeasuredWidth()) - suggTypeCommonHolder.h.getMeasuredWidth()) - agn.a(this.mContext, 39.0f);
    }

    private void processDistance(TextView textView, TipItem tipItem) {
        double d;
        if (tipItem != null && textView != null) {
            textView.setText("");
            if (tipItem.x >= 0.0d && tipItem.y >= 0.0d && hasLocationSuccess()) {
                try {
                    if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
                        d = (double) cfe.a(tipItem.x, tipItem.y, latestLocation.getLongitude(), latestLocation.getLatitude());
                    } else {
                        d = 0.0d;
                    }
                    if (d > 0.0d) {
                        if (this.FROM_PAGE != 10062) {
                            if (this.FROM_PAGE != 12400) {
                                textView.setVisibility(8);
                                return;
                            }
                        }
                        if (tipItem.type == 0) {
                            textView.setVisibility(8);
                            return;
                        }
                        textView.setText(cfe.a((int) d));
                        textView.setVisibility(0);
                        return;
                    }
                    textView.setVisibility(8);
                    return;
                } catch (Exception unused) {
                }
            }
            textView.setVisibility(8);
        }
    }

    private void processRoute(ImageView imageView, boolean z, final TipItem tipItem, final String str, final int i, TextView textView) {
        if (!z) {
            if (!TextUtils.isEmpty(tipItem.funcText)) {
                imageView.setVisibility(4);
                imageView.setOnClickListener(null);
                if (textView != null) {
                    textView.setText(tipItem.funcText);
                    textView.setVisibility(0);
                }
            } else if (getItemType(tipItem) == 1) {
                textView.setVisibility(8);
            } else if (getItemType(tipItem) == 0) {
                if (textView.getVisibility() == 0) {
                    imageView.setVisibility(4);
                } else {
                    imageView.setVisibility(0);
                }
                imageView.setTag(getMarkColorString(this.mContext, tipItem.name, str));
                imageView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (SearchSuggestAdapter.this.mOnItemEventListener != null) {
                            SearchSuggestAdapter.this.mOnItemEventListener.onAddClicked(tipItem, i);
                        }
                    }
                });
            }
        } else if (tipItem.x <= 0.0d || tipItem.y <= 0.0d) {
            imageView.setVisibility(4);
        } else {
            imageView.setBackgroundResource(R.drawable.search_home_item_right);
            imageView.setVisibility(0);
            imageView.setContentDescription(AMapAppGlobal.getApplication().getResources().getString(R.string.go_here));
            imageView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (SearchSuggestAdapter.this.mOnItemEventListener != null) {
                        SearchSuggestAdapter.this.mOnItemEventListener.onRouteClicked(tipItem);
                    }
                    tipItem.userInput = str;
                    tipItem.type = 0;
                    tipItem.time = new Date();
                    SearchHistoryHelper.getInstance(SearchSuggestAdapter.this.mContext).saveTipItem(tipItem);
                }
            });
        }
    }

    private void processSuggLogo(ImageView imageView, TipItem tipItem) {
        if (tipItem != null && imageView != null) {
            switch (tipItem.iconinfo) {
                case 0:
                    if (!TextUtils.isEmpty(tipItem.poiid)) {
                        imageView.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.default_generalsearch_sugg_tqueryicon_normal));
                        break;
                    } else {
                        imageView.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.default_generalsearch_sugg_searchicon_normal));
                        break;
                    }
                case 1:
                    imageView.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.xianlu));
                    break;
                case 2:
                    imageView.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.gongjiao));
                    break;
                case 3:
                    imageView.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.ditie));
                    break;
                case 4:
                    imageView.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.default_generalsearch_sugg_searchicon_normal));
                    break;
                default:
                    if (!TextUtils.isEmpty(tipItem.poiid) && tipItem.dataType != 1) {
                        imageView.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.default_generalsearch_sugg_tqueryicon_normal));
                        break;
                    } else {
                        imageView.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.default_generalsearch_sugg_searchicon_normal));
                        break;
                    }
                    break;
            }
            if (tipItem.dataType == 3) {
                imageView.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.default_generalsearch_sugg_tqueryicon_normal));
            }
            imageView.setVisibility(0);
        }
    }

    private void processSugFont(TextView textView, TipItem tipItem, String str) {
        if (tipItem != null && textView != null) {
            textView.setText(getMarkColorString(this.mContext, tipItem.name, str));
        }
    }

    private void processSameNameTip(TextView textView, TipItem tipItem) {
        if (tipItem != null && textView != null) {
            String str = tipItem.taginfo;
            if (getItemType(tipItem) != 0 || TextUtils.isEmpty(str)) {
                textView.setText("");
                textView.setTextColor(this.mContext.getResources().getColor(R.color.gray));
                textView.setVisibility(8);
            } else if (!TextUtils.isEmpty(str)) {
                if (tipItem.dataType == 1) {
                    textView.setBackgroundResource(R.drawable.busline_bg);
                    GradientDrawable gradientDrawable = (GradientDrawable) textView.getBackground();
                    gradientDrawable.setGradientType(0);
                    String str2 = "#f6712a";
                    if (tipItem.taginfo.contains(MetaRecord.LOG_SEPARATOR)) {
                        str2 = tipItem.taginfo.substring(tipItem.taginfo.indexOf(35), tipItem.taginfo.indexOf(35) + 7);
                    }
                    try {
                        gradientDrawable.setStroke(1, Color.parseColor(str2));
                    } catch (IllegalArgumentException unused) {
                        gradientDrawable.setStroke(1, Color.parseColor("#f6712a"));
                    }
                    textView.setPadding(10, 0, 10, 0);
                } else {
                    textView.setBackgroundResource(R.drawable.transparent);
                    textView.setPadding(0, 0, 0, 0);
                }
                textView.setText(Html.fromHtml(tipItem.taginfo));
                textView.setVisibility(0);
            } else {
                textView.setVisibility(8);
            }
        }
    }

    private void processtvPoiTag(TextView textView, View view, TipItem tipItem) {
        if (tipItem != null && textView != null) {
            if (!TextUtils.isEmpty(tipItem.poiTag)) {
                textView.setVisibility(0);
                if (view != null) {
                    view.setVisibility(0);
                }
                textView.setText(Html.fromHtml(tipItem.poiTag));
                return;
            }
            textView.setVisibility(8);
            textView.setText("");
            textView.setTextColor(this.mContext.getResources().getColor(R.color.f_c_3));
            if (view != null) {
                view.setVisibility(8);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:84:0x0173  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0178  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x017d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processAddress(com.autonavi.minimap.widget.SearchSuggestAdapter.SuggTypeCommonHolder r8, android.widget.LinearLayout r9, android.widget.TextView r10, android.view.View r11, com.autonavi.bundle.entity.sugg.TipItem r12) {
        /*
            r7 = this;
            if (r12 == 0) goto L_0x0181
            if (r10 != 0) goto L_0x0006
            goto L_0x0181
        L_0x0006:
            r0 = 8
            if (r8 == 0) goto L_0x0013
            android.widget.TextView r1 = r8.k
            if (r1 == 0) goto L_0x0013
            android.widget.TextView r1 = r8.k
            r1.setVisibility(r0)
        L_0x0013:
            android.content.Context r1 = r7.mContext
            android.content.res.Resources r1 = r1.getResources()
            int r2 = com.autonavi.minimap.R.color.f_c_3
            int r1 = r1.getColor(r2)
            r10.setTextColor(r1)
            java.lang.String r1 = r12.poiinfo
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            r2 = 0
            if (r1 != 0) goto L_0x0042
            r10.setVisibility(r2)
            int r8 = r12.poiinfoColor
            r10.setTextColor(r8)
            java.lang.String r8 = r12.poiinfo
            android.text.Spanned r8 = android.text.Html.fromHtml(r8)
            r10.setText(r8)
            if (r9 == 0) goto L_0x0180
            r9.setVisibility(r0)
            return
        L_0x0042:
            java.lang.String r1 = ""
            java.lang.String r3 = r12.terminals
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            r4 = 1
            if (r3 != 0) goto L_0x0051
            java.lang.String r1 = r12.terminals
        L_0x004f:
            r3 = 0
            goto L_0x0097
        L_0x0051:
            int r3 = r12.ignoreDistrict
            if (r4 != r3) goto L_0x006c
            java.lang.String r3 = r12.super_address
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0061
            java.lang.String r1 = r12.super_address
            r3 = 1
            goto L_0x0097
        L_0x0061:
            java.lang.String r3 = r12.addr
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x004f
            java.lang.String r1 = r12.addr
            goto L_0x004f
        L_0x006c:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r3 = r12.super_address
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x0080
            java.lang.String r3 = r12.super_address
            r1.append(r3)
            r3 = 1
            goto L_0x0093
        L_0x0080:
            java.lang.String r3 = r12.district
            if (r3 == 0) goto L_0x0089
            java.lang.String r3 = r12.district
            r1.append(r3)
        L_0x0089:
            java.lang.String r3 = r12.addr
            if (r3 == 0) goto L_0x0092
            java.lang.String r3 = r12.addr
            r1.append(r3)
        L_0x0092:
            r3 = 0
        L_0x0093:
            java.lang.String r1 = r1.toString()
        L_0x0097:
            if (r1 == 0) goto L_0x0171
            java.lang.String r12 = r12.name
            boolean r12 = r1.equals(r12)
            if (r12 != 0) goto L_0x0171
            java.lang.String r12 = ""
            java.lang.String r5 = r1.trim()
            boolean r12 = r12.equals(r5)
            if (r12 != 0) goto L_0x0171
            java.lang.String r11 = "\\|"
            java.lang.String[] r11 = r1.split(r11)
            int r11 = r11.length
            if (r11 <= r4) goto L_0x0163
            if (r3 == 0) goto L_0x0163
            java.lang.String r11 = ";"
            java.lang.String[] r11 = r1.split(r11)
            int r12 = r11.length
            if (r12 <= 0) goto L_0x0162
            if (r11 == 0) goto L_0x0162
            if (r9 == 0) goto L_0x0162
            if (r8 == 0) goto L_0x0162
            r9.setVisibility(r2)
            r10.setVisibility(r0)
            android.widget.LinearLayout$LayoutParams r9 = new android.widget.LinearLayout$LayoutParams
            r10 = -2
            r9.<init>(r10, r10)
            r9.setMargins(r2, r2, r0, r2)
            int r9 = r11.length
            com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock[] r9 = new com.autonavi.minimap.widget.SearchListColorBlockView.ColorBlock[r9]
            r10 = -9079435(0xffffffffff757575, float:-3.2627073E38)
            r12 = 0
            r0 = -9079435(0xffffffffff757575, float:-3.2627073E38)
        L_0x00e0:
            int r1 = r11.length
            if (r12 >= r1) goto L_0x0150
            r1 = r11[r12]
            java.lang.String r3 = "\\|"
            java.lang.String[] r1 = r1.split(r3)
            int r3 = r1.length
            if (r3 == 0) goto L_0x014d
            com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock r3 = new com.autonavi.minimap.widget.SearchListColorBlockView$ColorBlock
            r3.<init>()
            int r5 = r1.length
            if (r5 <= r4) goto L_0x013d
            r5 = r1[r2]
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x014b
            r5 = r1[r4]
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x014b
            r5 = r1[r2]
            r3.mText = r5
            int r5 = r11.length
            int r5 = r5 - r4
            if (r12 != r5) goto L_0x0126
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r5 = "#"
            r0.<init>(r5)
            r5 = r1[r4]
            r0.append(r5)
            java.lang.String r0 = r0.toString()
            int r0 = android.graphics.Color.parseColor(r0)     // Catch:{ IllegalArgumentException -> 0x0123 }
            goto L_0x0126
        L_0x0123:
            r0 = -9079435(0xffffffffff757575, float:-3.2627073E38)
        L_0x0126:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IllegalArgumentException -> 0x0149 }
            java.lang.String r6 = "#"
            r5.<init>(r6)     // Catch:{ IllegalArgumentException -> 0x0149 }
            r1 = r1[r4]     // Catch:{ IllegalArgumentException -> 0x0149 }
            r5.append(r1)     // Catch:{ IllegalArgumentException -> 0x0149 }
            java.lang.String r1 = r5.toString()     // Catch:{ IllegalArgumentException -> 0x0149 }
            int r1 = android.graphics.Color.parseColor(r1)     // Catch:{ IllegalArgumentException -> 0x0149 }
            r3.mColor = r1     // Catch:{ IllegalArgumentException -> 0x0149 }
            goto L_0x014b
        L_0x013d:
            r5 = r1[r2]
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x0149
            r1 = r1[r2]
            r3.mText = r1
        L_0x0149:
            r3.mColor = r10
        L_0x014b:
            r9[r12] = r3
        L_0x014d:
            int r12 = r12 + 1
            goto L_0x00e0
        L_0x0150:
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r10 = new com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo
            r10.<init>()
            com.autonavi.minimap.widget.SearchListColorBlockView r8 = r8.o
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r9 = r10.setBlocks(r9)
            com.autonavi.minimap.widget.SearchListColorBlockView$ItemInfo r9 = r9.setBlockColor(r0)
            r8.setItemInfo(r9)
        L_0x0162:
            return
        L_0x0163:
            if (r9 == 0) goto L_0x0168
            r9.setVisibility(r0)
        L_0x0168:
            if (r10 == 0) goto L_0x0180
            r10.setVisibility(r2)
            r10.setText(r1)
            return
        L_0x0171:
            if (r9 == 0) goto L_0x0176
            r9.setVisibility(r0)
        L_0x0176:
            if (r10 == 0) goto L_0x017b
            r10.setVisibility(r0)
        L_0x017b:
            if (r11 == 0) goto L_0x0180
            r11.setVisibility(r0)
        L_0x0180:
            return
        L_0x0181:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.SearchSuggestAdapter.processAddress(com.autonavi.minimap.widget.SearchSuggestAdapter$SuggTypeCommonHolder, android.widget.LinearLayout, android.widget.TextView, android.view.View, com.autonavi.bundle.entity.sugg.TipItem):void");
    }

    private void processRatingBar(TipItem tipItem, RatingBar ratingBar) {
        if (tipItem != null && ratingBar != null) {
            String str = tipItem.richRating;
            ratingBar.setRating(0.0f);
            if (str == null || str.isEmpty()) {
                ratingBar.setVisibility(8);
                return;
            }
            float floatValue = Float.valueOf(str).floatValue();
            if (floatValue > 0.0f) {
                ratingBar.setVisibility(0);
                ratingBar.setRating(floatValue);
            }
        }
    }

    private void processNumReview(TipItem tipItem, TextView textView) {
        if (tipItem != null && textView != null) {
            String str = tipItem.numReview;
            textView.setText("");
            textView.setTextColor(this.mContext.getResources().getColor(R.color.f_c_2));
            if (str == null || str.isEmpty()) {
                textView.setVisibility(8);
                textView.setText("");
                return;
            }
            textView.setVisibility(0);
            textView.setText(Html.fromHtml(str));
        }
    }

    private void processTopListData(View view, TextView textView, final TipItem tipItem) {
        if (tipItem != null) {
            textView.setText(tipItem.getTopListName());
            view.setOnClickListener(new OneClickListener() {
                public void doClick(View view) {
                    if (SearchSuggestAdapter.this.mTopListSchemaCallback != null && !TextUtils.isEmpty(tipItem.getTopListUrl())) {
                        Uri parse = Uri.parse(tipItem.getTopListUrl());
                        if (parse != null) {
                            Intent intent = new Intent();
                            intent.setData(parse);
                            intent.putExtra("owner", BaseIntentDispatcher.INTENT_CALL_FROMOWNER);
                            SearchSuggestAdapter.this.mTopListSchemaCallback.start(intent);
                        }
                    }
                }
            });
        }
    }

    public static boolean isChildNodeIconType(List<TipItem> list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        TipItem tipItem = list.get(0);
        if (tipItem == null || TextUtils.isEmpty(tipItem.getChildNodeIconUrl())) {
            return false;
        }
        clearDisableChildIconData(list);
        if (list.size() >= 3) {
            return true;
        }
        return false;
    }

    private static void clearDisableChildIconData(List<TipItem> list) {
        Iterator<TipItem> it = list.iterator();
        while (it.hasNext()) {
            TipItem next = it.next();
            if (next != null && (TextUtils.isEmpty(next.getChildNodeIconUrl()) || TextUtils.isEmpty(next.getChildNodeName()) || next.getChildNodeName().length() > 3)) {
                it.remove();
            }
        }
    }

    private boolean hasTopListData(TipItem tipItem) {
        return tipItem != null && !TextUtils.isEmpty(tipItem.getTopListName()) && !TextUtils.isEmpty(tipItem.getTopListUrl());
    }

    public void onChildItemClicked(TipItem tipItem, int i, boolean z) {
        if (this.mOnItemEventListener != null) {
            this.mOnItemEventListener.onItemClicked(tipItem, i, z);
        }
    }

    private static boolean hasLocationSuccess() {
        return LocationInstrument.getInstance().getLatestPosition(5) != null;
    }
}
