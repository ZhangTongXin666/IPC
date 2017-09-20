package demoTwo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 老头儿 on 2017/9/15.
 * @function 这个包中主要讲解通过信使类 Messenger 进行进程间通信
 */

public class MessengerService extends Service {

    private Messenger messengerClicent; //定义信使Messager的引用，这里是为了接受客户端发来的信使。

    /**
     * 处理客户端发送的请求
     */
    private Handler messengerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:
                    messengerClicent = msg.replyTo;//接受客户端发送的信使,用于向客户端发送消息
                    if (messengerClicent != null){
                         /*下面同客户端峰发送方式*/
                        Message message = new Message();
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("msgService","你好的客户端");
                        message.setData(bundle);
                        try {
                            messengerClicent.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
            }
        }
    };

    Messenger messengerService = new Messenger(messengerHandler);//定义服务端信使Messenger。

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messengerService.getBinder();//返回给客户端IBinder,通过信使拿到信使的Binder,我们在DemoTwoAcvitiy中说过，Messenger底层也是通过AIDL实现的进程间通信，所以这里拿到Binder也不足为奇
    }
}
