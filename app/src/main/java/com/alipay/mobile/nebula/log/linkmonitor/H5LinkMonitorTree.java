package com.alipay.mobile.nebula.log.linkmonitor;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class H5LinkMonitorTree {
    private static final String TAG = "H5LinkMonitorTree";
    private Map<String, H5LinkNode> linkNodeMap = new ConcurrentHashMap();
    private H5LinkNode rootNode;

    public void addNode(H5LinkNode linkNode, String parentNodeName) {
        if (linkNode == null) {
            H5Log.w(TAG, "linkNode == null");
            return;
        }
        this.linkNodeMap.put(linkNode.getNodeName(), linkNode);
        if (this.rootNode == null) {
            this.rootNode = linkNode;
            return;
        }
        H5LinkNode parentNode = this.linkNodeMap.get(parentNodeName);
        if (parentNode == null) {
            this.rootNode.addChildNode(linkNode);
            linkNode.setParentNode(this.rootNode);
            return;
        }
        parentNode.addChildNode(linkNode);
        linkNode.setParentNode(parentNode);
    }

    public List<H5LinkNode> getLinkMonitorList() {
        List list = new ArrayList();
        if (!this.linkNodeMap.isEmpty()) {
            for (Entry<String, H5LinkNode> mapEntry : this.linkNodeMap.entrySet()) {
                list.add(mapEntry.getValue());
            }
            Collections.sort(list, new Comparator<H5LinkNode>() {
                public int compare(H5LinkNode lhs, H5LinkNode rhs) {
                    long lTime = lhs.getTriggerTime();
                    long rTime = rhs.getTriggerTime();
                    if (lTime < rTime) {
                        return -1;
                    }
                    if (lTime > rTime) {
                        return 1;
                    }
                    return 0;
                }
            });
        }
        return list;
    }

    public H5LinkNode getLinkNodeByName(String nodeName) {
        return this.linkNodeMap.get(nodeName);
    }

    public boolean containsNode(String nodeName) {
        return this.linkNodeMap.containsKey(nodeName);
    }

    public void logExpectationFail(String linkId, String timeoutNodeName, int timeout) {
        H5LogData logData = H5LogData.seedId("H5_LINK_EXPECTATION_FAIL").param3().add("linkId", linkId).add("step", timeoutNodeName).add("timeout", Integer.valueOf(timeout));
        List nodeList = getLinkMonitorList();
        String replayStr = "";
        if (nodeList.size() > 0) {
            for (H5LinkNode linkNode : nodeList) {
                if (TextUtils.isEmpty(replayStr)) {
                    replayStr = replayStr + linkNode.getNodeName() + "(" + linkNode.getTriggerTime() + ")";
                } else {
                    replayStr = replayStr + "->" + linkNode.getNodeName() + "(" + linkNode.getTriggerTime() + ")";
                }
            }
        }
        logData.param3().add("replays", replayStr);
        H5Service h5Service = H5ServiceUtils.getH5Service();
        H5Page h5Page = null;
        if (h5Service != null) {
            h5Page = h5Service.getTopH5Page();
        }
        if (!(h5Page == null || h5Page.getPageData() == null)) {
            logData.param4().addUniteParam(h5Page.getPageData());
        }
        H5LogUtil.logH5Exception(logData);
    }
}
