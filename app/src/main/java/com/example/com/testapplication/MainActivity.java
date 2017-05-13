package com.example.com.testapplication;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  这是一个测试Demo
 *  我们在 源码SDK Application 里添加了一个 testShowToast()方法,把修改后的源码编译刷进手机里面，这个demo是测试添加的
 *  方法；由于AS使用的SDK里并没有这个方法，我们只能使用反射的方式，绕过编译检查，来调用 testShowToast()
 *
 *  public void testShowToast(){
 *     Toast.makeText(this, "test make update-api", Toast.LENGTH_LONG).show();
 *     }
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.bt_test);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class applicationClass = null;
        Method testShowToast = null;
        try {

            //  通过包名获得 Application class type
            applicationClass = Class.forName("android.app.Application");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {

            // 获取我们自己在 Application 中添加的方法
            testShowToast = applicationClass.getDeclaredMethod("testShowToast");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        //获得 Application 对象
        Application application = getApplication();
        try {

            // 反射调用我们添加的方法
            testShowToast.invoke(application);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
