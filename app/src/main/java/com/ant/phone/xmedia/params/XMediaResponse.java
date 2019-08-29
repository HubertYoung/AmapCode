package com.ant.phone.xmedia.params;

import java.util.List;

public class XMediaResponse {
    public ErrorInfo mErrInfo;
    public int mMode;
    public List<XMediaResult> mResult;

    public String toString() {
        if (this.mResult == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\tmMode:" + this.mMode);
        stringBuffer.append(",mErrInfo:" + this.mErrInfo);
        stringBuffer.append(",mResult:");
        for (int i = 0; i < this.mResult.size(); i++) {
            XMediaResult result = this.mResult.get(i);
            if (result instanceof XMediaClassifyResult) {
                XMediaClassifyResult classifyResult = (XMediaClassifyResult) result;
                stringBuffer.append("\t|label:" + classifyResult.mLabel);
                stringBuffer.append(",confidence:" + classifyResult.mConfidence);
            }
            if (result instanceof XMediaDetectResult) {
                stringBuffer.append(", rect:" + ((XMediaDetectResult) result).mBoundingBox.toString());
            }
        }
        return stringBuffer.toString();
    }
}
