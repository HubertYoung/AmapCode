package com.alipay.mobile.quinox.bundle;

import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.quinox.bundle.tools.BundleHelper;
import com.alipay.mobile.quinox.log.Log;
import com.alipay.mobile.quinox.utils.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class Bundle implements IBundle<Bundle>, Comparable<Bundle> {
    private List<String> mActivities;
    private List<String> mBroadcastReceivers;
    private List<String> mContentProviders;
    private String mCreatedBy;
    private Set<String> mDependNames;
    private boolean mInjectClassVerifier = false;
    boolean mIsDisable;
    private String mManifestVersion;
    private List<String> mServices;
    final IBundle mTarget;

    public Bundle(IBundle iBundle) {
        this.mTarget = iBundle;
    }

    public String getCreatedBy() {
        return this.mCreatedBy;
    }

    public Bundle setCreatedBy(String str) {
        this.mCreatedBy = str;
        return this;
    }

    public String getManifestVersion() {
        return this.mManifestVersion;
    }

    public Bundle setManifestVersion(String str) {
        this.mManifestVersion = str;
        return this;
    }

    public boolean isInjectClassVerifier() {
        return this.mInjectClassVerifier;
    }

    public Bundle setInjectClassVerifier(boolean z) {
        this.mInjectClassVerifier = z;
        return this;
    }

    public String getLocation() {
        return this.mTarget.getLocation();
    }

    public Bundle setLocation(String str) {
        this.mTarget.setLocation(str);
        return this;
    }

    public String getName() {
        return this.mTarget.getName();
    }

    public Bundle setName(String str) {
        this.mTarget.setName(str);
        return this;
    }

    public String getVersion() {
        return this.mTarget.getVersion();
    }

    public Bundle setVersion(String str) {
        this.mTarget.setVersion(str);
        return this;
    }

    public int getInitLevel() {
        return this.mTarget.getInitLevel();
    }

    public Bundle setInitLevel(int i) {
        this.mTarget.setInitLevel(i);
        return this;
    }

    public List<String> getPackageNames() {
        return this.mTarget.getPackageNames();
    }

    public Bundle setPackageNames(List<String> list) {
        this.mTarget.setPackageNames(list);
        return this;
    }

    public List<String> getExportPackages() {
        return this.mTarget.getExportPackages();
    }

    public Bundle setExportPackages(List<String> list) {
        this.mTarget.setExportPackages(list);
        return this;
    }

    public List<String> getComponents() {
        return this.mTarget.getComponents();
    }

    public Bundle setComponents(List<String> list) {
        this.mTarget.setComponents(list);
        return this;
    }

    public List<String> getContentProviders() {
        return this.mContentProviders;
    }

    public Bundle setContentProviders(List<String> list) {
        this.mContentProviders = list;
        return this;
    }

    public Bundle setBroadcastReceivers(List<String> list) {
        this.mBroadcastReceivers = list;
        return this;
    }

    public List<String> getBroadcastReceivers() {
        return this.mBroadcastReceivers;
    }

    public List<String> getServices() {
        return this.mServices;
    }

    public Bundle setServices(List<String> list) {
        this.mServices = list;
        return this;
    }

    public List<String> getActivities() {
        return this.mActivities;
    }

    public Bundle setActivities(List<String> list) {
        this.mActivities = list;
        return this;
    }

    public int getPackageId() {
        return this.mTarget.getPackageId();
    }

    public Bundle setPackageId(int i) {
        this.mTarget.setPackageId(i);
        return this;
    }

    public boolean containRes() {
        return this.mTarget.containRes();
    }

    public Bundle setContainRes(boolean z) {
        this.mTarget.setContainRes(z);
        return this;
    }

    public boolean containCode() {
        return this.mTarget.containCode();
    }

    public Bundle setContainCode(boolean z) {
        this.mTarget.setContainCode(z);
        return this;
    }

    public List<String> getNativeLibs() {
        return this.mTarget.getNativeLibs();
    }

    public Bundle setNativeLibs(List<String> list) {
        this.mTarget.setNativeLibs(list);
        return this;
    }

    public boolean containNativeLibs() {
        List<String> nativeLibs = getNativeLibs();
        return nativeLibs != null && !nativeLibs.isEmpty();
    }

    public List<String> getDependencies() {
        return this.mTarget.getDependencies();
    }

    public Bundle setDependencies(List<String> list) {
        this.mTarget.setDependencies(list);
        return this;
    }

    public long getSize() {
        return this.mTarget.getAdler32Sum();
    }

    public Bundle setSize(long j) {
        this.mTarget.setAdler32Sum(j);
        return this;
    }

    public long getAdler32Sum() {
        return this.mTarget.getAdler32Sum();
    }

    public Bundle setAdler32Sum(long j) {
        this.mTarget.setAdler32Sum(j);
        return this;
    }

    public String getMD5() {
        return this.mTarget.getMD5();
    }

    public Bundle setMD5(String str) {
        this.mTarget.setMD5(str);
        return this;
    }

    public int getVERSION() {
        return this.mTarget.getVERSION();
    }

    public Bundle setVERSION(int i) {
        this.mTarget.setVERSION(i);
        return this;
    }

    public synchronized boolean isDisable() {
        return this.mIsDisable;
    }

    public synchronized Bundle setDisable(boolean z) {
        this.mIsDisable = z;
        return this;
    }

    public Set<String> getDependNames() {
        if (this.mDependNames == null) {
            synchronized (this) {
                if (this.mDependNames == null) {
                    HashSet hashSet = null;
                    List<String> dependencies = getDependencies();
                    if (dependencies != null && !dependencies.isEmpty()) {
                        hashSet = new HashSet(dependencies.size());
                        for (String next : dependencies) {
                            if (!StringUtil.isEmpty(next)) {
                                int indexOf = next.indexOf(AUScreenAdaptTool.PREFIX_ID);
                                if (-1 == indexOf) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(this);
                                    sb.append(" has a wrong format depend: ");
                                    sb.append(next);
                                    throw new RuntimeException(sb.toString());
                                }
                                hashSet.add(next.substring(0, indexOf));
                            }
                        }
                    }
                    if (hashSet == null) {
                        this.mDependNames = new HashSet();
                    } else {
                        this.mDependNames = new HashSet(hashSet);
                    }
                }
            }
        }
        return this.mDependNames;
    }

    public int compareTo(Bundle bundle) {
        return getInitLevel() - bundle.getInitLevel();
    }

    public boolean isPure() {
        return !containCode() && !containRes() && !containNativeLibs();
    }

    public String toString() {
        try {
            StringBuilder sb = new StringBuilder("Bundle: VERSION=");
            sb.append(getVERSION());
            sb.append(",name=");
            sb.append(getName());
            sb.append(",version=");
            sb.append(getVersion());
            sb.append(",location=");
            sb.append(getLocation());
            return sb.toString();
        } catch (Throwable th) {
            Log.w((String) IBundle.TAG, th);
            return "Invalid Bundle (null == mTarget)?";
        }
    }

    private boolean equals(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IBundle)) {
            return false;
        }
        IBundle iBundle = (IBundle) obj;
        boolean z = equals(Integer.valueOf(getVERSION()), Integer.valueOf(iBundle.getVERSION())) && equals(getName(), iBundle.getName()) && equals(getVersion(), iBundle.getVersion()) && equals(getLocation(), iBundle.getLocation()) && equals(Long.valueOf(getAdler32Sum()), Long.valueOf(iBundle.getAdler32Sum())) && equals(getMD5(), iBundle.getMD5()) && equals(Integer.valueOf(getInitLevel()), Integer.valueOf(iBundle.getInitLevel())) && equals(Integer.valueOf(getPackageId()), Integer.valueOf(iBundle.getPackageId())) && equals(Boolean.valueOf(containRes()), Boolean.valueOf(iBundle.containRes())) && equals(Boolean.valueOf(containCode()), Boolean.valueOf(iBundle.containCode())) && equals(getPackageNames(), iBundle.getPackageNames()) && equals(getExportPackages(), iBundle.getExportPackages()) && equals(getComponents(), iBundle.getComponents()) && equals(getNativeLibs(), iBundle.getNativeLibs()) && equals(getDependencies(), iBundle.getDependencies());
        if (!(obj instanceof Bundle)) {
            return z;
        }
        Bundle bundle = (Bundle) obj;
        return z && equals(Boolean.valueOf(this.mIsDisable), Boolean.valueOf(bundle.mIsDisable)) && equals(this.mDependNames, bundle.mDependNames) && equals(this.mActivities, bundle.mActivities) && equals(this.mServices, bundle.mServices) && equals(this.mContentProviders, bundle.mContentProviders) && equals(this.mBroadcastReceivers, bundle.mBroadcastReceivers) && equals(Boolean.valueOf(this.mInjectClassVerifier), Boolean.valueOf(bundle.mInjectClassVerifier)) && equals(this.mCreatedBy, bundle.mCreatedBy) && equals(this.mManifestVersion, bundle.mManifestVersion);
    }

    public void initFromInputStream(InputStream inputStream) throws IOException {
        Attributes mainAttributes = new Manifest(inputStream).getMainAttributes();
        setName(mainAttributes.getValue(IBundleOperator.BUNDLE_NAME));
        setVersion(BundleHelper.genBundleVersion(mainAttributes.getValue(IBundleOperator.BUNDLE_VERSION)));
        setInitLevel(Integer.parseInt(mainAttributes.getValue(IBundleOperator.INIT_LEVEL)));
        String value = mainAttributes.getValue(IBundleOperator.PACKAGE_NAME);
        if (!StringUtil.isEmpty(value)) {
            setPackageNames(Arrays.asList(value.split(",")));
        }
        ArrayList arrayList = new ArrayList();
        String value2 = mainAttributes.getValue(IBundleOperator.ACTIVITY_NAME);
        if (!StringUtil.isEmpty(value2)) {
            List asList = Arrays.asList(value2.split(","));
            setActivities(asList);
            arrayList.addAll(asList);
        }
        String value3 = mainAttributes.getValue(IBundleOperator.SERVICE_NAME);
        if (!StringUtil.isEmpty(value3)) {
            List asList2 = Arrays.asList(value3.split(","));
            setServices(asList2);
            arrayList.addAll(asList2);
        }
        String value4 = mainAttributes.getValue(IBundleOperator.RECEIVER_NAME);
        if (!StringUtil.isEmpty(value4)) {
            List asList3 = Arrays.asList(value4.split(","));
            setBroadcastReceivers(asList3);
            arrayList.addAll(asList3);
        }
        String value5 = mainAttributes.getValue(IBundleOperator.PROVIDER_NAME);
        if (!StringUtil.isEmpty(value5)) {
            List asList4 = Arrays.asList(value5.split(","));
            setContentProviders(asList4);
            arrayList.addAll(asList4);
        }
        if (!arrayList.isEmpty()) {
            setComponents((List<String>) arrayList);
        }
        setPackageId(Integer.parseInt(mainAttributes.getValue(IBundleOperator.PACKAGE_ID)));
        setContainCode(Boolean.parseBoolean(mainAttributes.getValue(IBundleOperator.CONTAINS_CODE)));
        setContainRes(Boolean.parseBoolean(mainAttributes.getValue(IBundleOperator.CONTAINS_RES)));
        String value6 = mainAttributes.getValue(IBundleOperator.NATIVE_LIBRARY);
        if (!StringUtil.isEmpty(value6)) {
            setNativeLibs(Arrays.asList(value6.split(",")));
        }
        String value7 = mainAttributes.getValue(IBundleOperator.REQUIRE_BUNDLE);
        if (!StringUtil.isEmpty(value7)) {
            setDependencies(Arrays.asList(value7.split(",")));
        }
        String value8 = mainAttributes.getValue(IBundleOperator.EXPORT_PACKAGE);
        if (!StringUtil.isEmpty(value8)) {
            setExportPackages(Arrays.asList(value8.split(",")));
        }
        String value9 = mainAttributes.getValue(IBundleOperator.INJECT_CLASSVERIFIER);
        if (!StringUtil.isEmpty(value9)) {
            setInjectClassVerifier(Boolean.valueOf(value9).booleanValue());
        }
        setCreatedBy(mainAttributes.getValue(IBundleOperator.CREATED_BY));
        setManifestVersion(mainAttributes.getValue(IBundleOperator.MANIFEST_VERSION));
    }

    public String toLongString() {
        return this.mTarget.toString();
    }

    public boolean check(String str) {
        return str != null && check(new File(str));
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x009d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean check(java.io.File r9) {
        /*
            r8 = this;
            r0 = 1
            r1 = 0
            r2 = 0
            if (r9 != 0) goto L_0x000a
            java.lang.String r2 = "bundle.check() same=false : file == null"
        L_0x0007:
            r0 = 0
            goto L_0x009b
        L_0x000a:
            boolean r3 = r9.exists()
            if (r3 != 0) goto L_0x0024
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "bundle.check("
            r0.<init>(r2)
            r0.append(r9)
            java.lang.String r9 = ") same=false : file is not exists."
            r0.append(r9)
            java.lang.String r2 = r0.toString()
            goto L_0x0007
        L_0x0024:
            int r3 = r8.getVERSION()
            switch(r3) {
                case 3: goto L_0x006d;
                case 4: goto L_0x003e;
                default: goto L_0x002b;
            }
        L_0x002b:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r1 = "Unknown Bundle Format Version:"
            r9.<init>(r1)
            int r1 = r8.getVERSION()
            r9.append(r1)
            java.lang.String r2 = r9.toString()
            goto L_0x009b
        L_0x003e:
            long r3 = r8.getAdler32Sum()
            long r5 = com.alipay.mobile.quinox.security.Adler32Verifier.genFileAdler32Sum(r9)
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x004b
            goto L_0x004c
        L_0x004b:
            r0 = 0
        L_0x004c:
            if (r0 != 0) goto L_0x009b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "bundle.check("
            r1.<init>(r2)
            r1.append(r9)
            java.lang.String r9 = ") same=false : genFileAdler32Sum(file)="
            r1.append(r9)
            r1.append(r5)
            java.lang.String r9 = ", bundle.getAdler32Sum()="
            r1.append(r9)
            r1.append(r3)
            java.lang.String r2 = r1.toString()
            goto L_0x009b
        L_0x006d:
            long r3 = r8.getSize()
            long r5 = r9.length()
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L_0x007a
            goto L_0x007b
        L_0x007a:
            r0 = 0
        L_0x007b:
            if (r0 != 0) goto L_0x009b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "bundle.check("
            r1.<init>(r2)
            r1.append(r9)
            java.lang.String r9 = ") same=false : file.length()="
            r1.append(r9)
            r1.append(r5)
            java.lang.String r9 = ", bundle.getSize()="
            r1.append(r9)
            r1.append(r3)
            java.lang.String r2 = r1.toString()
        L_0x009b:
            if (r2 == 0) goto L_0x00a2
            java.lang.String r9 = "Bundle"
            com.alipay.mobile.quinox.log.Log.e(r9, r2)
        L_0x00a2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.bundle.Bundle.check(java.io.File):boolean");
    }
}
