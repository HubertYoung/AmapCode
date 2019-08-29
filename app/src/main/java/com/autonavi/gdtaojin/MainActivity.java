package com.autonavi.gdtaojin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.gdtaojin.camera.CameraInterface;
import com.autonavi.gdtaojin.camera.CameraInterface.onCaptureButtonClickListener;

public class MainActivity extends Activity {
    public static final int RESPONSE_CODE = 10;
    public static final String TAG = "test";
    private Button mbutton;

    public class buttonClickListener implements OnClickListener {
        public buttonClickListener() {
        }

        public void onClick(View view) {
            CameraInterface.setOnCaptureButtonClickListener(new onClickCaptureButtonListener());
            CameraInterface.setCameraPictureSize(1920);
            CameraInterface.setPictrueCompressSize(960);
            CameraInterface.setPictureCompressQuality(95);
            CameraInterface.setIsPhotoCompress(true);
            CameraInterface.setCameraFloder("test12333");
            CameraInterface.showCameraActivityForResult(MainActivity.this, 10);
        }
    }

    public class onClickCaptureButtonListener implements onCaptureButtonClickListener {
        public void onCapture() {
        }

        public void onCaptureEnd() {
        }

        public onClickCaptureButtonListener() {
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mbutton = new Button(this);
        setContentView(this.mbutton);
        this.mbutton.setOnClickListener(new buttonClickListener());
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 != 0 && i == 10) {
            String picturePathByURI = CameraInterface.getPicturePathByURI(intent.getData());
            CameraInterface.getShootedOrientation(intent);
            Toast.makeText(this, "mFilePath is ".concat(String.valueOf(picturePathByURI)), 1).show();
            StringBuilder sb = new StringBuilder("图片信息：\n");
            sb.append("相机直出宽度：");
            sb.append(intent.getIntExtra(CameraControllerManager.RESULT_KEY_PICTURE_WIDTH, -1));
            sb.append("\n");
            sb.append("相机直出高度：");
            sb.append(intent.getIntExtra(CameraControllerManager.RESULT_KEY_PICTURE_HEIGHT, -1));
            sb.append("\n");
            sb.append("压缩后宽度：");
            sb.append(intent.getIntExtra(CameraControllerManager.RESULT_KEY_COMPRESSED_PICTURE_WIDTH, -1));
            sb.append("\n");
            sb.append("压缩后高度：");
            sb.append(intent.getIntExtra(CameraControllerManager.RESULT_KEY_COMPRESSED_PICTURE_HEIGHT, -1));
            this.mbutton.setText(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }
}
