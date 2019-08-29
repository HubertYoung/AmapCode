package com.autonavi.minimap.bundle.qrscan.scanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alipay.mobile.bqcscanservice.BQCScanService;
import com.autonavi.minimap.R;

public class TorchView extends LinearLayout {
    BQCScanService mBqcScanService;
    private ImageView torchIv;
    private TextView torchTv;

    public TorchView(Context context) {
        this(context, null);
    }

    public TorchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(1);
        setGravity(1);
        init();
    }

    private void init() {
        this.mBqcScanService = AjxScanManager.getInstance().getBQCScanService();
        LayoutInflater.from(getContext()).inflate(R.layout.torch_layout, this, true);
        this.torchIv = (ImageView) findViewById(R.id.torch_image_view);
        this.torchTv = (TextView) findViewById(R.id.torch_tips_view);
    }

    public void changeTorchState(boolean z) {
        this.torchIv.setImageDrawable(getResources().getDrawable(z ? R.drawable.torch_on : R.drawable.torch_off));
        CharSequence text = getResources().getText(z ? R.string.close_torch : R.string.open_torch);
        this.torchTv.setText(text);
        setContentDescription(text);
    }

    public void switchTorch() {
        if (this.mBqcScanService != null) {
            boolean z = !this.mBqcScanService.isTorchOn();
            this.mBqcScanService.setTorch(z);
            changeTorchState(z);
        }
    }

    public boolean isTorchOn() {
        if (this.mBqcScanService != null) {
            return this.mBqcScanService.isTorchOn();
        }
        return false;
    }
}
