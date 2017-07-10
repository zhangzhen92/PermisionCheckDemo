package com.example.zz.permisioncheckdemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zz.permisioncheckdemo.utils.PermissionUtils;

import java.io.File;

/**
 * 类描述：6.0动态权限申请
 * 创建人：zz
 * 创建时间：2017/7/10 15:32
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView textTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化UI
     */
    private void initView() {
        textTest = ((TextView) findViewById(R.id.textview_test));
        textTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textview_test:
                if(PermissionUtils.checkPermissions(this,PermissionUtils.photosPermissions)) {
                    takePhoto();
                }
                break;
        }
    }

    /**
     * 照相
     */
    private void takePhoto() {
        String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/测试内存权限";
        File file = new File(s);
        if(!file.exists()){
            file.mkdirs();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//实例化Intent对象,使用MediaStore的ACTION_IMAGE_CAPTURE常量调用系统相机
        startActivityForResult(intent, 1);//开启相机，传入上面的Intent对
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PermissionUtils.REQUEST_CODE){
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                takePhoto();
            } else {
                Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
