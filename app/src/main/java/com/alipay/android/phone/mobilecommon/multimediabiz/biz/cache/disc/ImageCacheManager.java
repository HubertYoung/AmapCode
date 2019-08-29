package com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.disc;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APGifQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageBigQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageCacheQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageClearCacheQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageOriginalQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageQueryResult;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageSourceCutQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.APImageThumbnailQuery;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CacheImageOptions;
import com.alipay.android.phone.mobilecommon.multimedia.graphics.data.CutScaleType;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.BitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.CacheContext;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.OriginalBitmapCacheKey;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.memory.MemoryCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.cache.query.QueryCacheManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.image.assist.ImageDiskCache;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.statistic.image.ImageCachePerf;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CacheUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CompareUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.FileUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.ImageUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.PathUtils;
import com.alipay.diskcache.DiskCache.QueryFilter;
import com.alipay.diskcache.model.FileCacheModel;
import com.alipay.multimedia.img.ImageInfo;
import com.autonavi.common.SuperId;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImageCacheManager {
    /* access modifiers changed from: private */
    public static final Logger a = Logger.getLogger((String) "ImageCacheManager");

    public APImageQueryResult<APImageCacheQuery> queryImageFor(APImageCacheQuery query) {
        APImageQueryResult result = new APImageQueryResult();
        result.success = false;
        result.query = query;
        if (QueryCacheManager.getInstance().getConf().getImageSwitch()) {
            a.d("queryImageFor off APImageCacheQuery, result: " + result, new Object[0]);
        } else {
            File cacheFile = ImageDiskCache.get().getCacheFile(new BitmapCacheKey(query.path, query.width, query.height, query.cutScaleType, query.plugin, query.getQuality(), query.imageMarkRequest));
            if (CacheUtils.checkCacheFile(cacheFile)) {
                result.success = true;
                result.width = query.width;
                result.height = query.height;
                result.path = cacheFile.getAbsolutePath();
            }
        }
        return result;
    }

    public APImageQueryResult<APImageOriginalQuery> queryImageFor(APImageOriginalQuery query) {
        boolean success;
        APImageQueryResult result = new APImageQueryResult();
        result.success = false;
        result.query = query;
        if (QueryCacheManager.getInstance().getConf().getImageSwitch()) {
            a.d("queryImageFor off APImageOriginalQuery, result: " + result, new Object[0]);
        } else {
            if (query != null) {
                long start = System.currentTimeMillis();
                String path = "";
                if (!TextUtils.isEmpty(query.path)) {
                    String key = PathUtils.extractPath(query.path);
                    if (FileUtils.checkFile(key)) {
                        path = key;
                    } else if (FileUtils.checkFile(ImageDiskCache.get().queryAliasKey(key))) {
                        path = ImageDiskCache.get().queryAliasKey(key);
                    }
                    a.d("queryImageFor APImageOriginalQuery forKey: " + key + ", result: " + path, new Object[0]);
                    if (TextUtils.isEmpty(path)) {
                        String originalPath = ImageDiskCache.get().genPathByKey(query.path);
                        if (FileUtils.checkFile(originalPath)) {
                            path = originalPath;
                        }
                        if (TextUtils.isEmpty(path)) {
                            path = ImageDiskCache.get().genPathByKey(new OriginalBitmapCacheKey(query.path).complexCacheKey());
                            if (!FileUtils.checkFile(path)) {
                                path = "";
                            }
                        }
                    }
                }
                if (!TextUtils.isEmpty(path)) {
                    success = true;
                } else {
                    success = false;
                }
                ImageCachePerf.getCurrent().addDiskHitTime(success, System.currentTimeMillis() - start);
                if (success) {
                    result.success = true;
                    result.path = path;
                    if (query.requireImageInfo) {
                        ImageInfo info = ImageInfo.getImageInfo(result.path);
                        result.width = info.correctWidth;
                        result.height = info.correctHeight;
                    }
                }
            }
            a.d("queryOriginalCacheImage result: " + result, new Object[0]);
        }
        return result;
    }

    public APImageQueryResult<APImageBigQuery> queryImageFor(APImageBigQuery query) {
        APImageQueryResult result = new APImageQueryResult();
        result.success = false;
        result.query = query;
        if (QueryCacheManager.getInstance().getConf().getImageSwitch()) {
            a.d("queryImageFor off APImageBigQuery, result: " + result, new Object[0]);
        } else if (query != null) {
            APImageCacheQuery cacheQuery = new APImageCacheQuery(query.path, 0, 0);
            cacheQuery.setQuality(query.getQuality());
            APImageQueryResult cacheQueryResult = queryImageFor(cacheQuery);
            if (!cacheQueryResult.success) {
                cacheQuery.width = 1280;
                cacheQuery.height = 1280;
                cacheQueryResult = queryImageFor(cacheQuery);
            }
            if (cacheQueryResult.success) {
                result.success = true;
                result.path = cacheQueryResult.path;
                result.width = cacheQueryResult.width;
                result.height = cacheQueryResult.height;
            }
        }
        return result;
    }

    public APImageQueryResult<APGifQuery> queryImageFor(APGifQuery query) {
        APImageQueryResult result = new APImageQueryResult();
        result.success = false;
        result.query = query;
        if (query != null) {
            File cacheFile = ImageDiskCache.get().getCacheFile(new BitmapCacheKey(query.path, query.width.intValue(), query.height.intValue(), query.cutScaleType, query.plugin, query.getQuality(), query.imageMarkRequest));
            if (CacheUtils.checkCacheFile(cacheFile)) {
                result.success = true;
                result.width = query.width.intValue();
                result.height = query.height.intValue();
                result.path = cacheFile.getAbsolutePath();
            }
            if (!result.success) {
                APImageQueryResult tempResult = queryImageFor(new APImageClearCacheQuery(query.path));
                if (tempResult != null) {
                    result.success = tempResult.success;
                    result.width = tempResult.width;
                    result.height = tempResult.height;
                    result.path = tempResult.path;
                }
            }
        }
        return result;
    }

    /* access modifiers changed from: private */
    public static boolean b(BitmapCacheKey newOne, BitmapCacheKey oldOne) {
        if (newOne == null || oldOne == null) {
            return false;
        }
        if (newOne.pixels > oldOne.pixels) {
            return true;
        }
        if (newOne.pixels != oldOne.pixels || (newOne.quality != -1 && newOne.quality <= oldOne.quality)) {
            return false;
        }
        return true;
    }

    public APImageQueryResult<APImageClearCacheQuery> queryImageFor(APImageClearCacheQuery query) {
        APImageQueryResult result = new APImageQueryResult();
        result.success = false;
        result.query = query;
        if (QueryCacheManager.getInstance().getConf().getImageSwitch()) {
            a.d("queryImageFor off APImageClearCacheQuery, result: " + result, new Object[0]);
        } else {
            if (query != null && !TextUtils.isEmpty(query.path)) {
                APImageQueryResult queryResult = queryImageFor(new APImageOriginalQuery(query.path));
                a.d("queryClearCacheImage queryOriginal result: " + queryResult, new Object[0]);
                if (!queryResult.success) {
                    queryResult = queryImageFor(new APImageBigQuery(query.path));
                    a.d("queryClearCacheImage queryBig result: " + queryResult, new Object[0]);
                }
                if (!queryResult.success) {
                    long start = System.currentTimeMillis();
                    try {
                        FileCacheModel cacheModel = ImageDiskCache.get().get(query.path, new QueryFilter() {
                            public FileCacheModel parse(List<FileCacheModel> list) {
                                BitmapCacheKey tempCacheKey = null;
                                for (FileCacheModel m : list) {
                                    if (!TextUtils.isEmpty(m.extra) && FileUtils.checkFile(m.path)) {
                                        BitmapCacheKey cacheKey = BitmapCacheKey.create(m.extra);
                                        if (cacheKey != null) {
                                            if (tempCacheKey == null) {
                                                tempCacheKey = cacheKey;
                                                cacheKey.putExtra(SuperId.BIT_1_NEARBY_SEARCH, m);
                                            } else if (ImageCacheManager.b(cacheKey, tempCacheKey)) {
                                                tempCacheKey = cacheKey;
                                                cacheKey.putExtra(SuperId.BIT_1_NEARBY_SEARCH, m);
                                            }
                                        }
                                    }
                                }
                                if (tempCacheKey != null) {
                                    return (FileCacheModel) tempCacheKey.getExtra(SuperId.BIT_1_NEARBY_SEARCH);
                                }
                                return null;
                            }
                        });
                        if (cacheModel != null) {
                            BitmapCacheKey key = BitmapCacheKey.create(cacheModel.extra);
                            queryResult.success = true;
                            queryResult.path = cacheModel.path;
                            if (key != null) {
                                queryResult.width = key.width;
                                queryResult.height = key.height;
                            }
                        }
                        ImageCachePerf.getCurrent().addDbHitTime(result.success, System.currentTimeMillis() - start);
                    } catch (Exception e) {
                        a.e(e, "queryClearCacheImage", new Object[0]);
                    }
                }
                if (queryResult.success) {
                    result.success = true;
                    result.path = queryResult.path;
                    result.width = queryResult.width;
                    result.height = queryResult.height;
                }
            }
            a.d("queryClearCacheImage result: " + result, new Object[0]);
        }
        return result;
    }

    public APImageQueryResult<APImageSourceCutQuery> queryImageFor(final APImageSourceCutQuery query) {
        APImageQueryResult result = new APImageQueryResult();
        result.success = false;
        result.query = query;
        if (QueryCacheManager.getInstance().getConf().getImageSwitch()) {
            a.d("queryImageFor off SourceCutQuery, result: " + result, new Object[0]);
        } else {
            if (query != null && !TextUtils.isEmpty(query.path)) {
                APImageBigQuery bigQuery = new APImageBigQuery(query.path);
                bigQuery.businessId = query.businessId;
                APImageQueryResult queryResult = queryImageFor(bigQuery);
                if (!queryResult.success && CacheUtils.qualityCachekeyCheck(query.getQuality())) {
                    bigQuery.setQuality(query.getQuality());
                    queryResult = queryImageFor(bigQuery);
                }
                if (!queryResult.success) {
                    APImageOriginalQuery originalQuery = new APImageOriginalQuery(query.path);
                    originalQuery.businessId = query.businessId;
                    queryResult = queryImageFor(originalQuery);
                }
                long start = System.currentTimeMillis();
                if (!(queryResult.success || query.minWidth == null || query.minHeight == null || query.cutScaleType == null)) {
                    FileCacheModel cacheModel = ImageDiskCache.get().get(query.path, new QueryFilter() {
                        public FileCacheModel parse(List<FileCacheModel> list) {
                            int minHeight;
                            BitmapCacheKey tempCacheKey = null;
                            int minWidth = query.minWidth.intValue() == 0 ? 1280 : query.minWidth.intValue();
                            if (query.minHeight.intValue() == 0) {
                                minHeight = 1280;
                            } else {
                                minHeight = query.minHeight.intValue();
                            }
                            for (FileCacheModel m : list) {
                                if (!TextUtils.isEmpty(m.extra) && FileUtils.checkFile(m.path)) {
                                    BitmapCacheKey cacheKey = BitmapCacheKey.create(m.extra);
                                    if (cacheKey != null && TextUtils.isEmpty(cacheKey.pluginKey)) {
                                        if (cacheKey.quality == -1 || (query.getQuality() != -1 && cacheKey.quality >= query.getQuality())) {
                                            if (CompareUtils.in(Integer.valueOf(cacheKey.scaleType), Integer.valueOf(CutScaleType.KEEP_RATIO.getValue()), Integer.valueOf(CutScaleType.NONE.getValue())) && cacheKey.width >= minWidth && cacheKey.height >= minHeight) {
                                                if (tempCacheKey == null) {
                                                    tempCacheKey = cacheKey;
                                                    cacheKey.putExtra(SuperId.BIT_1_NEARBY_SEARCH, m);
                                                } else if (ImageCacheManager.b(cacheKey, tempCacheKey)) {
                                                    tempCacheKey = cacheKey;
                                                    cacheKey.putExtra(SuperId.BIT_1_NEARBY_SEARCH, m);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (tempCacheKey != null) {
                                return (FileCacheModel) tempCacheKey.getExtra(SuperId.BIT_1_NEARBY_SEARCH);
                            }
                            return null;
                        }
                    });
                    if (cacheModel != null) {
                        BitmapCacheKey key = BitmapCacheKey.create(cacheModel.extra);
                        queryResult.success = true;
                        queryResult.path = cacheModel.path;
                        if (key != null) {
                            queryResult.width = key.width;
                            queryResult.height = key.height;
                        }
                    }
                    ImageCachePerf.getCurrent().addDbHitTime(queryResult.success, System.currentTimeMillis() - start);
                }
                if (queryResult.success) {
                    result.success = true;
                    result.path = queryResult.path;
                    result.width = queryResult.width;
                    result.height = queryResult.height;
                }
            }
            a.d("queryImageFor SourceCutQuery, result: " + result, new Object[0]);
        }
        return result;
    }

    public APImageQueryResult<APImageThumbnailQuery> queryImageFor(final APImageThumbnailQuery query) {
        ImageQueryResult result = new ImageQueryResult();
        result.success = false;
        result.query = query;
        if (QueryCacheManager.getInstance().getConf().getImageSwitch()) {
            a.d("queryImageFor off APImageThumbnailQuery, result: " + result, new Object[0]);
        } else {
            if (query != null && !TextUtils.isEmpty(query.path)) {
                String path = PathUtils.extractPath(query.path);
                long start = System.currentTimeMillis();
                try {
                    FileCacheModel resultModel = ImageDiskCache.get().get(path, new QueryFilter() {
                        public FileCacheModel parse(List<FileCacheModel> list) {
                            List<BitmapCacheKey> cacheKeys = new ArrayList<>(list.size());
                            for (FileCacheModel m : list) {
                                if (!TextUtils.isEmpty(m.extra) && FileUtils.checkFile(m.path)) {
                                    BitmapCacheKey cacheKey = BitmapCacheKey.create(m.extra);
                                    if (cacheKey != null) {
                                        cacheKey.putExtra(SuperId.BIT_1_NEARBY_SEARCH, m);
                                        cacheKeys.add(cacheKey);
                                    }
                                }
                            }
                            Collections.sort(cacheKeys, new Comparator<BitmapCacheKey>() {
                                public int compare(BitmapCacheKey lhs, BitmapCacheKey rhs) {
                                    return lhs.pixels - rhs.pixels;
                                }
                            });
                            int minWidth = query.minWidth == null ? 0 : query.minWidth.intValue();
                            int minHeight = query.minHeight == null ? 0 : query.minHeight.intValue();
                            for (BitmapCacheKey cacheKey2 : cacheKeys) {
                                if (cacheKey2.width == 0 || cacheKey2.height == 0) {
                                    cacheKey2.width = 1280;
                                    cacheKey2.height = 1280;
                                }
                                if (cacheKey2.width < minWidth || cacheKey2.height < minHeight) {
                                    ImageCacheManager.a.d("queryImageFor APImageThumbnailQuery skip, cache.width: " + cacheKey2.width + ", minWidth: " + minWidth + ", cache.height: " + cacheKey2.height + ", minHeight: " + minHeight, new Object[0]);
                                } else {
                                    FileCacheModel cacheModel = (FileCacheModel) cacheKey2.getExtra(SuperId.BIT_1_NEARBY_SEARCH);
                                    if (cacheModel != null) {
                                        return cacheModel;
                                    }
                                }
                            }
                            return null;
                        }
                    });
                    if (resultModel != null) {
                        BitmapCacheKey key = BitmapCacheKey.create(resultModel.extra, PathUtils.extractPath(query.path));
                        result.success = true;
                        result.path = resultModel.path;
                        result.cacheKey = key;
                        if (key != null) {
                            result.width = key.width;
                            result.height = key.height;
                        }
                    }
                    ImageCachePerf.getCurrent().addDbHitTime(result.success, System.currentTimeMillis() - start);
                } catch (Exception e) {
                    a.e(e, "APImageThumbnailQuery query: %s", query);
                }
            }
            a.d("APImageThumbnailQuery query:%s, result: %s", query, result);
        }
        return result;
    }

    public int deleteCache(String path) {
        int deleteCount = 0;
        if (!TextUtils.isEmpty(path) && ImageDiskCache.get().remove(path)) {
            deleteCount = 0 + 1;
        }
        a.d("deleteCache for: " + path + ", deleted: " + deleteCount, new Object[0]);
        return deleteCount;
    }

    public boolean saveImageCache(Bitmap bitmap, String path, CacheImageOptions options, String businessId) {
        if (!ImageUtils.checkBitmap(bitmap)) {
            throw new IllegalArgumentException("bitmap is not ok! bitmap: " + bitmap);
        } else if (TextUtils.isEmpty(path)) {
            throw new IllegalArgumentException("path is not ok! path: " + path);
        } else if (options == null) {
            throw new IllegalArgumentException("options is null, error!!");
        } else if (TextUtils.isEmpty(businessId)) {
            throw new IllegalArgumentException("businessId is not ok! businessId: " + businessId);
        } else {
            boolean ret = false;
            String path2 = PathUtils.extractPath(path);
            options.businessId = businessId;
            BitmapCacheKey cacheKey = a(path2, options);
            try {
                if (options.isCacheInMem()) {
                    MemoryCache cache = CacheContext.get().getMemCache(businessId);
                    if (cache == null) {
                        cache = CacheContext.get().getMemCache();
                    }
                    ret = cache.put(cacheKey.complexCacheKey(), bitmap);
                }
                if (options.isCacheInDisk()) {
                    ret = ImageDiskCache.get().saveBitmap(cacheKey, bitmap, false, businessId, options.getExpiredTime());
                }
                if (!TextUtils.isEmpty(options.aliasPath)) {
                    ImageDiskCache.get().update(path2, options.aliasPath);
                }
            } catch (Exception e) {
                a.e(e, "saveImageCache error", new Object[0]);
            }
            return ret;
        }
    }

    private static BitmapCacheKey a(String path, CacheImageOptions options) {
        if (options.cutScaleType == CutScaleType.KEEP_RATIO && options.srcSize != null) {
            options.cutScaleType = a(options);
        }
        BitmapCacheKey cacheKey = new BitmapCacheKey(path, options.width.intValue(), options.height.intValue(), options.cutScaleType, options.plugin, options.getQuality(), options.imageMarkRequest);
        a.d("saveImageCache-makeCacheKey cacheKey: " + cacheKey, new Object[0]);
        return cacheKey;
    }

    private static CutScaleType a(CacheImageOptions options) {
        CutScaleType type = ImageUtils.calcCutScaleType(options.srcSize, options.scale.floatValue(), Math.max(options.width.intValue(), options.height.intValue()));
        if (!CutScaleType.CENTER_CROP.equals(type)) {
            return CutScaleType.KEEP_RATIO;
        }
        if (CutScaleType.AUTO_CUT_EXACTLY.equals(options.cutScaleType)) {
            return CutScaleType.AUTO_CUT_EXACTLY;
        }
        return type;
    }
}
