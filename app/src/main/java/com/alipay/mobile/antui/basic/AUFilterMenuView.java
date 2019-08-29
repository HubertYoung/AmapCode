package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.filter.AUFilterTabContainerView;
import com.alipay.mobile.antui.filter.IFilterListener;
import com.alipay.mobile.antui.model.FilterCategoryData;
import com.alipay.mobile.antui.model.FilterItemData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AUFilterMenuView extends RelativeLayout {
    public static String TAG = "FilterMenuView";
    private TextView confirm;
    private View confirmLayout;
    private FilterCategoryData filterCategoryData;
    private Map<String, String> filterMap = new HashMap();
    /* access modifiers changed from: private */
    public boolean isConfirmVisible;
    private boolean isGroupNameVisible;
    /* access modifiers changed from: private */
    public boolean isOneGroup;
    private boolean isSplitterViewVisible;
    private IFilterListener listener;
    private Context mContext;
    private TextView reset;
    private List<AUFilterTabContainerView> tabContainerList;
    private FilterCategoryData tmp;

    public boolean isSplitterViewVisible() {
        return this.isSplitterViewVisible;
    }

    public void setSplitterViewVisible(boolean splitterViewVisible) {
        this.isSplitterViewVisible = splitterViewVisible;
    }

    public boolean isOneGroup() {
        return this.isOneGroup;
    }

    public void setIsOneGroup(boolean isOneGroup2) {
        this.isOneGroup = isOneGroup2;
    }

    public AUFilterMenuView(Context context) {
        super(context);
        this.mContext = context;
    }

    public AUFilterMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public AUFilterMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void initData(IFilterListener listener2, FilterCategoryData filterCategory, FilterCategoryData tmpCatData) {
        this.filterMap = new HashMap();
        this.listener = listener2;
        this.tmp = tmpCatData;
        this.filterCategoryData = filterCategory;
        removeAllViews();
        updateMenusFilte(filterCategory);
    }

    public void setConfirmVisibe(boolean isConfirmVisible2) {
        this.isConfirmVisible = isConfirmVisible2;
    }

    public void setGroupNameVisible(boolean isGroupNameVisible2) {
        this.isGroupNameVisible = isGroupNameVisible2;
    }

    public boolean getCOnfirmVisible() {
        return this.isConfirmVisible;
    }

    public boolean getGroupNameVisible() {
        return this.isGroupNameVisible;
    }

    private void updateMenusFilte(FilterCategoryData data) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.view_category_filter, null);
        LinearLayout filterContainer = (LinearLayout) view.findViewById(R.id.filter_menu_container);
        this.confirm = (TextView) view.findViewById(R.id.confirm);
        this.reset = (TextView) view.findViewById(R.id.reset);
        this.confirmLayout = view.findViewById(R.id.confirm_layout);
        Drawable confirmDrawable = this.mContext.getResources().getDrawable(R.drawable.search_menu_button);
        Drawable resetDrawable = this.mContext.getResources().getDrawable(R.drawable.reset_menu_button);
        this.confirm.setBackgroundDrawable(confirmDrawable);
        this.reset.setBackgroundDrawable(resetDrawable);
        TextView textView = this.confirm;
        g gVar = new g(this);
        textView.setOnClickListener(gVar);
        TextView textView2 = this.reset;
        h hVar = new h(this);
        textView2.setOnClickListener(hVar);
        if (data.itemDatas != null && data.itemDatas.size() > 0) {
            int size = data.itemDatas.size();
            this.tabContainerList = new ArrayList();
            for (int i = 0; i < size; i++) {
                View v = LayoutInflater.from(this.mContext).inflate(R.layout.view_category_filte_item, this, false);
                TextView groupTextView = (TextView) v.findViewById(R.id.groupname);
                AUFilterTabContainerView tabContainer = (AUFilterTabContainerView) v.findViewById(R.id.filterSubMenuTabContainer);
                tabContainer.setTag(Integer.valueOf(i));
                tabContainer.setmMargin(this.mContext.getResources().getDimensionPixelSize(R.dimen.filer_15));
                groupTextView.setText(data.itemDatas.get(i).name);
                List items = data.itemDatas.get(i).subItemData;
                View splitterView = v.findViewById(R.id.filter_splitter);
                FilterCategoryData filterCategoryData2 = data;
                i iVar = new i(this, filterCategoryData2, i, tabContainer);
                tabContainer.setOnTagClickListener(iVar);
                tabContainer.setDatas(items);
                filterContainer.addView(v);
                if (tabContainer != null) {
                    this.tabContainerList.add(tabContainer);
                }
                if (!this.isGroupNameVisible) {
                    groupTextView.setVisibility(8);
                }
                if (!this.isSplitterViewVisible) {
                    splitterView.setVisibility(0);
                    if (i != size - 1) {
                    }
                }
                splitterView.setVisibility(4);
            }
        }
        addView(view);
        if (!this.isConfirmVisible) {
            this.reset.setVisibility(8);
            this.confirm.setVisibility(8);
            this.confirmLayout.setVisibility(8);
            return;
        }
        this.reset.setVisibility(0);
        this.confirm.setVisibility(0);
        this.confirmLayout.setVisibility(0);
    }

    /* access modifiers changed from: private */
    public void clearSelect() {
        for (AUFilterTabContainerView clearSelect : this.tabContainerList) {
            clearSelect.clearSelect();
        }
        setViewModel(this.tmp);
    }

    /* access modifiers changed from: private */
    public void updateData() {
        if (!(this.tabContainerList == null || this.tabContainerList.size() == 0)) {
            for (AUFilterTabContainerView updateSelectDatas : this.tabContainerList) {
                updateSelectDatas.updateSelectDatas();
            }
        }
        if (this.filterCategoryData != null) {
            ArrayList<FilterItemData> arrayList = this.filterCategoryData.itemDatas;
            this.filterCategoryData.selectItems = arrayList;
            if (!(arrayList == null || arrayList.size() == 0)) {
                for (FilterItemData filterItemData : arrayList) {
                    String key = filterItemData.key;
                    String val = "";
                    List list = new ArrayList();
                    List subFilterDatas = filterItemData.subItemData;
                    if (!(subFilterDatas == null || subFilterDatas.size() == 0)) {
                        for (FilterItemData sub : subFilterDatas) {
                            if (sub.isSelect) {
                                list.add(sub.code);
                            }
                        }
                        val = listToString2(list, ',');
                    }
                    this.filterMap.put(key, val);
                }
            }
        }
        if (this.listener != null) {
            this.listener.onFilterSelected(this.filterCategoryData, this.filterMap, true, true);
        }
    }

    public String listToString2(List list, char separator) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    private void setViewModel(FilterCategoryData categoryData) {
        if (categoryData != null) {
            this.filterCategoryData.code = categoryData.code;
            this.filterCategoryData.name = categoryData.name;
            this.filterCategoryData.groupId = categoryData.groupId;
            List<FilterItemData> datas = categoryData.itemDatas;
            List itemDatas = new ArrayList();
            if (!(datas == null || datas.size() == 0)) {
                for (FilterItemData item : datas) {
                    itemDatas.add(getFromFilterModel(item));
                }
            }
            this.filterCategoryData.itemDatas = (ArrayList) itemDatas;
        }
    }

    public FilterItemData getFromFilterModel(FilterItemData filterItemModel) {
        FilterItemData filterItemData = new FilterItemData();
        filterItemData.name = filterItemModel.name;
        filterItemData.code = filterItemModel.code;
        filterItemData.isSelect = filterItemModel.isSelect;
        filterItemData.count = filterItemModel.count;
        filterItemData.key = filterItemModel.key;
        filterItemData.allowMultipleSelect = filterItemModel.allowMultipleSelect;
        List filterItemDatas = new ArrayList();
        List filterItemModelList = filterItemModel.subItemData;
        if (!(filterItemModelList == null || filterItemModelList.size() == 0)) {
            for (FilterItemData itemModel : filterItemModelList) {
                FilterItemData itemData = new FilterItemData();
                itemData.isSelect = false;
                itemData.code = itemModel.code;
                itemData.count = itemModel.count;
                itemData.name = itemModel.name;
                itemData.key = itemModel.key;
                itemData.allowMultipleSelect = itemModel.allowMultipleSelect;
                filterItemDatas.add(itemData);
            }
        }
        filterItemData.subItemData = (ArrayList) filterItemDatas;
        return filterItemData;
    }
}
