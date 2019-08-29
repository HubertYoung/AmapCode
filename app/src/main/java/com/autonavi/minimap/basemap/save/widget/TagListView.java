package com.autonavi.minimap.basemap.save.widget;

import android.content.Context;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import com.autonavi.minimap.R;
import java.util.ArrayList;
import java.util.List;

public class TagListView extends ViewGroup {
    /* access modifiers changed from: private */
    public a mListner;
    private List<TagView> mTags;
    private int marginHor;
    private int marginVer;

    public static class LayoutParams extends MarginLayoutParams {
        int a;
        int b;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams() {
            super(-2, -2);
        }
    }

    public interface a {
        void a(TagView tagView);
    }

    public void init() {
        this.marginHor = getResources().getDimensionPixelSize(R.dimen.default_margin_5A);
        this.marginVer = getResources().getDimensionPixelSize(R.dimen.default_margin_5A);
        this.mTags = new ArrayList();
    }

    public TagListView(Context context) {
        super(context);
        init();
    }

    public TagListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TagListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            childAt.layout(layoutParams.a + layoutParams.leftMargin, layoutParams.b + layoutParams.topMargin, layoutParams.a + layoutParams.leftMargin + childAt.getMeasuredWidth(), layoutParams.b + layoutParams.topMargin + childAt.getMeasuredHeight());
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int size = (MeasureSpec.getSize(i) - getPaddingLeft()) - getPaddingRight();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                childAt.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), layoutParams.width), getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), layoutParams.height));
                if (this.marginHor + i5 + childAt.getMeasuredWidth() > size) {
                    i4 += childAt.getMeasuredHeight() + this.marginVer;
                    layoutParams.a = getPaddingLeft() + 0;
                    layoutParams.b = getPaddingTop() + i4;
                    i5 = childAt.getMeasuredWidth() + this.marginHor + 0;
                    i3 = Math.max(i5, i3);
                } else {
                    layoutParams.a = getPaddingLeft() + i5;
                    layoutParams.b = getPaddingTop() + i4;
                    i5 += childAt.getMeasuredWidth() + this.marginHor;
                    i3 = Math.max(i5, i3);
                }
            }
            if (i6 == childCount - 1) {
                i4 += childAt.getMeasuredHeight();
            }
        }
        setMeasuredDimension(resolveSize(i3, i), resolveSize(i4, i2));
    }

    public void setTagSelectListener(a aVar) {
        this.mListner = aVar;
    }

    public View addTag(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        LayoutParams layoutParams = new LayoutParams();
        TagView tagView = new TagView(getContext());
        tagView.mIsChecked = false;
        tagView.mOriginTagStr = str;
        tagView.mShowTagStr = TextUtils.ellipsize(str, tagView.getPaint(), (float) (agn.a(getContext(), 13.0f) * 15), TruncateAt.END).toString();
        tagView.setHeight(getResources().getDimensionPixelOffset(R.dimen.save_tag_height));
        tagView.setGravity(16);
        tagView.setText(tagView.mShowTagStr);
        tagView.setTextSize(1, 13.0f);
        tagView.setTextColor(getResources().getColor(R.color.f_c_3));
        tagView.setBackgroundResource(R.drawable.save_tag_background);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.save_tag_hor_padding);
        tagView.setPadding(dimensionPixelOffset, 0, dimensionPixelOffset, 0);
        tagView.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                if (view instanceof TagView) {
                    TagView tagView = (TagView) view;
                    TagListView.this.setDefaultStatus();
                    tagView.setSelected(true);
                    if (TagListView.this.mListner != null) {
                        TagListView.this.mListner.a(tagView);
                    }
                }
            }
        });
        addView(tagView, layoutParams);
        this.mTags.add(tagView);
        return tagView;
    }

    public void setDefaultStatus() {
        if (this.mTags != null && this.mTags.size() > 0) {
            for (TagView next : this.mTags) {
                if (next.isSelected()) {
                    next.setSelected(false);
                    next.setTextColor(getResources().getColor(R.color.f_c_3));
                }
            }
        }
    }

    public void selectTag(String str) {
        if (!TextUtils.isEmpty(str) && this.mTags != null && this.mTags.size() != 0) {
            for (TagView next : this.mTags) {
                if (str.equals(next.mOriginTagStr)) {
                    next.performClick();
                    return;
                }
            }
        }
    }
}
