package com.alipay.mobile.security.bio.workspace;

import android.content.Context;

public class JsonBioParameterToBioApp extends a {
    public JsonBioParameterToBioApp(Context context, BioTransfer bioTransfer) {
        super(context, bioTransfer);
    }

    /* JADX WARNING: Removed duplicated region for block: B:121:0x0316  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x00dd A[SYNTHETIC, Splitter:B:19:0x00dd] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0108  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.alipay.mobile.security.bio.service.BioAppDescription toBioApp(com.alipay.mobile.security.bio.api.BioParameter r16) {
        /*
            r15 = this;
            r13 = 303(0x12f, float:4.25E-43)
            r12 = 302(0x12e, float:4.23E-43)
            r11 = 991(0x3df, float:1.389E-42)
            r4 = 1
            if (r16 != 0) goto L_0x000f
            com.alipay.mobile.security.bio.exception.BioIllegalArgumentException r1 = new com.alipay.mobile.security.bio.exception.BioIllegalArgumentException
            r1.<init>()
            throw r1
        L_0x000f:
            java.lang.String r2 = r16.getProtocol()
            java.lang.String r3 = ""
            java.lang.Class<com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfig> r1 = com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfig.class
            java.lang.Object r1 = com.alibaba.fastjson.JSON.parseObject(r2, r1)     // Catch:{ Exception -> 0x0151 }
            com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfig r1 = (com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfig) r1     // Catch:{ Exception -> 0x0151 }
            if (r1 == 0) goto L_0x0319
            java.lang.String r1 = r1.getContent()     // Catch:{ Exception -> 0x0151 }
            java.lang.Class<com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfigContent> r5 = com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfigContent.class
            java.lang.Object r1 = com.alibaba.fastjson.JSON.parseObject(r1, r5)     // Catch:{ Exception -> 0x0151 }
            com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfigContent r1 = (com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfigContent) r1     // Catch:{ Exception -> 0x0151 }
            if (r1 == 0) goto L_0x0319
            int r5 = r1.getType()     // Catch:{ Exception -> 0x0151 }
            r6 = 200(0xc8, float:2.8E-43)
            if (r5 != r6) goto L_0x0319
            java.lang.String r5 = r1.getAndroidcfg()     // Catch:{ Exception -> 0x0151 }
            com.alibaba.fastjson.JSONObject r5 = com.alibaba.fastjson.JSONObject.parseObject(r5)     // Catch:{ Exception -> 0x0151 }
            if (r5 == 0) goto L_0x0319
            com.alipay.mobile.security.bio.workspace.BioTransfer r6 = r15.a     // Catch:{ Exception -> 0x0151 }
            int r6 = r6.fcStep     // Catch:{ Exception -> 0x0151 }
            if (r6 != 0) goto L_0x011d
            java.util.HashMap r3 = new java.util.HashMap     // Catch:{ Exception -> 0x0151 }
            r3.<init>()     // Catch:{ Exception -> 0x0151 }
            com.alipay.mobile.security.bio.workspace.BioTransfer r6 = r15.a     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = r1.getToken()     // Catch:{ Exception -> 0x0151 }
            r6.fcToken = r1     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = "fcToken"
            com.alipay.mobile.security.bio.workspace.BioTransfer r6 = r15.a     // Catch:{ Exception -> 0x0151 }
            java.lang.String r6 = r6.fcToken     // Catch:{ Exception -> 0x0151 }
            r3.put(r1, r6)     // Catch:{ Exception -> 0x0151 }
            com.alipay.mobile.security.bio.workspace.BioTransfer r1 = r15.a     // Catch:{ Exception -> 0x0151 }
            com.alipay.mobile.security.bio.common.statistics.RecordExtService r1 = r1.mRecordExtService     // Catch:{ Exception -> 0x0151 }
            com.alipay.mobile.security.bio.common.statistics.RecordExtAction r6 = com.alipay.mobile.security.bio.common.statistics.RecordExtAction.RECORD_FC_ENTRY_SDK     // Catch:{ Exception -> 0x0151 }
            r1.write(r6, r3)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = "params"
            r3.put(r1, r2)     // Catch:{ Exception -> 0x0151 }
            com.alipay.mobile.security.bio.workspace.BioTransfer r1 = r15.a     // Catch:{ Exception -> 0x0151 }
            com.alipay.mobile.security.bio.common.statistics.RecordExtService r1 = r1.mRecordExtService     // Catch:{ Exception -> 0x0151 }
            com.alipay.mobile.security.bio.common.statistics.RecordExtAction r6 = com.alipay.mobile.security.bio.common.statistics.RecordExtAction.RECORD_FC_GET_PARAM     // Catch:{ Exception -> 0x0151 }
            r1.write(r6, r3)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = "paperGuideUrl"
            java.lang.String r1 = r5.getString(r1)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r3 = "showPaperGuide"
            java.lang.String r3 = r5.getString(r3)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r6 = "certType"
            java.lang.String r6 = r5.getString(r6)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r7 = "paperStartPage"
            int r7 = r5.getIntValue(r7)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r8 = "paperTotalPage"
            int r8 = r5.getIntValue(r8)     // Catch:{ Exception -> 0x0151 }
            com.alibaba.fastjson.JSONObject r9 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0151 }
            r9.<init>()     // Catch:{ Exception -> 0x0151 }
            java.lang.String r10 = "showPaperGuide"
            r9.put(r10, r3)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r3 = "paperGuideUrl"
            r9.put(r3, r1)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = "certType"
            r9.put(r1, r6)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = "paperStartPage"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x0151 }
            r9.put(r1, r3)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = "paperTotalPage"
            java.lang.Integer r3 = java.lang.Integer.valueOf(r8)     // Catch:{ Exception -> 0x0151 }
            r9.put(r1, r3)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = "fcToken"
            com.alipay.mobile.security.bio.workspace.BioTransfer r3 = r15.a     // Catch:{ Exception -> 0x0151 }
            java.lang.String r3 = r3.fcToken     // Catch:{ Exception -> 0x0151 }
            r9.put(r1, r3)     // Catch:{ Exception -> 0x0151 }
            com.alipay.mobile.security.bio.workspace.BioTransfer r1 = r15.a     // Catch:{ Exception -> 0x0151 }
            r1.mFcSpecialData = r9     // Catch:{ Exception -> 0x0151 }
            com.alibaba.fastjson.JSONObject r1 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0151 }
            r1.<init>()     // Catch:{ Exception -> 0x0151 }
            java.lang.String r3 = "content"
            java.lang.String r6 = "papersCfg"
            java.lang.String r5 = r5.getString(r6)     // Catch:{ Exception -> 0x0151 }
            r1.put(r3, r5)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0151 }
        L_0x00d7:
            boolean r3 = com.alipay.mobile.security.bio.utils.StringUtil.isNullorEmpty(r1)     // Catch:{ Exception -> 0x0151 }
            if (r3 != 0) goto L_0x0316
            com.alipay.mobile.security.bio.workspace.BioTransfer r2 = r15.a     // Catch:{ Exception -> 0x0313 }
            r0 = r16
            r2.mIDFaceParam = r0     // Catch:{ Exception -> 0x0313 }
            com.alipay.mobile.security.bio.workspace.BioTransfer r2 = r15.a     // Catch:{ Exception -> 0x0313 }
            r3 = 1
            r2.isIDFaceFlag = r3     // Catch:{ Exception -> 0x0313 }
        L_0x00e8:
            com.alipay.mobile.security.bio.service.BioAppDescription r6 = new com.alipay.mobile.security.bio.service.BioAppDescription
            r6.<init>()
            java.lang.String r2 = getUniqueTag()
            r6.setTag(r2)
            java.lang.String r2 = r16.getRemoteURL()
            r6.setRemoteURL(r2)
            java.lang.String r2 = r16.getHeadImageUrl()
            r6.setHeadImageURL(r2)
            boolean r2 = com.alipay.mobile.security.bio.utils.StringUtil.isNullorEmpty(r1)
            if (r2 != 0) goto L_0x011c
            java.lang.String r7 = "bid-log-key-public.key"
            java.lang.String r8 = "bid-log-key-public_t.key"
            java.lang.Class<com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfig> r2 = com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfig.class
            java.lang.Object r1 = com.alibaba.fastjson.JSON.parseObject(r1, r2)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfig r1 = (com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfig) r1     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r1 == 0) goto L_0x011c
            java.lang.String r2 = r1.getContent()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r2 != 0) goto L_0x015d
        L_0x011c:
            return r6
        L_0x011d:
            com.alipay.mobile.security.bio.workspace.BioTransfer r1 = r15.a     // Catch:{ Exception -> 0x0151 }
            int r1 = r1.fcStep     // Catch:{ Exception -> 0x0151 }
            if (r1 != r4) goto L_0x0319
            com.alibaba.fastjson.JSONObject r1 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0151 }
            r1.<init>()     // Catch:{ Exception -> 0x0151 }
            java.lang.String r3 = "f"
            java.lang.String r6 = "fc"
            r1.put(r3, r6)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r3 = "fcToken"
            com.alipay.mobile.security.bio.workspace.BioTransfer r6 = r15.a     // Catch:{ Exception -> 0x0151 }
            java.lang.String r6 = r6.fcToken     // Catch:{ Exception -> 0x0151 }
            r1.put(r3, r6)     // Catch:{ Exception -> 0x0151 }
            com.alipay.mobile.security.bio.workspace.BioTransfer r3 = r15.a     // Catch:{ Exception -> 0x0151 }
            r3.mFcSpecialData = r1     // Catch:{ Exception -> 0x0151 }
            com.alibaba.fastjson.JSONObject r1 = new com.alibaba.fastjson.JSONObject     // Catch:{ Exception -> 0x0151 }
            r1.<init>()     // Catch:{ Exception -> 0x0151 }
            java.lang.String r3 = "content"
            java.lang.String r6 = "faceCfg"
            java.lang.String r5 = r5.getString(r6)     // Catch:{ Exception -> 0x0151 }
            r1.put(r3, r5)     // Catch:{ Exception -> 0x0151 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0151 }
            goto L_0x00d7
        L_0x0151:
            r1 = move-exception
            r14 = r1
            r1 = r2
            r2 = r14
        L_0x0155:
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.security.bio.utils.BioLog.e(r2)
            goto L_0x00e8
        L_0x015d:
            com.alipay.mobile.security.bio.workspace.BioTransfer r2 = r15.a     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            boolean r2 = r2.isIDFaceFlag     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r2 != 0) goto L_0x0169
            java.lang.String r2 = r1.getSign()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r2 == 0) goto L_0x011c
        L_0x0169:
            java.lang.String r2 = r1.getContent()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.lang.Class<com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfigContent> r3 = com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfigContent.class
            java.lang.Object r2 = com.alibaba.fastjson.JSON.parseObject(r2, r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfigContent r2 = (com.alipay.bis.common.service.facade.gw.model.common.BisJson.BisClientConfigContent) r2     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.lang.String r9 = r2.getAndroidcfg()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            boolean r3 = com.alipay.mobile.security.bio.utils.StringUtil.isNullorEmpty(r9)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 == 0) goto L_0x0252
            com.alipay.mobile.security.bio.workspace.ProtocolConfig r3 = new com.alipay.mobile.security.bio.workspace.ProtocolConfig     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r3.<init>()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r5 = r3
        L_0x0185:
            com.alipay.mobile.security.bio.workspace.BioTransfer r3 = r15.a     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            boolean r3 = r3.isIDFaceFlag     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != 0) goto L_0x0277
            if (r5 == 0) goto L_0x0269
            int r3 = r5.getEnv()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != 0) goto L_0x0269
            com.alipay.mobile.security.bio.workspace.BioTransfer r3 = r15.a     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            android.content.Context r3 = r3.mContext     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            android.content.res.AssetManager r3 = r3.getAssets()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.io.InputStream r3 = r3.open(r7)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
        L_0x019f:
            java.lang.String r7 = r1.getSign()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r8 = 8
            byte[] r7 = android.util.Base64.decode(r7, r8)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.lang.String r1 = r1.getContent()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            byte[] r1 = r1.getBytes()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            boolean r1 = com.alipay.mobile.security.bio.utils.RSASign.doCheck(r1, r7, r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
        L_0x01b5:
            int r3 = r2.getType()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r6.setBioType(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.lang.String r3 = " "
            java.lang.String r7 = ""
            java.lang.String r3 = r9.replaceAll(r3, r7)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.lang.String r7 = "\"ui\":992"
            boolean r3 = r3.contains(r7)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != 0) goto L_0x01fe
            int r3 = r2.getSampleMode()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r6.setBioAction(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            int r3 = r2.getType()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r7 = 100
            if (r3 != r7) goto L_0x02e5
            if (r5 == 0) goto L_0x02e5
            int r3 = r5.getUi()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != 0) goto L_0x0291
            com.alipay.mobile.security.bio.workspace.NavPageConfig r3 = r5.getNavigatepage()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 == 0) goto L_0x01fe
            com.alipay.mobile.security.bio.workspace.NavPageConfig r3 = r5.getNavigatepage()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            boolean r3 = r3.isEnable()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 == 0) goto L_0x01fe
            int r3 = r2.getSampleMode()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != r12) goto L_0x027a
            r3 = -302(0xfffffffffffffed2, float:NaN)
            r6.setBioAction(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
        L_0x01fe:
            r6.setCfg(r9)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.lang.String r2 = r2.getToken()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r6.setBistoken(r2)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            com.alipay.mobile.security.bio.workspace.BioTransfer r2 = r15.a     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            com.alibaba.fastjson.JSONObject r2 = r2.mFcSpecialData     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r6.setFcSpecialData(r2)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r6.setSigned(r1)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            boolean r1 = r16.isAutoClose()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r6.setAutoClose(r1)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            android.os.Bundle r1 = r16.getBundle()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r6.setBundle(r1)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.util.Map r1 = r16.getExtProperty()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.util.Set r1 = r1.keySet()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.util.Iterator r3 = r1.iterator()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
        L_0x022c:
            boolean r1 = r3.hasNext()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r1 == 0) goto L_0x011c
            java.lang.Object r1 = r3.next()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r0 = r1
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r2 = r0
            java.util.Map r1 = r16.getExtProperty()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r6.addExtProperty(r2, r1)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            goto L_0x022c
        L_0x0248:
            r1 = move-exception
            java.lang.String r1 = r1.toString()
            com.alipay.mobile.security.bio.utils.BioLog.e(r1)
            goto L_0x011c
        L_0x0252:
            java.lang.Class<com.alipay.mobile.security.bio.workspace.ProtocolConfig> r3 = com.alipay.mobile.security.bio.workspace.ProtocolConfig.class
            java.lang.Object r3 = com.alibaba.fastjson.JSON.parseObject(r9, r3)     // Catch:{ Exception -> 0x025d }
            com.alipay.mobile.security.bio.workspace.ProtocolConfig r3 = (com.alipay.mobile.security.bio.workspace.ProtocolConfig) r3     // Catch:{ Exception -> 0x025d }
            r5 = r3
            goto L_0x0185
        L_0x025d:
            r3 = move-exception
            com.alipay.mobile.security.bio.utils.BioLog.w(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            com.alipay.mobile.security.bio.workspace.ProtocolConfig r3 = new com.alipay.mobile.security.bio.workspace.ProtocolConfig     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r3.<init>()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r5 = r3
            goto L_0x0185
        L_0x0269:
            com.alipay.mobile.security.bio.workspace.BioTransfer r3 = r15.a     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            android.content.Context r3 = r3.mContext     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            android.content.res.AssetManager r3 = r3.getAssets()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            java.io.InputStream r3 = r3.open(r8)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            goto L_0x019f
        L_0x0277:
            r1 = r4
            goto L_0x01b5
        L_0x027a:
            int r3 = r2.getSampleMode()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != r13) goto L_0x01fe
            r3 = -303(0xfffffffffffffed1, float:NaN)
            r6.setBioAction(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            goto L_0x01fe
        L_0x0287:
            r1 = move-exception
            java.lang.String r1 = r1.toString()
            com.alipay.mobile.security.bio.utils.BioLog.e(r1)
            goto L_0x011c
        L_0x0291:
            int r3 = r5.getUi()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != r11) goto L_0x02c5
            int r3 = r2.getSampleMode()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != r12) goto L_0x02ae
            r3 = 991(0x3df, float:1.389E-42)
            r6.setBioAction(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            goto L_0x01fe
        L_0x02a4:
            r1 = move-exception
            java.lang.String r1 = r1.toString()
            com.alipay.mobile.security.bio.utils.BioLog.e(r1)
            goto L_0x011c
        L_0x02ae:
            int r3 = r2.getSampleMode()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != r13) goto L_0x01fe
            r3 = 992(0x3e0, float:1.39E-42)
            r6.setBioAction(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            goto L_0x01fe
        L_0x02bb:
            r1 = move-exception
            java.lang.String r1 = r1.toString()
            com.alipay.mobile.security.bio.utils.BioLog.e(r1)
            goto L_0x011c
        L_0x02c5:
            int r3 = r5.getUi()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != r4) goto L_0x01fe
            int r3 = r2.getSampleMode()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != r12) goto L_0x02d8
            r3 = 993(0x3e1, float:1.391E-42)
            r6.setBioAction(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            goto L_0x01fe
        L_0x02d8:
            int r3 = r2.getSampleMode()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != r13) goto L_0x01fe
            r3 = 994(0x3e2, float:1.393E-42)
            r6.setBioAction(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            goto L_0x01fe
        L_0x02e5:
            int r3 = r2.getType()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r4 = 201(0xc9, float:2.82E-43)
            if (r3 != r4) goto L_0x01fe
            if (r5 == 0) goto L_0x01fe
            int r3 = r5.getUi()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            if (r3 != r11) goto L_0x01fe
            int r3 = r2.getSampleMode()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r4 = 300(0x12c, float:4.2E-43)
            if (r3 != r4) goto L_0x0304
            r3 = 991(0x3df, float:1.389E-42)
            r6.setBioAction(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            goto L_0x01fe
        L_0x0304:
            int r3 = r2.getSampleMode()     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            r4 = 301(0x12d, float:4.22E-43)
            if (r3 != r4) goto L_0x01fe
            r3 = 992(0x3e0, float:1.39E-42)
            r6.setBioAction(r3)     // Catch:{ IllegalStateException -> 0x0248, JSONException -> 0x0287, IllegalArgumentException -> 0x02a4, IOException -> 0x02bb }
            goto L_0x01fe
        L_0x0313:
            r2 = move-exception
            goto L_0x0155
        L_0x0316:
            r1 = r2
            goto L_0x00e8
        L_0x0319:
            r1 = r3
            goto L_0x00d7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.security.bio.workspace.JsonBioParameterToBioApp.toBioApp(com.alipay.mobile.security.bio.api.BioParameter):com.alipay.mobile.security.bio.service.BioAppDescription");
    }
}
