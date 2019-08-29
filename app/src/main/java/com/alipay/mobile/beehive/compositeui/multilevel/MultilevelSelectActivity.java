package com.alipay.mobile.beehive.compositeui.multilevel;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUListView;
import com.alipay.mobile.antui.basic.AUTitleBar;
import com.alipay.mobile.antui.model.ItemCategory;
import com.alipay.mobile.antui.segement.AUSegment;
import com.alipay.mobile.antui.segement.AUSegment.TabSwitchListener;
import com.alipay.mobile.beehive.api.BeehiveConstant;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;

@EActivity(resName = "activity_multilevel_select")
public class MultilevelSelectActivity extends BaseActivity {
    public static volatile MultilevelSelectCallBack multilevelSelectCallBack;
    /* access modifiers changed from: private */
    public JSONArray allItemList;
    /* access modifiers changed from: private */
    public JSONArray currentItemList;
    /* access modifiers changed from: private */
    public String defaultSegmentName;
    @ViewById(resName = "container")
    protected AULinearLayout mContainer;
    /* access modifiers changed from: private */
    public int mCurrentSelTab;
    @ViewById(resName = "listview")
    protected AUListView mListView;
    @ViewById(resName = "segment")
    protected AUSegment mSegemnt;
    @ViewById(resName = "title_bar")
    protected AUTitleBar mTitleBar;
    /* access modifiers changed from: private */
    public JSONArray selectItemList = new JSONArray();
    private String title;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        if (VERSION.SDK_INT != 26) {
            setRequestedOrientation(1);
        }
        super.onCreate(savedInstanceState);
    }

    /* access modifiers changed from: protected */
    @AfterViews
    public void init() {
        initParam();
        if (this.allItemList == null || this.currentItemList == null) {
            cancel();
            return;
        }
        this.mTitleBar.toIOSStyle(this.title);
        this.mTitleBar.setBackButtonGone();
        this.mTitleBar.getLeftButton().setOnClickListener(new OnClickListener() {
            public final void onClick(View v) {
                MultilevelSelectActivity.this.cancel();
            }
        });
        List categoryList = new ArrayList();
        if (!this.selectItemList.isEmpty()) {
            int size = this.selectItemList.size();
            for (int i = 0; i < size; i++) {
                categoryList.add(getItemCategory(i, this.selectItemList.getJSONObject(i).getString("name")));
            }
            JSONArray subList = this.selectItemList.getJSONObject(this.selectItemList.size() - 1).getJSONArray("subList");
            if (subList != null && !subList.isEmpty()) {
                categoryList.add(getItemCategory(this.selectItemList.size(), this.defaultSegmentName));
            }
        } else {
            categoryList.add(getItemCategory(0, this.defaultSegmentName));
        }
        this.mSegemnt.setDivideAutoSize(false);
        this.mSegemnt.init(categoryList);
        this.mListView.setAdapter(new MultilevelSelectAdapter(this.currentItemList, this));
        this.mListView.setOnItemClickListener(new OnItemClickListener() {
            public final void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject item = MultilevelSelectActivity.this.currentItemList.getJSONObject(position);
                while (MultilevelSelectActivity.this.selectItemList.size() > MultilevelSelectActivity.this.mCurrentSelTab) {
                    MultilevelSelectActivity.this.selectItemList.remove(MultilevelSelectActivity.this.selectItemList.size() - 1);
                }
                MultilevelSelectActivity.this.selectItemList.add(item);
                JSONArray subList = item.getJSONArray("subList");
                if (subList == null || subList.isEmpty()) {
                    if (MultilevelSelectActivity.multilevelSelectCallBack != null) {
                        MultilevelSelectActivity.multilevelSelectCallBack.onSuccess(MultilevelSelectActivity.this.selectItemList);
                    }
                    MultilevelSelectActivity.this.finish();
                    return;
                }
                List categoryList = new ArrayList();
                for (int i = 0; i < MultilevelSelectActivity.this.selectItemList.size(); i++) {
                    categoryList.add(MultilevelSelectActivity.this.getItemCategory(i, MultilevelSelectActivity.this.selectItemList.getJSONObject(i).getString("name")));
                }
                categoryList.add(MultilevelSelectActivity.this.getItemCategory(MultilevelSelectActivity.this.selectItemList.size(), MultilevelSelectActivity.this.defaultSegmentName));
                MultilevelSelectActivity.this.mSegemnt.init(categoryList);
                MultilevelSelectActivity.this.setSegmentIndex(categoryList.size() - 1, subList);
            }
        });
        if (this.selectItemList.isEmpty()) {
            return;
        }
        if (this.selectItemList.size() > 1) {
            JSONArray subList2 = this.selectItemList.getJSONObject(this.selectItemList.size() - 2).getJSONArray("subList");
            if (subList2 != null && !subList2.isEmpty()) {
                categoryList.add(getItemCategory(this.selectItemList.size(), this.defaultSegmentName));
            }
            setSegmentIndex(this.selectItemList.size() - 1, subList2);
            return;
        }
        setSegmentIndex(0, this.allItemList);
    }

    /* access modifiers changed from: private */
    public void setSegmentIndex(int index, JSONArray subList) {
        this.mCurrentSelTab = index;
        this.mSegemnt.setCurrentSelTab(index);
        this.mListView.setAdapter(new MultilevelSelectAdapter(subList, this));
        this.currentItemList = subList;
        this.mSegemnt.setTabSwitchListener(new TabSwitchListener() {
            public final void onTabClick(int i, View view) {
                if (i > 0) {
                    MultilevelSelectActivity.this.setSegmentIndex(i, MultilevelSelectActivity.this.selectItemList.getJSONObject(i - 1).getJSONArray("subList"));
                    return;
                }
                MultilevelSelectActivity.this.setSegmentIndex(i, MultilevelSelectActivity.this.allItemList);
            }
        });
    }

    /* access modifiers changed from: private */
    @NonNull
    public ItemCategory getItemCategory(int index, String name) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.categoryname = name;
        itemCategory.categoryId = index + name;
        return itemCategory;
    }

    /* access modifiers changed from: private */
    public void cancel() {
        finish();
        if (multilevelSelectCallBack != null) {
            multilevelSelectCallBack.onCancel();
        }
    }

    public void onBackPressed() {
        cancel();
    }

    private void initParam() {
        Intent intent = getIntent();
        this.title = intent.getStringExtra("title");
        this.defaultSegmentName = intent.getStringExtra("defaultSegmentName");
        if (TextUtils.isEmpty(this.defaultSegmentName)) {
            this.defaultSegmentName = "请选择";
        }
        try {
            this.allItemList = JSON.parseArray(intent.getStringExtra("list"));
            this.currentItemList = this.allItemList;
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG, tr);
        }
        String preResult = intent.getStringExtra("result");
        if (!TextUtils.isEmpty(preResult) && this.allItemList != null && !this.allItemList.isEmpty()) {
            try {
                getSelectItemList(this.allItemList, JSON.parseArray(preResult), 0);
            } catch (Throwable tr2) {
                LoggerFactory.getTraceLogger().error(BeehiveConstant.TAG, tr2);
            }
        }
    }

    private void getSelectItemList(JSONArray itemList, JSONArray preResult, int startIndex) {
        if (itemList != null && preResult != null && startIndex < preResult.size()) {
            int size = itemList.size();
            JSONObject preItem = preResult.getJSONObject(startIndex);
            for (int i = 0; i < size; i++) {
                JSONObject item = itemList.getJSONObject(i);
                if (TextUtils.equals(item.getString("name"), preItem.getString("name"))) {
                    this.selectItemList.add(item);
                    startIndex++;
                    getSelectItemList(item.getJSONArray("subList"), preResult, startIndex);
                }
            }
        }
    }
}
