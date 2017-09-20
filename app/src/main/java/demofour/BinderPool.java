package demofour;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.kys_31.study_demo.IBinderPool;
import com.example.kys_31.study_demo.binder.Binder;

import java.util.concurrent.CountDownLatch;

/**
 * Created by 老头儿 on 2017/9/19.
 * @function 这个包中讲解的主要是Binder连接池，用于避免多个任务模块造成开启多个Service
 */

public class BinderPool  {

    private static final String TAG = "BinderPool";
    public static final int BINDER_COMPUTE = 0; //计算模块的标签
    public static final int BINDER_SERCURITY_CENTER = 1;//安全模块的标签

    private Context mContext; //上下文环境
    private IBinderPool mBinderPool;//AIDL接口，定义了queryBinder（）方法
    private static volatile BinderPool sInstance; // 单例模式的对象，对于描述符volatile 说明BinderPool对象只能从同一块内存区域取放，因为引用型变量的对象在两块内存中存放，
    // 用volatile描述符可以保证BinderPool对象只能从一处内存取放，避免了一处内存的值发生变化，而另一处确仍然保持原值，
    // 而后边取时从保存原值的地方取出的。

    private CountDownLatch mConnectBinderPoolCountDownLatch;//定义将异步转同步时所用对象引用 （闭锁）


    /**
     * 以下是单例模式
     * @param context 上下文环境
     */
    private BinderPool(Context context){
        mContext = context;
        connectBinderPoolService();//连接线程池服务
    }
    public static BinderPool getInsance(Context context){
        if (sInstance == null){
            synchronized (BinderPool.class){
                if (sInstance == null){
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }


    /**
     * 在正常情况下 连接服务 也是和单例模式联系的，只连接一次。
     */
    private synchronized void connectBinderPoolService() {
        Log.e("TAG", "客户端请求连接服务端");
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);//开启闭锁
        Intent serviceIntent = new Intent(mContext,BinderPoolService.class);
        mContext.bindService(serviceIntent, mBinderPoolConnection, Context.BIND_AUTO_CREATE); //开启服务，异步任务
        try {
            mConnectBinderPoolCountDownLatch.await();//等待异步任务完成后继续往下执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinderPool = IBinderPool.Stub.asInterface(iBinder);
            try {
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mConnectBinderPoolCountDownLatch.countDown();//开启等待的闭锁，告知异步任务完成。
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("TAG", "服务端断开连接");
        }
    };

    private Binder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;
            connectBinderPoolService();
        }
    };

    public IBinder queryBinder(int binderCode){
        IBinder binder = null;
        if (mBinderPool != null){
            try {
                Log.e("TAG", "返回Binder");
                binder = mBinderPool.queryBinder(binderCode);//根据BinderCode 获得指定的binder；获取是到服务端进行获取，这里就是通过AIDL进行跨进程通信
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return binder;
    }
}
