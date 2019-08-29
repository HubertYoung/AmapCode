package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AUCardOptionView extends AULinearLayout implements AntUI {
    /* access modifiers changed from: private */
    public static final String TAG = AUCardOptionView.class.getSimpleName();
    public static final String TEXT_NOT_CHANGE = "text_not_change";
    public static final String TYPE_COMMENT = "comment";
    public static final String TYPE_PRAISE = "praise";
    public static final String TYPE_REWARD = "reward";
    private ArrayList<CardOptionItem> cardOptionItems;
    private int default_height;
    private LayoutInflater inflater;
    private String mCommentText;
    /* access modifiers changed from: private */
    public CardOptionClickListner mListner;
    private String mPraiseText;
    private String mRewardText;
    private Map<String, View> mViewMap = new HashMap();
    private String textType = "";
    private boolean textVisible = true;

    public interface CardOptionClickListner {
        void onCardOptionClick(View view, CardOptionItem cardOptionItem, int i);
    }

    public class CardOptionItem {
        public int count;
        public boolean hasClicked = false;
        public boolean official = false;
        public String type;
    }

    public AUCardOptionView(Context context) {
        super(context);
        init();
    }

    public AUCardOptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(0);
        setGravity(17);
        this.default_height = getResources().getDimensionPixelSize(R.dimen.AU_SPACE10);
        this.mPraiseText = getResources().getString(R.string.praise);
        this.mRewardText = getResources().getString(R.string.reward);
        this.mCommentText = getResources().getString(R.string.comment);
        this.inflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
    }

    public void setTextVisible(boolean textVisible2) {
        this.textVisible = textVisible2;
    }

    public void setViewInfo(ArrayList<CardOptionItem> itemArrayList, boolean textVisible2) {
        this.textVisible = textVisible2;
        setViewInfo(itemArrayList);
    }

    public void setViewInfo(ArrayList<CardOptionItem> itemArrayList, String textType2) {
        this.textType = textType2;
        setViewInfo(itemArrayList);
    }

    public void setViewInfo(ArrayList<CardOptionItem> itemArrayList) {
        setViewInfo(itemArrayList, this.default_height);
    }

    public void setViewInfo(ArrayList<CardOptionItem> itemArrayList, int height, boolean textVisible2) {
        this.textVisible = textVisible2;
        setViewInfo(itemArrayList, height);
    }

    public void setViewInfo(ArrayList<CardOptionItem> itemArrayList, int height) {
        LayoutParams params = getLayoutParams();
        params.height = height;
        setLayoutParams(params);
        setLayoutView(itemArrayList);
    }

    private void setLayoutView(ArrayList<CardOptionItem> itemArrayList) {
        if (itemArrayList == null || itemArrayList.size() == 0) {
            Log.d(TAG, "CardOptionItem List is null");
            return;
        }
        if (this.cardOptionItems == null) {
            this.cardOptionItems = new ArrayList<>();
            int size = itemArrayList.size();
            for (int i = 0; i < size; i++) {
                addView(newChildView(i));
            }
        } else {
            int oldViewCount = this.cardOptionItems.size();
            int newViewCount = itemArrayList.size();
            if (oldViewCount < newViewCount) {
                for (int i2 = oldViewCount; i2 < newViewCount; i2++) {
                    addView(newChildView(i2));
                }
            } else if (oldViewCount > newViewCount) {
                removeViews(newViewCount, oldViewCount - newViewCount);
            }
        }
        this.cardOptionItems.clear();
        this.cardOptionItems.addAll(itemArrayList);
        for (int i3 = 0; i3 < this.cardOptionItems.size(); i3++) {
            setChildView(getChildAt(i3), this.cardOptionItems.get(i3));
        }
    }

    private View newChildView(int position) {
        View view = this.inflater.inflate(R.layout.view_card_option_item, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0f));
        f holder = new f(0);
        holder.a = (AUView) view.findViewById(R.id.card_option_divider_line);
        holder.b = (AUIconView) view.findViewById(R.id.card_option_item_icon);
        holder.c = (AUTextView) view.findViewById(R.id.card_option_item_title);
        holder.d = position;
        view.setTag(holder);
        return view;
    }

    private void setViewType(View view, AUIconView imageView, String type) {
        if (this.mViewMap == null || this.mViewMap.get(type) != view) {
            if (TextUtils.equals(type, TYPE_PRAISE)) {
                imageView.setImageResource(R.drawable.drawable_praise_icon);
            } else if (TextUtils.equals(type, TYPE_REWARD)) {
                imageView.setImageResource(R.drawable.drawable_reward_icon);
            } else if (TextUtils.equals(type, TYPE_COMMENT)) {
                imageView.setImageResource(R.drawable.drawable_comment_icon);
            }
            this.mViewMap.put(type, view);
        }
    }

    private void setIconInfo(AUIconView imageView, CardOptionItem optionItem, boolean anim) {
        setIconDefault(imageView);
        String type = optionItem.type;
        if (TextUtils.equals(type, TYPE_PRAISE)) {
            imageView.setSelected(optionItem.hasClicked);
            if (anim) {
                Log.d(TAG, "setIconInfo, show praise animation");
                imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.card_option_praise_anim));
            }
        } else if (TextUtils.equals(type, TYPE_REWARD)) {
            imageView.setSelected(optionItem.hasClicked);
        }
    }

    private void setCountText(AUTextView textView, CardOptionItem optionItem) {
        if (!this.textVisible) {
            textView.setVisibility(8);
            Log.d(TAG, "textVisible is false");
            return;
        }
        textView.setVisibility(0);
        setTextDefault(textView);
        String type = optionItem.type;
        if (optionItem.count > 0 && !TextUtils.equals(this.textType, TEXT_NOT_CHANGE)) {
            textView.setText(formatRPRNum(optionItem.count));
        } else if (TextUtils.equals(type, TYPE_PRAISE)) {
            textView.setText(this.mPraiseText);
        } else if (TextUtils.equals(type, TYPE_REWARD)) {
            textView.setText(this.mRewardText);
        } else if (TextUtils.equals(type, TYPE_COMMENT)) {
            textView.setText(this.mCommentText);
        }
        if (TextUtils.equals(type, TYPE_PRAISE) || TextUtils.equals(type, TYPE_REWARD)) {
            textView.setSelected(optionItem.hasClicked);
        } else {
            textView.setSelected(false);
        }
    }

    private String formatRPRNum(int num) {
        Log.d(TAG, "formatRPRNum");
        if (num < 10000) {
            return String.valueOf(num);
        }
        int num2 = num / getResources().getInteger(R.integer.num_unit_int);
        if (num2 % 10 == 0) {
            return String.valueOf(num2 / 10) + getResources().getString(R.string.num_unit_text);
        }
        return String.valueOf(((float) num2) / 10.0f) + getResources().getString(R.string.num_unit_text);
    }

    public void setChildView(View view, CardOptionItem optionItem) {
        if (view == null || view.getTag() == null || !(view.getTag() instanceof f)) {
            Log.d(TAG, "setChildView : view is invalide");
            return;
        }
        f viewHolder = (f) view.getTag();
        setViewType(view, viewHolder.b, optionItem.type);
        setIconInfo(viewHolder.b, optionItem, false);
        setCountText(viewHolder.c, optionItem);
        if (this.cardOptionItems.size() == 2 && viewHolder.d == 1) {
            viewHolder.a.setVisibility(0);
        } else {
            viewHolder.a.setVisibility(8);
        }
        view.setOnClickListener(new e(this, optionItem, viewHolder));
    }

    public void unitIncrease(View childView) {
        if (childView == null || childView.getTag() == null || !(childView.getTag() instanceof f)) {
            Log.d(TAG, "unitIncrease : view is invalide");
            return;
        }
        f viewHolder = (f) childView.getTag();
        int position = viewHolder.d;
        this.cardOptionItems.get(position).hasClicked = true;
        CardOptionItem cardOptionItem = this.cardOptionItems.get(position);
        cardOptionItem.count++;
        CardOptionItem item = this.cardOptionItems.get(position);
        Log.d(TAG, "unitIncrease : type = " + item.type + " count = " + item.count);
        setIconInfo(viewHolder.b, item, true);
        setCountText(viewHolder.c, item);
    }

    public void unitDecrease(View childView) {
        if (childView == null || childView.getTag() == null || !(childView.getTag() instanceof f)) {
            Log.d(TAG, "unitDecrease : view is invalide");
            return;
        }
        f viewHolder = (f) childView.getTag();
        int position = viewHolder.d;
        this.cardOptionItems.get(position).hasClicked = false;
        CardOptionItem cardOptionItem = this.cardOptionItems.get(position);
        cardOptionItem.count--;
        CardOptionItem item = this.cardOptionItems.get(position);
        Log.d(TAG, "unitDecrease : type = " + item.type + " count = " + item.count);
        setIconInfo(viewHolder.b, item, false);
        setCountText(viewHolder.c, item);
    }

    public int getCount(int position) {
        if (position >= this.cardOptionItems.size() || position < 0) {
            return 0;
        }
        return this.cardOptionItems.get(position).count;
    }

    public View getChildView(String type) {
        if (this.mViewMap.containsKey(type)) {
            return this.mViewMap.get(type);
        }
        return null;
    }

    private void setTextDefault(AUTextView textView) {
        textView.setActivated(false);
        textView.setSelected(false);
    }

    private void setIconDefault(AUIconView imageView) {
        imageView.setActivated(false);
        imageView.setSelected(false);
    }

    public void setCardOptionListner(CardOptionClickListner cardOptionListner) {
        this.mListner = cardOptionListner;
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
}
