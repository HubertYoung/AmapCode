package com.alipay.android.phone.mobilecommon.multimediabiz.biz.material;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.material.APFilterInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.APMaterialInfo;
import com.alipay.android.phone.mobilecommon.multimedia.material.APPackageInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.common.TaskScheduleManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.DirConstants;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.config.items.VideoFiltersConf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.falcon.impl.FalconFactory;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.mobile.common.utils.MD5Util;
import java.io.File;
import java.util.List;

public class MaterialResourcesManager {
    public static final String BUSINESS_ID = "MM_MATERIAL";
    private static MaterialResourcesManager a = new MaterialResourcesManager();
    private Logger b = Logger.getLogger((String) "MaterialResourcesManager");

    public static String createTempSavePath(Context context, String id) {
        return new File(context.getCacheDir(), "material_" + MD5Util.encrypt(id)).getAbsolutePath();
    }

    private MaterialResourcesManager() {
    }

    public static MaterialResourcesManager get() {
        return a;
    }

    public APPackageInfo getPackageInfo(String id) {
        return null;
    }

    public boolean saveMaterialResource(APMaterialInfo materialInfo, String savePath) {
        File baseDir = new File(DirConstants.getMaterialCache(), "materials");
        FileUtils.mkdirs(baseDir);
        String name = MD5Util.getMD5String(materialInfo.materialId);
        File targetZip = new File(baseDir, name + ".z");
        File unzipDir = new File(baseDir, name);
        boolean ret = FileUtils.copyFile(new File(savePath), targetZip);
        if (ret) {
            FileUtils.unzip(targetZip.getAbsolutePath(), unzipDir.getAbsolutePath());
            ret = FalconFactory.INS.isAvailable(unzipDir.getAbsolutePath() + File.separator);
            if (!ret) {
                cleanInvalidMaterialAsync(unzipDir);
            }
        }
        FileUtils.delete(targetZip);
        return ret;
    }

    public String getMaterialPath(String id) {
        boolean z;
        if (TextUtils.isEmpty(id)) {
            return "";
        }
        File materialDir = new File(new File(DirConstants.getMaterialCache(), "materials"), MD5Util.getMD5String(id));
        String materialDirPath = materialDir.getAbsolutePath() + File.separator;
        if (!materialDir.exists() || !materialDir.isDirectory() || !FalconFactory.INS.isAvailable(materialDirPath)) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            return materialDirPath;
        }
        this.b.d("getMaterialPath id: " + id + ", path is exists, but not available!! delete it!", new Object[0]);
        cleanInvalidMaterialAsync(materialDir);
        return "";
    }

    /* access modifiers changed from: protected */
    public void cleanInvalidMaterialAsync(final File materialDir) {
        TaskScheduleManager.get().execute(new Runnable() {
            public void run() {
                FileUtils.delete(materialDir);
            }
        });
    }

    public List<APFilterInfo> getSupportedFilters() {
        return new VideoFiltersConf().mFilterInfos;
    }
}
