package com.mg.axechen.bspatch;

/**
 * Created by AxeChen on 2018/1/19.
 */

public class BsPatch {
    public native static int patch(String oldFile,String newFile,String patchFile);
    static {
        System.loadLibrary("AxeBsPatch");
    }
}
