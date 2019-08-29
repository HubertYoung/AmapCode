package com.alipay.mobile.beehive.photo.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.view.APLoadingView;
import com.alipay.mobile.beehive.photo.view.PhotoGrid;
import com.alipay.mobile.beehive.photo.view.RemotePhotoGridView;
import java.util.ArrayList;

public class RemotePhotoAdapter extends GridAdapter {
    public RemotePhotoAdapter(Context context, ArrayList<PhotoItem> photoList) {
        super(context, photoList);
        this.defaultDrawable = context.getResources().getDrawable(R.drawable.default_photo);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoItem photoInfo;
        PhotoGrid photoGrid;
        if (this.cameraItem == null || position != 0) {
            if (this.cameraItem != null && position > 0) {
                position--;
            }
            photoInfo = (PhotoItem) this.dataList.get(position);
        } else {
            position = -1;
            photoInfo = this.cameraItem;
        }
        if (position == getCount() - 1 && RemotePhotoGridView.LOADING_TAG.equals(photoInfo.getPhotoPath())) {
            APLoadingView loadingView = new APLoadingView(this.context);
            loadingView.setLayoutParams(new LayoutParams(this.context.getResources().getDisplayMetrics().widthPixels, -2));
            loadingView.setGravity(17);
            loadingView.setTag(RemotePhotoGridView.LOADING_TAG);
            return loadingView;
        } else if ("mock".equals(photoInfo.getPhotoPath())) {
            PhotoGrid photoGrid2 = (PhotoGrid) LayoutInflater.from(this.context).inflate(R.layout.photo_grid, parent, false);
            photoGrid2.setClickable(false);
            photoGrid2.setCheckable(false);
            photoGrid2.findViewById(R.id.rl_select).setVisibility(4);
            return photoGrid2;
        } else {
            if (convertView instanceof PhotoGrid) {
                photoGrid = (PhotoGrid) convertView;
            } else {
                photoGrid = (PhotoGrid) LayoutInflater.from(this.context).inflate(R.layout.photo_grid, parent, false);
                photoGrid.setDefaultDrawable(this.defaultDrawable);
            }
            photoGrid.setCheckable(false);
            photoGrid.setPhotoInfo(photoInfo, position);
            photoGrid.setGridListener(this.gridListener);
            return photoGrid;
        }
    }
}
