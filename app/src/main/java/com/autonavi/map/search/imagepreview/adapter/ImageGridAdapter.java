package com.autonavi.map.search.imagepreview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.bundle.feedback.ajx.ModuleFeedBack;
import com.autonavi.common.imageloader.ImageLoader.LoadedFrom;
import com.autonavi.minimap.R;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class ImageGridAdapter extends BaseAdapter {
    private final Context mContext;
    private ArrayList<cal> mImageInfoList = new ArrayList<>();
    private LayoutParams mImageViewLayoutParams;
    private LayoutInflater mInflater;
    private int mItemHeight = 0;
    private int mNumColumns = 0;
    private String mPhotoType = null;

    static class a {
        ImageView a;
        RelativeLayout b;
        TextView c;
        RelativeLayout d;
        TextView e;
        TextView f;
        TextView g;
        LinearLayout h;
        ImageView i;
        View j;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public int getViewTypeCount() {
        return 2;
    }

    public boolean hasStableIds() {
        return true;
    }

    public ImageGridAdapter(Context context, ArrayList<cal> arrayList, String str) {
        this.mContext = context;
        this.mImageInfoList = arrayList;
        this.mPhotoType = str;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mImageViewLayoutParams = new LayoutParams(-1, -1);
    }

    public int getCount() {
        if (getNumColumns() == 0) {
            return 0;
        }
        return this.mImageInfoList.size() + this.mNumColumns;
    }

    public Object getItem(int i) {
        if (i < this.mNumColumns) {
            return null;
        }
        return this.mImageInfoList.get(i - this.mNumColumns);
    }

    public long getItemId(int i) {
        if (i < this.mNumColumns) {
            return 0;
        }
        return (long) (i - this.mNumColumns);
    }

    public int getItemViewType(int i) {
        return i < this.mNumColumns ? 1 : 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (i < this.mNumColumns) {
            if (view == null) {
                view = this.mInflater.inflate(R.layout.grid_image_item, null);
                view.findViewById(R.id.grid_poi_layout).setVisibility(8);
                view.findViewById(R.id.grid_food_layout).setVisibility(8);
                view.setVisibility(8);
            }
            return view;
        }
        if (view == null) {
            view = this.mInflater.inflate(R.layout.grid_image_item, null);
            view.setLayoutParams(this.mImageViewLayoutParams);
            a aVar = new a(0);
            aVar.a = (ImageView) view.findViewById(R.id.grid_image_view);
            aVar.a.setScaleType(ScaleType.CENTER_CROP);
            aVar.b = (RelativeLayout) view.findViewById(R.id.grid_poi_layout);
            aVar.d = (RelativeLayout) view.findViewById(R.id.grid_food_layout);
            if (this.mPhotoType.contains(ModuleFeedBack.RECOMMEND)) {
                aVar.b.setVisibility(8);
                aVar.d.setVisibility(0);
                aVar.e = (TextView) view.findViewById(R.id.grid_foot_title);
                aVar.f = (TextView) view.findViewById(R.id.grid_foot_message);
                aVar.g = (TextView) view.findViewById(R.id.grid_foot_recommend);
                aVar.h = (LinearLayout) view.findViewById(R.id.grid_food_bottom_layout);
                aVar.i = (ImageView) view.findViewById(R.id.grid_foot_recommend_img);
                aVar.j = view.findViewById(R.id.vertical_line);
            } else {
                aVar.b.setVisibility(0);
                aVar.d.setVisibility(8);
                view.findViewById(R.id.grid_poi_layout).setVisibility(0);
                view.findViewById(R.id.grid_food_layout).setVisibility(8);
                aVar.c = (TextView) view.findViewById(R.id.grid_title);
            }
            view.setTag(aVar);
        }
        a aVar2 = (a) view.getTag();
        if (view.getLayoutParams().height != this.mItemHeight) {
            view.setLayoutParams(this.mImageViewLayoutParams);
        }
        final SoftReference softReference = new SoftReference(aVar2.a);
        ((ImageView) softReference.get()).setImageResource(R.drawable.housenob_image_add);
        String str = this.mImageInfoList.get(i - this.mNumColumns).e;
        Bitmap a2 = ahc.a(str);
        if (a2 != null) {
            aVar2.a.setImageBitmap(a2);
        } else {
            ko.a(aVar2.a, str, null, R.drawable.housenob_image_add, new bkf() {
                public final void onBitmapFailed(Drawable drawable) {
                }

                public final void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
                }

                public final void onPrepareLoad(Drawable drawable) {
                    if (softReference != null) {
                        ((ImageView) softReference.get()).setImageDrawable(drawable);
                    }
                }
            });
        }
        if (this.mPhotoType.contains(ModuleFeedBack.RECOMMEND)) {
            aVar2.e.setText(this.mImageInfoList.get(i - this.mNumColumns).b);
            String str2 = this.mImageInfoList.get(i - this.mNumColumns).c;
            String str3 = this.mImageInfoList.get(i - this.mNumColumns).d;
            if (TextUtils.isEmpty(str3)) {
                aVar2.i.setVisibility(8);
            } else {
                aVar2.i.setVisibility(0);
            }
            if (!TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str3)) {
                aVar2.h.setVisibility(0);
                aVar2.j.setVisibility(0);
                aVar2.f.setText(this.mImageInfoList.get(i - this.mNumColumns).c);
                aVar2.g.setText(this.mImageInfoList.get(i - this.mNumColumns).d);
            } else {
                aVar2.h.setVisibility(8);
                aVar2.j.setVisibility(8);
            }
        } else {
            aVar2.c.setText(this.mImageInfoList.get(i - this.mNumColumns).b);
        }
        return view;
    }

    public void setItemHeight(int i) {
        if (i != this.mItemHeight) {
            this.mItemHeight = i;
            this.mImageViewLayoutParams = new LayoutParams(-1, this.mItemHeight);
            notifyDataSetChanged();
        }
    }

    public void setNumColumns(int i) {
        this.mNumColumns = i;
    }

    public int getNumColumns() {
        return this.mNumColumns;
    }
}
