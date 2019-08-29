package com.uc.webview.export.internal.interfaces;

import android.util.Pair;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.uc.webview.export.annotations.Api;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.uc.wa.a;
import com.uc.webview.export.internal.uc.wa.c;
import com.uc.webview.export.internal.uc.wa.d;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.b;
import com.uc.webview.export.utility.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Api
/* compiled from: ProGuard */
public interface IWaStat {
    public static final String ACTIVATE_PUSH_PROCESS = "sdk_act_pp";
    public static final String ACTIVATE_PUSH_PROCESS_DEFAUT_CONFIG = "sdk_act_pp_default_cfg";
    public static final String ACTIVATE_PUSH_PROCESS_UCM_CD = "sdk_act_pp_ucm_cd";
    public static final String BUSINESS_CHECK_INPUT_CONDITIONS = "bs_c_inc";
    public static final String BUSINESS_CHECK_NEW_CORE = "bs_c_new_c";
    public static final String BUSINESS_CHECK_OLD_CORE = "bs_c_old_c";
    public static final String BUSINESS_DECOMPRESS_AND_ODEX = "bs_dec_od";
    public static final String BUSINESS_ELAPSE_INIT_CHECK = "bs_e_ic";
    public static final String BUSINESS_ELAPSE_INIT_CHECK_CPU = "bs_e_ic_c";
    public static final String BUSINESS_ELAPSE_INIT_TYPE = "bs_e_it";
    public static final String BUSINESS_ELAPSE_KEY = "bs_ek";
    public static final String BUSINESS_ELAPSE_SETUP_CALLBACK = "bs_e_sec";
    public static final String BUSINESS_ELAPSE_SETUP_CALLBACK_CPU = "bs_e_sec_c";
    public static final String BUSINESS_ELAPSE_SUCCESS_CALLBACK = "bs_e_suc";
    public static final String BUSINESS_ELAPSE_SUCCESS_CALLBACK_CPU = "bs_e_suc_c";
    public static final String BUSINESS_INIT_PROCESS = "bs_i_pro";
    public static final String BUSINESS_INIT_START = "bs_i_st";
    public static final String CD_CP_TYPE_IP = "cd_cp_ip";
    public static final String CD_CP_TYPE_LOCATION = "cd_cp_lo";
    public static final String CD_DISPATCH_REQUEST_TASK = "dis_req_task";
    public static final String CD_INIT_TASK_COUNT = "cd_init_task";
    public static final String CD_INIT_TASK_SUCCESS = "cd_init_suc";
    public static final String CD_JOSN_CMD_PARSR_COUNT = "cd_pp_co";
    public static final String CD_JOSN_CMD_PARSR_FAILUE = "cd_pp_fa";
    public static final String CD_JOSN_CMD_PARSR_SUCCESS = "cd_pp_su";
    public static final String CD_SWITCH_STAT = "CD_SWITCH_STAT";
    public static final String CD_UTDID_FAILURE = "cd_utdid_fai";
    public static final String CD_UTDID_SUCCESS = "cd_utdid_suc";
    public static final String CORE_DOWNLOAD = "sdk_cdl";
    public static final String CORE_ERROR_CODE_DOWNLOAD = "sdk_ecdl0";
    public static final String CORE_ERROR_CODE_UNZIP = "sdk_ecuz0";
    public static final String CORE_ERROR_CODE_UPDATE_CHECK_REQUEST = "sdk_ecur0";
    public static final String CORE_ERROR_CODE_VERIFY = "sdk_ecv0";
    public static final String DEC = "sdk_dec";
    public static final String DEC_EXCEPTION = "sdk_dec_e";
    public static final String DEC_SUCCESS = "sdk_dec_s";
    public static final String DEC_ZIP = "sdk_decz";
    public static final String DEC_ZIP_SUCCESS = "sdk_decz_s";
    public static final String DOWNLOAD = "sdk_dl";
    public static final String DOWNLOAD_EXCEPTION = "sdk_dl_e";
    public static final String DOWNLOAD_EXISTS = "sdk_dl_x";
    public static final String DOWNLOAD_FAILED = "sdk_dl_f";
    public static final String DOWNLOAD_RECOVERED = "sdk_dl_r";
    public static final String DOWNLOAD_SUCCESS = "sdk_dl_s";
    public static final String ERROR_CODE_INIT = "sdk_eci";
    public static final String KEY_ART = "art";
    public static final String KEY_CHECK_BUILD_TYPE = "cbt";
    public static final String KEY_CHECK_COMPATIBLE = "ccp";
    public static final String KEY_CHECK_COMPRESS = "cc";
    public static final String KEY_CHECK_PAK_AND_HASH = "cpah";
    public static final String KEY_CHECK_PARAM = "cp";
    public static final String KEY_CHECK_SIGNATURE1 = "csg1";
    public static final String KEY_CHECK_SIGNATURE2 = "csg2";
    public static final String KEY_CHECK_SIGNATURE3 = "csg3";
    public static final String KEY_CHECK_SIZE_AND_HASH = "csah";
    public static final String KEY_CHECK_SKIP_OLD_KERNEL = "csok";
    public static final String KEY_CHECK_VER_EXCLUD = "cve";
    public static final String KEY_CHECK_VER_INCLUD = "cvi";
    public static final String KEY_CLASS = "cls";
    public static final String KEY_CNT = "cnt";
    public static final String KEY_CODE = "code";
    public static final String KEY_CORE_STARTUP = "sdk_cst";
    public static final String KEY_COST = "cost";
    public static final String KEY_COST_CPU = "cost_cpu";
    public static final String KEY_CPU_CNT = "cpu_cnt";
    public static final String KEY_CPU_FREQ = "cpu_freq";
    public static final String KEY_CRASH = "crash";
    public static final String KEY_DATA = "data";
    public static final String KEY_DIR = "dir";
    public static final String KEY_DVM = "dvm";
    public static final String KEY_DVM2 = "dvm2";
    public static final String KEY_ENABLE = "enable";
    public static final String KEY_ERRNO = "err";
    public static final String KEY_FIRST_RUN = "frun";
    public static final String KEY_FREE_DISK_SPACE = "free_disk_space";
    public static final String KEY_FREE_DISK_SPACE_BEFORE = "free_disk_space_before";
    public static final String KEY_HOOK_RUN_AS_EXPECTED = "run_expected";
    public static final String KEY_HOOK_SUCCESS = "hook_succ";
    public static final String KEY_ID = "id";
    public static final String KEY_LINK_SO_CODE = "link_so_code";
    public static final String KEY_LOAD_CORE_JAR = "lcj";
    public static final String KEY_LOAD_SHELL_JAR = "lsj";
    public static final String KEY_MESSAGE = "msg";
    public static final String KEY_MULTI_CORE = "multi_core";
    public static final String KEY_OLD = "old";
    public static final String KEY_PRIORITY = "pri";
    public static final String KEY_SDK_INT = "sdk_int";
    public static final String KEY_SUCCESS = "succ";
    public static final String KEY_SYSTEM_NEW_WEBVIEW_PV = "swv_npv";
    public static final String KEY_SYSTEM_SETUP_PV = "swv_spv";
    public static final String KEY_TASK = "task";
    public static final String KEY_VERIFY_RESULT = "result";
    public static final String KEY_WIFI = "wifi";
    public static final int MERGE_ADD = 0;
    public static final int MERGE_REPLACE = 1;
    public static final String MIDDLEWARE_CD_DISPATCH_REQUEST = "mid_cd_dis_req";
    public static final String MIDDLEWARE_CD_EXCEPTION = "mid_cd_parse_ex";
    public static final String MIDDLEWARE_CD_FAILURE = "mid_cd_parse_fai";
    public static final String MIDDLEWARE_CD_FILE_EXCEPTION = "mid_file_cd_parse_ex";
    public static final String MIDDLEWARE_CD_FILE_FAILURE = "mid_file_cd_parse_fai";
    public static final String MIDDLEWARE_CD_FILE_NOT_EXIST = "mid_file_cd_exi";
    public static final String MIDDLEWARE_CD_FILE_PARSE = "mid_file_cd_parse";
    public static final String MIDDLEWARE_CD_FILE_SUCCESS = "mid_file_cd_parse_suc";
    public static final String MIDDLEWARE_CD_JS_UPDATE = "mid_cd_js_update";
    public static final String MIDDLEWARE_CD_PARSE = "mid_cd_parse";
    public static final String MIDDLEWARE_CD_PARSE_PARAM_ILLEGAL = "mid_cd_parse_ill";
    public static final String MIDDLEWARE_CD_RESPOND_DO_BACKGROUND = "mid_cd_req_do_bac";
    public static final String MIDDLEWARE_CD_RESPOND_FAILURE = "mid_cd_req_fai";
    public static final String MIDDLEWARE_CD_RESPOND_FINISH = "mid_cd_req_fin";
    public static final String MIDDLEWARE_CD_RESPOND_RECEIVE = "mid_cd_req_rec";
    public static final String MIDDLEWARE_CD_RESPOND_STATUS_CODE = "mid_cd_req_st_co";
    public static final String MIDDLEWARE_CD_RESPOND_SUCCESS = "mid_cd_req_suc";
    public static final String MIDDLEWARE_CD_SUCCESS = "mid_cd_parse_suc";
    public static final String SETPARAM_CD_ISSUED_FAILURE = "setparam_cd_parse_failure";
    public static final String SETPARAM_CD_ISSUED_SUCCESS = "setparam_cd_parse_success";
    public static final String SETPARAM_CD_PARSE = "setparam_cd_parse";
    public static final String SETUP_CUR_UCCORE_COST_HOUR = "stp_curuc_hour";
    public static final String SETUP_DEFAULT_EXCEPTION = "sdk_stp_def_exc";
    public static final String SETUP_DELETE_CORE_COUNT = "sdk_stp_dcc";
    public static final String SETUP_EXTRA_EXCEPTION = "sdk_stp_ext_exc";
    public static final String SETUP_REPAIR = "sdk_stp_rp";
    public static final String SETUP_REPAIR_EXCEPTION = "sdk_stp_rep_exc";
    public static final String SETUP_START = "sdk_stp";
    public static final String SETUP_START_FINISH = "sdk_stp_fi";
    public static final String SETUP_SUCCESS = "sdk_stp_suc";
    public static final String SETUP_SUCCESS_INITED = "sdk_stp_i";
    public static final String SETUP_SUCCESS_LOADED = "sdk_stp_l";
    public static final String SETUP_SUCCESS_SETUPED = "sdk_stp_s";
    public static final String SETUP_TASK_ASYNC_VERIFY_FAILD = "sdk_async_vrf_faild";
    public static final String SETUP_TASK_DEXOPT = "sdk_opt";
    public static final String SETUP_TASK_HOOKDEX = "sdk_hookdex";
    public static final String SETUP_TASK_INIT = "sdk_ini";
    public static final String SETUP_TASK_LIBARY = "sdk_lib";
    public static final String SETUP_TASK_UCDEXOPT = "sdk_ucdexopt";
    public static final String SETUP_TASK_UPDATE = "sdk_upd";
    public static final String SETUP_TASK_VERIFY = "sdk_vrf";
    public static final String SETUP_TASK_VERIFY_DETAIL = "sdk_vrf_detail";
    public static final String SETUP_TOTAL_EXCEPTION = "sdk_stp_exc";
    public static final String SETUP_UCCORE_COST_HOUR = "stp_uc_hour";
    public static final String SEVENZIP = "sdk_7z";
    public static final String SEVENZIP_CLEARHTTPCACHE = "sdk_7z_clear_httpcache";
    public static final String SEVENZIP_EXCEPTION_CRC = "sdk_7z_e3";
    public static final String SEVENZIP_EXCEPTION_FAILED = "sdk_7z_e1";
    public static final String SEVENZIP_EXCEPTION_MEM = "sdk_7z_e2";
    public static final String SEVENZIP_FILE = "sdk_7z_f";
    public static final String SEVENZIP_FILE_SUCCESS = "sdk_7z_fs";
    public static final String SEVENZIP_LIB = "sdk_7z_l";
    public static final String SEVENZIP_LIB_SUCCESS = "sdk_7z_ls";
    public static final String SEVENZIP_SUCCESS = "sdk_7z_s";
    public static final String SHARE_CORE_COPY_HAS_EXISTS_PV = "csc_chev";
    public static final String SHARE_CORE_COPY_NOT_PV = "csc_cntv";
    public static final String SHARE_CORE_COPY_OTHER_PV = "csc_corv";
    public static final String SHARE_CORE_COPY_SUCCESS_PV = "csc_cspv";
    public static final String SHARE_CORE_COPY_TASK_MAIN_PROCESS_COUNT_PV = "csc_cmcp";
    public static final String SHARE_CORE_COPY_TASK_NEW_PV = "csc_ctnp";
    public static final String SHARE_CORE_COPY_TASK_RUN_CALL_PV = "csc_crncp";
    public static final String SHARE_CORE_COPY_TASK_RUN_PV = "csc_crnp";
    public static final String SHARE_CORE_COPY_TASK_TOTEL_COUNT_PV = "csc_ctcp";
    public static final String SHARE_CORE_COPY_TASK_UPD_CALL_PV = "csc_cupdcp";
    public static final String SHARE_CORE_COPY_TASK_UPD_PV = "csc_cupdp";
    public static final String SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_AUTHORITY = "csc_cdra";
    public static final String SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_COPY = "csc_cdrc";
    public static final String SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_DELETE = "csc_cdrd";
    public static final String SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_EXCEPIION = "csc_cdre";
    public static final String SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_PRECONDITION = "csc_cdri";
    public static final String SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_PROCESS = "csc_cdrp";
    public static final String SHARE_CORE_COPY_TO_SDCARD_TASK_RESULT_TIMECOST = "csc_cdrt";
    public static final String SHARE_CORE_CREATE_DELAY_SEARE_CORE_FILE_TASK_PV = "csc_cdscfp";
    public static final String SHARE_CORE_CURRENT_IS_UC_CORE = "csc_cisuc";
    public static final String SHARE_CORE_DELAY_DECOMPRESS_START_PV = "csc_ddspv";
    public static final String SHARE_CORE_DELAY_SEARE_CORE_FILE_EXCEPTION_PV = "csc_ddep";
    public static final String SHARE_CORE_DELAY_SEARE_CORE_FILE_SETUP_PV = "csc_ddstp";
    public static final String SHARE_CORE_DELAY_SEARE_CORE_FILE_SUCCESS_PV = "csc_ddsp";
    public static final String SHARE_CORE_DELETE_CONFIG = "csc_dcf";
    public static final String SHARE_CORE_DELETE_DEC_DIR = "csc_deldd";
    public static final String SHARE_CORE_DELETE_UPD_FILE_PV = "csc_duftp";
    public static final String SHARE_CORE_DELETE_UPD_FILE_THREAD_CALL_PV = "csc_dufrlp";
    public static final String SHARE_CORE_DELETE_UPD_FILE_THREAD_CB_PV = "csc_dufrcp";
    public static final String SHARE_CORE_DELETE_UPD_FILE_THREAD_PV = "csc_dufrp";
    public static final String SHARE_CORE_DELETE_UPD_FILE_THREAD_SH_PV = "csc_dufrsp";
    public static final String SHARE_CORE_DEVICES_HAS_SHARE_CORE = "csc_dhsc";
    public static final String SHARE_CORE_FAULTTOLERANCE_SETUP_TASK_EXCEPTION = "csc_ftsre";
    public static final String SHARE_CORE_FAULTTOLERANCE_SETUP_TASK_KRL = "csc_ftsrk";
    public static final String SHARE_CORE_FAULTTOLERANCE_SETUP_TASK_RUN = "csc_ftsrc";
    public static final String SHARE_CORE_FAULTTOLERANCE_SETUP_TASK_ZIP = "csc_ftsrz";
    public static final String SHARE_CORE_FAULT_TOLERANCE_TASK = "csc_ftt";
    public static final String SHARE_CORE_FIRST_KERNAL_SETUP_SUCCESS = "csc_fkss";
    public static final String SHARE_CORE_GET_CORE_DEC_FILE_PATH = "csc_gcdfp";
    public static final String SHARE_CORE_HAS_UPD_SOURCE = "csc_hupds";
    public static final String SHARE_CORE_INIT_TASK_SUCCESS = "csc_tsu";
    public static final String SHARE_CORE_INIT_TASK_SUCCESS_FAILUE = "csc_tfi";
    public static final String SHARE_CORE_INIT_TASK_SUCCESS_IS_SHARECORE = "csc_tis";
    public static final String SHARE_CORE_INIT_TASK_SUCCESS_NOT_SHARECORE = "csc_tns";
    public static final String SHARE_CORE_INVALID_UPDATE_TIMES = "csc_iupdts";
    public static final String SHARE_CORE_LIST_SHARE_CORE_UCM = "csc_lsu";
    public static final String SHARE_CORE_LOCATION_SETUP_TASK_RUN = "csc_lsrc";
    public static final String SHARE_CORE_NEW_DEF_TASK = "csc_ndft";
    public static final String SHARE_CORE_NEW_SC_TASK = "csc_nsct";
    public static final String SHARE_CORE_NEW_UPD_TASK = "csc_nupt";
    public static final String SHARE_CORE_NOT_UPD_SC_INIT_FAILURE_PV = "csc_nsifp";
    public static final String SHARE_CORE_NOT_UPD_SC_INIT_SUCCESS_PV = "csc_nsisp";
    public static final String SHARE_CORE_NO_WIFI_TIMES = "csc_nwits";
    public static final String SHARE_CORE_OLD_KERNAL_SETUP_SUCCESS = "csc_okss";
    public static final String SHARE_CORE_SDCARD_SETUP_DELAY_SEARCH_CORE_FILE = "csc_ssdscf";
    public static final String SHARE_CORE_SDCARD_SETUP_SUCCESS = "csc_sdss";
    public static final String SHARE_CORE_SDCARD_SETUP_TASK_EXCEPTION = "csc_ssse";
    public static final String SHARE_CORE_SDCARD_SETUP_TASK_HAD_INIT = "csc_ssthi";
    public static final String SHARE_CORE_SDCARD_SETUP_TASK_LOCATION_DEC = "csc_ssld";
    public static final String SHARE_CORE_SDCARD_SETUP_TASK_RUN = "csc_ssrc";
    public static final String SHARE_CORE_SDCARD_SETUP_TASK_SDCARD = "csc_sssd";
    public static final String SHARE_CORE_SDK_SIG_VERIFY_EXCEPTION = "csc_sigvrfe";
    public static final String SHARE_CORE_SDK_SIG_VERIFY_EXCEPTION_VALUE = "csc_sigvrfe_v";
    public static final String SHARE_CORE_SDK_SUCCESS_CB_INIT_PV = "csc_sscip";
    public static final String SHARE_CORE_SDK_SUCCESS_CB_TOTEL_PV = "csc_ssctp";
    public static final String SHARE_CORE_SDK_VERSION_CHECK_FAILURE = "csc_vvfckf";
    public static final String SHARE_CORE_SDK_VERSION_CONFIG = "csc_vconf";
    public static final String SHARE_CORE_SDK_VERSION_DEC_CORE_FILE_EXCEPTION = "csc_vvfdece";
    public static final String SHARE_CORE_SDK_VERSION_DEC_CORE_FILE_EXCEPTION_VALUE = "csc_vvfdece_v";
    public static final String SHARE_CORE_SDK_VERSION_DEC_CORE_FILE_SUCCESS = "csc_vvfdecs";
    public static final String SHARE_CORE_SDK_VERSION_DEX_SDK_SHELL_CLASS_LOADER = "csc_vvfdscl";
    public static final String SHARE_CORE_SDK_VERSION_GET_FROME_DEX_SDK_SHELL_CLASS_LOADER = "csc_vvfgscl";
    public static final String SHARE_CORE_SDK_VERSION_VALUE = "csc_vval";
    public static final String SHARE_CORE_SDK_VERSION_VERIFY_ERROR = "csc_vvferr";
    public static final String SHARE_CORE_SDK_VERSION_VERIFY_ERROR_VALUE = "csc_vvferr_v";
    public static final String SHARE_CORE_SDK_VERSION_VERIFY_EXCEPTION = "csc_vvfexc";
    public static final String SHARE_CORE_SDK_VERSION_VERIFY_EXCEPTION_VALUE = "csc_vvfexc_v";
    public static final String SHARE_CORE_SDK_VERSION_VERIFY_STAT_VALUE = "csc_vvfsv";
    public static final String SHARE_CORE_SEARCH_CORE_FILE_EXCEPTION = "csc_scfe";
    public static final String SHARE_CORE_SEARCH_CORE_FILE_SDCARD = "csc_scfd";
    public static final String SHARE_CORE_SEARCH_CORE_FILE_TASK_RUN = "csc_scftc";
    public static final String SHARE_CORE_SKIP_ONCE_VERIFY_CORE_FILE = "csc_sovcf";
    public static final String SHARE_CORE_TASK_BEGIN_PROCESS = "csc_tbp";
    public static final String SHARE_CORE_TASK_PROCESS = "csc_tp";
    public static final String SHARE_CORE_UPDATE_STILL = "csc_usl";
    public static final String SHARE_CORE_UPDATE_STOP = "csc_usp";
    public static final String SHARE_CORE_UPDATE_URL_ONCE_DOWNLOADED = "csc_updod";
    public static final String SHARE_CORE_UPD_DOWNLOAD_ELAPSE_TIME = "csc_udetm";
    public static final String SHARE_CORE_UPD_SC_INIT_EXCEPTION_PV = "csc_usiep";
    public static final String SHARE_CORE_UPD_SC_INIT_SUCCESS_PV = "csc_usisp";
    public static final String UCM = "sdk_ucm";
    public static final String UCM_DISK_MB = "sdk_ucm_dm";
    public static final String UCM_DISK_PERCENT = "sdk_ucm_dp";
    public static final String UCM_EXCEPTION_CHECK = "sdk_ucm_en";
    public static final String UCM_EXCEPTION_DOWNLOAD = "sdk_ucm_e1";
    public static final String UCM_EXCEPTION_UPDATE = "sdk_dec7z";
    public static final String UCM_EXISTS = "sdk_ucm_e";
    public static final String UCM_FAILED_DOWNLOAD = "sdk_dec7z_s";
    public static final String UCM_FAILED_VERIFY = "sdk_dec7z_ls";
    public static final String UCM_LAST_EXCEPTION = "sdk_ucm_le";
    public static final String UCM_OLD = "sdk_ucm_old";
    public static final String UCM_PERCENT = "sdk_ucm_p";
    public static final String UCM_RECOVERED = "sdk_ucm_f";
    public static final String UCM_SUCCESS = "sdk_ucm_s";
    public static final String UCM_SUCCESS_NOT_CORE_DOWNLAOD = "sdk_ucm_so";
    public static final String UCM_WIFI = "sdk_ucm_wifi";
    public static final String US_CP_DISPATCH_REQUEST = "us_cp_dis_req";
    public static final String US_CP_RESPOND_FAILURE = "us_cp_req_fai";
    public static final String US_CP_RESPOND_RECEIVE = "us_cp_req_rec";
    public static final String US_CP_RESPOND_STATUS_CODE = "us_cp_req_st_co";
    public static final String US_CP_RESPOND_SUCCESS = "us_cp_req_suc";
    public static final String UTDID_KEY = "ut_k";
    public static final String VIDEO_AC = "sdk_vac";
    public static final String VIDEO_DOWNLOAD = "sdk_vdl";
    public static final String VIDEO_DOWNLOAD_SUCCESS = "sdk_vdls";
    public static final String VIDEO_ERROR_CODE_DOWNLOAD = "sdk_ecdl1";
    public static final String VIDEO_ERROR_CODE_UNZIP = "sdk_ecuz1";
    public static final String VIDEO_ERROR_CODE_UPDATE_CHECK_REQUEST = "sdk_ecur1";
    public static final String VIDEO_ERROR_CODE_VERIFY = "sdk_ecv1";
    public static final String VIDEO_UNZIP = "sdk_vz";
    public static final String VIDEO_UNZIP_SUCCESS = "sdk_vzs";
    public static final String WV_NEW_AFTER = "sdk_wv_a";
    public static final String WV_NEW_BEFORE = "sdk_wv_b";

    @Api
    /* compiled from: ProGuard */
    public static class WaStat {
        static ISaveListener a;

        @Api
        /* compiled from: ProGuard */
        public interface ISaveListener {
            void onWillSave();
        }

        public static void setPrintLogEnable(boolean z) {
            a.c = z;
        }

        public static boolean getPrintLogEnable() {
            return a.c;
        }

        public static void statPV(String str) {
            if (a.a() != null) {
                a a2 = a.a();
                if (a.b()) {
                    if (!b.a(str)) {
                        if (a.a) {
                            a.a = false;
                            IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
                            if (iGlobalSettings != null) {
                                int intValue = iGlobalSettings.getIntValue("SdkStatFileLimit");
                                if (intValue != 0 && intValue < a.f) {
                                    a.e = intValue;
                                    a.g = intValue + 1024;
                                }
                            }
                            a2.b.postDelayed(new c(a2), 1000);
                        }
                        String lowerCase = str.toLowerCase();
                        if (lowerCase.startsWith(AjxHttpLoader.DOMAIN_HTTP) || lowerCase.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
                            if (Utils.sWAPrintLog) {
                                Log.d("SDKWaStat", "statPV:".concat(String.valueOf(lowerCase)));
                            }
                            a2.a((String) "sum_pv");
                            if (((Boolean) SDKFactory.invoke(10010, new Object[0])).booleanValue() && 2 == ((Integer) SDKFactory.invoke(SDKFactory.getCoreType, new Object[0])).intValue()) {
                                a2.a((String) "sum_swv_pv");
                            }
                        }
                    }
                    a2.a((String) "ill_upv");
                    return;
                }
                return;
            }
            Log.w("SDKWaStat", "statPV>>WaStatImp not inited");
        }

        public static void stat(String str) {
            if (str instanceof String) {
                a(str);
            }
        }

        private static void a(String str) {
            try {
                if (a.a() != null) {
                    a.a().a(str);
                } else {
                    Log.w("SDKWaStat", "stat>>WaStatImp not inited");
                }
            } catch (Throwable unused) {
            }
        }

        public static void statAdd(String str, int i) {
            try {
                if (a.a() != null) {
                    a a2 = a.a();
                    if (a.b()) {
                        a2.a(str, 0, 0, i, null);
                    }
                    return;
                }
                Log.w("SDKWaStat", "stat>>WaStatImp not inited");
            } catch (Throwable unused) {
            }
        }

        public static void statAverage(String str, int i) {
            try {
                if (a.a() != null) {
                    a a2 = a.a();
                    if (a.b()) {
                        a2.a(str, 2, 0, i, null);
                    }
                    return;
                }
                Log.w("SDKWaStat", "stat>>WaStatImp not inited");
            } catch (Throwable unused) {
            }
        }

        public static void stat(String str, String str2) {
            try {
                if (a.a() != null) {
                    a a2 = a.a();
                    if (a.b()) {
                        a2.a(str, 1, 1, 0, str2);
                    }
                    return;
                }
                Log.w("SDKWaStat", "stat>>WaStatImp not inited");
            } catch (Throwable unused) {
            }
        }

        public static void stat(String str, String str2, int i) {
            try {
                if (a.a() != null) {
                    a a2 = a.a();
                    if (a.b()) {
                        a2.a(str, 1, i, 0, str2);
                    }
                    return;
                }
                Log.w("SDKWaStat", "stat>>WaStatImp not inited");
            } catch (Throwable unused) {
            }
        }

        public static void stat(Pair<String, HashMap<String, String>> pair) {
            if (pair instanceof Pair) {
                statAKV(pair);
            }
        }

        public static void statAKV(Pair<String, HashMap<String, String>> pair) {
            try {
                if (a.a() != null) {
                    a a2 = a.a();
                    a.a(pair);
                    if (a.b()) {
                        if (100 < ((HashMap) pair.second).toString().length() && Utils.sWAPrintLog) {
                            StringBuilder sb = new StringBuilder("second length(");
                            sb.append(((HashMap) pair.second).toString().length());
                            sb.append(") more then 100");
                            Log.d("SDKWaStat", sb.toString());
                        }
                        synchronized (a2.i) {
                            if (a2.d == null) {
                                a2.d = new ArrayList();
                            }
                            ((HashMap) pair.second).put("tm", a2.h.format(new Date(System.currentTimeMillis())));
                            a2.d.add(new a.b((String) pair.first, (Map) pair.second));
                        }
                        return;
                    }
                    return;
                }
                Log.w("SDKWaStat", "stat>>WaStatImp not inited");
            } catch (Throwable unused) {
            }
        }

        public static void saveData() {
            if (a != null) {
                a.onWillSave();
            }
            if (a.a() != null) {
                a.a().a(false);
            }
        }

        public static void saveData(boolean z) {
            if (a != null) {
                a.onWillSave();
            }
            if (a.a() != null) {
                a.a().a(z);
            }
        }

        public static void setSaveListener(ISaveListener iSaveListener) {
            a = iSaveListener;
        }

        public static void upload() {
            if (a.a() != null) {
                a a2 = a.a();
                if (a.b() && !b.a((String) UCCore.getGlobalOption(UCCore.PROCESS_PRIVATE_DATA_DIR_SUFFIX_OPTION))) {
                    a2.b.postDelayed(new d(a2), 15000);
                }
            }
        }
    }
}
