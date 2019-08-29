package com.autonavi.minimap.ajx3.modules;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.alipay.mobile.common.share.widget.ResUtils;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.jni.ae.dice.NaviEngine;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.view.Label;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("ajxtest")
public final class ModuleSchemeTest extends AbstractModule {
    public static final String MODULE_NAME = "ajxtest";
    private static final int REQUEST_MEDIA_PROJECTION = 35423;
    private static ImageReader mImageReader;
    private static MediaProjectionManager mMediaProjectionManager;
    private static VirtualDisplay mVirtualDisplay;

    @TargetApi(21)
    public static void onActivityResult(Context context, int i, int i2, Intent intent) {
        if (i == REQUEST_MEDIA_PROJECTION && i2 == -1) {
            WindowManager windowManager = (WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY);
            int width = windowManager.getDefaultDisplay().getWidth();
            int height = windowManager.getDefaultDisplay().getHeight();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            mImageReader = ImageReader.newInstance(width, height, 1, 2);
            mVirtualDisplay = mMediaProjectionManager.getMediaProjection(i2, intent).createVirtualDisplay("screen-mirror", width, height, displayMetrics.densityDpi, 16, mImageReader.getSurface(), null, null);
        }
    }

    public ModuleSchemeTest(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod("init")
    public final void init() {
        if (mVirtualDisplay == null && VERSION.SDK_INT >= 21) {
            mMediaProjectionManager = (MediaProjectionManager) getNativeContext().getSystemService("media_projection");
            ((Activity) getNativeContext()).startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        }
    }

    @AjxMethod("getViewTree")
    public final void getViewTree(JsFunctionCallback jsFunctionCallback) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("version", getAppVersion());
            jSONObject.put("device_name", Build.MODEL);
            jSONObject.put("system_version", VERSION.RELEASE);
            jSONObject.put("ajx_version", AjxFileInfo.getAllAjxFileVersion());
            jSONObject.put("ajx_engine_version", Ajx.getInstance().getAjxEngineVersion());
            jSONObject.put("dice_version", NaviEngine.getLibDiceSoVersion());
            jSONObject.put("screen_density", getScreenPoint());
            JSONArray jSONArray = new JSONArray();
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                List<AjxView> ajxViewList = getAjxViewList(pageContext.getContentView());
                if (ajxViewList != null && ajxViewList.size() > 0) {
                    for (AjxView parseView : ajxViewList) {
                        JSONObject jSONObject2 = new JSONObject();
                        parseView(parseView, jSONObject2);
                        jSONArray.put(jSONObject2);
                    }
                }
            } else {
                JSONObject jSONObject3 = new JSONObject();
                parseView(getContext().getDomTree().getRootView(), jSONObject3);
                jSONArray.put(jSONObject3);
            }
            jSONObject.put("root_view", jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsFunctionCallback.callback(jSONObject.toString());
    }

    private List<AjxView> getAjxViewList(View view) {
        if (view == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        findAjxView(view, arrayList);
        return arrayList;
    }

    private void findAjxView(View view, List<AjxView> list) {
        if (view instanceof AjxView) {
            list.add((AjxView) view);
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                findAjxView(viewGroup.getChildAt(i), list);
            }
        }
    }

    private void parseView(View view, JSONObject jSONObject) throws JSONException {
        if (view != null) {
            jSONObject.put("view_id", view.getId());
            jSONObject.put("view_type", view.getClass());
            jSONObject.put("view_bounds", getViewBounds(view));
            jSONObject.put("view_text", getViewText(view));
            jSONObject.put("view_clickable", view.isClickable());
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            if (childCount > 0) {
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < childCount; i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONArray.put(jSONObject2);
                    parseView(viewGroup.getChildAt(i), jSONObject2);
                }
                jSONObject.put("children", jSONArray);
            }
        }
    }

    private String getAppVersion() {
        try {
            return getNativeContext().getPackageManager().getPackageInfo(getNativeContext().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getScreenPoint() {
        DisplayMetrics displayMetrics = getNativeContext().getResources().getDisplayMetrics();
        StringBuilder sb = new StringBuilder();
        sb.append(displayMetrics.heightPixels);
        sb.append(" x ");
        sb.append(displayMetrics.widthPixels);
        return sb.toString();
    }

    private String getViewText(View view) {
        CharSequence charSequence;
        if (view instanceof Label) {
            charSequence = ((Label) view).getText();
        } else {
            charSequence = view.getContentDescription();
        }
        return charSequence != null ? charSequence.toString() : "";
    }

    public final String getViewBounds(View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        StringBuilder sb = new StringBuilder("[");
        sb.append(rect.left);
        sb.append(",");
        sb.append(rect.right);
        sb.append("][");
        sb.append(rect.top);
        sb.append(",");
        sb.append(rect.bottom);
        sb.append("]");
        return sb.toString();
    }

    @AjxMethod("snapshotScreen")
    public final void snapshotScreen(String str, JsFunctionCallback jsFunctionCallback) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append("/autonavi/ajxtest/snapshot/");
        sb.append(str);
        sb.append(".jpg");
        String sb2 = sb.toString();
        jsFunctionCallback.callback(Boolean.valueOf(snapshotScreen(sb2)), sb2);
    }

    private boolean snapshotScreen(String str) {
        Bitmap bitmap;
        boolean z;
        if (VERSION.SDK_INT < 21 || mImageReader == null) {
            View decorView = ((Activity) getNativeContext()).getWindow().getDecorView();
            bitmap = Bitmap.createBitmap(decorView.getWidth(), decorView.getHeight(), Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(-1);
            decorView.draw(canvas);
        } else {
            Image acquireLatestImage = mImageReader.acquireLatestImage();
            int width = acquireLatestImage.getWidth();
            int height = acquireLatestImage.getHeight();
            Plane[] planes = acquireLatestImage.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            Bitmap createBitmap = Bitmap.createBitmap(((planes[0].getRowStride() - (pixelStride * width)) / pixelStride) + width, height, Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(buffer);
            int statusBarHeight = getStatusBarHeight(getNativeContext());
            bitmap = Bitmap.createBitmap(createBitmap, 0, statusBarHeight, width, height - statusBarHeight);
            acquireLatestImage.close();
        }
        try {
            z = saveBitmap(bitmap, str);
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        bitmap.recycle();
        return z;
    }

    private int getStatusBarHeight(Context context) {
        int i = 0;
        if (isFullScreen()) {
            return 0;
        }
        int identifier = context.getResources().getIdentifier("status_bar_height", ResUtils.DIMEN, "android");
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        }
        return i;
    }

    private boolean isFullScreen() {
        return (((Activity) getNativeContext()).getWindow().getAttributes().flags & 1024) == 1024;
    }

    private boolean saveBitmap(Bitmap bitmap, String str) throws Exception {
        if (bitmap == null || !isHasSDCard()) {
            return false;
        }
        File file = new File(str);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            return false;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        boolean compress = bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
        fileOutputStream.close();
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(file));
        getNativeContext().sendBroadcast(intent);
        if (!compress || new File(str).length() == 0) {
            return false;
        }
        return true;
    }

    private boolean isHasSDCard() {
        return Environment.getExternalStorageDirectory() != null && "mounted".equals(Environment.getExternalStorageState());
    }
}
