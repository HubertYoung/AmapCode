package com.alipay.mobile.quinox.bundle;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IBundleOperator {
    public static final String ACTIVITY_NAME = "Activity-Name";
    public static final String ANDROID_MANIFEST_XML = "AndroidManifest.xml";
    public static final String APPLICATION_NAME = "Application-Name";
    public static final String ASPECT_INFO = "Aspect-Info";
    public static final String ASSETS = "assets";
    public static final String BUNDLE_MF = "BUNDLE.MF";
    public static final String BUNDLE_NAME = "Bundle-Name";
    public static final String BUNDLE_VERSION = "Bundle-Version";
    public static final String CLASSES_DEX = "classes.dex";
    public static final String CONTAINS_CODE = "Contains-Dex";
    public static final String CONTAINS_RES = "Contains-Res";
    public static final String CREATED_BY = "Created-By";
    public static final String EXPORT_PACKAGE = "Export-Package";
    public static final String EXTENSION = ".jar";
    public static final String INIT_LEVEL = "Init-Level";
    public static final String INJECT_CLASSVERIFIER = "Inject-ClassVerifier";
    public static final String KEY_EXPORT_PACKAGES = "export.packages";
    public static final String KEY_INIT_LEVEL = "init.level";
    public static final String KEY_PACKAGE_ID = "package.id";
    public static final String KEY_PACKAGE_NAME = "package.name";
    public static final String LIB = "lib";
    public static final String MANIFEST_VERSION = "Manifest-Version";
    public static final String META_INF = "META-INF";
    public static final String META_INF_BUNDLE_MF = "META-INF/BUNDLE.MF";
    public static final String NATIVE_LIBRARY = "Native-Library";
    public static final String PACKAGE_ID = "Package-Id";
    public static final String PACKAGE_NAME = "Package-Name";
    public static final String PROVIDER_NAME = "Provider-Name";
    public static final String RECEIVER_NAME = "Receiver-Name";
    public static final String REQUIRE_BUNDLE = "Require-Bundle";
    public static final String RESOURCES_ARSC = "resources.arsc";
    public static final String SERVICE_NAME = "Service-Name";
    public static final String TAG = "BundleOperator";

    public enum BundleType {
        Unknown("bundles"),
        ByteData("bundles.cfg"),
        ProtoBuf("bundles.pb");
        
        public final String name;

        private BundleType(String str) {
            this.name = str;
        }

        public final String getName() {
            return this.name;
        }
    }

    BundleType getBundleType();

    void readBundlesFromCfg(List<String> list, Map<String, IBundle> map) throws IOException;

    void readBundlesFromInputStream(InputStream inputStream, List<String> list, Map<String, IBundle> map) throws IOException;

    @Deprecated
    void writeBundlesToCfg(List<String> list, List<IBundle> list2) throws IOException;

    void writeBundlesToCfg(List<String> list, List<IBundle> list2, boolean z) throws IOException;

    @Deprecated
    void writeBundlesToCfg2(List<String> list, List<IBundle> list2) throws IOException;
}
