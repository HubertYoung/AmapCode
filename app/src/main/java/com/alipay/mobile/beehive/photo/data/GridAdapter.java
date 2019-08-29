package com.alipay.mobile.beehive.photo.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.view.PhotoGrid;
import java.util.ArrayList;

public class GridAdapter extends PhotoAdapter<PhotoItem> {
    public static final String TAG = "GridAdapter";
    public PhotoItem cameraItem;
    private boolean checkable = true;
    public Drawable defaultDrawable;
    protected OnGridListener gridListener;
    private boolean isEnableVideoEdit;

    public interface OnGridListener {
        void onGridChecked(PhotoGrid photoGrid, int i);

        void onGridClick(PhotoGrid photoGrid, int i);
    }

    public void setEnableVideoEdit(boolean enable) {
        this.isEnableVideoEdit = enable;
    }

    public GridAdapter(Context context, ArrayList<PhotoItem> photoList) {
        super(context, photoList);
        this.defaultDrawable = context.getResources().getDrawable(R.drawable.default_photo);
    }

    public GridAdapter(Context context, ArrayList<PhotoItem> photoList, boolean checkable2) {
        super(context, photoList);
        this.checkable = checkable2;
        this.defaultDrawable = context.getResources().getDrawable(R.drawable.default_photo);
    }

    public int getCount() {
        int count = this.dataList == null ? 0 : this.dataList.size();
        if (this.cameraItem != null) {
            return count + 1;
        }
        return count;
    }

    public void setCheckable(boolean checkable2) {
        this.checkable = checkable2;
    }

    public void setGridListener(OnGridListener gridListener2) {
        this.gridListener = gridListener2;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoGrid photoGrid;
        PhotoItem photoInfo;
        if (convertView instanceof PhotoGrid) {
            photoGrid = (PhotoGrid) convertView;
        } else {
            photoGrid = (PhotoGrid) LayoutInflater.from(this.context).inflate(R.layout.photo_grid, parent, false);
            photoGrid.setDefaultDrawable(this.defaultDrawable);
        }
        if (this.cameraItem == null || position != 0) {
            if (this.cameraItem != null && position > 0) {
                position--;
            }
            photoInfo = (PhotoItem) this.dataList.get(position);
        } else {
            position = -1;
            photoInfo = this.cameraItem;
        }
        photoGrid.setCheckable(this.checkable);
        photoGrid.setEnableVideoEdit(this.isEnableVideoEdit);
        photoGrid.setPhotoInfo(photoInfo, position);
        photoGrid.setGridListener(this.gridListener);
        return photoGrid;
    }
}
