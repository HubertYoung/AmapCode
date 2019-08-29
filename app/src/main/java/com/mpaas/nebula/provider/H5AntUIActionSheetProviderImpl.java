package com.mpaas.nebula.provider;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.antui.badge.AUBadgeView;
import com.alipay.mobile.antui.badge.AUBadgeView.Style;
import com.alipay.mobile.antui.dialog.AUListDialog;
import com.alipay.mobile.antui.dialog.AUListDialog.OnItemClickListener;
import com.alipay.mobile.antui.dialog.PopMenuItem;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.nebula.provider.H5ActionSheetProvider;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class H5AntUIActionSheetProviderImpl implements H5ActionSheetProvider {
    /* access modifiers changed from: private */
    public AUListDialog a;
    /* access modifiers changed from: private */
    public boolean b = false;

    public Dialog getAntUIActionSheet() {
        return this.a;
    }

    public void setContextAndContent(Context context, ArrayList<String> arrayList, String title, H5BridgeContext bridgeContext, OnClickListener onClickListener) {
        setContextAndContent(context, arrayList, null, title, bridgeContext, onClickListener);
    }

    public void setContextAndContent(final Context context, ArrayList<String> arrayList, ArrayList<JSONObject> arrayListRedTexts, String title, final H5BridgeContext bridgeContext, final OnClickListener onClickListener) {
        if (arrayList == null || arrayList.isEmpty()) {
            this.a = null;
            return;
        }
        if (!TextUtils.isEmpty(title) || (arrayListRedTexts != null && !arrayListRedTexts.isEmpty())) {
            ArrayList menuItemList = new ArrayList();
            for (int index = 0; index < arrayList.size(); index++) {
                menuItemList.add(new PopMenuItem(arrayList.get(index), (Drawable) null));
            }
            if (arrayListRedTexts != null) {
                Iterator<JSONObject> it = arrayListRedTexts.iterator();
                while (it.hasNext()) {
                    JSONObject object = it.next();
                    Integer index2 = null;
                    try {
                        index2 = object.getInteger("index");
                    } catch (Throwable throwable) {
                        H5Log.e((String) "H5AntUIActionSheetProviderImpl", throwable);
                    }
                    if (index2 != null && index2.intValue() >= 0 && index2.intValue() < arrayList.size()) {
                        HashMap externParam = new HashMap();
                        externParam.put(AUBadgeView.KEY_BADGE_STYLE, Style.fromString(H5Utils.getString(object, (String) "type")));
                        externParam.put(AUBadgeView.KEY_BADGE_CONTENT, H5Utils.getString(object, (String) "text"));
                        ((PopMenuItem) menuItemList.get(index2.intValue())).setExternParam(externParam);
                    }
                }
            }
            this.a = new AUListDialog((String) null, title, menuItemList, context);
        } else {
            this.a = new AUListDialog(context, arrayList);
        }
        if (this.a != null) {
            this.a.setOnItemClickListener((OnItemClickListener) new OnItemClickListener() {
                public void onItemClick(int i) {
                    H5Log.d("H5AntUIActionSheetProviderImpl", "onItemClick " + i);
                    if (onClickListener != null) {
                        View view = new View(context);
                        view.setTag(Integer.valueOf(i));
                        onClickListener.onClick(view);
                    }
                    H5AntUIActionSheetProviderImpl.this.b = true;
                    JSONObject data = new JSONObject();
                    data.put((String) "success", (Object) "true");
                    data.put((String) "index", (Object) Integer.valueOf(i));
                    if (bridgeContext != null) {
                        bridgeContext.sendBridgeResult(data);
                    }
                    if (H5AntUIActionSheetProviderImpl.this.a != null) {
                        H5AntUIActionSheetProviderImpl.this.a.cancel();
                    }
                }
            });
            this.a.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    H5Log.d("H5AntUIActionSheetProviderImpl", "onCancel");
                    if (!H5AntUIActionSheetProviderImpl.this.b) {
                        JSONObject data = new JSONObject();
                        data.put((String) "success", (Object) "true");
                        data.put((String) "index", (Object) Integer.valueOf(-1));
                        if (bridgeContext != null) {
                            bridgeContext.sendBridgeResult(data);
                        }
                    }
                    H5AntUIActionSheetProviderImpl.this.b = false;
                }
            });
        }
    }

    public void updateActionSheetContent(ArrayList<String> arrayList) {
        if (this.a != null) {
            ArrayList menuItemList = new ArrayList();
            for (int index = 0; index < arrayList.size(); index++) {
                menuItemList.add(new PopMenuItem(arrayList.get(index), (Drawable) null));
            }
            this.a.updateData(menuItemList);
        }
    }

    public void onRelease() {
        this.a = null;
    }
}
