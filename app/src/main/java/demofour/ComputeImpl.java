package demofour;

import android.os.RemoteException;

import com.example.kys_31.study_demo.ICompute;

/**
 * Created by 老头儿 on 2017/9/19.
 * @function 这个包中讲解的主要是Binder连接池，用于避免多个任务模块造成开启多个Service
 */

public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
