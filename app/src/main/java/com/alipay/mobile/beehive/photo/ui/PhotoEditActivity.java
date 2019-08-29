package com.alipay.mobile.beehive.photo.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.data.PhotoContext;
import com.alipay.mobile.beehive.photo.util.PhotoLogger;
import com.alipay.mobile.beehive.photo.util.PhotoUtil;
import com.alipay.mobile.beehive.photo.view.PhotoView;
import com.alipay.mobile.beehive.photo.wrapper.ImageHelper;
import com.alipay.mobile.beehive.photo.wrapper.PhotoActivity;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.util.MicroServiceUtil;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.framework.service.common.TaskScheduleService.ScheduleType;
import java.io.File;

public class PhotoEditActivity extends PhotoActivity implements OnClickListener {
    public static final String TAG = "PhotoEditActivity";
    private boolean afterSaveInstanceState;
    private Button btCancel;
    private Button btFinish;
    private Button btRotate;
    private String contextIndex;
    private boolean cropSquare;
    /* access modifiers changed from: private */
    public PhotoContext photoContext;
    /* access modifiers changed from: private */
    public PhotoInfo photoInfo;
    private PhotoView photoView;
    private String saveFolder;
    private boolean saveOnEdit;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        Bundle bundle;
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        this.afterSaveInstanceState = false;
        if (savedInstanceState == null) {
            bundle = getIntent().getExtras();
            if (bundle == null) {
                finish();
                return;
            }
        } else {
            bundle = savedInstanceState;
        }
        if (bundle == null) {
            Log.e(TAG, "invalid bundle info");
            finish();
            return;
        }
        ImageHelper.updateBusinessId(bundle.getString("businessId"), TAG);
        this.contextIndex = bundle.getString(PhotoParam.CONTEXT_INDEX);
        this.photoContext = PhotoContext.get(this.contextIndex);
        PhotoContext.remove(this.contextIndex);
        this.saveOnEdit = bundle.getBoolean(PhotoParam.SAVE_ON_EDIT, false);
        this.cropSquare = bundle.getBoolean(PhotoParam.CROP_SQUARE, false);
        this.saveFolder = bundle.getString(PhotoParam.SAVE_FOLDER);
        if (TextUtils.isEmpty(this.saveFolder)) {
            this.saveFolder = PhotoUtil.getDefaultPhotoFolder();
        }
        this.photoInfo = initPhotoInfo();
        if (this.photoInfo == null) {
            Log.e(TAG, "invalid photoInfo");
            finish();
            return;
        }
        setContentView(R.layout.activity_photo_edit);
        this.photoView = (PhotoView) findViewById(R.id.iv_content);
        this.photoView.setInitToMaxSquare(bundle.getBoolean(PhotoParam.INIT_TO_CROP_MAX_SQUARE, false));
        this.photoView.setEnableCrop(true);
        this.photoView.setCropSquare(this.cropSquare);
        if (this.photoInfo.getPhoto() != null) {
            this.photoView.setImageDrawable(this.photoInfo.getPhoto());
        } else {
            ImageHelper.load(this.photoView, this.photoInfo.getPhotoPath(), null, -1, -1);
        }
        this.btFinish = (Button) findViewById(R.id.bt_done);
        this.btFinish.setOnClickListener(this);
        this.btCancel = (Button) findViewById(R.id.bt_cancel);
        this.btCancel.setOnClickListener(this);
        this.btRotate = (Button) findViewById(R.id.bt_rotate);
        this.btRotate.setOnClickListener(this);
    }

    private PhotoInfo initPhotoInfo() {
        Bundle bundle = getIntent().getExtras();
        PhotoInfo info = null;
        if (bundle.containsKey(PhotoParam.PHOTO_INFO)) {
            info = (PhotoInfo) bundle.getParcelable(PhotoParam.PHOTO_INFO);
        }
        return info != null ? info : this.photoContext.editPhotoInfo;
    }

    public void onClick(View view) {
        if (view.equals(this.btFinish)) {
            saveEdit();
        } else if (view.equals(this.btRotate)) {
            this.photoView.postRotate(90.0f);
        } else if (view.equals(this.btCancel)) {
            finish();
        }
    }

    private void saveEdit() {
        Bitmap bitmap = this.photoView.applyCrop();
        Bundle bundle = new Bundle();
        if (this.saveOnEdit) {
            saveImageToDisk(bitmap, bundle);
        } else {
            postNotifyEditFinish(bitmap, bundle);
        }
    }

    private void saveImageToDisk(final Bitmap bitmap, final Bundle bundle) {
        final String absPath = PhotoUtil.createPhoto(this.saveFolder);
        if (TextUtils.isEmpty(absPath)) {
            Log.e(TAG, "failed to create local file!");
            postNotifyEditFinish(bitmap, bundle);
            return;
        }
        showProgressDialog("", false, null);
        TaskScheduleService ts = (TaskScheduleService) MicroServiceUtil.getMicroService(TaskScheduleService.class);
        if (ts != null) {
            ts.acquireExecutor(ScheduleType.URGENT).execute(new Runnable() {
                public final void run() {
                    try {
                        bundle.putString(PhotoParam.SAVE_PATH, absPath);
                        if (!PhotoUtil.savePhoto(bitmap, new File(absPath))) {
                            Log.e(PhotoEditActivity.TAG, "failed to save local file!");
                        }
                    } finally {
                        PhotoEditActivity.this.dismissProgressDialog();
                        PhotoEditActivity.this.postNotifyEditFinish(bitmap, bundle);
                    }
                }
            });
            return;
        }
        PhotoLogger.warn((String) TAG, (String) "Get TaskScheduleService returned null!");
        postNotifyEditFinish(bitmap, bundle);
    }

    /* access modifiers changed from: private */
    public void postNotifyEditFinish(final Bitmap bitmap, final Bundle bundle) {
        runOnUiThread(new Runnable() {
            public final void run() {
                if (!(bitmap == null || PhotoEditActivity.this.photoContext == null || PhotoEditActivity.this.photoContext.editListener == null)) {
                    PhotoEditActivity.this.photoContext.editListener.onPhotoEdited(PhotoEditActivity.this.photoInfo, bundle, bitmap);
                    PhotoEditActivity.this.photoContext.editSent = true;
                }
                PhotoEditActivity.this.finish();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.afterSaveInstanceState = true;
        bundle.putAll(getIntent().getExtras());
        bundle.putString(PhotoParam.CONTEXT_INDEX, this.contextIndex);
        PhotoContext.contextMap.put(this.contextIndex, this.photoContext);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.photoContext != null && !this.afterSaveInstanceState) {
            PhotoContext.contextMap.clear();
        }
        this.photoContext = null;
    }

    public void finish() {
        super.finish();
        PhotoContext.remove(this.contextIndex);
        if (this.photoContext != null) {
            if (this.photoContext.editListener != null && !this.photoContext.editSent) {
                this.photoContext.editListener.onEditCanceled(this.photoInfo);
            }
            this.photoContext.editListener = null;
            return;
        }
        PhotoLogger.w(TAG, "finish:### but photoContext is Null!");
    }

    /* access modifiers changed from: protected */
    public Object getSpmObject() {
        return this;
    }

    /* access modifiers changed from: protected */
    public String getSpmID() {
        return "a310.b3484";
    }
}
