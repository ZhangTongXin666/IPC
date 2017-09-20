package demoone;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kys_31.study_demo.CustomData;
import com.example.kys_31.study_demo.CustomReceiver;
import com.example.kys_31.study_demo.CustomSender;

/**
 * Created by 老头儿 on 2017/9/14.
 * @function 这个包中讲解的是通过AIDL 实现客户端与服务端的跨进程通信
 */

public class CustomService extends Service {

    private static final String TAG ="CustomService";

    /**
     * 这个方法是客户端通过bindservice（）与服务端进行连接后，服务端需要从这里返回IBinder给客户端
     * @param intent
     * @return 返回自己实现的IBinder
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sender;
    }

    private RemoteCallbackList<CustomReceiver> rcl = new RemoteCallbackList<>();// 远程回调List,专门用于跨进程注册、解除监听使用。关于这个类的详细使用，请自行查阅。

    private IBinder sender = new CustomSender.Stub() { //返回给客户端的IBinder
        @Override
        public void sendMessage(CustomData customData) throws RemoteException {
            if (customData == null){  //客户端发送给服务端的数据 ，对数据进行判断
                Log.e(TAG,"客户端是空的");
            }else{
                Log.e(TAG,"姓名："+customData.name+"  id："+customData.id); //取出数据
            }
        }

        @Override
        public void register(CustomReceiver customReceiver) throws RemoteException {
            if (customReceiver != null)
            rcl.register(customReceiver); //注册监听
        }

        @Override
        public void unRegister(CustomReceiver costomReceiver) throws RemoteException {
            if (costomReceiver != null)
            rcl.unregister(costomReceiver);//解除监听
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 服务端每隔3秒需要给客户端发送一条信息
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0 ;
                while (count < 10){
                    count++;
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int size = rcl.beginBroadcast();//得到注册的客户端监听的数目
                    for (int i = 0; i < size; i++){
                        CustomReceiver customReceiver = rcl.getBroadcastItem(i);//取出指定位置的 成员
                        if (customReceiver != null){ //判空
                            try {
                                customReceiver.onMessageReceiver("来自服务端中的消息，时间："+System.currentTimeMillis());//发送消息
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    rcl.finishBroadcast();//beginBroadcast（）和finishBroadcast（）方法必须一起使用。
                }
            }
        }).start();
    }
}
