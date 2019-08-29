package com.alibaba.wireless.security.open.litevm;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

@InterfacePluginInfo(pluginName = "main")
public interface ILiteVMComponent extends IComponent {
    byte[] callLiteVMByteMethod(LiteVMInstance liteVMInstance, int i, LiteVMParameterWrapper[] liteVMParameterWrapperArr) throws SecException;

    long callLiteVMLongMethod(LiteVMInstance liteVMInstance, int i, LiteVMParameterWrapper[] liteVMParameterWrapperArr) throws SecException;

    String callLiteVMStringMethod(LiteVMInstance liteVMInstance, int i, LiteVMParameterWrapper[] liteVMParameterWrapperArr) throws SecException;

    void callLiteVMVoidMethod(LiteVMInstance liteVMInstance, int i, LiteVMParameterWrapper[] liteVMParameterWrapperArr) throws SecException;

    LiteVMInstance createLiteVMInstance(String str, String str2, byte[] bArr, Object[] objArr) throws SecException;

    void destroyLiteVMInstance(LiteVMInstance liteVMInstance) throws SecException;

    void reloadLiteVMInstance(LiteVMInstance liteVMInstance, byte[] bArr) throws SecException;
}
