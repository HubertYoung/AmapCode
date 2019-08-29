package com.autonavi.minimap.ajx3.widget.property;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.AjxContextHandlerCallback;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.image.PictureParams;
import com.autonavi.minimap.ajx3.loader.ImageCallback;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.support.scan.AjxScanView;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.PathUtils;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.bundle.qrscan.data.IScanResult;
import com.autonavi.minimap.bundle.qrscan.platform.CodePlatformResultFetcher;
import com.autonavi.minimap.bundle.qrscan.platform.CodePlatformResultFetcher.OnCodePlatformResultListener;
import com.autonavi.minimap.bundle.qrscan.scanner.ScanView.CameraErrorListener;
import com.autonavi.minimap.bundle.qrscan.scanner.ScanView.CameraGrayListener;
import com.autonavi.minimap.bundle.qrscan.scanner.ScanView.DecodeListener;
import org.json.JSONException;
import org.json.JSONObject;
import pl.droidsonroids.gif.GifDrawable;

public class AjxScanProperty extends BaseProperty<AjxScanView> implements AjxContextHandlerCallback {
    private static final String ATTR_ACTION = "action";
    private static final String ATTR_ACTION_STARTSCAN = "startscan";
    private static final String ATTR_ACTION_STOPSCAN = "stopscan";
    private static final String ATTR_CAMERAERROR = "cameraError";
    private static final String ATTR_CAMERAGRAY = "cameraGray";
    private static final String ATTR_CAPTURE = "capture";
    private static final String ATTR_CONTENT = "content";
    private static final String ATTR_FOCUSBORDERIMAGE = "focusborderimage";
    private static final String ATTR_FOCUSHEIGHT = "focusheight";
    private static final String ATTR_FOCUSLEFT = "focusleft";
    private static final String ATTR_FOCUSLINEIMAGE = "focuslineimage";
    private static final String ATTR_FOCUSTOP = "focustop";
    private static final String ATTR_FOCUSWIDTH = "focuswidth";
    private static final String ATTR_MAPLATFORM = "maplatform";
    private static final String ATTR_MASKCOLOR = "maskcolor";
    public static int DEFAULT_FRAME_HEIGHT;
    public static int DEFAULT_FRAME_LEFT = (DimensionUtils.getWidthPixels() / 5);
    public static int DEFAULT_FRAME_TOP = (DimensionUtils.getHeightPixels() / 5);
    public static int DEFAULT_FRAME_WIDTH;
    public static int INIT_HEIGHT;
    private ImageCallback focusborderImageImageCallback = new ImageCallback() {
        public void onBitmapFailed(Drawable drawable) {
        }

        public void onBitmapLoaded(Bitmap bitmap) {
        }

        public void onGifLoaded(GifDrawable gifDrawable) {
        }

        public void onPrepareLoad(Drawable drawable) {
        }
    };
    private ImageCallback focuslineImageImageCallback = new ImageCallback() {
        public void onBitmapFailed(Drawable drawable) {
        }

        public void onBitmapLoaded(Bitmap bitmap) {
        }

        public void onGifLoaded(GifDrawable gifDrawable) {
        }

        public void onPrepareLoad(Drawable drawable) {
        }
    };
    private int mDefaultHeight;
    private int mDefaultLeft;
    private int mDefaultTop;
    private int mDefaultWidth;
    private boolean maPlatform = false;

    private void updateMaskcolor(Object obj) {
    }

    static {
        int widthPixels = (DimensionUtils.getWidthPixels() * 3) / 5;
        DEFAULT_FRAME_WIDTH = widthPixels;
        DEFAULT_FRAME_HEIGHT = widthPixels;
    }

    public AjxScanProperty(@NonNull AjxScanView ajxScanView, @NonNull IAjxContext iAjxContext) {
        super(ajxScanView, iAjxContext);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = this.mDefaultWidth;
        int i6 = this.mDefaultHeight;
        updateSize();
        if (this.mAjxContext == null) {
            return;
        }
        if (i5 != this.mDefaultWidth || i6 != this.mDefaultHeight) {
            this.mAjxContext.post(this, null, 0);
        }
    }

    public void handleCallback(Message message) {
        try {
            DisplayMetrics displayMetrics = getAjxContext().getNativeContext().getResources().getDisplayMetrics();
            LayoutParams layoutParams = ((AjxScanView) this.mView).getLayoutParams();
            if (layoutParams instanceof AbsoluteLayout.LayoutParams) {
                ((AbsoluteLayout.LayoutParams) layoutParams).x = 0;
                ((AbsoluteLayout.LayoutParams) layoutParams).y = 0;
                ((AbsoluteLayout.LayoutParams) layoutParams).width = displayMetrics.widthPixels;
                ((AbsoluteLayout.LayoutParams) layoutParams).height = displayMetrics.heightPixels;
                ((AjxScanView) this.mView).setLayoutParams(layoutParams);
            }
        } catch (Exception unused) {
        }
    }

    private void updateSize() {
        DisplayMetrics displayMetrics = getAjxContext().getNativeContext().getResources().getDisplayMetrics();
        this.mDefaultLeft = 0;
        this.mDefaultTop = 0;
        this.mDefaultWidth = (int) DimensionUtils.pixelToStandardUnit((float) displayMetrics.widthPixels);
        this.mDefaultHeight = (int) DimensionUtils.pixelToStandardUnit((float) displayMetrics.heightPixels);
    }

    public void synEngine() {
        long nodeId = this.mAjxContext.getDomTree().getNodeId(this.mView);
        Parcel parcel = new Parcel();
        parcel.writeInt(8);
        float pixelToStandardUnit = DimensionUtils.pixelToStandardUnit((float) DEFAULT_FRAME_LEFT);
        float pixelToStandardUnit2 = DimensionUtils.pixelToStandardUnit((float) DEFAULT_FRAME_TOP);
        float pixelToStandardUnit3 = DimensionUtils.pixelToStandardUnit((float) DEFAULT_FRAME_WIDTH);
        float pixelToStandardUnit4 = DimensionUtils.pixelToStandardUnit((float) DEFAULT_FRAME_HEIGHT);
        parcel.writeString(ATTR_FOCUSLEFT);
        parcel.writeString(String.valueOf(pixelToStandardUnit));
        parcel.writeString(ATTR_FOCUSTOP);
        parcel.writeString(String.valueOf(pixelToStandardUnit2));
        parcel.writeString(ATTR_FOCUSWIDTH);
        parcel.writeString(String.valueOf(pixelToStandardUnit3));
        parcel.writeString(ATTR_FOCUSHEIGHT);
        parcel.writeString(String.valueOf(pixelToStandardUnit4));
        this.mAjxContext.invokeJsEvent(new Builder().setNodeId(nodeId).setAttribute(parcel).build());
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0065  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0072  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateSize(java.lang.String r8, float r9, boolean r10, boolean r11, boolean r12, boolean r13) {
        /*
            r7 = this;
            r7.updateSize()
            int r9 = r8.hashCode()
            r0 = -1221029593(0xffffffffb7389127, float:-1.1001051E-5)
            if (r9 == r0) goto L_0x003c
            r0 = 115029(0x1c155, float:1.6119E-40)
            if (r9 == r0) goto L_0x0031
            r0 = 3317767(0x32a007, float:4.649182E-39)
            if (r9 == r0) goto L_0x0027
            r0 = 113126854(0x6be2dc6, float:7.1537315E-35)
            if (r9 == r0) goto L_0x001c
            goto L_0x0046
        L_0x001c:
            java.lang.String r9 = "width"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x0046
            r9 = 2
            goto L_0x0047
        L_0x0027:
            java.lang.String r9 = "left"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x0046
            r9 = 0
            goto L_0x0047
        L_0x0031:
            java.lang.String r9 = "top"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x0046
            r9 = 1
            goto L_0x0047
        L_0x003c:
            java.lang.String r9 = "height"
            boolean r9 = r8.equals(r9)
            if (r9 == 0) goto L_0x0046
            r9 = 3
            goto L_0x0047
        L_0x0046:
            r9 = -1
        L_0x0047:
            switch(r9) {
                case 0: goto L_0x0072;
                case 1: goto L_0x0065;
                case 2: goto L_0x0058;
                case 3: goto L_0x004b;
                default: goto L_0x004a;
            }
        L_0x004a:
            goto L_0x007f
        L_0x004b:
            int r9 = r7.mDefaultHeight
            float r2 = (float) r9
            r0 = r7
            r1 = r8
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            super.updateSize(r1, r2, r3, r4, r5, r6)
            goto L_0x007f
        L_0x0058:
            int r9 = r7.mDefaultWidth
            float r2 = (float) r9
            r0 = r7
            r1 = r8
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            super.updateSize(r1, r2, r3, r4, r5, r6)
            return
        L_0x0065:
            int r9 = r7.mDefaultTop
            float r2 = (float) r9
            r0 = r7
            r1 = r8
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            super.updateSize(r1, r2, r3, r4, r5, r6)
            return
        L_0x0072:
            int r9 = r7.mDefaultLeft
            float r2 = (float) r9
            r0 = r7
            r1 = r8
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            super.updateSize(r1, r2, r3, r4, r5, r6)
            return
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.AjxScanProperty.updateSize(java.lang.String, float, boolean, boolean, boolean, boolean):void");
    }

    /* access modifiers changed from: protected */
    public void updateAttribute(String str, Object obj) {
        if (obj != null) {
            char c = 65535;
            switch (str.hashCode()) {
                case -2012502232:
                    if (str.equals(ATTR_CAMERAGRAY)) {
                        c = 6;
                        break;
                    }
                    break;
                case -1979915265:
                    if (str.equals(ATTR_FOCUSHEIGHT)) {
                        c = 3;
                        break;
                    }
                    break;
                case -1594842617:
                    if (str.equals(ATTR_MAPLATFORM)) {
                        c = 11;
                        break;
                    }
                    break;
                case -1422950858:
                    if (str.equals("action")) {
                        c = 10;
                        break;
                    }
                    break;
                case -1042255857:
                    if (str.equals(ATTR_FOCUSLINEIMAGE)) {
                        c = 9;
                        break;
                    }
                    break;
                case -1019731986:
                    if (str.equals(ATTR_FOCUSWIDTH)) {
                        c = 2;
                        break;
                    }
                    break;
                case -756964137:
                    if (str.equals(ATTR_FOCUSBORDERIMAGE)) {
                        c = 8;
                        break;
                    }
                    break;
                case -48260105:
                    if (str.equals(ATTR_MASKCOLOR)) {
                        c = 7;
                        break;
                    }
                    break;
                case 52567421:
                    if (str.equals(ATTR_FOCUSTOP)) {
                        c = 1;
                        break;
                    }
                    break;
                case 552585030:
                    if (str.equals(ATTR_CAPTURE)) {
                        c = 4;
                        break;
                    }
                    break;
                case 1629341919:
                    if (str.equals(ATTR_FOCUSLEFT)) {
                        c = 0;
                        break;
                    }
                    break;
                case 2035109347:
                    if (str.equals(ATTR_CAMERAERROR)) {
                        c = 5;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    updateFocusLeft(obj);
                    return;
                case 1:
                    updateFocusTop(obj);
                    return;
                case 2:
                    updateFocusWidth(obj);
                    return;
                case 3:
                    updateFocusHeight(obj);
                    return;
                case 4:
                    updateCapterListener(obj);
                    return;
                case 5:
                    updateCameraErrorListener(obj);
                    return;
                case 6:
                    updateCameraGrayListener(obj);
                    break;
                case 7:
                    break;
                case 8:
                    updateFocusborderimage(obj);
                    return;
                case 9:
                    updateFocuslineimage(obj);
                    return;
                case 10:
                    updateAction(obj);
                    return;
                case 11:
                    updateMaPlatform(obj);
                    return;
                default:
                    super.updateAttribute(str, obj);
                    return;
            }
            updateMaskcolor(obj);
        }
    }

    public Object getAttribute(String str) {
        return super.getAttribute(str);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004b  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0057  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void notifyUpdateSize(java.lang.String r3, float r4) {
        /*
            r2 = this;
            r2.updateSize()
            int r0 = r3.hashCode()
            r1 = -1221029593(0xffffffffb7389127, float:-1.1001051E-5)
            if (r0 == r1) goto L_0x003c
            r1 = 115029(0x1c155, float:1.6119E-40)
            if (r0 == r1) goto L_0x0031
            r1 = 3317767(0x32a007, float:4.649182E-39)
            if (r0 == r1) goto L_0x0027
            r1 = 113126854(0x6be2dc6, float:7.1537315E-35)
            if (r0 == r1) goto L_0x001c
            goto L_0x0046
        L_0x001c:
            java.lang.String r0 = "width"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0046
            r0 = 2
            goto L_0x0047
        L_0x0027:
            java.lang.String r0 = "left"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0046
            r0 = 0
            goto L_0x0047
        L_0x0031:
            java.lang.String r0 = "top"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0046
            r0 = 1
            goto L_0x0047
        L_0x003c:
            java.lang.String r0 = "height"
            boolean r0 = r3.equals(r0)
            if (r0 == 0) goto L_0x0046
            r0 = 3
            goto L_0x0047
        L_0x0046:
            r0 = -1
        L_0x0047:
            switch(r0) {
                case 0: goto L_0x0057;
                case 1: goto L_0x0053;
                case 2: goto L_0x004f;
                case 3: goto L_0x004b;
                default: goto L_0x004a;
            }
        L_0x004a:
            goto L_0x005a
        L_0x004b:
            int r4 = r2.mDefaultHeight
            float r4 = (float) r4
            goto L_0x005a
        L_0x004f:
            int r4 = r2.mDefaultWidth
            float r4 = (float) r4
            goto L_0x005a
        L_0x0053:
            int r4 = r2.mDefaultTop
            float r4 = (float) r4
            goto L_0x005a
        L_0x0057:
            int r4 = r2.mDefaultLeft
            float r4 = (float) r4
        L_0x005a:
            super.notifyUpdateSize(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.AjxScanProperty.notifyUpdateSize(java.lang.String, float):void");
    }

    private void updateFocusLeft(Object obj) {
        if (obj != null) {
            ((AjxScanView) this.mView).getScanFinderView().setFramingLeft(DimensionUtils.standardUnitToPixel((float) StringUtils.parseInt((String) obj, -1)));
        }
    }

    private void updateFocusTop(Object obj) {
        if (obj != null) {
            ((AjxScanView) this.mView).getScanFinderView().setFramingTop(DimensionUtils.standardUnitToPixel((float) StringUtils.parseInt((String) obj, -1)));
        }
    }

    private void updateFocusWidth(Object obj) {
        if (obj != null) {
            ((AjxScanView) this.mView).getScanFinderView().setFramingWidth(DimensionUtils.standardUnitToPixel((float) StringUtils.parseInt((String) obj, -1)));
        }
    }

    private void updateFocusHeight(Object obj) {
        if (obj != null) {
            ((AjxScanView) this.mView).getScanFinderView().setFramingHeight(DimensionUtils.standardUnitToPixel((float) StringUtils.parseInt((String) obj, -1)));
        }
    }

    private void updateCapterListener(Object obj) {
        if (obj == null) {
            ((AjxScanView) this.mView).setDecodeListener(null);
        } else {
            ((AjxScanView) this.mView).setDecodeListener(new DecodeListener() {
                public void onFailure(int i) {
                }

                public void onSuccess(final IScanResult iScanResult) {
                    if (AjxScanProperty.this.isMaPlatform()) {
                        CodePlatformResultFetcher.getInstance().fetchPlatformResultAsync(iScanResult, AjxScanProperty.this.mAjxContext.getAjxConfig().getAppVersion(), new OnCodePlatformResultListener() {
                            public void onResult(IScanResult iScanResult) {
                                AnonymousClass1.this.handleScanResult(iScanResult, iScanResult.getText());
                            }
                        });
                    } else {
                        handleScanResult(iScanResult, iScanResult.getText());
                    }
                }

                /* access modifiers changed from: private */
                public void handleScanResult(IScanResult iScanResult, String str) {
                    JSONObject jSONObject = new JSONObject();
                    boolean z = iScanResult.getErrorType() == 102;
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append(!z);
                        jSONObject.put("success", sb.toString());
                        jSONObject.put("errorCode", iScanResult.getErrorCode());
                        jSONObject.put("text", iScanResult.getText());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    invokeJsEvent(jSONObject.toString());
                }

                private void invokeJsEvent(String str) {
                    long nodeId = AjxScanProperty.this.mAjxContext.getDomTree().getNodeId(AjxScanProperty.this.mView);
                    Parcel parcel = new Parcel();
                    parcel.writeInt(2);
                    parcel.writeString("content");
                    parcel.writeString(str);
                    AjxScanProperty.this.mAjxContext.invokeJsEvent(new Builder().setEventName(AjxScanProperty.ATTR_CAPTURE).setNodeId(nodeId).setAttribute(parcel).build());
                }
            });
        }
    }

    private void updateCameraErrorListener(Object obj) {
        if (obj == null) {
            ((AjxScanView) this.mView).setCameraErrorListener(null);
        } else {
            ((AjxScanView) this.mView).setCameraErrorListener(new CameraErrorListener() {
                public void onCameraError(int i) {
                    AjxScanProperty.this.mAjxContext.invokeJsEvent(new Builder().setNodeId(AjxScanProperty.this.mAjxContext.getDomTree().getNodeId(AjxScanProperty.this.mView)).setEventName(AjxScanProperty.ATTR_CAMERAERROR).build());
                }
            });
        }
    }

    private void updateCameraGrayListener(Object obj) {
        if (obj == null) {
            ((AjxScanView) this.mView).setCameraGrayListener(null);
        } else {
            ((AjxScanView) this.mView).setCameraGrayListener(new CameraGrayListener() {
                public void onCameraGray(int i) {
                    long nodeId = AjxScanProperty.this.mAjxContext.getDomTree().getNodeId(AjxScanProperty.this.mView);
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("gray", i);
                    } catch (JSONException unused) {
                    }
                    AjxScanProperty.this.mAjxContext.invokeJsEvent(new Builder().setEventName(AjxScanProperty.ATTR_CAMERAGRAY).setNodeId(nodeId).setContent(jSONObject).build());
                }
            });
        }
    }

    private void updateFocusborderimage(Object obj) {
        doLoadImage(obj, this.focusborderImageImageCallback);
    }

    private void updateFocuslineimage(Object obj) {
        doLoadImage(obj, this.focuslineImageImageCallback);
    }

    private void updateAction(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (ATTR_ACTION_STARTSCAN.equals(str)) {
                ((AjxScanView) this.mView).onResume();
                if (INIT_HEIGHT == 0) {
                    INIT_HEIGHT = (((AjxScanView) this.mView).getResources().getDisplayMetrics().widthPixels * 3) / 5;
                }
            } else if (ATTR_ACTION_STOPSCAN.equals(str)) {
                ((AjxScanView) this.mView).onPause();
            }
        }
    }

    private void updateMaPlatform(Object obj) {
        new StringBuilder("--AjxScanProperty.updateMaPlatform :").append(obj);
        if (obj instanceof String) {
            this.maPlatform = StringUtils.parseBoolean((String) obj);
        }
    }

    private void doLoadImage(Object obj, ImageCallback imageCallback) {
        if (obj instanceof String) {
            String preProcessUrl = PathUtils.preProcessUrl((String) obj);
            Ajx.getInstance().lookupLoader(preProcessUrl).loadImage(this.mView, this.mAjxContext, PictureParams.make(this.mAjxContext, preProcessUrl, false), imageCallback);
        }
    }

    public boolean isMaPlatform() {
        return this.maPlatform;
    }
}
