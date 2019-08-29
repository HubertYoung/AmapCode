package com.alipay.mobile.beehive.photo.ui;

import android.os.Bundle;
import android.widget.RelativeLayout;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.data.PhotoItem;
import com.alipay.mobile.beehive.photo.data.PhotoResult;
import com.alipay.mobile.beehive.photo.data.PhotoRpcRunnable;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.view.RemotePhotoGridView;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.framework.app.ui.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class RemotePhotoGridActivity extends BaseActivity {
    /* access modifiers changed from: private */
    public int mPageNum;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_remote_photo_grid);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        PhotoRpcRunnable mRefreshRpcRunnable = new PhotoRpcRunnable() {
            public final PhotoResult execute(Object... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    PhotoLogger.error((String) "RemotePhoto", (Throwable) e);
                }
                List photoItems = new ArrayList();
                while (photoItems.size() < 21) {
                    photoItems.add(new PhotoItem((String) "http://www.sj88.com/attachments/201412/26/13/1s7vdu1do.jpg"));
                }
                PhotoResult result = new PhotoResult();
                result.success = true;
                result.photoItems = photoItems;
                result.nextCursor = "1";
                RemotePhotoGridActivity.this.mPageNum = 0;
                return result;
            }
        };
        PhotoRpcRunnable mLoadMoreRpcRunnable = new PhotoRpcRunnable() {
            public final PhotoResult execute(Object... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    PhotoLogger.error((String) "RemotePhoto", (Throwable) e);
                }
                List photoItems = new ArrayList();
                while (photoItems.size() < 21) {
                    photoItems.add(new PhotoItem((String) "http://www.sj88.com/attachments/201412/26/13/1s7vdu1do.jpg"));
                }
                PhotoResult result = new PhotoResult();
                result.success = true;
                result.photoItems = photoItems;
                RemotePhotoGridActivity.this.mPageNum = RemotePhotoGridActivity.this.mPageNum + 1;
                if (RemotePhotoGridActivity.this.mPageNum < 4) {
                    result.nextCursor = String.valueOf(RemotePhotoGridActivity.this.mPageNum);
                }
                return result;
            }
        };
        Bundle params = new Bundle();
        ArrayList photoItems = new ArrayList();
        while (photoItems.size() < 40) {
            photoItems.add(new PhotoItem((String) "http://www.sj88.com/attachments/201412/26/13/1s7vdu1do.jpg"));
        }
        params.putParcelableArrayList(PhotoParam.REMOTEPHOTO_PRELOADDATA, photoItems);
        params.putString(PhotoParam.REMOTEPHOTO_CURSOR, "123");
        params.putInt(PhotoParam.REMOTEPHOTO_NUMCOLUMNS, 4);
        container.addView(new RemotePhotoGridView(this, null, mRefreshRpcRunnable, mLoadMoreRpcRunnable, params));
    }
}
