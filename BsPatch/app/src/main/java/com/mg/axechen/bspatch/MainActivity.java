package com.mg.axechen.bspatch;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_WRITE = 100;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 安装APK的操作
            APKUtils.installApk(MainActivity.this, Contants.NEW_APK_PATH);
        }
    };

    private void call(){
        //1、判断是否有打电话的权限
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            //没有获取权限则做权限处理
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        call();
    }

    /**
     * 更新操作
     *
     * @param view
     */
    public void upload(View view) {
        if (APKUtils.getVersionCode(MainActivity.this, getPackageName()) >= 2) {
            //
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("已经是最新的版本了，无需更新！")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 下载就省略。 由于我没有服务器进行下载

                // 1、拿到老的APK
                String oldFile = APKUtils.getSourceApkPath(MainActivity.this, getPackageName());

                // 2、拿增量包的路径
                String patchFile = Contants.PATCH_FILE_PATH;

                Log.i("AXE", patchFile);
                // 3、开始执行合并操作
                int ret = BsPatch.patch(oldFile, Contants.NEW_APK_PATH, patchFile);

                // 4、合并的成功和失败
                if (ret == 0) {
                    Log.i("AXE", "合并成功");
                    handler.sendEmptyMessage(0);
                } else {
                    Log.i("AXE", "合并失败");
                }
            }
        }).start();
    }
}
