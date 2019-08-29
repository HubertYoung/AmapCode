package com.alibaba.wireless.security.open.avmp;

import com.alibaba.wireless.security.framework.InterfacePluginInfo;
import com.alibaba.wireless.security.open.IComponent;
import com.alibaba.wireless.security.open.SecException;

@InterfacePluginInfo(pluginName = "avmp")
public interface IAVMPSoftCertComponent extends IComponent {
    byte[] generateCSR(String str, String str2, int i) throws SecException;

    String getCert(String str) throws SecException;

    boolean initAVMPSoftCert(String str) throws SecException;

    boolean installCert(String str, String str2) throws SecException;

    byte[] signWithCert(String str, byte[] bArr, int i) throws SecException;

    boolean verifyWithCert(String str, byte[] bArr, byte[] bArr2, int i) throws SecException;
}
