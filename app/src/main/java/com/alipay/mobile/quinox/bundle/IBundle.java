package com.alipay.mobile.quinox.bundle;

import com.alipay.mobile.quinox.bundle.IBundle;
import java.util.List;

public interface IBundle<T extends IBundle<T>> {
    public static final int INT_127 = 127;
    public static final int INT_27 = 27;
    public static final String TAG = "Bundle";
    public static final int VERSION_1_1_3 = 0;
    public static final int VERSION_1_3_0 = 1;
    public static final int VERSION_1_5_0 = 2;
    public static final int VERSION_1_6_0 = 3;
    public static final int VERSION_2_1_3 = 4;
    public static final int VERSION_2_3_5 = 5;

    boolean containCode();

    boolean containRes();

    long getAdler32Sum();

    List<String> getComponents();

    List<String> getDependencies();

    List<String> getExportPackages();

    int getInitLevel();

    String getLocation();

    String getMD5();

    String getName();

    List<String> getNativeLibs();

    int getPackageId();

    List<String> getPackageNames();

    @Deprecated
    long getSize();

    int getVERSION();

    String getVersion();

    T setAdler32Sum(long j);

    T setComponents(List<String> list);

    T setContainCode(boolean z);

    T setContainRes(boolean z);

    T setDependencies(List<String> list);

    T setExportPackages(List<String> list);

    T setInitLevel(int i);

    T setLocation(String str);

    T setMD5(String str);

    T setName(String str);

    T setNativeLibs(List<String> list);

    T setPackageId(int i);

    T setPackageNames(List<String> list);

    @Deprecated
    T setSize(long j);

    T setVERSION(int i);

    T setVersion(String str);
}
