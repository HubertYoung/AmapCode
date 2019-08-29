package com.autonavi.map.core.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.R;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import org.json.JSONException;
import org.json.JSONObject;

public class AbPageChoiceDialogView extends FrameLayout {
    private View mBackBtn;
    /* access modifiers changed from: private */
    public a mBtnClickListener;
    private ListView mChoiceList;
    private OnClickListener mClickListener;
    private ImageView mCloseBtn;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public String[] mDataArray;
    /* access modifiers changed from: private */
    public StringBuilder mSelectedItems;

    class ChoiceListAdapter extends BaseAdapter {
        public ChoiceListAdapter() {
            notifyDataSetChanged();
        }

        public int getCount() {
            if (AbPageChoiceDialogView.this.mDataArray != null) {
                return AbPageChoiceDialogView.this.mDataArray.length;
            }
            return 0;
        }

        public Object getItem(int i) {
            if (AbPageChoiceDialogView.this.mDataArray != null) {
                return AbPageChoiceDialogView.this.mDataArray[i];
            }
            return Integer.valueOf(i);
        }

        public long getItemId(int i) {
            return AbPageChoiceDialogView.this.mDataArray != null ? (long) (i % AbPageChoiceDialogView.this.mDataArray.length) : (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            b bVar;
            if (view == null) {
                view = LayoutInflater.from(AbPageChoiceDialogView.this.mContext).inflate(R.layout.layer_ab_page_choice_list_item, null, false);
                bVar = new b(AbPageChoiceDialogView.this, 0);
                bVar.a = (TextView) view.findViewById(R.id.choice_item);
                bVar.b = false;
                view.setTag(bVar);
            } else {
                bVar = (b) view.getTag();
            }
            bVar.a.setText(AbPageChoiceDialogView.this.mDataArray[i]);
            return view;
        }
    }

    public interface a {
    }

    class b {
        public TextView a;
        public boolean b;

        private b() {
            this.b = false;
        }

        /* synthetic */ b(AbPageChoiceDialogView abPageChoiceDialogView, byte b2) {
            this();
        }
    }

    public AbPageChoiceDialogView(@NonNull Context context) {
        this(context, null);
    }

    public AbPageChoiceDialogView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbPageChoiceDialogView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSelectedItems = new StringBuilder();
        this.mClickListener = new OnClickListener() {
            public final void onClick(View view) {
                if (view != null) {
                    int id = view.getId();
                    if (AbPageChoiceDialogView.this.mBtnClickListener != null) {
                        if (R.id.close_btn == id) {
                            AbPageChoiceDialogView.this.logCloseBtn();
                            AbPageChoiceDialogView.this.mBtnClickListener;
                        } else if (R.id.back_old_home_page_btn == id) {
                            AbPageChoiceDialogView.this.logBackOldVersionBtn(AbPageChoiceDialogView.this.mSelectedItems.toString());
                            AbPageChoiceDialogView.this.mBtnClickListener;
                        }
                        if (AbPageChoiceDialogView.this.mSelectedItems != null) {
                            AbPageChoiceDialogView.this.mSelectedItems.delete(0, AbPageChoiceDialogView.this.mSelectedItems.length());
                        }
                    }
                }
            }
        };
        this.mDataArray = new String[]{"搜索框位置不习惯", "地图变小不习惯", "功能太多没啥用", "页面卡顿", "说不上来，就是喜欢旧版"};
        this.mContext = context;
        init(context);
    }

    public void setBtnClickListener(a aVar) {
        this.mBtnClickListener = aVar;
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layer_ab_page_choice_dialog_layout, null, false);
        LayoutParams layoutParams = new LayoutParams(agn.a(context, 280.0f), agn.a(context, 391.0f));
        layoutParams.gravity = 17;
        addView(inflate, layoutParams);
        this.mCloseBtn = (ImageView) inflate.findViewById(R.id.close_btn);
        this.mCloseBtn.setOnClickListener(this.mClickListener);
        initChoiceList();
        this.mBackBtn = inflate.findViewById(R.id.back_old_home_page_btn);
        this.mBackBtn.setOnClickListener(this.mClickListener);
    }

    public void updateChoiceListAdapter() {
        if (this.mSelectedItems != null) {
            this.mSelectedItems.delete(0, this.mSelectedItems.length());
        }
        this.mChoiceList.setAdapter(new ChoiceListAdapter());
    }

    private void initChoiceList() {
        this.mChoiceList = (ListView) findViewById(R.id.choice_list);
        updateChoiceListAdapter();
        this.mChoiceList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (view != null) {
                    b bVar = (b) view.getTag();
                    if (bVar != null && bVar.a != null) {
                        if (bVar.b) {
                            view.setBackgroundResource(R.drawable.layer_dialog_choice_item_normal_bg);
                            bVar.a.setTextColor(AbPageChoiceDialogView.this.getResources().getColor(R.color.item_normal_text_color));
                        } else {
                            view.setBackgroundResource(R.drawable.layer_dialog_choice_item_selected_bg);
                            bVar.a.setTextColor(AbPageChoiceDialogView.this.getResources().getColor(R.color.item_selected_text_color));
                        }
                        bVar.b = !bVar.b;
                        view.setTag(bVar);
                        AbPageChoiceDialogView.this.collectOptions(i, bVar.b);
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void collectOptions(int i, boolean z) {
        int i2 = i + 1;
        if (z) {
            if (this.mSelectedItems.length() > 0) {
                this.mSelectedItems.append(",");
            }
            this.mSelectedItems.append(i2);
            return;
        }
        int indexOf = this.mSelectedItems.indexOf(String.valueOf(i2));
        if (indexOf >= 0) {
            if (this.mSelectedItems.length() == 1) {
                this.mSelectedItems.delete(indexOf, indexOf + 1);
            } else if (indexOf == this.mSelectedItems.length() - 1) {
                this.mSelectedItems.delete(indexOf - 1, indexOf + 1);
            } else {
                this.mSelectedItems.delete(indexOf, indexOf + 2);
            }
        }
    }

    /* access modifiers changed from: private */
    public void logCloseBtn() {
        LogManager.actionLogV25(LogConstant.REDESIGN_SWITCH_AB, "B001", new SimpleEntry("status", "1"));
    }

    /* access modifiers changed from: private */
    public void logBackOldVersionBtn(String str) {
        if (TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("status", "0");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            defpackage.bob.a.a.a(LogConstant.REDESIGN_SWITCH_AB, "B001", jSONObject);
            return;
        }
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("status", "0");
            jSONObject2.put("type", str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        defpackage.bob.a.a.a(LogConstant.REDESIGN_SWITCH_AB, "B001", jSONObject2);
    }
}
