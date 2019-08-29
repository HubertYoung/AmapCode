package com.alipay.mobile.monitor.track.xpath;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.monitor.track.interceptor.AutoClickInterceptor;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.ArrayList;
import java.util.List;

public class XPathFinder {
    public static String a = "android:id/content";

    private static List<ViewParent> b(View origin) {
        if (origin == null) {
            return null;
        }
        ArrayList viewList = new ArrayList();
        for (ViewParent parentNode = origin.getParent(); parentNode != null; parentNode = parentNode.getParent()) {
            if (parentNode instanceof View) {
                View item = (View) parentNode;
                int id = item.getId();
                if (id != -1) {
                    String des = null;
                    try {
                        des = item.getResources().getResourceName(id);
                    } catch (Throwable e) {
                        LoggerFactory.getTraceLogger().error((String) "XPathFinder", e);
                    }
                    if (a.equals(des)) {
                        return viewList;
                    }
                } else {
                    continue;
                }
            }
            viewList.add(parentNode);
        }
        return viewList;
    }

    public static String a(View view) {
        if (view == null) {
            return null;
        }
        List list = b(view);
        if (list == null || list.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int abIndex = -1;
        for (int i = list.size() - 1; i >= 0; i--) {
            ViewParent node = list.get(i);
            ViewParent nodeParent = node.getParent();
            int index = 0;
            if (nodeParent != null && (nodeParent instanceof ViewGroup)) {
                index = ((ViewGroup) nodeParent).indexOfChild((View) node);
            }
            String controlId = "-";
            if (node != null && (node instanceof View)) {
                controlId = c((View) node);
            }
            if (nodeParent != null && (nodeParent instanceof AdapterView)) {
                abIndex = ((AdapterView) nodeParent).getFirstVisiblePosition() + index;
                sb.append("/").append(node.getClass().getSimpleName() + "[" + index + "]").append(":" + controlId);
            } else if (nodeParent == null || !(nodeParent instanceof RecyclerView)) {
                sb.append("/").append(node.getClass().getSimpleName() + "[" + index + "]").append(":" + controlId);
            } else {
                if (node instanceof View) {
                    try {
                        abIndex = ((RecyclerView) nodeParent).getChildAdapterPosition((View) node);
                    } catch (Throwable e) {
                        LoggerFactory.getTraceLogger().error((String) "XPathFinder", e);
                    }
                } else {
                    LoggerFactory.getTraceLogger().info("XPathFinder", "node is not view");
                }
                sb.append("/").append(node.getClass().getSimpleName() + "[" + index + "]").append(":" + controlId);
            }
        }
        String controlId2 = "-";
        if (view != null && (view instanceof View)) {
            controlId2 = c(view);
        }
        ViewParent vp = view.getParent();
        if (vp == null || !(vp instanceof ViewGroup)) {
            sb.append("/").append(view.getClass().getSimpleName());
        } else {
            int viewIndex = ((ViewGroup) vp).indexOfChild(view);
            if (vp instanceof AdapterView) {
                abIndex = ((AdapterView) vp).getFirstVisiblePosition() + viewIndex;
                sb.append("/").append(view.getClass().getSimpleName() + "[" + viewIndex + "]").append(":" + controlId2);
            } else if (vp instanceof RecyclerView) {
                if (view instanceof View) {
                    try {
                        abIndex = ((RecyclerView) vp).getChildAdapterPosition(view);
                    } catch (Throwable e2) {
                        LoggerFactory.getTraceLogger().error((String) "XPathFinder", e2);
                    }
                } else {
                    LoggerFactory.getTraceLogger().info("XPathFinder", "node is not view");
                }
                sb.append("/").append(view.getClass().getSimpleName() + "[" + viewIndex + "]").append(":" + controlId2);
            } else {
                sb.append("/").append(view.getClass().getSimpleName() + "[" + viewIndex + "]").append(":" + controlId2);
            }
        }
        sb.append(MergeUtil.SEPARATOR_KV).append(abIndex);
        sb.insert(0, "//" + view.getContext().getClass().getName());
        return sb.toString();
    }

    private static String c(View view) {
        return AutoClickInterceptor.getControlId(view, "-").replace("/", "^");
    }
}
