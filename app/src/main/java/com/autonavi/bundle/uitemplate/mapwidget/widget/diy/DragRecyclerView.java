package com.autonavi.bundle.uitemplate.mapwidget.widget.diy;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.diy.DIYMainMapEntry;
import java.util.Collections;
import java.util.List;

public class DragRecyclerView extends RecyclerView {
    /* access modifiers changed from: private */
    public static int PADDING;
    private float OFFSET_X;
    private float OFFSET_Y;
    private int WIDTH;
    /* access modifiers changed from: private */
    public ItemTouchHelper itemTouchHelper;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public List<DIYMainMapEntry> mEntries;
    /* access modifiers changed from: private */
    public MyAdapter mMyAdapter;
    /* access modifiers changed from: private */
    public OnItemClickListener mOnItemClickListener;

    class MyAdapter extends Adapter<MyViewHolder> {
        private Context mContext;
        private LayoutInflater mLiLayoutInflater = LayoutInflater.from(this.mContext);

        public MyAdapter(Context context) {
            this.mContext = context;
        }

        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(this.mLiLayoutInflater.inflate(R.layout.layout_diy_main_map_pop_item, viewGroup, false));
        }

        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            myViewHolder.imgContainer.setBackgroundResource(R.drawable.common_bg_pop_item);
            myViewHolder.imgContainer.setPadding(DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING);
            DIYMainMapEntry dIYMainMapEntry = (DIYMainMapEntry) DragRecyclerView.this.mEntries.get(i);
            myViewHolder.tv_title.setText(dIYMainMapEntry.name);
            String str = dIYMainMapEntry.isChecked ? dIYMainMapEntry.highlightText : dIYMainMapEntry.normalText;
            myViewHolder.info.setTextColor(this.mContext.getResources().getColor(dIYMainMapEntry.isChecked ? R.color.f_c_6 : R.color.f_c_2));
            myViewHolder.info.setText(str);
            Object obj = dIYMainMapEntry.isChecked ? dIYMainMapEntry.highlightIcon : dIYMainMapEntry.icon;
            if (obj instanceof Integer) {
                ImageLoader.a(this.mContext).a((Object) myViewHolder.img);
                myViewHolder.img.setImageResource(((Integer) obj).intValue());
                return;
            }
            String obj2 = obj != null ? obj.toString() : "";
            if (!TextUtils.isEmpty(obj2)) {
                ImageLoader.a(this.mContext).a(obj2).a(R.drawable.icon_c_diy_default).a(myViewHolder.img, (bjl) null);
                return;
            }
            ImageLoader.a(this.mContext).a((Object) myViewHolder.img);
            myViewHolder.img.setImageResource(R.drawable.icon_c_diy_default);
        }

        public int getItemCount() {
            if (DragRecyclerView.this.mEntries == null) {
                return 0;
            }
            return DragRecyclerView.this.mEntries.size();
        }
    }

    static class MyViewHolder extends ViewHolder {
        public ImageView img;
        public View imgContainer;
        public TextView info;
        public TextView tv_title;

        public MyViewHolder(View view) {
            super(view);
            this.tv_title = (TextView) view.findViewById(R.id.tv_title);
            this.info = (TextView) view.findViewById(R.id.info);
            this.img = (ImageView) view.findViewById(R.id.iv_icon);
            this.imgContainer = view.findViewById(R.id.icon_container);
            this.imgContainer.setPadding(DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING);
        }
    }

    public interface OnItemClickListener {
        void onItemChanged(int i, DIYMainMapEntry dIYMainMapEntry, int i2, DIYMainMapEntry dIYMainMapEntry2, List<DIYMainMapEntry> list);

        void onItemClick(int i, DIYMainMapEntry dIYMainMapEntry);

        void onItemLongClick(int i, DIYMainMapEntry dIYMainMapEntry);
    }

    class OnRecyclerItemClickListener implements OnItemTouchListener {
        private GestureDetectorCompat mGestureDetector;
        /* access modifiers changed from: private */
        public RecyclerView recyclerView;

        class ItemTouchHelperGestureListener extends SimpleOnGestureListener {
            private int downPsition;
            private View mView;

            private ItemTouchHelperGestureListener() {
            }

            public boolean onSingleTapUp(MotionEvent motionEvent) {
                View findChildViewUnder = OnRecyclerItemClickListener.this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (findChildViewUnder != null) {
                    OnRecyclerItemClickListener.this.recyclerView.getChildViewHolder(findChildViewUnder);
                    if (this.mView != null) {
                        this.mView.setBackgroundResource(R.drawable.common_bg_pop_item);
                        this.mView.setPadding(DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING);
                    }
                    if (DragRecyclerView.this.mMyAdapter != null) {
                        DragRecyclerView.this.mMyAdapter.notifyDataSetChanged();
                    }
                    if (DragRecyclerView.this.mOnItemClickListener != null && this.downPsition >= 0 && this.downPsition < DragRecyclerView.this.mEntries.size()) {
                        DragRecyclerView.this.mOnItemClickListener.onItemClick(this.downPsition, (DIYMainMapEntry) DragRecyclerView.this.mEntries.get(this.downPsition));
                    }
                }
                return true;
            }

            public boolean onDown(MotionEvent motionEvent) {
                View findChildViewUnder = OnRecyclerItemClickListener.this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (findChildViewUnder != null) {
                    ViewHolder childViewHolder = OnRecyclerItemClickListener.this.recyclerView.getChildViewHolder(findChildViewUnder);
                    this.mView = childViewHolder.itemView.findViewById(R.id.icon_container);
                    this.mView.setBackgroundResource(R.drawable.common_bg_pop_item_select);
                    this.mView.setPadding(DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING);
                    this.downPsition = childViewHolder.getAdapterPosition();
                }
                return true;
            }

            public void onLongPress(MotionEvent motionEvent) {
                View findChildViewUnder = OnRecyclerItemClickListener.this.recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (findChildViewUnder != null) {
                    ViewHolder childViewHolder = OnRecyclerItemClickListener.this.recyclerView.getChildViewHolder(findChildViewUnder);
                    ItemTouchHelper access$600 = DragRecyclerView.this.itemTouchHelper;
                    if (access$600.l.hasDragFlag(access$600.p, childViewHolder) && childViewHolder.itemView.getParent() == access$600.p) {
                        access$600.a();
                        access$600.h = 0.0f;
                        access$600.g = 0.0f;
                        access$600.a(childViewHolder, 2);
                    }
                    ((Vibrator) DragRecyclerView.this.mContext.getSystemService("vibrator")).vibrate(70);
                    if (DragRecyclerView.this.mOnItemClickListener != null) {
                        int adapterPosition = childViewHolder.getAdapterPosition();
                        if (adapterPosition >= 0 && adapterPosition < DragRecyclerView.this.mEntries.size()) {
                            DragRecyclerView.this.mOnItemClickListener.onItemLongClick(adapterPosition, (DIYMainMapEntry) DragRecyclerView.this.mEntries.get(adapterPosition));
                        }
                    }
                }
            }
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        public OnRecyclerItemClickListener(RecyclerView recyclerView2) {
            this.recyclerView = recyclerView2;
            this.mGestureDetector = new GestureDetectorCompat(recyclerView2.getContext(), new ItemTouchHelperGestureListener());
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView2, MotionEvent motionEvent) {
            this.mGestureDetector.onTouchEvent(motionEvent);
            return false;
        }

        public void onTouchEvent(RecyclerView recyclerView2, MotionEvent motionEvent) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
    }

    public DragRecyclerView(Context context) {
        this(context, null);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DragRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.itemTouchHelper = new ItemTouchHelper(new Callback() {
            private DIYMainMapEntry mFromEntry;
            private int mFromPosition = -1;
            private int mToPosition = -1;

            public boolean isLongPressDragEnabled() {
                return false;
            }

            public void onSwiped(ViewHolder viewHolder, int i) {
            }

            public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
                return makeMovementFlags(15, 0);
            }

            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2) {
                int adapterPosition = viewHolder.getAdapterPosition();
                int adapterPosition2 = viewHolder2.getAdapterPosition();
                if (adapterPosition < adapterPosition2) {
                    int i = adapterPosition;
                    while (i < adapterPosition2) {
                        int i2 = i + 1;
                        Collections.swap(DragRecyclerView.this.mEntries, i, i2);
                        i = i2;
                    }
                } else {
                    for (int i3 = adapterPosition; i3 > adapterPosition2; i3--) {
                        Collections.swap(DragRecyclerView.this.mEntries, i3, i3 - 1);
                    }
                }
                this.mToPosition = adapterPosition2;
                ((MyAdapter) DragRecyclerView.this.getAdapter()).notifyItemMoved(adapterPosition, adapterPosition2);
                return true;
            }

            public ViewHolder chooseDropTarget(ViewHolder viewHolder, List<ViewHolder> list, int i, int i2) {
                int width = viewHolder.itemView.getWidth();
                int i3 = width / 2;
                int i4 = i + i3;
                int height = viewHolder.itemView.getHeight() / 2;
                int i5 = i2 + height;
                ViewHolder viewHolder2 = null;
                double d = Double.MAX_VALUE;
                for (ViewHolder next : list) {
                    int left = (next.itemView.getLeft() + i3) - i4;
                    int top = (next.itemView.getTop() + height) - i5;
                    double sqrt = Math.sqrt((double) ((left * left) + (top * top)));
                    if (sqrt < d) {
                        if (sqrt < ((double) width) / 2.0d) {
                            viewHolder2 = next;
                        }
                        d = sqrt;
                    }
                }
                return viewHolder2;
            }

            public void onSelectedChanged(ViewHolder viewHolder, int i) {
                if (i != 0) {
                    scaleItemView(viewHolder, true);
                    this.mFromPosition = viewHolder.getAdapterPosition();
                    if (this.mFromPosition > 0 && this.mFromPosition <= DragRecyclerView.this.getChildCount()) {
                        this.mFromEntry = (DIYMainMapEntry) DragRecyclerView.this.mEntries.get(this.mFromPosition);
                    }
                }
                super.onSelectedChanged(viewHolder, i);
            }

            public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                scaleItemView(viewHolder, false);
                if (DragRecyclerView.this.mOnItemClickListener != null && this.mToPosition >= 0 && this.mToPosition < DragRecyclerView.this.mEntries.size()) {
                    DragRecyclerView.this.mOnItemClickListener.onItemChanged(this.mFromPosition, this.mFromEntry, this.mToPosition, (DIYMainMapEntry) DragRecyclerView.this.mEntries.get(this.mToPosition), DragRecyclerView.this.mEntries);
                }
                this.mFromPosition = -1;
                this.mToPosition = -1;
                this.mFromEntry = null;
            }

            private void scaleItemView(ViewHolder viewHolder, boolean z) {
                View findViewById = viewHolder.itemView.findViewById(R.id.icon_container);
                LayoutParams layoutParams = findViewById.getLayoutParams();
                if (z) {
                    layoutParams.width = (int) (((float) layoutParams.width) * 1.1f);
                    layoutParams.height = (int) (((float) layoutParams.height) * 1.1f);
                } else {
                    layoutParams.width = (int) (((float) layoutParams.width) / 1.1f);
                    layoutParams.height = (int) (((float) layoutParams.height) / 1.1f);
                    findViewById.setBackgroundResource(R.drawable.common_bg_pop_item);
                }
                findViewById.setLayoutParams(layoutParams);
                findViewById.setPadding(DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING, DragRecyclerView.PADDING);
            }
        });
        this.mContext = context;
        init();
    }

    private void init() {
        addOnItemTouchListener(new OnRecyclerItemClickListener(this));
        this.itemTouchHelper.a((RecyclerView) this);
        setLayoutManager(new GridLayoutManager(this.mContext, 3));
        this.mMyAdapter = new MyAdapter(this.mContext);
        setAdapter(this.mMyAdapter);
        PADDING = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_item_padding);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_width);
        int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_item_width);
        int dimensionPixelSize3 = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_padding_left_right);
        int dimensionPixelSize4 = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_popwindow_padding_top_bottom);
        int dimensionPixelSize5 = this.mContext.getResources().getDimensionPixelSize(R.dimen.diy_main_map_entry_recyclerview_margin) * 2;
        this.OFFSET_X = (float) (dimensionPixelSize3 + dimensionPixelSize5);
        this.OFFSET_Y = (float) (dimensionPixelSize4 + dimensionPixelSize5);
        this.WIDTH = dimensionPixelSize - (dimensionPixelSize2 - dimensionPixelSize5);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (motionEvent.getAction() == 1) {
            this.mMyAdapter.notifyDataSetChanged();
        }
        if (x - this.OFFSET_X >= 0.0f && y - this.OFFSET_Y >= 0.0f && x <= ((float) this.WIDTH) && y <= ((float) getLayoutParams().height)) {
            return super.onTouchEvent(motionEvent);
        }
        motionEvent.setAction(3);
        return super.onTouchEvent(motionEvent);
    }

    public void setEntries(List<DIYMainMapEntry> list, OnItemClickListener onItemClickListener) {
        this.mEntries = list;
        this.mOnItemClickListener = onItemClickListener;
        this.mMyAdapter.notifyDataSetChanged();
    }

    public void refresh() {
        this.mMyAdapter.notifyDataSetChanged();
    }
}
