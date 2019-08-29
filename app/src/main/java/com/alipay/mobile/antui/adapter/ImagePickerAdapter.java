package com.alipay.mobile.antui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUImageView;
import com.alipay.mobile.antui.iconfont.AUIconDrawable;
import com.alipay.mobile.antui.iconfont.model.IconPaintBuilder;
import com.alipay.mobile.antui.picker.AUAddImageView;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton.OnDataChangeListener;
import com.alipay.mobile.antui.picker.AUImagePickerSkeleton.PickeInfo;
import com.alipay.mobile.antui.picker.ItemDragCallback.onMoveListener;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.mobile.antui.utils.DensityUtil;
import java.util.List;

public class ImagePickerAdapter extends Adapter<MyViewHolder> implements onMoveListener {
    public static final String VIEW_STATA_END = "view_state_end";
    public static final String VIEW_STATE_DRAGGED = "view_state_dragged";
    /* access modifiers changed from: private */
    public Context mContext;
    private List<PickeInfo> mDatas;
    private LayoutInflater mInflater;
    boolean mIsDelete = false;
    private int mMaxNum;
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;
    private OnDataChangeListener onDataChangeListener;

    public class MyViewHolder extends ViewHolder {
        AUAddImageView mIvAddPhoto;
        View mIvDelete;
        AUImageView mIvDisPlayItemPhoto;
        AUImageView mIvImageType;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mIvDisPlayItemPhoto = (AUImageView) itemView.findViewById(R.id.ivDisPlayItemPhoto);
            this.mIvAddPhoto = (AUAddImageView) itemView.findViewById(R.id.ivAddPhoto);
            this.mIvDelete = itemView.findViewById(R.id.ivError);
            this.mIvImageType = (AUImageView) itemView.findViewById(R.id.ivImageType);
            ((AUImageView) itemView.findViewById(R.id.error_image)).setImageDrawable(new AUIconDrawable(ImagePickerAdapter.this.mContext, new IconPaintBuilder(-50384, DensityUtil.dip2px(ImagePickerAdapter.this.mContext, 22.0f), R.string.iconfont_system_warning3)));
            initListener(itemView);
        }

        private void initListener(View itemView) {
            itemView.setOnLongClickListener(new e(this));
            itemView.setOnTouchListener(new f(this));
            itemView.setOnClickListener(new g(this));
            this.mIvAddPhoto.setOnClickListener(new h(this));
        }

        public void onViewStateChanged(String state) {
            if (TextUtils.equals(state, ImagePickerAdapter.VIEW_STATA_END)) {
                this.itemView.setVisibility(0);
            } else if (TextUtils.equals(state, ImagePickerAdapter.VIEW_STATE_DRAGGED)) {
                AuiLogger.info("ItemDragCallback", "VIEW_STATE_DRAGGED");
            }
        }
    }

    public interface OnItemClickListener {
        void onItemAddClick(View view, int i);

        void onItemClick(View view, int i);

        void onItemLongClick(MyViewHolder myViewHolder, View view, int i);

        void onTouch(View view, MotionEvent motionEvent);
    }

    public ImagePickerAdapter(Context context, List<PickeInfo> datas, int maxNum) {
        this.mContext = context;
        this.mDatas = datas;
        this.mMaxNum = maxNum;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.onDataChangeListener = listener;
    }

    public void setmDatas(List<PickeInfo> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void setmMaxNum(int maxNum) {
        this.mMaxNum = maxNum;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(this.mInflater.inflate(R.layout.auitem_image_picker, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (this.mDatas.size() >= this.mMaxNum) {
            holder.mIvDisPlayItemPhoto.setVisibility(0);
            holder.mIvAddPhoto.setVisibility(8);
            PickeInfo pickeInfo = this.mDatas.get(position);
            pickeInfo.getImage(new c(this, holder, position));
            handleImageType(holder, pickeInfo);
        } else if (this.mDatas.size() == 0) {
            holder.mIvAddPhoto.setVisibility(0);
            holder.mIvDelete.setVisibility(8);
            holder.mIvDisPlayItemPhoto.setVisibility(8);
            holder.mIvImageType.setVisibility(8);
        } else if (position < this.mDatas.size()) {
            holder.mIvAddPhoto.setVisibility(8);
            holder.mIvDisPlayItemPhoto.setVisibility(0);
            AuiLogger.info("ImagePickerAdapter", "pickInfo Adapter execute: " + position);
            PickeInfo pickeInfo2 = this.mDatas.get(position);
            AuiLogger.info("ImagePickerAdapter", "pickInfo Adapter execute pics: " + position);
            pickeInfo2.getImage(new a(this, position, holder));
            handleImageType(holder, pickeInfo2);
        } else {
            AuiLogger.info("ImagePickerAdapter", "pickInfo Adapter 000000");
            holder.mIvAddPhoto.setVisibility(0);
            holder.mIvDelete.setVisibility(8);
            holder.mIvDisPlayItemPhoto.setVisibility(8);
            holder.mIvImageType.setVisibility(8);
        }
    }

    private void handleImageType(MyViewHolder holder, PickeInfo pickeInfo) {
        if (pickeInfo.isGif()) {
            holder.mIvImageType.setVisibility(0);
            holder.mIvImageType.setBackgroundResource(R.drawable.icon_gif);
            return;
        }
        holder.mIvImageType.setVisibility(8);
    }

    public int getItemCount() {
        if (this.mDatas == null || this.mDatas.size() == 0) {
            return 1;
        }
        if (this.mDatas.size() < this.mMaxNum) {
            return this.mDatas.size() + 1;
        }
        return this.mDatas.size();
    }

    public void onItemMove(int fromPosition, int toPosition) {
        if (this.mDatas != null && this.mDatas.size() != 0) {
            if (this.mDatas.size() >= this.mMaxNum || !(fromPosition == this.mDatas.size() || toPosition == this.mDatas.size())) {
                try {
                    PickeInfo curData = this.mDatas.get(fromPosition);
                    this.mDatas.remove(curData);
                    this.mDatas.add(toPosition, curData);
                } catch (Throwable e) {
                    AuiLogger.error("ImagePickerView", "onItemMove swap Exception:" + e);
                }
                notifyItemMoved(fromPosition, toPosition);
                AuiLogger.info("onItemMove", "onItemMove swap finish from:" + fromPosition + " toPosition:" + toPosition);
            }
        }
    }

    public void onItemMoveFinished() {
        if (this.onDataChangeListener != null) {
            this.onDataChangeListener.onDataExchanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setIsDelete(boolean isDelete) {
        this.mIsDelete = isDelete;
    }
}
