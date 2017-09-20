package demoone;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.example.kys_31.study_demo.CustomData;
import com.example.kys_31.study_demo.CustomReceiver;
import com.example.kys_31.study_demo.CustomSender;

/**
 * Created by 老头儿 on 2017/9/14.
 * @function 这个包中讲解的是通过AIDL 实现客户端与服务端的跨进程通信
 */

public class DemoActivity extends AppCompatActivity {

    private CustomSender cs;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        Intent startService = new Intent(this,CustomService.class);
        bindService(startService,sc, Context.BIND_AUTO_CREATE); //连接服务端
    }

    /**
     * 连接成功后，服务端返回的数据就在这里
     */
    ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
             CustomData cd = new CustomData(1,"张小米");//客户端要发送的数据
             cs = CustomSender.Stub.asInterface(iBinder); //判断客户端与服务端是否在一个进程，根据这里拿到服务端的返回对象的代理、还是本身对象。
            try {
                cs.sendMessage(cd); //发送消息
                cs.register(cr);//注册监听器
                cs.asBinder().linkToDeath(deathRecipient,0); //连接死亡代理
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    /**
     * 用于接受服务端发送的数据
     */
    CustomReceiver cr = new CustomReceiver.Stub() {
        @Override
        public void onMessageReceiver(String message) throws RemoteException {
            if (TextUtils.isEmpty(message)){
                Log.e("TAG","服务端消息为空");
            }else {
                Log.e("TAG","服务端消息为："+message);
            }
        }
    };

    /**
     * 死亡代理，当客户端与服务端异常中断时，客户端首先解除死亡代理，然后进行重连机制。
     */
    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (cs != null){
                    cs.asBinder().unlinkToDeath(this,0); //解除死亡代理
                    cs = null;
            }
            /*重连机制*/
            Intent startService = new Intent(DemoActivity.this,CustomService.class);
            bindService(startService,sc, Context.BIND_AUTO_CREATE);
        }
    };
}
