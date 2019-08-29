package com.autonavi.minimap.ajx3.widget;

import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsContextObserver;
import com.autonavi.minimap.ajx3.dom.JsDomEvent;
import com.autonavi.minimap.ajx3.log.LogBody.Builder;
import com.autonavi.minimap.ajx3.log.LogManager;
import com.autonavi.minimap.ajx3.platform.ackor.AjxFileInfo;
import com.autonavi.minimap.ajx3.util.PathUtils;
import com.autonavi.minimap.ajx3.util.ReflectUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxEventHandler implements JsContextObserver {
    private AjxView mAjxView;
    private boolean mHasErrorOccured = false;

    AjxEventHandler(AjxView ajxView) {
        this.mAjxView = ajxView;
    }

    /* access modifiers changed from: protected */
    public boolean hasErrorOccured() {
        return this.mHasErrorOccured;
    }

    private boolean checkAjxContext(long j) {
        IAjxContext ajxContext = this.mAjxView.getAjxContext();
        return ajxContext != null && !ajxContext.hasDestroy() && ajxContext.getJsContext().shadow() == j;
    }

    /* access modifiers changed from: 0000 */
    public void reset() {
        this.mHasErrorOccured = false;
    }

    public final void onUiEvent(long j, long j2) {
        JsDomEvent create = JsDomEvent.create(j2);
        if (create != null && checkAjxContext(j)) {
            if (create.type == 1) {
                Ajx.getInstance().addTimestamp("fullEvent-start");
                LogManager.performaceLog("fullEvent-start");
                this.mAjxView.onJsUiLoadStart();
            }
            this.mAjxView.getAjxContext().onUiEvent(create);
            if (create.type == 1) {
                this.mHasErrorOccured = false;
                this.mAjxView.onJsUiLoad();
            }
        }
    }

    public final void onUiListEvent(long j, long j2) {
        this.mHasErrorOccured = false;
        if (checkAjxContext(j)) {
            IAjxContext ajxContext = this.mAjxView.getAjxContext();
            if (ajxContext != null && !ajxContext.hasDestroy() && ajxContext.getJsContext().shadow() == j) {
                ajxContext.onUiListEvent(j, j2);
            }
        }
    }

    public void onDestroy(long j) {
        IAjxContext ajxContext = Ajx.getInstance().getAjxContext(j);
        if (ajxContext != null) {
            ajxContext.release();
        }
    }

    public final void onReplace(String str, String str2, Object obj, String str3) {
        if (this.mAjxView.getAjxContext() != null && !this.mAjxView.getAjxContext().hasDestroy()) {
            this.mAjxView.onReplace(str, str2, obj, str3);
        }
    }

    public void onOpen(String str, String str2, Object obj, String str3) {
        if (this.mAjxView.getAjxContext() != null && !this.mAjxView.getAjxContext().hasDestroy()) {
            this.mAjxView.onOpen(str, str2, obj, str3);
        }
    }

    public void onBack(Object obj, String str) {
        if (this.mAjxView.getAjxContext() != null && !this.mAjxView.getAjxContext().hasDestroy()) {
            this.mAjxView.onBack(obj, str);
        }
    }

    public void onNodeUniqueId(String str, String str2) {
        if (this.mAjxView.getAjxContext() != null && !this.mAjxView.getAjxContext().hasDestroy()) {
            this.mAjxView.onNodeUnitId(str, str2);
        }
    }

    public void onInvokeNodeMethod(long j, long j2, String str, Object... objArr) {
        try {
            View findViewByNodeId = this.mAjxView.getAjxContext().getDomTree().findViewByNodeId(j2);
            Class[] clsArr = null;
            if (objArr != null && objArr.length > 0) {
                int length = objArr.length;
                Class[] clsArr2 = new Class[length];
                for (int i = 0; i < length; i++) {
                    clsArr2[i] = objArr[i].getClass();
                }
                clsArr = clsArr2;
            }
            ReflectUtil.invokeMethod((Object) findViewByNodeId, str, clsArr, objArr);
        } catch (Exception unused) {
        }
    }

    public void onGetDebugData(String str) {
        LogManager.log(new Builder().setContextId(Ajx.getInstance().getCurrJsContext()).setLogType(2).setTag("节点树").setAjxFileVersion(AjxFileInfo.getAllAjxFileVersion()).setMsg(str).build());
    }

    public void onGetDebugDataForInspector(String str) {
        LogManager.send(str);
    }

    public void onCommandByInspector(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("method")) {
                String string = jSONObject.getString("method");
                if (!TextUtils.isEmpty(string)) {
                    if (string.contains("hideHighlight")) {
                        this.mAjxView.getAjxContext().getDomTree().removeHighLight();
                    } else if (string.contains("highlightNode") && jSONObject.has("params")) {
                        this.mAjxView.getAjxContext().getDomTree().highLightNode(new JSONObject(jSONObject.getString("params")).getLong("nodeId"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0059 A[Catch:{ JSONException -> 0x00e7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x008a A[Catch:{ JSONException -> 0x00e4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00b9 A[Catch:{ JSONException -> 0x00e4 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onPresentSub(java.lang.String r8, java.lang.Object r9) {
        /*
            r7 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.lang.String r2 = r7.joinPath(r8)
            com.autonavi.minimap.ajx3.widget.AjxView r8 = r7.mAjxView
            int r8 = r8.getWidth()
            com.autonavi.minimap.ajx3.widget.AjxView r0 = r7.mAjxView
            int r0 = r0.getHeight()
            r1 = 0
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00e6 }
            java.lang.String r9 = (java.lang.String) r9     // Catch:{ JSONException -> 0x00e6 }
            r3.<init>(r9)     // Catch:{ JSONException -> 0x00e6 }
            java.lang.String r9 = "x"
            boolean r9 = r3.has(r9)     // Catch:{ JSONException -> 0x00e6 }
            if (r9 == 0) goto L_0x004f
            java.lang.String r9 = "x"
            java.lang.String r9 = r3.getString(r9)     // Catch:{ JSONException -> 0x00e6 }
            java.lang.String r4 = "px"
            boolean r4 = r9.endsWith(r4)     // Catch:{ JSONException -> 0x00e6 }
            if (r4 == 0) goto L_0x004f
            int r4 = r9.length()     // Catch:{ JSONException -> 0x00e6 }
            int r4 = r4 + -2
            java.lang.String r9 = r9.substring(r1, r4)     // Catch:{ JSONException -> 0x00e6 }
            java.lang.Double r9 = java.lang.Double.valueOf(r9)     // Catch:{ JSONException -> 0x00e6 }
            double r4 = r9.doubleValue()     // Catch:{ JSONException -> 0x00e6 }
            float r9 = (float) r4     // Catch:{ JSONException -> 0x00e6 }
            int r9 = com.autonavi.minimap.ajx3.util.DimensionUtils.standardUnitToPixel(r9)     // Catch:{ JSONException -> 0x00e6 }
            goto L_0x0050
        L_0x004f:
            r9 = 0
        L_0x0050:
            java.lang.String r4 = "y"
            boolean r4 = r3.has(r4)     // Catch:{ JSONException -> 0x00e7 }
            if (r4 == 0) goto L_0x0080
            java.lang.String r4 = "y"
            java.lang.String r4 = r3.getString(r4)     // Catch:{ JSONException -> 0x00e7 }
            java.lang.String r5 = "px"
            boolean r5 = r4.endsWith(r5)     // Catch:{ JSONException -> 0x00e7 }
            if (r5 == 0) goto L_0x0080
            int r5 = r4.length()     // Catch:{ JSONException -> 0x00e7 }
            int r5 = r5 + -2
            java.lang.String r4 = r4.substring(r1, r5)     // Catch:{ JSONException -> 0x00e7 }
            java.lang.Double r4 = java.lang.Double.valueOf(r4)     // Catch:{ JSONException -> 0x00e7 }
            double r4 = r4.doubleValue()     // Catch:{ JSONException -> 0x00e7 }
            float r4 = (float) r4     // Catch:{ JSONException -> 0x00e7 }
            int r4 = com.autonavi.minimap.ajx3.util.DimensionUtils.standardUnitToPixel(r4)     // Catch:{ JSONException -> 0x00e7 }
            goto L_0x0081
        L_0x0080:
            r4 = 0
        L_0x0081:
            java.lang.String r5 = "w"
            boolean r5 = r3.has(r5)     // Catch:{ JSONException -> 0x00e4 }
            if (r5 == 0) goto L_0x00b1
            java.lang.String r5 = "w"
            java.lang.String r5 = r3.getString(r5)     // Catch:{ JSONException -> 0x00e4 }
            java.lang.String r6 = "px"
            boolean r6 = r5.endsWith(r6)     // Catch:{ JSONException -> 0x00e4 }
            if (r6 == 0) goto L_0x00b1
            int r6 = r5.length()     // Catch:{ JSONException -> 0x00e4 }
            int r6 = r6 + -2
            java.lang.String r5 = r5.substring(r1, r6)     // Catch:{ JSONException -> 0x00e4 }
            java.lang.Double r5 = java.lang.Double.valueOf(r5)     // Catch:{ JSONException -> 0x00e4 }
            double r5 = r5.doubleValue()     // Catch:{ JSONException -> 0x00e4 }
            float r5 = (float) r5     // Catch:{ JSONException -> 0x00e4 }
            int r5 = com.autonavi.minimap.ajx3.util.DimensionUtils.standardUnitToPixel(r5)     // Catch:{ JSONException -> 0x00e4 }
            r8 = r5
        L_0x00b1:
            java.lang.String r5 = "h"
            boolean r5 = r3.has(r5)     // Catch:{ JSONException -> 0x00e4 }
            if (r5 == 0) goto L_0x00df
            java.lang.String r5 = "h"
            java.lang.String r3 = r3.getString(r5)     // Catch:{ JSONException -> 0x00e4 }
            java.lang.String r5 = "px"
            boolean r5 = r3.endsWith(r5)     // Catch:{ JSONException -> 0x00e4 }
            if (r5 == 0) goto L_0x00df
            int r5 = r3.length()     // Catch:{ JSONException -> 0x00e4 }
            int r5 = r5 + -2
            java.lang.String r1 = r3.substring(r1, r5)     // Catch:{ JSONException -> 0x00e4 }
            java.lang.Double r1 = java.lang.Double.valueOf(r1)     // Catch:{ JSONException -> 0x00e4 }
            double r5 = r1.doubleValue()     // Catch:{ JSONException -> 0x00e4 }
            float r1 = (float) r5     // Catch:{ JSONException -> 0x00e4 }
            int r1 = com.autonavi.minimap.ajx3.util.DimensionUtils.standardUnitToPixel(r1)     // Catch:{ JSONException -> 0x00e4 }
            r0 = r1
        L_0x00df:
            r3 = r8
            r5 = r9
            r6 = r4
            r4 = r0
            goto L_0x00eb
        L_0x00e4:
            r1 = r4
            goto L_0x00e7
        L_0x00e6:
            r9 = 0
        L_0x00e7:
            r3 = r8
            r5 = r9
            r4 = r0
            r6 = r1
        L_0x00eb:
            com.autonavi.minimap.ajx3.widget.AjxView r1 = r7.mAjxView
            r1.present(r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.AjxEventHandler.onPresentSub(java.lang.String, java.lang.Object):void");
    }

    public void onDismissSub(long j) {
        this.mAjxView.dismissSub(j);
    }

    private String joinPath(String str) {
        String preProcessUrl = PathUtils.preProcessUrl(str);
        String scheme = Uri.parse(preProcessUrl).getScheme();
        String jsPath = this.mAjxView.getAjxContext().getJsPath();
        return (!TextUtils.isEmpty(scheme) || TextUtils.isEmpty(jsPath)) ? preProcessUrl : PathUtils.processPath(jsPath.substring(0, jsPath.lastIndexOf("/") + 1), preProcessUrl);
    }

    public void onAddLayer(String str, String str2, Object obj) {
        this.mAjxView.onAddLayer(str, str2, obj);
    }

    public void onRemoveLayer(String str) {
        this.mAjxView.onRemoveLayer(str);
    }
}
