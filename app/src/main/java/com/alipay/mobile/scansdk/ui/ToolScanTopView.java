package com.alipay.mobile.scansdk.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alipay.android.phone.scancode.export.R;
import com.alipay.mobile.beehive.service.PhotoInfo;
import com.alipay.mobile.beehive.service.PhotoParam;
import com.alipay.mobile.beehive.service.PhotoSelectListener;
import com.alipay.mobile.beehive.service.PhotoService;
import com.alipay.mobile.bqcscanservice.BQCScanResult;
import com.alipay.mobile.bqcscanservice.Logger;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.mobile.mascanengine.MaScanEngineService;
import com.alipay.mobile.mascanengine.MaScanResult;
import com.alipay.mobile.mascanengine.MultiMaScanResult;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.scansdk.constant.Constants;
import java.util.List;

public class ToolScanTopView extends BaseScanTopView {
    public static final String TAG = "ToolScanTopView";
    /* access modifiers changed from: private */
    public Activity activity;
    private View backView;
    /* access modifiers changed from: private */
    public ImageView mAlbumView;
    private ScaleFinderView scaleFinderView;
    private ScanRayView scanRayView;
    private TextView tipTextView;
    private TextView titleTextView;

    public ToolScanTopView(Context context) {
        super(context);
        init(context);
    }

    public ToolScanTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToolScanTopView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context ctx) {
        this.activity = (Activity) ctx;
        LayoutInflater.from(ctx).inflate(R.layout.view_ma_tool_top, this, true);
        this.scaleFinderView = (ScaleFinderView) findViewById(R.id.scale_finder_view);
        this.scaleFinderView.setScanType(ScanType.SCAN_MA);
        this.scanRayView = (ScanRayView) findViewById(R.id.scan_ray_view);
        this.scanRayView.setFinderView(this.scaleFinderView);
        this.backView = findViewById(R.id.back_press);
        this.backView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (ToolScanTopView.this.activity != null) {
                    ToolScanTopView.this.activity.finish();
                }
            }
        });
        this.titleTextView = (TextView) findViewById(R.id.title_text);
        this.tipTextView = (TextView) findViewById(R.id.txt_qr_barcode_tip);
        this.mAlbumView = (ImageView) findViewById(R.id.ma_album);
        this.mAlbumView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ToolScanTopView.this.startSelectPic();
            }
        });
    }

    /* access modifiers changed from: private */
    public void startSelectPic() {
        MicroApplicationContext micro = LauncherApplicationAgent.getInstance().getMicroApplicationContext();
        PhotoService photoService = (PhotoService) micro.getExtServiceByInterface(PhotoService.class.getName());
        Bundle bundle = new Bundle();
        bundle.putString("businessId", H5Utils.SCAN_APP_ID);
        bundle.putInt(PhotoParam.MAX_SELECT, 1);
        bundle.putBoolean(PhotoParam.ENABLE_CAMERA, false);
        bundle.putBoolean(PhotoParam.ENABLE_PREVIEW, false);
        if (photoService != null) {
            photoService.selectPhoto(micro.findTopRunningApp(), bundle, new PhotoSelectListener() {
                public void onPhotoSelected(List<PhotoInfo> list, Bundle bundle) {
                    ToolScanTopView.this.mAlbumView.setEnabled(true);
                    Logger.d(ToolScanTopView.TAG, "onPhotoSelected");
                    if (list != null && list.size() > 0) {
                        PhotoInfo photoInfo = list.get(0);
                        if (photoInfo != null && !TextUtils.isEmpty(photoInfo.getPhotoPath())) {
                            try {
                                ToolScanTopView.this.executeDecodeQrImageFromPath(photoInfo.getPhotoPath().substring(7));
                            } catch (Exception ex) {
                                Logger.e(ToolScanTopView.TAG, "executeDecodeQrImageFromPath error: " + ex.getMessage());
                            }
                        } else if (ToolScanTopView.this.topViewCallback != null) {
                            ToolScanTopView.this.topViewCallback.startPreview();
                        }
                    } else if (ToolScanTopView.this.topViewCallback != null) {
                        ToolScanTopView.this.topViewCallback.startPreview();
                    }
                }

                public void onSelectCanceled() {
                    ToolScanTopView.this.mAlbumView.setEnabled(true);
                    if (ToolScanTopView.this.topViewCallback != null) {
                        ToolScanTopView.this.topViewCallback.startPreview();
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void executeDecodeQrImageFromPath(final String path) {
        if (!TextUtils.isEmpty(path)) {
            Logger.d(TAG, "process album scan");
            new Thread(new Runnable() {
                public void run() {
                    final MaScanResult result = ((MaScanEngineService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MaScanEngineService.class.getName())).process(path);
                    if (ToolScanTopView.this.activity != null && !ToolScanTopView.this.activity.isFinishing()) {
                        ToolScanTopView.this.activity.runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    ToolScanTopView.this.onResultMa(result);
                                } catch (Exception ex) {
                                    Logger.e(ToolScanTopView.TAG, "onAlbumResult error: " + ex.getMessage());
                                }
                            }
                        });
                    }
                }
            }).start();
        } else if (this.topViewCallback != null) {
            this.topViewCallback.startPreview();
        }
    }

    public void onArguments(Bundle bundle) {
        super.onArguments(bundle);
        String titleText = bundle.getString(Constants.SERVICE_TITLE_TEXT, null);
        if (!TextUtils.isEmpty(titleText)) {
            this.titleTextView.setText(titleText);
        }
        String tipText = bundle.getString(Constants.SERVICE_VIEW_TEXT, null);
        if (!TextUtils.isEmpty(tipText)) {
            this.tipTextView.setText(tipText);
        }
        if (TextUtils.equals(bundle.getString(Constants.KEY_MA_UI_TYPE, Constants.SCAN_QR), Constants.SCAN_BAR)) {
            LayoutParams layoutParams = (LayoutParams) this.scanRayView.getLayoutParams();
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.bar_scan_ray_view_height);
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.bar_scan_ray_view_width);
            this.scanRayView.setLayoutParams(layoutParams);
        }
        if (bundle.getBoolean(Constants.SERVICE_NO_ALBUM, false)) {
            this.mAlbumView.setVisibility(8);
        }
    }

    public void onInitCamera() {
        this.scanRayView.stopScaleAnimation();
    }

    public void onStartScan() {
        this.scanRayView.startScaleAnimation();
    }

    public void onPreviewShow() {
    }

    public void onStopScan() {
    }

    public void onCameraOpenFailed() {
    }

    public void onDestroy() {
    }

    public void onTorchState(boolean isOn) {
    }

    public void onResultMa(BQCScanResult maScanResult) {
        if (this.mScanRouter == null) {
            return;
        }
        if (maScanResult instanceof MaScanResult) {
            this.mScanRouter.routeBarQrCode((MaScanResult) maScanResult);
        } else if (maScanResult instanceof MultiMaScanResult) {
            MultiMaScanResult multiResult = (MultiMaScanResult) maScanResult;
            if (multiResult.maScanResults != null && multiResult.maScanResults.length > 0) {
                this.mScanRouter.routeBarQrCode(multiResult.maScanResults[0]);
            }
        }
    }

    public Rect getScanRect(Camera camera, int previewWidth, int previewHeight) {
        if (camera == null) {
            return null;
        }
        int[] location = new int[2];
        this.scanRayView.getLocationOnScreen(location);
        Rect r = new Rect(location[0], location[1], location[0] + this.scanRayView.getWidth(), location[1] + this.scanRayView.getHeight());
        try {
            Size size = camera.getParameters().getPreviewSize();
            if (size == null) {
                return null;
            }
            double rateX = ((double) size.height) / ((double) previewWidth);
            double rateY = ((double) size.width) / ((double) previewHeight);
            int expandX = (int) (((double) this.scanRayView.getWidth()) * 0.05d);
            int expandY = (int) (((double) this.scanRayView.getHeight()) * 0.05d);
            Rect rect = new Rect((int) (((double) (r.top - expandY)) * rateY), (int) (((double) (r.left - expandX)) * rateX), (int) (((double) (r.bottom + expandY)) * rateY), (int) (((double) (r.right + expandX)) * rateX));
            Rect finalRect = new Rect(rect.left < 0 ? 0 : rect.left, rect.top < 0 ? 0 : rect.top, rect.width() > size.width ? size.width : rect.width(), rect.height() > size.height ? size.height : rect.height());
            Rect rect2 = new Rect((finalRect.left / 4) * 4, (finalRect.top / 4) * 4, (finalRect.right / 4) * 4, (finalRect.bottom / 4) * 4);
            int max = Math.max(rect2.right, rect2.bottom);
            int diff = (Math.abs(rect2.right - rect2.bottom) / 8) * 4;
            if (rect2.right > rect2.bottom) {
                Rect rect3 = new Rect(rect2.left, rect2.top - diff, max, max);
                return rect3;
            }
            Rect rect4 = new Rect(rect2.left - diff, rect2.top, max, max);
            return rect4;
        } catch (Exception e) {
            return null;
        }
    }
}
