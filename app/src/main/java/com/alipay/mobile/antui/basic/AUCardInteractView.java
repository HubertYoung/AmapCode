package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.api.OnLoadImageListener;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.iconfont.model.IconInfo;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import java.util.ArrayList;
import java.util.List;

public class AUCardInteractView extends AULinearLayout implements AntUI {
    public static final String GREY_STYLE = "grey";
    private List<Interaction> mInteractions;
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;
    private OnLoadImageListener mOnLoadImageListener;

    public class Interaction {
        public String colorStyle;
        public IconInfo icon;
        public String schema;
        public String text;
        public String type;

        public Interaction() {
        }

        public Interaction(String text2, String schema2) {
            this.text = text2;
            this.schema = schema2;
        }

        public Interaction(String text2, String schema2, String type2) {
            this.text = text2;
            this.schema = schema2;
            this.type = type2;
        }

        public Interaction(IconInfo icon2, String text2, String schema2, String type2) {
            this.icon = icon2;
            this.text = text2;
            this.schema = schema2;
            this.type = type2;
        }

        public Interaction(IconInfo icon2, String text2, String schema2, String type2, String colorStyle2) {
            this.icon = icon2;
            this.text = text2;
            this.schema = schema2;
            this.type = type2;
            this.colorStyle = colorStyle2;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Interaction interaction, int i);
    }

    public AUCardInteractView(Context context) {
        super(context);
        init(context, null);
    }

    public AUCardInteractView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUCardInteractView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void initStyleByTheme(Context context) {
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(0);
        init(context, null, null);
        initStyleByTheme(context);
    }

    public void setInteractions(List<Interaction> interactions) {
        if (interactions != null && interactions.size() != 0) {
            if (this.mInteractions == null) {
                this.mInteractions = new ArrayList();
                int size = interactions.size();
                for (int i = 0; i < size; i++) {
                    addView(newChildView(), (LayoutParams) getparam());
                }
            } else {
                int oldViewCount = this.mInteractions.size();
                int newViewCount = interactions.size();
                if (oldViewCount < newViewCount) {
                    for (int i2 = oldViewCount; i2 < newViewCount; i2++) {
                        addView(newChildView(), (LayoutParams) getparam());
                    }
                } else if (oldViewCount > newViewCount) {
                    removeViews(newViewCount, oldViewCount - newViewCount);
                }
            }
            this.mInteractions.clear();
            this.mInteractions.addAll(interactions);
            for (int i3 = 0; i3 < this.mInteractions.size(); i3++) {
                setChildView(getChildAt(i3), this.mInteractions.get(i3), i3);
            }
        }
    }

    public void updateInteraction(int position, String text) {
        if (position < getChildCount()) {
            View childView = getChildAt(position);
            if (childView != null && childView.getTag() != null && (childView.getTag() instanceof d)) {
                ((d) childView.getTag()).b.setText(text);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private LinearLayout.LayoutParams getparam() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, getResources().getDimensionPixelSize(R.dimen.AU_SPACE12));
        layoutParams.gravity = 17;
        layoutParams.weight = 1.0f;
        return layoutParams;
    }

    private View newChildView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.au_card_interact_item, null);
        d holder = new d(0);
        holder.b = (AUTextView) view.findViewById(R.id.interact_text);
        holder.a = (AUIconView) view.findViewById(R.id.interact_icon);
        view.setTag(holder);
        return view;
    }

    private void setChildView(View view, Interaction interaction, int position) {
        if (interaction != null && view != null && view.getTag() != null && (view.getTag() instanceof d)) {
            d viewHolder = (d) view.getTag();
            view.setOnClickListener(new c(this, interaction, position));
            setTextView(viewHolder.b, interaction.text, interaction.colorStyle);
            setIconView(viewHolder.a, interaction.icon);
        }
    }

    private void setTextView(AUTextView textView, String text, String colorStyle) {
        textView.setText(text);
        if (TextUtils.equals(colorStyle, GREY_STYLE)) {
            textView.setTextColor(getResources().getColor(R.color.AU_COLOR_ASS_CONTENT));
        } else {
            textView.setTextColor(getResources().getColor(R.color.AU_COLOR_HOME_PAGE_TITLEBAR_BG));
        }
    }

    private void setIconView(AUIconView imageView, IconInfo iconInfo) {
        if (iconInfo == null) {
            imageView.setVisibility(8);
        } else if (iconInfo.type == 1) {
            imageView.setVisibility(0);
            if (this.mOnLoadImageListener != null) {
                this.mOnLoadImageListener.loadImage(iconInfo.icon, imageView.getImageView(), null);
            }
        } else if (iconInfo.type == 3) {
            imageView.setVisibility(0);
            imageView.setImageDrawable(iconInfo.drawable);
        } else if (iconInfo.type == 2) {
            imageView.setVisibility(0);
            imageView.setIconfontUnicode(iconInfo.icon);
        } else {
            imageView.setVisibility(8);
        }
    }

    public AUIconView[] getSupportIconfontViews() {
        int childCount = getChildCount();
        AUIconView[] childViews = new AUIconView[childCount];
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (!(childAt == null || childAt.getTag() == null || !(childAt.getTag() instanceof d))) {
                childViews[i] = ((d) childAt.getTag()).a;
            }
        }
        return childViews;
    }

    public void setmOnItemClickListener(OnLoadImageListener onLoadImageListener) {
        this.mOnLoadImageListener = onLoadImageListener;
    }
}
