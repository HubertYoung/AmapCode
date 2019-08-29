package com.autonavi.gdtaojin.basemap;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.alipay.mobile.common.share.widget.ResUtils;
import org.json.JSONObject;

public class SimplePictureDialog extends CompatDialog {
    public static final String JS_KEY_PIC_NAME = "picName";
    public static final String JS_KEY_TEXT = "text";
    public static final String JS_KEY_TITLE = "title";
    private String mExampleContent;
    private ImageView mExampleIv;
    private String mExamplePicName;
    private TextView mExampleTextTv;
    private String mExampleTitle;
    private TextView mExampleTitleTv;
    private Resources mResouse = null;

    public SimplePictureDialog(Activity activity, String str) {
        super(activity, activity.getResources().getIdentifier("SamplePictureDialog", ResUtils.STYLE, activity.getPackageName()));
        this.mResouse = activity.getResources();
        setContentView(this.mResouse.getIdentifier("sample_photo_dialog", ResUtils.LAYOUT, activity.getPackageName()));
        Window window = getWindow();
        window.setGravity(16);
        LayoutParams attributes = window.getAttributes();
        attributes.alpha = 0.8f;
        attributes.width = -1;
        attributes.height = dp2px(activity, 280.0f);
        window.setAttributes(attributes);
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.mExamplePicName = jSONObject.optString(JS_KEY_PIC_NAME, "");
            this.mExampleContent = jSONObject.optString("text", "");
            this.mExampleTitle = jSONObject.optString("title", "");
            this.mExampleTextTv = (TextView) findViewById(this.mResouse.getIdentifier("tvSampleText", "id", activity.getPackageName()));
            this.mExampleTitleTv = (TextView) findViewById(this.mResouse.getIdentifier("tvSampleTitle", "id", activity.getPackageName()));
            this.mExampleIv = (ImageView) findViewById(this.mResouse.getIdentifier("ivSamplePicture", "id", activity.getPackageName()));
            this.mExampleTextTv.setText(this.mExampleContent);
            this.mExampleTitleTv.setText(this.mExampleTitle);
            Resources resources = this.mResouse;
            StringBuilder sb = new StringBuilder("example_");
            sb.append(this.mExamplePicName);
            int identifier = resources.getIdentifier(sb.toString(), ResUtils.DRAWABLE, activity.getPackageName());
            if (identifier > 0) {
                this.mExampleIv.setImageResource(identifier);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismiss() {
        super.dismiss();
        this.mResouse = null;
    }

    private int dp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
