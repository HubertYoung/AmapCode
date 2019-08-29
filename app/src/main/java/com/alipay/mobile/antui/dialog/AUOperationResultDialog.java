package com.alipay.mobile.antui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUDialog;
import java.util.ArrayList;
import java.util.List;

public class AUOperationResultDialog extends AUDialog implements android.widget.AdapterView.OnItemClickListener {
    private View divierView;
    private ImageView iconView;
    private final LayoutInflater inflater;
    private ah listAdapter;
    private ListView listView;
    private OnItemClickListener listener;
    private final Context mContext;
    /* access modifiers changed from: private */
    public ArrayList<PopMenuItem> mItemList = new ArrayList<>();
    private final int mMaxHeight;
    private View rootView;
    private String title = "";
    private TextView titleView;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public AUOperationResultDialog(Context context, String title2, List<String> list) {
        super(context, R.style.noTitleTransBgDialogStyle);
        this.title = title2;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mMaxHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.list_dialog_max_height);
        if (list != null) {
            for (String itemName : list) {
                this.mItemList.add(new PopMenuItem(itemName));
            }
            inflateLayout();
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener2) {
        this.listener = listener2;
    }

    private void inflateLayout() {
        this.rootView = this.inflater.inflate(R.layout.au_operation_result_dialog, null);
        this.listView = (ListView) this.rootView.findViewById(R.id.operation_listView);
        this.titleView = (TextView) this.rootView.findViewById(R.id.operation_title);
        this.iconView = (ImageView) this.rootView.findViewById(R.id.operation_icon);
        this.divierView = this.rootView.findViewById(R.id.operation_title_divider);
        init();
    }

    private void init() {
        this.listAdapter = new ah(this, 0);
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(this);
        if (!TextUtils.isEmpty(this.title)) {
            this.titleView.setText(this.title);
            this.titleView.setVisibility(0);
        }
    }

    public void show() {
        super.show();
        setContentView(this.rootView);
        setWindowMaxWidth(this.mContext.getResources().getDimensionPixelSize(R.dimen.AU_SPACE10));
    }

    public void updateData(ArrayList<PopMenuItem> list) {
        if (this.listAdapter != null) {
            this.mItemList.clear();
            this.mItemList = list;
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (this.listener != null) {
            this.listener.onItemClick(position);
        }
        dismiss();
    }

    public ImageView getIconView() {
        return this.iconView;
    }

    public void setDivierViewVisibility(int visibility) {
        this.divierView.setVisibility(visibility);
    }
}
