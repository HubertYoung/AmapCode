package com.autonavi.minimap.ajx3;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import proguard.annotation.KeepName;
import proguard.annotation.KeepPublicClassMembers;

@KeepPublicClassMembers
@KeepName
public class ViewAttributeGetter {
    private static ViewAttributeGetter sInstance;
    private List<IAjxContext> mAjxContextList = new ArrayList();
    private JSONArray mPathAndVersionArray = new JSONArray();
    private List<String> mPaths = new ArrayList();
    private int mSize = 0;
    private List<String> mValue = new ArrayList();

    public static ViewAttributeGetter getInstance() {
        if (sInstance == null) {
            sInstance = new ViewAttributeGetter();
        }
        return sInstance;
    }

    public static List<String> getValue() {
        if (sInstance == null) {
            return null;
        }
        return sInstance.getResult();
    }

    public static String getVersionAndPath() {
        if (sInstance == null) {
            return null;
        }
        return sInstance.getVersionAndPathValue();
    }

    public static int begin() {
        if (sInstance == null) {
            return 0;
        }
        return sInstance.beginGetFromEngine();
    }

    public List<String> getResult() {
        return this.mValue;
    }

    public String getVersionAndPathValue() {
        return this.mPathAndVersionArray.toString();
    }

    public int beginGetFromEngine() {
        this.mValue.clear();
        this.mPathAndVersionArray = new JSONArray();
        this.mPaths.clear();
        this.mSize = 0;
        if (this.mAjxContextList.size() <= 0) {
            return 0;
        }
        for (IAjxContext next : this.mAjxContextList) {
            if (next != null && !next.hasDestroy()) {
                run(next, next.getDomTree().getRootView());
            }
        }
        return this.mSize;
    }

    public void savePage(String str, String str2) {
        if (!this.mPaths.contains(str)) {
            this.mPaths.add(str);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("version", str2);
                jSONObject.put("path", str);
                this.mPathAndVersionArray.put(jSONObject);
            } catch (Exception unused) {
            }
        }
    }

    public void putValue(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) && str2.contains(MetaRecord.LOG_SEPARATOR)) {
            String[] split = str2.split(MetaRecord.LOG_SEPARATOR);
            String[] split2 = split[1].split(",");
            JSONObject jSONObject = new JSONObject();
            if (split2.length == 4) {
                String str4 = split2[0];
                String str5 = split2[1];
                String str6 = split2[2];
                String str7 = split2[3];
                String replace = str4.replace("cx=", "");
                String replace2 = str5.replace("cy=", "");
                String replace3 = str6.replace("sx=", "");
                String replace4 = str7.replace("sy=", "");
                try {
                    jSONObject.put(DictionaryKeys.CTRLXY_X, replace);
                    jSONObject.put(DictionaryKeys.CTRLXY_Y, replace2);
                    jSONObject.put("w", replace3);
                    jSONObject.put("h", replace4);
                } catch (Exception unused) {
                }
            }
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("stack", split[0]);
                jSONObject2.put("content", str3);
                jSONObject2.put("size", jSONObject.toString());
                jSONObject2.put("path", str);
            } catch (Exception unused2) {
            }
            if (jSONObject2.length() > 0) {
                this.mValue.add(jSONObject2.toString());
            }
        }
    }

    public void deleteContext(IAjxContext iAjxContext) {
        if (this.mAjxContextList.contains(iAjxContext)) {
            this.mAjxContextList.remove(iAjxContext);
        }
    }

    public void setContext(IAjxContext iAjxContext) {
        if (!this.mAjxContextList.contains(iAjxContext)) {
            this.mAjxContextList.add(iAjxContext);
        }
    }

    private void run(IAjxContext iAjxContext, View view) {
        if (view instanceof ViewGroup) {
            invokeToGetNodeStatus(iAjxContext, view, iAjxContext.getDomTree().getRootNode());
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            List<View> arrayList = new ArrayList<>();
            if (childCount > 0) {
                for (int i = 0; i < childCount; i++) {
                    if (viewGroup.getChildAt(i) != null && isViewVisiable(viewGroup.getChildAt(i))) {
                        arrayList.add(viewGroup.getChildAt(i));
                    }
                }
                for (View view2 : arrayList) {
                    if (view2 instanceof ViewExtension) {
                        ViewExtension viewExtension = (ViewExtension) view2;
                        if (!(viewExtension.getProperty() == null || viewExtension.getProperty().getNode() == null || view2.getVisibility() != 0)) {
                            invokeToGetNodeStatus(iAjxContext, view2, viewExtension.getProperty().getNode());
                        }
                    }
                }
                while (arrayList != null && arrayList.size() > 0) {
                    arrayList = getNextChildrenView(arrayList);
                    for (View view3 : arrayList) {
                        if (view3 instanceof ViewExtension) {
                            ViewExtension viewExtension2 = (ViewExtension) view3;
                            if (!(viewExtension2.getProperty() == null || viewExtension2.getProperty().getNode() == null || view3.getVisibility() != 0)) {
                                invokeToGetNodeStatus(iAjxContext, view3, viewExtension2.getProperty().getNode());
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isViewVisiable(View view) {
        if (view.getVisibility() != 0) {
            return false;
        }
        boolean localVisibleRect = view.getLocalVisibleRect(new Rect());
        boolean globalVisibleRect = view.getGlobalVisibleRect(new Rect());
        if (!localVisibleRect || !globalVisibleRect) {
            return false;
        }
        return true;
    }

    private List<View> getNextChildrenView(List<View> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (View next : list) {
            if (isViewVisiable(next)) {
                if (next instanceof ViewExtension) {
                    ViewExtension viewExtension = (ViewExtension) next;
                    if (viewExtension.getProperty() != null && viewExtension.getProperty().hasGroupId()) {
                    }
                }
                if (next instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) next;
                    int childCount = viewGroup.getChildCount();
                    if (childCount > 0) {
                        for (int i = 0; i < childCount; i++) {
                            arrayList.add(viewGroup.getChildAt(i));
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private void invokeToGetNodeStatus(IAjxContext iAjxContext, View view, AjxDomNode ajxDomNode) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        int standardUnitToPixel = DimensionUtils.standardUnitToPixel(ajxDomNode.getWidth());
        int standardUnitToPixel2 = DimensionUtils.standardUnitToPixel(ajxDomNode.getHeight());
        Parcel parcel = new Parcel();
        parcel.writeBoolean(true);
        parcel.writeDouble((double) i);
        parcel.writeDouble((double) i2);
        parcel.writeDouble((double) standardUnitToPixel);
        parcel.writeDouble((double) standardUnitToPixel2);
        parcel.writeString("");
        Object attributeValue = ajxDomNode.getAttributeValue("groupid");
        String str = "";
        if (attributeValue instanceof String) {
            str = (String) attributeValue;
        }
        this.mSize++;
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb = new StringBuilder("#cx=");
            sb.append(i);
            sb.append(",cy=");
            sb.append(i2);
            sb.append(",sx=");
            sb.append(standardUnitToPixel);
            sb.append(",sy=");
            sb.append(standardUnitToPixel2);
            putValue(iAjxContext.getJsRunInfo().getUrl(), sb.toString(), str);
            return;
        }
        iAjxContext.invokeJsEvent(new Builder().setEventName("testAllView").setNodeId(ajxDomNode.getId()).setHoverNodeId(ajxDomNode.getId()).setTouch(parcel).build());
    }
}
