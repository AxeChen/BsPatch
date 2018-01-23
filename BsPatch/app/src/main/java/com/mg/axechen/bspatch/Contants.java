package com.mg.axechen.bspatch;

import android.os.Environment;

import java.io.File;

/**
 * Created by AxeChen on 2018/1/20.
 */

public class Contants {

    public static final String SD_CARD = Environment.getExternalStorageDirectory() + File.separator;

    //新版本apk的目录
    public static final String NEW_APK_PATH = SD_CARD + "new_app.apk";

    //增量包的路径
    public static final String PATCH_FILE_PATH = SD_CARD + "apk.patch";
}
