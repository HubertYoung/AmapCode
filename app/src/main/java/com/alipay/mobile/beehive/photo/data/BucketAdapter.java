package com.alipay.mobile.beehive.photo.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaVideoService;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.util.ViewHolder;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import java.util.List;

public class BucketAdapter extends PhotoAdapter<BucketInfo> {
    public BucketAdapter(Context context, List<BucketInfo> bucketList) {
        super(context, bucketList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.album_item, parent, false);
        }
        ViewHolder holder = ViewHolder.get(convertView);
        ImageView ivAlbum = (ImageView) holder.findViewById(R.id.iv_album);
        ImageView ivIndex = (ImageView) holder.findViewById(R.id.iv_selected);
        TextView tvName = (TextView) holder.findViewById(R.id.tv_album_name);
        TextView tvCount = (TextView) holder.findViewById(R.id.tv_album_count);
        BucketInfo albumInfo = (BucketInfo) this.dataList.get(position);
        if (albumInfo == null) {
            LoggerFactory.getTraceLogger().error((String) "BeehiveCrashProtect", (String) "Data size not match,will cause crash, return here!!");
        } else {
            Size size = PhotoUtil.reorderSize(PhotoUtil.dp2px(this.context, 125));
            PhotoInfo coverInfo = albumInfo.getPhoto();
            if (coverInfo.isVideo()) {
                ((MultimediaVideoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(MultimediaVideoService.class.getName())).loadAlbumVideo(coverInfo.getPhotoPath(), ivAlbum, null, null, ImageHelper.getBusinessId());
            } else {
                PhotoInfo pi = albumInfo.getPhoto();
                ImageHelper.loadWidthThumbnailPath(ivAlbum, pi.getPhotoPath(), null, size.getWidth(), size.getWidth(), null, pi.getThumbPath(), pi.getMediaId(), pi.getIsAlbumMedia());
            }
            ivIndex.setVisibility(albumInfo.isSelected() ? 0 : 8);
            tvName.setText(albumInfo.getName());
            tvCount.setVisibility(albumInfo.getCount() > 0 ? 0 : 8);
            tvCount.setText(albumInfo.getCount() + parent.getContext().getString(R.string.unit_pic));
        }
        return convertView;
    }
}
