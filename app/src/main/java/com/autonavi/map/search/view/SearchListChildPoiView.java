package com.autonavi.map.search.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchservice.utils.PoiArrayTemplate;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.minimap.search.utils.SearchUtils;
import com.autonavi.minimap.widget.EllipsizedChildPoiView;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class SearchListChildPoiView extends LinearLayout {
    private ImageView mArrowImageView;
    /* access modifiers changed from: private */
    public List<POI> mCHildPoiList;
    private auw mChildConfig;
    private int mDefaultCount;
    /* access modifiers changed from: private */
    public boolean mExpanded = false;
    /* access modifiers changed from: private */
    public InfoliteResult mInfoliteResult;
    /* access modifiers changed from: private */
    public b mItemClickListener;
    private int mMaxCount;
    /* access modifiers changed from: private */
    public SearchPoi mPoi;
    private LinearLayout mPoiChildContainer;

    static class a {
        public String a;
        public String b;
        public int c;
        public boolean d;

        public a(String str, String str2, int i, boolean z) {
            this.a = str;
            this.b = str2;
            this.c = i;
            this.d = z;
        }
    }

    public interface b {
    }

    public SearchListChildPoiView(Context context) {
        super(context);
        init();
    }

    public SearchListChildPoiView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SearchListChildPoiView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setOrientation(0);
        LayoutInflater.from(getContext()).inflate(R.layout.view_poi_child_list, this, true);
        this.mPoiChildContainer = (LinearLayout) findViewById(R.id.layout_child_poi);
        this.mArrowImageView = (ImageView) findViewById(R.id.image_arrow);
    }

    public void setOnItemClickListener(b bVar) {
        this.mItemClickListener = bVar;
    }

    public void setOnArrowViewClickListener(final OnClickListener onClickListener) {
        if (onClickListener != null) {
            this.mArrowImageView.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onClick(view);
                        LogManager.actionLogV25("P00360", SearchListChildPoiView.this.mExpanded ? "B007" : "B008", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, SearchListChildPoiView.this.mPoi.getId()), new SimpleEntry("gsid", bcy.l(SearchListChildPoiView.this.mInfoliteResult)));
                    }
                }
            });
        } else {
            this.mArrowImageView.setClickable(false);
        }
    }

    public void setPoiData(InfoliteResult infoliteResult, SearchPoi searchPoi, List<POI> list, auw auw) {
        if (list != null && list.size() != 0) {
            this.mInfoliteResult = infoliteResult;
            this.mPoi = searchPoi;
            this.mCHildPoiList = list;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (POI next : list) {
                if (isSubwayCategory(searchPoi)) {
                    arrayList.add(((ISearchPoiData) next.as(ISearchPoiData.class)).getName());
                } else {
                    arrayList.add(((ISearchPoiData) next.as(ISearchPoiData.class)).getShortName());
                }
                arrayList2.add(((SearchPoi) next.as(SearchPoi.class)).getLabel() == null ? "" : ((SearchPoi) next.as(SearchPoi.class)).getLabel());
            }
            if (arrayList.size() != 0 && auw != null) {
                this.mChildConfig = auw;
                this.mChildConfig.a = this.mChildConfig.a == 0 ? 3 : this.mChildConfig.a;
                int i = 1;
                this.mChildConfig.c = this.mChildConfig.c == 0 ? 1 : this.mChildConfig.c;
                auw auw2 = this.mChildConfig;
                if (this.mChildConfig.b != 0) {
                    i = this.mChildConfig.b;
                } else if (arrayList.size() > 3) {
                    i = 2;
                }
                auw2.b = i;
                if (auw.b > 4) {
                    this.mChildConfig.b = 4;
                }
                if (auw.c > auw.b) {
                    this.mChildConfig.c = auw.b;
                }
                if (auw.a == 2 || auw.a == 3) {
                    this.mPoiChildContainer.removeAllViews();
                    this.mPoiChildContainer.setVisibility(0);
                    computeCount(arrayList);
                    buildCHildPoiView(arrayList, arrayList2);
                    initArrowImage();
                    doLog();
                }
            }
        }
    }

    private void initArrowImage() {
        if (this.mPoiChildContainer.getChildCount() <= 1) {
            this.mArrowImageView.setVisibility(8);
            this.mExpanded = true;
            return;
        }
        if (this.mDefaultCount < this.mMaxCount) {
            this.mExpanded = false;
        } else {
            this.mExpanded = true;
        }
        this.mArrowImageView.setImageResource(this.mExpanded ? R.drawable.table_arrow_down : R.drawable.table_arrow_up);
        this.mArrowImageView.setVisibility(0);
        setRightMargin(this.mPoiChildContainer, 0);
    }

    private void computeCount(List<String> list) {
        int i;
        if (list != null && list.size() != 0 && this.mChildConfig != null && this.mChildConfig.a > 0 && list != null && list.size() > 0) {
            auw auw = this.mChildConfig;
            if ((list.size() % auw.a == 0 ? list.size() / auw.a : (list.size() / auw.a) + 1) > auw.b) {
                this.mMaxCount = auw.a * auw.b;
                i = auw.a * auw.c;
            } else {
                this.mMaxCount = list.size();
                i = list.size() > auw.c * auw.a ? auw.c * auw.a : list.size();
            }
            this.mDefaultCount = i;
        }
    }

    private boolean canExpand() {
        return this.mPoiChildContainer.getChildCount() > 1;
    }

    public boolean isExpanded() {
        return this.mExpanded;
    }

    public void collapse() {
        if (canExpand() && this.mExpanded) {
            int i = 0;
            while (i < this.mPoiChildContainer.getChildCount()) {
                this.mPoiChildContainer.getChildAt(i).setVisibility(i == 0 ? 0 : 8);
                i++;
            }
            this.mArrowImageView.setImageResource(R.drawable.table_arrow_up);
            this.mExpanded = false;
        }
    }

    public void expand() {
        if (canExpand() && !this.mExpanded) {
            for (int i = 0; i < this.mPoiChildContainer.getChildCount(); i++) {
                this.mPoiChildContainer.getChildAt(i).setVisibility(0);
            }
            this.mArrowImageView.setImageResource(R.drawable.table_arrow_down);
            this.mExpanded = true;
        }
    }

    private void setRightMargin(View view, int i) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.rightMargin = i;
        view.setLayoutParams(marginLayoutParams);
    }

    private void buildCHildPoiView(List<String> list, List<String> list2) {
        int i = this.mMaxCount;
        ArrayList arrayList = new ArrayList(this.mChildConfig.a);
        int i2 = 0;
        while (i2 < i) {
            arrayList.add(new a(list.get(i2), list2.get(i2), i2, i2 < this.mDefaultCount));
            if (arrayList.size() >= this.mChildConfig.a) {
                addPoiChildView(arrayList);
                arrayList.clear();
            }
            i2++;
        }
        addPoiChildView(arrayList);
    }

    private void addPoiChildView(List<a> list) {
        if (list != null && list.size() != 0) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.view_poi_child_item, this.mPoiChildContainer, false);
            EllipsizedChildPoiView[] ellipsizedChildPoiViewArr = {(EllipsizedChildPoiView) inflate.findViewById(R.id.item1), (EllipsizedChildPoiView) inflate.findViewById(R.id.item2), (EllipsizedChildPoiView) inflate.findViewById(R.id.item3)};
            View findViewById = inflate.findViewById(R.id.split_vertical_line_1);
            View findViewById2 = inflate.findViewById(R.id.split_vertical_line_2);
            ellipsizedChildPoiViewArr[0].setVisibility(8);
            ellipsizedChildPoiViewArr[1].setVisibility(8);
            ellipsizedChildPoiViewArr[2].setVisibility(8);
            LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.poi_parent);
            if (list.size() <= 3) {
                for (int i = 0; i < list.size(); i++) {
                    ellipsizedChildPoiViewArr[i].setData(list.get(i).a, list.get(i).b, false);
                    ellipsizedChildPoiViewArr[i].setVisibility(list.get(i).d ? 0 : 8);
                    ellipsizedChildPoiViewArr[i].setOnClickListener(createItemClickListener(list.get(i)));
                }
                if (ellipsizedChildPoiViewArr[0].getVisibility() == 8 && ellipsizedChildPoiViewArr[1].getVisibility() == 8 && ellipsizedChildPoiViewArr[2].getVisibility() == 8) {
                    if (this.mChildConfig.a == 3) {
                        ellipsizedChildPoiViewArr[0].setVisibility(0);
                        ellipsizedChildPoiViewArr[1].setVisibility(0);
                        ellipsizedChildPoiViewArr[2].setVisibility(0);
                        inflate.setVisibility(8);
                    } else if (this.mChildConfig.a == 2) {
                        ellipsizedChildPoiViewArr[0].setVisibility(0);
                        ellipsizedChildPoiViewArr[1].setVisibility(0);
                        ellipsizedChildPoiViewArr[2].setVisibility(8);
                        inflate.setVisibility(8);
                    }
                }
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) inflate.getLayoutParams();
                marginLayoutParams.topMargin = this.mPoiChildContainer.getChildCount() == 0 ? 0 : agn.a(getContext(), 7.0f);
                inflate.setLayoutParams(marginLayoutParams);
                this.mPoiChildContainer.addView(inflate);
                linearLayout.measure(0, 0);
                linearLayout.getMeasuredWidth();
                linearLayout.getMeasuredHeight();
                WindowManager windowManager = (WindowManager) getContext().getSystemService(TemplateTinyApp.WINDOW_KEY);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                if (windowManager != null) {
                    windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                }
                int i2 = displayMetrics.widthPixels;
                int i3 = displayMetrics.heightPixels;
                int i4 = (int) (((float) i2) - (displayMetrics.density * 64.0f));
                if (this.mChildConfig.a != 3) {
                    if (this.mChildConfig.a == 2) {
                        if (list.size() == 1) {
                            findViewById.setVisibility(8);
                            findViewById2.setVisibility(8);
                            ellipsizedChildPoiViewArr[1].setVisibility(8);
                            ellipsizedChildPoiViewArr[2].setVisibility(8);
                            LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
                            layoutParams.width = i4 / 2;
                            linearLayout.setLayoutParams(layoutParams);
                        } else if (list.size() == 2) {
                            findViewById.setVisibility(0);
                            findViewById2.setVisibility(8);
                            ellipsizedChildPoiViewArr[2].setVisibility(8);
                        }
                    }
                } else if (list.size() == 1) {
                    ellipsizedChildPoiViewArr[1].setVisibility(8);
                    ellipsizedChildPoiViewArr[2].setVisibility(8);
                    findViewById.setVisibility(8);
                    findViewById2.setVisibility(8);
                    LayoutParams layoutParams2 = (LayoutParams) linearLayout.getLayoutParams();
                    layoutParams2.width = i4 / 3;
                    linearLayout.setLayoutParams(layoutParams2);
                } else if (list.size() == 2) {
                    findViewById.setVisibility(0);
                    findViewById2.setVisibility(8);
                    ellipsizedChildPoiViewArr[2].setVisibility(8);
                    LayoutParams layoutParams3 = (LayoutParams) linearLayout.getLayoutParams();
                    layoutParams3.width = (i4 / 3) * 2;
                    linearLayout.setLayoutParams(layoutParams3);
                } else {
                    findViewById.setVisibility(0);
                    findViewById2.setVisibility(0);
                }
            }
        }
    }

    private OnClickListener createItemClickListener(final a aVar) {
        return new OnClickListener() {
            public final void onClick(View view) {
                if (SearchListChildPoiView.this.mItemClickListener != null) {
                    SearchListChildPoiView.this.mItemClickListener;
                    LogManager.actionLogV25("P00360", "B005", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, SearchListChildPoiView.this.mPoi.getId()), new SimpleEntry("sudpoiid", ((POI) SearchListChildPoiView.this.mCHildPoiList.get(aVar.c)).getId()), new SimpleEntry("gsid", bcy.l(SearchListChildPoiView.this.mInfoliteResult)), new SimpleEntry(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, ((SearchPoi) ((POI) SearchListChildPoiView.this.mCHildPoiList.get(aVar.c)).as(SearchPoi.class)).getSubType()), new SimpleEntry("typecode", SearchListChildPoiView.this.mPoi.getType()), new SimpleEntry("itemid", Integer.valueOf(aVar.c + 1)), new SimpleEntry("text", aVar.b));
                }
            }
        };
    }

    private static boolean isSubwayCategory(SearchPoi searchPoi) {
        return SearchUtils.checkCategory(searchPoi.getType(), null, new String[]{"150500"});
    }

    private void doLog() {
        if (this.mPoi != null && this.mPoi.getTemplateDataMap() != null && this.mPoi.getTemplateDataMap().containsKey(Integer.valueOf(SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH))) {
            PoiLayoutTemplate poiLayoutTemplate = (PoiLayoutTemplate) this.mPoi.getTemplateDataMap().get(Integer.valueOf(SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH));
            if (poiLayoutTemplate instanceof PoiArrayTemplate) {
                Entry[] entryArr = new Entry[7];
                entryArr[0] = new SimpleEntry("type", this.mExpanded ? "2" : "1");
                entryArr[1] = new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, this.mPoi.getId());
                entryArr[2] = new SimpleEntry("typecode", this.mPoi.getType());
                PoiArrayTemplate poiArrayTemplate = (PoiArrayTemplate) poiLayoutTemplate;
                entryArr[3] = new SimpleEntry("sudpoiid", poiArrayTemplate.getPoiids());
                entryArr[4] = new SimpleEntry(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, poiArrayTemplate.getChildType());
                entryArr[5] = new SimpleEntry("gsid", bcy.l(this.mInfoliteResult));
                entryArr[6] = new SimpleEntry("text", poiArrayTemplate.getLabel());
                LogManager.actionLogV25("P00360", "B006", entryArr);
            }
        }
    }
}
