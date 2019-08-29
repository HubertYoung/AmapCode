package com.alipay.mobile.beehive.photo.view.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.alipay.mobile.antui.utils.DensityUtil;
import com.alipay.mobile.beehive.R;
import com.alipay.mobile.beehive.photo.view.CircleProgressBar;

public class VideoCompressDialog extends DialogFragment {
    private static final String COMPRESS_DIALOG_TAG = "compressDialog";
    /* access modifiers changed from: private */
    public CircleProgressBar mProgressBar;
    /* access modifiers changed from: private */
    public String mProgressPattern;
    /* access modifiers changed from: private */
    public TextView tvProgressText;

    public void updateProgress(final int progress) {
        if (this.mProgressBar != null && this.tvProgressText != null) {
            this.mProgressBar.post(new Runnable() {
                public final void run() {
                    VideoCompressDialog.this.mProgressBar.setProgress(progress);
                    VideoCompressDialog.this.tvProgressText.setText(String.format(VideoCompressDialog.this.mProgressPattern, new Object[]{Integer.valueOf(progress)}));
                }
            });
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(0, 16973840);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_video_compress_progress, container, false);
        this.mProgressBar = (CircleProgressBar) v.findViewById(R.id.v_compress_progress);
        this.mProgressBar.setRadius(DensityUtil.dip2px(getContext(), 18.0f), DensityUtil.dip2px(getContext(), 15.0f));
        this.tvProgressText = (TextView) v.findViewById(R.id.tv_compress_progress);
        this.mProgressPattern = v.getContext().getString(R.string.str_compress_progress_pattern);
        return v;
    }

    public static VideoCompressDialog buildAndShow(FragmentActivity activity) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag(COMPRESS_DIALOG_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        VideoCompressDialog dialog = new VideoCompressDialog();
        dialog.show(ft, (String) COMPRESS_DIALOG_TAG);
        dialog.setCancelable(false);
        return dialog;
    }
}
