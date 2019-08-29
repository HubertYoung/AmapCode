package com.alipay.mobile.tinyappcommon.h5plugin;

import android.text.TextPaint;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class TinyAppCanvasApiPlugin extends H5SimplePlugin {
    private static final String MEASURE_TEXT = "measureText";
    private static final String TAG = TinyAppCanvasApiPlugin.class.getSimpleName();

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(MEASURE_TEXT);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        if (MEASURE_TEXT.equals(event.getAction())) {
            measureText(event, context);
        }
        return true;
    }

    private void measureText(H5Event event, H5BridgeContext context) {
        boolean z = false;
        try {
            String fontWeight = H5Utils.getString(event.getParam(), (String) "fontWeight", (String) "");
            String text = H5Utils.getString(event.getParam(), (String) "text", (String) "");
            TextPaint paint = new TextPaint();
            paint.setTextSize((float) H5Utils.getInt(event.getParam(), (String) "fontSize", 10));
            if (!TextUtils.isEmpty(fontWeight) && "bold".equals(fontWeight)) {
                z = true;
            }
            paint.setFakeBoldText(z);
            Float width = Float.valueOf(paint.measureText(text, 0, text.length()));
            JSONObject result = new JSONObject();
            result.put((String) "width", (Object) width);
            context.sendBridgeResult(result);
        } catch (Throwable e) {
            H5Log.e(TAG, "measureText...e=" + e);
        }
    }
}
