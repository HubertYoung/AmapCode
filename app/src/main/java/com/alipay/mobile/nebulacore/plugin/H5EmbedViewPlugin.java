package com.alipay.mobile.nebulacore.plugin;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Plugin.CommonEvents;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.embedviewcommon.H5NewEmbedBaseViewListener;
import com.alipay.mobile.nebula.embedviewcommon.H5NewEmbedCommonLayout;
import com.alipay.mobile.nebula.newembedview.H5NewEmbedViewProvider;
import com.alipay.mobile.nebula.newembedview.IH5NewEmbedView;
import com.alipay.mobile.nebula.provider.H5EmbededViewProvider;
import com.alipay.mobile.nebula.util.H5DimensionUtil;
import com.alipay.mobile.nebula.util.H5JSONUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.view.IH5EmbedView;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.nebulacore.R;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class H5EmbedViewPlugin extends H5SimplePlugin {
    private Map<String, JSONObject> a = new ConcurrentHashMap();
    private Map<String, Set<String>> b = new ConcurrentHashMap();
    private Map<String, String> c = new ConcurrentHashMap();
    private H5NewEmbedViewProvider d;
    /* access modifiers changed from: private */
    public boolean e = false;

    public void onPrepare(H5EventFilter filter) {
        filter.addAction("NBComponent.render");
        filter.addAction("NBComponent.sendMessage");
        filter.addAction("NBComponent.remove");
        filter.addAction("NBComponent.setData");
        filter.addAction(CommonEvents.H5_PAGE_STARTED_SYNC);
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        final H5Page h5Page = event.getH5page();
        if (h5Page != null) {
            this.d = h5Page.getNewEmbedViewProvider();
        }
        if (TextUtils.equals(action, CommonEvents.H5_PAGE_STARTED_SYNC)) {
            if (h5Page != null) {
                if (this.d != null) {
                    this.d.clearAllView();
                }
                h5Page.setNewEmbedViewRoot(null);
            }
            return false;
        }
        JSONObject params = event.getParam();
        if (!(TextUtils.equals(H5Utils.getString(params, (String) "version"), "2.0") || TextUtils.equals(action, "NBComponent.remove") || TextUtils.equals(action, "NBComponent.setData"))) {
            if (TextUtils.equals(action, "NBComponent.render") || TextUtils.equals(action, "NBComponent.sendMessage")) {
                String elementId = H5Utils.getString(params, (String) "element");
                if (h5Page != null) {
                    H5EmbededViewProvider embededViewProvider = h5Page.getEmbededViewProvider();
                    if (embededViewProvider != null) {
                        IH5EmbedView embedViewWrapper = embededViewProvider.getEmbedViewWrapperById(elementId);
                        if (TextUtils.equals(action, "NBComponent.render")) {
                            H5Log.d("H5EmbedViewPlugin", "NB_RENDER");
                            if (embedViewWrapper != null) {
                                JSONObject props = H5Utils.getJSONObject(params, "props", null);
                                JSONObject data = H5Utils.getJSONObject(params, "data", null);
                                if (props != null && !props.isEmpty()) {
                                    data.putAll(props);
                                }
                                data.put((String) "element", (Object) elementId);
                                embedViewWrapper.onReceivedRender(data, context);
                            }
                        }
                        if (TextUtils.equals(action, "NBComponent.sendMessage")) {
                            H5Log.d("H5EmbedViewPlugin", "NB_SENDMSG");
                            if (embedViewWrapper != null) {
                                String actionType = H5Utils.getString(params, (String) "actionType");
                                JSONObject data2 = H5Utils.getJSONObject(params, "data", new JSONObject());
                                data2.put((String) "element", (Object) elementId);
                                embedViewWrapper.onReceivedMessage(actionType, data2, context);
                            } else {
                                embededViewProvider.addPendingMessage(elementId, context, params);
                            }
                        }
                    }
                }
                return true;
            }
        } else if (TextUtils.equals(action, "NBComponent.render")) {
            String elementId2 = H5Utils.getString(params, (String) "element");
            if (h5Page != null && !TextUtils.isEmpty(elementId2)) {
                final H5BridgeContext h5BridgeContext = context;
                h5Page.execJavaScript4EmbedView(a(params, elementId2), new IH5EmbedViewJSCallback() {
                    public void onReceiveValue(String renderCommandStr) {
                        H5EmbedViewPlugin.this.a(renderCommandStr, h5Page, h5BridgeContext);
                    }
                });
            }
            return true;
        } else if (TextUtils.equals(action, "NBComponent.remove")) {
            a(event, params);
            return true;
        } else if (TextUtils.equals(action, "NBComponent.sendMessage")) {
            String elementId3 = H5Utils.getString(params, (String) "element");
            if (!(h5Page == null || this.d == null)) {
                IH5NewEmbedView newEmbedView = this.d.getNewEmbedViewById(elementId3);
                if (newEmbedView != null) {
                    newEmbedView.onReceivedMessage(H5Utils.getString(params, (String) "actionType"), H5Utils.getJSONObject(params, "data", null), context);
                }
            }
            return true;
        } else if (TextUtils.equals(action, "NBComponent.setData")) {
            String elementId4 = H5Utils.getString(params, (String) "element");
            if (!(h5Page == null || this.d == null)) {
                IH5NewEmbedView newEmbedView2 = this.d.getNewEmbedViewById(elementId4);
                if (newEmbedView2 != null) {
                    newEmbedView2.onReceivedData(H5Utils.getJSONObject(params, "data", null), context);
                }
            }
            return true;
        }
        return super.handleEvent(event, context);
    }

    /* access modifiers changed from: private */
    public void a(String renderCommandStr, H5Page h5Page, H5BridgeContext context) {
        JSONArray renderCommandJSONArray = JSON.parseArray(renderCommandStr);
        if (renderCommandJSONArray == null || renderCommandJSONArray.isEmpty()) {
            H5Log.w("H5EmbedViewPlugin", "fatal error renderCommand null");
            return;
        }
        H5Log.d("H5EmbedViewPlugin", "renderCommand iterate begin");
        for (int i = 0; i < renderCommandJSONArray.size(); i++) {
            JSONObject renderCommandObj = renderCommandJSONArray.getJSONObject(i);
            H5Log.d("H5EmbedViewPlugin", "renderCommandObj: " + renderCommandObj);
            final JSONObject frame = H5Utils.getJSONObject(renderCommandObj, "frame", null);
            if (!(renderCommandObj == null || frame == null)) {
                final String type = renderCommandObj.getString("type");
                final String id = renderCommandObj.getString("id");
                JSONObject lastRenderCommand = this.a.get(id);
                H5Log.d("H5EmbedViewPlugin", "lastRenderCommand: " + lastRenderCommand);
                if (lastRenderCommand == null || !lastRenderCommand.equals(renderCommandObj)) {
                    this.a.put(id, H5JSONUtil.deepCopy(renderCommandObj));
                    a(id);
                    final String parentId = renderCommandObj.getString("parentId");
                    final JSONObject data = H5Utils.getJSONObject(renderCommandObj, "data", null);
                    JSONObject props = H5Utils.getJSONObject(renderCommandObj, "props", null);
                    if (props != null && !props.isEmpty()) {
                        data.putAll(props);
                    }
                    data.put((String) "element", (Object) id);
                    final H5Page h5Page2 = h5Page;
                    final H5BridgeContext h5BridgeContext = context;
                    IH5EmbedViewJSCallback jsCallback = new IH5EmbedViewJSCallback() {
                        public void onReceiveValue(String s) {
                            H5EmbedViewPlugin.this.e = true;
                            View newEmbedViewParent = h5Page2.getNewEmbedViewRoot(new H5NewEmbedBaseViewListener() {
                                public void onNewEmbedBaseViewReady(View view) {
                                    H5Log.d("H5EmbedViewPlugin", "renderNewEmbedView in callback");
                                    H5EmbedViewPlugin.this.a(h5Page2, (H5NewEmbedCommonLayout) view, type, id, parentId, data, frame, h5BridgeContext);
                                }
                            });
                            if (newEmbedViewParent != null) {
                                H5Log.d("H5EmbedViewPlugin", "renderNewEmbedView directly");
                                H5EmbedViewPlugin.this.a(h5Page2, (H5NewEmbedCommonLayout) newEmbedViewParent, type, id, parentId, data, frame, h5BridgeContext);
                            }
                        }
                    };
                    if (this.e) {
                        jsCallback.onReceiveValue(null);
                    } else {
                        StringBuilder jsTemplate = new StringBuilder();
                        jsTemplate.append("javascript:var newembedbase = document.getElementById('newembedbase');\nif(newembedbase) {\n    console.log('newembedbase exists');\n    newembedbase.setAttribute('style', 'z-index:-9999;position:absolute;left:0px;top:0px;width:100%;height:' + document.body.scrollHeight + 'px');");
                        jsTemplate.append("} else {\n    console.log('new newembedbase');\n    var ucobjparam = document.createElement('param');\n    ucobjparam.setAttribute('name', 'type');\n    ucobjparam.setAttribute('value','newembedbase');\n    var ucobjparam2 = document.createElement('param');\n    ucobjparam2.setAttribute('name', 'zindex');\n    ucobjparam2.setAttribute('value', '9999');    var ucobj = document.createElement('object');\n    ucobj.setAttribute('type', 'application/view');\n    ucobj.setAttribute('id', 'newembedbase');\n    ucobj.setAttribute('style','position:absolute;left:0px;top:0px;z-index:-9999;width:100%;height:'+document.body.scrollHeight+'px');\n");
                        jsTemplate.append("    ucobj.appendChild(ucobjparam);\n    ucobj.appendChild(ucobjparam2);    document.body.appendChild(ucobj);\n}");
                        h5Page.execJavaScript4EmbedView(jsTemplate.toString(), jsCallback);
                    }
                } else {
                    H5Log.d("H5EmbedViewPlugin", "lastRenderCommand equals new renderCommandObj, not render for " + id);
                }
            }
        }
        H5Log.d("H5EmbedViewPlugin", "renderCommand iterate end");
    }

    private void a(String elementId) {
        Set<String> childIds = this.b.get(elementId);
        if (childIds != null) {
            for (String id : childIds) {
                this.a.remove(id);
                a(id);
            }
        }
    }

    private JSONObject b(String parentId) {
        JSONObject renderCommand = this.a.get(parentId);
        if (renderCommand != null) {
            return renderCommand.getJSONObject("frame");
        }
        return null;
    }

    private void a(String parent, String child) {
        Set childSet = this.b.get(parent);
        if (childSet == null) {
            childSet = new HashSet();
            this.b.put(parent, childSet);
        }
        childSet.add(child);
    }

    private void a(H5Event event, JSONObject params) {
        final String elementId = H5Utils.getString(params, (String) "element");
        H5Log.d("H5EmbedViewPlugin", "handleRemoveComponent " + elementId);
        final H5Page h5Page = event.getH5page();
        if (h5Page != null && !TextUtils.isEmpty(elementId)) {
            View embedViewRoot = h5Page.getNewEmbedViewRoot(new H5NewEmbedBaseViewListener() {
                public void onNewEmbedBaseViewReady(View view) {
                    H5Log.d("H5EmbedViewPlugin", "deleteNewEmbedView in callback " + elementId);
                    H5EmbedViewPlugin.this.a(h5Page, elementId, (ViewGroup) view);
                }
            });
            if (embedViewRoot != null) {
                H5Log.d("H5EmbedViewPlugin", "deleteNewEmbedView directly " + elementId);
                a(h5Page, elementId, (ViewGroup) (H5NewEmbedCommonLayout) embedViewRoot);
            }
        }
    }

    private static String a(JSONObject params, String elementId) {
        StringBuilder jsTemplate = new StringBuilder();
        jsTemplate.append("javascript:componentsManager.renderV2(\"");
        jsTemplate.append(elementId).append("\"");
        JSONObject callData = H5Utils.getJSONObject(params, "data", null);
        jsTemplate.append(callData == null ? "" : "," + callData.toJSONString());
        JSONObject callProps = H5Utils.getJSONObject(params, "props", null);
        jsTemplate.append(callProps == null ? ");" : "," + callProps.toJSONString()).append(");");
        return jsTemplate.toString();
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.alipay.mobile.h5container.api.H5Page r9, java.lang.String r10, android.view.ViewGroup r11) {
        /*
            r8 = this;
            com.alipay.mobile.nebula.newembedview.H5NewEmbedViewProvider r1 = r9.getNewEmbedViewProvider()     // Catch:{ Throwable -> 0x0043 }
            if (r1 == 0) goto L_0x0036
            com.alipay.mobile.nebula.newembedview.IH5NewEmbedView r0 = r1.getNewEmbedViewById(r10)     // Catch:{ Throwable -> 0x0043 }
            java.util.Map<java.lang.String, java.lang.String> r6 = r8.c     // Catch:{ Throwable -> 0x0043 }
            java.lang.Object r4 = r6.get(r10)     // Catch:{ Throwable -> 0x0043 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x0043 }
            r2 = 0
            boolean r6 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x0043 }
            if (r6 != 0) goto L_0x0025
            com.alipay.mobile.nebula.newembedview.IH5NewEmbedView r3 = r1.getNewEmbedViewById(r4)     // Catch:{ Throwable -> 0x0043 }
            if (r3 == 0) goto L_0x0025
            android.view.View r2 = r3.getView()     // Catch:{ Throwable -> 0x0043 }
            android.view.ViewGroup r2 = (android.view.ViewGroup) r2     // Catch:{ Throwable -> 0x0043 }
        L_0x0025:
            if (r2 != 0) goto L_0x0028
            r2 = r11
        L_0x0028:
            if (r0 == 0) goto L_0x0036
            if (r2 == 0) goto L_0x0036
            android.view.View r6 = r0.getView()     // Catch:{ Throwable -> 0x003a }
            r2.removeView(r6)     // Catch:{ Throwable -> 0x003a }
        L_0x0033:
            r1.deleteView(r10)     // Catch:{ Throwable -> 0x0043 }
        L_0x0036:
            r8.c(r10)     // Catch:{ Throwable -> 0x0043 }
        L_0x0039:
            return
        L_0x003a:
            r5 = move-exception
            java.lang.String r6 = "H5EmbedViewPlugin"
            java.lang.String r7 = "removeView error!"
            com.alipay.mobile.nebula.util.H5Log.e(r6, r7, r5)     // Catch:{ Throwable -> 0x0043 }
            goto L_0x0033
        L_0x0043:
            r5 = move-exception
            java.lang.String r6 = "H5EmbedViewPlugin"
            java.lang.String r7 = "removeView error"
            com.alipay.mobile.nebula.util.H5Log.e(r6, r7, r5)
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.plugin.H5EmbedViewPlugin.a(com.alipay.mobile.h5container.api.H5Page, java.lang.String, android.view.ViewGroup):void");
    }

    private void c(String elementId) {
        this.a.remove(elementId);
        Set<String> childSet = this.b.remove(elementId);
        if (childSet != null) {
            for (String childId : childSet) {
                c(childId);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(H5Page h5Page, ViewGroup root, String type, String id, String parentId, JSONObject data, JSONObject frame, H5BridgeContext bridgeContext) {
        if (h5Page != null) {
            try {
                this.d = h5Page.getNewEmbedViewProvider();
                if (this.d != null) {
                    Context context = h5Page.getContext().getContext();
                    float realX = frame.getFloatValue("realX");
                    float realY = frame.getFloatValue("realY");
                    float width = frame.getFloatValue("width");
                    float height = frame.getFloatValue("height");
                    String zindex = frame.getString("zindex");
                    if (TextUtils.isEmpty(zindex)) {
                        zindex = null;
                    }
                    float parentX = 0.0f;
                    float parentY = 0.0f;
                    boolean parentHasRender = true;
                    if (!TextUtils.isEmpty(parentId)) {
                        if (this.d.getNewEmbedViewById(parentId) != null) {
                            root = (ViewGroup) this.d.getNewEmbedViewById(parentId).getView();
                            JSONObject parentFrame = b(parentId);
                            if (parentFrame != null) {
                                parentX = parentFrame.getFloatValue("realX");
                                parentY = parentFrame.getFloatValue("realY");
                                a(parentId, id);
                                this.c.put(id, parentId);
                            }
                        }
                        parentHasRender = false;
                        a(parentId, id);
                        this.c.put(id, parentId);
                    }
                    H5Log.d("H5EmbedViewPlugin", "renderNewEmbedView id: " + id + " with parentId: " + parentId + " width: " + width + " height: " + height + " realX: " + realX + " realY: " + realY + " parentX: " + parentX + " parentY: " + parentY + " data: " + data);
                    IH5NewEmbedView newEmbedView = this.d.getNewEmbedViewById(id);
                    if (newEmbedView == null) {
                        View view = this.d.getEmbedView(id, type);
                        view.setTag(R.id.h5_embedview_zindex, zindex);
                        view.setTag(R.id.h5_embedview_idfromjs, id);
                        LayoutParams layoutParams = new LayoutParams(H5DimensionUtil.dip2px(context, width), H5DimensionUtil.dip2px(context, height));
                        layoutParams.topMargin = H5DimensionUtil.dip2px(context, realY - parentY);
                        layoutParams.leftMargin = H5DimensionUtil.dip2px(context, realX - parentX);
                        if (parentHasRender) {
                            root.addView(view, layoutParams);
                            this.d.getNewEmbedViewById(id).onReceivedRender(data, bridgeContext);
                            return;
                        }
                        H5Log.d("H5EmbedViewPlugin", "parent not render for " + id);
                        return;
                    }
                    View view2 = this.d.getEmbedView(id, type);
                    if (!parentHasRender) {
                        H5Log.w("H5EmbedViewPlugin", "parent inconsistent for " + id + " this parent id: " + parentId);
                        return;
                    }
                    view2.setTag(R.id.h5_embedview_zindex, zindex);
                    view2.setTag(R.id.h5_embedview_idfromjs, id);
                    LayoutParams layoutParams2 = new LayoutParams(H5DimensionUtil.dip2px(context, width), H5DimensionUtil.dip2px(context, height));
                    layoutParams2.topMargin = H5DimensionUtil.dip2px(context, realY - parentY);
                    layoutParams2.leftMargin = H5DimensionUtil.dip2px(context, realX - parentX);
                    if (root.indexOfChild(view2) < 0) {
                        root.addView(view2, layoutParams2);
                    } else {
                        root.updateViewLayout(view2, layoutParams2);
                    }
                    newEmbedView.onReceivedRender(data, bridgeContext);
                }
            } catch (Throwable t) {
                H5Log.e("H5EmbedViewPlugin", "renderNewEmbedView error for element: " + id, t);
            }
        }
    }
}
