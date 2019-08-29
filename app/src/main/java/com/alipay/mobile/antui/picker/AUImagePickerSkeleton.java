package com.alipay.mobile.antui.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.adapter.ImagePickerAdapter;
import com.alipay.mobile.antui.api.AntUI;
import com.alipay.mobile.antui.basic.AURelativeLayout;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.theme.AUAbsTheme;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.util.ArrayList;
import java.util.List;

public class AUImagePickerSkeleton extends AURelativeLayout implements AntUI {
    /* access modifiers changed from: private */
    public static String TAG = "AUImagePickerSkeleton";
    public static float partionX;
    public static float partionY;
    private int GRID_SIZE = 4;
    private int MAX_NUM = 9;
    private float RL_SPACE = 4.7f;
    private float TB_SPACE = 4.7f;
    /* access modifiers changed from: private */
    public boolean allowDrag = true;
    private ImageCallBack callBack;
    private GridLayoutManager gridLayoutManager;
    public float gridSize = -1.0f;
    private AURelativeLayout layout;
    private OnDataChangeListener onDataChangeListener;
    private ImagePickerAdapter pickerAdapter;
    /* access modifiers changed from: private */
    public OnPickerClickListener pickerClickListener;
    private AUTextView pickerCount;
    private AUTextView pickerTitleeDesc;
    private RecyclerView recyclerView;
    private List<PickeInfo> showBmps = new ArrayList();
    /* access modifiers changed from: private */
    public float touchX;
    /* access modifiers changed from: private */
    public float touchY;

    public interface ImageCallBack {
        void setImage(Bitmap bitmap);
    }

    public interface OnDataChangeListener {
        void onDataChanged();

        void onDataExchanged();
    }

    public interface OnPickerClickListener {
        void onImageClick(int i);

        void onPickerClick(int i);
    }

    public interface PickeInfo {
        void getImage(ImageCallBack imageCallBack);

        int getState();

        boolean isGif();
    }

    public AUImagePickerSkeleton(Context context) {
        super(context);
        init(context, null);
    }

    public void setMaxNum(int maxNum) {
        this.MAX_NUM = maxNum;
        if (this.pickerAdapter != null) {
            this.pickerAdapter.setmMaxNum(this.MAX_NUM);
        }
    }

    public void isAllowDrag(boolean allowDrag2) {
        this.allowDrag = allowDrag2;
    }

    public AUImagePickerSkeleton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setGridSize(int gridSize2) {
        this.GRID_SIZE = gridSize2;
    }

    public void setItemSpace(int rlSpace, int tbSpace) {
        this.RL_SPACE = (float) rlSpace;
        this.TB_SPACE = (float) tbSpace;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void init(Context context, AttributeSet attrs) {
        init(context, attrs, null);
    }

    public void setData(List<PickeInfo> pickeInfos) {
        if (pickeInfos != null) {
            this.showBmps = pickeInfos;
            this.pickerAdapter.setmDatas(this.showBmps);
            this.pickerAdapter.notifyDataSetChanged();
            updatePickerCount(pickeInfos.size());
            if (this.onDataChangeListener != null) {
                this.onDataChangeListener.onDataChanged();
            }
            for (int i = 0; i < pickeInfos.size(); i++) {
                AuiLogger.info("ImagePickerView", "AUimagePickerSkeleton:" + pickeInfos.get(i).toString());
            }
        }
    }

    private void updatePickerCount(int size) {
        if (size == 0) {
            this.pickerCount.setVisibility(4);
        } else {
            this.pickerCount.setVisibility(0);
        }
        this.pickerCount.setText(size + "/" + this.MAX_NUM);
    }

    public void setPickerClickListener(OnPickerClickListener pickerClickListener2) {
        this.pickerClickListener = pickerClickListener2;
    }

    public void setPickerTitle(String desc) {
        this.layout.setVisibility(0);
        if (!TextUtils.isEmpty(desc)) {
            this.pickerTitleeDesc.setText(desc);
        }
    }

    public void init(Context context, AttributeSet attrs, TypedArray typedArray) {
        View view = LayoutInflater.from(context).inflate(R.layout.au_image_picker, this, true);
        this.layout = (AURelativeLayout) view.findViewById(R.id.picker_title);
        this.pickerCount = (AUTextView) view.findViewById(R.id.picker_title_count);
        this.pickerTitleeDesc = (AUTextView) view.findViewById(R.id.picker_title_desc);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        this.pickerAdapter = new ImagePickerAdapter(context, this.showBmps, this.MAX_NUM);
        this.gridLayoutManager = new GridLayoutManager(context, this.GRID_SIZE);
        this.recyclerView.addItemDecoration(new p(this, this.RL_SPACE, this.TB_SPACE));
        this.recyclerView.setLayoutManager(this.gridLayoutManager);
        this.recyclerView.setAdapter(this.pickerAdapter);
        this.recyclerView.setOnDragListener(new ItemDragCallback(this.pickerAdapter));
        this.pickerAdapter.setOnItemClickListener(new n(this));
    }

    public void initContent(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void initStyleByTheme(Context context) {
    }

    public void initAttrStyle(Context context, AttributeSet attrs, TypedArray typedArray) {
    }

    public void upDateTheme(Context context, AUAbsTheme theme) {
    }

    public void setImageCallBack(ImageCallBack callBack2) {
        this.callBack = callBack2;
        AuiLogger.info(TAG, "callback" + callBack2 + this.callBack);
    }

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.onDataChangeListener = listener;
        this.pickerAdapter.setOnDataChangeListener(listener);
    }
}
