package demoone;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import java.util.List;

/**
 * Created by 老头儿 on 2017/9/14.
 * @function 这个包中讲解的是通过AIDL 实现客户端与服务端的跨进程通信
 */

public class MyApplication extends Application{

    @Override
    public void onCreate(){
        super.onCreate();
        getProcessName();
    }

    public void getProcessName() {

        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);//获得Activity服务
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();//获得应用的所用进程
        if (list == null){
            return;
        }

        //输出对应的进程名
        for (ActivityManager.RunningAppProcessInfo processInfo : list){
            if (processInfo != null && processInfo.pid == Process.myPid()){
                Log.e("TAG","当前进程名字："+processInfo.processName);
            }
        }
    }
}
