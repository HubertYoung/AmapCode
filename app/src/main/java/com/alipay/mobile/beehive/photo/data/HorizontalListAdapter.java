package com.alipay.mobile.beehive.photo.data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.Size;
import com.alipay.mobile.antui.basic.AUHorizontalListView.LayoutParams;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.rpc.RpcRunConfig;
import com.alipay.mobile.beehive.rpc.RpcRunner;
import com.alipay.mobile.beehive.rpc.RpcSubscriber;
import com.alipay.mobile.beehive.rpc.RpcTask;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;
import java.util.ArrayList;
import java.util.List;

public class HorizontalListAdapter extends PhotoAdapter<PhotoItem> {
    public static final String TAG = "HorizontalListAdapter";
    public Drawable defaultDrawable = new ColorDrawable(Color.argb(218, 237, FavoritesPointFragment.REQUEST_COMPNAY, 240));
    private int height;
    Bundle param;
    PhotoRpcRunnable refreshRpcRunnable;
    private int width;

    public HorizontalListAdapter(Context context, PhotoRpcRunnable refreshRpcRunnable2, int width2, int height2, Bundle param2) {
        super(context, null);
        this.width = width2;
        this.height = height2;
        this.refreshRpcRunnable = refreshRpcRunnable2;
        this.param = param2;
        render();
    }

    /* access modifiers changed from: 0000 */
    public void render() {
        ArrayList photoItems = null;
        if (!(this.param == null || this.param.get(PhotoParam.REMOTEPHOTO_PRELOADDATA) == null)) {
            try {
                photoItems = this.param.getParcelableArrayList(PhotoParam.REMOTEPHOTO_PRELOADDATA);
                refreshData(photoItems);
            } catch (Exception e) {
                PhotoLogger.error(TAG, "preload photoItems fail", e);
            }
        }
        if (this.refreshRpcRunnable == null) {
            return;
        }
        if (photoItems == null || photoItems.isEmpty()) {
            new RpcRunner(RpcRunConfig.createBackgroundConfig(), this.refreshRpcRunnable, new RpcSubscriber<PhotoResult>() {
                /* access modifiers changed from: private */
                /* renamed from: a */
                public void onSuccess(PhotoResult result) {
                    HorizontalListAdapter.this.refreshData(result.photoItems);
                    PhotoLogger.verbose(HorizontalListAdapter.TAG, "refreshRpc onSuccess");
                }

                /* access modifiers changed from: private */
                /* renamed from: b */
                public void onFail(PhotoResult result) {
                    super.onFail(result);
                    PhotoLogger.warn((String) HorizontalListAdapter.TAG, (String) "refreshRpc onFail");
                }

                /* access modifiers changed from: protected */
                public final void onException(Exception ex, RpcTask task) {
                    super.onException(ex, task);
                    PhotoLogger.error(HorizontalListAdapter.TAG, "refreshRpc onException", ex);
                }
            }).start(new Object[0]);
        }
    }

    /* access modifiers changed from: 0000 */
    public void refreshData(List<PhotoItem> photoItems) {
        this.dataList.clear();
        addDatas(photoItems);
        PhotoLogger.verbose(TAG, "refresh data");
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv;
        if (convertView instanceof ImageView) {
            iv = (ImageView) convertView;
        } else {
            iv = new ImageView(this.context);
            iv.setScaleType(ScaleType.CENTER_CROP);
            iv.setLayoutParams(new LayoutParams(this.width, this.height));
        }
        PhotoItem info = (PhotoItem) this.dataList.get(position);
        if (info.getPhoto() != null) {
            iv.setImageDrawable(info.getPhoto());
        } else if (info.getThumb() != null) {
            iv.setImageDrawable(info.getThumb());
        } else {
            Size reorderSize = PhotoUtil.reorderSize(new Size(this.width, this.height));
            ImageHelper.load(iv, info.getPhotoPath(), this.defaultDrawable, reorderSize.getWidth(), reorderSize.getHeight());
            PhotoLogger.verbose(TAG, "load img:" + info.getPhotoPath() + " iv:" + reorderSize.getWidth() + Token.SEPARATOR + reorderSize.getHeight());
        }
        return iv;
    }

    public void preview(int index) {
        List photoInfos = new ArrayList();
        Bundle bundle = new Bundle();
        bundle.putInt(PhotoParam.PREVIEW_POSITION, index);
        bundle.putBoolean(PhotoParam.PREVIEW_CLICK_EXIT, true);
        photoInfos.addAll(getData());
        ((PhotoService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(PhotoService.class.getName())).browsePhoto(LauncherApplicationAgent.getInstance().getMicroApplicationContext().getTopApplication(), photoInfos, bundle, null);
    }
}
