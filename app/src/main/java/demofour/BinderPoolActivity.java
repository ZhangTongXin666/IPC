package demofour;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.kys_31.study_demo.ICompute;
import com.example.kys_31.study_demo.ISecurityCenter;
import com.example.kys_31.study_demo.binder.Binder;

/**
 * Created by 老头儿 on 2017/9/19.
 * @function 这个包中讲解的主要是Binder连接池，用于避免多个任务模块造成开启多个Service
 */

public class BinderPoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        /*重中之重，因为我们会将bindService（）这个方法 从异步转到同步，所以我们要开启线程来实现。*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doWork();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 两个任务模块，一个是安全模块，一个计算模块， 我们将两个任务模块通过Binder连接池，与一个Service进行连接。因为采用的AIDL所以，服务端支持高并发的操作
     * @throws RemoteException 远程过程调用错误
     */
    private void doWork() throws RemoteException {
        BinderPool binderPool = BinderPool.getInsance(BinderPoolActivity.this);//连接binder连接池 ，可以看出BinderPool是一个单例模式
        Log.e("TAG", "连接池就绪完毕");
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SERCURITY_CENTER);//通过连接池BinderPool的queryBinder（）方法根据不同的标签拿到对应的Binder；
        ISecurityCenter isecurityCenter = SecurityCenterImpl.asInterface(securityBinder);//这个方法我们在demoone包中已经讲解
        String password = isecurityCenter.encrypt("helloworld-安卓");//调用加密方法
        Log.e("TAG", "加密后的密码："+password +"   解密后的密码："+isecurityCenter.decrypt(password)); //输出加密后的字符串，和解密后的字符串

        /*同上*/
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute mCompute = ComputeImpl.asInterface(computeBinder);
        Log.e("TAG", "相加后的结果："+mCompute.add(1, 2));//输出1和2相加后的结果
    }
}
