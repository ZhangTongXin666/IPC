package demofour;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kys_31.study_demo.IBinderPool;

/**
 * Created by 老头儿 on 2017/9/19.
 * @function 这个包中讲解的主要是Binder连接池，用于避免多个任务模块造成开启多个Service
 */

public class BinderPoolService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return IBinderPool;
    }

    /**
     * 根据不同的BinderCode返回指定的Binder
     */
    private IBinder IBinderPool = new IBinderPool.Stub(){

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode){
                case BinderPool.BINDER_COMPUTE:
                    binder = new ComputeImpl(); //计算模块的Bidner
                    break;
                case BinderPool.BINDER_SERCURITY_CENTER:
                    binder = new SecurityCenterImpl();//安全模块的Binder
                    break;
                default:break;
            }
            return binder;
        }
    };
}
