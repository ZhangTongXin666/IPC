package demoTwo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by 老头儿 on 2017/9/15.
 * @function 这个包中主要讲解通过信使类 Messenger 进行进程间通信
 */

public class DemoTwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        Intent intent = new Intent(this,MessengerService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private Messenger messengerService;//定义信使引用

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messengerService = new Messenger(iBinder); //信使与服务端返回的IBinder连接，起始Message底层也是使用的AIDL,所以这里我们可以理解为cs = CustomSender.Stub.asInterface(iBinder); 对这个不理解的，请看demoone包中的例子
            /*构造发送的消息内容，在这里我们通过Message这个类，来作为我们信使发送的消息体*/
            Message message = new Message();
            message.what = 0;
            Bundle bundle = new Bundle(); //因为Message携带的数据类型有限，所以我们采用Bundle 扩大我们数据类型范围，正好message可以携带Bundle，这样我们就可以发送许多的数据类型了
            bundle.putString("msg","你好啊，服务端");
            message.setData(bundle);
            message.replyTo = messengerClient;// 这里将客户端的信使Messenger发送给服务端，使客户端能接受服务端发来的消息。
            try {
                messengerService.send(message);//发送数据
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    /**
     * 处理服务端发来的数据
     */
    private Handler handlerClient = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Log.e("TAG","服务端发来的消息："+msg.getData().get("msgService"));
                    break;
            }
        }
    };

    private Messenger messengerClient = new Messenger(handlerClient);//实例化客户端的信使Messenger ，用于接受服务端发送的消息

}
