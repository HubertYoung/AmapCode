package com.alipay.mobile.quinox.bundle.tools;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.quinox.bundle.Bundle;
import com.alipay.mobile.quinox.utils.StringUtil;
import java.util.Collection;

public final class BundleHelper {
    public static final String PREFIX_ALIPAY = "com.alipay.";
    public static final String SUFFIX_SNAPSHOT = "-SNAPSHOT";

    private BundleHelper() {
    }

    public static String genBundleCfgLine(Bundle bundle) {
        StringBuilder sb = new StringBuilder("lib");
        sb.append(bundle.getName());
        sb.append(".so,");
        sb.append(bundle.getName());
        sb.append(",");
        sb.append(bundle.getVersion());
        sb.append(",");
        sb.append(bundle.getSize());
        sb.append(",");
        sb.append(bundle.getMD5());
        sb.append(",");
        sb.append(bundle.getInitLevel());
        sb.append(",");
        sb.append(bundle.getPackageId());
        sb.append(",");
        sb.append(bundle.containCode());
        sb.append(",");
        sb.append(bundle.containRes());
        sb.append(",");
        sb.append(bundle.isInjectClassVerifier());
        sb.append(",");
        sb.append(bundle.getCreatedBy());
        sb.append(",");
        sb.append(bundle.getManifestVersion());
        sb.append(",");
        sb.append(StringUtil.join((Collection<T>) bundle.getNativeLibs(), (String) MergeUtil.SEPARATOR_KV));
        sb.append(",");
        sb.append(StringUtil.join((Collection<T>) bundle.getPackageNames(), (String) MergeUtil.SEPARATOR_KV));
        sb.append(",");
        sb.append(StringUtil.join((Collection<T>) bundle.getExportPackages(), (String) MergeUtil.SEPARATOR_KV));
        sb.append(",");
        sb.append(StringUtil.join((Collection<T>) bundle.getComponents(), (String) MergeUtil.SEPARATOR_KV));
        sb.append(",");
        sb.append(StringUtil.join((Collection<T>) bundle.getDependencies(), (String) MergeUtil.SEPARATOR_KV));
        return sb.toString();
    }

    public static String genGroupId(Bundle bundle) {
        return genGroupId(bundle, "");
    }

    public static String genGroupId4Alipay(Bundle bundle) {
        return genGroupId(bundle, PREFIX_ALIPAY);
    }

    public static String genGroupId(Bundle bundle, String str) {
        String name = bundle.getName();
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(name.substring(0, name.lastIndexOf(45)).replace('-', DjangoUtils.EXTENSION_SEPARATOR));
        return sb.toString();
    }

    public static String genArtifactId(Bundle bundle) {
        String name = bundle.getName();
        return name.substring(name.lastIndexOf(45) + 1);
    }

    public final String genStaticLink(Bundle bundle) {
        return genGroupId4Alipay(bundle).concat("-").concat(genArtifactId(bundle));
    }

    @Deprecated
    public static String genStaticLink4Alipay(String str) {
        return PREFIX_ALIPAY.concat(str.substring(0, str.lastIndexOf(45)).replace('-', DjangoUtils.EXTENSION_SEPARATOR)).concat("-").concat(str.substring(str.lastIndexOf(45) + 1));
    }

    public static String genBundleName(String str, String str2) {
        return str.replace(PREFIX_ALIPAY, "").replace(".", "-").concat("-").concat(str2.replace("-build", ""));
    }

    public static String genBundleVersion(String str) {
        return str.replace(SUFFIX_SNAPSHOT, "");
    }

    public static String keyToName(String str) {
        return str.substring(0, str.indexOf(64));
    }

    public static String keyToVersion(String str) {
        return str.substring(str.indexOf(64) + 1);
    }
}
