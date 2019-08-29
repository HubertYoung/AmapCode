package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUDialog;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.iconfont.model.IconInfo;
import com.alipay.mobile.antui.iconfont.model.MessagePopItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AUListDialog extends AUDialog implements android.widget.AdapterView.OnItemClickListener {
    private android.widget.AdapterView.OnItemClickListener adapterListener;
    private View buttonsContainer;
    private View buttonsDivider;
    private View headDivider;
    /* access modifiers changed from: private */
    public final LayoutInflater inflater;
    private ac listAdapter;
    private AUMaxItemCornerListView listView;
    private Boolean listViewFadeScrollbars;
    private OnItemClickListener listener;
    /* access modifiers changed from: private */
    public Button mCancelBtn;
    private final Context mContext;
    /* access modifiers changed from: private */
    public Button mEnsureBtn;
    /* access modifiers changed from: private */
    public List<MessagePopItem> mItemList;
    /* access modifiers changed from: private */
    public OnClickListener mNegativeListener;
    private String mNegativeString;
    /* access modifiers changed from: private */
    public OnClickListener mPositiveListener;
    private String mPositiveString;
    private float maxItems;
    private String message;
    private ScrollView messageContent;
    private AUTextView messageView;
    private float singleItemHeight;
    private String title;
    private View titleContainer;
    private TextView titleView;

    @Deprecated
    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public AUListDialog(Context context, ArrayList<String> list) {
        super(context, R.style.noTitleTransBgDialogStyle);
        this.listViewFadeScrollbars = null;
        this.mItemList = new ArrayList();
        this.title = "";
        this.message = "";
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        if (list != null) {
            this.mItemList.clear();
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                this.mItemList.add(new MessagePopItem(it.next()));
            }
        }
    }

    public AUListDialog(Context context, List<MessagePopItem> list) {
        this(context, (String) "", (String) "", list);
    }

    public AUListDialog(Context context, String title2, String message2, List<MessagePopItem> list) {
        this(context, title2, message2, list, (String) "", (OnClickListener) null, (String) "", (OnClickListener) null);
    }

    public AUListDialog(Context context, String title2, String message2, List<MessagePopItem> list, String positiveString, OnClickListener positiveListener, String negativeString, OnClickListener negativeListener) {
        super(context, R.style.noTitleTransBgDialogStyle);
        this.listViewFadeScrollbars = null;
        this.mItemList = new ArrayList();
        this.title = "";
        this.message = "";
        this.title = title2;
        this.message = message2;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mPositiveString = positiveString;
        this.mPositiveListener = positiveListener;
        this.mNegativeString = negativeString;
        this.mNegativeListener = negativeListener;
        if (list != null && list.size() != 0) {
            this.mItemList.clear();
            this.mItemList.addAll(list);
        }
    }

    @Deprecated
    public AUListDialog(ArrayList<PopMenuItem> list, Context context) {
        this(null, list, context);
    }

    @Deprecated
    public AUListDialog(String title2, ArrayList<PopMenuItem> list, Context context) {
        this(title2, (String) null, list, context);
    }

    @Deprecated
    public AUListDialog(String title2, String message2, ArrayList<PopMenuItem> list, Context context) {
        this(title2, message2, list, false, null, null, null, null, context);
    }

    @Deprecated
    public AUListDialog(String title2, ArrayList<PopMenuItem> list, boolean showSelectionState, String positiveString, OnClickListener positiveListener, String negativeString, OnClickListener negativeListener, Context context) {
        this(title2, null, list, showSelectionState, positiveString, positiveListener, negativeString, negativeListener, context);
    }

    @Deprecated
    public AUListDialog(String title2, String message2, ArrayList<PopMenuItem> list, boolean showSelectionState, String positiveString, OnClickListener positiveListener, String negativeString, OnClickListener negativeListener, Context context) {
        super(context, R.style.noTitleTransBgDialogStyle);
        this.listViewFadeScrollbars = null;
        this.mItemList = new ArrayList();
        this.title = "";
        this.message = "";
        this.title = title2;
        this.message = message2;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mPositiveString = positiveString;
        this.mPositiveListener = positiveListener;
        this.mNegativeString = negativeString;
        this.mNegativeListener = negativeListener;
        this.mItemList = getMessageList(list);
    }

    private List<MessagePopItem> getMessageList(List<PopMenuItem> popMenuItemList) {
        if (popMenuItemList == null || popMenuItemList.size() <= 0) {
            return new ArrayList();
        }
        List messagePopItemList = new ArrayList();
        for (PopMenuItem popMenuItem : popMenuItemList) {
            if (popMenuItem != null) {
                MessagePopItem messagePopItem = new MessagePopItem();
                messagePopItem.title = (String) popMenuItem.getName();
                if (popMenuItem.getResId() != 0) {
                    messagePopItem.icon = new IconInfo(popMenuItem.getResId());
                } else {
                    messagePopItem.icon = new IconInfo(popMenuItem.getDrawable());
                }
                messagePopItem.externParam = popMenuItem.getExternParam();
                messagePopItemList.add(messagePopItem);
            }
        }
        return messagePopItemList;
    }

    @Deprecated
    public void setOnItemClickListener(OnItemClickListener listener2) {
        this.listener = listener2;
    }

    public void setOnItemClickListener(android.widget.AdapterView.OnItemClickListener listener2) {
        this.adapterListener = listener2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = this.inflater.inflate(R.layout.au_list_dialog, null);
        this.listView = (AUMaxItemCornerListView) rootView.findViewById(R.id.dialog_listView);
        if (this.singleItemHeight > 0.0f) {
            this.listView.setSingleItemHeight(this.singleItemHeight);
        }
        if (this.maxItems > 0.0f) {
            this.listView.setMaxItems(this.maxItems);
        }
        if (this.listViewFadeScrollbars != null) {
            this.listView.setScrollbarFadingEnabled(this.listViewFadeScrollbars.booleanValue());
        }
        this.listView.setDivider(new ColorDrawable(this.mContext.getResources().getColor(R.color.AU_COLOR_DIALOG_DIVIDER_COLOR)));
        this.listView.setDividerHeight(this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_DIVIDER_SPACE1));
        this.listView.setHeaderDividersEnabled(false);
        this.listView.setFooterDividersEnabled(false);
        this.listView.setBackgroundResource(R.drawable.au_dialog_bg);
        this.titleContainer = rootView.findViewById(R.id.title_container);
        this.titleView = (TextView) this.titleContainer.findViewById(R.id.title);
        this.messageContent = (ScrollView) this.titleContainer.findViewById(R.id.message_content);
        this.messageView = (AUTextView) this.titleContainer.findViewById(R.id.message);
        this.messageView.setMinLines(0);
        this.headDivider = rootView.findViewById(R.id.head_divider);
        this.buttonsContainer = rootView.findViewById(R.id.bottom_container);
        this.mEnsureBtn = (Button) this.buttonsContainer.findViewById(R.id.ensure);
        this.mCancelBtn = (Button) this.buttonsContainer.findViewById(R.id.cancel);
        this.buttonsDivider = rootView.findViewById(R.id.bottom_divider);
        setContentView(rootView);
        init();
    }

    private void init() {
        this.listAdapter = new ac(this, 0);
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(this);
        if (!TextUtils.isEmpty(this.mNegativeString) || !TextUtils.isEmpty(this.mPositiveString)) {
            this.buttonsContainer.setVisibility(0);
            this.buttonsDivider.setVisibility(0);
            this.listView.updateFootState(true);
        } else {
            this.buttonsContainer.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.mNegativeString)) {
            this.mCancelBtn.setText(this.mNegativeString);
            this.mCancelBtn.setOnClickListener(new aa(this));
        } else {
            this.mCancelBtn.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.mPositiveString)) {
            this.mEnsureBtn.setText(this.mPositiveString);
            this.mEnsureBtn.setOnClickListener(new ab(this));
        } else {
            this.mEnsureBtn.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.message) || !TextUtils.isEmpty(this.title)) {
            this.titleContainer.setVisibility(0);
            this.headDivider.setVisibility(0);
            this.listView.updateHeadState(true);
        } else {
            this.titleContainer.setVisibility(8);
        }
        if (!TextUtils.isEmpty(this.title)) {
            this.titleView.setText(this.title);
            this.titleView.setVisibility(0);
        } else {
            this.titleView.setVisibility(8);
            ((LayoutParams) this.messageContent.getLayoutParams()).topMargin = 0;
        }
        if (!TextUtils.isEmpty(this.message)) {
            this.messageView.setText(this.message);
            this.messageView.setVisibility(0);
            return;
        }
        this.messageView.setVisibility(8);
    }

    public void show() {
        super.show();
        setWindowMaxWidth(this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE10));
    }

    @Deprecated
    public void updateData(ArrayList<PopMenuItem> list) {
        if (this.listAdapter != null) {
            this.mItemList.clear();
            this.mItemList.addAll(getMessageList(list));
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void updateListData(ArrayList<MessagePopItem> messagePopItems) {
        if (this.listAdapter != null) {
            this.mItemList.clear();
            this.mItemList.addAll(messagePopItems);
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void setMaxItems(float maxItems2) {
        this.maxItems = maxItems2;
    }

    public float getMaxItems() {
        if (this.maxItems > 0.0f) {
            return this.maxItems;
        }
        return 5.0f;
    }

    public void setListViewScrollbarFadingEnabled(boolean enabled) {
        this.listViewFadeScrollbars = Boolean.valueOf(enabled);
    }

    public void setSingleItemHeight(float singleItemHeight2) {
        this.singleItemHeight = singleItemHeight2;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.listener != null) {
            this.listener.onItemClick(position);
        }
        if (this.adapterListener != null) {
            this.adapterListener.onItemClick(parent, view, position, id);
        }
        dismiss();
    }
}
